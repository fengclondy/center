package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.service.OrderInfoService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.resquest.OrderQueryReqDTO;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderInfoServiceImpl.class);
	
	@Autowired
	TradeOrdersDAO tradeOrdersDAO;
	
	@Override
	public TradeOrdersDMO queryOrderBySellerIdAndBuyerId(OrderQueryReqDTO orderQueryReqDTO) {
		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
		try{
			TradeOrdersDMO tradeOrdersDMOParam = new TradeOrdersDMO();
			tradeOrdersDMOParam.setSellerCode(orderQueryReqDTO.getSellerCode());
			tradeOrdersDMOParam.setBuyerCode(orderQueryReqDTO.getBuyerCode());
			TradeOrdersDMO tradeOrdersDMOTemp = tradeOrdersDAO.queryOrderBySellerIdAndBuyerId(tradeOrdersDMOParam);
			if(null != tradeOrdersDMOTemp){
				tradeOrdersDMO = tradeOrdersDMOTemp;
			}
			tradeOrdersDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
			return tradeOrdersDMO;
		}catch (Exception e) {
			tradeOrdersDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderInfoServiceImpl.queryOrderBySellerIdAndBuyerId出现异常{}",orderQueryReqDTO.getMessageId(),w.toString());
			return tradeOrdersDMO;
		}
	}
}
