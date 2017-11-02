package cn.htd.marketcenter.service.impl.promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DateUtils;
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
import cn.htd.marketcenter.domain.TimelimitedCheckInfo;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionListDTO;
import cn.htd.marketcenter.dto.PromotionStatusHistoryDTO;
import cn.htd.marketcenter.dto.TimelimitPurchaseItemInfoDTO;
import cn.htd.marketcenter.dto.TimelimitPurchaseMallInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedConditionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedListDTO;
import cn.htd.marketcenter.dto.TimelimitedResultDTO;
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
					DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT));
			timelimitedInfo.setSkuTimelimitedPrice(CalculateUtils
					.setScale(timelimitedInfo.getSkuTimelimitedPrice()));
			checkTimelimitedDuringRepeat(timelimitedInfo);
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
			timelimitedRedisHandle.addTimelimitedInfo2Redis(timelimitedInfo);
			// timelimitedRedisHandle.addTimelimitedPurchaseInfo2Redis(timelimitedInfo);
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
	 * 校验活动期间是否已经存在活动
	 *
	 * @param timelimitedInfo
	 * @throws MarketCenterBusinessException
	 */
	private void checkTimelimitedDuringRepeat(TimelimitedInfoDTO timelimitedInfo)
			throws MarketCenterBusinessException {
		List<PromotionInfoDTO> promotionList = null;
		TimelimitedCheckInfo condition = new TimelimitedCheckInfo();
		List<String> promotionTypeList = new ArrayList<String>();
		String promotionTypePurchase = dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_TYPE,
				DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT);
		String promotionTypeTimelited = dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_TYPE,
				DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
		promotionTypeList.add(promotionTypePurchase);
		promotionTypeList.add(promotionTypeTimelited);
		condition.setPromotionTypeList(promotionTypeList);
		condition.setDeleteStatus(dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_STATUS,
				DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
		List<? extends PromotionAccumulatyDTO> accumulatyList = timelimitedInfo
				.getPromotionAccumulatyList();
		if (accumulatyList.size() > 0) {
			for (PromotionAccumulatyDTO accumulaty : accumulatyList) {
				TimelimitedInfoDTO timeLimited = (TimelimitedInfoDTO) accumulaty;
				if (!(new Date()).before(timeLimited.getStartTime())) {
					throw new MarketCenterBusinessException(
							MarketCenterCodeConst.LIMITED_TIME_PURCHASE_START,
							"不能创建已经开始的限时购活动!");
				}
				condition.setSkuCode(timeLimited.getSkuCode());
				promotionList = promotionInfoDAO
						.queryRepeatTimelimitedList(condition);

			}
		}
		if (promotionList != null && !promotionList.isEmpty()) {
			throw new MarketCenterBusinessException(
					MarketCenterCodeConst.TIMELIMITED_DURING_REPEAT,
					" 该商品存在未结束的促销活动!");
		}
	}

	@Override
	public ExecuteResult<DataGrid<PromotionListDTO>> queryPromotionListByCondition(
			TimelimitedConditionDTO conditionDTO, Pager<TimelimitedInfoDTO> page) {

		ExecuteResult<DataGrid<PromotionListDTO>> result = new ExecuteResult<DataGrid<PromotionListDTO>>();
		DataGrid<PromotionListDTO> dataGrid = new DataGrid<PromotionListDTO>();
		List<PromotionListDTO> promotionInfoList = new ArrayList<PromotionListDTO>();
		TimelimitedInfoDTO searchConditionDTO = new TimelimitedInfoDTO();
		long count = 0;
		try {
			searchConditionDTO.setPromotionType(dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_TYPE,
					DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT));
			searchConditionDTO.setItemCode(conditionDTO.getSkuCode());
			searchConditionDTO.setSkuName(conditionDTO.getSkuName());
			searchConditionDTO.setShowStatus(conditionDTO.getStatus());
			searchConditionDTO.setSellerCode(conditionDTO.getSelleCode());
			searchConditionDTO.setDeleteStatus(dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_STATUS,
					DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
			searchConditionDTO.setStartTimeStr(conditionDTO.getStartTimeStr());
			searchConditionDTO.setEndTimeStr(conditionDTO.getEndTimeStr());
			count = timelimitedInfoDAO
					.queryPromotionInfoListCount(searchConditionDTO);
			if (count > 0) {
				// 遍历promotion 查询 timelimited
				List<TimelimitedInfoDTO> timelimitedInfoDTOList = timelimitedInfoDAO
						.queryPromotionInfoListByCondition(searchConditionDTO,
								page);
				for (TimelimitedInfoDTO promotionlist : timelimitedInfoDTOList) {
					String promotionId = promotionlist.getPromotionId();
					List<TimelimitedInfoDTO> timelimitedInfoList = timelimitedInfoDAO
							.queryTimelimitedInfoByPromotionId(promotionId);
					if (timelimitedInfoList.size() > 0) {
						PromotionListDTO promotionInfo = new PromotionListDTO();
						promotionInfo.setPromotionId(promotionlist
								.getPromotionId());
						String status = promotionlist.getStatus();
						if ((new Date())
								.after(promotionlist.getEffectiveTime())
								&& (new Date()).before(promotionlist
										.getInvalidTime())) {
							status = "2";// 正在进行
						} else if ((new Date()).after(promotionlist
								.getInvalidTime())) {
							status = "3";// 已结束
						}
						promotionInfo.setStatus(status);
						promotionInfo.setShowStatus(promotionlist
								.getShowStatus());
						// promotion中加入限时购
						promotionInfo
								.setTimelimitedInfoDTO(timelimitedInfoList);
						TimelimitedInfoDTO timelimited = timelimitedInfoList
								.get(0);
						promotionInfo.setItemName(timelimited.getSkuName());
						promotionInfo.setItemCode(timelimited.getItemCode());
						promotionInfoList.add(promotionInfo);
					}
				}
				dataGrid.setRows(promotionInfoList);
				dataGrid.setTotal(count);
			}
			result.setResult(dataGrid);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 限时购 － 根据promotionId获取限时购结果信息
	 */
	@Override
	public ExecuteResult<List<TimelimitedInfoDTO>> queryPromotionInfoByItemCode(
			TimelimitPurchaseItemInfoDTO itemInfoDTO) {
		ExecuteResult<List<TimelimitedInfoDTO>> result = new ExecuteResult<List<TimelimitedInfoDTO>>();
		List<TimelimitedInfoDTO> timelimitedListDTO = null;
		try {
			// 获取限时购活动信息
			timelimitedListDTO = timelimitedInfoDAO
					.queryPromotionInfoByItemCode(itemInfoDTO);
			if (timelimitedListDTO == null) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PROMOTION_NOT_EXIST,
						"该商品没有正在参加限时购活动!");
			}
			result.setResult(timelimitedListDTO);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 限时购 － 根据promotionId获取限时购活动信息
	 */
	@Override
	public ExecuteResult<PromotionInfoDTO> queryPromotionInfo(String promotionId) {
		ExecuteResult<PromotionInfoDTO> result = new ExecuteResult<PromotionInfoDTO>();
		PromotionInfoDTO promotionInfoDTO = null;
		List<TimelimitedInfoDTO> timelimitedInfoDTO = null;
		TimelimitedResultDTO timelimitedResultDTO = null;
		try {
			promotionInfoDTO = promotionInfoDAO.queryById(promotionId);
			if (promotionInfoDTO == null) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该限时购活动不存在!");
			}
			// 获取限时购活动信息
			timelimitedInfoDTO = timelimitedInfoDAO
					.queryTimelimitedInfoByPromotionId(promotionId);
			if (timelimitedInfoDTO == null) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该限时购活动不存在!");
			}
			for (TimelimitedInfoDTO timelimitedInfo : timelimitedInfoDTO) {
				timelimitedResultDTO = timelimitedRedisHandle
						.getRedisTimelimitedPurchaseResult(promotionId,
								timelimitedInfo.getSkuCode());
				timelimitedInfo.setTimelimitedResult(timelimitedResultDTO);
			}
			promotionInfoDTO.setPromotionAccumulatyList(timelimitedInfoDTO);
			result.setResult(promotionInfoDTO);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 限时购 － 获取对应的限时购信息(根据sku查询)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExecuteResult<TimelimitedInfoDTO> getTimelimitedInfo(String skuCode) {
		ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
		List<String> promotionIdList = new ArrayList<String>();
		Date nowDt = new Date();
		TimelimitedInfoDTO timelimitedInfoDTO = null;
		TimelimitedInfoDTO timelimite = null;
		String timelimitedJSONStr = "";
		try {
			if (StringUtils.isNotEmpty(skuCode)) {
				Map<String, String> resultMap = timelimitedRedisHandle
						.getPromotionlistRedis(skuCode);
				if (resultMap.isEmpty()) {
					throw new MarketCenterBusinessException(
							MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NULL,
							"该商品限时活动不存在");
				}
				if (resultMap.get("SUCCESS") != null) {
					String promotionIdStr = resultMap.get("SUCCESS");
					promotionIdList = JSON.parseObject(promotionIdStr,
							List.class);
				} else if (resultMap.get("ERROR1").equals("ERROR1")) {
					throw new MarketCenterBusinessException(
							MarketCenterCodeConst.LIMITED_TIME_PURCHASE_DOWN_SHELF,
							"该商品限时活动已下架");
				} else if (resultMap.get("ERROR2") != null) {
					result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
					result.addErrorMessage(resultMap.get("ERROR2"));
					return result;
				}
				for (String promotionId : promotionIdList) {
					timelimitedJSONStr = marketRedisDB.getHash(
							RedisConst.REDIS_TIMELIMITED, promotionId);
					timelimitedInfoDTO = JSON.parseObject(timelimitedJSONStr,
							TimelimitedInfoDTO.class);
					List list = timelimitedInfoDTO.getPromotionAccumulatyList();
					if (null != list && !list.isEmpty()) {
						for (int i = 0; i < list.size(); i++) {
							timelimite = JSONObject.toJavaObject(
									(JSONObject) list.get(i),
									TimelimitedInfoDTO.class);
							if (timelimite.getSkuCode().equals(skuCode)) {
								int skuTotal = timelimitedRedisHandle
										.getShowRemainCount(promotionId,
												skuCode);
								timelimite.setTimelimitedSkuCount(skuTotal);
								timelimite.setItemCode(timelimitedInfoDTO
										.getItemCode());
								if (!nowDt.before(timelimite.getStartTime())
										&& !nowDt
												.after(timelimite.getEndTime())) {
									result.setCode("00000");
									result.setResult(timelimite);
									return result;
								} else if (nowDt.before(timelimite
										.getStartTime())) {
									result.setCode(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NOT_BEGIN);
									result.setResult(timelimite);
									continue;
								} else {
									result.setCode(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_IS_OVER);
									result.setResult(timelimite);
									continue;
								}
							}
						}
						if (null == timelimite) {
							throw new MarketCenterBusinessException(
									MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NULL,
									"该商品限时活动不存在");
						}
					} else {
						throw new MarketCenterBusinessException(
								MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NULL,
								"该商品限时活动不存在");
					}
				}
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
	public ExecuteResult<List<TimelimitPurchaseMallInfoDTO>> getTimelimitedInfo(
			TimelimitedInfoDTO dto) {
		ExecuteResult<List<TimelimitPurchaseMallInfoDTO>> result = new ExecuteResult<List<TimelimitPurchaseMallInfoDTO>>();
		List<TimelimitPurchaseMallInfoDTO> resultList = new ArrayList<TimelimitPurchaseMallInfoDTO>();
		List<String> promotionIdList = new ArrayList<String>();
		Date nowDt = new Date();
		TimelimitedInfoDTO timelimitedInfoDTO = null;
		String timelimitedJSONStr = "";
		try {
			Map<String, String> resultMap = timelimitedRedisHandle.getPromotionlistRedis(null);
			if (resultMap.isEmpty()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NULL, "该商品限时活动不存在");
			}
			if (resultMap.get("SUCCESS") != null) {
				String promotionIdStr = resultMap.get("SUCCESS");
				promotionIdList = JSON.parseObject(promotionIdStr, List.class);
			} else if (resultMap.get("ERROR2") != null) {
				result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
				result.addErrorMessage(resultMap.get("ERROR2"));
				return result;
			}
			for (String promotionId : promotionIdList) {
				timelimitedJSONStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
				timelimitedInfoDTO = JSON.parseObject(timelimitedJSONStr, TimelimitedInfoDTO.class);
				List list = timelimitedInfoDTO.getPromotionAccumulatyList();
				if (null != list && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
			            TimelimitedInfoDTO timelimite = JSONObject.toJavaObject((JSONObject) list.get(i), TimelimitedInfoDTO.class);
			            timelimite.setPurchaseSort(dto.getPurchaseSort());
			            timelimite.setItemCode(timelimitedInfoDTO.getItemCode());
			            int skuTotal = timelimitedRedisHandle.getShowRemainCount(promotionId, timelimite.getSkuCode());
			            timelimite.setTimelimitedSkuCount(skuTotal);
						if (dto.getPurchaseFlag() == 1 && !nowDt.before(timelimite.getStartTime())
								&& !nowDt.after(timelimite.getEndTime())) {
							/**
							 * 今日特惠
							 */
							TimelimitPurchaseMallInfoDTO timelimitPurchaseMallInfoDTO = new TimelimitPurchaseMallInfoDTO();
							timelimitPurchaseMallInfoDTO.setTimelimitedInfo(timelimite);
							timelimitPurchaseMallInfoDTO.setStartTimeSort(timelimite.getStartTime());
							timelimitPurchaseMallInfoDTO.setSalesVolumeSort(timelimite.getSalesVolume());
							timelimitPurchaseMallInfoDTO.setSalesVolumePriceSort(timelimite.getSalesVolumePrice());
							timelimitPurchaseMallInfoDTO.setSkuTimelimitedPriceSort(timelimite.getSkuTimelimitedPrice());
							timelimitPurchaseMallInfoDTO.setPreferentialStrengthSort(timelimite.getPreferentialStrength());
							resultList.add(timelimitPurchaseMallInfoDTO);
						} else if (dto.getPurchaseFlag() == 2 && nowDt.before(timelimite.getStartTime())) {
							/**
							 * 开售预告
							 */
							TimelimitPurchaseMallInfoDTO timelimitPurchaseMallInfoDTO = new TimelimitPurchaseMallInfoDTO();
							timelimitPurchaseMallInfoDTO.setTimelimitedInfo(timelimite);
							timelimitPurchaseMallInfoDTO.setStartTimeSort(timelimite.getStartTime());
							timelimitPurchaseMallInfoDTO.setSalesVolumeSort(timelimite.getSalesVolume());
							timelimitPurchaseMallInfoDTO.setSalesVolumePriceSort(timelimite.getSalesVolumePrice());
							timelimitPurchaseMallInfoDTO.setSkuTimelimitedPriceSort(timelimite.getSkuTimelimitedPrice());
							timelimitPurchaseMallInfoDTO.setPreferentialStrengthSort(timelimite.getPreferentialStrength());
							resultList.add(timelimitPurchaseMallInfoDTO);
						}
					}
				}
			}
			if(!resultList.isEmpty()){
				if(dto.getPurchaseFlag() == 1){
					Collections.sort(resultList);
				}else{
					System.out.println(222);
					Collections.sort(resultList, new PriceComparator());
				}
			}
			result.setCode("00000");
			result.setResult(resultList);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static class PriceComparator implements Comparator { 
	    public int compare(Object object1, Object object2) {
	    	TimelimitPurchaseMallInfoDTO t1 = (TimelimitPurchaseMallInfoDTO) object1; 
	    	TimelimitPurchaseMallInfoDTO t2 = (TimelimitPurchaseMallInfoDTO) object2; 
		    int priceSort = Double.compare(t2.getPreferentialStrength(),t1.getPreferentialStrength());
		    if(priceSort != 0){
		    	return priceSort;
		    }
		    return Long.compare(t1.getStartTime().getTime(), t2.getStartTime().getTime());
		} 
	} 

	@Override
	public ExecuteResult<String> updateTimitedInfoSalesVolumeRedis(
			TimelimitedInfoDTO timelimitedInfoDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		List<TimelimitedInfoDTO> timelimitedInfoList = new ArrayList<TimelimitedInfoDTO>();
		TimelimitedInfoDTO timelimitedInfo = null;
		String timelimitedJsonStr = "";
		String promotionId = timelimitedInfoDTO.getPromotionId();
		int salesVolume = timelimitedInfoDTO.getSalesVolume();
		String skuCode = timelimitedInfoDTO.getSkuCode();
		try {
			if (StringUtils.isEmpty(promotionId)) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PARAMETER_ERROR, "限时购活动id不能为空");
			} else if (salesVolume == 0) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PARAMETER_ERROR, "限时购活动销量不能为0");
			} else if (StringUtils.isEmpty(skuCode)) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PARAMETER_ERROR, "限时购sku编码不能为空");
			}
			timelimitedJsonStr = marketRedisDB.getHash(
					RedisConst.REDIS_TIMELIMITED, promotionId);
			timelimitedInfo = JSON.parseObject(timelimitedJsonStr,
					TimelimitedInfoDTO.class);
			if (timelimitedInfo == null) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PROMOTION_NOT_EXIST, "限时购活动ID:"
								+ promotionId + " 该限时购活动不存在!");
			}
			List list = timelimitedInfo.getPromotionAccumulatyList();
			for (int i = 0; i < list.size(); i++) {
				TimelimitedInfoDTO timelimite = JSONObject.toJavaObject(
						(JSONObject) list.get(i), TimelimitedInfoDTO.class);
				if (timelimite.getSkuCode().equals(skuCode)) {
					int salesVolumeResult = timelimite.getSalesVolume()
							+ salesVolume;
					BigDecimal salesVolumePriceResult = timelimite
							.getSkuTimelimitedPrice().multiply(
									new BigDecimal(salesVolumeResult));
					timelimite.setSalesVolume(salesVolumeResult);
					timelimite.setSalesVolumePrice(salesVolumePriceResult
							.doubleValue());
				}
				timelimitedInfoList.add(timelimite);
			}
			timelimitedInfo.setPromotionAccumulatyList(timelimitedInfoList);
			marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED, promotionId,
					JSON.toJSONString(timelimitedInfo));
			result.setCode("00000");
			result.setResult("SUCCESS");
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<TimelimitedInfoDTO> updateTimelimitedInfo(
			TimelimitedInfoDTO timelimitedInfo) {
		ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
		PromotionInfoDTO promotionInfoDTO = null;
		PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
		List<PromotionStatusHistoryDTO> historyList = null;
		String promotionId = timelimitedInfo.getPromotionId();
		String modifyTimeStr = "";
		String paramModifyTimeStr = "";
		String status = dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
				DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID);
		try {
			// checkTimelimitedDuringRepeat(timelimitedInfo);
			if (StringUtils.isEmpty(promotionId)) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PARAMETER_ERROR, "修改限时购活动ID不能为空");
			}
			paramModifyTimeStr = DateUtils.format(
					timelimitedInfo.getModifyTime(), DateUtils.YMDHMS);
			// 根据活动ID获取活动信息
			promotionInfoDTO = promotionInfoDAO.queryById(promotionId);
			if (promotionInfoDTO == null) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PROMOTION_NOT_EXIST, "限时购活动不存在");
			}
			if (!status.equals(promotionInfoDTO.getShowStatus())) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PROMOTION_STATUS_NOT_CORRECT,
						"限时购活动:" + promotionId + " 只有在下架状态时才能修改");
			}
			if (!(new Date()).before(promotionInfoDTO.getEffectiveTime())) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PROMOTION_STATUS_NOT_CORRECT,
						"限时购活动:" + promotionId + " 只有未开始的活动才能修改");
			}
			modifyTimeStr = DateUtils.format(promotionInfoDTO.getModifyTime(),
					DateUtils.YMDHMS);
			if (!modifyTimeStr.equals(paramModifyTimeStr)) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PROMOTION_HAS_MODIFIED, "限时购活动:"
								+ promotionId + " 已被修改请重新确认");
			}
			timelimitedInfo.setShowStatus(status);
			promotionInfoDTO = baseService.updatePromotionInfo(timelimitedInfo);
			timelimitedInfo.setPromoionInfo(promotionInfoDTO);
			List<? extends PromotionAccumulatyDTO> accumulatyList = timelimitedInfo
					.getPromotionAccumulatyList();
			if (accumulatyList.size() > 0) {
				for (PromotionAccumulatyDTO accumulaty : accumulatyList) {
					timelimitedInfoDAO.add((TimelimitedInfoDTO) accumulaty);
				}
			}
			historyDTO.setPromotionId(timelimitedInfo.getPromotionId());
			historyDTO.setPromotionStatus(timelimitedInfo.getShowStatus());
			historyDTO.setPromotionStatusText("修改限时购活动信息");
			historyDTO.setCreateId(timelimitedInfo.getCreateId());
			historyDTO.setCreateName(timelimitedInfo.getCreateName());
			promotionStatusHistoryDAO.add(historyDTO);
			historyList = promotionStatusHistoryDAO
					.queryByPromotionId(promotionId);
			timelimitedInfo.setPromotionStatusHistoryList(historyList);
			timelimitedRedisHandle.deleteRedisTimelimitedInfo(promotionId);
			timelimitedRedisHandle.addTimelimitedInfo2Redis(timelimitedInfo);
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

}
