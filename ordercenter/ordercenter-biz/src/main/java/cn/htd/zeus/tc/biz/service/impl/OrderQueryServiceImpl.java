package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberCompanyInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.storecenter.dto.QQCustomerDTO;
import cn.htd.zeus.tc.biz.dao.PayOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDiscountDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.OrderQueryListDMO;
import cn.htd.zeus.tc.biz.dmo.OrderQueryParamDMO;
import cn.htd.zeus.tc.biz.dmo.OrdersQueryVIPListDMO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDiscountDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.rao.MemberCenterRAO;
import cn.htd.zeus.tc.biz.rao.StoreCenterRAO;
import cn.htd.zeus.tc.biz.service.OrderQueryService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.GoodCenterEnum;
import cn.htd.zeus.tc.common.enums.MemberCenterEnum;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.OrderQueryPurchaseRecordInDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordOutDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordsInDTO;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.ChargeConditionInfoDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryPageSizeResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryParamResDTO;
import cn.htd.zeus.tc.dto.response.OrdersQueryParamListResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryParamReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQuerySupprMangerReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrdersQueryVIPListReqDTO;

/**
 * 订单查询Service实现类 Copyright (C), 2013-2016, 汇通达网络有限公司 FileName:
 * OrderQueryServiceImpl.java Author: jiaop Date: 2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述 History: //修改记录
 */
@Service
public class OrderQueryServiceImpl implements OrderQueryService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderQueryServiceImpl.class);

	@Autowired
	private TradeOrdersDAO traderdersDAO;

	@Autowired
	private TradeOrderItemsDAO traderderItemsDAO;

	@Autowired
	private MemberBaseInfoService memberBaseInfoService;

	@Autowired
	private TradeOrderItemsDiscountDAO tradeOrderItemsDiscountDAO;

	@Autowired
	private PayOrderInfoDAO paymentOrderInfoDAO;

	@Autowired
	private TradeOrdersDAO tradeOrdersDAO;

	@Autowired
	private StoreCenterRAO storeCenterRAO;

	@Autowired
	private MemberCenterRAO memberCenterRAO;

	private static final int THREE_MONTH = -3;// 三个月

	@Override
	public OrderQueryListDMO selectOrderByBuyerId(
			OrderQueryParamReqDTO orderQueryParamReqDTO) {

		OrderQueryListDMO orderQueryListDMO = new OrderQueryListDMO();

		try {
			OrderQueryParamDMO tradeOrdersDMOParam = new OrderQueryParamDMO();
			tradeOrdersDMOParam.setBuyerCode(orderQueryParamReqDTO
					.getBuyerCode());
			tradeOrdersDMOParam.setOrderDeleteStatus(orderQueryParamReqDTO
					.getOrderDeleteStatus());
			// tradeOrdersDMOParam.setIsCancelOrder(orderQueryParamReqDTO.getIsCancelOrder());
			tradeOrdersDMOParam.setStart(orderQueryParamReqDTO.getStart());
			tradeOrdersDMOParam.setRows(orderQueryParamReqDTO.getRows());
			List<TradeOrdersDMO> orderQueryDMOTempList = traderdersDAO
					.selectOrderByBuyerId(tradeOrdersDMOParam);
			if (CollectionUtils.isNotEmpty(orderQueryDMOTempList)) {
				for (TradeOrdersDMO order : orderQueryDMOTempList) {
					OrderQueryParamDMO orderQueryDetail = new OrderQueryParamDMO();
					orderQueryDetail.setOrderNo(order.getOrderNo());
					List<TradeOrderItemsDMO> orderItemList = traderdersDAO
							.selectOrderItemByOrderNo(orderQueryDetail);
					order.setOrderItemsList(orderItemList);
				}
			}
			orderQueryListDMO.setOrderList(orderQueryDMOTempList);
			orderQueryListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			orderQueryListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.selectOrderByBuyerId出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return orderQueryListDMO;
	}

	@Override
	public OrderQueryListDMO selectOrderByTradeOrdersParam(
			OrderQueryParamReqDTO orderQueryParamReqDTO) {

		OrderQueryListDMO orderQueryListDMO = new OrderQueryListDMO();

		try {
			OrderQueryParamDMO tradeOrdersParamDMO = new OrderQueryParamDMO();
			tradeOrdersParamDMO.setBuyerCode(orderQueryParamReqDTO
					.getBuyerCode());
			tradeOrdersParamDMO.setOrderNo(orderQueryParamReqDTO.getOrderNo());
			tradeOrdersParamDMO.setOrderDeleteStatus(orderQueryParamReqDTO
					.getOrderDeleteStatus());
			tradeOrdersParamDMO.setIsCancelOrder(orderQueryParamReqDTO
					.getIsCancelOrder());
			tradeOrdersParamDMO.setOrderStatus(orderQueryParamReqDTO
					.getOrderStatus());
			tradeOrdersParamDMO
					.setShopName(orderQueryParamReqDTO.getShopName());
			tradeOrdersParamDMO.setCreateOrderTimeFrom(orderQueryParamReqDTO
					.getCreateOrderTimeFrom());
			tradeOrdersParamDMO.setCreateOrderTimeTo(orderQueryParamReqDTO
					.getCreateOrderTimeTo());
			tradeOrdersParamDMO.setStart(orderQueryParamReqDTO.getStart());
			tradeOrdersParamDMO.setRows(orderQueryParamReqDTO.getRows());
			List<TradeOrdersDMO> tradeOrdersDMOTempList = traderdersDAO
					.selectOrderByTradeOrdersParam(tradeOrdersParamDMO);
			if (CollectionUtils.isNotEmpty(tradeOrdersDMOTempList)) {
				for (TradeOrdersDMO order : tradeOrdersDMOTempList) {
					OrderQueryParamDMO orderQueryDetail = new OrderQueryParamDMO();
					orderQueryDetail.setOrderNo(order.getOrderNo());
					List<TradeOrderItemsDMO> orderItemList = traderdersDAO
							.selectOrderItemByOrderNo(orderQueryDetail);
					order.setOrderItemsList(orderItemList);
				}
			}
			// tradeOrdersParamDMO.setOrderFrom("1");
			Integer count = traderdersDAO
					.selectOrderCountByBuyerId(tradeOrdersParamDMO);
			orderQueryListDMO.setOrderList(tradeOrdersDMOTempList);
			orderQueryListDMO.setCount(count == null ? 0 : count);
			orderQueryListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			orderQueryListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.selectOrderByBuyerId出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return orderQueryListDMO;
	}

	@Override
	public OrderQueryListDMO selectSupperBossOrderByTradeOrdersParam(
			OrderQueryParamReqDTO orderQueryParamReqDTO) {

		OrderQueryListDMO orderQueryListDMO = new OrderQueryListDMO();

		try {
			OrderQueryParamDMO tradeOrdersParamDMO = new OrderQueryParamDMO();
			tradeOrdersParamDMO.setBuyerCode(orderQueryParamReqDTO
					.getBuyerCode());
			tradeOrdersParamDMO.setOrderDeleteStatus(orderQueryParamReqDTO
					.getOrderDeleteStatus());
			tradeOrdersParamDMO.setIsCancelOrder(orderQueryParamReqDTO
					.getIsCancelOrder());
			tradeOrdersParamDMO.setOrderStatus(orderQueryParamReqDTO
					.getOrderStatus());
			tradeOrdersParamDMO.setSellerCode(orderQueryParamReqDTO
					.getSellerCode());
			tradeOrdersParamDMO.setSellerName(orderQueryParamReqDTO
					.getSellerName());
			tradeOrdersParamDMO.setStart(orderQueryParamReqDTO.getStart());
			tradeOrdersParamDMO.setRows(orderQueryParamReqDTO.getRows());
			List<TradeOrdersDMO> tradeOrdersDMOTempList = traderdersDAO
					.selectSupperBossOrderByTradeOrdersParam(tradeOrdersParamDMO);

			Integer count = traderdersDAO
					.selectOrderCountBySupperBoss(tradeOrdersParamDMO);
			orderQueryListDMO.setOrderList(tradeOrdersDMOTempList);
			orderQueryListDMO.setCount(count == null ? 0 : count);
			orderQueryListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			orderQueryListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.selectOrderByBuyerId出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return orderQueryListDMO;
	}

	@Override
	public OrderQueryPageSizeResDTO selectOrderCountByBuyerId(
			OrderQueryParamReqDTO orderQueryParamReqDTO) {

		OrderQueryPageSizeResDTO orderQueryPageSizeResDTO = new OrderQueryPageSizeResDTO();

		try {
			OrderQueryParamDMO tradeOrdersParamDMO = new OrderQueryParamDMO();
			tradeOrdersParamDMO.setBuyerCode(orderQueryParamReqDTO
					.getBuyerCode());
			tradeOrdersParamDMO.setOrderDeleteStatus(orderQueryParamReqDTO
					.getOrderDeleteStatus());
			tradeOrdersParamDMO.setIsCancelOrder(orderQueryParamReqDTO
					.getIsCancelOrder());
			tradeOrdersParamDMO.setOrderNo(orderQueryParamReqDTO.getOrderNo());
			tradeOrdersParamDMO.setOrderStatus(orderQueryParamReqDTO
					.getOrderStatus());
			tradeOrdersParamDMO
					.setShopName(orderQueryParamReqDTO.getShopName());
			// tradeOrdersParamDMO.setOrderFrom("1");
			tradeOrdersParamDMO.setCreateOrderTimeFrom(orderQueryParamReqDTO
					.getCreateOrderTimeFrom());
			tradeOrdersParamDMO.setCreateOrderTimeTo(orderQueryParamReqDTO
					.getCreateOrderTimeTo());
			Integer pageSize = traderdersDAO
					.selectOrderCountByBuyerId(tradeOrdersParamDMO);
			orderQueryPageSizeResDTO.setSize(pageSize == null ? 0 : pageSize);
			orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.SUCCESS
					.getCode());
			orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.SUCCESS
					.getMsg());
		} catch (Exception e) {
			orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.ERROR
					.getCode());
			orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.ERROR
					.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.selectOrderCountByBuyerId出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return orderQueryPageSizeResDTO;
	}

	@Override
	public TradeOrdersDMO selectOrderByPrimaryKey(
			OrderQueryParamReqDTO orderQueryParamReqDTO) {

		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();

		try {

			OrderQueryParamDMO orderQueryDetail = new OrderQueryParamDMO();
			orderQueryDetail.setOrderNo(orderQueryParamReqDTO.getOrderNo());
			TradeOrdersDMO tradeOrdersDMOTemp = traderdersDAO
					.selectOrderByOrderNoAndBuyerCode(orderQueryParamReqDTO);
			List<TradeOrderItemsDMO> orderItemList = traderdersDAO
					.selectOrderItemByOrderNo(orderQueryDetail);
			tradeOrdersDMOTemp.setOrderItemsList(orderItemList);
			if (tradeOrdersDMOTemp != null) {
				tradeOrdersDMO = tradeOrdersDMOTemp;
			}
			// 优惠券
			List<TradeOrderItemsDiscountDMO> tradeOrderItemsDiscountDMOList = tradeOrderItemsDiscountDAO
					.selectBuyerCouponCodeByOrderNo(orderQueryParamReqDTO
							.getOrderNo());
			String buyerCouponCode = "";
			// if (tradeOrderItemsDiscountDMOList.size() > 0) {
			// buyerCouponCode =
			// tradeOrderItemsDiscountDMOList.get(0).getBuyerCouponCode();
			// } else {
			// buyerCouponCode = "";
			// }
			if (CollectionUtils.isNotEmpty(tradeOrderItemsDiscountDMOList)) {
				for (TradeOrderItemsDiscountDMO discount : tradeOrderItemsDiscountDMOList) {
					if (StringUtils.isNotBlank(discount.getBuyerCouponCode())) {
						buyerCouponCode = discount.getBuyerCouponCode();
					}
				}
			}
			tradeOrdersDMO.setBuyerCouponCode(buyerCouponCode);

			tradeOrdersDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			tradeOrdersDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			tradeOrdersDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.selectOrderByPrimaryKey出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return tradeOrdersDMO;
	}

	@Override
	public OrderQueryListDMO queryListOrder(
			OrderQueryParamReqDTO orderQueryParamReqDTO) {

		OrderQueryListDMO orderQueryListDMO = new OrderQueryListDMO();

		try {
			OrderQueryParamDMO tradeOrdersDMOParam = new OrderQueryParamDMO();
			if (orderQueryParamReqDTO.getBuyerPhone() != null) {
				// 会员信息
				MemberCompanyInfoDTO memberCompanyInfoDTO = new MemberCompanyInfoDTO();
				memberCompanyInfoDTO
						.setBuyerSellerType(Integer
								.parseInt(MemberCenterEnum.MEMBER_TYPE_BUYER
										.getCode()));
				memberCompanyInfoDTO
						.setArtificialPersonMobile(orderQueryParamReqDTO
								.getBuyerPhone());
				ExecuteResult<MemberCompanyInfoDTO> result = memberBaseInfoService
						.selectMobilePhoneMemberId(memberCompanyInfoDTO);
				MemberCompanyInfoDTO memberDTO = new MemberCompanyInfoDTO();
				if (result.getResult() != null) {
					memberDTO = result.getResult();
					tradeOrdersDMOParam.setBuyerCode(memberDTO.getMemberCode());
				} else {
					orderQueryListDMO
							.setResultMsg(ResultCodeEnum.ORDERSETTLEMENT_MEMBER_INFO_NULL
									.getMsg());
					orderQueryListDMO
							.setResultCode(ResultCodeEnum.ORDERSETTLEMENT_MEMBER_INFO_NULL
									.getCode());
					return orderQueryListDMO;
				}
			} else {
				tradeOrdersDMOParam.setBuyerCode(orderQueryParamReqDTO
						.getBuyerCode());
			}
			tradeOrdersDMOParam.setBuyerName(orderQueryParamReqDTO
					.getBuyerName());
			if (orderQueryParamReqDTO.getStart() != null
					&& orderQueryParamReqDTO.getRows() != null) {
				tradeOrdersDMOParam.setRows(orderQueryParamReqDTO.getRows());
				tradeOrdersDMOParam
						.setStart((orderQueryParamReqDTO.getStart() - 1)
								* orderQueryParamReqDTO.getRows());
			}
			List<TradeOrdersDMO> orderQueryDMOTempList = traderdersDAO
					.queryListOrder(tradeOrdersDMOParam);

			tradeOrdersDMOParam.setOrderDeleteStatus(0);
			Integer count = traderdersDAO
					.selectOrderCountByBuyerId(tradeOrdersDMOParam);

			orderQueryListDMO.setOrderList(orderQueryDMOTempList);
			orderQueryListDMO.setCount(count == null ? 0 : count);
			orderQueryListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			orderQueryListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.queryListOrder出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return orderQueryListDMO;
	}

	@Override
	public Boolean queryOrderItemStatus(TradeOrderItemsDMO recoed) {
		Boolean result = true;
		Map map = new HashMap();
		map.put("orderNo", recoed.getOrderNo());
		long itemCount = traderderItemsDAO
				.selectTradeOrderItemsByOrderNoOrStatus(map);
		map.put("orderItemStatus", recoed.getOrderItemStatus());
		long statusCount = traderderItemsDAO
				.selectTradeOrderItemsByOrderNoOrStatus(map);
		if (itemCount != statusCount) {
			result = false;
		}
		return result;
	}

	@Override
	public OrderQueryListDMO queryListOrderByCondition(
			OrderQuerySupprMangerReqDTO recoed) {
		OrderQueryListDMO orderQueryListDMO = new OrderQueryListDMO();

		try {
			OrderQueryParamDMO tradeOrdersDMOParam = new OrderQueryParamDMO();
			tradeOrdersDMOParam.setSellerCode(recoed.getHtdvendor());
			tradeOrdersDMOParam.setSellerName(recoed.getMemberName());
			if (recoed.getPageSize() != null && recoed.getCurrentPage() != null) {
				tradeOrdersDMOParam.setRows(recoed.getPageSize());
				tradeOrdersDMOParam.setStart((recoed.getCurrentPage() - 1)
						* recoed.getPageSize());
			}
			tradeOrdersDMOParam.setOrderStatus(recoed.getOrderStatus());
			tradeOrdersDMOParam.setCustomerManagerID(recoed
					.getCustomerManagerID());
			tradeOrdersDMOParam.setCreateOrderTimeFrom(DateUtil
					.getMonthTime(THREE_MONTH));
			List<TradeOrdersDMO> orderQueryDMOTempList = traderdersDAO
					.queryListOrderByCondition(tradeOrdersDMOParam);

			Integer count = traderdersDAO
					.queryListOrderByConditionSupper3MonthCount(tradeOrdersDMOParam);

			orderQueryListDMO.setOrderList(orderQueryDMOTempList);
			orderQueryListDMO.setCount(count == null ? 0 : count);
			orderQueryListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			orderQueryListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.queryOrderBySupprBoss出现异常{}",
					recoed.getMessageId(), w.toString());
		}

		return orderQueryListDMO;
	}

	/**
	 * 查询最近三个月订单--提供给超级经理人
	 * 
	 * @param recoed
	 * @return
	 */
	@Override
	public OrderQueryListDMO queryListOrderByCondition4SuperManager(
			OrderQuerySupprMangerReqDTO recoed) {
		OrderQueryListDMO orderQueryListDMO = new OrderQueryListDMO();

		try {
			Long startTime = System.currentTimeMillis();

			OrderQueryParamDMO tradeOrdersDMOParam = new OrderQueryParamDMO();
			tradeOrdersDMOParam.setSellerCode(recoed.getHtdvendor());
			tradeOrdersDMOParam.setSellerName(recoed.getMemberName());
			if (recoed.getPageSize() != null && recoed.getCurrentPage() != null) {
				tradeOrdersDMOParam.setRows(recoed.getPageSize());
				tradeOrdersDMOParam.setStart((recoed.getCurrentPage() - 1)
						* recoed.getPageSize());
			}
			tradeOrdersDMOParam.setOrderStatus(recoed.getOrderStatus());
			tradeOrdersDMOParam.setCustomerManagerID(recoed
					.getCustomerManagerID());
			tradeOrdersDMOParam.setCreateOrderTimeFrom(DateUtil
					.getMonthTime(THREE_MONTH));
			LOGGER.info("客户经理人查询三个月订单参数:"
					+ JSONObject.toJSONString(tradeOrdersDMOParam));

			Integer count = traderdersDAO
					.queryListOrderByCondition4SuperManagerCount(tradeOrdersDMOParam);
			List<TradeOrdersDMO> orderQueryDMOTempList = new ArrayList<TradeOrdersDMO>();
			if (null != count && count > 0) {
				orderQueryDMOTempList = traderdersDAO
						.queryListOrderByCondition4SuperManager(tradeOrdersDMOParam);
				if (null != orderQueryDMOTempList) {
					for (TradeOrdersDMO orderQueryDMOTemp : orderQueryDMOTempList) {
						TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
						tradeOrderItemsDMO.setOrderNo(orderQueryDMOTemp
								.getOrderNo());
						tradeOrderItemsDMO.setCustomerManagerCode(recoed
								.getCustomerManagerID());
						List<TradeOrderItemsDMO> itemList = traderderItemsDAO
								.queryListOrderByCondition4SuperManagerItem(tradeOrderItemsDMO);
						if (null != itemList) {
							long totalGoodsCount = 0;
							BigDecimal totalGoodsAmt = new BigDecimal(0);
							BigDecimal totalDiscountAmount = new BigDecimal(0);

							BigDecimal orderTotalAmount = new BigDecimal(0);
							BigDecimal orderPayAmount = new BigDecimal(0);
							BigDecimal bargainingOrderFreight = new BigDecimal(
									0);
							for (TradeOrderItemsDMO itemTemp : itemList) {
								totalGoodsCount = totalGoodsCount
										+ itemTemp.getGoodsCount();
								totalGoodsAmt = totalGoodsAmt
										.add(itemTemp.getGoodsAmount() == null ? new BigDecimal(
												0) : itemTemp.getGoodsAmount());
								totalDiscountAmount = totalDiscountAmount
										.add(itemTemp.getTotalDiscountAmount() == null ? new BigDecimal(
												0) : itemTemp
												.getTotalDiscountAmount());

								bargainingOrderFreight = bargainingOrderFreight
										.add(itemTemp
												.getBargainingGoodsFreight() == null ? new BigDecimal(
												0) : itemTemp
												.getBargainingGoodsFreight());
								orderTotalAmount = orderTotalAmount
										.add(itemTemp.getOrderItemTotalAmount() == null ? new BigDecimal(
												0) : itemTemp
												.getOrderItemTotalAmount());
								orderPayAmount = orderPayAmount
										.add(itemTemp.getOrderItemPayAmount() == null ? new BigDecimal(
												0) : itemTemp
												.getOrderItemPayAmount());
							}
							orderQueryDMOTemp.setOrderItemsList(itemList);
							orderQueryDMOTemp
									.setTotalGoodsAmount(totalGoodsAmt);
							orderQueryDMOTemp
									.setTotalDiscountAmount(totalDiscountAmount);
							orderQueryDMOTemp
									.setBargainingOrderFreight(bargainingOrderFreight);
							orderQueryDMOTemp
									.setOrderTotalAmount(orderTotalAmount);
							orderQueryDMOTemp.setOrderPayAmount(orderPayAmount);
							orderQueryDMOTemp.setTotalGoodsCount(Integer
									.valueOf(String.valueOf(totalGoodsCount)));
						}
					}
				}
			}

			orderQueryListDMO.setOrderList(orderQueryDMOTempList);
			orderQueryListDMO.setCount(count == null ? 0 : count);
			orderQueryListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
			Long endTime = System.currentTimeMillis();
			LOGGER.info("客户经理人查询三个月订单 耗时:{}", endTime - startTime);
		} catch (Exception e) {
			orderQueryListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderQueryListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.queryListOrderByCondition4SuperManager出现异常{}",
					recoed.getMessageId(), w.toString());
		}

		return orderQueryListDMO;
	}

	/*
	 * 查询vip订单
	 * 
	 * @see
	 * cn.htd.zeus.tc.biz.service.OrderQueryService#selectVipOrder(cn.htd.zeus.
	 * tc.dto.resquest.OrdersQueryVIPListReqDTO)
	 */
	@Override
	public OrdersQueryVIPListDMO selectVipOrder(OrdersQueryVIPListReqDTO request) {
		OrdersQueryVIPListDMO ordersQueryVIPListDMO = new OrdersQueryVIPListDMO();
		try {
			OrderQueryParamDMO orderQueryParamDMO = new OrderQueryParamDMO();
			orderQueryParamDMO.setCreateOrderTimeFrom(DateUtil
					.getDateBySpecificDate(request.getStartTime()));
			orderQueryParamDMO.setCreateOrderTimeTo(DateUtil
					.getDateBySpecificDate(request.getEndTime()));
			Integer start = (request.getPage() - 1) * request.getRows();
			orderQueryParamDMO.setStart(start);
			orderQueryParamDMO.setRows(request.getRows());
			orderQueryParamDMO.setIsVipItem(Integer
					.valueOf(GoodCenterEnum.IS_VIP_GOODS.getCode()));
			List<String> orderStatus = new ArrayList<String>();
			orderStatus.add(OrderStatusEnum.ORDER_COMPLETE.getCode());
			orderQueryParamDMO.setOrderStatus(orderStatus);
			List<TradeOrdersDMO> tradeOrdersDMOList = traderdersDAO
					.selectVipOrder(orderQueryParamDMO);

			Integer count = traderdersDAO
					.selectVipOrderCount(orderQueryParamDMO);
			ordersQueryVIPListDMO.setCount(count == null ? 0 : count);
			ordersQueryVIPListDMO.setOrderList(tradeOrdersDMOList);
			ordersQueryVIPListDMO.setResultCode(ResultCodeEnum.SUCCESS
					.getCode());
			ordersQueryVIPListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			ordersQueryVIPListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			ordersQueryVIPListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.selectVipOrder出现异常{}",
					request.getMessageId(), w.toString());
		}
		return ordersQueryVIPListDMO;
	}

	@Override
	public OrdersQueryVIPListDMO selectVipOrderNotCompleted(
			OrdersQueryVIPListReqDTO request) {
		OrdersQueryVIPListDMO ordersQueryVIPListDMO = new OrdersQueryVIPListDMO();
		try {
			OrderQueryParamDMO orderQueryParamDMO = new OrderQueryParamDMO();
			orderQueryParamDMO.setSkuCode(request.getSkuCode());
			orderQueryParamDMO.setOrderStatus(request.getOrderStatus());
			orderQueryParamDMO.setIsVipItem(Integer
					.valueOf(GoodCenterEnum.IS_VIP_GOODS.getCode()));
			List<TradeOrdersDMO> tradeOrdersDMOList = traderdersDAO
					.selectVipOrder(orderQueryParamDMO);

			ordersQueryVIPListDMO.setOrderList(tradeOrdersDMOList);
			ordersQueryVIPListDMO.setResultCode(ResultCodeEnum.SUCCESS
					.getCode());
			ordersQueryVIPListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			ordersQueryVIPListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			ordersQueryVIPListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.selectVipOrderNotCompleted出现异常{}",
					request.getMessageId(), w.toString());
		}
		return ordersQueryVIPListDMO;
	}

	@Override
	public List<ChargeConditionInfoDTO> queryChargeConditionInfo(
			String messageId, TradeOrdersDMO tradeOrdersDMO) {
		List<TradeOrderItemsDMO> orderItemsList = tradeOrdersDMO
				.getOrderItemsList();
		List<ChargeConditionInfoDTO> chargeConditionInfoList = new ArrayList<ChargeConditionInfoDTO>();
		LOGGER.info(
				"MessageId:{} 调用方法OrderQueryServiceImpl.queryChargeConditionInfo查询数据开始{}",
				messageId, tradeOrdersDMO.getOrderNo());
		if (GoodCenterEnum.EXTERNAL_SUPPLIER.getCode().equals(
				orderItemsList.get(0).getChannelCode())) {
			for (TradeOrderItemsDMO orderItems : orderItemsList) {
				if (orderItems.getIsCancelOrderItem().equals(1)
						|| orderItems.getIsCancelOrderItem().equals(2)) {
					continue;
				}
				ChargeConditionInfoDTO chargeConditionInfoDTO = new ChargeConditionInfoDTO();
				chargeConditionInfoDTO.setBrandCode(String.valueOf(orderItems
						.getBrandId()));
				chargeConditionInfoDTO.setChargeAmount(orderItems
						.getOrderItemPayAmount());
				chargeConditionInfoDTO
						.setClassCode(GoodCenterEnum.EXTERNAL_SUPPLIER
								.getCode());
				chargeConditionInfoDTO.setCustomerCode(tradeOrdersDMO
						.getBuyerCode());
				chargeConditionInfoDTO.setCompanyCode(tradeOrdersDMO
						.getSellerCode());
				chargeConditionInfoDTO.setItemOrderNo(orderItems
						.getOrderItemNo());
				chargeConditionInfoList.add(chargeConditionInfoDTO);
			}
		} else if (GoodCenterEnum.INTERNAL_SUPPLIER.getCode().equals(
				orderItemsList.get(0).getChannelCode())) {
			List<PayOrderInfoDMO> orderList = paymentOrderInfoDAO
					.selectBrandCodeAndClassCodeByOrderNo(tradeOrdersDMO
							.getOrderNo());
			for (PayOrderInfoDMO payOrderInfo : orderList) {
				ChargeConditionInfoDTO chargeConditionInfoDTO = new ChargeConditionInfoDTO();
				chargeConditionInfoDTO
						.setBrandCode(payOrderInfo.getBrandCode());
				chargeConditionInfoDTO
						.setChargeAmount(payOrderInfo.getAmount());
				chargeConditionInfoDTO
						.setClassCode(payOrderInfo.getClassCode());
				chargeConditionInfoDTO.setCustomerCode(tradeOrdersDMO
						.getBuyerCode());
				chargeConditionInfoDTO.setCompanyCode(tradeOrdersDMO
						.getSellerCode());
				chargeConditionInfoDTO.setItemOrderNo(payOrderInfo
						.getDownOrderNo());
				chargeConditionInfoList.add(chargeConditionInfoDTO);
			}
		} else if (GoodCenterEnum.JD_SUPPLIER.getCode().equals(
				orderItemsList.get(0).getChannelCode())) {
			List<PayOrderInfoDMO> orderList = paymentOrderInfoDAO
					.selectBrandCodeAndClassCodeByOrderNo(tradeOrdersDMO
							.getOrderNo());
			ChargeConditionInfoDTO chargeConditionInfoDTO = new ChargeConditionInfoDTO();
			chargeConditionInfoDTO.setBrandCode(MiddleWareEnum.JD_BRAND_ID_PAY
					.getCode());
			chargeConditionInfoDTO
					.setChargeAmount(orderList.get(0).getAmount());
			chargeConditionInfoDTO.setClassCode(MiddleWareEnum.JD_BRAND_ID_PAY
					.getCode());
			chargeConditionInfoDTO.setCustomerCode(tradeOrdersDMO
					.getBuyerCode());
			chargeConditionInfoDTO.setCompanyCode(tradeOrdersDMO
					.getSellerCode());
			chargeConditionInfoDTO.setItemOrderNo(orderList.get(0)
					.getDownOrderNo());
			chargeConditionInfoList.add(chargeConditionInfoDTO);
		}
		LOGGER.info(
				"MessageId:{} 调用方法OrderQueryServiceImpl.queryChargeConditionInfo查询数据结束{}",
				messageId, JSONObject.toJSONString(chargeConditionInfoList));
		return chargeConditionInfoList;
	}

	@Override
	public void setCustomerQQInfo(
			OrdersQueryParamListResDTO ordersQueryParamListResDTO) {
		List<OrderQueryParamResDTO> orderList = ordersQueryParamListResDTO
				.getOrderList();
		if (CollectionUtils.isNotEmpty(orderList)) {
			for (OrderQueryParamResDTO order : orderList) {
				List<QQCustomerDTO> qqCustomerDTOList = storeCenterRAO
						.searchQQCustomerByCondition(order.getShopId(),
								ordersQueryParamListResDTO.getMessageId(),
								Constant.CUSTOMER_QQ_TYPE_OUTER);
				if (CollectionUtils.isNotEmpty(qqCustomerDTOList)
						&& !"".equals(qqCustomerDTOList.get(0)
								.getCustomerQQNumber())) {
					order.setSellerQQ(qqCustomerDTOList.get(0)
							.getCustomerQQNumber());
				} else {
					List<QQCustomerDTO> qqCustomerList = storeCenterRAO
							.searchQQCustomerByCondition(null,
									ordersQueryParamListResDTO.getMessageId(),
									Constant.CUSTOMER_QQ_TYPE_INNER);
					if (CollectionUtils.isNotEmpty(qqCustomerList)) {
						order.setSellerQQ(qqCustomerList.get(0)
								.getCustomerQQNumber());
					} else {
						order.setSellerQQ("");
					}
				}
			}
		}
	}

	@Override
	public String querySellerCodeWithPurchaseRecordsByBuyerCode(
			OrderQueryPurchaseRecordInDTO orderQueryPurchaseRecordInDTO) {
		String sellerCode = null;
		if (orderQueryPurchaseRecordInDTO.getBoxSellerCodeList().size() > 0) { // 如果包厢关系列表有大B数据
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, -1);
			Date timeLimited = calendar.getTime();
			sellerCode = this.traderdersDAO
					.selectSellerCodeWithPurchaseRecordByBuyerCode(
							orderQueryPurchaseRecordInDTO.getBuyerCode(),
							orderQueryPurchaseRecordInDTO
									.getBoxSellerCodeList(), timeLimited);
		}
		return sellerCode;
	}

	@Override
	public List<OrderRecentQueryPurchaseRecordOutDTO> queryPurchaseRecordsByBuyerCodeAndSellerCode(
			OrderRecentQueryPurchaseRecordsInDTO orderRecentQueryPurchaseRecordsInDTO) {
		List<OrderRecentQueryPurchaseRecordOutDTO> orderRecentQueryPurchaseRecordOutDTOList = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, -1);
		Date timeLimited = calendar.getTime();
		if (orderRecentQueryPurchaseRecordsInDTO.getBoxSellerCodeList().size() > 0) {
			orderRecentQueryPurchaseRecordOutDTOList = this.traderdersDAO
					.queryPurchaseRecordsByBuyerCodeAndSellerCode(
							orderRecentQueryPurchaseRecordsInDTO.getBuyerCode(),
							orderRecentQueryPurchaseRecordsInDTO
									.getBoxSellerCodeList(), timeLimited);
		}
		return orderRecentQueryPurchaseRecordOutDTOList;
	}

	@Override
	public OrderQueryPageSizeResDTO queryPresaleOrderCountByBuyerId(
			OrderQueryParamReqDTO orderQueryParamReqDTO) {
		OrderQueryPageSizeResDTO orderQueryPageSizeResDTO = new OrderQueryPageSizeResDTO();
		try {
			OrderQueryParamDMO tradeOrdersParamDMO = new OrderQueryParamDMO();
			String buyerCode = orderQueryParamReqDTO.getBuyerCode();
			if (StringUtilHelper.isNull(buyerCode)) {
				OtherCenterResDTO<String> memberInfo = memberCenterRAO
						.getMemberCodeById(orderQueryParamReqDTO.getBuyerId(),
								orderQueryParamReqDTO.getMessageId());
				if (!ResultCodeEnum.SUCCESS.getCode().equals(
						memberInfo.getOtherCenterResponseCode())) {
					orderQueryPageSizeResDTO.setResponseCode(memberInfo
							.getOtherCenterResponseCode());
					orderQueryPageSizeResDTO.setReponseMsg(memberInfo
							.getOtherCenterResponseMsg());
					return orderQueryPageSizeResDTO;
				}
				buyerCode = memberInfo.getOtherCenterResult();
			}
			tradeOrdersParamDMO.setBuyerCode(buyerCode);
			List<String> orderStatusList = new ArrayList<String>();
			orderStatusList.add(OrderStatusEnum.PRE_PAY.getCode());
			tradeOrdersParamDMO.setOrderStatus(orderStatusList);
			tradeOrdersParamDMO.setIsCancelOrder(Integer.valueOf(OrderStatusEnum.NOT_CANCLE.getCode()));
			// 查询预售订单
			tradeOrdersParamDMO.setOrderFrom("5");
			Integer pageSize = traderdersDAO
					.queryPresaleOrderCountByBuyerId(tradeOrdersParamDMO);
			orderQueryPageSizeResDTO.setSize(pageSize == null ? 0 : pageSize);
			orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.SUCCESS
					.getCode());
			orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.SUCCESS
					.getMsg());
		} catch (Exception e) {
			orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.ERROR
					.getCode());
			orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.ERROR
					.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法OrderQueryServiceImpl.selectOrderCountByBuyerId出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}
		return orderQueryPageSizeResDTO;
	}
}
