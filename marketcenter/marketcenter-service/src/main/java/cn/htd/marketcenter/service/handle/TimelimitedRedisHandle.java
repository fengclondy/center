package cn.htd.marketcenter.service.handle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import cn.htd.marketcenter.service.PromotionBaseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("timelimitedRedisHandle")
public class TimelimitedRedisHandle {

    protected static transient Logger logger = LoggerFactory.getLogger(TimelimitedRedisHandle.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private PromotionBaseService baseService;

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
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        timelimitedResult = getRedisTimelimitedResult(promotionId);
//        timelimitedInfo.setTimelimitedResult(timelimitedResult);
        getRedisTimelimitedResult(timelimitedInfo);
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
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
        //----- add by jiangkun for 2017双12限时购 on 20171013 start -----
        List<?> accuDTOList = null;
        TimelimitedInfoDTO tmpTimelimiteDTO = null;
        //----- add by jiangkun for 2017双12限时购 on 20171013 end -----

        timelimitedJsonStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
        timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoDTO.class);
        if (timelimitedInfo != null) {
            //----- modify by jiangkun for 2017双12限时购 on 20171013 start -----
//            indexKey = timelimitedInfo.getSkuCode() + "&" + timelimitedInfo.getIsVip();
//            if (!StringUtils.isEmpty(timelimitedInfo.getPromotionProviderSellerCode())) {
//                indexKey = indexKey + "&" + timelimitedInfo.getPromotionProviderSellerCode();
//            }
//            promotionAllIdStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_INDEX, indexKey);
//            if (!StringUtils.isEmpty(promotionAllIdStr)) {
//                if (promotionAllIdStr.equals(promotionId)) {
//                    marketRedisDB.delHash(RedisConst.REDIS_TIMELIMITED_INDEX, indexKey);
//                } else {
//                    promotionAllIdStr = "," + promotionAllIdStr + ",";
//                    promotionAllIdStr = promotionAllIdStr.replace("," + promotionId + ",", ",");
//                    promotionAllIdStr = promotionAllIdStr.substring(1, promotionAllIdStr.length() - 1);
//                    marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED_INDEX, indexKey, promotionAllIdStr);
//                }
//            }
            accuDTOList = timelimitedInfo.getPromotionAccumulatyList();
            if (accuDTOList == null || accuDTOList.isEmpty()) {
                indexKey = timelimitedInfo.getPromotionType() + "&" + timelimitedInfo.getSkuCode() + "&" + timelimitedInfo.getIsVip();
                if (!StringUtils.isEmpty(timelimitedInfo.getPromotionProviderSellerCode())) {
                    indexKey = indexKey + "&" + timelimitedInfo.getPromotionProviderSellerCode();
                }
                keyList.add(indexKey);
            } else {
                for(int i = 0; i < accuDTOList.size(); i++){
                    tmpTimelimiteDTO = JSONObject.toJavaObject((JSONObject) accuDTOList.get(i), TimelimitedInfoDTO.class);
                    indexKey = tmpTimelimiteDTO.getPromotionType() + "&" + tmpTimelimiteDTO.getSkuCode() + "&" + timelimitedInfo.getIsVip();
                    if (!StringUtils.isEmpty(tmpTimelimiteDTO.getPromotionProviderSellerCode())) {
                        indexKey = indexKey + "&" + tmpTimelimiteDTO.getPromotionProviderSellerCode();
                    }
                    keyList.add(indexKey);
                }
            }
            for (String key : keyList) {
                promotionAllIdStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_INDEX, key);
                if (!StringUtils.isEmpty(promotionAllIdStr)) {
                    if (promotionAllIdStr.equals(promotionId)) {
                        marketRedisDB.delHash(RedisConst.REDIS_TIMELIMITED_INDEX, key);
                    } else {
                        promotionAllIdStr = "," + promotionAllIdStr + ",";
                        promotionAllIdStr = promotionAllIdStr.replace("," + promotionId + ",", ",");
                        promotionAllIdStr = promotionAllIdStr.substring(1, promotionAllIdStr.length() - 1);
                        marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED_INDEX, key, promotionAllIdStr);
                    }
                }
            }
            //----- modify by jiangkun for 2017双12限时购 on 20171013 end -----
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
        timelimitedInfo.setCreateTime(new Date());
        timelimitedInfo.setModifyTime(new Date());
        timelimitedInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
        //edit by lijun 限时购
        List<? extends PromotionAccumulatyDTO> accumulatyList = timelimitedInfo.getPromotionAccumulatyList();
        if (null != accumulatyList && accumulatyList.size() > 0) {
            for (PromotionAccumulatyDTO accumulaty : accumulatyList) {
                TimelimitedInfoDTO timelimited = (TimelimitedInfoDTO) accumulaty;
                String sku_code = timelimited.getSkuCode();
                resultMap.put(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT + "_" + sku_code,
                        String.valueOf(timelimited.getTimelimitedSkuCount()));
                resultMap.put(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT + "_" + sku_code,
                        String.valueOf(timelimited.getTimelimitedSkuCount()));
                resultMap.put(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT + "_" + sku_code,
                        String.valueOf(timelimited.getTimelimitedSkuCount()));
            }
        } else {
            resultMap.put(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT,
                    String.valueOf(timelimitedInfo.getTimelimitedSkuCount()));
            resultMap.put(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT,
                    String.valueOf(timelimitedInfo.getTimelimitedSkuCount()));
            resultMap.put(RedisConst.REDIS_TIMELIMITED_SHOW_ACTOR_COUNT, "0");
            resultMap.put(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT,
                    String.valueOf(timelimitedInfo.getTimelimitedSkuCount()));
            resultMap.put(RedisConst.REDIS_TIMELIMITED_REAL_ACTOR_COUNT, "0");
        }
        //edit by lijun 限时购
        //----- add by jiangkun for 2017活动需求商城无敌券 on 20171009 start -----
        baseService.deletePromotionUselessInfo(timelimitedInfo);
        baseService.deleteBuyerUselessInfo(timelimitedInfo);
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
        String promotionIdStr = "";
        String indexKey = "";
        List<String> keyList = new ArrayList<String>();
        List<? extends PromotionAccumulatyDTO> accuDTOList = null;
        //modify by lijun for 限时购 start
        accuDTOList = timelimitedInfo.getPromotionAccumulatyList();
        if (accuDTOList == null || accuDTOList.isEmpty()) {
            indexKey = timelimitedInfo.getPromotionType() + "&" + timelimitedInfo.getSkuCode() + "&" + timelimitedInfo.getIsVip();
            if (!StringUtils.isEmpty(timelimitedInfo.getPromotionProviderSellerCode())) {
                indexKey = indexKey + "&" + timelimitedInfo.getPromotionProviderSellerCode();
            }
            keyList.add(indexKey);
        } else {
        	 for (PromotionAccumulatyDTO accumulaty : accuDTOList) {
        		 TimelimitedInfoDTO tmpTimelimiteDTO = (TimelimitedInfoDTO) accumulaty;
        		 indexKey = tmpTimelimiteDTO.getPromotionType() + "&" + tmpTimelimiteDTO.getSkuCode() + "&" + timelimitedInfo.getIsVip();
                 if (!StringUtils.isEmpty(tmpTimelimiteDTO.getPromotionProviderSellerCode())) {
                     indexKey = indexKey + "&" + tmpTimelimiteDTO.getPromotionProviderSellerCode();
                 }
                 keyList.add(indexKey);
        	 }
        }
        for(String key :keyList){
            promotionIdStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_INDEX, key);
            if (StringUtils.isEmpty(promotionIdStr)) {
                promotionIdStr = promotionId;
            } else {
                promotionIdStr += "," + promotionId;
            }
            marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED_INDEX, key, promotionIdStr);
        }
        //modify by lijun for 限时购 end
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
            //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//            boolean isContainSellerTimelimitedFlag) {
            boolean isContainSellerTimelimitedFlag, String...promotionType) {
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
        List<String> promotionIdList = new ArrayList<String>();
        Map<String, String> indexMap = null;
        String key = "";
        String promotionIdStr = "";
        String promotionAllIdStr = "";
        String[] keyArr = null;
        String tmpIsVip = "";
        String tmpSkuCode = "";
        String tmpSellerCode = "";
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
        String tmpPromotionType ="";
        String targetPromotionType = null;
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
        indexMap = marketRedisDB.getHashOperations(RedisConst.REDIS_TIMELIMITED_INDEX);
        if (indexMap == null || indexMap.isEmpty()) {
            return null;
        }
        if (promotionType != null && promotionType.length > 0) {
            targetPromotionType = promotionType[0];
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
            //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
            tmpPromotionType = keyArr[0];
            tmpSkuCode = keyArr[1];
            tmpIsVip = keyArr[2];
            //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
            tmpSellerCode = "";
            if (skuCodeList != null && !skuCodeList.isEmpty() && !skuCodeList.contains(tmpSkuCode)) {
                continue;
            }
            if (!StringUtils.isEmpty(isVipFlag) && !tmpIsVip.equals(isVipFlag)) {
                continue;
            }
            //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
            if(!StringUtils.isEmpty(targetPromotionType) && !tmpPromotionType.equals(targetPromotionType)){
            	 continue;
            }
            //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
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
            Pager<TimelimitedInfoDTO> page, String promotionType) {
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
        promotionIdList = getRedisTimelimitedIndex(isVipFlag, null, "", false, promotionType);
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
     * @param promotionType
     * @return
     */
    //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//    public List<TimelimitedInfoDTO> getRedisTimelimitedInfoBySkuCode(List<String> skuCodeList) {
    public List<TimelimitedInfoDTO> getRedisTimelimitedInfoBySkuCode(List<String> skuCodeList, String...promotionType) {
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
        List<String> promotionIdList = null;
        TimelimitedInfoDTO timelimitedInfoDTO = null;
        List<TimelimitedInfoDTO> timelimitedInfoList = new ArrayList<TimelimitedInfoDTO>();
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        promotionIdList = getRedisTimelimitedIndex("", skuCodeList, "", false);
        List<?> accuDTOList = null;
        TimelimitedInfoDTO tmpTimelimitedInfoDTO = null;
        promotionIdList = getRedisTimelimitedIndex("", skuCodeList, "", false, promotionType);
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
        if (promotionIdList == null || promotionIdList.isEmpty()) {
            return timelimitedInfoList;
        }
        for (String promotionId : promotionIdList) {
            try {
                timelimitedInfoDTO = getRedisTimelimitedInfo(promotionId);
                if ((new Date()).after(timelimitedInfoDTO.getInvalidTime())) {
                    continue;
                }
                //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
                accuDTOList = timelimitedInfoDTO.getPromotionAccumulatyList();
                if (accuDTOList!= null && !accuDTOList.isEmpty()) {
                    for (int i = 0; i < accuDTOList.size(); i ++) {
                        tmpTimelimitedInfoDTO = (TimelimitedInfoDTO) accuDTOList.get(i);
                        if (!skuCodeList.contains(tmpTimelimitedInfoDTO.getSkuCode())) {
                            continue;
                        }
                        timelimitedInfoList.add(tmpTimelimitedInfoDTO);
                    }
                } else {
                    //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
                    timelimitedInfoList.add(timelimitedInfoDTO);
                    //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
                }
                //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
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
     * @param timelimitedInfoDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//    public TimelimitedResultDTO getRedisTimelimitedResult(String promotionId) throws MarketCenterBusinessException {
    public void getRedisTimelimitedResult(TimelimitedInfoDTO timelimitedInfoDTO) throws MarketCenterBusinessException {
        String promotionId = timelimitedInfoDTO.getPromotionId();
        List<?> accuDTOList = timelimitedInfoDTO.getPromotionAccumulatyList();
        TimelimitedInfoDTO tmpTimelimitedInfoDTO = null;
        List<TimelimitedInfoDTO> timelimitedInfoList = new ArrayList<TimelimitedInfoDTO>();
        String skuCode = "";
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
        Map<String, String> resultMap = null;
        TimelimitedResultDTO resultDTO = null;
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        resultMap = marketRedisDB.getHashOperations(timelimitedResultKey);
        if (resultMap == null || resultMap.isEmpty()) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_RESULT_NOT_EXIST,
                    "该秒杀活动的结果数据不存在!");
        }
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
        if (accuDTOList != null && !accuDTOList.isEmpty()) {
            for (int i = 0; i < accuDTOList.size(); i ++) {
                tmpTimelimitedInfoDTO =
                        JSONObject.toJavaObject((JSONObject) accuDTOList.get(i), TimelimitedInfoDTO.class);
                skuCode = tmpTimelimitedInfoDTO.getSkuCode();
                resultDTO = new TimelimitedResultDTO();
                resultDTO.setPromotionId(promotionId);
                if (!StringUtils.isEmpty(skuCode)) {
                    if (resultMap.containsKey(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT + "_" + skuCode)) {
                        resultDTO.setTotalSkuCount(Integer.valueOf(
                                resultMap.get(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT + "_" + skuCode)));
                    }
                    if (resultMap.containsKey(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT + "_" + skuCode)) {
                        resultDTO.setShowRemainSkuCount(Integer.valueOf(
                                resultMap.get(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT + "_" + skuCode)));
                    }
                    if (resultMap.containsKey(RedisConst.REDIS_TIMELIMITED_SHOW_ACTOR_COUNT + "_" + skuCode)) {
                        resultDTO.setShowTimelimitedActorCount(Integer.valueOf(
                                resultMap.get(RedisConst.REDIS_TIMELIMITED_SHOW_ACTOR_COUNT + "_" + skuCode)));
                    }
                    if (resultMap.containsKey(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT + "_" + skuCode)) {
                        resultDTO.setRealRemainSkuCount(Integer.valueOf(
                                resultMap.get(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT + "_" + skuCode)));
                    }
                    if (resultMap.containsKey(RedisConst.REDIS_TIMELIMITED_REAL_ACTOR_COUNT + "_" + skuCode)) {
                        resultDTO.setRealTimelimitedActorCount(Integer.valueOf(
                                resultMap.get(RedisConst.REDIS_TIMELIMITED_REAL_ACTOR_COUNT + "_" + skuCode)));
                    }
                }
                tmpTimelimitedInfoDTO.setTimelimitedResult(resultDTO);
                timelimitedInfoList.add(tmpTimelimitedInfoDTO);
            }
            timelimitedInfoDTO.setPromotionAccumulatyList(timelimitedInfoList);
        } else {
            //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
            resultDTO = new TimelimitedResultDTO();
            resultDTO.setPromotionId(promotionId);
            resultDTO.setTotalSkuCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_TOTAL_COUNT)));
            resultDTO.setShowRemainSkuCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT)));
            resultDTO.setShowTimelimitedActorCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_SHOW_ACTOR_COUNT)));
            resultDTO.setRealRemainSkuCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT)));
            resultDTO.setRealTimelimitedActorCount(Integer.valueOf(resultMap.get(RedisConst.REDIS_TIMELIMITED_REAL_ACTOR_COUNT)));
            //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
            timelimitedInfoDTO.setTimelimitedResult(resultDTO);
        }
//        return resultDTO;
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
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
        useLog.setPromotionType(orderTimelimiteDTO.getPromotionType());
        useLog.setSkuCode(orderTimelimiteDTO.getSkuCode());
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

    //----- add by jiangkun for 2017活动需求商城限时购 on 20171009 start -----
    /**
     * 处理会员限时购活动信息
     *
     * @param timelimitedMap
     * @param dealType
     * @throws MarketCenterBusinessException
     */
    public void dealRedisBuyerLimitedDiscountList(Map<String, List<OrderItemPromotionDTO>> timelimitedMap, String dealType)
            throws MarketCenterBusinessException {
        List<OrderItemPromotionDTO> rollbackTimelimitedList = new ArrayList<OrderItemPromotionDTO>();
        String promotionId = "";
        List<OrderItemPromotionDTO> timelimitedList = null;
        String validStatus = "";
        String timelimitedJsonStr = "";
        TimelimitedInfoDTO timelimitedInfo = null;
        TimelimitedInfoDTO tmpTimelimitedInfoDTO = null;
        List<?> accuDTOList = null;
        Map<String, TimelimitedInfoDTO> timelimitedInfoDTOMap = new HashMap<String, TimelimitedInfoDTO>();
        String skuCode = "";

        try {
            for (Entry<String, List<OrderItemPromotionDTO>> entry : timelimitedMap.entrySet()) {
                promotionId = entry.getKey();
                timelimitedList = entry.getValue();
                if (DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE.equals(dealType)) {
                    validStatus = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_VALID, promotionId);
                    if (StringUtils.isEmpty(validStatus) || !dictionary
                            .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(validStatus)) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_VALID,
                                "活动ID:" + promotionId + " 该活动未启用");
                    }
                    timelimitedJsonStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
                    timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoDTO.class);
                    if (timelimitedInfo == null) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST,
                                "活动ID:" + promotionId + " 该活动不存在!");
                    }
                    accuDTOList = timelimitedInfo.getPromotionAccumulatyList();
                    if (accuDTOList!= null && !accuDTOList.isEmpty()) {
                        for (int i = 0; i < accuDTOList.size(); i ++) {
                            tmpTimelimitedInfoDTO =
                                    JSONObject.toJavaObject((JSONObject) accuDTOList.get(i), TimelimitedInfoDTO.class);
                            timelimitedInfoDTOMap.put(tmpTimelimitedInfoDTO.getSkuCode(), tmpTimelimitedInfoDTO);
                        }
                    }
                }
                for (OrderItemPromotionDTO orderItemDTO : timelimitedList) {
                    if (DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE.equals(dealType)) {
                        skuCode = orderItemDTO.getSkuCode();
                        if (!timelimitedInfoDTOMap.containsKey(skuCode)) {
                            throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NO_CONTAIN_SKU,
                                    "活动ID:" + promotionId + " SKU编码:" + skuCode + " 限时购活动中不包含购买商品");
                        }
                        tmpTimelimitedInfoDTO = timelimitedInfoDTOMap.get(skuCode);
                        dealRedisReverseBuyerLimitedDiscountInfo(tmpTimelimitedInfoDTO, orderItemDTO);
                        rollbackTimelimitedList.add(orderItemDTO);
                    } else if (DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE.equals(dealType)) {
                        dealRedisReleaseBuyerLimitedDiscountInfo(orderItemDTO);
                    }
                }
            }
        } catch (MarketCenterBusinessException bcbe) {
            if (!rollbackTimelimitedList.isEmpty()) {
                for (OrderItemPromotionDTO rollbackTimelimited : rollbackTimelimitedList) {
                    dealRedisReleaseBuyerLimitedDiscountInfo(rollbackTimelimited);
                }
            }
            throw bcbe;
        }
    }
    /**
     * 从Redis中取得锁定时订单行秒杀log信息
     *
     * @param orderTimelimiteDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    public BuyerUseTimelimitedLog getRedisReverseBuyerLimitedDiscountUseLog(OrderItemPromotionDTO orderTimelimiteDTO)
            throws MarketCenterBusinessException {
        String orderNo = orderTimelimiteDTO.getOrderNo();
        String orderItemNo = orderTimelimiteDTO.getOrderItemNo();
        String buyerCode = orderTimelimiteDTO.getBuyerCode();
        String promotionId = orderTimelimiteDTO.getPromotionId();
        BuyerUseTimelimitedLog useLog = null;
        useLog = getRedisBuyerLimitedDiscountUseLog(orderTimelimiteDTO);
        if (useLog != null) {
            if (!orderTimelimiteDTO.getPromoitionChangeType().equals(useLog.getUseType())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_LIMITED_TIME_PURCHASE_STATUS_ERROR,
                        " 订单行编号:" + orderItemNo + " 促销活动ID:" + promotionId + " 该子订单已被" + dictionary
                                .getNameByValue(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS, useLog.getUseType()));
            }
            if (useLog.getUsedCount().compareTo(orderTimelimiteDTO.getQuantity()) != 0) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_LIMITED_TIME_PURCHASE_DEAL_DIFF_COUNT,
                        " 订单行编号:" + orderItemNo + " 促销活动ID:" + promotionId + " 该子订单锁定数量不同");
            }
            return null;
        }
        useLog = new BuyerUseTimelimitedLog();
        useLog.setBuyerCode(buyerCode);
        useLog.setOrderNo(orderNo);
        useLog.setOrderItemNo(orderItemNo);
        useLog.setPromotionId(promotionId);
        useLog.setLevelCode(orderTimelimiteDTO.getLevelCode());
        useLog.setPromotionType(orderTimelimiteDTO.getPromotionType());
        useLog.setSkuCode(orderTimelimiteDTO.getSkuCode());
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
     * 取得限时购Log信息
     * @param orderTimelimiteDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    private BuyerUseTimelimitedLog getRedisBuyerLimitedDiscountUseLog(OrderItemPromotionDTO orderTimelimiteDTO)
            throws MarketCenterBusinessException {
        String promotionId = orderTimelimiteDTO.getPromotionId();
        String orderItemNo = orderTimelimiteDTO.getOrderItemNo();
        String useLogJsonStr = "";
        BuyerUseTimelimitedLog useLog = null;
        useLogJsonStr = marketRedisDB.getHash(RedisConst.REDIS_BUYER_TIMELIMITED_USELOG, orderItemNo + "&" + promotionId);
        useLog = JSON.parseObject(useLogJsonStr, BuyerUseTimelimitedLog.class);
        return useLog;
    }

    /**
     * 更新Redis中的限时购活动参加记录并更新DB
     *
     * @param useLogList
     */
    public void updateRedisUseLimitedDiscountLog(List<BuyerUseTimelimitedLog> useLogList) {
        String useLogRedisKey = "";
        if (useLogList == null || useLogList.isEmpty()) {
            return;
        }
        for (BuyerUseTimelimitedLog useLog : useLogList) {
            useLogRedisKey = useLog.getOrderItemNo() + "&" + useLog.getPromotionId();
            marketRedisDB.setHash(RedisConst.REDIS_BUYER_TIMELIMITED_USELOG, useLogRedisKey, JSON.toJSONString(useLog));
            marketRedisDB.tailPush(RedisConst.REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG, JSON.toJSONString(useLog));
        }
    }
    /**
     * 锁定会员参加限时购活动商品数量
     *
     *
     * @param timelimitedInfoDTO
     * @param buyerTimelimitedInfo
     * @throws MarketCenterBusinessException
     */
    private void dealRedisReverseBuyerLimitedDiscountInfo(TimelimitedInfoDTO timelimitedInfoDTO,
            OrderItemPromotionDTO buyerTimelimitedInfo)
            throws MarketCenterBusinessException {
        Date nowDt = new Date();
        String promotionId = buyerTimelimitedInfo.getPromotionId();
        String orderItemNo = buyerTimelimitedInfo.getOrderItemNo();
        String skuCode = buyerTimelimitedInfo.getSkuCode();
        Integer skuCount = buyerTimelimitedInfo.getQuantity();
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        int timelimitedThresholdMin = 0;
        int timelimitedThreshold = 0;
        long showRemainCount = 0;

        try {
            if (nowDt.before(timelimitedInfoDTO.getStartTime())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NOT_BEGIN,
                        "限时购活动编号:" + promotionId + " SKU编码:" + skuCode + " 该活动未开始");
            } else if (nowDt.after(timelimitedInfoDTO.getEndTime())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_IS_OVER,
                        "限时购活动编号:" + promotionId + " SKU编码:" + skuCode + " 该活动已结束");
            }
            timelimitedThresholdMin = timelimitedInfoDTO.getTimelimitedThresholdMin().intValue();
            timelimitedThreshold = timelimitedInfoDTO.getTimelimitedThreshold().intValue();
            if (skuCount < timelimitedThresholdMin) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_MIN_QUANTITY,
                        "子订单号:" + orderItemNo + " 活动编号:" + promotionId + " SKU编码:" + skuCode + " 未到活动起订量");
            }
            if (skuCount > timelimitedThreshold) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_MAX_QUANTITY,
                        "子订单号:" + orderItemNo + " 活动编号:" + promotionId + " SKU编码:" + skuCode + " 已超活动限购量");
            }
            showRemainCount = marketRedisDB.incrHashBy(timelimitedResultKey,
                    RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT + "_" + skuCode, skuCount * -1);
            if (showRemainCount < 0) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NO_COUNT,
                        "子订单号:" + orderItemNo + " 活动编号:" + promotionId + " SKU编码:" + skuCode +  " 该活动已被抢光");
            }
        } catch (MarketCenterBusinessException mcbe) {
            if (MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NO_COUNT.equals(mcbe.getCode())) {
                marketRedisDB.incrHashBy(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT + "_" + skuCode,
                        skuCount);
            }
            throw mcbe;
        }
    }
    /**
     * 释放会员参加限时购活动商品数量
     *
     * @param buyerTimelimitedInfo
     */
    private void dealRedisReleaseBuyerLimitedDiscountInfo(OrderItemPromotionDTO buyerTimelimitedInfo) {
        String promotionId = buyerTimelimitedInfo.getPromotionId();
        String skuCode = buyerTimelimitedInfo.getSkuCode();
        Integer skuCount = buyerTimelimitedInfo.getQuantity();
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;

        marketRedisDB.incrHashBy(timelimitedResultKey, RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT +"_" + skuCode, skuCount);
    }

    /**
     * 从Redis中取得非锁定时订单行秒杀log信息
     *
     * @param orderTimelimiteDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    public BuyerUseTimelimitedLog getRedisReleaseBuyerLimitedDiscountUseLog(OrderItemPromotionDTO orderTimelimiteDTO)
            throws MarketCenterBusinessException {
        String promotionId = orderTimelimiteDTO.getPromotionId();
        String orderItemNo = orderTimelimiteDTO.getOrderItemNo();
        BuyerUseTimelimitedLog useLog = null;
        useLog = getRedisBuyerLimitedDiscountUseLog(orderTimelimiteDTO);
        if (useLog == null) {
            return null;
        }
        if (orderTimelimiteDTO.getPromoitionChangeType().equals(useLog.getUseType())) {
            return null;
        }
        if (!dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE).equals(useLog.getUseType())) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_LIMITED_TIME_PURCHASE_NO_REVERSE,
                    "子订单号:" + orderItemNo + " 促销活动ID:" + promotionId + " 该子订单已被" + dictionary
                            .getNameByValue(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS, useLog.getUseType()));
        }
        useLog.setUseType(orderTimelimiteDTO.getPromoitionChangeType());
        useLog.setModifyId(orderTimelimiteDTO.getOperaterId());
        useLog.setModifyName(orderTimelimiteDTO.getOperaterName());
        useLog.setModifyTime(new Date());
        return useLog;
    }
    //----- add by jiangkun for 2017活动需求商城限时购 on 20171009 end -----

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
				List<String> newList = getRedisTimelimitedIndex("0", null, null, false, 
						dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
						DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT));
				Set<String> set = new  HashSet<String>(); 
		        set.addAll(newList);
		        promotionIdList.addAll(set);
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

	public PromotionStockChangeDTO getPromotionStockChangeList(String promotionId, String skuCode, String promotionType) {
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
    			for (int i = 0; i < list.size(); i++) {
    	            TimelimitedInfoDTO timelimite = JSONObject.toJavaObject((JSONObject) list.get(i), TimelimitedInfoDTO.class);
    	            int skuTotal = getShowRemainCount(promotionId, timelimite.getSkuCode());
    	            if(!timelimite.getSkuCode().equals(skuCode)){
    	            	continue;
    	            }
    	            if(skuTotal <= 0){
    	            	continue;
    	            }
    	            PromotionStockChangeDTO promotionStockChangeDTO = new PromotionStockChangeDTO();
    	            promotionStockChangeDTO.setSkuCode(timelimite.getSkuCode());
    	            promotionStockChangeDTO.setQuantity(skuTotal);
    	            return promotionStockChangeDTO;
    			}
    		}
		}
        return null;
	}
	
	public int getShowRemainCount(String promotionId, String skuCode){
		Map<String, String> resultMap = null;
        String timelimitedResultKey = RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId;
		int realRemainCount = 0;
        resultMap = marketRedisDB.getHashOperations(timelimitedResultKey);
        if (resultMap != null) {
        	String resultCount = resultMap.get(RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT + "_" + skuCode);
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
