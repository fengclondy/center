package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class ErpSellerupDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vendorName;// 企业名称
	private String vendorCode;// 企业编码
	private String isNormalTaxpayer;// 是否一般纳税人
	private String registeredAddressProvince;// 公司注册地址-省
	private String businessAddressCity;// 公司办公地址-市
	private String businessAddressCounty;// ;// 公司办公地址-区
	private String businessAddressDetailAddress;// 公司办公地址-详细
	private String companyLeagalPersion;// 法人／自然人
	private String contactName;// 业务联络人
	private String contactMobile;// 业务人员手机号
	private String contactEmail;// 业务人员邮箱
	private String useOtherChannels;// 是否在其他平台经营
	private String taxpayerIDnumber;// 纳税人识别号
	private String depositBank;// 开户银行
	private String financialAccount;// 银行账号
	private String memberCode;// 客户编码
	private String createTime;// 创建时间
	private Long memberId;// 会员ID

	private String businessLicenseCode;// 营业执照编号

	private String memberCenterCode;// 会员中心会员编码

	private String errMsg;// 日志上行错误信息

	private String status;// 日志上行状态

	private String accountNo;// 会员账号

	private Date registTime;// 注册时间

	private String locationAddr;// 详细地址

	private String realNameStatus;// 实名状态

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

	public String getIsNormalTaxpayer() {
		return isNormalTaxpayer;
	}

	public void setIsNormalTaxpayer(String isNormalTaxpayer) {
		this.isNormalTaxpayer = isNormalTaxpayer;
	}

	public String getRegisteredAddressProvince() {
		return registeredAddressProvince;
	}

	public void setRegisteredAddressProvince(String registeredAddressProvince) {
		this.registeredAddressProvince = registeredAddressProvince;
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

	public String getBusinessAddressDetailAddress() {
		return businessAddressDetailAddress;
	}

	public void setBusinessAddressDetailAddress(String businessAddressDetailAddress) {
		this.businessAddressDetailAddress = businessAddressDetailAddress;
	}

	public String getCompanyLeagalPersion() {
		return companyLeagalPersion;
	}

	public void setCompanyLeagalPersion(String companyLeagalPersion) {
		this.companyLeagalPersion = companyLeagalPersion;
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

	public String getUseOtherChannels() {
		return useOtherChannels;
	}

	public void setUseOtherChannels(String useOtherChannels) {
		this.useOtherChannels = useOtherChannels;
	}

	public String getTaxpayerIDnumber() {
		return taxpayerIDnumber;
	}

	public void setTaxpayerIDnumber(String taxpayerIDnumber) {
		this.taxpayerIDnumber = taxpayerIDnumber;
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

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the memberId
	 */
	public Long getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberCenterCode
	 */
	public String getMemberCenterCode() {
		return memberCenterCode;
	}

	/**
	 * @param memberCenterCode
	 *            the memberCenterCode to set
	 */
	public void setMemberCenterCode(String memberCenterCode) {
		this.memberCenterCode = memberCenterCode;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg
	 *            the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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
	 * @return the businessLicenseCode
	 */
	public String getBusinessLicenseCode() {
		return businessLicenseCode;
	}

	/**
	 * @param businessLicenseCode
	 *            the businessLicenseCode to set
	 */
	public void setBusinessLicenseCode(String businessLicenseCode) {
		this.businessLicenseCode = businessLicenseCode;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 *            the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	/**
	 * @return the realNameStatus
	 */
	public String getRealNameStatus() {
		return realNameStatus;
	}

	/**
	 * @param realNameStatus
	 *            the realNameStatus to set
	 */
	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}

}
