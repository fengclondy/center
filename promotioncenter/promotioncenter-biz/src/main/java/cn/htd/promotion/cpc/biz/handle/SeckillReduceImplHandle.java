package cn.htd.promotion.cpc.biz.handle;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.service.impl.StockChangeImpl;
import cn.htd.promotion.cpc.common.constants.Constants;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.SeckillInfoReqDTO;

@Service("seckillReduceImplHandle")
public class SeckillReduceImplHandle extends StockChangeImpl {

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Override
	protected void changeStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) throws Exception {
		String promotionId = seckillInfoReqDTO.getPromotionId();
		String buyerCode = seckillInfoReqDTO.getBuyerCode();
		int count = seckillInfoReqDTO.getCount();
		String reserveHashKey = RedisConst.PROMOTION_REIDS_BUYER_TIMELIMITED_RESERVE_HASH + "_" + promotionId;
		String reserveResult = promotionRedisDB.getHash(reserveHashKey, buyerCode);
		if (StringUtils.isNotBlank(reserveResult)
				&& this.checkSeckillOperateLegalOrNot(promotionId, buyerCode, Constants.SECKILL_REDUCE)) {
			String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
			promotionRedisDB.incrHashBy(RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_REMAIN_COUNT, timelimitedResultKey,
					count * -1);
			// 删除锁定记录
			promotionRedisDB.delHash(reserveHashKey, buyerCode);
			// 保存秒杀操作日志
			this.setTimelimitedLog(seckillInfoReqDTO, Constants.SECKILL_REDUCE);
		}
	}

}
