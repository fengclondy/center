package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class PopupAdModifyConditionDTO implements Serializable {

	@NotNull(message = "弹屏广告ID不能为空")
	private Long adId;

	@NotNull(message = "操作人ID不能为空")
	private Long operatorId;

	@NotEmpty(message = "操作人名称不能为空")
	private String operatorName;

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
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
