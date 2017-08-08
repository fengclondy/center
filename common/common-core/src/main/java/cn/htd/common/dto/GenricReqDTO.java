package cn.htd.common.dto;

import java.io.Serializable;

public class GenricReqDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2967702300648209828L;
	private String messageId = null;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
