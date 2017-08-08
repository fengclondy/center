package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class BuyerCouponConditionDTO implements Serializable {

	private static final long serialVersionUID = -7928968078167470456L;
	/**
	 * 会员编码
	 */
	@NotBlank(message = "会员编码不能为空")
	private String buyerCode;
	/**
	 * 会员名称
	 */
	private String buyerName;
	/**
	 * 优惠券编码
	 */
	private List<String> couponCodeList;
	/**
	 * 优惠券类型
	 */
	private String couponType;
	/**
	 * 促销活动名称
	 */
	private String couponName;
	/**
	 * 优惠券状态
	 */
	private String status;
	/**
	 * 优惠券删除状态
	 */
	private String deleteStatus;

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

	public List<String> getCouponCodeList() {
		return couponCodeList;
	}

	public void setCouponCodeList(List<String> couponCodeList) {
		this.couponCodeList = couponCodeList;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
}
