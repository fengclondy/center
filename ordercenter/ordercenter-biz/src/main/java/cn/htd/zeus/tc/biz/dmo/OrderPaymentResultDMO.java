package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.htd.zeus.tc.dto.OrderItemPaymentDTO;

/**
 * 订单支付 Copyright (C), 2013-2016, 汇通达网络有限公司 FileName: OrderPaymentResultDMO.java
 * Author: jiaop Date: 2016-8-25 下午4:47:39 Description: //模块目的、功能描述 History:
 * //修改记录
 */
public class OrderPaymentResultDMO extends GenericDMO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1622686562850169986L;

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
	 * 支付渠道
	 */
	private String payChannel;

	/**
	 * 支付时间
	 */
	private Date payOrderTime;

	private String orderStatusText;

	private Date orderReceiptTime;

	private String modifyName;

	private Long modifyId;

	private Date modifyTime;

	private List<String> orderNoList;

	private String flag;

	/**
	 * 订单行佣金
	 */
	private List<OrderItemPaymentDTO> commissionList;

	private String orderErrorStatus;

	private Date orderErrorTime;

	private String orderErrorReason;

	private Integer isCancelOrder;
	
	private Integer orderType;

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

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public Date getPayOrderTime() {
		return payOrderTime;
	}

	public void setPayOrderTime(Date payOrderTime) {
		this.payOrderTime = payOrderTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusText() {
		return orderStatusText;
	}

	public void setOrderStatusText(String orderStatusText) {
		this.orderStatusText = orderStatusText;
	}

	public Date getOrderReceiptTime() {
		return orderReceiptTime;
	}

	public void setOrderReceiptTime(Date orderReceiptTime) {
		this.orderReceiptTime = orderReceiptTime;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<String> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<String> orderNoList) {
		this.orderNoList = orderNoList;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the commissionList
	 */
	public List<OrderItemPaymentDTO> getCommissionList() {
		return commissionList;
	}

	/**
	 * @param commissionList
	 *            the commissionList to set
	 */
	public void setCommissionList(List<OrderItemPaymentDTO> commissionList) {
		this.commissionList = commissionList;
	}

	/**
	 * @return the orderErrorStatus
	 */
	public String getOrderErrorStatus() {
		return orderErrorStatus;
	}

	/**
	 * @param orderErrorStatus
	 *            the orderErrorStatus to set
	 */
	public void setOrderErrorStatus(String orderErrorStatus) {
		this.orderErrorStatus = orderErrorStatus;
	}

	/**
	 * @return the orderErrorTime
	 */
	public Date getOrderErrorTime() {
		return orderErrorTime;
	}

	/**
	 * @param orderErrorTime
	 *            the orderErrorTime to set
	 */
	public void setOrderErrorTime(Date orderErrorTime) {
		this.orderErrorTime = orderErrorTime;
	}

	/**
	 * @return the orderErrorReason
	 */
	public String getOrderErrorReason() {
		return orderErrorReason;
	}

	/**
	 * @param orderErrorReason
	 *            the orderErrorReason to set
	 */
	public void setOrderErrorReason(String orderErrorReason) {
		this.orderErrorReason = orderErrorReason;
	}

	/**
	 * @return the isCancelOrder
	 */
	public Integer getIsCancelOrder() {
		return isCancelOrder;
	}

	/**
	 * @param isCancelOrder
	 *            the isCancelOrder to set
	 */
	public void setIsCancelOrder(Integer isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

}
