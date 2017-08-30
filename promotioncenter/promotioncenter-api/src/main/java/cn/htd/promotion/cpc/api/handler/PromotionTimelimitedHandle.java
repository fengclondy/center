package cn.htd.promotion.cpc.api.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.response.PromotionOrderItemDTO;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("promotionTimelimitedHandle")
public class PromotionTimelimitedHandle {

	private static final Logger logger = LoggerFactory.getLogger(PromotionTimelimitedHandle.class);
	
    @Resource
    private PromotionTimelimitedRedisHandle promotionTimelimitedRedisHandle;

	/**
	 * 秒杀 - 锁定 （创建订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
    public void reservePromotionTimelimitedDeal(String messageId, List<PromotionOrderItemDTO> targetPromotionDTOList)
			throws PromotionCenterBusinessException, Exception {
		List<PromotionOrderItemDTO> buyerPromotionDTOList = new ArrayList<PromotionOrderItemDTO>();
		BuyerUseTimelimitedLogDMO useLog = null;
		List<BuyerUseTimelimitedLogDMO> releaseUseLogList = new ArrayList<BuyerUseTimelimitedLogDMO>();
		List<BuyerUseTimelimitedLogDMO> newUseLogList = new ArrayList<BuyerUseTimelimitedLogDMO>();
		List<BuyerUseTimelimitedLogDMO> chgUseLogList = new ArrayList<BuyerUseTimelimitedLogDMO>();
		List<PromotionOrderItemDTO> targetReleaseUseLogList = new ArrayList<PromotionOrderItemDTO>();
		PromotionOrderItemDTO targetReleasePromotionDTO = null;
		Long operaterId = 0L;
		String operaterName = "";

		for (PromotionOrderItemDTO buyerPromotionDTO : targetPromotionDTOList) {
			if (StringUtils.isEmpty(buyerPromotionDTO.getSeckillLockNo())) {
				throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
						"促销活动ID:" + buyerPromotionDTO.getPromotionId() + " 的秒杀锁定预占订单号为空");
			}
			operaterId = buyerPromotionDTO.getOperaterId();
			operaterName = buyerPromotionDTO.getOperaterName();
			useLog = promotionTimelimitedRedisHandle.getNoSubmitBuyerTimelimitedUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLog.setModifyId(operaterId);
				useLog.setModifyName(operaterName);
				useLog.setModifyTime(new Date());
				releaseUseLogList.add(useLog);
			}
		}
		if (!releaseUseLogList.isEmpty()) {
			for (BuyerUseTimelimitedLogDMO noSubmitOrderLog : releaseUseLogList) {
				targetReleasePromotionDTO = new PromotionOrderItemDTO();
				targetReleasePromotionDTO.setPromotionId(noSubmitOrderLog.getPromotionId());
				targetReleasePromotionDTO.setBuyerCode(noSubmitOrderLog.getBuyerCode());
				targetReleasePromotionDTO.setQuantity(noSubmitOrderLog.getUsedCount());
				targetReleasePromotionDTO.setOperaterId(operaterId);
				targetReleasePromotionDTO.setOperaterName(operaterName);
				targetReleaseUseLogList.add(targetReleasePromotionDTO);
				logger.info("************** 会员秒杀预占信息释放" + JSON.toJSONString(noSubmitOrderLog) + " **************");
			}
			promotionTimelimitedRedisHandle.dealRedisBuyerTimelimitedList(targetReleaseUseLogList,
					DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
			promotionTimelimitedRedisHandle.updateNoSubmitRedisUseTimelimitedLog(releaseUseLogList);
		}
		for (PromotionOrderItemDTO buyerPromotionDTO : targetPromotionDTOList) {
			if (buyerPromotionDTO.getQuantity() == 0) {
				continue;
			}
			useLog = promotionTimelimitedRedisHandle.getRedisReverseBuyerTimelimitedUseLog(buyerPromotionDTO);
			if (useLog != null) {
				if (!StringUtils.isEmpty(useLog.getSeckillLockNo()) && !StringUtils.isEmpty(useLog.getOrderNo())) {
					chgUseLogList.add(useLog);
				} else {
					newUseLogList.add(useLog);
					buyerPromotionDTOList.add(buyerPromotionDTO);
				}
			}
		}
		if (!chgUseLogList.isEmpty()) {
			promotionTimelimitedRedisHandle.updateRedisUseTimelimitedLog(chgUseLogList);
		}
		if (!buyerPromotionDTOList.isEmpty()) {
			promotionTimelimitedRedisHandle.dealRedisBuyerTimelimitedList(buyerPromotionDTOList,
					DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
			promotionTimelimitedRedisHandle.updateRedisUseTimelimitedLog(newUseLogList);
		}
	}

	/**
	 * 秒杀 - 扣减 （支付完成）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public void reducePromotionTimelimitedDeal(String messageId, List<PromotionOrderItemDTO> targetPromotionDTOList)
			throws PromotionCenterBusinessException, Exception {
		List<PromotionOrderItemDTO> buyerPromotionDTOList = new ArrayList<PromotionOrderItemDTO>();
		BuyerUseTimelimitedLogDMO useLog = null;
		List<BuyerUseTimelimitedLogDMO> useLogList = new ArrayList<BuyerUseTimelimitedLogDMO>();

		for (PromotionOrderItemDTO buyerPromotionDTO : targetPromotionDTOList) {
			useLog = promotionTimelimitedRedisHandle.getRedisReleaseBuyerTimelimitedUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLogList.add(useLog);
				buyerPromotionDTOList.add(buyerPromotionDTO);
			}
		}
		if (buyerPromotionDTOList.isEmpty()) {
			return;
		}
		promotionTimelimitedRedisHandle.updateRedisUseTimelimitedLog(useLogList);
	}

	/**
	 * 秒杀 - 解锁 （取消未支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public void releasePromotionTimelimitedDeal(String messageId, List<PromotionOrderItemDTO> targetPromotionDTOList)
			throws PromotionCenterBusinessException, Exception {
		List<PromotionOrderItemDTO> buyerPromotionDTOList = new ArrayList<PromotionOrderItemDTO>();
		BuyerUseTimelimitedLogDMO useLog = null;
		List<BuyerUseTimelimitedLogDMO> useLogList = new ArrayList<BuyerUseTimelimitedLogDMO>();

		for (PromotionOrderItemDTO buyerPromotionDTO : targetPromotionDTOList) {
			useLog = promotionTimelimitedRedisHandle.getRedisReleaseBuyerTimelimitedUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLogList.add(useLog);
				buyerPromotionDTO.setQuantity(useLog.getUsedCount());
				buyerPromotionDTOList.add(buyerPromotionDTO);
			}
		}
		if (buyerPromotionDTOList.isEmpty()) {
			return;
		}
		promotionTimelimitedRedisHandle.dealRedisBuyerTimelimitedList(buyerPromotionDTOList,
				DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
		promotionTimelimitedRedisHandle.updateRedisUseTimelimitedLog(useLogList);
	}

	/**
	 * 秒杀 - 回滚（取消已支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public void rollbackPromotionTimelimitedDeal(String messageId, List<PromotionOrderItemDTO> buyerPromotionDTOList)
			throws PromotionCenterBusinessException, Exception {
	   }
}
