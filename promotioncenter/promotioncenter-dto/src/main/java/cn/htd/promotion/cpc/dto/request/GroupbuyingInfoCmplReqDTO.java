package cn.htd.promotion.cpc.dto.request;

import java.util.List;

public class GroupbuyingInfoCmplReqDTO extends GroupbuyingInfoReqDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6120671872089454923L;

	// 团购价格设置
	private List<GroupbuyingPriceSettingReqDTO> groupbuyingPriceSettingReqDTOList;
	// 活动信息
	private SinglePromotionInfoReqDTO singlePromotionInfoReqDTO;


	public SinglePromotionInfoReqDTO getSinglePromotionInfoReqDTO() {
		return singlePromotionInfoReqDTO;
	}

	public void setSinglePromotionInfoReqDTO(
			SinglePromotionInfoReqDTO singlePromotionInfoReqDTO) {
		this.singlePromotionInfoReqDTO = singlePromotionInfoReqDTO;
	}

	public List<GroupbuyingPriceSettingReqDTO> getGroupbuyingPriceSettingReqDTOList() {
		return groupbuyingPriceSettingReqDTOList;
	}

	public void setGroupbuyingPriceSettingReqDTOList(
			List<GroupbuyingPriceSettingReqDTO> groupbuyingPriceSettingReqDTOList) {
		this.groupbuyingPriceSettingReqDTOList = groupbuyingPriceSettingReqDTOList;
	}
	
	

}