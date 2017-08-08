package cn.htd.tradecenter.service.impl;

import cn.htd.tradecenter.dao.TradeSettlementStatusHistoryDAO;
import cn.htd.tradecenter.dto.bill.TradeSettlementStatusHistoryDTO;
import cn.htd.tradecenter.service.TradeSettlementStatusHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by taolei on 17/2/16.
 */
@Service("tradeSettlementStatusHistoryService")
public class TradeSettlementStatusHistoryServiceImpl implements TradeSettlementStatusHistoryService {

    @Resource
    private TradeSettlementStatusHistoryDAO tradeSettlementStatusHistoryDao;

    @Override
    public TradeSettlementStatusHistoryDTO findStatusHistory(Long id) {
        return tradeSettlementStatusHistoryDao.queryHistoryById(id);
    }

    @Override
    public void addStatusHistory(TradeSettlementStatusHistoryDTO history) {
        tradeSettlementStatusHistoryDao.insert(history);
    }
}
