/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	CreateTradeOrderOutDTO.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: 订单基础DTO  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class CreateTradeOrderDTO implements Serializable {

	private static final long serialVersionUID = -8915134329788703999L;

	/**
	 * 买家ID
	 */
	@NotNull(message = "买家ID不能为空")
	private Long buyerId;

	/**
	 * 卖家ID
	 */
	@NotNull(message = "卖家ID不能为空")
	private Long sellerId;

	/**
	 * 订单备注
	 */
	@Length(max = 512, message = "订单备注超长")
	private String orderRemarks = "";

	/**
	 * 配送方式 1:供应商配送，2:自提
	 */
	@NotBlank(message = "配送方式不能为空")
	private String deliveryType;
	/**
	 * 收货人姓名
	 */
	@NotBlank(message = "收货人姓名不能为空")
	@Length(min = 1, max = 128, message = "收货人姓名超长")
	private String consigneeName;

	/**
	 * 收货人联系电话
	 */
	@NotBlank(message = "收货人联系电话不能为空")
	@Length(max = 32, message = "收货人联系电话超长")
	private String consigneePhoneNum;

	/**
	 * 收货地址
	 */
	private String consigneeAddress;

	/**
	 * 收货地址-省
	 */
	private String consigneeAddressProvince;

	/**
	 * 收货地址-市
	 */
	private String consigneeAddressCity;

	/**
	 * 收货地址-区
	 */
	private String consigneeAddressDistrict;

	/**
	 * 收货地址-镇
	 */
	private String consigneeAddressTown;

	/**
	 * 收货地址-详细
	 */
	private String consigneeAddressDetail;

	/**
	 * 收货地址-邮编
	 */
	private String postCode = "";

	/**
	 * 是否需要发票
	 */
	@Range(min = 0, max = 1, message = "是否需要发票未选择")
	private int isNeedInvoice = 1;

	/**
	 * 发票类型
	 */
	@NotBlank(message = "发票类型不能为空")
	private String invoiceType;

	/**
	 * 普通发票抬头
	 */
	@Length(max = 255, message = "普通发票抬头超长")
	private String invoiceNotify = "";

	/**
	 * 增值税发票公司名称
	 */
	@Length(max = 255, message = "增值税发票公司名称超长")
	private String invoiceCompanyName = "";

	/**
	 * 纳税人识别号
	 */
	@Length(max = 128, message = "纳税人识别号超长")
	private String taxManId = "";

	/**
	 * 开户行名称
	 */
	@Length(max = 255, message = "开户行名称超长")
	private String bankName = "";

	/**
	 * 银行账号
	 */
	@Length(max = 255, message = "银行账号超长")
	private String bankAccount = "";

	/**
	 * 发票联系电话
	 */
	@Length(max = 20, message = "发票联系电话超长")
	private String contactPhone = "";

	/**
	 * 发票邮寄地址
	 */
	@Length(max = 1024, message = "发票邮寄地址超长")
	private String invoiceAddress = "";

	/**
	 * 操作者ID
	 */
	@NotNull(message = "操作者ID不能为空")
	private Long operatorId;

	/**
	 * 操作者名称
	 */
	@NotBlank(message = "操作者名称不能为空")
	@Length(max = 255, message = "操作者名称超长")
	private String operatorName;

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneePhoneNum() {
		return consigneePhoneNum;
	}

	public void setConsigneePhoneNum(String consigneePhoneNum) {
		this.consigneePhoneNum = consigneePhoneNum;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public int getIsNeedInvoice() {
		return isNeedInvoice;
	}

	public void setIsNeedInvoice(int isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceNotify() {
		return invoiceNotify;
	}

	public void setInvoiceNotify(String invoiceNotify) {
		this.invoiceNotify = invoiceNotify;
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

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
