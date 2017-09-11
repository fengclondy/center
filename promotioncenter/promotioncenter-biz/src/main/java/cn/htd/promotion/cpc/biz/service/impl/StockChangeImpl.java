package cn.htd.promotion.cpc.biz.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.biz.service.StockChangeService;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.Constants;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.DateUtil;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.common.util.RedissonClientUtil;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.request.SeckillInfoReqDTO;

@Service("StockChangeService")
public abstract class StockChangeImpl implements StockChangeService {

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Resource
	private TimelimitedInfoService timelimitedInfoService;

	@Autowired
	private RedissonClientUtil redissonClientUtil;

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
		RLock rLock = null;
		try {
			RedissonClient redissonClient = redissonClientUtil.getInstance();
			String lockKey = Constants.REDIS_KEY_PREFIX_STOCK + String.valueOf(seckillInfoReqDTO.getPromotionId()); // 竞争资源标志
			rLock = redissonClient.getLock(lockKey);
			/** 上锁 **/
			rLock.lock();
			changeStock(messageId, seckillInfoReqDTO);
		} finally {
			/** 释放锁资源 **/
			if (rLock != null) {
				rLock.unlock();
			}
		}
	}

	/**
	 * 具体的扣减，锁定，释放，回滚操作。
	 * 
	 * @param promotionId
	 * @return
	 */
	protected abstract void changeStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) throws Exception;

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
	protected void setTimelimitedLog(SeckillInfoReqDTO seckillInfoReqDTO, String useType) throws Exception {
		BuyerUseTimelimitedLogDMO log = new BuyerUseTimelimitedLogDMO();
		BuyerUseTimelimitedLogDMO timelimitedLog = null;
		String promotionId = seckillInfoReqDTO.getPromotionId();
		String buyerCode = seckillInfoReqDTO.getBuyerCode();
		String key = buyerCode + "&" + promotionId;
		String str = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG, key);
		if (StringUtils.isNotBlank(str)) {
			timelimitedLog = JSON.parseObject(promotionRedisDB
					.getHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG, seckillInfoReqDTO.getPromotionId()),
					BuyerUseTimelimitedLogDMO.class);
			log.setSeckillLockNo(timelimitedLog.getSeckillLockNo());
			log.setUseType(useType);
			log.setUsedCount(timelimitedLog.getUsedCount());
			log.setBuyerCode(timelimitedLog.getBuyerCode());
		} else {
			log.setSeckillLockNo(seckillInfoReqDTO.getSeckillLockNo());
			log.setUseType(useType);
			log.setUsedCount(seckillInfoReqDTO.getCount());
			log.setBuyerCode(seckillInfoReqDTO.getBuyerCode());
		}
		// 秒杀默认一个层级
		log.setLevelCode("1");
		log.setPromotionId(seckillInfoReqDTO.getPromotionId());
		// 判断库存是否被释放标志 0：未释放 1：已释放
		if (Constants.SECKILL_RESERVE.equals(useType)) {
			log.setHasReleasedStock(0);
		} else {
			log.setHasReleasedStock(1);
		}
		log.setCreateId(seckillInfoReqDTO.getOperaterId());
		log.setCreateName(seckillInfoReqDTO.getOperaterName());
		log.setCreateTime(DateUtil.getSystemTime());
		log.setModifyId(seckillInfoReqDTO.getOperaterId());
		log.setModifyName(seckillInfoReqDTO.getOperaterName());
		log.setModifyTime(DateUtil.getSystemTime());
		timelimitedInfoService.saveOrUpdateTimelimitedOperlog(log);
	}

	protected boolean checkSeckillOperateLegalOrNot(String promotionId, String buyerCode, String useType) {
		boolean flag = false;
		String useLogRedisKey = buyerCode + "&" + promotionId;
		String useLogJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG,
				useLogRedisKey);
		BuyerUseTimelimitedLogDMO timelimitedLog = JSON.parseObject(useLogJsonStr, BuyerUseTimelimitedLogDMO.class);
		logger.info("booean1:{},boolean2:{}", Constants.SECKILL_RESERVE.equals(useType),
				(StringUtils.isBlank(useLogJsonStr)
						|| timelimitedLog.getHasReleasedStock() == Constants.HAS_RELEASE_FLAG));
		// 秒杀履历为空或者秒杀履历为已释放或者已抢到秒杀资格并且没有支付订单
		if (Constants.SECKILL_RESERVE.equals(useType) && (StringUtils.isBlank(useLogJsonStr)
				|| timelimitedLog.getHasReleasedStock() == Constants.HAS_RELEASE_FLAG)) {
			flag = true;
			// 存在秒杀履历并且前置操作为锁定库存
		} else if (Constants.SECKILL_RELEASE.equals(useType) && StringUtils.isNotBlank(useLogJsonStr)) {
			if (Constants.SECKILL_RESERVE.equals(timelimitedLog.getUseType())) {
				flag = true;
			}
		} else if (Constants.SECKILL_REDUCE.equals(useType) && StringUtils.isNotBlank(useLogJsonStr)) {
			if (Constants.SECKILL_RESERVE.equals(timelimitedLog.getUseType())) {
				flag = true;
			}
		} else if (Constants.SECKILL_ROLLBACK.equals(useType) && StringUtils.isNotBlank(useLogJsonStr)) {
			flag = true;
		}
		return flag;
	}

}
