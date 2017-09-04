package cn.htd.promotion.cpc.biz.handle;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.promotion.cpc.biz.service.impl.StockChangeImpl;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;

public class SeckillReserveImplHandle extends StockChangeImpl {

	/**
	 * 日志
	 */
	protected Logger logger = LoggerFactory.getLogger(SeckillReserveImplHandle.class);

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Override
	protected void changeStock(String messageId, String promotionId, String buyerCode) throws Exception {
		String reserveHashKey = RedisConst.PROMOTION_REIDS_BUYER_TIMELIMITED_RESERVE_HASH + "_" + promotionId;
		String reserveResult = promotionRedisDB.getHash(reserveHashKey, buyerCode);
		if (StringUtils.isBlank(reserveResult)) {
			String timeLimitedQueueKey = RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_QUEUE + "_" + promotionId;
			// 获取该秒杀活动库存锁定队列的值
			String result = promotionRedisDB.lpop(timeLimitedQueueKey);
			// 如果队列取不到值说明秒杀库存已经被抢完
			if (StringUtils.isBlank(result)) {
				throw new PromotionCenterBusinessException(PromotionCenterConst.TIMELIMITED_BUYER_NO_COUNT, "秒杀商品已抢光");
			}
			String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
			promotionRedisDB.incrHash(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_ACTOR_COUNT);
			promotionRedisDB.incrHash(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_ACTOR_COUNT);
		}
	}

}
