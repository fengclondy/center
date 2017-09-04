package cn.htd.promotion.cpc.biz.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.biz.service.StockChangeService;
import cn.htd.promotion.cpc.common.constants.Constants;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.request.SeckillInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;

@Service("StockChangeService")
public abstract class StockChangeImpl implements StockChangeService {

	@Resource
	private PromotionRedisDB promotionRedisDB;

	/**
	 * 日志
	 */
	protected Logger logger = LoggerFactory.getLogger(StockChangeImpl.class);

	@Override
	public void checkAndChangeStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) throws Exception {
		/* 验空2017-02-13 */
		ValidateResult validateResult = DTOValidateUtil.validate(seckillInfoReqDTO);
		if (!validateResult.isPass()) {
			throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
					validateResult.getErrorMsg());
		}
		logger.info("MessageId:{} 调用方法StockChangeImpl.checkAndChangeStock入参{}", JSON.toJSONString(seckillInfoReqDTO));
		this.changeStock(messageId, seckillInfoReqDTO.getPromotionId(), seckillInfoReqDTO.getBuyerCode());
	}

	/**
	 * 具体的扣减，锁定，释放，回滚操作。
	 * 
	 * @param promotionId
	 * @return
	 */
	protected abstract void changeStock(String messageId, String promotionId, String buyerCode) throws Exception;

	/**
	 * 更新Redis中的秒杀活动参加记录并更新DB
	 *
	 * @param useLogList
	 */
	public void updateRedisUseTimelimitedLog(BuyerUseTimelimitedLogDMO useLogList) {
		String useLogRedisKey = "";
		if (useLogList == null) {
			return;
		}
		useLogRedisKey = useLogList.getBuyerCode() + "&" + useLogList.getPromotionId();
		promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG, useLogRedisKey,
				JSON.toJSONString(useLogList));
		promotionRedisDB.tailPush(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG,
				JSON.toJSONString(useLogList));
	}

	/**
	 * 设置秒杀履历信息
	 * 
	 * @param timelimitedInfoReqDTO
	 * @param orderNo
	 * @param seckillLockNo
	 * @param useType
	 * @param seckillNum
	 * @return
	 */
	protected BuyerUseTimelimitedLogDMO setTimelimitedLog(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String useType) {
		BuyerUseTimelimitedLogDMO log = new BuyerUseTimelimitedLogDMO();
		Map<String, Object> orderMap = JSON.parseObject(promotionRedisDB.getHash(
				RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_ORDER_INFO, timelimitedInfoReqDTO.getPromotionId()));
		log.setOrderNo((String) orderMap.get("orderNo"));
		log.setSeckillLockNo((String) orderMap.get("seckillLockNo"));
		log.setUseType(useType);
		log.setUsedCount((Integer) orderMap.get("seckillNum"));
		log.setBuyerCode((String) orderMap.get("buyerCode"));
		// 秒杀默认一个层级
		log.setLevelCode("1");
		log.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
		// 判断库存是否被释放标志 0：未释放 1：已释放
		if (Constants.SECKILL_RESERVE.equals(useType)) {
			log.setHasReleasedStock(0);
		} else {
			log.setHasReleasedStock(1);
		}
		log.setCreateId((Long) orderMap.get("buyerId"));
		log.setCreateName((String) orderMap.get("buyerName"));
		log.setModifyId((Long) orderMap.get("buyerId"));
		log.setModifyName((String) orderMap.get("buyerName"));
		return log;
	}

	protected boolean checkSeckillOperateLegalOrNot(String promotionId, String buyerCode, String useType) {
		boolean flag = false;
		String useLogRedisKey = buyerCode + "&" + promotionId;
		String useLogJsonStr = "";
		useLogJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG, useLogRedisKey);
		BuyerUseTimelimitedLogDMO useLog = JSON.parseObject(useLogJsonStr, BuyerUseTimelimitedLogDMO.class);
		if (Constants.SECKILL_RESERVE.equals(useType)) {

		}
		return flag;
	}

}
