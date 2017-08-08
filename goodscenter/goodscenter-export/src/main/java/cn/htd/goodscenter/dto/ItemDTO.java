package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.pricecenter.dto.StandardPriceDTO;


/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:商品信息的DTO]
 * </p>
 */
public class ItemDTO implements Serializable {
	private static final long serialVersionUID = 2464215588527124822L;
	private Long itemId;// 商品id
	private Long sellerId;// 商家ID 平台:非必填；商家:必填
	private Long shopCid;// 商品所属店铺类目id 平台:非必填；商家:必填
	private String itemName;// 商品名称 平台:必填；商家:必填
	private String itemCode;//商品编码
	private String ad;// 广告词 平台:非必填；商家:非必填
	private Long cid;// 类目ID 平台:必填；商家:必填
	private String cName;// 类目名称
	private String outerItemStatus;//外部商品状态
	private Long brand;// 品牌 平台:必填；商家:必填
	private String brandName;// 品牌名称 查询用
	private String productChannelCode;//渠道编码
	private String outerChannelCategoryCode;//外接渠道品类编码
	private String outerChannelBrandCode;//外接渠道品牌编码
	private boolean applyInSpu;//是否申请进入商品模板库
	private boolean isSpu;//是否来自商品模板
	private Integer itemSpuId;//商品模板ID
	private Integer verifyStatus;//审核状态
	private String verifyName;//审核人
	private Map<String, Map<String, List<String>>> boxSalesAreaMap;
	private Map<String, Map<String, List<String>>> publicSalesAreaMap;
	
	public Map<String, Map<String, List<String>>> getBoxSalesAreaMap() {
		return boxSalesAreaMap;
	}

	public void setBoxSalesAreaMap(
			Map<String, Map<String, List<String>>> boxSalesAreaMap) {
		this.boxSalesAreaMap = boxSalesAreaMap;
	}

	public Map<String, Map<String, List<String>>> getPublicSalesAreaMap() {
		return publicSalesAreaMap;
	}

	public void setPublicSalesAreaMap(
			Map<String, Map<String, List<String>>> publicSalesAreaMap) {
		this.publicSalesAreaMap = publicSalesAreaMap;
	}

	/**
	 * 模板code
	 */
	private String spuCode;
	/**
	 * 模板名称
	 */
	private String spuName;

	private String itemQualification;//商品参数
	private String itemPictureUrl;//商品主图URL
	private Integer hasPrice;// 是否有报价：1：有价格；2：暂无报价 平台:必填；商家:必填
	private Long productId;// 商品货号 平台:必填；商家:必填
	private BigDecimal marketPrice;// 市场价 平台:非必填；商家:必填
	private BigDecimal marketPrice2;// 成本价 平台:非必填；商家:必填
	private Integer inventory;// 库存量 平台:非必填；商家:必填
	private BigDecimal weight;// 商品毛重 平台:非必填；商家:必填
	private BigDecimal netWeight;//净重 商家：必填
	private String weightUnit;// 重量单位
	private BigDecimal taxRate;//税率    商家：必填
	private String modelType;//型号
	private String describeUrl;// 商品描述url，存在jfs中 平台:必填；商家:必填
	private String packingList;// 包装清单 平台:非必填；商家:必填
	private String afterService;// 售后服务 平台:非必填；商家:必填
	private Date timingListing;// 定时上架，为空则表示未设置定时上架 平台:非必填；商家:非必填
	private Integer addSource;// 来源：1自定义添加2：从平台商品库选择 平台:非必填；商家:必填
	private Long plstItemId;// 平台商品ID，只有add_source为2时值才有意义 平台:非必填；商家:非必填 ,
							// add_source为2时必填
	private Integer itemStatus;// 平台:必填；商家:必填
								// 商品状态,1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定，
								// 7： 申请解锁

	private BigDecimal freightAmount;//运费金额
	private boolean isPreSale;//是否预售
	private Date preSaleStarttime;//预售开始时间
	private Date preSaleEndtime;//预售结束时间
	private String erpStatus; //商品下行erp状态 1 待下行 2 下行中 3 下行失败 4已下行
	private String erpFirstCategoryCode;//ERP一级类目编码
	private String erpFiveCategoryCode;//ERP五级类目编码
	private Long createId;//创建人ID
	private String createName;//创建人名称
	private Long modifyId;//更新人ID
	private String modifyName;//更新人名称
	private Date created;// 创建日期
	private Date modified;// 修改日期
	private String erpCode;//erp编码

	private String[] picUrls;// 商品图片url列表 平台:必填；商家:必填
	private Long shopId;// 店铺ID 平台:非必填；商家:必填

	private String attributesStr;// 商品类目属性 平台:非必填；商家:必填
	private String attrSaleStr;// 销售属性 平台:非必填；商家:必填

	// 平台:非必填；商家:必填
	private List<SkuInfoDTO> skuInfos = new ArrayList<SkuInfoDTO>();// SKU列表
	// 平台:非必填；商家:必填
	private List<ItemAttrDTO> attrSale = new ArrayList<ItemAttrDTO>();// 销售属性
	// 平台:非必填；商家:必填
	// 也可以查询作为查询条件用
	private List<ItemAttrDTO> attributes = new ArrayList<ItemAttrDTO>();// 商品类目属性
	// 平台:必填；商家:必填
	// 也可以查询作为查询条件用

	private Date listtingTime;// 上架时间
	private Date delistingTime;// 下架时间
	private Integer operator;// 操作方，1：商家，2：平台 平台:必填；商家:必填
	private String statusChangeReason;// 平台方下架或锁定或审核驳回时给出的理由
	private BigDecimal guidePrice;// 商城指导价 平台:必填；商家:必填
	private String origin;// 商品产地 平台:非必填；商家:必填
	private Integer platLinkStatus;// 平台:必填；商家:非必填
												// 与平台商品库关联状态：1：未符合待入库2：待入库3：已入库4：删除
	private String keywords;// 关键字
	private Integer copied;// 1 未加入商品库 2 已加入平台商品库

	private Long shopFreightTemplateId;// 运费模版id 平台:必填；商家:必填
	private BigDecimal volume;// 体积 平台:非必填；商家:非必填

	private Long payType;// 支付方式，1：货到付款

	private ItemDescribe itemDescribe;
	
	private String length;
	//宽
	private String width;
	//高
	private String height;
	
	private String eanCode;

	private StandardPriceDTO standardPriceDTO;

	// 颜色属性分组的图片集合
    private List<SkuColorGroupPictureDTO> skuColorGroupPictureDTOList;

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

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
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

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public String getStatusChangeReason() {
		return statusChangeReason;
	}

	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
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

	public Integer getPlatLinkStatus() {
		return platLinkStatus;
	}

	public void setPlatLinkStatus(Integer platLinkStatus) {
		this.platLinkStatus = platLinkStatus;
	}

	public List<SkuInfoDTO> getSkuInfos() {
		return skuInfos;
	}

	public void setSkuInfos(List<SkuInfoDTO> skuInfos) {
		this.skuInfos = skuInfos;
	}

	public String[] getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(String[] picUrls) {
		this.picUrls = picUrls;
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

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
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

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
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

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getAddSource() {
		return addSource;
	}

	public void setAddSource(Integer addSource) {
		this.addSource = addSource;
	}

	public Integer getHasPrice() {
		return hasPrice;
	}

	public void setHasPrice(Integer hasPrice) {
		this.hasPrice = hasPrice;
	}

	public Long getPlstItemId() {
		return plstItemId;
	}

	public void setPlstItemId(Long plstItemId) {
		this.plstItemId = plstItemId;
	}

	public List<ItemAttrDTO> getAttrSale() {
		return attrSale;
	}

	public void setAttrSale(List<ItemAttrDTO> attrSale) {
		this.attrSale = attrSale;
	}

	public List<ItemAttrDTO> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ItemAttrDTO> attributes) {
		this.attributes = attributes;
	}

	public String getAttributesStr() {
		return attributesStr;
	}

	public void setAttributesStr(String attributesStr) {
		this.attributesStr = attributesStr;
	}

	public String getAttrSaleStr() {
		return attrSaleStr;
	}

	public void setAttrSaleStr(String attrSaleStr) {
		this.attrSaleStr = attrSaleStr;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
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

	public Integer getCopied() {
		return copied;
	}

	public void setCopied(Integer copied) {
		this.copied = copied;
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

	public boolean isSpu() {
		return isSpu;
	}

	public void setSpu(boolean spu) {
		isSpu = spu;
	}

	public Integer getItemSpuId() {
		return itemSpuId;
	}

	public void setItemSpuId(Integer itemSpuId) {
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

	public ItemDescribe getItemDescribe() {
		return itemDescribe;
	}

	public void setItemDescribe(ItemDescribe itemDescribe) {
		this.itemDescribe = itemDescribe;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public StandardPriceDTO getStandardPriceDTO() {
		return standardPriceDTO;
	}

	public void setStandardPriceDTO(StandardPriceDTO standardPriceDTO) {
		this.standardPriceDTO = standardPriceDTO;
	}

	public String getEanCode() {
		return eanCode;
	}

	public void setEanCode(String eanCode) {
		this.eanCode = eanCode;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public List<SkuColorGroupPictureDTO> getSkuColorGroupPictureDTOList() {
		return skuColorGroupPictureDTOList;
	}

	public void setSkuColorGroupPictureDTOList(List<SkuColorGroupPictureDTO> skuColorGroupPictureDTOList) {
		this.skuColorGroupPictureDTOList = skuColorGroupPictureDTOList;
	}

	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}

	public String getSpuName() {
		return spuName;
	}

	public void setSpuName(String spuName) {
		this.spuName = spuName;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}
}
