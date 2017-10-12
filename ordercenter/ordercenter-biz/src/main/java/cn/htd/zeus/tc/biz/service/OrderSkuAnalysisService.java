package cn.htd.zeus.tc.biz.service;

import java.util.List;

import cn.htd.zeus.tc.biz.dmo.OrderSkuAnalysisDMO;

public interface OrderSkuAnalysisService {

	public List<OrderSkuAnalysisDMO> queryShopInfo();

	public List<OrderSkuAnalysisDMO> queryOrderSkuInfo(String sellerCode, String lastDayStart,
			String lastDayEnd);

	public void insertOrderSkuInfo(OrderSkuAnalysisDMO orderSkuAnalysisDMO);
}
