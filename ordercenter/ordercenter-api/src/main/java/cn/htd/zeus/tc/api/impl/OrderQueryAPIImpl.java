package cn.htd.zeus.tc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.OrderQueryAPI;
import cn.htd.zeus.tc.biz.dao.TradeOrderStatusHistoryDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.OrderQueryListDMO;
import cn.htd.zeus.tc.biz.dmo.OrderQueryParamDMO;
import cn.htd.zeus.tc.biz.dmo.OrdersQueryVIPListDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.service.OrderInfoService;
import cn.htd.zeus.tc.biz.service.OrderQueryService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.OrderQueryPurchaseRecordInDTO;
import cn.htd.zeus.tc.dto.OrderQueryPurchaseRecordOutDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordOutDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordsInDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordsOutDTO;
import cn.htd.zeus.tc.dto.response.ChargeConditionInfoDTO;
import cn.htd.zeus.tc.dto.response.OrderAmountResDTO;
import cn.htd.zeus.tc.dto.response.OrderDayAmountResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryDetailResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryPageSizeResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryResDTO;
import cn.htd.zeus.tc.dto.response.OrdersQueryParamListResDTO;
import cn.htd.zeus.tc.dto.response.OrdersQueryVIPListResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderAmountQueryReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryParamReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQuerySupprBossReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQuerySupprMangerReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrdersQueryVIPListReqDTO;

@Service("orderQueryAPI")
public class OrderQueryAPIImpl implements OrderQueryAPI {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderQueryAPIImpl.class);

	@Resource
	private OrderInfoService orderInfoService;

	@Resource
	private OrderQueryService orderQueryService;

	@Resource
	private TradeOrdersDAO traderdersDAO;

	@Resource
	private TradeOrderStatusHistoryDAO tradeOrderStatusHistoryDAO;

	@Override
	public OrderQueryResDTO queryOrderBySellerIdAndBuyerId(OrderQueryReqDTO orderQueryReqDTO) {
		OrderQueryResDTO orderQueryResDTO = new OrderQueryResDTO();
		try {
			orderQueryResDTO.setMessageId(orderQueryReqDTO.getMessageId());
			if (!StringUtilHelper.allIsNotNull(orderQueryReqDTO.getBuyerCode(), orderQueryReqDTO.getSellerCode(),
					orderQueryReqDTO.getMessageId())) {
				orderQueryResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				orderQueryResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return orderQueryResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderQueryReqDTO.getMessageId())) {
				orderQueryResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				orderQueryResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return orderQueryResDTO;
			}

			TradeOrdersDMO tradeOrdersDMO = orderInfoService.queryOrderBySellerIdAndBuyerId(orderQueryReqDTO);
			orderQueryResDTO.setOrderNo(tradeOrdersDMO.getOrderNo());
			orderQueryResDTO.setTradeNo(tradeOrdersDMO.getTradeNo());
			orderQueryResDTO.setCreateTime(tradeOrdersDMO.getCreateOrderTime());
			orderQueryResDTO.setReponseMsg(tradeOrdersDMO.getResultMsg());
			orderQueryResDTO.setResponseCode(tradeOrdersDMO.getResultCode());
			return orderQueryResDTO;
		} catch (Exception e) {
			orderQueryResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			orderQueryResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.queryOrderBySellerIdAndBuyerId出现异常{}",
					orderQueryReqDTO.getMessageId(), w.toString());
			return orderQueryResDTO;
		}
	}

	@Override
	public OrdersQueryParamListResDTO selectOrderByBuyerId(OrderQueryParamReqDTO orderQueryParamReqDTO) {

		OrdersQueryParamListResDTO ordersQueryParamListResDTO = new OrdersQueryParamListResDTO();

		try {
			if (!StringUtilHelper.allIsNotNull(orderQueryParamReqDTO.getBuyerCode(), orderQueryParamReqDTO.getRows(),
					orderQueryParamReqDTO.getMessageId(), orderQueryParamReqDTO.getStart())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return ordersQueryParamListResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderQueryParamReqDTO.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return ordersQueryParamListResDTO;
			}

			OrderQueryListDMO tradeOrdersQueryListDMO = orderQueryService.selectOrderByBuyerId(orderQueryParamReqDTO);

			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(tradeOrdersQueryListDMO);
			ordersQueryParamListResDTO = JSONObject.toJavaObject(jsonObj, OrdersQueryParamListResDTO.class);
			orderQueryService.setCustomerQQInfo(ordersQueryParamListResDTO);
			ordersQueryParamListResDTO.setReponseMsg(tradeOrdersQueryListDMO.getResultMsg());
			ordersQueryParamListResDTO.setResponseCode(tradeOrdersQueryListDMO.getResultCode());
			ordersQueryParamListResDTO.setMessageId(orderQueryParamReqDTO.getMessageId());
		} catch (Exception e) {
			ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.selectOrderByBuyerId出现异常{}",
					orderQueryParamReqDTO.getMessageId(),
					new Object[] { orderQueryParamReqDTO.getOrderNo(), w.toString() });
		}

		return ordersQueryParamListResDTO;
	}

	@Override
	public OrdersQueryParamListResDTO selectOrderByTradeOrdersParam(OrderQueryParamReqDTO orderQueryParamReqDTO) {
		OrdersQueryParamListResDTO ordersQueryParamListResDTO = new OrdersQueryParamListResDTO();

		try {
			ordersQueryParamListResDTO.setMessageId(orderQueryParamReqDTO.getMessageId());
			if (!StringUtilHelper.allIsNotNull(orderQueryParamReqDTO.getBuyerCode(),
					orderQueryParamReqDTO.getMessageId(), orderQueryParamReqDTO.getStart(),
					orderQueryParamReqDTO.getRows())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return ordersQueryParamListResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderQueryParamReqDTO.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return ordersQueryParamListResDTO;
			}

			OrderQueryListDMO tradeOrdersQueryListDMO = orderQueryService
					.selectOrderByTradeOrdersParam(orderQueryParamReqDTO);
			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(tradeOrdersQueryListDMO);
			ordersQueryParamListResDTO = JSONObject.toJavaObject(jsonObj, OrdersQueryParamListResDTO.class);
			// 设置供应商QQ信息
			orderQueryService.setCustomerQQInfo(ordersQueryParamListResDTO);
			ordersQueryParamListResDTO.setReponseMsg(tradeOrdersQueryListDMO.getResultMsg());
			ordersQueryParamListResDTO.setResponseCode(tradeOrdersQueryListDMO.getResultCode());
		} catch (Exception e) {
			ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.selectOrderByTradeOrdersParam出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return ordersQueryParamListResDTO;
	}

	@Override
	public OrderQueryPageSizeResDTO selectOrderCountByBuyerId(OrderQueryParamReqDTO orderQueryParamReqDTO) {

		OrderQueryPageSizeResDTO orderQueryPageSizeResDTO = new OrderQueryPageSizeResDTO();

		try {
			orderQueryPageSizeResDTO.setMessageId(orderQueryParamReqDTO.getMessageId());
			if (!StringUtilHelper.allIsNotNull(orderQueryParamReqDTO.getBuyerCode(),
					orderQueryParamReqDTO.getMessageId())) {
				orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return orderQueryPageSizeResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderQueryParamReqDTO.getMessageId())) {
				orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return orderQueryPageSizeResDTO;
			}

			OrderQueryPageSizeResDTO orderQueryPageSizeResDTOTemp = orderQueryService
					.selectOrderCountByBuyerId(orderQueryParamReqDTO);
			if (orderQueryPageSizeResDTOTemp != null) {
				orderQueryPageSizeResDTO = orderQueryPageSizeResDTOTemp;
			}
		} catch (Exception e) {
			orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.selectOrderCountByBuyerId出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return orderQueryPageSizeResDTO;
	}

	@Override
	public OrderQueryDetailResDTO selectOrderByPrimaryKey(OrderQueryParamReqDTO orderQueryParamReqDTO) {
		OrderQueryDetailResDTO ordersQueryDetailResDTO = new OrderQueryDetailResDTO();

		try {
			ordersQueryDetailResDTO.setMessageId(orderQueryParamReqDTO.getMessageId());
			if (!StringUtilHelper.allIsNotNull(orderQueryParamReqDTO.getMessageId(),
					orderQueryParamReqDTO.getOrderNo())) {
				ordersQueryDetailResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				ordersQueryDetailResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return ordersQueryDetailResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderQueryParamReqDTO.getMessageId())) {
				ordersQueryDetailResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				ordersQueryDetailResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return ordersQueryDetailResDTO;
			}

			TradeOrdersDMO ordersQueryDetailDMO = orderQueryService.selectOrderByPrimaryKey(orderQueryParamReqDTO);

			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(ordersQueryDetailDMO);
			ordersQueryDetailResDTO = JSONObject.toJavaObject(jsonObj, OrderQueryDetailResDTO.class);

			// if (ordersQueryDetailDMO != null &&
			// ordersQueryDetailDMO.getIsChangePrice() == 1) {
			// 查询议价信息
			List<ChargeConditionInfoDTO> chargeConditionInfoList = orderQueryService
					.queryChargeConditionInfo(orderQueryParamReqDTO.getMessageId(), ordersQueryDetailDMO);
			ordersQueryDetailResDTO.setChargeConditionInfoList(chargeConditionInfoList);
			// }
			ordersQueryDetailResDTO.setReponseMsg(ordersQueryDetailDMO.getResultMsg());
			ordersQueryDetailResDTO.setResponseCode(ordersQueryDetailDMO.getResultCode());
		} catch (Exception e) {
			ordersQueryDetailResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			ordersQueryDetailResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.selectOrderByPrimaryKey出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return ordersQueryDetailResDTO;
	}

	@Override
	public OrderQueryDetailResDTO queryDetailsOrder(OrderQueryParamReqDTO orderQueryParamReqDTO) {
		OrderQueryDetailResDTO ordersQueryDetailResDTO = new OrderQueryDetailResDTO();

		try {
			ordersQueryDetailResDTO.setMessageId(orderQueryParamReqDTO.getMessageId());
			if (!StringUtilHelper.allIsNotNull(orderQueryParamReqDTO.getMessageId(),
					orderQueryParamReqDTO.getOrderNo())) {
				ordersQueryDetailResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				ordersQueryDetailResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return ordersQueryDetailResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderQueryParamReqDTO.getMessageId())) {
				ordersQueryDetailResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				ordersQueryDetailResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return ordersQueryDetailResDTO;
			}

			OrderQueryParamDMO orderQueryDetail = new OrderQueryParamDMO();
			orderQueryDetail.setOrderNo(orderQueryParamReqDTO.getOrderNo());
			TradeOrdersDMO tradeOrdersDMOTemp = traderdersDAO.selectOrderByPrimaryKey(orderQueryDetail);

			TradeOrderStatusHistoryDMO record = new TradeOrderStatusHistoryDMO();
			record.setOrderNo(orderQueryParamReqDTO.getOrderNo());
			List<TradeOrderStatusHistoryDMO> orderStatusList = tradeOrderStatusHistoryDAO
					.selectHistoryByOrderNo(record);
			tradeOrdersDMOTemp.setOrderTail(orderStatusList);

			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(tradeOrdersDMOTemp);
			ordersQueryDetailResDTO = JSONObject.toJavaObject(jsonObj, OrderQueryDetailResDTO.class);

			ordersQueryDetailResDTO.setReponseMsg(tradeOrdersDMOTemp.getResultMsg());
			ordersQueryDetailResDTO.setResponseCode(tradeOrdersDMOTemp.getResultCode());
		} catch (Exception e) {
			ordersQueryDetailResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			ordersQueryDetailResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.queryDetailsOrder出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return ordersQueryDetailResDTO;
	}

	@Override
	public OrdersQueryParamListResDTO queryListOrder(OrderQueryParamReqDTO orderQueryParamReqDTO) {

		OrdersQueryParamListResDTO ordersQueryParamListResDTO = new OrdersQueryParamListResDTO();

		try {
			ordersQueryParamListResDTO.setMessageId(orderQueryParamReqDTO.getMessageId());
			if (!StringUtilHelper.allIsNotNull(orderQueryParamReqDTO.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return ordersQueryParamListResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderQueryParamReqDTO.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return ordersQueryParamListResDTO;
			}

			OrderQueryListDMO tradeOrdersQueryListDMO = orderQueryService.queryListOrder(orderQueryParamReqDTO);

			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(tradeOrdersQueryListDMO);
			ordersQueryParamListResDTO = JSONObject.toJavaObject(jsonObj, OrdersQueryParamListResDTO.class);

			ordersQueryParamListResDTO.setReponseMsg(tradeOrdersQueryListDMO.getResultMsg());
			ordersQueryParamListResDTO.setResponseCode(tradeOrdersQueryListDMO.getResultCode());
		} catch (Exception e) {
			ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.queryListOrder出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return ordersQueryParamListResDTO;
	}

	@Override
	public OrdersQueryParamListResDTO queryListOrderByCondition(OrderQuerySupprMangerReqDTO recoed) {
		OrdersQueryParamListResDTO ordersQueryParamListResDTO = new OrdersQueryParamListResDTO();

		try {
			ordersQueryParamListResDTO.setMessageId(recoed.getMessageId());
			if (!StringUtilHelper.allIsNotNull(recoed.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return ordersQueryParamListResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(recoed.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return ordersQueryParamListResDTO;
			}

			OrderQueryListDMO tradeOrdersQueryListDMO = orderQueryService.queryListOrderByCondition(recoed);

			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(tradeOrdersQueryListDMO);
			ordersQueryParamListResDTO = JSONObject.toJavaObject(jsonObj, OrdersQueryParamListResDTO.class);

			ordersQueryParamListResDTO.setReponseMsg(tradeOrdersQueryListDMO.getResultMsg());
			ordersQueryParamListResDTO.setResponseCode(tradeOrdersQueryListDMO.getResultCode());
		} catch (Exception e) {
			ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.queryListOrderByCondition出现异常{}", recoed.getMessageId(),
					w.toString());
		}

		return ordersQueryParamListResDTO;
	}

	/**
	 * 查询最近三个月订单--提供给超级经理人
	 * 
	 * @param recoed
	 * @return
	 */
	@Override
	public OrdersQueryParamListResDTO queryListOrderByCondition4SuperManager(OrderQuerySupprMangerReqDTO record) {
		OrdersQueryParamListResDTO ordersQueryParamListResDTO = new OrdersQueryParamListResDTO();

		try {
			ordersQueryParamListResDTO.setMessageId(record.getMessageId());
			if (!StringUtilHelper.allIsNotNull(record.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return ordersQueryParamListResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(record.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return ordersQueryParamListResDTO;
			}

			OrderQueryListDMO tradeOrdersQueryListDMO = orderQueryService
					.queryListOrderByCondition4SuperManager(record);

			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(tradeOrdersQueryListDMO);
			ordersQueryParamListResDTO = JSONObject.toJavaObject(jsonObj, OrdersQueryParamListResDTO.class);

			ordersQueryParamListResDTO.setReponseMsg(tradeOrdersQueryListDMO.getResultMsg());
			ordersQueryParamListResDTO.setResponseCode(tradeOrdersQueryListDMO.getResultCode());
		} catch (Exception e) {
			ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.queryListOrderByCondition4SuperManager出现异常{}",
					record.getMessageId(), w.toString());
		}

		return ordersQueryParamListResDTO;
	}

	@Override
	public OrdersQueryParamListResDTO queryOrderBySupprBoss(OrderQuerySupprBossReqDTO recoed) {

		OrdersQueryParamListResDTO ordersQueryParamListResDTO = new OrdersQueryParamListResDTO();

		try {
			ordersQueryParamListResDTO.setMessageId(recoed.getMessageId());
			if (!StringUtilHelper.allIsNotNull(recoed.getBuyerCode(), recoed.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return ordersQueryParamListResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(recoed.getMessageId())) {
				ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return ordersQueryParamListResDTO;
			}

			OrderQueryParamReqDTO orderQueryParamReqDTO = new OrderQueryParamReqDTO();
			orderQueryParamReqDTO.setBuyerCode(recoed.getBuyerCode());
			orderQueryParamReqDTO.setOrderDeleteStatus(recoed.getOrderDeleteStatus());
			orderQueryParamReqDTO.setIsCancelOrder(recoed.getIsCancelOrder());
			orderQueryParamReqDTO.setOrderStatus(recoed.getOrderStatus());
			orderQueryParamReqDTO.setSellerCode(recoed.getSellerCode());
			orderQueryParamReqDTO.setSellerName(recoed.getSellerName());

			if (recoed.getPageSize() != null && recoed.getCurrentPage() != null) {
				orderQueryParamReqDTO.setStart((recoed.getCurrentPage() - 1) * recoed.getPageSize());
				orderQueryParamReqDTO.setRows(recoed.getPageSize());
			}
			OrderQueryListDMO tradeOrdersQueryListDMO = orderQueryService
					.selectSupperBossOrderByTradeOrdersParam(orderQueryParamReqDTO);

			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(tradeOrdersQueryListDMO);
			ordersQueryParamListResDTO = JSONObject.toJavaObject(jsonObj, OrdersQueryParamListResDTO.class);

			ordersQueryParamListResDTO.setReponseMsg(tradeOrdersQueryListDMO.getResultMsg());
			ordersQueryParamListResDTO.setResponseCode(tradeOrdersQueryListDMO.getResultCode());
		} catch (Exception e) {
			ordersQueryParamListResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			ordersQueryParamListResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.queryOrderBySupprBoss出现异常{}", recoed.getMessageId(),
					w.toString());
		}

		return ordersQueryParamListResDTO;
	}

	/*
	 * 查询vip订单
	 * 
	 * @see cn.htd.zeus.tc.api.OrderQueryAPI#selectVipOrder(cn.htd.zeus.tc.dto.
	 * resquest.OrdersQueryVIPListReqDTO)
	 */
	@Override
	public OrdersQueryVIPListResDTO selectVipOrder(OrdersQueryVIPListReqDTO request) {
		OrdersQueryVIPListResDTO res = new OrdersQueryVIPListResDTO();
		try {
			request.setMessageId(GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp()));

			if (!StringUtilHelper.allIsNotNull(request.getStartTime(), request.getEndTime(), request.getPage(),
					request.getRows(), request.getMessageId())) {
				res.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				res.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return res;
			}
			if (!StringUtilHelper.parseNumFormat(request.getMessageId())) {
				res.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				res.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return res;
			}

			OrdersQueryVIPListDMO resDMO = orderQueryService.selectVipOrder(request);

			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(resDMO);
			res = JSONObject.toJavaObject(jsonObj, OrdersQueryVIPListResDTO.class);
			res.setReponseMsg(resDMO.getResultMsg());
			res.setResponseCode(resDMO.getResultCode());
			res.setMessageId(request.getMessageId());
		} catch (Exception e) {
			res.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			res.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.selectVipOrder出现异常{}", request.getMessageId(),
					w.toString());
		}
		return res;
	}

	@Override
	public OrdersQueryVIPListResDTO selectVipOrderNotCompleted(OrdersQueryVIPListReqDTO request) {
		OrdersQueryVIPListResDTO res = new OrdersQueryVIPListResDTO();
		try {
			request.setMessageId(GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp()));

			if (!StringUtilHelper.allIsNotNull(request.getMessageId(), request.getSkuCode())
					|| null == request.getOrderStatus() && request.getOrderStatus().size() == 0) {
				res.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				res.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				return res;
			}
			if (!StringUtilHelper.parseNumFormat(request.getMessageId())) {
				res.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				res.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return res;
			}

			OrdersQueryVIPListDMO resDMO = orderQueryService.selectVipOrderNotCompleted(request);

			// DMO转成DTO
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(resDMO);
			res = JSONObject.toJavaObject(jsonObj, OrdersQueryVIPListResDTO.class);
			res.setReponseMsg(resDMO.getResultMsg());
			res.setResponseCode(resDMO.getResultCode());
			res.setMessageId(request.getMessageId());
		} catch (Exception e) {
			res.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			res.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.selectVipOrderNotCompleted出现异常{}", request.getMessageId(),
					w.toString());
		}
		return res;
	}

	@Override
	public OrderQueryPurchaseRecordOutDTO querySellerCodeWithPurchaseRecordsByBuyerCode(
			OrderQueryPurchaseRecordInDTO orderQueryPurchaseRecordInDTO) {
		OrderQueryPurchaseRecordOutDTO orderQueryPurchaseRecordOutDTO = new OrderQueryPurchaseRecordOutDTO();
		try {
			if (orderQueryPurchaseRecordInDTO == null
					|| StringUtils.isEmpty(orderQueryPurchaseRecordInDTO.getBuyerCode())
					|| orderQueryPurchaseRecordInDTO.getBoxSellerCodeList() == null) {
				orderQueryPurchaseRecordOutDTO.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				orderQueryPurchaseRecordOutDTO
						.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				return orderQueryPurchaseRecordOutDTO;
			}
			String sellerCode = this.orderQueryService
					.querySellerCodeWithPurchaseRecordsByBuyerCode(orderQueryPurchaseRecordInDTO);
			orderQueryPurchaseRecordOutDTO.setSellerCode(sellerCode);
			orderQueryPurchaseRecordOutDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			orderQueryPurchaseRecordOutDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			orderQueryPurchaseRecordOutDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			orderQueryPurchaseRecordOutDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("调用方法OrderQueryAPIImpl.querySellerCodeWithPurchaseRecordsByBuyerCode出现异常{}", w.toString());
		}
		return orderQueryPurchaseRecordOutDTO;
	}

	@Override
	public OrderRecentQueryPurchaseRecordsOutDTO queryPurchaseRecordsByBuyerCodeAndSellerCode(
			OrderRecentQueryPurchaseRecordsInDTO orderRecentQueryPurchaseRecordsInDTO) {
		OrderRecentQueryPurchaseRecordsOutDTO orderRecentQueryPurchaseRecordsOutDTO = new OrderRecentQueryPurchaseRecordsOutDTO();
		try {
			if (orderRecentQueryPurchaseRecordsInDTO == null
					|| StringUtils.isEmpty(orderRecentQueryPurchaseRecordsInDTO.getBuyerCode())
					|| orderRecentQueryPurchaseRecordsInDTO.getBoxSellerCodeList() == null) {
				orderRecentQueryPurchaseRecordsOutDTO
						.setReponseMsg(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getMsg());
				orderRecentQueryPurchaseRecordsOutDTO
						.setResponseCode(ResultCodeEnum.ORDERQUERY_PARAM_LEAST_ONE_NULL.getCode());
				return orderRecentQueryPurchaseRecordsOutDTO;
			}
			List<OrderRecentQueryPurchaseRecordOutDTO> orderRecentQueryPurchaseRecordOutDTOList = this.orderQueryService
					.queryPurchaseRecordsByBuyerCodeAndSellerCode(orderRecentQueryPurchaseRecordsInDTO);
			orderRecentQueryPurchaseRecordsOutDTO
					.setOrderRecentQueryPurchaseRecordOutDTOList(orderRecentQueryPurchaseRecordOutDTOList);
			orderRecentQueryPurchaseRecordsOutDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			orderRecentQueryPurchaseRecordsOutDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			orderRecentQueryPurchaseRecordsOutDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			orderRecentQueryPurchaseRecordsOutDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("调用方法OrderQueryAPIImpl.queryPurchaseRecordsByBuyerCodeAndSellerCode出现异常{}", w.toString());
		}
		return orderRecentQueryPurchaseRecordsOutDTO;
	}

	@Override
	public OrderQueryPageSizeResDTO queryPresalePendingPayOrderNums(OrderQueryParamReqDTO orderQueryParamReqDTO) {
		OrderQueryPageSizeResDTO orderQueryPageSizeResDTO = new OrderQueryPageSizeResDTO();
		try {
			orderQueryPageSizeResDTO.setMessageId(orderQueryParamReqDTO.getMessageId());
			if (!StringUtilHelper.allIsNotNull(orderQueryParamReqDTO.getMessageId()) || StringUtilHelper
					.allIsNull(orderQueryParamReqDTO.getBuyerCode(), orderQueryParamReqDTO.getBuyerId())) {
				orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.ORDERCANCEL_PARAM_IS_ERROR.getCode());
				orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.ORDERCANCEL_PARAM_IS_ERROR.getMsg());
				return orderQueryPageSizeResDTO;
			}
			if (!StringUtilHelper.parseNumFormat(orderQueryParamReqDTO.getMessageId())) {
				orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getCode());
				orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.GOODSCENTER_MESSAGE_ID_IS_ERROR.getMsg());
				return orderQueryPageSizeResDTO;
			}
			OrderQueryPageSizeResDTO orderQueryPageSizeResDTOTemp = orderQueryService
					.queryPresaleOrderCountByBuyerId(orderQueryParamReqDTO);
			if (orderQueryPageSizeResDTOTemp != null) {
				orderQueryPageSizeResDTO = orderQueryPageSizeResDTOTemp;
			}
		} catch (Exception e) {
			orderQueryPageSizeResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			orderQueryPageSizeResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderQueryAPIImpl.queryPresalePendingPayOrderNums出现异常{}",
					orderQueryParamReqDTO.getMessageId(), w.toString());
		}

		return orderQueryPageSizeResDTO;
	}

	@Override
	public OrderAmountResDTO queryOrderAmountForSuperboss(OrderAmountQueryReqDTO orderAmountQueryReqDTO) {
		OrderAmountResDTO orderAmountResDTO = new OrderAmountResDTO();
		if (orderAmountQueryReqDTO == null) {
			orderAmountResDTO.setStatus(0);
			orderAmountResDTO.setErrorMsg("参数对象为空");
			return orderAmountResDTO;
		}
		if (StringUtils.isEmpty(orderAmountQueryReqDTO.getMemberCode())) {
			orderAmountResDTO.setStatus(0);
			orderAmountResDTO.setErrorMsg("参数对象memberCode为空");
			return orderAmountResDTO;
		}
		if (StringUtils.isEmpty(orderAmountQueryReqDTO.getStartDate())) {
			orderAmountResDTO.setStatus(0);
			orderAmountResDTO.setErrorMsg("参数对象startdate为空");
			return orderAmountResDTO;
		}
		if (StringUtils.isEmpty(orderAmountQueryReqDTO.getEndDate())) {
			orderAmountResDTO.setStatus(0);
			orderAmountResDTO.setErrorMsg("参数对象enddate为空");
			return orderAmountResDTO;
		}
		// 查询总订单金额
		String totalOrderAmount = traderdersDAO.queryTotalOrderAmountByMemberCode(orderAmountQueryReqDTO);
		orderAmountResDTO.setTotalAmount(totalOrderAmount);

		List<OrderDayAmountResDTO> orderDayAmountResDTOList = traderdersDAO
				.queryOrderDayAmountResDTO(orderAmountQueryReqDTO);
		orderAmountResDTO.setOrderDayAmountResDTOList(orderDayAmountResDTOList);
		// 查询当月支出的总金额
		String startDay = getCurrentMonthStartDay(orderAmountQueryReqDTO.getCurrentMonth());
		String endDay = getCurrentMonthEndDay(orderAmountQueryReqDTO.getCurrentMonth());
		orderAmountResDTO.setMonthAmount("0");
		if (StringUtils.isNotEmpty(startDay) && StringUtils.isNotEmpty(endDay)) {
			String monthAmount = traderdersDAO.queryMonthOrderAmountByMemberCode(startDay, endDay,
					orderAmountQueryReqDTO.getMemberCode());
			orderAmountResDTO.setMonthAmount(monthAmount);
		}

		orderAmountResDTO.setStatus(1);
		return orderAmountResDTO;
	}

	private String getCurrentMonthStartDay(String currentMonth) {
		String startDay = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			Date dd = format.parse(currentMonth);
			// 获取前月的第一天
			Calendar cale = Calendar.getInstance();
			cale.setTime(dd);

			cale.add(Calendar.MONTH, 0);
			cale.set(Calendar.DAY_OF_MONTH, 1);
			startDay = format2.format(cale.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return startDay;
	}

	private String getCurrentMonthEndDay(String currentMonth) {
		String endDay = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			Date dd = format.parse(currentMonth);
			// 获取前月的第一天
			Calendar cale = Calendar.getInstance();
			cale.setTime(dd);
			cale.add(Calendar.MONTH, 1);
			cale.set(Calendar.DAY_OF_MONTH, 0);
			endDay = format2.format(cale.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return endDay;
	}
}
