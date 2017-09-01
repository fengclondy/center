package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 促销活动
 */
public class PromotionValidDTO implements Serializable {

	private static final long serialVersionUID = -1587859622731343639L;
	/**
	 * 促销活动编码
	 */
	@NotBlank(message = "促销活动编码不能为空")
	private String promotionId;
	/**
	 * 促销活动展示状态
	 */
	private String showStatus;
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
	 * 模版标记
	 */
	private String temlateFlag;
	
	/**
	 * 活动进行状态
	 * @return
	 */
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTemlateFlag() {
		return temlateFlag;
	}

	public void setTemlateFlag(String temlateFlag) {
		this.temlateFlag = temlateFlag;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
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
