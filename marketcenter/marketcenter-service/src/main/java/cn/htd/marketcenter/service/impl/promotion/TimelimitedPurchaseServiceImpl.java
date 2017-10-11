package cn.htd.marketcenter.service.impl.promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionStatusHistoryDTO;
import cn.htd.marketcenter.dto.TimelimitPurchaseMallInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedConditionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedListDTO;
import cn.htd.marketcenter.service.PromotionBaseService;
import cn.htd.marketcenter.service.TimelimitedPurchaseService;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("timelimitedPurchaseService")
public class TimelimitedPurchaseServiceImpl implements TimelimitedPurchaseService {

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
	public ExecuteResult<TimelimitedInfoDTO> addTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo) {
		ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
		PromotionInfoDTO promotionInfo = null;
		PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
		List<PromotionStatusHistoryDTO> historyList = new ArrayList<PromotionStatusHistoryDTO>();
		try {
			timelimitedInfo.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
					DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT));
			timelimitedInfo.setSkuTimelimitedPrice(CalculateUtils.setScale(timelimitedInfo.getSkuTimelimitedPrice()));
			promotionInfo = baseService.insertPromotionInfo(timelimitedInfo);
			timelimitedInfo.setPromoionInfo(promotionInfo);
			List<? extends PromotionAccumulatyDTO> accumulatyList = timelimitedInfo.getPromotionAccumulatyList();
			if (accumulatyList.size() > 0) {
				for (PromotionAccumulatyDTO accumulaty : accumulatyList) {
					timelimitedInfoDAO.add((TimelimitedInfoDTO) accumulaty);
				}
			}
			historyDTO.setPromotionId(timelimitedInfo.getPromotionId());
			historyDTO.setPromotionStatus(timelimitedInfo.getShowStatus());
			historyDTO.setPromotionStatusText(dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
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
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<TimelimitedListDTO>> queryTimelimitedListByCondition(
			TimelimitedConditionDTO conditionDTO, Pager<TimelimitedListDTO> page) {
		ExecuteResult<DataGrid<TimelimitedListDTO>> result = new ExecuteResult<DataGrid<TimelimitedListDTO>>();
		DataGrid<TimelimitedListDTO> dataGrid = new DataGrid<TimelimitedListDTO>();
		List<TimelimitedListDTO> timelimitedInfoList = new ArrayList<TimelimitedListDTO>();
		TimelimitedInfoDTO searchConditionDTO = new TimelimitedInfoDTO();
		long count = 0;
		try {
			searchConditionDTO.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
					DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED));
			searchConditionDTO.setSkuCode(conditionDTO.getSkuCode());
			searchConditionDTO.setSkuName(conditionDTO.getSkuName());
			searchConditionDTO.setStatus(conditionDTO.getStatus());
			searchConditionDTO.setPromotionProviderSellerCode(conditionDTO.getSelleCode());
			searchConditionDTO.setDeleteStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
					DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
			searchConditionDTO.setStartTime(conditionDTO.getStartTime());
			searchConditionDTO.setEndTime(conditionDTO.getEndTime());
			count = timelimitedInfoDAO.queryTimelimitedInfoListCount(searchConditionDTO);
			if (count > 0) {
				// 。queryTimelimitedInfoList(conditionDTO,page);
				timelimitedInfoList = timelimitedInfoDAO.queryTimelimitedInfoList(searchConditionDTO, page);
				dataGrid.setRows(timelimitedInfoList);
			}
			dataGrid.setTotal(count);
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
	public ExecuteResult<List<TimelimitedInfoDTO>> queryTimelimitedInfo(String promotionId) {
		ExecuteResult<List<TimelimitedInfoDTO>> result = new ExecuteResult<List<TimelimitedInfoDTO>>();
		List<TimelimitedInfoDTO> timelimitedInfoDTO = null;
		try {
			// 获取限时购活动信息
			timelimitedInfoDTO = timelimitedInfoDAO.queryTimelimitedInfoByPromotionId(promotionId);
			if (timelimitedInfoDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该限时购活动不存在!");
			}
			result.setResult(timelimitedInfoDTO);
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
	public ExecuteResult<List<TimelimitedInfoDTO>> getTimelimitedInfo(String skuCode) {
		ExecuteResult<List<TimelimitedInfoDTO>> result = new ExecuteResult<List<TimelimitedInfoDTO>>();
		List<TimelimitedInfoDTO> resultList = new ArrayList<TimelimitedInfoDTO>();
		List<String> promotionIdList = new ArrayList<String>();
		Date nowDt = new Date();
		TimelimitedInfoDTO timelimitedInfoDTO = null;
		String timelimitedJSONStr = "";
		try {
			if (StringUtils.isNotEmpty(skuCode)) {
				Map<String, String> resultMap = getPromotionlistRedis(skuCode);
				if (resultMap.isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NULL,
							"该商品限时活动不存在");
				}
				if (resultMap.get("SUCCESS") != null) {
					String promotionIdStr = resultMap.get("SUCCESS");
					promotionIdList = JSON.parseObject(promotionIdStr, List.class);
				} else if (resultMap.get("ERROR1").equals("ERROR1")) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_DOWN_SHELF,
							"该商品限时活动已下架");
				} else if (resultMap.get("ERROR2") != null) {
					result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
					result.addErrorMessage(resultMap.get("ERROR2"));
					return result;
				}
				for (String promotionId : promotionIdList) {
					timelimitedJSONStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
					timelimitedInfoDTO = JSON.parseObject(timelimitedJSONStr, TimelimitedInfoDTO.class);
					String status = timelimitedInfoDTO.getStatus();
					List list = timelimitedInfoDTO.getPromotionAccumulatyList();
					if (null != list && !list.isEmpty()) {
						for (int i = 0; i < list.size(); i++) {
							TimelimitedInfoDTO timelimite = JSONObject.toJavaObject((JSONObject) list.get(i),
									TimelimitedInfoDTO.class);
							if (timelimite.getSkuCode().equals(skuCode)) {
								if (status.equals(dictionary.getValueByCode(
										DictionaryConst.TYPE_PROMOTION_STATUS,
										DictionaryConst.OPT_PROMOTION_STATUS_START))) {
									resultList.add(timelimite);
									result.setCode("00000");
									result.setResult(resultList);
									return result;
								} else if (status.equals(dictionary.getValueByCode(
										DictionaryConst.TYPE_PROMOTION_STATUS,
										DictionaryConst.OPT_PROMOTION_STATUS_NO_START))) {
									resultList.add(timelimite);
									result.setCode(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NOT_BEGIN);
									result.setResult(resultList);
									return result;
								}
							}
						}
						if (resultList.size() == 0) {
							throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_IS_OVER,
									"该商品限时活动已结束");
						}
					}
				}
				result.setCode("00000");
				result.setResult(resultList);
			}
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
		TimelimitedInfoDTO timelimitedInfoDTO = null;
		String timelimitedJSONStr = "";
		try {
			Map<String, String> resultMap = getPromotionlistRedis(null);
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
				String status = timelimitedInfoDTO.getStatus();
				List list = timelimitedInfoDTO.getPromotionAccumulatyList();
				if (null != list && !list.isEmpty()) {
					TimelimitedInfoDTO timelimitedConvert = timilimitedConvert(list);
					if (dto.getPurchaseFlag() == 1 && status.equals(
							dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
							DictionaryConst.OPT_PROMOTION_STATUS_START))) {
						/**
						 * 今日特惠
						 */
						TimelimitPurchaseMallInfoDTO timelimitPurchaseMallInfoDTO = new TimelimitPurchaseMallInfoDTO();
						timelimitPurchaseMallInfoDTO.setTimelimitedInfo(timelimitedConvert);
						timelimitPurchaseMallInfoDTO.setStartTimeSort(timelimitedConvert.getStartTime());
						timelimitPurchaseMallInfoDTO.setSalesVolume(timelimitedConvert.getSalesVolume());
						timelimitPurchaseMallInfoDTO.setSalesVolumePriceSort(timelimitedConvert.getSalesVolumePrice());
						timelimitPurchaseMallInfoDTO.setPreferentialStrengthSort(timelimitedConvert.getPreferentialStrength());
						resultList.add(timelimitPurchaseMallInfoDTO);
					} else if (dto.getPurchaseFlag() == 2 && status.equals(
							dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
							DictionaryConst.OPT_PROMOTION_STATUS_NO_START))) {
						/**
						 * 开售预告
						 */
						TimelimitPurchaseMallInfoDTO timelimitPurchaseMallInfoDTO = new TimelimitPurchaseMallInfoDTO();
						timelimitPurchaseMallInfoDTO.setTimelimitedInfo(timelimitedConvert);
						timelimitPurchaseMallInfoDTO.setTimelimitedInfo(timelimitedConvert);
						timelimitPurchaseMallInfoDTO.setStartTimeSort(timelimitedConvert.getStartTime());
						timelimitPurchaseMallInfoDTO.setSalesVolume(timelimitedConvert.getSalesVolume());
						timelimitPurchaseMallInfoDTO.setSalesVolumePriceSort(timelimitedConvert.getSalesVolumePrice());
						timelimitPurchaseMallInfoDTO.setPreferentialStrengthSort(timelimitedConvert.getPreferentialStrength());
						resultList.add(timelimitPurchaseMallInfoDTO);
					}
				}
			}
			if(!resultList.isEmpty()){
//				System.out.println(JSONObject.toJSONString(resultList));
				Collections.sort(resultList);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private TimelimitedInfoDTO timilimitedConvert(List list) {
		List<TimelimitedInfoDTO> timelimitedInfoList = new ArrayList<TimelimitedInfoDTO>();
		for (int i = 0; i < list.size(); i++) {
            TimelimitedInfoDTO timelimite = JSONObject.toJavaObject((JSONObject) list.get(i), TimelimitedInfoDTO.class);
            timelimitedInfoList.add(timelimite);
		}
		Collections.sort(timelimitedInfoList, new PriceComparator());
		return timelimitedInfoList.get(0);
	}
	
	@SuppressWarnings("rawtypes")
	public static class PriceComparator implements Comparator { 
	    public int compare(Object object1, Object object2) {
		    TimelimitedInfoDTO t1 = (TimelimitedInfoDTO) object1; 
		    TimelimitedInfoDTO t2 = (TimelimitedInfoDTO) object2; 
		    	return t1.getSkuTimelimitedPrice().compareTo(t2.getSkuTimelimitedPrice()); 
		    } 
	} 

	private Map<String, String> getPromotionlistRedis(String skuCode) {
		Map<String, String> resultMap = new HashMap<String, String>();
		List<String> promotionIdList = new ArrayList<String>();
		List<String> purchaseIndexList = new ArrayList<String>();
		Set<String> purChaseSet = null;
		String promotionIdStr = "";
		String validStatus = "";
		try {
			purChaseSet = marketRedisDB.getHashFields(RedisConst.REDIS_TIMELIMITED_INDEX);
			if (null != purChaseSet && !purChaseSet.isEmpty()) {
				for (String pur : purChaseSet) {
					String purchaseFirst = pur.substring(0, 1);
					String purchaseSecond = pur.substring(1, 2);
					if ("&".equals(purchaseSecond) 
							&& dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
											DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT).equals(purchaseFirst)) {
						if (StringUtils.isNotEmpty(skuCode)) {
							if (pur.contains(skuCode)) {
								purchaseIndexList.add(pur);
							}
						} else {
							purchaseIndexList.add(pur);
						}
					}
				}
				if (!purchaseIndexList.isEmpty()) {
					for (String purchaseIndex : purchaseIndexList) {
						promotionIdStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_INDEX, purchaseIndex);
						String[] promotionIdSplit = promotionIdStr.split(",");
						for (int i = 0; i < promotionIdSplit.length; i++) {
							validStatus = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_VALID, promotionIdSplit[i]);
							if (!StringUtils.isEmpty(validStatus)
									&& dictionary
											.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
													DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID)
											.equals(validStatus)) {
								promotionIdList.add(promotionIdSplit[i]);
							} else {
								resultMap.put("ERROR1", "ERROR1");
							}
						}
					}
				}
				if (promotionIdList.size() > 0) {
					resultMap.put("SUCCESS", JSON.toJSONString(promotionIdList));
				}
			}
		} catch (Exception e) {
			resultMap.put("ERROR2", ExceptionUtils.getStackTraceAsString(e));
		}
		return resultMap;
	}

	@Override
	public ExecuteResult<String> updateTimitedInfoSalesVolumeRedis(TimelimitedInfoDTO timelimitedInfoDTO) {
		 ExecuteResult<String> result = new ExecuteResult<String>();
		 List<TimelimitedInfoDTO> TimelimitedInfoList = new ArrayList<TimelimitedInfoDTO>();
		 TimelimitedInfoDTO timelimitedInfo = null;
		 String timelimitedJsonStr = "";
		 String promotionId = timelimitedInfoDTO.getPromotionId();
		 int salesVolume = timelimitedInfoDTO.getSalesVolume();
		 String skuCode = timelimitedInfoDTO.getSkuCode();
		 try {
			 if(StringUtils.isEmpty(promotionId)){
				 throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "限时购活动id不能为空");
			 }else if(salesVolume == 0){
				 throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "限时购活动销量不能为0");
			 }else if(StringUtils.isEmpty(skuCode)){
				 throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "限时购sku编码不能为空");
			 }
			 timelimitedJsonStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
		     timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoDTO.class);
		     if (timelimitedInfo == null) {
		    	 throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST,
		    			 "限时购活动ID:" + promotionId + " 该限时购活动不存在!");
		     }
		     List list = timelimitedInfo.getPromotionAccumulatyList();
		     for (int i = 0; i < list.size(); i++) {
		    	 TimelimitedInfoDTO timelimite = JSONObject.toJavaObject((JSONObject) list.get(i), TimelimitedInfoDTO.class);
		    	 if(timelimite.getSkuCode().equals(skuCode)){
		    		 int salesVolumeResult = timelimite.getSalesVolume() + salesVolume;
		    		 BigDecimal salesVolumePriceResult = timelimite.getSkuTimelimitedPrice().multiply(new BigDecimal(salesVolumeResult));
		    		 timelimite.setSalesVolume(salesVolumeResult);
		    		 timelimite.setSalesVolumePrice(salesVolumePriceResult.doubleValue());
		    	 }
		    	 TimelimitedInfoList.add(timelimite);
			 }
		     timelimitedInfo.setPromotionAccumulatyList(TimelimitedInfoList);
		     timelimitedRedisHandle.addTimelimitedInfo2Redis(timelimitedInfo);
		     result.setCode("00000");
		     result.setResult("SUCCESS");
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}	
	
	@Override
	public ExecuteResult<TimelimitedInfoDTO> updateTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo) {
        ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
        PromotionInfoDTO promotionInfoDTO = null;
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        List<PromotionStatusHistoryDTO> historyList = null;
        String promotionId = timelimitedInfo.getPromotionId();
        String modifyTimeStr = "";
        String paramModifyTimeStr = "";
        String status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID);
        try {
            if (StringUtils.isEmpty(promotionId)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "修改限时购活动ID不能为空");
            }
            paramModifyTimeStr = DateUtils.format(timelimitedInfo.getModifyTime(), DateUtils.YMDHMS);
            // 根据活动ID获取活动信息
            promotionInfoDTO = promotionInfoDAO.queryById(promotionId);
            if (promotionInfoDTO == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "限时购活动不存在");
            }
            if (!(new Date()).before(promotionInfoDTO.getEffectiveTime()) || !status
                    .equals(promotionInfoDTO.getShowStatus())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_STATUS_NOT_CORRECT,
                        "限时购活动:" + promotionId + " 只有在未启用状态时才能修改");
            }
            modifyTimeStr = DateUtils.format(promotionInfoDTO.getModifyTime(), DateUtils.YMDHMS);
            if (!modifyTimeStr.equals(paramModifyTimeStr)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_MODIFIED,
                        "限时购活动:" + promotionId + " 已被修改请重新确认");
            }
            timelimitedInfo.setShowStatus(status);
            promotionInfoDTO = baseService.updatePromotionInfo(timelimitedInfo);
            timelimitedInfo.setPromoionInfo(promotionInfoDTO);
            timelimitedInfoDAO.update(timelimitedInfo);
            historyDTO.setPromotionId(timelimitedInfo.getPromotionId());
            historyDTO.setPromotionStatus(timelimitedInfo.getShowStatus());
            historyDTO.setPromotionStatusText("修改限时购活动信息");
            historyDTO.setCreateId(timelimitedInfo.getCreateId());
            historyDTO.setCreateName(timelimitedInfo.getCreateName());
            promotionStatusHistoryDAO.add(historyDTO);
            historyList = promotionStatusHistoryDAO.queryByPromotionId(promotionId);
            int a = 1/0;
            timelimitedInfo.setPromotionStatusHistoryList(historyList);
            timelimitedRedisHandle.deleteRedisTimelimitedInfo(promotionId);
            timelimitedRedisHandle.addTimelimitedInfo2Redis(timelimitedInfo);
            result.setResult(timelimitedInfo);
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
}
