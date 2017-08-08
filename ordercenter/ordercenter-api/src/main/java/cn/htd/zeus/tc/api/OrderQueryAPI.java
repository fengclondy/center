package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.OrderQueryPurchaseRecordInDTO;
import cn.htd.zeus.tc.dto.OrderQueryPurchaseRecordOutDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordsInDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordsOutDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryDetailResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryPageSizeResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryResDTO;
import cn.htd.zeus.tc.dto.response.OrdersQueryParamListResDTO;
import cn.htd.zeus.tc.dto.response.OrdersQueryVIPListResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryParamReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQuerySupprBossReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQuerySupprMangerReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrdersQueryVIPListReqDTO;

public interface OrderQueryAPI {

	/*
	 * 根据大B和小Bid查询订单表的最新一条记录
	 * @param orderQueryReqDTO
	 * @return OrderCancelInfoResDTO
	 */
	public OrderQueryResDTO queryOrderBySellerIdAndBuyerId(OrderQueryReqDTO orderQueryReqDTO);

	/**
	  * 根据会员id查询订单及订单行
	  * @Title: selectOrderByBuyerId
	  * @param orderQueryParamReqDTO
	  * @return OrdersQueryParamListResDTO
	 */
	public OrdersQueryParamListResDTO selectOrderByBuyerId(OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	  * 根据条件查询订单及订单行
	  * @Title: selectOrderByTradeOrdersParam
	  * @param orderQueryParamReqDTO
	  * @return OrdersQueryParamListResDTO
	 */
	public OrdersQueryParamListResDTO selectOrderByTradeOrdersParam(OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	  * 查询订单数量
	  * @Title: selectOrderCountByBuyerId
	  * @param orderQueryParamReqDTO
	  * @return OrderQueryPageSizeResDTO
	  * @throws
	 */
	public OrderQueryPageSizeResDTO selectOrderCountByBuyerId(OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	  * 根据订单号查询订单详情
	  * @Title: selectOrderByTradeOrdersParam
	  * @param orderQueryDetailReqDTO
	  * @return OrdersQueryParamListResDTO
	 */
	public OrderQueryDetailResDTO selectOrderByPrimaryKey(OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	 * 呼叫中心查询订单详情
	  * queryDetailsOrder(方法的作用)
	  * @Title: queryDetailsOrder
	  * @param orderQueryParamReqDTO
	  * @return OrderQueryDetailResDTO
	  * @throws
	 */
	public OrderQueryDetailResDTO queryDetailsOrder(OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	 * 呼叫中心查询订单列表
	  * queryListOrder(方法的作用)
	  * @Title: queryListOrder
	  * @param orderQueryParamReqDTO
	  * @return OrdersQueryParamListResDTO
	  * @throws
	 */
	public OrdersQueryParamListResDTO queryListOrder(OrderQueryParamReqDTO orderQueryParamReqDTO);

	/**
	 * 查询最近三个月订单
	 * @param recoed
	 * @return
	 */
	public OrdersQueryParamListResDTO queryListOrderByCondition(OrderQuerySupprMangerReqDTO recoed);


	/**
	 * 查询最近三个月订单--提供给超级经理人
	 * @param recoed
	 * @return
	 */
	public OrdersQueryParamListResDTO queryListOrderByCondition4SuperManager(OrderQuerySupprMangerReqDTO recoed);
	
	/**
	 * 查询订单（超级老板）
	 * @param recoed
	 * @return
	 */
	public OrdersQueryParamListResDTO queryOrderBySupprBoss(OrderQuerySupprBossReqDTO recoed);
	
	/*
	 * 查询vip订单(查询已经完成的订单)
	 */
	public OrdersQueryVIPListResDTO selectVipOrder(OrdersQueryVIPListReqDTO request);
	
	/*
	 * 查询vip订单(查询未完成的订单)
	 */
	public OrdersQueryVIPListResDTO selectVipOrderNotCompleted(OrdersQueryVIPListReqDTO request);

	/**
	 * 查询小B最近有购买记录的大B的code，排除没有经营关系的大B
	 */
	public OrderQueryPurchaseRecordOutDTO querySellerCodeWithPurchaseRecordsByBuyerCode(OrderQueryPurchaseRecordInDTO orderQueryPurchaseRecordInDTO);

	/**
	 * 查询小B最近有购买记录
	 */
	public OrderRecentQueryPurchaseRecordsOutDTO queryPurchaseRecordsByBuyerCodeAndSellerCode(OrderRecentQueryPurchaseRecordsInDTO orderRecentQueryPurchaseRecordsInDTO);
}
