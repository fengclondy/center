package cn.htd.promotion.cpc.biz.handle;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.biz.service.impl.StockChangeImpl;
import cn.htd.promotion.cpc.common.constants.Constants;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.SeckillInfoReqDTO;

@Service("seckillReserveImplHandle")
public class SeckillReserveImplHandle extends StockChangeImpl {

	@Resource
	private GeneratorUtils noGenerator;

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Override
	protected void changeStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) throws Exception {
		String promotionId = seckillInfoReqDTO.getPromotionId();
		String buyerCode = seckillInfoReqDTO.getBuyerCode();
		int count = seckillInfoReqDTO.getCount();
		String reserveHashKey = RedisConst.PROMOTION_REIDS_BUYER_TIMELIMITED_RESERVE_HASH + "_" + promotionId;
		String reserveResult = promotionRedisDB.getHash(reserveHashKey, buyerCode);
		if (StringUtils.isBlank(reserveResult)
				&& this.checkSeckillOperateLegalOrNot(promotionId, buyerCode, Constants.SECKILL_RESERVE)) {
			String timeLimitedQueueKey = RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_QUEUE + "_" + promotionId;
			// 获取该秒杀活动库存锁定队列的值
			String result = promotionRedisDB.lpop(timeLimitedQueueKey);
			// 如果队列取不到值说明秒杀库存已经被抢完
			if (StringUtils.isBlank(result)) {
				throw new PromotionCenterBusinessException(PromotionCenterConst.TIMELIMITED_BUYER_NO_COUNT, "秒杀商品已抢光");
			}
			// 设置该买家抢到秒杀资格标志
			promotionRedisDB.setHash(reserveHashKey, buyerCode, Constants.SECKILL_RESERVE);
			String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
			promotionRedisDB.incrHash(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_ACTOR_COUNT);
			promotionRedisDB.incrHash(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_ACTOR_COUNT);
			promotionRedisDB.incrHashBy(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_REMAIN_COUNT,
					count * -1);
			String lockNo = noGenerator.generateSeckillLockNo(Constants.ORDER_PREHOLDING_NUMBER);
			seckillInfoReqDTO.setSeckillLockNo(lockNo);
			// 保存秒杀操作日志
			this.setTimelimitedLog(seckillInfoReqDTO, Constants.SECKILL_RESERVE);
		} else if (StringUtils.isNotBlank(reserveResult)) {
			String useLogRedisKey = buyerCode + "&" + promotionId;
			String useLogJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG,
					useLogRedisKey);
			BuyerUseTimelimitedLogDMO timelimitedLog = JSON.parseObject(useLogJsonStr, BuyerUseTimelimitedLogDMO.class);
			if ((StringUtils.isNotBlank(reserveResult)
					&& timelimitedLog.getUseType().equals(Constants.SECKILL_REDUCE))) {
				throw new PromotionCenterBusinessException(PromotionCenterConst.BUYER_HAS_TIMELIMITED_ERROR,
						"您已参加该秒杀活动不能再次秒杀");
			}
		} else {
			throw new PromotionCenterBusinessException(PromotionCenterConst.BUYER_HAS_TIMELIMITED_ERROR,
					"您已存在未支付秒杀订单，不能继续参与秒杀，请支付待支付订单");
		}
	}

}
