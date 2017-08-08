package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubContentDTO implements Serializable{
    private static final long serialVersionUID = 3897635786008522410L;
    private Long id;

    private String subId;// 城市站

    private String subName;// 城市站名称

    private Long subAdId;// 城市站广告位ID

    private Date startTime;// 生效开始时间

    private Date endTime;// 生效结束时间

    private String startTimetemp;//生效开始时间  接收页面传值使用

    private String endTimetemp;//生效结束时间     接收页面传值使用

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    // add by lh 2017-01-22 start
    private String name;// 专场名称
    private SubAdDTO subAdDTO;// 城市站广告位
    private List<SubContentSubDTO> subContentSubDTOs = new ArrayList<SubContentSubDTO>();// 城市站内容信息子表
    private List<SubContentPicSubDTO> subContentPicSubDTOs = new ArrayList<SubContentPicSubDTO>();// 城市站内容图片字表
    private String picUrls;
    private String linkUrls;

    // add by lh 2017-01-22 end

    public SubAdDTO getSubAdDTO() {
        return subAdDTO;
    }

    public void setSubAdDTO(SubAdDTO subAdDTO) {
        this.subAdDTO = subAdDTO;
    }

    public List<SubContentSubDTO> getSubContentSubDTOs() {
        return subContentSubDTOs;
    }

    public void setSubContentSubDTOs(List<SubContentSubDTO> subContentSubDTOs) {
        this.subContentSubDTOs = subContentSubDTOs;
    }

    public String getStartTimetemp() {
        return startTimetemp;
    }

    public void setStartTimetemp(String startTimetemp) {
        this.startTimetemp = startTimetemp;
    }

    public String getEndTimetemp() {
        return endTimetemp;
    }

    public void setEndTimetemp(String endTimetemp) {
        this.endTimetemp = endTimetemp;
    }

    public List<SubContentPicSubDTO> getSubContentPicSubDTOs() {
        return subContentPicSubDTOs;
    }

    public void setSubContentPicSubDTOs(List<SubContentPicSubDTO> subContentPicSubDTOs) {
        this.subContentPicSubDTOs = subContentPicSubDTOs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName == null ? null : subName.trim();
    }

    public Long getSubAdId() {
        return subAdId;
    }

    public void setSubAdId(Long subAdId) {
        this.subAdId = subAdId;
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

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    public String getLinkUrls() {
        return linkUrls;
    }

    public void setLinkUrls(String linkUrls) {
        this.linkUrls = linkUrls;
    }
}