package cn.htd.zeus.tc.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.zeus.tc.biz.dao.TradeOrderConsigneeDownInfoDAO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderConsigneeDownInfoDMO;
import cn.htd.zeus.tc.biz.service.OrderConsigneeDownService;

@Service
public class OrderConsigneeDownServiceImpl implements OrderConsigneeDownService{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderConsigneeDownServiceImpl.class);
	
	@Autowired
	private TradeOrderConsigneeDownInfoDAO tradeOrderConsigneeDownInfoDAO;

	@Override
	public List<TradeOrderConsigneeDownInfoDMO> selectTradeOrderConsigneeDownInfoList(
			Map paramMap) {
		return tradeOrderConsigneeDownInfoDAO.selectTradeOrderConsigneeDownInfoList(paramMap);
	}

	@Override
	@Transactional
	public void orderConsigneeDownERP(TradeOrderConsigneeDownInfoDMO[] tasks) {
		// TODO Auto-generated method stub
		
	}
	
}
