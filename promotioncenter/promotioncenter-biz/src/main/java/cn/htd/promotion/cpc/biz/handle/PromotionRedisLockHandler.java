package cn.htd.promotion.cpc.biz.handle;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service("promotionRedisLockHandler")
public class PromotionRedisLockHandler {

	private static final Logger logger = LoggerFactory.getLogger(PromotionRedisLockHandler.class);

	// 持有锁的最长时间 [单位:秒 s]
	private static final int DEFAULT_EXPIRE_TIME = 3;
	// 获取不到锁的休眠时间 [单位:毫秒 ms]
	private static final long DEFAULT_SLEEP_TIME = 100;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 获取锁【如果锁获取成功，立即返回true；否则返回false】
	 * @param key  锁主键
	 * @return
	 */
	public boolean tryLock(String key) {
		return tryLock(key, 0L);
	}

	/**
	 * 尝试在等待时间内获取锁【如果锁获取成功，立即返回true；否则返回false】
	 * @param key  锁主键
	 * @param timeout  等待超时时间 [单位：毫秒ms]
	 * @return
	 */
	public boolean tryLock(String key, long timeout) {

		try {
			long nano = System.nanoTime();
			do {
				boolean isNoAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key, key);
				if (isNoAbsent) {
					stringRedisTemplate.expire(key, DEFAULT_EXPIRE_TIME,TimeUnit.SECONDS);
					return Boolean.TRUE;
				} else { // 存在锁
					if (logger.isDebugEnabled()) {
						String desc = stringRedisTemplate.opsForValue().get(key);
						logger.debug("key: " + key+ " locked by another business：" + desc);
					}
				}
				
				Thread.sleep(DEFAULT_SLEEP_TIME);
			} while ((System.nanoTime() - nano) < TimeUnit.MILLISECONDS.toNanos(timeout));
			
		} catch (Exception e) {
			logger.error("执行方法【tryLock】报错：{}", e.toString());
		}
		return Boolean.FALSE;
	}

	/**
	 * 获取锁，获取失败，一直等待【如果锁获取成功，立即返回true；否则返回false】
	 * @param key 锁主键
	 */
	public void lock(String key) {
		try {
			while (true) {
				boolean isNoAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key, key);
				if (isNoAbsent) {
					stringRedisTemplate.expire(key, DEFAULT_EXPIRE_TIME,TimeUnit.SECONDS);
					break;
				} else {
					if (logger.isDebugEnabled()) {
						String desc = stringRedisTemplate.opsForValue().get(key);
						logger.debug("key: " + key + " locked by another business：" + desc);
					}
				}
				Thread.sleep(DEFAULT_SLEEP_TIME);
			}
		} catch (Exception e) {
			logger.error("执行方法【lock】报错：{}", e.toString());
		}
	}

	/**
	 * 释放锁
	 * @param key 锁主键
	 */
	public void unLock(String key) {
		try {
			boolean isExists = stringRedisTemplate.hasKey(key);
			if (isExists) {
				stringRedisTemplate.delete(key);
				logger.debug("release lock, key :" + key);
			} else {
				logger.debug("lock is not exists, key :" + key);
			}
		} catch (Exception e) {
			logger.error("执行方法【unLock】报错：{}", e.toString());
		}
	}

}
