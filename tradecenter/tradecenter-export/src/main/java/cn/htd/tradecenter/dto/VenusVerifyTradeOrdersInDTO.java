package cn.htd.tradecenter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class VenusVerifyTradeOrdersInDTO implements Serializable {

	private static final long serialVersionUID = -8028562916259758697L;
	/**
	 * 订单号
	 */
	@NotBlank(message = "订单号不能为空")
	private String orderNo;
	/**
	 * 审核备注
	 */
	private String verifyComment;
	/**
	 * 审核人ID
	 */
	@NotNull(message = "审核人ID不能为空")
	private Long operatorId;
	/**
	 * 审核人名称
	 */
	@NotBlank(message = "审核人名称不能为空")
	private String operatorName;
	/**
	 * 订单modifyTime做乐观排他用格式YYYYMMDDHH24MISS
	 */
	@NotBlank(message = "订单更新时间不能为空")
	private String modifyTimeStr;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getVerifyComment() {
		return verifyComment;
	}

	public void setVerifyComment(String verifyComment) {
		this.verifyComment = verifyComment;
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

	public String getModifyTimeStr() {
		return modifyTimeStr;
	}

	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}
}
