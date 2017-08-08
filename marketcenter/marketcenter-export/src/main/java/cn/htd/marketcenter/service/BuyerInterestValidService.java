package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.TradeInfoDTO;

public interface BuyerInterestValidService {

	/**
	 * 根据购物车筛选可用优惠券信息
	 * 
	 * @param messageId
	 * @param cart
	 * @return
	 */
	public ExecuteResult<TradeInfoDTO> getAvailableCouponInfo(String messageId, TradeInfoDTO cart);

	/**
	 * 根据选择的优惠券信息计算订单行的分摊金额
	 * 
	 * @param messageId
	 * @param cart
	 * @return
	 */
	public ExecuteResult<TradeInfoDTO> calculateTradeDiscount(String messageId, TradeInfoDTO cart);

}
