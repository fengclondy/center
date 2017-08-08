package cn.htd.zeus.tc.biz.service;

import java.util.List;
import java.util.Map;

import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPCallBackDMO;
import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderErpDistributionDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsWarehouseDetailDMO;
import cn.htd.zeus.tc.dto.resquest.OrderCompensateERPCallBackReqDTO;

public interface OrderCompensateERPService {
	
	/*
	 * 订单下行ERP补偿查询,查询订单分销单信息表和订单信息表
	 *@param paramMap
	 *@return List<查询订单分销单信息表和订单信息表>
	 */
	public List<OrderCompensateERPDMO> selectErpDistributionOrdersList(Map paramMap);
	
	/*
	 * 订单下行ERP补偿查询,查询订单行信息表
	 * @param tradeOrderItemsDmo
	 * @return List<订单行信息表>
	 */
	public TradeOrderItemsDMO selectTradeOrderItemsInfo(TradeOrderItemsDMO tradeOrderItemsDMO);
	
	/*
	 * 订单下行ERP补偿查询,查询订单行拆单明细信息表
	 * @param tradeOrderItemsWarehouseDetailDmo
	 * @return List<订单行拆单明细信息表>
	 */
	public List<TradeOrderItemsWarehouseDetailDMO> selectTradeOrderItemsWarehouseDetailList(TradeOrderItemsWarehouseDetailDMO tradeOrderItemsWarehouseDetailDMO);
	
	/*
	 * 订单下行ERP补偿,组装并发送mq
	 */
	public void compensateERP(OrderCompensateERPDMO[] tasks);
	
	/*
	 * 中间件回调订单中心
	 */
	public OrderCompensateERPCallBackDMO executeOrderCompensateERPCallBack(OrderCompensateERPCallBackReqDTO orderCompensateERPCallBackReqDTO);
	
	/*
	 *根据订单分销单信息表主键更新订单分销单信息表
	 *@param  tradeOrderErpDistributionDMO
	 *@return int
	 */
	public int updateTradeOrderErpDistributionByPrimaryKey(TradeOrderErpDistributionDMO tradeOrderErpDistributionDMO);
}
