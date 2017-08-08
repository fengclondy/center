package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.api.OrderCreateAPI;
import cn.htd.zeus.tc.biz.dmo.OrderCreateInfoDMO;
import cn.htd.zeus.tc.biz.service.OrderCreateService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DTOValidateUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.common.util.ValidateResult;
import cn.htd.zeus.tc.dto.response.OrderCreateInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateListInfoReqDTO;

import com.alibaba.fastjson.JSONObject;

@Service("orderCreateAPI")
public class OrderCreateAPIImpl implements OrderCreateAPI{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreateAPIImpl.class);
	
	@Autowired
	private OrderCreateService orderCreateService;
	
	@Override
	public OrderCreateInfoResDTO orderCreate(OrderCreateInfoReqDTO orderCreateInfoReqDTO) {
		OrderCreateInfoResDTO orderCreateInfoResDTO = new OrderCreateInfoResDTO();
		try{
			
			/*验空2017-02-13*/
			ValidateResult validateResult = DTOValidateUtil.validate(orderCreateInfoReqDTO);
			if(!validateResult.isPass()){
				orderCreateInfoResDTO.setReponseMsg(validateResult.getReponseMsg());
				orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.ORDERCENTER_CREAREORDER_PARAM_IS_NULL.getCode());
				return  orderCreateInfoResDTO;
			}
			orderCreateInfoResDTO.setMessageId(orderCreateInfoReqDTO.getMessageId());
			/*orderCreateInfoResDTO = validateParam(orderCreateInfoReqDTO,orderCreateInfoResDTO);
			if(!ResultCodeEnum.SUCCESS.getCode().equals(orderCreateInfoResDTO.getResponseCode())){
				return orderCreateInfoResDTO;
			}*/
			OrderCreateInfoDMO orderCreateInfoDMO = orderCreateService.orderCreate(orderCreateInfoReqDTO);
			JSONObject jsonObj = (JSONObject)JSONObject.toJSON(orderCreateInfoDMO);
			orderCreateInfoResDTO = JSONObject.toJavaObject(jsonObj, OrderCreateInfoResDTO.class);
			orderCreateInfoResDTO.setResponseCode(orderCreateInfoDMO.getResultCode());
			orderCreateInfoResDTO.setReponseMsg(orderCreateInfoDMO.getResultMsg());
		}catch(Exception e){
			orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			orderCreateInfoResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{}创建订单时候发生异常:{}" ,orderCreateInfoReqDTO.getMessageId(), w.toString());
		}
		return orderCreateInfoResDTO;
	}
	
	/*private OrderCreateInfoResDTO validateParam(OrderCreateInfoReqDTO orderCreateInfoReqDTO,OrderCreateInfoResDTO orderCreateInfoResDTO){
		
		if(StringUtilHelper.isNull(orderCreateInfoReqDTO.getMessageId())){
			orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.ORDERCENTER_PARAM_IS_NULL.getCode());
			orderCreateInfoResDTO.setReponseMsg(ResultCodeEnum.ORDERCENTER_PARAM_IS_NULL.getMsg());
		}else if(StringUtilHelper.isNull(orderCreateInfoReqDTO.getTradeNo())){
			orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.ORDERCENTER_CREAREORDER_PARAM_IS_NULL.getCode());
			orderCreateInfoResDTO.setReponseMsg(ResultCodeEnum.ORDERCENTER_CREAREORDER_PARAM_IS_NULL.getMsg()+" tradeNo:"+orderCreateInfoReqDTO.getTradeNo());
			//TODO 到时候定义错误码 并set
		}else{
			orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		}
		return orderCreateInfoResDTO;
	}*/

	@Override
	public OrderCreateInfoResDTO orderCreate4MiddleWare(
			OrderCreateInfoReqDTO orderCreateInfoReqDTO) {
		LOGGER.info("中间件请求--创建订单开始:"+JSONObject.toJSONString(orderCreateInfoReqDTO));
		OrderCreateInfoResDTO orderCreateInfoResDTO = new OrderCreateInfoResDTO();
		try{
			
			/*验空2017-02-13*/
			ValidateResult validateResult = DTOValidateUtil.validate(orderCreateInfoReqDTO);
			if(!validateResult.isPass()){
				orderCreateInfoResDTO.setReponseMsg(validateResult.getReponseMsg());
				orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.ORDERCENTER_CREAREORDER_PARAM_IS_NULL.getCode());
				return  orderCreateInfoResDTO;
			}
			orderCreateInfoResDTO.setMessageId(orderCreateInfoReqDTO.getMessageId());
			/*orderCreateInfoResDTO = validateParam(orderCreateInfoReqDTO,orderCreateInfoResDTO);
			if(!ResultCodeEnum.SUCCESS.getCode().equals(orderCreateInfoResDTO.getResponseCode())){
				return orderCreateInfoResDTO;
			}*/
			
			orderCreateInfoResDTO = validateParam4MiddleWare(orderCreateInfoReqDTO,orderCreateInfoResDTO);
			if(!ResultCodeEnum.SUCCESS.getCode().equals(orderCreateInfoResDTO.getResponseCode())){
				return orderCreateInfoResDTO;
			}
			
			OrderCreateInfoDMO orderCreateInfoDMO = orderCreateService.orderCreate(orderCreateInfoReqDTO);
			JSONObject jsonObj = (JSONObject)JSONObject.toJSON(orderCreateInfoDMO);
			orderCreateInfoResDTO = JSONObject.toJavaObject(jsonObj, OrderCreateInfoResDTO.class);
			orderCreateInfoResDTO.setResponseCode(orderCreateInfoDMO.getResultCode());
			orderCreateInfoResDTO.setReponseMsg(orderCreateInfoDMO.getResultMsg());
		}catch(Exception e){
			orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			orderCreateInfoResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("创建订单时候发生异常:" + w.toString());
		}
		LOGGER.info("中间件请求--创建订单结束:"+JSONObject.toJSONString(orderCreateInfoResDTO));
		return orderCreateInfoResDTO;
	}
	
	private OrderCreateInfoResDTO validateParam4MiddleWare(OrderCreateInfoReqDTO orderCreateInfoReqDTO,OrderCreateInfoResDTO orderCreateInfoResDTO){
		if (!StringUtilHelper.parseNumFormat(orderCreateInfoReqDTO.getMessageId())) {
			orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
			orderCreateInfoResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
			return orderCreateInfoResDTO;
		}
		
		if (!StringUtilHelper.parseNumFormat(orderCreateInfoReqDTO.getTradeNo())) {
			orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_PARAM_IS_ERROR.getCode());
			orderCreateInfoResDTO.setReponseMsg("交易号"+ResultCodeEnum.GOODSCENTER_PARAM_IS_ERROR.getMsg());
			return orderCreateInfoResDTO;
		}
		
		List<OrderCreateListInfoReqDTO> orderList = orderCreateInfoReqDTO.getOrderList();
		if(null != orderList && orderList.size()>0){
			for(OrderCreateListInfoReqDTO orderTemp : orderList){
				String orderNo = orderTemp.getOrderNo();
				if (!StringUtilHelper.parseNumFormat(orderNo) || orderNo.length() != 19 || !orderNo.startsWith("20")) {
					orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_PARAM_IS_ERROR.getCode());
					orderCreateInfoResDTO.setReponseMsg("订单号"+orderNo+ResultCodeEnum.GOODSCENTER_PARAM_IS_ERROR.getMsg());
					return orderCreateInfoResDTO;
				}
				List<OrderCreateItemListInfoReqDTO> orderItemList = orderTemp.getOrderItemList();
				if(null != orderItemList && orderItemList.size()>0){
					for(OrderCreateItemListInfoReqDTO orderItemTemp : orderItemList){
						String orderItemNo = orderItemTemp.getOrderItemNo();
						if(!StringUtilHelper.parseNumFormat(orderItemNo) || orderItemNo.length() != 21 || !orderItemNo.startsWith("20")){
							orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_PARAM_IS_ERROR.getCode());
							orderCreateInfoResDTO.setReponseMsg("订单行号"+orderItemNo+ResultCodeEnum.GOODSCENTER_PARAM_IS_ERROR.getMsg());
							return orderCreateInfoResDTO;
						}
					}
				}else{
					orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL.getCode());
					orderCreateInfoResDTO.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL.getMsg()+"订单行集合为空");
					return orderCreateInfoResDTO;
				}
			
			}
		}else{
			orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL.getCode());
			orderCreateInfoResDTO.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL.getMsg()+"订单集合为空");
			return orderCreateInfoResDTO;
		}
		orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		return orderCreateInfoResDTO;
	}
}
