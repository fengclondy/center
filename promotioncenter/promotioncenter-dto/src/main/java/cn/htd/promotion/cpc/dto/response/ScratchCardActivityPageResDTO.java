package cn.htd.promotion.cpc.dto.response;

import java.util.Date;
import java.util.List;

public class ScratchCardActivityPageResDTO extends GenricResDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3482022369003395494L;

	/**
	 * 活动名称
	 */
	private String promotionName;
	
	/**
	 * 活动开始时间
	 */
	private Date effectiveTime;
	
	/**
	 * 活动结束时间
	 */
	private Date invalidTime;
	
	/**
	 * 活动页图片路径
	 */
	private List<String> pictureUrl;

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public List<String> getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(List<String> pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
}
