package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberModifyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long memberId;

	private String verifyStatus;

	private Long operatorId;

	private String operatorName;

	private Long verifyId;

	private String remark;

	private String infoType;

	private Long recordId;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	/**
	 * @return the verifyStatus
	 */
	public String getVerifyStatus() {
		return verifyStatus;
	}

	/**
	 * @param verifyStatus
	 *            the verifyStatus to set
	 */
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
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

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the infoType
	 */
	public String getInfoType() {
		return infoType;
	}

	/**
	 * @param infoType
	 *            the infoType to set
	 */
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	/**
	 * @return the recordId
	 */
	public Long getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId
	 *            the recordId to set
	 */
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

}
