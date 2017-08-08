package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;

import java.util.List;

/**
 * 订单相关买家促销处理
 * 
 * @author jiangkun
 */
public interface BuyerInterestChangeService {
	/**
	 * 锁定会员优惠券、秒杀 （创建订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> reserveBuyerPromotion(String messageId,
			List<OrderItemPromotionDTO> orderItemPromotionList);

	/**
	 * 扣减会员优惠券、秒杀 （支付完成）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> reduceBuyerPromotion(String messageId,
			List<OrderItemPromotionDTO> orderItemPromotionList);

	/**
	 * 解除锁定会员优惠券、秒杀 （取消未支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */

	public ExecuteResult<String> releaseBuyerPromotion(String messageId,
			List<OrderItemPromotionDTO> orderItemPromotionList);

	/**
	 * 回滚会员优惠券、秒杀（取消已支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> rollbackBuyerPromotion(String messageId,
			List<OrderItemPromotionDTO> orderItemPromotionList);
}
