package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.membercenter.common.annotation.VerifyDetail;

public class MemberOutsideSupplierCompanyDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 5324334861668728112L;

	private Long memberId;// 会员Id
	private String memberCode;// 会员Code
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_COMPANY_INFO", fieldId = "COMPANY_NAME", contentName = "公司名称")
	private String companyName;// 公司名称
	private Long curBelongSellerId;// 当前归属公司Id
	private Long belongSellerId;
	private String belongManagerId;
	private String curBelongManagerId;// 当前归属商家客户经理iD
	private String belongCompanyName;// 归属公司名称
	private String companyCode;// 公司编号 、商家编号
	@VerifyDetail(type = "DICTIONARIES", key = { "1", "2" }, value = { "外部供应商",
			"二代供应商" }, tableId = "MEMBER_BASE_INFO", fieldId = "SELLER_TYPE", contentName = "商家类型")
	private String sellerType;// 商家类型 1外部 2二代
	private String buyerSellerType;// 卖家买家类型
	private String businessCategory; // 经营范围
	private Integer isDiffIndustry;// 是否异业
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_COMPANY_INFO", fieldId = "ARTIFICIAL_PERSON_NAME", contentName = "公司法人姓名")
	private String artificialPersonName;// 法人名称
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_COMPANY_INFO", fieldId = "ARTIFICIAL_PERSON_IDCARD", contentName = "法人身份证号")
	private String artificialPersonIdcard;// 法人身份证号
	private String locationProvince;// 省
	private String locationCity;// 市
	private String locationCounty;// 区
	private String locationTown;// 镇
	private String locationAllAddress;// 商家地域
	private String locationDetail;// 详细地址
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_COMPANY_INFO", fieldId = "LOCATION_ADDR", contentName = "公司详细地址")
	private String locationAddr;// 公司地址
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_COMPANY_INFO", fieldId = "ARTIFICIAL_PERSON_PIC_SRC", contentName = "法人身份证照")
	private String artificialPersonPicSrc;// 法人身份证
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_COMPANY_INFO", fieldId = "ARTIFICIAL_PERSON_PIC_BACK_SRC", contentName = "法人身份证反面照")
	private String artificialPersonPicBackSrc;// 法人身份证 （反）
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_CONSIGNEE_ADDRESS_INFO", fieldId = "CONSIGNEE_AREA_CODE", contentName = "座机区号")
	private String areaCode;// 座机区号
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_CONSIGNEE_ADDRESS_INFO", fieldId = "CONSIGNEE_LANDLINE", contentName = "座机号码")
	private String landline;// 公司电话
	private String contactMobile;// 联系人电话
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_COMPANY_INFO", fieldId = "ARTIFICIAL_PERSON_MOBILE", contentName = "公司法人手机号码")
	private String artificialPersonMobile;// 法人手机号码
	private String contactName;// 联系人名称
	@VerifyDetail(type = "DICTIONARIES", key = { "1", "2" }, value = { "三证一号",
			"一证一号" }, tableId = "MEMBER_LICENCE_INFO", fieldId = "CERTIFICATE_TYPE", contentName = "证照类型")
	private String certificateType;// 证照类型
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_LICENCE_INFO", fieldId = "UNIFIED_SOCIAL_CREDIT_CODE", contentName = " 统一社会信用代码")
	private String unifiedSocialCreditCode;// 统一社会信用代码
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_LICENCE_INFO", fieldId = "BUSINESS_LICENSE_ID", contentName = "营业执照注册号")
	private String businessLicenseId;// 营业执照注册号
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_LICENCE_INFO", fieldId = "TAX_MAN_ID", contentName = "税务人识别号")
	private String taxManId;// 税务人识别号
	@VerifyDetail(type = "DICTIONARIES", key = { "1", "2", "3" }, value = { "一般纳税人", "小规模纳税人",
			"非增值税纳税人" }, tableId = "MEMBER_LICENCE_INFO", fieldId = "TAXPAYER_TYPE", contentName = "纳税人类别")
	private String taxpayerType;// 纳税人种类 1：一般纳税人，2：小规模纳税人，3：非增值税纳税人'
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_LICENCE_INFO", fieldId = "BUSINESS_LICENSE_PIC_SRC", contentName = "营业执照电子版图片z")
	private String businessLicensePicSrc;// 营业执照电子版图片
	private String organizationPicSrc;// 组织机构代码
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_LICENCE_INFO", fieldId = "TAX_REGISTRATION_CERTIFICATE_PIC_SRC", contentName = "税务登记证电子版图片")
	private String taxRegistrationCertificatePicSrc;// 税务登记证电子版图片
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_LICENCE_INFO", fieldId = "TAXPAYER_CERTIFICATE_PIC_SRC", contentName = "纳税人资格证电子版图片")
	private String taxpayerCertificatePicSrc;// 纳税人资格证电子版图片
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_EXTEND_INFO", fieldId = "BUSINESS_SCOPE", contentName = "经营范围")
	private String businessScope;// 经营范围

	@VerifyDetail(type = "DICTIONARIES", key = { "1", "0" }, value = { "是",
			"否" }, tableId = "MEMBER_EXTEND_INFO", fieldId = "IS_FINANCING", contentName = "是否有融资需求")
	private String isFinancing;// 是否有融资需求 0否 1有
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_EXTEND_INFO", fieldId = "FINANCING_NUMBER", contentName = "融资金额")
	private String financingNumber;// 融资金额
	@VerifyDetail(type = "DICTIONARIES", key = { "1", "2" }, value = { "品牌商",
			"经销商" }, tableId = "MEMBER_EXTEND_INFO", fieldId = "COMPANY_TYPE", contentName = "企业类型")
	private String companyType;// 企业类型
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_EXTEND_INFO", fieldId = "HOME_PAGE", contentName = "公司网站")
	private String homePage;// 公司网站
	@VerifyDetail(type = "DICTIONARIES", key = { "1", "0" }, value = { "是",
			"否" }, tableId = "MEMBER_EXTEND_INFO", fieldId = "IS_HAVE_EBUSINESS", contentName = "是否有电子商务经验")
	private String isHaveEbusiness;// 是否有电子商务经验
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_EXTEND_INFO", fieldId = "WEBSITE_OPERTION_NUMBER", contentName = "网站运营人数")
	private String websiteOpertionNumber;// 网站运营人数
	@VerifyDetail(type = "DICTIONARIES", key = { "1", "0" }, value = { "是",
			"否" }, tableId = "MEMBER_EXTEND_INFO", fieldId = "has_erp  ", contentName = " ERP有无")
	private String hasErp;// ERP有无
	private String salesVolumn;// 公司销售规模
	private String realNameStatus;// 企业实名认证状态:1成功，0失败
	private Long createId;// 创建人id
	private String createName;// 创建人名称
	private String buyerBusinessLicensePicSrc;// 会员营业执照地址

	private List<MemberContractInfo> contractList;// 合同列表

	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_BANK_INFO", fieldId = "BUSINESS_BANK_ACCOUNT_NAME", contentName = "银行开户名")
	private String bankAccountName;// 银行开户名
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_BANK_INFO", fieldId = "BUSINESS_BANK_ACCOUNT", contentName = "公司银行帐号")
	private String bankAccount;// 公司银行帐号
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_BANK_INFO", fieldId = "BUSINESS_BANK_NAME", contentName = "开户行支行名称")
	private String bankName;// 开户行支行名称
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_BANK_INFO", fieldId = "BUSINESS_BANK_BRANCH_JOINT_LINE", contentName = "开户行支行联行号")
	private String bankBranchJointLine;// 开户行支行联行号
	@VerifyDetail(type = "NORMAL", tableId = "MEMBER_BANK_INFO", fieldId = "BUSINESS_BANK_BRANCH_IS_LOCATED", contentName = "开户行支行所在地")
	private String bankBranchIsLocated;// 开户行支行所在地
	private String cardBindStatus;// 银行卡绑定状态
	private String bankProvince; // '所在地-省',
	private String bankCity; // '所在地-市',
	private String bankCounty; // '所在地-区',
	private String bankAllAddress;// 所在地详细地址

	private Long contractId;// 合同id
	private String contractCode;// 合同编号
	private String contractType;// 合同类型
	private String contractJssAddr;// 合同存储地址
	private String contractStarttime;// 合同生效开始时间
	private String contractEndtime;// 合同生效结束时间
	private String bindId;
	private String industryCategory;// 发展行业
	private String bankCode;// 银行简称
	private Integer canMallLogin;
	private String status;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	private String accountNo;

	public String getBankAllAddress() {
		return bankAllAddress;
	}

	public void setBankAllAddress(String bankAllAddress) {
		this.bankAllAddress = bankAllAddress;
	}

	public String getLocationTown() {
		return locationTown;
	}

	public void setLocationTown(String locationTown) {
		this.locationTown = locationTown;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankCounty() {
		return bankCounty;
	}

	public void setBankCounty(String bankCounty) {
		this.bankCounty = bankCounty;
	}

	public String getUnifiedSocialCreditCode() {
		return unifiedSocialCreditCode;
	}

	public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
		this.unifiedSocialCreditCode = unifiedSocialCreditCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractJssAddr() {
		return contractJssAddr;
	}

	public void setContractJssAddr(String contractJssAddr) {
		this.contractJssAddr = contractJssAddr;
	}

	public String getContractStarttime() {
		return contractStarttime;
	}

	public void setContractStarttime(String contractStarttime) {
		this.contractStarttime = contractStarttime;
	}

	public String getContractEndtime() {
		return contractEndtime;
	}

	public void setContractEndtime(String contractEndtime) {
		this.contractEndtime = contractEndtime;
	}

	public String getHasErp() {
		return hasErp;
	}

	public void setHasErp(String hasErp) {
		this.hasErp = hasErp;
	}

	public String getSalesVolumn() {
		return salesVolumn;
	}

	public void setSalesVolumn(String salesVolumn) {
		this.salesVolumn = salesVolumn;
	}

	public String getRealNameStatus() {
		return realNameStatus;
	}

	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}

	public String getCardBindStatus() {
		return cardBindStatus;
	}

	public void setCardBindStatus(String cardBindStatus) {
		this.cardBindStatus = cardBindStatus;
	}

	public String getLocationAllAddress() {
		return locationAllAddress;
	}

	public void setLocationAllAddress(String locationAllAddress) {
		this.locationAllAddress = locationAllAddress;
	}

	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	public Long getCurBelongSellerId() {
		return curBelongSellerId;
	}

	public void setCurBelongSellerId(Long curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getIsHaveEbusiness() {
		return isHaveEbusiness;
	}

	public void setIsHaveEbusiness(String isHaveEbusiness) {
		this.isHaveEbusiness = isHaveEbusiness;
	}

	public String getWebsiteOpertionNumber() {
		return websiteOpertionNumber;
	}

	public void setWebsiteOpertionNumber(String websiteOpertionNumber) {
		this.websiteOpertionNumber = websiteOpertionNumber;
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
		this.createName = createName;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
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

	public String getBankBranchJointLine() {
		return bankBranchJointLine;
	}

	public void setBankBranchJointLine(String bankBranchJointLine) {
		this.bankBranchJointLine = bankBranchJointLine;
	}

	public String getBankBranchIsLocated() {
		return bankBranchIsLocated;
	}

	public void setBankBranchIsLocated(String bankBranchIsLocated) {
		this.bankBranchIsLocated = bankBranchIsLocated;
	}

	public List<MemberContractInfo> getContractList() {
		return contractList;
	}

	public void setContractList(List<MemberContractInfo> contractList) {
		this.contractList = contractList;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBelongCompanyName() {
		return belongCompanyName;
	}

	public void setBelongCompanyName(String belongCompanyName) {
		this.belongCompanyName = belongCompanyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	/**
	 * @return the businessCategory
	 */
	public String getBusinessCategory() {
		return businessCategory;
	}

	/**
	 * @param businessCategory
	 *            the businessCategory to set
	 */
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getArtificialPersonIdcard() {
		return artificialPersonIdcard;
	}

	public void setArtificialPersonIdcard(String artificialPersonIdcard) {
		this.artificialPersonIdcard = artificialPersonIdcard;
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

	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
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

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getBusinessLicenseId() {
		return businessLicenseId;
	}

	public void setBusinessLicenseId(String businessLicenseId) {
		this.businessLicenseId = businessLicenseId;
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId;
	}

	public String getTaxpayerType() {
		return taxpayerType;
	}

	public void setTaxpayerType(String taxpayerType) {
		this.taxpayerType = taxpayerType;
	}

	public String getBusinessLicensePicSrc() {
		return businessLicensePicSrc;
	}

	public void setBusinessLicensePicSrc(String businessLicensePicSrc) {
		this.businessLicensePicSrc = businessLicensePicSrc;
	}

	public String getOrganizationPicSrc() {
		return organizationPicSrc;
	}

	public void setOrganizationPicSrc(String organizationPicSrc) {
		this.organizationPicSrc = organizationPicSrc;
	}

	public String getTaxRegistrationCertificatePicSrc() {
		return taxRegistrationCertificatePicSrc;
	}

	public void setTaxRegistrationCertificatePicSrc(String taxRegistrationCertificatePicSrc) {
		this.taxRegistrationCertificatePicSrc = taxRegistrationCertificatePicSrc;
	}

	public String getTaxpayerCertificatePicSrc() {
		return taxpayerCertificatePicSrc;
	}

	public void setTaxpayerCertificatePicSrc(String taxpayerCertificatePicSrc) {
		this.taxpayerCertificatePicSrc = taxpayerCertificatePicSrc;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getIsFinancing() {
		return isFinancing;
	}

	public void setIsFinancing(String isFinancing) {
		this.isFinancing = isFinancing;
	}

	public String getFinancingNumber() {
		return financingNumber;
	}

	public void setFinancingNumber(String financingNumber) {
		this.financingNumber = financingNumber;
	}

	public String getLocationAddr() {
		return locationAddr;
	}

	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
	}

	/**
	 * @return the buyerSellerType
	 */
	public String getBuyerSellerType() {
		return buyerSellerType;
	}

	/**
	 * @param buyerSellerType
	 *            the buyerSellerType to set
	 */
	public void setBuyerSellerType(String buyerSellerType) {
		this.buyerSellerType = buyerSellerType;
	}

	/**
	 * @return the canMallLogin
	 */
	public Integer getCanMallLogin() {
		return canMallLogin;
	}

	/**
	 * @param canMallLogin
	 *            the canMallLogin to set
	 */
	public void setCanMallLogin(Integer canMallLogin) {
		this.canMallLogin = canMallLogin;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the belongSellerId
	 */
	public Long getBelongSellerId() {
		return belongSellerId;
	}

	/**
	 * @param belongSellerId
	 *            the belongSellerId to set
	 */
	public void setBelongSellerId(Long belongSellerId) {
		this.belongSellerId = belongSellerId;
	}

	/**
	 * @return the belongManagerId
	 */
	public String getBelongManagerId() {
		return belongManagerId;
	}

	/**
	 * @param belongManagerId
	 *            the belongManagerId to set
	 */
	public void setBelongManagerId(String belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

	/**
	 * @return the bindId
	 */
	public String getBindId() {
		return bindId;
	}

	/**
	 * @param bindId
	 *            the bindId to set
	 */
	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	/**
	 * @return the industryCategory
	 */
	public String getIndustryCategory() {
		return industryCategory;
	}

	/**
	 * @param industryCategory
	 *            the industryCategory to set
	 */
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	/**
	 * @return the buyerBusinessLicensePicSrc
	 */
	public String getBuyerBusinessLicensePicSrc() {
		return buyerBusinessLicensePicSrc;
	}

	/**
	 * @param buyerBusinessLicensePicSrc
	 *            the buyerBusinessLicensePicSrc to set
	 */
	public void setBuyerBusinessLicensePicSrc(String buyerBusinessLicensePicSrc) {
		this.buyerBusinessLicensePicSrc = buyerBusinessLicensePicSrc;
	}

	/**
	 * @return the isDiffIndustry
	 */
	public Integer getIsDiffIndustry() {
		return isDiffIndustry;
	}

	/**
	 * @param isDiffIndustry
	 *            the isDiffIndustry to set
	 */
	public void setIsDiffIndustry(Integer isDiffIndustry) {
		this.isDiffIndustry = isDiffIndustry;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode
	 *            the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
