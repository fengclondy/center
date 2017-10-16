/**
 * 
 */
package cn.htd.marketcenter.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ly
 *
 */
public class GenricReqDTO {
	
	@NotEmpty(message = "messageId不能为空")
	private String messageId = null;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
