package cn.htd.marketcenter.domain;

import java.util.List;

/**
 * 秒杀订单数据
 */
public class TimelimitedCheckInfo {
	/**
	 * 促销活动类型
	 */
	private String promotionType;
	/**
	 * 删除状态
	 */
	private String deleteStatus;
	/**
	 * 秒杀活动商品编码
	 */
	private String skuCode;
	
	/**
	 * 秒杀活动展示状态
	 */
	private String showStatus;
	
	/**
	 * 促销活动类型
	 */
	private List<String> promotionTypeList;

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public List<String> getPromotionTypeList() {
		return promotionTypeList;
	}

	public void setPromotionTypeList(List<String> promotionTypeList) {
		this.promotionTypeList = promotionTypeList;
	}
	
}