/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName:    RebateDTO.java
 * Author:      shihb
 * Date:        2018年1月22日
 * Description: 返利单信息
 * History:     
 * shihb     2018年1月22日 1.0         创建
 */
package cn.htd.tradecenter.dto;

import java.io.Serializable;

/**
 * 返利单信息
 */
public class RebateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返利单号
     */
    private String rebateNo;
    /**
     * 返利名称
     */
    private String rebateName;
    /**
     * 返利类型
     */
    private String rebateType;
    /**
     * 返利代码
     */
    private String rebateCode;
    /**
     * 返利金额
     */
    private String rebatePrice;
    /**
     * 创建时间
     */
    private String createTime;

    public RebateDTO() {
    }

    public String getRebateNo() {
        return rebateNo;
    }

    public void setRebateNo(String rebateNo) {
        this.rebateNo = rebateNo;
    }

    public String getRebateName() {
        return rebateName;
    }

    public void setRebateName(String rebateName) {
        this.rebateName = rebateName;
    }

    public String getRebateType() {
        return rebateType;
    }

    public void setRebateType(String rebateType) {
        this.rebateType = rebateType;
    }

    public String getRebateCode() {
        return rebateCode;
    }

    public void setRebateCode(String rebateCode) {
        this.rebateCode = rebateCode;
    }

    public String getRebatePrice() {
        return rebatePrice;
    }

    public void setRebatePrice(String rebatePrice) {
        this.rebatePrice = rebatePrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
