package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

public class MallBannerInDTO implements Serializable {

	private static final long serialVersionUID = 3110761272079286567L;

	private Integer id;//
	private String bannerUrl;// 图片url
	private String bannerLink;// 跳转链接
	private String title;// 轮播名称
	private Integer sortNumber;// 排序序号
	private String timeFlag;// 时间标记
	private String startTime;// 开始展示时间
	private String endTime;// 结束时间
	private String status; // 轮播状态 轮播添加.即时发布时，直接添加为上架轮播 定时发布时，添加的轮播是非上架状态 上架状态
							// 为1 下架状态为0
	private String type;// 图片类型，定义图片位置
	private String bgimgUrl;// 大輪播背景圖
	private Integer themeId;// 主题id
	private String themeName; // 主题名称
	private Integer themeStatus; // 主题状态
	private Integer themeType;// 主题类型
	private String themeUId; // 创建者id
	private Double platformId;// 平台id


	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public String getBannerLink() {
		return bannerLink;
	}

	public void setBannerLink(String bannerLink) {
		this.bannerLink = bannerLink;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBgimgUrl() {
		return bgimgUrl;
	}

	public void setBgimgUrl(String bgimgUrl) {
		this.bgimgUrl = bgimgUrl;
	}

	public Double getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Double platformId) {
		this.platformId = platformId;
	}

	public String toString() {
		return "MallBannerInDTO [id=" + id + ", bannerUrl=" + bannerUrl + ", bannerLink=" + bannerLink + ", title="
				+ title + ", sortNumber=" + sortNumber + ", timeFlag=" + timeFlag + ", startTime=" + startTime
				+ ", endTime=" + endTime + "type=" + type + "]";
	}

}
