package cn.htd.tradecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.ItemSkuSellReportInDTO;
import cn.htd.tradecenter.dto.ItemSkuSellReportOutDTO;

/**
 * Created by thinkpad on 2017/1/11.
 */
public interface ItemSkuSellReportExportService {
    /**
     * 获取   销售额、销售量 列表及数量
     * @param shopId  店铺id (不能为空)
     * @param shopId  skuid (不能为空)
     * @param startDate  开始日期 yyyyMMdd	(如果为空，传null或"")
     * @param endDate  结束日期   yyyyMMdd	(如果为空，传null或"")
     * @return
     */
    public DataGrid<ItemSkuSellReportOutDTO> getItemSkuSellListByPager(ItemSkuSellReportInDTO itemSkuSellReportIn, Pager<ItemSkuSellReportOutDTO> pager);
}
