package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MyMemberDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long memberId;// 会员ID
	private String memberCode;// 会员ID
	private String memberName;// 会员名称
	private String companyName;// 公司名称
	private String bankAccount;// 银行账号
	private String bankName;// 开户行名称
	private String buyerGuaranteeLicensePicSrc;// 会员供应商担保证明电子版图片地址
	private String buyerBusinessLicensePicSrc;// 会员营业执照电子版图片地址
	private String qualificationDocuments;// 资质文件
	private String buyerFeature;// 会员属性，也是客商属性
	private Integer isDiffIndustry;// 是否异业：1是,0否
	private String industryCategory;// 行业类型名称
	private Integer isPhoneAuthenticated;// 是否有手机验证
	private Long sellerId;// 归属商家ID
	private String belongManagerId;// 归属客户经理ID
	private Long curBelongSellerId;// 当前归属商家ID
	private String curBelongManagerId;// 当前客户经理ID
	private String buyerGrade;// 会员等级
	private String groupName;// 会员分组
	private String status;// 有效会员

	private String belong;// 不为空则归属为是
	private String artificialPersonName;// 法人姓名
	private String artificialPersonMobile;// 法人电话
	private String artificialPersonIdcard;// 法人身份证号
	private String locationProvince;// 省
	private String locationCity;// 市
	private String locationCounty;// 区
	private String locationTown;// 镇
	private String locationDetail;// 详细地址
	private String registTime;// 注册时间
	private String locationAddr;// 会员地址

	private String diffIndustryName;// 是否异业
	private String phoneAuthenticatedName;// 是否有手机验证
	private String belongManagerName;// 客户经理

	private String buyerBusinessLicenseId;// 营业执照号
	private String businessLicenseCertificatePicSrc;// 营业执照变更证明图片地址
	private String artificialPersonIdcardPicSrc;// 法人手持身份证电子版图片地址

	private String belongStatus;
	private String verifyStatus;// 审批状态
	
	/**
	 * 发票抬头
	 */
	private String invoiceCompanyName;
	/**
	 * 发票电话
	 */
	private String contactPhone; 
	/**
	 * 发票地址
	 */
	private String invoiceAddress; 
	/**
	 * 纳税人识别号
	 */
	private String taxManId; 
	/**
	 * 法人身份证正面
	 */
	private String artificialPersonPicSrc;
	/**
	 * 法人身份证反面
	 */
	private String artificialPersonPicBackSrc;
	
	
	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBuyerFeature() {
		return buyerFeature;
	}

	public String getBuyerGuaranteeLicensePicSrc() {
		return buyerGuaranteeLicensePicSrc;
	}

	public void setBuyerGuaranteeLicensePicSrc(String buyerGuaranteeLicensePicSrc) {
		this.buyerGuaranteeLicensePicSrc = buyerGuaranteeLicensePicSrc;
	}

	public String getBuyerBusinessLicensePicSrc() {
		return buyerBusinessLicensePicSrc;
	}

	public void setBuyerBusinessLicensePicSrc(String buyerBusinessLicensePicSrc) {
		this.buyerBusinessLicensePicSrc = buyerBusinessLicensePicSrc;
	}

	public void setBuyerFeature(String buyerFeature) {
		this.buyerFeature = buyerFeature;
	}

	public String getQualificationDocuments() {
		return qualificationDocuments;
	}

	public void setQualificationDocuments(String qualificationDocuments) {
		this.qualificationDocuments = qualificationDocuments;
	}

	public Integer getIsDiffIndustry() {
		return isDiffIndustry;
	}

	public void setIsDiffIndustry(Integer isDiffIndustry) {
		this.isDiffIndustry = isDiffIndustry;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
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

	public String getArtificialPersonIdcard() {
		return artificialPersonIdcard;
	}

	public void setArtificialPersonIdcard(String artificialPersonIdcard) {
		this.artificialPersonIdcard = artificialPersonIdcard;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
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

	public String getBelongManagerId() {
		return belongManagerId;
	}

	public void setBelongManagerId(String belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

	public Long getCurBelongSellerId() {
		return curBelongSellerId;
	}

	public void setCurBelongSellerId(Long curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getDiffIndustryName() {
		return diffIndustryName;
	}

	public void setDiffIndustryName(String diffIndustryName) {
		this.diffIndustryName = diffIndustryName;
	}

	public String getPhoneAuthenticatedName() {
		return phoneAuthenticatedName;
	}

	public void setPhoneAuthenticatedName(String phoneAuthenticatedName) {
		this.phoneAuthenticatedName = phoneAuthenticatedName;
	}

	public String getBelongManagerName() {
		return belongManagerName;
	}

	public void setBelongManagerName(String belongManagerName) {
		this.belongManagerName = belongManagerName;
	}

	public String getBusinessLicenseCertificatePicSrc() {
		return businessLicenseCertificatePicSrc;
	}

	public void setBusinessLicenseCertificatePicSrc(String businessLicenseCertificatePicSrc) {
		this.businessLicenseCertificatePicSrc = businessLicenseCertificatePicSrc;
	}

	public String getArtificialPersonIdcardPicSrc() {
		return artificialPersonIdcardPicSrc;
	}

	public void setArtificialPersonIdcardPicSrc(String artificialPersonIdcardPicSrc) {
		this.artificialPersonIdcardPicSrc = artificialPersonIdcardPicSrc;
	}

	public String getBelongStatus() {
		return belongStatus;
	}

	public void setBelongStatus(String belongStatus) {
		this.belongStatus = belongStatus;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getBuyerBusinessLicenseId() {
		return buyerBusinessLicenseId;
	}


	public void setBuyerBusinessLicenseId(String buyerBusinessLicenseId) {
		this.buyerBusinessLicenseId = buyerBusinessLicenseId;
	}

	public String getInvoiceCompanyName() {
		return invoiceCompanyName;
	}

	public void setInvoiceCompanyName(String invoiceCompanyName) {
		this.invoiceCompanyName = invoiceCompanyName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId;
	}

	public String getArtificialPersonPicSrc() {
		return artificialPersonPicSrc;
	}

	public void setArtificialPersonPicSrc(String artificialPersonPicSrc) {
		this.artificialPersonPicSrc = artificialPersonPicSrc;
	}

	public String getArtificialPersonPicBackSrc() {
		return artificialPersonPicBackSrc;
	}

	public void setArtificialPersonPicBackSrc(String artificialPersonPicBackSrc) {
		this.artificialPersonPicBackSrc = artificialPersonPicBackSrc;
	}
	
	

}
