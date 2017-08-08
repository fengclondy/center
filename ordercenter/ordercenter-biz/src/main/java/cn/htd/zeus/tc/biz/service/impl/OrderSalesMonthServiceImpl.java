package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.dao.OrderSalesMonthDAO;
import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoQueryListDMO;
import cn.htd.zeus.tc.biz.service.OrderSalesMonthService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.dto.resquest.OrderSalesMonthInfoReqDTO;

@Service
public class OrderSalesMonthServiceImpl implements OrderSalesMonthService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSalesMonthServiceImpl.class);

	@Autowired
	private OrderSalesMonthDAO orderSalesMonthDAO;
	
	@Override
	public OrderSalesMonthInfoQueryListDMO queryOrderSalesMonthInfoSevenMonthsAgo(OrderSalesMonthInfoReqDTO orderSalesMonthInfoReqDTO) {
		OrderSalesMonthInfoQueryListDMO OrderSalesMonthInfoQueryListDMO = new OrderSalesMonthInfoQueryListDMO();
		try{
			OrderSalesMonthInfoDMO orderSalesMonthInfoDMO = new OrderSalesMonthInfoDMO();
			orderSalesMonthInfoDMO.setSalesMonthYear(Integer.valueOf(DateUtil.getYearMonth()));
			orderSalesMonthInfoDMO.setSupperlierId(orderSalesMonthInfoReqDTO.getSupperlierId());
			List<OrderSalesMonthInfoDMO> orderSalesMonthInfoDMOList = orderSalesMonthDAO.queryOrderSalesMonthInfoSevenMonthsAgo(orderSalesMonthInfoDMO);
			OrderSalesMonthInfoQueryListDMO.setOrderSalesMonthList(orderSalesMonthInfoDMOList);
			OrderSalesMonthInfoQueryListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			OrderSalesMonthInfoQueryListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		}catch(Exception e){
			OrderSalesMonthInfoQueryListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			OrderSalesMonthInfoQueryListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderSalesMonthServiceImpl.queryOrderSalesMonthInfoSevenMonthsAgo出现异常{}",orderSalesMonthInfoReqDTO.getMessageId(),w.toString());
		}
		
		return OrderSalesMonthInfoQueryListDMO;
	}

}
