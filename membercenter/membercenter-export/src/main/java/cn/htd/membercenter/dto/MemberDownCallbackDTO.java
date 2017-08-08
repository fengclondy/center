package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberDownCallbackDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String memberCode;
	private String merchOrderNo;
	private String erpMemberCode;
	private String payMemberCode;
	private int erpResult;
	private String erpErrormessage;
	private int payResult;
	private String payErrormessage;
	private Date modifyTime;
	private String status;
	private String syncKey;

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getErpMemberCode() {
		return erpMemberCode;
	}

	public void setErpMemberCode(String erpMemberCode) {
		this.erpMemberCode = erpMemberCode;
	}

	public String getPayMemberCode() {
		return payMemberCode;
	}

	public void setPayMemberCode(String payMemberCode) {
		this.payMemberCode = payMemberCode;
	}

	public int getErpResult() {
		return erpResult;
	}

	public void setErpResult(int erpResult) {
		this.erpResult = erpResult;
	}

	public String getErpErrormessage() {
		return erpErrormessage;
	}

	public void setErpErrormessage(String erpErrormessage) {
		this.erpErrormessage = erpErrormessage;
	}

	public int getPayResult() {
		return payResult;
	}

	public void setPayResult(int payResult) {
		this.payResult = payResult;
	}

	public String getPayErrormessage() {
		return payErrormessage;
	}

	public void setPayErrormessage(String payErrormessage) {
		this.payErrormessage = payErrormessage;
	}

	/**
	 * @return the merchOrderNo
	 */
	public String getMerchOrderNo() {
		return merchOrderNo;
	}

	/**
	 * @param merchOrderNo
	 *            the merchOrderNo to set
	 */
	public void setMerchOrderNo(String merchOrderNo) {
		this.merchOrderNo = merchOrderNo;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the syncKey
	 */
	public String getSyncKey() {
		return syncKey;
	}

	/**
	 * @param syncKey the syncKey to set
	 */
	public void setSyncKey(String syncKey) {
		this.syncKey = syncKey;
	}

}
