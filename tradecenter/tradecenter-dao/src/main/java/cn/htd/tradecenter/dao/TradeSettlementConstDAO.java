package cn.htd.tradecenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.bill.TradeSettlementConstDTO;


/**
 * Created by taolei on 17/2/14.
 * 
 */
public interface TradeSettlementConstDAO extends BaseDAO<TradeSettlementConstDTO> {
	
	
	List<Map<String,Object>> getTradeSetConst(@Param("params")Map<String, Object> params);
	
	/**
	 * 添加单条常量
	 * @param tradeSettlementConstDTO
	 */
	void addTradeSettlementConst(TradeSettlementConstDTO tradeSettlementConstDTO);
	
	/**
	 * 根据type和key查询单条常量
	 * @param tradeSettlementConstDTO
	 * @return
	 */
	TradeSettlementConstDTO getSetConsByTypeAndKey(TradeSettlementConstDTO tradeSettlementConstDTO);
	
	/**
	 * 根据type查询多条常量
	 * @param tradeSettlementConstDTO
	 * @return
	 */
	List<TradeSettlementConstDTO> getSetConsByType(TradeSettlementConstDTO tradeSettlementConstDTO);

}
