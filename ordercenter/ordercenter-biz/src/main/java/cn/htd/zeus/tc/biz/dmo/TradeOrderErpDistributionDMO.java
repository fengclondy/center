package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;

public class TradeOrderErpDistributionDMO extends GenericDMO{

	/**
	 *
	 */
	private static final long serialVersionUID = 2214556172650429004L;

	private Long id;

	private String orderNo;

	private String buyerCode;

	private String sellerCode;

	private Long brandId;

	private String serviceArea;

	private String customerManagerCode;

	private String customerManagerName;

	private String orderItemNos;

	private String erpLockBalanceCode;

	private String erpStatus;

	private String erpSholesalerCode;

	private String eprStrikeaBalanceCode;

	private String erpWholesalePaymentCode;

	private String erpCreateRecordCode;

	private String erpCompanyCode;

	private String erpSholesalerStatus;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	private Byte deleteFlag;

	private String erpFirstCategoryCode;

	private Long categoryId;

	private Byte erpDownTimes;

	private Date erpDownTime;

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

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
		this.serviceArea = serviceArea;
	}

	public String getCustomerManagerCode() {
		return customerManagerCode;
	}

	public void setCustomerManagerCode(String customerManagerCode) {
		this.customerManagerCode = customerManagerCode;
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	public String getOrderItemNos() {
		return orderItemNos;
	}

	public void setOrderItemNos(String orderItemNos) {
		this.orderItemNos = orderItemNos;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public String getErpSholesalerCode() {
		return erpSholesalerCode;
	}

	public void setErpSholesalerCode(String erpSholesalerCode) {
		this.erpSholesalerCode = erpSholesalerCode;
	}

	public String getEprStrikeaBalanceCode() {
		return eprStrikeaBalanceCode;
	}

	public void setEprStrikeaBalanceCode(String eprStrikeaBalanceCode) {
		this.eprStrikeaBalanceCode = eprStrikeaBalanceCode;
	}

	public String getErpWholesalePaymentCode() {
		return erpWholesalePaymentCode;
	}

	public void setErpWholesalePaymentCode(String erpWholesalePaymentCode) {
		this.erpWholesalePaymentCode = erpWholesalePaymentCode;
	}

	public String getErpCreateRecordCode() {
		return erpCreateRecordCode;
	}

	public void setErpCreateRecordCode(String erpCreateRecordCode) {
		this.erpCreateRecordCode = erpCreateRecordCode;
	}

	public String getErpCompanyCode() {
		return erpCompanyCode;
	}

	public void setErpCompanyCode(String erpCompanyCode) {
		this.erpCompanyCode = erpCompanyCode;
	}

	public String getErpSholesalerStatus() {
		return erpSholesalerStatus;
	}

	public void setErpSholesalerStatus(String erpSholesalerStatus) {
		this.erpSholesalerStatus = erpSholesalerStatus;
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
		this.createName = createName;
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
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Byte getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Byte deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getErpFirstCategoryCode() {
		return erpFirstCategoryCode;
	}

	public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
		this.erpFirstCategoryCode = erpFirstCategoryCode;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Byte getErpDownTimes() {
		return erpDownTimes;
	}

	public void setErpDownTimes(Byte erpDownTimes) {
		this.erpDownTimes = erpDownTimes;
	}

	public String getErpLockBalanceCode() {
		return erpLockBalanceCode;
	}

	public void setErpLockBalanceCode(String erpLockBalanceCode) {
		this.erpLockBalanceCode = erpLockBalanceCode;
	}

	public Date getErpDownTime() {
		return erpDownTime;
	}

	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}
}