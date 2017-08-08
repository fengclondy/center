package cn.htd.zeus.tc.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsWarehouseDetailDMO;

@Repository("cn.htd.zeus.tc.biz.dao.OrderCompensateERPDAO")
public interface OrderCompensateERPDAO {
	
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
	 * 五合一异常订单查询
	 */
	public List<OrderCompensateERPDMO> selectErpDistributionExceptionOrdersList(Map paramMap);
}
