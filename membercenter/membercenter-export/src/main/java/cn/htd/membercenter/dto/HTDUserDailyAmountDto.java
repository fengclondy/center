package cn.htd.membercenter.dto;

import java.io.Serializable;

public class HTDUserDailyAmountDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String targetDate;

	private String lastCusNo;

	private int pageSize;

	public String getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	public String getLastCusNo() {
		return lastCusNo;
	}

	public void setLastCusNo(String lastCusNo) {
		this.lastCusNo = lastCusNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
