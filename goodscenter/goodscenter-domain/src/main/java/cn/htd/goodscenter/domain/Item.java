package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:商品信息domain类]
 * </p>
 */
public class Item implements Serializable {

	private static final long serialVersionUID = 2671677765195120413L;

	private Long itemId;// 商品id
	private String itemCode;// 商品编码
	private String itemName;// 商品名称
	private Integer itemStatus;// 商品状态,1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定，7：申请解锁
	private Long sellerId;// 商家ID
	private Long shopId;// 店铺ID
	private Long cid;// 类目ID
	private Long shopCid;// 商品所属店铺类目id
	private Long brand;// 品牌
	private String modelType;// 型号
	private String weightUnit;// 商品毛重
	private BigDecimal taxRate;// 税率
	private BigDecimal weight;// 商品毛重
	private BigDecimal netWeight;// 净重
	// add by zhangxiaolong
	// 长
	private BigDecimal length;
	// 宽
	private BigDecimal width;
	// 高
	private BigDecimal height;
	private String ad;// 广告词
	private String origin;// 商品产地
	private String attributes;// 商品类目属性keyId:valueId
	private String attrSale;// 商品销售属性keyId:valueId
	private String itemQualification;// 商品参数
	private String productChannelCode;// 渠道编码
	private String outerItemStatus;// 外部商品状态
	private String outerChannelCategoryCode;// 外接渠道品类编码
	private String outerChannelBrandCode;// 外接渠道品牌编码
	private boolean applyInSpu;// 是否申请进入商品模板库
	private Integer isSpu;// 是否来自商品模板
	private Long itemSpuId;// 商品模板ID
	private String itemPictureUrl;// 商品主图URL
	private Integer hasPrice;// 是否有报价：1：有价格；2：暂无报价
	private BigDecimal marketPrice;// 市场价
	private BigDecimal marketPrice2;// 成本价
	private BigDecimal guidePrice;// 商城指导价
	private String packingList;// 包装清单
	private String afterService;// 售后服务
	private Date timingListing;// 定时上架，为空则表示未设置定时上架
	private Date listtingTime;// 上架时间
	private Date delistingTime;// 下架时间private String statusChangeReason;//
								// 平台方下架或锁定或审核驳回时给出的理由
	private String statusChangeReason;// 平台方下架或锁定或审核驳回时给出的理由
	private String keywords;// 关键字
	private Long shopFreightTemplateId;// 运费模版id 平台:必填；商家:必填
	private BigDecimal freightAmount;// 运费金额
	private Long payType;// 支付方式，1：货到付款
	private boolean isPreSale;// 是否预售
	private Date preSaleStarttime;// 预售开始时间
	private Date preSaleEndtime;// 预售结束时间
	private String erpFirstCategoryCode;// ERP一级类目编码
	private String erpFiveCategoryCode;// ERP五级类目编码
	private String erpStatus;
	private Date erpDownTime;
	private String erpErrorMsg;
	private Date created;// 创建日期
	private Long createId;// 创建人ID
	private String createName;// 创建人名称
	private Date modified;// 修改日期
	private Long modifyId;// 更新人ID
	private String modifyName;// 更新人名称
	private String erpCode;//erp编码
	private Integer isVipItem;//是否VIP套餐商品:0 非vip套餐商品 1 vip套餐商品
	private Integer vipItemType;//VIP商品类型:当is_vip_item=1时，该字段有效 1 VIP套餐 2 智慧门店套餐
	private Integer vipSyncFlag;//同步VIP会员标记:当is_vip_item=1时，该字段为1时有效 0 无效 1 有效

	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	public Long getShopFreightTemplateId() {
		return shopFreightTemplateId;
	}

	public void setShopFreightTemplateId(Long shopFreightTemplateId) {
		this.shopFreightTemplateId = shopFreightTemplateId;
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

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getShopCid() {
		return shopCid;
	}

	public void setShopCid(Long shopCid) {
		this.shopCid = shopCid;
	}

	public Long getBrand() {
		return brand;
	}

	public void setBrand(Long brand) {
		this.brand = brand;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getMarketPrice2() {
		return marketPrice2;
	}

	public void setMarketPrice2(BigDecimal marketPrice2) {
		this.marketPrice2 = marketPrice2;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getPackingList() {
		return packingList;
	}

	public void setPackingList(String packingList) {
		this.packingList = packingList;
	}

	public String getAfterService() {
		return afterService;
	}

	public void setAfterService(String afterService) {
		this.afterService = afterService;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public Date getTimingListing() {
		return timingListing;
	}

	public void setTimingListing(Date timingListing) {
		this.timingListing = timingListing;
	}

	public Date getListtingTime() {
		return listtingTime;
	}

	public void setListtingTime(Date listtingTime) {
		this.listtingTime = listtingTime;
	}

	public Date getDelistingTime() {
		return delistingTime;
	}

	public void setDelistingTime(Date delistingTime) {
		this.delistingTime = delistingTime;
	}

	public String getStatusChangeReason() {
		return statusChangeReason;
	}

	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(BigDecimal guidePrice) {
		this.guidePrice = guidePrice;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getHasPrice() {
		return hasPrice;
	}

	public void setHasPrice(Integer hasPrice) {
		this.hasPrice = hasPrice;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getAttrSale() {
		return attrSale;
	}

	public void setAttrSale(String attrSale) {
		this.attrSale = attrSale;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getOuterItemStatus() {
		return outerItemStatus;
	}

	public void setOuterItemStatus(String outerItemStatus) {
		this.outerItemStatus = outerItemStatus;
	}

	public String getProductChannelCode() {
		return productChannelCode;
	}

	public void setProductChannelCode(String productChannelCode) {
		this.productChannelCode = productChannelCode;
	}

	
	public String getOuterChannelCategoryCode() {
		return outerChannelCategoryCode;
	}

	public void setOuterChannelCategoryCode(String outerChannelCategoryCode) {
		this.outerChannelCategoryCode = outerChannelCategoryCode;
	}

	public String getOuterChannelBrandCode() {
		return outerChannelBrandCode;
	}

	public void setOuterChannelBrandCode(String outerChannelBrandCode) {
		this.outerChannelBrandCode = outerChannelBrandCode;
	}

	public boolean isApplyInSpu() {
		return applyInSpu;
	}

	public void setApplyInSpu(boolean applyInSpu) {
		this.applyInSpu = applyInSpu;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getIsSpu() {
		return isSpu;
	}

	public void setIsSpu(Integer isSpu) {
		this.isSpu = isSpu;
	}

	public Long getItemSpuId() {
		return itemSpuId;
	}

	public void setItemSpuId(Long itemSpuId) {
		this.itemSpuId = itemSpuId;
	}

	public String getItemQualification() {
		return itemQualification;
	}

	public void setItemQualification(String itemQualification) {
		this.itemQualification = itemQualification;
	}

	public String getItemPictureUrl() {
		return itemPictureUrl;
	}

	public void setItemPictureUrl(String itemPictureUrl) {
		this.itemPictureUrl = itemPictureUrl;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public BigDecimal getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(BigDecimal freightAmount) {
		this.freightAmount = freightAmount;
	}

	public boolean isPreSale() {
		return isPreSale;
	}

	public void setPreSale(boolean preSale) {
		isPreSale = preSale;
	}

	public Date getPreSaleStarttime() {
		return preSaleStarttime;
	}

	public void setPreSaleStarttime(Date preSaleStarttime) {
		this.preSaleStarttime = preSaleStarttime;
	}

	public Date getPreSaleEndtime() {
		return preSaleEndtime;
	}

	public void setPreSaleEndtime(Date preSaleEndtime) {
		this.preSaleEndtime = preSaleEndtime;
	}

	public String getErpFirstCategoryCode() {
		return erpFirstCategoryCode;
	}

	public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
		this.erpFirstCategoryCode = erpFirstCategoryCode;
	}

	public String getErpFiveCategoryCode() {
		return erpFiveCategoryCode;
	}

	public void setErpFiveCategoryCode(String erpFiveCategoryCode) {
		this.erpFiveCategoryCode = erpFiveCategoryCode;
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

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
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
}
