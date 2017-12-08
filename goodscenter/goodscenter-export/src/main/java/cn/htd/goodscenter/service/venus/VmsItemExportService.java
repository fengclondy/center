package cn.htd.goodscenter.service.venus;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;
import cn.htd.goodscenter.dto.vms.BatchAddItemInDTO;
import cn.htd.goodscenter.dto.vms.BatchAddItemOutDTO;
import cn.htd.goodscenter.dto.vms.QueryVmsMyItemListInDTO;
import cn.htd.goodscenter.dto.vms.QueryVmsMyItemListOutDTO;

import java.util.List;

/**
 * vms2.0 商品中心接口
 * @date 2017-12-06
 * @author chenkang
 */
public interface VmsItemExportService {
    /**
     * 我的商品 - 商品列表
     * @param queryVmsMyItemListInDTO
     * @param pager
     * @return
     */
    ExecuteResult<DataGrid<QueryVmsMyItemListOutDTO>> queryMyItemList(QueryVmsMyItemListInDTO queryVmsMyItemListInDTO, Pager<String> pager);

    /**
     * 我的商品 - 商品详情
     * @param itemSkuId
     * @return
     */
    ExecuteResult<VenusItemSkuDetailOutDTO> queryItemSkuDetail(Long itemSkuId);

    /**
     * 我的商品 - 商品模板库列表
     * @param venusItemSpuInDTO
     * @param page
     * @return
     */
    ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>> queryItemSpuDataList(VenusItemMainDataInDTO venusItemSpuInDTO, Pager<String> page);

    /**
     * 我的商品 - 申请商品
     * @param spuIdList
     * @param sellerId
     * @param shopId
     * @param operatorId
     * @param operatorName
     * @return
     */
    ExecuteResult<String> applyItemSpu2HtdProduct(List<Long> spuIdList, String sellerId, String shopId, String operatorId, String operatorName);

    /**
     * 我的商品 - 批量新增商品 （导入）
     * @param batchAddItemInDTOList
     * @return
     */
    ExecuteResult<BatchAddItemOutDTO> batchAddItem(List<BatchAddItemInDTO> batchAddItemInDTOList);
}
