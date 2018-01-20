package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class QueryBatchAddItemAuthOutDTO implements Serializable {
    // 平台公司名称
    private String companyName;
    // 平台公司编码
    private String companyCode;
    // 是否开通 0：未开通 1：已开通
    private Integer isOpen;
    //开通时间
    private Date createTime;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
