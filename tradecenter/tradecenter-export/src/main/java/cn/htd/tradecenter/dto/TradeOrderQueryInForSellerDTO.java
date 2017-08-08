package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by thinkpad on 2017/1/16.
 */

//订单中心查询条件
public class TradeOrderQueryInForSellerDTO implements Serializable {

    private static final long serialVersionUID = -825510315914028295L;
    // -------------------------订单列表查询,输入条件, start-------------------
    private String orderNo;// 订单号
    private Date createOrderTime;// 下单时间
    private Date createStart;// 下单开始时间
    private Date createEnd;// 下单结束时间
    private String goodsName;//商品名
    private String orderStatus;// 订单状态
    private String[] orderStatuss;//订单状态lsit
    private Long shopId;//店铺id
    private String buyerName;//会员名称
    private String payStatus;//支付状态
    private Integer isCancelOrder;//是否是取消订单 0:未取消，1：已取消

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateOrderTime() {
        return createOrderTime;
    }

    public void setCreateOrderTime(Date createOrderTime) {
        this.createOrderTime = createOrderTime;
    }

    public Date getCreateStart() {
        return createStart;
    }

    public void setCreateStart(Date createStart) {
        this.createStart = createStart;
    }

    public Date getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(Date createEnd) {
        this.createEnd = createEnd;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String[] getOrderStatuss() {
        return orderStatuss;
    }

    public void setOrderStatuss(String[] orderStatuss) {
        this.orderStatuss = orderStatuss;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getIsCancelOrder() {
        return isCancelOrder;
    }

    public void setIsCancelOrder(Integer isCancelOrder) {
        this.isCancelOrder = isCancelOrder;
    }
}
