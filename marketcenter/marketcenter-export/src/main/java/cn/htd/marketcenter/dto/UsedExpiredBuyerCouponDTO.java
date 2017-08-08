package cn.htd.marketcenter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class UsedExpiredBuyerCouponDTO implements Serializable {

	private static final long serialVersionUID = -7928968078167470456L;
	/**
	 * 会员编码
	 */
	@NotBlank(message = "会员编码不能为空")
	private String buyerCode;
	/**
	 * 优惠券编号
	 */
	@NotBlank(message = "优惠券编号不能为空")
	private String couponCode;
	/**
	 * 操作人ID
	 */
	@NotNull(message = "领券人ID不能为空")
	private Long operatorId;
	/**
	 * 操作人名称
	 */
	@NotBlank(message = "领券人名称不能为空")
	private String operatorName;

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
