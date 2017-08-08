package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @version 创建时间：2016年12月2日 下午5:14:45 类说明:会员收货地址信息
 */
public class MemberConsigAddressDTO implements Serializable {

	private static final long serialVersionUID = 8737822560587874667L;

	private Long memberId;// 会员ID
	private Long addressId;// 地址ID
	private Long invoiceId;// 发票ID
	private String consigneeName;// 收货人姓名
	private String consigneeMobile;// 收货人手机号码
	private String consigneeEmail;// 收货人邮箱
	private String invoiceCompanyName;// 增值税发票公司名称
	private String invoiceNotify;//发票抬头
	private String taxManId;// 纳税人识别号
	private String invoicePerson;// 收票人
	private String contactPhone;// 联系电话
	private String bankName;// 开户银行
	private String bankAccount;// 银行账号
	private String channelCode;// 外接渠道编码
	private String channelName;// 外接渠道名称
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
	private Long modifyId;// 更新人ID
	private String modifyName;// 更新人名称
	private List<String> strList;// 用于批量删除
	private String postCode; // 邮编
	private String defaultFlag;// 设置默认地址标记
	private String consigneeAreaCode;// 收货人座机区号
	private String consigneeLandline;// 收货人座机号码

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeEmail() {
		return consigneeEmail;
	}

	public void setConsigneeEmail(String consigneeEmail) {
		this.consigneeEmail = consigneeEmail;
	}

	public String getInvoiceCompanyName() {
		return invoiceCompanyName;
	}

	public void setInvoiceCompanyName(String invoiceCompanyName) {
		this.invoiceCompanyName = invoiceCompanyName;
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId;
	}

	public String getInvoicePerson() {
		return invoicePerson;
	}

	public void setInvoicePerson(String invoicePerson) {
		this.invoicePerson = invoicePerson;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getConsigneeAddressProvince() {
		return consigneeAddressProvince;
	}

	public void setConsigneeAddressProvince(String consigneeAddressProvince) {
		this.consigneeAddressProvince = consigneeAddressProvince;
	}

	public String getConsigneeAddressCity() {
		return consigneeAddressCity;
	}

	public void setConsigneeAddressCity(String consigneeAddressCity) {
		this.consigneeAddressCity = consigneeAddressCity;
	}

	public String getConsigneeAddressDistrict() {
		return consigneeAddressDistrict;
	}

	public void setConsigneeAddressDistrict(String consigneeAddressDistrict) {
		this.consigneeAddressDistrict = consigneeAddressDistrict;
	}

	public String getConsigneeAddressTown() {
		return consigneeAddressTown;
	}

	public void setConsigneeAddressTown(String consigneeAddressTown) {
		this.consigneeAddressTown = consigneeAddressTown;
	}

	public String getConsigneeAddressDetail() {
		return consigneeAddressDetail;
	}

	public void setConsigneeAddressDetail(String consigneeAddressDetail) {
		this.consigneeAddressDetail = consigneeAddressDetail;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getInvoiceAddressProvince() {
		return invoiceAddressProvince;
	}

	public void setInvoiceAddressProvince(String invoiceAddressProvince) {
		this.invoiceAddressProvince = invoiceAddressProvince;
	}

	public String getInvoiceAddressCity() {
		return invoiceAddressCity;
	}

	public void setInvoiceAddressCity(String invoiceAddressCity) {
		this.invoiceAddressCity = invoiceAddressCity;
	}

	public String getInvoiceAddressCounty() {
		return invoiceAddressCounty;
	}

	public void setInvoiceAddressCounty(String invoiceAddressCounty) {
		this.invoiceAddressCounty = invoiceAddressCounty;
	}

	public String getInvoiceAddressTown() {
		return invoiceAddressTown;
	}

	public void setInvoiceAddressTown(String invoiceAddressTown) {
		this.invoiceAddressTown = invoiceAddressTown;
	}

	public String getInvoiceAddressDetail() {
		return invoiceAddressDetail;
	}

	public void setInvoiceAddressDetail(String invoiceAddressDetail) {
		this.invoiceAddressDetail = invoiceAddressDetail;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public List<String> getStrList() {
		return strList;
	}

	public void setStrList(List<String> strList) {
		this.strList = strList;
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

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getConsigneeLandline() {
		return consigneeLandline;
	}

	public void setConsigneeLandline(String consigneeLandline) {
		this.consigneeLandline = consigneeLandline;
	}

	public String getConsigneeAreaCode() {
		return consigneeAreaCode;
	}

	public void setConsigneeAreaCode(String consigneeAreaCode) {
		this.consigneeAreaCode = consigneeAreaCode;
	}

	public String getInvoiceNotify() {
		return invoiceNotify;
	}

	public void setInvoiceNotify(String invoiceNotify) {
		this.invoiceNotify = invoiceNotify;
	}
	
	
}
