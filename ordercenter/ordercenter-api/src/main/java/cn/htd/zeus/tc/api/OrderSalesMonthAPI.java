package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.OrderSalesMonthInfoQueryListResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderSalesMonthInfoReqDTO;

public interface OrderSalesMonthAPI {
	
	/*
	 * 查询订单月销量信息表小于当月的前7条记录
	 * @param orderSalesMonthInfoReqDTO
	 */
	public OrderSalesMonthInfoQueryListResDTO queryOrderSalesMonthInfoSevenMonthsAgo(OrderSalesMonthInfoReqDTO orderSalesMonthInfoReqDTO);
}
