package cn.htd.marketcenter.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

public class ModifyPromotionCouponInfoDTO implements Serializable{

	private static final long serialVersionUID = -6080118085241711263L;
	/**
	 * 促销活动编码
	 */
	@NotBlank(message = "促销活动编码不能为空")
	private String promotionId;
	/**
	 * 操作人ID
	 */
	@NotNull(message = "操作人ID不能为空")
	private Long operatorId;
	/**
	 * 操作人名称
	 */
	@NotBlank(message = "操作人名称不能为空")
	private String operatorName;
	/**
	 * 更新时间（促销活动更新时必须传入做乐观排他用）
	 */
	@NotNull(message = "促销活动更新时间不能为空")
	private Date modifyTime;
	/**
	 * 促销活动名称
	 */
	@NotBlank(message = "促销活动名称不能为空")
	private String promotionName;
	/**
	 * 促销活动描述
	 */
	@NotBlank(message = "促销活动描述不能为空")
	private String promotionDescribe;
	/**
	 * 发放开始日期
	 */
	private Date prepStartTime;
	/**
	 * 发放结束日期
	 */
	private Date prepEndTime;
	/**
	 * 有效开始日期
	 */
	@NotNull(message = "有效开始日期不能为空")
	private Date effectiveStartTime;
	/**
	 * 有效结束日期
	 */
	@NotNull(message = "有效结束日期不能为空")
	private Date effectiveEndTime;

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getPromotionDescribe() {
		return promotionDescribe;
	}

	public void setPromotionDescribe(String promotionDescribe) {
		this.promotionDescribe = promotionDescribe;
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
}
