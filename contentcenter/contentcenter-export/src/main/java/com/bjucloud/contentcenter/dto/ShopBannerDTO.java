package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taolei on 2017/6/29.
 */
public class ShopBannerDTO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -5769927504226824754L;
	
	private Long id;
    private String name ;//轮播图标题名称
    private String picSrc;//图片地址
    private String picLink; //图片链接
    private Long sortNum;//显示顺序
    private Date startTimeEnabled;//生效开始时间
    private Date endTimeEnabled;//生效结束时间
    private int status;//启用状态：1启用；0未启用
    private int flag;//删除状态：1删除；0未删除
    private String shopId;//店铺id
    //活动渠道：1.超级老板APP，2.汇掌柜；多个以英文逗号分隔，如：1,2
    private String activityChannel;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public Date getStartTimeEnabled() {
        return startTimeEnabled;
    }

    public void setStartTimeEnabled(Date startTimeEnabled) {
        this.startTimeEnabled = startTimeEnabled;
    }

    public Date getEndTimeEnabled() {
        return endTimeEnabled;
    }

    public void setEndTimeEnabled(Date endTimeEnabled) {
        this.endTimeEnabled = endTimeEnabled;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

	public String getActivityChannel() {
		return activityChannel;
	}

	public void setActivityChannel(String activityChannel) {
		this.activityChannel = activityChannel;
	}
    
    
}
