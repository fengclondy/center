package cn.htd.goodscenter.service.venus;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.ItemSalesDefaultArea;
import cn.htd.goodscenter.dto.venus.indto.VenusBatchDeleteItemSkuInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSetShelfStatusInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuAutoShelfStatusInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInfoInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusQueryDropdownItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusStockItemInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemListStyleOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemMainDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusOrderItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusQueryDropdownItemDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusQueryDropdownItemListOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusWarningStockLevelDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusWarningStockLevelListOutDTO;
import cn.htd.goodscenter.dto.venus.po.QuerySkuPublishInfoDetailParamDTO;
import cn.htd.goodscenter.dto.venus.po.QueryVenusItemListParamDTO;

public interface VenusItemExportService {
	public ExecuteResult<String> addItem(VenusItemInDTO venusItemDTO);
	
	public ExecuteResult<String> updateItem(VenusItemInDTO venusItemDTO);
	
	public ExecuteResult<DataGrid<VenusItemListStyleOutDTO>> queryItemSkuList(QueryVenusItemListParamDTO queryVenusItemListPo,Pager<String> pager);
	
	/**
	 * 查询商品sku详情
	 * 
	 * @param itemSkuId
	 * @return
	 */
	public ExecuteResult<VenusItemSkuDetailOutDTO> queryItemSkuDetail(Long itemSkuId);
	
	public ExecuteResult<VenusOrderItemSkuDetailOutDTO> queryOrderItemSkuDetail(String itemSkuCode,String shelfType);
	
	public ExecuteResult<String> applyHtdProduct(List<Long> itemIdList,String sellerId,String shopId,String operatorId,String operatorName);
	
	public ExecuteResult<DataGrid<VenusItemSkuPublishInfoOutDTO>> queryItemSkuPublishInfoList(VenusItemSkuPublishInfoInDTO venusItemSkuPublishInfo,Pager<String> page);
	
	/**
	 * 查询上架商品明细
	 * 
	 * @param querySkuPublishInfoDetailParamDTO
	 * @return
	 */
	public ExecuteResult<VenusItemSkuPublishInfoDetailOutDTO> queryItemSkuPublishInfoDetail(QuerySkuPublishInfoDetailParamDTO querySkuPublishInfoDetailParamDTO);
	
	/**
	 * 平台公司商品上下架
	 * 
	 * @param venusItemSkuPublishInDTO
	 * @return
	 */
	public ExecuteResult<String> txPublishItemSkuInfo(VenusItemSkuPublishInDTO venusItemSkuPublishInDTO);
	
	public ExecuteResult<String> txBatchSetItemSkuOnShelfStatus(VenusItemSetShelfStatusInDTO venusItemSetShelfStatusInDTO);
	
	public ExecuteResult<String> txBatchDeleteItemSku(VenusBatchDeleteItemSkuInDTO venusBatchDeleteItemSkuInDTO);
	
	public ExecuteResult<DataGrid<VenusItemMainDataOutDTO>> queryItemMainDataList(VenusItemMainDataInDTO venusItemSpuInDTO,Pager<String> page);
	
	/**
	 * 查询库存商品列表
	 * 
	 * @param venusStockItemInDTO
	 * @return
	 */
	public ExecuteResult<String> queryVenusStockItemList(VenusStockItemInDTO venusStockItemInDTO);
	
	/**
	 * 查询协议商品列表
	 *  
	 * @param venusStockItemInDTO
	 * @return
	 */
	public ExecuteResult<String> queryVenusAggrementItemList(VenusStockItemInDTO venusStockItemInDTO);
	
	/**
	 * 保存自动上下架规则
	 * @param venusItemSkuAutoShelfStatusInDTO
	 * @return
	 */
	public ExecuteResult<String> txBatchSetItemSkuAutoOnShelfStatus(VenusItemSkuAutoShelfStatusInDTO venusItemSkuAutoShelfStatusInDTO);
	
	/**
	 * 查询默认销售区域
	 * 
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<List<ItemSalesDefaultArea>> queryDefaultSalesAreaList(Long sellerId);
	
	/**
	 * 保存销售区域
	 * 
	 * @return
	 */
	public ExecuteResult<String> saveDefaultSalesArea(List<ItemSalesDefaultArea> itemSalesDefaultAreaList);
	
	/**
	 * 根据品类和品牌查询商品列表
	 * 
	 * @param venusQueryDropdownItemInDTO
	 * @return
	 */
	public ExecuteResult<List<VenusQueryDropdownItemListOutDTO>> queryDropdownItemList(VenusQueryDropdownItemInDTO venusQueryDropdownItemInDTO);
	
	/**
	 * 查询venus新增订单时，所需商品明细信息
	 * 
	 * @param venusQueryDropdownItemInDTO
	 * @return
	 */
	public ExecuteResult<VenusQueryDropdownItemDetailOutDTO> queryDropdownItemDetail(VenusQueryDropdownItemInDTO venusQueryDropdownItemInDTO);
	
	/**
	 * VENUS首页库存预警饼状图所需的数据
	 * 
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<VenusWarningStockLevelDataOutDTO> getWarningStockLevelProductsData(Long sellerId);
	
	/**
	 * VENUS首页库存预警表格
	 * 
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<List<VenusWarningStockLevelListOutDTO>> getWarningStockLevelProductsInfoList(Long sellerId);
	
	/**
	 * 查询商品spu主信息
	 * 
	 * @param venusItemSpuInDTO
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>> queryItemSpuDataList(VenusItemMainDataInDTO venusItemSpuInDTO,Pager<String> page);
	
	/**
	 * 申请spu为供应商商品
	 * 
	 * @param spuIdList
	 * @param sellerId
	 * @param shopId
	 * @param operatorId
	 * @param operatorName
	 * @return
	 */
	public ExecuteResult<String> applyItemSpu2HtdProduct(List<Long> spuIdList,String sellerId,String shopId,String operatorId,String operatorName);
	
	
}
