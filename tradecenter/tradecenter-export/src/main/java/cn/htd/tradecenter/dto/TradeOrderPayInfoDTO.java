package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TradeOrderPayInfoDTO implements Serializable {

	private static final long serialVersionUID = 7682625407279199308L;

	private Long id;

	private String downOrderNo;

	private int orderType;

	private int payResultStatus;

	private String payResultCode;

	private String payResultMsg;

	private String payLastMessageId;

	private Date payLastTime;

	private int paySendCount;

	private String brandCode;

	private String classCode;

	private BigDecimal amount = BigDecimal.ZERO;

	private String departmentCode;

	private String payStatus;

	private String payType;

	private String orderNo;

	private String supplierCode;

	private int isLockBalanceFlag;

	private String memberCode;

	private String productCode;

	private int isRushReceivable;

	private String remarks;

	private String orderFrom;

	private int deleteFlag;

	private Date createTime;

	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDownOrderNo() {
		return downOrderNo;
	}

	public void setDownOrderNo(String downOrderNo) {
		this.downOrderNo = downOrderNo;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getPayResultStatus() {
		return payResultStatus;
	}

	public void setPayResultStatus(int payResultStatus) {
		this.payResultStatus = payResultStatus;
	}

	public String getPayResultCode() {
		return payResultCode;
	}

	public void setPayResultCode(String payResultCode) {
		this.payResultCode = payResultCode;
	}

	public String getPayResultMsg() {
		return payResultMsg;
	}

	public void setPayResultMsg(String payResultMsg) {
		this.payResultMsg = payResultMsg;
	}

	public String getPayLastMessageId() {
		return payLastMessageId;
	}

	public void setPayLastMessageId(String payLastMessageId) {
		this.payLastMessageId = payLastMessageId;
	}

	public Date getPayLastTime() {
		return payLastTime;
	}

	public void setPayLastTime(Date payLastTime) {
		this.payLastTime = payLastTime;
	}

	public int getPaySendCount() {
		return paySendCount;
	}

	public void setPaySendCount(int paySendCount) {
		this.paySendCount = paySendCount;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public int getIsLockBalanceFlag() {
		return isLockBalanceFlag;
	}

	public void setIsLockBalanceFlag(int isLockBalanceFlag) {
		this.isLockBalanceFlag = isLockBalanceFlag;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getIsRushReceivable() {
		return isRushReceivable;
	}

	public void setIsRushReceivable(int isRushReceivable) {
		this.isRushReceivable = isRushReceivable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}