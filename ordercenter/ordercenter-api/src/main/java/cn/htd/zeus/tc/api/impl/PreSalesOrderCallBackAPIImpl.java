package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.PreSalesOrderCallBackAPI;
import cn.htd.zeus.tc.biz.dmo.PreSalesOrderCallBackDMO;
import cn.htd.zeus.tc.biz.service.PreSalesOrderDownERPJDCreateOrderService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DTOValidateUtil;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.ValidateResult;
import cn.htd.zeus.tc.dto.response.PreSalesOrderCallBackResDTO;
import cn.htd.zeus.tc.dto.resquest.PreSalesOrderCallBackReqDTO;

@Service("preSalesOrderCallBackAPI")
public class PreSalesOrderCallBackAPIImpl implements PreSalesOrderCallBackAPI{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreSalesOrderCallBackAPIImpl.class);
	
	@Autowired
	private PreSalesOrderDownERPJDCreateOrderService preSalesOrderService;
	
	@Override
	public PreSalesOrderCallBackResDTO preSalesOrderCallBack(
			PreSalesOrderCallBackReqDTO preSalesOrderCallBackReqDTO) {
		LOGGER.info("预售下行回调开始:"+JSONObject.toJSONString(preSalesOrderCallBackReqDTO));
		PreSalesOrderCallBackResDTO res = new PreSalesOrderCallBackResDTO();
		try{
			preSalesOrderCallBackReqDTO.setMessageId(GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp()));
			ValidateResult validateResult = DTOValidateUtil.validate(preSalesOrderCallBackReqDTO);
			if(!validateResult.isPass()){
				res.setReponseMsg(validateResult.getReponseMsg());
				res.setResponseCode(ResultCodeEnum.ORDERCENTER_CREAREORDER_PARAM_IS_NULL.getCode());
				return res;
			}
			PreSalesOrderCallBackDMO resDmo = preSalesOrderService.executeJDCreateOrderCallBack(preSalesOrderCallBackReqDTO);
			if(null != resDmo){
				res.setResponseCode(resDmo.getResultCode());
				res.setReponseMsg(resDmo.getResultMsg());
				res.setMessageId(preSalesOrderCallBackReqDTO.getMessageId());
			}
		}catch(Exception e){
			res.setResponseCode(ResultCodeEnum.ERROR.getCode());
			res.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("中间件回调订单中心(调用预售下行回调方法)时候发生异常:" + w.toString());
		}
		return res;
	}

}
