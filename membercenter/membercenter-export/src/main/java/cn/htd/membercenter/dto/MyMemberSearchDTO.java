package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 法人姓名
	 */
	private String artificialPersonName;
	/**
	 * 当前归属客户经理Id
	 */
	private String curBelongManagerId;
	/**
	 * 会员账号
	 */
	private String memberCode;
	/**
	 * 区域 - 省
	 */
	private String locationProvince;
	/**
	 * 区域 - 市
	 */
	private String locationCity;
	/**
	 * 区域 - 区/县
	 */
	private String locationCounty;
	
	/**
	 * VMS系统标志 - 用于区分新系统和老系统 1：新系统；其他：老系统
	 */
	private String sysFlag;
	
	/**
	 * 会员类型 1：非会员；2：会员；3：担保会员；
	 */
	private String memberType;

	/**
	 * 开始时间 - 字符串格式
	 */
	private String beginTime;
	/**
	 * 结束时间 - 字符串格式
	 */
	private String finishTime;
	
	/**
	 * 分页参数 - 当前页
	 */
	private Integer start;
	
	/**
	 * 分页参数 - 每页显示记录数
	 */
	private Integer length;
	
	private List<Long> memberIds;
	
	private String auditStatus;
	
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

	
	public String getHasBusinessLicense() {
		return hasBusinessLicense;
	}

	public void setHasBusinessLicense(String hasBusinessLicense) {
		this.hasBusinessLicense = hasBusinessLicense;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getLocationProvince() {
		return locationProvince;
	}

	public void setLocationProvince(String locationProvince) {
		this.locationProvince = locationProvince;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public String getLocationCounty() {
		return locationCounty;
	}

	public void setLocationCounty(String locationCounty) {
		this.locationCounty = locationCounty;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public List<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}
