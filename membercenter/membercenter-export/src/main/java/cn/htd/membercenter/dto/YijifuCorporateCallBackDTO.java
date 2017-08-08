package cn.htd.membercenter.dto;

import java.io.Serializable;

public class YijifuCorporateCallBackDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;// 用户ID
	private String accountNo;// 资金账户 字符串(0-32)
	private String status;// 实名认证状态
	private String resultCode;// 返回CODE
	private String resultMessage;// 返回消息
	private String resultDetail;

	/**
	 * @return the resultDetail
	 */
	public String getResultDetail() {
		return resultDetail;
	}

	/**
	 * @param resultDetail
	 *            the resultDetail to set
	 */
	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
