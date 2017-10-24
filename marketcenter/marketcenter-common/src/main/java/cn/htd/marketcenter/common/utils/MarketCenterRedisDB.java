package cn.htd.marketcenter.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis工具类，现在只有简单的set，get，del方法
 */
@Component("marketRedisDB")
public class MarketCenterRedisDB {

	private static final Logger logger = LoggerFactory.getLogger(MarketCenterRedisDB.class);

	@Resource(name = "marketJedisPool")
	private JedisPool marketJedisPool;

	/**
	 * 取得jedis资源
	 * 
	 * @return
	 */
	public Jedis getResource() {
		return marketJedisPool.getResource();
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public void releaseResource(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	private String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断redis中是否存在key
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		boolean isExists = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			isExists = jedis.exists(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-exists", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		String value = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-get", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-set", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 删除Redis中key的对象
	 * 
	 * @param key
	 */
	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.del(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-del", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.set(key, value);
			jedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-setAndExpire", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 向Redis中设定key对象的有效期为seconds秒
	 * 
	 * @param key
	 * @param seconds
	 */
	public void expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-expire", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 向Redis中设定hash对象
	 * 
	 * @param key
	 * @param hValue
	 */
	public void setHash(String key, Map<String, String> hValue) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hmset(key, hValue);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-setHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-setHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 向Redis中设定hash对象
	 *
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long setHashNx(String key, String field, String value) {
		Jedis jedis = null;
		Long ret = 0L;
		try {
			jedis = getResource();
			ret = jedis.hsetnx(key, field, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-setHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return ret;
	}

	/**
	 * 从Redis中设定hash对象
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String getHash(String key, String field) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.hexists(key, field)) {
				value = jedis.hget(key, field);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-getHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return value;
	}

	/**
	 * 从Redis中设定hash对象
	 *
	 * @param key
	 * @param fields
	 * @return
	 */
	public List<String> getMHash(String key, String[] fields) {
		List<String> valueList = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				valueList = jedis.hmget(key, fields);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-getMHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return valueList;
	}
	/**
	 * 从Redis中设定hash对象
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public void delHash(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hdel(key, field);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-delHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		boolean isExists = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			isExists = jedis.hexists(key, field);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-existsHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		Map<String, String> valueMap = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				valueMap = jedis.hgetAll(key);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-getHashOperations", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return valueMap;
	}

	/**
	 * 根据Key取得redis中hash的所有field和value的字符串
	 *
	 * @param key
	 * @return
	 */
	public Set<String> getHashFields(String key) {
		Set<String> fieldSet = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				fieldSet = jedis.hkeys(key);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-getHashFields", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return fieldSet;
	}

	/**
	 * 根据key设定redis中存储内容到指定时间
	 * 
	 * @param key
	 * @param endTime
	 */
	public void setExpire(String key, Date endTime) {
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
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.rpush(key, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-tailPush", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 在redis消息队列对头插入数据
	 * 
	 * @param key
	 * @param value
	 */
	public void headPush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.lpush(key, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-headPush", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 在redis消息队列队尾删除数据
	 * 
	 * @param key
	 * @return
	 */
	public String tailPop(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.rpop(key);
				if (!StringUtils.isEmpty(value) && "nil".equals(value)) {
					return null;
				}
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-tailPop", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.lpop(key);
				if (!StringUtils.isEmpty(value) && "nil".equals(value)) {
					return null;
				}
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-headPop", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return value;
	}

	/**
	 * 取得redis消息队列长度
	 */
	public Long getLlen(String key) {
		Long length = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			length = jedis.llen(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-getLlen", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.incr(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-incr", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.decr(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-decr", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.incrBy(key, increment);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-incrBy", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.decrBy(key, increment);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-decrBy", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		return incrHashBy(key, field, 1);
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
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.hincrBy(key, field, increment);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-incrHashBy", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
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
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.sadd(key, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-addSet", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return returnValue;
	}

	/**
	 * 从redis的Set中随机抛出一个元素
	 * @param key
	 * @return
	 */
	public String popSet(String key) {
		String returnValue = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				returnValue = jedis.spop(key);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-popSet", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return returnValue;
	}

	/**
	 * 移除redis中的元素
	 * @param key
	 * @param value
	 */
	public Long removeSet(String key, String value) {
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.srem(key, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-removeSet", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return returnValue;
	}

	/**
	 * 获取redis中set中的元素数量
	 * @param key
	 * @return
	 */
	public Long getSetLen(String key) {
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.scard(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-getSetLen", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
		}
		return returnValue;
	}

	/**
	 * 获取Value是否是redis的set中的元素
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean isSetMember(String key, String value) {
		Boolean isMember = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			isMember = jedis.sismember(key, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "marketRedisDB-isSetMember", ExceptionUtils.getStackTraceAsString(e));
		} finally {
		    releaseResource(jedis);
		}
		return isMember.booleanValue();
	}
}
