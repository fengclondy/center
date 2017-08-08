package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 邮件提醒表格的行
 * 
 * @author zhangxiaolong
 *
 */
public class MailWarnRow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -955428886885065212L;
	
	List<MailWarnColumn> mailWarnColumnList;

	public List<MailWarnColumn> getMailWarnColumnList() {
		return mailWarnColumnList;
	}

	public void setMailWarnColumnList(List<MailWarnColumn> mailWarnColumnList) {
		this.mailWarnColumnList = mailWarnColumnList;
	}
	

}
