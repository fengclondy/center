package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MyNoMemberDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long memberId;// 会员ID
	private String memberName;// 会员名称
	private String memberCode;// 会员编码
	private String invoiceCompanyName;
	private String invoiceNotify;// 发票抬头
	private String taxManId;// 纳税人识别号
	private String invoiceAddress;// 发票地址
	private String contactPhone;// 发票联系电话
	private String bankName;// 开户行名称
	private String bankAccount;// 银行账号
	private String buyerFeature;// 会员属性，也是客商属性
	private Long sellerId;// 归属商家ID
	private String belongManagerId;// 归属客户经理ID
	private Long curBelongSellerId;// 当前归属商家ID
	private String curBelongManagerId;// 当前客户经理ID
	private String belong;// 不为空则归属为是
	private String registTime;// 注册时间
	private String verifyStatus;// 会员审核状态
	private String companyName;// 公司名称，会员名称
	private String companyCode;// 公司编号，会员/供应商的ERP编码
	private Integer isDiffIndustry;// 是否异业：1是,0否
	private String industryCategory;// 行业类型名称
	private Integer isPhoneAuthenticated;// 是否有手机验证
	private String buyerGrade;// 会员等级
	private String groupName;// 会员分组
	private String tranceRemark;// 非会员砖会员驳回原因
	private String status;// 有效会员
	private String contactName;// 联系人姓名
	private String artificialPersonName;// 法人姓名
	private String artificialPersonMobile;// 法人电话
	private String artificialPersonIdcard;// 法人身份证号
	private String artificialPersonPicSrc;// 法人身份证电子版图片地址
	private String artificialPersonPicBackSrc;// 法人身份证电子版图片地址(反面)
	private Integer canMallLogin;// 是否登陆商城
	private Integer hasGuaranteeLicense;// 是否有担保证明
	private Integer hasBusinessLicense;// 是否有营业执照
	private String companyNameModifyStatus;// 名称修改状态

	private String remark;
	private String address;// 注册地址
	private String locationProvince;// 注册省份
	private String locationCity;// 注册所在城市
	private String locationCounty;// 注册所在区
	private String locationTown;// 注册所在镇
	private String locationDetail;// 注册详细地址

	private Long modifyId;// 修改人ID
	private String modifyName;// 修改人
	private String locationAddr;// 会员地址

	private String buyerGuaranteeLicensePicSrc;// 供应商担保证明电子版图片地址
	private String buyerBusinessLicensePicSrc;// 会员营业执照电子版图片地址
	private String buyerBusinessLicenseId;// 会员营业执照号

	private String memberType;// 会员类型
	private String erpStatus;// ERP下行状态

	private String belongStatus;// 会员类型\

	private String diffIndustryName;// 是否异业：1是,0否
	private String phoneAuthenticatedName;// 是否有手机验证
	private String curBelongManagerName;// 当前客户经理名称
	private Long verifyId;// 审批ID
	private String infoType;// 信息类型
	private String verifyStatusTranse;// 非会员转正状态
	private String channelCode;// 外接渠道编码
	private String modifyType;// 修改类型，1：修改非会员信息重新提交运营审核，2：修改非会员基本信息
	private String newCompanyName;// 修改后公司名称

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public Long getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Long verifyId) {
		this.verifyId = verifyId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public String getInvoiceNotify() {
		return invoiceNotify;
	}

	public void setInvoiceNotify(String invoiceNotify) {
		this.invoiceNotify = invoiceNotify;
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBuyerFeature() {
		return buyerFeature;
	}

	public void setBuyerFeature(String buyerFeature) {
		this.buyerFeature = buyerFeature;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
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

	public Integer getCanMallLogin() {
		return canMallLogin;
	}

	public void setCanMallLogin(Integer canMallLogin) {
		this.canMallLogin = canMallLogin;
	}

	public Integer getHasGuaranteeLicense() {
		return hasGuaranteeLicense;
	}

	public void setHasGuaranteeLicense(Integer hasGuaranteeLicense) {
		this.hasGuaranteeLicense = hasGuaranteeLicense;
	}

	public Integer getHasBusinessLicense() {
		return hasBusinessLicense;
	}

	public void setHasBusinessLicense(Integer hasBusinessLicense) {
		this.hasBusinessLicense = hasBusinessLicense;
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

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getBuyerBusinessLicenseId() {
		return buyerBusinessLicenseId;
	}

	public void setBuyerBusinessLicenseId(String buyerBusinessLicenseId) {
		this.buyerBusinessLicenseId = buyerBusinessLicenseId;
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

	public String getCurBelongManagerName() {
		return curBelongManagerName;
	}

	public void setCurBelongManagerName(String curBelongManagerName) {
		this.curBelongManagerName = curBelongManagerName;
	}

	/**
	 * @return the verifyStatusTranse
	 */
	public String getVerifyStatusTranse() {
		return verifyStatusTranse;
	}

	/**
	 * @param verifyStatusTranse
	 *            the verifyStatusTranse to set
	 */
	public void setVerifyStatusTranse(String verifyStatusTranse) {
		this.verifyStatusTranse = verifyStatusTranse;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * @return the modifyType
	 */
	public String getModifyType() {
		return modifyType;
	}

	/**
	 * @param modifyType
	 *            the modifyType to set
	 */
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	/**
	 * @return the invoiceCompanyName
	 */
	public String getInvoiceCompanyName() {
		return invoiceCompanyName;
	}

	/**
	 * @param invoiceCompanyName
	 *            the invoiceCompanyName to set
	 */
	public void setInvoiceCompanyName(String invoiceCompanyName) {
		this.invoiceCompanyName = invoiceCompanyName;
	}

	/**
	 * @return the tranceRemark
	 */
	public String getTranceRemark() {
		return tranceRemark;
	}

	/**
	 * @param tranceRemark
	 *            the tranceRemark to set
	 */
	public void setTranceRemark(String tranceRemark) {
		this.tranceRemark = tranceRemark;
	}

	/**
	 * @return the belongStatus
	 */
	public String getBelongStatus() {
		return belongStatus;
	}

	/**
	 * @param belongStatus
	 *            the belongStatus to set
	 */
	public void setBelongStatus(String belongStatus) {
		this.belongStatus = belongStatus;
	}

	/**
	 * @return the newCompanyName
	 */
	public String getNewCompanyName() {
		return newCompanyName;
	}

	/**
	 * @param newCompanyName
	 *            the newCompanyName to set
	 */
	public void setNewCompanyName(String newCompanyName) {
		this.newCompanyName = newCompanyName;
	}

	/**
	 * @return the companyNameModifyStatus
	 */
	public String getCompanyNameModifyStatus() {
		return companyNameModifyStatus;
	}

	/**
	 * @param companyNameModifyStatus
	 *            the companyNameModifyStatus to set
	 */
	public void setCompanyNameModifyStatus(String companyNameModifyStatus) {
		this.companyNameModifyStatus = companyNameModifyStatus;
	}

}
