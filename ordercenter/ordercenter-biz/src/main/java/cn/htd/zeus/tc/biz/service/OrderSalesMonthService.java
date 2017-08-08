package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoQueryListDMO;
import cn.htd.zeus.tc.dto.resquest.OrderSalesMonthInfoReqDTO;


public interface OrderSalesMonthService {

	/*
	 * 查询订单月销量信息表小于当月的前7条记录
	 * @param orderSalesMonthInfoReqDTO
	 */
	public OrderSalesMonthInfoQueryListDMO queryOrderSalesMonthInfoSevenMonthsAgo(OrderSalesMonthInfoReqDTO orderSalesMonthInfoReqDTO);
}
