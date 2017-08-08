package cn.htd.marketcenter.service.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.domain.BuyerUseCouponLog;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.service.BuyerPromotionDeal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("buyerCouponHandle")
public class BuyerCouponHandle extends AbstractBuyerPromotionHandle implements BuyerPromotionDeal {

	private static final Logger logger = LoggerFactory.getLogger(BuyerCouponHandle.class);

	@Resource
	private CouponRedisHandle couponRedisHandle;

	/**
	 * 根据处理的订单行优惠列表生成check对象列表
	 * 
	 * @param buyerPromotionDTOList
	 * @return
	 */
	private Map<String, OrderItemPromotionDTO> getDistinctBuyerCouponList(
			List<OrderItemPromotionDTO> buyerPromotionDTOList) {
		Map<String, OrderItemPromotionDTO> distinctCouponMap = new HashMap<String, OrderItemPromotionDTO>();
		OrderItemPromotionDTO distinctCouopnDTO = null;
		String buyerCode = "";
		String promotionId = "";
		String levelCode = "";
		String couponCode = "";
		String mapKey = "";
		long itemCouponCount = 0;

		for (OrderItemPromotionDTO orderCouponDTO : buyerPromotionDTOList) {
			buyerCode = orderCouponDTO.getBuyerCode();
			promotionId = orderCouponDTO.getPromotionId();
			levelCode = orderCouponDTO.getLevelCode();
			couponCode = orderCouponDTO.getCouponCode();
			mapKey = buyerCode + "_" + promotionId + "_" + levelCode + "_" + couponCode;
			if (distinctCouponMap.containsKey(mapKey)) {
				distinctCouopnDTO = distinctCouponMap.get(mapKey);
			} else {
				distinctCouopnDTO = new OrderItemPromotionDTO();
				itemCouponCount = 0;
			}
			itemCouponCount++;
			distinctCouopnDTO.setBuyerCode(buyerCode);
			distinctCouopnDTO.setPromotionId(promotionId);
			distinctCouopnDTO.setLevelCode(levelCode);
			distinctCouopnDTO.setCouponCode(couponCode);
			distinctCouopnDTO.setDiscountAmount(
					CalculateUtils.add(distinctCouopnDTO.getDiscountAmount(), orderCouponDTO.getDiscountAmount()));
			distinctCouopnDTO.setOperaterId(orderCouponDTO.getOperaterId());
			distinctCouopnDTO.setOperaterName(orderCouponDTO.getOperaterName());
			distinctCouopnDTO.setUseLogCount(itemCouponCount);
			distinctCouponMap.put(mapKey, distinctCouopnDTO);
		}
		return distinctCouponMap;
	}

	@Override
	protected void reserveBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> targetPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		List<OrderItemPromotionDTO> buyerPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		Map<String, OrderItemPromotionDTO> distinctCouponMap = null;
		BuyerUseCouponLog useLog = null;
		List<BuyerUseCouponLog> useLogList = new ArrayList<BuyerUseCouponLog>();

		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			useLog = couponRedisHandle.getRedisReverseBuyerCouponUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLogList.add(useLog);
				buyerPromotionDTOList.add(buyerPromotionDTO);
			}
		}
		if (buyerPromotionDTOList.isEmpty()) {
			return;
		}
		distinctCouponMap = getDistinctBuyerCouponList(buyerPromotionDTOList);
		couponRedisHandle.dealRedisBuyerCouponAmountList(distinctCouponMap,
				DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
		couponRedisHandle.updateRedisUseCouponLog(useLogList);
	}

	@Override
	protected void reduceBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> targetPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		List<OrderItemPromotionDTO> buyerPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		Map<String, OrderItemPromotionDTO> distinctCouponMap = null;
		BuyerUseCouponLog useLog = null;
		List<BuyerUseCouponLog> useLogList = new ArrayList<BuyerUseCouponLog>();

		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			useLog = couponRedisHandle.getRedisReleaseBuyerCouponUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLogList.add(useLog);
				buyerPromotionDTOList.add(buyerPromotionDTO);
			}
		}
		if (buyerPromotionDTOList.isEmpty()) {
			return;
		}
		distinctCouponMap = getDistinctBuyerCouponList(buyerPromotionDTOList);
        couponRedisHandle.updateRedisUseCouponLog(useLogList);
		couponRedisHandle.dealRedisBuyerCouponAmountList(distinctCouponMap,
				DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE);
	}

	@Override
	protected void releaseBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> targetPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		List<OrderItemPromotionDTO> buyerPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		Map<String, OrderItemPromotionDTO> distinctCouponMap = null;
		BuyerUseCouponLog useLog = null;
		List<BuyerUseCouponLog> useLogList = new ArrayList<BuyerUseCouponLog>();

		for (OrderItemPromotionDTO buyerPromotionDTO : targetPromotionDTOList) {
			useLog = couponRedisHandle.getRedisReleaseBuyerCouponUseLog(buyerPromotionDTO);
			if (useLog != null) {
				useLogList.add(useLog);
				buyerPromotionDTO.setDiscountAmount(useLog.getCouponUsedAmount());
				buyerPromotionDTOList.add(buyerPromotionDTO);
			}
		}
		if (buyerPromotionDTOList.isEmpty()) {
			return;
		}
		distinctCouponMap = getDistinctBuyerCouponList(buyerPromotionDTOList);
		couponRedisHandle.updateRedisUseCouponLog(useLogList);
		couponRedisHandle.dealRedisBuyerCouponAmountList(distinctCouponMap,
				DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
	}

	@Override
	protected void rollbackBuyerPromotionDeal(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception {

	}
}
