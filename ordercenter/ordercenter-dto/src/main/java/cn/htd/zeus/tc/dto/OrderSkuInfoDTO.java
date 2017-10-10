package cn.htd.zeus.tc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderSkuInfoDTO implements Serializable {

	private static final long serialVersionUID = -1583130971130330701L;

	private long skuId; // 商品skuId

	@NotEmpty(message = "skuCode不能为空")
	private String skuCode; // 商品sku编码

	@NotNull(message = "isBoxFlag不能为空")
	private Integer isBoxFlag; // 0：区域商品 1：包厢

	private String citySiteCode;// 购买区域

	private String memberCode;// 卖家会员编码

	private String companyName; // 公司名称

	@NotNull(message = "isHasDevRelation不能为空")
	private Integer isHasDevRelation; // 是否有经营关系 1-有 0-没有 【内部供应商商品必传】

	private Integer isLogin;// 是否已登录 1:已登录 0：没有登录

	private String productCode;// 商品编码

	private String productName;// 商品名称

	private String productUrl;// 商品图片URL

	private String productStatus;// 商品状态

	private List<OrderItemAttributeDTO> productAttributeList; // sku销售属性

	private List<OrderItemAttributeDTO> itemAttributeList; // 商品属性

	private BigDecimal price = BigDecimal.ZERO;// 商品价格

	@Min(value = 1, message = "最少为1")
	private int productCount;// 商品数量

	private BigDecimal freight = BigDecimal.ZERO;// 运费

	private long brandId;// 品牌编码

	private long thirdCategoryId;// 三级类目编码

	private String itemChannel; // 商品渠道

	private BigDecimal weight; // 毛重

	private BigDecimal netWeight; // 净重

	private BigDecimal length; // 长

	private BigDecimal width; // 宽

	private BigDecimal height; // 高

	private String weightUnit; // 单位

	private Long shopFreightTemplateId;// 运费模版ID 【外部供应商】
	
	//是否是限时购商品  0：不是，1：是
	private Integer isLimitedTimePurchase;
	
	//促销活动类型 1：优惠券，2:秒杀,3:限时购
	private String promotionType;
	
	//促销活动编码
	private String promotionId;

	/**
	 * @return the skuId
	 */
	public long getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId
	 *            the skuId to set
	 */
	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	/**
	 * @return the skuCode
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * @param skuCode
	 *            the skuCode to set
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * @return the isBoxFlag
	 */
	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}

	/**
	 * @param isBoxFlag
	 *            the isBoxFlag to set
	 */
	public void setIsBoxFlag(Integer isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

	/**
	 * @return the citySiteCode
	 */
	public String getCitySiteCode() {
		return citySiteCode;
	}

	/**
	 * @param citySiteCode
	 *            the citySiteCode to set
	 */
	public void setCitySiteCode(String citySiteCode) {
		this.citySiteCode = citySiteCode;
	}

	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the isHasDevRelation
	 */
	public Integer getIsHasDevRelation() {
		return isHasDevRelation;
	}

	/**
	 * @param isHasDevRelation
	 *            the isHasDevRelation to set
	 */
	public void setIsHasDevRelation(Integer isHasDevRelation) {
		this.isHasDevRelation = isHasDevRelation;
	}

	/**
	 * @return the isLogin
	 */
	public Integer getIsLogin() {
		return isLogin;
	}

	/**
	 * @param isLogin
	 *            the isLogin to set
	 */
	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productUrl
	 */
	public String getProductUrl() {
		return productUrl;
	}

	/**
	 * @param productUrl
	 *            the productUrl to set
	 */
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	/**
	 * @return the productStatus
	 */
	public String getProductStatus() {
		return productStatus;
	}

	/**
	 * @param productStatus
	 *            the productStatus to set
	 */
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	/**
	 * @return the productAttributeList
	 */
	public List<OrderItemAttributeDTO> getProductAttributeList() {
		return productAttributeList;
	}

	/**
	 * @param productAttributeList
	 *            the productAttributeList to set
	 */
	public void setProductAttributeList(List<OrderItemAttributeDTO> productAttributeList) {
		this.productAttributeList = productAttributeList;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the productCount
	 */
	public int getProductCount() {
		return productCount;
	}

	/**
	 * @param productCount
	 *            the productCount to set
	 */
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	/**
	 * @return the freight
	 */
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * @param freight
	 *            the freight to set
	 */
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	/**
	 * @return the brandId
	 */
	public long getBrandId() {
		return brandId;
	}

	/**
	 * @param brandId
	 *            the brandId to set
	 */
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return the thirdCategoryId
	 */
	public long getThirdCategoryId() {
		return thirdCategoryId;
	}

	/**
	 * @param thirdCategoryId
	 *            the thirdCategoryId to set
	 */
	public void setThirdCategoryId(long thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}

	/**
	 * @return the itemChannel
	 */
	public String getItemChannel() {
		return itemChannel;
	}

	/**
	 * @param itemChannel
	 *            the itemChannel to set
	 */
	public void setItemChannel(String itemChannel) {
		this.itemChannel = itemChannel;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return the netWeight
	 */
	public BigDecimal getNetWeight() {
		return netWeight;
	}

	/**
	 * @param netWeight
	 *            the netWeight to set
	 */
	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * @return the length
	 */
	public BigDecimal getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(BigDecimal length) {
		this.length = length;
	}

	/**
	 * @return the width
	 */
	public BigDecimal getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * @return the weightUnit
	 */
	public String getWeightUnit() {
		return weightUnit;
	}

	/**
	 * @param weightUnit
	 *            the weightUnit to set
	 */
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	/**
	 * @return the shopFreightTemplateId
	 */
	public Long getShopFreightTemplateId() {
		return shopFreightTemplateId;
	}

	/**
	 * @param shopFreightTemplateId
	 *            the shopFreightTemplateId to set
	 */
	public void setShopFreightTemplateId(Long shopFreightTemplateId) {
		this.shopFreightTemplateId = shopFreightTemplateId;
	}

	/**
	 * @return the itemAttributeList
	 */
	public List<OrderItemAttributeDTO> getItemAttributeList() {
		return itemAttributeList;
	}

	/**
	 * @param itemAttributeList
	 *            the itemAttributeList to set
	 */
	public void setItemAttributeList(List<OrderItemAttributeDTO> itemAttributeList) {
		this.itemAttributeList = itemAttributeList;
	}

	public Integer getIsLimitedTimePurchase() {
		return isLimitedTimePurchase;
	}

	public void setIsLimitedTimePurchase(Integer isLimitedTimePurchase) {
		this.isLimitedTimePurchase = isLimitedTimePurchase;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

}
