package cn.htd.promotion.cpc.biz.service;


import java.util.List;

import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

public interface PromotionTimelimitedInfoService {

	public List<TimelimitedInfoResDTO> getPromotionTimelimitedInfoByBuyerCode(String messageId,String buyerCode) throws PromotionCenterBusinessException;
	
	public List<PromotionSellerDetailDTO> getPromotionSellerDetailDTOByBuyerCode(String promotionId,String buyerCode) throws PromotionCenterBusinessException;

}
