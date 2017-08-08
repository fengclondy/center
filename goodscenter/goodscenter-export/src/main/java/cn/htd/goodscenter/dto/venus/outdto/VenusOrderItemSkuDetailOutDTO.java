package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;

public class VenusOrderItemSkuDetailOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8016617368133302212L;
	//skuId
	private Long skuId;
	//skuCode
	private String itemCode;
	//运费
	private Double shippingCost;
	//itemId
	private Long itemId;
	//商品名称
	private String itemName;
	//商品编码
	private String skuCode;
	//ERP code
	private String erpCode;
	//ERP一级类目编码
	private String erpFistCatCode;
	//ERP五级类目编码
	private String erpFiveCatCode;
	//商品渠道
	private String itemChannel;
	//主图url
	private String pictureUrl;
	//一级目录id
	private Long firstCatId;
	//一级目录名称
	private String firstCatName;
	//二级目录id
	private Long secondCatId;
	//二级目录名称
	private String secondCatName;
	//三级目录id
	private Long thirdCatId;
	//三级目录名称
	private String thirdCatName;
	//品牌id
	private Long brandId;
	//品牌名称
	private String brandName;
	//EAN编码
	private String eanCode;
	//spuId
	private Long itemSpuId;
	//spucode
	private String itemSpuCode;
	//基础价格
	private ItemSkuBasePriceDTO itemSkuBasePrice;
	//上架信息
	private ItemSkuPublishInfo itemSkuPublishInfo;
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Long getItemId() {
		return itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Double getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
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
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	public String getErpFistCatCode() {
		return erpFistCatCode;
	}
	public void setErpFistCatCode(String erpFistCatCode) {
		this.erpFistCatCode = erpFistCatCode;
	}
	public String getErpFiveCatCode() {
		return erpFiveCatCode;
	}
	public void setErpFiveCatCode(String erpFiveCatCode) {
		this.erpFiveCatCode = erpFiveCatCode;
	}
	public String getItemChannel() {
		return itemChannel;
	}
	public void setItemChannel(String itemChannel) {
		this.itemChannel = itemChannel;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Long getFirstCatId() {
		return firstCatId;
	}
	public void setFirstCatId(Long firstCatId) {
		this.firstCatId = firstCatId;
	}
	public String getFirstCatName() {
		return firstCatName;
	}
	public void setFirstCatName(String firstCatName) {
		this.firstCatName = firstCatName;
	}
	public Long getSecondCatId() {
		return secondCatId;
	}
	public void setSecondCatId(Long secondCatId) {
		this.secondCatId = secondCatId;
	}
	public String getSecondCatName() {
		return secondCatName;
	}
	public void setSecondCatName(String secondCatName) {
		this.secondCatName = secondCatName;
	}
	public Long getThirdCatId() {
		return thirdCatId;
	}
	public void setThirdCatId(Long thirdCatId) {
		this.thirdCatId = thirdCatId;
	}
	public String getThirdCatName() {
		return thirdCatName;
	}
	public void setThirdCatName(String thirdCatName) {
		this.thirdCatName = thirdCatName;
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
	public ItemSkuBasePriceDTO getItemSkuBasePrice() {
		return itemSkuBasePrice;
	}
	public void setItemSkuBasePrice(ItemSkuBasePriceDTO itemSkuBasePrice) {
		this.itemSkuBasePrice = itemSkuBasePrice;
	}
	public ItemSkuPublishInfo getItemSkuPublishInfo() {
		return itemSkuPublishInfo;
	}
	public void setItemSkuPublishInfo(ItemSkuPublishInfo itemSkuPublishInfo) {
		this.itemSkuPublishInfo = itemSkuPublishInfo;
	}

	public String getEanCode() {
		return eanCode;
	}

	public void setEanCode(String eanCode) {
		this.eanCode = eanCode;
	}
	public Long getItemSpuId() {
		return itemSpuId;
	}
	public void setItemSpuId(Long itemSpuId) {
		this.itemSpuId = itemSpuId;
	}
	public String getItemSpuCode() {
		return itemSpuCode;
	}
	public void setItemSpuCode(String itemSpuCode) {
		this.itemSpuCode = itemSpuCode;
	}
	
}
