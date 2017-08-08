package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderPayInfoDTO;

public interface TradeOrderPayInfoDAO extends BaseDAO<TradeOrderPayInfoDTO> {

	/**
	 * 根据订单号查询锁定余额信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<TradeOrderPayInfoDTO> queryOrderPayInfoByOrderNo(String orderNo);

	/**
	 * 根据订单号删除收付款记录
	 * 
	 * @param orderNo
	 */
	public void deleteOrderPayInfoByOrderNo(String orderNo);

	/**
	 * 新增订单收付款信息
	 * 
	 * @param payInfo
	 */
	public void addOrderPayInfo(TradeOrderPayInfoDTO payInfo);

	/**
	 * 根据订单行信息更新订单行收付款状态
	 * 
	 * @param payInfoDTO
	 */
	public int updateOrderPayTimesByOrderItem(TradeOrderPayInfoDTO payInfoDTO);

}