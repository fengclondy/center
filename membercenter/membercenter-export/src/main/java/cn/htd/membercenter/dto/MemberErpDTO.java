package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberErpDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;// 主标识

	public String getErpDownType() {
		return erpDownType;
	}

	public void setErpDownType(String erpDownType) {
		this.erpDownType = erpDownType;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public Long getSpaceTime() {
		return spaceTime;
	}

	public void setSpaceTime(Long spaceTime) {
		this.spaceTime = spaceTime;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the syncErrorMsg
	 */
	public String getSyncErrorMsg() {
		return syncErrorMsg;
	}

	/**
	 * @param syncErrorMsg
	 *            the syncErrorMsg to set
	 */
	public void setSyncErrorMsg(String syncErrorMsg) {
		this.syncErrorMsg = syncErrorMsg;
	}

	private String erpDownType;// ERP下行类型，1：会员信息下行，2：单位往来关系下行，3：客商业务员下行
	private String erpStatus;// ERP处理状态
	private Long spaceTime;// 下行处理间隔时间
	private String memberCode;// 会员编码
	private Date time;// 异常时间
	private String syncErrorMsg;// 异常原因
}
