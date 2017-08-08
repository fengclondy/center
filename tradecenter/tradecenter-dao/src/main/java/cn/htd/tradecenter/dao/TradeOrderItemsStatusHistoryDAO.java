package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderItemsStatusHistoryDTO;

public interface TradeOrderItemsStatusHistoryDAO extends BaseDAO<TradeOrderItemsStatusHistoryDTO> {

	/**
	 * 根据订单行号查询状态变更履历
	 * 
	 * @param orderItemNo
	 * @return
	 */
	public List<TradeOrderItemsStatusHistoryDTO> queryStatusHistoryByOrderItemNo(String orderItemNo);

	/**
	 * 根据订单行号和状态查询状态变更履历
	 * 
	 * @param orderItemDTO
	 * @return
	 */
	public TradeOrderItemsStatusHistoryDTO queryStatusHistoryByOrderItemStatus(
			TradeOrderItemsStatusHistoryDTO orderItemDTO);

	/**
	 * 批量添加订单行状态数据
	 * 
	 * @param orderItemsStatusHistoryDTOList
	 */
	public void addOrderItemsStatusHistoryList(List<TradeOrderItemsStatusHistoryDTO> orderItemsStatusHistoryDTOList);

	/**
	 * 添加订单行状态数据
	 * 
	 * @param orderItemsStatusHistoryDTO
	 */
	public void addOrderItemsStatusHistory(TradeOrderItemsStatusHistoryDTO orderItemsStatusHistoryDTO);
}
