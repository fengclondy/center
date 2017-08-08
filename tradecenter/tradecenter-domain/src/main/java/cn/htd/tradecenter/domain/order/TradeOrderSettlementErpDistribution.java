package cn.htd.tradecenter.domain.order;

import java.io.Serializable;
import java.util.Date;

public class TradeOrderSettlementErpDistribution implements Serializable {

	private static final long serialVersionUID = 2314155064537576614L;

	private Long id;

	private String orderNo;

	private Long buyerId;

	private Long sellerId;

	private Long categoryId;

	private String categoryName;

	private String erpFiveCategoryCode;

	private Long brandId;

	private String brandName;

	private String serviceArea;

	private String customerManagerCode;

	private String customerManagerName;

	private String orderItemNos;

	private String erpStatus;

	private int erpDownTimes;

	private Date erpDownTime;

	private String erpSholesalerCode;

	private String eprStrikeaBalanceCode;

	private String erpWholesalePaymentCode;

	private String erpCreateRecordCode;

	private String erpCompanyCode;

	private String erpSholesalerStatus;

	private int deleteFlag;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

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
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getErpFiveCategoryCode() {
		return erpFiveCategoryCode;
	}

	public void setErpFiveCategoryCode(String erpFiveCategoryCode) {
		this.erpFiveCategoryCode = erpFiveCategoryCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea == null ? null : serviceArea.trim();
	}

	public String getCustomerManagerCode() {
		return customerManagerCode;
	}

	public void setCustomerManagerCode(String customerManagerCode) {
		this.customerManagerCode = customerManagerCode == null ? null : customerManagerCode.trim();
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName == null ? null : customerManagerName.trim();
	}

	public String getOrderItemNos() {
		return orderItemNos;
	}

	public void setOrderItemNos(String orderItemNos) {
		this.orderItemNos = orderItemNos == null ? null : orderItemNos.trim();
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus == null ? null : erpStatus.trim();
	}

	public int getErpDownTimes() {
		return erpDownTimes;
	}

	public void setErpDownTimes(int erpDownTimes) {
		this.erpDownTimes = erpDownTimes;
	}

	public Date getErpDownTime() {
		return erpDownTime;
	}

	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}

	public String getErpSholesalerCode() {
		return erpSholesalerCode;
	}

	public void setErpSholesalerCode(String erpSholesalerCode) {
		this.erpSholesalerCode = erpSholesalerCode == null ? null : erpSholesalerCode.trim();
	}

	public String getEprStrikeaBalanceCode() {
		return eprStrikeaBalanceCode;
	}

	public void setEprStrikeaBalanceCode(String eprStrikeaBalanceCode) {
		this.eprStrikeaBalanceCode = eprStrikeaBalanceCode == null ? null : eprStrikeaBalanceCode.trim();
	}

	public String getErpWholesalePaymentCode() {
		return erpWholesalePaymentCode;
	}

	public void setErpWholesalePaymentCode(String erpWholesalePaymentCode) {
		this.erpWholesalePaymentCode = erpWholesalePaymentCode == null ? null : erpWholesalePaymentCode.trim();
	}

	public String getErpCreateRecordCode() {
		return erpCreateRecordCode;
	}

	public void setErpCreateRecordCode(String erpCreateRecordCode) {
		this.erpCreateRecordCode = erpCreateRecordCode == null ? null : erpCreateRecordCode.trim();
	}

	public String getErpCompanyCode() {
		return erpCompanyCode;
	}

	public void setErpCompanyCode(String erpCompanyCode) {
		this.erpCompanyCode = erpCompanyCode == null ? null : erpCompanyCode.trim();
	}

	public String getErpSholesalerStatus() {
		return erpSholesalerStatus;
	}

	public void setErpSholesalerStatus(String erpSholesalerStatus) {
		this.erpSholesalerStatus = erpSholesalerStatus == null ? null : erpSholesalerStatus.trim();
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
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
}