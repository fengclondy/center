package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tangjiayong on 2017/9/7.
 */
public class SeckillOrderReqDTO implements Serializable{

     private static final long serialVersionUID = 1L;

     private String orderNo;//订单编号

     private String productName;//商品名称

     private String totalMoeny;//订单金额

     private String fanCode;//粉丝账号

     private String memberBossName;//会员店老板姓名

     private String bossTelphone;//老板联系方式

     private String memberName;//会员店名称

     private String memberAddress;//会员店地址

     private String orderStatus;//订单状态 1:待支付；2：待发货；3：待确认 4：已完成；5已关闭

     private Date orderTime;//下单时间

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTotalMoeny() {
        return totalMoeny;
    }

    public void setTotalMoeny(String totalMoeny) {
        this.totalMoeny = totalMoeny;
    }

    public String getFanCode() {
        return fanCode;
    }

    public void setFanCode(String fanCode) {
        this.fanCode = fanCode;
    }

    public String getMemberBossName() {
        return memberBossName;
    }

    public void setMemberBossName(String memberBossName) {
        this.memberBossName = memberBossName;
    }

    public String getBossTelphone() {
        return bossTelphone;
    }

    public void setBossTelphone(String bossTelphone) {
        this.bossTelphone = bossTelphone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
