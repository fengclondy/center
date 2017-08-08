package cn.htd.searchcenter.dump.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ItemSearchData implements Serializable {

	private String id;
	private Long ItemId;// 商品id
	private String ItemName;// 商品名称
	private String ItemCode;//商品编码
	private Long sellerId;// 卖家id
	private Integer shopId;// 商品建新状态;1是申请，2是通过，3是驳回， 4是平台关闭，5是开通
	private Long cid;// 商品域名
	private Long shopCid;
	private Long brandId;// 商品logourl
	private String productChannelCode;// 关键字
	private String describeContent;// 商品介绍
	private Long salesVolume;// 开通时间
	private String vbc;
	private Boolean isSalesWholeCountry;// 终止时间
	private String[] areaCode;// 终止时间
	private Long quantity;// 商品域名
	private Timestamp listtingTime;// 商品域名
	private Boolean hasQuantity;// 终止时间
	private BigDecimal price;// 商品价格
	private BigDecimal unLoginPrice; //未登录价格
	private String createId;// 申请时间开始
	private String modifyId;// 申请时间开始
	private String createName;// 申请时间开始
	private String modifyName;// 申请时间开始
	private Date created;// 申请时间结束
	private Date modified;// 申请时间结束
	private Long itemType;
	private String attrAll;
	private String[] attr_id;
	private String attr_name;
	private String attr_sale_value;
	private String sellerType; //供应商类型
	private String imgURL; //商品图片
	private String belongRelationships; //归属关系拼接
	private String boxRelationships;	//包厢关系拼接
	private Integer shelvesFlag;
	private String hotWord;
	private String shieldCidAndBrandId;
	private String cidName; //商品品类名称
	private String brandName;//商品品牌名称
	private String sellerName; //供应商名称
	private String cidNameScreen;
	private String brandNameScreen;
	private String sellerNameScreen;
	private Integer publicSaleWholeCountryFlag;
	
	public Integer getPublicSaleWholeCountryFlag() {
		return publicSaleWholeCountryFlag;
	}
	public void setPublicSaleWholeCountryFlag(Integer publicSaleWholeCountryFlag) {
		this.publicSaleWholeCountryFlag = publicSaleWholeCountryFlag;
	}
	public String getCidNameScreen() {
		return cidNameScreen;
	}
	public void setCidNameScreen(String cidNameScreen) {
		this.cidNameScreen = cidNameScreen;
	}
	public String getBrandNameScreen() {
		return brandNameScreen;
	}
	public void setBrandNameScreen(String brandNameScreen) {
		this.brandNameScreen = brandNameScreen;
	}
	public String getSellerNameScreen() {
		return sellerNameScreen;
	}
	public void setSellerNameScreen(String sellerNameScreen) {
		this.sellerNameScreen = sellerNameScreen;
	}
	public Integer getShelvesFlag() {
		return shelvesFlag;
	}
	public void setShelvesFlag(Integer shelvesFlag) {
		this.shelvesFlag = shelvesFlag;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getBoxRelationships() {
		return boxRelationships;
	}
	public void setBoxRelationships(String boxRelationships) {
		this.boxRelationships = boxRelationships;
	}
	public String getBelongRelationships() {
		return belongRelationships;
	}
	public void setBelongRelationships(String belongRelationships) {
		this.belongRelationships = belongRelationships;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public Boolean getHasQuantity() {
		return hasQuantity;
	}
	public void setHasQuantity(Boolean hasQuantity) {
		this.hasQuantity = hasQuantity;
	}
	public BigDecimal getUnLoginPrice() {
		return unLoginPrice;
	}
	public void setUnLoginPrice(BigDecimal unLoginPrice) {
		this.unLoginPrice = unLoginPrice;
	}
	public String getVbc() {
		return vbc;
	}
	public void setVbc(String vbc) {
		this.vbc = vbc;
	}
	public Long getItemType() {
		return itemType;
	}
	public void setItemType(Long itemType) {
		this.itemType = itemType;
	}
	public Long getItemId() {
		return ItemId;
	}
	public void setItemId(Long itemId) {
		ItemId = itemId;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getProductChannelCode() {
		return productChannelCode;
	}
	public void setProductChannelCode(String productChannelCode) {
		this.productChannelCode = productChannelCode;
	}
	public String getDescribeContent() {
		return describeContent;
	}
	public void setDescribeContent(String describeContent) {
		this.describeContent = describeContent;
	}
	public Long getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(Long salesVolume) {
		this.salesVolume = salesVolume;
	}
	public Boolean getIsSalesWholeCountry() {
		return isSalesWholeCountry;
	}
	public void setIsSalesWholeCountry(Boolean isSalesWholeCountry) {
		this.isSalesWholeCountry = isSalesWholeCountry;
	}
	public String[] getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String[] areaCode) {
		this.areaCode = areaCode;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Timestamp getListtingTime() {
		return listtingTime;
	}
	public void setListtingTime(Timestamp listtingTime) {
		this.listtingTime = listtingTime;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getModifyId() {
		return modifyId;
	}
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getModifyName() {
		return modifyName;
	}
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
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
	public String getCidName() {
		return cidName;
	}
	public void setCidName(String cidName) {
		this.cidName = cidName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getAttrAll() {
		return attrAll;
	}
	public void setAttrAll(String attrAll) {
		this.attrAll = attrAll;
	}
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getAttr_sale_value() {
		return attr_sale_value;
	}
	public void setAttr_sale_value(String attr_sale_value) {
		this.attr_sale_value = attr_sale_value;
	}
	public String[] getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String[] attr_id) {
		this.attr_id = attr_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHotWord() {
		return hotWord;
	}
	public void setHotWord(String hotWord) {
		this.hotWord = hotWord;
	}
	public String getItemCode() {
		return ItemCode;
	}
	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}
	public String getShieldCidAndBrandId() {
		return shieldCidAndBrandId;
	}
	public void setShieldCidAndBrandId(String shieldCidAndBrandId) {
		this.shieldCidAndBrandId = shieldCidAndBrandId;
	}
	public Long getShopCid() {
		return shopCid;
	}
	public void setShopCid(Long shopCid) {
		this.shopCid = shopCid;
	}
}
