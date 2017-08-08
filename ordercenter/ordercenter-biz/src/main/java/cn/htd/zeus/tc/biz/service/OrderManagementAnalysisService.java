package cn.htd.zeus.tc.biz.service;

import java.util.List;

import cn.htd.zeus.tc.biz.dmo.OrderManagementAnalysisDMO;

public interface OrderManagementAnalysisService {

	public List<OrderManagementAnalysisDMO> queryShopInfo();

	public OrderManagementAnalysisDMO queryOrderManagermentInfo(String sellerCode,
			String payOrderDate);

	public void insertOrderManagementInfo(OrderManagementAnalysisDMO orderManagementAnalysisDMO);
}
