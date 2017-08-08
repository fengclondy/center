package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

/**
 * 查询订单返回ListDTO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: OrdersQueryParamListResDTO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 */
public class OrdersQueryParamListResDTO extends GenricResDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -3021924190833704431L;

	private Integer count;

	private List<OrderQueryParamResDTO> orderList;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<OrderQueryParamResDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderQueryParamResDTO> orderList) {
		this.orderList = orderList;
	}

}
