/**
 *
 */
package cn.htd.zeus.tc.biz.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderQueryParamDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordOutDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryParamReqDTO;

/**
 * @author ly
 *
 */
@Repository("cn.htd.zeus.tc.dao.TradeOrdersDAO")
public interface TradeOrdersDAO {

	/*
	 * 根据orderNo跟新订单表
	 */
	int updateTradeOrdersByOrderNo(TradeOrdersDMO tradeOrdersDMO);
	
	/**
	 * 根据订单List更新订单支付时间
	 * @param tradeOrdersDMO
	 * @return
	 */
	int updateTradeOrdersByOrderNoList(TradeOrdersDMO tradeOrdersDMO);

	/*
	 * 根据大B和小Bid查询订单表的最新一条记录
	 * 
	 * @param orderQueryReqDTO
	 * 
	 * @return OrderCancelInfoResDTO
	 */
	public TradeOrdersDMO queryOrderBySellerIdAndBuyerId(TradeOrdersDMO tradeOrdersDMO);

	/**
	 * 根据会员id查询订单
	 * 
	 * @Title: selectBySelective
	 * @param orderQueryParamDMO
	 * @return List<TradeOrdersDMO>
	 */
	public List<TradeOrdersDMO> selectOrderByBuyerId(OrderQueryParamDMO orderQueryParamDMO);

	/**
	 *
	 * @Title: selectOrderCountByBuyerId @param orderQueryParamDMO @return
	 *         int @throws
	 */
	public Integer selectOrderCountByBuyerId(OrderQueryParamDMO orderQueryParamDMO);

	public Integer queryListOrderByConditionSupper3MonthCount(
			OrderQueryParamDMO orderQueryParamDMO);

	public Integer selectOrderCountBySupperBoss(OrderQueryParamDMO orderQueryParamDMO);

	/**
	 * 根据条件查询订单
	 * 
	 * @Title: selectOrderBySelective
	 * @param orderQueryParamDMO
	 * @return List<TradeOrdersDMO>
	 */
	public List<TradeOrdersDMO> selectOrderByTradeOrdersParam(
			OrderQueryParamDMO orderQueryParamDMO);

	/**
	 * 根据订单号查询订单详情
	 * 
	 * @Title: selectOrderByPrimaryKey
	 * @param id
	 * @return TradeOrdersDMO
	 */
	public TradeOrdersDMO selectOrderByPrimaryKey(OrderQueryParamDMO orderQueryParamDMO);

	public List<TradeOrderItemsDMO> selectOrderItemByOrderNo(OrderQueryParamDMO orderQueryParamDMO);

	public List<TradeOrdersDMO> selectSupperBossOrderByTradeOrdersParam(
			OrderQueryParamDMO orderQueryParamDMO);

	/**
	 * 根据订单号和订单行号查询订单和订单行集合
	 * 
	 * @Title: selectOrderByPrimaryKey
	 * @param id
	 * @return TradeOrdersDMO
	 */
	public TradeOrdersDMO selectOrderByOrderNoANDOrderItemNo(OrderQueryParamDMO orderQueryParamDMO);

	public TradeOrdersDMO selectOrderByOrderNo(String orderNo);

	/*
	 * 根据订单号查询订单详情
	 */
	public TradeOrdersDMO selectOrderByOrderNoAndBuyerCode(
			OrderQueryParamReqDTO orderQueryParamReqDTO);

	/*
	 * 创建订单的时候-插入订单表
	 */
	public int insertTradeOrders(TradeOrdersDMO tradeOrdersDMO);

	/**
	 * 呼叫中心查询订单列表 queryListOrder(方法的作用) @Title: queryListOrder @param
	 * orderQueryParamDMO @return List<TradeOrdersDMO> @throws
	 */
	public List<TradeOrdersDMO> queryListOrder(OrderQueryParamDMO orderQueryParamDMO);

	/**
	 * 定时任务，查询订单状态为已发货的订单到期未收货的订单集合
	 * selectOrderByOrderStatusAndPayOrderTime(方法的作用) @Title:
	 * selectOrderByOrderStatusAndPayOrderTime @param map @return List
	 * <TradeOrdersDMO> @throws
	 */
	List<TradeOrdersDMO> selectOrderByOrderStatusAndTime(Map map);

	/**
	 * 查询最近三个月订单
	 * 
	 * @param orderQueryParamDMO
	 * @return
	 */
	List<TradeOrdersDMO> queryListOrderByCondition(OrderQueryParamDMO orderQueryParamDMO);

	/**
	 * 查询最近三个月订单--超级经理人
	 * 
	 * @param orderQueryParamDMO
	 * @return
	 */
	List<TradeOrdersDMO> queryListOrderByCondition4SuperManager(
			OrderQueryParamDMO orderQueryParamDMO);

	public Integer queryListOrderByCondition4SuperManagerCount(
			OrderQueryParamDMO orderQueryParamDMO);

	/*
	 * 查询vip商品
	 */
	List<TradeOrdersDMO> selectVipOrder(OrderQueryParamDMO orderQueryParamDMO);

	public Integer selectVipOrderCount(OrderQueryParamDMO orderQueryParamDMO);

	/**
	 * 根据条件查询取消订单信息
	 * 
	 * @Title: selectOrderByTradeOrdersParamByCancel
	 * @param orderQueryParamDMO
	 * @return List<TradeOrdersDMO>
	 */
	public List<TradeOrdersDMO> selectOrderByTradeOrdersParamByCancel(
			OrderQueryParamDMO orderQueryParamDMO);

	/*
	 * 查询订单的实际支付金额
	 */
	public TradeOrdersDMO queryOrderPayAmt(TradeOrdersDMO tradeOrdersDMO);

	/**
	 * 查询最近购买记录的大B code，（必须是有包厢关系的）
	 * 
	 * @param buyerCode
	 * @param boxSellerCodeList
	 * @return
	 */
	public String selectSellerCodeWithPurchaseRecordByBuyerCode(
			@Param("buyerCode") String buyerCode,
			@Param("boxSellerCodeList") List<String> boxSellerCodeList,
			@Param("timeLimit") Date timeLimit);

	/**
	 * 查询最近购买记录
	 * 
	 * @param buyerCode
	 * @param boxSellerCodeList
	 * @return
	 */
	public List<OrderRecentQueryPurchaseRecordOutDTO> queryPurchaseRecordsByBuyerCodeAndSellerCode(
			@Param("buyerCode") String buyerCode,
			@Param("boxSellerCodeList") List<String> boxSellerCodeList,
			@Param("timeLimit") Date timeLimit);

	/**
	 * 根据买家编码查询预售待支付订单数量
	 * 
	 * @param orderQueryParamDMO
	 * @return
	 */
	public Integer queryPresaleOrderCountByBuyerId(OrderQueryParamDMO orderQueryParamDMO);

}
