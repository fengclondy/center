package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.domain.order.TradeOrderSettlement;
import cn.htd.tradecenter.dto.TradeOrderSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeOrderItemDTO;
import cn.htd.tradecenter.dto.bill.TradeSetSellerInfoDTO;

/**
 * 
 * @author zf.zhang
 *
 */
public interface TradeOrderSettlementDAO extends BaseDAO<TradeOrderSettlementDAO>{
	
	/**
	 * 根据条件查询符合结算规则的订单
	 * @param tradeOrderSettlementDTO
	 * @return
	 */
	List<TradeOrderSettlement> getOrderSettlementsByParams(TradeOrderSettlementDTO tradeOrderSettlementDTO);
	
	/**
	 * 查询符合结算规则的订单数量
	 * @param tradeOrderSettlementDTO
	 * @return
	 */
	Integer getOrderSettlementCount(TradeOrderSettlementDTO tradeOrderSettlementDTO);
	
	/**
	 * 查询符合结算规则的订单里的所有供应商（外部供应商和商品+的订单，筛选出供应商） 渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
	 * @param tradeOrderSettlementDTO
	 * @return
	 */
	List<TradeSetSellerInfoDTO> getTradeSetSellerInfos(TradeOrderSettlementDTO tradeOrderSettlementDTO);
	
	/**
	 * 跟新订单行的结算状态
	 * @param tradeOrderItemDTO
	 */
	void updateTradeOrderItem(TradeOrderItemDTO tradeOrderItemDTO);
	
	/**
	 * 添加订单行历史
	 * @param tradeOrderItemDTO
	 */
	void addTradeOrderItemStatusHis(TradeOrderItemDTO tradeOrderItemDTO);
	
	
}
