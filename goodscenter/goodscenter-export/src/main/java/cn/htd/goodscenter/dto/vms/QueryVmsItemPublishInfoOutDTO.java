package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;

/**
 * 
 * @author zhangxiaolong
 *
 */
public class QueryVmsItemPublishInfoOutDTO implements Serializable{

	private Long itemId;
	//商品名称
	private String itemName;
	//商品skuId
	private Long skuId;
	//商品编码
	private String itemCode;
	//商品SKU编码
	private String skuCode;
	//商品目录id
	private String categoryId;
    //商品目录名称
	private String categoryName;
	//品牌id
	private String brandId;
	//品牌名称
	private String brandName;
	//分销限价
	private String saleLimitedPrice;
	//销售单价
	private String salePrice;
	//零售价
	private String retailPrice;
	//可见库存
	private Long publishStock;
	//上架状态
	private String shelfStatus;
	//是否包厢  0 大厅上架 1 包厢上架
	private Integer isBoxFlag;
	//商品spuCode
	private String spuCode;

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getSaleLimitedPrice() {
		return saleLimitedPrice;
	}
	public void setSaleLimitedPrice(String saleLimitedPrice) {
		this.saleLimitedPrice = saleLimitedPrice;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Long getPublishStock() {
		return publishStock;
	}
	public void setPublishStock(Long publishStock) {
		this.publishStock = publishStock;
	}
	public String getShelfStatus() {
		return shelfStatus;
	}
	public void setShelfStatus(String shelfStatus) {
		this.shelfStatus = shelfStatus;
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
	public String getSpuCode() {
		return spuCode;
	}
	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}


	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(Integer isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}
}
