package cn.htd.tradecenter.dao;

import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.TradeOrdersShowDTO;
import cn.htd.tradecenter.dto.VenusTradeOrdersQueryInDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VMSOrderDAO {

    /**
     * 查询VMS待处理订单的集合
     *
     * @param conditionDTO
     * @param pager
     * @return
     */
    public List<TradeOrdersShowDTO> queryVMSpendingOrderByCondition(@Param("entity") VenusTradeOrdersQueryInDTO conditionDTO,
                                                                    @Param("page") Pager<VenusTradeOrdersQueryInDTO> pager);


    /**
     * 查询VMS待处理订单的数量
     *
     * @param conditionDTO
     * @return
     */
    public long queryVMSpendingOrderCountByCondition(@Param("entity") VenusTradeOrdersQueryInDTO conditionDTO);
}
