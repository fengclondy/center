package cn.htd.zeus.tc.biz.rao;

import java.util.List;

import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedMallInfoDTO;
import cn.htd.marketcenter.dto.TradeInfoDTO;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

public interface MarketCenterRAO {

	/*
	 * 调用促销中心查询会员优惠券列表信息
	 */
	public OtherCenterResDTO<TradeInfoDTO> getAvailableCouponInfo(TradeInfoDTO cart,
			String messageId);

	/*
	 * 锁定会员优惠券、秒杀
	 */
	public OtherCenterResDTO<String> reserveBuyerPromotion(
			List<OrderItemPromotionDTO> orderItemPromotionList, String messageId);

	/*
	 * 扣减会员优惠券、秒杀
	 */
	public OtherCenterResDTO<String> reduceBuyerPromotion(
			List<OrderItemPromotionDTO> orderItemPromotionList, String messageId);

	/*
	 * 回滚会员优惠券、秒杀
	 */
	public OtherCenterResDTO<String> rollbackBuyerPromotion(
			List<OrderItemPromotionDTO> orderItemPromotionList, String messageId);

	public OtherCenterResDTO<String> releaseBuyerPromotion(
			List<OrderItemPromotionDTO> orderItemPromotionList, String messageId);

	/*
	 * 调用促销中心查询秒杀活动信息
	 */
	public OtherCenterResDTO<TimelimitedMallInfoDTO> getSeckillInfo(String promotionId,
			String messageId);

	/*
	 * 根据选择的优惠券信息计算订单行的分摊金额
	 */
	public OtherCenterResDTO<TradeInfoDTO> calculateCouponDiscount(List<String> couponCodeList,
			TradeInfoDTO cart, String messageId);
	
	/**
	 * 限时购	  －  获取对应的限时购信息
	 * @param skuCode
	 * @return
	 */
	public OtherCenterResDTO<List<TimelimitedInfoDTO>> getTimelimitedInfo(
			String skuCode, String messageId);
}
