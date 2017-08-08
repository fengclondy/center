package cn.htd.tradecenter.dao;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.ItemSkuSellReportInDTO;
import cn.htd.tradecenter.dto.ItemSkuSellReportOutDTO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by thinkpad on 2017/1/11.
 */
public interface ItemSkuSellReportDAO extends BaseDAO<ItemSkuSellReportOutDTO> {

    /**
     *
     * 分页查询 销售额、销售量
     * @param shopId  店铺id (不能为空)
     * @param skuId  skuid (不能为空)
     * @param startDate  开始日期 yyyyMMdd	(如果为空，传null或"")
     * @param endDate  结束日期   yyyyMMdd	(如果为空，传null或"")
     * @return List<ItemSkuSellReportOut>
     */
    public List<ItemSkuSellReportOutDTO> queryItemSkuSellReportList (@Param("reportIn") ItemSkuSellReportInDTO itemSkuSellReportIn,
                                                                     @Param("page") Pager<ItemSkuSellReportOutDTO> pager);

}
