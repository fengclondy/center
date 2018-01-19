package cn.htd.goodscenter.service.impl.venus;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.common.constants.VenusErrorCodes;
import cn.htd.goodscenter.dao.*;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.*;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.SpuInfoDTO;
import cn.htd.goodscenter.dto.enums.AuditStatusEnum;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.middleware.outdto.QuerySpecialItemOutDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusStockItemInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;
import cn.htd.goodscenter.dto.venus.po.QuerySkuPublishInfoDetailParamDTO;
import cn.htd.goodscenter.dto.vms.*;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.ItemSpuExportService;
import cn.htd.goodscenter.service.venus.VenusItemExportService;
import cn.htd.goodscenter.service.venus.VmsItemExportService;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import com.google.common.collect.Lists;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * vms2.0 商品中心接口
 *
 * @author chenkang
 * @date 2017-12-06
 */
@Service("vmsItemExportService")
public class VmsItemExportServiceImpl implements VmsItemExportService {

    private final Logger logger = LoggerFactory.getLogger(VmsItemExportServiceImpl.class);

    @Autowired
    private VenusItemExportService venusItemExportService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @Resource
    private ItemMybatisDAO itemMybatisDAO;

    @Resource
    private ItemSpuMapper itemSpuMapper;

    @Resource
    private ItemDraftMapper itemDraftMapper;

    @Resource
    private ItemSkuDAO itemSkuDAO;

    @Resource
    private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;

    @Resource
    private ItemSkuPriceService itemSkuPriceService;

    @Resource
    private DictionaryUtils dictionaryUtils;

    @Resource
    private ItemSpuExportService itemSpuExportService;

    @Autowired
    private RedisDB redisDB;

    @Autowired
    private CategoryAttrDAO categoryAttrDAO;

    @Resource
    private ItemBrandDAO itemBrandDAO;

    @Resource
    private ItemSalesDefaultAreaMapper itemSalesDefaultAreaMapper;

    /**
     * 我的商品 - 商品列表
     *
     * @param queryVmsMyItemListInDTO
     * @param pager
     * @return
     */
    @Override
    public ExecuteResult<DataGrid<QueryVmsMyItemListOutDTO>> queryMyItemList(QueryVmsMyItemListInDTO queryVmsMyItemListInDTO, Pager<String> pager) {
        ExecuteResult<DataGrid<QueryVmsMyItemListOutDTO>> result = new ExecuteResult<>();
        DataGrid<QueryVmsMyItemListOutDTO> dtoDataGrid = new DataGrid<>();
        try {
            // 入参校验
            if (queryVmsMyItemListInDTO == null || pager == null) {
                result.setCode(VenusErrorCodes.E1040009.name());
                result.setResultMessage(VenusErrorCodes.E1040009.getErrorMsg());
                result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040009.getErrorMsg()));
                return result;
            }
            if (queryVmsMyItemListInDTO.getSellerId() == null || queryVmsMyItemListInDTO.getSellerId() <= 0) {
                result.setCode(VenusErrorCodes.E1040010.name());
                result.setResultMessage(VenusErrorCodes.E1040010.getErrorMsg());
                result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040010.getErrorMsg()));
                return result;
            }
            if (StringUtils.isEmpty(queryVmsMyItemListInDTO.getProductCode())) {
                queryVmsMyItemListInDTO.setProductCode(null);
            }
            if (StringUtils.isEmpty(queryVmsMyItemListInDTO.getProductName())) {
                queryVmsMyItemListInDTO.setProductName(null);
            }
            if (StringUtils.isEmpty(queryVmsMyItemListInDTO.getBrandName())) {
                queryVmsMyItemListInDTO.setBrandName(null);
            }
            // 封装三级类目集合
            Long[] thirdCategoryIds = this.itemCategoryService.getAllThirdCategoryByCategoryId(queryVmsMyItemListInDTO.getFirstCategoryId(),
                    queryVmsMyItemListInDTO.getSecCategoryId(), queryVmsMyItemListInDTO.getThirdCategoryId());
            if (thirdCategoryIds != null) {
                queryVmsMyItemListInDTO.setThirdCategoryIdList(Arrays.asList(thirdCategoryIds));
            }
            List<QueryVmsMyItemListOutDTO> queryVmsMyItemListOutDTOS = new ArrayList<>();
            Long count = 0L;
            // 如果查询的待审核和修改后待审核状态，直接查草稿表数据
            if (queryVmsMyItemListInDTO.getAuditStatus() != null && (AuditStatusEnum.AUDIT.getCode() == queryVmsMyItemListInDTO.getAuditStatus() ||
                    AuditStatusEnum.MODIFY_AUDIT.getCode() == queryVmsMyItemListInDTO.getAuditStatus())) {
                count = this.itemDraftMapper.queryVmsDraftItemSkuListCount(queryVmsMyItemListInDTO);
                if (count > 0) {
                    queryVmsMyItemListOutDTOS = this.itemDraftMapper.queryVmsDraftItemSkuList(queryVmsMyItemListInDTO, pager);
                }
            } else { // 否则以商品表数据为准
                count = this.itemMybatisDAO.queryVmsItemSkuListCount(queryVmsMyItemListInDTO);
                if (count > 0) {
                    queryVmsMyItemListOutDTOS = this.itemMybatisDAO.queryVmsItemSkuList(queryVmsMyItemListInDTO, pager);
                }
            }
            // 补充信息
            for (QueryVmsMyItemListOutDTO queryVmsMyItemListOutDTO : queryVmsMyItemListOutDTOS) {
                Integer itemStatus = queryVmsMyItemListOutDTO.getItemStatus(); // 商品表状态
                Integer status = queryVmsMyItemListOutDTO.getStatus(); // 草稿表状态
                // 添加审核状态
                queryVmsMyItemListOutDTO.setAuditStatus(this.getAuditStatus(itemStatus, status));
                // 补充三级类目信息
                ExecuteResult<Map<String, Object>> categoryResult = itemCategoryService.queryItemOneTwoThreeCategoryName(queryVmsMyItemListOutDTO.getCategoryId(), ">");
                if (categoryResult != null && MapUtils.isNotEmpty(categoryResult.getResult())) {
                    String catName = (String) categoryResult.getResult().get("categoryName");
                    queryVmsMyItemListOutDTO.setCategoryName(catName);
                }
            }
            dtoDataGrid.setTotal(count);
            dtoDataGrid.setRows(queryVmsMyItemListOutDTOS);
            result.setCode(ResultCodeEnum.SUCCESS.getCode());
            result.setResult(dtoDataGrid);
        } catch (Exception e) {
            logger.error("我的商品列表查询出错, 错误信息", e);
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMessage());
            result.addErrorMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 我的商品 - 商品详情
     *
     * @param skuId
     * @return
     */
    @Override
    public ExecuteResult<VenusItemSkuDetailOutDTO> queryItemSkuDetail(Long skuId) {
        ExecuteResult<VenusItemSkuDetailOutDTO> executeResult = this.venusItemExportService.queryItemSkuDetail(skuId);
        if (executeResult != null && executeResult.isSuccess()) {
            VenusItemSkuDetailOutDTO venusItemSkuDetailOutDTO = executeResult.getResult();
            venusItemSkuDetailOutDTO.setCategoryAttrHandled(this.parseCategoryAttr(venusItemSkuDetailOutDTO.getAttributes()));
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>> queryItemSpuDataList(VenusItemMainDataInDTO venusItemSpuInDTO, Pager<String> page) {
        ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>> executeResult = this.venusItemExportService.queryItemSpuDataList(venusItemSpuInDTO, page);
        if (executeResult != null && executeResult.isSuccess()) {
            DataGrid<VenusItemSpuDataOutDTO> dataOutDTODataGrid = executeResult.getResult();
            if (dataOutDTODataGrid != null) {
                List<VenusItemSpuDataOutDTO> venusItemSpuDataOutDTOList = dataOutDTODataGrid.getRows();
                for (VenusItemSpuDataOutDTO venusItemSpuDataOutDTO : venusItemSpuDataOutDTOList) {
                    // 设置单位
                    String unitName = dictionaryUtils.getNameByValue(DictionaryConst.TYPE_ITEM_UNIT, venusItemSpuDataOutDTO.getUnit());
                    venusItemSpuDataOutDTO.setUnit(unitName);
                    // 补充三级类目信息
                    ExecuteResult<Map<String, Object>> categoryResult = itemCategoryService.queryItemOneTwoThreeCategoryName(venusItemSpuDataOutDTO.getCategoryId(), ">");
                    if (categoryResult != null && MapUtils.isNotEmpty(categoryResult.getResult())) {
                        String catName = (String) categoryResult.getResult().get("categoryName");
                        venusItemSpuDataOutDTO.setCategoryName(catName);
                    }
                }
            }

        }
        return executeResult;
    }

    @Override
    public ExecuteResult<SpuInfoDTO> queryItemSpuDetail(Long spuId) {
        ExecuteResult<SpuInfoDTO> executeResult = this.itemSpuExportService.getItemSpuBySpuId(spuId);
        if (executeResult != null && ResultCodeEnum.SUCCESS.getCode().equals(executeResult.getCode())) {
            SpuInfoDTO spuInfoDTO = executeResult.getResult();
            // 设置单位
            String unitName = dictionaryUtils.getNameByValue(DictionaryConst.TYPE_ITEM_UNIT, spuInfoDTO.getUnit());
            spuInfoDTO.setUnit(unitName);
            // 补充三级类目信息
            ExecuteResult<Map<String, Object>> categoryResult = itemCategoryService.queryItemOneTwoThreeCategoryName(spuInfoDTO.getCategoryId(), ">");
            if (categoryResult != null && MapUtils.isNotEmpty(categoryResult.getResult())) {
                String catName = (String) categoryResult.getResult().get("categoryName");
                spuInfoDTO.setCategoryName(catName);
            }
            spuInfoDTO.setCategoryAttrHandled(this.parseCategoryAttr(spuInfoDTO.getCategoryAttributes()));
            // 设置品牌
            ItemBrand itemBrand = this.itemBrandDAO.queryById(spuInfoDTO.getBrandId());
            if (itemBrand != null) {
                spuInfoDTO.setBrandName(itemBrand.getBrandName());
            }
        }
        return executeResult;
    }

    /**
     * 我的商品 - 申请商品
     *
     * @param spuIdList
     * @param sellerId
     * @param shopId
     * @param operatorId
     * @param operatorName
     * @return
     */
    @Override
    public ExecuteResult<String> applyItemSpu2HtdProduct(List<Long> spuIdList, String sellerId, String shopId, String operatorId, String operatorName) {
        return this.venusItemExportService.applyItemSpu2HtdProduct(spuIdList, sellerId, shopId, operatorId, operatorName);
    }

    /**
     * 我的商品 - 新增商品
     *
     * @param venusItemDTO
     * @return
     */
    @Override
    public ExecuteResult<String> addItem(VenusItemInDTO venusItemDTO) {
        return this.venusItemExportService.addItem(venusItemDTO);
    }

    /**
     * 我的商品 - 修改商品
     *
     * @param venusItemDTO
     * @return
     */
    @Override
    public ExecuteResult<String> updateItem(VenusItemInDTO venusItemDTO) {
        return this.venusItemExportService.updateItem(venusItemDTO);
    }

    /**
     * 我的商品 - 库存商品
     *
     * @param venusStockItemInDTO
     * @return
     */
    @Override
    public ExecuteResult<String> queryErpStockItemList(VenusStockItemInDTO venusStockItemInDTO) {
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        if (venusStockItemInDTO == null) {
            executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
            executeResult.addErrorMessage("入参对象为空");
            return executeResult;
        }
        if (StringUtils.isEmpty(venusStockItemInDTO.getSupplierCode())) {
            executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
            executeResult.addErrorMessage("中台供应商编号必填");
            return executeResult;
        }
        if (StringUtils.isEmpty(venusStockItemInDTO.getPageCount())) {
            executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
            executeResult.addErrorMessage("pageCount必填");
            return executeResult;
        }
        if (StringUtils.isEmpty(venusStockItemInDTO.getPageIndex())) {
            executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
            executeResult.addErrorMessage("pageIndex必填");
            return executeResult;
        }
        // 商品编码转模板编码
        venusStockItemInDTO.setProductCode(StringUtils.isEmpty(venusStockItemInDTO.getProductCode()) ? null : venusStockItemInDTO.getProductCode().trim());
        venusStockItemInDTO.setProductName(StringUtils.isEmpty(venusStockItemInDTO.getProductName()) ? null : venusStockItemInDTO.getProductName());
        String itemCode = venusStockItemInDTO.getProductCode();
        if (StringUtils.isNotEmpty(itemCode)) {
            Item item = this.itemMybatisDAO.queryItemByItemCode(itemCode);
            if (item != null) {
                ItemSpu itemSpu = this.itemSpuMapper.selectByPrimaryKey(item.getItemSpuId());
                if (itemSpu != null) {
                    venusStockItemInDTO.setProductCode(itemSpu.getSpuCode()); // 模板编码
                    venusStockItemInDTO.setItemCode(itemCode); // 商品编码
                } else {
                    Map map = new HashMap();
                    Map<String,Object> mapData = new HashMap<>();
                    List<QuerySpecialItemOutDTO> newQuerySpecialItemList = new ArrayList<>();
                    mapData.put("storeList", newQuerySpecialItemList);
                    map.put("data",mapData);
                    executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
                    executeResult.setResult(JSONObject.fromObject(map).toString());
                    return executeResult;
                }
            } else {
                Map map = new HashMap();
                Map<String,Object> mapData = new HashMap<>();
                List<QuerySpecialItemOutDTO> newQuerySpecialItemList = new ArrayList<>();
                mapData.put("storeList", newQuerySpecialItemList);
                map.put("data",mapData);
                executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
                executeResult.setResult(JSONObject.fromObject(map).toString());
                return executeResult;
            }
        }
        venusStockItemInDTO.setNewVms(true);
        venusStockItemInDTO.setHasPage("1");
        venusStockItemInDTO.setIsAgreement("0");
        executeResult = this.venusItemExportService.queryVenusStockItemList(venusStockItemInDTO);
        return executeResult;
    }

    /** 包厢商品 大厅商品 **/
    /**
     * 查询包厢商品列表
     * 查询大厅商品列表
     * <p>
     * sort字段：display_quantity ；
     * order ：asc (从小到大) / desc （从大到小）
     *
     * @param queryVmsItemPublishInfoInDTO
     * @param page                         排序字段传在page中
     * @return
     */
    @Override
    public ExecuteResult<DataGrid<QueryVmsItemPublishInfoOutDTO>> queryItemSkuPublishInfoList(QueryVmsItemPublishInfoInDTO queryVmsItemPublishInfoInDTO, Pager<String> page) {
        ExecuteResult<DataGrid<QueryVmsItemPublishInfoOutDTO>> result = new ExecuteResult<>();
        DataGrid<QueryVmsItemPublishInfoOutDTO> dataGrid = new DataGrid<>();
        if (queryVmsItemPublishInfoInDTO == null) {
            result.setCode(VenusErrorCodes.E1040009.name());
            result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040009.getErrorMsg()));
            return result;
        }
        try {
            Integer isBoxFlag = queryVmsItemPublishInfoInDTO.getIsBoxFlag();
            List<QueryVmsItemPublishInfoOutDTO> queryVmsItemPublishInfoOutDTOList = new ArrayList<>();
            // 封装三级类目集合
            Long[] thirdCategoryIds = this.itemCategoryService.getAllThirdCategoryByCategoryId(queryVmsItemPublishInfoInDTO.getFirstCid(),
                    queryVmsItemPublishInfoInDTO.getSecondCid(), queryVmsItemPublishInfoInDTO.getThirdCid());
            if (thirdCategoryIds != null) {
                queryVmsItemPublishInfoInDTO.setThirdCategoryIdList(Arrays.asList(thirdCategoryIds));
            }
            Long totalCount = itemSkuDAO.queryVmsItemSkuPublishInfoListCount(queryVmsItemPublishInfoInDTO);
            if (totalCount > 0) {
                queryVmsItemPublishInfoOutDTOList = itemSkuDAO.queryVmsItemSkuPublishInfoList(queryVmsItemPublishInfoInDTO, page);
                //获取价格
                makeUpPriceInfo4ItemSku(queryVmsItemPublishInfoOutDTOList, isBoxFlag, queryVmsItemPublishInfoInDTO.getSupplierCode());
            }
            dataGrid.setTotal(totalCount);
            dataGrid.setRows(queryVmsItemPublishInfoOutDTOList);
            result.setCode(ResultCodeEnum.SUCCESS.getCode());
            result.setResult(dataGrid);
        } catch (Exception e) {
            logger.error("包厢/大厅商品列表查询出错, 错误信息:", e);
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ErrorCodes.E00001.getErrorMsg());
            result.addErrorMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 包厢商品详情
     * 大厅商品详情
     *
     * @param querySkuPublishInfoDetailParamDTO
     * @return
     */
    @Override
    public ExecuteResult<VenusItemSkuPublishInfoDetailOutDTO> queryItemSkuPublishInfoDetail(QuerySkuPublishInfoDetailParamDTO querySkuPublishInfoDetailParamDTO) {
        querySkuPublishInfoDetailParamDTO.setNewVms(true);
        ExecuteResult<VenusItemSkuPublishInfoDetailOutDTO> executeResult = this.venusItemExportService.queryItemSkuPublishInfoDetail(querySkuPublishInfoDetailParamDTO);
        return executeResult;
    }

    /**
     * 下架商品
     *
     * @param skuCode
     * @param isBoxFlag 是否包厢   0：大厅 ；  1：包厢
     * @return
     */
    @Transactional
    @Override
    public ExecuteResult<String> offShelves(String skuCode, Integer isBoxFlag, Long operateId, String operateName) {
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            // 校验入参
            if (StringUtils.isEmpty(skuCode) || isBoxFlag == null || operateId == null || StringUtils.isEmpty(operateName)) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                executeResult.setResultMessage("inputParam is empty");
                return executeResult;
            }
            // 修改库存表记录；可见库存请0；如果存在锁定库存，可见库存和锁定库存一致
            ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectBySkuCodeAndShelfType(skuCode, isBoxFlag);
            if (itemSkuPublishInfo == null) { // 库存信息不存在
                executeResult.setCode(ResultCodeEnum.STOCK_PUBLISH_INFO_IS_NULL.getCode());
                executeResult.setResultMessage(ResultCodeEnum.STOCK_PUBLISH_INFO_IS_NULL.getMessage());
                return executeResult;
            }
            if (itemSkuPublishInfo.getIsVisable() == 1) { // 如果库存存在，且上架，进行下架操作
                int result = this.itemSkuPublishInfoMapper.updateSkuOffShelf(skuCode, isBoxFlag);
                if (result == 0) {
                    executeResult.setCode(ResultCodeEnum.STOCK_OFF_SHELF_FALI.getCode());
                    executeResult.setResultMessage("下架失败");
                }
            }
            List<ItemSkuPublishInfo> itemSkuPublishInfos = this.itemSkuPublishInfoMapper.queryBySkuId(itemSkuPublishInfo.getSkuId());
            int offShelfCount = 0;// 下架数量
            for (ItemSkuPublishInfo itemSkuPublishInfo1 : itemSkuPublishInfos) { // 遍历sku，包厢和大厅是否都下架
                if (itemSkuPublishInfo1.getIsVisable() == 0) {
                    offShelfCount++;
                }
            }
            if (itemSkuPublishInfos.size() == offShelfCount) { // 如果都下架，更新itemStatus = 4
                this.itemMybatisDAO.updateItemStatusByPk(itemSkuPublishInfo.getItemId(), HtdItemStatusEnum.NOT_SHELVES.getCode(), operateId, operateName);
            }
            //修改商品更新时间
            itemMybatisDAO.updateItemModifyTimeByItemId(itemSkuPublishInfo.getItemId(), 0);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage("下架成功");
        } catch (Exception e) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.addErrorMessage(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<String> onShelves(VenusItemSkuPublishInDTO venusItemSkuPublishInDTO) {
        ExecuteResult<String> result = new ExecuteResult<>();
        if (venusItemSkuPublishInDTO == null) {
            result.setCode(ErrorCodes.E10000.name());
            result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("venusItemSkuPublishInDTO")));
            return result;
        }
        // 上架
        venusItemSkuPublishInDTO.setIsVisible("1");
        venusItemSkuPublishInDTO.setNewVms(true);
        return this.venusItemExportService.txPublishItemSkuInfo(venusItemSkuPublishInDTO);
    }

    @Override
    public ExecuteResult<String> modifyShelves(VenusItemSkuPublishInDTO venusItemSkuPublishInDTO) {
        ExecuteResult<String> result = new ExecuteResult<>();
        if (venusItemSkuPublishInDTO == null) {
            result.setCode(ErrorCodes.E10000.name());
            result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("venusItemSkuPublishInDTO")));
            return result;
        }
        // 只有上架状态的商品才能进入修改接口
        venusItemSkuPublishInDTO.setIsVisible("1");
        venusItemSkuPublishInDTO.setNewVms(true);
        return this.venusItemExportService.txPublishItemSkuInfo(venusItemSkuPublishInDTO);
    }


    @Override
    public ExecuteResult<DefaultSaleAreaDTO> queryDefaultSaleArea(Long sellerId, String defaultAreaCode) {
        ExecuteResult<DefaultSaleAreaDTO> executeResult = new ExecuteResult<>();
        if (sellerId == null || sellerId <= 0) {
            executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
            executeResult.setResultMessage("sellerId不能为空");
            return executeResult;
        }
        if (StringUtils.isEmpty(defaultAreaCode)) {
            executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
            executeResult.setResultMessage("defaultAreaCode不能为空");
            return executeResult;
        }
        try {
            DefaultSaleAreaDTO defaultSaleAreaDTO = new DefaultSaleAreaDTO();
            List<ItemSalesDefaultArea> defaultList = this.itemSalesDefaultAreaMapper.selectDefaultSalesAreaBySellerId(sellerId);
            // 没有设置默认销售区域
            if (defaultList == null || defaultList.size() == 0) { //没有设置过默认销售区域，取大B的注册所在省
                ItemSalesArea itemSaleArea = new ItemSalesArea();
                itemSaleArea.setIsSalesWholeCountry(0); // 全国
                List<ItemSalesAreaDetail> itemSalesAreaDetailList = new ArrayList<>();
                ItemSalesAreaDetail itemSalesAreaDetail = new ItemSalesAreaDetail();
                itemSalesAreaDetail.setAreaCode(defaultAreaCode);
                itemSalesAreaDetail.setSalesAreaType("1");
                itemSalesAreaDetailList.add(itemSalesAreaDetail);
                defaultSaleAreaDTO.setItemSaleArea(itemSaleArea);
                defaultSaleAreaDTO.setItemSaleAreaDetailList(itemSalesAreaDetailList);
                executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
                executeResult.setResult(defaultSaleAreaDTO);
                return executeResult;
            }
            // 如果销售区域是全国 则是1条数，并且是 areaCode : 00
            if (defaultList.size() == 1 && defaultList.get(0).getAreaCode().equals("00")) {
                ItemSalesArea itemSaleArea = new ItemSalesArea();
                itemSaleArea.setIsSalesWholeCountry(1); // 全国
                defaultSaleAreaDTO.setItemSaleArea(itemSaleArea);
                executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
                executeResult.setResult(defaultSaleAreaDTO);
                return executeResult;
            }
            // 非全国
            ItemSalesArea itemSaleArea = new ItemSalesArea();
            List<ItemSalesAreaDetail> itemSalesAreaDetailList = new ArrayList<>();
            itemSaleArea.setIsSalesWholeCountry(0); // 非全国
            for (ItemSalesDefaultArea itemSalesDefaultArea : defaultList) {
                if (StringUtils.isNotEmpty(itemSalesDefaultArea.getAreaCode())) {
                    ItemSalesAreaDetail itemSalesAreaDetail = new ItemSalesAreaDetail();
                    itemSalesAreaDetail.setAreaCode(itemSalesDefaultArea.getAreaCode());
                    itemSalesAreaDetail.setSalesAreaType((itemSalesDefaultArea.getAreaCode().length() / 2) + ""); // 省码2位，市码4位，区位6位；除以2 得到 类型1,2,3
                    itemSalesAreaDetailList.add(itemSalesAreaDetail);
                }
            }
            defaultSaleAreaDTO.setItemSaleArea(itemSaleArea);
            defaultSaleAreaDTO.setItemSaleAreaDetailList(itemSalesAreaDetailList);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResult(defaultSaleAreaDTO);
        } catch (Exception e) {
            logger.error("查询默认销售区域出错, 错误信息：", e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("查询默认销售区域出错");
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<String> setDefaultSaleArea(Long sellerId, DefaultSaleAreaDTO defaultSaleAreaDTO) {
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            if (sellerId == null || sellerId <= 0 || defaultSaleAreaDTO == null) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                executeResult.setResultMessage("必传值为空");
                return executeResult;
            }
            //先做删除
            itemSalesDefaultAreaMapper.deleteBySellerId(sellerId);
            List<ItemSalesDefaultArea> itemSalesDefaultAreaList = new ArrayList<>();
            ItemSalesArea itemSaleArea = defaultSaleAreaDTO.getItemSaleArea();
            List<ItemSalesAreaDetail> itemSalesAreaDetailList = defaultSaleAreaDTO.getItemSaleAreaDetailList();
            if (itemSaleArea == null) {
                if (sellerId == null || sellerId <= 0 || defaultSaleAreaDTO == null) {
                    executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                    executeResult.setResultMessage("itemSaleArea为空");
                    return executeResult;
                }
            }
            if (itemSaleArea.getIsSalesWholeCountry() == 1) {
                ItemSalesDefaultArea itemSalesDefaultArea = new ItemSalesDefaultArea();
                itemSalesDefaultArea.setAreaCode("00");
                itemSalesDefaultArea.setSellerId(sellerId);
                itemSalesDefaultArea.setCreateId(0L);
                itemSalesDefaultArea.setCreateName("system");
                itemSalesDefaultArea.setCreateTime(new Date());
                itemSalesDefaultAreaList.add(itemSalesDefaultArea);
            } else {
                for (ItemSalesAreaDetail itemSalesAreaDetail : itemSalesAreaDetailList) {
                    if (StringUtils.isEmpty(itemSalesAreaDetail.getAreaCode())) {
                        continue;
                    }
                    ItemSalesDefaultArea itemSalesDefaultArea = new ItemSalesDefaultArea();
                    itemSalesDefaultArea.setAreaCode(itemSalesAreaDetail.getAreaCode());
                    itemSalesDefaultArea.setSellerId(sellerId);
                    itemSalesDefaultArea.setCreateId(0L);
                    itemSalesDefaultArea.setCreateName("system");
                    itemSalesDefaultArea.setCreateTime(new Date());
                    itemSalesDefaultAreaList.add(itemSalesDefaultArea);
                }
            }
            for(ItemSalesDefaultArea itemSalesDefaultArea : itemSalesDefaultAreaList){
                itemSalesDefaultAreaMapper.insertSelective(itemSalesDefaultArea);
            }
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("设置默认销售区域出错,", e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 根据商品表状态和草稿表状态，计算出给前台的审核状态
     *
     * @param itemStatus
     * @param status
     * @param erpCode    // TODO : 魔法数字替换
     * @return
     */
    private Integer getAuditStatus(Integer itemStatus, Integer status) {
        // 待审核
        if (itemStatus == 0 && status == 0) {
            return AuditStatusEnum.AUDIT.getCode();
        }
        // 审核通过
        if ((itemStatus == 1 || itemStatus == 4 || itemStatus == 5) && status == 1) {
            return AuditStatusEnum.PASS.getCode();
        }
        // 新增审核驳回
        if (itemStatus == 2 && status == 2) {
            return AuditStatusEnum.REJECTED.getCode();
        }
        // 修改待审核
        if ((itemStatus == 1 || itemStatus == 4 || itemStatus == 5) && status == 0 ) {
            return AuditStatusEnum.MODIFY_AUDIT.getCode();
        }
        // 修改审核驳回
        if ((itemStatus == 1 || itemStatus == 4 || itemStatus == 5) && status == 2) {
            return AuditStatusEnum.MODIFY_REJECTED.getCode();
        }
        return null;
    }

    private void makeUpPriceInfo4ItemSku(List<QueryVmsItemPublishInfoOutDTO> queryVmsItemPublishInfoOutDTOList, Integer isBoxFlag, String supplierCode) {
        if (CollectionUtils.isEmpty(queryVmsItemPublishInfoOutDTOList)) {
            return;
        }
        List<Long> skuIdList = Lists.newArrayList();
        for (QueryVmsItemPublishInfoOutDTO venusItemSkuPublishInfoOutDTO : queryVmsItemPublishInfoOutDTOList) {
            if (venusItemSkuPublishInfoOutDTO.getSkuId() != null) {
                skuIdList.add(venusItemSkuPublishInfoOutDTO.getSkuId());
            }
        }
        ExecuteResult<List<ItemSkuBasePrice>> basePriceList = itemSkuPriceService.batchQueryItemSkuBasePrice(skuIdList);
        if (basePriceList == null || CollectionUtils.isEmpty(basePriceList.getResult())) {
            return;
        }
        for (QueryVmsItemPublishInfoOutDTO venusItemSkuPublishInfoOutDTO : queryVmsItemPublishInfoOutDTOList) {
            venusItemSkuPublishInfoOutDTO.setIsBoxFlag(isBoxFlag);
            for (ItemSkuBasePrice price : basePriceList.getResult()) {
                if (price.getSkuId().equals(venusItemSkuPublishInfoOutDTO.getSkuId())) {
                    //分销限价
                    String saleLimitedPrice = MiddlewareInterfaceUtil.findItemFloorPrice(supplierCode, venusItemSkuPublishInfoOutDTO.getSpuCode());
                    if (saleLimitedPrice != null) {
                        venusItemSkuPublishInfoOutDTO.setSaleLimitedPrice(String.valueOf(this.wrapDecimal(new BigDecimal(saleLimitedPrice), 2)));
                    }
                    //包厢价格
                    if (null != price.getBoxSalePrice() &&  1 == venusItemSkuPublishInfoOutDTO.getIsBoxFlag()) {
                        venusItemSkuPublishInfoOutDTO.setSalePrice(String.valueOf(this.wrapDecimal(price.getBoxSalePrice(), 2)));
                    }
                    //大厅价格
                    if (null != price.getAreaSalePrice() && 0 == venusItemSkuPublishInfoOutDTO.getIsBoxFlag()) {
                        venusItemSkuPublishInfoOutDTO.setSalePrice(String.valueOf(this.wrapDecimal(price.getAreaSalePrice(), 2)));
                    }
                    //零售价
                    if (null != price.getRetailPrice()) {
                        venusItemSkuPublishInfoOutDTO.setRetailPrice(String.valueOf(this.wrapDecimal(price.getRetailPrice(), 2)));
                    }
                    break;
                }
            }
            // 设置类目名称
            // 补充三级类目信息
            ExecuteResult<Map<String, Object>> categoryResult = itemCategoryService.queryItemOneTwoThreeCategoryName(venusItemSkuPublishInfoOutDTO.getCategoryId(), ">");
            if (categoryResult != null && MapUtils.isNotEmpty(categoryResult.getResult())) {
                String catName = (String) categoryResult.getResult().get("categoryName");
                venusItemSkuPublishInfoOutDTO.setCategoryName(catName);
            }
        }
    }


    private Map<String, String[]> parseCategoryAttr(String categoryAttr) {
        Map<String, String[]> paresMapResult = new HashMap<>();
        if (StringUtils.isEmpty(categoryAttr)) {
            return paresMapResult;
        }
        try {
            Map<String, JSONArray> map = (Map<String, JSONArray>) JSONObject.fromObject(categoryAttr);
            for (Map.Entry<String, JSONArray> entry : map.entrySet()) {
                String attrCode = entry.getKey();
                String attrName = this.getAttributeName(Long.valueOf(attrCode));
                JSONArray attrValueCodeArray = entry.getValue();
                String[] array = new String[attrValueCodeArray.size()];
                if (attrValueCodeArray != null) {
                    for (int i = 0; i < attrValueCodeArray.size(); i++) {
                        Integer attrValueCode = Integer.valueOf(attrValueCodeArray.get(i) + "");
                        array[i] = this.getAttributeValueName(attrValueCode);
                    }
                }
                paresMapResult.put(attrName, array);
            }
        } catch (Exception e) {
            logger.error("parseCategoryAttr出错，", e);
        }
        return paresMapResult;
    }

    private String getAttributeName(Long attributeId) {
        if (attributeId != null) {
            String attributeName = this.redisDB.get(Constants.REDIS_KEY_PREFIX_ATTRIBUTE + attributeId);
            if (org.apache.commons.lang3.StringUtils.isEmpty(attributeName)) {
                return this.categoryAttrDAO.getAttrNameByAttrId(attributeId);
            } else {
                return attributeName;
            }
        } else {
            return null;
        }
    }

    private String getAttributeValueName(Integer attributeValueId) {
        if (attributeValueId != null) {
            String attributeValueName = this.redisDB.get(Constants.REDIS_KEY_PREFIX_ATTRIBUTE_VALUE + attributeValueId);
            if (org.apache.commons.lang3.StringUtils.isEmpty(attributeValueName)) {
                return this.categoryAttrDAO.getAttrValueNameByAttrValueId(Long.valueOf(attributeValueId));
            } else {
                return attributeValueName;
            }
        } else {
            return null;
        }
    }


    /**
     * 设置小数位数 （默认四舍五入）
     * @param origin
     * @return
     */
    private static BigDecimal wrapDecimal(BigDecimal origin, int newScale) {
        if (origin == null) {
            return origin;
        } else {
            return origin.setScale(newScale, BigDecimal.ROUND_HALF_UP);
        }
    }
}
