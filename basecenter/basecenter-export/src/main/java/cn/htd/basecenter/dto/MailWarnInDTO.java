package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.basecenter.enums.EmailMimeTypeEnum;

public class MailWarnInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3761332412551296271L;
	
	/**
	 * 收件人邮箱
	 */
	private String recevierMail;
	
	/**
	 * 邮件类型：纯文本 HTMl类型
	 * 
	 * @see EmailMimeTypeEnum
	 */
	private  String emailMimeType;
	
	/**
	 * 邮件主题
	 * 
	 */
	private String emailTitle;
	
	private String senderMail;
	
	/**
	 * 邮件表格元素内容
	 * 
	 */
	private List<MailWarnRow> rowList;

	public String getRecevierMail() {
		return recevierMail;
	}

	public void setRecevierMail(String recevierMail) {
		this.recevierMail = recevierMail;
	}

	public String getEmailMimeType() {
		return emailMimeType;
	}

	public void setEmailMimeType(String emailMimeType) {
		this.emailMimeType = emailMimeType;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public List<MailWarnRow> getRowList() {
		return rowList;
	}

	public void setRowList(List<MailWarnRow> rowList) {
		this.rowList = rowList;
	}

	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}
	
	
	
}
