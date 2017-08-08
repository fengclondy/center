package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

public class OrdersQueryVIPListResDTO extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -59464560534141131L;

	private Integer count;
	
	private List<OrdersQueryVIPOrderListResDTO> orderList;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<OrdersQueryVIPOrderListResDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrdersQueryVIPOrderListResDTO> orderList) {
		this.orderList = orderList;
	}
}
