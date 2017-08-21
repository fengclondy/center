package cn.htd.promotion.cpc.common.util;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by xh on 2017/8/18.
 */
@Service
public class RedisTemplateUtil {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RedisTemplateUtil.class);

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Key exists
     * @param key 主键
     * @return true:存在 false:不存在
     */
    public boolean exists(final String key){
        boolean returnValue = false;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil exists : key=%s", key));
            if(StringUtils.isBlank(key)){
                return false;
            }
            returnValue = redisTemplate.getConnectionFactory().getConnection().exists(key.getBytes());
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil exists is error : key=%s", key), e);
        }

        return returnValue;
    }

    /**
     * Key 失效时间
     * @param key 主键
     * @param timeout 失效时间,单位秒
     */
    public void expire(final String key, final int timeout) {
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil expire : key=%s", key));
            if(StringUtils.isBlank(key)){
                return;
            }
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil expire is error : key=%s", key), e);
        }
    }

    /***
     * Key 自减
     * @param key 主键
     * @return 自减后的值
     */
    public int decr(final String key) {
        Long returnValue = 0L;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil decr : key=%s", key));
            returnValue = redisTemplate.getConnectionFactory().getConnection().decr(key.getBytes());
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil decr is error : key=%s", key), e);
        }
        return returnValue.intValue();
    }

    /***
     * Key 多个key自减
     * @param key 主键
     */
    public void decrS(final String key) {
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil decrS : key=%s", key));
            if(StringUtils.isBlank(key)){
                return;
            }
            String[] keyArry = key.split(",");
            for(String keyS : keyArry){
                redisTemplate.getConnectionFactory().getConnection().decr(keyS.getBytes());
            }
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil decrS is error : key=%s", key), e);
        }
    }

    /***
     * Key 自增
     * @param key 主键
     * @return 自增后的值
     */
    public int incr(final String key){
        Long returnValue = 0L;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil incr : key=%s", key));
            returnValue = redisTemplate.getConnectionFactory().getConnection().incr(key.getBytes());
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil incr is error : key=%s", key), e);
        }
        return returnValue.intValue();
    }

    /***
     * Key 多个key自增
     * @param key 主键
     */
    public void incrS(final String key) {
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil incrS : key=%s", key));
            if(StringUtils.isBlank(key)){
                return;
            }
            String[] keyArry = key.split(",");
            for(String keyS : keyArry){
                redisTemplate.getConnectionFactory().getConnection().incr(keyS.getBytes());
            }
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil incrS is error : key=%s", key), e);
        }
    }

    /**
     * String set
     * @param key 主键
     * @param value 值 
     */
    public void set(final String key, final String value) {
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil set : key=%s", key));
            if(StringUtils.isBlank(value)){
                return;
            }
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil set is error : key=%s", key), e);
        }
    }

    /**
     * String set
     * by expire time
     * @param key 主键
     * @param value 值
     * @param timeout 失效时间,单位秒
     */
    public void setByExpire(final String key, final String value, final int timeout) {
        this.set(key, value);
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil setByExpire : key=%s", key));
            if(StringUtils.isBlank(value)){
                return;
            }
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil setByExpire is error : key=%s", key), e);
        }
    }

    /**
     * String setnx
     * @param key 主键
     * @param value 值
     */
    public void setnx(final String key, final String value) {
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil setnx : key=%s", key));
            if(StringUtils.isBlank(value)){
                return;
            }
            redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil setnx is error : key=%s", key), e);
        }
    }

    /**
     * String get
     * @param key 主键
     * @return 返回值
     */
    public String get(final String key) {
        String returnValue = null;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil get : key=%s", key));
            returnValue = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil get is error : key=%s", key), e);
        }
        return returnValue;
    }

    /**
     * Hash set
     * @param key 主键
     * @param field 成员
     * @param value 值
     */
    public void hset(final String key, final String field, final String value){
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil hset : key=%s", key));
            if(StringUtils.isBlank(key) || StringUtils.isBlank(field)){
                return;
            }
            redisTemplate.opsForHash().put(key, field, value);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil hset is error : key=%s", key), e);
        }
    }

    /**
     * Hash set
     * @param key 主键
     * @param map 集合
     */
    public void hset(final String key, final HashMap<Object, Object> map){
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil hset2 : key=%s", key));
            if(StringUtils.isBlank(key)){
                return;
            }
            if(MapUtils.isEmpty(map)){
                return;
            }
            redisTemplate.opsForHash().putAll(key, map);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil hset2 is error : key=%s", key), e);
        }
    }

    /**
     * Hash hget
     * @param key 主键
     * @param field 成员
     * @return 返回值
     */
    public String hget(final String key, final String field){
        String returnValue = null;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil hget : key=%s", key));
            if(StringUtils.isBlank(key) || StringUtils.isBlank(field)){
                return null;
            }
            Object obj = redisTemplate.opsForHash().get(key, field);
            if(obj == null){
                return null;
            }
            returnValue = String.valueOf(obj);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil hget is error : key=%s", key), e);
        }
        return returnValue;
    }

    /**
     * Hash hget
     * @param key 主键
     * @param fields 成员集合
     * @return 返回值集合
     */
    public List<?> hget(final String key, final List<Object> fields){
        List<?> returnValue = null;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil hget2 : key=%s", key));
            if(StringUtils.isBlank(key) || CollectionUtils.isEmpty(fields)){
                return null;
            }
            returnValue = redisTemplate.opsForHash().multiGet(key, fields);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil hget2 is error : key=%s", key), e);
        }
        return returnValue;
    }

    /**
     * Hash 获取所有的fields
     * @param key 主键
     * @return 返回成员集合
     */
    public Set<?> hfields(final String key){
        Set<?> returnValue = null;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil hfields : key=%s", key));
            if(StringUtils.isBlank(key)){
                return null;
            }
            returnValue = redisTemplate.opsForHash().keys(key);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil hfields is error : key=%s", key), e);
        }
        return returnValue;
    }

    /**
     * Hash 获取所有的value
     * @param key 主键
     * @return 返回值集合
     */
    public List<?> hvalues(final String key){
        List<?> returnValue = null;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil hvalues : key=%s", key));
            if(StringUtils.isBlank(key)){
                return null;
            }
            returnValue = redisTemplate.opsForHash().values(key);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil hvalues is error : key=%s", key), e);
        }
        return returnValue;
    }

    /**
     * Hash hdel
     * @param key 主键
     * @param filed 成员
     */
    public void hdel(final String key, final String filed){
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil hdel : key=%s", key));
            if(StringUtils.isBlank(key) || StringUtils.isBlank(filed)){
                return;
            }
            redisTemplate.opsForHash().delete(key, filed);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil hdel is error : key=%s", key), e);
        }
    }

    /**
     * Hash hdel
     * @param key 主键
     * @param fileds 成员集合
     */
    public void hdel(final String key, final List<Object> fileds){
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil hdel2 : key=%s", key));
            if(StringUtils.isBlank(key) || CollectionUtils.isEmpty(fileds)){
                return;
            }
            redisTemplate.opsForHash().delete(key, fileds);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil hdel2 is error : key=%s", key), e);
        }
    }

    /**
     * List lpush
     * 表头插入
     * @param key 主键
     * @param value 值
     */
    public void lpush(final String key, final String value){
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil lpush : key=%s", key));
            if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
                return;
            }
            redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil lpush is error : key=%s", key), e);
        }
    }

    /**
     * List lpush
     * 表头插入
     * @param key 主键
     * @param values 值集合
     */
    public void lpush(final String key, final List<String> values){
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil lpush2 : key=%s", key));
            if(StringUtils.isBlank(key) || CollectionUtils.isEmpty(values)){
                return;
            }
            redisTemplate.opsForList().leftPushAll(key, values);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil lpush2 is error : key=%s", key), e);
        }
    }

    /**
     * List lpop
     * 表头删除
     * @param key 主键
     */
    public String lpop(final String key){
        String returnValue = null;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil lpop : key=%s", key));
            if(StringUtils.isBlank(key)){
                return null;
            }
            returnValue = redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil lpop is error : key=%s", key), e);
        }
        return returnValue;
    }

    /**
     * List rpop
     * 表尾删除
     * @param key 主键
     * @return 删除的元素
     */
    public String rpop(final String key){
        String returnValue = null;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil rpop : key=%s", key));
            if(StringUtils.isBlank(key)){
                return null;
            }
            returnValue = redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil rpop is error : key=%s", key), e);
        }
        return returnValue;
    }

    /**
     * List llen
     * @param key 主键
     * @return 长度
     */
    public int llen(final String key){
        Long returnValue = 0L;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil rpop : key=%s", key));
            if(StringUtils.isBlank(key)){
                return 0;
            }
            returnValue = redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil rpop is error : key=%s", key), e);
        }
        return returnValue.intValue();
    }

    /**
     * Set sadd
     * @param key 主键
     * @param value 值
     * @return 0:重复数据 >0:不重复数据
     */
    public int sadd(final String key, final String value){
        Long returnValue = 0L;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil sadd : key=%s", key));
            if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
                return 0;
            }
            returnValue = redisTemplate.opsForSet().add(key, value);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil sadd is error : key=%s", key), e);
        }
        return returnValue.intValue();
    }

    /**
     * Set scard
     * @param key 主键 主键
     * @return 返回集合的长度
     */
    public int scard(final String key){
        Long returnValue = 0L;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil scard : key=%s", key));
            if(StringUtils.isBlank(key)){
                return 0;
            }
            returnValue = redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil scard is error : key=%s", key), e);
        }
        return returnValue.intValue();
    }

    /**
     * Set srem
     * @param key 主键
     * @param value 值
     * @return 0:不存在元素 >0:移除一个或多个元素
     */
    public int srem(final String key, final String value){
        Long returnValue = 0L;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil srem : key=%s", key));
            if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
                return 9;
            }
            returnValue = redisTemplate.opsForSet().remove(key, value);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil srem is error : key=%s", key), e);
        }
        return returnValue.intValue();
    }

    /**
     * Set srem
     * @param key 主键
     * @param values 值集合
     * @return 0:不存在元素 >0:移除一个或多个元素
     */
    public int srem(final String key, final List<?> values){
        Long returnValue = 0L;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil srem2 : key=%s", key));
            if(StringUtils.isBlank(key) || CollectionUtils.isEmpty(values)){
                return 0;
            }
            returnValue = redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil srem2 is error : key=%s", key), e);
        }
        return returnValue.intValue();
    }

    /**
     * Set smembers
     * @param key 主键
     * @return 集合
     */
    public Set<?> smembers(final String key){
        Set<?> returnValue = null;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil smembers : key=%s", key));
            if(StringUtils.isBlank(key)){
                return null;
            }
            returnValue = redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil smembers is error : key=%s", key), e);
        }
        return returnValue;
    }

    /**
     * Set sismember
     * 判断是否为set的成员
     * @param key 主键
     * @param value 值
     * @return 0:不存在或者key不存在 1:存在
     */
    public boolean sismember(final String key, final String value){
        boolean returnValue = false;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil sismember : key=%s", key));
            if(StringUtils.isBlank(key)){
                return false;
            }
            returnValue = redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil sismember is error : key=%s", key), e);
        }
        return returnValue;
    }

    /**
     * SortedSet zadd
     * @param key 主键
     * @param score 分数
     * @param value 值 member
     */
    public void zadd(final String key, final double score, final String value){
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil zadd : key=%s", key));
            if(StringUtils.isBlank(key)){
                return;
            }
            redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil zadd is error : key=%s", key), e);
        }
    }

    /**
     * SortedSet zcard
     * @param key 主键
     * @return 0:不存在或者key不存在 >0:有序集合长度
     */
    public int zcard(final String key){
        Long returnValue = 0L;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil zcard : key=%s", key));
            if(StringUtils.isBlank(key)){
                return 0;
            }
            returnValue = redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil zcard is error : key=%s", key), e);
        }
        return returnValue.intValue();
    }

    /**
     * SortedSet zcount
     * @param key 主键
     * @param min 最小分数
     * @param max 最大分数
     * @return min和max之间的元素的个数
     */
    public int zcount(final String key, final double min, final double max){
        Long returnValue = 0L;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil zcount : key=%s", key));
            if(StringUtils.isBlank(key)){
                return 0;
            }
            returnValue = redisTemplate.opsForZSet().count(key, min, max);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil zcount is error : key=%s", key), e);
        }
        return returnValue.intValue();
    }

    /**
     * SortedSet zcount
     * @param key 主键
     * @param min 最小分数
     * @param max 最大分数
     * @return min和max之间的元素集合
     */
    public Set<?> zrange(final String key, final double min, final double max){
        Set<?> returnValue = null;
        try {
            LOGGER.info(String.format("------ RedisTemplateUtil zrange : key=%s", key));
            if(StringUtils.isBlank(key)){
                return null;
            }
            returnValue = redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception e) {
            LOGGER.error(String.format("------ RedisTemplateUtil zrange is error : key=%s", key), e);
        }
        return returnValue;
    }
}