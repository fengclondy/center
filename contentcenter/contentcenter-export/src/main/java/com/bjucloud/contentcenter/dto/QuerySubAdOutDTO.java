package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2017/3/13.
 */
public class QuerySubAdOutDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long subAdId;
    private String subAdName;
    private Long subId;
    private String subName;
    private String navTemp;
    private Integer navCount;

    public Long getSubAdId() {
        return subAdId;
    }

    public void setSubAdId(Long subAdId) {
        this.subAdId = subAdId;
    }

    public String getSubAdName() {
        return subAdName;
    }

    public void setSubAdName(String subAdName) {
        this.subAdName = subAdName;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getNavTemp() {
        return navTemp;
    }

    public void setNavTemp(String navTemp) {
        this.navTemp = navTemp;
    }

    public Integer getNavCount() {
        return navCount;
    }

    public void setNavCount(Integer navCount) {
        this.navCount = navCount;
    }
}
