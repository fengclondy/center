package cn.htd.tradecenter.dao;

import java.util.List;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.domain.order.TradeSettlementComp;
import cn.htd.tradecenter.dto.bill.TradeSettlementCompDTO;

/**
 * 
 * @author zf.zhang
 *
 */
public interface TradeSettlementCompDAO extends BaseDAO<TradeSettlementCompDAO>{
	
	
	/**
	 * 添加结算补偿信息
	 * @param tradeSettlementCompDTO
	 */
	void addTradeSettlementComp(TradeSettlementCompDTO tradeSettlementCompDTO);
	
	/**
	 * 根据条件查询补偿的结算单
	 * @param tradeSettlementCompDTO
	 * @return
	 */
	List<TradeSettlementComp> getTradeSettlementCompsByParams(TradeSettlementCompDTO tradeSettlementCompDTO);
	
}
