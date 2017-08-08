package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2017/2/7.
 */
public class FloorQueryOutDTO implements Serializable{
    private static final long serialVersionUID = 9193906134094015640L;

    private Long floorId;//楼层Id
    private String floorName;//楼层名称
    private String floorStatus;//楼层状态
    private Long navId;//导航Id
    private String navName;//导航名称
    private String navStatus;//导航状态
    private String navTemp;//导航模板
    private String createName;//创建人
    private Integer tempCount;//模板坑位数
    private FloorContentDTO floorContentDTO;

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorStatus() {
        return floorStatus;
    }

    public void setFloorStatus(String floorStatus) {
        this.floorStatus = floorStatus;
    }

    public Long getNavId() {
        return navId;
    }

    public void setNavId(Long navId) {
        this.navId = navId;
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }

    public String getNavStatus() {
        return navStatus;
    }

    public void setNavStatus(String navStatus) {
        this.navStatus = navStatus;
    }

    public String getNavTemp() {
        return navTemp;
    }

    public void setNavTemp(String navTemp) {
        this.navTemp = navTemp;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Integer getTempCount() {
        return tempCount;
    }

    public void setTempCount(Integer tempCount) {
        this.tempCount = tempCount;
    }

    public FloorContentDTO getFloorContentDTO() {
        return floorContentDTO;
    }

    public void setFloorContentDTO(FloorContentDTO floorContentDTO) {
        this.floorContentDTO = floorContentDTO;
    }
}
