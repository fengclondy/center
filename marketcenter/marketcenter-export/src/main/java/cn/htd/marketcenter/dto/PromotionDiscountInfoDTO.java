package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class PromotionDiscountInfoDTO extends PromotionAccumulatyDTO implements Serializable {

	private static final long serialVersionUID = 7962849245620162651L;

	private Long discountId;// 优惠ID

	private String rewardType;// 奖励类型

	@NotBlank(message = "优惠券种类不能为空")
	private String couponKind;// 优惠券种类

	@NotBlank(message = "优惠券发放方式不能为空")
	private String couponProvideType;// 优惠券发放方式

	private String couponReceiveUrl;// 优惠券领取地址

	private Integer provideCount;// 申请数量

	private Integer receiveLimit;// 每人最大领取次数

	@DecimalMin(value = "0", message = "优惠券使用门槛阈值不能小于0")
	private BigDecimal discountThreshold;// 使用门槛阈值

	@DecimalMin(value = "0", message = "优惠券折扣金额不能小于0")
	private BigDecimal discountAmount;// 折扣金额

	private Integer discountPercent;// 折扣券单次使用百分比值

	private Date prepStartTime;// 发放开始日期

	private Date prepEndTime;// 发放结束日期

	@NotNull(message = "有效开始日期不能为空")
	private Date effectiveStartTime;// 有效开始日期

	@NotNull(message = "有效结束日期不能为空")
	private Date effectiveEndTime;// 有效结束日期

	private Integer receiveCount;// 领取总数量

	private int dealFlag; // 定时任务处理标记

	private String couponUseRangeDesc; // 优惠券适用范围描述

	private String couponItemDesc;// 优惠券适用商品范围描述

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public String getCouponKind() {
		return couponKind;
	}

	public void setCouponKind(String couponKind) {
		this.couponKind = couponKind;
	}

	public String getCouponProvideType() {
		return couponProvideType;
	}

	public void setCouponProvideType(String couponProvideType) {
		this.couponProvideType = couponProvideType;
	}

	public String getCouponReceiveUrl() {
		return couponReceiveUrl;
	}

	public void setCouponReceiveUrl(String couponReceiveUrl) {
		this.couponReceiveUrl = couponReceiveUrl;
	}

	public Integer getProvideCount() {
		return provideCount;
	}

	public void setProvideCount(Integer provideCount) {
		this.provideCount = provideCount;
	}

	public Integer getReceiveLimit() {
		return receiveLimit;
	}

	public void setReceiveLimit(Integer receiveLimit) {
		this.receiveLimit = receiveLimit;
	}

	public BigDecimal getDiscountThreshold() {
		return discountThreshold;
	}

	public void setDiscountThreshold(BigDecimal discountThreshold) {
		this.discountThreshold = discountThreshold;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Date getPrepStartTime() {
		return prepStartTime;
	}

	public void setPrepStartTime(Date prepStartTime) {
		this.prepStartTime = prepStartTime;
	}

	public Date getPrepEndTime() {
		return prepEndTime;
	}

	public void setPrepEndTime(Date prepEndTime) {
		this.prepEndTime = prepEndTime;
	}

	public Date getEffectiveStartTime() {
		return effectiveStartTime;
	}

	public void setEffectiveStartTime(Date effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}

	public Date getEffectiveEndTime() {
		return effectiveEndTime;
	}

	public void setEffectiveEndTime(Date effectiveEndTime) {
		this.effectiveEndTime = effectiveEndTime;
	}

	public String getCouponUseRangeDesc() {
		return couponUseRangeDesc;
	}

	public void setCouponUseRangeDesc(String couponUseRangeDesc) {
		this.couponUseRangeDesc = couponUseRangeDesc;
	}

	public String getCouponItemDesc() {
		return couponItemDesc;
	}

	public void setCouponItemDesc(String couponItemDesc) {
		this.couponItemDesc = couponItemDesc;
	}

	public Integer getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}

	public int getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(int dealFlag) {
		this.dealFlag = dealFlag;
	}
}