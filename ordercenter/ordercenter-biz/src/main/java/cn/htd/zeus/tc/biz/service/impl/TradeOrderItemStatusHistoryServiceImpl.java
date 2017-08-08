package cn.htd.zeus.tc.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.dao.TradeOrderItemsStatusHistoryDAO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;
import cn.htd.zeus.tc.biz.service.TradeOrderItemStatusHistoryService;
import cn.htd.zeus.tc.common.util.DateUtil;

@Service
public class TradeOrderItemStatusHistoryServiceImpl implements TradeOrderItemStatusHistoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TradeOrderItemStatusHistoryServiceImpl.class);

	private static final int ONE = 1;// 常量1

	@Autowired
	private TradeOrderItemsStatusHistoryDAO tradeOrderItemsStatusHistoryDAO;

	/**
	 * 新增订单行状态履历 insertSelective(方法的作用) @Title: insertSelective @param
	 * record @return int @throws
	 */
	@Override
	public void insertTradeOrderItemStatusHistory(TradeOrderItemsStatusHistoryDMO record) {
		int orderStatusCount = tradeOrderItemsStatusHistoryDAO.selectCountByOrderItemNoAndOrderItemStatus(record);
		if (orderStatusCount < ONE) {
			record.setCreateTime(DateUtil.getSystemTime());
			int count = tradeOrderItemsStatusHistoryDAO.insertSelective(record);
			if (count < ONE) {
				LOGGER.info("新增订单行状态履历失败");
			} else {
				LOGGER.info("新增订单行状态履历成功");
			}
			LOGGER.info("【插入订单行状态履历结束】--结果:{}",count);
		}
	}

}
