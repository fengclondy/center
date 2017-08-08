package cn.htd.basecenter.dto;

import java.io.Serializable;
/**
 * 邮件提醒表格中的单元格
 * 
 * @author zhangxiaolong
 *
 */
public class MailWarnColumn implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4178033299966505653L;
	private Integer index;
	private String value;
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
