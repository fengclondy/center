package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * 
 * <p>
 * Copyright (C); 2013-2016; 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MemberBuyerPersonalInfo
 * </p>
 * 
 * @author root
 * @date 2016年12月26日
 *       <p>
 *       Description: 买家中心 会员个人信息
 *       </p>
 */
public class MemberBuyerPersonalInfoDTO implements Serializable {

	private static final long serialVersionUID = 146823089780840426L;

	private Long memberId;// 会员ID
	private String memberCode;// 会员账号
	private String memberType;// 会员类型
	private String buyerGuaranteeLicensePicSrc;// 担保证明
	private String artificialPersonName;// 法人名称
	private String artificialPersonMobile;// 法人手机号
	private String companyName;// 公司名称
	private String birthday;// 出生日期
	private String birthdayYear;// 出生日期-年
	private String birthdayMonth;// 出生日期-月
	private String birthdayDay;// 出生日期-天
	private String locationProvince;// 省 编码
	private String locationCity;// 市 编码
	private String locationCounty;// 区 编码
	private String locationTown;// 镇 编码
	private String locationProvinceString;// 省 名称
	private String locationCityString;// 市 名称
	private String locationCountyString;// 区 名称
	private String locationTownString;// 镇 名称
	private String locationDetail;// 详细地址
	private String verifyStatus;// 审核状态
	private String remark;// 审核意见
	private String buyerBusinessLicenseId;// 会员营业执照注册号
	private String businessLicenseCertificatePicSrc;// 营业执照变更证明
	private String buyerBusinessLicensePicSrc;// 营业执照附件
	private Long modifyId;// 修改人id \创建人id
	private String modifyName;// 修改人姓名 \创建人名称
	private Integer has_guarantee_license;// 是否有担保证明
	private Integer has_business_license;// 是否有营业执照
	private Integer can_mall_login;// 是否登陆商城
	private String locationAddr;// 详细地址

	public String getBuyerGuaranteeLicensePicSrc() {
		return buyerGuaranteeLicensePicSrc;
	}

	public void setBuyerGuaranteeLicensePicSrc(String buyerGuaranteeLicensePicSrc) {
		this.buyerGuaranteeLicensePicSrc = buyerGuaranteeLicensePicSrc;
	}

	public Integer getHas_guarantee_license() {
		return has_guarantee_license;
	}

	public void setHas_guarantee_license(Integer has_guarantee_license) {
		this.has_guarantee_license = has_guarantee_license;
	}

	public Integer getHas_business_license() {
		return has_business_license;
	}

	public void setHas_business_license(Integer has_business_license) {
		this.has_business_license = has_business_license;
	}

	public Integer getCan_mall_login() {
		return can_mall_login;
	}

	public void setCan_mall_login(Integer can_mall_login) {
		this.can_mall_login = can_mall_login;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLocationProvinceString() {
		return locationProvinceString;
	}

	public void setLocationProvinceString(String locationProvinceString) {
		this.locationProvinceString = locationProvinceString;
	}

	public String getLocationCityString() {
		return locationCityString;
	}

	public void setLocationCityString(String locationCityString) {
		this.locationCityString = locationCityString;
	}

	public String getLocationCountyString() {
		return locationCountyString;
	}

	public void setLocationCountyString(String locationCountyString) {
		this.locationCountyString = locationCountyString;
	}

	public String getLocationTownString() {
		return locationTownString;
	}

	public void setLocationTownString(String locationTownString) {
		this.locationTownString = locationTownString;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
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
		this.modifyName = modifyName;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthdayYear() {
		return birthdayYear;
	}

	public void setBirthdayYear(String birthdayYear) {
		this.birthdayYear = birthdayYear;
	}

	public String getBirthdayMonth() {
		return birthdayMonth;
	}

	public void setBirthdayMonth(String birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}

	public String getBirthdayDay() {
		return birthdayDay;
	}

	public void setBirthdayDay(String birthdayDay) {
		this.birthdayDay = birthdayDay;
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

	public String getLocationTown() {
		return locationTown;
	}

	public void setLocationTown(String locationTown) {
		this.locationTown = locationTown;
	}

	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	public String getBuyerBusinessLicenseId() {
		return buyerBusinessLicenseId;
	}

	public void setBuyerBusinessLicenseId(String buyerBusinessLicenseId) {
		this.buyerBusinessLicenseId = buyerBusinessLicenseId;
	}

	public String getBusinessLicenseCertificatePicSrc() {
		return businessLicenseCertificatePicSrc;
	}

	public void setBusinessLicenseCertificatePicSrc(String businessLicenseCertificatePicSrc) {
		this.businessLicenseCertificatePicSrc = businessLicenseCertificatePicSrc;
	}

	public String getBuyerBusinessLicensePicSrc() {
		return buyerBusinessLicensePicSrc;
	}

	public void setBuyerBusinessLicensePicSrc(String buyerBusinessLicensePicSrc) {
		this.buyerBusinessLicensePicSrc = buyerBusinessLicensePicSrc;
	}

	/**
	 * @return the locationAddr
	 */
	public String getLocationAddr() {
		return locationAddr;
	}

	/**
	 * @param locationAddr
	 *            the locationAddr to set
	 */
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

}
