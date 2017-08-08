package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class ErpInnerVendorUpLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;// ID
	private String vendorName; // 企业名称
	private String vendorCode;// 企业编码
	private String memberCode;// 客户编码
	private String vendorCompanyType;// 企业类型
	private Integer isNormaTaxpayer;// 是否是一般纳税人
	private String registeredAddress;// 公司注册地址-省
	private String registeredAddressCity;// 公司注册地址-市
	private String registeredAddressCounty;// 公司注册地址-区
	private String registeredAddressDetail;// 公司注册地址-详细
	private String businessAddressProvince;// 公司办公地址-省
	private String businessAddressCity;// 公司办公地址-市
	private String businessAddressCounty;// 公司办公地址-区
	private String businessAddressDetail;// 公司办公地址-详细
	private String companySalesScale;// 公司规模
	private String businessScope;// 经营范围
	private String companyLeagalPersionFlag;// 法人/自然人标记 0：未设置，1：法人，2：自然人',
	private String contactName;// 业务联系人
	private String contactMobile;// 业务人员手机号
	private String contactEmail;// 业务人员邮箱
	private String businessCategory;// 经营类目
	private String majorBusinessCategory;// 主营类目
	private String businessBrand;// 经营品牌
	private String operatingLife;// 经营年限
	private String vendorNature;// 供应商性质
	private String chainOfAuthorize;// 授权链
	private String businessLicenseId;// 营业执照注册号
	private String organizationId;// 组织机构代码
	private Integer isUseOtherPlatform;// 是否在其他平台经营
	private String taxManId;// 纳税人识别号
	private String depositBank;// 开户行名称
	private String financialAccount;// 银行账号
	private String invoiceContactPhone;// 发票联系电话
	private String signingBank;// 收款银行
	private String signingBankCard;// 收款账号
	private String status;// 1:待处理，2：处理中，3：处理成功，4：处理失败'
	private String errorMsg;// 错误信息
	private Long memberId;// 处理后生成会员ID
	private Long createId;// 创建人ID
	private String createName;// 创建人名称
	private Date createTime;// 创建时间
	private Long modifyId;// 更新人ID
	private String modifyName;// 更新人名称
	private Date modifyTime;// 更新时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getVendorCompanyType() {
		return vendorCompanyType;
	}

	public void setVendorCompanyType(String vendorCompanyType) {
		this.vendorCompanyType = vendorCompanyType;
	}

	public Integer getIsNormaTaxpayer() {
		return isNormaTaxpayer;
	}

	public void setIsNormaTaxpayer(Integer isNormaTaxpayer) {
		this.isNormaTaxpayer = isNormaTaxpayer;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getRegisteredAddressCity() {
		return registeredAddressCity;
	}

	public void setRegisteredAddressCity(String registeredAddressCity) {
		this.registeredAddressCity = registeredAddressCity;
	}

	public String getRegisteredAddressCounty() {
		return registeredAddressCounty;
	}

	public void setRegisteredAddressCounty(String registeredAddressCounty) {
		this.registeredAddressCounty = registeredAddressCounty;
	}

	public String getRegisteredAddressDetail() {
		return registeredAddressDetail;
	}

	public void setRegisteredAddressDetail(String registeredAddressDetail) {
		this.registeredAddressDetail = registeredAddressDetail;
	}

	public String getBusinessAddressProvince() {
		return businessAddressProvince;
	}

	public void setBusinessAddressProvince(String businessAddressProvince) {
		this.businessAddressProvince = businessAddressProvince;
	}

	public String getBusinessAddressCity() {
		return businessAddressCity;
	}

	public void setBusinessAddressCity(String businessAddressCity) {
		this.businessAddressCity = businessAddressCity;
	}

	public String getBusinessAddressCounty() {
		return businessAddressCounty;
	}

	public void setBusinessAddressCounty(String businessAddressCounty) {
		this.businessAddressCounty = businessAddressCounty;
	}

	public String getBusinessAddressDetail() {
		return businessAddressDetail;
	}

	public void setBusinessAddressDetail(String businessAddressDetail) {
		this.businessAddressDetail = businessAddressDetail;
	}

	public String getCompanySalesScale() {
		return companySalesScale;
	}

	public void setCompanySalesScale(String companySalesScale) {
		this.companySalesScale = companySalesScale;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getCompanyLeagalPersionFlag() {
		return companyLeagalPersionFlag;
	}

	public void setCompanyLeagalPersionFlag(String companyLeagalPersionFlag) {
		this.companyLeagalPersionFlag = companyLeagalPersionFlag;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getMajorBusinessCategory() {
		return majorBusinessCategory;
	}

	public void setMajorBusinessCategory(String majorBusinessCategory) {
		this.majorBusinessCategory = majorBusinessCategory;
	}

	public String getBusinessBrand() {
		return businessBrand;
	}

	public void setBusinessBrand(String businessBrand) {
		this.businessBrand = businessBrand;
	}

	public String getOperatingLife() {
		return operatingLife;
	}

	public void setOperatingLife(String operatingLife) {
		this.operatingLife = operatingLife;
	}

	public String getVendorNature() {
		return vendorNature;
	}

	public void setVendorNature(String vendorNature) {
		this.vendorNature = vendorNature;
	}

	public String getChainOfAuthorize() {
		return chainOfAuthorize;
	}

	public void setChainOfAuthorize(String chainOfAuthorize) {
		this.chainOfAuthorize = chainOfAuthorize;
	}

	public String getBusinessLicenseId() {
		return businessLicenseId;
	}

	public void setBusinessLicenseId(String businessLicenseId) {
		this.businessLicenseId = businessLicenseId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getIsUseOtherPlatform() {
		return isUseOtherPlatform;
	}

	public void setIsUseOtherPlatform(Integer isUseOtherPlatform) {
		this.isUseOtherPlatform = isUseOtherPlatform;
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId;
	}

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	public String getFinancialAccount() {
		return financialAccount;
	}

	public void setFinancialAccount(String financialAccount) {
		this.financialAccount = financialAccount;
	}

	public String getInvoiceContactPhone() {
		return invoiceContactPhone;
	}

	public void setInvoiceContactPhone(String invoiceContactPhone) {
		this.invoiceContactPhone = invoiceContactPhone;
	}

	public String getSigningBank() {
		return signingBank;
	}

	public void setSigningBank(String signingBank) {
		this.signingBank = signingBank;
	}

	public String getSigningBankCard() {
		return signingBankCard;
	}

	public void setSigningBankCard(String signingBankCard) {
		this.signingBankCard = signingBankCard;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
