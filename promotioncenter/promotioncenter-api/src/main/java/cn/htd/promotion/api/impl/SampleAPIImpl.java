/**
 * 
 */
package cn.htd.promotion.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.promotion.api.SampleAPI;
import cn.htd.promotion.cpc.biz.service.SampleService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.dto.request.SampleReqDTO;
import cn.htd.promotion.cpc.dto.response.SampleResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author admin
 *
 */
@Service("sampleAPI")
public class SampleAPIImpl implements SampleAPI {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleAPIImpl.class);
	
	@Autowired
	private SampleService sampleService;
	
	@Override
	public String sampleMethod(String sampleReqDTOString) {
		// TODO Auto-generated method stub
		String sampleResDTOString = null;
		SampleResDTO sampleResDTO = new SampleResDTO();
		SampleReqDTO sampleReqDTO = new SampleReqDTO();
		try {
			sampleReqDTO = JSON.parseObject(sampleReqDTOString, new TypeReference<SampleReqDTO>(){});
			sampleResDTO = sampleService.sampleServiceMethod(sampleReqDTO);
			sampleResDTOString = JSON.toJSONString(sampleResDTO);
			sampleResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			sampleResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getMsg());
			LOGGER.info(
					"MessageId:{}调用方法OrderCancelServiceImpl.vmsOperateOrderCancel收付款表订单信息为{}",
					sampleReqDTO);
		} catch (Exception e) {
			sampleResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			sampleResDTO.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法SampleServiceImpl.sampleMethod出现异常{}",
					sampleReqDTO.getMessageID(), w.toString());
		}
		return sampleResDTOString;
	}
}
