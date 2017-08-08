package cn.htd.marketcenter.service.handle;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;

public abstract class AbstractBuyerPromotionHandle {

	@Resource
	private DictionaryUtils dictionary;

	/**
	 * 锁定促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	protected abstract void reserveBuyerPromotionDeal(String messageId,
			List<OrderItemPromotionDTO> buyerPromotionDTOList) throws MarketCenterBusinessException, Exception;

	/**
	 * 扣减促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	protected abstract void reduceBuyerPromotionDeal(String messageId,
			List<OrderItemPromotionDTO> buyerPromotionDTOList) throws MarketCenterBusinessException, Exception;

	/**
	 * 释放锁定促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	protected abstract void releaseBuyerPromotionDeal(String messageId,
			List<OrderItemPromotionDTO> buyerPromotionDTOList) throws MarketCenterBusinessException, Exception;

	/**
	 * 回滚扣减锁定促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	protected abstract void rollbackBuyerPromotionDeal(String messageId,
			List<OrderItemPromotionDTO> buyerPromotionDTOList) throws MarketCenterBusinessException, Exception;

	/**
	 * 锁定买家促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	@Transactional
	public void reserveBuyerPromotion(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		reserveBuyerPromotionDeal(messageId, buyerPromotionDTOList);
	}

	/**
	 * 释放锁定的买家促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	@Transactional
	public void releaseBuyerPromotion(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		releaseBuyerPromotionDeal(messageId, buyerPromotionDTOList);
	}

	/**
	 * 扣减买家促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	@Transactional
	public void reduceBuyerPromotion(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		reduceBuyerPromotionDeal(messageId, buyerPromotionDTOList);

	}

	/**
	 * 回滚扣减的买家促销活动
	 * 
	 * @param messageId
	 * @param buyerPromotionDTOList
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	@Transactional
	public void rollbackBuyerPromotion(String messageId, List<OrderItemPromotionDTO> buyerPromotionDTOList)
			throws MarketCenterBusinessException, Exception {
		rollbackBuyerPromotionDeal(messageId, buyerPromotionDTOList);
	}
}