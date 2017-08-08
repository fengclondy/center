package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

public class OrderSalesMonthInfoQueryListResDTO extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7585178042263125073L;
	
	private List<OrderSalesMonthInfoResDTO> orderSalesMonthList;

	public List<OrderSalesMonthInfoResDTO> getOrderSalesMonthList() {
		return orderSalesMonthList;
	}

	public void setOrderSalesMonthList(
			List<OrderSalesMonthInfoResDTO> orderSalesMonthList) {
		this.orderSalesMonthList = orderSalesMonthList;
	}
	
}
