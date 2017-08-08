package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BuyerCouponInfoDTO extends PromotionAccumulatyDTO implements Serializable {

	private static final long serialVersionUID = -7928968078167470456L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 会员编码
	 */
	private String buyerCode;
	/**
	 * 会员名称
	 */
	private String buyerName;
	/**
	 * 会员优惠券号
	 */
	private String buyerCouponCode;
	/**
	 * 优惠券名称
	 */
	private String couponName;
	/**
	 * 优惠券使用范围
	 */
	private String couponUseRang;
	/**
	 * 优惠券商品限制
	 */
	private String couponTargetItemLimit;
	/**
	 * 优惠券描述
	 */
	private String couponDescribe;
	/**
	 * 优惠券类型
	 */
	private String couponType;
	/**
	 * 优惠券领取时间
	 */
	private Date getCouponTime;
	/**
	 * 优惠券有效期开始时间
	 */
	private Date couponStartTime;
	/**
	 * 优惠券有效期结束时间
	 */
	private Date couponEndTime;
	/**
	 * 使用门槛阈值（满减时表示满多少元）
	 */
	private BigDecimal discountThreshold;
	/**
	 * 满减/折扣券金额
	 */
	private BigDecimal couponAmount;
	/**
	 * 折扣券单次使用百分比值
	 */
	private Integer discountPercent;
	/**
	 * 优惠券限领数量
	 */
	private Integer receiveLimit;
	/**
	 * 优惠券剩余金额
	 */
	private BigDecimal couponLeftAmount;
	/**
	 * 是否过期清除Redis标记
	 */
	private int hasRedisClean;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerCouponCode() {
		return buyerCouponCode;
	}

	public void setBuyerCouponCode(String buyerCouponCode) {
		this.buyerCouponCode = buyerCouponCode;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponUseRang() {
		return couponUseRang;
	}

	public void setCouponUseRang(String couponUseRang) {
		this.couponUseRang = couponUseRang;
	}

	public String getCouponTargetItemLimit() {
		return couponTargetItemLimit;
	}

	public void setCouponTargetItemLimit(String couponTargetItemLimit) {
		this.couponTargetItemLimit = couponTargetItemLimit;
	}

	public String getCouponDescribe() {
		return couponDescribe;
	}

	public void setCouponDescribe(String couponDescribe) {
		this.couponDescribe = couponDescribe;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public Date getGetCouponTime() {
		return getCouponTime;
	}

	public void setGetCouponTime(Date getCouponTime) {
		this.getCouponTime = getCouponTime;
	}

	public Date getCouponStartTime() {
		return couponStartTime;
	}

	public void setCouponStartTime(Date couponStartTime) {
		this.couponStartTime = couponStartTime;
	}

	public Date getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(Date couponEndTime) {
		this.couponEndTime = couponEndTime;
	}

	public BigDecimal getDiscountThreshold() {
		return discountThreshold;
	}

	public void setDiscountThreshold(BigDecimal discountThreshold) {
		this.discountThreshold = discountThreshold;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Integer getReceiveLimit() {
		return receiveLimit;
	}

	public void setReceiveLimit(Integer receiveLimit) {
		this.receiveLimit = receiveLimit;
	}

	public BigDecimal getCouponLeftAmount() {
		return couponLeftAmount;
	}

	public void setCouponLeftAmount(BigDecimal couponLeftAmount) {
		this.couponLeftAmount = couponLeftAmount;
	}

	public int getHasRedisClean() {
		return hasRedisClean;
	}

	public void setHasRedisClean(int hasRedisClean) {
		this.hasRedisClean = hasRedisClean;
	}

	public void setBuyerCouponInfo(BuyerCouponInfoDTO buyerCouponInfo) {
		super.setPromotionAccumulaty(buyerCouponInfo);
		this.id = buyerCouponInfo.getId();
		this.buyerCode = buyerCouponInfo.getBuyerCode();
		this.buyerName = buyerCouponInfo.getBuyerName();
		this.buyerCouponCode = buyerCouponInfo.getBuyerCouponCode();
		this.couponName = buyerCouponInfo.getCouponName();
		this.couponUseRang = buyerCouponInfo.getCouponUseRang();
		this.couponTargetItemLimit = buyerCouponInfo.getCouponTargetItemLimit();
		this.couponDescribe = buyerCouponInfo.getCouponDescribe();
		this.couponType = buyerCouponInfo.getCouponType();
		this.getCouponTime = buyerCouponInfo.getGetCouponTime();
		this.couponStartTime = buyerCouponInfo.getCouponStartTime();
		this.couponEndTime = buyerCouponInfo.getCouponEndTime();
		this.discountThreshold = buyerCouponInfo.getDiscountThreshold();
		this.couponAmount = buyerCouponInfo.getCouponAmount();
		this.discountPercent = buyerCouponInfo.getDiscountPercent();
		this.couponLeftAmount = buyerCouponInfo.getCouponLeftAmount();
	}
}
