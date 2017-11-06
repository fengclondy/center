package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.membercenter.dto.BoxAddDto;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberCompanyInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberGradeService;
import cn.htd.zeus.tc.biz.dao.JDOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.OrderPaymentResultDAO;
import cn.htd.zeus.tc.biz.dao.PayOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDiscountDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsPriceHistoryDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderItemPaymentDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPayInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPayResultInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPayValidateIsBargainCancleResDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultListDMO;
import cn.htd.zeus.tc.biz.dmo.OrderQueryParamDMO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderErpDistributionDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDiscountDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsPriceHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.rao.GoodsCenterRAO;
import cn.htd.zeus.tc.biz.rao.MarketCenterRAO;
import cn.htd.zeus.tc.biz.service.OrderPaymentResultService;
import cn.htd.zeus.tc.biz.service.TradeOrderItemStatusHistoryService;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.GoodCenterEnum;
import cn.htd.zeus.tc.common.enums.MemberCenterEnum;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.PayStatusEnum;
import cn.htd.zeus.tc.common.enums.PayTypeEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.CalculateUtils;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.JSONUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.EmptyResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPayValidateIsBargainCancleListReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPayValidateIsBargainCancleReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPaymentResultReqDTO;

@Service
public class OrderPaymentResultServiceImpl implements OrderPaymentResultService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderPaymentResultServiceImpl.class);

	@Autowired
	private OrderPaymentResultDAO orderPaymentResultDAO;

	@Autowired
	private MemberBaseInfoService memberBaseInfoService;

	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;

	@Autowired
	private MemberGradeService memberGradeService;

	@Autowired
	private TradeOrdersDAO traderdersDAO;

	@Autowired
	private JDOrderInfoDAO jdOrderInfoDAO;

	@Autowired
	private PayOrderInfoDAO paymentOrderInfoDAO;

	@Autowired
	private GoodsCenterRAO goodsCenterRAO;

	@Autowired
	private MarketCenterRAO marketCenterRAO;

	@Autowired
	private TradeOrderItemsDiscountDAO tradeOrderItemsDiscountDAO;

	@Autowired
	private TradeOrderItemsPriceHistoryDAO tradeOrderItemsPriceHistoryDAO;

	@Autowired
	private TradeOrderStatusHistoryService tradeOrderStatusHistoryService;

	@Autowired
	private TradeOrderItemStatusHistoryService tradeOrderItemStatusHistoryService;

	private static final int ZREO = 0;// 常量0

	private static final String ORDER_STATUS = "1";// 更新会员经验值（商城购买）

	private static final Integer ORDER_TYPE = 1;// vip套餐商品（京东待下行）

	@Override
	public OrderPaymentResultListDMO selectPaymentResultByTradeNo(
			OrderPaymentResultReqDTO orderPaymentResultReqDTO) {

		OrderPaymentResultListDMO orderPaymentResultListDMO = new OrderPaymentResultListDMO();

		try {
			OrderPaymentResultDMO orderPaymentResultDMO = new OrderPaymentResultDMO();
			List<String> orderNo = new ArrayList<String>();

			String jsonStr = orderPaymentResultReqDTO.getBatchPayInfos();
			List<OrderPayInfoDMO> transList = JSONUtil.parseArray(jsonStr, OrderPayInfoDMO.class);
			for (OrderPayInfoDMO orderPayInfoDMO : transList) {
				orderNo.add(orderPayInfoDMO.getMerchantOrderNo());
				orderPaymentResultDMO.setOrderNoList(orderNo);
			}

			int resultCount = orderPaymentResultDAO.seleteCountByTradeNo(orderPaymentResultDMO);
			List<OrderPaymentResultDMO> orderPaymentResultDMOList = orderPaymentResultDAO
					.selectPaymentResultByOrderNo(orderPaymentResultDMO);
			if (resultCount == orderPaymentResultDMOList.size()) {
				orderPaymentResultListDMO.setOrderPaymentResultDMO(orderPaymentResultDMOList);
				orderPaymentResultListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
				orderPaymentResultListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				orderPaymentResultListDMO
						.setResultCode(ResultCodeEnum.ORDERPAYMENTRESULT_IS_ERROR.getCode());
				orderPaymentResultListDMO
						.setResultMsg(ResultCodeEnum.ORDERPAYMENTRESULT_IS_ERROR.getMsg());
			}
		} catch (Exception e) {
			orderPaymentResultListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderPaymentResultListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderPaymentResultServiceImpl.selectPaymentResultByTradeNo出现异常:{}",
					orderPaymentResultReqDTO.getMessageId(), w.toString());
		}

		return orderPaymentResultListDMO;
	}

	/**
	 * 支付回调
	 */
	@Override
	@Transactional
	public OrderPaymentResultDMO updateOrderStatusByTradeNo(
			OrderPayResultInfoDMO orderPayResultInfoDMO) {

		OrderPaymentResultDMO orderPaymentResultDMO = new OrderPaymentResultDMO();

		try {

			OrderPaymentResultDMO orderPaymentResultDMOTemp = new OrderPaymentResultDMO();
			orderPaymentResultDMOTemp.setOrderNo(orderPayResultInfoDMO.getOrderNo());
			orderPaymentResultDMOTemp.setPayType(orderPayResultInfoDMO.getPayType());
			orderPaymentResultDMOTemp.setPayChannel(orderPayResultInfoDMO.getPayChannel());
			orderPaymentResultDMOTemp.setPayStatus(orderPayResultInfoDMO.getPayStatus());
			orderPaymentResultDMOTemp.setPayOrderTime(DateUtil.getSystemTime());
			orderPaymentResultDMOTemp.setOrderNo(orderPayResultInfoDMO.getOrderNo());
			orderPaymentResultDMOTemp.setBuyerCode(orderPayResultInfoDMO.getBuyerCode());
			orderPaymentResultDMOTemp.setOrderPayAmount(orderPayResultInfoDMO.getOrderPayAmount());

			// 会员信息
			MemberCompanyInfoDTO memberCompanyInfoDTO = new MemberCompanyInfoDTO();
			memberCompanyInfoDTO.setBuyerSellerType(
					Integer.parseInt(MemberCenterEnum.MEMBER_TYPE_BUYER.getCode()));
			memberCompanyInfoDTO.setMemberCode(orderPaymentResultDMOTemp.getBuyerCode());
			ExecuteResult<MemberCompanyInfoDTO> result = memberBaseInfoService
					.selectMobilePhoneMemberId(memberCompanyInfoDTO);
			MemberCompanyInfoDTO memberDTO = new MemberCompanyInfoDTO();
			if (result.getResult() != null) {
				memberDTO = result.getResult();
			} else {
				orderPaymentResultDMO
						.setResultMsg(ResultCodeEnum.ORDERSETTLEMENT_MEMBER_INFO_NULL.getMsg());
				orderPaymentResultDMO
						.setResultCode(ResultCodeEnum.ORDERSETTLEMENT_MEMBER_INFO_NULL.getCode());
				return orderPaymentResultDMO;
			}

			if (PayStatusEnum.SUCCESS.getCode().equals(orderPayResultInfoDMO.getPayStatus())) {
				OrderQueryParamDMO orderQueryParamDMO = new OrderQueryParamDMO();
				orderQueryParamDMO.setOrderNo(orderPaymentResultDMOTemp.getOrderNo());
				TradeOrdersDMO record = traderdersDAO.selectOrderByPrimaryKey(orderQueryParamDMO);
				List<TradeOrderItemsDMO> itemsList = record.getOrderItemsList();
				// 判断是否是外部供应商，是——订单状态待发货，否——订单状态已支付待下行
				if (itemsList.get(0).getChannelCode()
						.equals(GoodCenterEnum.EXTERNAL_SUPPLIER.getCode())) {
					orderPaymentResultDMOTemp
							.setOrderStatus(OrderStatusEnum.PRE_DELIVERY.getCode());
					orderPaymentResultDMOTemp
							.setOrderStatusText(OrderStatusEnum.PRE_DELIVERY.getMsg());
				} else {
					orderPaymentResultDMOTemp
							.setOrderStatus(OrderStatusEnum.PAYED_PRE_SPLIT_ORDER.getCode());
					orderPaymentResultDMOTemp
							.setOrderStatusText(OrderStatusEnum.PAYED_PRE_SPLIT_ORDER.getMsg());
				}
				orderPaymentResultDMOTemp.setModifyId(memberDTO.getMemberId());
				orderPaymentResultDMOTemp.setModifyName(memberDTO.getCompanyName());
				orderPaymentResultDMOTemp.setModifyTime(DateUtil.getSystemTime());

				// 库存扣减
				String channelCode = itemsList.get(0).getChannelCode();
				EmptyResDTO reduceResDTO = inventoryDeduction(record, memberDTO, channelCode);
				if (!reduceResDTO.getResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
					orderPaymentResultDMO.setResultMsg(reduceResDTO.getReponseMsg());
					orderPaymentResultDMO.setResultCode(reduceResDTO.getResponseCode());
					return orderPaymentResultDMO;
				}
				BigDecimal orderPayAmount = orderPaymentResultDAO
						.selectSumOrderPayAmountByOrderNo(orderPaymentResultDMOTemp.getOrderNo());
				BigDecimal bigDecimal = orderPayResultInfoDMO.getOrderPayAmount();
				String messageId = orderPayResultInfoDMO.getMessageId();
				if (null != orderPayAmount && orderPayAmount
						.compareTo(bigDecimal.setScale(2, BigDecimal.ROUND_DOWN)) != 0) {
					orderPaymentResultDMOTemp.setOrderErrorStatus(Constant.IS_ERROR_ORDER);
					orderPaymentResultDMOTemp.setOrderErrorTime(new Date());
					orderPaymentResultDMOTemp.setOrderErrorReason(Constant.ORDER_ERROR_REASON);
					LOGGER.error(
							"MessageId:{} 调用方法OrderPaymentResultServiceImpl.updateOrderStatusByTradeNo出现异常:{}",
							messageId, "订单号" + orderPayResultInfoDMO.getOrderNo() + "金额被篡改");
				}
				if (record.getIsCancelOrder().equals(Constant.IS_CANCEL_ORDER)) {
					orderPaymentResultDMOTemp.setOrderErrorStatus(Constant.IS_ERROR_ORDER);
					orderPaymentResultDMOTemp.setOrderErrorTime(new Date());
					orderPaymentResultDMOTemp.setOrderErrorReason(Constant.ORDER_ERROR_REASON2);
					orderPaymentResultDMOTemp.setIsCancelOrder(Constant.IS_NOT_CANCEL_ORDER);
					LOGGER.error(
							"MessageId:{} 调用方法OrderPaymentResultServiceImpl.updateOrderStatusByTradeNo出现异常:{}",
							messageId, "订单号" + orderPayResultInfoDMO.getOrderNo() + "已取消订单被支付");
				}
				List<TradeOrderItemsDMO> orderItemList = record.getOrderItemsList();
				if (CollectionUtils.isNotEmpty(orderItemList)) {
					BigDecimal orderAmount = BigDecimal.ZERO;
					for (TradeOrderItemsDMO orderItem : orderItemList) {
						if (orderItem.getIsCancelOrderItem().equals(Constant.IS_NOT_CANCEL_ORDER)) {
							orderAmount = orderAmount.add(
									CalculateUtils.setScale(orderItem.getOrderItemPayAmount()));
						}
					}
					if (orderAmount.compareTo(CalculateUtils
							.setScale(orderPayResultInfoDMO.getOrderPayAmount())) != 0) {
						orderPaymentResultDMOTemp.setOrderErrorStatus(Constant.IS_ERROR_ORDER);
						orderPaymentResultDMOTemp.setOrderErrorTime(new Date());
						orderPaymentResultDMOTemp.setOrderErrorReason(Constant.ORDER_ERROR_REASON3
								+ ",实际支付金额：" + orderPayResultInfoDMO.getOrderPayAmount() + "订单金额应为："
								+ orderAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					}
				}
				int resultCount = orderPaymentResultDAO
						.updateOrderStatusByTradeNo(orderPaymentResultDMOTemp);
				if (resultCount > ZREO) {

					// 更新会员经验值
					modifyUserGradeExp(orderPaymentResultDMOTemp, messageId);

					// 创建往来关系
					orderAfterDownErp(record, memberDTO, messageId);

					// 新增订单状态履历表
					insertTradeOrderStatusHistory(orderPaymentResultDMOTemp, messageId);

					// 插入京东待发订单信息表(下行)
					EmptyResDTO jdResDTO = insertJDOrderInfo(record, memberDTO);
					if (!jdResDTO.getResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
						orderPaymentResultDMO.setResultMsg(jdResDTO.getReponseMsg());
						orderPaymentResultDMO.setResultCode(jdResDTO.getResponseCode());
						return orderPaymentResultDMO;
					}

					// 更新收付款下行表
					EmptyResDTO payResDTO = updateByRechargeOrderNo(orderPaymentResultDMOTemp);
					if (!payResDTO.getResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
						orderPaymentResultDMO.setResultMsg(payResDTO.getReponseMsg());
						orderPaymentResultDMO.setResultCode(payResDTO.getResponseCode());
						return orderPaymentResultDMO;
					}

					String flag = orderPayResultInfoDMO.getFlag();
					if ("1".equals(flag)) {
						// 转换订单行佣金参数
						List<OrderItemPaymentDMO> itemList = new ArrayList<OrderItemPaymentDMO>();
						if (CollectionUtils.isNotEmpty(orderPayResultInfoDMO.getCommissionList())) {
							String marketCouponListString = JSON
									.toJSONString(orderPayResultInfoDMO.getCommissionList());
							itemList = JSON.parseObject(marketCouponListString,
									new TypeReference<ArrayList<OrderItemPaymentDMO>>() {
									});
							LOGGER.info(
									"MessageId:{} 调用方法OrderPaymentResultAPIImpl.updateOrderItemCommissionByItemNo入参:{}",
									JSONObject.toJSONString(itemList));
							if (CollectionUtils.isNotEmpty(itemList)) {
								for (OrderItemPaymentDMO itemDMO : itemList) {
									LOGGER.info(
											"MessageId:{} 调用方法OrderPaymentResultAPIImpl.updateOrderItemCommissionByItemNo入参:{}",
											JSONObject.toJSONString(itemDMO));
									tradeOrderItemsDAO.updateOrderItemCommissionByItemNo(itemDMO);
								}
							}
						}
					} else {
						List<TradeOrderItemsDMO> orderItemsList = tradeOrderItemsDAO
								.selectOrderItemsByOrderNo(orderPayResultInfoDMO.getOrderNo());
						if (CollectionUtils.isNotEmpty(orderItemsList)) {
							for (TradeOrderItemsDMO orderItem : orderItemsList) {
								if (Constant.PRODUCT_CHANNEL_CODE_OUTLINE
										.equals(orderItem.getChannelCode())) {
									BigDecimal payOrderAmount = orderItem.getOrderItemPayAmount();
									BigDecimal costPrice = orderItem.getCostPrice();
									int count = orderItem.getGoodsCount();
									BigDecimal ratio = orderItem.getCommissionRatio();
									OrderItemPaymentDMO itemDMO = new OrderItemPaymentDMO();
									itemDMO.setItemOrderNo(orderItem.getOrderItemNo());
									itemDMO.setAmount(ratio.multiply(payOrderAmount
											.subtract(costPrice.multiply(new BigDecimal(count)))));
									LOGGER.info(
											"MessageId:{} 调用方法OrderPaymentResultAPIImpl.updateOrderItemCommissionByItemNo入参:{}",
											JSONObject.toJSONString(itemDMO));
									tradeOrderItemsDAO.updateOrderItemCommissionByItemNo(itemDMO);
								}
							}
						}
					}
					// 更新订单行状态
					tradeOrderItemsDAO.updateOrderItemStatusByTradeNo(orderPaymentResultDMOTemp);

					if (itemsList.size() > ZREO) {
						for (TradeOrderItemsDMO tradeOrderItemsDMO : itemsList) {
							tradeOrderItemsDMO
									.setOrderItemStatus(orderPaymentResultDMOTemp.getOrderStatus());
							tradeOrderItemsDMO.setOrderItemStatusText(
									orderPaymentResultDMOTemp.getOrderStatusText());

							// 新增订单行状态履历表
							insertTradeOrderItemsStatusHistory(tradeOrderItemsDMO, memberDTO);

							// 判断是否是vip商品，是——开通会员
							if (tradeOrderItemsDMO.getIsVipItem() != null
									&& GoodCenterEnum.IS_VIP_GOODS.getCode()
											.equals(tradeOrderItemsDMO.getIsVipItem().toString())) {

								// 开通会员Vip
								modifyMemberGradeAndPackageTypeById(tradeOrderItemsDMO, memberDTO,
										messageId);

							}
						}
					}
				} else {
					orderPaymentResultDMO.setResultCode(
							ResultCodeEnum.ORDERSTATUS_ORDER_STATUS_UPDATE_FAIL.getCode());
					orderPaymentResultDMO.setResultMsg(
							ResultCodeEnum.ORDERSTATUS_ORDER_STATUS_UPDATE_FAIL.getMsg());
					return orderPaymentResultDMO;
				}
			} else {
				orderPaymentResultDAO.updateOrderStatusByTradeNo(orderPaymentResultDMOTemp);
			}

			orderPaymentResultDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			orderPaymentResultDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			orderPaymentResultDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderPaymentResultDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderPaymentResultServiceImpl.updateOrderStatusByTradeNo出现异常:{}",
					orderPayResultInfoDMO.getMessageId(), w.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return orderPaymentResultDMO;
	}

	/*
	 * 插入订单状态履历
	 */
	private void insertTradeOrderStatusHistory(final OrderPaymentResultDMO orderPaymentResultDMO,
			final String messageId) {
		try {
			new Thread(new Runnable() {
				public void run() {
					TradeOrderStatusHistoryDMO record = new TradeOrderStatusHistoryDMO();
					record.setOrderNo(orderPaymentResultDMO.getOrderNo());
					record.setOrderStatus(orderPaymentResultDMO.getOrderStatus());
					record.setOrderStatusText(orderPaymentResultDMO.getOrderStatusText());
					record.setCreateId(orderPaymentResultDMO.getModifyId());
					record.setCreateName(orderPaymentResultDMO.getModifyName());
					LOGGER.info("MessageId:{}【支付回调】【插入订单状态履历开始】--组装查询参数开始:{}", messageId,
							JSONObject.toJSONString(record));
					tradeOrderStatusHistoryService.insertTradeOrderStatusHistory(record);
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 插入订单状态履历表出现异常-(此异常不需要回滚):{}", messageId, w.toString());
		}
	}

	/*
	 * 插入订单行状态履历
	 */
	private void insertTradeOrderItemsStatusHistory(final TradeOrderItemsDMO tradeOrderItemsDMO,
			final MemberCompanyInfoDTO memberDTO) {
		try {
			new Thread(new Runnable() {
				public void run() {
					TradeOrderItemsStatusHistoryDMO record = new TradeOrderItemsStatusHistoryDMO();
					record.setOrderItemNo(tradeOrderItemsDMO.getOrderItemNo());
					record.setOrderItemStatus(tradeOrderItemsDMO.getOrderItemStatus());
					record.setOrderItemStatusText(tradeOrderItemsDMO.getOrderItemStatusText());
					record.setCreateId(memberDTO.getMemberId());
					record.setCreateName(memberDTO.getCompanyName());
					LOGGER.info("【支付回调】【插入订单行状态履历开始】--组装查询参数开始:{}",
							JSONObject.toJSONString(record));
					tradeOrderItemStatusHistoryService.insertTradeOrderItemStatusHistory(record);
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("插入订单行状态履历表出现异常-(此异常不需要回滚)");
		}
	}

	/*
	 * 开通会员Vip
	 */
	private void modifyMemberGradeAndPackageTypeById(final TradeOrderItemsDMO tradeOrderItemsDMO,
			final MemberCompanyInfoDTO memberDTO, final String messageId) {
		try {
			new Thread(new Runnable() {
				public void run() {
					MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
					memberBaseDTO.setMemberPackageType(tradeOrderItemsDMO.getVipItemType());
					memberBaseDTO.setIsVip(tradeOrderItemsDMO.getVipSyncFlag().toString());
					memberBaseDTO.setPackageActiveStartTime(DateUtil.getSystemTime());
					memberBaseDTO.setMemberId(memberDTO.getMemberId().toString());
					memberBaseDTO.setOperateName(memberDTO.getCompanyName());
					memberBaseDTO.setOperateId(memberDTO.getMemberId().toString());
					LOGGER.info("MessageId{}【支付回调】【开通会员Vip开始】--组装查询参数开始:{}", messageId,
							JSONObject.toJSONString(memberBaseDTO));
					ExecuteResult<Boolean> result = memberGradeService
							.modifyMemberGradeAndPackageTypeById(memberBaseDTO);
					if (result.getResult() == false) {
						memberGradeService.modifyMemberGradeAndPackageTypeById(memberBaseDTO);
					} else {
						LOGGER.warn("开通会员Vip成功");
					}
					LOGGER.info("MessageId:{}【支付回调】【开通会员Vip结束】--结果:{}", messageId,
							JSONObject.toJSONString(result));
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{}开通会员Vip失败:{}", messageId, w.toString());
		}
	}

	/*
	 * 插入京东待发订单信息表
	 */
	private EmptyResDTO insertJDOrderInfo(final TradeOrdersDMO record,
			final MemberCompanyInfoDTO memberDTO) {
		EmptyResDTO emptyResDTO = new EmptyResDTO();
		TradeOrderErpDistributionDMO erpDistributionDMO = new TradeOrderErpDistributionDMO();
		erpDistributionDMO.setBuyerCode(record.getBuyerCode());
		erpDistributionDMO.setSellerCode(record.getSellerCode());
		erpDistributionDMO.setOrderNo(record.getOrderNo());

		List<TradeOrderItemsDMO> itemsList = record.getOrderItemsList();
		for (TradeOrderItemsDMO tradeOrderItemsDMO : itemsList) {
			if (GoodCenterEnum.JD_SUPPLIER.getCode().equals(tradeOrderItemsDMO.getChannelCode())) {
				if (StringUtilHelper.isNotNull(tradeOrderItemsDMO.getOuterChannelOrderNo())) {
					JDOrderInfoDMO jdOrderInfoDMO = new JDOrderInfoDMO();
					jdOrderInfoDMO.setOrderNo(record.getOrderNo());
					jdOrderInfoDMO.setJdorderNo(tradeOrderItemsDMO.getOuterChannelOrderNo());
					jdOrderInfoDMO.setOrderItemNo(tradeOrderItemsDMO.getOrderItemNo());
					LOGGER.info("【支付回调】【插入京东待发订单信息表开始】--组装查询参数开始:{}",
							JSONObject.toJSONString(jdOrderInfoDMO));
					int count = jdOrderInfoDAO.updateJDOrderInfoByOrderNo(jdOrderInfoDMO);
					if (count == ZREO) {
						int result = jdOrderInfoDAO.insertSelective(jdOrderInfoDMO);
						LOGGER.info("【支付回调】【插入京东待发订单信息表结束】--结果:{}", count + "-" + result);
						if (result == ZREO) {
							emptyResDTO.setReponseMsg(
									ResultCodeEnum.GOODSCENTER_INSERT_JDORDERINFO_FAILED.getMsg());
							emptyResDTO.setResponseCode(
									ResultCodeEnum.GOODSCENTER_INSERT_JDORDERINFO_FAILED.getCode());
							return emptyResDTO;
						}
					}

				}
			} else if (tradeOrderItemsDMO.getIsVipItem() != null && GoodCenterEnum.IS_VIP_GOODS
					.getCode().equals(tradeOrderItemsDMO.getIsVipItem().toString())) {
				JDOrderInfoDMO jdOrderInfoDMO = new JDOrderInfoDMO();

				List<PayOrderInfoDMO> payOrderInfoList = paymentOrderInfoDAO
						.selectBrandCodeAndClassCodeByOrderNo(record.getOrderNo());
				String orderNo = "";
				if (null != payOrderInfoList && payOrderInfoList.size() > 0
						&& null != payOrderInfoList.get(0)
						&& StringUtilHelper.isNotNull(payOrderInfoList.get(0).getDownOrderNo())) {
					orderNo = payOrderInfoList.get(0).getDownOrderNo();
				}
				jdOrderInfoDMO.setOrderNo(orderNo);
				jdOrderInfoDMO.setOrderType(ORDER_TYPE);
				jdOrderInfoDMO.setOrderItemNo(tradeOrderItemsDMO.getOrderItemNo());
				LOGGER.info("【支付回调】【插入京东待发订单信息表开始】--组装查询参数开始:{}",
						JSONObject.toJSONString(jdOrderInfoDMO));
				int count = jdOrderInfoDAO.updateJDOrderInfoByOrderNo(jdOrderInfoDMO);
				if (count == ZREO) {
					int result = jdOrderInfoDAO.insertSelective(jdOrderInfoDMO);
					LOGGER.info("【支付回调】【插入京东待发订单信息表结束】--结果:{}", count + "-" + result);
					if (result == ZREO) {
						emptyResDTO.setReponseMsg(
								ResultCodeEnum.GOODSCENTER_INSERT_JDORDERINFO_FAILED.getMsg());
						emptyResDTO.setResponseCode(
								ResultCodeEnum.GOODSCENTER_INSERT_JDORDERINFO_FAILED.getCode());
						return emptyResDTO;
					}
				}
			}
		}
		emptyResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		emptyResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());

		return emptyResDTO;
	}

	/*
	 * 创建往来关系
	 */
	private void orderAfterDownErp(final TradeOrdersDMO tradeOrdersDMO,
			final MemberCompanyInfoDTO memberDTO, final String messageId) {
		try {
			new Thread(new Runnable() {
				public void run() {
					BoxAddDto boxAddDto = new BoxAddDto();
					boxAddDto.setMemberCode(tradeOrdersDMO.getBuyerCode());
					boxAddDto.setModifyId(memberDTO.getMemberId());
					boxAddDto.setModifyName(memberDTO.getCompanyName());
					boxAddDto.setSupplierCode(tradeOrdersDMO.getSellerCode());
					List<TradeOrderItemsDMO> itemsList = tradeOrdersDMO.getOrderItemsList();
					for (TradeOrderItemsDMO tradeOrderItemsDMO : itemsList) {
						boxAddDto.setBrandCode(tradeOrderItemsDMO.getBrandId().toString());
						boxAddDto.setClassCategoryCode(
								tradeOrderItemsDMO.getThirdCategoryId().toString());
						LOGGER.info("【支付回调】【创建往来关系开始】--组装参数:{}",
								JSONObject.toJSONString(boxAddDto));
						ExecuteResult<Boolean> result = memberBaseInfoService
								.orderAfterDownErp(boxAddDto);
						if (result.getResult() == false) {
							memberBaseInfoService.orderAfterDownErp(boxAddDto);
						} else {
							LOGGER.info("更新会员经验值成功");
						}
						LOGGER.info("MessageId:{}【支付回调】【创建往来关系结束】--结果:{}", messageId,
								JSONObject.toJSONString(result));
					}
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 创建往来关系失败{}", messageId, w.toString());
		}
	}

	/*
	 * 更新会员经验值
	 */
	private void modifyUserGradeExp(final OrderPaymentResultDMO orderPaymentResultDMO,
			final String messageId) {
		try {
			new Thread(new Runnable() {
				public void run() {
					String orderNo = orderPaymentResultDMO.getOrderNo();
					String memberCode = orderPaymentResultDMO.getBuyerCode();
					BigDecimal orderPrice = orderPaymentResultDMO.getOrderPayAmount();
					Date orderTime = orderPaymentResultDMO.getPayOrderTime();
					LOGGER.info("【支付回调】【更新会员经验值开始】");
					ExecuteResult<Boolean> result = memberGradeService.modifyUserGradeExp(orderNo,
							memberCode, orderTime, orderPrice, ORDER_STATUS);
					if (result.getResult() == false) {
						memberGradeService.modifyUserGradeExp(orderNo, memberCode, orderTime,
								orderPrice, ORDER_STATUS);
					} else {
						LOGGER.info("更新会员经验值成功");
					}
					LOGGER.info("MessageId:{}【支付回调】【更新会员经验值结束】--结果:{}", messageId,
							JSONObject.toJSONString(result));
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 更新会员经验值失败{}", messageId, w.toString());
		}
	}

	/*
	 * 更新收付款下行表
	 */
	private EmptyResDTO updateByRechargeOrderNo(final OrderPaymentResultDMO orderPaymentResultDMO) {
		EmptyResDTO emptyResDTO = new EmptyResDTO();

		List<PayOrderInfoDMO> orderList = paymentOrderInfoDAO
				.selectBrandCodeAndClassCodeByOrderNo(orderPaymentResultDMO.getOrderNo());
		if (null != orderList) {
			PayOrderInfoDMO payOrderInfoDMO = new PayOrderInfoDMO();
			payOrderInfoDMO.setOrderNo(orderPaymentResultDMO.getOrderNo());
			payOrderInfoDMO.setPayStatus(PayTypeEnum.SUCCESS.getCode());
			payOrderInfoDMO.setPayType(new Byte(orderPaymentResultDMO.getPayType().toString()));
			for (PayOrderInfoDMO payOrderDMO : orderList) {
				TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
				tradeOrderItemsDMO.setOrderNo(payOrderDMO.getOrderNo());
				if (MiddleWareEnum.JD_CLASS_CODE_ERP.getCode().equals(payOrderDMO.getClassCode())
						&& MiddleWareEnum.JD_BRAND_ID_ERP.getCode()
								.equals(payOrderDMO.getBrandCode())) {
					tradeOrderItemsDMO.setChannelCode(GoodCenterEnum.JD_SUPPLIER.getCode());
				} else {
					tradeOrderItemsDMO.setErpFirstCategoryCode(payOrderDMO.getClassCode());
					tradeOrderItemsDMO.setBrandId(Long.valueOf(payOrderDMO.getBrandCode()));
				}
				// LOGGER.info("【支付回调】【查询分销金额开始】--组装查询参数开始:{}",
				// JSONObject.toJSONString(payOrderInfoDMO));
				// BigDecimal amount = tradeOrderItemsDAO
				// .selectSumAmountByBrandCodeAndClassCode(tradeOrderItemsDMO);
				// LOGGER.info("【支付回调】【查询分销金额结束】--结果:{}",
				// JSONObject.toJSONString(amount));
				// payOrderInfoDMO.setAmount(amount);
				payOrderInfoDMO.setBrandCode(payOrderDMO.getBrandCode());
				payOrderInfoDMO.setClassCode(payOrderDMO.getClassCode());

				LOGGER.info("【支付回调】【更新收付款下行表开始】--组装查询参数开始:{}",
						JSONObject.toJSONString(payOrderInfoDMO));
				int result = paymentOrderInfoDAO.updateByRechargeOrderNo(payOrderInfoDMO);
				LOGGER.info("【支付回调】【更新收付款下行表结束】--结果:{}", JSONObject.toJSONString(result));
				if (result == ZREO) {
					emptyResDTO.setReponseMsg(
							ResultCodeEnum.GOODSCENTER_UPDATE_PAYORDERINFO_FAILED.getMsg());
					emptyResDTO.setResponseCode(
							ResultCodeEnum.GOODSCENTER_UPDATE_PAYORDERINFO_FAILED.getCode());
					return emptyResDTO;
				}
			}
		}
		emptyResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		emptyResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		return emptyResDTO;
	}

	/*
	 * 库存扣减
	 */
	private EmptyResDTO inventoryDeduction(final TradeOrdersDMO record,
			final MemberCompanyInfoDTO memberDTO, final String channelCode) {

		EmptyResDTO emptyResDTO = new EmptyResDTO();

		String messageId = GenerateIdsUtil.generateId(null);

		// 秒杀扣减
		if (OrderStatusEnum.IS_TIMELIMITED_ORDER.getCode()
				.equals(record.getIsTimelimitedOrder().toString())) {

			String promotionType = OrderStatusEnum.PROMOTION_TYPE_SECKILL.getCode();

			EmptyResDTO marketResDTO = reduceBuyerPromotion(record, messageId, promotionType,
					memberDTO);
			if (!marketResDTO.getResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
				emptyResDTO.setReponseMsg(marketResDTO.getReponseMsg());
				emptyResDTO.setResponseCode(marketResDTO.getResponseCode());
				return emptyResDTO;
			}
			// 优惠券扣减
		} else if (OrderStatusEnum.HAS_USED_COUPON.getCode()
				.equals(record.getHasUsedCoupon().toString())) {

			String promotionType = OrderStatusEnum.PROMOTION_TYPE_COUPON.getCode();

			EmptyResDTO marketResDTO = reduceBuyerPromotion(record, messageId, promotionType,
					memberDTO);
			if (!marketResDTO.getResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
				emptyResDTO.setReponseMsg(marketResDTO.getReponseMsg());
				emptyResDTO.setResponseCode(marketResDTO.getResponseCode());
				return emptyResDTO;
			}
			// 只扣减外部供应商的库存
			if (GoodCenterEnum.EXTERNAL_SUPPLIER.getCode().equals(channelCode)) {
				EmptyResDTO goodsResDTO = batchReduceStock(record, messageId);
				if (!goodsResDTO.getResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
					emptyResDTO.setReponseMsg(goodsResDTO.getReponseMsg());
					emptyResDTO.setResponseCode(goodsResDTO.getResponseCode());
					return emptyResDTO;
				}
			}
			// 只扣减外部供应商的库存
		} else {
			if (GoodCenterEnum.EXTERNAL_SUPPLIER.getCode().equals(channelCode)) {
				String promotionType = "";
				EmptyResDTO marketResDTO = reduceBuyerPromotion(record, messageId, promotionType,
						memberDTO);
				if (!marketResDTO.getResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
					emptyResDTO.setReponseMsg(marketResDTO.getReponseMsg());
					emptyResDTO.setResponseCode(marketResDTO.getResponseCode());
					return emptyResDTO;
				}
				
				EmptyResDTO goodsResDTO = batchReduceStock(record, messageId);
				if (!goodsResDTO.getResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
					emptyResDTO.setReponseMsg(goodsResDTO.getReponseMsg());
					emptyResDTO.setResponseCode(goodsResDTO.getResponseCode());
					return emptyResDTO;
				}
			}

		}
		emptyResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		emptyResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());

		return emptyResDTO;
	}

	/*
	 * 调商品中心批量扣减库存
	 */
	private EmptyResDTO batchReduceStock(final TradeOrdersDMO record, final String messageId) {
		EmptyResDTO emptyResDTO = new EmptyResDTO();
		try {
			emptyResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			emptyResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			List<Order4StockChangeDTO> order4StockChangeDTOs = new ArrayList<Order4StockChangeDTO>();
			Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
			order4StockChangeDTO.setOrderNo(record.getOrderNo());
			order4StockChangeDTO.setOrderResource(record.getOrderFrom());
			order4StockChangeDTO.setMessageId(messageId);
			List<Order4StockEntryDTO> orderEntries = new ArrayList<Order4StockEntryDTO>();

			List<TradeOrderItemsDMO> itemsList = record.getOrderItemsList();
			for (TradeOrderItemsDMO itemsDMO : itemsList) {
				Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
				order4StockEntryDTO.setSkuCode(itemsDMO.getSkuCode());
				order4StockEntryDTO.setIsBoxFlag(itemsDMO.getIsBoxFlag());
				order4StockEntryDTO.setQuantity(itemsDMO.getGoodsCount());
				List<TradeOrderItemsDiscountDMO> tradeOrderItemsDiscountDMOList = tradeOrderItemsDiscountDAO
						.selectBuyerCouponCodeByOrderItemNo(itemsDMO.getOrderItemNo());
				//如果没有从优惠表里查到数据，说明可以锁定商品中心库存，如果查到数据且活动类型不是3(限时购)，也可以锁定商品中心库存
				if (null == tradeOrderItemsDiscountDMOList
						|| tradeOrderItemsDiscountDMOList.size() == 0) {
					orderEntries.add(order4StockEntryDTO);
				} else {
					TradeOrderItemsDiscountDMO tradeOrderItemsDiscountDMO = tradeOrderItemsDiscountDMOList
							.get(0);
					if (!OrderStatusEnum.PROMOTION_TYPE_LIMITED_TIME_PURCHASE
							.getCode().equals(
									tradeOrderItemsDiscountDMO
											.getPromotionType())) {
						orderEntries.add(order4StockEntryDTO);
					}
				}
			}
			if(CollectionUtils.isEmpty(orderEntries)){
				return emptyResDTO;
			}
			order4StockChangeDTO.setOrderEntries(orderEntries);
			order4StockChangeDTOs.add(order4StockChangeDTO);

			LOGGER.info("【支付回调】【调商品中心批量扣减库存开始】--组装查询参数开始:{}",
					JSONObject.toJSONString(order4StockChangeDTOs));
			OtherCenterResDTO<String> result = goodsCenterRAO
					.batchReduceStock(order4StockChangeDTOs, messageId);
			LOGGER.info("【支付回调】【调商品中心批量扣减库存结束】--结果:{}", JSONObject.toJSONString(result));
			if (!result.getOtherCenterResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
				emptyResDTO.setReponseMsg(result.getOtherCenterResponseMsg());
				emptyResDTO.setResponseCode(result.getOtherCenterResponseCode());
				return emptyResDTO;
			}
		} catch (Exception e) {
			emptyResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			emptyResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("MessageId:{} 调商品中心批量扣减库存失败:{}",messageId,w.toString());
		}

		return emptyResDTO;
	}

	/*
	 * 扣减会员优惠券、秒杀
	 */
	private EmptyResDTO reduceBuyerPromotion(final TradeOrdersDMO record, final String messageId,
			final String promotionType, final MemberCompanyInfoDTO memberDTO) {

		EmptyResDTO emptyResDTO = new EmptyResDTO();
		try {
			emptyResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			emptyResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			
			List<OrderItemPromotionDTO> orderItemPromotionList = new ArrayList<OrderItemPromotionDTO>();

			List<TradeOrderItemsDiscountDMO> discountList = tradeOrderItemsDiscountDAO
					.selectBuyerCouponCodeByOrderNo(record.getOrderNo());
			for (TradeOrderItemsDiscountDMO discountDMO : discountList) {
				OrderItemPromotionDTO orderItemPromotionDTO = new OrderItemPromotionDTO();
				orderItemPromotionDTO.setBuyerCode(discountDMO.getBuyerCode());
				if (OrderStatusEnum.PROMOTION_TYPE_COUPON.getCode().equals(promotionType)
						&& !OrderStatusEnum.PROMOTION_TYPE_LIMITED_TIME_PURCHASE.getCode().equals(discountDMO.getPromotionType())) {
					orderItemPromotionDTO.setCouponCode(discountDMO.getBuyerCouponCode());
					orderItemPromotionDTO.setDiscountAmount(discountDMO.getCouponDiscount());
				}
				orderItemPromotionDTO.setLevelCode(discountDMO.getLevelCode());
				orderItemPromotionDTO.setOperaterId(memberDTO.getMemberId());
				orderItemPromotionDTO.setOperaterName(memberDTO.getCompanyName());
				orderItemPromotionDTO.setOrderItemNo(discountDMO.getOrderItemNo());
				orderItemPromotionDTO.setOrderNo(discountDMO.getOrderNo());
				List<TradeOrderItemsDMO> itemsList = record.getOrderItemsList();
				for (TradeOrderItemsDMO itemsDMO : itemsList) {
					if (itemsDMO.getOrderItemNo().equals(discountDMO.getOrderItemNo())) {
						if(StringUtils.isNotEmpty(discountDMO.getPromotionType())){
							orderItemPromotionDTO.setPromotionType(discountDMO.getPromotionType());
						}else{
							orderItemPromotionDTO.setPromotionType(promotionType);
						}
						if(OrderStatusEnum.PROMOTION_TYPE_LIMITED_TIME_PURCHASE.getCode().equals(discountDMO.getPromotionType())){
							//异步通知小名统计限时购销量
							asyncNoticeMarketStatisticalSales(discountDMO,itemsDMO,messageId);
						}
						orderItemPromotionDTO.setPromotionId(discountDMO.getPromotionId());
						orderItemPromotionDTO.setQuantity(itemsDMO.getGoodsCount());
						orderItemPromotionList.add(orderItemPromotionDTO);
						break;
					}
				}
				
			}
			//如果orderItemPromotionList为空，说明没有限时购，故返回成功
			if(CollectionUtils.isEmpty(orderItemPromotionList)){
				return emptyResDTO;
			}
			LOGGER.info("MessageId:{}【支付回调】【扣减会员优惠券、秒杀开始】--组装查询参数开始:{}",messageId,
					JSONObject.toJSONString(orderItemPromotionList));
			OtherCenterResDTO<String> result = marketCenterRAO
					.reduceBuyerPromotion(orderItemPromotionList, messageId);
			LOGGER.info("MessageId:{}【支付回调】【扣减会员优惠券、秒杀结束】--结果:{}",messageId, JSONObject.toJSONString(result));
			if (!result.getOtherCenterResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
				emptyResDTO.setReponseMsg(result.getOtherCenterResponseMsg());
				emptyResDTO.setResponseCode(result.getOtherCenterResponseCode());
				return emptyResDTO;
			}
		} catch (Exception e) {
			emptyResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			emptyResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("MessageId:{} 扣减会员优惠券、秒杀失败",messageId,w.toString());
		}
		return emptyResDTO;
	}
	
	/**
	 * 异步通知促销中心统计限时购销量
	 * 
	 * @param discountDMO
	 */
	public void asyncNoticeMarketStatisticalSales(
			final TradeOrderItemsDiscountDMO discountDMO,
			final TradeOrderItemsDMO itemsDMO, final String messageId) {
		try {
			new Thread(new Runnable() {
				public void run() {
					TimelimitedInfoDTO timelimitedInfoDTO = new TimelimitedInfoDTO();
					timelimitedInfoDTO.setSkuCode(itemsDMO.getSkuCode());
					timelimitedInfoDTO.setPromotionId(discountDMO
							.getPromotionId());
					timelimitedInfoDTO.setSalesVolume(itemsDMO.getGoodsCount());
					marketCenterRAO.updateTimitedInfoSalesVolumeRedis(
							timelimitedInfoDTO, messageId);

				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("MessageId:{} 支付回调--统计限时购销量--出现异常-(此异常不需要回滚){}" ,messageId, w.toString());
		}
	}

	@Override
	public OrderPaymentResultDMO updatePayStatusByOrderNo(
			OrderPaymentResultReqDTO orderPaymentResultReqDTO) {

		OrderPaymentResultDMO orderPaymentResultDMO = new OrderPaymentResultDMO();

		try {
			OrderPaymentResultDMO orderPaymentResultDMOTemp = new OrderPaymentResultDMO();
			orderPaymentResultDMOTemp.setPayStatus(orderPaymentResultReqDTO.getPayStatus());
			orderPaymentResultDMOTemp.setOrderNo(orderPaymentResultReqDTO.getOrderNo());

			int count = orderPaymentResultDAO.updateOrderStatusByTradeNo(orderPaymentResultDMOTemp);
			if (count == ZREO) {
				orderPaymentResultDMO.setResultCode(
						ResultCodeEnum.ORDERSTATUS_ORDER_STATUS_UPDATE_FAIL.getCode());
				orderPaymentResultDMO
						.setResultMsg(ResultCodeEnum.ORDERSTATUS_ORDER_STATUS_UPDATE_FAIL.getMsg());
				return orderPaymentResultDMO;
			}

			orderPaymentResultDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			orderPaymentResultDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			orderPaymentResultDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderPaymentResultDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderPaymentResultServiceImpl.updatePayStatusByOrderNo出现异常{}",
					orderPaymentResultReqDTO.getMessageId(), w.toString());
		}

		return orderPaymentResultDMO;
	}

	/**
	 * 支付前校验,是否有议价|是否取消
	 */
	@Override
	public OrderPayValidateIsBargainCancleResDMO validateIsBargainCancle(
			OrderPayValidateIsBargainCancleReqDTO orderPayValidateIsBargainCancleReqDTO) {
		OrderPayValidateIsBargainCancleResDMO resDMO = new OrderPayValidateIsBargainCancleResDMO();
		try {
			resDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			resDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
			String messageId = orderPayValidateIsBargainCancleReqDTO.getMessageId();
			LOGGER.info("MessageId:{} 支付校验-入参:{}", messageId,
					JSONObject.toJSONString(orderPayValidateIsBargainCancleReqDTO));

			List<OrderPayValidateIsBargainCancleListReqDTO> reqDTO = orderPayValidateIsBargainCancleReqDTO
					.getOrderPayValidateIsBargainCancleListReqDTO();
			if (null != reqDTO && reqDTO.size() > 0) {
				for (OrderPayValidateIsBargainCancleListReqDTO reqTemp : reqDTO) {
					Date createTime = DateUtil
							.getDateBySpecificDate(reqTemp.getOpenCashierDeskTime());
					String orderNo = reqTemp.getOrderNo();

					TradeOrderItemsPriceHistoryDMO tradeOrderItemsPriceHistoryDMO = new TradeOrderItemsPriceHistoryDMO();
					tradeOrderItemsPriceHistoryDMO.setOrderNo(orderNo);
					tradeOrderItemsPriceHistoryDMO.setCreateTime(createTime);
					Integer tradeOrderItemsPriceCount = tradeOrderItemsPriceHistoryDAO
							.queryOrderItemsPriceHistoryCount(tradeOrderItemsPriceHistoryDMO);
					if (null != tradeOrderItemsPriceCount && tradeOrderItemsPriceCount > 0) {
						resDMO.setResultCode(ResultCodeEnum.VALIDATE_HAS_BARGAIN.getCode());
						resDMO.setResultMsg(orderNo + ResultCodeEnum.VALIDATE_HAS_BARGAIN.getMsg());
						LOGGER.info("MessageId:{} 支付校验-打开收银台后含有议价的订单-结果:{}", messageId,
								JSONObject.toJSONString(resDMO));
						return resDMO;
					}
					TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
					tradeOrderItemsDMO.setOrderNo(orderNo);
					tradeOrderItemsDMO.setIsCancelOrderItem(
							Integer.valueOf(OrderStatusEnum.CANCLED.getCode()));
					tradeOrderItemsDMO.setOrderItemCancelTime(createTime);
					Integer itemCancleCount = tradeOrderItemsDAO
							.queryCancleItemCount(tradeOrderItemsDMO);
					if (null != itemCancleCount && itemCancleCount > 0) {
						resDMO.setResultCode(ResultCodeEnum.VALIDATE_HAS_CANCEL.getCode());
						resDMO.setResultMsg(orderNo + ResultCodeEnum.VALIDATE_HAS_CANCEL.getMsg());
						LOGGER.info("MessageId:{} 支付校验-打开收银台后含有取消的订单-结果:{}", messageId,
								JSONObject.toJSONString(resDMO));
						return resDMO;
					}
					TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
					tradeOrdersDMO.setOrderNo(orderNo);
					TradeOrdersDMO tradeOrdersResDao = traderdersDAO
							.queryOrderPayAmt(tradeOrdersDMO);
					BigDecimal orderPayAmount = tradeOrdersResDao.getOrderPayAmount();
					if (null != orderPayAmount) {
						orderPayAmount = CalculateUtils.setScale(orderPayAmount);
						String orderPayAmountReq = reqTemp.getOrderPayAmount();
						if (StringUtilHelper.isNull(orderPayAmountReq) || orderPayAmount
								.compareTo(new BigDecimal(orderPayAmountReq)) != 0) {
							resDMO.setResultCode(
									ResultCodeEnum.VALIDATE_COMPARE_ORDER_PAY_AMT.getCode());
							resDMO.setResultMsg(orderNo
									+ ResultCodeEnum.VALIDATE_COMPARE_ORDER_PAY_AMT.getMsg());
							LOGGER.info("MessageId:{} 支付校验-打开收银台后订单支付和中台支付金额不一致-结果:{}", messageId,
									JSONObject.toJSONString(resDMO));
							return resDMO;
						}
					}
				}
			}
		} catch (Exception e) {
			resDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			resDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderPaymentResultServiceImpl.validateIsBargainCancle出现异常{}",
					orderPayValidateIsBargainCancleReqDTO.getMessageId(), w.toString());
		}

		return resDMO;
	}

}
