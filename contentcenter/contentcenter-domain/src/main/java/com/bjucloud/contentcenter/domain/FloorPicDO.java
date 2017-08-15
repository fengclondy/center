package com.bjucloud.contentcenter.domain;

import java.io.Serializable;

/**
 * @Purpose 楼层导航图片DO
 * @author zf.zhang
 * @since 2017-3-11 17:22
 */
public class FloorPicDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941345816379535454L;
	
	
	//主键
	private Long id;
	//楼层导航ID
	private Long floorNavId;
	//楼层内容图片地址
	private String picUrl;
	//楼层内容图片指向连接地址
	private String linkUrl;
	//楼层内容图片显示顺序
	private int sortNum;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFloorNavId() {
		return floorNavId;
	}
	public void setFloorNavId(Long floorNavId) {
		this.floorNavId = floorNavId;
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
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	
	
}
