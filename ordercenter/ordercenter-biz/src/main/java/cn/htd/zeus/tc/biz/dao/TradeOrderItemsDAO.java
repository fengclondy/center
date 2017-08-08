package cn.htd.zeus.tc.biz.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderItemPaymentDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;

@Repository("cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO")
public interface TradeOrderItemsDAO {

	/*
	 * 根据订单行号更新订单行表
	 * 
	 * @param tradeOrderItemsDMO
	 * 
	 * @return 整数
	 */
	int updateTradeOrderItemsByItemNo(TradeOrderItemsDMO tradeOrderItemsDMO);

	/*
	 * 根据订单号+渠道编码更新订单行表
	 * 
	 * @param tradeOrderItemsDMO
	 * 
	 * @return 整数
	 */
	int updateTradeOrderItemsByOrderNo(TradeOrderItemsDMO tradeOrderItemsDMO);

	/*
	 * 根据订单号和是否是取消订单查询订单行表,查询结果为0,表示订单行表全部取消
	 * 
	 * @param orderNo
	 */
	long selectTradeOrderItemsByOrderNoIsNotCancel(String orderNo);

	/*
	 * 根据订单号、订单行状态查询订单行表的个数
	 */
	long selectTradeOrderItemsByOrderNoOrStatus(Map paramMap);

	/**
	 * 根据订单号查询订单行
	 * 
	 * @Title: selectOrderItemsByOrderNo
	 * @param orderNo
	 * @return List<TradeOrderItemsDMO>
	 */
	public List<TradeOrderItemsDMO> selectOrderItemsByOrderNo(String orderNo);

	/**
	 * 根据订单行号查询订单行
	 * 
	 * @Title: selectOrderItemsByOrderItemNo
	 * @param orderItemNo
	 * @return List<TradeOrderItemsDMO>
	 */
	public List<TradeOrderItemsDMO> selectOrderItemsByOrderItemNo(String orderItemNo);

	/*
	 * 创建订单的时候-插入订单行表
	 */
	public int insertTradeOrderItems(TradeOrderItemsDMO tradeOrderItemsDMO);

	/**
	 * 根据交易号更新订单行状态 updateOrderItemStatusByTradeNo(方法的作用) @Title:
	 * updateOrderItemStatusByTradeNo @return int @throws
	 */
	public int updateOrderItemStatusByTradeNo(OrderPaymentResultDMO orderPaymentResultDMO);

	/**
	 * 根据订单号更新订单行中未取消非异常且已发货的订单行为已收货
	 * 
	 * @param orderPaymentResultDMO
	 * @return
	 */
	public int updateOrderItemStatus4OrderExpire(OrderPaymentResultDMO orderPaymentResultDMO);

	public int updateOrderItemCommissionByItemNo(OrderItemPaymentDMO orderItemPaymentDMO);

	/*
	 * 查询vip的订单行信息
	 */
	public List<TradeOrderItemsDMO> selectVipOrderItem(Map paramMap);

	public BigDecimal selectSumAmountByBrandCodeAndClassCode(TradeOrderItemsDMO tradeOrderItemsDMO);

	public TradeOrderItemsDMO selectOrderNoByErpSholesalerCodeOrderNo(
			TradeOrderItemsDMO tradeOrderItemsDMO);

	public TradeOrderItemsDMO selectOrderItemNoByBrandCodeClassCode(
			TradeOrderItemsDMO tradeOrderItemsDMO);
	/*
	 * 按照品牌品类查出一个List
	 */
	public List<TradeOrderItemsDMO> selectAllItemOrderItemNoByBrandCodeClassCode(
			TradeOrderItemsDMO tradeOrderItemsDMO);
	
	/*
	 * 按照品牌品类查出一个sum金额
	 */
	public TradeOrderItemsDMO selectSumItemOrderItemNoByBrandCodeClassCode(
			TradeOrderItemsDMO tradeOrderItemsDMO);
	
	long selectTradeOrderItemsByOrderChannelCode(TradeOrderItemsDMO tradeOrderItemsDMO);

	public List<TradeOrderItemsDMO> queryListOrderByCondition4SuperManagerItem(
			TradeOrderItemsDMO tradeOrderItemsDMO);

	/*
	 * 查询取消订单行的数量
	 */
	public Integer queryCancleItemCount(TradeOrderItemsDMO tradeOrderItemsDMO);
}