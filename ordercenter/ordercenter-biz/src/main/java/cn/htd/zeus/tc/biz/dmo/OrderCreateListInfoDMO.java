package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;
import java.util.List;

public class OrderCreateListInfoDMO extends OrderCreateListInfoPayDMO{

	//订单号
    private String orderNo;
    
    //秒杀订单截止时间
    private Date payTimeLimit;
		
	//订单行List
	private List<OrderCreateItemListInfoDMO> orderItemResList;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<OrderCreateItemListInfoDMO> getOrderItemResList() {
		return orderItemResList;
	}

	public void setOrderItemResList(List<OrderCreateItemListInfoDMO> orderItemResList) {
		this.orderItemResList = orderItemResList;
	}

	public Date getPayTimeLimit() {
		return payTimeLimit;
	}

	public void setPayTimeLimit(Date payTimeLimit) {
		this.payTimeLimit = payTimeLimit;
	}

}
