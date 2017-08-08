package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class ReceiveTriggerCouponDTO implements Serializable {

	private static final long serialVersionUID = -3776204954445748904L;
	/**
	 * 会员编码
	 */
	@NotBlank(message = "会员编码不能为空")
	private String buyerCode;
	/**
	 * 会员名称
	 */
	@NotBlank(message = "会员名称不能为空")
	private String buyerName;
	/**
	 * 优惠券金额
	 */
	@NotNull(message = "优惠券金额不能为空")
	private BigDecimal couponAmount;

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

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

}
