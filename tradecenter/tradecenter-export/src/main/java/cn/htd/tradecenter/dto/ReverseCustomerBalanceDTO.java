/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName:    ReverseCustomerBalanceDTO.java
 * Author:      shihb
 * Date:        2018年1月22日
 * Description: 锁定客户帐存信息
 * History:     
 * shihb     2018年1月22日 1.0         创建
 */
package cn.htd.tradecenter.dto;

import java.io.Serializable;

/**
 * 锁定客户帐存信息
 */
public class ReverseCustomerBalanceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌编号
     */
    private String brandCode;
    /**
     * ERP一级类目代码
     */
    private String classCode;
    /**
     * 会员编号
     */
    private String customerCode;
    /**
     * 供应商编号
     */
    private String companyCode;
    /**
     * 锁定金额
     */
    private String chargeAmount;
    /**
     * 订单行编号
     */
    private String itemOrderNo;

    public ReverseCustomerBalanceDTO() {
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getItemOrderNo() {
        return itemOrderNo;
    }

    public void setItemOrderNo(String itemOrderNo) {
        this.itemOrderNo = itemOrderNo;
    }
}
