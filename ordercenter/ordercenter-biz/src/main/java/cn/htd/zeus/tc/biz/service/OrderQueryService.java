package cn.htd.zeus.tc.biz.service;

import java.util.List;

import cn.htd.zeus.tc.biz.dmo.OrderQueryListDMO;
import cn.htd.zeus.tc.biz.dmo.OrdersQueryVIPListDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.dto.OrderQueryPurchaseRecordInDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordOutDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordsInDTO;
import cn.htd.zeus.tc.dto.response.ChargeConditionInfoDTO;
import cn.htd.zeus.tc.dto.response.OrderAmountResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryPageSizeResDTO;
import cn.htd.zeus.tc.dto.response.OrdersQueryParamListResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderAmountQueryReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryParamReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQuerySupprMangerReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrdersQueryVIPListReqDTO;

public interface OrderQueryService {

	/**
	 * 根据会员id查询订单
	 * 
	 * @Title: selectBySelective
	 * @param orderQueryParamReqDTO
	 * @return TradeOrdersQueryListDMO
	 */
	public OrderQueryListDMO selectOrderByBuyerId(OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	 * 根据条件查询订单
	 * 
	 * @Title: selectOrderByTradeOrdersParam
	 * @param orderQueryParamReqDTO
	 * @return TradeOrdersQueryListDMO
	 */
	public OrderQueryListDMO selectOrderByTradeOrdersParam(
			OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	 * 查询店铺QQ信息
	 * 
	 * @param ordersQueryParamListResDTO
	 */
	public void setCustomerQQInfo(OrdersQueryParamListResDTO ordersQueryParamListResDTO);

	/**
	 * 查询订单数量 @Title: selectOrderCountByBuyerId @param
	 * orderQueryParamReqDTO @return OrderQueryPageSizeResDTO @throws
	 */
	public OrderQueryPageSizeResDTO selectOrderCountByBuyerId(
			OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	 * 查询预售待支付订单数量 @Title: selectOrderCountByBuyerId @param
	 * orderQueryParamReqDTO @return OrderQueryPageSizeResDTO @throws
	 */
	public OrderQueryPageSizeResDTO queryPresaleOrderCountByBuyerId(
			OrderQueryParamReqDTO orderQueryParamReqDTO);

	public OrderQueryListDMO selectSupperBossOrderByTradeOrdersParam(
			OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	 * 根据订单号查询订单详情
	 * 
	 * @Title: selectOrderByPrimaryKey
	 * @param OrderQueryParamReqDTO
	 * @return TradeOrdersDMO
	 */
	public TradeOrdersDMO selectOrderByPrimaryKey(OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	 * 查询议价订单信息
	 * 
	 * @param messageId
	 * @param tradeOrdersDMO
	 * @return
	 */
	public List<ChargeConditionInfoDTO> queryChargeConditionInfo(String messageId,
			TradeOrdersDMO tradeOrdersDMO);

	/**
	 * 呼叫中心查询订单列表 queryListOrder(方法的作用) @Title: queryListOrder @param
	 * orderQueryParamDMO @return OrderQueryListDMO @throws
	 */
	public OrderQueryListDMO queryListOrder(OrderQueryParamReqDTO orderQueryParamReqDTO);

	public Boolean queryOrderItemStatus(TradeOrderItemsDMO recoed);

	/**
	 * 查询最近三个月订单
	 * 
	 * @param orderQueryParamDMO
	 * @return
	 */
	OrderQueryListDMO queryListOrderByCondition(OrderQuerySupprMangerReqDTO recoed);

	/**
	 * 查询最近三个月订单--提供给超级经理人
	 * 
	 * @param recoed
	 * @return
	 */
	public OrderQueryListDMO queryListOrderByCondition4SuperManager(
			OrderQuerySupprMangerReqDTO recoed);

	/*
	 * 查询vip订单
	 */
	public OrdersQueryVIPListDMO selectVipOrder(OrdersQueryVIPListReqDTO request);

	/*
	 * 查询vip套餐订单-未完成
	 */
	public OrdersQueryVIPListDMO selectVipOrderNotCompleted(OrdersQueryVIPListReqDTO request);

	/**
	 * 查询小B最近有购买记录的大B的code，排除没有经营关系的大B
	 */
	public String querySellerCodeWithPurchaseRecordsByBuyerCode(
			OrderQueryPurchaseRecordInDTO orderQueryPurchaseRecordInDTO);

	/**
	 * 查询小B最近的购买记录
	 * 
	 * @param orderRecentQueryPurchaseRecordsInDTO
	 * @return
	 */
	public List<OrderRecentQueryPurchaseRecordOutDTO> queryPurchaseRecordsByBuyerCodeAndSellerCode(
			OrderRecentQueryPurchaseRecordsInDTO orderRecentQueryPurchaseRecordsInDTO);

	/**
	 * 给超级老板提供查询订单金额的接口
	 *
	 * @param orderAmountQueryReqDTO
	 * @return
	 */
	public OrderAmountResDTO queryOrderAmountForSuperboss(OrderAmountQueryReqDTO orderAmountQueryReqDTO);
}
