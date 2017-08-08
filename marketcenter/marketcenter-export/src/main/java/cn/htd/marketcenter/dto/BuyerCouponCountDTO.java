package cn.htd.marketcenter.dto;

import java.io.Serializable;

public class BuyerCouponCountDTO implements Serializable {

	private static final long serialVersionUID = -7928968078167470456L;
	/**
	 * 会员编码
	 */
	private String buyerCode;
	/**
	 * 会员名称
	 */
	private String buyerName;
	/**
	 * 优惠券状态
	 */
	private String status;
	/**
	 * 优惠券类型
	 */
	private String couponType;
	/**
	 * 统计件数
	 */
	private Long receiveCount;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public Long getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Long receiveCount) {
		this.receiveCount = receiveCount;
	}
}
