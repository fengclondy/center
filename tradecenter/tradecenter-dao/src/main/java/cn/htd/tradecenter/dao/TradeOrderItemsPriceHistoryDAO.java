package cn.htd.tradecenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderItemsPriceHistoryDTO;

import java.util.List;

public interface TradeOrderItemsPriceHistoryDAO extends BaseDAO<TradeOrderItemsPriceHistoryDTO> {

	/**
	 * 保存订单议价历史记录
	 * 
	 * @param priceHistoryDTO
	 */
	public void insertItemPriceHistoryInfo(TradeOrderItemsPriceHistoryDTO priceHistoryDTO);

	public List<TradeOrderItemsPriceHistoryDTO> queryItemPriceHistoryInfoByOrderItemNo(String orderItemNo);
}