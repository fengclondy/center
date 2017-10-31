package cn.htd.promotion.cpc.dto.response;

import java.util.List;

import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;


public class SinglePromotionInfoCmplResDTO extends  SinglePromotionInfoResDTO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5731075511465955290L;
	
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