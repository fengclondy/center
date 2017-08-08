package cn.htd.goodscenter.dto.vip;

import java.io.Serializable;

public class VipItemEntryInfoDTO implements Serializable{

	private static final long serialVersionUID = 672963234374772809L;
	
	private Long id;

	private Long skuId;

	private String skuCode; // 组成项skuCode编码

	private Long itemId;

	private String itemName; // 商品名称

	private Long basePrice; // 供货价 必须是整数 单元： 元

    private Long salePrice; // 销售价 必须是整数 单元： 元

	private String supplierName; // 供货商名称

	private Integer productNumber; // 商品数量

	private Long productAmount; // 商品价格 （salePrice * productNumber）

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Long basePrice) {
		this.basePrice = basePrice;
	}

	public Long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public Long getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Long productAmount) {
		this.productAmount = productAmount;
	}
}
