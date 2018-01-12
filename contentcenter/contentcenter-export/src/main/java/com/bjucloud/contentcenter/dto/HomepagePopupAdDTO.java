package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class HomepagePopupAdDTO implements Serializable {

    private static final long serialVersionUID = 3527613047270244097L;

    private Long id;

    @NotEmpty(message = "广告名称不能为空")
    private String adName;

    @NotEmpty(message = "图片地址不能为空")
    private String picUrl;

    private String linkUrl;

    @NotNull(message = "展示开始时间不能为空")
    private Date startTime;

    @NotNull(message = "展示结束时间不能为空")
    @Future(message = "展示结束时间不能为过去时间")
    private Date endTime;

    private String terminallTypeStr;

    @NotNull(message = "操作人ID不能为空")
    private Long operatorId;

    @NotEmpty(message = "操作人名称不能为空")
    private String operatorName;

    private Date operateTime;

    @NotNull(message = "展示终端必须选择")
    private List<String> terminalTypeList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
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

    public String getTerminallTypeStr() {
        return terminallTypeStr;
    }

    public void setTerminallTypeStr(String terminallTypeStr) {
        this.terminallTypeStr = terminallTypeStr;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public List<String> getTerminalTypeList() {
        return terminalTypeList;
    }

    public void setTerminalTypeList(List<String> terminalTypeList) {
        this.terminalTypeList = terminalTypeList;
    }
}
