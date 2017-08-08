package cn.htd.membercenter.dto;

import java.io.Serializable;

public class BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 操作人编码
	public String operateId;
	// 操作人姓名
	public String operateName;
	// 操作类型
	public String operateType;

	public String operateTime;

	/**
	 * @return the operateId
	 */
	public String getOperateId() {
		return operateId;
	}

	/**
	 * @param operateId
	 *            the operateId to set
	 */
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	/**
	 * @return the operateName
	 */
	public String getOperateName() {
		return operateName;
	}

	/**
	 * @param operateName
	 *            the operateName to set
	 */
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	/**
	 * @return the operateType
	 */
	public String getOperateType() {
		return operateType;
	}

	/**
	 * @param operateType
	 *            the operateType to set
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	/**
	 * @return the operateTime
	 */
	public String getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 *            the operateTime to set
	 */
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
