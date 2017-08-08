package cn.htd.tradecenter.dao;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.OrderDealReportInDTO;
import cn.htd.tradecenter.dto.OrderDealReportOutDTO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by thinkpad on 2017/1/11.
 */
public interface OrderDealReportDAO extends BaseDAO<OrderDealReportOutDTO> {
    /**
     *
     * 查询 成交金额 、 成交人数、 商品数
     * @param shopId  店铺id (不能为空)
     * @param startDate  开始日期 yyyyMMdd	(如果为空，传null或"")
     * @param endDate  结束日期   yyyyMMdd	(如果为空，传null或"")
     * @return OrderDealReport
     */
    public OrderDealReportOutDTO getOrderDealPaySum(@Param("reportIn") OrderDealReportInDTO orderDealReportIn);

    /**
     *
     * 查询 订单数、成交转化率
     * @param shopId  店铺id (不能为空)
     * @param startDate  开始日期 yyyyMMdd	(如果为空，传null或"")
     * @param endDate  结束日期   yyyyMMdd	(如果为空，传null或"")
     * @return OrderDealReport
     */
    public OrderDealReportOutDTO getOrderDealNumSum(@Param("reportIn") OrderDealReportInDTO orderDealReportIn);


    /**
     *
     * 分页查询 成交金额 、 成交人数、 商品数 、订单数、成交转化率
     * @param shopId  店铺id (不能为空)
     * @param startDate  开始日期 yyyyMMdd	(如果为空，传null或"")
     * @param endDate  结束日期   yyyyMMdd	(如果为空，传null或"")
     * @return List<OrderDealReport>
     */
    public List<OrderDealReportOutDTO> queryOrderDealReportList (@Param("reportIn") OrderDealReportInDTO orderDealReportIn,
                                                                 @Param("page") Pager<OrderDealReportOutDTO> pager);

    /**
     *
     * <p>Discription:[查询分页的总数]</p>
     * @param shopId  店铺id (不能为空)
     * @param startDate  开始日期 yyyyMMdd	(如果为空，传null或"")
     * @param endDate  结束日期   yyyyMMdd	(如果为空，传null或"")
     * @return List<OrderDealReport>
     */
    public Long queryOrderDealReportCount (@Param("reportIn") OrderDealReportInDTO orderDealReportIn);
}

