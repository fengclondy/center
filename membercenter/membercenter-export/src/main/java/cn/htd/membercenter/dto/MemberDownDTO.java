package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberDownDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memberCode;// 中台会员编号
	private String id;// 中台会员编号
	private String areaCode;// 地区代码（如果为空，默认为90010101）
	private String name;// 中文名称
	private String singleName;// 简称（取前6位）
	private String registerAddress;// 注册地址（中文省、市、区/县、乡镇、详细地址的拼接）
	private String postCode;// 邮政编码
	private String faxNumber;// 传真号码
	private String internalVATDetailAddress;//
	private String internalVATICellPhone;// 发票电话
	private String depositBank;// 开户银行
	private String depositBankNum;// 开户账户
	private String ein;// 税号
	private String billingAccount;// 开票户头（以增值税发票优先）
	private String validTag;// 有效标记（默认为1）
	private String registrar;// 登记人（默认SYS_B2B）
	private String registrationTime;// 登记时间
	private String netProperty;// 进销属性（默认1，1：客户 0：供应商）
	private String memberFeature;// 会员类型（供应商默认为51）
	private String companyCode;// 公司代码（即，会员选择的供应商代码，为空时，默认为0801）
	private String currency;// 币种（默认为01）
	private String merchantsType;// 客商类型（默认为1501、供应商为空）
	private String openingDate;// 开户日期
	private Boolean sentErp;// 是否下行ERP
	private Boolean sentPay;// 是否下行支付系统
	private Integer isUpdateFlag;// 是否更新，更新：1，新增：0
	private String identifier;
	private String merchOrderNo;// erp同步唯一key
	private String supplierCode;
	private Long sellerId;// 审核状态

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSingleName() {
		return singleName;
	}

	public void setSingleName(String singleName) {
		this.singleName = singleName;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getInternalVATDetailAddress() {
		return internalVATDetailAddress;
	}

	public void setInternalVATDetailAddress(String internalVATDetailAddress) {
		this.internalVATDetailAddress = internalVATDetailAddress;
	}

	public String getInternalVATICellPhone() {
		return internalVATICellPhone;
	}

	public void setInternalVATICellPhone(String internalVATICellPhone) {
		this.internalVATICellPhone = internalVATICellPhone;
	}

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	public String getDepositBankNum() {
		return depositBankNum;
	}

	public void setDepositBankNum(String depositBankNum) {
		this.depositBankNum = depositBankNum;
	}

	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

	public String getBillingAccount() {
		return billingAccount;
	}

	public void setBillingAccount(String billingAccount) {
		this.billingAccount = billingAccount;
	}

	public String getValidTag() {
		return validTag;
	}

	public void setValidTag(String validTag) {
		this.validTag = validTag;
	}

	public String getRegistrar() {
		return registrar;
	}

	public void setRegistrar(String registrar) {
		this.registrar = registrar;
	}

	public String getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(String registrationTime) {
		this.registrationTime = registrationTime;
	}

	public String getNetProperty() {
		return netProperty;
	}

	public void setNetProperty(String netProperty) {
		this.netProperty = netProperty;
	}

	public String getMemberFeature() {
		return memberFeature;
	}

	public void setMemberFeature(String memberFeature) {
		this.memberFeature = memberFeature;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMerchantsType() {
		return merchantsType;
	}

	public void setMerchantsType(String merchantsType) {
		this.merchantsType = merchantsType;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

	public Boolean getSentErp() {
		return sentErp;
	}

	public void setSentErp(Boolean sentErp) {
		this.sentErp = sentErp;
	}

	public Boolean getSentPay() {
		return sentPay;
	}

	public void setSentPay(Boolean sentPay) {
		this.sentPay = sentPay;
	}

	public Integer getIsUpdateFlag() {
		return isUpdateFlag;
	}

	public void setIsUpdateFlag(Integer isUpdateFlag) {
		this.isUpdateFlag = isUpdateFlag;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the merchOrderNo
	 */
	public String getMerchOrderNo() {
		return merchOrderNo;
	}

	/**
	 * @param merchOrderNo
	 *            the merchOrderNo to set
	 */
	public void setMerchOrderNo(String merchOrderNo) {
		this.merchOrderNo = merchOrderNo;
	}

	/**
	 * @return the supplierCode
	 */
	public String getSupplierCode() {
		return supplierCode;
	}

	/**
	 * @param supplierCode
	 *            the supplierCode to set
	 */
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	/**
	 * @return the sellerId
	 */
	public Long getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
}
