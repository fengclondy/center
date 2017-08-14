package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;

public class GenricResDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1186301269812058781L;

	private String messageID = null;
	
	private String responseCode = null;
	
	private String responseMsg = null;

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	
	
}
