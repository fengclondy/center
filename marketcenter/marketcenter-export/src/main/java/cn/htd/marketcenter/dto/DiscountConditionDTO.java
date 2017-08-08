package cn.htd.marketcenter.dto;

import java.io.Serializable;

/**
 * 优惠券活动检索条件
 */
public class DiscountConditionDTO implements Serializable {

	private static final long serialVersionUID = 5210372509736403342L;
	/**
	 * 优惠券活动名称
	 */
	private String discountName;
	/**
	 * 优惠券活动类型
	 */
	private String discountType;
	/**
	 * 活动状态
	 */
	private String status;
	/**
	 * 审核状态
	 */
	private String verifyStatus;

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
}
