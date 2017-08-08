package cn.htd.tradecenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dao.OrderDealReportDAO;
import cn.htd.tradecenter.dto.OrderDealReportInDTO;
import cn.htd.tradecenter.dto.OrderDealReportOutDTO;
import cn.htd.tradecenter.service.OrderDealReportExportService;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by thinkpad on 2017/1/11.
 */
@Service("orderDealReportExportService")
public class OrderDealReportExportServiceImpl implements OrderDealReportExportService {
    private final static Logger logger = LoggerFactory.getLogger(OrderDealReportExportServiceImpl.class);
    @Resource
    private OrderDealReportDAO orderDealReportDAO;

    @Override
    public OrderDealReportOutDTO getOrderDealSum(OrderDealReportInDTO orderDealReportIn) {
        orderDealReportIn.setStartDate(orderDealReportIn.getStartDate());
        orderDealReportIn.setEndDate(orderDealReportIn.getEndDate());

        logger.info("\n 方法[{}]，入参：[{}]", "orderDealReportDAOImpl-getOrderDealSum",
                JSONObject.toJSONString(orderDealReportIn));

        // 查询 成交金额 、 成交人数、 商品数
        OrderDealReportOutDTO payReport = orderDealReportDAO.getOrderDealPaySum(orderDealReportIn);
        // 查询 订单数、成交转化率
        OrderDealReportOutDTO numReport = orderDealReportDAO.getOrderDealNumSum(orderDealReportIn);
        if (payReport != null && numReport != null) {
            payReport.setOrderNum(numReport.getOrderNum());
            payReport.setPayConversion(numReport.getPayConversion());
            return payReport;
        } else {
            return new OrderDealReportOutDTO();
        }
    }

    @Override
    public DataGrid<OrderDealReportOutDTO> getOrderDealListByPager(OrderDealReportInDTO orderDealReportIn,
                                                                   Pager<OrderDealReportOutDTO> pager) {
        logger.info("\n 方法[{}]，入参：[{}]", "orderDealReportDAOImpl-getOrderDealListByPager",
                JSONObject.toJSONString(orderDealReportIn));

        orderDealReportIn.setStartDate(orderDealReportIn.getStartDate());
        orderDealReportIn.setEndDate(orderDealReportIn.getEndDate());

        DataGrid<OrderDealReportOutDTO> res = new DataGrid<OrderDealReportOutDTO>();
        // 查询数量
        Long count = orderDealReportDAO.queryOrderDealReportCount(orderDealReportIn);
        // 查询列表
        List<OrderDealReportOutDTO> list = orderDealReportDAO.queryOrderDealReportList(orderDealReportIn, pager);
        if(orderDealReportIn.getDateFormat()!=null &&
                orderDealReportIn.getDateFormat().length()>0){
            if(null!=list && list.size()>0){
                for (OrderDealReportOutDTO od : list) {
                    if(null != od.getDealDate() &&
                            StringUtils.isNotEmpty(od.getDealDate())){
                        od.setDealDate(orderDealReportIn.getDateFormat());
                    }
                }
            }
        }

        res.setTotal(count);
        res.setRows(list);
        return res;
    }

}
