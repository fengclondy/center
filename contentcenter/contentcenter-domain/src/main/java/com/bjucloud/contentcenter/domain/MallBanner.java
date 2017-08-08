package com.bjucloud.contentcenter.domain;

public class MallBanner {

	private Integer id;//
	private String bannerUrl;// 图片url
	private Double platformId;// 平台id
	private Integer sortNumber;// 排序序号
	private String created;// 创建时间
	private String modified;// 修改时间
	private String status;// 0:不可用 1：可用
	private String startTime;// 开始展示时间
	private String endTime;// 结束时间
	private String bannerLink;// 跳转链接
	private String title;//
	private Integer type;// 图片类型（位置）1:大轮播，2：小轮播，3：店铺的轮播 ,
	private String bgimgUrl;// 背景url
	private Integer themeId; // 主题id

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

	public Double getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Double platformId) {
		this.platformId = platformId;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBgimgUrl() {
		return bgimgUrl;
	}

	public void setBgimgUrl(String bgimgUrl) {
		this.bgimgUrl = bgimgUrl;
	}

	public String toString() {
		return "MallBanner [id=" + id + ", bannerUrl=" + bannerUrl + ", platformId=" + platformId + ", sortNumber="
				+ sortNumber + ", created=" + created + ", modified=" + modified + ", status=" + status + ", startTime="
				+ startTime + ", endTime=" + endTime + ", bannerLink=" + bannerLink + ", title=" + title + "type="
				+ type + "]";
	}

}
