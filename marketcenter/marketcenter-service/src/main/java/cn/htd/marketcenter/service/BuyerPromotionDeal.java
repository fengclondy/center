package cn.htd.marketcenter.service;

import java.util.List;

import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;

public interface BuyerPromotionDeal {

	/**
	 * 锁定买家促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	public void reserveBuyerPromotion(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception;

	/**
	 * 释放锁定的买家促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	public void releaseBuyerPromotion(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception;

	/**
	 * 扣减买家促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	public void reduceBuyerPromotion(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception;

	/**
	 * 回滚扣减的买家促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	public void rollbackBuyerPromotion(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception;

}
