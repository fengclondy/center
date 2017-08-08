package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.OrderPaymentResultAPI;
import cn.htd.zeus.tc.biz.dmo.OrderPayInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPayResultInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPayValidateIsBargainCancleResDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultListDMO;
import cn.htd.zeus.tc.biz.service.OrderPaymentResultService;
import cn.htd.zeus.tc.common.enums.PayStatusEnum;
import cn.htd.zeus.tc.common.enums.PayTypeEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.JSONUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.response.EmptyResDTO;
import cn.htd.zeus.tc.dto.response.OrderPayValidateIsBargainCancleResDTO;
import cn.htd.zeus.tc.dto.response.OrderPaymentResultListResDTO;
import cn.htd.zeus.tc.dto.response.OrderPaymentResultResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPayValidateIsBargainCancleReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPaymentResultReqDTO;

@Service("orderPaymentResultAPI")
public class OrderPaymentResultAPIImpl implements OrderPaymentResultAPI {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentResultAPIImpl.class);

	@Autowired
	private OrderPaymentResultService orderPaymentResultService;

	@Override
	public OrderPaymentResultListResDTO selectPaymentResultByTradeNo(
			OrderPaymentResultReqDTO orderPaymentResultReqDTO) {

		OrderPaymentResultListResDTO orderPaymentResultListResDTO = new OrderPaymentResultListResDTO();
		try {
			orderPaymentResultListResDTO.setMessageId(orderPaymentResultReqDTO.getMessageId());
			if (!StringUtilHelper.allIsNotNull(orderPaymentResultReqDTO.getBatchPayInfos(),
					orderPaymentResultReqDTO.getMessageId())) {
				orderPaymentResultListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				orderPaymentResultListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return orderPaymentResultListResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderPaymentResultReqDTO.getMessageId())) {
				orderPaymentResultListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				orderPaymentResultListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return orderPaymentResultListResDTO;
			}

			OrderPaymentResultListDMO orderPaymentResultListDMO = orderPaymentResultService
					.selectPaymentResultByTradeNo(orderPaymentResultReqDTO);

			// TODO

			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(orderPaymentResultListDMO);
			orderPaymentResultListResDTO = JSONObject.toJavaObject(jsonObj, OrderPaymentResultListResDTO.class);

			orderPaymentResultListResDTO.setReponseMsg(orderPaymentResultListDMO.getResultMsg());
			orderPaymentResultListResDTO.setResponseCode(orderPaymentResultListDMO.getResultCode());
		} catch (Exception e) {
			orderPaymentResultListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			orderPaymentResultListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderPaymentResultAPIImpl.selectPaymentResultByTradeNo出现异常{}",
					orderPaymentResultReqDTO.getMessageId(), w.toString());
		}

		return orderPaymentResultListResDTO;
	}

	@Override
	public OrderPaymentResultResDTO updateOrderStatusByTradeNo(OrderPaymentResultReqDTO orderPaymentResultReqDTO) {

		OrderPaymentResultResDTO orderPaymentResultResDTO = new OrderPaymentResultResDTO();
		try {
			orderPaymentResultResDTO.setMessageId(orderPaymentResultReqDTO.getMessageId());

			if (!StringUtilHelper.allIsNotNull(orderPaymentResultReqDTO.getBatchPayInfos(),
					orderPaymentResultReqDTO.getBuyerCode(), orderPaymentResultReqDTO.getMessageId())) {
				orderPaymentResultResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				orderPaymentResultResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return orderPaymentResultResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderPaymentResultReqDTO.getMessageId())) {
				orderPaymentResultResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				orderPaymentResultResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return orderPaymentResultResDTO;
			}

			String jsonStr = orderPaymentResultReqDTO.getBatchPayInfos();
			List<OrderPayInfoDMO> transList = JSONUtil.parseArray(jsonStr, OrderPayInfoDMO.class);

			for (OrderPayInfoDMO orderPayInfoDMO : transList) {
				OrderPayResultInfoDMO orderPayResultInfoDMO = new OrderPayResultInfoDMO();
				if (!StringUtilHelper.allIsNotNull(orderPayInfoDMO.getMerchantOrderNo(),
						orderPayInfoDMO.getTradeStatus(), orderPayInfoDMO.getTradeType(),
						orderPayInfoDMO.getAmount())) {
					orderPaymentResultResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
					orderPaymentResultResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
					return orderPaymentResultResDTO;
				}

				// 订单支付状态
				if (PayTypeEnum.SUCCESS.getCode().equals(orderPayInfoDMO.getTradeStatus())) {
					orderPayResultInfoDMO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
				} else if (PayTypeEnum.PROCESSING.getCode().equals(orderPayInfoDMO.getTradeStatus())) {
					orderPayResultInfoDMO.setPayStatus(PayStatusEnum.PROCESSING.getCode());
				} else {
					orderPayResultInfoDMO.setPayStatus(PayStatusEnum.FAIL.getCode());
				}

				// 订单支付类型
				if (PayTypeEnum.BALANCE_PAY.getCode().equals(orderPayInfoDMO.getTradeType())) {
					orderPayResultInfoDMO.setPayType(PayStatusEnum.BALANCE_PAY.getCode());
				} else if (PayTypeEnum.ERP_PAY.getCode().equals(orderPayInfoDMO.getTradeType())) {
					orderPayResultInfoDMO.setPayType(PayStatusEnum.ERP_PAY.getCode());
				} else {
					orderPayResultInfoDMO.setPayType(PayStatusEnum.ONLINE_PAY.getCode());
				}
				orderPayResultInfoDMO.setPayChannel(orderPaymentResultReqDTO.getPayChannel());
				orderPayResultInfoDMO.setBuyerCode(orderPaymentResultReqDTO.getBuyerCode());
				orderPayResultInfoDMO.setOrderNo(orderPayInfoDMO.getMerchantOrderNo());
				orderPayResultInfoDMO.setOrderPayAmount(new BigDecimal(orderPayInfoDMO.getAmount()));
				orderPayResultInfoDMO.setMessageId(orderPaymentResultReqDTO.getMessageId());
				orderPayResultInfoDMO.setFlag(orderPayInfoDMO.getFlag());
				orderPayResultInfoDMO.setCommissionList(orderPayInfoDMO.getCommissionList());
				LOGGER.info("MessageId:{} 调用方法OrderPaymentResultAPIImpl.updateOrderStatusByTradeNo入参{}",
						JSONObject.toJSONString(orderPayResultInfoDMO));
				OrderPaymentResultDMO orderPaymentResultDMO = orderPaymentResultService
						.updateOrderStatusByTradeNo(orderPayResultInfoDMO);

				JSONObject jsonObj = (JSONObject) JSONObject.toJSON(orderPaymentResultDMO);
				orderPaymentResultResDTO = JSONObject.toJavaObject(jsonObj, OrderPaymentResultResDTO.class);

				orderPaymentResultResDTO.setReponseMsg(orderPaymentResultDMO.getResultMsg());
				orderPaymentResultResDTO.setResponseCode(orderPaymentResultDMO.getResultCode());
			}

		} catch (Exception e) {
			orderPaymentResultResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			orderPaymentResultResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderPaymentResultAPIImpl.updateOrderStatusByTradeNo出现异常{}",
					orderPaymentResultReqDTO.getMessageId(), w.toString());
		}

		return orderPaymentResultResDTO;
	}

	@Override
	public EmptyResDTO updatePayStatusByOrderNo(OrderPaymentResultReqDTO orderPaymentResultReqDTO) {

		EmptyResDTO emptyResDTO = new EmptyResDTO();
		try {
			if (!StringUtilHelper.parseNumFormat(orderPaymentResultReqDTO.getMessageId())) {
				emptyResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				emptyResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return emptyResDTO;
			}

			emptyResDTO.setMessageId(orderPaymentResultReqDTO.getMessageId());

			if (!StringUtilHelper.allIsNotNull(orderPaymentResultReqDTO.getOrderNo(),
					orderPaymentResultReqDTO.getPayStatus(), orderPaymentResultReqDTO.getMessageId())) {
				emptyResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				emptyResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return emptyResDTO;
			}

			OrderPaymentResultDMO orderPaymentResultDMO = orderPaymentResultService
					.updatePayStatusByOrderNo(orderPaymentResultReqDTO);

			emptyResDTO.setResponseCode(orderPaymentResultDMO.getResultCode());
			emptyResDTO.setReponseMsg(orderPaymentResultDMO.getResultMsg());
		} catch (Exception e) {
			emptyResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			emptyResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderPaymentResultAPIImpl.updatePayStatusByOrderNo出现异常{}",
					orderPaymentResultReqDTO.getMessageId(), w.toString());
		}

		return emptyResDTO;
	}

	/**
	 * 支付前校验,是否有议价|是否取消
	 */
	@Override
	public OrderPayValidateIsBargainCancleResDTO validateIsBargainCancle(
			OrderPayValidateIsBargainCancleReqDTO orderPayValidateIsBargainCancleReqDTO) {
		
        OrderPayValidateIsBargainCancleResDTO resDTO = new OrderPayValidateIsBargainCancleResDTO();
		try {
			String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
			orderPayValidateIsBargainCancleReqDTO.setMessageId(messageId);
			OrderPayValidateIsBargainCancleResDMO resDMO = orderPaymentResultService
					.validateIsBargainCancle(orderPayValidateIsBargainCancleReqDTO);
			
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(resDMO);
			resDTO = JSONObject.toJavaObject(jsonObj, OrderPayValidateIsBargainCancleResDTO.class);
			resDTO.setReponseMsg(resDMO.getResultMsg());
			resDTO.setResponseCode(resDMO.getResultCode());
		} catch (Exception e) {
			resDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			resDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderPaymentResultAPIImpl.validateIsBargainCancle出现异常{}",
					orderPayValidateIsBargainCancleReqDTO.getMessageId(), w.toString());
		}
		return resDTO;
	}

}
