package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.dao.OrderMonthAnalysisDAO;
import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoDMO;
import cn.htd.zeus.tc.biz.service.OrderMonthAnalysisService;

@Service
public class OrderMonthAnalysisServiceImpl implements OrderMonthAnalysisService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderMonthAnalysisServiceImpl.class);

	@Resource
	private OrderMonthAnalysisDAO orderMonthAnalysisDAO;

	@Override
	public List<OrderSalesMonthInfoDMO> queryShopInfo() {
		List<OrderSalesMonthInfoDMO> shopInfo = null;
		try {
			shopInfo = orderMonthAnalysisDAO.queryShopInfo();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderMonthAnalysisServiceImpl.queryShopInfo出现异常{}",
					w.toString());
		}
		return shopInfo;
	}

	@Override
	public OrderSalesMonthInfoDMO queryOrderMonthAnalysisInfo(String sellerCode, String firstDay,
			String lastDay) {
		OrderSalesMonthInfoDMO analysis = null;
		try {
			analysis = orderMonthAnalysisDAO.queryOrderMonthAnalysisInfo(sellerCode, firstDay,
					lastDay);
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderMonthAnalysisServiceImpl.queryOrderMonthAnalysisInfo出现异常{}",
					"卖家编号为=" + sellerCode, w.toString());
		}
		return analysis;
	}

	@Override
	public void insertOrderMonthAnalysisInfo(OrderSalesMonthInfoDMO orderSalesMonthInfoDMO) {
		try {
			int count = orderMonthAnalysisDAO.updateOrderMonthAnalysisInfo(orderSalesMonthInfoDMO);
			if (count == 0) {
				orderMonthAnalysisDAO.insertOrderMonthAnalysisInfo(orderSalesMonthInfoDMO);
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderMonthAnalysisServiceImpl.queryOrderMonthAnalysisInfo出现异常{}",
					"插入失败订单统计数据为"
							+ com.alibaba.fastjson.JSONObject.toJSONString(orderSalesMonthInfoDMO),
					w.toString());
		}
	}

}
