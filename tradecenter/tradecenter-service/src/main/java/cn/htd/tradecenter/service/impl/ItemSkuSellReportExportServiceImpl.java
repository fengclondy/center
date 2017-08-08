package cn.htd.tradecenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dao.ItemSkuSellReportDAO;
import cn.htd.tradecenter.dto.ItemSkuSellReportInDTO;
import cn.htd.tradecenter.dto.ItemSkuSellReportOutDTO;
import cn.htd.tradecenter.service.ItemSkuSellReportExportService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by thinkpad on 2017/1/11.
 */

@Service("itemSkuSellReportExportService")
public class ItemSkuSellReportExportServiceImpl implements ItemSkuSellReportExportService {

    private final static Logger logger = LoggerFactory.getLogger(ItemSkuSellReportExportServiceImpl.class);
    @Resource
    private ItemSkuSellReportDAO itemSkuSellReportDAO;
    @Override
    public DataGrid<ItemSkuSellReportOutDTO> getItemSkuSellListByPager(ItemSkuSellReportInDTO itemSkuSellReportIn, Pager<ItemSkuSellReportOutDTO> pager) {
        logger.info("\n 方法[{}]，入参：[{}]", "itemSkuSellReportDAOImpl-getItemSkuSellListByPager",
                JSONObject.toJSONString(itemSkuSellReportIn));

        itemSkuSellReportIn.setStartDate(itemSkuSellReportIn.getStartDate());
        itemSkuSellReportIn.setEndDate(itemSkuSellReportIn.getEndDate());

        DataGrid<ItemSkuSellReportOutDTO> res = new DataGrid<ItemSkuSellReportOutDTO>();
        try {
            // 查询数量
            Integer count = itemSkuSellReportDAO.queryItemSkuSellReportList(itemSkuSellReportIn,null).size();
            if (count != null) {
                // 查询列表
                List<ItemSkuSellReportOutDTO> list = itemSkuSellReportDAO.queryItemSkuSellReportList(itemSkuSellReportIn,
                        pager);

                //根据商品sku编码查询商品编码
                for (int i = 0; i < list.size(); i++) {
                    list.get(0).setGoodsCode(list.get(0).getGoodSkuCode());
                }

                res.setTotal(new Long(count));
                res.setRows(list);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return res;
    }
}
