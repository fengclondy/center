package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单支付结果返回DTO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: OrderPaymentResultResDTO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 */
public class OrderPaymentResultResDTO extends GenricResDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -292531020253143940L;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 交易号
	 */
	private String tradeNo;

	/**
	 * 会员id
	 */
	private Long buyerId;

	/**
	 * 配送方式
	 */
	private String deliveryType;

	/**
	 * 卖家名称
	 */
	private String sellerName;

	/**
	 * 订单支付价格
	 */
	private BigDecimal orderPayAmount;

	/**
	 * 店铺 名称
	 */
	private String shopName;

	private String payStatus;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

}
