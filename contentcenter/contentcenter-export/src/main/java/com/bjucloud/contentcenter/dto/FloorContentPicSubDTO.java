package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2017/2/8.
 */
public class FloorContentPicSubDTO implements Serializable {
    private static final long serialVersionUID = -316329548553235474L;

    private Long id;
    private Long floorNavId;//导航Id
    private String picUrl;
    private String linkUrl;
    private Long sortNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public Long getFloorNavId() {
        return floorNavId;
    }

    public void setFloorNavId(Long floorNavId) {
        this.floorNavId = floorNavId;
    }
}
