package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.api.JDCreateOrderCallBackAPI;
import cn.htd.zeus.tc.biz.dmo.JDCreateOrderCallBackDMO;
import cn.htd.zeus.tc.biz.service.JDCreateOrderService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DTOValidateUtil;
import cn.htd.zeus.tc.common.util.ValidateResult;
import cn.htd.zeus.tc.dto.response.JDCreateOrderCallBackResDTO;
import cn.htd.zeus.tc.dto.resquest.JDCreateOrderCallBackReqDTO;

@Service("jdCreateOrderCallBackAPI")
public class JDCreateOrderCallBackAPIImpl implements JDCreateOrderCallBackAPI{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JDCreateOrderCallBackAPIImpl.class);
	
	@Autowired
	private JDCreateOrderService jdCreateOrderService;
	
	/*
	 * 京东抛单回调方法
	 * 目前走http,没有回调
	 */
	@Override
	public JDCreateOrderCallBackResDTO jdCreateOrderCallBack(
			JDCreateOrderCallBackReqDTO jdCreateOrderCallBackReqDTO) {
		JDCreateOrderCallBackResDTO jdRes = new JDCreateOrderCallBackResDTO();
		try{
			ValidateResult validateResult = DTOValidateUtil.validate(jdCreateOrderCallBackReqDTO);
			if(!validateResult.isPass()){
				jdRes.setReponseMsg(validateResult.getReponseMsg());
				jdRes.setResponseCode(ResultCodeEnum.ORDERCENTER_CREAREORDER_PARAM_IS_NULL.getCode());
				return jdRes;
			}
			
			JDCreateOrderCallBackDMO jdCreateOrderCallBackDMO = jdCreateOrderService.executeJDCreateOrderCallBack(jdCreateOrderCallBackReqDTO);
			if(null != jdCreateOrderCallBackDMO){
				jdRes.setMessageId(jdCreateOrderCallBackReqDTO.getMessageId());
				jdRes.setResponseCode(jdCreateOrderCallBackDMO.getResultCode());
				jdRes.setResponseCode(jdCreateOrderCallBackDMO.getResultMsg());
			}
		}catch(Exception e){
			jdRes.setResponseCode(ResultCodeEnum.ERROR.getCode());
			jdRes.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("中间件回调订单中心(调用京东抛单回调方法)时候发生异常:" + w.toString());
		}
		
		return jdRes;
	}

}
