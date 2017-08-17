package cn.htd.promotion.cpc.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * Redis操作辅助工具<br>
 * 
 * @author zhangding
 * @since 1.1.6
 */
public class RedisUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RedisUtil.class);

	@Autowired
	private JedisPool jedisPool;
	
	public static RedisUtil getInstance()
	{
		RedisUtil redisUtil = (RedisUtil)ApplicationUtil.getBean("redisUtil");
		return redisUtil;
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public  void returnResource(Jedis redis) {
		if (redis != null) {
			redis.close();
		}
	}

	public void set(final String key, final String value) {
		Jedis jedis = null;
		try {
			LOGGER.info("enter class RedisUtil and the method is SET---------->"
					+ key);
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis set 方法  异常 key="+key+" "+w.toString());
			// TODO Auto-generated catch block
		} finally {
			returnResource(jedis);
		}
	}

	public String get(final String key) {
		Jedis jedis = null;
		String returnValue = null;
		try {
			jedis = jedisPool.getResource();
			returnValue = jedis.get(key);			
			LOGGER.info("enter class RedisUtil and the method is GET---------->"
					+ returnValue);
		} catch (Exception e) {
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis get 方法  异常 key="+key+" "+w.toString());
			// TODO Auto-generated catch block
		} finally {
			if(null == returnValue){
				LOGGER.error("finally 查询redis 返回  null key====="+key);
			}
			returnResource(jedis);
		}
		return returnValue;
	}

	/*
	 * 自减
	 */
	public Long decr(final String key) {
		Jedis jedis = null;
		Long resultLong = 0L;
		try {
			jedis = jedisPool.getResource();
			resultLong = jedis.decr(key);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis decr 方法  异常 key="+key+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
		return resultLong;
	}
	
	/*
	 * 自减,传入多个值
	 */
	public void decrS(final String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String[] keyArry = key.split(",");
			for(int i=0;i<keyArry.length;i++){
				jedis.decr(keyArry[i]);				
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis decr 方法  异常 key="+key+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
	}

	/*
	 * 自增
	 */
	public Long incr(final String key) {
		Jedis jedis = null;
		Long resultLong = 0L;
		try {
			jedis = jedisPool.getResource();
			resultLong = jedis.incr(key);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis incr 方法  异常 key="+key+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
		return resultLong;
	}
	/*
	 * 传5等差数列自增 5，传-5等差数列 自减5
	 */
	public Long incrBy(final String key, final long integer){
		Jedis jedis = null;
		Long resultLong = 0L;
		try {
			jedis = jedisPool.getResource();
			resultLong = jedis.incrBy(key, integer);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis incr 方法  异常 key="+key+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
		return resultLong;
	}
	
	/*
	 * 自减  value 传1 自增 ，传-1 自减
	 */
	public Long hincrBy(final String key,final String field,final long value) {
		Jedis jedis = null;
		Long resultLong = 0L;
		try {
			jedis = jedisPool.getResource();
			resultLong = jedis.hincrBy(key, field, value);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis hincrBy 方法  异常 key="+key+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
		return resultLong;
	}

	public Map<String,String> getValueArry(final List<String> redisKeyList){
		Jedis jedis = null;
		Map<String,String> mapValue = new HashMap<String,String>();
		try {
			jedis = jedisPool.getResource();
			if(null != redisKeyList && redisKeyList.size()>0){
				for(int i=0;i<redisKeyList.size();i++){
					mapValue.put(redisKeyList.get(i), jedis.get(redisKeyList.get(i)));
				}
			}
			LOGGER.info("返回从redis里查询map:"+mapValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis getValueArry 方法  异常 key="+redisKeyList+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
		return mapValue;
	}
	
	public void hset(final String key,final String field, final String value) {
		Jedis jedis = null;
		try {
			LOGGER.info("enter class RedisUtil and the method is SET---------->"
					+ key);
			jedis = jedisPool.getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis hset 方法  异常 key="+key+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
	}
	
	public Map<String,String> hgets(final String key,final List<String> redisKeyList){
		Jedis jedis = null;
		Map<String,String> mapValue = new HashMap<String,String>();
		try {
			jedis = jedisPool.getResource();
			if(null != redisKeyList && redisKeyList.size()>0){
				for(int i=0;i<redisKeyList.size();i++){
					mapValue.put(redisKeyList.get(i), jedis.hget(key, redisKeyList.get(i)));
				}
			}
			LOGGER.info("返回从 redis hgets里查询map:"+mapValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis hgets 方法  异常 key="+key+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
		return mapValue;
	}
	
	public String hget(final String key,final String field) {
		Jedis jedis = null;
		String returnValue = null;
		try {
			jedis = jedisPool.getResource();
			returnValue = jedis.hget(key, field);
			LOGGER.info("enter class RedisUtil and the method is GET---------->"
					+ returnValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis hget 方法  异常 key="+key+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
		return returnValue;
	}

	public List<String> mget(final String... keys) {
		Jedis jedis = null;
		List<String> returnValue = null;
		try {
			jedis = jedisPool.getResource();
			returnValue = jedis.mget(keys);
			LOGGER.info("进入RedisUtil mget 方法---------->查询出的List keys为:"+keys+" value:"
					+ returnValue);
		} catch (Exception e) {
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis mget 方法  异常 keys="+keys+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
		return returnValue;
	}
	
	//从对列的左边取
	public String lpop(final String key) {
		Jedis jedis = null;
		String returnValue = null;
		try {
			jedis = jedisPool.getResource();
			returnValue = jedis.lpop(key);
			LOGGER.info("进入RedisUtil lpop 方法---------->查询出的 key为:"+key+" value:"+
				 returnValue);
		} catch (Exception e) {
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("redis lpop 方法  异常 key="+key+" "+w.toString());
		} finally {
			returnResource(jedis);
		}
		return returnValue;
	}
	
	    //从右边推进对列中
		public Long rpush(final String key, final String... strings) {
			Jedis jedis = null;
			Long returnValue = null;
			try {
				jedis = jedisPool.getResource();
				returnValue = jedis.rpush(key, strings);
				LOGGER.info("进入RedisUtil rpush 方法---------->查询出的 key为:"+key+" value:"
						+ returnValue);
			} catch (Exception e) {
				StringWriter w = new StringWriter();
			    e.printStackTrace(new PrintWriter(w));
				LOGGER.error("redis rpush 方法  异常 key="+key+" "+w.toString());
			} finally {
				returnResource(jedis);
			}
			return returnValue;
		}
		
		public Long del(final String key) {
			Jedis jedis = null;
			Long returnValue = null;
			try {
				jedis = jedisPool.getResource();
				returnValue = jedis.del(key);
				LOGGER.info("进入RedisUtil del 方法---------->查询出的 key为:"+key);
			} catch (Exception e) {
				StringWriter w = new StringWriter();
			    e.printStackTrace(new PrintWriter(w));
				LOGGER.error("redis del 方法  异常 key="+key+" "+w.toString());
			} finally {
				returnResource(jedis);
			}
			return returnValue;
		}
		
		public Long delAll(final String... key) {
			Jedis jedis = null;
			Long returnValue = null;
			try {
				jedis = jedisPool.getResource();
				returnValue = jedis.del(key);
				LOGGER.info("进入RedisUtil delAll 方法---------->查询出的 key为:"+key);
			} catch (Exception e) {
				StringWriter w = new StringWriter();
			    e.printStackTrace(new PrintWriter(w));
				LOGGER.error("redis del 方法  异常 key="+key+" "+w.toString());
			} finally {
				returnResource(jedis);
			}
			return returnValue;
		}
}