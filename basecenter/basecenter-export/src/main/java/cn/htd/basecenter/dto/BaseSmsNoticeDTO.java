package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class BaseSmsNoticeDTO implements Serializable {

	private static final long serialVersionUID = -7164378797532310726L;
	
	private Long id;
	
	private String noticeName;  //预警人名称
	
	private String noticePhone; //预警人电话
	
	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getNoticePhone() {
		return noticePhone;
	}

	public void setNoticePhone(String noticePhone) {
		this.noticePhone = noticePhone;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
