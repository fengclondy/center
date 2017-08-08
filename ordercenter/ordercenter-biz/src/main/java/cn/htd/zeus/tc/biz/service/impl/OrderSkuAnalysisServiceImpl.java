package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.dao.OrderSkuAnalysisDAO;
import cn.htd.zeus.tc.biz.dmo.OrderSkuAnalysisDMO;
import cn.htd.zeus.tc.biz.service.OrderSkuAnalysisService;

@Service
public class OrderSkuAnalysisServiceImpl implements OrderSkuAnalysisService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSkuAnalysisServiceImpl.class);

	@Resource
	private OrderSkuAnalysisDAO orderSkuAnalysisDAO;

	@Override
	public List<OrderSkuAnalysisDMO> queryShopInfo() {
		List<OrderSkuAnalysisDMO> shopInfo = null;
		try {
			shopInfo = orderSkuAnalysisDAO.queryShopInfo();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderSkuAnalysisServiceImpl.queryShopInfo出现异常{}",
					w.toString());
		}
		return shopInfo;
	}

	@Override
	public List<OrderSkuAnalysisDMO> queryOrderSkuInfo(String sellerCode, String payOrderDate) {
		List<OrderSkuAnalysisDMO> analysis = null;
		try {
			analysis = orderSkuAnalysisDAO.queryOrderSkuInfo(sellerCode, payOrderDate);
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderSkuAnalysisServiceImpl.queryOrderSkuInfo出现异常{}",
					"卖家编号为=" + sellerCode, w.toString());
		}
		return analysis;
	}

	@Override
	public void insertOrderSkuInfo(OrderSkuAnalysisDMO orderSkuAnalysisDMO) {
		try {
			int count = orderSkuAnalysisDAO.updateOrderSkuInfo(orderSkuAnalysisDMO);
			if (count == 0) {
				orderSkuAnalysisDAO.insertOrderSkuInfo(orderSkuAnalysisDMO);
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderSkuAnalysisServiceImpl.insertOrderSkuInfo出现异常{}",
					"插入失败订单统计数据为"
							+ com.alibaba.fastjson.JSONObject.toJSONString(orderSkuAnalysisDMO),
					w.toString());
		}

	}

}
