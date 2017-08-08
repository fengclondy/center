package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2017/2/8.
 */
public class FloorContentDTO implements Serializable {
    private static final long serialVersionUID = -316329548553235474L;

    private Long id;
    private Long navId;//导航Id
    private Date startTime;//生效开始时间
    private Date endTime;//生效结束时间
    private Long showBrand;//是否展示品牌
    private Long createId;
    private String createName;
    private Date createTime;
    private Long modifyId;
    private String modifyName;
    private Date modifyTime;
    private List<FloorContentPicSubDTO> picSubDTOs;
    private List<FloorContentSubDTO> brandDTOs;
    private String status;//状态


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNavId() {
        return navId;
    }

    public void setNavId(Long navId) {
        this.navId = navId;
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

    public List<FloorContentPicSubDTO> getPicSubDTOs() {
        return picSubDTOs;
    }

    public void setPicSubDTOs(List<FloorContentPicSubDTO> picSubDTOs) {
        this.picSubDTOs = picSubDTOs;
    }

    public List<FloorContentSubDTO> getBrandDTOs() {
        return brandDTOs;
    }

    public void setBrandDTOs(List<FloorContentSubDTO> brandDTOs) {
        this.brandDTOs = brandDTOs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
