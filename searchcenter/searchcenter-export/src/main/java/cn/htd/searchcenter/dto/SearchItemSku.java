package cn.htd.searchcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SearchItemSku implements Serializable {

	private static final long serialVersionUID = -9122880311384974918L;

	private Long itemId;// 商品id
	private Long sellerId;// 商家ID
	private Long shopCid;// 商品所属店铺类目id
	private String itemName;// 商品名称
	private String ad;// 广告词
	private Long cid;// 类目ID
	private Long brand;// 品牌
	private String brandName;// 品牌名称
	private Integer hasPrice;// 是否有报价：1：有价格；2：暂无报价
	private Long productId;// 商品货号

	private String describeUrl;// 商品描述url，存在jfs中
	private Integer itemStatus;// 商品状态,1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定，
								// 7： 申请解锁

	private String picUrl;// 商品图片url列表
	private Long shopId;// 店铺ID
	private BigDecimal itemPrice;// 商品显示价格

	private Long skuId;// SKU ID
	private Integer skuInventory;// SKU库存
	private BigDecimal skuPrice;// SKU价格
	private BigDecimal skuInquiryPirce;// SKU询价
	private String skuScope;// SKU评价评分

	private BigDecimal salesVolume;// 销量

	private String aliasItemName;// 商品別名

	private Long shopFreightTemplateId;// 运费模版id 平台:必填；商家:必填

	private List<SearchItemAttr> attributes = new ArrayList<SearchItemAttr>();// 属性值

	private Long payType;// 支付方式，1：货到付款

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

	public List<SearchItemAttr> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<SearchItemAttr> attributes) {
		this.attributes = attributes;
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

	public String getAliasItemName() {
		return aliasItemName;
	}

	public void setAliasItemName(String aliasItemName) {
		this.aliasItemName = aliasItemName;
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

}
