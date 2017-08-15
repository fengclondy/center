/**
 * 
 */
package cn.htd.promotion.cpc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.htd.promotion.cpc.biz.dao.PromotionSampleDAO;
import cn.htd.promotion.cpc.biz.rao.SampleRAO;
import cn.htd.promotion.cpc.biz.service.SampleService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.dto.request.SampleReqDTO;
import cn.htd.promotion.cpc.dto.response.SampleResDTO;

/**
 * @author admin
 *
 */
public class SampleServiceImpl implements SampleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleServiceImpl.class);
	
	@Autowired
	private SampleRAO sampleRAO;
	
	@Autowired
	private PromotionSampleDAO promotionSampleDAO;
	
	/* (non-Javadoc)
	 * @see cn.htd.promotion.cpc.biz.service.SampleService#sampleServiceMethod(cn.htd.promotion.cpc.dto.request.SampleReqDTO)
	 */
	@Override
	public SampleResDTO sampleServiceMethod(SampleReqDTO sampleReqDTO) {
		// TODO Auto-generated method stub
		SampleResDTO sampleResDTO = new SampleResDTO();
		try {
			sampleResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			sampleResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getMsg());
			LOGGER.info(
					"MessageId:{}调用方法OrderCancelServiceImpl.vmsOperateOrderCancel收付款表订单信息为{}",
					sampleReqDTO.getMessageID());
		} catch (Exception e) {
			sampleResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			sampleResDTO.setResponseCode(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法SampleServiceImpl.sampleServiceMethod出现异常{}",
					sampleReqDTO.getMessageID(), w.toString());
		}
		return sampleResDTO;
	}

}
