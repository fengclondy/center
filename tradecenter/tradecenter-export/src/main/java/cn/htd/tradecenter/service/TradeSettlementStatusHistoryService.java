package cn.htd.tradecenter.service;

import cn.htd.tradecenter.dto.bill.TradeSettlementStatusHistoryDTO;

/**
 * Created by taolei on 17/2/16.
 * 结算单状态历史
 */
public interface TradeSettlementStatusHistoryService {
    TradeSettlementStatusHistoryDTO findStatusHistory(Long id);

    void addStatusHistory(TradeSettlementStatusHistoryDTO history);
}
