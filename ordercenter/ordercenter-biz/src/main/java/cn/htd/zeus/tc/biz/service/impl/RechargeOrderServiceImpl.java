package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.biz.dao.PayOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.RechargeOrderDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.RechargeOrderDMO;
import cn.htd.zeus.tc.biz.dmo.RechargeOrderListDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.service.OrderStatusChangeCommonService;
import cn.htd.zeus.tc.biz.service.RechargeOrderService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.PayStatusEnum;
import cn.htd.zeus.tc.common.enums.PayTypeEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.response.EmptyResDTO;
import cn.htd.zeus.tc.dto.resquest.PaymentOrderInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderQueryReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderReqDTO;

@Service
public class RechargeOrderServiceImpl implements RechargeOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RechargeOrderServiceImpl.class);

	@Autowired
	private RechargeOrderDAO rechargeOrderDAO;

	@Autowired
	private PayOrderInfoDAO payOrderInfoDAO;

	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;

	@Autowired
	private OrderStatusChangeCommonService orderStatusChangeCommonService;

	private static final int ZREO = 0;

	private static final byte ORDER_TYPE = 0;

	@Override
	public EmptyResDTO insertRechargeOrder(RechargeOrderReqDTO rechargeOrderReqDTO) {
		EmptyResDTO emptyResDTO = new EmptyResDTO();
		try {
			RechargeOrderDMO record = new RechargeOrderDMO();
			record.setRechargeOrderNo(rechargeOrderReqDTO.getRechargeNo());

			int count = rechargeOrderDAO.selectCountByRechargeOrderNo(record);
			LOGGER.info("MessageId:{} 调用方法RechargeOrderServiceImpl.insertRechargeOrder查询订单充值记录开始{}",
					rechargeOrderReqDTO.getMessageId(), count);
			record.setAmount(rechargeOrderReqDTO.getAmount());
			record.setDepartmentCode(rechargeOrderReqDTO.getDepartmentCode());
			record.setPayCode(rechargeOrderReqDTO.getPayCode());
			record.setMemberCode(rechargeOrderReqDTO.getOperaterCode());
			record.setMemberName(rechargeOrderReqDTO.getOperaterName());
			record.setPayStatus(rechargeOrderReqDTO.getPayStatus());
			record.setPayTime(rechargeOrderReqDTO.getPayTime());
			record.setSupplierCode(rechargeOrderReqDTO.getSupplierCode());
			record.setPayeeAccountNo(rechargeOrderReqDTO.getPayeeAccountNo());
			record.setRechargeChannelCode(rechargeOrderReqDTO.getRechargeChannelCode());
			LOGGER.info(
					"MessageId:{} 调用方法RechargeOrderServiceImpl.insertRechargeOrder插入或更新订单充值记录开始{}",
					rechargeOrderReqDTO.getMessageId(), JSONObject.toJSONString(record));
			if (count == ZREO) {
				rechargeOrderDAO.insertSelective(record);
			} else {
				rechargeOrderDAO.updateRechargeOrderByRechargeOrderNo(record);
			}

			PayOrderInfoDMO payOrderInfoDMO = new PayOrderInfoDMO();
			payOrderInfoDMO.setDownOrderNo(rechargeOrderReqDTO.getRechargeNo());
			payOrderInfoDMO.setOrderNo(rechargeOrderReqDTO.getRechargeNo());
			payOrderInfoDMO.setOrderType(ORDER_TYPE);
			payOrderInfoDMO.setPayStatus(rechargeOrderReqDTO.getPayStatus());
			if (PayTypeEnum.BALANCE_PAY.getCode().equals(rechargeOrderReqDTO.getPayCode())) {
				payOrderInfoDMO
						.setPayType(new Byte(PayStatusEnum.BALANCE_PAY.getCode().toString()));
			} else if (PayTypeEnum.ERP_PAY.getCode().equals(rechargeOrderReqDTO.getPayCode())) {
				payOrderInfoDMO.setPayType(new Byte(PayStatusEnum.ERP_PAY.getCode().toString()));
			} else if (PayTypeEnum.POS_PAY.getCode().equals(rechargeOrderReqDTO.getPayCode())) {
				payOrderInfoDMO.setPayType(new Byte(PayStatusEnum.POS_PAY.getCode().toString()));
			} else {
				payOrderInfoDMO.setPayType(new Byte(PayStatusEnum.ONLINE_PAY.getCode().toString()));
			}
			payOrderInfoDMO.setMemberCode(rechargeOrderReqDTO.getOperaterCode());
			// 如果是超级老板充值则通道是9002 否则为商城通道9001
			if (Constant.RECHARGE_CHANNEL_SUPERBOSS.equals(rechargeOrderReqDTO.getRechargeChannelCode())) {
				payOrderInfoDMO.setProductCode(MiddleWareEnum.SUPERBOSS_RECHARGE.getCode());
				// 如果是POS机充值则通道是9003
			} else if (Constant.RECHARGE_CHANNEL_POS.equals(rechargeOrderReqDTO.getRechargeChannelCode())) {
				payOrderInfoDMO.setProductCode(MiddleWareEnum.POS_RECHARGE.getCode());
			} else {
				payOrderInfoDMO.setProductCode(MiddleWareEnum.PRODUCTCODE_RECHARGE.getCode());
			}
			payOrderInfoDMO.setSupplierCode(rechargeOrderReqDTO.getSupplierCode());
			payOrderInfoDMO.setAmount(rechargeOrderReqDTO.getAmount());
			payOrderInfoDMO.setDepartmentCode(rechargeOrderReqDTO.getDepartmentCode());
			if (Constant.RECHARGE_CHANNEL_SUPERBOSS.equals(rechargeOrderReqDTO.getRechargeChannelCode())
					|| Constant.RECHARGE_CHANNEL_POS.equals(rechargeOrderReqDTO.getRechargeChannelCode())) {
				payOrderInfoDMO.setClassCode(rechargeOrderReqDTO.getClassCode());
				payOrderInfoDMO.setBrandCode(rechargeOrderReqDTO.getBrandCode());
			}
			int resultCount = payOrderInfoDAO.updateByRechargeOrderNo(payOrderInfoDMO);
			if (resultCount == ZREO) {
				LOGGER.info(
						"MessageId:{} 调用方法RechargeOrderServiceImpl.insertRechargeOrder插入订单充值记录开始{}",
						rechargeOrderReqDTO.getMessageId(),
						JSONObject.toJSONString(payOrderInfoDMO));
				payOrderInfoDAO.insertSelective(payOrderInfoDMO);
			}

			emptyResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			emptyResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			emptyResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			emptyResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法RechargeOrderServiceImpl.insertRechargeOrder出现异常{}",
					rechargeOrderReqDTO.getMessageId(), w.toString());
		}

		return emptyResDTO;
	}

	@Override
	public RechargeOrderListDMO selectRechargeOrder(
			RechargeOrderQueryReqDTO rechargeOrderQueryReqDTO) {

		RechargeOrderListDMO rechargeOrderList = new RechargeOrderListDMO();

		try {
			RechargeOrderDMO record = new RechargeOrderDMO();
			record.setMemberCode(rechargeOrderQueryReqDTO.getMemberCode());
			record.setPayTimeFrom(rechargeOrderQueryReqDTO.getPayTimeFrom());
			record.setPayTimeTo(rechargeOrderQueryReqDTO.getPayTimeTo());
			if (rechargeOrderQueryReqDTO.getStart() != null
					&& rechargeOrderQueryReqDTO.getRows() != null) {
				record.setStart((rechargeOrderQueryReqDTO.getStart() - 1)
						* rechargeOrderQueryReqDTO.getRows());
				record.setRows(rechargeOrderQueryReqDTO.getRows());
			}
			// 默认商城充值查询
			if (StringUtils.isBlank(record.getRechargeChannelCode())) {
				record.setRechargeChannelCode("1");
			}
			List<RechargeOrderDMO> rechargeOrder = rechargeOrderDAO
					.selectRechargeOrderByMemberCode(record);
			int count = rechargeOrderDAO.selectCountByRechargeOrderNo(record);
			rechargeOrderList.setRechargeOrder(rechargeOrder);
			rechargeOrderList.setCount(count);
			rechargeOrderList.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			rechargeOrderList.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			rechargeOrderList.setResultCode(ResultCodeEnum.ERROR.getCode());
			rechargeOrderList.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法RechargeOrderServiceImpl.selectRechargeOrder出现异常{}",
					rechargeOrderQueryReqDTO.getMessageId(), w.toString());
		}

		return rechargeOrderList;
	}

	/*
	 * 收付款回调(non-Javadoc)
	 * 
	 * @see
	 * cn.htd.zeus.tc.biz.service.RechargeOrderService#updatePaymentOrderInfo(cn
	 * .htd.zeus.tc.dto.resquest.PaymentOrderInfoReqDTO)
	 */
	@Override
	public EmptyResDTO updatePaymentOrderInfo(PaymentOrderInfoReqDTO paymentOrderInfoReqDTO,
			String messageId) {
		LOGGER.info("MessageId:{} 收付款回调开始中间件传入参数paymentOrderInfoReqDTO:{}",messageId,
				JSONObject.toJSONString(paymentOrderInfoReqDTO));
		EmptyResDTO emptyResDTO = new EmptyResDTO();

		try {
			PayOrderInfoDMO payOrderInfoDMO = new PayOrderInfoDMO();
			payOrderInfoDMO.setDownOrderNo(paymentOrderInfoReqDTO.getRechargeOrderNo());
			String payResultMsg= paymentOrderInfoReqDTO.getResultMessage();
			if(org.apache.commons.lang.StringUtils.isNotEmpty(payResultMsg)){
				int payResultMsgLength = payResultMsg.length();
				payResultMsg = payResultMsgLength>1000?payResultMsg.substring(0, 1000):payResultMsg;
				payOrderInfoDMO.setPayResultMsg(payResultMsg);
			}
			payOrderInfoDMO.setPayLastMessageId(messageId);
			payOrderInfoDMO.setPayResultStatus(
					paymentOrderInfoReqDTO.getPaymentResultStatus().byteValue());
			payOrderInfoDMO.setPayLastTime(paymentOrderInfoReqDTO.getTimestamp() == null
					? DateUtil.getSystemTime() : paymentOrderInfoReqDTO.getTimestamp());
			payOrderInfoDMO.setPayResultCode(paymentOrderInfoReqDTO.getResultCode());
			int count = payOrderInfoDAO.updateByDownOrderNo(payOrderInfoDMO);
			if (count > ZREO) {
				emptyResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
				emptyResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());

				Integer paymentResultStatus = paymentOrderInfoReqDTO.getPaymentResultStatus();
				if (null != paymentResultStatus && paymentResultStatus.intValue() == Integer
						.valueOf(
								OrderStatusEnum.JD_RESULT_STATUS_HAVE_DOWN_MQ_RETURN_FAIL.getCode())
						.intValue()) {
					// 直接更新订单表
					changeOrderStatus(paymentOrderInfoReqDTO.getRechargeOrderNo(), "", "", true,
							OrderStatusEnum.POST_STRIKEA_CALL_BACK.getCode(),
							OrderStatusEnum.POST_STRIKEA_CALL_BACK.getMsg());
				} else if (null != paymentResultStatus && paymentResultStatus.intValue() == Integer
						.valueOf(OrderStatusEnum.JD_RESULT_STATUS_HAVE_DOWN_MQ_RETURN_SUC.getCode())
						.intValue()) {
					// 不直接更新订单表
					changeOrderStatus(paymentOrderInfoReqDTO.getRechargeOrderNo(),
							OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getCode(),
							OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getMsg(),
							false, "", "");
				}
			}
		} catch (Exception e) {
			emptyResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			emptyResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法RechargeOrderServiceImpl.updatePaymentOrderInfo出现异常{}",
					paymentOrderInfoReqDTO.getMessageId(), w.toString());
		}

		return emptyResDTO;
	}

	/*
	 * 收付款回调
	 */
	private void changeOrderStatus(final String downOrderNo, final String status,
			final String statusText, final boolean directUpdateOrder, final String orderErrorStatus,
			final String orderErrorReason) {
		try {
			new Thread(new Runnable() {
				public void run() {

					PayOrderInfoDMO payOrderInfoDMO = payOrderInfoDAO
							.selectByRechargeOrderNo(downOrderNo);
					if (null != payOrderInfoDMO
							&& StringUtilHelper.isNotNull(payOrderInfoDMO.getOrderNo())) {
						TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();

						String orderNo = payOrderInfoDMO.getOrderNo();
						String brandCode = "";
						String classCode = "";
						// 如果不是京东商品,就是内部供应商商品
						if (!brandCode.equals(MiddleWareEnum.JD_BRAND_ID_ERP.getCode())) {
							brandCode = payOrderInfoDMO.getBrandCode();
							classCode = payOrderInfoDMO.getClassCode();

							tradeOrderItemsDMO.setBrandId(Long.parseLong(brandCode));
							tradeOrderItemsDMO.setErpFirstCategoryCode(classCode);
						}
						tradeOrderItemsDMO.setOrderNo(orderNo);
						TradeOrderItemsDMO tradeOrderItemsDMORes = tradeOrderItemsDAO
								.selectOrderItemNoByBrandCodeClassCode(tradeOrderItemsDMO);
						String orderItems = tradeOrderItemsDMORes.getOrderItemNos();

						orderStatusChangeCommonService.orderStatusChange(orderNo, orderItems,
								status, statusText, directUpdateOrder, orderErrorStatus,
								orderErrorReason);
					} else {
						LOGGER.warn("MessageId:{} 从表TB_B_PAYORDERINFO没有查出的订单号(downOrderNo):{}","", downOrderNo);
					}
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("MessageId:{} 收付款回调 -- 修改订单和订单行表装填出现异常-(此异常不需要回滚)","",w.toString());
		}
	}

	@Override
	public RechargeOrderDMO selectRechargeOrderByOrderNo(
			RechargeOrderQueryReqDTO rechargeOrderQueryReqDTO) {
		RechargeOrderDMO order = new RechargeOrderDMO();
		order.setRechargeOrderNo(rechargeOrderQueryReqDTO.getOrderNo());
		RechargeOrderDMO result = rechargeOrderDAO.selectByOrderNo(order);
		return result;
	}

}
