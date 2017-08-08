package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class BuyerHisPointDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8737822560587874667L;
	private Long memberId;
	private Date provideStartTime;
	private Date provideEndTime;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Date getProvideStartTime() {
		return provideStartTime;
	}

	public void setProvideStartTime(Date provideStartTime) {
		this.provideStartTime = provideStartTime;
	}

	public Date getProvideEndTime() {
		return provideEndTime;
	}

	public void setProvideEndTime(Date provideEndTime) {
		this.provideEndTime = provideEndTime;
	}
}
