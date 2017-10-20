package cn.htd.marketcenter.service.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.domain.BuyerUseTimelimitedLog;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.service.BuyerPromotionDeal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("buyerLimitedDiscountHandle")
public class BuyerLimitedDiscountHandle extends AbstractBuyerPromotionHandle implements BuyerPromotionDeal {

	private static final Logger logger = LoggerFactory.getLogger(BuyerLimitedDiscountHandle.class);

	@Resource
	private TimelimitedRedisHandle timelimitedRedisHandle;

	/**
	 * 根据处理的订单行限时购对象生成check对象列表
	 *
	 * @param buyerPromotionDTOList
	 * @return
	 */
	private Map<String, List<OrderItemPromotionDTO>> getDistinctBuyerLimitedDiscountList(
			List<OrderItemPromotionDTO> buyerPromotionDTOList) {
		Map<String, List<OrderItemPromotionDTO>> distinctTimelimitedDiscountMap =
				new HashMap<String, List<OrderItemPromotionDTO>>();
		List<OrderItemPromotionDTO> targetOrderItemList = null;
		String promotionId = "";

		for (OrderItemPromotionDTO orderItemPromotionDTO : buyerPromotionDTOList) {
			promotionId = orderItemPromotionDTO.getPromotionId();
			if (distinctTimelimitedDiscountMap.containsKey(promotionId)) {
				targetOrderItemList = distinctTimelimitedDiscountMap.get(promotionId);
			} else {
				targetOrderItemList = new ArrayList<OrderItemPromotionDTO>();
			}
			targetOrderItemList.add(orderItemPromotionDTO);
			distinctTimelimitedDiscountMap.put(promotionId, targetOrderItemList);
		}
		return distinctTimelimitedDiscountMap;
	}
	@Override
	protected void reserveBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> targetPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		List<OrderItemPromotionDTO> buyerPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		BuyerUseTimelimitedLog useLog = null;
		List<BuyerUseTimelimitedLog> useLogList = new ArrayList<BuyerUseTimelimitedLog>();
		Map<String, List<OrderItemPromotionDTO>> distinctLimitedDiscountMap = null;
		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			if (StringUtils.isEmpty(buyerPromotionDTO.getSkuCode())) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
						"促销活动ID:" + buyerPromotionDTO.getPromotionId() + " 的锁定子订单号SKU编号为空");
			}
			useLog = timelimitedRedisHandle.getRedisReverseBuyerLimitedDiscountUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLogList.add(useLog);
				buyerPromotionDTOList.add(buyerPromotionDTO);
			}
		}
		if (buyerPromotionDTOList.isEmpty()) {
			return;
		}
		distinctLimitedDiscountMap = getDistinctBuyerLimitedDiscountList(buyerPromotionDTOList);
		timelimitedRedisHandle.dealRedisBuyerLimitedDiscountList(distinctLimitedDiscountMap,
				DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
		timelimitedRedisHandle.updateRedisUseLimitedDiscountLog(useLogList);
	}

	@Override
	protected void reduceBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> targetPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		List<OrderItemPromotionDTO> buyerPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		BuyerUseTimelimitedLog useLog = null;
		List<BuyerUseTimelimitedLog> useLogList = new ArrayList<BuyerUseTimelimitedLog>();

		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			useLog = timelimitedRedisHandle.getRedisReleaseBuyerLimitedDiscountUseLog(buyerPromotionDTO);
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
		Map<String, List<OrderItemPromotionDTO>> limitedDiscountMap =
				new HashMap<String, List<OrderItemPromotionDTO>>();

		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			useLog = timelimitedRedisHandle.getRedisReleaseBuyerLimitedDiscountUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLogList.add(useLog);
				buyerPromotionDTO.setSkuCode(useLog.getSkuCode());
				buyerPromotionDTO.setQuantity(useLog.getUsedCount());
				buyerPromotionDTOList.add(buyerPromotionDTO);
			}
		}
		if (buyerPromotionDTOList.isEmpty()) {
			return;
		}
		limitedDiscountMap.put("1", buyerPromotionDTOList);
		timelimitedRedisHandle.dealRedisBuyerLimitedDiscountList(limitedDiscountMap,
				DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
		timelimitedRedisHandle.updateRedisUseLimitedDiscountLog(useLogList);
	}

	@Override
	protected void rollbackBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception {

	}
}
