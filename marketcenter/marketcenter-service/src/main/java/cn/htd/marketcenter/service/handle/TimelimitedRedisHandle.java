package cn.htd.marketcenter.service.handle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.dto.stock.PromotionStockChangeDTO;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.enums.TimelimitedStatusEnum;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.domain.BuyerUseTimelimitedLog;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedMallInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedResultDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Service("timelimitedRedisHandle")
public class TimelimitedRedisHandle {

    protected static transient Logger logger = LoggerFactory.getLogger(TimelimitedRedisHandle.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    /**
     * 保存秒杀活动的启用状态
     *
     * @param promotionInfo
     */
    public void saveTimelimitedValidStatus2Redis(PromotionInfoDTO promotionInfo) {
        marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED_VALID, promotionInfo.getPromotionId(),
                promotionInfo.getShowStatus());
    }

    /**
     * 取得Redis秒杀活动信息
     *
     * @param promotionId
     */
    public TimelimitedInfoDTO getRedisTimelimitedInfo(String promotionId) throws MarketCenterBusinessException {
        TimelimitedInfoDTO timelimitedInfo = null;
        TimelimitedResultDTO timelimitedResult = null;
        String timelimitedJsonStr = "";
        String validStatus = "";

        validStatus = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_VALID, promotionId);
        if (!StringUtils.isEmpty(validStatus)
                && !dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(validStatus)) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_VALID,
                    "秒杀活动ID:" + promotionId + " 该秒杀活动未启用");
        }
        timelimitedJsonStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
        timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoDTO.class);
        if (timelimitedInfo == null) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST,
                    "秒杀活动ID:" + promotionId + " 该秒杀活动不存在!");
        }
        timelimitedResult = getRedisTimelimitedResult(promotionId);
        timelimitedInfo.setTimelimitedResult(timelimitedResult);
        return timelimitedInfo;
    }

    /**
     * 取得Redis秒杀活动信息
     *
     * @param promotionId
     */
    public void deleteRedisTimelimitedInfo(String promotionId) {
        TimelimitedInfoDTO timelimitedInfo = null;
        String timelimitedJsonStr = "";
        String promotionAllIdStr = "";
        String indexKey = "";
        List<String> keyList = new ArrayList<String>();
        timelimitedJsonStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
        timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoDTO.class);
        if (timelimitedInfo != null) {
            //add by lijun for 限时购 start
            String promotionType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT);
            if(promotionType.equals(timelimitedInfo.getPromotionType())){
            	List accumulatyList = timelimitedInfo.getPromotionAccumulatyList();
    			if (accumulatyList.size() > 0) {
    				for(int i=0;i<accumulatyList.size();i++){
                		TimelimitedInfoDTO timelimite = JSONObject.toJavaObject((JSONObject) accumulatyList.get(i),TimelimitedInfoDTO.class);
                		String sku_code = timelimite.getSkuCode();
                		indexKey = timelimitedInfo.getPromotionType() + "&" + sku_code + "&" + timelimitedInfo.getIsVip();
    					keyList.add(indexKey);
                	}
    			}
            }else {
	            indexKey = timelimitedInfo.getPromotionType() + "&" + timelimitedInfo.getSkuCode() + "&" + timelimitedInfo.getIsVip();
            }
           //add by lijun for 限时购 end

            if (!StringUtils.isEmpty(timelimitedInfo.getPromotionProviderSellerCode())) {
                indexKey = indexKey + "&" + timelimitedInfo.getPromotionProviderSellerCode();
            }
            
          //add by lijun for 限时购 start
            if(promotionType.equals(timelimitedInfo.getPromotionType())){
            	for(String promotionkey :keyList){
                     promotionAllIdStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_INDEX, promotionkey);
                     if (!StringUtils.isEmpty(promotionAllIdStr)) {
                         if (promotionAllIdStr.equals(promotionId)) {
                             marketRedisDB.delHash(RedisConst.REDIS_TIMELIMITED_INDEX, promotionkey);
                         } else {
                             promotionAllIdStr = "," + promotionAllIdStr + ",";
                             promotionAllIdStr = promotionAllIdStr.replace("," + promotionId + ",", ",");
                             promotionAllIdStr = promotionAllIdStr.substring(1, promotionAllIdStr.length() - 1);
                             marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED_INDEX, promotionkey, promotionAllIdStr);
                         }
                     }
                   }
            }else{
            //add by lijun for 限时购 end
                promotionAllIdStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_INDEX, indexKey);
                if (!StringUtils.isEmpty(promotionAllIdStr)) {
                    if (promotionAllIdStr.equals(promotionId)) {
                        marketRedisDB.delHash(RedisConst.REDIS_TIMELIMITED_INDEX, indexKey);
                    } else {
                        promotionAllIdStr = "," + promotionAllIdStr + ",";
                        promotionAllIdStr = promotionAllIdStr.replace("," + promotionId + ",", ",");
                        promotionAllIdStr = promotionAllIdStr.substring(1, promotionAllIdStr.length() - 1);
                        marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED_INDEX, indexKey, promotionAllIdStr);
                    }
                }
            }
        }
        marketRedisDB.delHash(RedisConst.REDIS_TIMELIMITED, promotionId);
        marketRedisDB.del(RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId);
        marketRedisDB.delHash(RedisConst.REDIS_TIMELIMITED_VALID, promotionId);
    }

    /**
     * 保存秒杀活动信息进Redis
     *
     * @param timelimitedInfo
     */
    public void addTimelimitedInfo2Redis(TimelimitedInfoDTO timelimitedInfo) {
    	 
        Map<String, String> resultMap = new HashMap<String, String>();
        String promotionId = timelimitedInfo.getPromotionId();
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        //----- add by jiangkun for 2017活动需求商城无敌券 on 20171009 start -----
        PromotionBuyerRuleDTO buyerRuleDTO = timelimitedInfo.getBuyerRuleDTO();
        List<PromotionBuyerDetailDTO> buyerDetailDTOList = null;
        List<String> buyerGroupList = null;
        long diffTime = 0L;
        int seconds = 0;
        //----- add by jiangkun for 2017活动需求商城无敌券 on 20171009 end -----
        timelimitedInfo.setCreateTime(new Date());
        timelimitedInfo.setModifyTime(new Date());
        timelimitedInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
        //edit by lijun 限时购
    	    List<? extends PromotionAccumulatyDTO> accumulatyList = timelimitedInfo.getPromotionAccumulatyList();
			if (null != accumulatyList && accumulatyList.size() > 0) {
			    for(PromotionAccumulatyDTO accumulaty : accumulatyList) {
			    	 TimelimitedInfoDTO timelimited = (TimelimitedInfoDTO) accumulaty;
				     String  sku_code = timelimited.getSkuCode();
			         resultMap.put(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT  +"_"+ sku_code, String.valueOf(timelimited.getTimelimitedSkuCount()));
			         resultMap.put(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT  +"_"+ sku_code,String.valueOf(timelimited.getTimelimitedSkuCount()));
			         resultMap.put(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT +"_"+ sku_code, String.valueOf(timelimited.getTimelimitedSkuCount()));
				}
	      }else{
	         resultMap.put(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT,String.valueOf(timelimitedInfo.getTimelimitedSkuCount()));
	         resultMap.put(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT , String.valueOf(timelimitedInfo.getTimelimitedSkuCount()));
	         resultMap.put(RedisConst.REDIS_TIMELIMITED_SHOW_ACTOR_COUNT, "0");
	         resultMap.put(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT, String.valueOf(timelimitedInfo.getTimelimitedSkuCount()));
	         resultMap.put(RedisConst.REDIS_TIMELIMITED_REAL_ACTOR_COUNT, "0");
	     }
    //edit by lijun 限时购
        //----- add by jiangkun for 2017活动需求商城无敌券 on 20171009 start -----
        diffTime = timelimitedInfo.getInvalidTime().getTime() - new Date().getTime();
        seconds = (int) (diffTime / 1000);
        if (buyerRuleDTO != null) {
            buyerDetailDTOList = buyerRuleDTO.getBuyerDetailList();
            buyerGroupList = buyerRuleDTO.getTargetBuyerGroupList();
            if (buyerGroupList != null && !buyerGroupList.isEmpty()) {
                for (String buyerGroupId : buyerGroupList) {
                    marketRedisDB.addSet(RedisConst.REDIS_PROMOTION_BUYER_RULE_GROUP_SET + "_" + promotionId, buyerGroupId);
                }
                marketRedisDB.expire(RedisConst.REDIS_PROMOTION_BUYER_RULE_GROUP_SET + "_" + promotionId, seconds);
                buyerRuleDTO.setTargetBuyerGroupList(null);
                buyerRuleDTO.setTargetBuyerGroup(null);
            }
            if (buyerDetailDTOList != null && !buyerDetailDTOList.isEmpty()) {
                for (PromotionBuyerDetailDTO buyerDetailDTO : buyerDetailDTOList) {
                    marketRedisDB.setHash(RedisConst.REDIS_PROMOTION_BUYER_RULE_DETAIL_HASH + "_" + promotionId,
                            buyerDetailDTO.getBuyerCode(), buyerDetailDTO.getBuyerName());
                }
                marketRedisDB.expire(RedisConst.REDIS_PROMOTION_BUYER_RULE_DETAIL_HASH + "_" + promotionId, seconds);
                buyerRuleDTO.setBuyerDetailList(null);
            }
        }
        //----- add by jiangkun for 2017活动需求商城无敌券 on 20171009 end -----
        marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED, promotionId, JSON.toJSONString(timelimitedInfo));
        marketRedisDB.setHash(timelimitedResultKey, resultMap);
        saveTimelimitedIndex2Redis(timelimitedInfo);
        saveTimelimitedValidStatus2Redis(timelimitedInfo);
    }
    
    /**
     * 保存秒杀活动ID索引
     *
     * @param timelimitedInfo
     */
    private void saveTimelimitedIndex2Redis(TimelimitedInfoDTO timelimitedInfo) {
        String promotionId = timelimitedInfo.getPromotionId();
        //String key = timelimitedInfo.getSkuCode() + "&" + timelimitedInfo.getIsVip();
        String promotionIdStr = "";
        String key = "";
        List<String> keyList = new ArrayList<String>();
        //add by lijun for 限时购 start
        //限时购 - 活动类型
        String promotionType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT);
        if(promotionType.equals(timelimitedInfo.getPromotionType())){
        	List<? extends PromotionAccumulatyDTO> accumulatyList = timelimitedInfo.getPromotionAccumulatyList();
			if (accumulatyList.size() > 0) {
				for (PromotionAccumulatyDTO accumulaty : accumulatyList) {
					String  sku_code = ((TimelimitedInfoDTO) accumulaty).getSkuCode();
				    key = timelimitedInfo.getPromotionType() +"&"+ sku_code + "&" + timelimitedInfo.getIsVip();
					keyList.add(key);
				}
			}
        }else {
        	 key = timelimitedInfo.getPromotionType() +"&"+ timelimitedInfo.getSkuCode() + "&" + timelimitedInfo.getIsVip();
        }
       //add by lijun for 限时购 end
        
        if (StringUtils.isNotBlank(timelimitedInfo.getPromotionProviderSellerCode())) {
            key = key + "&" + timelimitedInfo.getPromotionProviderSellerCode();
        }
       //add by lijun for 限时购 start
        if(promotionType.equals(timelimitedInfo.getPromotionType())){
        	for(String promotionkey :keyList){
                promotionIdStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_INDEX, promotionkey);
                if (StringUtils.isEmpty(promotionIdStr)) {
                    promotionIdStr = promotionId;
                } else {
                    promotionIdStr += "," + promotionId;
                }
                marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED_INDEX, promotionkey, promotionIdStr);
        	}
        }else{
        	//add by lijun for 限时购 end
            promotionIdStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_INDEX, key);
            if (StringUtils.isEmpty(promotionIdStr)) {
                promotionIdStr = promotionId;
            } else {
                promotionIdStr += "," + promotionId;
            }
            marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED_INDEX, key, promotionIdStr);
        }
        

    }

    /**
     * 根据VIP标记、商品SkuCode列表取得秒杀活动信息列表
     *
     * @param isVipFlag
     * @param skuCodeList
     * @param sellerCode
     * @param isContainSellerTimelimitedFlag
     */
    public List<String> getRedisTimelimitedIndex(String isVipFlag, List<String> skuCodeList, String sellerCode,
            boolean isContainSellerTimelimitedFlag,String promotionType) {
        List<String> promotionIdList = new ArrayList<String>();
        Map<String, String> indexMap = null;
        String key = "";
        String promotionIdStr = "";
        String promotionAllIdStr = "";
        String[] keyArr = null;
        String tmpIsVip = "";
        String tmpSkuCode = "";
        String tmpSellerCode = "";
        //限时购 - 活动类型
        String temp_promotion_type ="";
        indexMap = marketRedisDB.getHashOperations(RedisConst.REDIS_TIMELIMITED_INDEX);
        if (indexMap == null || indexMap.isEmpty()) {
            return null;
        }
        for (Entry<String, String> entry : indexMap.entrySet()) {
            key = entry.getKey();
            promotionIdStr = entry.getValue();
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(promotionIdStr) || key.indexOf("&") < 0) {
                marketRedisDB.delHash(RedisConst.REDIS_TIMELIMITED_INDEX, key);
                continue;
            }
            keyArr = key.split("&");
            if(keyArr.length <= 2){
            	continue;
            }
            //edit
            temp_promotion_type = keyArr[0];
            tmpSkuCode = keyArr[1];
            tmpIsVip = keyArr[2];	
            //edit
            tmpSellerCode = "";
            if (skuCodeList != null && !skuCodeList.isEmpty() && !skuCodeList.contains(tmpSkuCode)) {
                continue;
            }
            if (!StringUtils.isEmpty(isVipFlag) && !tmpIsVip.equals(isVipFlag)) {
                continue;
            }
            if(!StringUtils.isEmpty(promotionType) && !temp_promotion_type.equals(promotionType)){
            	 continue;
            }
            if (!isContainSellerTimelimitedFlag) {
                if (keyArr.length > 3) {
                    tmpSellerCode = keyArr[3];
                }
                if (StringUtils.isEmpty(sellerCode)) {
                    if (!StringUtils.isEmpty(tmpSellerCode)) {
                        continue;
                    }
                } else {
                    if (!sellerCode.equals(tmpSellerCode)) {
                        continue;
                    }
                }
            }
            promotionAllIdStr += "," + promotionIdStr;
        }
        if (!StringUtils.isEmpty(promotionAllIdStr)) {
            promotionAllIdStr = promotionAllIdStr.substring(1);
            promotionIdList = Arrays.asList(promotionAllIdStr.split(","));
        }
        return promotionIdList;
    }

    /**
     * 从Redis中查询秒杀活动列表
     *
     * @param isVipFlag
     * @param sellerCode
     * @param page
     * @return
     */
    public DataGrid<TimelimitedMallInfoDTO> getRedisTimelimitedInfoList(String isVipFlag, String sellerCode,
            Pager<TimelimitedInfoDTO> page,String promotionType) {
        DataGrid<TimelimitedMallInfoDTO> datagrid = new DataGrid<TimelimitedMallInfoDTO>();
        List<TimelimitedMallInfoDTO> mallDTOList = new ArrayList<TimelimitedMallInfoDTO>();
        List<TimelimitedMallInfoDTO> mallAllDTOList = new ArrayList<TimelimitedMallInfoDTO>();
        List<String> promotionIdList = null;
        TimelimitedInfoDTO timelimitedInfoDTO = null;
        TimelimitedMallInfoDTO timelimitedMallDTO = null;
        TimelimitedResultDTO timelimitedResultDTO = null;
        int count = 0;
        long total = 0;
        int offset = 0;
        int rows = Integer.MAX_VALUE;
        if (page != null) {
            offset = page.getPageOffset();
            rows = page.getRows();
        }
        promotionIdList = getRedisTimelimitedIndex(isVipFlag, null, "", false,promotionType);
        if (promotionIdList == null || promotionIdList.isEmpty()) {
            return datagrid;
        }
        for (String promotionId : promotionIdList) {
            try {
                timelimitedInfoDTO = getRedisTimelimitedInfo(promotionId);
                timelimitedResultDTO = timelimitedInfoDTO.getTimelimitedResult();
                timelimitedMallDTO = new TimelimitedMallInfoDTO();
                timelimitedMallDTO.setTimelimitedInfo(timelimitedInfoDTO);
                timelimitedMallDTO.setRemainCount(timelimitedResultDTO.getShowRemainSkuCount());
                if (timelimitedResultDTO.getShowRemainSkuCount() <= 0) {
                    timelimitedMallDTO.setRemainCount(0);
                    timelimitedMallDTO.setCompareStatus(TimelimitedStatusEnum.CLEAR.getValue());
                } else if ((new Date()).before(timelimitedInfoDTO.getEffectiveTime())) {
                    timelimitedMallDTO.setCompareStatus(TimelimitedStatusEnum.NO_START.getValue());
                } else if ((new Date()).after(timelimitedInfoDTO.getInvalidTime())) {
                    timelimitedMallDTO.setCompareStatus(TimelimitedStatusEnum.ENDED.getValue());
                } else {
                    timelimitedMallDTO.setCompareStatus(TimelimitedStatusEnum.PROCESSING.getValue());
                }
                timelimitedMallDTO
                        .setShowStatusStr(TimelimitedStatusEnum.getName(timelimitedMallDTO.getCompareStatus()));
                mallAllDTOList.add(timelimitedMallDTO);
            } catch (MarketCenterBusinessException bcbe) {
                continue;
            }
        }
        if (!mallAllDTOList.isEmpty()) {
            total = mallAllDTOList.size();
            logger.info("************ mallAllDTO-Size: " + total + "************");
            Collections.sort(mallAllDTOList);
            logger.info("************ mallAllDTO-Size: " + mallAllDTOList.size() + "************");
            while (total > count) {
                logger.info("************ count: " + count + " returnLen:" + mallDTOList.size() + " offset:" + offset
                        + " rows:" + rows + " ************");
                if (count >= offset && mallDTOList.size() < rows) {
                    mallDTOList.add(mallAllDTOList.get(count));
                }
                if (mallDTOList.size() >= rows) {
                    break;
                }
                count++;
            }
            datagrid.setTotal(total);
            datagrid.setRows(mallDTOList);
        }
        return datagrid;

    }

    /**
     * 从Redis中查询对应skuCode的秒杀活动信息
     *
     * @param skuCodeList
     * @return
     */
    public List<TimelimitedInfoDTO> getRedisTimelimitedInfoBySkuCode(List<String> skuCodeList) {
        List<String> promotionIdList = null;
        TimelimitedInfoDTO timelimitedInfoDTO = null;
        List<TimelimitedInfoDTO> timelimitedInfoList = new ArrayList<TimelimitedInfoDTO>();
        String promotionType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
        promotionIdList = getRedisTimelimitedIndex("", skuCodeList, "", false,promotionType);
        if (promotionIdList == null || promotionIdList.isEmpty()) {
            return timelimitedInfoList;
        }
        for (String promotionId : promotionIdList) {
            try {
                timelimitedInfoDTO = getRedisTimelimitedInfo(promotionId);
                if ((new Date()).after(timelimitedInfoDTO.getInvalidTime())) {
                    continue;
                }
                timelimitedInfoList.add(timelimitedInfoDTO);
            } catch (MarketCenterBusinessException bcbe) {
                continue;
            }
        }
        if (!timelimitedInfoList.isEmpty()) {
            Collections.sort(timelimitedInfoList, new Comparator<TimelimitedInfoDTO>() {
                public int compare(TimelimitedInfoDTO o1, TimelimitedInfoDTO o2) {
                    return o1.getInvalidTime().compareTo(o2.getInvalidTime());
                }
            });
        }
        return timelimitedInfoList;
    }

    /**
     * 修改秒杀活动展示结果信息进Redis
     *
     * @param resultDTO
     * @throws MarketCenterBusinessException
     */
    public void updateRedisTimelimitedShowResult(TimelimitedResultDTO resultDTO) throws MarketCenterBusinessException {
        Map<String, String> resultMap = null;
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + resultDTO.getPromotionId();
        int realRemainCount = 0;
        int buyedCount = 0;
        int remainCount = 0;
        resultMap = marketRedisDB.getHashOperations(timelimitedResultKey);
        if (resultMap != null) {
            realRemainCount = Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT));
            buyedCount = resultDTO.getShowTimelimitedBuyedCount();
            if (buyedCount <= realRemainCount) {
                remainCount = realRemainCount - buyedCount;
            } else {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "展示已秒杀商品数量不能大于剩余商品总数量");
            }
            marketRedisDB.setHash(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT,
                    String.valueOf(remainCount));
            marketRedisDB.setHash(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_SHOW_ACTOR_COUNT,
                    String.valueOf(resultDTO.getShowTimelimitedActorCount()));
        }
    }

    /**
     * 查询Redis秒杀活动展示结果信息
     *
     * @param promotionId
     * @return
     * @throws MarketCenterBusinessException
     */
    public TimelimitedResultDTO getRedisTimelimitedResult(String promotionId) throws MarketCenterBusinessException {
        Map<String, String> resultMap = null;
        TimelimitedResultDTO resultDTO = null;
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        resultMap = marketRedisDB.getHashOperations(timelimitedResultKey);
        if (resultMap == null || resultMap.isEmpty()) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_RESULT_NOT_EXIST,
                    "该秒杀活动的结果数据不存在!");
        }
        resultDTO = new TimelimitedResultDTO();
        resultDTO.setPromotionId(promotionId);
        resultDTO.setTotalSkuCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT)));
        resultDTO.setShowRemainSkuCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT)));
        resultDTO.setShowTimelimitedActorCount(
                Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_SHOW_ACTOR_COUNT)));
        resultDTO.setRealRemainSkuCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT)));
        resultDTO.setRealTimelimitedActorCount(
                Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_REAL_ACTOR_COUNT)));
        return resultDTO;
    }

    /**
     * 更新秒杀变化活动状态
     *
     * @param promotionInfo
     */
    public void updateRedisTimeilimitedStatus(PromotionInfoDTO promotionInfo) {
        TimelimitedInfoDTO timelimitedInfo = null;
        String promotionId = promotionInfo.getPromotionId();
        String timelimitedJsonStr = "";
        timelimitedJsonStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
        timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoDTO.class);
        if (timelimitedInfo == null) {
            return;
        }
        timelimitedInfo.setStatus(promotionInfo.getStatus());
        marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED, promotionId, JSON.toJSONString(timelimitedInfo));
    }
    
    /**
     * 处理会员秒杀活动信息
     *
     * @param timelimitedList
     * @param dealType
     * @throws MarketCenterBusinessException
     */
    public void dealRedisBuyerTimelimitedList(List<OrderItemPromotionDTO> timelimitedList, String dealType)
            throws MarketCenterBusinessException {
        List<OrderItemPromotionDTO> rollbackTimelimitedList = new ArrayList<OrderItemPromotionDTO>();
        try {
            for (OrderItemPromotionDTO timelimitedInfo : timelimitedList) {
                if (DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE.equals(dealType)) {
                    dealRedisReverseBuyerTimelimitedInfo(timelimitedInfo);
                    rollbackTimelimitedList.add(timelimitedInfo);
                } else if (DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE.equals(dealType)) {
                    dealRedisReleaseBuyerTimelimitedInfo(timelimitedInfo);
                }
            }
        } catch (MarketCenterBusinessException bcbe) {
            if (!rollbackTimelimitedList.isEmpty()) {
                for (OrderItemPromotionDTO rollbackTimelimited : rollbackTimelimitedList) {
                    dealRedisReleaseBuyerTimelimitedInfo(rollbackTimelimited);
                }
            }
            throw bcbe;
        }
    }

    /**
     * 锁定会员参加秒杀活动商品数量
     *
     * @param buyerTimelimitedInfo
     * @throws MarketCenterBusinessException
     */
    private void dealRedisReverseBuyerTimelimitedInfo(OrderItemPromotionDTO buyerTimelimitedInfo)
            throws MarketCenterBusinessException {
        Date nowDt = new Date();
        String promotionId = buyerTimelimitedInfo.getPromotionId();
        String buyerCode = buyerTimelimitedInfo.getBuyerCode();
        String orderNo = buyerTimelimitedInfo.getOrderNo();
        Integer skuCount = buyerTimelimitedInfo.getQuantity();
        TimelimitedInfoDTO timelimitedInfo = null;
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        String buyerTimelimitedKey = buyerCode + "&" + promotionId;
        long timelimitedThreshold = 0;
        long remainCount = 0;
        long showRemainCount = 0;
        long buyerTimelimitedCount = 0;

        timelimitedInfo = getRedisTimelimitedInfo(promotionId);
        try {
            if (nowDt.before(timelimitedInfo.getEffectiveTime())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NO_START,
                        "秒杀活动编号:" + promotionId + " 该活动未开始");
            } else if (nowDt.after(timelimitedInfo.getInvalidTime())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_EXPIRED,
                        "秒杀活动编号:" + promotionId + " 该活动已结束");
            }
            timelimitedThreshold = timelimitedInfo.getTimelimitedThreshold().longValue();
            marketRedisDB.incrHash(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_SHOW_ACTOR_COUNT);
            marketRedisDB.incrHash(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_REAL_ACTOR_COUNT);
            showRemainCount = marketRedisDB.incrHashBy(timelimitedResultKey,
                    RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT, skuCount * -1);
            remainCount = marketRedisDB.incrHashBy(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT,
                    skuCount * -1);
            if (remainCount < 0 || showRemainCount < 0) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_SKU_NO_REMAIN,
                        "秒杀活动编号:" + promotionId + " 该活动已被抢光");
            }
            buyerTimelimitedCount = marketRedisDB.incrHashBy(RedisConst.REDIS_BUYER_TIMELIMITED_COUNT,
                    buyerTimelimitedKey, skuCount);
            if (buyerTimelimitedCount > timelimitedThreshold) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_BUYER_NO_COUNT,
                        "订单号:" + orderNo + " 会员编号:" + buyerCode + " 秒杀活动编号:" + promotionId + " 会员已经超限秒数量");
            }
        } catch (MarketCenterBusinessException mcbe) {
            if (MarketCenterCodeConst.TIMELIMITED_SKU_NO_REMAIN.equals(mcbe.getCode())
                    || MarketCenterCodeConst.TIMELIMITED_BUYER_NO_COUNT.equals(mcbe.getCode())) {
                if (MarketCenterCodeConst.TIMELIMITED_BUYER_NO_COUNT.equals(mcbe.getCode())) {
                    marketRedisDB.incrHashBy(RedisConst.REDIS_BUYER_TIMELIMITED_COUNT, buyerTimelimitedKey,
                            skuCount * -1);
                }
                marketRedisDB.incrHashBy(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT,
                        skuCount);
                marketRedisDB.incrHashBy(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT,
                        skuCount);
            }
            throw mcbe;
        }
    }

    /**
     * 释放会员参加秒杀活动商品数量
     *
     * @param buyerTimelimitedInfo
     */
    private void dealRedisReleaseBuyerTimelimitedInfo(OrderItemPromotionDTO buyerTimelimitedInfo) {
        String promotionId = buyerTimelimitedInfo.getPromotionId();
        String buyerCode = buyerTimelimitedInfo.getBuyerCode();
        Integer skuCount = buyerTimelimitedInfo.getQuantity();
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        String buyerTimelimitedKey = buyerCode + "&" + promotionId;
        long buyerTimelimitedCount = 0;

        marketRedisDB.incrHashBy(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT, skuCount);
        marketRedisDB.incrHashBy(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT, skuCount);
        buyerTimelimitedCount = marketRedisDB.incrHashBy(RedisConst.REDIS_BUYER_TIMELIMITED_COUNT, buyerTimelimitedKey,
                skuCount * -1);
        if (buyerTimelimitedCount < 0) {
            marketRedisDB.setHash(RedisConst.REDIS_BUYER_TIMELIMITED_COUNT, buyerTimelimitedKey, "0");
        }
    }

    /**
     * 从Redis中取得锁定时订单行秒杀log信息
     *
     * @param orderTimelimiteDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    public BuyerUseTimelimitedLog getRedisReverseBuyerTimelimitedUseLog(OrderItemPromotionDTO orderTimelimiteDTO)
            throws MarketCenterBusinessException {
        String seckillLockNo = orderTimelimiteDTO.getSeckillLockNo();
        String orderNo = orderTimelimiteDTO.getOrderNo();
        String buyerCode = orderTimelimiteDTO.getBuyerCode();
        String promotionId = orderTimelimiteDTO.getPromotionId();
        BuyerUseTimelimitedLog useLog = null;
        useLog = getRedisBuyerTimelimitedUseLog(orderTimelimiteDTO);
        if (useLog != null) {
            if (!orderTimelimiteDTO.getPromoitionChangeType().equals(useLog.getUseType())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_IMELIMITED_STATUS_ERROR, " 会员编号:"
                        + buyerCode + " 促销活动ID:" + promotionId + " 该会员已参加秒杀活动秒杀活动次数已被"
                        + dictionary.getNameByValue(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS, useLog.getUseType()));
            }
            if (!StringUtils.isEmpty(useLog.getOrderNo())) {
                if (!StringUtils.isEmpty(orderNo)) {
                    if (orderNo.equals(useLog.getOrderNo())) {
                        return null;
                    }
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_TIMELIMITED_DOUBLE_REVERSE,
                            " 会员编号:" + buyerCode + " 促销活动ID:" + promotionId + " 秒杀订单号:" + useLog.getOrderNo()
                                    + " 该会员已参加该秒杀活动生成了秒杀订单");
                }
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_HAS_TIMELIMITED_ERROR,
                        " 会员编号:" + buyerCode + " 促销活动ID:" + promotionId + " 该会员已参加该秒杀活动");
            } else {
                if (seckillLockNo.equals(useLog.getSeckillLockNo())) {
                    if (StringUtils.isEmpty(orderNo)) {
                        return null;
                    }
                    useLog.setSeckillLockNo(seckillLockNo);
                    useLog.setOrderNo(orderNo);
                    useLog.setModifyId(orderTimelimiteDTO.getOperaterId());
                    useLog.setModifyName(orderTimelimiteDTO.getOperaterName());
                    useLog.setModifyTime(new Date());
                    return useLog;
                }
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_TIMELIMITED_INFO_ERROR,
                        " 会员编号:" + buyerCode + " 促销活动ID:" + promotionId + " 会员预占秒杀活动信息未释放");
            }
        } else if (!StringUtils.isEmpty(seckillLockNo) && !StringUtils.isEmpty(orderNo)) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_TIMELIMITED_NO_PREREVERSE,
                    " 会员编号:" + buyerCode + " 促销活动ID:" + promotionId + " 秒杀订单号:" + orderNo + " 该会员生成秒杀订单时没有秒杀库存预锁数据");

        }
        useLog = new BuyerUseTimelimitedLog();
        useLog.setBuyerCode(buyerCode);
        useLog.setSeckillLockNo(seckillLockNo);
        useLog.setOrderNo(orderNo);
        useLog.setPromotionId(promotionId);
        useLog.setUseType(orderTimelimiteDTO.getPromoitionChangeType());
        useLog.setUsedCount(orderTimelimiteDTO.getQuantity());
        useLog.setCreateId(orderTimelimiteDTO.getOperaterId());
        useLog.setCreateName(orderTimelimiteDTO.getOperaterName());
        useLog.setCreateTime(new Date());
        useLog.setModifyId(orderTimelimiteDTO.getOperaterId());
        useLog.setModifyName(orderTimelimiteDTO.getOperaterName());
        useLog.setModifyTime(new Date());
        return useLog;
    }

    /**
     * 取得没有提交的预占秒杀订单
     *
     * @param orderTimelimiteDTO
     * @return
     */
    public BuyerUseTimelimitedLog getNoSubmitBuyerTimelimitedUseLog(OrderItemPromotionDTO orderTimelimiteDTO) {
        String seckillLockNo = orderTimelimiteDTO.getSeckillLockNo();
        String buyerCode = orderTimelimiteDTO.getBuyerCode();
        String promotionId = orderTimelimiteDTO.getPromotionId();
        String useLogRedisKey = buyerCode + "&" + promotionId;
        String useLogJsonStr = "";
        BuyerUseTimelimitedLog useLog = null;
        useLogJsonStr = marketRedisDB.getHash(RedisConst.REDIS_BUYER_TIMELIMITED_USELOG, useLogRedisKey);
        useLog = JSON.parseObject(useLogJsonStr, BuyerUseTimelimitedLog.class);
        if (useLog == null) {
            return null;
        }
        if (StringUtils.isEmpty(useLog.getOrderNo()) && !seckillLockNo.equals(useLog.getSeckillLockNo())) {
            return useLog;
        }
        return null;
    }

    /**
     * 校验会员是否已参加过秒杀活动
     *
     * @param promotionId
     * @param buyerCode
     * @return
     */
    public boolean checkBuyerTimelimitedCondition(String promotionId, String buyerCode) {
        BuyerUseTimelimitedLog useLog = null;
        String useLogRedisKey = buyerCode + "&" + promotionId;
        String useLogJsonStr = marketRedisDB.getHash(RedisConst.REDIS_BUYER_TIMELIMITED_USELOG, useLogRedisKey);
        useLog = JSON.parseObject(useLogJsonStr, BuyerUseTimelimitedLog.class);
        if (useLog == null) {
            return true;
        }
        if (StringUtils.isEmpty(useLog.getOrderNo())) {
            return true;
        }
        return false;
    }

    /**
     * 从Redis中取得非锁定时订单行秒杀log信息
     *
     * @param orderTimelimiteDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    public BuyerUseTimelimitedLog getRedisReleaseBuyerTimelimitedUseLog(OrderItemPromotionDTO orderTimelimiteDTO)
            throws MarketCenterBusinessException {
        String promotionId = orderTimelimiteDTO.getPromotionId();
        String orderNo = orderTimelimiteDTO.getOrderNo();
        String buyerCode = orderTimelimiteDTO.getBuyerCode();
        BuyerUseTimelimitedLog useLog = null;
        useLog = getRedisBuyerTimelimitedUseLog(orderTimelimiteDTO);
        if (useLog == null) {
            return null;
        }
        if (orderTimelimiteDTO.getPromoitionChangeType().equals(useLog.getUseType())) {
            return null;
        }
        if (!dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE).equals(useLog.getUseType())) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_TIMELIMITED_NO_REVERSE, "订单号:" + orderNo
                    + " 会员编号:" + buyerCode + " 促销活动ID:" + promotionId + " 该订单已被"
                    + dictionary.getNameByValue(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS, useLog.getUseType()));
        }
        useLog.setUseType(orderTimelimiteDTO.getPromoitionChangeType());
        useLog.setModifyId(orderTimelimiteDTO.getOperaterId());
        useLog.setModifyName(orderTimelimiteDTO.getOperaterName());
        useLog.setModifyTime(new Date());
        return useLog;
    }

    /**
     * 从Redis中取得订单行秒杀log信息
     *
     * @param orderTimelimiteDTO
     * @return
     */
    private BuyerUseTimelimitedLog getRedisBuyerTimelimitedUseLog(OrderItemPromotionDTO orderTimelimiteDTO)
            throws MarketCenterBusinessException {
        String buyerCode = orderTimelimiteDTO.getBuyerCode();
        String promotionId = orderTimelimiteDTO.getPromotionId();
        String useLogRedisKey = buyerCode + "&" + promotionId;
        String useLogJsonStr = "";
        BuyerUseTimelimitedLog useLog = null;
        useLogJsonStr = marketRedisDB.getHash(RedisConst.REDIS_BUYER_TIMELIMITED_USELOG, useLogRedisKey);
        useLog = JSON.parseObject(useLogJsonStr, BuyerUseTimelimitedLog.class);
        return useLog;
    }

    /**
     * 更新未提交订单的Redis中的秒杀活动参加记录并更新DB
     *
     * @param useLogList
     */
    public void updateNoSubmitRedisUseTimelimitedLog(List<BuyerUseTimelimitedLog> useLogList) {
        String useLogRedisKey = "";
        String useType = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
        if (useLogList == null || useLogList.isEmpty()) {
            return;
        }
        for (BuyerUseTimelimitedLog useLog : useLogList) {
            useLog.setUseType(useType);
            useLogRedisKey = useLog.getBuyerCode() + "&" + useLog.getPromotionId();
            marketRedisDB.delHash(RedisConst.REDIS_BUYER_TIMELIMITED_USELOG, useLogRedisKey);
            marketRedisDB.tailPush(RedisConst.REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG, JSON.toJSONString(useLog));
        }
    }

    /**
     * 更新Redis中的秒杀活动参加记录并更新DB
     *
     * @param useLogList
     */
    public void updateRedisUseTimelimitedLog(List<BuyerUseTimelimitedLog> useLogList) {
        String useLogRedisKey = "";
        if (useLogList == null || useLogList.isEmpty()) {
            return;
        }
        for (BuyerUseTimelimitedLog useLog : useLogList) {
            useLogRedisKey = useLog.getBuyerCode() + "&" + useLog.getPromotionId();
            marketRedisDB.setHash(RedisConst.REDIS_BUYER_TIMELIMITED_USELOG, useLogRedisKey, JSON.toJSONString(useLog));
            marketRedisDB.tailPush(RedisConst.REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG, JSON.toJSONString(useLog));
        }
    }

    /**
     * 取得会员指定秒杀活动已秒商品数量
     *
     * @param buyerCode
     * @param promotionId
     * @return
     */
    public int getBuyerTimelimitedCount(String buyerCode, String promotionId) {
        String buyerTimelimitedKey = buyerCode + "&" + promotionId;
        String buyedCount = "";
        int returnCount = 0;
        buyedCount = marketRedisDB.getHash(RedisConst.REDIS_BUYER_TIMELIMITED_COUNT, buyerTimelimitedKey);
        if (!StringUtils.isEmpty(buyedCount)) {
            try {
                returnCount = Integer.valueOf(buyedCount);
            } catch (NumberFormatException e) {
                returnCount = 0;
            }
        }
        return returnCount;
    }


	/**
	 * 限时购  -  保存限时购活动信息进Redis
	 * @author li.jun
	 * @time 2017-10-09
	 * @param timelimitedInfo
	 * @return
	 */
    public void addTimelimitedPurchaseInfo2Redis(TimelimitedInfoDTO timelimitedInfo) {

        Map<String, String> resultMap = new HashMap<String, String>();
        String promotionId = timelimitedInfo.getPromotionId();
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        timelimitedInfo.setCreateTime(new Date());
        timelimitedInfo.setModifyTime(new Date());
        timelimitedInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
        resultMap.put(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT,
                String.valueOf(timelimitedInfo.getTimelimitedSkuCount()));
        resultMap.put(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT,
                String.valueOf(timelimitedInfo.getTimelimitedSkuCount()));
        resultMap.put(RedisConst.REDIS_TIMELIMITED_SHOW_ACTOR_COUNT, "0");
        resultMap.put(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT,
                String.valueOf(timelimitedInfo.getTimelimitedSkuCount()));
        resultMap.put(RedisConst.REDIS_TIMELIMITED_REAL_ACTOR_COUNT, "0");
        marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED, promotionId, JSON.toJSONString(timelimitedInfo));
        marketRedisDB.setHash(timelimitedResultKey, resultMap);
        saveTimelimitedPurchaseIndex2Redis(timelimitedInfo);
        saveTimelimitedValidStatus2Redis(timelimitedInfo);
    }

    /**
     * 限时购  -  保存秒杀活动ID索引
     * @author li.jun
	 * @time 2017-10-09
     * @param timelimitedInfo
     */
    private void saveTimelimitedPurchaseIndex2Redis(TimelimitedInfoDTO timelimitedInfo) {
        String promotionId = timelimitedInfo.getPromotionId();
        String key = "";
        List<? extends PromotionAccumulatyDTO> accumulatyList = timelimitedInfo.getPromotionAccumulatyList();
        if(accumulatyList.size() > 0) {
        	for(PromotionAccumulatyDTO accumulaty: accumulatyList){
        		key +=((TimelimitedInfoDTO) accumulaty).getSkuCode() +"&";
        	}
        }
        key = key + "&" + "1"; //1.限时购
        String promotionIdStr = "";
        promotionIdStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_PURCHASE_INDEX, key);
        if (StringUtils.isEmpty(promotionIdStr)) {
            promotionIdStr = promotionId;
        } else {
            promotionIdStr += "," + promotionId;
        }
        marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED_PURCHASE_INDEX, key, promotionIdStr);
    }
    
	public Map<String, String> getPromotionlistRedis(String skuCode) {
		Map<String, String> resultMap = new HashMap<String, String>();
		List<String> promotionIdList = new ArrayList<String>();
		List<String> promotionIdResultList = new ArrayList<String>();
		String validStatus = "";
		try {
			if(StringUtils.isNotEmpty(skuCode)){
				String purchaseKey = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
						DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT) + "&" + skuCode + "&0";
				String promotionIds = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_INDEX, purchaseKey);
				if(StringUtils.isNotEmpty(promotionIds)){
					promotionIdList = Arrays.asList(promotionIds.split(","));
				}
			}else{
				promotionIdList = getRedisTimelimitedIndex("0", null, null, false, 
						dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
						DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT));
			}
			if (!promotionIdList.isEmpty()) {
				for (int i = 0; i < promotionIdList.size(); i++) {
					validStatus = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_VALID, promotionIdList.get(i));
					if (!StringUtils.isEmpty(validStatus)
							&& dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
							DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID)
								.equals(validStatus)) {
							promotionIdResultList.add(promotionIdList.get(i));
					} else {
							resultMap.put("ERROR1", "ERROR1");
					}
				}
			}
			if (promotionIdResultList.size() > 0) {
				resultMap.put("SUCCESS", JSON.toJSONString(promotionIdResultList));
			}
		} catch (Exception e) {
			resultMap.put("ERROR2", ExceptionUtils.getStackTraceAsString(e));
		}
		return resultMap;
	}

	public List<PromotionStockChangeDTO> getPromotionStockChangeList(String promotionId, String promotionType) {
		List<PromotionStockChangeDTO> resultList = new ArrayList<PromotionStockChangeDTO>();
        TimelimitedInfoDTO timelimitedInfo = null;
        String timelimitedJsonStr = "";
        timelimitedJsonStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
        timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoDTO.class);
        if (timelimitedInfo == null) {
            return null;
        }
        if(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
				DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT).equals(promotionType)) {
        	List list = timelimitedInfo.getPromotionAccumulatyList();
    		if (null != list && !list.isEmpty()) {
    			resultList = new ArrayList<PromotionStockChangeDTO>();
    			for (int i = 0; i < list.size(); i++) {
    	            TimelimitedInfoDTO timelimite = JSONObject.toJavaObject((JSONObject) list.get(i), TimelimitedInfoDTO.class);
    	            if(timelimite.getTimelimitedSkuCount().intValue() <= 0){
    	            	continue;
    	            }
    	            PromotionStockChangeDTO promotionStockChangeDTO = new PromotionStockChangeDTO();
    	            promotionStockChangeDTO.setSkuCode(timelimite.getSkuCode());
    	            promotionStockChangeDTO.setQuantity(timelimite.getTimelimitedSkuCount());
    	            resultList.add(promotionStockChangeDTO);
    			}
    		}
		}
        return resultList;
	}
	
	public int getRealRemainCount(String promotionId, String skuCode){
		Map<String, String> resultMap = null;
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
		int realRemainCount = 0;
        resultMap = marketRedisDB.getHashOperations(timelimitedResultKey);
        if (resultMap != null) {
        	String resultCount = resultMap.get(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT + "_" + skuCode);
        	if(StringUtils.isNotEmpty(resultCount)){
                realRemainCount = Integer.valueOf(resultCount);
        	}
        }
		return realRemainCount;
	}

    /**
     * 查询Redis限时购活动展示结果信息
     * @author li.jun
     * @time 2017-10-17
     * @param promotionId
     * @return
     */
    public TimelimitedResultDTO getRedisTimelimitedPurchaseResult(String promotionId,String skuCode) throws MarketCenterBusinessException {
        Map<String, String> resultMap = null;
        TimelimitedResultDTO resultDTO = null;
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        resultMap = marketRedisDB.getHashOperations(timelimitedResultKey);
        if (resultMap == null || resultMap.isEmpty()) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_RESULT_NOT_EXIST,"该秒杀活动的结果数据不存在!");
        }
        resultDTO = new TimelimitedResultDTO();
        resultDTO.setPromotionId(promotionId);
        resultDTO.setTotalSkuCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT + "_" + skuCode)));
        resultDTO.setShowRemainSkuCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT + "_" + skuCode)));
        resultDTO.setRealRemainSkuCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT + "_" + skuCode)));
        return resultDTO;
    }

	
}
