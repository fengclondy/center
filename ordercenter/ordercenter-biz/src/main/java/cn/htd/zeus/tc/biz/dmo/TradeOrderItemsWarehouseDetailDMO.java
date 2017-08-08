package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;

public class TradeOrderItemsWarehouseDetailDMO {
	
    private Long id;//订单行拆单明细信息表主键id

    private String orderNo;//订单号

    private String orderItemNo;//订单行号

    private String skuCode;//商品SKU编码

    private Integer goodsCount;//商品数量

    private String warehouseCode;//仓库代码

    private String warehouseName;//仓库名称

    private String supplierCode;//供货商编码

    private String supplierName;//供货商名称

    private String purchaseDepartmentCode;//采购部门编码

    private String purchaseDepartmentName;//采购部门名称

    private String productAttribute;//商品属性

    private Integer availableInventory;//可用库存

    private Integer isAgreementSku;//是否协议商品

    private String agreementCode;//协议编号

    private Integer deleteFlag;//删除标记

    private Long createId;//创建人ID

    private String createName;//创建人名称

    private Date createTime;//创建时间

    private Long modifyId;//更新人ID

    private String modifyName;//更新人名称

    private Date modifyTime;//更新时间

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

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
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
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPurchaseDepartmentCode() {
		return purchaseDepartmentCode;
	}

	public void setPurchaseDepartmentCode(String purchaseDepartmentCode) {
		this.purchaseDepartmentCode = purchaseDepartmentCode;
	}

	public String getPurchaseDepartmentName() {
		return purchaseDepartmentName;
	}

	public void setPurchaseDepartmentName(String purchaseDepartmentName) {
		this.purchaseDepartmentName = purchaseDepartmentName;
	}

	public String getProductAttribute() {
		return productAttribute;
	}

	public void setProductAttribute(String productAttribute) {
		this.productAttribute = productAttribute;
	}

	public Integer getAvailableInventory() {
		return availableInventory;
	}

	public void setAvailableInventory(Integer availableInventory) {
		this.availableInventory = availableInventory;
	}

	public Integer getIsAgreementSku() {
		return isAgreementSku;
	}

	public void setIsAgreementSku(Integer isAgreementSku) {
		this.isAgreementSku = isAgreementSku;
	}

	public String getAgreementCode() {
		return agreementCode;
	}

	public void setAgreementCode(String agreementCode) {
		this.agreementCode = agreementCode;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
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
	
}