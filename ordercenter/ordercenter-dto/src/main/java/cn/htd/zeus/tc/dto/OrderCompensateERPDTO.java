package cn.htd.zeus.tc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCompensateERPDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5874686500711221999L;
	
	private Long id;
	private String issueLogId;
	private String supplierCode;
	private String categroyCode;
	private String brandCode;
	private String memberCode;
	private String saleAssistantCode;
	private String salesMan;
	private String paymentTotal;
	private String saleType;
	private String rebateAmountTotal;
	private String paymentMethod;
	private String operateCode;
	private String operateName;
	private String orderNote;
	private String invoiceType;
	private String creditNode;
	private String serviceArea;
	private String recieverAddress;
	private String recieverPhone;
	private String recieverName;
	private String deliveryNote;
	private String sourceId;
	private String orderNo;
	private List<Map<String,Object>> orderDetail;
	private List<Map<String,Object>> rebateDetail;
	private String messageId;
	private String departmentCode;//销售部门编码
	
	private String masterOrderNo;
	
	private String shippingMethod;//1-供货商配送,2自提
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIssueLogId() {
		return issueLogId;
	}
	public void setIssueLogId(String issueLogId) {
		this.issueLogId = issueLogId;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getCategroyCode() {
		return categroyCode;
	}
	public void setCategroyCode(String categroyCode) {
		this.categroyCode = categroyCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getSaleAssistantCode() {
		return saleAssistantCode;
	}
	public void setSaleAssistantCode(String saleAssistantCode) {
		this.saleAssistantCode = saleAssistantCode;
	}
	public String getSalesMan() {
		return salesMan;
	}
	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}
	public String getPaymentTotal() {
		return paymentTotal;
	}
	public void setPaymentTotal(String paymentTotal) {
		this.paymentTotal = paymentTotal;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getRebateAmountTotal() {
		return rebateAmountTotal;
	}
	public void setRebateAmountTotal(String rebateAmountTotal) {
		this.rebateAmountTotal = rebateAmountTotal;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getOperateCode() {
		return operateCode;
	}
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getOrderNote() {
		return orderNote;
	}
	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getCreditNode() {
		return creditNode;
	}
	public void setCreditNode(String creditNode) {
		this.creditNode = creditNode;
	}
	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
	public String getRecieverAddress() {
		return recieverAddress;
	}
	public void setRecieverAddress(String recieverAddress) {
		this.recieverAddress = recieverAddress;
	}
	public String getRecieverPhone() {
		return recieverPhone;
	}
	public void setRecieverPhone(String recieverPhone) {
		this.recieverPhone = recieverPhone;
	}
	public String getRecieverName() {
		return recieverName;
	}
	public void setRecieverName(String recieverName) {
		this.recieverName = recieverName;
	}
	public String getDeliveryNote() {
		return deliveryNote;
	}
	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<Map<String, Object>> getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(List<Map<String, Object>> orderDetail) {
		this.orderDetail = orderDetail;
	}
	public List<Map<String, Object>> getRebateDetail() {
		return rebateDetail;
	}
	public void setRebateDetail(List<Map<String, Object>> rebateDetail) {
		this.rebateDetail = rebateDetail;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getMasterOrderNo() {
		return masterOrderNo;
	}
	public void setMasterOrderNo(String masterOrderNo) {
		this.masterOrderNo = masterOrderNo;
	}
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	
}
