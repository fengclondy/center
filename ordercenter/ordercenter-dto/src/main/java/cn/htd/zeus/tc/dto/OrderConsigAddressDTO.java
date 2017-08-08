package cn.htd.zeus.tc.dto;

import java.io.Serializable;

public class OrderConsigAddressDTO implements Serializable {

	private static final long serialVersionUID = -8868317353831327011L;

	private String consigneeName;// 收货人姓名
	private String consigneeMobile;// 收货人手机号码
	private String consigneeEmail;// 收货人邮箱
	private String invoiceCompanyName;// 增值税发票公司名称
	private String taxManId;// 纳税人识别号
	private String invoicePerson;// 收票人
	private String contactPhone;// 联系电话
	private String consigneeAddressProvince;// 收货地址-省
	private String consigneeAddressCity;// 收货地址-市
	private String consigneeAddressDistrict;// 收货地址-区
	private String consigneeAddressTown;// 收货地址-镇
	private String consigneeAddressDetail;// 收货地址-详细
	private String consigneeAddress;// 收货地址
	private String invoiceAddressProvince;// 发票邮寄地址-省
	private String invoiceAddressCity;// 发票邮寄地址-市
	private String invoiceAddressCounty;// 发票邮寄地址-区
	private String invoiceAddressTown;// 发票邮寄地址-镇
	private String invoiceAddressDetail;// 发票邮寄地址-详细
	private String invoiceAddress;// 发票地址
	private String postCode; // 邮编
	private String defaultFlag;// 设置默认地址标记

	/**
	 * @return the consigneeName
	 */
	public String getConsigneeName() {
		return consigneeName;
	}

	/**
	 * @param consigneeName
	 *            the consigneeName to set
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the consigneeEmail
	 */
	public String getConsigneeEmail() {
		return consigneeEmail;
	}

	/**
	 * @param consigneeEmail
	 *            the consigneeEmail to set
	 */
	public void setConsigneeEmail(String consigneeEmail) {
		this.consigneeEmail = consigneeEmail;
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
	 * @return the consigneeAddressProvince
	 */
	public String getConsigneeAddressProvince() {
		return consigneeAddressProvince;
	}

	/**
	 * @param consigneeAddressProvince
	 *            the consigneeAddressProvince to set
	 */
	public void setConsigneeAddressProvince(String consigneeAddressProvince) {
		this.consigneeAddressProvince = consigneeAddressProvince;
	}

	/**
	 * @return the consigneeAddressCity
	 */
	public String getConsigneeAddressCity() {
		return consigneeAddressCity;
	}

	/**
	 * @param consigneeAddressCity
	 *            the consigneeAddressCity to set
	 */
	public void setConsigneeAddressCity(String consigneeAddressCity) {
		this.consigneeAddressCity = consigneeAddressCity;
	}

	/**
	 * @return the consigneeAddressDistrict
	 */
	public String getConsigneeAddressDistrict() {
		return consigneeAddressDistrict;
	}

	/**
	 * @param consigneeAddressDistrict
	 *            the consigneeAddressDistrict to set
	 */
	public void setConsigneeAddressDistrict(String consigneeAddressDistrict) {
		this.consigneeAddressDistrict = consigneeAddressDistrict;
	}

	/**
	 * @return the consigneeAddressTown
	 */
	public String getConsigneeAddressTown() {
		return consigneeAddressTown;
	}

	/**
	 * @param consigneeAddressTown
	 *            the consigneeAddressTown to set
	 */
	public void setConsigneeAddressTown(String consigneeAddressTown) {
		this.consigneeAddressTown = consigneeAddressTown;
	}

	/**
	 * @return the consigneeAddressDetail
	 */
	public String getConsigneeAddressDetail() {
		return consigneeAddressDetail;
	}

	/**
	 * @param consigneeAddressDetail
	 *            the consigneeAddressDetail to set
	 */
	public void setConsigneeAddressDetail(String consigneeAddressDetail) {
		this.consigneeAddressDetail = consigneeAddressDetail;
	}

	/**
	 * @return the consigneeAddress
	 */
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	/**
	 * @param consigneeAddress
	 *            the consigneeAddress to set
	 */
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
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
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode
	 *            the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the defaultFlag
	 */
	public String getDefaultFlag() {
		return defaultFlag;
	}

	/**
	 * @param defaultFlag
	 *            the defaultFlag to set
	 */
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
