/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	TradeOrderStatusHistoryUpdateDAO.java
 * Author:   	jiangkun
 * Date:     	2016年11月23日
 * Description: 订单状态DAO
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月23日	1.0			创建
 */
package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderStatusHistoryDTO;

/**
 * 订单状态DAO
 * 
 * @author jiangkun
 */
public interface TradeOrderStatusHistoryDAO extends BaseDAO<TradeOrderStatusHistoryDTO> {

	/**
	 * 添加订单状态数据
	 * 
	 * @param orderStatusHistoryDTO
	 */
	public void addTradeOrderStatusHistory(TradeOrderStatusHistoryDTO orderStatusHistoryDTO);

	/**
	 * 批量添加订单状态数据
	 * 
	 * @param orderStatusHistoryDTOList
	 */
	public void addTradeOrderStatusHistoryList(List<TradeOrderStatusHistoryDTO> orderStatusHistoryDTOList);

	/**
	 * 根据订单号取得订单状态数据
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<TradeOrderStatusHistoryDTO> queryStatusHistoryByOrderNo(String orderNo);

	/**
	 * 根据订单号状态查询订单状态信息
	 * 
	 * @param orderDTO
	 * @return
	 */
	public TradeOrderStatusHistoryDTO queryStatusHistoryByOrderStatus(TradeOrderStatusHistoryDTO orderDTO);
}