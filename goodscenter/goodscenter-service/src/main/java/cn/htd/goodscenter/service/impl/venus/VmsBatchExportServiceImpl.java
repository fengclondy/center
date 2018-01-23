package cn.htd.goodscenter.service.impl.venus;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.common.util.MessageIdUtils;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.common.utils.DTOValidateUtil;
import cn.htd.goodscenter.common.utils.ValidateResult;
import cn.htd.goodscenter.dao.*;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.*;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.ItemCategoryCompleteDTO;
import cn.htd.goodscenter.dto.ItemCategoryDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ProductChannelEnum;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.dto.vms.*;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.ItemExportService;
import cn.htd.goodscenter.service.venus.VmsBatchExportService;
import cn.htd.goodscenter.service.venus.VmsItemExportService;
import cn.htd.marketcenter.dto.TimelimitedSkuCountDTO;
import cn.htd.marketcenter.service.TimelimitedInfoService;
import cn.htd.marketcenter.service.TimelimitedSkuInfo4VMSService;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.dto.StandardPriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import cn.htd.storecenter.dto.ShopCategorySellerQueryDTO;
import cn.htd.storecenter.service.ShopCategorySellerExportService;
import com.google.common.collect.Lists;
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

@Service("vmsBatchExportServiceImpl")
public class VmsBatchExportServiceImpl implements VmsBatchExportService {

    private final Logger logger = LoggerFactory.getLogger(VmsBatchExportServiceImpl.class);

    @Resource
    private ItemCategoryDAO itemCategoryDAO;

    @Resource
    private ItemBrandDAO itemBrandDAO;

    @Resource
    private ShopCategorySellerExportService shopCategorySellerExportService;

    @Resource
    private VmsItemExportService vmsItemExportService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @Resource
    private ItemMybatisDAO itemMybatisDAO;

    @Resource
    private ItemSpuMapper itemSpuMapper;

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
    private TimelimitedSkuInfo4VMSService timelimitedSkuInfo4VMSService;

    @Resource
    private ItemSalesDefaultAreaMapper itemSalesDefaultAreaMapper;

    @Resource
    private ItemSalesAreaMapper itemSalesAreaMapper;

    @Resource
    private ItemSalesAreaDetailMapper itemSalesAreaDetailMapper;

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
                String unit = batchAddItemInDTO.getUnit();
                ExecuteResult<String> unitResult = validateUnit(unit);
                if (!ResultCodeEnum.SUCCESS.getCode().equals(unitResult.getCode())) {
                    batchAddItemErrorListOutDTO.setErroMsg(unitResult.getResultMessage());
                    errorList.add(batchAddItemErrorListOutDTO);
                    continue;
                }
                String unitCode = unitResult.getResult();
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
        return executeResult;
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
                    BigDecimal saleLimitPrice = null;
                    BigDecimal wsaleUtprice = null;
                    BigDecimal webPrice = null;
                    if (StringUtils.isNotEmpty(spuCode)) {
                        Map priceMap = MiddlewareInterfaceUtil.findItemERPPrice(supplierCode, spuCode);
                        if (MapUtils.isNotEmpty(priceMap)) {
                            saleLimitPrice = priceMap.get("floorPrice") != null ? new BigDecimal((String) priceMap.get("floorPrice")) : null;
                            wsaleUtprice = priceMap.get("wsaleUtprice") != null ? new BigDecimal((String) priceMap.get("wsaleUtprice")) : null;
                            webPrice = priceMap.get("webPrice") != null ? new BigDecimal((String) priceMap.get("webPrice")) : null;
                        }
                    }
                    queryOffShelfItemOutDTO.setSaleLimitedPrice(saleLimitPrice);
                    queryOffShelfItemOutDTO.setWsaleUtprice(wsaleUtprice);
                    queryOffShelfItemOutDTO.setWebPrice(webPrice);
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
                BigDecimal saleLimitPrice = batchOnShelfItemInDTO.getSaleLimitedPrice(); //分销限价
                BigDecimal wsaleUtprice = batchOnShelfItemInDTO.getWsaleUtprice(); // erp零售价
                BigDecimal webPrice = batchOnShelfItemInDTO.getWebPrice(); // erp分销单价
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
                    salePrice = webPrice;
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
            Integer hasBelowLimitPriceAuth = batchModifyPriceInDTO.getHasBelowLimitPriceAuth();
            List<BatchModifyPriceItemInDTO> dataList = batchModifyPriceInDTO.getDataList();
            // output
            BatchModifyPriceOutDTO batchModifyPriceOutDTO = new BatchModifyPriceOutDTO();
            List<BatchModifyPriceItemOutDTO> failureList = new ArrayList<>();
            for (BatchModifyPriceItemInDTO batchModifyPriceItemInDTO : dataList) {
                String itemCode = batchModifyPriceItemInDTO.getItemCode();
                // 商品是否存在
                Item item = this.itemMybatisDAO.queryItemByItemCode(itemCode);
                if (item == null) {
                    String errorMsg = "商品不存在";
                    this.addFailureList(failureList, "", itemCode, null, null, null, errorMsg);
                    continue;
                }
                String salePrice = batchModifyPriceItemInDTO.getSalePrice();
                String retailPrice = batchModifyPriceItemInDTO.getRetailPrice();
                // 入参校验
                ValidateResult validateResult1 = DTOValidateUtil.validate(batchModifyPriceItemInDTO);
                if (!validateResult1.isPass()) {
                    String errorMsg = validateResult1.getMessage();
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                ItemSpu itemSpu = this.itemSpuMapper.selectByPrimaryKey(item.getItemSpuId());
                if (itemSpu == null) {
                    String errorMsg = "商品模板不存在";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                List<ItemSku> itemSkuList = this.itemSkuDAO.queryByItemId(item.getItemId());
                if (itemSkuList.size() == 0) {
                    String errorMsg = "商品不存在";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                ItemSku itemSku = itemSkuList.get(0);
                // 不是该大b的商品
                if (!sellerId.equals(item.getSellerId())) {
                    String errorMsg = "该商品不是此供应商的商品";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                // 商品是否上架
                ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(itemSku.getSkuId(), shelfType, "0");
                if (itemSkuPublishInfo == null) {
                    String errorMsg = "该商品未上架";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                Integer itemStatus = item.getItemStatus();
                Integer isVisable = itemSkuPublishInfo.getIsVisable();
                if (isVisable == 0 || itemStatus != 5) {
                    String errorMsg = "该商品未上架, itemStatus:"+itemStatus + ",isVisable:"+isVisable;
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                // 价格校验
                // 查询基本价格
                ExecuteResult<ItemSkuBasePriceDTO> priceResult = this.itemSkuPriceService.queryItemSkuBasePrice(itemSku.getSkuId());
                if (priceResult == null || !priceResult.isSuccess() || priceResult.getResult() == null) {
                    String errorMsg = "没有查到价格信息";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, null, errorMsg);
                    continue;
                }
                ItemSkuBasePriceDTO itemSkuBasePriceDTO = priceResult.getResult();
                BigDecimal saleLimitedPrice = itemSkuBasePriceDTO.getSaleLimitedPrice();
                if (new BigDecimal(salePrice).compareTo(new BigDecimal(retailPrice)) > 0) {
                    String errorMsg = "销售价大于零售价";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitedPrice, errorMsg);
                    continue;
                }
                // 和分销限价比
                if (hasBelowLimitPriceAuth == 0 && new BigDecimal(retailPrice).compareTo(saleLimitedPrice) < 0) {
                    String errorMsg = "自定义价格,没有低于分销限价的权限,零售价不能低于分销限价";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitedPrice, errorMsg);
                    continue;
                }
                if (hasBelowLimitPriceAuth == 0 && new BigDecimal(salePrice).compareTo(saleLimitedPrice) < 0) {
                    String errorMsg = "自定义价格,没有低于分销限价的权限,销售价不能低于分销限价";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitedPrice, errorMsg);
                    continue;
                }
                ItemSkuBasePrice itemSkuBasePrice = new ItemSkuBasePrice();
                itemSkuBasePrice.setSkuId(itemSkuBasePriceDTO.getSkuId());
                itemSkuBasePrice.setRetailPrice(new BigDecimal(retailPrice));
                if (isBoxFlag == 1) {
                    itemSkuBasePrice.setBoxSalePrice(new BigDecimal(salePrice));
                } else {
                    itemSkuBasePrice.setAreaSalePrice(new BigDecimal(salePrice));
                }
                ExecuteResult<String> updateResult = this.itemSkuPriceService.updateItemSkuBasePrice(itemSkuBasePrice);
                if (updateResult == null || !updateResult.isSuccess()) {
                    String errorMsg = "价格更新失败";
                    this.addFailureList(failureList, item.getItemName(), itemCode, retailPrice, salePrice, saleLimitedPrice, errorMsg);
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
        executeResult.setResult(dictionaryInfo.getValue());
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
        if (modelType.length() > 80) {
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage("型号长度不能超过80");
            return executeResult;
        }
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
        if (CollectionUtils.isEmpty(cNameList)) {
            return resultMap;
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
        if (CollectionUtils.isEmpty(cNameList)) {
            return resultMap;
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

    /**
     * 获取该商品在促销中心占用的库存
     *
     * @param skuCode
     * @return
     */
    private Integer getPromotionStock(String skuCode) {
        Integer promotionQty = 0;
        try {
            ExecuteResult<TimelimitedSkuCountDTO> timelimitedInfoDTOResult = timelimitedSkuInfo4VMSService.getSkuTimelimitedAllCount(MessageIdUtils.generateMessageId(), skuCode);
            if(timelimitedInfoDTOResult != null && timelimitedInfoDTOResult.isSuccess() && timelimitedInfoDTOResult.getResult() != null) {
                TimelimitedSkuCountDTO timelimitedSkuCountDTO = timelimitedInfoDTOResult.getResult();
                promotionQty = timelimitedSkuCountDTO.getInvalidSkuCount() + timelimitedSkuCountDTO.getValidSkuCount();
            }
        } catch (Exception e) {
            logger.error(" 获取该商品在促销中心占用的库存出错, 出错信息：", e);
        }
        return promotionQty;
    }

    private void addFailureList(List<BatchOnShelfItemOutDTO> failureList, String itemName, String itemCode, String errorMsg) {
        BatchOnShelfItemOutDTO batchOnShelfItemOutDTO = new BatchOnShelfItemOutDTO();
        batchOnShelfItemOutDTO.setItemName(itemName);
        batchOnShelfItemOutDTO.setItemCode(itemCode);
        batchOnShelfItemOutDTO.setErrorMsg(errorMsg);
        failureList.add(batchOnShelfItemOutDTO);
    }

    private void addFailureList(List<BatchModifyPriceItemOutDTO> failureList, String itemName, String itemCode,  String retailPrice,
                                String salePrice, BigDecimal saleLimitPrice, String errorMsg) {
        BatchModifyPriceItemOutDTO batchModifyPriceItemOutDTO = new BatchModifyPriceItemOutDTO();
        batchModifyPriceItemOutDTO.setItemName(itemName);
        batchModifyPriceItemOutDTO.setItemCode(itemCode);
        batchModifyPriceItemOutDTO.setRetailPrice(retailPrice);
        batchModifyPriceItemOutDTO.setSalePrice(salePrice);
        batchModifyPriceItemOutDTO.setSaleLimitPrice(saleLimitPrice == null ? "" : String.valueOf(saleLimitPrice));
        batchModifyPriceItemOutDTO.setErrorMsg(errorMsg);
        failureList.add(batchModifyPriceItemOutDTO);
    }
}
