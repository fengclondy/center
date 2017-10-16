package cn.htd.promotion.cpc.dto.request;

import java.util.List;

import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;


public class SinglePromotionInfoCmplReqDTO extends  SinglePromotionInfoReqDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4333223990265896155L;
	
	// 活动配置信息
    private List<PromotionConfigureDTO> promotionConfigureList;

	public List<PromotionConfigureDTO> getPromotionConfigureList() {
		return promotionConfigureList;
	}

	public void setPromotionConfigureList(
			List<PromotionConfigureDTO> promotionConfigureList) {
		this.promotionConfigureList = promotionConfigureList;
	}
	
    
    
	
}