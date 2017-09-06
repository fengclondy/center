package cn.htd.zeus.tc.dto.response;

import cn.htd.zeus.tc.dto.resquest.GenricReqDTO;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 17/9/4.
 */
public class OrderDayAmountResDTO extends GenricReqDTO implements Serializable {
    //日期
    private String day;
    //金额
    private String amount;
    //订单编号
    private String orderNo;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
