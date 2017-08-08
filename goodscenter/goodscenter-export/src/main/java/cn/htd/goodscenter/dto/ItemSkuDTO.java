package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemSkuDTO implements Serializable {

	private static final long serialVersionUID = -2730234833973142428L;

	/**
	 * item
	 */
	private Long itemId;// 商品id

	private String itemCode;//Item编码

	private Long sellerId;// 商家ID

	private String productChannelCode;

	private Long shopCid;// 商品所属店铺类目id

	private String itemName;// 商品名称

	private String modelType; // 型号

	private BigDecimal weight; // 毛重

	private BigDecimal netWeight; // 净重

	private BigDecimal length; // 长

	private BigDecimal width; // 宽

	private BigDecimal height; // 高

	private String weightUnit; // 单位

	private String colour; // 颜色

	private String origin; // 产地

	private String ad;// 广告词

	private Long cid;// 类目ID

	private String cName;// 类目名称

	private Long brand;// 品牌

	private String brandName;// 品牌名称

	private Integer hasPrice;// 是否有报价：1：有价格；2：暂无报价

	private Integer isPreSale; // 是否预售  0 否 1 是

	private Date preSaleStartTime; // 预售开始时间

	private Date preSaleEndTime; // 预售结束时间

	private Long shopFreightTemplateId;// 运费模版id 平台:必填；商家:必填

	private Integer itemStatus;

	private String eanCode;//EAN编码

	private String itemQualification; // 商品参数

	private String packingList; // 包装清单

	private boolean isSpu;

	private Long spuId;

	private String itemSpuCode;// 商品模板编码 【可为空】

	private Integer isVipItem;//是否VIP套餐商品:0 非vip套餐商品 1 vip套餐商品

	private Integer vipItemType;//VIP商品类型:当is_vip_item=1时，该字段有效 1 VIP套餐 2 智慧门店套餐

	private Integer vipSyncFlag;//同步VIP会员标记:当is_vip_item=1时，该字段为1时有效 0 无效 1 有效

	private BigDecimal freightAmount;//运费 【内部供应商】

	private String attrSaleStr; // item销售属性

    private String attributesStr; // item类目属性

	private String erpFirstCategoryCode; // erp一级类目

	private String erpFiveCategoryCode; // erp五级类目

	private String erpCode;//商品SKU编码

	/**
	 * sku
	 */
	private Long productId;// 商品货号

	private String subTitle;//商品副标题

	private String skuErpCode;//商品SKU编码

	private String erpStatus;//ERP下行状态

	private Date erpDownTime;//ERP下行时间

	private String erpErrorMsg;//ERP下行错误信息

	private String describeUrl;// 商品描述url，存在jfs中

	private String picUrl;// Sku图片

	private Long shopId;// 店铺ID

	private BigDecimal itemPrice;// 商品显示价格

	private Long skuId;// SKU ID

	private Integer skuInventory;// SKU库存

	private BigDecimal skuPrice;// SKU价格

	private BigDecimal skuInquiryPirce;// SKU询价

	private String skuAttributeStr;// SKU销售属性串

	private String skuScope;// 评价

	private BigDecimal salesVolume;// 销量

	private String skuCode;//商品sku编码，保存当前hybris系统中的HTDH_******类似的编码

	private String outerSkuId;//外接商品sku_id或者erp上行过来的sku

	private List<ItemAttrDTO> attributes = new ArrayList<ItemAttrDTO>();// 属性值

	private Long payType;// 支付方式，1：货到付款


	private Long createId;//创建人ID

	private String createName;//创建人名称

	private Long modifyId;//更新人ID

	private String modifyName;//更新人名称

	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getShopCid() {
		return shopCid;
	}

	public void setShopCid(Long shopCid) {
		this.shopCid = shopCid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getBrand() {
		return brand;
	}

	public void setBrand(Long brand) {
		this.brand = brand;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getHasPrice() {
		return hasPrice;
	}

	public void setHasPrice(Integer hasPrice) {
		this.hasPrice = hasPrice;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getDescribeUrl() {
		return describeUrl;
	}

	public void setDescribeUrl(String describeUrl) {
		this.describeUrl = describeUrl;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Integer getSkuInventory() {
		return skuInventory;
	}

	public void setSkuInventory(Integer skuInventory) {
		this.skuInventory = skuInventory;
	}

	public BigDecimal getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}

	public List<ItemAttrDTO> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ItemAttrDTO> attributes) {
		this.attributes = attributes;
	}

	public String getSkuAttributeStr() {
		return skuAttributeStr;
	}

	public void setSkuAttributeStr(String skuAttributeStr) {
		this.skuAttributeStr = skuAttributeStr;
	}

	public String getSkuScope() {
		return skuScope;
	}

	public void setSkuScope(String skuScope) {
		this.skuScope = skuScope;
	}

	public BigDecimal getSkuInquiryPirce() {
		return skuInquiryPirce;
	}

	public void setSkuInquiryPirce(BigDecimal skuInquiryPirce) {
		this.skuInquiryPirce = skuInquiryPirce;
	}

	public BigDecimal getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(BigDecimal salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Long getShopFreightTemplateId() {
		return shopFreightTemplateId;
	}

	public void setShopFreightTemplateId(Long shopFreightTemplateId) {
		this.shopFreightTemplateId = shopFreightTemplateId;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSkuErpCode() {
		return skuErpCode;
	}

	public void setSkuErpCode(String skuErpCode) {
		this.skuErpCode = skuErpCode;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public Date getErpDownTime() {
		return erpDownTime;
	}

	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}

	public String getErpErrorMsg() {
		return erpErrorMsg;
	}

	public void setErpErrorMsg(String erpErrorMsg) {
		this.erpErrorMsg = erpErrorMsg;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getOuterSkuId() {
		return outerSkuId;
	}

	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}

	public String getEanCode() {
		return eanCode;
	}

	public void setEanCode(String eanCode) {
		this.eanCode = eanCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getProductChannelCode() {
		return productChannelCode;
	}

	public void setProductChannelCode(String productChannelCode) {
		this.productChannelCode = productChannelCode;
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

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
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

	public BigDecimal getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(BigDecimal freightAmount) {
		this.freightAmount = freightAmount;
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

	public String getAttrSaleStr() {
		return attrSaleStr;
	}

	public void setAttrSaleStr(String attrSaleStr) {
		this.attrSaleStr = attrSaleStr;
	}

	public String getAttributesStr() {
		return attributesStr;
	}

	public void setAttributesStr(String attributesStr) {
		this.attributesStr = attributesStr;
	}

	public String getErpFirstCategoryCode() {
		return erpFirstCategoryCode;
	}

	public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
		this.erpFirstCategoryCode = erpFirstCategoryCode;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public String getErpFiveCategoryCode() {
		return erpFiveCategoryCode;
	}

	public void setErpFiveCategoryCode(String erpFiveCategoryCode) {
		this.erpFiveCategoryCode = erpFiveCategoryCode;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}
}
