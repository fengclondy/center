package cn.htd.tradecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.OrderDealReportInDTO;
import cn.htd.tradecenter.dto.OrderDealReportOutDTO;

/**
 * Created by thinkpad on 2017/1/11.
 */
public interface OrderDealReportExportService {

    /**
     * 获取总的   成交金额、购买人数、成交商品数、下单量、成交转化率
     * @param shopId  店铺id (不能为空)
     * @param startDate  开始日期 yyyyMMdd	(如果为空，传null或"")
     * @param endDate  结束日期   yyyyMMdd	(如果为空，传null或"")
     * @return
     */
    public OrderDealReportOutDTO getOrderDealSum(OrderDealReportInDTO orderDealReportIn);


    /**
     * 获取   成交金额、购买人数、成交商品数、下单量、成交转化率 列表
     * @param shopId  店铺id (不能为空)
     * @param startDate  开始日期 yyyyMMdd	(如果为空，传null或"")
     * @param endDate  结束日期   yyyyMMdd	(如果为空，传null或"")
     * @return
     */
    public DataGrid<OrderDealReportOutDTO> getOrderDealListByPager(OrderDealReportInDTO orderDealReportIn, Pager<OrderDealReportOutDTO> pager);

}
