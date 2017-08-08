package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class SubContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long subAdId;

	private String pic1Url;

	private String link1Url;

	private String pic2Url;

	private String link2Url;

	private String pic3Url;

	private String link3Url;

	private Date startTime;

	private Date endTime;

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

	public Long getSubAdId() {
		return subAdId;
	}

	public void setSubAdId(Long subAdId) {
		this.subAdId = subAdId;
	}

	public String getPic1Url() {
		return pic1Url;
	}

	public void setPic1Url(String pic1Url) {
		this.pic1Url = pic1Url == null ? null : pic1Url.trim();
	}

	public String getLink1Url() {
		return link1Url;
	}

	public void setLink1Url(String link1Url) {
		this.link1Url = link1Url == null ? null : link1Url.trim();
	}

	public String getPic2Url() {
		return pic2Url;
	}

	public void setPic2Url(String pic2Url) {
		this.pic2Url = pic2Url == null ? null : pic2Url.trim();
	}

	public String getLink2Url() {
		return link2Url;
	}

	public void setLink2Url(String link2Url) {
		this.link2Url = link2Url == null ? null : link2Url.trim();
	}

	public String getPic3Url() {
		return pic3Url;
	}

	public void setPic3Url(String pic3Url) {
		this.pic3Url = pic3Url == null ? null : pic3Url.trim();
	}

	public String getLink3Url() {
		return link3Url;
	}

	public void setLink3Url(String link3Url) {
		this.link3Url = link3Url == null ? null : link3Url.trim();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
		this.createName = createName == null ? null : createName.trim();
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
		this.modifyName = modifyName == null ? null : modifyName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}