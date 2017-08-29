/**
 * 
 */
package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author admin
 *
 */
public class GenricReqDTO implements Serializable{

	private static final long serialVersionUID = -7569714466068742961L;

	@NotEmpty(message = "messageId不能为空")
	private String messageId = null;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
