package cn.htd.tradecenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.bill.TradeSettlementStatusHistoryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taolei on 17/2/14.
 */
public interface TradeSettlementStatusHistoryDAO extends BaseDAO<TradeSettlementStatusHistoryDTO> {
	/**
	 *
	 * @param id
	 * @return
	 */
    TradeSettlementStatusHistoryDTO queryHistoryById(@Param("id") Long id);


	/**
	 * 根据结算单号查询结算单历史记录
	 * @param settlementNo
	 * @return
	 */
	List<TradeSettlementStatusHistoryDTO> getTradeSetStatusHistoryBySetNo(@Param("settlementNo") String settlementNo);

	/**
	 * 添加结算单历史记录
	 * @param tradeSettlementStatusHistoryDTO
	 */
	int addTradeSetStatusHistory(TradeSettlementStatusHistoryDTO tradeSettlementStatusHistoryDTO);



}
