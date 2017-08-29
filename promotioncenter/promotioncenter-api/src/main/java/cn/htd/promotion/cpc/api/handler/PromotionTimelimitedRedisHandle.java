package cn.htd.promotion.cpc.api.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedResultDTO;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("timelimitedRedisHandle")
public class PromotionTimelimitedRedisHandle {

    protected static transient Logger logger = LoggerFactory.getLogger(PromotionTimelimitedRedisHandle.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionRedisDB promotionRedisDB;


    /**
     * 从Redis中查询对应skuCode的秒杀活动信息
     * @param skuCodeList
     * @return
     */
    public List<TimelimitedInfoResDTO> getRedisTimelimitedInfoBySkuCode(List<String> skuCodeList) {
        List<String> promotionIdList = null;
        TimelimitedInfoResDTO timelimitedInfoDTO = null;
        List<TimelimitedInfoResDTO> timelimitedInfoList = new ArrayList<TimelimitedInfoResDTO>();
        promotionIdList = getRedisTimelimitedIndex(skuCodeList);
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
            } catch (PromotionCenterBusinessException bcbe) {
                continue;
            }
        }
        if (!timelimitedInfoList.isEmpty()) {
            Collections.sort(timelimitedInfoList, new Comparator<TimelimitedInfoResDTO>() {
                public int compare(TimelimitedInfoResDTO o1, TimelimitedInfoResDTO o2) {
                    return o1.getInvalidTime().compareTo(o2.getInvalidTime());
                }
            });
        }
        return timelimitedInfoList;
    }
    
    
    
    /**
     * 根据商品SkuCode列表取得秒杀活动信息列表
     *
     * @param skuCodeList
     */
    public List<String> getRedisTimelimitedIndex(List<String> skuCodeList) {
        List<String> promotionIdList = new ArrayList<String>();
        Map<String, String> indexMap = null;
        String key = "";
        String promotionIdStr = "";
        String promotionAllIdStr = "";
        String[] keyArr = null;
        String tmpSkuCode = "";
        indexMap = promotionRedisDB.getHashOperations(RedisConst.PROMOTION_REDIS_TIMELIMITED_INDEX);
        if (indexMap == null || indexMap.isEmpty()) {
            return null;
        }
        for (Entry<String, String> entry : indexMap.entrySet()) {
            key = entry.getKey();
            promotionIdStr = entry.getValue();
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(promotionIdStr) || key.indexOf("&") < 0) {
            	promotionRedisDB.delHash(RedisConst.PROMOTION_REDIS_TIMELIMITED_INDEX, key);
                continue;
            }
            keyArr = key.split("&");
            tmpSkuCode = keyArr[0];
            if (skuCodeList != null && !skuCodeList.isEmpty() && !skuCodeList.contains(tmpSkuCode)) {
                continue;
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
     * 取得Redis秒杀活动信息
     *
     * @param promotionId
     */
    public TimelimitedInfoResDTO getRedisTimelimitedInfo(String promotionId) throws PromotionCenterBusinessException {
    	TimelimitedInfoResDTO timelimitedInfo = null;
        TimelimitedResultDTO timelimitedResult = null;
        String timelimitedJsonStr = "";
        String validStatus = "";
        validStatus = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_TIMELIMITED_VALID, promotionId);
        if (!StringUtils.isEmpty(validStatus)
                && !dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(validStatus)) {
            throw new PromotionCenterBusinessException(PromotionCenterConst.PROMOTION_NOT_VALID,
                    "秒杀活动ID:" + promotionId + " 该秒杀活动未上架");
        }
        timelimitedJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionId);
        timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoResDTO.class);
        if (timelimitedInfo == null) {
            throw new PromotionCenterBusinessException(PromotionCenterConst.PROMOTION_NOT_EXIST,
                    "秒杀活动ID:" + promotionId + " 该秒杀活动不存在!");
        }
        timelimitedResult = getRedisTimelimitedResult(promotionId);
        timelimitedInfo.setTimelimitedResult(timelimitedResult);
        return timelimitedInfo;
    }
    
    

    /**
     * 查询Redis秒杀活动展示结果信息
     *
     * @param promotionId
     * @return
     * @throws MarketCenterBusinessException
     */
    public TimelimitedResultDTO getRedisTimelimitedResult(String promotionId) throws PromotionCenterBusinessException {
        Map<String, String> resultMap = null;
        TimelimitedResultDTO resultDTO = null;
        String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        resultMap = promotionRedisDB.getHashOperations(timelimitedResultKey);
        if (resultMap == null || resultMap.isEmpty()) {
            throw new PromotionCenterBusinessException(PromotionCenterConst.TIMELIMITED_RESULT_NOT_EXIST,
                    "该秒杀活动的结果数据不存在!");
        }
        resultDTO = new TimelimitedResultDTO();
        resultDTO.setPromotionId(promotionId);
        resultDTO.setTotalSkuCount(Integer.valueOf(resultMap.get(RedisConst.PROMOTION_REDIS_TIMELIMITED_TOTAL_COUNT)));
        resultDTO.setShowRemainSkuCount(Integer.valueOf(resultMap.get(RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_REMAIN_COUNT)));
        resultDTO.setShowTimelimitedActorCount(
                Integer.valueOf(resultMap.get(RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_ACTOR_COUNT)));
        resultDTO.setRealRemainSkuCount(Integer.valueOf(resultMap.get(RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_REMAIN_COUNT)));
        resultDTO.setRealTimelimitedActorCount(
                Integer.valueOf(resultMap.get(RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_ACTOR_COUNT)));
        return resultDTO;
    }
}
