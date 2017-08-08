package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.RechargeOrderAPI;
import cn.htd.zeus.tc.biz.dmo.RechargeOrderListDMO;
import cn.htd.zeus.tc.biz.service.RechargeOrderService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.response.EmptyResDTO;
import cn.htd.zeus.tc.dto.response.RechargeOrderListResDTO;
import cn.htd.zeus.tc.dto.resquest.PaymentOrderInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderQueryReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderReqDTO;

@Service("rechargeOrderAPI")
public class RechargeOrderAPIImpl implements RechargeOrderAPI {

	private static final Logger LOGGER = LoggerFactory.getLogger(RechargeOrderAPIImpl.class);

	@Autowired
	private RechargeOrderService rechargeOrderService;

	@Override
	public EmptyResDTO insertRechargeOrder(RechargeOrderReqDTO rechargeOrderReqDTO) {

		EmptyResDTO emptyResDTO = new EmptyResDTO();

		try {
			if (!StringUtilHelper.parseNumFormat(rechargeOrderReqDTO.getMessageId())) {
				emptyResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				emptyResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return emptyResDTO;
			}
			LOGGER.info("MessageId:{} 调用方法RechargeOrderAPIImpl.insertRechargeOrder开始{}",
					rechargeOrderReqDTO.getMessageId(), JSONObject.toJSONString(rechargeOrderReqDTO));
			EmptyResDTO returnDTO = rechargeOrderService.insertRechargeOrder(rechargeOrderReqDTO);
			LOGGER.info("MessageId:{} 调用方法RechargeOrderAPIImpl.insertRechargeOrder结束{}",
					rechargeOrderReqDTO.getMessageId(), JSONObject.toJSONString(returnDTO));
			emptyResDTO.setReponseMsg(returnDTO.getReponseMsg());
			emptyResDTO.setResponseCode(returnDTO.getResponseCode());
		} catch (Exception e) {
			emptyResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			emptyResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法RechargeOrderAPIImpl.insertRechargeOrder出现异常{}",
					rechargeOrderReqDTO.getMessageId(), w.toString());
			return emptyResDTO;
		}

		return emptyResDTO;
	}

	@Override
	public RechargeOrderListResDTO selectRechargeOrder(RechargeOrderQueryReqDTO rechargeOrderQueryReqDTO) {

		RechargeOrderListResDTO rechargeOrderListResDTO = new RechargeOrderListResDTO();

		try {
			if (!StringUtilHelper.parseNumFormat(rechargeOrderQueryReqDTO.getMessageId())) {
				rechargeOrderListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				rechargeOrderListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return rechargeOrderListResDTO;
			}

			RechargeOrderListDMO rechargeOrderList = rechargeOrderService.selectRechargeOrder(rechargeOrderQueryReqDTO);

			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(rechargeOrderList);
			rechargeOrderListResDTO = JSONObject.toJavaObject(jsonObj, RechargeOrderListResDTO.class);

			rechargeOrderListResDTO.setReponseMsg(rechargeOrderList.getResultMsg());
			rechargeOrderListResDTO.setResponseCode(rechargeOrderList.getResultCode());
			rechargeOrderListResDTO.setMessageId(rechargeOrderQueryReqDTO.getMessageId());
		} catch (Exception e) {
			rechargeOrderListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			rechargeOrderListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法RechargeOrderAPIImpl.selectRechargeOrder出现异常{}",
					rechargeOrderQueryReqDTO.getMessageId(), w.toString());
		}

		return rechargeOrderListResDTO;
	}

	/*
	 * 收付款回调(non-Javadoc)
	 * 
	 * @see
	 * cn.htd.zeus.tc.api.RechargeOrderAPI#updatePaymentOrderInfo(cn.htd.zeus.tc
	 * .dto.resquest.PaymentOrderInfoReqDTO)
	 */
	@Override
	public EmptyResDTO updatePaymentOrderInfo(PaymentOrderInfoReqDTO paymentOrderInfoReqDTO) {

		EmptyResDTO emptyResDTO = new EmptyResDTO();

		try {
			String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
			LOGGER.info("收付款回调开始,中间件没有传入messageId,订单中心生成messageId:" + messageId);
			EmptyResDTO returnDTO = rechargeOrderService.updatePaymentOrderInfo(paymentOrderInfoReqDTO, messageId);

			emptyResDTO.setReponseMsg(returnDTO.getReponseMsg());
			emptyResDTO.setResponseCode(returnDTO.getResponseCode());
		} catch (Exception e) {
			emptyResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			emptyResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法RechargeOrderAPIImpl.insertRechargeOrder出现异常{}",
					paymentOrderInfoReqDTO.getMessageId(), w.toString());
			return emptyResDTO;
		}

		return emptyResDTO;
	}

}
