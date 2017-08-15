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

	/**
	 * 
	 */
	private static final long serialVersionUID = -7569714466068742961L;

	private String messageID = null;

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
}
