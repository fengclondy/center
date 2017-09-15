package cn.htd.promotion.cpc.biz.handle;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.service.impl.StockChangeImpl;
import cn.htd.promotion.cpc.common.constants.Constants;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.SeckillInfoReqDTO;

@Service("seckillReleaseImplHandle")
public class SeckillReleaseImplHandle extends StockChangeImpl {

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Override
	protected void changeStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) throws Exception {
		String promotionId = seckillInfoReqDTO.getPromotionId();
		String buyerCode = seckillInfoReqDTO.getBuyerCode();
		int count = seckillInfoReqDTO.getCount();
		if (this.checkSeckillOperateLegalOrNot(promotionId, buyerCode, Constants.SECKILL_RELEASE)) {
			String timeLimitedQueueKey = RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_QUEUE + "_" + promotionId;
			// 向该秒杀队列插入新的请求
			promotionRedisDB.rpush(timeLimitedQueueKey, promotionId);
			String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
			promotionRedisDB.incrHashBy(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_ACTOR_COUNT,
					-1);
			promotionRedisDB.incrHashBy(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_ACTOR_COUNT,
					-1);
			promotionRedisDB.incrHashBy(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_REMAIN_COUNT,
					count);
			// 保存秒杀操作日志
			this.setTimelimitedLog(seckillInfoReqDTO, Constants.SECKILL_RELEASE);
		}
	}

}
