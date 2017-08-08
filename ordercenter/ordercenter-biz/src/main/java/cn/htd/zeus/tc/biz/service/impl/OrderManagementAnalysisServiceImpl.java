package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.dao.OrderManagementAnalysisDAO;
import cn.htd.zeus.tc.biz.dmo.OrderManagementAnalysisDMO;
import cn.htd.zeus.tc.biz.service.OrderManagementAnalysisService;

@Service
public class OrderManagementAnalysisServiceImpl implements OrderManagementAnalysisService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderManagementAnalysisServiceImpl.class);

	@Resource
	private OrderManagementAnalysisDAO orderManagementAnalysisDAO;

	@Override
	public OrderManagementAnalysisDMO queryOrderManagermentInfo(String sellerCode,
			String payOrderDate) {
		OrderManagementAnalysisDMO analysis = null;
		try {
			analysis = orderManagementAnalysisDAO.queryOrderManagermentInfo(sellerCode,
					payOrderDate);
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderManagementAnalysisServiceImpl.queryOrderManagermentInfo出现异常{}",
					"卖家编号为=" + sellerCode, w.toString());
		}
		return analysis;
	}

	@Override
	public void insertOrderManagementInfo(OrderManagementAnalysisDMO orderManagementAnalysisDMO) {
		try {
			int count = orderManagementAnalysisDAO
					.updateOrderManagementInfo(orderManagementAnalysisDMO);
			if (count == 0) {
				orderManagementAnalysisDAO.insertOrderManagementInfo(orderManagementAnalysisDMO);
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderManagementAnalysisServiceImpl.queryOrderManagermentInfo出现异常{}",
					"插入失败订单统计数据为" + com.alibaba.fastjson.JSONObject
							.toJSONString(orderManagementAnalysisDMO),
					w.toString());
		}
	}

	@Override
	public List<OrderManagementAnalysisDMO> queryShopInfo() {
		List<OrderManagementAnalysisDMO> shopInfo = null;
		try {
			shopInfo = orderManagementAnalysisDAO.queryShopInfo();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderManagementAnalysisServiceImpl.queryShopInfo出现异常{}",
					w.toString());
		}
		return shopInfo;
	}

}
