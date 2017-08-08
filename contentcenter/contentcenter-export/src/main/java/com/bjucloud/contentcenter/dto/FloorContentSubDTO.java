package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2017/2/8.
 */
public class FloorContentSubDTO implements Serializable{
    private static final long serialVersionUID = -316329548553235474L;

    private Long id;
    private Long floorId;
    private Long brandId;
    private String brandName;
    private Long sortNum;

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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }
}
