package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;

public class OrderCompensateERPDMO {
	private Long id;//订单分销单信息表的主键id
	private String orderNo;//订单号
	private String categroyCode;//品类ID
	private Long brandCode;//品牌ID
	private String orderItemNos;//订单行号  分销单关联的订单行号拼接起来   订单行id1，订单行id2，。。。。
	private String saleAssistantCode;//客户经理编号
	private String salesMan;//客户经理名称
	private String serviceArea;//服务区域
	private String supplierCode;//卖家编号
	private String memberCode;//会员ID
	private String salesDepartmentCode;//销售部门编码
	private String saleType;//销售方式
	private String paymentMethod;//支付方式
	private String invoiceType;//发票类型
	private String recieverAddress;//收货地址
	private String recieverPhone;//收货人联系电话
	private String recieverName;//收货人姓名
    private String sourceId;//订单来源
    private Integer isTimelimitedOrder;//是否为秒杀订单 0-否，1-是
    private String promotionId;//秒杀活动编码
    private String orderFrom;//订单来源 1：商城，2：VMS开单，3：超级老板PC，4：超级老板APP
    private String erpLockBalanceCode;//ERP锁定余额编号
    
    private String buyerRemarks;//买家留言
    private String orderRemarks;//订单备注
    
    private Date modifyTime;
    
    private String erpStatus;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCategroyCode() {
		return categroyCode;
	}
	public void setCategroyCode(String categroyCode) {
		this.categroyCode = categroyCode;
	}
	public Long getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(Long brandCode) {
		this.brandCode = brandCode;
	}
	public String getOrderItemNos() {
		return orderItemNos;
	}
	public void setOrderItemNos(String orderItemNos) {
		this.orderItemNos = orderItemNos;
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
	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}
	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
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
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getIsTimelimitedOrder() {
		return isTimelimitedOrder;
	}
	public void setIsTimelimitedOrder(Integer isTimelimitedOrder) {
		this.isTimelimitedOrder = isTimelimitedOrder;
	}
	public String getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	public String getOrderFrom() {
		return orderFrom;
	}
	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}
	public String getErpLockBalanceCode() {
		return erpLockBalanceCode;
	}
	public void setErpLockBalanceCode(String erpLockBalanceCode) {
		this.erpLockBalanceCode = erpLockBalanceCode;
	}
	public String getBuyerRemarks() {
		return buyerRemarks;
	}
	public void setBuyerRemarks(String buyerRemarks) {
		this.buyerRemarks = buyerRemarks;
	}
	public String getOrderRemarks() {
		return orderRemarks;
	}
	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getErpStatus() {
		return erpStatus;
	}
	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}
	
}
