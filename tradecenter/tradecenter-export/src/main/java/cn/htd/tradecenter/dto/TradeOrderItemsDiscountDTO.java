package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TradeOrderItemsDiscountDTO implements Serializable {

	private static final long serialVersionUID = 8723510974566421189L;

	private Long id;

	private String orderNo;

	private String orderItemNo;

	private String buyerCode;

	private String sellerCode;

	private Long shopId;

	private String promotionId;

	private String levelCode;

	private String buyerCouponCode;

	private String couponProviderType;

	private String couponType;

	private BigDecimal couponDiscount;
	
	private String promotionType; //促销活动类型 1：优惠券，2：秒杀，3：限时购

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo == null ? null : orderItemNo.trim();
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode == null ? null : buyerCode.trim();
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode == null ? null : sellerCode.trim();
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId == null ? null : promotionId.trim();
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode == null ? null : levelCode.trim();
	}

	public String getBuyerCouponCode() {
		return buyerCouponCode;
	}

	public void setBuyerCouponCode(String buyerCouponCode) {
		this.buyerCouponCode = buyerCouponCode == null ? null : buyerCouponCode.trim();
	}

	public String getCouponProviderType() {
		return couponProviderType;
	}

	public void setCouponProviderType(String couponProviderType) {
		this.couponProviderType = couponProviderType == null ? null : couponProviderType.trim();
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType == null ? null : couponType.trim();
	}

	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}
	
}