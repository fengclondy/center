package cn.htd.zeus.tc.biz.dmo;

import java.util.List;

public class OrderQueryListDMO extends GenericDMO{

	/**
	 *
	 */
	private static final long serialVersionUID = 1988838022845026865L;

	private List<TradeOrdersDMO> orderList;

	private Integer count;

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
