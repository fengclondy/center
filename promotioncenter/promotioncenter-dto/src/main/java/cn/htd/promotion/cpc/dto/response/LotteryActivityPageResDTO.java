package cn.htd.promotion.cpc.dto.response;


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
	private String activityStartTime;
	
	/**
	 * 活动每天结束时间
	 */
	private String activityEndTime;
	
	/**
	 * 粉丝剩余抽奖次数
	 */
	private String remainingTimes;
	
	/**
	 * 活动页图片路径
	 */
	private String pictureUrl;

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public String getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public String getRemainingTimes() {
		return remainingTimes;
	}

	public void setRemainingTimes(String remainingTimes) {
		this.remainingTimes = remainingTimes;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
}
