package cn.htd.promotion.cpc.dto.request;

import java.util.Date;

public class PromotionExtendInfoReqDTO  extends GenricReqDTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String promotionId;

    private Date offlineStartTime;

    private Date offlineEndTime;

    private String cycleTimeType;

    private String cycleTimeValue;

    private Date eachStartTime;

    private Date eachEndTime;

    private int isTotalTimesLimit;

    private Long totalPartakeTimes;

    private int isDailyTimesLimit;

    private Long dailyWinningTimes;

    private Long dailyBuyerPartakeTimes;

    private Long dailyBuyerWinningTimes;

    private int isShareTimesLimit;

    private Long shareExtraPartakeTimes;

    private Long topExtraPartakeTimes;

    private String contactName;

    private String contactTelephone;

    private String contactAddress;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

	/**
	 * 模版标志
	 */
	private Integer templateFlag;
	
    public Integer getTemplateFlag() {
		return templateFlag;
	}

	public void setTemplateFlag(Integer templateFlag) {
		this.templateFlag = templateFlag;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId == null ? null : promotionId.trim();
    }

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
        this.cycleTimeType = cycleTimeType == null ? null : cycleTimeType.trim();
    }

    public String getCycleTimeValue() {
        return cycleTimeValue;
    }

    public void setCycleTimeValue(String cycleTimeValue) {
        this.cycleTimeValue = cycleTimeValue == null ? null : cycleTimeValue.trim();
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

    public int getIsTotalTimesLimit() {
        return isTotalTimesLimit;
    }

    public void setIsTotalTimesLimit(int isTotalTimesLimit) {
        this.isTotalTimesLimit = isTotalTimesLimit;
    }

    public Long getTotalPartakeTimes() {
        return totalPartakeTimes;
    }

    public void setTotalPartakeTimes(Long totalPartakeTimes) {
        this.totalPartakeTimes = totalPartakeTimes;
    }

    public int getIsDailyTimesLimit() {
        return isDailyTimesLimit;
    }

    public void setIsDailyTimesLimit(int isDailyTimesLimit) {
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

    public int getIsShareTimesLimit() {
        return isShareTimesLimit;
    }

    public void setIsShareTimesLimit(int isShareTimesLimit) {
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
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone == null ? null : contactTelephone.trim();
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}