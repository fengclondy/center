/**
 * 
 */
package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryReqDTO;


/**
 * @author ly
 *
 */
public interface OrderInfoService {
	
	/*
	 * 根据大B和小Bid查询订单表的最新一条记录
	 * @param orderQueryReqDTO
	 * @return OrderCancelInfoResDTO
	 */
	public TradeOrdersDMO queryOrderBySellerIdAndBuyerId(OrderQueryReqDTO orderQueryReqDTO);
	
}
