package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class PopupAdConditionDTO implements Serializable {

	private String adName;

	private String terminalCode;

	private Date startTime;

	private Date endTime;

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
