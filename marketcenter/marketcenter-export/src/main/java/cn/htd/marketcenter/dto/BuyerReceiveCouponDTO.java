package cn.htd.marketcenter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class BuyerReceiveCouponDTO implements Serializable {

	private static final long serialVersionUID = -7928968078167470456L;
	/**
	 * 会员编码
	 */
	@NotBlank(message = "会员编码不能为空")
	private String buyerCode;
	/**
	 * 会员等级
	 */
	@NotBlank(message = "会员等级不能为空")
	private String buyerGrade;
	/**
	 * 会员名称
	 */
	@NotBlank(message = "会员编码不能为空")
	private String buyerName;
	/**
	 * 优惠券活动编码
	 */
	@NotBlank(message = "优惠券活动编码不能为空")
	private String promotionId;
	/**
	 * 优惠券活动层级编码
	 */
	@NotBlank(message = "优惠券活动层级编码不能为空")
	private String levelCode;
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

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
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
