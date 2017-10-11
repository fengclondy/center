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
	private SinglePromotionInfoResDTO singlePromotionInfoResDTO;
	
	public List<GroupbuyingPriceSettingResDTO> getGroupbuyingPriceSettingResDTOList() {
		return groupbuyingPriceSettingResDTOList;
	}
	public void setGroupbuyingPriceSettingResDTOList(
			List<GroupbuyingPriceSettingResDTO> groupbuyingPriceSettingResDTOList) {
		this.groupbuyingPriceSettingResDTOList = groupbuyingPriceSettingResDTOList;
	}
	public SinglePromotionInfoResDTO getSinglePromotionInfoResDTO() {
		return singlePromotionInfoResDTO;
	}
	public void setSinglePromotionInfoResDTO(
			SinglePromotionInfoResDTO singlePromotionInfoResDTO) {
		this.singlePromotionInfoResDTO = singlePromotionInfoResDTO;
	}


	
	
	

}