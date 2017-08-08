package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MallAdDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;// 主键ID
	private String adSrc;// 图片URL
	private String adURL;// 广告指向链接
	private String title;// 广告标题
	private String adSubTitle;//广告副标题
	private String adKeyword;//关键字
	private Integer sortNumber;// 排序号
	private Date created;// 创建时间
	private Date modified;// 修改时间
	private Integer status;// 是否可用
	private Date startTime;// 定时发布时间
	private Date endTime;// 结束时间
	private Integer adType;// 广告类型 1，轮播下广告位 2，登录 3 头部广告 4 类目广告
	private List<Integer> adTypeList;
	private Integer close;// 是否可关闭 1 可关闭 2 不可关闭
	private Long cid;// 类目id
	private Integer themeId; // 主题id
	private String themeName; // 主题名称
	private Integer themeStatus; // 主题状态
	private Integer themeType;// 主题类型
	private String themeUId; // 创建者id
	private Long categoryId;// 主题类目id
	private Long addressId;// 主题地区
	private Double platformId;// 平台ID


	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getThemeUId() {
		return themeUId;
	}

	public void setThemeUId(String themeUId) {
		this.themeUId = themeUId;
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

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public Integer getClose() {
		return close;
	}

	public void setClose(Integer close) {
		this.close = close;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdSrc() {
		return adSrc;
	}

	public void setAdSrc(String adSrc) {
		this.adSrc = adSrc;
	}

	public String getAdURL() {
		return adURL;
	}

	public void setAdURL(String adURL) {
		this.adURL = adURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	public String getAdSubTitle() {
		return adSubTitle;
	}

	public void setAdSubTitle(String adSubTitle) {
		this.adSubTitle = adSubTitle;
	}

	public String getAdKeyword() {
		return adKeyword;
	}

	public void setAdKeyword(String adKeyword) {
		this.adKeyword = adKeyword;
	}

	public List<Integer> getAdTypeList() {
		return adTypeList;
	}

	public void setAdTypeList(List<Integer> adTypeList) {
		this.adTypeList = adTypeList;
	}

	public Double getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Double platformId) {
		this.platformId = platformId;
	}
}
