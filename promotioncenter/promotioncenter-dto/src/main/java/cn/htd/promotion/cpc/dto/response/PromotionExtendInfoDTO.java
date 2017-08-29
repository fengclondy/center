package cn.htd.promotion.cpc.dto.response;

import java.util.Date;

/**
 * 促销活动扩展信息DTO
 */
public class PromotionExtendInfoDTO extends PromotionInfoDTO {

	private Date offlineStartTime;//到店购买开始时间
	
	private Date offlineEndTime;//到店购买结束时间
	
	private String cycleTimeType;//周期时间类型
	
	private String cycleTimeValue;//周期时间值 

	private Date eachStartTime;//周期活动开始时间

	private Date eachEndTime;//周期活动结束时间

	private Integer isTotalTimesLimit;//是否有总次数限制

	private Long totalPartakeTimes;//参与总次数
	
	private Integer isDailyTimesLimit;//是否有每日次数限制
	
	private Long dailyWinningTimes;//每日中奖总次数
	
	private Long dailyBuyerPartakeTimes;//每会员每日参与次数
	
	private Long dailyBuyerWinningTimes;//每会员每日中奖次数
	
	private Integer isShareTimesLimit;//是否有分享次数
	
	private Long shareExtraPartakeTimes;//每次分享获得额外参与次数
	
	private Long topExtraPartakeTimes;//最高可获的参与次数

	private String contactName;//联系人名称
	
	private String contactTelephone;//联系电话
	
	private String contactAddress;//联系地址

	/**
	 * 模版标志
	 */
	private Integer templateFlag;

	public Date getOfflineStartTime() {
		return offlineStartTime;
	}

	public void setOfflineStartTime(Date offlineStartTime) {
		this.offlineStartTime = offlineStartTime;
	}

	public Date getOfflineEndTime() {
		return offlineEndTime;
	}

	public void setOfflineEndTime(Date offlineEndTime) {
		this.offlineEndTime = offlineEndTime;
	}

	public String getCycleTimeType() {
		return cycleTimeType;
	}

	public void setCycleTimeType(String cycleTimeType) {
		this.cycleTimeType = cycleTimeType;
	}

	public String getCycleTimeValue() {
		return cycleTimeValue;
	}

	public void setCycleTimeValue(String cycleTimeValue) {
		this.cycleTimeValue = cycleTimeValue;
	}

	public Date getEachStartTime() {
		return eachStartTime;
	}

	public void setEachStartTime(Date eachStartTime) {
		this.eachStartTime = eachStartTime;
	}

	public Date getEachEndTime() {
		return eachEndTime;
	}

	public void setEachEndTime(Date eachEndTime) {
		this.eachEndTime = eachEndTime;
	}

	public Integer getIsTotalTimesLimit() {
		return isTotalTimesLimit;
	}

	public void setIsTotalTimesLimit(Integer isTotalTimesLimit) {
		this.isTotalTimesLimit = isTotalTimesLimit;
	}

	public Long getTotalPartakeTimes() {
		return totalPartakeTimes;
	}

	public void setTotalPartakeTimes(Long totalPartakeTimes) {
		this.totalPartakeTimes = totalPartakeTimes;
	}

	public Integer getIsDailyTimesLimit() {
		return isDailyTimesLimit;
	}

	public void setIsDailyTimesLimit(Integer isDailyTimesLimit) {
		this.isDailyTimesLimit = isDailyTimesLimit;
	}

	public Long getDailyWinningTimes() {
		return dailyWinningTimes;
	}

	public void setDailyWinningTimes(Long dailyWinningTimes) {
		this.dailyWinningTimes = dailyWinningTimes;
	}

	public Long getDailyBuyerPartakeTimes() {
		return dailyBuyerPartakeTimes;
	}

	public void setDailyBuyerPartakeTimes(Long dailyBuyerPartakeTimes) {
		this.dailyBuyerPartakeTimes = dailyBuyerPartakeTimes;
	}

	public Long getDailyBuyerWinningTimes() {
		return dailyBuyerWinningTimes;
	}

	public void setDailyBuyerWinningTimes(Long dailyBuyerWinningTimes) {
		this.dailyBuyerWinningTimes = dailyBuyerWinningTimes;
	}

	public Integer getIsShareTimesLimit() {
		return isShareTimesLimit;
	}

	public void setIsShareTimesLimit(Integer isShareTimesLimit) {
		this.isShareTimesLimit = isShareTimesLimit;
	}

	public Long getShareExtraPartakeTimes() {
		return shareExtraPartakeTimes;
	}

	public void setShareExtraPartakeTimes(Long shareExtraPartakeTimes) {
		this.shareExtraPartakeTimes = shareExtraPartakeTimes;
	}

	public Long getTopExtraPartakeTimes() {
		return topExtraPartakeTimes;
	}

	public void setTopExtraPartakeTimes(Long topExtraPartakeTimes) {
		this.topExtraPartakeTimes = topExtraPartakeTimes;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public Integer getTemplateFlag() {
		return templateFlag;
	}

	public void setTemplateFlag(Integer templateFlag) {
		this.templateFlag = templateFlag;
	}

	public void setPromoionExtendInfo(PromotionExtendInfoDTO extendDTO){
		super.setPromoionInfo(extendDTO);
		this.offlineStartTime = extendDTO.getOfflineStartTime();
		this.offlineEndTime = extendDTO.getOfflineEndTime();
		this.cycleTimeType = extendDTO.getCycleTimeType();
		this.cycleTimeValue = extendDTO.getCycleTimeValue();
		this.eachStartTime = extendDTO.getEachStartTime();
		this.eachEndTime = extendDTO.getEachEndTime();
		this.isTotalTimesLimit = extendDTO.getIsTotalTimesLimit();
		this.totalPartakeTimes = extendDTO.getTotalPartakeTimes();
		this.isDailyTimesLimit = extendDTO.getIsDailyTimesLimit();
		this.dailyWinningTimes = extendDTO.getDailyBuyerWinningTimes();
		this.dailyBuyerPartakeTimes = extendDTO.getDailyBuyerPartakeTimes();
		this.dailyBuyerWinningTimes = extendDTO.getDailyWinningTimes();
		this.isShareTimesLimit = extendDTO.getIsShareTimesLimit();
		this.shareExtraPartakeTimes = extendDTO.getShareExtraPartakeTimes();
		this.topExtraPartakeTimes = extendDTO.getTopExtraPartakeTimes();
		this.contactName = extendDTO.getContactName();
		this.contactTelephone = extendDTO.getContactTelephone();
		this.contactAddress = extendDTO.getContactAddress();
		this.templateFlag = extendDTO.getTemplateFlag();
	}
}
