package cn.htd.zeus.tc.dto;

import java.io.Serializable;

public class OrderItemCouponDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6375992804416429571L;
	
	/**
	 * 层级编码
	 */
	private String levelCode;

	/**
	 * 会员优惠券号
	 */
	private String buyerCouponCode;
	
	/**
	 * 优惠券类型
	 */
	private String couponType;

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getBuyerCouponCode() {
		return buyerCouponCode;
	}

	public void setBuyerCouponCode(String buyerCouponCode) {
		this.buyerCouponCode = buyerCouponCode;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
}
