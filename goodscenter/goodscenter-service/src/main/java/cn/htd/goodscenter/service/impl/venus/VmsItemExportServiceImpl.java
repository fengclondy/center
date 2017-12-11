package cn.htd.goodscenter.service.impl.venus;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.common.constants.VenusErrorCodes;
import cn.htd.goodscenter.dao.*;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.*;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.enums.AuditStatusEnum;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;
import cn.htd.goodscenter.dto.vms.*;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.venus.VenusItemExportService;
import cn.htd.goodscenter.service.venus.VmsItemExportService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * vms2.0 商品中心接口
 * @date 2017-12-06
 * @author chenkang
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
    private DictionaryUtils dictionaryUtils;

    /**
     * 我的商品 - 商品列表
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
            if(queryVmsMyItemListInDTO == null || pager == null) {
                result.setCode(VenusErrorCodes.E1040009.name());
                result.setResultMessage(VenusErrorCodes.E1040009.getErrorMsg());
                result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040009.getErrorMsg()));
                return result;
            }
            if(queryVmsMyItemListInDTO.getSellerId() == null||queryVmsMyItemListInDTO.getSellerId() <= 0){
                result.setCode(VenusErrorCodes.E1040010.name());
                result.setResultMessage(VenusErrorCodes.E1040010.getErrorMsg());
                result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040010.getErrorMsg()));
                return result;
            }
            // 封装三级类目集合
            Long[] thirdCategoryIds  = this.itemCategoryService.getAllThirdCategoryByCategoryId(queryVmsMyItemListInDTO.getFirstCategoryId(),
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
                count = this.itemMybatisDAO.queryVmsDraftItemSkuListCount(queryVmsMyItemListInDTO);
                if (count > 0) {
                    queryVmsMyItemListOutDTOS = this.itemMybatisDAO.queryVmsDraftItemSkuList(queryVmsMyItemListInDTO, pager);
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
     * @param itemSkuId
     * @return
     */
    @Override
    public ExecuteResult<VenusItemSkuDetailOutDTO> queryItemSkuDetail(Long itemSkuId) {
        return this.venusItemExportService.queryItemSkuDetail(itemSkuId);
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

    /**
     * 我的商品 - 申请商品
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
     * @param batchAddItemInDTOList
     * @return
     */
    @Override
    public ExecuteResult<BatchAddItemOutDTO> batchAddItem(List<BatchAddItemInDTO> batchAddItemInDTOList) {
        ExecuteResult<BatchAddItemOutDTO> executeResult = new ExecuteResult<>();
        //前置校验
        if(batchAddItemInDTOList == null){
            executeResult.setCode(ErrorCodes.E10005.name());
            executeResult.setErrorMessages(Lists.newArrayList(ErrorCodes.E10005.getErrorMsg()));
            return  executeResult;
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
                ExecuteResult<String> modelTypeResult =  validateModelType(modelType);
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
     * @param venusItemDTO
     * @return
     */
    @Override
    public ExecuteResult<String> addItem(VenusItemInDTO venusItemDTO) {
        return this.venusItemExportService.addItem(venusItemDTO);
    }

    @Override
    public ExecuteResult<DataGrid<QueryVmsItemPublishInfoOutDTO>> queryItemSkuPublishInfoList(QueryVmsItemPublishInfoInDTO queryVmsItemPublishInfoInDTO, Pager<String> page) {
        return null;
    }

    /**
     * 计算商品名称重复的数量
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
     * @param itemStatus
     * @param status
     * @param erpCode
     * // TODO : 魔法数字替换
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
        if ((itemStatus == 1 || itemStatus == 4 || itemStatus == 5) && status == 2 &&  StringUtils.isNotEmpty(erpCode) && !"0".equals(erpCode)) {
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
        ItemSpu itemSpu=itemSpuMapper.queryItemSpuByName(itemName);
        if(itemSpu !=null && itemSpu.getDeleteFlag() != 1) { // 能查到模板且未删除
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
}
