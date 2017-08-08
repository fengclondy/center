package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [公告实体类]
 * </p>
 */
public class Notice implements Serializable {

	private static final long serialVersionUID = -795669671851761746L;

	/**
	 * 公告id
	 */
	private Long id;

	/**
	 * 公告标题
	 */
	private String title;

	/**
	 * 公告内容
	 */
	private String content;

	/**
	 * 公告状态
	 */
	private Integer status;

	/**
	 * 是否置顶 0 未置顶 1置顶
	 */
	private Integer isRecommend;

	/**
	 * 创建时间
	 */
	private Date created;

	/**
	 * 修改时间
	 */
	private Date modified;

	/**
	 * 排序号
	 */
	private Integer sortNum;

	/**
	 * 店铺ID
	 */
	private Long platformId;

	private Integer noticeType;// 公告类型 1 文字公告 2 链接公告
	private String url;// 公告链接

	private Date createDtBegin;
	private Date createDtEnd;

	private Date publishDtBegin;
	private Date publishDtEnd;

	private Integer themeId; // 主题id

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public Date getCreateDtBegin() {
		return createDtBegin;
	}

	public void setCreateDtBegin(Date createDtBegin) {
		this.createDtBegin = createDtBegin;
	}

	public Date getCreateDtEnd() {
		return createDtEnd;
	}

	public void setCreateDtEnd(Date createDtEnd) {
		this.createDtEnd = createDtEnd;
	}

	public Date getPublishDtBegin() {
		return publishDtBegin;
	}

	public void setPublishDtBegin(Date publishDtBegin) {
		this.publishDtBegin = publishDtBegin;
	}

	public Date getPublishDtEnd() {
		return publishDtEnd;
	}

	public void setPublishDtEnd(Date publishDtEnd) {
		this.publishDtEnd = publishDtEnd;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

}
