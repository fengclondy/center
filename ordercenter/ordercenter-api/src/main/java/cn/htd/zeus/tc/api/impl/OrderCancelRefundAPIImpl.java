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

import cn.htd.zeus.tc.api.OrderCancelRefundAPI;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.service.OrderCancelService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.response.OrderCancelInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCancelInfoReqDTO;

/**
 * @author ly
 *
 */
@Service("orderCancelRefundAPI")
public class OrderCancelRefundAPIImpl implements OrderCancelRefundAPI {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderCancelRefundAPIImpl.class);

	@Autowired
	private OrderCancelService orderCancelService;

	@Override
	public OrderCancelInfoResDTO orderCancel(
			OrderCancelInfoReqDTO orderCancelInfoReqDTO) {
		OrderCancelInfoResDTO orderCancelInfoResDTO = null;
		try {
			orderCancelInfoResDTO = validateReqParam(orderCancelInfoReqDTO,
					"OrderCancelRefundAPIImpl.orderCancel");
			String orderCancelReason = orderCancelInfoReqDTO.getOrderCancelReason();
			if (StringUtils.isEmpty(orderCancelReason)) {
				LOGGER.info("调用方法orderItemCancel的入参orderCancelReason不能为空");
				orderCancelInfoResDTO
						.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getCode());
				orderCancelInfoResDTO
						.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getMsg());
				return orderCancelInfoResDTO;

			}
			if (ResultCodeEnum.SUCCESS.getCode().equals(
					orderCancelInfoResDTO.getResponseCode())) {
				TradeOrdersDMO tradeOrdersDMO = orderCancelService
						.orderCancel(orderCancelInfoReqDTO);
				orderCancelInfoResDTO.setResponseCode(tradeOrdersDMO
						.getResultCode());
				orderCancelInfoResDTO.setReponseMsg(tradeOrdersDMO
						.getResultMsg());
				return orderCancelInfoResDTO;
			}
		} catch (Exception e) {
			orderCancelInfoResDTO.setResponseCode(ResultCodeEnum.ERROR
					.getCode());
			orderCancelInfoResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderNo:{} 调用方法OrderCancelRefundAPIImpl.orderCancel出现异常{}",
					orderCancelInfoReqDTO.getMessageId(),orderCancelInfoReqDTO.getOrderNo());
		}
		return orderCancelInfoResDTO;
	}

	@Override
	public OrderCancelInfoResDTO orderItemRefund(
			OrderCancelInfoReqDTO orderCancelInfoReqDTO) {
		OrderCancelInfoResDTO orderCancelInfoResDTO = new OrderCancelInfoResDTO();
//		try {
//			String orderItemNo = orderCancelInfoReqDTO.getOrderItemNo();
//			if (StringUtils.isEmpty(orderItemNo)) {
//				orderCancelInfoResDTO
//						.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
//								.getCode());
//				orderCancelInfoResDTO
//						.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
//								.getMsg());
//				LOGGER.info(
//						"MessageId:{} 调用方法OrderCancelRefundAPIImpl.orderItemRefund的入参不能为空",
//						orderCancelInfoReqDTO.getMessageId());
//				return orderCancelInfoResDTO;
//			}
//
//			if (ResultCodeEnum.SUCCESS.getCode().equals(
//					orderCancelInfoResDTO.getResponseCode())) {
//				TradeOrderItemsDMO tradeOrderItemsDMO = orderCancelService
//						.orderItemCancel(orderCancelInfoReqDTO);
//				orderCancelInfoResDTO.setResponseCode(tradeOrderItemsDMO
//						.getResultCode());
//				orderCancelInfoResDTO.setReponseMsg(tradeOrderItemsDMO
//						.getResultMsg());
//				return orderCancelInfoResDTO;
//			}
//		} catch (Exception e) {
//			orderCancelInfoResDTO.setResponseCode(ResultCodeEnum.ERROR
//					.getCode());
//			orderCancelInfoResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
//			StringWriter w = new StringWriter();
//			e.printStackTrace(new PrintWriter(w));
//			LOGGER.error(
//					"MessageId:{} 调用方法OrderCancelRefundAPIImpl.orderItemRefund出现异常{}",
//					orderCancelInfoReqDTO.getMessageId(), w.toString());
//		}
		return orderCancelInfoResDTO;
	}

	@Override
	public OrderCancelInfoResDTO orderItemCancel(
			OrderCancelInfoReqDTO orderCancelInfoReqDTO) {
		// TODO Auto-generated method stub
		OrderCancelInfoResDTO orderCancelInfoResDTO = new OrderCancelInfoResDTO();
		try {
			String orderItemNo = orderCancelInfoReqDTO.getOrderItemNo();
			if (StringUtils.isEmpty(orderItemNo)) {
				orderCancelInfoResDTO
						.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getCode());
				orderCancelInfoResDTO
						.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getMsg());
				LOGGER.info(
						"MessageId:{} 调用方法OrderCancelRefundAPIImpl.orderItemCancel的入参不能为空",
						orderCancelInfoReqDTO.getMessageId());
				return orderCancelInfoResDTO;
			}
			String orderCancelReason = orderCancelInfoReqDTO.getOrderCancelReason();
			if (StringUtils.isEmpty(orderCancelReason)) {
				LOGGER.info("调用方法orderItemCancel的入参orderCancelReason不能为空");
				orderCancelInfoResDTO
						.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getCode());
				orderCancelInfoResDTO
						.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getMsg());
				return orderCancelInfoResDTO;

			}

			orderCancelInfoResDTO = validateReqParam(orderCancelInfoReqDTO,
					"OrderCancelRefundAPIImpl.orderItemCancel");

			if (ResultCodeEnum.SUCCESS.getCode().equals(
					orderCancelInfoResDTO.getResponseCode())) {
				TradeOrderItemsDMO tradeOrderItemsDMO = orderCancelService
						.orderItemCancel(orderCancelInfoReqDTO);
				orderCancelInfoResDTO.setResponseCode(tradeOrderItemsDMO
						.getResultCode());
				orderCancelInfoResDTO.setReponseMsg(tradeOrderItemsDMO
						.getResultMsg());
				return orderCancelInfoResDTO;
			}
		} catch (Exception e) {
			orderCancelInfoResDTO.setResponseCode(ResultCodeEnum.ERROR
					.getCode());
			orderCancelInfoResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderItemNo:{} 调用方法OrderCancelRefundAPIImpl.orderItemCancel出现异常{}",
					orderCancelInfoReqDTO.getMessageId(),orderCancelInfoReqDTO.getOrderItemNo());
		}
		return orderCancelInfoResDTO;
	}
	

	@Override
	public OrderCancelInfoResDTO orderDelete(
			OrderCancelInfoReqDTO orderCancelInfoReqDTO) {
		OrderCancelInfoResDTO orderCancelInfoResDTO = null;
		try {
			orderCancelInfoResDTO = validateReqParam(orderCancelInfoReqDTO,
					"OrderCancelRefundAPIImpl.orderDelete");
			if (ResultCodeEnum.SUCCESS.getCode().equals(
					orderCancelInfoResDTO.getResponseCode())) {
				TradeOrdersDMO tradeOrdersDMO = orderCancelService
						.orderDelete(orderCancelInfoReqDTO);
				orderCancelInfoResDTO.setResponseCode(tradeOrdersDMO
						.getResultCode());
				orderCancelInfoResDTO.setReponseMsg(tradeOrdersDMO
						.getResultMsg());
				return orderCancelInfoResDTO;
			}
		} catch (Exception e) {
			orderCancelInfoResDTO.setResponseCode(ResultCodeEnum.ERROR
					.getCode());
			orderCancelInfoResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderNo:{} 调用方法OrderCancelRefundAPIImpl.orderDelete出现异常{}",
					orderCancelInfoReqDTO.getMessageId(),orderCancelInfoReqDTO.getOrderNo());
		}
		return orderCancelInfoResDTO;
	}

	/**
	 * 
	 * @param orderCancelInfoReqDTO
	 * @return
	 */
	private OrderCancelInfoResDTO validateReqParam(
			OrderCancelInfoReqDTO orderCancelInfoReqDTO, String metodName) {
		OrderCancelInfoResDTO orderCancelInfoResDTO = new OrderCancelInfoResDTO();
		orderCancelInfoResDTO.setMessageId(orderCancelInfoReqDTO.getMessageId());
		try {
			orderCancelInfoResDTO.setResponseCode(ResultCodeEnum.SUCCESS
					.getCode());
			orderCancelInfoResDTO
					.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			String orderNo = orderCancelInfoReqDTO.getOrderNo();
			String messageID = orderCancelInfoReqDTO.getMessageId();
			String memberCode = orderCancelInfoReqDTO.getMemberCode();
			if (StringUtils.isEmpty(messageID)) {
				LOGGER.info("调用方法" + metodName + "的入参MessageID不能为空");
				orderCancelInfoResDTO
						.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getCode());
				orderCancelInfoResDTO
						.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getMsg());
				return orderCancelInfoResDTO;

			}
			if (StringUtils.isEmpty(orderNo)
					|| StringUtils.isEmpty(memberCode)) {
				orderCancelInfoResDTO
						.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getCode());
				orderCancelInfoResDTO
						.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getMsg());
				LOGGER.info("MessageId:{} 调用方法" + metodName + "的入参不能为空",
						messageID);
				return orderCancelInfoResDTO;
			}
		} catch (Exception e) {
			orderCancelInfoResDTO.setResponseCode(ResultCodeEnum.ERROR
					.getCode());
			orderCancelInfoResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderNo:{} 调用方法OrderCancelRefundAPIImpl.orderDelete出现异常{}",
					orderCancelInfoReqDTO.getMessageId(),orderCancelInfoReqDTO.getOrderNo());
		}
		return orderCancelInfoResDTO;
	}

	@Override
	public OrderCancelInfoResDTO vmsOperateOrderItemCancel(
			OrderCancelInfoReqDTO orderCancelInfoReqDTO) {
		OrderCancelInfoResDTO orderCancelInfoResDTO = new OrderCancelInfoResDTO();
		try {
			String orderItemNo = orderCancelInfoReqDTO.getOrderItemNo();
			if (StringUtils.isEmpty(orderItemNo)) {
				orderCancelInfoResDTO
						.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getCode());
				orderCancelInfoResDTO
						.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getMsg());
				LOGGER.info(
						"MessageId:{} 调用方法OrderCancelRefundAPIImpl.vmsOperateOrderItemCancel的入参不能为空",
						orderCancelInfoReqDTO.getMessageId());
				return orderCancelInfoResDTO;
			}
			String orderCancelReason = orderCancelInfoReqDTO.getOrderCancelReason();
			if (StringUtils.isEmpty(orderCancelReason)) {
				LOGGER.info("调用方法orderItemCancel的入参orderCancelReason不能为空");
				orderCancelInfoResDTO
						.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getCode());
				orderCancelInfoResDTO
						.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getMsg());
				return orderCancelInfoResDTO;

			}
			orderCancelInfoResDTO = validateReqParam(orderCancelInfoReqDTO,
					"OrderCancelRefundAPIImpl.vmsOperateOrderItemCancel");
			if (ResultCodeEnum.SUCCESS.getCode().equals(
					orderCancelInfoResDTO.getResponseCode())) {
				TradeOrderItemsDMO tradeOrderItemsDMO = orderCancelService
						.vmsOperateOrderItemCancel(orderCancelInfoReqDTO);
				orderCancelInfoResDTO.setResponseCode(tradeOrderItemsDMO
						.getResultCode());
				orderCancelInfoResDTO.setReponseMsg(tradeOrderItemsDMO
						.getResultMsg());
				return orderCancelInfoResDTO;
			}
		} catch (Exception e) {
			orderCancelInfoResDTO.setResponseCode(ResultCodeEnum.ERROR
					.getCode());
			orderCancelInfoResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderItemNo:{} 调用方法OrderCancelRefundAPIImpl.vmsOperateOrderCancel出现异常{}",
					orderCancelInfoReqDTO.getMessageId(),orderCancelInfoReqDTO.getOrderItemNo());
		}
		return orderCancelInfoResDTO;
	}

	@Override
	public OrderCancelInfoResDTO vmsOperateOrderCancel(
			OrderCancelInfoReqDTO orderCancelInfoReqDTO) {
		OrderCancelInfoResDTO orderCancelInfoResDTO = new OrderCancelInfoResDTO();
		try {
			orderCancelInfoResDTO = validateReqParam(orderCancelInfoReqDTO,
					"OrderCancelRefundAPIImpl.vmsOperateOrderCancel");
			String orderCancelReason = orderCancelInfoReqDTO.getOrderCancelReason();
			if (StringUtils.isEmpty(orderCancelReason)) {
				LOGGER.info("调用方法orderItemCancel的入参orderCancelReason不能为空");
				orderCancelInfoResDTO
						.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getCode());
				orderCancelInfoResDTO
						.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_NULL
								.getMsg());
				return orderCancelInfoResDTO;

			}
			if (ResultCodeEnum.SUCCESS.getCode().equals(
					orderCancelInfoResDTO.getResponseCode())) {
				TradeOrdersDMO tradeOrdersDMO = orderCancelService
						.vmsOperateOrderCancel(orderCancelInfoReqDTO);
				orderCancelInfoResDTO.setResponseCode(tradeOrdersDMO
						.getResultCode());
				orderCancelInfoResDTO.setReponseMsg(tradeOrdersDMO
						.getResultMsg());
				return orderCancelInfoResDTO;
			}
		} catch (Exception e) {
			orderCancelInfoResDTO.setResponseCode(ResultCodeEnum.ERROR
					.getCode());
			orderCancelInfoResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderNo:{} 调用方法OrderCancelRefundAPIImpl.vmsOperateOrderCancel出现异常{}",
					orderCancelInfoReqDTO.getMessageId(),orderCancelInfoReqDTO.getOrderNo());
		}
		return orderCancelInfoResDTO;
	}
}
