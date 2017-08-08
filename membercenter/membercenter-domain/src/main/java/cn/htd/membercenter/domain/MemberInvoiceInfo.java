package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class MemberInvoiceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long invoiceId;// 发票ID

	private Long memberId;// 会员ID

	private Long channelId;// 外接渠道编码

	private String invoiceNotify;// 普通发票抬头

	private String invoiceCompanyName;// 增值税发票公司名称

	private String taxManId;// 纳税人识别号

	private String bankName;// 开户行名称

	private String bankAccount;// 银行账号

	private String contactPhone;// 联系电话

	private String invoiceAddressProvince;// 发票邮寄地址-省

	private String invoiceAddressCity;// 发票邮寄地址-市

	private String invoiceAddressCounty;// 发票邮寄地址-区

	private String invoiceAddressTown;// 发票邮寄地址-镇

	private String invoiceAddressDetail;// 发票邮寄地址-详细

	private String invoiceAddress;// 发票邮寄地址-详细

	private Integer deleteFlag;// 删除标记

	private String erpStatus;// ERP下行状态

	private Date erpDownTime;// ERP下行时间

	private String erpErrorMsg;// ERP下行错误信息

	private Long createId;// 创建人ID

	private String invoicePerson;

	private String createName;// 创建人名称

	private Date createTime;// 创建时间

	private Long modifyId;// 更新人ID

	private String modifyName;// 更新人名称

	private Date modifyTime;// 更新时间

	private String channelCode;// 外接渠道编码

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getInvoiceNotify() {
		return invoiceNotify;
	}

	public void setInvoiceNotify(String invoiceNotify) {
		this.invoiceNotify = invoiceNotify == null ? null : invoiceNotify.trim();
	}

	public String getInvoiceCompanyName() {
		return invoiceCompanyName;
	}

	public void setInvoiceCompanyName(String invoiceCompanyName) {
		this.invoiceCompanyName = invoiceCompanyName == null ? null : invoiceCompanyName.trim();
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId == null ? null : taxManId.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount == null ? null : bankAccount.trim();
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone == null ? null : contactPhone.trim();
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
		this.invoiceAddressDetail = invoiceAddressDetail == null ? null : invoiceAddressDetail.trim();
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus == null ? null : erpStatus.trim();
	}

	public Date getErpDownTime() {
		return erpDownTime;
	}

	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}

	public String getErpErrorMsg() {
		return erpErrorMsg;
	}

	public void setErpErrorMsg(String erpErrorMsg) {
		this.erpErrorMsg = erpErrorMsg == null ? null : erpErrorMsg.trim();
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
	 * @return the invoiceAddress
	 */
	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	/**
	 * @param invoiceAddress the invoiceAddress to set
	 */
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
}