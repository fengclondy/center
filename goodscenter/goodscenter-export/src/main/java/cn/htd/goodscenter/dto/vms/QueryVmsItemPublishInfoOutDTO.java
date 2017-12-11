package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;
import java.util.Date;

/**
 * 包厢列表查询
 * @author chenkang
 * @date 2017-12-11
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
	private Long categoryId;
    //商品目录名称
	private String categoryName;
	//品牌id
	private Long brandId;
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
	private Integer shelfStatus;
	//是否包厢  0 大厅上架 1 包厢上架
	private Integer isBoxFlag;
	//商品spuCode
	private String spuCode;
	//创建时间
	private Date createTime;

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
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
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
	public Integer getShelfStatus() {
		return shelfStatus;
	}
	public void setShelfStatus(Integer shelfStatus) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
