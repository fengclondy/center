package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSkuPicture;

/**
 * 商品详情出参+
 * @author chenkang
 */
public class MallSkuOutDTO implements Serializable {

	private static final long serialVersionUID = -5919786885017397474L;

	/** SKU **/
	private String skuCode;//商品sku编码

	private Long skuId;// skuId

	private String subTitle;// 副标题

	/** 属性相关 **/
	private List<MallSkuAttributeDTO> saleAttributeList;// 当前sku销售属性【只有外部供应商有】

	private Map<String, List<MallSkuAttributeDTO>> allAbleSaleAttributeList;// 该item下所有可卖sku的销售属性。 格式： {skuId_skuCode : 它的销售属性}【只有外部供应商有】

	private List<MallSkuAttributeDTO> itemSaleAttributeList;// item的销售属性【只有外部供应商有】

	private List<MallSkuAttributeDTO> itemAttributeList;// item的类目属性

	/** ITEM **/
	private Long itemId;// 商品id

	private String itemName;// 商品名称

	private String itemCode; // item编码

	private String modelType; // 型号

	private BigDecimal weight; // 毛重

	private BigDecimal netWeight; // 净重

	private BigDecimal length; // 长

	private BigDecimal width; // 宽

	private BigDecimal height; // 高

	private String weightUnit; // 单位

	private String colour; // 颜色

	private String origin; // 产地

	private String ad; // 广告语

	private String itemQualification; // 商品参数

	private String packingList; // 包装清单

	private Long sellerId;// 卖家ID

	private Long shopId;// 店铺id

	private Long shopFreightTemplateId;//运费模版ID 【外部供应商】

	private BigDecimal freightAmount;//运费 【内部供应商】

	private String itemPictureUrl;// item主图 列表页展示

	private List<ItemPicture> itemPictureList; // 商品详情页item图片 【平台公司、京东商品使用, 外部供应商商品没有sku图片时使用】

	private List<ItemSkuPicture> itemSkuPictureList; // 商品详情页sku图片 【外部供应商商品sku图片】

	private Integer itemStatus;// item状态

	private String itemDescribe;// 商品描述 【格式：html文本。详情页下方具体的描述包括图文】

	private boolean isSpu;

	private Long spuId;


	private String itemSpuCode;// 商品模板编码 【可为空】

	private Integer isVipItem;//是否VIP套餐商品:0 非vip套餐商品 1 vip套餐商品

	private Integer vipItemType;//VIP商品类型:当is_vip_item=1时，该字段有效 1 VIP套餐 2 智慧门店套餐

	private Integer vipSyncFlag;//同步VIP会员标记:当is_vip_item=1时，该字段为1时有效 0 无效 1 有效

	private String eanCode; // EAN编码

	private String skuErpCode; // item表 erpCode

	// 预售
	private Integer isPreSale; // 是否预售  0 否 1 是

	private Date preSaleStartTime; // 预售开始时间

	private Date preSaleEndTime; // 预售结束时间

	/** 品牌品类 **/
	private Long brandId; // 品牌id

	private String brandName; //  品牌名称

	private Long firstCategoryId; // 一级类目id

	private String firstCategoryName; // 一级类目名称

	private Long secondCategoryId; // 二级类目Id

	private String secondCategoryName; // 二级类目名称

	private Long thirdCategoryId;// 三级类目ID

	private String thirdCategoryName; // 三级类目名称

	private String categoryName; // 品类名称 一级类目>>二级类目>>三级类目

	private String erpFirstCategoryCode; // ERP一级类目编码

	private String erpFiveCategoryCode; // ERP一级类目编码

	/** 商品类型 内部供应商商品 外部供应商商品 外接渠道商品 **/
	private String outerSkuId; /// 外接商品ID

	private String productChannelCode; // 商品渠道编码　10：内部供应商商品 20：外部供应商商品 30^：外接渠道商品

	/** 其他 **/
	private String skuSaleAttrStr; // sku销售属性

	private String itemSaleAttrStr; // item销售属性

	private String itemAttrStr; // item类目属性

	private Map<String, Object> tunnelMap = new HashMap<>(); // 注：隧道字段

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
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

	public Long getShopFreightTemplateId() {
		return shopFreightTemplateId;
	}

	public void setShopFreightTemplateId(Long shopFreightTemplateId) {
		this.shopFreightTemplateId = shopFreightTemplateId;
	}

	public String getItemPictureUrl() {
		return itemPictureUrl;
	}

	public void setItemPictureUrl(String itemPictureUrl) {
		this.itemPictureUrl = itemPictureUrl;
	}

	public String getOuterSkuId() {
		return outerSkuId;
	}

	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}

	public String getProductChannelCode() {
		return productChannelCode;
	}

	public void setProductChannelCode(String productChannelCode) {
		this.productChannelCode = productChannelCode;
	}

	public List<MallSkuAttributeDTO> getSaleAttributeList() {
		return saleAttributeList;
	}

	public void setSaleAttributeList(List<MallSkuAttributeDTO> saleAttributeList) {
		this.saleAttributeList = saleAttributeList;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public BigDecimal getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(BigDecimal freightAmount) {
		this.freightAmount = freightAmount;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getItemQualification() {
		return itemQualification;
	}

	public void setItemQualification(String itemQualification) {
		this.itemQualification = itemQualification;
	}

	public String getPackingList() {
		return packingList;
	}

	public void setPackingList(String packingList) {
		this.packingList = packingList;
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

	public Long getFirstCategoryId() {
		return firstCategoryId;
	}

	public void setFirstCategoryId(Long firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}

	public Long getSecondCategoryId() {
		return secondCategoryId;
	}

	public void setSecondCategoryId(Long secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}

	public Long getThirdCategoryId() {
		return thirdCategoryId;
	}

	public void setThirdCategoryId(Long thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<ItemSkuPicture> getItemSkuPictureList() {
		return itemSkuPictureList;
	}

	public void setItemSkuPictureList(List<ItemSkuPicture> itemSkuPictureList) {
		this.itemSkuPictureList = itemSkuPictureList;
	}

	public List<MallSkuAttributeDTO> getItemSaleAttributeList() {
		return itemSaleAttributeList;
	}

	public void setItemSaleAttributeList(List<MallSkuAttributeDTO> itemSaleAttributeList) {
		this.itemSaleAttributeList = itemSaleAttributeList;
	}

	public String getFirstCategoryName() {
		return firstCategoryName;
	}

	public void setFirstCategoryName(String firstCategoryName) {
		this.firstCategoryName = firstCategoryName;
	}

	public String getSecondCategoryName() {
		return secondCategoryName;
	}

	public void setSecondCategoryName(String secondCategoryName) {
		this.secondCategoryName = secondCategoryName;
	}

	public String getThirdCategoryName() {
		return thirdCategoryName;
	}

	public void setThirdCategoryName(String thirdCategoryName) {
		this.thirdCategoryName = thirdCategoryName;
	}

	public String getItemDescribe() {
		return itemDescribe;
	}

	public void setItemDescribe(String itemDescribe) {
		this.itemDescribe = itemDescribe;
	}

	public Map<String, Object> getTunnelMap() {
		return tunnelMap;
	}

	public void setTunnelMap(Map<String, Object> tunnelMap) {
		this.tunnelMap = tunnelMap;
	}

	public List<MallSkuAttributeDTO> getItemAttributeList() {
		return itemAttributeList;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public void setItemAttributeList(List<MallSkuAttributeDTO> itemAttributeList) {
		this.itemAttributeList = itemAttributeList;
	}

	public Map<String, List<MallSkuAttributeDTO>> getAllAbleSaleAttributeList() {
		return allAbleSaleAttributeList;
	}

	public void setAllAbleSaleAttributeList(Map<String, List<MallSkuAttributeDTO>> allAbleSaleAttributeList) {
		this.allAbleSaleAttributeList = allAbleSaleAttributeList;
	}

	public String getItemSpuCode() {
		return itemSpuCode;
	}

	public void setItemSpuCode(String itemSpuCode) {
		this.itemSpuCode = itemSpuCode;
	}

	public Integer getIsVipItem() {
		return isVipItem;
	}

	public void setIsVipItem(Integer isVipItem) {
		this.isVipItem = isVipItem;
	}

	public Integer getVipItemType() {
		return vipItemType;
	}

	public void setVipItemType(Integer vipItemType) {
		this.vipItemType = vipItemType;
	}

	public Integer getVipSyncFlag() {
		return vipSyncFlag;
	}

	public void setVipSyncFlag(Integer vipSyncFlag) {
		this.vipSyncFlag = vipSyncFlag;
	}

	public boolean isSpu() {
		return isSpu;
	}

	public void setSpu(boolean isSpu) {
		this.isSpu = isSpu;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public String getSkuSaleAttrStr() {
		return skuSaleAttrStr;
	}

	public void setSkuSaleAttrStr(String skuSaleAttrStr) {
		this.skuSaleAttrStr = skuSaleAttrStr;
	}

	public String getItemSaleAttrStr() {
		return itemSaleAttrStr;
	}

	public void setItemSaleAttrStr(String itemSaleAttrStr) {
		this.itemSaleAttrStr = itemSaleAttrStr;
	}

	public String getItemAttrStr() {
		return itemAttrStr;
	}

	public void setItemAttrStr(String itemAttrStr) {
		this.itemAttrStr = itemAttrStr;
	}

	public Integer getIsPreSale() {
		return isPreSale;
	}

	public void setIsPreSale(Integer isPreSale) {
		this.isPreSale = isPreSale;
	}

	public Date getPreSaleStartTime() {
		return preSaleStartTime;
	}

	public void setPreSaleStartTime(Date preSaleStartTime) {
		this.preSaleStartTime = preSaleStartTime;
	}

	public Date getPreSaleEndTime() {
		return preSaleEndTime;
	}

	public void setPreSaleEndTime(Date preSaleEndTime) {
		this.preSaleEndTime = preSaleEndTime;
	}

	public String getErpFirstCategoryCode() {
		return erpFirstCategoryCode;
	}

	public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
		this.erpFirstCategoryCode = erpFirstCategoryCode;
	}

	public List<ItemPicture> getItemPictureList() {
		return itemPictureList;
	}

	public void setItemPictureList(List<ItemPicture> itemPictureList) {
		this.itemPictureList = itemPictureList;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getEanCode() {
		return eanCode;
	}

	public void setEanCode(String eanCode) {
		this.eanCode = eanCode;
	}

	public String getSkuErpCode() {
		return skuErpCode;
	}

	public void setSkuErpCode(String skuErpCode) {
		this.skuErpCode = skuErpCode;
	}

	public String getErpFiveCategoryCode() {
		return erpFiveCategoryCode;
	}

	public void setErpFiveCategoryCode(String erpFiveCategoryCode) {
		this.erpFiveCategoryCode = erpFiveCategoryCode;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
}
