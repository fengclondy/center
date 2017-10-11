package cn.htd.zeus.tc.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.zeus.tc.biz.rao.MarketCenterRAO;
import cn.htd.zeus.tc.biz.service.OrderCreate4BusinessHandleService;
import cn.htd.zeus.tc.common.enums.GoodCenterEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.exception.OrderCenterBusinessException;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateListInfoReqDTO;

@Service
public class OrderCreate4BusinessHandleServiceImpl implements
		OrderCreate4BusinessHandleService {

	@Resource
	private MarketCenterRAO marketCenterRAO;

	/**
	 * 1:查询外部供应商下的商品是否是限时购商品，如果是就在订单行维度赋值promotionId、promotionTyp、
	 * isLimitedTimePurchase和IsHasLimitedTimePurchase 2:校验买家和买家不能是同一个账号 3:校验限时购促销库存数量，如果数量不足就返回给调用者
	 * 
	 * @param orderCreateInfoReqDTO
	 */
	@Override
	public void handleLimitedTimePurchaseSkuCode(
			OrderCreateInfoReqDTO orderCreateInfoReqDTO) {
		List<OrderCreateListInfoReqDTO> orderList = orderCreateInfoReqDTO
				.getOrderList();
		String messageId = orderCreateInfoReqDTO.getMessageId();
		String buyerCode = orderCreateInfoReqDTO.getBuyerCode();
		for (OrderCreateListInfoReqDTO orderTemp : orderList) {
			List<OrderCreateItemListInfoReqDTO> orderItemList = orderTemp
					.getOrderItemList();
			String sellerCode = orderTemp.getSellerCode();
			// 1-校验买家和卖家不能是同一个账号
			if (buyerCode.equals(sellerCode)) {
				throw new OrderCenterBusinessException(
						ResultCodeEnum.ORDER_BUYER_SELLER_SAME.getCode(),
						"提交订单时"
								+ ResultCodeEnum.ORDER_BUYER_SELLER_SAME
										.getMsg() + " buyerCode:" + buyerCode
								+ " sellerCode:" + sellerCode);
			}
			// 2-如果是外部供应商，则根据skuCode校验是否参与限时购，如果参与限时购，校验库存是否足够
			for (OrderCreateItemListInfoReqDTO orderItemTemp : orderItemList) {
				String channelCode = orderItemTemp.getChannelCode();
				if (GoodCenterEnum.EXTERNAL_SUPPLIER.getCode().equals(
						channelCode)) {
					String skuCode = orderItemTemp.getSkuCode();
					OtherCenterResDTO<List<TimelimitedInfoDTO>> timeLimitedInfoRes = marketCenterRAO
							.getTimelimitedInfo(skuCode, messageId);
					String timeLimitedInfoResCode = timeLimitedInfoRes
							.getOtherCenterResponseCode();
					if (timeLimitedInfoResCode.equals(ResultCodeEnum.SUCCESS
							.getCode())) {
						List<TimelimitedInfoDTO> timelimitedInfoDTOList = timeLimitedInfoRes
								.getOtherCenterResult();
						if (null == timelimitedInfoDTOList
								|| timelimitedInfoDTOList.size() < 1) {
							throw new OrderCenterBusinessException(
									ResultCodeEnum.MARKETCENTER_LIMITED_TIME_PURCHASE_IS_NULL
											.getCode(),
									"提交订单时"
											+ ResultCodeEnum.MARKETCENTER_LIMITED_TIME_PURCHASE_IS_NULL
													.getMsg() + " 入参skuCode:"
											+ skuCode);
						}
						TimelimitedInfoDTO timelimitedInfo = timelimitedInfoDTOList
								.get(0);
						// 每单限制数量
						Integer timelimitedThreshold = timelimitedInfo
								.getTimelimitedThreshold();
						// 限时购商品剩余数量
						Integer timelimitedSkuCount = timelimitedInfo
								.getTimelimitedSkuCount();
						Integer goodsCount = Integer.valueOf(orderItemTemp
								.getGoodsCount().toString());
						if (goodsCount > timelimitedThreshold
								|| goodsCount > timelimitedSkuCount) {
							throw new OrderCenterBusinessException(
									ResultCodeEnum.MARKERCENTER_LIMITED_TIME_PURCHASE_BUYCOUNT_BEYOND
											.getCode(),
									"提交订单时"
											+ ResultCodeEnum.MARKERCENTER_LIMITED_TIME_PURCHASE_BUYCOUNT_BEYOND
													.getMsg());
						}
						orderItemTemp.setPromotionId(timelimitedInfo
								.getPromotionId());
						orderItemTemp.setPromotionType(timelimitedInfo
								.getPromotionType());
						orderItemTemp
								.setIsLimitedTimePurchase(Integer
										.valueOf(OrderStatusEnum.IS_LIMITED_TIME_PURCHASE
												.getCode()));
						orderCreateInfoReqDTO
								.setIsHasLimitedTimePurchase(Integer
										.valueOf(OrderStatusEnum.HAS_LIMITED_TIME_PURCHASE
												.getCode()));
					}
				}
			}
		}
	}

}
