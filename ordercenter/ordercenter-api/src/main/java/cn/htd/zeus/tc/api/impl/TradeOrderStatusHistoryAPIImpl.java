package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.TradeOrderStatusHistoryAPI;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryListDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryListDMO;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.response.TradeOrderItemStatusHistoryListResDTO;
import cn.htd.zeus.tc.dto.response.TradeOrderStatusHistoryListResDTO;
import cn.htd.zeus.tc.dto.response.UpdateOrderStatusResDTO;
import cn.htd.zeus.tc.dto.resquest.TradeOrderStatusHistoryReqDTO;
import cn.htd.zeus.tc.dto.resquest.UpdateOrderStatusReqDTO;

@Service("tradeOrderStatusHistoryAPI")
public class TradeOrderStatusHistoryAPIImpl implements TradeOrderStatusHistoryAPI{

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderQueryAPIImpl.class);

	@Resource
	private TradeOrderStatusHistoryService tradeOrderStatusHistoryService;

	@Override
	public TradeOrderStatusHistoryListResDTO selectHistoryByOrderNo(TradeOrderStatusHistoryReqDTO record) {

		TradeOrderStatusHistoryListResDTO tradeOrderStatusHistoryListResDTO = new TradeOrderStatusHistoryListResDTO();
		try {
			tradeOrderStatusHistoryListResDTO.setMessageId(record.getMessageId());
			if (!StringUtilHelper.allIsNotNull( record.getOrderNo(),record.getMessageId())) {
				tradeOrderStatusHistoryListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				tradeOrderStatusHistoryListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return tradeOrderStatusHistoryListResDTO;
			}
			if(!StringUtilHelper.parseNumFormat(record.getMessageId())){
				tradeOrderStatusHistoryListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				tradeOrderStatusHistoryListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return tradeOrderStatusHistoryListResDTO;
			}

			TradeOrderStatusHistoryListDMO TradeOrderStatusListDMOTemp =
							tradeOrderStatusHistoryService.selectHistoryByOrderNo(record);

			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(TradeOrderStatusListDMOTemp);
			tradeOrderStatusHistoryListResDTO = JSONObject.toJavaObject(jsonObj, TradeOrderStatusHistoryListResDTO.class);

			tradeOrderStatusHistoryListResDTO.setReponseMsg(TradeOrderStatusListDMOTemp.getResultMsg());
			tradeOrderStatusHistoryListResDTO.setResponseCode(TradeOrderStatusListDMOTemp.getResultCode());

		} catch (Exception e) {
			tradeOrderStatusHistoryListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			tradeOrderStatusHistoryListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法TradeOrderStatusHistoryAPIImpl.selectByOrderNoAndStatus出现异常{}",
					record.getMessageId(), w.toString());
			return tradeOrderStatusHistoryListResDTO;
		}
		return tradeOrderStatusHistoryListResDTO;
	}

	@Override
	public UpdateOrderStatusResDTO updateOrderStatus(UpdateOrderStatusReqDTO record) {

		UpdateOrderStatusResDTO updateOrderStatusResDTO = new UpdateOrderStatusResDTO();
		try {
			updateOrderStatusResDTO.setMessageId(record.getMessageId());
			if (!StringUtilHelper.allIsNotNull(record.getMessageId(),record.getBuyerCode(),record.getOrderNo(),
					record.getOrderStatus(),record.getOrderStatusText())) {
				updateOrderStatusResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				updateOrderStatusResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return updateOrderStatusResDTO;
			}
			if(!StringUtilHelper.parseNumFormat(record.getMessageId())){
				updateOrderStatusResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				updateOrderStatusResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return updateOrderStatusResDTO;
			}
			
			UpdateOrderStatusResDTO orderStatusResDTO = tradeOrderStatusHistoryService.updateOrderStatus(record);

			updateOrderStatusResDTO.setReponseMsg(orderStatusResDTO.getReponseMsg());
			updateOrderStatusResDTO.setResponseCode(orderStatusResDTO.getResponseCode());

		} catch (Exception e) {
			updateOrderStatusResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			updateOrderStatusResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法TradeOrderStatusHistoryAPIImpl.updateOrderStatus出现异常{}",
					record.getMessageId(), w.toString());
			return updateOrderStatusResDTO;
		}
		return updateOrderStatusResDTO;
	}

	@Override
	public TradeOrderItemStatusHistoryListResDTO selectItemsHistoryByOrderNo(TradeOrderStatusHistoryReqDTO record) {

		TradeOrderItemStatusHistoryListResDTO tradeOrderItemStatusHistoryListResDTO = new TradeOrderItemStatusHistoryListResDTO();
		try {
			tradeOrderItemStatusHistoryListResDTO.setMessageId(record.getMessageId());
			if (!StringUtilHelper.allIsNotNull( record.getOrderNo(),record.getMessageId())) {
				tradeOrderItemStatusHistoryListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				tradeOrderItemStatusHistoryListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return tradeOrderItemStatusHistoryListResDTO;
			}
			if(!StringUtilHelper.parseNumFormat(record.getMessageId())){
				tradeOrderItemStatusHistoryListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				tradeOrderItemStatusHistoryListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return tradeOrderItemStatusHistoryListResDTO;
			}

			TradeOrderItemsStatusHistoryListDMO TradeOrderItemsStatusListDMOTemp =
							tradeOrderStatusHistoryService.selectOrderItemsHistoryByOrderNo(record);

			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(TradeOrderItemsStatusListDMOTemp);
			tradeOrderItemStatusHistoryListResDTO = JSONObject.toJavaObject(jsonObj, TradeOrderItemStatusHistoryListResDTO.class);

			tradeOrderItemStatusHistoryListResDTO.setReponseMsg(TradeOrderItemsStatusListDMOTemp.getResultMsg());
			tradeOrderItemStatusHistoryListResDTO.setResponseCode(TradeOrderItemsStatusListDMOTemp.getResultCode());

		} catch (Exception e) {
			tradeOrderItemStatusHistoryListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			tradeOrderItemStatusHistoryListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法TradeOrderStatusHistoryAPIImpl.selectByOrderNoAndStatus出现异常{}",
					record.getMessageId(), w.toString());
			return tradeOrderItemStatusHistoryListResDTO;
		}
		return tradeOrderItemStatusHistoryListResDTO;
	}

}
