/**
 * 
 */
package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.api.ShopOrderReportAPI;
import cn.htd.zeus.tc.biz.service.ShopOrderAnalysisReportService;
import cn.htd.zeus.tc.biz.service.ShopOrderStatisticsReportService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.dto.response.ShopOrderAnalsisDayReportResDTO;
import cn.htd.zeus.tc.dto.response.ShopOrderStatisticsDayReportResDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderAnalysisDayReportReqDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderStatisticsDayReportReqDTO;

/**
 * @author ly
 *
 */
@Service("shopOrderReportAPI")
public class ShopOrderReportAPImpl implements ShopOrderReportAPI{

	private static final Logger LOGGER = LoggerFactory.getLogger(ShopOrderReportAPImpl.class);
	
	@Autowired
	private ShopOrderAnalysisReportService shopOrderAnalysisReportService;
	
	@Autowired
	private ShopOrderStatisticsReportService shopOrderStatisticsReportService;
	
	@Override
	public ShopOrderAnalsisDayReportResDTO queryShopOrderDayAnalsis(
			ShopOrderAnalysisDayReportReqDTO shopOrderAnalysisDayReportReqDTO) {
		ShopOrderAnalsisDayReportResDTO shopOrderAnalsisDayReportResDTO = new ShopOrderAnalsisDayReportResDTO();
		try {
			Integer shopId = shopOrderAnalysisDayReportReqDTO.getShopId();
			String messageId = shopOrderAnalysisDayReportReqDTO.getMessageId();
			Integer startTime = shopOrderAnalysisDayReportReqDTO.getStartTime();
			Integer endTime = shopOrderAnalysisDayReportReqDTO.getEndTime();
			if(shopId == null || StringUtils.isEmpty(messageId)||startTime == null ||  endTime == null)
			{
				shopOrderAnalsisDayReportResDTO.setResponseCode(ResultCodeEnum.SHOPORDER_ANALSIS_PARAM_IS_NULL.getCode());
				shopOrderAnalsisDayReportResDTO.setReponseMsg(ResultCodeEnum.SHOPORDER_ANALSIS_PARAM_IS_NULL.getMsg());
				return shopOrderAnalsisDayReportResDTO;
			}
			boolean startTimeIsDateFormatter = DateUtil.isValidDate(String.valueOf(startTime));
			boolean endTimeIsDateFormatter = DateUtil.isValidDate(String.valueOf(endTime));
			if( !startTimeIsDateFormatter|| !endTimeIsDateFormatter)
			{
				shopOrderAnalsisDayReportResDTO.setResponseCode(ResultCodeEnum.SHOPORDER_ANALSIS_DATEPARAM_IS_NULL.getCode());
				shopOrderAnalsisDayReportResDTO.setReponseMsg(ResultCodeEnum.SHOPORDER_ANALSIS_DATEPARAM_IS_NULL.getMsg());
				return shopOrderAnalsisDayReportResDTO;
			}
			shopOrderAnalsisDayReportResDTO = shopOrderAnalysisReportService.queryShopOrderAnalsisDayReportList(shopOrderAnalysisDayReportReqDTO);
		} catch (Exception e) {
			shopOrderAnalsisDayReportResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			shopOrderAnalsisDayReportResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法ShopOrderReportAPImpl.queryShopOrderDayAnalsis出现异常{}",
					shopOrderAnalsisDayReportResDTO.getMessageId(), w.toString());
		
		}
		return shopOrderAnalsisDayReportResDTO;
	}

	@Override
	public ShopOrderStatisticsDayReportResDTO queryShopOrderDayStatistics(
			ShopOrderStatisticsDayReportReqDTO shopOrderStatisticsDayReportReqDTO) {
		
		ShopOrderStatisticsDayReportResDTO shopOrderStatisticsDayReportResDTO = new ShopOrderStatisticsDayReportResDTO();
		try {
			Integer shopId = shopOrderStatisticsDayReportReqDTO.getShopId();
			String messageId = shopOrderStatisticsDayReportReqDTO.getMessageId();
			Integer startTime = shopOrderStatisticsDayReportReqDTO.getStartTime();
			Integer endTime = shopOrderStatisticsDayReportReqDTO.getEndTime();
			if(shopId == null || StringUtils.isEmpty(messageId)||startTime == null ||  endTime == null)
			{
				shopOrderStatisticsDayReportResDTO.setResponseCode(ResultCodeEnum.SHOPORDER_ANALSIS_PARAM_IS_NULL.getCode());
				shopOrderStatisticsDayReportResDTO.setReponseMsg(ResultCodeEnum.SHOPORDER_ANALSIS_PARAM_IS_NULL.getMsg());
				return shopOrderStatisticsDayReportResDTO;
			}
			boolean startTimeIsDateFormatter = DateUtil.isValidDate(String.valueOf(startTime));
			boolean endTimeIsDateFormatter = DateUtil.isValidDate(String.valueOf(endTime));
			if( !startTimeIsDateFormatter|| !endTimeIsDateFormatter)
			{
				shopOrderStatisticsDayReportResDTO.setResponseCode(ResultCodeEnum.SHOPORDER_ANALSIS_DATEPARAM_IS_NULL.getCode());
				shopOrderStatisticsDayReportResDTO.setReponseMsg(ResultCodeEnum.SHOPORDER_ANALSIS_DATEPARAM_IS_NULL.getMsg());
				return shopOrderStatisticsDayReportResDTO;
			}
			shopOrderStatisticsDayReportResDTO = shopOrderStatisticsReportService.queryShopOrderStatisticsDayReportList(shopOrderStatisticsDayReportReqDTO);
		} catch (Exception e) {
			shopOrderStatisticsDayReportResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			shopOrderStatisticsDayReportResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法ShopOrderReportAPImpl.queryShopOrderDayStatistics出现异常{}",
					shopOrderStatisticsDayReportResDTO.getMessageId(), w.toString());
		}
		return shopOrderStatisticsDayReportResDTO;
	}
}
