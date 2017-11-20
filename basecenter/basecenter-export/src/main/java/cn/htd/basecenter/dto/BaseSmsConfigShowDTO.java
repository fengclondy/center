package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class BaseSmsConfigShowDTO implements Serializable {

	private static final long serialVersionUID = 6008185440283522692L;

	private String channelCode;

	private String channelName;

	private int usedFlag;

	private Long operatorId;

	private String operatorName;

	private Date operatorTime;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getUsedFlag() {
		return usedFlag;
	}

	public void setUsedFlag(int usedFlag) {
		this.usedFlag = usedFlag;
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

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
}