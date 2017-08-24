/**
 * 
 */
package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;

/**
 * @author admin
 *
 */
public class GenricReqDTO implements Serializable{

	private static final long serialVersionUID = -7569714466068742961L;

	private String messageId = null;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
