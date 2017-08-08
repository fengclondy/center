package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class MemberExtendInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long memberId;

	private String companyType;

	private String businessScope;

	private String businessCategory;

	private String majorBusinessCategory;

	private String businessBrand;

	private String operatingLife;

	private String vendorNature;

	private String chainOfAuthorize;

	private Byte isUseOtherPlatform;

	private Byte isFinancing;

	private String financingNumber;

	private String homePage;

	private Byte isHaveEbusiness;

	private String websiteOpertionNumber;

	private Byte hasErp;

	private String salesVolumn;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType == null ? null : companyType.trim();
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope == null ? null : businessScope.trim();
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory == null ? null : businessCategory.trim();
	}

	public String getMajorBusinessCategory() {
		return majorBusinessCategory;
	}

	public void setMajorBusinessCategory(String majorBusinessCategory) {
		this.majorBusinessCategory = majorBusinessCategory == null ? null : majorBusinessCategory.trim();
	}

	public String getBusinessBrand() {
		return businessBrand;
	}

	public void setBusinessBrand(String businessBrand) {
		this.businessBrand = businessBrand == null ? null : businessBrand.trim();
	}

	public String getOperatingLife() {
		return operatingLife;
	}

	public void setOperatingLife(String operatingLife) {
		this.operatingLife = operatingLife == null ? null : operatingLife.trim();
	}

	public String getVendorNature() {
		return vendorNature;
	}

	public void setVendorNature(String vendorNature) {
		this.vendorNature = vendorNature == null ? null : vendorNature.trim();
	}

	public String getChainOfAuthorize() {
		return chainOfAuthorize;
	}

	public void setChainOfAuthorize(String chainOfAuthorize) {
		this.chainOfAuthorize = chainOfAuthorize == null ? null : chainOfAuthorize.trim();
	}

	public Byte getIsUseOtherPlatform() {
		return isUseOtherPlatform;
	}

	public void setIsUseOtherPlatform(Byte isUseOtherPlatform) {
		this.isUseOtherPlatform = isUseOtherPlatform;
	}

	public Byte getIsFinancing() {
		return isFinancing;
	}

	public void setIsFinancing(Byte isFinancing) {
		this.isFinancing = isFinancing;
	}

	public String getFinancingNumber() {
		return financingNumber;
	}

	public void setFinancingNumber(String financingNumber) {
		this.financingNumber = financingNumber == null ? null : financingNumber.trim();
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage == null ? null : homePage.trim();
	}

	public Byte getIsHaveEbusiness() {
		return isHaveEbusiness;
	}

	public void setIsHaveEbusiness(Byte isHaveEbusiness) {
		this.isHaveEbusiness = isHaveEbusiness;
	}

	public String getWebsiteOpertionNumber() {
		return websiteOpertionNumber;
	}

	public void setWebsiteOpertionNumber(String websiteOpertionNumber) {
		this.websiteOpertionNumber = websiteOpertionNumber == null ? null : websiteOpertionNumber.trim();
	}

	public Byte getHasErp() {
		return hasErp;
	}

	public void setHasErp(Byte hasErp) {
		this.hasErp = hasErp;
	}

	public String getSalesVolumn() {
		return salesVolumn;
	}

	public void setSalesVolumn(String salesVolumn) {
		this.salesVolumn = salesVolumn == null ? null : salesVolumn.trim();
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