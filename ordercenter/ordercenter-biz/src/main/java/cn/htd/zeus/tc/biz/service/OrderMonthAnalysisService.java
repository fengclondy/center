package cn.htd.zeus.tc.biz.service;

import java.util.List;

import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoDMO;

public interface OrderMonthAnalysisService {

	public List<OrderSalesMonthInfoDMO> queryShopInfo();

	public OrderSalesMonthInfoDMO queryOrderMonthAnalysisInfo(String sellerCode, String firstDay,
			String lastDay);

	public void insertOrderMonthAnalysisInfo(OrderSalesMonthInfoDMO orderSalesMonthInfoDMO);
}
