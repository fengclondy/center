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
	// 活动状态 [1.未开始,2.开团进行中,3.下单未开始,4.下单进行中,5.已结束,-1.无效活动]
	private String activeState;
	// 活动状态文本
//	private String activeStateText;
	// 下订单数量
	private Integer orderQuantity;

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

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}
	

	public String getActiveStateText() {
		String activeStateText = null;
		// 活动状态 [1.未开始,2.开团进行中,3.下单未开始,4.下单进行中,5.已结束,-1.无效活动]
		switch (getActiveState()) {
		case "1":
			activeStateText = "未开始";
			break;
		case "2":
			activeStateText = "开团进行中";
			break;
		case "3":
			activeStateText = "下单未开始";
			break;
		case "4":
			activeStateText = "下单进行中";
			break;
		case "5":
			activeStateText = "已结束";
			break;
		case "-1":
			activeStateText = "无效活动";
			break;
		default:
			activeStateText = null;
			break;
		}
		return activeStateText;
	}

	

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

}