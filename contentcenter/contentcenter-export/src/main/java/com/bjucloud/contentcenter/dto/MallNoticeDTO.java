package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MallNoticeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String title;

	private String content;

	private Integer status; // 公告状态

	private Integer isRecommend; // 是否置顶

	private Date created;

	private Date modified;

	private Integer sortNum;

	private Long platformId;
	private Integer noticeType;// 公告类型 1 文字公告 2 链接公告
	private String url;// 公告链接

	private Date createDtBegin;
	private Date createDtEnd;

	private Date publishDtBegin;
	private Date publishDtEnd;

	private Integer themeId; // 主题id
	private String themeName; // 主题名称
	private Integer themeStatus; // 主题状态
	private Integer themeType;// 主题类型
	private String themeUid;// 创建主题的用户id
	private Long cid;// 类目id
	private Long addressId;// 地区

	private Integer noticeStatus;

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getThemeUid() {
		return themeUid;
	}

	public void setThemeUid(String themeUid) {
		this.themeUid = themeUid;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public Integer getThemeStatus() {
		return themeStatus;
	}

	public void setThemeStatus(Integer themeStatus) {
		this.themeStatus = themeStatus;
	}

	public Integer getThemeType() {
		return themeType;
	}

	public void setThemeType(Integer themeType) {
		this.themeType = themeType;
	}

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

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Integer getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(Integer noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

}
