package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.api.OrderCompensateERPCallBackAPI;
import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPCallBackDMO;
import cn.htd.zeus.tc.biz.service.OrderCompensateERPService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.response.OrderCompensateERPCallBackResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCompensateERPCallBackReqDTO;

@Service("orderCompensateERPCallBackAPI")
public class OrderCompensateERPCallBackAPIImpl implements OrderCompensateERPCallBackAPI{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCompensateERPCallBackAPIImpl.class);
	
	@Autowired
	private OrderCompensateERPService orderCompensateERPService;
	
	@Override
	public OrderCompensateERPCallBackResDTO orderCompensateERPCallBack(
			OrderCompensateERPCallBackReqDTO orderCompensateERPCallBackReqDTO) {
		OrderCompensateERPCallBackResDTO orderCompensateERPCallBackResDTO = new OrderCompensateERPCallBackResDTO();
		try {
			orderCompensateERPCallBackResDTO = validateParam(orderCompensateERPCallBackReqDTO,orderCompensateERPCallBackResDTO);
			
			String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
			LOGGER.info("中间件回调没有传messageId,此messageId是订单中心自己生成的messageId:"+messageId);
			orderCompensateERPCallBackResDTO.setMessageId(messageId);
			
			if(!ResultCodeEnum.SUCCESS.getCode().equals(orderCompensateERPCallBackResDTO.getResponseCode())){
				return orderCompensateERPCallBackResDTO;
			}

			OrderCompensateERPCallBackDMO orderCompensateERPCallBackDMO = orderCompensateERPService.executeOrderCompensateERPCallBack(orderCompensateERPCallBackReqDTO);
			if(null != orderCompensateERPCallBackDMO){
				orderCompensateERPCallBackResDTO.setReponseMsg(orderCompensateERPCallBackDMO.getResultMsg());
				orderCompensateERPCallBackResDTO.setResponseCode(orderCompensateERPCallBackDMO.getResultCode());
			}	
		} catch (Exception e) {
			orderCompensateERPCallBackResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			orderCompensateERPCallBackResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("中间件回调订单中心时候发生异常:" + w.toString());
		}
		
		return orderCompensateERPCallBackResDTO;
	}
	
	private OrderCompensateERPCallBackResDTO validateParam(OrderCompensateERPCallBackReqDTO orderCompensateERPCallBackReqDTO,OrderCompensateERPCallBackResDTO orderCompensateERPCallBackResDTO){
		
		if(StringUtilHelper.isNull(orderCompensateERPCallBackReqDTO.getOrderCode())){
			orderCompensateERPCallBackResDTO.setResponseCode(ResultCodeEnum.ORDERCALLBACK_PARAM_ORDERID_IS_NULL.getCode());
			orderCompensateERPCallBackResDTO.setReponseMsg(ResultCodeEnum.ORDERCALLBACK_PARAM_ORDERID_IS_NULL.getMsg());
		}else if(StringUtilHelper.isNull(orderCompensateERPCallBackReqDTO.getResult())){
			orderCompensateERPCallBackResDTO.setResponseCode(ResultCodeEnum.ORDERCALLBACK_PARAM_RESULT_IS_NULL.getCode());
			orderCompensateERPCallBackResDTO.setReponseMsg(ResultCodeEnum.ORDERCALLBACK_PARAM_RESULT_IS_NULL.getMsg());
		}else{
			orderCompensateERPCallBackResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		}
		return orderCompensateERPCallBackResDTO;
	}
	
}
