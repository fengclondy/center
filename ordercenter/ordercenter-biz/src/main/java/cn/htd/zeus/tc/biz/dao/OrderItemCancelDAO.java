/**
 * 
 */
package cn.htd.zeus.tc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;

/**
 * @author ly
 *
 */
@Repository("cn.htd.zeus.tc.dao.OrderItemCancelDAO")
public interface OrderItemCancelDAO {
	
	/**
	 * 根据订单号更新订单取消记录信息
	 * @param orderInfoDMO
	 * @return 更新条数
	 */
	int updateOrderItemCancelInfoByOrderNo(TradeOrderItemsDMO tradeOrdersDMO);
	
	
	/**
	 * 根据订单行号更新订单取消记录信息
	 * @param orderInfoDMO
	 * @return 更新条数
	 */
	int updateOrderItemCancelInfoByOrderItemNo(TradeOrderItemsDMO tradeOrdersDMO);
	/**
	 * 根据订单号和订单状态查询订单信息
	 * @param tradeOrderItemsDMO
	 * @return
	 */
	TradeOrderItemsDMO selectTradeCancelOrderItemByOrderNo(TradeOrderItemsDMO tradeOrderItemsDMO);

}
