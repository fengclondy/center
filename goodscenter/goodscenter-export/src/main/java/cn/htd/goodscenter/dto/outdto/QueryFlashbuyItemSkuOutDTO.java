package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;

import cn.htd.goodscenter.domain.ItemPicture;

public class QueryFlashbuyItemSkuOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6696036143577758466L;
	
	private String skuCode;
	private String itemCode;
	private Long itemId;
	private Long skuId;
	private String itemName;
	private String supplierName;
	/**
	 * 1 平台公司
	 * 2 外部供应商
	 */
	private String productChannelCode;
	//供应商Id
	private Long supplierId;
	//第一张主图
	private ItemPicture firstPicture;
	
	private String supplierCode;
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProductChannelCode() {
		return productChannelCode;
	}

	public void setProductChannelCode(String productChannelCode) {
		this.productChannelCode = productChannelCode;
	}

	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public ItemPicture getFirstPicture() {
		return firstPicture;
	}
	public void setFirstPicture(ItemPicture firstPicture) {
		this.firstPicture = firstPicture;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

}
