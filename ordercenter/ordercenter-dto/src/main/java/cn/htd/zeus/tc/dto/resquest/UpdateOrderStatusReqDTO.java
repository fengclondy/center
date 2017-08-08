package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UpdateOrderStatusReqDTO extends GenricReqDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -3540988646605257200L;

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
	private String buyerCode;

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
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 订单状态说明
	 */
	private String orderStatusText;

	/**
	 * 店铺 名称
	 */
	private String shopName;

	/**
	 * 支付状态
	 */
	private String payStatus;

	/**
	 * 支付方式
	 */
	private String payType;

	/**
	 * 支付时间
	 */
	private Date payOrderTime;

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

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getPayOrderTime() {
		return payOrderTime;
	}

	public void setPayOrderTime(Date payOrderTime) {
		this.payOrderTime = payOrderTime;
	}

	public String getOrderStatusText() {
		return orderStatusText;
	}

	public void setOrderStatusText(String orderStatusText) {
		this.orderStatusText = orderStatusText;
	}

}
