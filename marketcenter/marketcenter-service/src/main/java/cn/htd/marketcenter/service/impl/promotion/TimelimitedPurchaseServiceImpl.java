package cn.htd.marketcenter.service.impl.promotion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dao.PromotionStatusHistoryDAO;
import cn.htd.marketcenter.dao.TimelimitedInfoDAO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionStatusHistoryDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.service.PromotionBaseService;
import cn.htd.marketcenter.service.TimelimitedPurchaseService;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;

@Service("timelimitedPurchaseService")
public class TimelimitedPurchaseServiceImpl implements
		TimelimitedPurchaseService {

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private TimelimitedRedisHandle timelimitedRedisHandle;

	@Resource
	private MarketCenterRedisDB marketRedisDB;

	@Resource
	private PromotionBaseService baseService;

	@Resource
	private PromotionInfoDAO promotionInfoDAO;

	@Resource
	private PromotionStatusHistoryDAO promotionStatusHistoryDAO;

	@Resource
	private TimelimitedInfoDAO timelimitedInfoDAO;

	/**
	 * 限时购 - 新增限时购活动信息
	 * 
	 * @author li.jun
	 * @time 2017-10-09
	 * @param timelimitedInfo
	 * @return
	 */
	@Override
	public ExecuteResult<TimelimitedInfoDTO> addTimelimitedInfo(
			TimelimitedInfoDTO timelimitedInfo) {
		ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
		PromotionInfoDTO promotionInfo = null;
		PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
		List<PromotionStatusHistoryDTO> historyList = new ArrayList<PromotionStatusHistoryDTO>();
		try {
			timelimitedInfo.setPromotionType(dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_TYPE,
					DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED));
			timelimitedInfo.setSkuTimelimitedPrice(CalculateUtils
					.setScale(timelimitedInfo.getSkuTimelimitedPrice()));
			promotionInfo = baseService.insertPromotionInfo(timelimitedInfo);
			timelimitedInfo.setPromoionInfo(promotionInfo);
			List<? extends PromotionAccumulatyDTO> accumulatyList = timelimitedInfo
					.getPromotionAccumulatyList();
			if (accumulatyList.size() > 0) {
				for (PromotionAccumulatyDTO accumulaty : accumulatyList) {
					timelimitedInfoDAO.add((TimelimitedInfoDTO) accumulaty);
				}
			}
			historyDTO.setPromotionId(timelimitedInfo.getPromotionId());
			historyDTO.setPromotionStatus(timelimitedInfo.getShowStatus());
			historyDTO.setPromotionStatusText(dictionary.getNameByValue(
					DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
					timelimitedInfo.getShowStatus()));
			historyDTO.setCreateId(timelimitedInfo.getCreateId());
			historyDTO.setCreateName(timelimitedInfo.getCreateName());
			promotionStatusHistoryDAO.add(historyDTO);
			historyList.add(historyDTO);
			timelimitedInfo.setPromotionStatusHistoryList(historyList);
			// timelimitedRedisHandle.addTimelimitedInfo2Redis(timelimitedInfo);
			timelimitedRedisHandle
					.addTimelimitedPurchaseInfo2Redis(timelimitedInfo);
			result.setResult(timelimitedInfo);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return result;
	}

	/**
	 * 限时购 － 获取对应的限时购信息(根据sku查询)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExecuteResult<List<TimelimitedInfoDTO>> getTimelimitedInfo(
			String skuCode) {
		ExecuteResult<List<TimelimitedInfoDTO>> result = new ExecuteResult<List<TimelimitedInfoDTO>>();
		List<TimelimitedInfoDTO> resultList = new ArrayList<TimelimitedInfoDTO>();
		List<String> promotionIdList = new ArrayList<String>();
		Date nowDt = new Date();
		TimelimitedInfoDTO timelimitedInfoDTO = null;
		String timelimitedJSONStr = "";
		try {
			if (StringUtils.isNotEmpty(skuCode)) {
				Map<String, String> resultMap = getPromotionlistRedis(skuCode);
				if (resultMap.get("SUCCESS") != null) {
					String promotionIdStr = resultMap.get("SUCCESS");
					promotionIdList = JSON.parseObject(promotionIdStr,
							List.class);
				} else if (resultMap.get("ERROR1").equals("ERROR1")) {
					throw new MarketCenterBusinessException(
							MarketCenterCodeConst.LIMITED_TIME_PURCHASE_DOWN_SHELF,
							"该商品限时活动已下架");
				} else if (resultMap.get("ERROR2").equals("ERROR2")) {
					throw new MarketCenterBusinessException(
							MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NULL,
							"该商品限时活动不存在");
				} else if (resultMap.get("ERROR3") != null) {
					result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
					result.addErrorMessage(resultMap.get("ERROR3"));
					return result;
				}
				for (String promotionId : promotionIdList) {
					timelimitedJSONStr = marketRedisDB.getHash(
							RedisConst.REDIS_TIMELIMITED, promotionId);
					List<?> list = (List<?>) JSONObject.fromObject(
							timelimitedJSONStr).get("promotionAccumulatyList");
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							timelimitedInfoDTO = (TimelimitedInfoDTO) JSONObject
									.toBean(JSONObject.fromObject(list.get(i)),
											TimelimitedInfoDTO.class);
							if (!nowDt
									.before(timelimitedInfoDTO.getStartTime())) {
								throw new MarketCenterBusinessException(
										MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NOT_BEGIN,
										"该商品限时活动未开始");
							} else if (nowDt.after(timelimitedInfoDTO
									.getEndTime())) {
								throw new MarketCenterBusinessException(
										MarketCenterCodeConst.LIMITED_TIME_PURCHASE_IS_OVER,
										"该商品限时活动已结束");
							}
							resultList.add(timelimitedInfoDTO);
						}
					}
				}
				result.setCode("00000");
				result.setResult(resultList);
			}
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return result;
	}

	/**
	 * 限时购 － 获取对应的限时购信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExecuteResult<List<TimelimitedInfoDTO>> getTimelimitedInfo(
			TimelimitedInfoDTO dto) {
		ExecuteResult<List<TimelimitedInfoDTO>> result = new ExecuteResult<List<TimelimitedInfoDTO>>();
		List<TimelimitedInfoDTO> resultList = new ArrayList<TimelimitedInfoDTO>();
		List<String> promotionIdList = new ArrayList<String>();
		Date nowDt = new Date();
		TimelimitedInfoDTO timelimitedInfoDTO = null;
		String timelimitedJSONStr = "";
		try {
			Map<String, String> resultMap = getPromotionlistRedis(null);
			if (resultMap.get("SUCCESS") != null) {
				String promotionIdStr = resultMap.get("SUCCESS");
				promotionIdList = JSON.parseObject(promotionIdStr, List.class);
			} else if (resultMap.get("ERROR1").equals("ERROR1")) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.LIMITED_TIME_PURCHASE_DOWN_SHELF,
						"该商品限时活动已下架");
			} else if (resultMap.get("ERROR2").equals("ERROR2")) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NULL,
						"该商品限时活动不存在");
			} else if (resultMap.get("ERROR3") != null) {
				result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
				result.addErrorMessage(resultMap.get("ERROR3"));
				return result;
			}
			for (String promotionId : promotionIdList) {
				timelimitedJSONStr = marketRedisDB.getHash(
						RedisConst.REDIS_TIMELIMITED, promotionId);
				List<?> list = (List<?>) JSONObject.fromObject(
						timelimitedJSONStr).get("promotionAccumulatyList");
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						timelimitedInfoDTO = (TimelimitedInfoDTO) JSONObject
								.toBean(JSONObject.fromObject(list.get(i)),
										TimelimitedInfoDTO.class);
						if (dto.getPurchaseFlag() == 1
								&& !nowDt.before(timelimitedInfoDTO
										.getEffectiveTime())
								&& !nowDt.after(timelimitedInfoDTO
										.getInvalidTime())) {
							/**
							 * 今日特惠
							 */
							resultList.add(timelimitedInfoDTO);
						} else if (dto.getPurchaseFlag() == 2
								&& !nowDt.before(timelimitedInfoDTO
										.getStartTime())) {
							/**
							 * 开售预告
							 */
							resultList.add(timelimitedInfoDTO);
						}
					}
				}
			}
			result.setCode("00000");
			result.setResult(resultList);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return result;
	}

	private Map<String, String> getPromotionlistRedis(String skuCode) {
		Map<String, String> resultMap = new HashMap<String, String>();
		List<String> promotionIdList = new ArrayList<String>();
		List<String> purchaseIndexList = new ArrayList<String>();
		Set<String> purChaseSet = null;
		String promotionIdStr = "";
		String validStatus = "";
		try {
			purChaseSet = marketRedisDB
					.getHashFields(RedisConst.REDIS_TIMELIMITED_INDEX);
			if (null != purChaseSet && !purChaseSet.isEmpty()) {
				for (String pur : purChaseSet) {
					String purchaseFirst = pur.substring(0, 1);
					String purchaseSecond = pur.substring(1, 2);
					if("&".equals(purchaseSecond) && dictionary.getValueByCode(
							DictionaryConst.TYPE_PROMOTION_TYPE,
							DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED).equals(purchaseFirst)){
						if (StringUtils.isNotEmpty(skuCode)) {
							if (pur.contains(skuCode)) {
								
								purchaseIndexList.add(pur);
							}
						} else {
							purchaseIndexList.add(pur);
						}
					}
				}
				if(!purchaseIndexList.isEmpty()){
					for (String purchaseIndex : purchaseIndexList) {
						promotionIdStr = marketRedisDB.getHash(
								RedisConst.REDIS_TIMELIMITED_INDEX,
								purchaseIndex);
						validStatus = marketRedisDB.getHash(
								RedisConst.REDIS_TIMELIMITED_VALID, promotionIdStr);
						if (!StringUtils.isEmpty(validStatus)
								&& dictionary
										.getValueByCode(
												DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
												DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID)
										.equals(validStatus)) {
							promotionIdList.add(promotionIdStr);
						} else {
							resultMap.put("ERROR1", "ERROR1");
						}
					}
				}
				if (promotionIdList.size() > 0) {
					resultMap
							.put("SUCCESS", JSON.toJSONString(promotionIdList));
				}
			} else {
				resultMap.put("ERROR2", "ERROR2");
			}
		} catch (Exception e) {
			resultMap.put("ERROR3", ExceptionUtils.getStackTraceAsString(e));
		}
		return resultMap;
	}
}
