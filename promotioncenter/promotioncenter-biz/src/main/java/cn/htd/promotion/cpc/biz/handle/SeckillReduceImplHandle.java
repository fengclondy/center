package cn.htd.promotion.cpc.biz.handle;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.biz.service.impl.StockChangeImpl;
import cn.htd.promotion.cpc.common.constants.Constants;
import cn.htd.promotion.cpc.common.util.RedissonClientUtil;

@Service("seckillReduceImpl")
public class SeckillReduceImplHandle extends StockChangeImpl {

	@Autowired
	private RedissonClientUtil redissonClientUtil;

	@Override
	protected void changeStock(String messageId, String promotionId, String buyerCode) throws Exception {
		RLock rLock = null;
		BuyerUseTimelimitedLogDMO redisUseLog = null;
		RedissonClient redissonClient = redissonClientUtil.getInstance();
		String lockKey = Constants.REDIS_KEY_PREFIX_STOCK + String.valueOf(promotionId); // 竞争资源标志
		rLock = redissonClient.getLock(lockKey);
		/** 上锁 **/
		rLock.lock();
	}

}
