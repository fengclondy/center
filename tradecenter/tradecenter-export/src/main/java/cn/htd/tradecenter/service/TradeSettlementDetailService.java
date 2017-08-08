package cn.htd.tradecenter.service;

import cn.htd.tradecenter.dto.bill.TradeSettlementDetailDTO;

/**
 * Created by taolei on 17/2/15.
 */
public interface TradeSettlementDetailService {
    TradeSettlementDetailDTO findDetail(Long id);

    void addDetail(TradeSettlementDetailDTO detailDTO);

}
