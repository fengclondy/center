package cn.htd.marketcenter.dto;

import java.io.Serializable;

public class TimelimitedSkuCountDTO implements Serializable{

    private Integer validSkuCount = 0;

    private Integer invalidSkuCount = 0;

    public Integer getValidSkuCount() {
        return validSkuCount;
    }

    public void setValidSkuCount(Integer validSkuCount) {
        this.validSkuCount = validSkuCount;
    }

    public Integer getInvalidSkuCount() {
        return invalidSkuCount;
    }

    public void setInvalidSkuCount(Integer invalidSkuCount) {
        this.invalidSkuCount = invalidSkuCount;
    }
}
