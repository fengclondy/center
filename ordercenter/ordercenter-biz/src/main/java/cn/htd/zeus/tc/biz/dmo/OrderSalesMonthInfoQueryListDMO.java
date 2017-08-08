package cn.htd.zeus.tc.biz.dmo;

import java.util.List;

public class OrderSalesMonthInfoQueryListDMO extends GenericDMO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7374752439996589011L;
	
	private List<OrderSalesMonthInfoDMO> orderSalesMonthList;

	public List<OrderSalesMonthInfoDMO> getOrderSalesMonthList() {
		return orderSalesMonthList;
	}

	public void setOrderSalesMonthList(List<OrderSalesMonthInfoDMO> orderSalesMonthList) {
		this.orderSalesMonthList = orderSalesMonthList;
	}
}
