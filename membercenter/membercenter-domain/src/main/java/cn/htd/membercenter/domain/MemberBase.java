package cn.htd.membercenter.domain;

import java.util.Date;

/**
 * member_base_info 实体类 Tue Nov 15 14:07:18 CST 2016 haozy
 */
public class MemberBase {
	private long id;
	private String memberCode;
	private String companyLeagalPersionFlag;
	private String isBuyer;
	private String isSeller;
	private String canMallLogin;
	private String hasGuaranteeLicense;
	private String hasBusinessLicense;
	private Date registTime;
	private String accountType;
	private Date becomeMemberTime;
	private String isCenterStore;
	private Date upgradeCenterStoreTime;
	private Date upgradeSellerTime;
	private String sellerType;
	private String isGeneration;
	private String industryCategory;
	private String isDiffIndustry;
	private String contactName;
	private String contactMobile;
	private String contactEmail;
	private String contactIdcard;
	private String contactPicSrc;
	private String contactPicBackSrc;
	private String isPhoneAuthenticated;
	private String isRealNameAuthenticated;
	private String cooperateVendor;
	private String registFrom;
	private long promotionPerson;
	private long belongSellerId;
	private String belongManagerId;
	private long curBelongSellerId;
	private String curBelongManagerId;
	private String status;
	private String memberType;
	private String phoneNumber;
	private String companyName;
	private String companyType;
	private String businessScope;
	private String businessCategory;
	private String majorBusinessCategory;
	private String businessBrand;
	private String operatingLife;
	private String vendorNature;
	private String chainOfAuthorize;
	private String isUseOtherPlatform;
	private String isFinancing;
	private String financingNumber;
	private String homePage;
	private String isHaveEbusiness;
	private String websiteOpertionNumber;
	private String hasErp;
	private String salesVolumn;
	private String memberId;
	private String buyerGuaranteeLicensePicSrc;
	private String buyerBusinessLicensePicSrc;
	private String certificateType;
	private String unifiedSocialCreditCode;
	private String businessLicenseId;
	private String businessLicensePicSrc;
	private String businessLicenseCertificatePicSrc;
	private String organizationId;
	private String organizationPicSrc;
	private String taxpayerType;
	private String taxManId;
	private String taxpayerCode;
	private String taxRegistrationCertificatePicSrc;
	private String taxpayerCertificatePicSrc;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @return the companyLeagalPersionFlag
	 */
	public String getCompanyLeagalPersionFlag() {
		return companyLeagalPersionFlag;
	}

	/**
	 * @param companyLeagalPersionFlag
	 *            the companyLeagalPersionFlag to set
	 */
	public void setCompanyLeagalPersionFlag(String companyLeagalPersionFlag) {
		this.companyLeagalPersionFlag = companyLeagalPersionFlag;
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
	 * @return the isSeller
	 */
	public String getIsSeller() {
		return isSeller;
	}

	/**
	 * @param isSeller
	 *            the isSeller to set
	 */
	public void setIsSeller(String isSeller) {
		this.isSeller = isSeller;
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
	 * @return the registTime
	 */
	public Date getRegistTime() {
		return registTime;
	}

	/**
	 * @param registTime
	 *            the registTime to set
	 */
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	/**
	 * @return the isCenterStore
	 */
	public String getIsCenterStore() {
		return isCenterStore;
	}

	/**
	 * @param isCenterStore
	 *            the isCenterStore to set
	 */
	public void setIsCenterStore(String isCenterStore) {
		this.isCenterStore = isCenterStore;
	}

	/**
	 * @return the upgradeCenterStoreTime
	 */
	public Date getUpgradeCenterStoreTime() {
		return upgradeCenterStoreTime;
	}

	/**
	 * @param upgradeCenterStoreTime
	 *            the upgradeCenterStoreTime to set
	 */
	public void setUpgradeCenterStoreTime(Date upgradeCenterStoreTime) {
		this.upgradeCenterStoreTime = upgradeCenterStoreTime;
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

	/**
	 * @return the isGeneration
	 */
	public String getIsGeneration() {
		return isGeneration;
	}

	/**
	 * @param isGeneration
	 *            the isGeneration to set
	 */
	public void setIsGeneration(String isGeneration) {
		this.isGeneration = isGeneration;
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
	 * @return the isDiffIndustry
	 */
	public String getIsDiffIndustry() {
		return isDiffIndustry;
	}

	/**
	 * @param isDiffIndustry
	 *            the isDiffIndustry to set
	 */
	public void setIsDiffIndustry(String isDiffIndustry) {
		this.isDiffIndustry = isDiffIndustry;
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

	public String getContactMobile() {
		return contactMobile;
	}

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
	 * @return the contactIdcard
	 */
	public String getContactIdcard() {
		return contactIdcard;
	}

	/**
	 * @param contactIdcard
	 *            the contactIdcard to set
	 */
	public void setContactIdcard(String contactIdcard) {
		this.contactIdcard = contactIdcard;
	}

	/**
	 * @return the contactPicSrc
	 */
	public String getContactPicSrc() {
		return contactPicSrc;
	}

	/**
	 * @param contactPicSrc
	 *            the contactPicSrc to set
	 */
	public void setContactPicSrc(String contactPicSrc) {
		this.contactPicSrc = contactPicSrc;
	}

	/**
	 * @return the contactPicBackSrc
	 */
	public String getContactPicBackSrc() {
		return contactPicBackSrc;
	}

	/**
	 * @param contactPicBackSrc
	 *            the contactPicBackSrc to set
	 */
	public void setContactPicBackSrc(String contactPicBackSrc) {
		this.contactPicBackSrc = contactPicBackSrc;
	}

	/**
	 * @return the isPhoneAuthenticated
	 */
	public String getIsPhoneAuthenticated() {
		return isPhoneAuthenticated;
	}

	/**
	 * @param isPhoneAuthenticated
	 *            the isPhoneAuthenticated to set
	 */
	public void setIsPhoneAuthenticated(String isPhoneAuthenticated) {
		this.isPhoneAuthenticated = isPhoneAuthenticated;
	}

	/**
	 * @return the isRealNameAuthenticated
	 */
	public String getIsRealNameAuthenticated() {
		return isRealNameAuthenticated;
	}

	/**
	 * @param isRealNameAuthenticated
	 *            the isRealNameAuthenticated to set
	 */
	public void setIsRealNameAuthenticated(String isRealNameAuthenticated) {
		this.isRealNameAuthenticated = isRealNameAuthenticated;
	}

	/**
	 * @return the cooperateVendor
	 */
	public String getCooperateVendor() {
		return cooperateVendor;
	}

	/**
	 * @param cooperateVendor
	 *            the cooperateVendor to set
	 */
	public void setCooperateVendor(String cooperateVendor) {
		this.cooperateVendor = cooperateVendor;
	}

	/**
	 * @return the registFrom
	 */
	public String getRegistFrom() {
		return registFrom;
	}

	/**
	 * @param registFrom
	 *            the registFrom to set
	 */
	public void setRegistFrom(String registFrom) {
		this.registFrom = registFrom;
	}

	/**
	 * @return the promotionPerson
	 */
	public long getPromotionPerson() {
		return promotionPerson;
	}

	/**
	 * @param promotionPerson
	 *            the promotionPerson to set
	 */
	public void setPromotionPerson(long promotionPerson) {
		this.promotionPerson = promotionPerson;
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
	 * @return the becomeMemberTime
	 */
	public Date getBecomeMemberTime() {
		return becomeMemberTime;
	}

	/**
	 * @param becomeMemberTime
	 *            the becomeMemberTime to set
	 */
	public void setBecomeMemberTime(Date becomeMemberTime) {
		this.becomeMemberTime = becomeMemberTime;
	}

	/**
	 * @return the upgradeSellerTime
	 */
	public Date getUpgradeSellerTime() {
		return upgradeSellerTime;
	}

	/**
	 * @param upgradeSellerTime
	 *            the upgradeSellerTime to set
	 */
	public void setUpgradeSellerTime(Date upgradeSellerTime) {
		this.upgradeSellerTime = upgradeSellerTime;
	}

	/**
	 * @return the belongSellerId
	 */
	public long getBelongSellerId() {
		return belongSellerId;
	}

	/**
	 * @param belongSellerId
	 *            the belongSellerId to set
	 */
	public void setBelongSellerId(long belongSellerId) {
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
	 * @return the curBelongSellerId
	 */
	public long getCurBelongSellerId() {
		return curBelongSellerId;
	}

	/**
	 * @param curBelongSellerId
	 *            the curBelongSellerId to set
	 */
	public void setCurBelongSellerId(long curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	/**
	 * @return the curBelongManagerId
	 */
	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	/**
	 * @param curBelongManagerId
	 *            the curBelongManagerId to set
	 */
	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
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
	 * @return the isFinancing
	 */
	public String getIsFinancing() {
		return isFinancing;
	}

	/**
	 * @param isFinancing
	 *            the isFinancing to set
	 */
	public void setIsFinancing(String isFinancing) {
		this.isFinancing = isFinancing;
	}

	/**
	 * @return the financingNumber
	 */
	public String getFinancingNumber() {
		return financingNumber;
	}

	/**
	 * @param financingNumber
	 *            the financingNumber to set
	 */
	public void setFinancingNumber(String financingNumber) {
		this.financingNumber = financingNumber;
	}

	/**
	 * @return the homePage
	 */
	public String getHomePage() {
		return homePage;
	}

	/**
	 * @param homePage
	 *            the homePage to set
	 */
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	/**
	 * @return the isHaveEbusiness
	 */
	public String getIsHaveEbusiness() {
		return isHaveEbusiness;
	}

	/**
	 * @param isHaveEbusiness
	 *            the isHaveEbusiness to set
	 */
	public void setIsHaveEbusiness(String isHaveEbusiness) {
		this.isHaveEbusiness = isHaveEbusiness;
	}

	/**
	 * @return the websiteOpertionNumber
	 */
	public String getWebsiteOpertionNumber() {
		return websiteOpertionNumber;
	}

	/**
	 * @param websiteOpertionNumber
	 *            the websiteOpertionNumber to set
	 */
	public void setWebsiteOpertionNumber(String websiteOpertionNumber) {
		this.websiteOpertionNumber = websiteOpertionNumber;
	}

	/**
	 * @return the hasErp
	 */
	public String getHasErp() {
		return hasErp;
	}

	/**
	 * @param hasErp
	 *            the hasErp to set
	 */
	public void setHasErp(String hasErp) {
		this.hasErp = hasErp;
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
	 * @return the buyerGuaranteeLicensePicSrc
	 */
	public String getBuyerGuaranteeLicensePicSrc() {
		return buyerGuaranteeLicensePicSrc;
	}

	/**
	 * @param buyerGuaranteeLicensePicSrc
	 *            the buyerGuaranteeLicensePicSrc to set
	 */
	public void setBuyerGuaranteeLicensePicSrc(String buyerGuaranteeLicensePicSrc) {
		this.buyerGuaranteeLicensePicSrc = buyerGuaranteeLicensePicSrc;
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
	 * @return the certificateType
	 */
	public String getCertificateType() {
		return certificateType;
	}

	/**
	 * @param certificateType
	 *            the certificateType to set
	 */
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	/**
	 * @return the unifiedSocialCreditCode
	 */
	public String getUnifiedSocialCreditCode() {
		return unifiedSocialCreditCode;
	}

	/**
	 * @param unifiedSocialCreditCode
	 *            the unifiedSocialCreditCode to set
	 */
	public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
		this.unifiedSocialCreditCode = unifiedSocialCreditCode;
	}

	/**
	 * @return the businessLicenseId
	 */
	public String getBusinessLicenseId() {
		return businessLicenseId;
	}

	/**
	 * @param businessLicenseId
	 *            the businessLicenseId to set
	 */
	public void setBusinessLicenseId(String businessLicenseId) {
		this.businessLicenseId = businessLicenseId;
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
	 * @return the businessLicenseCertificatePicSrc
	 */
	public String getBusinessLicenseCertificatePicSrc() {
		return businessLicenseCertificatePicSrc;
	}

	/**
	 * @param businessLicenseCertificatePicSrc
	 *            the businessLicenseCertificatePicSrc to set
	 */
	public void setBusinessLicenseCertificatePicSrc(String businessLicenseCertificatePicSrc) {
		this.businessLicenseCertificatePicSrc = businessLicenseCertificatePicSrc;
	}

	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId
	 *            the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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
	 * @return the taxpayerType
	 */
	public String getTaxpayerType() {
		return taxpayerType;
	}

	/**
	 * @param taxpayerType
	 *            the taxpayerType to set
	 */
	public void setTaxpayerType(String taxpayerType) {
		this.taxpayerType = taxpayerType;
	}

	/**
	 * @return the taxManId
	 */
	public String getTaxManId() {
		return taxManId;
	}

	/**
	 * @param taxManId
	 *            the taxManId to set
	 */
	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId;
	}

	/**
	 * @return the taxpayerCode
	 */
	public String getTaxpayerCode() {
		return taxpayerCode;
	}

	/**
	 * @param taxpayerCode
	 *            the taxpayerCode to set
	 */
	public void setTaxpayerCode(String taxpayerCode) {
		this.taxpayerCode = taxpayerCode;
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

}
