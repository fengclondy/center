package cn.htd.common.dto;

import java.io.Serializable;

public class GenricResDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8597399058022448121L;

	/**
	 * 返回码
	 */
	private String responseCode = null;
	
	/**
	 * 返回码
	 */
	private String reponseMsg = null;
	
	/**
	 * 调用消息id
	 */
	private String messageId = null;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getReponseMsg() {
		return reponseMsg;
	}

	public void setReponseMsg(String reponseMsg) {
		this.reponseMsg = reponseMsg;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
