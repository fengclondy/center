package cn.htd.goodscenter.service.impl.productplus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.common.utils.DTOValidateUtil;
import cn.htd.goodscenter.common.utils.ValidateResult;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.dao.ProductChannelMapper;
import cn.htd.goodscenter.dao.productplus.OuterChannelBrandMappingMapper;
import cn.htd.goodscenter.dao.productplus.OuterChannelCategoryMappingMapper;
import cn.htd.goodscenter.dao.productplus.ProductPlusItemMapper;
import cn.htd.goodscenter.dao.productplus.SellerCategoryBrandShieldMapper;
import cn.htd.goodscenter.dao.productplus.SellerOuterProductChannelMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.common.ProductChannel;
import cn.htd.goodscenter.domain.productplus.OuterChannelBrandMapping;
import cn.htd.goodscenter.domain.productplus.OuterChannelCategoryMapping;
import cn.htd.goodscenter.domain.productplus.SellerCategoryBrandShield;
import cn.htd.goodscenter.domain.productplus.SellerOuterProductChannel;
import cn.htd.goodscenter.dto.ItemBrandDTO;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.OutItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ProductChannelEnum;
import cn.htd.goodscenter.dto.productplus.JdProductQueryInDTO;
import cn.htd.goodscenter.dto.productplus.JdProductQueryItemDTO;
import cn.htd.goodscenter.dto.productplus.JdProductQueryOutDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusAccessInfoInDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusAccessInfoOutDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusItemDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusItemUpShelfSettingDTO;
import cn.htd.goodscenter.dto.productplus.SellerCategoryBrandShieldDTO;
import cn.htd.goodscenter.dto.productplus.SellerOuterProductChannelDTO;
import cn.htd.goodscenter.dto.productplus.SellerOuterProductChannelImportResultDTO;
import cn.htd.goodscenter.service.ItemBrandExportService;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.productplus.ProductPlusExportService;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.pricecenter.domain.InnerItemSkuPrice;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 外接商品服务提供
 *
 * @author chenakng
 */
@Service("productPlusExportService")
public class ProductPlusExportServiceImpl implements ProductPlusExportService {
    /**
     * 商品渠道-持久化
     */
    @Resource
    private ProductChannelMapper productChannelMapper;

    /**
     * 外接渠道商品类目映射关系-持久化
     */
    @Resource
    private OuterChannelCategoryMappingMapper outerChannelCategoryMappingMapper;

    /**
     * 外接渠道商品品牌映射关系-持久化
     */
    @Resource
    private OuterChannelBrandMappingMapper outerChannelBrandMappingMapper;

    /**
     * 卖家接入渠道-持久化
     */
    @Resource
    private SellerOuterProductChannelMapper sellerOuterProductChannelMapper;

    /**
     * 商品+-持久化
     */
    @Resource
    private ProductPlusItemMapper productPlusItemMapper;

    /**
     * price service
     */
    @Autowired
    private ItemSkuPriceService itemSkuPriceService;

    /**
     * category service
     */
    @Autowired
    private ItemCategoryService itemCategoryService;

    @Resource
    private ItemMybatisDAO itemMybatisDAO;

    @Autowired
    private ItemBrandExportService itemBrandExportService;

    @Autowired
    private SellerCategoryBrandShieldMapper sellerCategoryBrandShieldMapper;

    @Resource
    private MemberBaseInfoService memberBaseInfoService;

    @Autowired
    private ItemPictureDAO itemPictureDAO;
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(ProductPlusExportServiceImpl.class);


    /**  运营中心  **/


    /**
     * 外接商品渠道一览
     *
     * @return 外接商品渠道一览
     */
    @Override
    public ExecuteResult<List<ProductChannel>> queryOuterProductChannelList() {
        ExecuteResult<List<ProductChannel>> executeResult = new ExecuteResult();
        try {
            ProductChannel productChannel = new ProductChannel();
            productChannel.setOuterChannelFlag(1); // 外部渠道
            productChannel.setAccessStatus(1); // 已接入状态
            // 所有外接渠道列表
            List<ProductChannel> productChannels = productChannelMapper.selectProductChannelList(productChannel);
            executeResult.setResult(productChannels);
        } catch (Exception e) {
            logger.error("Method [queryOuterProductChannelList] Error::", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<ProductChannel> queryProductChannelByChannelCode(String code) {
        ExecuteResult<ProductChannel> executeResult = new ExecuteResult();
        try {
            ProductChannel productChannel = this.queryProductChannelByCode(code);
            if (productChannel != null) {
                executeResult.setResult(productChannel);
            } else {
                executeResult.addErrorMessage("channel is not exits");
            }
        } catch (Exception e) {
            logger.error("queryProductChannelByChannelCode error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 根据渠道编码查询渠道信息
     *
     * @param code
     * @return
     */
    private ProductChannel queryProductChannelByCode(String code) {
        ProductChannel productChannel = null;
        try {
            productChannel = productChannelMapper.selectProductChannelByCode(code);
        } catch (Exception e) {
            logger.error("Method [queryProductChannelByCode] Error::", e);
        }
        return productChannel;
    }

    /**
     * 查询外接商品类目【已映射】关系
     *
     * @param outerChannelCategoryMapping 外接商品类目映射关系
     * @param pager                       分页参数
     * @return 返回类目已映射关系
     */
    @Override
    public ExecuteResult<DataGrid<OuterChannelCategoryMapping>> queryMappedOuterChannelCategoryList(OuterChannelCategoryMapping outerChannelCategoryMapping, Pager pager) {
        logger.info("Method [queryMappedOuterChannelCategoryList] Entrance, [outerChannelCategoryMapping] : {}, [pager] : {}", outerChannelCategoryMapping, pager);
        ExecuteResult<DataGrid<OuterChannelCategoryMapping>> executeResult = new ExecuteResult<>();
        if (outerChannelCategoryMapping == null) {
            logger.info("Method [queryMappedOuterChannelCategoryList] validate failure, outerChannelCategoryMapping is null");
            executeResult.addErrorMessage("outerChannelCategoryMapping is null");
            return executeResult;
        }
        if (StringUtils.isEmpty(outerChannelCategoryMapping.getChannelCode())) {
            logger.info("Method [queryMappedOuterChannelCategoryList] validate failure, channelCode is empty");
            executeResult.addErrorMessage("channelCode is empty");
            return executeResult;
        }
        return this.queryOuterChannelCategoryMappingList(outerChannelCategoryMapping, pager, true);
    }

    /**
     * 查询外接商品类目【未映射】关系
     *
     * @param outerChannelCategoryMapping 外接商品类目映射关系
     * @param pager                       分页参数
     * @return 返回类目未映射关系
     */
    @Override
    public ExecuteResult<DataGrid<OuterChannelCategoryMapping>> queryNoMappedOuterChannelCategoryList(OuterChannelCategoryMapping outerChannelCategoryMapping, Pager pager) {
        logger.info("Method [queryNoMappedOuterChannelCategoryList] Entrance, [outerChannelCategoryMapping] : {}, [pager] : {}", outerChannelCategoryMapping, pager);
        ExecuteResult<DataGrid<OuterChannelCategoryMapping>> executeResult = new ExecuteResult<>();
        if (outerChannelCategoryMapping == null) {
            logger.info("Method [queryNoMappedOuterChannelCategoryList] validate failure, outerChannelCategoryMapping is null");
            executeResult.addErrorMessage("outerChannelCategoryMapping is null");
            return executeResult;
        }
        if (StringUtils.isEmpty(outerChannelCategoryMapping.getChannelCode())) {
            logger.info("Method [queryNoMappedOuterChannelCategoryList] validate failure, channelCode is empty");
            executeResult.addErrorMessage("channelCode is empty");
            return executeResult;
        }
        return this.queryOuterChannelCategoryMappingList(outerChannelCategoryMapping, pager, false);
    }

    /**
     * 查询外接渠道映射
     *
     * @param outerChannelCategoryMapping 外接渠道映射参数
     * @param pager                       分页参数
     * @param isMapped                    是否映射
     * @return
     */
    @Override
    public ExecuteResult<DataGrid<OuterChannelCategoryMapping>> queryOuterChannelCategoryMappingList(OuterChannelCategoryMapping outerChannelCategoryMapping, Pager pager, boolean isMapped) {
        ExecuteResult<DataGrid<OuterChannelCategoryMapping>> executeResult = new ExecuteResult<>();
        DataGrid<OuterChannelCategoryMapping> dataGrid = new DataGrid<>();
        try {
            if (isMapped) {
                Long categoryId = outerChannelCategoryMapping.getCategoryId(); // 三级类目id
                Long firstCategoryId = outerChannelCategoryMapping.getFirstCategoryId(); // 一级类目id
                Long secondCategoryId = outerChannelCategoryMapping.getSecondCategoryId(); // 二级类目id
                outerChannelCategoryMapping.setCategoryIdParam(this.itemCategoryService.getAllThirdCategoryByCategoryId(firstCategoryId, secondCategoryId, categoryId));
            }
            /** 总条数 **/
            Long count = this.outerChannelCategoryMappingMapper.selectOuterChannelCategoryMappingListCount(outerChannelCategoryMapping, isMapped);
            /** 当前分页 **/
            List<OuterChannelCategoryMapping> outerChannelCategoryMappings = new ArrayList<>();
            if (count > 0) { // 如果有记录查询记录
                // pager如果为null，查询全部记录
                outerChannelCategoryMappings = outerChannelCategoryMappingMapper.selectOuterChannelCategoryMappingList(outerChannelCategoryMapping, pager, isMapped);
                if (isMapped) { // 如果是映射关系，查询类目的名称
                    for (OuterChannelCategoryMapping outerChannelCategoryMappingDb : outerChannelCategoryMappings) {
                        // 查询1级2级3级名称
                        String categoryName = "";
                        Long firstCId = null;
                        Long secondCId = null;
                        Long thirdCId = null;
                        ExecuteResult<Map<String, Object>> executeResultCategory = this.itemCategoryService.queryItemOneTwoThreeCategoryName(outerChannelCategoryMappingDb.getCategoryId(), ">>");
                        if (executeResultCategory.isSuccess()) {
                            Map<String, Object> map = executeResultCategory.getResult();
                            categoryName = (String) map.get("categoryName");
                            firstCId = (Long) map.get("firstCategoryId");
                            secondCId = (Long) map.get("secondCategoryId");
                            thirdCId = (Long) map.get("thirdCategoryId");
                        }
                        outerChannelCategoryMappingDb.setCategoryName(categoryName);
                        outerChannelCategoryMappingDb.setFirstCategoryId(firstCId);
                        outerChannelCategoryMappingDb.setSecondCategoryId(secondCId);
                        outerChannelCategoryMappingDb.setCategoryId(thirdCId);
                    }
                }
            }
            dataGrid.setRows(outerChannelCategoryMappings);
            dataGrid.setTotal(count);
            executeResult.setResult(dataGrid);
        } catch (Exception e) {
            logger.error("Method [queryOuterChannelCategoryMappingList] error::", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 保存、更新外接商品类目映射关系
     *
     * @param outerChannelCategoryMapping 参数
     * @return 成功结果，成功返回主键
     */
    @Override
    public ExecuteResult<String> addOrModifyOuterChannelCategoryMapping(OuterChannelCategoryMapping outerChannelCategoryMapping) {
        logger.info("Method [addOrModifyOuterChannelCategoryMapping] Entrance, [outerChannelCategoryMapping] : {}", outerChannelCategoryMapping);
        // 结果
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            // 校验
            if (outerChannelCategoryMapping == null) {
                logger.info("outerChannelCategoryMapping is null");
                executeResult.addErrorMessage("outerChannelCategoryMapping is null");
                return executeResult;
            }
            if (outerChannelCategoryMapping.getCategoryMappingId() == null || outerChannelCategoryMapping.getCategoryMappingId() <= 0) {
                logger.info("categoryMappingId is empty");
                executeResult.addErrorMessage("categoryMappingId is empty");
                return executeResult;
            }
            if (outerChannelCategoryMapping.getCategoryId() == null || outerChannelCategoryMapping.getCategoryId() <= 0) {
                logger.info("categoryId is empty");
                executeResult.addErrorMessage("categoryId is empty");
                return executeResult;
            }
            if (outerChannelCategoryMapping.getModifyId() == null) {
                executeResult.addErrorMessage("modifyId is null");
                return executeResult;
            }
            if (StringUtils.isEmpty(outerChannelCategoryMapping.getModifyName())) {
                executeResult.addErrorMessage("modifyName is null");
                return executeResult;
            }
            // 更新映射关系
            this.outerChannelCategoryMappingMapper.updateByPrimaryKeySelective(outerChannelCategoryMapping);
            /** 后置业务 **/
            // 1. 更新item 的 cid 。
            // 2. 如果item状态是待映射，且item上brandId已经映射，则更新item_status 为 未上架
            // 3. 如果item状态是待映射，且item上brandId已经映射，则建立品牌和品类的关系
            OuterChannelCategoryMapping outerChannelCategoryMappingDb = this.outerChannelCategoryMappingMapper.
                selectByPrimaryKey(outerChannelCategoryMapping.getCategoryMappingId());
            this.afterAddOrModifyOuterChannelCategoryMapping(outerChannelCategoryMappingDb);
        } catch (Exception e) {
            logger.error("Method [addOrModifyOuterChannelCategoryMapping] error::", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 1. 更新item 的 cid 。
     * 2. 如果item状态是待映射，且item上brandId已经映射，则更新item_status 为 未上架
     * 3. 如果item状态是待映射，且item上brandId已经映射，则建立品牌和品类的关系
     * @param outerChannelCategoryMapping 参数
     */
    private void afterAddOrModifyOuterChannelCategoryMapping(OuterChannelCategoryMapping outerChannelCategoryMapping) {
        // 确定已经映射成功
        if (outerChannelCategoryMapping.getCategoryId() != null && outerChannelCategoryMapping.getCategoryId() > 0) {
            String outChannelCategoryCode = outerChannelCategoryMapping.getOuterChannelCategoryCode(); // 外接渠道编码
            Long categoryId = outerChannelCategoryMapping.getCategoryId(); // 内部渠道类目
            /** 1 根据外接渠道编码 更新商品的类目 **/
            //  根据外接渠道编码查询商品
            Item itemParam = new Item(); itemParam.setOuterChannelCategoryCode(outChannelCategoryCode);
            List<Item> itemList = this.itemMybatisDAO.queryList(itemParam, null);
            //  更新item中的cid
            for (Item item : itemList) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setItemId(item.getItemId());
                itemDTO.setCid(categoryId);
                /** 2 如果item状态是待映射，且item上brand已经映射，则更新item_status 为 未上架 **/
                if (item.getItemStatus().equals(HtdItemStatusEnum.ERP_STOCKPRICE_OR_OUTPRODUCTPRICE.getCode())) { // 如果外接商品状态是待映射状态
                    if (item.getBrand() != null && item.getBrand() > 0) { // 发现品牌已经映射
                        itemDTO.setItemStatus(HtdItemStatusEnum.NOT_SHELVES.getCode()); // 设置为未上架
                        /** 3 立品牌和品类的关系 **/
                        ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
                        itemBrandDTO.setThirdLevCid(categoryId);
                        itemBrandDTO.setBrandIds(new Long[]{item.getBrand()});
                        itemBrandDTO.setCreateId(outerChannelCategoryMapping.getModifyId());
                        itemBrandDTO.setCreateName(outerChannelCategoryMapping.getModifyName());
                        ExecuteResult executeResult = this.itemBrandExportService.addCategoryBrandBatch(itemBrandDTO);
                        if (!executeResult.isSuccess()) {
                            throw new RuntimeException("addCategoryBrandBatch error.");
                        }
                    }
                }
                // 更新item
                this.itemMybatisDAO.updateItem(itemDTO);
            }
        }
    }

    /**
     * 查询外接商品渠道品牌已映射关系
     *
     * @param outerChannelBrandMapping 参数
     * @param pager                    分页信息
     * @return
     */
    @Override
    public ExecuteResult<DataGrid<OuterChannelBrandMapping>> queryMappedOuterChannelBrandList(OuterChannelBrandMapping outerChannelBrandMapping, Pager pager) {
        logger.info("Method [queryMappedOuterChannelBrandList] Entrance, [outerChannelBrandMapping] : {}, [pager] : {}", outerChannelBrandMapping, pager);
        ExecuteResult<DataGrid<OuterChannelBrandMapping>> executeResult = new ExecuteResult<>();
        if (outerChannelBrandMapping == null) {
            logger.info("Method [queryMappedOuterChannelBrandList] validate failure, outerChannelBrandMapping is null");
            executeResult.addErrorMessage("outerChannelCategoryMapping is null");
            return executeResult;
        }
        if (StringUtils.isEmpty(outerChannelBrandMapping.getChannelCode())) { // 渠道编码必填
            logger.info("Method [queryMappedOuterChannelBrandList] validate failure, channelCode is empty");
            executeResult.addErrorMessage("channelCode is empty");
            return executeResult;
        }
        return this.queryOuterChannelBrandMappingList(outerChannelBrandMapping, pager, true);
    }

    /**
     * 查询外接商品渠道品牌未映射关系
     *
     * @param outerChannelBrandMapping 参数
     * @param pager                    分页信息
     * @return
     */
    @Override
    public ExecuteResult<DataGrid<OuterChannelBrandMapping>> queryNoMappedOuterChannelBrandList(OuterChannelBrandMapping outerChannelBrandMapping, Pager pager) {
        logger.info("Method [queryNoMappedOuterChannelBrandList] Entrance, [outerChannelBrandMapping] : {}, [pager] : {}", outerChannelBrandMapping, pager);
        ExecuteResult<DataGrid<OuterChannelBrandMapping>> executeResult = new ExecuteResult<>();
        if (outerChannelBrandMapping == null) {
            logger.info("Method [queryNoMappedOuterChannelBrandList] validate failure, outerChannelBrandMapping is null");
            executeResult.addErrorMessage("outerChannelCategoryMapping is null");
            return executeResult;
        }
        if (StringUtils.isEmpty(outerChannelBrandMapping.getChannelCode())) { // 渠道编码必填
            logger.info("Method [queryNoMappedOuterChannelBrandList] validate failure, channelCode is empty");
            executeResult.addErrorMessage("channelCode is empty");
            return executeResult;
        }
        return this.queryOuterChannelBrandMappingList(outerChannelBrandMapping, pager, false);
    }

    /**
     * 查询外接商品渠道品牌未映射关系
     *
     * @param outerChannelBrandMapping 参数
     * @param pager                    分页信息
     * @param isMapped                 是否已映射
     * @return
     */
    @Override
    public ExecuteResult<DataGrid<OuterChannelBrandMapping>> queryOuterChannelBrandMappingList(OuterChannelBrandMapping outerChannelBrandMapping, Pager pager, boolean isMapped) {
        ExecuteResult<DataGrid<OuterChannelBrandMapping>> executeResult = new ExecuteResult<>();
        DataGrid<OuterChannelBrandMapping> dataGrid = new DataGrid<>();
        try {
            /** 总条数 **/
            Long count = this.outerChannelBrandMappingMapper.selectOuterChannelBrandMappingListCount(outerChannelBrandMapping, isMapped);
            /** 当前分页 **/
            List<OuterChannelBrandMapping> outerChannelBrandMappings = new ArrayList<>();
            if (count > 0) { // 如果有记录查询记录
                // pager如果为null，查询全部记录
                outerChannelBrandMappings = outerChannelBrandMappingMapper.selectOuterBrandCategoryMappingList(outerChannelBrandMapping, pager, isMapped);
            }
            dataGrid.setRows(outerChannelBrandMappings);
            dataGrid.setTotal(count);
            executeResult.setResult(dataGrid);
        } catch (Exception e) {
            logger.error("Method [queryOuterChannelCategoryMappingList] error::", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 保存、更新外接商品品牌映射关系
     *
     * @param outerChannelBrandMapping 参数
     * @return 成功结果，成功返回主键
     */
    @Override
    public ExecuteResult<String> addOrModifyOuterChannelBrandMapping(OuterChannelBrandMapping outerChannelBrandMapping) {
        logger.info("Method [addOrModifyOuterChannelBrandMapping] Entrance, [outerChannelBrandMapping] : {}", outerChannelBrandMapping);
        // 结果
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            // 校验
            if (outerChannelBrandMapping == null) {
                executeResult.addErrorMessage("outerChannelCategoryMapping is null");
                return executeResult;
            }
            if (outerChannelBrandMapping.getBrandMappingId() == null || outerChannelBrandMapping.getBrandMappingId() <= 0) {
                executeResult.addErrorMessage("brandMappingId is empty");
                return executeResult;
            }
            if (outerChannelBrandMapping.getBrandId() == null || outerChannelBrandMapping.getBrandId() <= 0) {
                executeResult.addErrorMessage("brandId is empty");
                return executeResult;
            }
            if (outerChannelBrandMapping.getModifyId() == null) {
                executeResult.addErrorMessage("modifyId is null");
                return executeResult;
            }
            if (StringUtils.isEmpty(outerChannelBrandMapping.getModifyName())) {
                executeResult.addErrorMessage("modifyId is empty");
                return executeResult;
            }
            // modify
            this.outerChannelBrandMappingMapper.updateByPrimaryKeySelective(outerChannelBrandMapping);
            /** 后置业务 **/
            // 1. 更新item 的 cid 。
            // 2. 如果item状态是待映射，且item上brandId已经映射，则更新item_status 为 未上架
            // 3. 如果item状态是待映射，且item上brandId已经映射，则建立品牌和品类的关系
            OuterChannelBrandMapping outerChannelBrandMappingDb = this.outerChannelBrandMappingMapper.
                selectByPrimaryKey(outerChannelBrandMapping.getBrandMappingId());
            this.afterAddOrModifyOuterChannelBrandMapping(outerChannelBrandMappingDb);
        } catch (Exception e) {
            logger.error("Method [addOrModifyOuterChannelBrandMapping] error::", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 1. 更新item 的 brand 。
     * 2. 如果item状态是待映射，且item上cid已经映射，则更新item_status 为 未上架
     * 3. 如果item状态是待映射，且item上cid已经映射，则建立品牌和品类的关系
     * @param outerChannelBrandMapping 参数
     */
    private void afterAddOrModifyOuterChannelBrandMapping(OuterChannelBrandMapping outerChannelBrandMapping) {
        // 确定已经映射成功
        if (outerChannelBrandMapping.getBrandId() != null && outerChannelBrandMapping.getBrandId() > 0) {
            String outChannelBrandCode = outerChannelBrandMapping.getOuterChannelBrandCode(); // 外接渠道编码
            Long brandId = outerChannelBrandMapping.getBrandId(); // 内部渠道品牌
            /** 1 根据外接渠道品牌编码 更新商品的品牌 **/
            //  根据外接渠道编码查询商品
            Item itemParam = new Item(); itemParam.setOuterChannelBrandCode(outChannelBrandCode);
            List<Item> itemList = this.itemMybatisDAO.queryList(itemParam, null);
            //  更新item中的cid
            for (Item item : itemList) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setItemId(item.getItemId());
                itemDTO.setBrand(brandId);
                /** 2 如果item状态是待映射，且item上brand已经映射，则更新item_status 为 未上架 **/
                if (item.getItemStatus().equals(HtdItemStatusEnum.ERP_STOCKPRICE_OR_OUTPRODUCTPRICE.getCode())) { // 如果外接商品状态是待映射状态
                    if (item.getCid() != null && item.getCid() > 0) { // 发现品牌已经映射
                        itemDTO.setItemStatus(HtdItemStatusEnum.NOT_SHELVES.getCode()); // 设置为未上架
                        /** 3 立品牌和品类的关系 **/
                        ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
                        itemBrandDTO.setThirdLevCid(item.getCid());
                        itemBrandDTO.setBrandIds(new Long[]{brandId});
                        itemBrandDTO.setCreateId(outerChannelBrandMapping.getModifyId());
                        itemBrandDTO.setCreateName(outerChannelBrandMapping.getModifyName());
                        ExecuteResult executeResult = this.itemBrandExportService.addCategoryBrandBatch(itemBrandDTO);
                        if (!executeResult.isSuccess()) {
                            throw new RuntimeException("addCategoryBrandBatch error.");
                        }
                    }
                }
                // 更新item
                this.itemMybatisDAO.updateItem(itemDTO);
            }
        }
    }

    /**
     * 查询商家接入外接商品渠道列表 for 运营中心查询商家接入
     *
     * @param sellerOuterProductChannelDTO 参数
     * @param pager                        分页信息
     */
    @Override
    public ExecuteResult<DataGrid<SellerOuterProductChannelDTO>> querySellerOuterProductChannelList(SellerOuterProductChannelDTO sellerOuterProductChannelDTO, Pager pager) {
        logger.info("Method [queryOuterProductChannelList] Entrance, [sellerOuterProductChannelDTO] : {}, [pager] : {}", sellerOuterProductChannelDTO, pager);
        ExecuteResult<DataGrid<SellerOuterProductChannelDTO>> executeResult = new ExecuteResult<>();
        DataGrid<SellerOuterProductChannelDTO> dtoDataGrid = new DataGrid<>();
        if (sellerOuterProductChannelDTO == null) {
            logger.info("sellerOuterProductChannelDTO is null");
            executeResult.addErrorMessage("sellerOuterProductChannelDTO is null");
            return executeResult;
        }
        try {
            SellerOuterProductChannel sellerOuterProductChannelParam = new SellerOuterProductChannel();
            sellerOuterProductChannelParam.setSellerId(sellerOuterProductChannelDTO.getSellerId()); // 卖家id
            sellerOuterProductChannelParam.setChannelCode(sellerOuterProductChannelDTO.getChannelCode()); //  渠道编码
            sellerOuterProductChannelParam.setAccessStatus(sellerOuterProductChannelDTO.getAccessStatus());
            // 总数
            Long count = this.sellerOuterProductChannelMapper.selectSellerOuterProductChannelListCount(sellerOuterProductChannelParam);
            List<SellerOuterProductChannel> sellerOuterProductChannels = new ArrayList();
            if (count > 0) {
                // 查询出按照卖家分组的所有卖家id
                sellerOuterProductChannels = this.sellerOuterProductChannelMapper.selectSellerOuterProductChannelList(sellerOuterProductChannelParam, pager);
            }
            // 组装DTO
            List<SellerOuterProductChannelDTO> sellerOuterProductChannelDTOResults = new ArrayList<>();
            for (SellerOuterProductChannel sellerOuterProductChannelDb : sellerOuterProductChannels) { // 循环卖家
                // 结果对象
                SellerOuterProductChannelDTO sellerOuterProductChannelDTOResult = new SellerOuterProductChannelDTO();
                BeanUtils.copyProperties(sellerOuterProductChannelDb, sellerOuterProductChannelDTOResult);
                // 根据sellerId查询已接入渠道的记录
                List<SellerOuterProductChannel> sellerOuterProductChannelList = this.sellerOuterProductChannelMapper.selectAccessedListBySellerId(sellerOuterProductChannelDb.getSellerId());
                String channelName = this.getChannelNames(sellerOuterProductChannelList).get("channelName");
                String channelCodes = this.getChannelNames(sellerOuterProductChannelList).get("channelNames");
                sellerOuterProductChannelDTOResult.setChannelName(channelName);
                sellerOuterProductChannelDTOResult.setChannelCodes(channelCodes);
                // 添加到集合
                sellerOuterProductChannelDTOResults.add(sellerOuterProductChannelDTOResult);
            }
            dtoDataGrid.setTotal(count);
            dtoDataGrid.setRows(sellerOuterProductChannelDTOResults);
            executeResult.setResult(dtoDataGrid);
        } catch (Exception e) {
            logger.error("Method [queryOuterProductChannelList] error::", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 拼接渠道名称。 如果未接入则显示未接入任务平台
     * @param sellerOuterProductChannelList
     * @return
     */
    private Map<String, String> getChannelNames(List<SellerOuterProductChannel> sellerOuterProductChannelList) {
        Map<String, String> map = new HashMap();
        String channelName = "";
        String channelNames = "";
        if (sellerOuterProductChannelList.size() == 0) { // 没有已接入的渠道
            channelName = "未接入任何平台";
            channelNames = "0";
        } else { // 存在已接入的渠道
            for (SellerOuterProductChannel sellerOuterProductChannel : sellerOuterProductChannelList) {
                ProductChannel productChannel = this.productChannelMapper.selectProductChannelByCode(sellerOuterProductChannel.getChannelCode());
                if (productChannel != null) {
                    channelName += productChannel.getChannelName() + ",";
                    channelNames += productChannel.getChannelCode() + ",";
                }
            }
            if (!StringUtils.isEmpty(channelName)) {
                channelName = channelName.substring(0, channelName.length() - 1);
            }
            if (!StringUtils.isEmpty(channelNames)) {
                channelNames = channelNames.substring(0, channelNames.length() - 1);
            }
        }
        map.put("channelName", channelName);
        map.put("channelNames", channelNames);
        return map;
    }

    /**
     * 查询商家外接渠道接入详情
     *
     * @param sellerOuterProductChannelDTOParam 参数
     * @retrun 商家外接渠道接入详情
     */
    @Override
    public ExecuteResult<SellerOuterProductChannelDTO> querySellerOuterProductChannelDetail(SellerOuterProductChannelDTO sellerOuterProductChannelDTOParam) {
        logger.info("Method [querySellerOuterProductChannelDetail] Entrance, sellerOuterProductChannelDTOParam : {}", sellerOuterProductChannelDTOParam);
        ExecuteResult<SellerOuterProductChannelDTO> executeResult = new ExecuteResult<>();
        if (sellerOuterProductChannelDTOParam == null) {
            executeResult.addErrorMessage("sellerOuterProductChannelDTOParam is null");
            return executeResult;
        }
        if (sellerOuterProductChannelDTOParam.getSellerId() == null) {
            executeResult.addErrorMessage("sellerId is null");
            return executeResult;
        }
        try {
            SellerOuterProductChannelDTO sellerOuterProductChannelDTO = new SellerOuterProductChannelDTO();
            List<SellerOuterProductChannel> sellerOuterProductChannelListAll = this.sellerOuterProductChannelMapper.selectBySellerId(sellerOuterProductChannelDTOParam.getSellerId());
            for (SellerOuterProductChannel sellerOuterProductChannel : sellerOuterProductChannelListAll) {
                BeanUtils.copyProperties(sellerOuterProductChannel, sellerOuterProductChannelDTO);
            }
            // 根据主键查询商家接入渠道详情
            List<SellerOuterProductChannel> sellerOuterProductChannelList = this.sellerOuterProductChannelMapper.selectAccessedListBySellerId(sellerOuterProductChannelDTOParam.getSellerId());
            String channelName = this.getChannelNames(sellerOuterProductChannelList).get("channelName");
            String channelNames = this.getChannelNames(sellerOuterProductChannelList).get("channelNames");;
            sellerOuterProductChannelDTO.setChannelCodes(channelNames);
            sellerOuterProductChannelDTO.setChannelCode(null);
            sellerOuterProductChannelDTO.setChannelName(channelName);
            executeResult.setResult(sellerOuterProductChannelDTO);
        } catch (Exception e) {
            logger.error("querySellerOuterProductChannelDetail error::", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }


    /**
     * 商家接入 - 外部平台接入管理
     */
    @Override
    public ExecuteResult<String> addSellerOuterProductChannel(SellerOuterProductChannelDTO sellerOuterProductChannelDTO) {
        logger.info("Method [addSellerOuterProductChannel] Entrance, sellerOuterProductChannelDTO : {}", sellerOuterProductChannelDTO);
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            // 检验入参
            if (sellerOuterProductChannelDTO == null) {
                executeResult.addErrorMessage("入参为空");
                return executeResult;
            }
            // 检验具体参数
            if (sellerOuterProductChannelDTO.getSellerId() == null || sellerOuterProductChannelDTO.getSellerId() < 0) {
                executeResult.addErrorMessage("sellerId is empty");
                return executeResult;
            }
            if (sellerOuterProductChannelDTO.getModifyId() == null) {
                executeResult.addErrorMessage("modifyId is null");
                return executeResult;
            }
            if (sellerOuterProductChannelDTO.getModifyName() == null) {
                executeResult.addErrorMessage("modifyName is null");
                return executeResult;
            }
            if (sellerOuterProductChannelDTO.getChannelCodes() == null) {
                executeResult.addErrorMessage("channelCodes is null");
                return executeResult;
            }
            // 1.上次所有接入的渠道
            Long sellerId = sellerOuterProductChannelDTO.getSellerId();
            // 过滤0801大B，不给接入商品加
            Long sellerId_0801 = this.get0801SellerId();
            if (sellerId_0801.equals(sellerId)) {
                // 如果是0801要接入
                logger.info("拦截0801接入商品加");
                executeResult.setCode(ResultCodeEnum.ERROR.getCode());
                executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
                executeResult.addErrorMsg("0801不能接入商品加");
                return executeResult;
            }
            List<SellerOuterProductChannel> list = this.sellerOuterProductChannelMapper.selectBySellerId(sellerId);
            // 2.此次要接入的渠道
            String channelCodes = sellerOuterProductChannelDTO.getChannelCodes();
            String[] channelCodeArr = channelCodes.split(",");
            if (channelCodeArr.length == 1 && channelCodeArr[0].equals("0")) { // 如果全部取消勾选
                for (SellerOuterProductChannel sellerOuterProductChannel : list) {
                    sellerOuterProductChannel.setAccessStatus(0);
                    this.sellerOuterProductChannelMapper.updateByPrimaryKeySelective(sellerOuterProductChannel);
                }
            } else { // 否则其他情况，有断开，有接入的情况
                // 1. 全部断开
                for (SellerOuterProductChannel sellerOuterProductChannel : list) {
                    sellerOuterProductChannel.setAccessStatus(0);
                    this.sellerOuterProductChannelMapper.updateByPrimaryKeySelective(sellerOuterProductChannel);
                }
                // 2. 按照此次接入
                for (String c : channelCodeArr) {
                    // 是否存在
                    SellerOuterProductChannel sellerOuterProductChannelParam = new SellerOuterProductChannel();
                    sellerOuterProductChannelParam.setSellerId(sellerId);
                    sellerOuterProductChannelParam.setChannelCode(c);
                    SellerOuterProductChannel sellerOuterProductChannel = this.sellerOuterProductChannelMapper.selectBySellerIdAndChannelCode(sellerOuterProductChannelParam);
                    if (sellerOuterProductChannel != null) {
                        sellerOuterProductChannel.setAccessStatus(1);
                        this.sellerOuterProductChannelMapper.updateByPrimaryKeySelective(sellerOuterProductChannel);
                    } else {
                        SellerOuterProductChannel sellerOuterProductChannelParam2 = new SellerOuterProductChannel();
                        sellerOuterProductChannelParam2.setSellerId(sellerId);
                        sellerOuterProductChannelParam2.setChannelCode(c);
                        sellerOuterProductChannelParam2.setAccessStatus(1);
                        sellerOuterProductChannelParam2.setCreateId(sellerOuterProductChannelDTO.getModifyId());
                        sellerOuterProductChannelParam2.setCreateName(sellerOuterProductChannelDTO.getModifyName());
                        this.sellerOuterProductChannelMapper.insert(sellerOuterProductChannelParam2);
                    }
                }
            }
        } catch (Exception e) {
            logger.info("Method [addSellerOuterProductChannel] error::", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 商家接入 - 批量操作 批量导入
     */
    @Override
    public ExecuteResult<SellerOuterProductChannelImportResultDTO> batchAddSellerOuterProductChannel(List<SellerOuterProductChannelDTO> sellerOuterProductChannelDTOList) {
        logger.info("Method [batchAddSellerOuterProductChannel] Entrance, sellerOuterProductChannels : {}", sellerOuterProductChannelDTOList);
        ExecuteResult<SellerOuterProductChannelImportResultDTO> executeResult = new ExecuteResult<>();
        SellerOuterProductChannelImportResultDTO sellerOuterProductChannelImportResultDTO = new SellerOuterProductChannelImportResultDTO();
        // 检验入参
        if (sellerOuterProductChannelDTOList == null) {
            executeResult.addErrorMessage("sellerOuterProductChannelDTOList is null");
            return executeResult;
        }
        // 检验具体参数
        int index = 1;
        List<SellerOuterProductChannelDTO> failureList = new ArrayList();
        for (SellerOuterProductChannelDTO sellerOuterProductChannelDTO : sellerOuterProductChannelDTOList) {
            try {
                Long sellerId = sellerOuterProductChannelDTO.getSellerId();
                // 过滤0801大B，不给接入商品加
                Long sellerId_0801 = this.get0801SellerId();
                if (sellerId_0801.equals(sellerId)) {
                    // 如果是0801要接入
                    logger.error("row {} data is illegal : 0801不能接入商品加", index);
                    failureList.add(sellerOuterProductChannelDTO);
                    index++;
                    continue;
                }
                if (sellerOuterProductChannelDTO.getSellerId() == null
                        || StringUtils.isEmpty(sellerOuterProductChannelDTO.getChannelName())
                        || sellerOuterProductChannelDTO.getCreateId() == null
                        || StringUtils.isEmpty(sellerOuterProductChannelDTO.getCreateName())
                        || sellerOuterProductChannelDTO.getAccessStatus() == null) {
                    logger.error("row {} data is illegal : {}", index, sellerOuterProductChannelDTO);
                    failureList.add(sellerOuterProductChannelDTO);
                    index++;
                    continue;
                }
                String channelName = sellerOuterProductChannelDTO.getChannelName();
                Long createId = sellerOuterProductChannelDTO.getCreateId();
                String createName = sellerOuterProductChannelDTO.getCreateName();
                Integer accessStatus = sellerOuterProductChannelDTO.getAccessStatus();
                // 根据channelName查询channelCode
                String channelCode = this.productChannelMapper.queryChannelCodeByName(channelName);
                if (StringUtils.isEmpty(channelCode)) {
                    logger.error("row {} channelName is illegal : {}", index, channelName);
                    failureList.add(sellerOuterProductChannelDTO);
                    index++;
                    continue;
                }
                // 查看是否已经存入该条数据， 根据code和卖家id查询
                SellerOuterProductChannel sellerOuterProductChannelParam = new SellerOuterProductChannel();
                sellerOuterProductChannelParam.setSellerId(sellerId);
                sellerOuterProductChannelParam.setChannelCode(channelCode);
                SellerOuterProductChannel sellerOuterProductChannelDB = this.sellerOuterProductChannelMapper.selectBySellerIdAndChannelCode(sellerOuterProductChannelParam);
                if (sellerOuterProductChannelDB != null) { // 之前导入过该数据
                    sellerOuterProductChannelDB.setAccessStatus(accessStatus);
                    this.sellerOuterProductChannelMapper.updateByPrimaryKeySelective(sellerOuterProductChannelDB);
                } else {
                    SellerOuterProductChannel sellerOuterProductChannelParam2 = new SellerOuterProductChannel();
                    sellerOuterProductChannelParam2.setSellerId(sellerId);
                    sellerOuterProductChannelParam2.setChannelCode(channelCode);
                    sellerOuterProductChannelParam2.setCreateId(createId);
                    sellerOuterProductChannelParam2.setCreateName(createName);
                    sellerOuterProductChannelParam2.setAccessStatus(accessStatus);
                    this.sellerOuterProductChannelMapper.insert(sellerOuterProductChannelParam2);
                }
            } catch (Exception e) {
                logger.error("Method [batchAddSellerOuterProductChannel] error::", e);
                failureList.add(sellerOuterProductChannelDTO);
            }
            index++;
        }
        sellerOuterProductChannelImportResultDTO.setTotal(sellerOuterProductChannelDTOList.size());
        sellerOuterProductChannelImportResultDTO.setSuccess(sellerOuterProductChannelDTOList.size() - failureList.size());
        sellerOuterProductChannelImportResultDTO.setFail(failureList.size());
        sellerOuterProductChannelImportResultDTO.setFailureList(failureList);
        executeResult.setResult(sellerOuterProductChannelImportResultDTO);
        return executeResult;
    }

    private Long get0801SellerId() {
        Long sellerId_0801 = null;
        ExecuteResult<List<MemberBaseInfoDTO>> listExecuteResult = this.memberBaseInfoService.getMemberInfoByCompanyCode("0801", "2");
        if (listExecuteResult.isSuccess()) {
            List<MemberBaseInfoDTO> memberBaseInfoDTOList = listExecuteResult.getResult();
            if (memberBaseInfoDTOList != null && memberBaseInfoDTOList.size() > 0) {
                sellerId_0801 = memberBaseInfoDTOList.get(0).getId();
            }
        } else {
            throw new RuntimeException("getMemberInfoByCompanyCode error");
        }
        return sellerId_0801;
    }

    /**
     * 查询外接商品单品列表 (我司上下架状态，京东上下架状态)
     *
     * @param productPlusItemDTO 参数
     * @param pager              分页参数
     * @return
     */
    @Override
    public ExecuteResult<DataGrid<ProductPlusItemDTO>> queryProductPlushItemList(ProductPlusItemDTO productPlusItemDTO, Pager pager) {
        logger.info("Method [queryProductPlushItemList] Entrance, productPlusItemDTO : {}", productPlusItemDTO);
        ExecuteResult<DataGrid<ProductPlusItemDTO>> executeResult = new ExecuteResult<>();
        DataGrid<ProductPlusItemDTO> dtoDataGrid = new DataGrid<>();
        if (productPlusItemDTO == null) { // 参数为空
            executeResult.addErrorMessage("productPlusItemDTO is null");
            return executeResult;
        }
        if (StringUtils.isEmpty(productPlusItemDTO.getProductChannelCode())) { // 渠道编码必传
            executeResult.addErrorMessage("productChannelCode is empty");
            return executeResult;
        }
        try {
            // 设置类目参数
            Long firstCategoryId = productPlusItemDTO.getFirstCategoryId();
            Long secondCategoryId = productPlusItemDTO.getSecondCategoryId();
            Long thirdCategoryId = productPlusItemDTO.getCid();
            Long[] categoryParam = this.itemCategoryService.getAllThirdCategoryByCategoryId(firstCategoryId, secondCategoryId, thirdCategoryId);
            productPlusItemDTO.setCategoryParam(categoryParam);
            // 状态为上下架
            productPlusItemDTO.setItemStatusStr(HtdItemStatusEnum.NOT_SHELVES.getCode() + "," + HtdItemStatusEnum.SHELVED.getCode());
            // 数量
            Long count = this.productPlusItemMapper.selectProductPlusItemListCount(productPlusItemDTO);
            List<ProductPlusItemDTO> productPlusItemDTOs = new ArrayList<>();
            if (count > 0) {
                // 结果集
                productPlusItemDTOs = this.productPlusItemMapper.selectProductPlusItemList(productPlusItemDTO, pager);
            }
            // 循环调用价格中心获取价格信息
            for (ProductPlusItemDTO productPlusItemDTO1 : productPlusItemDTOs) {
                // set price
                this.queryProductPlusPrice(productPlusItemDTO1.getSkuId(), productPlusItemDTO1);
                // set category
                ExecuteResult<Map<String, Object>> executeResultCategory = this.itemCategoryService.queryItemOneTwoThreeCategoryName(productPlusItemDTO1.getCid(), ">>");
                if (executeResultCategory.isSuccess()) {
                    productPlusItemDTO1.setCategoryName((String) executeResultCategory.getResult().get("categoryName"));
                }
            }
            dtoDataGrid.setRows(productPlusItemDTOs);
            dtoDataGrid.setTotal(count);
            executeResult.setResult(dtoDataGrid);
        } catch (Exception e) {
            logger.error("Method [queryProductPlushItemList] Error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 查询外接商品单品详情
     *
     * @param skuId 参数
     * @return 外接商品单品详情
     */
    @Override
    public ExecuteResult<ProductPlusItemDTO> queryProductPlushItemDetail(Long skuId) {
        logger.info("Method [queryProductPlushItemDetail] Entrance, skuId : {}", skuId);
        ExecuteResult<ProductPlusItemDTO> executeResult = new ExecuteResult<>();
        if (skuId == null || skuId <= 0) {
            executeResult.addErrorMessage("skuId is illegal");
            return executeResult;
        }
        try {
            ProductPlusItemDTO productPlusItemDTO = this.productPlusItemMapper.selectProductPlusItem(skuId);
            // set price
            this.queryProductPlusPrice(skuId, productPlusItemDTO);
            // set category
            ExecuteResult<Map<String, Object>> executeResultCategory = this.itemCategoryService.queryItemOneTwoThreeCategoryName(productPlusItemDTO.getCid(), ">>");
            if (executeResultCategory.isSuccess()) {
                productPlusItemDTO.setCategoryName((String) executeResultCategory.getResult().get("categoryName"));
            }
            executeResult.setResult(productPlusItemDTO);
        } catch (Exception e) {
            logger.error("Error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 上架外接渠道商品
     *
     * @param productPlusItemDTO 参数
     */
    @Override
    public ExecuteResult<String> upShelfProductPlushItem(ProductPlusItemDTO productPlusItemDTO) {
        // 参数校验 必填项
        logger.info("Method [upShelfProductPlushItem] Entrance, productPlusItemDTO : {}", productPlusItemDTO);
        ExecuteResult<String> executeResult = new ExecuteResult();
        // 参数校验 必填项.   价格浮动比例 商城销售价  零售价浮动比例 总部佣金比例 必填
        ValidateResult validateResult = DTOValidateUtil.validate(productPlusItemDTO);
        if (!validateResult.isPass()) {
            executeResult.addErrorMessage(validateResult.getMessage());
            return executeResult;
        }
        try {
            Long itemId = productPlusItemDTO.getItemId();
            Item item = this.itemMybatisDAO.queryItemByPk(itemId);
            String outerItemStatus = item.getOuterItemStatus();
            if (OutItemStatusEnum.NOT_SHELVES.getCode().equals(outerItemStatus)) {
                executeResult.addErrorMessage("外部平台商品状态是下架状态");
                return executeResult;
            }
            // 修改价格
            this.modifyProductPlushItemPrice(productPlusItemDTO);
            // 修改预售信息&item状态为上架
            productPlusItemDTO.setItemStatus(HtdItemStatusEnum.SHELVED.getCode());
            productPlusItemDTO.setUpdateShelfTimeFlag(1);//为0时更新下架时间，为1时修改上架时间
            this.modifyProductPlushItem(productPlusItemDTO);
        } catch (Exception e) {
            logger.error("Error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 下架外接渠道商品
     * （注：下架只修改下架状态）
     *
     * @param itemId 参数itemId
     */
    @Override
    public ExecuteResult<String> downShelfProductPlushItem(Long itemId) {
        logger.info("Method [downShelfProductPlushItem] Entrance, itemId : {}", itemId);
        ExecuteResult<String> executeResult = new ExecuteResult();
        // 参数校验 必填项
        if (itemId == null || itemId <= 0) {
            executeResult.addErrorMessage("skuId is illegal");
            return executeResult;
        }
        // 修改item状态为下架
        try {
            ProductPlusItemDTO productPlusItemDTO = new ProductPlusItemDTO();
            productPlusItemDTO.setItemId(itemId);
            productPlusItemDTO.setItemStatus(HtdItemStatusEnum.NOT_SHELVES.getCode());
            productPlusItemDTO.setUpdateShelfTimeFlag(0);//为0时更新下架时间，为1时修改上架时间
            this.modifyProductPlushItem(productPlusItemDTO);
        } catch (Exception e) {
            logger.error("Error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 修改外接渠道商品价格、浮动比例及预售信息。
     * （在上架状态下，修改）
     *
     * @param productPlusItemDTO 参数
     */
    @Override
    public ExecuteResult<String> modifyProductPlushItemPriceAndPreSaleInfo(ProductPlusItemDTO productPlusItemDTO) {
        logger.info("Method [modifyProductPlushItemPriceAndPreSaleInfo] Entrance, productPlusItemDTO : {}", productPlusItemDTO);
        ExecuteResult<String> executeResult = new ExecuteResult();
        // 参数校验 必填项.   价格浮动比例 商城销售价  零售价浮动比例 总部佣金比例 必填
        ValidateResult validateResult = DTOValidateUtil.validate(productPlusItemDTO);
        if (!validateResult.isPass()) {
            executeResult.addErrorMessage(validateResult.getMessage());
            return executeResult;
        }
        // 修改预售信息和价格
        try {
            // 修改广告和预售信息
            this.modifyProductPlushItem(productPlusItemDTO);
            // 修改基本价格
            this.modifyProductPlushItemPrice(productPlusItemDTO);
        } catch (Exception e) {
            logger.error("Error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 批量上架
     *
     * @param productPlusItemDTOs 参数
     * @return 批量上架结果
     */
    @Transactional
    @Override
    public ExecuteResult<String> batchUpShelfProductPlushItem(List<ProductPlusItemDTO> productPlusItemDTOs, ProductPlusItemUpShelfSettingDTO productPlusItemUpShelfSettingDTO) {
        logger.info("批量上架商品+商品, 商品信息: {}, 上架参数: {}", productPlusItemDTOs, productPlusItemUpShelfSettingDTO);
        ExecuteResult<String> executeResult = new ExecuteResult();
        if (productPlusItemDTOs == null || productPlusItemDTOs.size() <= 0 || productPlusItemUpShelfSettingDTO == null) {
            logger.info("param in null");
            executeResult.addErrorMessage("param in null or size is empty");
            return executeResult;
        }
        ValidateResult validateResult = DTOValidateUtil.validate(productPlusItemUpShelfSettingDTO);
        if (!validateResult.isPass()) {
            logger.info(validateResult.getMessage());
            executeResult.addErrorMessage(validateResult.getMessage());
            return executeResult;
        }
        try {
            // 根据比例计算 【商城销售价】、【商城零售价】
            BigDecimal priceFloatingRatio = productPlusItemUpShelfSettingDTO.getPriceFloatingRatio(); // 价格浮动比例
            BigDecimal retailPriceFloatingRatio = productPlusItemUpShelfSettingDTO.getRetailPriceFloatingRatio(); // 零售价格浮动比例
            BigDecimal commissionRatio = productPlusItemUpShelfSettingDTO.getCommissionRatio(); // 佣金比例
            String vipPriceFloatingRatio = productPlusItemUpShelfSettingDTO.getVipPriceFloatingRatio(); // vip价格浮动比例
            int i = 0;
            for (ProductPlusItemDTO productPlusItemDTO : productPlusItemDTOs) {
                Long skuId = productPlusItemDTO.getSkuId(); // skuId
                Long itemId = productPlusItemDTO.getItemId(); // itemId
                if (skuId == null || itemId == null) {
                    logger.error("skuId或者itemId是空, 第 {} 条", i);
                    executeResult.addErrorMessage("skuId或者itemId是空, 第" + i + "条");
                    return executeResult;
                }
                // 校验外部平台商品状态是下架状态的情况
                Item item = this.itemMybatisDAO.queryItemByPk(itemId);
                String outerItemStatus = item.getOuterItemStatus();
                if (OutItemStatusEnum.NOT_SHELVES.getCode().equals(outerItemStatus)) {
                    logger.error("该商品不能上架，外部平台商品状态是下架状态. item_id : {}, sku_id : {}", itemId, skuId);
                    executeResult.addErrorMessage("该商品不能上架，外部平台商品状态是下架状态. item_id : " + itemId + ", sku_id : " + skuId);
                    return executeResult;
                }
                // 查询外部供货价
                ExecuteResult<ItemSkuBasePriceDTO> itemSkuBasePriceDTOExecuteResult = this.itemSkuPriceService.queryItemSkuBasePrice(skuId);
                if (itemSkuBasePriceDTOExecuteResult.isSuccess()) {
                    ItemSkuBasePriceDTO itemSkuBasePriceDTO = itemSkuBasePriceDTOExecuteResult.getResult();
                    // 外部供货价
                    BigDecimal costPrice = itemSkuBasePriceDTO.getCostPrice();
                    if (costPrice == null || costPrice.compareTo(new BigDecimal(0)) <= 0) { // 如果外部供货价为null,或者价格小于等于0
                        throw new RuntimeException("外部供货价非法, costPrice : " + costPrice + ", skuId : " + skuId);
                    }
                    // 商城销售价
                    BigDecimal areaSalePrice = costPrice.multiply(new BigDecimal("1").add(priceFloatingRatio));  // 商城销售价 = 外部供货价 乘以 (1 + 价格浮动比例)
                    // 商城零售价
                    BigDecimal retailPrice = areaSalePrice.multiply(new BigDecimal("1").add(retailPriceFloatingRatio)); // 商城零售价 = 商城销售价 乘以 (1 + 价格浮动比例)
                    // VIP价格
                    BigDecimal vipPrice = null;
                    BigDecimal vipPriceFloatingRatioBigDecimal = null;
                    try {
                        vipPriceFloatingRatioBigDecimal = new BigDecimal(vipPriceFloatingRatio);
                    } catch (Exception e) {
                        vipPriceFloatingRatioBigDecimal = null;
                    }
                    if (vipPriceFloatingRatioBigDecimal != null) {
                        vipPrice = costPrice.multiply(new BigDecimal("1").add(vipPriceFloatingRatioBigDecimal));
                    }
                    // 更新价格
                    productPlusItemDTO.setAreaSalePrice(areaSalePrice); // 商城销售价
                    productPlusItemDTO.setPriceFloatingRatio(priceFloatingRatio); // 价格浮动比例
                    productPlusItemDTO.setRetailPrice(retailPrice); // 商城零售钱
                    productPlusItemDTO.setRetailPriceFloatingRatio(retailPriceFloatingRatio); // 零售价格浮动比例
                    productPlusItemDTO.setCommissionRatio(commissionRatio); // 佣金比例
                    productPlusItemDTO.setVipPriceFloatingRatio(vipPriceFloatingRatio);
                    productPlusItemDTO.setVipPrice(vipPrice);
                    this.modifyProductPlushItemPrice(productPlusItemDTO);
                    // 更新广告语预售信息及上架状态
                    productPlusItemDTO.setAd(productPlusItemUpShelfSettingDTO.getAd());
                    productPlusItemDTO.setIsPreSale(productPlusItemUpShelfSettingDTO.getIsPreSale());
                    productPlusItemDTO.setPreSaleStarttime(productPlusItemUpShelfSettingDTO.getPreSaleStarttime());
                    productPlusItemDTO.setPreSaleEndtime(productPlusItemUpShelfSettingDTO.getPreSaleEndtime());
                    productPlusItemDTO.setItemStatus(HtdItemStatusEnum.SHELVED.getCode());
                    productPlusItemDTO.setUpdateShelfTimeFlag(1);//为0时更新下架时间，为1时修改上架时间
                    this.modifyProductPlushItem(productPlusItemDTO);
                    i++;
                } else {
                    throw new RuntimeException("查询基本价格出错, 错误信息: " + itemSkuBasePriceDTOExecuteResult.getErrorMessages() + ", skuId : " + skuId);
                }
            }
        } catch (Exception e) {
            logger.error("批量上架商品+商品出错, 错误信息", e);
            executeResult.addErrorMessage(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    /**
     * 批量下架
     * @param itemIds 参数
     * @return
     */
    @Override
    public ExecuteResult<String> batchDownShelfProductPlushItem(List<Long> itemIds) {
        // 参数校验 必填项
        logger.info("Method [batchDownShelfProductPlushItem] Entrance, itemIds : {}", itemIds);
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        if (itemIds == null || itemIds.size() <= 0) {
            executeResult.addErrorMessage("skuIds is empty");
            return executeResult;
        }
        // 修改item状态为下架
        try {
            for (Long itemId : itemIds) {
                ProductPlusItemDTO productPlusItemDTO = new ProductPlusItemDTO();
                productPlusItemDTO.setItemId(itemId);
                productPlusItemDTO.setItemStatus(HtdItemStatusEnum.NOT_SHELVES.getCode());
                productPlusItemDTO.setUpdateShelfTimeFlag(0);//为0时更新下架时间，为1时修改上架时间
                this.modifyProductPlushItem(productPlusItemDTO);
            }
        } catch (Exception e) {
            logger.error("Error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**  vms **/
    /**
     * 查询卖家外接商品列表
     * （我司已上架，京东已上架）
     * @param productPlusItemDTO 参数
     * @return 卖家外接渠道商品列表
     */
    @Override
    public ExecuteResult<DataGrid<ProductPlusItemDTO>> querySellerProductPlusProductList(ProductPlusItemDTO productPlusItemDTO, Pager pager) {
        logger.info("Method [querySellerProductPlusProductList] Entrance, productPlusItemDTO : {}", productPlusItemDTO);
        ExecuteResult<DataGrid<ProductPlusItemDTO>> executeResult = new ExecuteResult<>();
        DataGrid<ProductPlusItemDTO> dtoDataGrid = new DataGrid<>();
        if (productPlusItemDTO == null) { // 参数为空
            executeResult.addErrorMessage("productPlusItemDTO is null");
            return executeResult;
        }
        if (StringUtils.isEmpty(productPlusItemDTO.getProductChannelCode())) { // 渠道编码必传
            executeResult.addErrorMessage("productChannelCode is empty");
            return executeResult;
        }
        if (productPlusItemDTO.getSellerId() == null) { // 渠道编码必传
            executeResult.addErrorMessage("sellerId is empty");
            return executeResult;
        }
        try {
            // 判断卖家是否接入该渠道
            SellerOuterProductChannel sellerOuterProductChannel = new SellerOuterProductChannel();
            sellerOuterProductChannel.setSellerId(productPlusItemDTO.getSellerId());
            sellerOuterProductChannel.setChannelCode(productPlusItemDTO.getProductChannelCode());
            SellerOuterProductChannel sellerOuterProductChannel1 = this.sellerOuterProductChannelMapper.selectBySellerIdAndChannelCode(sellerOuterProductChannel);
            if (sellerOuterProductChannel1 == null || sellerOuterProductChannel1.getAccessStatus() == 0) {
                // 该卖家未接入这个渠道
                executeResult.addErrorMessage("该卖家未接入这个渠道");
                return executeResult;
            }
            // 设置类目参数
            Long firstCategoryId = productPlusItemDTO.getFirstCategoryId();
            Long secondCategoryId = productPlusItemDTO.getSecondCategoryId();
            Long thirdCategoryId = productPlusItemDTO.getCid();
            Long[] categoryParam = this.itemCategoryService.getAllThirdCategoryByCategoryId(firstCategoryId, secondCategoryId, thirdCategoryId);
            productPlusItemDTO.setCategoryParam(categoryParam);
            // 状态为我司已上架，京东已上架
            productPlusItemDTO.setItemStatus(HtdItemStatusEnum.SHELVED.getCode());
            // 数量
            Long count = this.productPlusItemMapper.selectSellerProductPlusItemListCount(productPlusItemDTO);
            List<ProductPlusItemDTO> productPlusItemDTOs = new ArrayList<>();
            if (count > 0) {
                // 结果集
                productPlusItemDTOs = this.productPlusItemMapper.selectSellerProductPlusItemList(productPlusItemDTO, pager);
            }
            // 循环调用价格中心获取价格信息
            for (ProductPlusItemDTO productPlusItemDTO1 : productPlusItemDTOs) {
                // set price
                this.queryProductPlusPrice(productPlusItemDTO1.getSkuId(), productPlusItemDTO1);
                // set category
                ExecuteResult<Map<String, Object>> executeResultCategory = this.itemCategoryService.queryItemOneTwoThreeCategoryName(productPlusItemDTO1.getCid(), ">>");
                if (executeResultCategory.isSuccess()) {
                    productPlusItemDTO1.setCategoryName((String) executeResultCategory.getResult().get("categoryName"));
                }
            }
            dtoDataGrid.setRows(productPlusItemDTOs);
            dtoDataGrid.setTotal(count);
            executeResult.setResult(dtoDataGrid);
        } catch (Exception e) {
            logger.error("Method [queryProductPlushItemList] Error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 未屏蔽的品类与品牌
     * （通过渠道编码查询已经映射过的品牌、品类）除去 (卖家已屏蔽的)
     */
    @Override
    public ExecuteResult<DataGrid<SellerCategoryBrandShieldDTO>> querySellerNoShieldCategoryBrandList(SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO, Pager pager) {
        ExecuteResult<DataGrid<SellerCategoryBrandShieldDTO>> executeResult = new ExecuteResult();
        DataGrid<SellerCategoryBrandShieldDTO> dtoDataGrid = new DataGrid();
        // 查询所有的已映射品牌和品类， 从item商品表查询出全部已经映射过品牌-品类 -  查询卖家已屏蔽的品牌-品类.
        try {
            // 参数校验
            if (sellerCategoryBrandShieldDTO == null) {
                executeResult.addErrorMessage("sellerCategoryBrandShieldDTO is null");
                return executeResult;
            }
            if (sellerCategoryBrandShieldDTO.getSellerId() == null || sellerCategoryBrandShieldDTO.getSellerId() < 0) {
                executeResult.addErrorMessage("sellerId is empty");
                return executeResult;
            }
            if (StringUtils.isEmpty(sellerCategoryBrandShieldDTO.getChannelCode())) {
                executeResult.addErrorMessage("channelCode is empty");
                return executeResult;
            }
            // 判断卖家是否接入该渠道
            SellerOuterProductChannel sellerOuterProductChannel = new SellerOuterProductChannel();
            sellerOuterProductChannel.setSellerId(sellerCategoryBrandShieldDTO.getSellerId());
            sellerOuterProductChannel.setChannelCode(sellerCategoryBrandShieldDTO.getChannelCode());
            SellerOuterProductChannel sellerOuterProductChannel1 = this.sellerOuterProductChannelMapper.selectBySellerIdAndChannelCode(sellerOuterProductChannel);
            if (sellerOuterProductChannel1 == null || sellerOuterProductChannel1.getAccessStatus() == 0) {
                // 该卖家未接入这个渠道
                executeResult.addErrorMessage("该卖家未接入这个渠道");
                return executeResult;
            }
            Long firstCategoryId = sellerCategoryBrandShieldDTO.getFirstCategoryId(); // 一级类目id
            Long secondCategoryId = sellerCategoryBrandShieldDTO.getSecondCategoryId(); // 二级类目id
            Long categoryId = sellerCategoryBrandShieldDTO.getThirdCategoryId(); // 三级类目id
            Long[] categoryParam = this.itemCategoryService.getAllThirdCategoryByCategoryId(firstCategoryId, secondCategoryId, categoryId);
            sellerCategoryBrandShieldDTO.setCategoryParam(categoryParam);
            /** 开始查询 **/
            Long count = this.sellerCategoryBrandShieldMapper.selectSellerNoShieldCategoryBrandListCount(sellerCategoryBrandShieldDTO);
            List<SellerCategoryBrandShieldDTO> sellerCategoryBrandShieldDTOList = new ArrayList();
            if (count > 0 ) {
                sellerCategoryBrandShieldDTOList = this.sellerCategoryBrandShieldMapper.selectSellerNoShieldCategoryBrandList(sellerCategoryBrandShieldDTO, pager);
                for (SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTODB : sellerCategoryBrandShieldDTOList) {
                    // 设置类目
                    ExecuteResult<Map<String, Object>> executeResult1 = this.itemCategoryService.queryItemOneTwoThreeCategoryName(sellerCategoryBrandShieldDTODB.getThirdCategoryId(), ">>");
                    if (executeResult1.isSuccess()) {
                        Map<String, Object> resultMap = executeResult1.getResult();
                        sellerCategoryBrandShieldDTODB.setFirstCategoryId((Long) resultMap.get("firstCategoryId"));
                        sellerCategoryBrandShieldDTODB.setSecondCategoryId((Long) resultMap.get("secondCategoryId"));
                        sellerCategoryBrandShieldDTODB.setThirdCategoryId((Long) resultMap.get("thirdCategoryId"));
                        sellerCategoryBrandShieldDTODB.setCategoryName((String) resultMap.get("categoryName"));
                        sellerCategoryBrandShieldDTODB.setFirstCategoryName((String) resultMap.get("firstCategoryName"));
                        sellerCategoryBrandShieldDTODB.setSecondCategoryName((String) resultMap.get("secondCategoryName"));
                        sellerCategoryBrandShieldDTODB.setThirdCategoryName((String) resultMap.get("thirdCategoryName"));
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
            dtoDataGrid.setTotal(count);
            dtoDataGrid.setRows(sellerCategoryBrandShieldDTOList);
            executeResult.setResult(dtoDataGrid);
        } catch (Exception e) {
            logger.error("querySellerNoShieldCategoryBrandList error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 屏蔽外接品牌品类
     * @param sellerCategoryBrandShieldList 参数
     * @return
     */
    @Override
    public ExecuteResult<String> shieldCategoryBrand(List<SellerCategoryBrandShield> sellerCategoryBrandShieldList) {
        ExecuteResult<String> executeResult = new ExecuteResult();
        try {
            // 校验必填参数
            if (sellerCategoryBrandShieldList == null) {
                executeResult.addErrorMessage("sellerCategoryBrandShield is null");
                return executeResult;
            }
            for (SellerCategoryBrandShield sellerCategoryBrandShield : sellerCategoryBrandShieldList) {
                ValidateResult validateResult = DTOValidateUtil.validate(sellerCategoryBrandShield);
                if (!validateResult.isPass()) {
                    executeResult.addErrorMessage(validateResult.getMessage());
                    return executeResult;
                }
                // 判断屏蔽记录是否存在，存在则更新屏蔽标志为1
                SellerCategoryBrandShield sellerCategoryBrandShieldParam = new SellerCategoryBrandShield();
                sellerCategoryBrandShieldParam.setBrandId(sellerCategoryBrandShield.getBrandId());
                sellerCategoryBrandShieldParam.setThirdCategoryId(sellerCategoryBrandShield.getThirdCategoryId());
                sellerCategoryBrandShieldParam.setSellerId(sellerCategoryBrandShield.getSellerId());
                sellerCategoryBrandShieldParam.setChannelCode(sellerCategoryBrandShield.getChannelCode());
                SellerCategoryBrandShield sellerCategoryBrandShieldDb = this.sellerCategoryBrandShieldMapper.select(sellerCategoryBrandShieldParam);
                if (sellerCategoryBrandShieldDb != null) {
                    sellerCategoryBrandShieldDb.setShieldFlag(1);
                    logger.info("shieldCategoryBrand update exist data : {}", JSON.toJSONString(sellerCategoryBrandShieldParam));
                    this.sellerCategoryBrandShieldMapper.updateByPrimaryKeySelective(sellerCategoryBrandShieldDb);
                } else {
                    // 否则插入屏蔽记录
                    sellerCategoryBrandShield.setShopId(0L);
                    sellerCategoryBrandShield.setCategoryBrandId(0L);
                    sellerCategoryBrandShield.setShieldFlag(1);
                    logger.info("shieldCategoryBrand insert data : {}", JSON.toJSONString(sellerCategoryBrandShield));
                    this.sellerCategoryBrandShieldMapper.insert(sellerCategoryBrandShield);
                }
            }
        } catch (Exception e) {
            logger.error("shiedCategoryBrand error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 取消屏蔽外接品牌品类
     * @param sellerCategoryBrandShieldList 参数
     * @return
     */
    @Override
    public ExecuteResult<String> cancelShieldCategoryBrand(List<SellerCategoryBrandShield> sellerCategoryBrandShieldList) {
        ExecuteResult<String> executeResult = new ExecuteResult();
        try {
            // 校验必填参数
            if (sellerCategoryBrandShieldList == null) {
                executeResult.addErrorMessage("sellerCategoryBrandShield is null");
                return executeResult;
            }
            for (SellerCategoryBrandShield sellerCategoryBrandShield : sellerCategoryBrandShieldList) {
                if (sellerCategoryBrandShield.getCategoryBrandShieldId() == null || sellerCategoryBrandShield.getCategoryBrandShieldId() < 0) {
                    executeResult.addErrorMessage("sellerCategoryBrandShield is null");
                    return executeResult;
                }
                sellerCategoryBrandShield.setShieldFlag(0);
                this.sellerCategoryBrandShieldMapper.updateByPrimaryKeySelective(sellerCategoryBrandShield);
            }
        } catch (Exception e) {
            logger.error("shiedCategoryBrand error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 已经屏蔽的品类与品牌
     */
    @Override
    public ExecuteResult<DataGrid<SellerCategoryBrandShieldDTO>> querySellerShieldCategoryBrandList(SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO, Pager pager) {
        ExecuteResult<DataGrid<SellerCategoryBrandShieldDTO>> executeResult = new ExecuteResult();
        DataGrid<SellerCategoryBrandShieldDTO> dtoDataGrid = new DataGrid();
        try {
            // 参数校验
            if (sellerCategoryBrandShieldDTO == null) {
                executeResult.addErrorMessage("sellerCategoryBrandShieldDTO is null");
                return executeResult;
            }
            if (sellerCategoryBrandShieldDTO.getSellerId() == null || sellerCategoryBrandShieldDTO.getSellerId() < 0) {
                executeResult.addErrorMessage("sellerId is empty");
                return executeResult;
            }
            if (StringUtils.isEmpty(sellerCategoryBrandShieldDTO.getChannelCode())) {
                executeResult.addErrorMessage("channelCode is empty");
                return executeResult;
            }
            // 判断卖家是否接入该渠道
            SellerOuterProductChannel sellerOuterProductChannel = new SellerOuterProductChannel();
            sellerOuterProductChannel.setSellerId(sellerCategoryBrandShieldDTO.getSellerId());
            sellerOuterProductChannel.setChannelCode(sellerCategoryBrandShieldDTO.getChannelCode());
            SellerOuterProductChannel sellerOuterProductChannel1 = this.sellerOuterProductChannelMapper.selectBySellerIdAndChannelCode(sellerOuterProductChannel);
            if (sellerOuterProductChannel1 == null || sellerOuterProductChannel1.getAccessStatus() == 0) {
                // 该卖家未接入这个渠道
                executeResult.addErrorMessage("该卖家未接入这个渠道");
                return executeResult;
            }
            Long firstCategoryId = sellerCategoryBrandShieldDTO.getFirstCategoryId(); // 一级类目id
            Long secondCategoryId = sellerCategoryBrandShieldDTO.getSecondCategoryId(); // 二级类目id
            Long categoryId = sellerCategoryBrandShieldDTO.getThirdCategoryId(); // 三级类目id
            Long[] categoryParam = this.itemCategoryService.getAllThirdCategoryByCategoryId(firstCategoryId, secondCategoryId, categoryId);
            sellerCategoryBrandShieldDTO.setCategoryParam(categoryParam);
            /** 开始查询 **/
            Long count = this.sellerCategoryBrandShieldMapper.selectShieldSellerCategoryBrandListCount(sellerCategoryBrandShieldDTO);
            List<SellerCategoryBrandShieldDTO> sellerCategoryBrandShieldDTOList = new ArrayList();
            if (count > 0 ) {
                sellerCategoryBrandShieldDTOList = this.sellerCategoryBrandShieldMapper.selectShieldSellerCategoryBrandList(sellerCategoryBrandShieldDTO, pager);
                for (SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTODB : sellerCategoryBrandShieldDTOList) {
                    // 设置渠道名称
                    sellerCategoryBrandShieldDTODB.setChannelName(this.queryProductChannelByCode(sellerCategoryBrandShieldDTODB.getChannelCode()).getChannelName());
                    // 设置类目
                    ExecuteResult<Map<String, Object>> executeResult1 = this.itemCategoryService.queryItemOneTwoThreeCategoryName(sellerCategoryBrandShieldDTODB.getThirdCategoryId(), ">>");
                    if (executeResult1.isSuccess()) {
                        Map<String, Object> resultMap = executeResult1.getResult();
                        sellerCategoryBrandShieldDTODB.setFirstCategoryId((Long) resultMap.get("firstCategoryId"));
                        sellerCategoryBrandShieldDTODB.setSecondCategoryId((Long) resultMap.get("secondCategoryId"));
                        sellerCategoryBrandShieldDTODB.setThirdCategoryId((Long) resultMap.get("thirdCategoryId"));
                        sellerCategoryBrandShieldDTODB.setCategoryName((String) resultMap.get("categoryName"));
                        sellerCategoryBrandShieldDTODB.setFirstCategoryName((String) resultMap.get("firstCategoryName"));
                        sellerCategoryBrandShieldDTODB.setSecondCategoryName((String) resultMap.get("secondCategoryName"));
                        sellerCategoryBrandShieldDTODB.setThirdCategoryName((String) resultMap.get("thirdCategoryName"));
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
            dtoDataGrid.setTotal(count);
            dtoDataGrid.setRows(sellerCategoryBrandShieldDTOList);
            executeResult.setResult(dtoDataGrid);
        } catch (Exception e) {
            logger.error("querySellerShieldCategoryBrandList error:", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public JdProductQueryOutDTO queryJdProduct4SupperBoss(JdProductQueryInDTO jdProductQueryInDTO) {
        logger.info("超级老板查询京东商品, 参数 : {}", JSONObject.fromObject(jdProductQueryInDTO));
        JdProductQueryOutDTO jdProductQueryOutDTO = new JdProductQueryOutDTO();
        try {
            if (jdProductQueryInDTO == null) {
                jdProductQueryOutDTO.setResultCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                jdProductQueryOutDTO.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
                return jdProductQueryOutDTO;
            }
            String skuCode = jdProductQueryInDTO.getSkuCode();
            Pager pager = jdProductQueryInDTO.getPager();
            Date startTime = jdProductQueryInDTO.getStartTime();
            Date endTime = jdProductQueryInDTO.getEndTime();
            Integer isPreSale = jdProductQueryInDTO.getIsPreSale();
            if (StringUtils.isEmpty(skuCode)) {
                if (pager == null) {
                    jdProductQueryOutDTO.setResultCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
                    jdProductQueryOutDTO.setResultMessage("skuCode为空时, 分页条件必传");
                    return jdProductQueryOutDTO;
                }
                if (startTime == null) {
                    jdProductQueryOutDTO.setResultCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
                    jdProductQueryOutDTO.setResultMessage("skuCode为空时, 开始时间必传");
                    return jdProductQueryOutDTO;
                }
                if (endTime == null) {
                    jdProductQueryOutDTO.setResultCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
                    jdProductQueryOutDTO.setResultMessage("skuCode为空时, 结束时间必传");
                    return jdProductQueryOutDTO;
                }
            }
            int count = this.productPlusItemMapper.queryJdProductListCount(skuCode, isPreSale, startTime, endTime);
            List<JdProductQueryItemDTO> dataList = new ArrayList();
            if (count > 0) {
                dataList = this.productPlusItemMapper.queryJdProductList(skuCode, isPreSale, startTime, endTime, pager);
                for (JdProductQueryItemDTO jdProductQueryItemDTO : dataList) {
                    try {
                        // 图片
                        List<ItemPicture> itemPictureList = this.itemPictureDAO.queryItemPicsById(jdProductQueryItemDTO.getItemId());
                        jdProductQueryItemDTO.setItemPictureList(itemPictureList);
                        // 查询零售价，销售价
                        ExecuteResult<ItemSkuBasePriceDTO> itemSkuBasePriceDTOExecuteResult = this.itemSkuPriceService.queryItemSkuBasePrice(jdProductQueryItemDTO.getSkuId());
                        ItemSkuBasePriceDTO itemSkuBasePriceDTO = itemSkuBasePriceDTOExecuteResult.getResult();
                        jdProductQueryItemDTO.setRetailPrice(itemSkuBasePriceDTO.getRetailPrice());
                        jdProductQueryItemDTO.setSalePrice(itemSkuBasePriceDTO.getAreaSalePrice());
                    } catch (Exception e) {
                        logger.error("查询京东列表-价格失败, skuCode : {}", jdProductQueryItemDTO.getSkuCode());
                    }
                }
            }
            if (pager == null) {
                pager = new Pager();
            }
            pager.setTotalCount(count);
            jdProductQueryOutDTO.setPager(pager);
            jdProductQueryOutDTO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            jdProductQueryOutDTO.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
            jdProductQueryOutDTO.setDataList(dataList);
        } catch (Exception e) {
            logger.error("超级老板查询京东商品错误, 错误信息 : ", e);
            jdProductQueryOutDTO.setResultCode(ResultCodeEnum.ERROR.getCode());
            jdProductQueryOutDTO.setResultMessage(ResultCodeEnum.ERROR.getMessage());
        }
        return jdProductQueryOutDTO;
    }

    @Override
    public ExecuteResult<Boolean> canProductPlusSaleBySeller(Long sellerId, String productChannel, Long categoryId, Long brandId) {
        ExecuteResult<Boolean> executeResult = new ExecuteResult();
        try {
            if (ProductChannelEnum.JD_PRODUCT.getCode().equals(productChannel)) {
                // 判断卖家是否接入该渠道
                SellerOuterProductChannel param = new SellerOuterProductChannel();
                param.setSellerId(sellerId);
                param.setChannelCode(productChannel);
                SellerOuterProductChannel sellerOuterProductChannel = this.sellerOuterProductChannelMapper.selectBySellerIdAndChannelCode(param);
                if (sellerOuterProductChannel != null && sellerOuterProductChannel.getAccessStatus().equals(1)) { // 被这个大B接入
                    // 判断品牌、品牌是否被屏蔽
                    SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO = new SellerCategoryBrandShieldDTO();
                    sellerCategoryBrandShieldDTO.setChannelCode(productChannel);
                    sellerCategoryBrandShieldDTO.setSellerId(sellerId);
                    sellerCategoryBrandShieldDTO.setBrandId(brandId);
                    sellerCategoryBrandShieldDTO.setCategoryParam(new Long[]{categoryId});
                    Long count = this.sellerCategoryBrandShieldMapper.selectShieldSellerCategoryBrandListCount(sellerCategoryBrandShieldDTO);
                    if (count == 0) {
                        executeResult.setResult(true);
                        executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
                        executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
                    } else {
                        logger.error("该卖家屏蔽了该商品加品牌品类, sellerId : {}, productChannel :{}, categoryId : {}, brandId : {}",
                                sellerId, productChannel, categoryId, brandId);
                        executeResult.setResult(false);
                        executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
                        executeResult.setResultMessage("该卖家屏蔽了该商品加品牌品类");
                    }
                } else {
                    logger.error("该卖家未接入此商品加渠道, sellerId : {}, productChannel :{}", sellerId, productChannel);
                    executeResult.setResult(false);
                    executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
                    executeResult.setResultMessage("该卖家未接入此商品加渠道, sellerId : " + sellerId + ", productChannel : " + productChannel);
                }
            } else {
                // 非商品加渠道，不校验
                executeResult.setResult(true);
                executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
                executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
            }
        } catch (Exception e) {
            logger.error("查询该商品加商品是否可以被该卖家售卖出错, 错误信息", e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<Boolean> isSellerAccessProductPlus(Long sellerId, String productChannel) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            if (sellerId == null) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                executeResult.setResultMessage("sellerId is null");
                executeResult.addErrorMsg("sellerId is null");
                return executeResult;
            }
            if (StringUtils.isEmpty(productChannel)) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                executeResult.setResultMessage("productChannel is empty");
                executeResult.addErrorMsg("productChannel is empty");
                return executeResult;
            }
            Boolean flag = false;
            List<SellerOuterProductChannel> sellerOuterProductChannelList = this.sellerOuterProductChannelMapper.selectAccessedListBySellerId(sellerId);
            for (SellerOuterProductChannel sellerOuterProductChannel : sellerOuterProductChannelList) {
                if (productChannel.equals(sellerOuterProductChannel.getChannelCode())) {
                    flag = true;
                }
            }
            executeResult.setResult(flag);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("查询大B是否接入商品+出错 :", e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.addErrorMsg(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 修改商品加商品
     *
     * @param productPlusItemDTO 参数
     */
    private void modifyProductPlushItem(ProductPlusItemDTO productPlusItemDTO) {
        this.productPlusItemMapper.updateProductPlusItem(productPlusItemDTO);
    }

    /**
     * 查询商品加价格
     * @param skuId
     * @param productPlusItemDTO
     */
    private void queryProductPlusPrice(Long skuId, ProductPlusItemDTO productPlusItemDTO) {
        ExecuteResult<ItemSkuBasePriceDTO> executeResultPrice = itemSkuPriceService.queryItemSkuBasePrice(skuId);
        // 京东基本价格
        if (executeResultPrice.isSuccess()) {
            ItemSkuBasePriceDTO itemSkuBasePriceDTO = executeResultPrice.getResult();
            productPlusItemDTO.setCostPrice(wrapDecimal(itemSkuBasePriceDTO.getCostPrice(), 2)); // 外部供货价
            productPlusItemDTO.setSaleLimitedPrice(wrapDecimal(itemSkuBasePriceDTO.getSaleLimitedPrice(), 2));
            productPlusItemDTO.setAreaSalePrice(wrapDecimal(itemSkuBasePriceDTO.getAreaSalePrice(), 2));// 商城销售价
            productPlusItemDTO.setPriceFloatingRatio(wrapDecimal(itemSkuBasePriceDTO.getPriceFloatingRatio(), 4));// 价格浮动比例
            productPlusItemDTO.setRetailPrice(wrapDecimal(itemSkuBasePriceDTO.getRetailPrice(), 2));// 零售价
            productPlusItemDTO.setRetailPriceFloatingRatio(wrapDecimal(itemSkuBasePriceDTO.getRetailPriceFloatingRatio(), 4));// 零售价格浮动比例
            productPlusItemDTO.setCommissionRatio(wrapDecimal(itemSkuBasePriceDTO.getCommissionRatio(), 4));// 佣金比例
            // TODO : 四舍五入
            productPlusItemDTO.setVipPriceFloatingRatio(itemSkuBasePriceDTO.getVipPriceFloatingRatio());// vip价浮动比例
        }
        // 查询vip等级价
        ExecuteResult<InnerItemSkuPrice> executeResultInnerPrice = this.itemSkuPriceService.queryInnerItemSkuMemberLevelPrice(skuId, Constants.PRICE_TYPE_BUYER_GRADE, Constants.VIP_BUYER_GRADE, 0);
        if (executeResultInnerPrice.isSuccess()) {
            InnerItemSkuPrice innerItemSkuPrice = executeResultInnerPrice.getResult();
            if (innerItemSkuPrice != null) {
                productPlusItemDTO.setVipPrice(wrapDecimal(innerItemSkuPrice.getPrice(), 2));
            }
        }
    }

    /**
     * 修改商品加商品价格
     *
     * @param productPlusItemDTO 参数
     */
    private void modifyProductPlushItemPrice(ProductPlusItemDTO productPlusItemDTO) {
        ItemSkuBasePrice itemSkuBasePrice = new ItemSkuBasePrice();
        itemSkuBasePrice.setItemId(productPlusItemDTO.getItemId());
        itemSkuBasePrice.setSkuId(productPlusItemDTO.getSkuId());
        itemSkuBasePrice.setAreaSalePrice(wrapDecimal(productPlusItemDTO.getAreaSalePrice(), 2)); // 商城销售价
        itemSkuBasePrice.setPriceFloatingRatio(wrapDecimal(productPlusItemDTO.getPriceFloatingRatio(), 4)); // 价格浮动比例
        itemSkuBasePrice.setRetailPrice(wrapDecimal(productPlusItemDTO.getRetailPrice(), 2)); // 商城零售钱
        itemSkuBasePrice.setRetailPriceFloatingRatio(wrapDecimal(productPlusItemDTO.getRetailPriceFloatingRatio(), 4)); // 零售价格浮动比例
        itemSkuBasePrice.setCommissionRatio(wrapDecimal(productPlusItemDTO.getCommissionRatio(), 4)); // 佣金比例
        itemSkuBasePrice.setVipPriceFloatingRatio(productPlusItemDTO.getVipPriceFloatingRatio());
        ExecuteResult<String> executeResult = this.itemSkuPriceService.updateItemSkuBasePrice(itemSkuBasePrice);
        if (!executeResult.isSuccess()) {
            throw new RuntimeException("更新商品+基本价格出错, 错误信息 : " + executeResult.getErrorMessages());
        }
        BigDecimal vipPrice = productPlusItemDTO.getVipPrice();
        ExecuteResult<InnerItemSkuPrice> executeResultInnerPrice = this.itemSkuPriceService.queryInnerItemSkuMemberLevelPrice(productPlusItemDTO.getSkuId(), Constants.PRICE_TYPE_BUYER_GRADE, Constants.VIP_BUYER_GRADE, 0);
        InnerItemSkuPrice innerItemSkuPrice = executeResultInnerPrice.getResult();
        if (vipPrice != null) {
            // 设置item hasVipPrice = 1
            int result = this.itemMybatisDAO.updateHasVipPrice(productPlusItemDTO.getItemId(), 1);
            if (result == 0) {
                throw new RuntimeException("updateHasVipPrice出错, itemId : " + productPlusItemDTO.getItemId());
            }
            // inner_sku_price 新增\更新一条vip价格记录
            if (innerItemSkuPrice != null) {
                innerItemSkuPrice.setPrice(wrapDecimal(productPlusItemDTO.getVipPrice(), 2));
                ExecuteResult<String> executeResultUpdate = this.itemSkuPriceService.updateInnerItemSkuPrice(innerItemSkuPrice);
                if (!executeResultUpdate.isSuccess()) {
                    throw new RuntimeException("更新内部会员VIP等级价格出错, 错误信息 : " + executeResultUpdate.getErrorMessages());
                }
            } else {
                InnerItemSkuPrice innerItemSkuPriceParam = new InnerItemSkuPrice();
                innerItemSkuPriceParam.setSkuId(productPlusItemDTO.getSkuId());
                innerItemSkuPriceParam.setItemId(productPlusItemDTO.getItemId());
                innerItemSkuPriceParam.setSellerId(0L);
                innerItemSkuPriceParam.setShopId(0L);
                innerItemSkuPriceParam.setPriceType(Constants.PRICE_TYPE_BUYER_GRADE);
                innerItemSkuPriceParam.setBuyerGrade(Constants.VIP_BUYER_GRADE);
                innerItemSkuPriceParam.setPrice(wrapDecimal(productPlusItemDTO.getVipPrice(), 2));
                innerItemSkuPriceParam.setCreateId(0L);
                innerItemSkuPriceParam.setCreateName("system");
                ExecuteResult<String> executeResultAdd = this.itemSkuPriceService.addInnerItemSkuPrice(innerItemSkuPriceParam);
                if (!executeResultAdd.isSuccess()) {
                    throw new RuntimeException("新增内部会员VIP等级价格出错, 错误信息 : " + executeResultAdd.getErrorMessages());
                }
            }
        } else {
            // 设置item hasVipPrice = 0
            int result = this.itemMybatisDAO.updateHasVipPrice(productPlusItemDTO.getItemId(), 0);
            if (result == 0) {
                throw new RuntimeException("updateHasVipPrice出错, itemId : " + productPlusItemDTO.getItemId());
            }
            // inner_sku_price 删除一条vip价格记录
            if (innerItemSkuPrice != null) {
                InnerItemSkuPrice innerItemSkuPriceParam = new InnerItemSkuPrice();
                innerItemSkuPriceParam.setGradePriceId(innerItemSkuPrice.getGradePriceId());
                innerItemSkuPriceParam.setDeleteFlag(1);
                ExecuteResult executeResultDelete = this.itemSkuPriceService.updateInnerItemSkuPrice(innerItemSkuPriceParam);
                if (!executeResultDelete.isSuccess()) {
                    throw new RuntimeException("删除内部会员VIP等级价格出错, 错误信息 : " + executeResultDelete.getErrorMessages());
                }
            }
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

	@Override
	public ProductPlusAccessInfoOutDTO queryProductPlusAccessInfoList(
			ProductPlusAccessInfoInDTO productPlusAccessInfoInDTO) {
		ProductPlusAccessInfoOutDTO result=new ProductPlusAccessInfoOutDTO();
		if(productPlusAccessInfoInDTO==null||productPlusAccessInfoInDTO.getCurrentPage()==null){
			result.setSuccess(false);
			result.setErrorMsg("currentPage is null");
			return result;
		}
		
		Integer totalCount=productPlusItemMapper.queryTotalProductPlusItemCount();
		
		Integer pageSize=100;
		
		if(totalCount==null||totalCount<=0){
			result.setSuccess(false);
			result.setErrorMsg("没有数据");
			return result;
		}
		
		Integer totalPageCount=(totalCount % pageSize==0) ?totalCount/pageSize:(totalCount/pageSize+1);
		
		Map<String, Object> param=Maps.newHashMap();
		Integer start=(productPlusAccessInfoInDTO.getCurrentPage()-1) * pageSize;
		param.put("start", start);
		param.put("pageSize", pageSize);
		List<Map> dataList=productPlusItemMapper.queryPagedProductPlusItem(param);
		
		if(CollectionUtils.isEmpty(dataList)){
			result.setSuccess(false);
			result.setErrorMsg("没有数据");
			return result;
		}
		
		
		Map<String,List<String>> dataCotainer=Maps.newHashMap();
		List<String> itemIdList=Lists.newArrayList();
		for(Map obj:dataList){
			String itemId=String.valueOf(obj.get("itemid"));
			String sellerId=String.valueOf(obj.get("sellerid"));
			String skuCode=String.valueOf(obj.get("skucode"));
			if(StringUtils.isEmpty(itemId)||"0".equals(sellerId)){
				continue;
			}
			itemIdList.add(itemId);
			dataCotainer.put(skuCode,Lists.newArrayList(StringUtils.split(sellerId,",")));
		}
		
		//fetch shield sellerId start
		Map<String,List<String>> shieldDataCotainer=Maps.newHashMap();
		if(CollectionUtils.isNotEmpty(itemIdList)){
			List<Map> shieldList=productPlusItemMapper.queryShieldSellerIdsByItemId(itemIdList);
			for(Map shieldData:shieldList){
				//String shieldItemId=String.valueOf(shieldData.get("itemid"));
				String shieldSellerId=String.valueOf(shieldData.get("sellerid"));
				String shieldSkuCode=String.valueOf(shieldData.get("skucode"));
				if("".equals(shieldSellerId)){
					continue;
				}
				shieldDataCotainer.put(shieldSkuCode,Lists.newArrayList(StringUtils.split(shieldSellerId,",")));
			}
		}
		if(MapUtils.isNotEmpty(shieldDataCotainer)){
			for(String sku:dataCotainer.keySet()){
				List<String> shieldSellerIdList=shieldDataCotainer.get(sku);
				//no shield data,then continue
				if(CollectionUtils.isEmpty(shieldSellerIdList)){
					continue;
				}
				
				//如果包含，则剔除
				for(String shieldSellerId:shieldSellerIdList){
					if(CollectionUtils.isEmpty(dataCotainer.get(sku))){
						continue;
					}
					if(dataCotainer.get(sku).contains(shieldSellerId)){
						dataCotainer.get(sku).remove(shieldSellerId);
					}
				}
				
			}
		}
		 //fetch shield sellerId end
				
		result.setData(dataCotainer);
		result.setTotalPage(totalPageCount);
		return result;
	}
}
