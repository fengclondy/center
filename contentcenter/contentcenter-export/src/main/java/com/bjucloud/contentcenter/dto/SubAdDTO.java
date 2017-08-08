package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

public class SubAdDTO implements Serializable{
    private static final long serialVersionUID = -7122070816660888634L;
    private Long id;

    private String subId;// 城市站

    private String name;// 专场名称

    private String navTemp;// 导航魔板

    private String navTempSrc;// 导航模板样式

    private String status;// 状态

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    // add by lh 20170220 start
    private String subName;// 地区名称
    // add by lh 20170220 end

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId == null ? null : subId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNavTemp() {
        return navTemp;
    }

    public void setNavTemp(String navTemp) {
        this.navTemp = navTemp == null ? null : navTemp.trim();
    }

    public String getNavTempSrc() {
        return navTempSrc;
    }

    public void setNavTempSrc(String navTempSrc) {
        this.navTempSrc = navTempSrc == null ? null : navTempSrc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
        this.createName = createName == null ? null : createName.trim();
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
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}