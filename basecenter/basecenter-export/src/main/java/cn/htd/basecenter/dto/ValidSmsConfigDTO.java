package cn.htd.basecenter.dto;

import java.io.Serializable;

public class ValidSmsConfigDTO implements Serializable {

	private static final long serialVersionUID = 2648782374271000247L;

	private String channelCode;

	private Long operatorId;

	private String operatorName;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
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