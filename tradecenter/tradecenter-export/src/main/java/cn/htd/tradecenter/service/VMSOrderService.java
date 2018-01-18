package cn.htd.tradecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.TradeOrdersShowDTO;
import cn.htd.tradecenter.dto.VenusTradeOrdersQueryInDTO;

public interface VMSOrderService {

    public ExecuteResult<DataGrid<TradeOrdersShowDTO>> queryVMSpendingOrderByCondition(VenusTradeOrdersQueryInDTO conditionDTO, Pager<VenusTradeOrdersQueryInDTO> pager);
}
