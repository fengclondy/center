package cn.htd.searchcenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class ItemDTO implements Serializable {

	private static final long serialVersionUID = 8737941431210274666L;

	private Long ItemId;// 商品id
	private String ItemCode;
	private String ItemName;// 商品名称
	private Long sellerId;// 卖家id
	private Integer shopId;// 店铺id
	private Long cid;// 品类id
	private Long shopCid;
	private Long brandId;// 品牌id
	private String cidName;//品类名称
	private String brandName;//品牌名称
	private String productChannelCode;// 关键字 10: 内部供应商 20: 外部供应商 3010: 商品+
	private String describeContent;// 商品介绍
	private Long salesVolume;// 销售量
	
	private Boolean isBoxFlag;// 是否包厢标记
	private Boolean isSalesWholeCountry;// 是否销售全国
	private String areaCode;// 销售区域
	private String deliveryTo;// 终止时间
	private Long areaDisplayQuantity;// 商品域名
	private Long boxDisplayQuantity;// 商品域名
	private Long timelimitedSkuCount;// 商品域名
	private BigDecimal skuTimelimitedPrice;// 商品域名
	private Timestamp listtingTime;// 商品域名
	private Timestamp effectiveTime;
	private BigDecimal retailPrice;// 商品域名
	private BigDecimal areaSalePrice;// 商品域名
	private BigDecimal boxSalePrice;// 商品域名
	private String sellerName; //供应商名称
	private String imgURL; //商品图片
	private BigDecimal vipSalePrice;// 商品域名
	private BigDecimal ladderPrice;// 商品域名

	private String createId;// 创建id
	private String modifyId;// 修改id
	private String createName;// 创建人名称
	private String modifyName;// 修改人名称
	private Date created;// 创建时间结束
	private Date modified;// 修改时间结束
	
	private String c_attr_id_name;
	private String c_attrId_valueId;
	private String c_valueId_valueName;
	private String attr_id;
	private String attr_name;
	private String attr_sale_value;
	private Integer hasVipPrice;
	
	public BigDecimal getVipSalePrice() {
		return vipSalePrice;
	}
	public void setVipSalePrice(BigDecimal vipSalePrice) {
		this.vipSalePrice = vipSalePrice;
	}
	public Integer getHasVipPrice() {
		return hasVipPrice;
	}
	public void setHasVipPrice(Integer hasVipPrice) {
		this.hasVipPrice = hasVipPrice;
	}
	public Timestamp getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Timestamp effectiveTime) {
		this.effectiveTime = effectiveTime;
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
	public Boolean getIsBoxFlag() {
		return isBoxFlag;
	}
	public void setIsBoxFlag(Boolean isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}
	public Boolean getIsSalesWholeCountry() {
		return isSalesWholeCountry;
	}
	public void setIsSalesWholeCountry(Boolean isSalesWholeCountry) {
		this.isSalesWholeCountry = isSalesWholeCountry;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getDeliveryTo() {
		return deliveryTo;
	}
	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}
	public Long getAreaDisplayQuantity() {
		return areaDisplayQuantity;
	}
	public void setAreaDisplayQuantity(Long areaDisplayQuantity) {
		this.areaDisplayQuantity = areaDisplayQuantity;
	}
	public Long getBoxDisplayQuantity() {
		return boxDisplayQuantity;
	}
	public void setBoxDisplayQuantity(Long boxDisplayQuantity) {
		this.boxDisplayQuantity = boxDisplayQuantity;
	}
	public Long getTimelimitedSkuCount() {
		return timelimitedSkuCount;
	}
	public void setTimelimitedSkuCount(Long timelimitedSkuCount) {
		this.timelimitedSkuCount = timelimitedSkuCount;
	}
	public BigDecimal getSkuTimelimitedPrice() {
		return skuTimelimitedPrice;
	}
	public void setSkuTimelimitedPrice(BigDecimal skuTimelimitedPrice) {
		this.skuTimelimitedPrice = skuTimelimitedPrice;
	}
	public Timestamp getListtingTime() {
		return listtingTime;
	}
	public void setListtingTime(Timestamp listtingTime) {
		this.listtingTime = listtingTime;
	}
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public BigDecimal getAreaSalePrice() {
		return areaSalePrice;
	}
	public void setAreaSalePrice(BigDecimal areaSalePrice) {
		this.areaSalePrice = areaSalePrice;
	}
	public BigDecimal getBoxSalePrice() {
		return boxSalePrice;
	}
	public void setBoxSalePrice(BigDecimal boxSalePrice) {
		this.boxSalePrice = boxSalePrice;
	}
	public BigDecimal getLadderPrice() {
		return ladderPrice;
	}
	public void setLadderPrice(BigDecimal ladderPrice) {
		this.ladderPrice = ladderPrice;
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
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCidName() {
		return cidName;
	}
	public void setCidName(String cidName) {
		this.cidName = cidName;
	}
	public String getC_attr_id_name() {
		return c_attr_id_name;
	}
	public void setC_attr_id_name(String c_attr_id_name) {
		this.c_attr_id_name = c_attr_id_name;
	}
	public String getC_attrId_valueId() {
		return c_attrId_valueId;
	}
	public void setC_attrId_valueId(String c_attrId_valueId) {
		this.c_attrId_valueId = c_attrId_valueId;
	}
	public String getC_valueId_valueName() {
		return c_valueId_valueName;
	}
	public void setC_valueId_valueName(String c_valueId_valueName) {
		this.c_valueId_valueName = c_valueId_valueName;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
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
	public String getItemCode() {
		return ItemCode;
	}
	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}
	public Long getShopCid() {
		return shopCid;
	}
	public void setShopCid(Long shopCid) {
		this.shopCid = shopCid;
	}
}
