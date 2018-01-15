package cn.htd.goodscenter.service.impl.venus;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.common.utils.DTOValidateUtil;
import cn.htd.goodscenter.common.utils.ValidateResult;
import cn.htd.goodscenter.dao.ItemBrandDAO;
import cn.htd.goodscenter.dao.ItemCategoryDAO;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.ItemCategoryCompleteDTO;
import cn.htd.goodscenter.dto.ItemCategoryDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.dto.vms.BatchAddItemErrorListOutDTO;
import cn.htd.goodscenter.dto.vms.BatchAddItemInDTO;
import cn.htd.goodscenter.dto.vms.BatchAddItemOutDTO;
import cn.htd.goodscenter.service.venus.VmsBatchAddItemExportService;
import cn.htd.goodscenter.service.venus.VmsItemExportService;
import cn.htd.storecenter.dto.ShopCategorySellerQueryDTO;
import cn.htd.storecenter.service.ShopCategorySellerExportService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

public class VmsBatchAddItemExportServiceImpl implements VmsBatchAddItemExportService {

    private final Logger logger = LoggerFactory.getLogger(VmsBatchAddItemExportServiceImpl.class);

    @Resource
    private ItemCategoryDAO itemCategoryDAO;

    @Resource
    private ItemBrandDAO itemBrandDAO;

    @Resource
    private ShopCategorySellerExportService shopCategorySellerExportService;

    @Resource
    private ItemSpuMapper itemSpuMapper;

    @Resource
    private DictionaryUtils dictionaryUtils;

    @Resource
    private VmsItemExportService vmsItemExportService;

    @Override
    public ExecuteResult<BatchAddItemOutDTO> batchAddItem(List<BatchAddItemInDTO> batchAddItemInDTOList) {
        System.out.println("计算时间开始~~~~");
        Long start0 = (new Date()).getTime();
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
            List<Long> preQueryThirdCidList = new ArrayList<>();
            // 统计商品名称的重复数量
            Map<String, Integer> sameProCountMap = calculateSameProductNameCount(batchAddItemInDTOList);
            // 批量获取类目对应的类目ID
            Map<String, Long> cidMap = batchQueryCid4CName(batchAddItemInDTOList);
            Map<String, Long> bidMap = batchQueryBid4BName(batchAddItemInDTOList);
            // 业务校验
            for (BatchAddItemInDTO batchAddItemInDTO : batchAddItemInDTOList) {
                // 失败DTO
                BatchAddItemErrorListOutDTO batchAddItemErrorListOutDTO = new BatchAddItemErrorListOutDTO();
                this.copyBatchAddItemInDTO2BatchAddItemErrorListOutDTO(batchAddItemInDTO, batchAddItemErrorListOutDTO);
                ValidateResult validateResult = DTOValidateUtil.validate(batchAddItemInDTO);
                if (!validateResult.isPass()) {
                    batchAddItemErrorListOutDTO.setErroMsg(validateResult.getMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                // 校验类目
                String categoryName = batchAddItemInDTO.getCategoryName();
                ExecuteResult<Long> categoryResult = validateCategoryName(categoryName, cidMap);
                if (!ResultCodeEnum.SUCCESS.getCode().equals(categoryResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(categoryResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                Long cid = categoryResult.getResult();
                // 校验品牌
                String brandName = batchAddItemInDTO.getBrandName();
                ExecuteResult<Long> brandResult = validateBrandName(brandName, bidMap);
                if (!ResultCodeEnum.SUCCESS.getCode().equals(brandResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(brandResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                Long brandId = brandResult.getResult();
                // 校验型号
                String modelType = batchAddItemInDTO.getModelType();
                ExecuteResult<String> modelTypeResult = validateModelType(modelType);
                if (!ResultCodeEnum.SUCCESS.getCode().equals(modelTypeResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(modelTypeResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                // 校验单位
                String unit = batchAddItemInDTO.getUnit(); // TODO : 暂时写死
//                ExecuteResult<String> unitResult = validateUnit(unit);
//                if (!ResultCodeEnum.SUCCESS.getCode().equals(unitResult.getCode())) {
//                    batchAddItemErrorListOutDTO.setErroMsg(unitResult.getResultMessage());
//                    errorList.add(batchAddItemErrorListOutDTO);
//                    continue;
//                }
                String unitCode = "dui";//unitResult.getResult();
                // 校验税率
                String taxRate = batchAddItemInDTO.getTaxRate();
                ExecuteResult<String> rateResult = validateTaxRate(taxRate);
                if (!ResultCodeEnum.SUCCESS.getCode().equals(rateResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(rateResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                // 校验名称
                String itemName = batchAddItemInDTO.getProductName();
                ExecuteResult<String> itemResult = validateProductName(itemName);
                if (!ResultCodeEnum.SUCCESS.getCode().equals(itemResult.getCode())) {
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
                // 待导入的ITEN
                BatchAddItemInDTO preImportItem = new BatchAddItemInDTO();
                preImportItem.setProductName(batchAddItemInDTO.getProductName());
                preImportItem.setCategoryName(batchAddItemInDTO.getCategoryName());
                preImportItem.setBrandName(batchAddItemInDTO.getBrandName());
                preImportItem.setModelType(batchAddItemInDTO.getModelType());
                preImportItem.setUnit(batchAddItemInDTO.getUnit());
                preImportItem.setTaxRate(batchAddItemInDTO.getTaxRate());
                preImportItem.setSellerId(batchAddItemInDTO.getSellerId());
                preImportItem.setShopId(batchAddItemInDTO.getShopId());
                preImportItem.setShopCid(batchAddItemInDTO.getShopCid());
                preImportItem.setOperatorId(batchAddItemInDTO.getOperatorId());
                preImportItem.setOperatorName(batchAddItemInDTO.getOperatorName());
                preImportItem.setBrandId(brandId);
                preImportItem.setThirdCid(cid);
                preImportItem.setUnitCode(unitCode);
                preImportItem.setTaxRate(new BigDecimal(preImportItem.getTaxRate()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP) + "");
                preImportItemList.add(preImportItem);
                preQueryThirdCidList.add(cid);
            }
            // 批量查询类目信息
            if (preQueryThirdCidList.size() > 0) {
                List<ItemCategoryCompleteDTO> itemCategoryCompleteDTOList = this.itemCategoryDAO.batchQueryCategoryComplete(preQueryThirdCidList);
                // 补充类目参数
                if (itemCategoryCompleteDTOList != null) {
                    for (ItemCategoryCompleteDTO itemCategoryCompleteDTO : itemCategoryCompleteDTOList) {
                        for (BatchAddItemInDTO batchAddItemInDTO : preImportItemList) {
                            if (itemCategoryCompleteDTO.getThirdCategoryId().equals(batchAddItemInDTO.getThirdCid())) {
                                batchAddItemInDTO.setSecondCid(itemCategoryCompleteDTO.getSecondCategoryId());
                                batchAddItemInDTO.setFirstCid(itemCategoryCompleteDTO.getFirstCategoryId());
                                batchAddItemInDTO.setParentCategoryName(itemCategoryCompleteDTO.getSecondCategoryName());
                            }
                        }
                    }
                }
            }
            // 开始导入
            for (BatchAddItemInDTO preImportItem : preImportItemList) {
                // 新增查询shpCid
                ShopCategorySellerQueryDTO shopCategorySellerQueryDTO = new ShopCategorySellerQueryDTO();
                shopCategorySellerQueryDTO.setCname(preImportItem.getCategoryName());
                shopCategorySellerQueryDTO.setParentCName(preImportItem.getParentCategoryName());
                shopCategorySellerQueryDTO.setSellerId(preImportItem.getSellerId());
                shopCategorySellerQueryDTO.setCreateId(preImportItem.getOperatorId());
                shopCategorySellerQueryDTO.setCreateName(preImportItem.getOperatorName());
                ExecuteResult<ShopCategorySellerQueryDTO> sellerCatResult = this.shopCategorySellerExportService.addOrQueryByCondition(shopCategorySellerQueryDTO);
                if (sellerCatResult != null && sellerCatResult.isSuccess() && sellerCatResult.getResult() != null) {
                    preImportItem.setShopCid(sellerCatResult.getResult().getCid());
                } else {
                    logger.error("新增店铺类目出错:错误信息", sellerCatResult.getErrorMessages());
                    String errorMsg = "新增店铺类目出错";
                    BatchAddItemErrorListOutDTO batchAddItemErrorListOutDTO = getBatchAddItemErrorListOutDTO(preImportItem, errorMsg);
                    errorList.add(batchAddItemErrorListOutDTO);
                }
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
                venusItemDTO.setOperatorName(StringUtils.isEmpty(preImportItem.getOperatorName()) ? "SYSTEM" : preImportItem.getOperatorName());
                ItemDescribe itemDescribe = new ItemDescribe();
                venusItemDTO.setDescribe(itemDescribe);
                ExecuteResult<String> addResult = this.vmsItemExportService.addItem(venusItemDTO);
                if (!ResultCodeEnum.SUCCESS.getCode().equals(addResult.getCode())) {
                    String errorMsg = addResult.getResultMessage();
                    BatchAddItemErrorListOutDTO batchAddItemErrorListOutDTO = getBatchAddItemErrorListOutDTO(preImportItem, errorMsg);
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
        Long end = (new Date()).getTime();
        System.out.println("耗时：" + (end - start0));
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
        try {
            BigDecimal bigDecimal = new BigDecimal(taxRate);
        } catch (Exception e) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("税率必须是整数");
            return executeResult;
        }
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

    private ExecuteResult<Long> validateBrandName(String brandName, Map<String, Long> brandIdMap) {
        ExecuteResult<Long> executeResult = new ExecuteResult<>();
        if (StringUtils.isEmpty(brandName)) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("品牌为空");
            return executeResult;
        }
        Long brandId = brandIdMap.get(brandName.trim());
        if (brandId == null || brandId <= 0) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("品牌在系统中不存在");
            return executeResult;
        }
        executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        executeResult.setResult(brandId);
        executeResult.setResultMessage("校验成功");
        return executeResult;
    }

    /**
     * 校验三级类目
     *
     * @param categoryName
     * @return 00000
     */
    private ExecuteResult<Long> validateCategoryName(String categoryName, Map<String, Long> cidMap) {
        ExecuteResult<Long> executeResult = new ExecuteResult<>();
        if (StringUtils.isEmpty(categoryName)) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("类目为空");
            return executeResult;
        }
        Long cid = cidMap.get(categoryName.trim());
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

    private Map<String, Long> batchQueryCid4CName(List<BatchAddItemInDTO> batchAddItemInDTOList) {
        Map<String, Long> resultMap = new HashMap<>();
        List<String> cNameList = new ArrayList<>();
        for (BatchAddItemInDTO batchAddItemInDTO : batchAddItemInDTOList) {
            if (batchAddItemInDTO != null && StringUtils.isNotEmpty(batchAddItemInDTO.getCategoryName())) {
                cNameList.add(batchAddItemInDTO.getCategoryName());
            }
        }
        List<ItemCategoryDTO> itemCategoryDTOList = this.itemCategoryDAO.batchQueryThirdCategoryIdByName(cNameList);
        for (ItemCategoryDTO itemCategoryDTO : itemCategoryDTOList) {
            resultMap.put(itemCategoryDTO.getCategoryCName(), itemCategoryDTO.getCategoryCid());
        }
        return resultMap;
    }

    private Map<String, Long> batchQueryBid4BName(List<BatchAddItemInDTO> batchAddItemInDTOList) {
        Map<String, Long> resultMap = new HashMap<>();
        List<String> cNameList = new ArrayList<>();
        for (BatchAddItemInDTO batchAddItemInDTO : batchAddItemInDTOList) {
            if (batchAddItemInDTO != null && StringUtils.isNotEmpty(batchAddItemInDTO.getBrandName())) {
                cNameList.add(batchAddItemInDTO.getBrandName());
            }
        }
        List<ItemBrand> itemBrandList = this.itemBrandDAO.batchQueryBrandByName(cNameList);
        for (ItemBrand itemBrand : itemBrandList) {
            resultMap.put(itemBrand.getBrandName(), itemBrand.getBrandId());
        }
        return resultMap;
    }

    private BatchAddItemErrorListOutDTO getBatchAddItemErrorListOutDTO(BatchAddItemInDTO preImportItem, String errorMsg) {
        BatchAddItemErrorListOutDTO batchAddItemErrorListOutDTO = new BatchAddItemErrorListOutDTO();
        batchAddItemErrorListOutDTO.setProductName(preImportItem.getProductName());
        batchAddItemErrorListOutDTO.setCategoryName(preImportItem.getCategoryName());
        batchAddItemErrorListOutDTO.setBrandName(preImportItem.getBrandName());
        batchAddItemErrorListOutDTO.setModelType(preImportItem.getModelType());
        batchAddItemErrorListOutDTO.setTaxRate(preImportItem.getTaxRate());
        batchAddItemErrorListOutDTO.setUnit(preImportItem.getUnit());
        batchAddItemErrorListOutDTO.setErroMsg(errorMsg);
        return batchAddItemErrorListOutDTO;
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
}
