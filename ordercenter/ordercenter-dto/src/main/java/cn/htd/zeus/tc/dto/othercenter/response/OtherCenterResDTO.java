/**
 * 
 */
package cn.htd.zeus.tc.dto.othercenter.response;

import java.io.Serializable;

/*
 * 用来包装其他中心的返回码
 * @author 张丁
 *
 */
public class OtherCenterResDTO<T> implements Serializable {

	private static final long serialVersionUID = 6470740170411520107L;

	/**
	 * 返回码
	 */
	private String otherCenterResponseCode = null;

	/**
	 * 返回码
	 */
	private String otherCenterResponseMsg = null;

	/**
	 * 调用消息id
	 */
	private String messageId = null;

	/*
	 * 用来存储其他中心返回的结果
	 */
	private T otherCenterResult;

	public String getOtherCenterResponseCode() {
		return otherCenterResponseCode;
	}

	public void setOtherCenterResponseCode(String otherCenterResponseCode) {
		this.otherCenterResponseCode = otherCenterResponseCode;
	}

	public String getOtherCenterResponseMsg() {
		return otherCenterResponseMsg;
	}

	public void setOtherCenterResponseMsg(String otherCenterResponseMsg) {
		this.otherCenterResponseMsg = otherCenterResponseMsg;
	}

	public T getOtherCenterResult() {
		return otherCenterResult;
	}

	public void setOtherCenterResult(T otherCenterResult) {
		this.otherCenterResult = otherCenterResult;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
