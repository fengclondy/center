package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.TradeOrderErpDistributionDMO;

@Repository("cn.htd.zeus.tc.biz.dao.TradeOrderErpDistributionDAO")
public interface TradeOrderErpDistributionDAO {

	/*
	 *根据订单分销单信息表主键更新订单分销单信息表-下行中间件专用
	 *@param  tradeOrderErpDistributionDMO
	 *@return int
	 */
	public int updateTradeOrderErpDistributionByPrimaryKey(TradeOrderErpDistributionDMO tradeOrderErpDistributionDMO);

	/*
	 *根据订单分销单信息表主键更新订单分销单信息表
	 *@param  tradeOrderErpDistributionDMO
	 *@return int
	 */
	public int updateTradeOrderErpDistribution(TradeOrderErpDistributionDMO tradeOrderErpDistributionDMO);

	/*
	 * 根据分销单表的id查询订单号和订单行号
	 */
	public TradeOrderErpDistributionDMO selectTradeOrderErpDistributionByErpSholesalerCode(TradeOrderErpDistributionDMO record);

	/**
	 * 新增订单分销
	  * insertSelective(方法的作用)
	  * @Title: insertSelective
	  * @param record
	  * @return int
	  * @throws
	 */
	int insertSelective(TradeOrderErpDistributionDMO record);

	/**
	 * 根据订单号品牌id五级类目查询是否有分销单
	  * selectOrderItemNosBySelective(方法的作用)
	  * @Title: selectOrderItemNosBySelective
	  * @param record
	  * @return TradeOrderErpDistributionDMO
	  * @throws
	 */
	TradeOrderErpDistributionDMO selectOrderItemNosBySelective(TradeOrderErpDistributionDMO record);

	/**
	 * 根据订单号品牌id五级类目更新分销单表
	  * updateOrderItemNosBySelective(方法的作用)
	  * @Title: updateOrderItemNosBySelective
	  * @param record
	  * @return int
	  * @throws
	 */
	int updateOrderItemNosBySelective(TradeOrderErpDistributionDMO record);
	
	/*
	 * 根据erp分销单号更新分销单表
	 */
	int updateTradeOrderErpDistributionByErpSholesalerCode(TradeOrderErpDistributionDMO record);
	
	/**
	 * 根据订单号查询分销单号List
	 * @param record
	 * @return
	 */
	List<TradeOrderErpDistributionDMO> selectErpLockBalanceCodeByOrderNo(TradeOrderErpDistributionDMO record); 
}
