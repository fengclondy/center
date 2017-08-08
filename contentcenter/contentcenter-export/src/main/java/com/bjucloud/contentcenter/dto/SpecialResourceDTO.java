package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by thinkpad on 2017/2/13.
 */
public class SpecialResourceDTO implements Serializable {
    private static final long serialVersionUID = 4960467303990035458L;

    private Long id;
    private String name;//专题页面名称
    private String memo;//描述
    private String linkUrl;//专题页面附件
    private String status;//状态 1：上架 0：下架
    private Long createId;
    private String createName;
    private Date createTime;
    private Long modifyId;
    private String modifyName;
    private Date modifyTime;

    private Date createStart;
    private Date createEnd;
    private Date modifyStart;
    private Date modifyEnd;
    private String pageType;
    private String htmlStr;
    
    public String getHtmlStr() {
		return htmlStr;
	}

	public void setHtmlStr(String htmlStr) {
		this.htmlStr = htmlStr;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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

    public Date getCreateStart() {
        return createStart;
    }

    public void setCreateStart(Date createStart) {
        this.createStart = createStart;
    }

    public Date getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(Date createEnd) {
        this.createEnd = createEnd;
    }

    public Date getModifyStart() {
        return modifyStart;
    }

    public void setModifyStart(Date modifyStart) {
        this.modifyStart = modifyStart;
    }

    public Date getModifyEnd() {
        return modifyEnd;
    }

    public void setModifyEnd(Date modifyEnd) {
        this.modifyEnd = modifyEnd;
    }
}
