package cn.htd.promotion.cpc.biz.handle;

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
import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionOrderItemDTO;
import cn.htd.promotion.cpc.dto.response.PromotionTimelimitedShowDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedResultDTO;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("promotionGroupbuyingRedisHandle")
public class PromotionGroupbuyingRedisHandle {

    protected static transient Logger logger = LoggerFactory.getLogger(PromotionGroupbuyingRedisHandle.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionRedisDB promotionRedisDB;
    
    public TimelimitedInfoResDTO getTimelitedInfoByPromotionId(String promotionId){
    	String timelimitedJsonStr = "";
    	 TimelimitedInfoResDTO timelimitedInfoDTO = null;
		timelimitedJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionId);
		timelimitedInfoDTO = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoResDTO.class);
		if(null !=timelimitedInfoDTO){
			return timelimitedInfoDTO;
		}
		return null;
    }
    

    /**
     * 秒杀 - 保存秒杀活动的启用状态
     * 
     * @param skuCodeList 商品编码集合
     * @return
     */
    public void saveTimelimitedValidStatus2Redis(TimelimitedInfoResDTO timelimitedInfoResDTO) {
    	  String jsonObj = JSON.toJSONString(timelimitedInfoResDTO);
    	  promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, timelimitedInfoResDTO.getPromotionId(), jsonObj);
    }
    
    
    /**
     * 秒杀 - 根据促销活动id修改秒杀活动的启用状态
     * 
     * @param skuCodeList 商品编码集合
     * @return
     */
    public void updateTimelimitedValidStatus2Redis(String promotionId,String showStatus) {
    	 TimelimitedInfoResDTO timelimitedInfoDTO = null;
	     String timelimitedJsonStr = "";
	     if(StringUtils.isNotBlank(promotionId) && StringUtils.isNotBlank(showStatus)){
			 timelimitedJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionId);
			 timelimitedInfoDTO = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoResDTO.class);
			 timelimitedInfoDTO.getPromotionExtendInfoDTO().setShowStatus(showStatus);
	    	 String jsonObj = JSON.toJSONString(timelimitedInfoDTO);
	    	 promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, timelimitedInfoDTO.getPromotionId(), jsonObj);
	     }
    }


    /**
     * 秒杀 - 查询Redis秒杀活动展示结果信息
     *
     * @param promotionId
     * @return
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
