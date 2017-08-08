package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderCreateInfoResDTO extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3010132875939606032L;

	//整个交易的总金额
	private BigDecimal totalAmount;
	
	//订单返回集合
	private List<OrderCreateListInfoResDTO> orderResList;
	
	//订单返回状态 10:待审核,20:待支付,
	private String orderResStatus;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<OrderCreateListInfoResDTO> getOrderResList() {
		return orderResList;
	}

	public void setOrderResList(List<OrderCreateListInfoResDTO> orderResList) {
		this.orderResList = orderResList;
	}

	public String getOrderResStatus() {
		return orderResStatus;
	}

	public void setOrderResStatus(String orderResStatus) {
		this.orderResStatus = orderResStatus;
	}
	
}
