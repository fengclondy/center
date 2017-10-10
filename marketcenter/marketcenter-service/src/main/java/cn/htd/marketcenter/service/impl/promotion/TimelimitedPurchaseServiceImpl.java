package cn.htd.marketcenter.service.impl.promotion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
	 * 限时购 － 获取对应的限时购信息
	 */
	@Override
	public ExecuteResult<TimelimitedInfoDTO> getTimelimitedInfo(String skuCode) {
		ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
		List<String> promotionIdList = new ArrayList<String>();
		List<String> purchaseIndexList = new ArrayList<String>();
		Date nowDt = new Date();
		Set<String> purChaseSet = null;
		TimelimitedInfoDTO timelimitedInfoDTO = null;
		String timelimitedJSONStr = "";
		String promotionIdStr = "";
		String validStatus = "";
		try {
			if (StringUtils.isNotEmpty(skuCode)) {
				// 根据skuCode查询限时抢购信息
				purChaseSet = marketRedisDB
						.getHashFields(RedisConst.REDIS_TIMELIMITED_PURCHASE_INDEX);
				if (null != purChaseSet && !purChaseSet.isEmpty()) {
					for (String pur : purChaseSet) {
						if (pur.contains(skuCode)) {
							purchaseIndexList.add(pur);
						}
					}
					for (String purchaseIndex : purchaseIndexList) {
						promotionIdStr = marketRedisDB.getHash(
								RedisConst.REDIS_TIMELIMITED_PURCHASE_INDEX,
								purchaseIndex);
						validStatus = marketRedisDB.getHash(
								RedisConst.REDIS_TIMELIMITED_VALID,
								promotionIdStr);
						if (!StringUtils.isEmpty(validStatus)
								&& dictionary
										.getValueByCode(
												DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
												DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID)
										.equals(validStatus)) {
							promotionIdList.add(promotionIdStr);
						} else {
							throw new MarketCenterBusinessException(
									MarketCenterCodeConst.LIMITED_TIME_PURCHASE_DOWN_SHELF,
									"该商品限时活动已下架");
						}
					}
					for (String promotionId : promotionIdList) {
						timelimitedJSONStr = marketRedisDB.getHash(
								RedisConst.REDIS_TIMELIMITED, promotionId);
						//用这种方法
//						List<?> list =  (List<?>) JSONObject.fromObject(timelimitedJSONStr).get("promotionAccumulatyList");
//						if(list != null && list.size()>0){
//							timelimitedInfoDTO = (TimelimitedInfoDTO) JSONObject.toBean(JSONObject.fromObject(list.get(0)), TimelimitedInfoDTO.class);
//						}
//						
						//此种方法作废
						timelimitedInfoDTO = JSON.parseObject(
								timelimitedJSONStr, TimelimitedInfoDTO.class);
						List AccumulatyList = timelimitedInfoDTO
								.getPromotionAccumulatyList();
						for (int i = 0; i < AccumulatyList.size(); i++) {
//							;
//							TimelimitedInfoDTO timelimite = (TimelimitedInfoDTO) JSONObject
//									.toBean(JSONObject
//											.fromObject(AccumulatyList.get(i)),
//											TimelimitedInfoDTO.class);
							TimelimitedInfoDTO timelimite = JSONObject.toJavaObject((JSONObject) AccumulatyList.get(i), TimelimitedInfoDTO.class);

							if (nowDt.before(timelimite.getStartTime())) {
								throw new MarketCenterBusinessException(
										MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NOT_BEGIN,
										"该商品限时活动未开始");
							} else if (nowDt.after(timelimite.getEndTime())) {
								throw new MarketCenterBusinessException(
										MarketCenterCodeConst.LIMITED_TIME_PURCHASE_IS_OVER,
										"该商品限时活动已结束");
							}
							result.setCode("00000");
							result.setResult(timelimitedInfoDTO);
						}
					}
				} else {
					throw new MarketCenterBusinessException(
							MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NULL,
							"该限时购商品不存在");
				}
			} else {
				// 聚合页限时抢购信息查询

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

}
