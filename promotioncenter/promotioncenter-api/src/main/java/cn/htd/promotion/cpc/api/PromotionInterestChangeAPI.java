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
	 * 秒杀 - 锁定 （创建订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> reservePromotionTimelimited(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList);

	/**
	 * 秒杀 - 扣减 （支付完成）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> reducePromotionTimelimited(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList);

	/**
	 * 秒杀 - 解锁 （取消未支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> releasePromotionTimelimited(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList);

	/**
	 * 秒杀 - 回滚（取消已支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	public ExecuteResult<String> rollbackPromotionTimelimited(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList);
}
