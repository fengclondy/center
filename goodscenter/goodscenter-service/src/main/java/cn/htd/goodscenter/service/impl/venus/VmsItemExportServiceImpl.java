package cn.htd.goodscenter.service.impl.venus;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.common.util.MessageIdUtils;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.common.constants.VenusErrorCodes;
import cn.htd.goodscenter.common.utils.DTOValidateUtil;
import cn.htd.goodscenter.common.utils.ValidateResult;
import cn.htd.goodscenter.dao.*;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.*;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.SpuInfoDTO;
import cn.htd.goodscenter.dto.enums.AuditStatusEnum;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ProductChannelEnum;
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
import cn.htd.goodscenter.service.ItemExportService;
import cn.htd.goodscenter.service.ItemSpuExportService;
import cn.htd.goodscenter.service.venus.VenusItemExportService;
import cn.htd.goodscenter.service.venus.VmsItemExportService;
import cn.htd.marketcenter.service.TimelimitedInfoService;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.dto.StandardPriceDTO;
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

    private static final Logger logger = LoggerFactory.getLogger(VmsItemExportServiceImpl.class);

    @Autowired
    private VenusItemExportService venusItemExportService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @Resource
    private ItemMybatisDAO itemMybatisDAO;

    @Resource
    private ItemCategoryDAO itemCategoryDAO;

    @Resource
    private ItemBrandDAO itemBrandDAO;

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
    private ItemExportService itemExportService;

    @Resource
    private TimelimitedInfoService timelimitedInfoService;

    @Resource
    private ItemSpuExportService itemSpuExportService;

    @Autowired
    private RedisDB redisDB;

    @Autowired
    private CategoryAttrDAO categoryAttrDAO;

    @Resource
    private ItemSalesDefaultAreaMapper itemSalesDefaultAreaMapper;

    @Resource
    private ItemSalesAreaMapper itemSalesAreaMapper;

    @Resource
    private ItemSalesAreaDetailMapper itemSalesAreaDetailMapper;

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
                String erpCode = queryVmsMyItemListOutDTO.getErpCode(); // erpCpde
                // 添加审核状态
                queryVmsMyItemListOutDTO.setAuditStatus(this.getAuditStatus(itemStatus, status, erpCode));
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
        return this.venusItemExportService.queryItemSkuDetail(skuId);
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
     * 我的商品 - 批量新增商品 （导入）
     *
     * @param batchAddItemInDTOList
     * @return
     */
    @Override
    public ExecuteResult<BatchAddItemOutDTO> batchAddItem(List<BatchAddItemInDTO> batchAddItemInDTOList) {
        ExecuteResult<BatchAddItemOutDTO> executeResult = new ExecuteResult<>();
        //前置校验
        if (batchAddItemInDTOList == null) {
            executeResult.setCode(ErrorCodes.E10005.name());
            executeResult.setErrorMessages(Lists.newArrayList(ErrorCodes.E10005.getErrorMsg()));
            return executeResult;
        }
        try {
            // 失败的集合
            List<BatchAddItemErrorListOutDTO> errorList = new ArrayList<>();
            // 准备导入的商品集合
            List<BatchAddItemInDTO> preImportItemList = new ArrayList<>();
            // 统计商品名称的重复数量
            Map<String, Integer> sameProCountMap = calculateSameProductNameCount(batchAddItemInDTOList);
            // 业务校验
            for (BatchAddItemInDTO batchAddItemInDTO : batchAddItemInDTOList) {
                // 失败DTO
                BatchAddItemErrorListOutDTO batchAddItemErrorListOutDTO = new BatchAddItemErrorListOutDTO();
                this.copyBatchAddItemInDTO2BatchAddItemErrorListOutDTO(batchAddItemInDTO, batchAddItemErrorListOutDTO);
                // 待导入的ITEN
                BatchAddItemInDTO preImportItem = new BatchAddItemInDTO();
                // 校验类目
                String categoryName = batchAddItemInDTO.getCategoryName();
                ExecuteResult<Long> categoryResult = validateCategoryName(categoryName);
                if (!ResultCodeEnum.SUCCESS.equals(categoryResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(categoryResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                Long cid = categoryResult.getResult();
                // 校验品牌
                String brandName = batchAddItemInDTO.getBrandName();
                ExecuteResult<Long> brandResult = validateBrandName(brandName);
                if (!ResultCodeEnum.SUCCESS.equals(brandResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(brandResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                Long brandId = brandResult.getResult();
                // 校验型号
                String modelType = batchAddItemInDTO.getModelType();
                ExecuteResult<String> modelTypeResult = validateModelType(modelType);
                if (!ResultCodeEnum.SUCCESS.equals(modelTypeResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(modelTypeResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                // 校验单位
                String unit = batchAddItemInDTO.getUnit();
                ExecuteResult<String> unitResult = validateUnit(unit);
                if (!ResultCodeEnum.SUCCESS.equals(unitResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(unitResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                String unitCode = unitResult.getResult();
                // 校验税率
                String taxRate = batchAddItemInDTO.getTaxRate();
                ExecuteResult<String> rateResult = validateTaxRate(taxRate);
                if (!ResultCodeEnum.SUCCESS.equals(rateResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(rateResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                // 校验名称
                String itemName = batchAddItemInDTO.getProductName();
                ExecuteResult<String> itemResult = validateProductName(itemName);
                if (!ResultCodeEnum.SUCCESS.equals(itemResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(itemResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                // 校验是否有重复的
                int sameProCount = sameProCountMap.get(itemName.trim());
                if (sameProCount > 1) {
                    batchAddItemErrorListOutDTO.setErroMsg("存在" + sameProCount + "相同名称的商品");
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                // 封装ITEM
                // 补充三级类目信息
                ExecuteResult<Map<String, Object>> categoryResult1 = itemCategoryService.queryItemOneTwoThreeCategoryName(cid, ">");
                if (categoryResult1 != null && MapUtils.isNotEmpty(categoryResult1.getResult())) {
                    preImportItem.setFirstCid((Long) categoryResult1.getResult().get("firstCategoryId"));
                    preImportItem.setSecondCid((Long) categoryResult1.getResult().get("secondCategoryId"));
                }
                preImportItem.setBrandId(brandId);
                preImportItem.setThirdCid(cid);
                preImportItem.setUnitCode(unitCode);
                preImportItemList.add(preImportItem);
            }
            // 开始导入
            for (BatchAddItemInDTO preImportItem : preImportItemList) {
                VenusItemInDTO venusItemDTO = new VenusItemInDTO();
                venusItemDTO.setProductName(preImportItem.getProductName());
                venusItemDTO.setFirstLevelCategoryId(preImportItem.getFirstCid());
                venusItemDTO.setSecondLevelCategoryId(preImportItem.getSecondCid());
                venusItemDTO.setThirdLevelCategoryId(preImportItem.getThirdCid());
                venusItemDTO.setBrandId(preImportItem.getBrandId());
                venusItemDTO.setUnit(preImportItem.getUnitCode());
                venusItemDTO.setSerial(preImportItem.getModelType());
                venusItemDTO.setTaxRate(preImportItem.getTaxRate());
                venusItemDTO.setHtdVendorId(preImportItem.getSellerId());
                venusItemDTO.setShopId(preImportItem.getShopId());
                venusItemDTO.setShopCid(preImportItem.getShopCid());
                venusItemDTO.setOperatorId(preImportItem.getOperatorId());
                venusItemDTO.setOperatorName(preImportItem.getOperatorName());
                ExecuteResult<String> addResult = this.addItem(venusItemDTO);
                if (!ResultCodeEnum.SUCCESS.getCode().equals(addResult.getCode())) {
                    BatchAddItemErrorListOutDTO batchAddItemErrorListOutDTO = new BatchAddItemErrorListOutDTO();
                    batchAddItemErrorListOutDTO.setProductName(preImportItem.getProductName());
                    batchAddItemErrorListOutDTO.setCategoryName(preImportItem.getCategoryName());
                    batchAddItemErrorListOutDTO.setBrandName(preImportItem.getBrandName());
                    batchAddItemErrorListOutDTO.setModelType(preImportItem.getModelType());
                    batchAddItemErrorListOutDTO.setTaxRate(preImportItem.getTaxRate());
                    batchAddItemErrorListOutDTO.setUnit(preImportItem.getUnit());
                    batchAddItemErrorListOutDTO.setErroMsg(addResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                }
            }
            // 结果
            BatchAddItemOutDTO batchAddItemOutDTO = new BatchAddItemOutDTO();
            batchAddItemOutDTO.setTotalCount(batchAddItemInDTOList.size());
            batchAddItemOutDTO.setSuccessCount(batchAddItemInDTOList.size() - errorList.size());
            batchAddItemOutDTO.setFailureCount(errorList.size());
            batchAddItemOutDTO.setBatchAddItemListOutDTOErrorList(errorList);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResult(batchAddItemOutDTO);
        } catch (Exception e) {
            logger.error("我的商品列表查询出错, 错误信息", e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
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
        String itemCode = venusStockItemInDTO.getProductCode();
        if (StringUtils.isNotEmpty(itemCode)) {
            Item item = this.itemMybatisDAO.queryItemByItemCode(itemCode);
            ItemSpu itemSpu = this.itemSpuMapper.selectByPrimaryKey(item.getItemSpuId());
            venusStockItemInDTO.setProductCode(itemSpu.getSpuCode()); // 模板编码
            venusStockItemInDTO.setItemCode(itemCode); // 商品编码
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
                makeUpPriceInfo4ItemSku(queryVmsItemPublishInfoOutDTOList, isBoxFlag);
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
    public ExecuteResult<DataGrid<QueryOffShelfItemOutDTO>> queryOffShelfItemBySellerId(QueryOffShelfItemInDTO queryOffShelfItemInDTO, Pager pager) {
        ExecuteResult<DataGrid<QueryOffShelfItemOutDTO>> executeResult = new ExecuteResult<>();
        DataGrid<QueryOffShelfItemOutDTO> dtoDataGrid = new DataGrid<>();
        try {
            // 参数校验
            if (queryOffShelfItemInDTO == null) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                return executeResult;
            }
            ValidateResult validateResult = DTOValidateUtil.validate(queryOffShelfItemInDTO);
            if (!validateResult.isPass()) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                executeResult.setResultMessage(validateResult.getMessage());
                return executeResult;
            }
            // 批量同步该大B下所有未上架商品的库存
            String supplierCode = queryOffShelfItemInDTO.getSupplyCode();
            Long sellerId = queryOffShelfItemInDTO.getSellerId();
            Integer isBoxFlag = queryOffShelfItemInDTO.getIsBoxFlag();
            this.batchSyncStockBySellerId(supplierCode, sellerId, isBoxFlag);
            // 封装三级类目集合
            Long[] thirdCategoryIds = this.itemCategoryService.getAllThirdCategoryByCategoryId(queryOffShelfItemInDTO.getFirstCategoryId(),
                    queryOffShelfItemInDTO.getSecondCategoryId(), queryOffShelfItemInDTO.getThirdCategoryId());
            if (thirdCategoryIds != null) {
                queryOffShelfItemInDTO.setThirdCategoryIdList(Arrays.asList(thirdCategoryIds));
            }
            Long count = this.itemSkuDAO.queryVmsOffShelfItemSkuPublishInfoListCount(queryOffShelfItemInDTO);
            List<QueryOffShelfItemOutDTO> queryOffShelfItemOutDTOList = new ArrayList<>();
            if (count > 0) {
                queryOffShelfItemOutDTOList = this.itemSkuDAO.queryVmsOffShelfItemSkuPublishInfoList(queryOffShelfItemInDTO, pager);
                List<Long> skuIdList = new ArrayList<>();
                // 获取最大可上架数量和最小上架数量
                for (QueryOffShelfItemOutDTO queryOffShelfItemOutDTO : queryOffShelfItemOutDTOList) {
                    this.setMinAndMaxStock(queryOffShelfItemOutDTO);
                    // ERP价格
                    String spuCode = queryOffShelfItemOutDTO.getSpuCode();
                    BigDecimal saleLimitPrice = new BigDecimal("3000"); // TODO :
                    BigDecimal wsaleUtprice =  new BigDecimal("3100"); // TODO :
                    if (StringUtils.isNotEmpty(spuCode)) {
                        Map priceMap = MiddlewareInterfaceUtil.findItemERPPrice(supplierCode, spuCode);
                        if (MapUtils.isNotEmpty(priceMap)) {
                            saleLimitPrice = priceMap.get("floorPrice") != null ? new BigDecimal((String) priceMap.get("floorPrice")) : null;
                            wsaleUtprice = priceMap.get("wsaleUtprice") != null ? new BigDecimal((String) priceMap.get("wsaleUtprice")) : null;
                        }
                    }
                    queryOffShelfItemOutDTO.setSaleLimitedPrice(saleLimitPrice);
                    queryOffShelfItemOutDTO.setWsaleUtprice(wsaleUtprice);
                    skuIdList.add(queryOffShelfItemOutDTO.getSkuId());
                }
                ExecuteResult<List<ItemSkuBasePrice>> basePriceList = itemSkuPriceService.batchQueryItemSkuBasePrice(skuIdList);
                if (basePriceList != null && !CollectionUtils.isEmpty(basePriceList.getResult())) {
                    List<ItemSkuBasePrice> itemSkuBasePriceList = basePriceList.getResult();
                    for (QueryOffShelfItemOutDTO queryOffShelfItemOutDTO : queryOffShelfItemOutDTOList) {
                        for (ItemSkuBasePrice itemSkuBasePrice : itemSkuBasePriceList) {
                            if (queryOffShelfItemOutDTO.getSkuId().equals(itemSkuBasePrice.getSkuId())) {
                                queryOffShelfItemOutDTO.setSalePrice(queryOffShelfItemInDTO.getIsBoxFlag() == 0 ? itemSkuBasePrice.getAreaSalePrice() : itemSkuBasePrice.getBoxSalePrice());
                                queryOffShelfItemOutDTO.setRetailPrice(itemSkuBasePrice.getRetailPrice());
                            }
                        }
                    }
                }
            }
            dtoDataGrid.setTotal(count);
            dtoDataGrid.setRows(queryOffShelfItemOutDTOList);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResult(dtoDataGrid);
        } catch (Exception e) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.addErrorMessage(e.getMessage());
            logger.error("查询供应商所有下架数据, 错误信息：", e);
        }
        return executeResult;
    }

    private void setMinAndMaxStock(QueryOffShelfItemOutDTO queryOffShelfItemOutDTO) {
        // X最大可上架库存 =  实际库存 - y上架的库存或者y下架的锁定库存 - 促销维护的库存
        int totalStock = queryOffShelfItemOutDTO.getTotalStock();
        // 在其他地方占用的库存（如果其上架则取display库存，如果其下架则取锁定库存）
        int otherStock = queryOffShelfItemOutDTO.getOtherIsVisable() == 1 ? queryOffShelfItemOutDTO.getOtherDisplayQuantity() : queryOffShelfItemOutDTO.getOtherReserveQuantity();
        int promotionStock = this.getPromotionStock(queryOffShelfItemOutDTO.getSkuCode());
        int aviableStock = (totalStock - otherStock - promotionStock) < 0 ? 0 : (totalStock - otherStock - promotionStock);
        // X最小可上架库存 = X的锁定库存，没有则为0
        int minStock = queryOffShelfItemOutDTO.getCurrentIsVisable() == 0 ? queryOffShelfItemOutDTO.getCurrentReserveQuantity() : 0;
        queryOffShelfItemOutDTO.setAviableStock(aviableStock);
        queryOffShelfItemOutDTO.setMinStock(minStock);
        queryOffShelfItemOutDTO.setPromtionStock(promotionStock);
    }

    private void batchSyncStockBySellerId(String supplierCode, Long sellerId, Integer isBoxFlag) {
        // 同步大B下面所有ERP实际库存
        // 1. 查询所有该大B下包厢或者大厅的下架商品
        List<Map<String, Object>> allOffItemList = this.itemSkuDAO.queryALLOffShelfItemList(sellerId, isBoxFlag);
        List<String> spuCodeList = new ArrayList<>();
        if (allOffItemList != null) {
            for (Map offItemMap : allOffItemList) {
                String spuCode = (String) offItemMap.get("spucode");
                if (StringUtils.isEmpty(spuCode)) {
                    continue;
                }
                spuCodeList.add(spuCode);
            }
        }
        // 2. 同步实际库存
        if (spuCodeList.size() > 0) {
            this.itemExportService.batchsyncItemStock(spuCodeList, supplierCode, sellerId);
        }
    }

    @Transactional
    @Override
    public ExecuteResult<BatchOnShelfOutDTO> batchOnShelves(BatchOnShelfInDTO batchOnShelfInDTO) {
        ExecuteResult<BatchOnShelfOutDTO> executeResult = new ExecuteResult<>();
        try {
            // input
            String shelfType = batchOnShelfInDTO.getIsBoxFlag() == 1 ? "1" : "2";
            Integer isBoxFlag = batchOnShelfInDTO.getIsBoxFlag();
            Long sellerId = batchOnShelfInDTO.getSellerId();
            String supplierCode = batchOnShelfInDTO.getSupplierCode();
            BigDecimal ratio = batchOnShelfInDTO.getRatio();
            Integer batchOnShelfType = batchOnShelfInDTO.getBatchOnShelfType(); // 1:默认价格 2:自定义价格 3:自定义涨幅
            Integer hasBelowLimitPriceAuth = batchOnShelfInDTO.getHasBelowLimitPriceAuth(); // 是否有低于分销限价的权限
            String areCode = batchOnShelfInDTO.getDefaultAreaCode();
            ValidateResult validateResult = DTOValidateUtil.validate(batchOnShelfInDTO);
            if (!validateResult.isPass()) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
                executeResult.setResultMessage(validateResult.getMessage());
                return executeResult;
            }
            // 校验比率
            if (batchOnShelfType == 3 && (ratio == null || ratio.compareTo(BigDecimal.ZERO) <= 0)) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
                executeResult.setResultMessage("自定义涨幅批量上架,比率必须是正整数");
                return executeResult;
            }
            List<BatchOnShelfItemInDTO> dataList = batchOnShelfInDTO.getDataList();
            // 同步实际库存
            // 批量同步该大B下所有未上架商品的库存
            this.batchSyncStockBySellerId(supplierCode, sellerId, isBoxFlag);
            // 获取可上架的最小库存和最大库存
            List<Long> itemIdList = new ArrayList<>();
            for (BatchOnShelfItemInDTO batchOnShelfItemInDTO : dataList) {
                itemIdList.add(batchOnShelfItemInDTO.getItemId());
            }
            QueryOffShelfItemInDTO queryOffShelfItemInDTO = new QueryOffShelfItemInDTO();
            queryOffShelfItemInDTO.setSellerId(sellerId);
            queryOffShelfItemInDTO.setIsBoxFlag(isBoxFlag);
            queryOffShelfItemInDTO.setItemIdList(itemIdList);
            Map<Long, QueryOffShelfItemOutDTO> stockDataMap = new HashMap<>();
            List<QueryOffShelfItemOutDTO> queryOffShelfItemOutDTOList = this.itemSkuDAO.queryVmsOffShelfItemSkuPublishInfoList(queryOffShelfItemInDTO, null);
            for (QueryOffShelfItemOutDTO queryOffShelfItemOutDTO : queryOffShelfItemOutDTOList) {
                this.setMinAndMaxStock(queryOffShelfItemOutDTO);
                stockDataMap.put(queryOffShelfItemOutDTO.getItemId(), queryOffShelfItemOutDTO);
            }
            // output
            BatchOnShelfOutDTO batchOnShelfOutDTO = new BatchOnShelfOutDTO();
            List<BatchOnShelfItemOutDTO> failureList = new ArrayList<>();
            for (BatchOnShelfItemInDTO batchOnShelfItemInDTO : dataList) {
                // 开始上架
                Long itemId = batchOnShelfItemInDTO.getItemId();
                String itemName = batchOnShelfItemInDTO.getItemName();
                String itemCode = batchOnShelfItemInDTO.getItemCode();
                Long skuId = batchOnShelfItemInDTO.getSkuId();
                String skuCode = batchOnShelfItemInDTO.getSkuCode();
                BigDecimal saleLimitPrice = batchOnShelfItemInDTO.getSaleLimitedPrice();
                BigDecimal wsaleUtprice = batchOnShelfItemInDTO.getWsaleUtprice(); // erp零售价
                ValidateResult validateResult1 = DTOValidateUtil.validate(batchOnShelfItemInDTO);
                if (!validateResult1.isPass()) {
                    this.addFailureList(failureList, itemName, itemCode, validateResult1.getMessage());
                    continue;
                }
                Item item = itemMybatisDAO.queryItemByPk(itemId);
                // 校验商品状态
                if (item == null || Integer.valueOf(HtdItemStatusEnum.AUDITING.getCode()).equals(item.getItemStatus())
                        || Integer.valueOf(HtdItemStatusEnum.REJECTED.getCode()).equals(item.getItemStatus())
                        || Integer.valueOf(HtdItemStatusEnum.ERP_STOCKPRICE_OR_OUTPRODUCTPRICE.getCode()).equals(item.getItemStatus())
                        || Integer.valueOf(HtdItemStatusEnum.DELETED.getCode()).equals(item.getItemStatus())
                        ) {
                    String errorMsg = "商品状态不符合上架条件";
                    this.addFailureList(failureList, itemName, itemCode, errorMsg);
                    continue;
                }
                // 校验库存
                Integer OnShelfQuanty = batchOnShelfItemInDTO.getOnShelfQuantiy(); // 上架数量
                if (stockDataMap.isEmpty()) {
                    String errorMsg = "查询不到可上架的库存";
                    this.addFailureList(failureList, itemName, itemCode, errorMsg);
                    continue;
                }
                QueryOffShelfItemOutDTO queryOffShelfItemOutDTO = stockDataMap.get(itemId);
                if (queryOffShelfItemOutDTO == null) {
                    String errorMsg = "查询不到可上架的库存";
                    this.addFailureList(failureList, itemName, itemCode, errorMsg);
                    continue;
                }
                Integer minQuanty = queryOffShelfItemOutDTO.getMinStock();
                Integer maxQuanty = queryOffShelfItemOutDTO.getAviableStock();
                Date date = new Date();
                // 根据上架类型设置价格
                BigDecimal retailPrice = null;
                BigDecimal salePrice = null;
                if (batchOnShelfType == 1) { // 默认价格
                    retailPrice = wsaleUtprice;
                    salePrice = saleLimitPrice;
                    OnShelfQuanty = maxQuanty;
                    if (OnShelfQuanty == null || OnShelfQuanty <= 0) {
                        String errorMsg = "默认价格,上架库存必须大于0";
                        this.addFailureList(failureList, itemName, itemCode, errorMsg);
                        continue;
                    }
                } else if (batchOnShelfType == 2) { // 自定义价格
                    if (batchOnShelfItemInDTO.getRetailPrice() == null || batchOnShelfItemInDTO.getRetailPrice().compareTo(BigDecimal.ZERO) <= 0) {
                        String errorMsg = "自定义价格,零售价为空或者不是正数";
                        this.addFailureList(failureList, itemName, itemCode, errorMsg);
                        continue;
                    }
                    if (batchOnShelfItemInDTO.getSalePrice() == null || batchOnShelfItemInDTO.getSalePrice().compareTo(BigDecimal.ZERO) <= 0) {
                        String errorMsg = "自定义价格,销售价为空或者不是正数";
                        this.addFailureList(failureList, itemName, itemCode, errorMsg);
                        continue;
                    }
                    if (batchOnShelfItemInDTO.getRetailPrice().compareTo(batchOnShelfItemInDTO.getSalePrice()) < 0) {
                        String errorMsg = "自定义价格,销售价不能大于零售价";
                        this.addFailureList(failureList, itemName, itemCode, errorMsg);
                        continue;
                    }
                    // 和分销限价比
                    if (hasBelowLimitPriceAuth == 0 && batchOnShelfItemInDTO.getRetailPrice().compareTo(saleLimitPrice) < 0) {
                        String errorMsg = "自定义价格,没有低于分销限价的权限,零售价不能低于分销限价";
                        this.addFailureList(failureList, itemName, itemCode, errorMsg);
                        continue;
                    }
                    if (hasBelowLimitPriceAuth == 0 && batchOnShelfItemInDTO.getSalePrice().compareTo(saleLimitPrice) < 0) {
                        String errorMsg = "自定义价格,没有低于分销限价的权限,销售价不能低于分销限价";
                        this.addFailureList(failureList, itemName, itemCode, errorMsg);
                        continue;
                    }
                    if (OnShelfQuanty == null || OnShelfQuanty <= 0) {
                        String errorMsg = "自定义价格, 可上架库存不能为空";
                        this.addFailureList(failureList, itemName, itemCode, errorMsg);
                        continue;
                    }
                    if (OnShelfQuanty < minQuanty || OnShelfQuanty > maxQuanty) {
                        String errorMsg = "可上架商品数量须在" + minQuanty + "到" + maxQuanty + "之间";
                        this.addFailureList(failureList, itemName, itemCode, errorMsg);
                        continue;
                    }
                    retailPrice = batchOnShelfItemInDTO.getRetailPrice();
                    salePrice = batchOnShelfItemInDTO.getSalePrice();
                } else if (batchOnShelfType == 3) { // 自定义涨幅
                    retailPrice = saleLimitPrice.multiply(ratio).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP).add(saleLimitPrice);
                    salePrice = retailPrice;
                    OnShelfQuanty = maxQuanty;
                    if (OnShelfQuanty == null || OnShelfQuanty <= 0) {
                        String errorMsg = "自定义涨幅,上架库存必须大于0";
                        this.addFailureList(failureList, itemName, itemCode, errorMsg);
                        continue;
                    }
                }
                // 处理库存
                ItemSkuPublishInfo itemSkuPublishInfoFromDb = itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, shelfType, "0");
                if (itemSkuPublishInfoFromDb == null) { // 新增
                    ItemSkuPublishInfo itemSkuPublishInfo = new ItemSkuPublishInfo();
                    itemSkuPublishInfo.setSkuId(skuId);
                    itemSkuPublishInfo.setItemId(itemId);
                    itemSkuPublishInfo.setSkuCode(skuCode);
                    itemSkuPublishInfo.setIsBoxFlag(isBoxFlag);
                    itemSkuPublishInfo.setDisplayQuantity(OnShelfQuanty);
                    itemSkuPublishInfo.setReserveQuantity(0);
                    itemSkuPublishInfo.setMimQuantity(1); // 默认起订量为1
                    itemSkuPublishInfo.setIsPurchaseLimit(0); // 默认不限购
                    itemSkuPublishInfo.setIsVisable(1); // 上架
                    itemSkuPublishInfo.setVisableTime(date);
                    itemSkuPublishInfo.setIsAutomaticVisable(0);
                    itemSkuPublishInfo.setCreateId(0L);
                    itemSkuPublishInfo.setCreateTime(date);
                    itemSkuPublishInfo.setCreateName("system");
                    itemSkuPublishInfoMapper.insertSelective(itemSkuPublishInfo);
                } else { // 更新
                    itemSkuPublishInfoFromDb.setDisplayQuantity(OnShelfQuanty);
                    itemSkuPublishInfoFromDb.setMimQuantity(1); // 默认起订量为1
                    itemSkuPublishInfoFromDb.setIsPurchaseLimit(0); // 默认不限购
                    itemSkuPublishInfoFromDb.setIsVisable(1); // 上架
                    itemSkuPublishInfoFromDb.setVisableTime(date);
                    itemSkuPublishInfoFromDb.setIsAutomaticVisable(0);
                    itemSkuPublishInfoFromDb.setModifyId(0L);
                    itemSkuPublishInfoFromDb.setModifyTime(date);
                    itemSkuPublishInfoFromDb.setModifyName("system");
                    itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfoFromDb);
                }
                // 处理价格
                ExecuteResult<StandardPriceDTO> priceResult = this.itemSkuPriceService.queryStandardPrice4InnerSeller(skuId, isBoxFlag);
                StandardPriceDTO standardPriceDTO = new StandardPriceDTO();
                if (priceResult != null && priceResult.isSuccess()) {
                    standardPriceDTO = priceResult.getResult();
                }
                ItemSkuBasePrice itemSkuBasePrice = new ItemSkuBasePrice();
                if (standardPriceDTO.getItemSkuBasePrice() != null) {
                    itemSkuBasePrice = standardPriceDTO.getItemSkuBasePrice();
                }
                itemSkuBasePrice.setSkuId(skuId);
                itemSkuBasePrice.setItemId(itemId);
                itemSkuBasePrice.setSellerId(sellerId);
                itemSkuBasePrice.setItemCode(itemCode);
                itemSkuBasePrice.setChannelCode(ProductChannelEnum.INTERNAL_SUPPLIER.getCode());
                itemSkuBasePrice.setSaleLimitedPrice(saleLimitPrice);
                itemSkuBasePrice.setModifyTime(new Date());

                itemSkuBasePrice.setRetailPrice(retailPrice); // 默认erp零售价
                if (isBoxFlag == 0) { // 大厅
                    itemSkuBasePrice.setAreaSalePrice(salePrice);
                } else if (isBoxFlag == 1) {
                    itemSkuBasePrice.setBoxSalePrice(salePrice);
                }
                standardPriceDTO.setItemSkuBasePrice(itemSkuBasePrice);
                this.itemSkuPriceService.updateItemSkuStandardPrice(standardPriceDTO, isBoxFlag);
                // 处理销售区域；默认销售区域
                List<ItemSalesAreaDetail> itemSalesAreaDetailList = new ArrayList<>();
                List<ItemSalesDefaultArea> defaultList = this.itemSalesDefaultAreaMapper.selectDefaultSalesAreaBySellerId(sellerId);
                if (defaultList != null && defaultList.size() > 0) {
                    for (ItemSalesDefaultArea itemSalesDefaultArea : defaultList) {
                        if (StringUtils.isNotEmpty(itemSalesDefaultArea.getAreaCode())) {
                            ItemSalesAreaDetail itemSalesAreaDetail = new ItemSalesAreaDetail();
                            itemSalesAreaDetail.setAreaCode(itemSalesDefaultArea.getAreaCode());
                            itemSalesAreaDetail.setSalesAreaType((itemSalesDefaultArea.getAreaCode().length() / 2) + ""); // 省码2位，市码4位，区位6位；除以2 得到 类型1,2,3
                            itemSalesAreaDetailList.add(itemSalesAreaDetail);
                        }
                    }
                } else { // 注册所在地的省
                    ItemSalesAreaDetail itemSalesAreaDetail = new ItemSalesAreaDetail();
                    itemSalesAreaDetail.setAreaCode(areCode);
                    itemSalesAreaDetail.setSalesAreaType("1");
                    itemSalesAreaDetailList.add(itemSalesAreaDetail);
                }
                Long salesAreaId = null;
                ItemSalesArea itemSalesAreaFromDb = itemSalesAreaMapper.selectByItemId(itemId, shelfType);
                if (itemSalesAreaFromDb == null) {
                    ItemSalesArea itemSalesArea = new ItemSalesArea();
                    itemSalesArea.setItemId(itemId);
                    itemSalesArea.setIsBoxFlag(isBoxFlag);
                    itemSalesArea.setIsSalesWholeCountry(0);
                    itemSalesArea.setCreateId(0L);
                    itemSalesArea.setCreateName("system");
                    itemSalesArea.setCreateTime(new Date());
                    itemSalesArea.setModifyId(0L);
                    itemSalesArea.setModifyTime(date);
                    itemSalesArea.setModifyName("system");
                    itemSalesArea.setDeleteFlag(0);
                    itemSalesAreaMapper.insertSelective(itemSalesArea);
                    salesAreaId = itemSalesArea.getSalesAreaId();
                } else {
                    //update
                    itemSalesAreaFromDb.setSalesAreaId(itemSalesAreaFromDb.getSalesAreaId());
                    itemSalesAreaFromDb.setModifyId(0L);
                    itemSalesAreaFromDb.setModifyTime(date);
                    itemSalesAreaFromDb.setModifyName("system");
                    itemSalesAreaFromDb.setDeleteFlag(0);
                    itemSalesAreaMapper.updateByPrimaryKeySelective(itemSalesAreaFromDb);
                    salesAreaId = itemSalesAreaFromDb.getSalesAreaId();
                }
                //处理salesareadetail
                if (CollectionUtils.isNotEmpty(itemSalesAreaDetailList)) {
                    for (ItemSalesAreaDetail salesAreaDetail : itemSalesAreaDetailList) {
                        salesAreaDetail.setItemId(itemId);
                        salesAreaDetail.setCreateId(0L);
                        salesAreaDetail.setCreateName("system");
                        salesAreaDetail.setCreateTime(new Date());
                        salesAreaDetail.setModifyId(0L);
                        salesAreaDetail.setModifyTime(date);
                        salesAreaDetail.setModifyName("system");
                        salesAreaDetail.setSalesAreaId(salesAreaId);
                    }
                    //先删除
                    itemSalesAreaDetailMapper.deleteBySalesAreaId(salesAreaId);
                    //再批量插入
                    itemSalesAreaDetailMapper.batchInsertSalesAreaDetail(itemSalesAreaDetailList);
                }
                // 处理商品状态；
                itemMybatisDAO.updateItemStatusByPk(itemId, HtdItemStatusEnum.SHELVED.getCode(), 0l, "system"); //更新主状态为上架
                //修改商品更新时间
                itemMybatisDAO.updateItemModifyTimeByItemId(itemId, 1);
            }
            batchOnShelfOutDTO.setTotal(dataList.size());
            batchOnShelfOutDTO.setFailureList(failureList);
            batchOnShelfOutDTO.setFailCount(failureList.size());
            batchOnShelfOutDTO.setSuccess(dataList.size() - failureList.size());
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResult(batchOnShelfOutDTO);
        } catch (Exception e) {
            logger.error("批量上架出错, 错误信息：", e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.addErrorMessage(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Transactional
    @Override
    public ExecuteResult<BatchModifyPriceOutDTO> batchModifyItemPrice(BatchModifyPriceInDTO batchModifyPriceInDTO) {
        ExecuteResult<BatchModifyPriceOutDTO> executeResult = new ExecuteResult<>();
        try {
            ValidateResult validateResult = DTOValidateUtil.validate(batchModifyPriceInDTO);
            if (!validateResult.isPass()) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
                executeResult.setResultMessage(validateResult.getMessage());
                return executeResult;
            }
            // input
            Integer isBoxFlag = batchModifyPriceInDTO.getIsBoxFlag();
            String shelfType = isBoxFlag == 1 ? "1" : "2";
            Long sellerId = batchModifyPriceInDTO.getSellerId();
            String supplierCode = batchModifyPriceInDTO.getSupplierCode();
            Integer hasBelowLimitPriceAuth = batchModifyPriceInDTO.getHasBelowLimitPriceAuth();
            List<BatchModifyPriceItemInDTO> dataList = batchModifyPriceInDTO.getDataList();
            // output
            BatchModifyPriceOutDTO batchModifyPriceOutDTO = new BatchModifyPriceOutDTO();
            List<BatchModifyPriceItemOutDTO> failureList = new ArrayList<>();
            for (BatchModifyPriceItemInDTO batchModifyPriceItemInDTO : dataList) {
                String itemCode = batchModifyPriceItemInDTO.getItemCode();
                BigDecimal salePrice = batchModifyPriceItemInDTO.getSalePrice();
                BigDecimal retailPrice = batchModifyPriceItemInDTO.getRetailPrice();
                // 入参校验
                ValidateResult validateResult1 = DTOValidateUtil.validate(batchModifyPriceItemInDTO);
                if (!validateResult1.isPass()) {
                    String errorMsg = validateResult1.getMessage();
                    this.addFailureList(failureList, "", itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                // 商品是否存在
                Item item = this.itemMybatisDAO.queryItemByItemCode(itemCode);
                if (item == null) {
                    String errorMsg = "商品不存在";
                    this.addFailureList(failureList, "", itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                ItemSpu itemSpu = this.itemSpuMapper.selectByPrimaryKey(item.getItemSpuId());
                if (itemSpu == null) {
                    String errorMsg = "商品模板不存在";
                    this.addFailureList(failureList, "", itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                String saleLimitPriceStr = MiddlewareInterfaceUtil.findItemFloorPrice(supplierCode, itemSpu.getSpuCode());
                BigDecimal saleLimitPrice = StringUtils.isNumeric(saleLimitPriceStr) ? new BigDecimal(saleLimitPriceStr) : null;
                List<ItemSku> itemSkuList = this.itemSkuDAO.queryByItemId(item.getItemId());
                if (itemSkuList.size() == 0) {
                    String errorMsg = "商品不存在";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
                ItemSku itemSku = itemSkuList.get(0);
                // 不是该大b的商品
                if (!sellerId.equals(item.getSellerId())) {
                    String errorMsg = "该商品不是此供应商的商品";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
                // 商品是否上架
                ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(itemSku.getSkuId(), shelfType, "0");
                if (itemSkuPublishInfo == null) {
                    String errorMsg = "该商品未上架";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
                Integer itemStatus = item.getItemStatus();
                Integer isVisable = itemSkuPublishInfo.getIsVisable();
                if (isVisable == 0 || itemStatus != 5) {
                    String errorMsg = "该商品未上架, itemStatus:"+itemStatus + ",isVisable:"+isVisable;
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
                // 价格校验
                if (salePrice.compareTo(retailPrice) > 0) {
                    String errorMsg = "销售价大于零售价";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
                // 和分销限价比
                if (hasBelowLimitPriceAuth == 0 && retailPrice.compareTo(saleLimitPrice) < 0) {
                    String errorMsg = "自定义价格,没有低于分销限价的权限,零售价不能低于分销限价";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
                if (hasBelowLimitPriceAuth == 0 && salePrice.compareTo(saleLimitPrice) < 0) {
                    String errorMsg = "自定义价格,没有低于分销限价的权限,销售价不能低于分销限价";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
                // 查询基本价格
                ExecuteResult<ItemSkuBasePriceDTO> priceResult = this.itemSkuPriceService.queryItemSkuBasePrice(itemSku.getSkuId());
                if (priceResult == null || !priceResult.isSuccess()) {
                    String errorMsg = "没有查到价格信息";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
                ItemSkuBasePriceDTO itemSkuBasePriceDTO = priceResult.getResult();
                if (itemSkuBasePriceDTO == null) {
                    String errorMsg = "没有查到价格信息";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
                ItemSkuBasePrice itemSkuBasePrice = new ItemSkuBasePrice();
                itemSkuBasePrice.setSkuId(itemSkuBasePriceDTO.getSkuId());
                itemSkuBasePrice.setRetailPrice(retailPrice);
                if (isBoxFlag == 1) {
                    itemSkuBasePrice.setBoxSalePrice(salePrice);
                } else {
                    itemSkuBasePrice.setAreaSalePrice(salePrice);
                }
                ExecuteResult<String> updateResult = this.itemSkuPriceService.updateItemSkuBasePrice(itemSkuBasePrice);
                if (updateResult == null || !updateResult.isSuccess()) {
                    String errorMsg = "价格更新失败";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitPrice, errorMsg);
                    continue;
                }
            }
            batchModifyPriceOutDTO.setFailCount(failureList.size());
            batchModifyPriceOutDTO.setFailureList(failureList);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResult(batchModifyPriceOutDTO);
        } catch (Exception e) {
            logger.error("批量改价出错, 错误信息：", e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("批量改价系统错误");
            executeResult.addErrorMessage(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
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

    private void addFailureList(List<BatchOnShelfItemOutDTO> failureList, String itemName, String itemCode, String errorMsg) {
        BatchOnShelfItemOutDTO batchOnShelfItemOutDTO = new BatchOnShelfItemOutDTO();
        batchOnShelfItemOutDTO.setItemName(itemName);
        batchOnShelfItemOutDTO.setItemCode(itemCode);
        batchOnShelfItemOutDTO.setErrorMsg(errorMsg);
        failureList.add(batchOnShelfItemOutDTO);
    }

    private void addFailureList(List<BatchModifyPriceItemOutDTO> failureList, String itemName, String itemCode,  BigDecimal retailPrice,
                                BigDecimal salePrice, BigDecimal saleLimitPrice, String errorMsg) {
        BatchModifyPriceItemOutDTO batchModifyPriceItemOutDTO = new BatchModifyPriceItemOutDTO();
        batchModifyPriceItemOutDTO.setItemName(itemName);
        batchModifyPriceItemOutDTO.setItemCode(itemCode);
        batchModifyPriceItemOutDTO.setRetailPrice(retailPrice);
        batchModifyPriceItemOutDTO.setSalePrice(salePrice);
        batchModifyPriceItemOutDTO.setSaleLimitPrice(saleLimitPrice);
        batchModifyPriceItemOutDTO.setErrorMsg(errorMsg);
        failureList.add(batchModifyPriceItemOutDTO);
    }

    /**
     * 计算商品名称重复的数量
     *
     * @param batchAddItemInDTOList
     * @return
     */
    private Map<String, Integer> calculateSameProductNameCount(List<BatchAddItemInDTO> batchAddItemInDTOList) {
        Map<String, Integer> map = new HashMap<>();
        for (BatchAddItemInDTO batchAddItemInDTO : batchAddItemInDTOList) {
            String productName = batchAddItemInDTO.getProductName();
            if (StringUtils.isEmpty(productName)) {
                continue;
            }
            if (map.containsKey(productName.trim())) {
                int count = map.get(productName.trim());
                map.put(productName.trim(), ++count);
            } else {
                map.put(productName.trim(), 1);
            }
        }
        return map;
    }

    /**
     * 根据商品表状态和草稿表状态，计算出给前台的审核状态
     *
     * @param itemStatus
     * @param status
     * @param erpCode    // TODO : 魔法数字替换
     * @return
     */
    private Integer getAuditStatus(Integer itemStatus, Integer status, String erpCode) {
        // 待审核
        if (itemStatus == 0 && status == 0 && (StringUtils.isEmpty(erpCode) || "0".equals(erpCode))) {
            return AuditStatusEnum.AUDIT.getCode();
        }
        // 审核通过
        if ((itemStatus == 1 || itemStatus == 4 || itemStatus == 5) && status == 1) {
            return AuditStatusEnum.PASS.getCode();
        }
        // 新增审核驳回
        if (itemStatus == 2 && status == 2 && (StringUtils.isEmpty(erpCode) || "0".equals(erpCode))) {
            return AuditStatusEnum.REJECTED.getCode();
        }
        // 修改待审核
        if ((itemStatus == 1 || itemStatus == 4 || itemStatus == 5) && status == 0 && StringUtils.isNotEmpty(erpCode) && !"0".equals(erpCode)) {
            return AuditStatusEnum.MODIFY_AUDIT.getCode();
        }
        // 修改审核驳回
        if ((itemStatus == 1 || itemStatus == 4 || itemStatus == 5) && status == 2 && StringUtils.isNotEmpty(erpCode) && !"0".equals(erpCode)) {
            return AuditStatusEnum.MODIFY_REJECTED.getCode();
        }
        return null;
    }

    private ExecuteResult<String> validateProductName(String itemName) {
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        if (StringUtils.isEmpty(itemName)) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("商品名称为空");
            return executeResult;
        }
        // 校验模板中有没有
        ItemSpu itemSpu = itemSpuMapper.queryItemSpuByName(itemName);
        if (itemSpu != null && itemSpu.getDeleteFlag() != 1) { // 能查到模板且未删除
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("商品主数据已存在，请通过手动方式新增");
            return executeResult;
        }
        executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        executeResult.setResultMessage("校验成功");
        return executeResult;
    }

    private ExecuteResult<String> validateTaxRate(String taxRate) {
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        if (StringUtils.isEmpty(taxRate)) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("税率为空");
            return executeResult;
        }
        // TODO : 校验数值
        executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        executeResult.setResultMessage("校验成功");
        return executeResult;
    }

    private ExecuteResult<String> validateUnit(String unit) {
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        if (StringUtils.isEmpty(unit)) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("单位为空");
            return executeResult;
        }
        DictionaryInfo dictionaryInfo = dictionaryUtils.getDictionaryByName(DictionaryConst.TYPE_ITEM_UNIT, unit);
        if (dictionaryInfo == null) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("单位在系统中不存在");
            return executeResult;
        }
        executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        executeResult.setResult(dictionaryInfo.getCode());
        executeResult.setResultMessage("校验成功");
        return executeResult;
    }

    private ExecuteResult<String> validateModelType(String modelType) {
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        if (StringUtils.isEmpty(modelType)) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("型号为空");
            return executeResult;
        }
        // TODO : 校验型号的长度
//        if () {
//
//        }
        executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        executeResult.setResultMessage("校验成功");
        return executeResult;
    }

    private ExecuteResult<Long> validateBrandName(String brandName) {
        ExecuteResult<Long> executeResult = new ExecuteResult<>();
        if (StringUtils.isEmpty(brandName)) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("品牌为空");
            return executeResult;
        }
        ItemBrand itemBrand = this.itemBrandDAO.queryByName(brandName.trim());
        if (itemBrand == null || itemBrand.getBrandId() == null || itemBrand.getBrandId() <= 0) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("品牌在系统中不存在");
            return executeResult;
        }
        executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        executeResult.setResult(itemBrand.getBrandId());
        executeResult.setResultMessage("校验成功");
        return executeResult;
    }

    /**
     * 校验三级类目
     *
     * @param categoryName
     * @return 00000
     */
    private ExecuteResult<Long> validateCategoryName(String categoryName) {
        ExecuteResult<Long> executeResult = new ExecuteResult<>();
        if (StringUtils.isEmpty(categoryName)) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("类目为空");
            return executeResult;
        }
        Long cid = this.itemCategoryDAO.queryThirdCategoryIdByName(categoryName.trim());
        if (cid == null || cid <= 0) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("类目在系统中不存在");
            return executeResult;
        }
        executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        executeResult.setResult(cid);
        executeResult.setResultMessage("校验成功");
        return executeResult;
    }

    private void copyBatchAddItemInDTO2BatchAddItemErrorListOutDTO(BatchAddItemInDTO batchAddItemInDTO, BatchAddItemErrorListOutDTO batchAddItemErrorListOutDTO) {
        batchAddItemErrorListOutDTO.setProductName(batchAddItemInDTO.getProductName());
        batchAddItemErrorListOutDTO.setCategoryName(batchAddItemInDTO.getCategoryName());
        batchAddItemErrorListOutDTO.setBrandName(batchAddItemInDTO.getBrandName());
        batchAddItemErrorListOutDTO.setModelType(batchAddItemInDTO.getModelType());
        batchAddItemErrorListOutDTO.setTaxRate(batchAddItemInDTO.getTaxRate());
        batchAddItemErrorListOutDTO.setUnit(batchAddItemInDTO.getUnit());
    }

    private void makeUpPriceInfo4ItemSku(List<QueryVmsItemPublishInfoOutDTO> queryVmsItemPublishInfoOutDTOList, Integer isBoxFlag) {
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
                    if (price.getSaleLimitedPrice() != null) {
                        venusItemSkuPublishInfoOutDTO.setSaleLimitedPrice(String.valueOf(price.getSaleLimitedPrice()));
                    }
                    //包厢价格
                    if (null != price.getBoxSalePrice() &&  1 == venusItemSkuPublishInfoOutDTO.getIsBoxFlag()) {
                        venusItemSkuPublishInfoOutDTO.setSalePrice(String.valueOf(price.getBoxSalePrice()));
                    }
                    //大厅价格
                    if (null != price.getAreaSalePrice() && 0 == venusItemSkuPublishInfoOutDTO.getIsBoxFlag()) {
                        venusItemSkuPublishInfoOutDTO.setSalePrice(String.valueOf(price.getAreaSalePrice()));
                    }
                    //零售价
                    if (null != price.getRetailPrice()) {
                        venusItemSkuPublishInfoOutDTO.setRetailPrice(String.valueOf(price.getRetailPrice()));
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

    /**
     * 获取该商品在促销中心占用的库存
     *
     * @param skuCode
     * @return
     */
    private Integer getPromotionStock(String skuCode) {
        Integer promotionQty = 0;
        try {
            ExecuteResult<Integer> timelimitedInfoDTOResult = timelimitedInfoService.getSkuTimelimitedAllCount(MessageIdUtils.generateMessageId(), skuCode);
            if (timelimitedInfoDTOResult != null && timelimitedInfoDTOResult.isSuccess()) {
                promotionQty = timelimitedInfoDTOResult.getResult() == null ? 0 : timelimitedInfoDTOResult.getResult();
            }
        } catch (Exception e) {
            logger.error(" 获取该商品在促销中心占用的库存出错, 出错信息：", e);
        }
        return promotionQty;
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
                        Integer attrValueCode = (Integer) attrValueCodeArray.get(i);
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
}
