package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderItemsDiscountDTO;

public interface TradeOrderItemsDiscountDAO extends BaseDAO<TradeOrderItemsDiscountDTO> {

	/**
	 * 运营系统根据订单号查询订单行优惠信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<TradeOrderItemsDiscountDTO> queryItemDiscountByOrderNo(String orderNo);

}