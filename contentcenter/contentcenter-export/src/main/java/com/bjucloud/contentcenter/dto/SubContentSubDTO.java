package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

public class SubContentSubDTO implements Serializable {
    private static final long serialVersionUID = -7449171470387540431L;
    private Long id;

    private Long subContentId;// 城市站内容信息ID

    private String sellerName;// 供应商名称

    private String sellerCode;// 供应商编码

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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode == null ? null : sellerCode.trim();
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }
}