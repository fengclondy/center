package cn.htd.promotion.cpc.api;

import cn.htd.common.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionOrderItemDTO;

import java.util.List;

/**
 * 订单相关促销处理
 * 
 */
public interface PromotionInterestChangeAPI {
	/**
	 * 秒杀 - 锁定秒杀 （创建订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> reserveBuyerPromotion(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList);

	/**
	 * 秒杀 - 扣减 （支付完成）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> reduceBuyerPromotion(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList);

	/**
	 * 秒杀 - 解锁 （取消未支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */

	public ExecuteResult<String> releaseBuyerPromotion(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList);

	/**
	 * 秒杀 - 回滚（取消已支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> rollbackBuyerPromotion(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList);
}
