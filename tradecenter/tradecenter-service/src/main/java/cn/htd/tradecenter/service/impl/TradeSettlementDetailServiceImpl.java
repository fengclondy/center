package cn.htd.tradecenter.service.impl;

import cn.htd.tradecenter.dao.TradeSettlementDetailDAO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDetailDTO;
import cn.htd.tradecenter.service.TradeSettlementDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by taolei on 17/2/15.
 * 结算单管理
 */
@Service("tradeSettlementDetailService")
public class TradeSettlementDetailServiceImpl implements TradeSettlementDetailService {
    @Resource
    private TradeSettlementDetailDAO tradeSettlementDetailDAO;

    /**
     * 根据结算单id查询结算单
     *
     * @param id
     * @return
     */
    @Override
    public TradeSettlementDetailDTO findDetail(Long id) {
        return tradeSettlementDetailDAO.queryById(id);
    }

    /**
     * 添加结算单
     *
     * @param detailDTO
     */
    @Override
    public void addDetail(TradeSettlementDetailDTO detailDTO) {
        tradeSettlementDetailDAO.insert(detailDTO);
    }
}
