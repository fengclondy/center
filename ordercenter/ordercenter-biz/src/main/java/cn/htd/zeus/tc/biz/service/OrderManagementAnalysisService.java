package cn.htd.zeus.tc.biz.service;

import java.util.List;

import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.zeus.tc.biz.dmo.OrderManagementAnalysisDMO;

public interface OrderManagementAnalysisService {

	public List<ShopDTO> queryShopInfo(String messageId);

	public OrderManagementAnalysisDMO queryOrderManagermentInfo(String sellerCode,
			String lastDayStart, String lastDayEnd);

	public void insertOrderManagementInfo(OrderManagementAnalysisDMO orderManagementAnalysisDMO);
}
