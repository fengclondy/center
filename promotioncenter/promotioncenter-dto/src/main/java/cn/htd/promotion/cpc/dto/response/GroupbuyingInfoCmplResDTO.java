package cn.htd.promotion.cpc.dto.response;

import java.util.List;

public class GroupbuyingInfoCmplResDTO extends GroupbuyingInfoResDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3727561858547961215L;
	
	// 团购价格设置
	private List<GroupbuyingPriceSettingResDTO> groupbuyingPriceSettingResDTOList;
	// 活动信息
	private SinglePromotionInfoCmplResDTO singlePromotionInfoCmplResDTO;
	
	public List<GroupbuyingPriceSettingResDTO> getGroupbuyingPriceSettingResDTOList() {
		return groupbuyingPriceSettingResDTOList;
	}
	public void setGroupbuyingPriceSettingResDTOList(
			List<GroupbuyingPriceSettingResDTO> groupbuyingPriceSettingResDTOList) {
		this.groupbuyingPriceSettingResDTOList = groupbuyingPriceSettingResDTOList;
	}
	public SinglePromotionInfoCmplResDTO getSinglePromotionInfoCmplResDTO() {
		return singlePromotionInfoCmplResDTO;
	}
	public void setSinglePromotionInfoCmplResDTO(
			SinglePromotionInfoCmplResDTO singlePromotionInfoCmplResDTO) {
		this.singlePromotionInfoCmplResDTO = singlePromotionInfoCmplResDTO;
	}
	

	

	
	
	

}