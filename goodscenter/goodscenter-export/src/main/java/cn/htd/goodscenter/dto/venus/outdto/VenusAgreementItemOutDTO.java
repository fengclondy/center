package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;

public class VenusAgreementItemOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960444631930340198L;
	//商品编码（商城）
	private String itemCode;
	//商品名称
	private String itemName;
	//品牌名称
	private String brandName;
	private String agreementCode;
	//品类名称
	private String categoryName;
	//仓库名称
	private String warehouseName;
	//商品属性
	private String prodAttr;
	//销售部门
	private String salesDept;
	//供货商名称
	private String supplierName;
	//分配数量
	private Long stockQty;
	//销售数量
	private Long purchaseQty;
	//剩余数量
	private Long remainingQty;
	//有效期起
	private String startDate;
	//有效期止
	private String endDate;
	//商品ERP编码
	private String erpCode;
    //商品ERP品类名称
	private String erpCatName;
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getAgreementCode() {
		return agreementCode;
	}
	public void setAgreementCode(String agreementCode) {
		this.agreementCode = agreementCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getProdAttr() {
		return prodAttr;
	}
	public void setProdAttr(String prodAttr) {
		this.prodAttr = prodAttr;
	}
	public String getSalesDept() {
		return salesDept;
	}
	public void setSalesDept(String salesDept) {
		this.salesDept = salesDept;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public Long getStockQty() {
		return stockQty;
	}
	public void setStockQty(Long stockQty) {
		this.stockQty = stockQty;
	}
	public Long getPurchaseQty() {
		return purchaseQty;
	}
	public void setPurchaseQty(Long purchaseQty) {
		this.purchaseQty = purchaseQty;
	}
	public Long getRemainingQty() {
		return remainingQty;
	}
	public void setRemainingQty(Long remainingQty) {
		this.remainingQty = remainingQty;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	public String getErpCatName() {
		return erpCatName;
	}
	public void setErpCatName(String erpCatName) {
		this.erpCatName = erpCatName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


}
