package cn.htd.marketcenter.service.handle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.domain.BuyerUseTimelimitedLog;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.service.BuyerPromotionDeal;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("buyerTimelimitedHandle")
public class BuyerTimelimitedHandle extends AbstractBuyerPromotionHandle implements BuyerPromotionDeal {

	private static final Logger logger = LoggerFactory.getLogger(BuyerTimelimitedHandle.class);

	@Resource
	private TimelimitedRedisHandle timelimitedRedisHandle;

	@Override
	protected void reserveBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> targetPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		List<OrderItemPromotionDTO> buyerPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		BuyerUseTimelimitedLog useLog = null;
		List<BuyerUseTimelimitedLog> releaseUseLogList = new ArrayList<BuyerUseTimelimitedLog>();
		List<BuyerUseTimelimitedLog> newUseLogList = new ArrayList<BuyerUseTimelimitedLog>();
		List<BuyerUseTimelimitedLog> chgUseLogList = new ArrayList<BuyerUseTimelimitedLog>();
		List<OrderItemPromotionDTO> targetReleaseUseLogList = new ArrayList<OrderItemPromotionDTO>();
		OrderItemPromotionDTO targetReleasePromotionDTO = null;
		Long operaterId = 0L;
		String operaterName = "";

		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			if (StringUtils.isEmpty(buyerPromotionDTO.getSeckillLockNo())) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
						"促销活动ID:" + buyerPromotionDTO.getPromotionId() + " 的秒杀锁定预占订单号为空");
			}
			operaterId = buyerPromotionDTO.getOperaterId();
			operaterName = buyerPromotionDTO.getOperaterName();
			useLog = timelimitedRedisHandle.getNoSubmitBuyerTimelimitedUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLog.setModifyId(operaterId);
				useLog.setModifyName(operaterName);
				useLog.setModifyTime(new Date());
				releaseUseLogList.add(useLog);
			}
		}
		if (!releaseUseLogList.isEmpty()) {
			for (BuyerUseTimelimitedLog noSubmitOrderLog : releaseUseLogList) {
				targetReleasePromotionDTO = new OrderItemPromotionDTO();
				targetReleasePromotionDTO.setPromotionId(noSubmitOrderLog.getPromotionId());
				targetReleasePromotionDTO.setPromotionType(noSubmitOrderLog.getPromotionType());
				targetReleasePromotionDTO.setBuyerCode(noSubmitOrderLog.getBuyerCode());
				targetReleasePromotionDTO.setSkuCode(noSubmitOrderLog.getSkuCode());
				targetReleasePromotionDTO.setQuantity(noSubmitOrderLog.getUsedCount());
				targetReleasePromotionDTO.setOperaterId(operaterId);
				targetReleasePromotionDTO.setOperaterName(operaterName);
				targetReleaseUseLogList.add(targetReleasePromotionDTO);
				logger.info("************** 会员秒杀预占信息释放" + JSON.toJSONString(noSubmitOrderLog) + " **************");
			}
			timelimitedRedisHandle.dealRedisBuyerTimelimitedList(targetReleaseUseLogList,
					DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
			timelimitedRedisHandle.updateNoSubmitRedisUseTimelimitedLog(releaseUseLogList);
		}
		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			if (buyerPromotionDTO.getQuantity() == 0) {
				continue;
			}
			useLog = timelimitedRedisHandle.getRedisReverseBuyerTimelimitedUseLog(buyerPromotionDTO);
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
			timelimitedRedisHandle.updateRedisUseTimelimitedLog(chgUseLogList);
		}
		if (!buyerPromotionDTOList.isEmpty()) {
			timelimitedRedisHandle.dealRedisBuyerTimelimitedList(buyerPromotionDTOList,
					DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
			timelimitedRedisHandle.updateRedisUseTimelimitedLog(newUseLogList);
		}
	}

	@Override
	protected void reduceBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> targetPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		List<OrderItemPromotionDTO> buyerPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		BuyerUseTimelimitedLog useLog = null;
		List<BuyerUseTimelimitedLog> useLogList = new ArrayList<BuyerUseTimelimitedLog>();

		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			useLog = timelimitedRedisHandle.getRedisReleaseBuyerTimelimitedUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLogList.add(useLog);
				buyerPromotionDTOList.add(buyerPromotionDTO);
			}
		}
		if (buyerPromotionDTOList.isEmpty()) {
			return;
		}
		timelimitedRedisHandle.updateRedisUseTimelimitedLog(useLogList);
	}

	@Override
	protected void releaseBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> targetPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		List<OrderItemPromotionDTO> buyerPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		BuyerUseTimelimitedLog useLog = null;
		List<BuyerUseTimelimitedLog> useLogList = new ArrayList<BuyerUseTimelimitedLog>();

		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			useLog = timelimitedRedisHandle.getRedisReleaseBuyerTimelimitedUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLogList.add(useLog);
				buyerPromotionDTO.setQuantity(useLog.getUsedCount());
				buyerPromotionDTOList.add(buyerPromotionDTO);
			}
		}
		if (buyerPromotionDTOList.isEmpty()) {
			return;
		}
		timelimitedRedisHandle.dealRedisBuyerTimelimitedList(buyerPromotionDTOList,
				DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
		timelimitedRedisHandle.updateRedisUseTimelimitedLog(useLogList);
	}

	@Override
	protected void rollbackBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception {

	}
}
