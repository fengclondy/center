package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsWarehouseDetailDTO;

public interface TradeOrderItemsWarehouseDetailDAO
		extends BaseDAO<TradeOrderItemsWarehouseDetailDTO> {

	/**
	 * 运营系统根据订单号和订单行号查询订单行拆单信息
	 * 
	 * @param orderItemDTO
	 * @return
	 */
	public List<TradeOrderItemsWarehouseDetailDTO> queryOrderItemWarehouseDetailByOrderItemNo(
			TradeOrderItemsDTO orderItemDTO);

	/**
	 * 添加订单行拆单明细数据
	 * 
	 * @param itemsWarehouseDetailDTOList
	 */
	public void addOrderItemsWarehouseDetail(TradeOrderItemsWarehouseDetailDTO itemsWarehouseDetailDTOList);

	/**
	 * 批量添加订单行拆单明细数据
	 * 
	 * @param itemsWarehouseDetailDTOList
	 */
	public void addOrderItemsWarehouseDetailList(
			List<TradeOrderItemsWarehouseDetailDTO> itemsWarehouseDetailDTOList);

	/**
	 * 删除订单拆单详细信息
	 * 
	 * @param itemsWarehouseDTO
	 * @return
	 */
	public int deleteTradeOrderItemsWarehouseInfo(TradeOrderItemsWarehouseDetailDTO itemsWarehouseDTO);
}