package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;
import java.util.List;

import cn.htd.zeus.tc.dto.OrderItemPaymentDTO;

public class OrderPayResultInfoDMO extends GenericDMO {

	/**
	 *
	 */
	private static final long serialVersionUID = -7653870317355557282L;

	private String orderNo;

	private String payType;

	private String orderStatus;

	private String payStatus;
	
	private String payChannel;

	private BigDecimal orderPayAmount;

	private String buyerCode;

	private String messageId;

	private String flag;

	/**
	 * 订单行佣金
	 */
	private List<OrderItemPaymentDTO> commissionList;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
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

}
