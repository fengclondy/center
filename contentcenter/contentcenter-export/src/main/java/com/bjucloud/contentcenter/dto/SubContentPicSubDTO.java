package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

public class SubContentPicSubDTO implements Serializable{
    private static final long serialVersionUID = 6964713273850897462L;
    private Long id;

    private Long subContentId;// 城市站内容信息ID

    private String picUrl;// 图片地址

    private String linkUrl;// 指向连接地址

    private Long sortNum;// 显示顺序

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubContentId() {
        return subContentId;
    }

    public void setSubContentId(Long subContentId) {
        this.subContentId = subContentId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl == null ? null : linkUrl.trim();
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }
}