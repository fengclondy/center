package cn.htd.promotion.cpc.biz.handle;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.constants.TimelimitedConstants;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;

import com.alibaba.fastjson.JSON;

@Service("promotionGroupbuyingRedisHandle")
public class PromotionGroupbuyingRedisHandle {

    protected static transient Logger logger = LoggerFactory.getLogger(PromotionGroupbuyingRedisHandle.class);
    
    @Resource
    private PromotionRedisDB promotionRedisDB;
    
    /**
     * 获取redis里的团购活动信息
     * @param promotionId
     * @return
     */
    public GroupbuyingInfoCmplResDTO getGroupbuyingInfoCmplByPromotionId(String promotionId){
    	
    	String groupbuyingInfoJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, promotionId);
    	if(null == groupbuyingInfoJsonStr || groupbuyingInfoJsonStr.length() == 0) return null;
    	GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = JSON.parseObject(groupbuyingInfoJsonStr, GroupbuyingInfoCmplResDTO.class);
		if(null == groupbuyingInfoCmplResDTO) return null;
		
		 String groupbuyingResultKey = RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_RESULT + "_" + promotionId;
		 // 真实参团人数
		 String realActorCountStr = promotionRedisDB.getHash(groupbuyingResultKey, RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_ACTOR_COUNT);
		 Integer realActorCount = Integer.valueOf(realActorCountStr);
   	     // 真实拼团价
		 String realGroupbuyingPriceStr = promotionRedisDB.getHash(groupbuyingResultKey, RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_GROUPBUYINGPRICE);
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
            promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, promotionId, jsonObj);
            // 设置团购活动其他信息
            promotionRedisDB.setHash(groupbuyingResultKey, resultMap);
        }
    }
    
    
    /**
     * 根据promotionId移除redis里的团购活动信息(redis的事物处理)
     * @param promotionId
     * @return
     */
    public boolean removeGroupbuyingInfoCmpl2Redis(final String promotionId){
    	
    	try {
    		return promotionRedisDB.getStringRedisTemplate().execute(new SessionCallback<Boolean>()  {
        	
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {

            		operations.multi();
            		
                    String groupbuyingResultKey = RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_RESULT + "_" + promotionId;
                    // 移除团购活动
                    operations.opsForHash().delete(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, promotionId);
                    // 移除团购活动其他信息
                    operations.delete(groupbuyingResultKey);
            		
                	operations.exec();

                return true;
                
            }
        });  
        
		} catch (Exception e) {
			logger.error("messageId{}:执行方法【removeGroupbuyingInfoCmpl2Redis】报错：{}", e.toString());
			return false;  
		}
    	
    }
    
    /**
     * 回滚修移除redis里的团购活动信息
     * @param promotionId
     * @param jsonObj
     * @param resultMap
     */
	public void rollbackRemoveGroupbuyingInfoCmpl2Redis(String promotionId,String groupbuyingResultKey,String jsonObj,Map<String, String> resultMap) {
        
        // 设置团购活动
        promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, promotionId, jsonObj);
        // 设置团购活动其他信息
        promotionRedisDB.setHash(groupbuyingResultKey, resultMap);
	}

    
    /**
     * 根据promotionId修改活动的启用状态(redis的事物处理)
     * @param promotionId
     * @param showStatus
     */
    public boolean upDownShelvesPromotionInfo2Redis(final String promotionId,final String showStatus) {
    	
    	try {
    		return promotionRedisDB.getStringRedisTemplate().execute(new SessionCallback<Boolean>()  {
	        	
				@SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
	            public Boolean execute(RedisOperations operations) throws DataAccessException {
	            
	            		operations.multi();
	            		
	            		String groupbuyingInfoJsonStr = String.valueOf(operations.opsForHash().get(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, promotionId));
	        			if(null != groupbuyingInfoJsonStr && groupbuyingInfoJsonStr.length() > 0){
	        				GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = JSON.parseObject(groupbuyingInfoJsonStr, GroupbuyingInfoCmplResDTO.class);
	        				if(null != groupbuyingInfoCmplResDTO){
	        					groupbuyingInfoCmplResDTO.getSinglePromotionInfoCmplResDTO().setShowStatus(showStatus);
	        					String jsonObj = JSON.toJSONString(groupbuyingInfoCmplResDTO);
	        					// 设置团购活动
	        					operations.opsForHash().put(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, promotionId, jsonObj);
	        				}
	        			}
	            		
	                	operations.exec();
	              
	               return true;  
	            }
	        	
	        });  
	        
		} catch (Exception e) {
			logger.error("messageId{}:执行方法【upDownShelvesPromotionInfo2Redis】报错：{}", e.toString());
			return false;  
		}
    }
    
    
    /**
     * 回滚修改活动的启用状态
     * @param promotionId
     * @param showStatus
     */
	public void rollbackUpDownShelvesPromotionInfo2Redis(String promotionId,String showStatus) {
		// 还原上一个上下架状态
		if (showStatus.equals(TimelimitedConstants.PromotionShowStatusEnum.VALID.key())) {
			showStatus = TimelimitedConstants.PromotionShowStatusEnum.INVALID.key();
		} else {
			showStatus = TimelimitedConstants.PromotionShowStatusEnum.VALID.key();
		}

		String groupbuyingInfoJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, promotionId);
		if (null != groupbuyingInfoJsonStr && groupbuyingInfoJsonStr.length() > 0) {
			GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = JSON.parseObject(groupbuyingInfoJsonStr,GroupbuyingInfoCmplResDTO.class);
			if (null != groupbuyingInfoCmplResDTO) {
				groupbuyingInfoCmplResDTO.getSinglePromotionInfoCmplResDTO().setShowStatus(showStatus);
				String jsonObj = JSON.toJSONString(groupbuyingInfoCmplResDTO);
				// 设置团购活动
				promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO,promotionId, jsonObj);
			}
		}
	}
    
    
	public boolean testHash(final String key, final Map<String, String> value) {

		try {
			return promotionRedisDB.getStringRedisTemplate().execute(new SessionCallback<Boolean>() {

						@SuppressWarnings({ "rawtypes", "unchecked" })
						@Override
						public Boolean execute(RedisOperations operations) throws DataAccessException {

							operations.multi();

							// String key = "user-zzf";
							// BoundValueOperations<String, String> oper =
							// operations.boundValueOps(key);
							// oper.set("zzf-6666");
							// int i = 1;i=i/0;
							// String key2 = "user-zzf-2";
							// BoundValueOperations<String, String> oper2 =
							// operations.boundValueOps(key2);
							// oper2.set("zzf2-6666");

							// operations.exec();

							// operations.opsForValue().set("user-zzf","zzfaa6666");
							// int i = 1;i=i/0;
							// operations.opsForValue().set("user-zzf-2","zzf2aa");

							operations.opsForValue().set("test-zzf-2", "zzfaa6666");
//							int i = 1; i = i / 0;
							operations.opsForHash().putAll(key, value);

							Object val = operations.exec();
							System.out.println("===>val:" + val);

							Map<String, String> redisMap = operations.opsForHash().entries(key);
							System.out.println("===>redisMap:" + redisMap);

							return true;
						}

					});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

    
    

	public PromotionRedisDB getPromotionRedisDB() {
		return promotionRedisDB;
	}
    
    
    



}
