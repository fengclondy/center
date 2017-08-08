package cn.htd.goodscenter.dto.middleware.outdto;

import java.io.Serializable;

public class QuerySpecialItemOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3619791809296890413L;
	private String productCode;//	商品编码
	private String  productName;//	商品名称
	private String  categroyName;//	品类名称
	private String  brandName;//	品牌名称
	private String  warehouseName;//	仓库名称
	private String  productAttribute;//	商品属性
	private String  departmentName;//	采购部门名称
	private String  supplierName;//	供货商名称
	private String  canSellNum;//	可卖数
	private String  erpProductCode;//	Erp商品编码
	private String  erpCategreyCode;//	Erp5级类目编码
	private String protocolNum;//	协议号
	private String  allocationNum;//	分配数量
	private String  sellNum;//	销售数量
	private String  surplusNum;//	剩余数量
	private String startTime;//	有效期起
	private String endTime;//	有效期止
	private String protocolPrice;//协议售价
	private String catName;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategroyName() {
		return categroyName;
	}
	public void setCategroyName(String categroyName) {
		this.categroyName = categroyName;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getProductAttribute() {
		return productAttribute;
	}
	public void setProductAttribute(String productAttribute) {
		this.productAttribute = productAttribute;
	}
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getCanSellNum() {
		return canSellNum;
	}
	public void setCanSellNum(String canSellNum) {
		this.canSellNum = canSellNum;
	}
	public String getErpProductCode() {
		return erpProductCode;
	}
	public void setErpProductCode(String erpProductCode) {
		this.erpProductCode = erpProductCode;
	}
	public String getErpCategreyCode() {
		return erpCategreyCode;
	}
	public void setErpCategreyCode(String erpCategreyCode) {
		this.erpCategreyCode = erpCategreyCode;
	}
	public String getProtocolNum() {
		return protocolNum;
	}
	public void setProtocolNum(String protocolNum) {
		this.protocolNum = protocolNum;
	}
	public String getAllocationNum() {
		return allocationNum;
	}
	public void setAllocationNum(String allocationNum) {
		this.allocationNum = allocationNum;
	}
	public String getSellNum() {
		return sellNum;
	}
	public void setSellNum(String sellNum) {
		this.sellNum = sellNum;
	}
	public String getSurplusNum() {
		return surplusNum;
	}
	public void setSurplusNum(String surplusNum) {
		this.surplusNum = surplusNum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getProtocolPrice() {
		return protocolPrice;
	}
	public void setProtocolPrice(String protocolPrice) {
		this.protocolPrice = protocolPrice;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
}
