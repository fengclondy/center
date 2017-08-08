package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: MallAdvertise domain类
 * </p>
 */
public class MallAdvertise implements Serializable{

	private Long id;//
	private Integer sortNum;// 序号
	private Integer adType;// 广告类型 1，轮播下广告位 2，登录 3 头部广告 4 类目广告
	private String adTitle;// 广告标题
	private String adSubTitle;//广告副标题
	private String adKeyword;//关键字
	private String adUrl;// 广告指向链接
	private String adSrc;// 图片链接
	private Date startTime;// 定时发布时间
	private Date endTime;// 定时结束时间
	private Date created;// 创建时间
	private Date modified;// 修改时间
	private Double platformId;// 平台ID
	private Integer status;// 是否可用 1 发布 0 未发布
	private Integer timeFlag;// 时间标记 1 创建时间 2 发布时间 3结束时间
	private Integer close; // 是否可关闭 1 可关闭 2 不可关闭
	private Long cid; // 类目id （一级类目）
	private Integer themeId; // 主题id

	public Integer getThemeId() {
		return themeId;
	}

	public void setTheme_id(Integer themeId) {
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

	// setter and getter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public String getAdSrc() {
		return adSrc;
	}

	public void setAdSrc(String adSrc) {
		this.adSrc = adSrc;
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

	public Double getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Double platformId) {
		this.platformId = platformId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
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

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}
}
