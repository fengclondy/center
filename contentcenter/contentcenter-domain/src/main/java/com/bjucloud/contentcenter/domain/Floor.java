package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.Date;

public class Floor implements Serializable {

	private static final long serialVersionUID = 1648331538269746097L;

	private Long id;

	private Long floorId;

	private Long navId;

	private Long contentId;

	private Date startTime;

	private Date endTime;

	private Long showBrand;

	private String picUrl;

	private String linkUrl;

	private Long FloorContentPicSubSortNum;

	private String name;

	private String navTemp;

	private String navTempSrc;

	private Long sortNum;

	private String status;

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

	public Long getFloorId() {
		return floorId;
	}

	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}

	public Long getNavId() {
		return navId;
	}

	public void setNavId(Long navId) {
		this.navId = navId;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
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

	public Long getShowBrand() {
		return showBrand;
	}

	public void setShowBrand(Long showBrand) {
		this.showBrand = showBrand;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Long getFloorContentPicSubSortNum() {
		return FloorContentPicSubSortNum;
	}

	public void setFloorContentPicSubSortNum(Long floorContentPicSubSortNum) {
		FloorContentPicSubSortNum = floorContentPicSubSortNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNavTemp() {
		return navTemp;
	}

	public void setNavTemp(String navTemp) {
		this.navTemp = navTemp;
	}

	public String getNavTempSrc() {
		return navTempSrc;
	}

	public void setNavTempSrc(String navTempSrc) {
		this.navTempSrc = navTempSrc;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		this.createName = createName;
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
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
