package cn.htd.promotion.cpc.biz.service;


import java.util.List;

import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

public interface PromotionTimelimitedInfoService {

	public List<TimelimitedInfoResDTO> getPromotionTimelimitedInfoByBuyerCode(String messageId,String buyerCode) throws PromotionCenterBusinessException;
	
	public List<PromotionSellerDetailDTO> getPromotionSellerDetailDTOByBuyerCode(String promotionId,String buyerCode) throws PromotionCenterBusinessException;
	
	
	public TimelimitedInfoResDTO getPromotionTimelimitedInfoBySkuCode(String messageId,String skuCode) throws PromotionCenterBusinessException;

	/**
	 * 删除促销活动
	 *
	 * @param validDTO
	 * @throws PromotionCenterBusinessException
	 * @throws Exception
	 */
	public void deletePromotionInfo(String messageId, PromotionValidDTO validDTO) throws PromotionCenterBusinessException, Exception;
}
