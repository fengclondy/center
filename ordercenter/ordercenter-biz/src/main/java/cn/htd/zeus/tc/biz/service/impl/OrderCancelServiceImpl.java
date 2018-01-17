/**
 * 
 */
package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.zeus.tc.biz.dao.OrderCancelDAO;
import cn.htd.zeus.tc.biz.dao.OrderItemCancelDAO;
import cn.htd.zeus.tc.biz.dao.PayOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderErpDistributionDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDiscountDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderErpDistributionDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDiscountDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.rao.ErpMiddleWareOrderRAO;
import cn.htd.zeus.tc.biz.rao.GoodsCenterRAO;
import cn.htd.zeus.tc.biz.rao.MarketCenterRAO;
import cn.htd.zeus.tc.biz.service.OrderCancelService;
import cn.htd.zeus.tc.biz.service.OrderStatusChangeCommonService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.PayStatusEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCancelInfoReqDTO;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;

/**
 * @author ly
 *
 */
@Service
public class OrderCancelServiceImpl implements OrderCancelService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCancelServiceImpl.class);

	@Autowired
	private OrderCancelDAO orderCancelDAO = null;

	@Autowired
	private OrderItemCancelDAO orderItemCancelDAO = null;

	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO = null;

	@Autowired
	private TradeOrdersDAO tradeOrdersDAO = null;

	@Autowired
	private TradeOrderItemsDiscountDAO tradeOrderItemsDiscountDAO = null;

	@Autowired
	private MarketCenterRAO marketCenterRAO = null;

	@Autowired
	private GoodsCenterRAO goodsCenterRAO = null;

	@Autowired
	private ErpMiddleWareOrderRAO erpMiddleWareOrderRAO = null;

	@Autowired
	private PayOrderInfoDAO payOrderInfoDAO = null;

	@Autowired
	private TradeOrderErpDistributionDAO tradeOrderErpDistributionDAO;

	@Autowired
	private OrderStatusChangeCommonService orderStatusChangeCommonService;

	@Override
	@Transactional
	public TradeOrdersDMO orderCancel(OrderCancelInfoReqDTO orderCancelInfoDTO) {
		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
		String orderNo = orderCancelInfoDTO.getOrderNo();
		try {
			String messageId = orderCancelInfoDTO.getMessageId();
			String memberCode = orderCancelInfoDTO.getMemberCode();
			tradeOrdersDMO.setOrderNo(orderNo);
			tradeOrdersDMO.setBuyerCode(memberCode);
			tradeOrdersDMO = orderCancelDAO.selectTradeCancelOrderByOrderNo(tradeOrdersDMO);
			if (tradeOrdersDMO == null) {
				tradeOrdersDMO = new TradeOrdersDMO();
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getMsg());
				return tradeOrdersDMO;
			}
			String orderStatus = tradeOrdersDMO.getOrderStatus();
			if (StringUtils.isEmpty((orderStatus))) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getMsg());
				return tradeOrdersDMO;
			}
			int isCancelOrder = tradeOrdersDMO.getIsCancelOrder();
			if (OrderStatusEnum.CANCLED.getCode().equals(String.valueOf(isCancelOrder))) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getMsg());
				return tradeOrdersDMO;
			}
			if (!OrderStatusEnum.PRE_CHECK.getCode().equals(orderStatus)
					&& !OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode().equals(orderStatus)
					&& !OrderStatusEnum.PRE_PAY.getCode().equals(orderStatus)
					&& !OrderStatusEnum.PRE_CONFIRM.getCode().equals(orderStatus)) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getMsg());
				return tradeOrdersDMO;
			}
			String memberID = orderCancelInfoDTO.getOrderCancelMemberId();
			if (StringUtils.isEmpty(memberID)) {
				memberID = "0";
			}
			String memberName = orderCancelInfoDTO.getOrderCancelMemberName();
			if (OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_CHECK.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_PAY.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_CONFIRM.getCode().equals(orderStatus)) {
				tradeOrdersDMO = reservseResource(tradeOrdersDMO, orderNo, messageId, memberID, memberName,
						Constant.ORDER_TYPE_PARENT, null);
				String reserveResultCode = tradeOrdersDMO.getResultCode();
				if (!ResultCodeEnum.SUCCESS.getCode().equals(reserveResultCode)) {
					return tradeOrdersDMO;
				}
			}
			tradeOrdersDMO.setOrderCancelMemberId(Long.parseLong(memberID));
			tradeOrdersDMO.setOrderCancelMemberName(memberName);
			String cancelReason = orderCancelInfoDTO.getOrderCancelReason();
			tradeOrdersDMO.setOrderCancelReason(cancelReason);
			tradeOrdersDMO.setIsCancelOrder(Constant.IS_CANCEL_ORDER);
			tradeOrdersDMO.setOrderCancelTime(DateUtil.getSystemTime());
			tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
			int updateResultInt = orderCancelDAO.updateOrderCancelInfo(tradeOrdersDMO);
			if (updateResultInt != 0) {
				TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
				tradeOrderItemsDMO.setOrderItemCancelOperatorName(memberName);
				tradeOrderItemsDMO.setOrderItemCancelOperatorId(Long.parseLong(memberID));
				tradeOrderItemsDMO.setOrderItemCancelReason(cancelReason);
				tradeOrderItemsDMO.setOrderItemCancelTime(DateUtil.getSystemTime());
				tradeOrderItemsDMO.setIsCancelOrderItem(Constant.IS_CANCEL_ORDER);
				tradeOrderItemsDMO.setOrderNo(orderNo);
				tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
				orderItemCancelDAO.updateOrderItemCancelInfoByOrderNo(tradeOrderItemsDMO);
				tradeOrdersDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
				return tradeOrdersDMO;
			}
		} catch (Exception e) {
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.ERROR.getCode());
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.orderCancel出现异常:{}",
					orderCancelInfoDTO.getMessageId(), orderNo, w.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return tradeOrdersDMO;
		}
		return tradeOrdersDMO;
	}

	@Override
	@Transactional
	public TradeOrdersDMO vmsOperateOrderCancel(OrderCancelInfoReqDTO orderCancelInfoDTO) {
		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
		String orderNo = orderCancelInfoDTO.getOrderNo();
		try {
			String messageId = orderCancelInfoDTO.getMessageId();
			tradeOrdersDMO.setOrderNo(orderNo);
			tradeOrdersDMO = orderCancelDAO.selectTradeCancelOrderByOrderNo(tradeOrdersDMO);
			if (tradeOrdersDMO == null) {
				tradeOrdersDMO = new TradeOrdersDMO();
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getMsg());
				return tradeOrdersDMO;
			}
			String orderStatus = tradeOrdersDMO.getOrderStatus();
			if (StringUtils.isEmpty((orderStatus))) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getMsg());
				return tradeOrdersDMO;
			}
			TradeOrderItemsDMO tradeOrderItemsDMOTemp = new TradeOrderItemsDMO();
			tradeOrderItemsDMOTemp.setOrderNo(orderNo);
			tradeOrderItemsDMOTemp.setChannelCode(Constant.PRODUCT_CHANNEL_CODE_OUTLINE);
			long tradeItemCount = tradeOrderItemsDAO.selectTradeOrderItemsByOrderChannelCode(tradeOrderItemsDMOTemp);
			if ((tradeItemCount != 0 && OrderStatusEnum.PAYED_PRE_SPLIT_ORDER.getCode().equals(orderStatus))
					|| OrderStatusEnum.PAYED_PRE_SPLIT_ORDER_PRE.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_SPLITED_ORDER_PRE_ERP.getCode().equals(orderStatus)) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getMsg());
				return tradeOrdersDMO;
			}
			int isCancelOrder = tradeOrdersDMO.getIsCancelOrder();
			if (OrderStatusEnum.CANCLED.getCode().equals(String.valueOf(isCancelOrder))) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getMsg());
				return tradeOrdersDMO;
			}
			String memberID = orderCancelInfoDTO.getOrderCancelMemberId();
			if (StringUtils.isEmpty(memberID)) {
				memberID = "0";
			}
			String memberName = orderCancelInfoDTO.getOrderCancelMemberName();
			String payType = tradeOrdersDMO.getPayType();
			if (OrderStatusEnum.PAYED_POST_STRIKEA_DOWNING.getCode().equals(orderStatus)
					&& !PayStatusEnum.ERP_PAY.getCode().equals(payType)) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ERP_DOWN_MQ_FAIL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ERP_DOWN_MQ_FAIL.getMsg());
				return tradeOrdersDMO;
			}
			if (OrderStatusEnum.PAYED_PRE_SPLIT_ORDER.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getCode().equals(orderStatus)
					|| OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_CHECK.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_PAY.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_CONFIRM.getCode().equals(orderStatus)) {
				if (OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode().equals(orderStatus)
						|| OrderStatusEnum.PRE_CHECK.getCode().equals(orderStatus)
						|| OrderStatusEnum.PRE_PAY.getCode().equals(orderStatus)) {
					PayOrderInfoDMO payOrderInfoDMO = new PayOrderInfoDMO();
					payOrderInfoDMO.setOrderNo(orderNo);
					payOrderInfoDMO.setDeleteFlag(Byte.valueOf("1"));
					payOrderInfoDAO.updateByRechargeOrderNo(payOrderInfoDMO);
				} else if (OrderStatusEnum.PAYED_PRE_SPLIT_ORDER.getCode().equals(orderStatus)
						|| OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getCode().equals(orderStatus)) {
					List<PayOrderInfoDMO> payOrderInfoDMOList = payOrderInfoDAO
							.selectBrandCodeAndClassCodeByOrderNo(orderNo);
					List<PayOrderInfoDMO> downPayOrderList = new ArrayList<PayOrderInfoDMO>();
					for (PayOrderInfoDMO payOrderInfoDMO : payOrderInfoDMOList) {
						LOGGER.info("MessageId:{} :{} 调用方法OrderCancelServiceImpl.vmsOperateOrderCancel收付款表订单信息为{}",
								messageId, orderCancelInfoDTO.getOrderNo(), JSON.toJSONString(payOrderInfoDMO));
						Byte payResultStatus = payOrderInfoDMO.getPayResultStatus();
						if (!OrderStatusEnum.ERP_RESULT_STATUS_HAVE_DOWN_MQ_RETURN.getCode()
								.equals(String.valueOf(payResultStatus))
								&& !PayStatusEnum.ERP_PAY.getCode().equals(payType)) {
							tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ERP_DOWN_MQ_FAIL.getCode());
							tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ERP_DOWN_MQ_FAIL.getMsg());
							return tradeOrdersDMO;
						}
						if (OrderStatusEnum.ERP_RESULT_STATUS_HAVE_DOWN_MQ_RETURN.getCode().equals(
								String.valueOf(payResultStatus)) || PayStatusEnum.ERP_PAY.getCode().equals(payType)) {
							downPayOrderList.add(payOrderInfoDMO);
							continue;
						}
					}
					for (PayOrderInfoDMO payOrderInfoDMO : downPayOrderList) {
						String lockBalanceCode = payOrderInfoDMO.getDownOrderNo();
						OtherCenterResDTO<String> otherCenterResDTO = erpMiddleWareOrderRAO.erpBalanceUnlock(messageId,
								lockBalanceCode);
						String resultCode = otherCenterResDTO.getOtherCenterResponseCode();
						payOrderInfoDMO.setDeleteFlag(Byte.valueOf("1"));
						payOrderInfoDAO.updateByDownOrderNo(payOrderInfoDMO);
						if (!ResultCodeEnum.SUCCESS.getCode().equals(resultCode)) {
							tradeOrdersDMO.setResultCode(otherCenterResDTO.getOtherCenterResponseCode());
							tradeOrdersDMO.setResultMsg(otherCenterResDTO.getOtherCenterResponseMsg());
							return tradeOrdersDMO;
						}
					}
				}
				tradeOrdersDMO = reservseResource(tradeOrdersDMO, orderNo, messageId, memberID, memberName,
						Constant.ORDER_TYPE_PARENT, null);
				String reserveResultCode = tradeOrdersDMO.getResultCode();
				if (!ResultCodeEnum.SUCCESS.getCode().equals(reserveResultCode)) {
					return tradeOrdersDMO;
				}
			}
			tradeOrdersDMO.setOrderCancelMemberId(Long.parseLong(memberID));
			tradeOrdersDMO.setOrderCancelMemberName(memberName);
			String cancelReason = orderCancelInfoDTO.getOrderCancelReason();
			tradeOrdersDMO.setOrderCancelReason(cancelReason);
			tradeOrdersDMO.setIsCancelOrder(Constant.IS_CANCEL_ORDER);
			tradeOrdersDMO.setOrderCancelTime(DateUtil.getSystemTime());
			tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
			int updateResultInt = orderCancelDAO.updateOrderCancelInfo(tradeOrdersDMO);
			if (updateResultInt != 0) {
				TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
				tradeOrderItemsDMO.setOrderItemCancelOperatorName(memberName);
				tradeOrderItemsDMO.setOrderItemCancelOperatorId(Long.parseLong(memberID));
				tradeOrderItemsDMO.setOrderItemCancelReason(cancelReason);
				tradeOrderItemsDMO.setOrderItemCancelTime(DateUtil.getSystemTime());
				tradeOrderItemsDMO.setIsCancelOrderItem(Constant.IS_CANCEL_ORDER);
				tradeOrderItemsDMO.setOrderNo(orderNo);
				tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
				orderItemCancelDAO.updateOrderItemCancelInfoByOrderNo(tradeOrderItemsDMO);
				tradeOrdersDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
				return tradeOrdersDMO;
			}
		} catch (Exception e) {
			tradeOrdersDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.vmsOperateOrderCancel出现异常{}",
					orderCancelInfoDTO.getMessageId(), orderNo, w.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return tradeOrdersDMO;
		}
		return tradeOrdersDMO;
	}

	@Override
	@Transactional
	public TradeOrderItemsDMO vmsOperateOrderItemCancel(OrderCancelInfoReqDTO orderCancelInfoDTO) {
		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
		TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
		String orderNo = orderCancelInfoDTO.getOrderNo();
		try {
			String messageId = orderCancelInfoDTO.getMessageId();
			String orderItemNo = orderCancelInfoDTO.getOrderItemNo();
			List<TradeOrderItemsDMO> tradeOrderItemsDMOs = tradeOrderItemsDAO
					.selectOrderItemsByOrderItemNo(orderItemNo);

			if (tradeOrderItemsDMOs == null || tradeOrderItemsDMOs.isEmpty()) {
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getMsg());
				return tradeOrderItemsDMO;
			}
			tradeOrderItemsDMO = tradeOrderItemsDMOs.get(0);
			String orderStatus = tradeOrderItemsDMO.getOrderItemStatus();
			if (StringUtils.isEmpty((orderStatus))) {
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getMsg());
				return tradeOrderItemsDMO;
			}
			if ((OrderStatusEnum.PAYED_PRE_SPLIT_ORDER.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_PRE_SPLIT_ORDER_PRE.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_POST_STRIKEA_DOWNING.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_SPLITED_ORDER_PRE_ERP.getCode().equals(orderStatus))) {
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getMsg());
				return tradeOrderItemsDMO;
			}
			int isCancelOrder = tradeOrderItemsDMO.getIsCancelOrderItem();
			if (OrderStatusEnum.CANCLED.getCode().equals(String.valueOf(isCancelOrder))) {
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getMsg());
				return tradeOrderItemsDMO;
			}
			String memberID = orderCancelInfoDTO.getOrderCancelMemberId();
			if (StringUtils.isEmpty(memberID)) {
				memberID = "0";
			}
			String memberName = orderCancelInfoDTO.getOrderCancelMemberName();
			if (OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_CHECK.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_PAY.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_CONFIRM.getCode().equals(orderStatus)) {
				tradeOrdersDMO.setOrderNo(orderNo);
				tradeOrdersDMO = orderCancelDAO.selectTradeCancelOrderByOrderNo(tradeOrdersDMO);
				tradeOrdersDMO = reservseResource(tradeOrdersDMO, orderItemNo, messageId, memberID, memberName,
						Constant.ORDER_TYPE_SUB, tradeOrderItemsDMOs);
				tradeOrdersDMO.setOrderStatus(orderStatus);
				String reserveResultCode = tradeOrdersDMO.getResultCode();
				if (!ResultCodeEnum.SUCCESS.getCode().equals(reserveResultCode)) {
					tradeOrderItemsDMO.setResultCode(reserveResultCode);
					tradeOrderItemsDMO.setResultMsg(tradeOrdersDMO.getResultMsg());
					return tradeOrderItemsDMO;
				}
				tradeOrderItemsDMO.setOrderItemNo(orderItemNo);
			} else if (OrderStatusEnum.VMS_ORDER_PRE_DOWN_ERP.getCode().equals(orderStatus)) {// 如果是vms开单取消订单行，就取消相同的品牌品类的订单行
				tradeOrderItemsDMO.setBrandId(tradeOrderItemsDMO.getBrandId());
				tradeOrderItemsDMO.setErpFirstCategoryCode(tradeOrderItemsDMO.getErpFirstCategoryCode());
				tradeOrderItemsDMO.setOrderItemNo(null);

				tradeOrdersDMO.setOrderNo(orderNo);
				tradeOrdersDMO = orderCancelDAO.selectTradeCancelOrderByOrderNo(tradeOrdersDMO);
				tradeOrdersDMO = batchReleaseStock4VMSItemCancle(tradeOrderItemsDMO, orderNo, tradeOrdersDMO,
						messageId);
				String reserveResultCode = tradeOrdersDMO.getResultCode();
				if (!ResultCodeEnum.SUCCESS.getCode().equals(reserveResultCode)) {
					tradeOrderItemsDMO.setResultCode(reserveResultCode);
					tradeOrderItemsDMO.setResultMsg(tradeOrdersDMO.getResultMsg());
					return tradeOrderItemsDMO;
				}
			}
			tradeOrderItemsDMO.setOrderItemCancelOperatorName(memberName);
			tradeOrderItemsDMO.setOrderItemCancelOperatorId(Long.parseLong(memberID));
			String cancelReason = orderCancelInfoDTO.getOrderCancelReason();
			tradeOrderItemsDMO.setOrderItemCancelReason(cancelReason);
			tradeOrderItemsDMO.setOrderItemCancelTime(DateUtil.getSystemTime());
			tradeOrderItemsDMO.setOrderNo(orderNo);
			tradeOrderItemsDMO.setIsCancelOrderItem(Constant.IS_CANCEL_ORDER);
			tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
			int updateResultInt = orderItemCancelDAO.updateOrderItemCancelInfoByOrderItemNo(tradeOrderItemsDMO);
			if (updateResultInt == 1) {
				Long orderItemCancelCount = tradeOrderItemsDAO.selectTradeOrderItemsByOrderNoIsNotCancel(orderNo);
				if (orderItemCancelCount == 0L) {
					tradeOrdersDMO.setOrderNo(orderNo);
					tradeOrdersDMO.setOrderCancelMemberId(Long.parseLong(memberID));
					tradeOrdersDMO.setOrderCancelMemberName(memberName);
					tradeOrdersDMO.setOrderCancelReason(cancelReason);
					tradeOrdersDMO.setIsCancelOrder(Constant.IS_CANCEL_ORDER);
					tradeOrdersDMO.setOrderCancelTime(DateUtil.getSystemTime());
					tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
					orderCancelDAO.updateOrderCancelInfo(tradeOrdersDMO);
				}
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_REFUNDSTATUS_IS_DATABASEFAIL.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_REFUNDSTATUS_IS_DATABASEFAIL.getMsg());
			}
			if (OrderStatusEnum.PRE_CHECK.getCode().equals(orderStatus)
					|| OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_PAY.getCode().equals(orderStatus)) {

				// 取消订单行更新订单表相关金额、数量等数据
				vmsOperateOrderItemCancelUpdateOrderTable(tradeOrderItemsDMO, orderNo, tradeOrdersDMO, messageId);
				BigDecimal orderItemAmount = tradeOrderItemsDMO.getOrderItemPayAmount();

				Long brandCode = tradeOrderItemsDMO.getBrandId();
				String classCode = tradeOrderItemsDMO.getErpFirstCategoryCode();
				PayOrderInfoDMO payOrderInfoDMO = new PayOrderInfoDMO();
				payOrderInfoDMO.setOrderNo(orderNo);
				payOrderInfoDMO.setBrandCode(String.valueOf(brandCode));
				payOrderInfoDMO.setClassCode(classCode);
				String channelCode = tradeOrderItemsDMO.getChannelCode();
				if (Constant.PRODUCT_CHANNEL_CODE_OUTLINE.equals(channelCode)) {
					payOrderInfoDMO.setBrandCode(MiddleWareEnum.JD_BRAND_ID_ERP.getCode());
					payOrderInfoDMO.setClassCode(MiddleWareEnum.JD_CLASS_CODE_ERP.getCode());
				}
				payOrderInfoDMO = payOrderInfoDAO.selectPayOrderInfoByOrderNo(payOrderInfoDMO);
				if (payOrderInfoDMO == null) {
					LOGGER.error(
							"MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.vmsOperateOrderItemCancel不存在收付款下行记录，订单行信息为{}",
							orderCancelInfoDTO.getMessageId(), orderCancelInfoDTO.getOrderNo(),
							JSON.toJSONString(tradeOrderItemsDMO));
					tradeOrderItemsDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
					tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
					return tradeOrderItemsDMO;
				}
				LOGGER.info("MessageId:{} OrderNo:{} 进行收付款表记录操作,订单行记录是{}，收付款记录是{}", orderCancelInfoDTO.getMessageId(),
						orderCancelInfoDTO.getOrderNo(), JSON.toJSONString(tradeOrderItemsDMO),
						JSON.toJSONString(payOrderInfoDMO));
				BigDecimal payOrderAmount = payOrderInfoDMO.getAmount();
				LOGGER.info(
						"MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.vmsOperateOrderItemCancel，收付款表金额扣减订单行金额为{}",
						orderCancelInfoDTO.getMessageId(), orderCancelInfoDTO.getOrderNo(),
						payOrderAmount.subtract(orderItemAmount));
				if (payOrderAmount.subtract(orderItemAmount).compareTo(new BigDecimal(0)) == 0) {
					LOGGER.info(
							"MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.vmsOperateOrderItemCancel，收付款表删除锁定余额子订单为{}",
							orderCancelInfoDTO.getMessageId(), payOrderInfoDMO.getDownOrderNo());
					payOrderInfoDMO.setDeleteFlag(Byte.valueOf("1"));
					payOrderInfoDMO.setOrderNo(orderNo);
					payOrderInfoDAO.updatePayorderinfoByOrderNoBrandIdClassCode(payOrderInfoDMO);
				} else {
					LOGGER.info(
							"MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.vmsOperateOrderItemCancel，收付款表更新金额锁定余额号为{}",
							orderCancelInfoDTO.getMessageId(), payOrderInfoDMO.getDownOrderNo());
					payOrderInfoDMO.setOrderNo(orderNo);
					payOrderInfoDMO.setAmount(payOrderAmount.subtract(orderItemAmount));
					payOrderInfoDAO.updatePayorderinfoByOrderNoBrandIdClassCode(payOrderInfoDMO);
				}
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
				return tradeOrderItemsDMO;
			} else if (OrderStatusEnum.VMS_ORDER_PRE_DOWN_ERP.getCode().equals(orderStatus)) {// 如果是vms开单取消订单行，就取消相同的品牌品类的订单行

				// 取消订单行更新订单表相关金额、数量等数据
				vmsOperateOrderItemCancelUpdateOrderTable(tradeOrderItemsDMO, orderNo, tradeOrdersDMO, messageId);

				TradeOrderErpDistributionDMO record = new TradeOrderErpDistributionDMO();
				record.setBrandId(tradeOrderItemsDMO.getBrandId());
				record.setErpFirstCategoryCode(tradeOrderItemsDMO.getErpFirstCategoryCode());
				record.setOrderNo(orderNo);
				record.setDeleteFlag(new Byte(OrderStatusEnum.ORDER_DELETE_STATUS.getCode()));
				tradeOrderErpDistributionDAO.updateOrderItemNosBySelective(record);
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
				return tradeOrderItemsDMO;
			}
		} catch (Exception e) {
			tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderItemNo:{} 调用方法OrderCancelServiceImpl.vmsOperateOrderItemCancel出现异常{}",
					orderCancelInfoDTO.getMessageId(), orderCancelInfoDTO.getOrderItemNo(), w.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return tradeOrderItemsDMO;

		}
		return tradeOrderItemsDMO;
	}

	/*
	 * 取消订单行更新订单表相关金额、数量等数据
	 */
	private void vmsOperateOrderItemCancelUpdateOrderTable(TradeOrderItemsDMO tradeOrderItemsDMO, String orderNo,
			TradeOrdersDMO tradeOrdersDMO, String messageId) {
		String orderStatus = tradeOrderItemsDMO.getOrderItemStatus();

		BigDecimal orderItemAmount = null;
		Integer orderItemGoodsCount = null;
		BigDecimal goodsAmount = null;
		BigDecimal orderItemTotalDiscountAmount = null;
		BigDecimal goodsFreight = null;
		BigDecimal orderItemTotalAmount = null;

		// 如果是vms开单取消订单行，订单的金额要按照品牌品类计算
		if (StringUtilHelper.isNotNull(orderStatus)
				&& OrderStatusEnum.VMS_ORDER_PRE_DOWN_ERP.getCode().equals(orderStatus)) {
			Long brandId = tradeOrderItemsDMO.getBrandId();
			String erpFirstCategoryCode = tradeOrderItemsDMO.getErpFirstCategoryCode();
			TradeOrderItemsDMO record = new TradeOrderItemsDMO();
			record.setBrandId(brandId);
			record.setErpFirstCategoryCode(erpFirstCategoryCode);
			record.setOrderNo(orderNo);
			TradeOrderItemsDMO sumTradeOrderItemsDMO = tradeOrderItemsDAO
					.selectSumItemOrderItemNoByBrandCodeClassCode(record);

			orderItemAmount = sumTradeOrderItemsDMO.getOrderItemPayAmount();
			orderItemGoodsCount = sumTradeOrderItemsDMO.getGoodsCount();
			goodsAmount = sumTradeOrderItemsDMO.getGoodsAmount();
			orderItemTotalDiscountAmount = sumTradeOrderItemsDMO.getTotalDiscountAmount();
			goodsFreight = sumTradeOrderItemsDMO.getGoodsFreight();
			orderItemTotalAmount = sumTradeOrderItemsDMO.getOrderItemTotalAmount();
		} else {
			orderItemAmount = tradeOrderItemsDMO.getOrderItemPayAmount();
			orderItemGoodsCount = tradeOrderItemsDMO.getGoodsCount();
			goodsAmount = tradeOrderItemsDMO.getGoodsAmount();
			orderItemTotalDiscountAmount = tradeOrderItemsDMO.getTotalDiscountAmount();
			goodsFreight = tradeOrderItemsDMO.getGoodsFreight();
			orderItemTotalAmount = tradeOrderItemsDMO.getOrderItemTotalAmount();
		}

		TradeOrdersDMO tradeOrdersDMOTemp = tradeOrdersDAO.selectOrderByOrderNo(orderNo);
		Integer orderGoodsCount = tradeOrdersDMOTemp.getTotalGoodsCount();
		BigDecimal orderTotalDiscountAmount = tradeOrdersDMOTemp.getTotalDiscountAmount();
		BigDecimal orderTotalFreight = tradeOrdersDMOTemp.getTotalFreight();
		BigDecimal orderGoodsAmount = tradeOrdersDMOTemp.getTotalGoodsAmount();
		BigDecimal orderAmount = tradeOrdersDMOTemp.getOrderPayAmount();
		BigDecimal orderTotalAmout = tradeOrdersDMOTemp.getOrderTotalAmount();
		tradeOrdersDMO.setOrderPayAmount(orderAmount.subtract(orderItemAmount));
		tradeOrdersDMO.setOrderTotalAmount(orderTotalAmout.subtract(orderItemTotalAmount));
		tradeOrdersDMO.setTotalGoodsCount(orderGoodsCount - orderItemGoodsCount);
		tradeOrdersDMO.setTotalGoodsAmount(orderGoodsAmount.subtract(goodsAmount));
		tradeOrdersDMO.setTotalFreight(orderTotalFreight.subtract(goodsFreight));
		tradeOrdersDMO.setTotalDiscountAmount(orderTotalDiscountAmount.subtract(orderItemTotalDiscountAmount));
		tradeOrdersDMO.setOrderNo(orderNo);
		// 修改订单状态，异常显示，支付状态(针对vms开单才更新的这个字段，vms是先开单，下行回调的时候才扣钱)
		orderStatusChangeCommonService.updateOrderErrorVMSPayStatus(tradeOrdersDMO);
		tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
		LOGGER.info("MessageId:{} OrderNo:{}调用方法OrderCancelServiceImpl.vmsOperateOrderItemCancel更新订单信息为{}", messageId,
				orderNo, JSON.toJSONString(tradeOrdersDMO));
		tradeOrdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
	}

	@Override
	public TradeOrderItemsDMO orderItemCancel(OrderCancelInfoReqDTO orderCancelInfoDTO) {
		TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
		// try {
		// String orderNo = orderCancelInfoDTO.getOrderNo();
		// String messageId = orderCancelInfoDTO.getMessageId();
		// String buyerCode = orderCancelInfoDTO.getMemberCode();
		// tradeOrdersDMO.setOrderNo(orderNo);
		// tradeOrdersDMO.setBuyerCode(buyerCode);
		// tradeOrdersDMO =
		// orderCancelDAO.selectTradeCancelOrderByOrderNo(tradeOrdersDMO);
		// if (tradeOrdersDMO == null) {
		// tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST
		// .getCode());
		// tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST
		// .getMsg());
		// return tradeOrderItemsDMO;
		// }
		// String orderStatus = tradeOrdersDMO.getOrderStatus();
		// if (StringUtils.isEmpty((orderStatus))) {
		// tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL
		// .getCode());
		// tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL
		// .getMsg());
		// return tradeOrderItemsDMO;
		// }
		// if (!OrderStatusEnum.PRE_CHECK.getCode().equals(orderStatus)
		// && !OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode().equals(orderStatus)
		// && !OrderStatusEnum.PRE_PAY.getCode().equals(orderStatus)) {
		// tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL
		// .getCode());
		// tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL
		// .getMsg());
		// return tradeOrderItemsDMO;
		// }
		//
		// String memberID = orderCancelInfoDTO.getOrderCancelMemberId();
		// String memberName = orderCancelInfoDTO.getOrderCancelMemberName();
		// if (OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode().equals(orderStatus)
		// || OrderStatusEnum.PRE_CHECK.getCode().equals(orderStatus)
		// || OrderStatusEnum.PRE_PAY.getCode().equals(orderStatus))
		// {
		// tradeOrdersDMO = reservseResource(tradeOrdersDMO, orderNo, messageId,
		// memberID,
		// memberName, Constant.ORDER_TYPE_SUB);
		// String reserveResultCode = tradeOrdersDMO.getResultCode();
		// if (!ResultCodeEnum.SUCCESS.getCode().equals(reserveResultCode)) {
		// tradeOrderItemsDMO.setResultCode(reserveResultCode);
		// tradeOrderItemsDMO.setResultMsg(tradeOrdersDMO.getResultMsg());
		// return tradeOrderItemsDMO;
		// }
		// }
		// tradeOrderItemsDMO.setOrderItemCancelOperatorName(memberName);
		// tradeOrderItemsDMO.setOrderItemCancelOperatorId(Long.parseLong(memberID));
		// String cancelReason = orderCancelInfoDTO.getOrderCancelReason();
		// tradeOrderItemsDMO.setOrderItemCancelReason(cancelReason);
		// tradeOrderItemsDMO.setOrderItemCancelTime(DateUtil.getSystemTime());
		// tradeOrderItemsDMO.setOrderNo(orderNo);
		// String orderItemNo = orderCancelInfoDTO.getOrderItemNo();
		// tradeOrderItemsDMO.setOrderItemNo(orderItemNo);
		// tradeOrderItemsDMO.setIsCancelOrderItem(Constant.IS_CANCEL_ORDER);
		// int updateResultInt =
		// orderItemCancelDAO.updateOrderItemCancelInfo(tradeOrderItemsDMO);
		// if (updateResultInt == 1) {
		// Long orderItemCancelCount = tradeOrderItemsDAO
		// .selectTradeOrderItemsByOrderNoIsNotCancel(orderNo);
		// if (orderItemCancelCount == 0L) {
		// tradeOrdersDMO.setOrderNo(orderNo);
		// tradeOrdersDMO.setOrderCancelMemberId(Long.parseLong(memberID));
		// tradeOrdersDMO.setOrderCancelMemberName(memberName);
		// tradeOrdersDMO.setOrderCancelReason(cancelReason);
		// tradeOrdersDMO.setIsCancelOrder(Constant.IS_CANCEL_ORDER);
		// tradeOrdersDMO.setOrderCancelTime(DateUtil.getSystemTime());
		// orderCancelDAO.updateOrderCancelInfo(tradeOrdersDMO);
		// }
		// tradeOrderItemsDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		// tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		// }
		// } catch (Exception e) {
		// tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
		// tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
		// StringWriter w = new StringWriter();
		// e.printStackTrace(new PrintWriter(w));
		// LOGGER.error("MessageId:{}
		// 调用方法OrderCancelServiceImpl.orderItemCancel出现异常{}",
		// orderCancelInfoDTO.getMessageId(), w.toString());
		// }
		return tradeOrderItemsDMO;
	}

	/**
	 * @param tradeOrdersDMO
	 * @param orderNo
	 * @param messageId
	 * @param memberID
	 * @param memberName
	 * @throws NumberFormatException
	 */
	private TradeOrdersDMO reservseResource(TradeOrdersDMO tradeOrdersDMO, String orderNo, String messageId,
			String memberID, String memberName, String orderType, List<TradeOrderItemsDMO> tradeItemOrderDmoParam)
					throws Exception {
		int isTimeLimitedOrder = tradeOrdersDMO.getIsTimelimitedOrder();
		int hasUsedCoupon = tradeOrdersDMO.getHasUsedCoupon();
		LOGGER.info("MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.reservseResource输入参数:{}", messageId, orderNo,
				JSON.toJSONString(tradeOrdersDMO));
		// 订单状态有311 和 312的 所以必须截取后判断订单状态
		String orderstatus = tradeOrdersDMO.getOrderStatus().substring(0, 2);
		// 如果有促销就进入下面的逻辑(因为限时购的标志位在trade_order_items_discount表里)
		List<TradeOrderItemsDiscountDMO> tradeOrderItemsDiscountDMOList = new ArrayList<TradeOrderItemsDiscountDMO>();
		if (Constant.ORDER_TYPE_PARENT.equals(orderType)) {
			tradeOrderItemsDiscountDMOList = tradeOrderItemsDiscountDAO.selectBuyerCouponCodeByOrderNo(orderNo);
		} else if (Constant.ORDER_TYPE_SUB.equals(orderType)) {
			tradeOrderItemsDiscountDMOList = tradeOrderItemsDiscountDAO.selectBuyerCouponCodeByOrderItemNo(orderNo);
		}

		if ((isTimeLimitedOrder == Constant.IS_TIMELIMITED_ORDER || hasUsedCoupon == Constant.HAS_USED_COUPON
				|| CollectionUtils.isNotEmpty(tradeOrderItemsDiscountDMOList)) && Integer.valueOf(orderstatus) < 30) {

			List<OrderItemPromotionDTO> orderItemPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
			for (TradeOrderItemsDiscountDMO tradeOrderItemsDiscountDMO : tradeOrderItemsDiscountDMOList) {
				OrderItemPromotionDTO orderItemPromotionDTO = new OrderItemPromotionDTO();
				orderItemPromotionDTO.setOrderNo(tradeOrderItemsDiscountDMO.getOrderNo());
				String orderItemNo = tradeOrderItemsDiscountDMO.getOrderItemNo();
				orderItemPromotionDTO.setOrderItemNo(orderItemNo);
				String buyerCode = tradeOrderItemsDiscountDMO.getBuyerCode();
				orderItemPromotionDTO.setBuyerCode(buyerCode);
				if (isTimeLimitedOrder == Constant.IS_TIMELIMITED_ORDER) {
					orderItemPromotionDTO.setQuantity(1);
				}
				if (hasUsedCoupon == Constant.HAS_USED_COUPON) {
					String couponCode = tradeOrderItemsDiscountDMO.getBuyerCouponCode();
					orderItemPromotionDTO.setCouponCode(couponCode);
					BigDecimal discountAmount = tradeOrderItemsDiscountDMO.getCouponDiscount();
					orderItemPromotionDTO.setDiscountAmount(discountAmount);
				}
				String promotionId = tradeOrderItemsDiscountDMO.getPromotionId();
				orderItemPromotionDTO.setPromotionId(promotionId);
				String promotionType = tradeOrderItemsDiscountDMO.getPromotionType();
				orderItemPromotionDTO.setPromotionType(promotionType);
				String levelCode = tradeOrderItemsDiscountDMO.getLevelCode();
				orderItemPromotionDTO.setLevelCode(levelCode);
				orderItemPromotionDTO.setOperaterId(Long.valueOf(memberID));
				if (StringUtils.isEmpty(memberName)) {
					memberName = "System";
				}
				orderItemPromotionDTO.setOperaterName(memberName);
				orderItemPromotionDTOList.add(orderItemPromotionDTO);
			}
			OtherCenterResDTO<String> otherCenterResDTO = marketCenterRAO
					.releaseBuyerPromotion(orderItemPromotionDTOList, messageId);
			String responseCode = otherCenterResDTO.getOtherCenterResponseCode();
			if (!ResultCodeEnum.SUCCESS.getCode().equals(responseCode)) {
				tradeOrdersDMO.setResultCode(responseCode);
				tradeOrdersDMO.setResultMsg(otherCenterResDTO.getOtherCenterResponseMsg());
				return tradeOrdersDMO;
			}
		}
		if (isTimeLimitedOrder != Constant.IS_TIMELIMITED_ORDER) {
			Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
			List<TradeOrderItemsDMO> tradeItemOrderDmos = new ArrayList<TradeOrderItemsDMO>();
			if (Constant.ORDER_TYPE_PARENT.equals(orderType)) {
				tradeItemOrderDmos = tradeOrderItemsDAO.selectOrderItemsByOrderNo(orderNo);
			} else if (Constant.ORDER_TYPE_SUB.equals(orderType)) {
				tradeItemOrderDmos = tradeItemOrderDmoParam;
			}

			List<Order4StockEntryDTO> order4StockEntryDTOList = new ArrayList<Order4StockEntryDTO>();
			String orderResource = tradeOrdersDMO.getOrderFrom();
			for (TradeOrderItemsDMO tradeOrderItemsDMO : tradeItemOrderDmos) {
				String channelCode = tradeOrderItemsDMO.getChannelCode();
				if (Constant.PRODUCT_CHANNEL_CODE_OUTLINE.equals(channelCode)
						|| (Constant.PRODUCT_CHANNEL_CODE_INNER.equals(channelCode)
								&& Integer.valueOf(orderstatus) >= 40)
						|| (Constant.PRODUCT_CHANNEL_CODE_OUTER.equals(channelCode)
								&& Integer.valueOf(orderstatus) >= 30)) {
					continue;
				}
				Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
				String skuCode = tradeOrderItemsDMO.getSkuCode();
				order4StockEntryDTO.setSkuCode(skuCode);
				int goodCount = tradeOrderItemsDMO.getGoodsCount();
				order4StockEntryDTO.setQuantity(goodCount);
				Integer isChangePrice = tradeOrderItemsDMO.getIsChangePrice();
				Integer bargainingGoodsCount = tradeOrderItemsDMO.getBargainingGoodsCount();
				if (isChangePrice == 1 && bargainingGoodsCount != 0) {
					order4StockEntryDTO.setQuantity(bargainingGoodsCount);
				}
				order4StockEntryDTO.setStockTypeEnum(StockTypeEnum.RELEASE);
				int isBoxFlag = tradeOrderItemsDMO.getIsBoxFlag();
				order4StockEntryDTO.setIsBoxFlag(isBoxFlag);
				// 如果没有从优惠表里查到数据，说明可以锁定商品中心库存，如果查到数据且活动类型不是3(限时购)，也可以锁定商品中心库存
				List<TradeOrderItemsDiscountDMO> tradeOrderItemsDiscountPurchaseList = tradeOrderItemsDiscountDAO
						.selectBuyerCouponCodeByOrderItemNo(tradeOrderItemsDMO.getOrderItemNo());
				if (null == tradeOrderItemsDiscountPurchaseList || tradeOrderItemsDiscountPurchaseList.size() == 0) {
					order4StockEntryDTOList.add(order4StockEntryDTO);
				} else {
					TradeOrderItemsDiscountDMO tradeOrderItemsDiscountDMO = tradeOrderItemsDiscountPurchaseList.get(0);
					if (!OrderStatusEnum.PROMOTION_TYPE_LIMITED_TIME_PURCHASE.getCode()
							.equals(tradeOrderItemsDiscountDMO.getPromotionType())) {
						order4StockEntryDTOList.add(order4StockEntryDTO);
					}
				}

			}
			if (order4StockEntryDTOList != null && order4StockEntryDTOList.size() != 0) {
				order4StockChangeDTO.setOrderNo(tradeOrdersDMO.getOrderNo());
				order4StockChangeDTO.setOrderResource(orderResource);
				order4StockChangeDTO.setMessageId(messageId);
				order4StockChangeDTO.setOrderEntries(order4StockEntryDTOList);
				List<Order4StockChangeDTO> order4StockChangeDTOList = new ArrayList<Order4StockChangeDTO>();
				order4StockChangeDTOList.add(order4StockChangeDTO);
				OtherCenterResDTO<String> otherCenterResDTO = null;
				// //如果议价就调用comboChangeStock释放库存(不校验订单生命周期)
				// if(null != tradeOrdersDMO.getIsChangePrice() &&
				// tradeOrdersDMO.getIsChangePrice()==1){
				// otherCenterResDTO = goodsCenterRAO.comboChangeStock(
				// order4StockChangeDTOList, messageId);
				// }else{
				otherCenterResDTO = goodsCenterRAO.batchReleaseStock(order4StockChangeDTOList, messageId);
				// }
				String stockReleaseResCode = otherCenterResDTO.getOtherCenterResponseCode();
				if (!ResultCodeEnum.SUCCESS.getCode().equals(stockReleaseResCode)) {
					tradeOrdersDMO.setResultCode(stockReleaseResCode);
					tradeOrdersDMO.setResultMsg(otherCenterResDTO.getOtherCenterResponseMsg());
					return tradeOrdersDMO;
				}
			}
		}
		tradeOrdersDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		return tradeOrdersDMO;
	}

	/*
	 * 批量释放商品库存
	 */
	private TradeOrdersDMO batchReleaseStock4VMSItemCancle(TradeOrderItemsDMO tradeOrderItemsDMO, String orderNo,
			TradeOrdersDMO tradeOrdersDMO, String messageId) {
		LOGGER.info("MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.batchReleaseStock4VMSItemCancle输入参数:{}",
				messageId, orderNo, JSON.toJSONString(tradeOrdersDMO));
		Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
		List<Order4StockEntryDTO> order4StockEntryDTOList = new ArrayList<Order4StockEntryDTO>();
		TradeOrderItemsDMO record = new TradeOrderItemsDMO();
		record.setOrderNo(orderNo);
		record.setBrandId(tradeOrderItemsDMO.getBrandId());
		record.setErpFirstCategoryCode(tradeOrderItemsDMO.getErpFirstCategoryCode());
		List<TradeOrderItemsDMO> sameBrandCodeClassCodeOrder = tradeOrderItemsDAO
				.selectAllItemOrderItemNoByBrandCodeClassCode(record);

		for (TradeOrderItemsDMO tradeOrderItemsDMOTemp : sameBrandCodeClassCodeOrder) {
			Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
			String skuCode = tradeOrderItemsDMOTemp.getSkuCode();
			order4StockEntryDTO.setSkuCode(skuCode);
			int goodCount = tradeOrderItemsDMOTemp.getGoodsCount();
			order4StockEntryDTO.setQuantity(goodCount);
			Integer isChangePrice = tradeOrderItemsDMOTemp.getIsChangePrice();
			Integer bargainingGoodsCount = tradeOrderItemsDMOTemp.getBargainingGoodsCount();
			if (isChangePrice == 1 && bargainingGoodsCount != 0) {
				order4StockEntryDTO.setQuantity(bargainingGoodsCount);
			}
			order4StockEntryDTO.setStockTypeEnum(StockTypeEnum.RELEASE);
			int isBoxFlag = tradeOrderItemsDMOTemp.getIsBoxFlag();
			order4StockEntryDTO.setIsBoxFlag(isBoxFlag);
			order4StockEntryDTOList.add(order4StockEntryDTO);
		}

		order4StockChangeDTO.setOrderNo(tradeOrdersDMO.getOrderNo());
		order4StockChangeDTO.setOrderResource(tradeOrdersDMO.getOrderFrom());
		order4StockChangeDTO.setMessageId(messageId);
		order4StockChangeDTO.setOrderEntries(order4StockEntryDTOList);
		List<Order4StockChangeDTO> order4StockChangeDTOList = new ArrayList<Order4StockChangeDTO>();
		order4StockChangeDTOList.add(order4StockChangeDTO);

		OtherCenterResDTO<String> otherCenterResDTO = null;
		// // 如果议价就调用comboChangeStock释放库存(不校验订单生命周期)
		// if (null != tradeOrdersDMO.getIsChangePrice() &&
		// tradeOrdersDMO.getIsChangePrice() == 1) {
		// otherCenterResDTO =
		// goodsCenterRAO.comboChangeStock(order4StockChangeDTOList,
		// messageId);
		// } else {
		otherCenterResDTO = goodsCenterRAO.batchReleaseStock(order4StockChangeDTOList, messageId);
		// }

		String stockReleaseResCode = otherCenterResDTO.getOtherCenterResponseCode();
		if (!ResultCodeEnum.SUCCESS.getCode().equals(stockReleaseResCode)) {
			LOGGER.info("MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.batchReleaseStock4VMSItemCancle结束,释放商品库存失败",
					messageId, orderNo);
			tradeOrdersDMO.setResultCode(stockReleaseResCode);
			tradeOrdersDMO.setResultMsg(otherCenterResDTO.getOtherCenterResponseMsg());
			return tradeOrdersDMO;
		}

		tradeOrdersDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		LOGGER.info("MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.batchReleaseStock4VMSItemCancle结束", messageId,
				orderNo);
		return tradeOrdersDMO;
	}

	@Override
	@Transactional
	public TradeOrdersDMO orderDelete(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception {
		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
		String orderNo = orderCancelInfoDTO.getOrderNo();
		try {
			String memberCode = orderCancelInfoDTO.getMemberCode();
			tradeOrdersDMO.setOrderNo(orderNo);
			tradeOrdersDMO.setBuyerCode(memberCode);
			tradeOrdersDMO = orderCancelDAO.selectTradeCancelOrderByOrderNo(tradeOrdersDMO);
			String orderStatus = tradeOrdersDMO.getOrderStatus();
			if (StringUtils.isEmpty((orderStatus))) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERDELETE_ORDERSTATUS_IS_NULL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERDELETE_ORDERSTATUS_IS_NULL.getMsg());
				return tradeOrdersDMO;
			}
			int isCancelOrder = tradeOrdersDMO.getIsCancelOrder();
			if (!OrderStatusEnum.BUYER_RECEIPT.getCode().equals(orderStatus)
					&& !OrderStatusEnum.EXPIRE_RECEIPT.getCode().equals(orderStatus)
					&& !OrderStatusEnum.CANCLED.getCode().equals(String.valueOf(isCancelOrder))) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERDELETE_ORDERSTATUS_IS_FAIL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERDELETE_ORDERSTATUS_IS_FAIL.getMsg());
				return tradeOrdersDMO;
			}
			int orderDeleteStatus = tradeOrdersDMO.getOrderDeleteStatus();
			String deleteStatus = orderCancelInfoDTO.getIsDeleteStatus();
			if (orderDeleteStatus == Integer.valueOf(OrderStatusEnum.ORDER_NOT_DELETE_STATUS.getCode())) {
				if (OrderStatusEnum.ORDER_DELETE_STATUS.getCode().equals(deleteStatus)) {
					tradeOrdersDMO.setOrderDeleteStatus(Integer.valueOf(deleteStatus));
					orderCancelDAO.updateOrderCancelInfo(tradeOrdersDMO);
					tradeOrdersDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
					tradeOrdersDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
					return tradeOrdersDMO;
				}
			} else if (orderDeleteStatus == Integer.valueOf(OrderStatusEnum.ORDER_DELETE_STATUS.getCode())) {
				if (OrderStatusEnum.ORDER_THROUGH_DELETE_STATUS.getCode().equals(deleteStatus)
						|| OrderStatusEnum.ORDER_RESTORE_DELETE_STATUS.getCode().equals(deleteStatus)) {
					tradeOrdersDMO.setOrderDeleteStatus(Integer.valueOf(deleteStatus));
					orderCancelDAO.updateOrderCancelInfo(tradeOrdersDMO);
					tradeOrdersDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
					tradeOrdersDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
					return tradeOrdersDMO;
				}
			} else if (orderDeleteStatus == Integer.valueOf(OrderStatusEnum.ORDER_THROUGH_DELETE_STATUS.getCode())) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERDELETE_ORDERSTATUS_IS_THROUGH_FAIL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERDELETE_ORDERSTATUS_IS_THROUGH_FAIL.getMsg());
				return tradeOrdersDMO;
			}
			LOGGER.warn("MessageId:{} OrderNo:{}调用方法OrderCancelServiceImpl.ordeDelete订单状态错误,不能从{}状态到{}状态",
					orderCancelInfoDTO.getMessageId(), new Object[] { orderNo, orderDeleteStatus, deleteStatus });
			tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERDELETE_ORDERSTATUS_IS_TORSION_FAIL.getCode());
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERDELETE_ORDERSTATUS_IS_TORSION_FAIL.getMsg());
		} catch (Exception e) {
			tradeOrdersDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.orderDelete出现异常{}",
					orderCancelInfoDTO.getMessageId(), orderNo, w.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return tradeOrdersDMO;
		}
		return tradeOrdersDMO;
	}
	
	@Transactional
	@Override
	public TradeOrderItemsDMO cancelOrderItemForNewVMS(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception {

		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
		TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
		String orderNo = orderCancelInfoDTO.getOrderNo();
		try {
			String messageId = orderCancelInfoDTO.getMessageId();
			String orderItemNo = orderCancelInfoDTO.getOrderItemNo();
			List<TradeOrderItemsDMO> tradeOrderItemsDMOs = tradeOrderItemsDAO.selectOrderItemsByOrderItemNo(orderItemNo);
			//订单行不存在
			if (CollectionUtils.isEmpty(tradeOrderItemsDMOs)) {
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getMsg());
				return tradeOrderItemsDMO;
			}
			tradeOrderItemsDMO = tradeOrderItemsDMOs.get(0);
			String orderStatus = tradeOrderItemsDMO.getOrderItemStatus();
			if (StringUtils.isEmpty((orderStatus))) {
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getMsg());
				return tradeOrderItemsDMO;
			}
			//这几种状态不能取消订单
			if ((OrderStatusEnum.PAYED_PRE_SPLIT_ORDER.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_PRE_SPLIT_ORDER_PRE.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_POST_STRIKEA_DOWNING.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_SPLITED_ORDER_PRE_ERP.getCode().equals(orderStatus))) {
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getMsg());
				return tradeOrderItemsDMO;
			}
			
			//是否已经取消
			if (OrderStatusEnum.CANCLED.getCode().equals(String.valueOf(tradeOrderItemsDMO.getIsCancelOrderItem()))) {
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getMsg());
				return tradeOrderItemsDMO;
			}
			String memberID = StringUtils.isEmpty(orderCancelInfoDTO.getOrderCancelMemberId()) ? "0" : orderCancelInfoDTO.getOrderCancelMemberId();
			
			String memberName = orderCancelInfoDTO.getOrderCancelMemberName();
			if (OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_CHECK.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_PAY.getCode().equals(orderStatus)
					|| OrderStatusEnum.PRE_CONFIRM.getCode().equals(orderStatus)) {
				tradeOrdersDMO.setOrderNo(orderNo);
				tradeOrdersDMO = orderCancelDAO.selectTradeCancelOrderByOrderNo(tradeOrdersDMO);
				tradeOrdersDMO.setOrderStatus(orderStatus);
				tradeOrderItemsDMO.setOrderItemNo(orderItemNo);
			} else if (OrderStatusEnum.VMS_ORDER_PRE_DOWN_ERP.getCode().equals(orderStatus)) {// 如果是vms开单取消订单行，就取消相同的品牌品类的订单行
				tradeOrderItemsDMO.setBrandId(tradeOrderItemsDMO.getBrandId());
				tradeOrderItemsDMO.setErpFirstCategoryCode(tradeOrderItemsDMO.getErpFirstCategoryCode());
				tradeOrderItemsDMO.setOrderItemNo(null);

				tradeOrdersDMO.setOrderNo(orderNo);
				tradeOrdersDMO = orderCancelDAO.selectTradeCancelOrderByOrderNo(tradeOrdersDMO);
			}
			tradeOrderItemsDMO.setOrderItemCancelOperatorName(memberName);
			tradeOrderItemsDMO.setOrderItemCancelOperatorId(Long.parseLong(memberID));
			String cancelReason = orderCancelInfoDTO.getOrderCancelReason();
			tradeOrderItemsDMO.setOrderItemCancelReason(cancelReason);
			tradeOrderItemsDMO.setOrderItemCancelTime(DateUtil.getSystemTime());
			tradeOrderItemsDMO.setOrderNo(orderNo);
			tradeOrderItemsDMO.setIsCancelOrderItem(Constant.IS_CANCEL_ORDER);
			tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
			int updateResultInt = orderItemCancelDAO.updateOrderItemCancelInfoByOrderItemNo(tradeOrderItemsDMO);
			if(updateResultInt < 1){
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_REFUNDSTATUS_IS_DATABASEFAIL.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_REFUNDSTATUS_IS_DATABASEFAIL.getMsg());
				return tradeOrderItemsDMO;
			}

			//查询主订单状态是否有订单行取消标志
			Long orderItemCancelCount = tradeOrderItemsDAO.selectTradeOrderItemsByOrderNoIsNotCancel(orderNo);
			//如果没有，则要在主订单上标记一下
			if (orderItemCancelCount == 0L) {
				tradeOrdersDMO.setOrderNo(orderNo);
				tradeOrdersDMO.setOrderCancelMemberId(Long.parseLong(memberID));
				tradeOrdersDMO.setOrderCancelMemberName(memberName);
				tradeOrdersDMO.setOrderCancelReason(cancelReason);
				tradeOrdersDMO.setIsCancelOrder(Constant.IS_CANCEL_ORDER);
				tradeOrdersDMO.setOrderCancelTime(DateUtil.getSystemTime());
				tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
				orderCancelDAO.updateOrderCancelInfo(tradeOrdersDMO);
			}
			tradeOrderItemsDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		
			//处在下行中订单，要更新TradeOrderErpDistribution相关表
			if (OrderStatusEnum.VMS_ORDER_PRE_DOWN_ERP.getCode().equals(orderStatus)) {// 如果是vms开单取消订单行，就取消相同的品牌品类的订单行
				// 取消订单行更新订单表相关金额、数量等数据
				vmsOperateOrderItemCancelUpdateOrderTable(tradeOrderItemsDMO, orderNo, tradeOrdersDMO, messageId);
				TradeOrderErpDistributionDMO record = new TradeOrderErpDistributionDMO();
				record.setBrandId(tradeOrderItemsDMO.getBrandId());
				record.setErpFirstCategoryCode(tradeOrderItemsDMO.getErpFirstCategoryCode());
				record.setOrderNo(orderNo);
				record.setDeleteFlag(new Byte(OrderStatusEnum.ORDER_DELETE_STATUS.getCode()));
				tradeOrderErpDistributionDAO.updateOrderItemNosBySelective(record);
				tradeOrderItemsDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
				tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
				return tradeOrderItemsDMO;
			}
		} catch (Exception e) {
			tradeOrderItemsDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			tradeOrderItemsDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderItemNo:{} 调用方法OrderCancelServiceImpl.cancelOrderItemForNewVMS 出现异常{}",
					orderCancelInfoDTO.getMessageId(), orderCancelInfoDTO.getOrderItemNo(), w.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return tradeOrderItemsDMO;
		}
		return tradeOrderItemsDMO;
	}

	@Override
	public TradeOrdersDMO cancelOrderForNewVMS(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception {
		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
		String orderNo = orderCancelInfoDTO.getOrderNo();
		try {
			tradeOrdersDMO.setOrderNo(orderNo);
			tradeOrdersDMO = orderCancelDAO.selectTradeCancelOrderByOrderNo(tradeOrdersDMO);
			//订单不存在
			if (tradeOrdersDMO == null) {
				tradeOrdersDMO = new TradeOrdersDMO();
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDER_IS_NOT_EXIST.getMsg());
				return tradeOrdersDMO;
			}
			String orderStatus = tradeOrdersDMO.getOrderStatus();
			//订单状态异常
			if (StringUtils.isEmpty((orderStatus))) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_NULL.getMsg());
				return tradeOrdersDMO;
			}
			
			//这几种状态不能取消订单
			if ((OrderStatusEnum.PAYED_PRE_SPLIT_ORDER.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_PRE_SPLIT_ORDER_PRE.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_POST_STRIKEA_DOWNING.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getCode().equals(orderStatus)
					|| OrderStatusEnum.PAYED_SPLITED_ORDER_PRE_ERP.getCode().equals(orderStatus))) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_FAIL.getMsg());
				return tradeOrdersDMO;
			}
			
			//已取消
			if (OrderStatusEnum.CANCLED.getCode().equals(String.valueOf(tradeOrdersDMO.getIsCancelOrder()))) {
				tradeOrdersDMO.setResultCode(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.ORDERCANCEL_ORDERSTATUS_IS_CANCEL.getMsg());
				return tradeOrdersDMO;
			}
			String memberID = StringUtils.isEmpty(orderCancelInfoDTO.getOrderCancelMemberId())?"0":orderCancelInfoDTO.getOrderCancelMemberId();
			
			String memberName = orderCancelInfoDTO.getOrderCancelMemberName();

			tradeOrdersDMO.setOrderCancelMemberId(Long.parseLong(memberID));
			tradeOrdersDMO.setOrderCancelMemberName(memberName);
			String cancelReason = orderCancelInfoDTO.getOrderCancelReason();
			tradeOrdersDMO.setOrderCancelReason(cancelReason);
			tradeOrdersDMO.setIsCancelOrder(Constant.IS_CANCEL_ORDER);
			tradeOrdersDMO.setOrderCancelTime(DateUtil.getSystemTime());
			tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
			int updateResultInt = orderCancelDAO.updateOrderCancelInfo(tradeOrdersDMO);
			if (updateResultInt != 0) {
				TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
				tradeOrderItemsDMO.setOrderItemCancelOperatorName(memberName);
				tradeOrderItemsDMO.setOrderItemCancelOperatorId(Long.parseLong(memberID));
				tradeOrderItemsDMO.setOrderItemCancelReason(cancelReason);
				tradeOrderItemsDMO.setOrderItemCancelTime(DateUtil.getSystemTime());
				tradeOrderItemsDMO.setIsCancelOrderItem(Constant.IS_CANCEL_ORDER);
				tradeOrderItemsDMO.setOrderNo(orderNo);
				tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
				orderItemCancelDAO.updateOrderItemCancelInfoByOrderNo(tradeOrderItemsDMO);
				tradeOrdersDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
				tradeOrdersDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
				return tradeOrdersDMO;
			}
		} catch (Exception e) {
			tradeOrdersDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} OrderNo:{} 调用方法OrderCancelServiceImpl.cancelOrderForNewVMS 出现异常{}",
					orderCancelInfoDTO.getMessageId(), orderNo, w.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return tradeOrdersDMO;
		}
		return tradeOrdersDMO;
	}
}
