/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	CreateTradeOrderService.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: 新增订单服务  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.RedoErpWorkDTO;
import cn.htd.tradecenter.dto.TradeOrderConfirmDTO;
import cn.htd.tradecenter.dto.TradeOrderItemStockDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsShowDTO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;
import cn.htd.tradecenter.dto.TradeOrdersQueryInDTO;
import cn.htd.tradecenter.dto.TradeOrdersShowDTO;
import cn.htd.tradecenter.dto.VenusConfirmTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusNegotiateTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusTradeOrdersQueryInDTO;
import cn.htd.tradecenter.dto.VenusVerifyTradeOrdersInDTO;

public interface TradeOrderService {

	/**
	 * VMS新增订单接口
	 * 
	 * @param venusInDTO
	 * @return
	 */
	public ExecuteResult<TradeOrdersDTO> createVenusTradeOrderInfo(VenusCreateTradeOrderDTO venusInDTO);

	/**
	 * VMS确认订单
	 * 
	 * @param confirmInDTO
	 * @return
	 */
	public ExecuteResult<TradeOrdersDTO> confirmVenusTradeOrderInfo(VenusConfirmTradeOrderDTO confirmInDTO);

	/**
	 * 商城确认订单
	 * 
	 * @param tradeOrderConfirmDTO
	 * @return
	 */
	public ExecuteResult<TradeOrdersDTO> confirmVenusOrderByMember(TradeOrderConfirmDTO tradeOrderConfirmDTO);

	/**
	 * VMS订单处理列表查询
	 * 
	 * @param conditionDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<TradeOrdersShowDTO>> queryVenusDealTradeOrderListByCondition(
			VenusTradeOrdersQueryInDTO conditionDTO, Pager<VenusTradeOrdersQueryInDTO> pager);

	/**
	 * VMS订单审核列表导出
	 * 
	 * @param conditionDTO
	 * @return
	 */
	public ExecuteResult<List<TradeOrdersShowDTO>> exportVenusDealTradeOrderListByCondition(
			VenusTradeOrdersQueryInDTO conditionDTO);

	/**
	 * VMS订单审核列表查询
	 * 
	 * @param conditionDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<TradeOrdersShowDTO>> queryVenusVerifyTradeOrderListByCondition(
			VenusTradeOrdersQueryInDTO conditionDTO, Pager<VenusTradeOrdersQueryInDTO> pager);

	/**
	 * VMS订单审核列表导出
	 * 
	 * @param conditionDTO
	 * @return
	 */
	public ExecuteResult<List<TradeOrdersShowDTO>> exportVenusVerifyTradeOrderListByCondition(
			VenusTradeOrdersQueryInDTO conditionDTO);

	/**
	 * VMS商品+订单列表查询
	 * 
	 * @param conditionDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> queryVenusProductPlusTradeOrderListByCondition(
			TradeOrdersQueryInDTO conditionDTO, Pager<TradeOrdersQueryInDTO> pager);

	/**
	 * VMS订单审核通过
	 * 
	 * @param verifyDTO
	 * @return
	 */
	public ExecuteResult<TradeOrdersDTO> approveTradeOrderInfo(VenusVerifyTradeOrdersInDTO verifyDTO);

	/**
	 * 运营系统订单列表查询
	 * 
	 * @param inDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> queryTradeOrderListByCondition(TradeOrdersQueryInDTO inDTO,
			Pager<TradeOrdersQueryInDTO> pager);

	/**
	 * 运营系统查询商品对应的订单集合
	 * 
	 * @param inDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> queryTradeOrderHmsListByItemCodeAndBoxFlag(
			TradeOrdersQueryInDTO inDTO, Pager<TradeOrdersQueryInDTO> pager);

	/**
	 * 运营系统或者商品的库存以及锁库存
	 * 
	 * @param orderNo
	 * @return
	 */
	public ExecuteResult<TradeOrderItemStockDTO> getItemStockAndLockStock(TradeOrdersQueryInDTO inDTO);

	/**
	 * VMS系统订单详情查询
	 * 
	 * @param orderNo
	 * @return
	 */
	public ExecuteResult<TradeOrdersShowDTO> queryVenusTradeOrderInfo(String orderNo);

	/**
	 * 运营系统订单详情查询
	 * 
	 * @param orderNo
	 * @return
	 */
	public ExecuteResult<TradeOrdersShowDTO> queryTradeOrderInfo(String orderNo);

	/**
	 * 运营系统订单行详情查询
	 * 
	 * @param orderNo
	 * @param orderItemNo
	 * @return
	 */
	public ExecuteResult<TradeOrderItemsShowDTO> queryTradeOrderItemInfo(String orderNo, String orderItemNo);

	/**
	 * VMS订单议价处理
	 * 
	 * @param negotiateOrderDTO
	 * @return
	 */
	public ExecuteResult<TradeOrdersDTO> negotiateTradeOrderInfo(VenusNegotiateTradeOrderDTO negotiateOrderDTO);

	/**
	 * 运营系统异常订单列表查询
	 * 
	 * @param inDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> queryTradeOrderErrorListByCondition(
			TradeOrdersQueryInDTO inDTO, Pager<TradeOrdersQueryInDTO> pager);

	/**
	 * 运营系统异常订单重新处理
	 * 
	 * @param workDTO
	 * @return
	 */
	public ExecuteResult<String> redoErpDownWork(RedoErpWorkDTO workDTO);
}
