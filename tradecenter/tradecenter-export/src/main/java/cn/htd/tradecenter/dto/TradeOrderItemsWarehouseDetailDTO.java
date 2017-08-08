package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class TradeOrderItemsWarehouseDetailDTO implements Serializable {

	private static final long serialVersionUID = 2958840487272373829L;

	private Long id;

	private String orderNo;

	private String orderItemNo;

	private String skuCode;

	private Integer goodsCount;

	private String warehouseCode;

	private String warehouseName;

	private String supplierCode;

	private String supplierName;

	private String purchaseDepartmentCode;

	private String purchaseDepartmentName;

	private String productAttribute;

	private Integer availableInventory;

	private int isAgreementSku;

	private String agreementCode;

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

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo == null ? null : orderItemNo.trim();
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode == null ? null : skuCode.trim();
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName == null ? null : warehouseName.trim();
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode == null ? null : supplierCode.trim();
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName == null ? null : supplierName.trim();
	}

	public String getPurchaseDepartmentCode() {
		return purchaseDepartmentCode;
	}

	public void setPurchaseDepartmentCode(String purchaseDepartmentCode) {
		this.purchaseDepartmentCode = purchaseDepartmentCode == null ? null : purchaseDepartmentCode.trim();
	}

	public String getPurchaseDepartmentName() {
		return purchaseDepartmentName;
	}

	public void setPurchaseDepartmentName(String purchaseDepartmentName) {
		this.purchaseDepartmentName = purchaseDepartmentName == null ? null : purchaseDepartmentName.trim();
	}

	public String getProductAttribute() {
		return productAttribute;
	}

	public void setProductAttribute(String productAttribute) {
		this.productAttribute = productAttribute == null ? null : productAttribute.trim();
	}

	public Integer getAvailableInventory() {
		return availableInventory;
	}

	public void setAvailableInventory(Integer availableInventory) {
		this.availableInventory = availableInventory;
	}

	public int getIsAgreementSku() {
		return isAgreementSku;
	}

	public void setIsAgreementSku(int isAgreementSku) {
		this.isAgreementSku = isAgreementSku;
	}

	public String getAgreementCode() {
		return agreementCode;
	}

	public void setAgreementCode(String agreementCode) {
		this.agreementCode = agreementCode == null ? null : agreementCode.trim();
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

	public void setTradeOrderItemsWarehouseDetailDTO(TradeOrderItemsWarehouseDetailDTO warehouseDTO) {
		this.id = warehouseDTO.getId();
		this.orderNo = warehouseDTO.getOrderNo();
		this.orderItemNo = warehouseDTO.getOrderItemNo();
		this.skuCode = warehouseDTO.getSkuCode();
		this.goodsCount = warehouseDTO.getGoodsCount();
		this.warehouseCode = warehouseDTO.getWarehouseCode();
		this.warehouseName = warehouseDTO.getWarehouseName();
		this.supplierCode = warehouseDTO.getSupplierCode();
		this.supplierName = warehouseDTO.getSupplierName();
		this.purchaseDepartmentCode = warehouseDTO.getPurchaseDepartmentCode();
		this.purchaseDepartmentName = warehouseDTO.getPurchaseDepartmentName();
		this.productAttribute = warehouseDTO.getProductAttribute();
		this.availableInventory = warehouseDTO.getAvailableInventory();
		this.isAgreementSku = warehouseDTO.getIsAgreementSku();
		this.agreementCode = warehouseDTO.getAgreementCode();
		this.deleteFlag = warehouseDTO.getDeleteFlag();
		this.createId = warehouseDTO.getCreateId();
		this.createName = warehouseDTO.getCreateName();
		this.createTime = warehouseDTO.getCreateTime();
		this.modifyId = warehouseDTO.getModifyId();
		this.modifyName = warehouseDTO.getModifyName();
		this.modifyTime = warehouseDTO.getModifyTime();
	}
}