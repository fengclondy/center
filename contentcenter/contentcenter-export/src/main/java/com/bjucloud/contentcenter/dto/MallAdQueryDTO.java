package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MallAdQueryDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer adType;// 广告类型 暂时前台写死：1，轮播下广告位 2，登录 3 头部广告 4 类目广告
	private List<Integer> adTypeList;
	private String adTitle;// 广告标题
	private String adURL;
	private Integer timeFlag;// 时间标记 1 创建时间 2 发布时间 3结束时间
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private Integer status; // 状态 1 发布 0 未发布
	private Long cid; // 类目ID
	private Integer themeId;// 主题id
	private String themeName; // 主题名称
	private Integer themeStatus; // 主题状态
	private Integer themeType;// 主题类型
	private String themeUId; // 创建者id
	private Long addressId;// 地区
	private Double platformId;// 平台ID

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public Integer getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getAdURL() {
		return adURL;
	}

	public void setAdURL(String adURL) {
		this.adURL = adURL;
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
