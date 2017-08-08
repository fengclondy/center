package cn.htd.goodscenter.dto.venus.indto;

import java.io.Serializable;

public class VenusStockItemInDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9181361811582006840L;
	private Long supplierId;
	private String supplierCode;
	private String isAgreement;
	private String pageCount;
	private String pageIndex;
	private String canSellNum;
	private String distributionNum;
	private String surplusNum;
	private String productName;
	private String hasPage;
	private String productCode;
	private String brandName;
	private String categroyName;
	private String stockAttr;
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getIsAgreement() {
		return isAgreement;
	}
	public void setIsAgreement(String isAgreement) {
		this.isAgreement = isAgreement;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getCanSellNum() {
		return canSellNum;
	}
	public void setCanSellNum(String canSellNum) {
		this.canSellNum = canSellNum;
	}
	public String getDistributionNum() {
		return distributionNum;
	}
	public void setDistributionNum(String distributionNum) {
		this.distributionNum = distributionNum;
	}
	public String getSurplusNum() {
		return surplusNum;
	}
	public void setSurplusNum(String surplusNum) {
		this.surplusNum = surplusNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getHasPage() {
		return hasPage;
	}
	public void setHasPage(String hasPage) {
		this.hasPage = hasPage;
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
	public String getCategroyName() {
		return categroyName;
	}
	public void setCategroyName(String categroyName) {
		this.categroyName = categroyName;
	}
	public String getStockAttr() {
		return stockAttr;
	}
	public void setStockAttr(String stockAttr) {
		this.stockAttr = stockAttr;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
}
