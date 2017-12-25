package cn.htd.goodscenter.service.venus;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.SpuInfoDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusStockItemInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;
import cn.htd.goodscenter.dto.venus.po.QuerySkuPublishInfoDetailParamDTO;
import cn.htd.goodscenter.dto.vms.*;

import java.util.List;

/**
 * vms2.0 商品中心接口
 * @date 2017-12-06
 * @author chenkang
 */
public interface VmsItemExportService {
    /** 我的商品 **/

    /**
     * 我的商品 - 商品列表
     * @param queryVmsMyItemListInDTO
     * @param pager
     * @return
     */
    ExecuteResult<DataGrid<QueryVmsMyItemListOutDTO>> queryMyItemList(QueryVmsMyItemListInDTO queryVmsMyItemListInDTO, Pager<String> pager);

    /**
     * 我的商品 - 商品详情
     * @param skuId
     * @return
     */
    ExecuteResult<VenusItemSkuDetailOutDTO> queryItemSkuDetail(Long skuId);

    /**
     * 我的商品 - 商品模板库列表
     * @param venusItemSpuInDTO
     * @param page
     * @return
     */
    ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>> queryItemSpuDataList(VenusItemMainDataInDTO venusItemSpuInDTO, Pager<String> page);

    /**
     * 根据商品模板ID查询商品模板详细信息
     * @param spuId
     * @return
     */
    ExecuteResult<SpuInfoDTO> queryItemSpuDetail(Long spuId);

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

    /**
     * 我的商品 - 新增商品
     * @param venusItemDTO
     * @return
     */
    ExecuteResult<String> addItem(VenusItemInDTO venusItemDTO);

    /**
     * 我的商品 - 修改商品
     * @param venusItemDTO
     * @return
     */
    ExecuteResult<String> updateItem(VenusItemInDTO venusItemDTO);

    /**
     * 我的商品 - 库存商品
     * @param venusStockItemInDTO
     * @return
     */
    ExecuteResult<String> queryErpStockItemList(VenusStockItemInDTO venusStockItemInDTO);

    /** 包厢商品 大厅商品 **/
    /**
     * 查询包厢商品列表
     * 查询大厅商品列表
     *
     * sort字段：display_quantity ；
     * order ：asc (从小到大) / desc （从大到小）
     * @param queryVmsItemPublishInfoInDTO
     * @param page 排序字段传在page中
     * @return
     */
    ExecuteResult<DataGrid<QueryVmsItemPublishInfoOutDTO>> queryItemSkuPublishInfoList(QueryVmsItemPublishInfoInDTO queryVmsItemPublishInfoInDTO, Pager<String> page);

    /**
     * 包厢商品详情
     * 大厅商品详情
     * @param querySkuPublishInfoDetailParamDTO
     * @return
     */
    ExecuteResult<VenusItemSkuPublishInfoDetailOutDTO> queryItemSkuPublishInfoDetail(QuerySkuPublishInfoDetailParamDTO querySkuPublishInfoDetailParamDTO);

    /**
     *
     * 包厢商品下架商品
     * 大厅商品下架商品
     * @param skuCode
     * @param isBoxFlag 是否包厢   0：大厅 ；  1：包厢
     * @return
     */
    ExecuteResult<String> offShelves(String skuCode, Integer isBoxFlag, Long operateId, String operateName);

    /**
     * 包厢商品上架商品
     * 大厅商品上架商品
     * @param venusItemSkuPublishInDTO
     * @return
     */
    ExecuteResult<String> onShelves(VenusItemSkuPublishInDTO venusItemSkuPublishInDTO);

    /**
     * 包厢商品修改商品
     * 大厅商品修改商品
     * @param venusItemSkuPublishInDTO
     * @return
     */
    ExecuteResult<String> modifyShelves(VenusItemSkuPublishInDTO venusItemSkuPublishInDTO);

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
}
