package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by thinkpad on 2016/12/20.
 */
public class BaseValueModelDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long sellerId; // 卖家ID
	private String periods; // 周期
	private String subject;// 科目名称
	private String value; // 价值
	private Date createTime;// 创建时间
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
