package cn.htd.promotion.cpc.dto.response;

import java.util.Date;
import java.util.List;


public class LotteryActivityPageResDTO extends GenricResDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8823417622222146462L;

	/**
	 * 活动名称
	 */
	private String promotionName;
	
	/**
	 * 活动每天开始时间
	 */
	private Date activityStartTime;
	
	/**
	 * 活动每天结束时间
	 */
	private Date activityEndTime;
	
	/**
	 * 粉丝剩余抽奖次数
	 */
	private Integer remainingTimes;
	
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

	public Date getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public Date getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public Integer getRemainingTimes() {
		return remainingTimes;
	}

	public void setRemainingTimes(Integer remainingTimes) {
		this.remainingTimes = remainingTimes;
	}

	public List<String> getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(List<String> pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
}
