package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;

public class VenusStockItemOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5351018287680708029L;
	//商品编码（商城）
	private String itemCode;
	//商品名称
	private String itemName;
	//品牌名称
	private String brandName;
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
	//可卖数
	private Long avaliableQty;
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
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	public Long getAvaliableQty() {
		return avaliableQty;
	}
	public void setAvaliableQty(Long avaliableQty) {
		this.avaliableQty = avaliableQty;
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
	
	
}
