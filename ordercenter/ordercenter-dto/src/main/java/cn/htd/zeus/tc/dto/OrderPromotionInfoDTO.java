package cn.htd.zeus.tc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderPromotionInfoDTO implements Serializable {

	private static final long serialVersionUID = -545918682261472097L;

	private String promotionId; // 主键

	private String type; // 活动类型，1：优惠券，2：秒杀

	private String couponType; // 优惠券类型，1：满减券 2：折扣券

	private String couponCode; // 优惠券编号

	private String couponName; // 优惠券名称

	private BigDecimal price;// 秒杀为秒杀单价；满减为满减金额，折扣为能折扣金额

	private List<Long> itemIdList; // 活动包含的商品id

	private String couponDescribe; // 优惠券描述

	private Date getCouponTime; // 优惠券领取时间

	private Date couponStartTime; // 优惠券有效期开始时间

	private Date couponEndTime; // 优惠券有效期结束时间

	private BigDecimal totalDiscountAmount; // 该优惠券折扣总金额

	private BigDecimal discountThreshold; // 使用门槛阈值（满减时表示满多少元）

	private BigDecimal couponAmount; // 满减/折扣券金额

	private Integer discountPercent; // 折扣券单次使用百分比值

	private BigDecimal couponUsedAmount; // 折扣券已使用金额

	private BigDecimal couponLeftAmount; // 优惠券剩余金额

	private String couponUseRang; // 优惠券使用范围

	private String couponTargetItemLimit; // 优惠券商品限制

	private String buyerCouponCode;

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the couponType
	 */
	public String getCouponType() {
		return couponType;
	}

	/**
	 * @param couponType
	 *            the couponType to set
	 */
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * @param couponCode
	 *            the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the couponName
	 */
	public String getCouponName() {
		return couponName;
	}

	/**
	 * @param couponName
	 *            the couponName to set
	 */
	public void setCouponName(String couponName) {
		this.couponName = couponName;
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
	 * @return the itemIdList
	 */
	public List<Long> getItemIdList() {
		return itemIdList;
	}

	/**
	 * @param itemIdList
	 *            the itemIdList to set
	 */
	public void setItemIdList(List<Long> itemIdList) {
		this.itemIdList = itemIdList;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the couponDescribe
	 */
	public String getCouponDescribe() {
		return couponDescribe;
	}

	/**
	 * @param couponDescribe
	 *            the couponDescribe to set
	 */
	public void setCouponDescribe(String couponDescribe) {
		this.couponDescribe = couponDescribe;
	}

	/**
	 * @return the getCouponTime
	 */
	public Date getGetCouponTime() {
		return getCouponTime;
	}

	/**
	 * @param getCouponTime
	 *            the getCouponTime to set
	 */
	public void setGetCouponTime(Date getCouponTime) {
		this.getCouponTime = getCouponTime;
	}

	/**
	 * @return the couponStartTime
	 */
	public Date getCouponStartTime() {
		return couponStartTime;
	}

	/**
	 * @param couponStartTime
	 *            the couponStartTime to set
	 */
	public void setCouponStartTime(Date couponStartTime) {
		this.couponStartTime = couponStartTime;
	}

	/**
	 * @return the couponEndTime
	 */
	public Date getCouponEndTime() {
		return couponEndTime;
	}

	/**
	 * @param couponEndTime
	 *            the couponEndTime to set
	 */
	public void setCouponEndTime(Date couponEndTime) {
		this.couponEndTime = couponEndTime;
	}

	/**
	 * @return the totalDiscountAmount
	 */
	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	/**
	 * @param totalDiscountAmount
	 *            the totalDiscountAmount to set
	 */
	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	/**
	 * @return the discountThreshold
	 */
	public BigDecimal getDiscountThreshold() {
		return discountThreshold;
	}

	/**
	 * @param discountThreshold
	 *            the discountThreshold to set
	 */
	public void setDiscountThreshold(BigDecimal discountThreshold) {
		this.discountThreshold = discountThreshold;
	}

	/**
	 * @return the couponAmount
	 */
	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	/**
	 * @param couponAmount
	 *            the couponAmount to set
	 */
	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	/**
	 * @return the discountPercent
	 */
	public Integer getDiscountPercent() {
		return discountPercent;
	}

	/**
	 * @param discountPercent
	 *            the discountPercent to set
	 */
	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	/**
	 * @return the couponUsedAmount
	 */
	public BigDecimal getCouponUsedAmount() {
		return couponUsedAmount;
	}

	/**
	 * @param couponUsedAmount
	 *            the couponUsedAmount to set
	 */
	public void setCouponUsedAmount(BigDecimal couponUsedAmount) {
		this.couponUsedAmount = couponUsedAmount;
	}

	/**
	 * @return the couponLeftAmount
	 */
	public BigDecimal getCouponLeftAmount() {
		return couponLeftAmount;
	}

	/**
	 * @param couponLeftAmount
	 *            the couponLeftAmount to set
	 */
	public void setCouponLeftAmount(BigDecimal couponLeftAmount) {
		this.couponLeftAmount = couponLeftAmount;
	}

	/**
	 * @return the couponUseRang
	 */
	public String getCouponUseRang() {
		return couponUseRang;
	}

	/**
	 * @param couponUseRang
	 *            the couponUseRang to set
	 */
	public void setCouponUseRang(String couponUseRang) {
		this.couponUseRang = couponUseRang;
	}

	/**
	 * @return the couponTargetItemLimit
	 */
	public String getCouponTargetItemLimit() {
		return couponTargetItemLimit;
	}

	/**
	 * @param couponTargetItemLimit
	 *            the couponTargetItemLimit to set
	 */
	public void setCouponTargetItemLimit(String couponTargetItemLimit) {
		this.couponTargetItemLimit = couponTargetItemLimit;
	}

	public String getBuyerCouponCode() {
		return buyerCouponCode;
	}

	public void setBuyerCouponCode(String buyerCouponCode) {
		this.buyerCouponCode = buyerCouponCode;
	}

}
