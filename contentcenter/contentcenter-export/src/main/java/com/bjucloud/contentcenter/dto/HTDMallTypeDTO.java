package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Date;

/**
 * Created by thinkpad on 2017/2/15.
 */
public class HTDMallTypeDTO implements Serializable{

    private static final long serialVersionUID = 4960467303990035458L;

    private Long id;
    private String name;
    private String iconUrl;
    private String status;
    private Long createId;
    private String createName;
    private Date createTime;
    private Long modifyId;
    private String modifyName;
    private Date modifyTime;

    private List<HTDMallTypeSubDTO> mallTypeSubDTOs = new ArrayList<HTDMallTypeSubDTO>();

    public List<HTDMallTypeSubDTO> getMallTypeSubDTOs() {
        return mallTypeSubDTOs;
    }

    public void setMallTypeSubDTOs(List<HTDMallTypeSubDTO> mallTypeSubDTOs) {
        this.mallTypeSubDTOs = mallTypeSubDTOs;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
