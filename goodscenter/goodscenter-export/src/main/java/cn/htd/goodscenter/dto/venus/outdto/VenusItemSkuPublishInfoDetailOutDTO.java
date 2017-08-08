package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSalesArea;
import cn.htd.goodscenter.domain.ItemSalesAreaDetail;
import cn.htd.pricecenter.dto.StandardPriceDTO;

public class VenusItemSkuPublishInfoDetailOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2316545766121200548L;
	private Long id;
	//商品Id
	private Long itemId;
	//目录id
	private Long categoryId;
    //目录名称
	private String categoryName;
	//商品名称
	private String itemName;
	//品牌id
	private String brandId;
	//品牌名称
	private String brandName;
	//类目属性
	private String categoryAttr;
	//商品sku编码
	private Long skuId;
	//商品编码
	private String skuCode;
	//副标题
	private String subTitle;
	//备注
	private String note;
	//显示库存
	private String displayQty;
	//锁定库存
	private String reserveQty;
	//总库存
	private String totalStock;
	//同步erp库存标志 0
	private String erpSync;
	//逻辑可卖数
	private String avaliableQty;
	//起订量
	private String minBuyQty;
	//包厢发布数量
	private String boxPublishQty;
	//区域发布数量
	private String areaPublishQty;
	//是否限购
	private String isPurchaseLimit;
	//最大采购量
	private String maxPurchaseQty;
	//运费
	private String shippingCost;
	//上架状态 0 下架 1 上架
	private String isVisible;
	//是否自动上下架
	private String isAutomaticVisible;
	//按照库存自动上下架
	private String isAutomaticVisibleByStock;
	//上架时间
	private Date automaticVisibleUpTime;
	//下架时间
	private Date automaticVisibleDownTime;
	//图片
	private List<ItemPicture> pictures;
	//描述
	private ItemDescribe describe;
	//销售区域
	private ItemSalesArea itemSaleArea;
	private List<ItemSalesAreaDetail> itemSaleAreaDetailList;
	//标准价格
	private StandardPriceDTO standardPriceDTO;
	//型号
	private String modelType;
	//商品spu编码
	private String spuCode;
	//促销锁定库存数量
	private String promotionReserveQty;
	//商品item编码
	private String itemCode;
	
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
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	public String getCategoryAttr() {
		return categoryAttr;
	}
	public void setCategoryAttr(String categoryAttr) {
		this.categoryAttr = categoryAttr;
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
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDisplayQty() {
		return displayQty;
	}
	public void setDisplayQty(String displayQty) {
		this.displayQty = displayQty;
	}
	public String getReserveQty() {
		return reserveQty;
	}
	public void setReserveQty(String reserveQty) {
		this.reserveQty = reserveQty;
	}
	public String getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(String totalStock) {
		this.totalStock = totalStock;
	}
	public String getErpSync() {
		return erpSync;
	}
	public void setErpSync(String erpSync) {
		this.erpSync = erpSync;
	}
	public String getAvaliableQty() {
		return avaliableQty;
	}
	public void setAvaliableQty(String avaliableQty) {
		this.avaliableQty = avaliableQty;
	}
	public String getMinBuyQty() {
		return minBuyQty;
	}
	public void setMinBuyQty(String minBuyQty) {
		this.minBuyQty = minBuyQty;
	}
	public String getBoxPublishQty() {
		return boxPublishQty;
	}
	public void setBoxPublishQty(String boxPublishQty) {
		this.boxPublishQty = boxPublishQty;
	}
	public String getAreaPublishQty() {
		return areaPublishQty;
	}
	public void setAreaPublishQty(String areaPublishQty) {
		this.areaPublishQty = areaPublishQty;
	}
	public String getIsPurchaseLimit() {
		return isPurchaseLimit;
	}
	public void setIsPurchaseLimit(String isPurchaseLimit) {
		this.isPurchaseLimit = isPurchaseLimit;
	}
	public String getMaxPurchaseQty() {
		return maxPurchaseQty;
	}
	public void setMaxPurchaseQty(String maxPurchaseQty) {
		this.maxPurchaseQty = maxPurchaseQty;
	}
	public String getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(String shippingCost) {
		this.shippingCost = shippingCost;
	}
	public String getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	public String getIsAutomaticVisible() {
		return isAutomaticVisible;
	}
	public void setIsAutomaticVisible(String isAutomaticVisible) {
		this.isAutomaticVisible = isAutomaticVisible;
	}
	public String getIsAutomaticVisibleByStock() {
		return isAutomaticVisibleByStock;
	}
	public void setIsAutomaticVisibleByStock(String isAutomaticVisibleByStock) {
		this.isAutomaticVisibleByStock = isAutomaticVisibleByStock;
	}
	public Date getAutomaticVisibleUpTime() {
		return automaticVisibleUpTime;
	}
	public void setAutomaticVisibleUpTime(Date automaticVisibleUpTime) {
		this.automaticVisibleUpTime = automaticVisibleUpTime;
	}
	public Date getAutomaticVisibleDownTime() {
		return automaticVisibleDownTime;
	}
	public void setAutomaticVisibleDownTime(Date automaticVisibleDownTime) {
		this.automaticVisibleDownTime = automaticVisibleDownTime;
	}
	public List<ItemPicture> getPictures() {
		return pictures;
	}
	public void setPictures(List<ItemPicture> pictures) {
		this.pictures = pictures;
	}
	public ItemDescribe getDescribe() {
		return describe;
	}
	public void setDescribe(ItemDescribe describe) {
		this.describe = describe;
	}
	public ItemSalesArea getItemSaleArea() {
		return itemSaleArea;
	}
	public void setItemSaleArea(ItemSalesArea itemSaleArea) {
		this.itemSaleArea = itemSaleArea;
	}
	public List<ItemSalesAreaDetail> getItemSaleAreaDetailList() {
		return itemSaleAreaDetailList;
	}
	public void setItemSaleAreaDetailList(
			List<ItemSalesAreaDetail> itemSaleAreaDetailList) {
		this.itemSaleAreaDetailList = itemSaleAreaDetailList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StandardPriceDTO getStandardPriceDTO() {
		return standardPriceDTO;
	}
	public void setStandardPriceDTO(StandardPriceDTO standardPriceDTO) {
		this.standardPriceDTO = standardPriceDTO;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getSpuCode() {
		return spuCode;
	}
	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}
	public String getPromotionReserveQty() {
		return promotionReserveQty;
	}
	public void setPromotionReserveQty(String promotionReserveQty) {
		this.promotionReserveQty = promotionReserveQty;
	}
	
}
