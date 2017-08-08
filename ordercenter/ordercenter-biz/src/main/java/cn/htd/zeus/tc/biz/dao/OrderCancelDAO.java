/**
 * 
 */
package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDiscountDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;

/**
 * @author ly
 *
 */
@Repository("cn.htd.zeus.tc.dao.OrderCancelDAO")
public interface OrderCancelDAO {
	
	/**
	 * 更新订单取消记录信息
	 * @param orderInfoDMO
	 * @return 更新条数
	 */
	int updateOrderCancelInfo(TradeOrdersDMO tradeOrdersDMO);
	
	/**
	 * 根据订单号和订单状态查询订单信息
	 * @param tradeOrdersDMO
	 * @return
	 */
	TradeOrdersDMO selectTradeCancelOrderByOrderNo(TradeOrdersDMO tradeOrdersDMO);
	
    
    /**
     * 根据订单号查询订单行
     * @Title: selectOrderItemsByOrderItemNo
     * @param orderNo
     * @return List<TradeOrderItemsDMO>
    */
    List<TradeOrderItemsDiscountDMO> selectOrderItemsByOrderItemNo(String orderNo);
}