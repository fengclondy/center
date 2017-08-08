package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.OrderSalesMonthAPI;
import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoQueryListDMO;
import cn.htd.zeus.tc.biz.service.OrderSalesMonthService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.response.OrderSalesMonthInfoQueryListResDTO;
import cn.htd.zeus.tc.dto.response.OrderSalesMonthInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderSalesMonthInfoReqDTO;

@Service("orderSalesMonthAPI")
public class OrderSalesMonthAPIImpl implements OrderSalesMonthAPI{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSalesMonthAPIImpl.class);
	
	@Autowired
	private OrderSalesMonthService orderSalesMonthService;
	
	@Override
	public OrderSalesMonthInfoQueryListResDTO queryOrderSalesMonthInfoSevenMonthsAgo(
			OrderSalesMonthInfoReqDTO orderSalesMonthInfoReqDTO) {
		OrderSalesMonthInfoQueryListResDTO orderSalesMonthInfoQueryListResDTO = new OrderSalesMonthInfoQueryListResDTO();
		try{
			orderSalesMonthInfoQueryListResDTO.setMessageId(orderSalesMonthInfoReqDTO.getMessageId());
			if(!StringUtilHelper.allIsNotNull(orderSalesMonthInfoReqDTO.getMessageId(),orderSalesMonthInfoReqDTO.getSupperlierId())){
				orderSalesMonthInfoQueryListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				orderSalesMonthInfoQueryListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return orderSalesMonthInfoQueryListResDTO;
			}
			OrderSalesMonthInfoQueryListDMO orderSalesMonthInfoQueryListDMO = orderSalesMonthService.queryOrderSalesMonthInfoSevenMonthsAgo(orderSalesMonthInfoReqDTO);
			
			JSONObject jsonObj = (JSONObject)JSONObject.toJSON(orderSalesMonthInfoQueryListDMO);
			orderSalesMonthInfoQueryListResDTO = JSONObject.toJavaObject(jsonObj, OrderSalesMonthInfoQueryListResDTO.class);
			
			orderSalesMonthInfoQueryListResDTO.setReponseMsg(orderSalesMonthInfoQueryListDMO.getResultMsg());
			orderSalesMonthInfoQueryListResDTO.setResponseCode(orderSalesMonthInfoQueryListDMO.getResultCode());
		}catch(Exception e){
			orderSalesMonthInfoQueryListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			orderSalesMonthInfoQueryListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderSalesMonthAPIImpl.queryOrderSalesMonthInfoSevenMonthsAgo出现异常{}",
					orderSalesMonthInfoReqDTO.getMessageId(),w.toString());
		}
		orderSalesMonthInfoQueryListResDTO.setMessageId(orderSalesMonthInfoReqDTO.getMessageId());
		return orderSalesMonthInfoQueryListResDTO;
	}

}
