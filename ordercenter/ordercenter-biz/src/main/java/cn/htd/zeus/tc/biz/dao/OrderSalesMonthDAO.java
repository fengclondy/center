package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoDMO;

@Repository("cn.htd.zeus.tc.biz.dao.OrderSalesMonthDAO")
public interface OrderSalesMonthDAO {

	/*
	 * 查询订单月销量信息表小于当月的前7条记录
	 * @param orderSalesMonthInfoReqDTO
	 */
	public List<OrderSalesMonthInfoDMO> queryOrderSalesMonthInfoSevenMonthsAgo(OrderSalesMonthInfoDMO orderSalesMonthInfoDMO);
}
