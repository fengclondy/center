package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class VerifyResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String verifyStatus;
	private String cooperateVerifyStatus;
	private Long operatorId;
	private String operatorName;
	private String remark;
	private Date modifyTime;
	private Date cooperateModifyTime;
	private Long verifyId;

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getCooperateVerifyStatus() {
		return cooperateVerifyStatus;
	}

	public void setCooperateVerifyStatus(String cooperateVerifyStatus) {
		this.cooperateVerifyStatus = cooperateVerifyStatus;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCooperateModifyTime() {
		return cooperateModifyTime;
	}

	public void setCooperateModifyTime(Date cooperateModifyTime) {
		this.cooperateModifyTime = cooperateModifyTime;
	}

	/**
	 * @return the verifyId
	 */
	public Long getVerifyId() {
		return verifyId;
	}

	/**
	 * @param verifyId
	 *            the verifyId to set
	 */
	public void setVerifyId(Long verifyId) {
		this.verifyId = verifyId;
	}

}
