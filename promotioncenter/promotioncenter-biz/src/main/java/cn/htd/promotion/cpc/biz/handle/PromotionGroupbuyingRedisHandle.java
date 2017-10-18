package cn.htd.promotion.cpc.biz.handle;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionCenterRedisDB;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;

import com.alibaba.fastjson.JSON;

@Service("promotionGroupbuyingRedisHandle")
public class PromotionGroupbuyingRedisHandle {

    protected static transient Logger logger = LoggerFactory.getLogger(PromotionGroupbuyingRedisHandle.class);
    
    @Resource
    private PromotionCenterRedisDB promotionCenterRedisDB;
    
    /**
     * 获取redis里的团购活动信息
     * @param promotionId
     * @return
     */
    public GroupbuyingInfoCmplResDTO getGroupbuyingInfoCmplByPromotionId(String promotionId){
    	
    	String groupbuyingInfoJsonStr = promotionCenterRedisDB.getHash(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, promotionId);
    	GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = JSON.parseObject(groupbuyingInfoJsonStr, GroupbuyingInfoCmplResDTO.class);
		if(null == groupbuyingInfoCmplResDTO) return null;
		
		 String groupbuyingResultKey = RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_RESULT + "_" + promotionId;
		 // 真实参团人数
		 String realActorCountStr = promotionCenterRedisDB.getHash(groupbuyingResultKey, RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_ACTOR_COUNT);
		 Integer realActorCount = Integer.valueOf(realActorCountStr);
   	     // 真实拼团价
		 String realGroupbuyingPriceStr = promotionCenterRedisDB.getHash(groupbuyingResultKey, RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_GROUPBUYINGPRICE);
   	     BigDecimal realGroupbuyingPrice = new BigDecimal(realGroupbuyingPriceStr);
   	     
   		// 开团开始时间
   		Date effectiveTime = groupbuyingInfoCmplResDTO.getSinglePromotionInfoCmplResDTO().getEffectiveTime();
   		// 开团结束时间
   		Date invalidTime = groupbuyingInfoCmplResDTO.getSinglePromotionInfoCmplResDTO().getInvalidTime();
   		// 下单开始时间
   		Date startTime = groupbuyingInfoCmplResDTO.getStartTime();
   		// 下单结束时间
   		Date endTime = groupbuyingInfoCmplResDTO.getEndTime();
        // 当前时间
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        // 活动状态 [1.未开始,2.开团进行中,3.下单未开始,4.下单进行中,5.已结束,-1.无效活动]
        String activeState = null;
        
        if(currentTime.getTime() < effectiveTime.getTime()){ //(当前时间 < 开团开始时间)  1.未开始
        	activeState = "1";
        }else if(currentTime.getTime() > endTime.getTime()){ //(当前时间 > 下单结束时间)  5.已结束
        	activeState = "5";
        }else if(currentTime.getTime() >= effectiveTime.getTime() && currentTime.getTime() <= invalidTime.getTime()){ //(当前时间 >= 开团开始时间 && 当前时间 <= 开团结束时间)  2.开团进行中
        	activeState = "2";
        }else if(currentTime.getTime() > invalidTime.getTime() && currentTime.getTime() < startTime.getTime()){ //(当前时间 > 开团结束时间 && 当前时间 < 下单开始时间)  3.下单未开始
        	activeState = "3";
        }else if(currentTime.getTime() >= startTime.getTime() && currentTime.getTime() <= endTime.getTime()){ //(当前时间 >= 下单开始时间 && 当前时间 <= 下单结束时间)  4.下单进行中
        	activeState = "4";
        }else{ // -1.无效活动
        	activeState = "-1";
        }
   	     
   	     groupbuyingInfoCmplResDTO.setRealActorCount(realActorCount);
   	     groupbuyingInfoCmplResDTO.setRealGroupbuyingPrice(realGroupbuyingPrice);
   	     groupbuyingInfoCmplResDTO.setActiveState(activeState);
		
		return groupbuyingInfoCmplResDTO;
    }
    
    
	/**
	 * 保存团购活动信息到redis
	 * @param groupbuyingInfoCmplResDTO
	 */
    public void setGroupbuyingInfoCmpl2Redis(GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO) {
        if (groupbuyingInfoCmplResDTO != null) {
        	
            String promotionId = groupbuyingInfoCmplResDTO.getPromotionId();
            String jsonObj = JSON.toJSONString(groupbuyingInfoCmplResDTO);
            
            String groupbuyingPriceSettingStr = JSON.toJSONString(groupbuyingInfoCmplResDTO.getGroupbuyingPriceSettingResDTOList());
            
            Map<String, String> resultMap = new HashMap<String, String>();
            String groupbuyingResultKey = RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_RESULT + "_" + promotionId;
            resultMap.put(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_ACTOR_COUNT,String.valueOf(groupbuyingInfoCmplResDTO.getRealActorCount()));
            resultMap.put(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_GROUPBUYINGPRICE,String.valueOf(groupbuyingInfoCmplResDTO.getRealGroupbuyingPrice()));
            resultMap.put(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_PRICESETTING, groupbuyingPriceSettingStr);
            
            // 设置团购活动
            promotionCenterRedisDB.setHash(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, promotionId, jsonObj);
            // 设置团购活动其他信息
            promotionCenterRedisDB.setHash(groupbuyingResultKey, resultMap);
        }
    }


    
    
    

	public PromotionCenterRedisDB getPromotionCenterRedisDB() {
		return promotionCenterRedisDB;
	}


}
