package cn.htd.marketcenter.service.handle;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service("promotionRedisHandle")
public class PromotionRedisHandle {

    protected static transient Logger logger = LoggerFactory.getLogger(PromotionRedisHandle.class);

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    /**
     * 回滚商城操作日志
     *
     * @param key
     */
    public void unlockRedisPromotionAction(String key) {
        if (!StringUtils.isEmpty(key)) {
            marketRedisDB.delHash(RedisConst.REDIS_PROMOTION_ACTION_HASH, key);
        }
    }

    /**
     * 保存商城操作日志
     *
     * @param messageId
     * @param key
     * @return
     */
    public boolean lockRedisPromotionAction(String messageId, String key) {

        String tmpStr = "";
        boolean returnFlag = true;
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        if (!marketRedisDB.existsHash(RedisConst.REDIS_PROMOTION_ACTION_HASH, key)) {
            marketRedisDB.setHash(RedisConst.REDIS_PROMOTION_ACTION_HASH, key, messageId);
        } else {
            tmpStr = marketRedisDB.getHash(RedisConst.REDIS_PROMOTION_ACTION_HASH, key);
            if (!messageId.equals(tmpStr)) {
                returnFlag = false;
            }
        }
        return returnFlag;
    }

    /**
     * 回滚商城操作日志
     *
     * @param keyList
     */
    public void unlockRedisPromotionAction(List<String> keyList) {
        if (keyList != null && !keyList.isEmpty()) {
            for (String key : keyList) {
                unlockRedisPromotionAction(key);
            }
        }
    }
}
