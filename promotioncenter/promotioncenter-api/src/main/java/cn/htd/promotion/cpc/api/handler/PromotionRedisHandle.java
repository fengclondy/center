package cn.htd.promotion.cpc.api.handler;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;

@Service("cpcPromotionRedisHandle")
public class PromotionRedisHandle {

    protected static transient Logger logger = LoggerFactory.getLogger(PromotionRedisHandle.class);
    
    @Resource
    private PromotionRedisDB promotionRedisDB;

	/**
	 * 秒杀 - 根据key解锁促销活动根据key
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
    public void unlockRedisPromotionAction(String key) {
        if (!StringUtils.isEmpty(key)) {
        	promotionRedisDB.delHash(RedisConst.PROMOTION_REDIS_PROMOTION_ACTION_HASH, key);
        }
    }


	/**
	 * 秒杀 - 根据key锁定促销活动，同一个活动一次只能处理一次
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
    public boolean lockRedisPromotionAction(String messageId, String key) {
        String tmpStr = "";
        boolean returnFlag = true;
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        if (!promotionRedisDB.existsHash(RedisConst.PROMOTION_REDIS_PROMOTION_ACTION_HASH, key)) {
        	promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_PROMOTION_ACTION_HASH, key, messageId);
        } else {
            tmpStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_PROMOTION_ACTION_HASH, key);
            if (!messageId.equals(tmpStr)) {
                returnFlag = false;
            }
        }
        return returnFlag;
    }

	/**
	 * 秒杀 - 根据key集合解锁促销活动根据key
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
    public void unlockRedisPromotionAction(List<String> keyList) {
        if (keyList != null && !keyList.isEmpty()) {
            for (String key : keyList) {
                unlockRedisPromotionAction(key);
            }
        }
    }
}
