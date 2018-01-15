package cn.htd.goodscenter.service.venus;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.vms.*;

import java.util.List;

/**
 * VMS批量导入服务
 */
public interface VmsBatchExportService {

    /**
     * 我的商品 - 批量新增商品 （导入）
     * @param batchAddItemInDTOList
     * @return
     */
    ExecuteResult<BatchAddItemOutDTO> batchAddItem(List<BatchAddItemInDTO> batchAddItemInDTOList);

    /**
     * 批量上架查询大B下未上架，下架的商品
     * 查询大B下所有未上架、下架的商品
     */
    ExecuteResult<DataGrid<QueryOffShelfItemOutDTO>> queryOffShelfItemBySellerId(QueryOffShelfItemInDTO queryOffShelfItemInDTO, Pager pager);

    /**
     * 批量上架
     * @param batchOnShelfInDTO
     * @return
     */
    ExecuteResult<BatchOnShelfOutDTO> batchOnShelves(BatchOnShelfInDTO batchOnShelfInDTO);

    /**
     * 批量改价
     */
    ExecuteResult<BatchModifyPriceOutDTO> batchModifyItemPrice(BatchModifyPriceInDTO batchModifyPriceInDTO);
}
