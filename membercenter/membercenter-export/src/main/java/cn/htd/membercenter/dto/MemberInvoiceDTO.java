package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberInvoiceDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	// 主键
	private String invoiceId;
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
	// 下行ERP状态
	private String erpStatus;
	// 下行ERP时间
	private Date erpDownTime;
	// 下行ERP错误信息
	private String erpErrorMsg;
	private String createId;
	private String createName;
	private Date createTime;
	private String modifyId;
	private String modifyName;
	private Date modifyTime;
	// 发票日志记录类型
	private String recordType;

	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @param invoiceId
	 *            the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * @return the erpStatus
	 */
	public String getErpStatus() {
		return erpStatus;
	}

	/**
	 * @param erpStatus
	 *            the erpStatus to set
	 */
	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	/**
	 * @return the erpDownTime
	 */
	public Date getErpDownTime() {
		return erpDownTime;
	}

	/**
	 * @param erpDownTime
	 *            the erpDownTime to set
	 */
	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}

	/**
	 * @return the erpErrorMsg
	 */
	public String getErpErrorMsg() {
		return erpErrorMsg;
	}

	/**
	 * @param erpErrorMsg
	 *            the erpErrorMsg to set
	 */
	public void setErpErrorMsg(String erpErrorMsg) {
		this.erpErrorMsg = erpErrorMsg;
	}

	/**
	 * @return the createId
	 */
	public String getCreateId() {
		return createId;
	}

	/**
	 * @param createId
	 *            the createId to set
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}

	/**
	 * @param createName
	 *            the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyId
	 */
	public String getModifyId() {
		return modifyId;
	}

	/**
	 * @param modifyId
	 *            the modifyId to set
	 */
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	/**
	 * @return the modifyName
	 */
	public String getModifyName() {
		return modifyName;
	}

	/**
	 * @param modifyName
	 *            the modifyName to set
	 */
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the recordType
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @param recordType
	 *            the recordType to set
	 */
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

}
