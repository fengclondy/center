package cn.htd.zeus.tc.dto;

import java.io.Serializable;

public class OrderInvoiceDTO implements Serializable {

	private static final long serialVersionUID = 3682206729357667115L;
	// 会员编码
	private String memberId;
	// 渠道编码
	private String channelCode;
	// 普通发票抬头
	private String invoiceNotify;
	// 增值税发票公司
	private String invoiceCompanyName;
	// 纳税人识别号
	private String taxManId;
	// 开户行名称
	private String bankName;
	// 开户行账号
	private String bankAccount;
	// 联系人
	private String invoicePerson;
	// 发票地址
	private String invoiceAddress;
	// 联系电话
	private String contactPhone;
	// 发票邮寄地址-省
	private String invoiceAddressProvince;
	// 发票邮寄地址-市
	private String invoiceAddressCity;
	// 发票邮寄地址-区
	private String invoiceAddressCounty;
	// 发票邮寄地址-镇
	private String invoiceAddressTown;
	// 发票邮寄地址-详细地址
	private String invoiceAddressDetail;

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
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @param channelCode
	 *            the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * @return the invoiceNotify
	 */
	public String getInvoiceNotify() {
		return invoiceNotify;
	}

	/**
	 * @param invoiceNotify
	 *            the invoiceNotify to set
	 */
	public void setInvoiceNotify(String invoiceNotify) {
		this.invoiceNotify = invoiceNotify;
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
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankAccount
	 */
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * @param bankAccount
	 *            the bankAccount to set
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * @return the invoicePerson
	 */
	public String getInvoicePerson() {
		return invoicePerson;
	}

	/**
	 * @param invoicePerson
	 *            the invoicePerson to set
	 */
	public void setInvoicePerson(String invoicePerson) {
		this.invoicePerson = invoicePerson;
	}

	/**
	 * @return the invoiceAddress
	 */
	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	/**
	 * @param invoiceAddress
	 *            the invoiceAddress to set
	 */
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the invoiceAddressProvince
	 */
	public String getInvoiceAddressProvince() {
		return invoiceAddressProvince;
	}

	/**
	 * @param invoiceAddressProvince
	 *            the invoiceAddressProvince to set
	 */
	public void setInvoiceAddressProvince(String invoiceAddressProvince) {
		this.invoiceAddressProvince = invoiceAddressProvince;
	}

	/**
	 * @return the invoiceAddressCity
	 */
	public String getInvoiceAddressCity() {
		return invoiceAddressCity;
	}

	/**
	 * @param invoiceAddressCity
	 *            the invoiceAddressCity to set
	 */
	public void setInvoiceAddressCity(String invoiceAddressCity) {
		this.invoiceAddressCity = invoiceAddressCity;
	}

	/**
	 * @return the invoiceAddressCounty
	 */
	public String getInvoiceAddressCounty() {
		return invoiceAddressCounty;
	}

	/**
	 * @param invoiceAddressCounty
	 *            the invoiceAddressCounty to set
	 */
	public void setInvoiceAddressCounty(String invoiceAddressCounty) {
		this.invoiceAddressCounty = invoiceAddressCounty;
	}

	/**
	 * @return the invoiceAddressTown
	 */
	public String getInvoiceAddressTown() {
		return invoiceAddressTown;
	}

	/**
	 * @param invoiceAddressTown
	 *            the invoiceAddressTown to set
	 */
	public void setInvoiceAddressTown(String invoiceAddressTown) {
		this.invoiceAddressTown = invoiceAddressTown;
	}

	/**
	 * @return the invoiceAddressDetail
	 */
	public String getInvoiceAddressDetail() {
		return invoiceAddressDetail;
	}

	/**
	 * @param invoiceAddressDetail
	 *            the invoiceAddressDetail to set
	 */
	public void setInvoiceAddressDetail(String invoiceAddressDetail) {
		this.invoiceAddressDetail = invoiceAddressDetail;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
