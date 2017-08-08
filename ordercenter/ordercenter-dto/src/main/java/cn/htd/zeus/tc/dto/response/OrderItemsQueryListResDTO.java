package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

public class OrderItemsQueryListResDTO  extends GenricResDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1677559589070516102L;

	private List<OrderQueryItemsResDTO> orderItemsList;

	public List<OrderQueryItemsResDTO> getOrderItemsList() {
		return orderItemsList;
	}

	public void setOrderItemsList(List<OrderQueryItemsResDTO> orderItemsList) {
		this.orderItemsList = orderItemsList;
	}

}
