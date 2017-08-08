package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告信息
 */
public class PlacardInfo implements Serializable {

	private static final long serialVersionUID = 6593096669524893752L;

	/**
	 * 公告ID
	 */
	private Long placardId;

	/**
	 * 公告编号
	 */
	private String placardCode;

	/**
	 * 主题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 发布时间
	 */
	private Date publishTime;

	/**
	 * 备注
	 */
	private String comment;

	/**
	 * 图片附件
	 */
	private String picAttachment;

	/**
	 * 是否有超链接
	 */
	private int hasUrl;
	/**
	 * 超链接
	 */
	private String url;
	/**
	 * 是否已读标记
	 */
	private int hasRead;

	public Long getPlacardId() {
		return placardId;
	}

	public void setPlacardId(Long placardId) {
		this.placardId = placardId;
	}

	public String getPlacardCode() {
		return placardCode;
	}

	public void setPlacardCode(String placardCode) {
		this.placardCode = placardCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPicAttachment() {
		return picAttachment;
	}

	public void setPicAttachment(String picAttachment) {
		this.picAttachment = picAttachment;
	}

	public int getHasUrl() {
		return hasUrl;
	}

	public void setHasUrl(int hasUrl) {
		this.hasUrl = hasUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getHasRead() {
		return hasRead;
	}

	public void setHasRead(int hasRead) {
		this.hasRead = hasRead;
	}

}
