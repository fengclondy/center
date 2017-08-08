package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreateInfoDMO extends GenericDMO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5147404620566494872L;

	//整个交易的总金额
	private BigDecimal totalAmount;
	
	//订单返回集合
	private List<OrderCreateListInfoDMO> orderResList;
	
	//订单返回状态 10:待审核,20:待支付,
	private String orderResStatus;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<OrderCreateListInfoDMO> getOrderResList() {
		return orderResList;
	}

	public void setOrderResList(List<OrderCreateListInfoDMO> orderResList) {
		this.orderResList = orderResList;
	}

	public String getOrderResStatus() {
		return orderResStatus;
	}

	public void setOrderResStatus(String orderResStatus) {
		this.orderResStatus = orderResStatus;
	}
	
}
