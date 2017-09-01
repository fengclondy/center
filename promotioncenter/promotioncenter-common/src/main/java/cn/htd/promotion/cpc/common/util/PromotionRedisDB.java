package cn.htd.promotion.cpc.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis工具类，现在只有简单的set，get，del方法
 */
@Component("promotionRedisDB")
public class PromotionRedisDB {

    private static final Logger logger = LoggerFactory.getLogger(PromotionRedisDB.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    /**
     * 判断redis中是否存在key
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-exists", "key=" + key);
        boolean isExists = false;
        try {
            isExists = stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-exists", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-exists", "isExist=" + isExists);
        }
        return isExists;
    }

    /**
     * 取得redis中key保存的value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-get", "key=" + key);
        String value = "";

        try {
            value = stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-get", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-get", "value=" + value);
        }
        return value;
    }

    /**
     * 根据key向redis中保存value
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-set", "key=" + key, "value=" + value);

        try {
            stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-set", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-set", "无");
        }
    }

    /**
     * 删除Redis中key的对象
     *
     * @param key
     */
    public void del(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-del", "key=" + key);

        try {
            if (exists(key)) {
                stringRedisTemplate.delete(key);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-del", ExceptionUtils.getStackTraceAsString(e));
        } finally {

            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-del", "无");
        }
    }

    /**
     * 向Redis中添加有效期到endTime的key的字符串
     *
     * @param key
     * @param value
     * @param endTime
     */
    public void setAndExpire(String key, String value, Date endTime) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}][{}]", "promotionRedisDB-setAndExpire", "key=" + key, "value=" + value,
                "endTime=" + endTime);
        long diffTime = endTime.getTime() - new Date().getTime();
        int seconds = (int) (diffTime / 1000);
        setAndExpire(key, value, seconds);
    }

    /**
     * 向Redis中添加有效期为seconds的key的字符串
     *
     * @param key
     * @param value
     * @param seconds
     */
    public void setAndExpire(String key, String value, int seconds) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}][{}]", "promotionRedisDB-setAndExpire", "key=" + key, "value=" + value,
                "seconds=" + seconds);

        try {
            set(key, value);
            expire(key, seconds);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-setAndExpire",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {

            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-setAndExpire", "无");
        }
    }

    /**
     * 向Redis中设定key对象的有效期为seconds秒
     *
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-expire", "key=" + key, "seconds=" + seconds);

        try {
            stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-expire", ExceptionUtils.getStackTraceAsString(e));
        } finally {

            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-expire", "无");
        }
    }

    /**
     * 向Redis中设定hash对象
     *
     * @param key
     * @param hValue
     */
    public void setHash(String key, Map<String, String> hValue) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-setHash", "key=" + key,
                "value=" + JSON.toJSONString(hValue));
        try {
            stringRedisTemplate.opsForHash().putAll(key, hValue);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-setHash", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-setHash", "无");
        }
    }

    /**
     * 向Redis中设定hash对象
     *
     * @param key
     * @param field
     * @param value
     */
    public void setHash(String key, String field, String value) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}][{}]", "promotionRedisDB-setHash", "key=" + key, "field=" + field,
                "value=" + value);

        try {
            stringRedisTemplate.opsForHash().put(key, field, value);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-setHash", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-setHash", "无");
        }
    }

    /**
     * 从Redis中设定hash对象
     *
     * @param key
     * @param field
     * @return
     */
    public String getHash(String key, String field) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-getHash", "key=" + key, "field=" + field);
        String value = null;

        try {
            value = (String) stringRedisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-getHash", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-getHash", "value=" + value);
        }
        return value;
    }

    /**
     * 从Redis中设定hash对象
     *
     * @param key
     * @param field
     * @return
     */
    public void delHash(String key, String field) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-delHash", "key=" + key, "field=" + field);

        try {
            stringRedisTemplate.opsForHash().delete(key, field);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-delHash", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，处理结束", "promotionRedisDB-delHash");
        }
    }

    /**
     * 从Redis中设定hash对象
     *
     * @param key
     * @param field
     * @return
     */
    public boolean existsHash(String key, String field) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-existsHash", "key=" + key, "field=" + field);
        boolean isExists = false;

        try {
            isExists = stringRedisTemplate.opsForHash().hasKey(key, field);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-existsHash", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-existsHash", "value=" + isExists);
        }
        return isExists;
    }

    /**
     * 根据Key取得redis中hash的所有field和value的字符串
     *
     * @param key
     * @return
     */
    public Map<String, String> getHashOperations(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-getHashOperations", "key=" + key);
        Map<Object, Object> tmpMap = null;
        Map<String, String> valueMap = new HashMap<String, String>();

        try {
            if (exists(key)) {
                tmpMap = stringRedisTemplate.opsForHash().entries(key);
                if (tmpMap != null && !tmpMap.isEmpty()) {
                    for (Map.Entry<Object, Object> entry : tmpMap.entrySet()) {
                        valueMap.put((String) entry.getKey(), (String) entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-getHashOperations",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-getHashOperations",
                    "value=" + JSON.toJSONString(valueMap));
        }
        return valueMap;
    }

    /**
     * 根据Key取得redis中hash的所有field和value的字符串
     *
     * @param key
     * @return
     */
    public List<String> getHashFields(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-getHashFields", "key=" + key);
        Set<Object> tmpSet = null;
        List<String> filedList = new ArrayList<String>();

        try {
            if (exists(key)) {
                tmpSet = stringRedisTemplate.opsForHash().keys(key);
                if (tmpSet != null && !tmpSet.isEmpty()) {
                    for (Object filed : tmpSet) {
                        filedList.add((String) filed);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-getHashFields",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-getHashFields",
                    "fields=" + JSON.toJSONString(filedList));
        }
        return filedList;
    }

    /**
     * 根据key设定redis中存储内容到指定时间
     *
     * @param key
     * @param endTime
     */
    public void setExpire(String key, Date endTime) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-setExpire", "key=" + key, "endTime=" + endTime);
        long diffTime = endTime.getTime() - new Date().getTime();
        int seconds = (int) (diffTime / 1000);
        expire(key, seconds);
    }

    /**
     * 在redis消息队列队尾插入数据
     *
     * @param key
     * @param value
     */
    public void tailPush(String key, String value) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-tailPush", "key=" + key, "value=" + value);

        try {
            stringRedisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-tailPush", ExceptionUtils.getStackTraceAsString(e));
        } finally {

            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-tailPush", "无");
        }
    }

    /**
     * 在redis消息队列对头插入数据
     *
     * @param key
     * @param value
     */
    public void headPush(String key, String value) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-headPush", "key=" + key, "value=" + value);

        try {
            stringRedisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-headPush", ExceptionUtils.getStackTraceAsString(e));
        } finally {

            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-headPush", "无");
        }
    }

    /**
     * 在redis消息队列队尾删除数据
     *
     * @param key
     * @return
     */
    public String tailPop(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-tailPop", "key=" + key);
        String value = "";

        try {
            if (exists(key)) {
                value = stringRedisTemplate.opsForList().rightPop(key);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-tailPop", ExceptionUtils.getStackTraceAsString(e));
        } finally {

            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-tailPop", "value=" + value);
        }
        return value;
    }

    /**
     * 在redis消息队列队头删除数据
     *
     * @param key
     * @return
     */
    public String headPop(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-headPop", "key=" + key);
        String value = "";

        try {
            if (exists(key)) {
                value = stringRedisTemplate.opsForList().leftPop(key);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-headPop", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-headPop", "value=" + value);
        }
        return value;
    }

    /**
     * 取得redis消息队列长度
     */
    public Long getLlen(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-getLlen", "key=" + key);
        Long length = 0L;

        try {
            length = stringRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-getLlen", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-getLlen", "length=" + length);
        }
        return length;
    }

    /**
     * 通过redis取得自增数据
     *
     * @param key
     * @return
     */
    public Long incr(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-incr", "key=" + key);
        Long returnValue = 0L;

        try {
            returnValue = incrBy(key, 1L);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-incr", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-incr", "returnValue=" + returnValue);
        }
        return returnValue;
    }

    /**
     * 通过redis取得自减数据
     *
     * @param key
     * @return
     */
    public Long decr(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-decr", "key=" + key);
        Long returnValue = 0L;

        try {
            returnValue = incrBy(key, -1);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-decr", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-decr", "returnValue=" + returnValue);
        }
        return returnValue;
    }

    /**
     * 通过redis取得增长increment的数据
     *
     * @param key
     * @param increment
     * @return
     */
    public Long incrBy(String key, long increment) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-incrBy", "key=" + key, "increment=" + increment);
        Long returnValue = 0L;

        try {
            returnValue = stringRedisTemplate.opsForValue().increment(key, increment);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-incrBy", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-incrBy", "returnValue=" + returnValue);
        }
        return returnValue;
    }

    /**
     * 通过redis取得减少increment的数据
     *
     * @param key
     * @param increment
     * @return
     */
    public Long decrBy(String key, long increment) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-decrBy", "key=" + key, "increment=" + increment);
        Long returnValue = 0L;

        try {
            returnValue = incrBy(key, -1 * increment);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-decrBy", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-decrBy", "returnValue=" + returnValue);
        }
        return returnValue;
    }

    /**
     * 通过redis取得自增数据
     *
     * @param key
     * @param field
     * @return
     */
    public Long incrHash(String key, String field) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-incrHash", "key=" + key, "field=" + field);
        return incrHashBy(key, field, 1);
    }
    /**
     * 通过redis取得自减数据
     *
     * @param key
     * @param field
     * @return
     */
    public Long decrHash(String key, String field) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-decrHash", "key=" + key, "field=" + field);
        return incrHashBy(key, field, -1);
    }

    /**
     * 通过redis取得增长increment的数据
     *
     * @param key
     * @param field
     * @param increment
     * @return
     */
    public Long incrHashBy(String key, String field, long increment) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}][{}]", "promotionRedisDB-incrHashBy", "key=" + key, "field=" + field,
                "increment=" + increment);
        Long returnValue = 0L;

        try {
            returnValue = stringRedisTemplate.opsForHash().increment(key, field, increment);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-incrHashBy", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-incrHashBy", "returnValue=" + returnValue);
        }
        return returnValue;
    }

    /**
     * 通过redis保存Set的数据
     *
     * @param key
     * @param value
     * @return
     */
    public Long addSet(String key, String value) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-addSet", "key=" + key, "value=" + value);
        Long returnValue = 0L;

        try {
            returnValue = stringRedisTemplate.opsForSet().add(key, value);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-addSet", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-addSet", "returnValue=" + returnValue);
        }
        return returnValue;
    }

    /**
     * 从redis的Set中随机抛出一个元素
     *
     * @param key
     * @return
     */
    public String popSet(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-popSet", "key=" + key);
        String returnValue = "";

        try {
            if (exists(key)) {
                returnValue = stringRedisTemplate.opsForSet().pop(key);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-popSet", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-popSet", "returnValue=" + returnValue);
        }
        return returnValue;
    }

    /**
     * 移除redis中的元素
     *
     * @param key
     * @param value
     */
    public Long removeSet(String key, String value) {
        logger.debug("\n 方法:[{}]，入参:[{}][{}]", "promotionRedisDB-removeSet", "key=" + key, "value=" + value);
        Long returnValue = 0L;

        try {
            returnValue = stringRedisTemplate.opsForSet().remove(key, value);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-removeSet", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-removeSet", "returnValue=" + returnValue);
        }
        return returnValue;
    }

    /**
     * 获取redis中set中的元素数量
     *
     * @param key
     * @return
     */
    public Long getSetLen(String key) {
        logger.debug("\n 方法:[{}]，入参:[{}]", "promotionRedisDB-getSetLen", "key=" + key);
        Long returnValue = 0L;

        try {
            returnValue = stringRedisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            logger.error("\n 方法:[{}]，异常:[{}]", "promotionRedisDB-getSetLen", ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.debug("\n 方法:[{}]，出参:[{}]", "promotionRedisDB-getSetLen", "returnValue=" + returnValue);
        }
        return returnValue;
    }
}
