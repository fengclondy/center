package cn.htd.marketcenter.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

public class VerifyInfoDTO implements Serializable{

	private static final long serialVersionUID = 1659811059998357042L;
	/**
	 * 促销活动编码
	 */
	@NotBlank(message = "促销活动编码不能为空")
	private String promotionId;
	/**
	 * 审核结果
	 */
	@NotBlank(message = "审核结果不能为空")
	@Pattern(regexp="^[1-2]{1}$", message = "审核结果不正确")
	private String verifyResult;
	/**
	 * 审核人ID
	 */
	@NotNull(message = "审核人ID不能为空")
	private Long verifierId;
	/**
	 * 审核人名称
	 */
	@NotBlank(message = "审核人名称不能为空")
	private String verifierName;
	/**
	 * 审核备注
	 */
	private String verifyRemark;
	/**
	 * 更新时间（促销活动更新时必须传入做乐观排他用）
	 */
	@NotNull(message = "促销活动更新时间不能为空")
	private Date modifyTime;

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getVerifyResult() {
		return verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	public Long getVerifierId() {
		return verifierId;
	}

	public void setVerifierId(Long verifierId) {
		this.verifierId = verifierId;
	}

	public String getVerifierName() {
		return verifierName;
	}

	public void setVerifierName(String verifierName) {
		this.verifierName = verifierName;
	}

	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
