package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2017/1/20.
 */
public class HTDFloorNavDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long floorId;//楼层Id
    private String name;//名称
    private String navTemp; //导航模板
    private String navTempSrc;//导航模板样式
    private Long sortNum;//排序号
    private String status;//状态：1：上架 2：下架
    private Long createId;
    private String createName;
    private Date createTime;
    private Long modifyId;
    private String modifyName;
    private Date modifyTime;
    private String floorName; //楼层名称
    List<FloorContentPicSubDTO> picSubDTOs;

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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public List<FloorContentPicSubDTO> getPicSubDTOs() {
        return picSubDTOs;
    }

    public void setPicSubDTOs(List<FloorContentPicSubDTO> picSubDTOs) {
        this.picSubDTOs = picSubDTOs;
    }
}
