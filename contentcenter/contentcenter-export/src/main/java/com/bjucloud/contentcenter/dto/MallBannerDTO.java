package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

public class MallBannerDTO implements Serializable {

	private static final long serialVersionUID = 3110761272079286567L;

	private Integer id;//
	private String bannerUrl;// 图片url
	private String bannerLink;// 跳转链接
	private Long platformId;// 平台id
	private String title;// 轮播名称
	private Integer sortNumber;// 排序序号
	private String created;// 创建时间
	private String modified;// 修改时间
	private String status;// 0:不可用 1：可用
	private String timeFlag;// 0：创建时间 1：发布时间
	private String startTime;// 开始展示时间
	private String endTime;// 结束时间
	private String type;// 图片类型，定义图片位置
	private String bgimgUrl;// 背景圖url
	private Integer themeId;// 主题id
	private String themeName; // 主题名称
	private Integer themeStatus; // 主题状态
	private Integer themeType;// 主题类型
	private String themeUId; // 创建者id
	private Long cid;// 类目id
	private Long addressId;// 地区

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
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

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String toString() {
		return "MallBannerDTO [id=" + id + ", bannerUrl=" + bannerUrl + ", bannerLink=" + bannerLink + ", title="
				+ title + ", sortNumber=" + sortNumber + ", created=" + created + ", modified=" + modified + ", status="
				+ status + ", startTime=" + startTime + ", endTime=" + endTime + ",timeFlag=" + timeFlag + "type" + type
				+ "]";
	}

}
