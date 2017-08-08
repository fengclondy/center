package cn.htd.goodscenter.dto;


import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuOutDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:商品信息列表查询的出参DTO]
 * </p>
 */
public class ItemQueryOutDTO implements Serializable {
	private static final long serialVersionUID = 5105703741539775929L;
	private Long itemId;// 商品id
	private String itemCode;//商品编码
	private String itemName;// 商品名称
	private String cName;// 所属类目（根据cid获取cname）
	private String keywords; // 关键字
	private Integer itemStatus;// 商品状态,1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定，
											// 7： 申请解锁
	private BigDecimal marketPrice;// 销售价
	private BigDecimal marketPrice2;// 成本价
	private Long productId;// 商品货号
	private Integer hasPrice;// 是否有报价：1：有价格；2：暂无报价
	private String pictureUrl;// 商品的url（根据商品id关联 商品图片信息）
	private Long shopId;// 店铺id 用来关联店铺信息
	private Integer platLinkStatus; // 商品库状态 1：未符合待入库2：待入库3：已入库4：删除
	private Integer inventory;// 库存
	private BigDecimal guidePrice;// 商城知道价
	private Integer cid;// 类目ID

	private String ad;// 广告词
	private Long shopCid; // 店铺类目ID
	private Integer addSource;// 来源
	private Date created;// 创建时间
	private Date modified;// 修改时间
	private Date timingListing;// 定时上架时间

	private Long shopFreightTemplateId;// 运费模版id 平台:必填；商家:必填
	private BigDecimal volume;// 体积 平台:非必填；商家:非必填

	private String statusChangeReason;// 驳回原因

	private Integer copied;// 1 未加入商品库 2 已加入平台商品库

	private String passKey;// 生成加密key

	private Long payType;// 支付方式，1：货到付款
	 private String erpCode;//erpCode
	private String sellerId;//供应商ID
	private String productChannelCode;//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
	private String brandName;//品牌名称 查询用
	private Long brand;
	private String attributes;//商品类目属性
	private String attrSale;//商品销售属性
	private String weightUnit;//单位
	private List<VenusItemSkuOutDTO> venusItemSkuOutDTOs;
	private List<ItemAttrDTO> attrSales = new ArrayList<ItemAttrDTO>();// 销售属性
	private List<ItemAttrDTO> attributess = new ArrayList<ItemAttrDTO>();// 商品类目属性
	private List<ItemCatCascadeDTO> itemCatCascadeDTO; // 类目属性
	private Integer shelvesStatus; //上下架状态
	
	public Integer getShelvesStatus() {
		return shelvesStatus;
	}

	public void setShelvesStatus(Integer shelvesStatus) {
		this.shelvesStatus = shelvesStatus;
	}

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

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getPassKey() {
		return passKey;
	}

	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public Integer getAddSource() {
		return addSource;
	}

	public void setAddSource(Integer addSource) {
		this.addSource = addSource;
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

	public Date getTimingListing() {
		return timingListing;
	}

	public void setTimingListing(Date timingListing) {
		this.timingListing = timingListing;
	}

	public Long getShopCid() {
		return shopCid;
	}

	public void setShopCid(Long shopCid) {
		this.shopCid = shopCid;
	}

	public Integer getPlatLinkStatus() {
		return platLinkStatus;
	}

	public void setPlatLinkStatus(Integer platLinkStatus) {
		this.platLinkStatus = platLinkStatus;
	}

	public BigDecimal getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(BigDecimal guidePrice) {
		this.guidePrice = guidePrice;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
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

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getHasPrice() {
		return hasPrice;
	}

	public void setHasPrice(Integer hasPrice) {
		this.hasPrice = hasPrice;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getCopied() {
		return copied;
	}

	public void setCopied(Integer copied) {
		this.copied = copied;
	}

	public String getStatusChangeReason() {
		return statusChangeReason;
	}

	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getProductChannelCode() {
		return productChannelCode;
	}

	public void setProductChannelCode(String productChannelCode) {
		this.productChannelCode = productChannelCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getBrand() {
		return brand;
	}

	public void setBrand(Long brand) {
		this.brand = brand;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getAttrSale() {
		return attrSale;
	}

	public void setAttrSale(String attrSale) {
		this.attrSale = attrSale;
	}

	public List<VenusItemSkuOutDTO> getVenusItemSkuOutDTOs() {
		return venusItemSkuOutDTOs;
	}

	public void setVenusItemSkuOutDTOs(List<VenusItemSkuOutDTO> venusItemSkuOutDTOs) {
		this.venusItemSkuOutDTOs = venusItemSkuOutDTOs;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public List<ItemAttrDTO> getAttrSales() {
		return attrSales;
	}

	public void setAttrSales(List<ItemAttrDTO> attrSales) {
		this.attrSales = attrSales;
	}

	public List<ItemAttrDTO> getAttributess() {
		return attributess;
	}

	public void setAttributess(List<ItemAttrDTO> attributess) {
		this.attributess = attributess;
	}

	public List<ItemCatCascadeDTO> getItemCatCascadeDTO() {
		return itemCatCascadeDTO;
	}

	public void setItemCatCascadeDTO(List<ItemCatCascadeDTO> itemCatCascadeDTO) {
		this.itemCatCascadeDTO = itemCatCascadeDTO;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
}
