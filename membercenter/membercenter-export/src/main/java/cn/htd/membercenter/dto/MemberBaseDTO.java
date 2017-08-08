package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * member_base_info 实体类 Tue Nov 15 14:07:18 CST 2016 haozy
 */
public class MemberBaseDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String memberId;
	// 是否商城登陆
	private String canMallLogin;
	// 是否有担保证明
	private String hasGuaranteeLicense;
	// 是否有营业执照
	private String hasBusinessLicense;
	// 会员类型: 1：非会员，3：担保会员 ，2：正式会员
	private String memberType;
	// 是否是买家
	private String isBuyer;
	// 公司名称
	private String companyName;
	// 公司类型
	private String companyType;
	// 是否一般纳税人
	private String taxmanFlag;
	// 公司办公地址
	private String companyWorkAddress;
	// 公司注册地址所在省份
	private String locationProvince;
	// 公司注册地址所在城市
	private String locationCity;
	// 公司注册地址所在区
	private String locationCounty;
	// 公司注册地址所在镇
	private String locationTown;
	// 公司注册详细地址
	private String locationDetail;
	// 公司注册地址
	private String registerAddress;
	// 销售规模
	private String salesVolumn;
	// 经营范围
	private String businessScope;
	// 公司法人/自然人
	private String artificialPersonName;
	// 法人手机号
	private String artificialPersonMobile;
	// 业务联系人
	private String contactName;
	// 联系电话
	private String contactMobile;
	// 邮箱
	private String contactEmail;
	// 经营类目
	private String businessCategory;
	// 主营类目
	private String majorBusinessCategory;
	// 经营品牌
	private String businessBrand;
	// 经营年限
	private String operatingLife;
	// 主营性质
	private String vendorNature;
	// 授权链
	private String chainOfAuthorize;
	// 是否其他平台经营
	private String isUseOtherPlatform;
	// 营业执照编号
	private String businessLicensePicSrc;
	// 组织机构代码证
	private String organizationPicSrc;
	// 税务登记证
	private String taxRegistrationCertificatePicSrc;
	// 一般纳税人证明
	private String taxpayerCertificatePicSrc;

	private String buyerGrade;

	private String isVip;

	// 会员套餐类型 1：VIP套餐 2：智慧门店套餐
	private String memberPackageType;
	// 套餐生效开始时间
	private Date packageActiveStartTime;
	// 套餐生效结束时间
	private Date packageActiveEndTime;
	// 套餐更新时间
	private Date packageUpdateTime;
	// 默认查询条件
	private String searchCondition;
	// 默认查询类型：1、查询会员编码 2、查询公司名称 3、查询法人姓名
	private String searchType;
	// 商家编码
	private String sellerId;
	// 收货地址
	private String receiptAddress;
	// 账号类型
	private String accountType;

	private String phoneNumber;

	private List<MemberBusinessRelationDTO> relationList;
	// 最后一次登陆时间
	private Date lastLoginTime;
	// ERP编码
	private String companyCode;
	// 会员/商家类型 1：会员，2：商家
	private String buyerSellerType;
	// 商家编码
	private String memberCode;
	// 商家类型 1:内部供应商，2:外部供应商
	private String sellerType;
	// 供应商担保证明电子版图片地址
	private String buyerGuaranteeLicensePicSrc;
	// 会员营业执照电子版图片地址
	private String buyerBusinessLicensePicSrc;
	// 会员营业执照变更证明图片地址
	private String businessLicenseCertificatePicSrc;
	// 变更会员等级套餐等类型:1: 会员等级规则变更操作 2:会员保底经验值变更操作 3:会员权重值变更操作 4:保底经验值变更
	// 5：会员套餐类型变更操作 6：会员套餐时间变更操作
	private String afterGrade;

	// 归属商家编码
	private String curBelongSellerId;
	// 归属商家名称
	private String curBelongSellerName;
	private String curBelongMemberCode;

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the canMallLogin
	 */
	public String getCanMallLogin() {
		return canMallLogin;
	}

	/**
	 * @param canMallLogin
	 *            the canMallLogin to set
	 */
	public void setCanMallLogin(String canMallLogin) {
		this.canMallLogin = canMallLogin;
	}

	/**
	 * @return the hasGuaranteeLicense
	 */
	public String getHasGuaranteeLicense() {
		return hasGuaranteeLicense;
	}

	/**
	 * @param hasGuaranteeLicense
	 *            the hasGuaranteeLicense to set
	 */
	public void setHasGuaranteeLicense(String hasGuaranteeLicense) {
		this.hasGuaranteeLicense = hasGuaranteeLicense;
	}

	/**
	 * @return the hasBusinessLicense
	 */
	public String getHasBusinessLicense() {
		return hasBusinessLicense;
	}

	/**
	 * @param hasBusinessLicense
	 *            the hasBusinessLicense to set
	 */
	public void setHasBusinessLicense(String hasBusinessLicense) {
		this.hasBusinessLicense = hasBusinessLicense;
	}

	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType
	 *            the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the isBuyer
	 */
	public String getIsBuyer() {
		return isBuyer;
	}

	/**
	 * @param isBuyer
	 *            the isBuyer to set
	 */
	public void setIsBuyer(String isBuyer) {
		this.isBuyer = isBuyer;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the companyType
	 */
	public String getCompanyType() {
		return companyType;
	}

	/**
	 * @param companyType
	 *            the companyType to set
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	/**
	 * @return the taxmanFlag
	 */
	public String getTaxmanFlag() {
		return taxmanFlag;
	}

	/**
	 * @param taxmanFlag
	 *            the taxmanFlag to set
	 */
	public void setTaxmanFlag(String taxmanFlag) {
		this.taxmanFlag = taxmanFlag;
	}

	/**
	 * @return the companyWorkAddress
	 */
	public String getCompanyWorkAddress() {
		return companyWorkAddress;
	}

	/**
	 * @param companyWorkAddress
	 *            the companyWorkAddress to set
	 */
	public void setCompanyWorkAddress(String companyWorkAddress) {
		this.companyWorkAddress = companyWorkAddress;
	}

	/**
	 * @return the locationProvince
	 */
	public String getLocationProvince() {
		return locationProvince;
	}

	/**
	 * @param locationProvince
	 *            the locationProvince to set
	 */
	public void setLocationProvince(String locationProvince) {
		this.locationProvince = locationProvince;
	}

	/**
	 * @return the locationCity
	 */
	public String getLocationCity() {
		return locationCity;
	}

	/**
	 * @param locationCity
	 *            the locationCity to set
	 */
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	/**
	 * @return the locationCounty
	 */
	public String getLocationCounty() {
		return locationCounty;
	}

	/**
	 * @param locationCounty
	 *            the locationCounty to set
	 */
	public void setLocationCounty(String locationCounty) {
		this.locationCounty = locationCounty;
	}

	/**
	 * @return the locationTown
	 */
	public String getLocationTown() {
		return locationTown;
	}

	/**
	 * @param locationTown
	 *            the locationTown to set
	 */
	public void setLocationTown(String locationTown) {
		this.locationTown = locationTown;
	}

	/**
	 * @return the locationDetail
	 */
	public String getLocationDetail() {
		return locationDetail;
	}

	/**
	 * @param locationDetail
	 *            the locationDetail to set
	 */
	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	/**
	 * @return the registerAddress
	 */
	public String getRegisterAddress() {
		return registerAddress;
	}

	/**
	 * @param registerAddress
	 *            the registerAddress to set
	 */
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	/**
	 * @return the salesVolumn
	 */
	public String getSalesVolumn() {
		return salesVolumn;
	}

	/**
	 * @param salesVolumn
	 *            the salesVolumn to set
	 */
	public void setSalesVolumn(String salesVolumn) {
		this.salesVolumn = salesVolumn;
	}

	/**
	 * @return the businessScope
	 */
	public String getBusinessScope() {
		return businessScope;
	}

	/**
	 * @param businessScope
	 *            the businessScope to set
	 */
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	/**
	 * @return the artificialPersonName
	 */
	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	/**
	 * @param artificialPersonName
	 *            the artificialPersonName to set
	 */
	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName
	 *            the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactMobile
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	/**
	 * @param contactMobile
	 *            the contactMobile to set
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail
	 *            the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
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
	 * @return the majorBusinessCategory
	 */
	public String getMajorBusinessCategory() {
		return majorBusinessCategory;
	}

	/**
	 * @param majorBusinessCategory
	 *            the majorBusinessCategory to set
	 */
	public void setMajorBusinessCategory(String majorBusinessCategory) {
		this.majorBusinessCategory = majorBusinessCategory;
	}

	/**
	 * @return the businessBrand
	 */
	public String getBusinessBrand() {
		return businessBrand;
	}

	/**
	 * @param businessBrand
	 *            the businessBrand to set
	 */
	public void setBusinessBrand(String businessBrand) {
		this.businessBrand = businessBrand;
	}

	/**
	 * @return the operatingLife
	 */
	public String getOperatingLife() {
		return operatingLife;
	}

	/**
	 * @param operatingLife
	 *            the operatingLife to set
	 */
	public void setOperatingLife(String operatingLife) {
		this.operatingLife = operatingLife;
	}

	/**
	 * @return the vendorNature
	 */
	public String getVendorNature() {
		return vendorNature;
	}

	/**
	 * @param vendorNature
	 *            the vendorNature to set
	 */
	public void setVendorNature(String vendorNature) {
		this.vendorNature = vendorNature;
	}

	/**
	 * @return the chainOfAuthorize
	 */
	public String getChainOfAuthorize() {
		return chainOfAuthorize;
	}

	/**
	 * @param chainOfAuthorize
	 *            the chainOfAuthorize to set
	 */
	public void setChainOfAuthorize(String chainOfAuthorize) {
		this.chainOfAuthorize = chainOfAuthorize;
	}

	/**
	 * @return the isUseOtherPlatform
	 */
	public String getIsUseOtherPlatform() {
		return isUseOtherPlatform;
	}

	/**
	 * @param isUseOtherPlatform
	 *            the isUseOtherPlatform to set
	 */
	public void setIsUseOtherPlatform(String isUseOtherPlatform) {
		this.isUseOtherPlatform = isUseOtherPlatform;
	}

	/**
	 * @return the businessLicensePicSrc
	 */
	public String getBusinessLicensePicSrc() {
		return businessLicensePicSrc;
	}

	/**
	 * @param businessLicensePicSrc
	 *            the businessLicensePicSrc to set
	 */
	public void setBusinessLicensePicSrc(String businessLicensePicSrc) {
		this.businessLicensePicSrc = businessLicensePicSrc;
	}

	/**
	 * @return the organizationPicSrc
	 */
	public String getOrganizationPicSrc() {
		return organizationPicSrc;
	}

	/**
	 * @param organizationPicSrc
	 *            the organizationPicSrc to set
	 */
	public void setOrganizationPicSrc(String organizationPicSrc) {
		this.organizationPicSrc = organizationPicSrc;
	}

	/**
	 * @return the taxRegistrationCertificatePicSrc
	 */
	public String getTaxRegistrationCertificatePicSrc() {
		return taxRegistrationCertificatePicSrc;
	}

	/**
	 * @param taxRegistrationCertificatePicSrc
	 *            the taxRegistrationCertificatePicSrc to set
	 */
	public void setTaxRegistrationCertificatePicSrc(String taxRegistrationCertificatePicSrc) {
		this.taxRegistrationCertificatePicSrc = taxRegistrationCertificatePicSrc;
	}

	/**
	 * @return the taxpayerCertificatePicSrc
	 */
	public String getTaxpayerCertificatePicSrc() {
		return taxpayerCertificatePicSrc;
	}

	/**
	 * @param taxpayerCertificatePicSrc
	 *            the taxpayerCertificatePicSrc to set
	 */
	public void setTaxpayerCertificatePicSrc(String taxpayerCertificatePicSrc) {
		this.taxpayerCertificatePicSrc = taxpayerCertificatePicSrc;
	}

	/**
	 * @return the buyerGrade
	 */
	public String getBuyerGrade() {
		return buyerGrade;
	}

	/**
	 * @param buyerGrade
	 *            the buyerGrade to set
	 */
	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	/**
	 * @return the isVip
	 */
	public String getIsVip() {
		return isVip;
	}

	/**
	 * @param isVip
	 *            the isVip to set
	 */
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	/**
	 * @return the memberPackageType
	 */
	public String getMemberPackageType() {
		return memberPackageType;
	}

	/**
	 * @param memberPackageType
	 *            the memberPackageType to set
	 */
	public void setMemberPackageType(String memberPackageType) {
		this.memberPackageType = memberPackageType;
	}

	/**
	 * @return the packageActiveStartTime
	 */
	public Date getPackageActiveStartTime() {
		return packageActiveStartTime;
	}

	/**
	 * @param packageActiveStartTime
	 *            the packageActiveStartTime to set
	 */
	public void setPackageActiveStartTime(Date packageActiveStartTime) {
		this.packageActiveStartTime = packageActiveStartTime;
	}

	/**
	 * @return the packageActiveEndTime
	 */
	public Date getPackageActiveEndTime() {
		return packageActiveEndTime;
	}

	/**
	 * @param packageActiveEndTime
	 *            the packageActiveEndTime to set
	 */
	public void setPackageActiveEndTime(Date packageActiveEndTime) {
		this.packageActiveEndTime = packageActiveEndTime;
	}

	/**
	 * @return the packageUpdateTime
	 */
	public Date getPackageUpdateTime() {
		return packageUpdateTime;
	}

	/**
	 * @param packageUpdateTime
	 *            the packageUpdateTime to set
	 */
	public void setPackageUpdateTime(Date packageUpdateTime) {
		this.packageUpdateTime = packageUpdateTime;
	}

	/**
	 * @return the searchCondition
	 */
	public String getSearchCondition() {
		return searchCondition;
	}

	/**
	 * @param searchCondition
	 *            the searchCondition to set
	 */
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType
	 *            the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return the sellerId
	 */
	public String getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the receiptAddress
	 */
	public String getReceiptAddress() {
		return receiptAddress;
	}

	/**
	 * @param receiptAddress
	 *            the receiptAddress to set
	 */
	public void setReceiptAddress(String receiptAddress) {
		this.receiptAddress = receiptAddress;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the artificialPersonMobile
	 */
	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	/**
	 * @param artificialPersonMobile
	 *            the artificialPersonMobile to set
	 */
	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	/**
	 * @return the relationList
	 */
	public List<MemberBusinessRelationDTO> getRelationList() {
		return relationList;
	}

	/**
	 * @param relationList
	 *            the relationList to set
	 */
	public void setRelationList(List<MemberBusinessRelationDTO> relationList) {
		this.relationList = relationList;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime
	 *            the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	/**
	 * @return the sellerType
	 */
	public String getSellerType() {
		return sellerType;
	}

	/**
	 * @param sellerType
	 *            the sellerType to set
	 */
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
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

	public String getBusinessLicenseCertificatePicSrc() {
		return businessLicenseCertificatePicSrc;
	}

	public void setBusinessLicenseCertificatePicSrc(String businessLicenseCertificatePicSrc) {
		this.businessLicenseCertificatePicSrc = businessLicenseCertificatePicSrc;
	}

	/**
	 * @return the afterGrade
	 */
	public String getAfterGrade() {
		return afterGrade;
	}

	/**
	 * @param afterGrade
	 *            the afterGrade to set
	 */
	public void setAfterGrade(String afterGrade) {
		this.afterGrade = afterGrade;
	}

	/**
	 * @return the curBelongSellerId
	 */
	public String getCurBelongSellerId() {
		return curBelongSellerId;
	}

	/**
	 * @param curBelongSellerId
	 *            the curBelongSellerId to set
	 */
	public void setCurBelongSellerId(String curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	/**
	 * @return the curBelongSellerName
	 */
	public String getCurBelongSellerName() {
		return curBelongSellerName;
	}

	/**
	 * @param curBelongSellerName
	 *            the curBelongSellerName to set
	 */
	public void setCurBelongSellerName(String curBelongSellerName) {
		this.curBelongSellerName = curBelongSellerName;
	}

	public String getCurBelongMemberCode() {
		return curBelongMemberCode;
	}

	public void setCurBelongMemberCode(String curBelongMemberCode) {
		this.curBelongMemberCode = curBelongMemberCode;
	}

}
