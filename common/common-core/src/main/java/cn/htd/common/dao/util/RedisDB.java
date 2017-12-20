package cn.htd.common.dao.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;

/**
 * redis工具类，现在只有简单的set，get，del方法
 */
@Component("redisDB")
public class RedisDB {

	private static final Logger logger = LoggerFactory.getLogger(RedisDB.class);

	@Resource
	private JedisPool jedisPool;

	/**
	 * 取得jedis资源
	 * 
	 * @return
	 */
	private Jedis getResource() {
		return jedisPool.getResource();
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	private void releaseResource(Jedis jedis) {
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
		logger.debug("\n 方法:[{}]，入参:[{}]", "redisDB-exists", "key=" + key);
		boolean isExists = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			isExists = jedis.exists(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-exists", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-exists", "isExist=" + isExists);
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
		logger.debug("\n 方法:[{}]，入参:[{}]", "redisDB-get", "key=" + key);
		String value = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-get", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-get", "value=" + value);
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-set", "key=" + key, "value=" + value);
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-set", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-set", "无");
		}
	}

	/**
	 * 删除Redis中key的对象
	 * 
	 * @param key
	 */
	public void del(String key) {
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-del", "key=" + key);
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.del(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-del", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-del", "无");
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}][{}]", "redisDB-setAndExpire", "key=" + key, "value=" + value,
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}][{}]", "redisDB-setAndExpire", "key=" + key, "value=" + value,
				"seconds=" + seconds);
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.set(key, value);
			jedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-setAndExpire", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-setAndExpire", "无");
		}
	}

	/**
	 * 向Redis中设定key对象的有效期为seconds秒
	 * 
	 * @param key
	 * @param seconds
	 */
	public void expire(String key, int seconds) {
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-expire", "key=" + key, "seconds=" + seconds);
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-expire", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-expire", "无");
		}
	}

	/**
	 * 向Redis中设定hash对象
	 * 
	 * @param key
	 * @param hValue
	 */
	public void setHash(String key, Map<String, String> hValue) {
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-setHash", "key=" + key, "value=" + JSON.toJSONString(hValue));
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hmset(key, hValue);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-setHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-setHash", "无");
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}][{}]", "redisDB-setHash", "key=" + key, "field=" + field, "value=" + value);
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-setHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-setHash", "无");
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-getHash", "key=" + key, "field=" + field);
		String value = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.hexists(key, field)) {
				value = jedis.hget(key, field);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-getHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-getHash", "value=" + value);
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
	public String delHash(String key, String field) {
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-delHash", "key=" + key, "field=" + field);
		String value = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hdel(key, field);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-delHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-delHash", "value=" + value);
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
	public boolean existsHash(String key, String field) {
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-existsHash", "key=" + key, "field=" + field);
		boolean isExists = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			isExists = jedis.hexists(key, field);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-existsHash", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-existsHash", "value=" + isExists);
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
		logger.debug("\n 方法:[{}]，入参:[{}]", "redisDB-getHashOperations", "key=" + key);
		Map<String, String> valueMap = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				valueMap = jedis.hgetAll(key);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-getHashOperations", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-getHashOperations", "value=" + JSON.toJSONString(valueMap));
		}
		return valueMap;
	}

	/**
	 * 根据key设定redis中存储内容到指定时间
	 * 
	 * @param key
	 * @param endTime
	 */
	public void setExpire(String key, Date endTime) {
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-setExpire", "key=" + key, "endTime=" + endTime);
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-tailPush", "key=" + key, "value=" + value);
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.rpush(key, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-tailPush", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-tailPush", "无");
		}
	}

	/**
	 * 在redis消息队列对头插入数据
	 * 
	 * @param key
	 * @param value
	 */
	public void headPush(String key, String value) {
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-headPush", "key=" + key, "value=" + value);
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.lpush(key, value);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-headPush", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-headPush", "无");
		}
	}

	/**
	 * 在redis消息队列队尾删除数据
	 * 
	 * @param key
	 * @return
	 */
	public String tailPop(String key) {
		logger.debug("\n 方法:[{}]，入参:[{}]", "redisDB-tailPop", "key=" + key);
		String value = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.rpop(key);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-tailPop", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-tailPop", "value=" + value);
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
		logger.debug("\n 方法:[{}]，入参:[{}]", "redisDB-headPop", "key=" + key);
		String value = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.lpop(key);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-headPop", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-headPop", "value=" + value);
		}
		return value;
	}

	/**
	 * 取得redis消息队列长度
	 */
	public Long getLlen(String key) {
		logger.debug("\n 方法:[{}]，入参:[{}]", "redisDB-getLlen", "key=" + key);
		Long length = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			length = jedis.llen(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-getLlen", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-getLlen", "length=" + length);
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
		logger.debug("\n 方法:[{}]，入参:[{}]", "redisDB-incr", "key=" + key);
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.incr(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-incr", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-incr", "returnValue=" + returnValue);
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
		logger.debug("\n 方法:[{}]，入参:[{}]", "redisDB-decr", "key=" + key);
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.decr(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-decr", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-decr", "returnValue=" + returnValue);
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-incrBy", "key=" + key, "increment=" + increment);
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.incrBy(key, increment);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-incrBy", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-incrBy", "returnValue=" + returnValue);
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-decrBy", "key=" + key, "increment=" + increment);
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.decrBy(key, increment);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-decrBy", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-decrBy", "returnValue=" + returnValue);
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}]", "redisDB-incrHash", "key=" + key, "field=" + field);
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
		logger.debug("\n 方法:[{}]，入参:[{}][{}][{}]", "redisDB-incrHashBy", "key=" + key, "field=" + field,
				"increment=" + increment);
		Long returnValue = 0L;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.hincrBy(key, field, increment);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-incrHashBy", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-incrHashBy", "returnValue=" + returnValue);
		}
		return returnValue;
	}
	
	public Set<String> getAllHKeys(String key){
		logger.debug("\n 方法:[{}]，入参:[{}][{}][{}]", "redisDB-incrHashBy", "key=" + key);
		Set<String> returnValue = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			returnValue = jedis.hkeys(key);
		} catch (Exception e) {
			logger.error("\n 方法:[{}]，异常:[{}]", "redisDB-getAllHKeys", getStackTraceAsString(e));
		} finally {
			releaseResource(jedis);
			logger.debug("\n 方法:[{}]，出参:[{}]", "redisDB-getAllHKeys", "returnValue=" + returnValue);
		}
		return returnValue;
	}
}
