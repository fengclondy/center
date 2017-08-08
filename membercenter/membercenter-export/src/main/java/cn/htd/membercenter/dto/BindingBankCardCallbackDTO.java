package cn.htd.membercenter.dto;

import java.io.Serializable;

public class BindingBankCardCallbackDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;// 用户ID
	private String bindId;// 签约流水号
	private String bankId;// 银行英文简称
	private String bankName;// 银行中文全称
	private String bankCardName;// 银行卡产品名称
	private String bankCardTypeCode;// 银行卡类型编码
	private String bankCardTypeName;// 银行卡类型名称
	private String pactStatus;// 绑定状态
	private String resultCode;// 返回CODE
	private String resultMessage;// 返回消息

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

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardName() {
		return bankCardName;
	}

	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}

	public String getBankCardTypeCode() {
		return bankCardTypeCode;
	}

	public void setBankCardTypeCode(String bankCardTypeCode) {
		this.bankCardTypeCode = bankCardTypeCode;
	}

	public String getBankCardTypeName() {
		return bankCardTypeName;
	}

	public void setBankCardTypeName(String bankCardTypeName) {
		this.bankCardTypeName = bankCardTypeName;
	}

	public String getPactStatus() {
		return pactStatus;
	}

	public void setPactStatus(String pactStatus) {
		this.pactStatus = pactStatus;
	}

}
