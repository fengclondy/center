package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;

/**
 * 
 * @author zhangxiaolong
 *
 */
public class VenusItemSkuPublishInfoOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5288647410810056223L;
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
    //库存
	private Long totalStock;
	//可见库存
	private Long publishStock;
	//起订量
	private Integer minPurchaseNum;
	//限订量
	private Integer maxPurchaseNum;
	//上架状态
	private String shelfStatus;
	//上架类型 1 包厢上架 2 大厅上架
	private String shelfType;
	//商品spuCode
	private String spuCode;
	
	private Integer totalCount;
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
	public Long getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(Long totalStock) {
		this.totalStock = totalStock;
	}
	public Long getPublishStock() {
		return publishStock;
	}
	public void setPublishStock(Long publishStock) {
		this.publishStock = publishStock;
	}
	public Integer getMinPurchaseNum() {
		return minPurchaseNum;
	}
	public void setMinPurchaseNum(Integer minPurchaseNum) {
		this.minPurchaseNum = minPurchaseNum;
	}
	public Integer getMaxPurchaseNum() {
		return maxPurchaseNum;
	}
	public void setMaxPurchaseNum(Integer maxPurchaseNum) {
		this.maxPurchaseNum = maxPurchaseNum;
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
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getShelfType() {
		return shelfType;
	}
	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
	}
	public String getSpuCode() {
		return spuCode;
	}
	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}
	
	
}
