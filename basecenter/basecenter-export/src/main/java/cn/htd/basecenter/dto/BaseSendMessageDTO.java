package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class BaseSendMessageDTO implements Serializable {

	private static final long serialVersionUID = 5868139811544096007L;
	private Long id;
	private String title;// 标题
	private String address;// 邮件/手机 地址
	private String content;// 内容
	private String type;// 类型 2:短信 1：邮件
	private int isSend;// 是否已经发送 0：否 1：是
	private String sendResult;
	private Date createTime;// 创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

	public String getSendResult() {
		return sendResult;
	}

	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
