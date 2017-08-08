package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MyMemberSearchDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stringName;// 根据公司名称或者法人名称查询
	private Date startTime; // 起始时间
	private Date endTime;// 截止时间
	private String belong;// 是否归属
	private String status;// 有效
	private String buyerFeature;// 会员属性
	private String groupName;// 分组
	private String groupId;// 分组ID
	private String industryCategory;// 是否异业
	private Integer isPhoneAuthenticated;// 是否有手机验证
	private String buyerGrade;// 等级
	private String canMallLogin;// 是否登录商城
	private String hasGuaranteeLicense;// 是否有担保证明
	private String hasBusinessLicense;// 是否有营业执照

	public String getStringName() {
		return stringName;
	}

	public void setStringName(String stringName) {
		this.stringName = stringName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBuyerFeature() {
		return buyerFeature;
	}

	public void setBuyerFeature(String buyerFeature) {
		this.buyerFeature = buyerFeature;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public Integer getIsPhoneAuthenticated() {
		return isPhoneAuthenticated;
	}

	public void setIsPhoneAuthenticated(Integer isPhoneAuthenticated) {
		this.isPhoneAuthenticated = isPhoneAuthenticated;
	}

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	public String getCanMallLogin() {
		return canMallLogin;
	}

	public void setCanMallLogin(String canMallLogin) {
		this.canMallLogin = canMallLogin;
	}

	public String getHasGuaranteeLicense() {
		return hasGuaranteeLicense;
	}

	public void setHasGuaranteeLicense(String hasGuaranteeLicense) {
		this.hasGuaranteeLicense = hasGuaranteeLicense;
	}

	public String getHasbussinessLicense() {
		return hasBusinessLicense;
	}

	public void setHasbussinessLicense(String hasBusinessLicense) {
		this.hasBusinessLicense = hasBusinessLicense;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
