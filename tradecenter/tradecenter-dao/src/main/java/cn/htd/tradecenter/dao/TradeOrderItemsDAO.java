package cn.htd.tradecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.domain.TraderOrderItemsCondition;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.dto.TradeOrdersQueryInDTO;

public interface TradeOrderItemsDAO extends BaseDAO<TradeOrderItemsDTO> {

    /**
     * 运营系统根据查询条件查询符合条件订单数量
     *
     * @param inDTO
     * @return
     */
    public long queryTradeOrderCountByCondition(@Param("entity") TradeOrdersQueryInDTO inDTO);

    /**
     * 运营系统根据条件查询符合条件订单数据
     *
     * @param inDTO
     * @param pager
     * @return
     */
    public List<TradeOrderItemsDTO> queryTradeOrderListByCondition(@Param("entity") TradeOrdersQueryInDTO inDTO,
                                                                   @Param("page") Pager<TradeOrdersQueryInDTO> pager);
    
    /**
     * 运营系统根据商品编码查询订单数量
     *
     * @param inDTO
     * @return
     */
    public long queryTradeOrderCountItemCodeAndBoxFlag(@Param("entity") TradeOrdersQueryInDTO inDTO);

    /**
     * 运营系统根据商品编码查询订单数据
     *
     * @param inDTO
     * @param pager
     * @return
     */
    public List<TradeOrderItemsDTO> queryTradeOrderListItemCodeAndBoxFlag(@Param("entity") TradeOrdersQueryInDTO inDTO,
                                                                   @Param("page") Pager<TradeOrdersQueryInDTO> pager);

    /**
     * 运营系统根据条件查询ERP下行错误订单数据
     *
     * @param inDTO
     * @param pager
     * @return
     */
    public List<TradeOrderItemsDTO> queryErpErrorTradeOrderListByCondition(@Param("entity") TradeOrdersQueryInDTO inDTO,
                                                                           @Param("page") Pager<TradeOrdersQueryInDTO> pager);

    /**
     * 运营系统根据订单号查询订单行信息
     *
     * @param orderItemDTO
     * @return
     */
    public List<TradeOrderItemsDTO> queryTradeOrderItemsByOrderNo(TradeOrderItemsDTO orderItemDTO);

    /**
     * 根据订单号和订单行号取的订单行信息
     *
     * @param orderItemDTO
     * @return
     */
    public TradeOrderItemsDTO queryTradeOrderItemsByOrderItemNo(TradeOrderItemsDTO orderItemDTO);

    /**
     * 新增订单行数据
     *
     * @param orderItemDTO
     */
    public void addTradeOrderItemInfo(TradeOrderItemsDTO orderItemDTO);

    /**
     * 根据订单行编号更新订单状态信息
     *
     * @param orderItemDTO
     * @return
     */
    public int updateTradeOrderItemsStatusInfo(TradeOrderItemsDTO orderItemDTO);

    /**
     * 订单议价时删除订单行
     *
     * @param orderItemDTO
     * @return
     */
    public int deleteTradeOrderItemsInfo(TradeOrderItemsDTO orderItemDTO);

    /**
     * 订单议价时更新订单行议价信息
     *
     * @param orderItemDTO
     * @return
     */
    public int updateTradeOrderItemsNegotiateInfo(TradeOrderItemsDTO orderItemDTO);

    /**
     * 更新ERP预售五合一下行错误状态
     *
     * @param orderItemDTO
     * @return
     */
    public int updateTradeOrderItemsErpDownStatus(TradeOrderItemsDTO orderItemDTO);

    /**
     * 更新ERP预售下行错误状态
     *
     * @param orderItemDTO
     * @return
     */
    public int updateTradeOrderItemsPreSaleDownStatus(TradeOrderItemsDTO orderItemDTO);

    /**
     * 更新收付款下行错误状态
     *
     * @param orderItemDTO
     * @return
     */
    public int updateTradeOrderItemsPostStrikeaStatus(TradeOrderItemsDTO orderItemDTO);

    /**
     * 订单议价
     *
     * @param itemsDTO
     * @return
     */
    public int changePrice(TradeOrderItemsDTO itemsDTO);

    /**
     * 确认发货，添加物流信息
     *
     * @param itemsDTO
     * @return
     */
    public int confimDeliver(TradeOrderItemsDTO itemsDTO);

    /**
     * 查询待更新客户经理信息的订单行记录
     *
     * @param condition
     * @param pager
     * @return
     */
    public List<TradeOrderItemsDTO> queryCustomerManagerOrderItems4Task(
            @Param("entity") TraderOrderItemsCondition condition, @Param("page") Pager<TradeOrderItemsDTO> pager);

    /**
     * 根据订单行编号更新订单客户经理信息信息
     *
     * @param orderItemDTO
     * @return
     */
    public int updateTradeOrderItemsCustomerManagerInfo(TradeOrderItemsDTO orderItemDTO);

}
