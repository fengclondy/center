package cn.htd.tradecenter.domain;

import java.math.BigDecimal;

public class TradeOrdersDiscountInfo {

	private String promotionId;

	private String levelCode;

	private String couponName;

	private String buyerCouponCode;

	private String couponProviderType;

	private String couponType;

	private BigDecimal couponDiscount = BigDecimal.ZERO;

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
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
}