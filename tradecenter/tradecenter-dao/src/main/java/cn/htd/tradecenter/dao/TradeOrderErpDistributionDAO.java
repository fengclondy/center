package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderErpDistributionDTO;

public interface TradeOrderErpDistributionDAO extends BaseDAO<TradeOrderErpDistributionDTO> {

	/**
	 * 运营系统根据订单号查询分销单信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<TradeOrderErpDistributionDTO> queryOrderErpDistributionByOrderNo(String orderNo);

	/**
	 * 运营系统根据分销单数据ID查询分销单信息
	 * 
	 * @param id
	 * @return
	 */
	public TradeOrderErpDistributionDTO queryOrderErpDistributionById(Long id);

	/**
	 * 新增ERP分销单记录数据
	 * 
	 * @param erpDistributionDTO
	 */
	public void addErpDistributionInfo(TradeOrderErpDistributionDTO erpDistributionDTO);

	/**
	 * 更新订单分销单状态信息
	 * 
	 * @param erpDistributionDTO
	 * @return
	 */
	public int updateTradeOrdersErpDistributionStatusInfo(TradeOrderErpDistributionDTO erpDistributionDTO);

	/**
	 * 更新订单分销单发送状态
	 * 
	 * @param erpDistributionDTO
	 * @return
	 */
	public int updateTradeOrdersErpDistributionTimes(TradeOrderErpDistributionDTO erpDistributionDTO);
}