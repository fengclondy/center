package cn.htd.zeus.tc.biz.dmo;

import java.util.List;

public class OrdersQueryVIPListDMO extends GenericDMO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4885264886443961840L;

	private Integer count;
	
	private List<TradeOrdersDMO> orderList;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<TradeOrdersDMO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<TradeOrdersDMO> orderList) {
		this.orderList = orderList;
	}

}
