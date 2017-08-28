package cn.htd.goodscenter.service.listener;

import cn.htd.common.ExecuteResult;
import cn.htd.common.util.SysProperties;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.productplus.OuterChannelBrandMappingMapper;
import cn.htd.goodscenter.dao.productplus.OuterChannelCategoryMappingMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.productplus.OuterChannelBrandMapping;
import cn.htd.goodscenter.domain.productplus.OuterChannelCategoryMapping;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.OutItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ProductChannelEnum;
import cn.htd.goodscenter.dto.productplus.ProductPlusPictureImportDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusPriceSupplyDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusProductImportDTO;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 京东外接商品数据批量导入服务【全量】
 *  导入京东外接商品、京东外接品类、京东品牌、京东价格
 * @author chenakng
 */
public class JdProductDataImportServiceImpl implements MessageListener {

    @Resource
    private ItemMybatisDAO itemMybatisDAO;

    @Resource
    private ItemSkuDAO itemSkuDAO;

    @Resource
    private OuterChannelCategoryMappingMapper outerChannelCategoryMappingMapper;

    @Resource
    private OuterChannelBrandMappingMapper outerChannelBrandMappingMapper;

    @Resource
    private ItemPictureDAO itemPictureDAO;

    @Resource
    private ItemSkuPriceService itemSkuPriceService;

    @Resource
    private JdSupplyProductPriceServiceImpl jdSupplyProductPriceService;

    private static final Long JD_SELLER_ID = 0L;

    private static final Long JD_SHOP_ID = 0L;

    private static final Long JD_CREATE_ID = 0L;

    private static final String JD_CREATE_NAME = "JD";

    private Logger logger = LoggerFactory.getLogger(JdProductDataImportServiceImpl.class);

    @Transactional
    @Override
    public void onMessage(Message message) {
        logger.info("京东商品全量导入监听开始");
        String messageBody = null;
        try {
            messageBody = new String(message.getBody(),"utf-8");
            /** 解析数据 **/
            ProductPlusProductImportDTO productPlusProductImportDTO = this.parseMessage(messageBody);
            this.importData(productPlusProductImportDTO);
        } catch (Exception e) {
            logger.error("京东商品全量导入失败, 错误数据信息 : {}", messageBody, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            logger.info("京东商品全量导入监听结束");
        }
    }

    /**
     * 导入外接商品数据
     * @param productPlusProductImportDTO
     */
    public void importData(ProductPlusProductImportDTO productPlusProductImportDTO) throws Exception {
        logger.info("京东商品全量导入, 参数 ： {}", JSONObject.fromObject(productPlusProductImportDTO));
        this.importProduct(productPlusProductImportDTO);
        /** 导入类目 **/
        this.importJdCategory(productPlusProductImportDTO);
        /** 导入品牌 **/
        this.importJdBrand(productPlusProductImportDTO);
    }

    /**
     * 导入商品
     * @param productPlusProductImportDTO 参数
     * @throws IOException 异常
     */
    private void importProduct(ProductPlusProductImportDTO productPlusProductImportDTO) throws Exception {
        /** 判断是否重复 **/
        // item 和 sku 是 1v1关系
        String outSkuId = productPlusProductImportDTO.getOuterSkuId(); // 京东skuId
        ItemSku skuOld = this.itemSkuDAO.queryItemSkuByOuterSkuId(outSkuId);
        if (skuOld != null) {
            /** 存在重复的, 准备更新记录 **/
            logger.info("中台数据存在该京东数据, 更新该条数据, 外部商品SKUID : {}", outSkuId);
            Long skuId = skuOld.getSkuId();
            Long itemId = skuOld.getItemId();
            Item itemOld = this.itemMybatisDAO.queryItemByPk(itemId);
            // 更新item
            Item item = this.createItemFromDTO(productPlusProductImportDTO, true, itemOld); // 更新逻辑
            item.setItemId(itemId);
            this.itemMybatisDAO.updateByPrimaryKeySelective(item);
            // 更新sku
            ItemSku itemSku = this.createItemSkuFromDTO(productPlusProductImportDTO, itemId, true, skuOld);
            itemSku.setSkuId(skuId);
            this.itemSkuDAO.update(itemSku);
            // 更新price
            ProductPlusPriceSupplyDTO productPlusPriceSupplyDTO = new ProductPlusPriceSupplyDTO();
            productPlusPriceSupplyDTO.setOuterSkuId(outSkuId);
            productPlusPriceSupplyDTO.setCostPrice(productPlusProductImportDTO.getCostPrice());
            productPlusPriceSupplyDTO.setSaleLimitedPrice(productPlusProductImportDTO.getSaleLimitedPrice());
            this.jdSupplyProductPriceService.modifyPrice(productPlusPriceSupplyDTO);
            // 更新picture
            // 1. 删除原有图片
            this.itemPictureDAO.updateDeleteFlagByItemId(itemId);
            // 2. 新增此次图片
            List<ProductPlusPictureImportDTO> productPlusPictureImportDTOList = productPlusProductImportDTO.getProductPlusPictureImportDTOList();
            int sortNumber = 0;
            if (productPlusPictureImportDTOList != null) {
                for (ProductPlusPictureImportDTO productPlusPictureImportDTO : productPlusPictureImportDTOList) {
                    ItemPicture itemPicture = this.createItemPictureFromDTO(productPlusPictureImportDTO, itemId, sortNumber++);
                    this.itemPictureDAO.add(itemPicture);
                }
            }
        } else {
            /** 不存在重复的，准备插入记录 **/
            // 插入item
            Item item = this.createItemFromDTO(productPlusProductImportDTO, false, null);
            this.itemMybatisDAO.addItem(item);
            Long itemId = item.getItemId();
            // 插入shu
            ItemSku itemSku = this.createItemSkuFromDTO(productPlusProductImportDTO, itemId, false, null);
            this.itemSkuDAO.add(itemSku);
            Long skuId = itemSku.getSkuId();
            // 插入price
            ItemSkuBasePrice itemSkuBasePrice = this.createItemPriceFromDTO(productPlusProductImportDTO, skuId, itemId);
            ExecuteResult executePriceResult = itemSkuPriceService.saveItemSkuBasePrice(itemSkuBasePrice);
            if (!executePriceResult.isSuccess()) {
                throw new RuntimeException("saveItemSkuBasePrice error.");
            }
            // 插入picture
            List<ProductPlusPictureImportDTO> productPlusPictureImportDTOList = productPlusProductImportDTO.getProductPlusPictureImportDTOList();
            int sortNumber = 0;
            if (productPlusPictureImportDTOList != null) {
                for (ProductPlusPictureImportDTO productPlusPictureImportDTO : productPlusPictureImportDTOList) {
                    ItemPicture itemPicture = this.createItemPictureFromDTO(productPlusPictureImportDTO, itemId, sortNumber++);
                    this.itemPictureDAO.add(itemPicture);
                }
            }
        }
    }

    /**
     * 导入类目
     * @param productPlusProductImportDTO 参数
     * @throws IOException
     */
    private void importJdCategory(ProductPlusProductImportDTO productPlusProductImportDTO) throws IOException {
        // 根据渠道编码和编码判断记录是否存在
        OuterChannelCategoryMapping outerChannelCategoryMapping = new OuterChannelCategoryMapping();
        outerChannelCategoryMapping.setChannelCode(ProductChannelEnum.JD_PRODUCT.getCode());
        outerChannelCategoryMapping.setOuterChannelCategoryCode(productPlusProductImportDTO.getOuterChannelCategoryCode());
        outerChannelCategoryMapping.setOuterChannelCategoryName(productPlusProductImportDTO.getOuterChannelCategoryName());
        Long count = this.outerChannelCategoryMappingMapper.selectCountByOCCCodeAndChannelCode(outerChannelCategoryMapping);
        logger.info("京东商品全量导入之【导入京东类目】, outerChannelCategoryMapping : {}", outerChannelCategoryMapping);
        if (count != 0) {
            outerChannelCategoryMapping.setModifyId(JD_CREATE_ID);
            outerChannelCategoryMapping.setModifyName(JD_CREATE_NAME);
            outerChannelCategoryMapping.setModifyTime(new Date());
            this.outerChannelCategoryMappingMapper.updateByOuterCategoryCodeSelective(outerChannelCategoryMapping);
        } else {
            // 满足，则插入
            outerChannelCategoryMapping.setCategoryId(0L); // 默认值
            outerChannelCategoryMapping.setDeleteFlag(0);
            outerChannelCategoryMapping.setCreateId(JD_CREATE_ID);
            outerChannelCategoryMapping.setCreateName(JD_CREATE_NAME);
            outerChannelCategoryMapping.setCreateTime(new Date());
            this.outerChannelCategoryMappingMapper.insert(outerChannelCategoryMapping);
        }
    }

    /**
     * 导入品牌
     * @param productPlusProductImportDTO 参数
     * @throws IOException
     */
    private void importJdBrand(ProductPlusProductImportDTO productPlusProductImportDTO) throws IOException {
        OuterChannelBrandMapping outerChannelBrandMapping = new OuterChannelBrandMapping();
        outerChannelBrandMapping.setChannelCode(ProductChannelEnum.JD_PRODUCT.getCode());
        outerChannelBrandMapping.setOuterChannelBrandCode(productPlusProductImportDTO.getOuterChannelBrandCode());
        outerChannelBrandMapping.setOuterChannelBrandName(productPlusProductImportDTO.getOuterChannelBrandName());
        // 根据渠道编码和编码判断记录是否存在
        Long count = this.outerChannelBrandMappingMapper.selectCountByOCCCodeAndChannelCode(outerChannelBrandMapping);
        logger.info("京东商品全量导入之【导入京东品牌】, outerChannelBrandMapping : {}", outerChannelBrandMapping);
        if (count != 0) {
            outerChannelBrandMapping.setModifyId(JD_CREATE_ID);
            outerChannelBrandMapping.setModifyName(JD_CREATE_NAME);
            outerChannelBrandMapping.setModifyTime(new Date());
            this.outerChannelBrandMappingMapper.updateByOuterBrandCodeSelective(outerChannelBrandMapping);
        } else {
            // 满足，则插入
            outerChannelBrandMapping.setBrandId(0L); // 默认值
            outerChannelBrandMapping.setDeleteFlag(0);
            outerChannelBrandMapping.setCreateId(JD_CREATE_ID);
            outerChannelBrandMapping.setCreateName(JD_CREATE_NAME);
            outerChannelBrandMapping.setCreateTime(new Date());
            this.outerChannelBrandMappingMapper.insert(outerChannelBrandMapping);
        }
    }

    /**
     * 解析文本
     * @param message 文本
     * @return 结果集
     */
    private ProductPlusProductImportDTO parseMessage(String message) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return mapper.readValue(message, ProductPlusProductImportDTO.class);
    }

    /**
     * 创建item对象
     * @param productPlusProductImportDTO 参数
     * @return item对象
     */
    private Item createItemFromDTO(ProductPlusProductImportDTO productPlusProductImportDTO, boolean isUpdate, Item itemOld) {
        Item item = new Item();
        if (!isUpdate) { //
            item.setItemCode(ItemCodeGenerator.generateItemCode()); // 商品编码。 京东skuId  itemCode
            // jd状态转换 0: 未上架 1: 已上架 2: 非图书商品表示预上架，图书商品表示前台屏蔽 10: POP商品删除状态 
            String outerItemStatus;
            if (productPlusProductImportDTO.getOuterItemStatus().equals("1")) {
                outerItemStatus = OutItemStatusEnum.SHELVED.getCode();
            } else {
                outerItemStatus = OutItemStatusEnum.NOT_SHELVES.getCode();
            }
            /** 查询参数 **/
            // 根据外接渠道品类编码 去映射表查询 我司类目
            Long cid = getCategoryIdByCode(productPlusProductImportDTO); // 品类
            Long brand = getBrandIdByCode(productPlusProductImportDTO); // 品牌
            // 根据品牌品类设置item状态
            Integer itemStatus = HtdItemStatusEnum.ERP_STOCKPRICE_OR_OUTPRODUCTPRICE.getCode(); // 品牌品类待映射;
            if (cid != null && cid > 0 && brand != null && brand > 0) { // 外部品牌品类已经和内部品牌品类映射
                itemStatus = HtdItemStatusEnum.NOT_SHELVES.getCode(); // 未上架
            }
            item.setCid(cid);
            item.setBrand(brand);
            item.setOuterItemStatus(outerItemStatus);
            item.setItemStatus(itemStatus);

            item.setIsPreSale(0);// 是否预售
            item.setPreSaleStarttime(null); // 预售开始时间
            item.setPreSaleEndtime(null); // 预售结束时间
            item.setTimingListing(null); // 定时上架，为空则表示未设置定时上架
            item.setListtingTime(null); // 上架时间
            item.setDelistingTime(null); // 下架时间
            item.setAttributes("");// 类目的属性
            item.setAttrSale(""); // 京东商品销售属性keyId:valueId
        } else { // 更新逻辑
            Integer oldItemStatus = itemOld.getItemStatus(); // 老商品状态
            String outerItemStatus; // 外部商品状态
            Long cid = getCategoryIdByCode(productPlusProductImportDTO); // 品类
            Long brand = getBrandIdByCode(productPlusProductImportDTO); // 品牌
            Integer newItemStatus = null;
            if (productPlusProductImportDTO.getOuterItemStatus().equals("1")) { // 外部状态上架
                outerItemStatus = OutItemStatusEnum.SHELVED.getCode();
                if (cid != null && cid > 0 && brand != null && brand > 0) { // 已映射
                    if (oldItemStatus.equals(HtdItemStatusEnum.ERP_STOCKPRICE_OR_OUTPRODUCTPRICE.getCode())) { // 如果以前是待映射
                        newItemStatus = HtdItemStatusEnum.NOT_SHELVES.getCode(); // 更新为未上架
                    } else { // 以前是未上架，和已上架 保持不变
                        newItemStatus = oldItemStatus;
                    }
                } else { // 未映射。直接更新为待映射状态
                    newItemStatus = HtdItemStatusEnum.ERP_STOCKPRICE_OR_OUTPRODUCTPRICE.getCode();
                }
            } else { // 外部状态已下架
                outerItemStatus = OutItemStatusEnum.NOT_SHELVES.getCode();
                if (cid != null && cid > 0 && brand != null && brand > 0) { // 已映射
                    newItemStatus = HtdItemStatusEnum.NOT_SHELVES.getCode(); // 新状态为未上架
                } else { // 未映射
                    newItemStatus = HtdItemStatusEnum.ERP_STOCKPRICE_OR_OUTPRODUCTPRICE.getCode();  // 新状态为待映射
                }
            }
            item.setCid(cid);
            item.setBrand(brand);
            item.setOuterItemStatus(outerItemStatus);
            item.setItemStatus(newItemStatus);

            item.setIsPreSale(itemOld.getIsPreSale());// 是否预售
            item.setPreSaleStarttime(itemOld.getPreSaleStarttime()); // 预售开始时间
            item.setPreSaleEndtime(itemOld.getPreSaleEndtime()); // 预售结束时间
            item.setTimingListing(itemOld.getTimingListing()); // 定时上架，为空则表示未设置定时上架
            item.setListtingTime(itemOld.getListtingTime()); // 上架时间
            item.setDelistingTime(itemOld.getDelistingTime()); // 下架时间
            item.setAttributes(itemOld.getAttributes());// 类目的属性
            item.setAttrSale(itemOld.getAttrSale()); // 京东商品销售属性keyId:valueId
        }
        item.setItemName(productPlusProductImportDTO.getItemName());// 商品名称 itemName
        item.setModelType(productPlusProductImportDTO.getModelType()); // 型号 modelType
        item.setWeightUnit(productPlusProductImportDTO.getWeightUnit());// 商品毛重
        item.setTaxRate(productPlusProductImportDTO.getTaxRate());// 税率
        item.setWeight(productPlusProductImportDTO.getWeight());// 商品毛重
        item.setNetWeight(productPlusProductImportDTO.getNetWeight()); // 净重
        item.setLength(productPlusProductImportDTO.getLength());// 长
        item.setWidth(productPlusProductImportDTO.getWidth());
        item.setHeight(productPlusProductImportDTO.getHeight());
        item.setAd(productPlusProductImportDTO.getAd());
        item.setOrigin(productPlusProductImportDTO.getOrigin());
        item.setItemQualification(productPlusProductImportDTO.getItemQualification()); // 商品参数
        item.setProductChannelCode(ProductChannelEnum.JD_PRODUCT.getCode());
        item.setOuterChannelCategoryCode(productPlusProductImportDTO.getOuterChannelCategoryCode()); // 外接渠道品类编码
        item.setOuterChannelBrandCode(productPlusProductImportDTO.getOuterChannelBrandCode()); // 外接渠道品牌编码
        item.setItemPictureUrl(productPlusProductImportDTO.getItemPictureUrl()); // 商品主图URL
        item.setPackingList(productPlusProductImportDTO.getPackingList()); // 包装清单
        item.setAfterService(productPlusProductImportDTO.getAfterService()); // 售后服务
        item.setKeywords(productPlusProductImportDTO.getKeywords()); // 关键字
        /** 京东默认参数 **/
        item.setSellerId(JD_SELLER_ID);// 商家ID
        item.setShopId(JD_SHOP_ID); // 店铺ID
        item.setShopCid(0L); // 商品所属店铺类目id
        item.setCreated(new Date());
        item.setCreateId(JD_CREATE_ID); // 创建人ID
        item.setCreateName(JD_CREATE_NAME); // 创建人
        item.setModifyId(JD_CREATE_ID);
        item.setModifyName(JD_CREATE_NAME);
        item.setModified(new Date() );
        return item;
    }

    /**
     * 根据dto创建sku记录
     * @param productPlusProductImportDTO 参数
     * @param itemId
     * @return sku记录
     */
    private ItemSku createItemSkuFromDTO(ProductPlusProductImportDTO productPlusProductImportDTO, Long itemId, boolean isUpdate, ItemSku itemSkuOld) {
        ItemSku itemSku = new ItemSku();
        itemSku.setItemId(itemId);
        if (!isUpdate) { // 新增
            itemSku.setSkuCode(ItemCodeGenerator.generateSkuCode());
            itemSku.setSubTitle(""); // 商品副标题
            itemSku.setSkuStatus(1); // sku 状态,1:有效;0:无效
            itemSku.setSkuType(1); // sku 类型 1:主sku,2:非主sku
            itemSku.setAd(productPlusProductImportDTO.getAd());// 广告语
            itemSku.setAttributes(""); // 京东没有属性
            itemSku.setSkuErpCode("");
        } else { // 修改
            itemSku.setSubTitle(itemSkuOld.getSubTitle()); // 商品副标题
            itemSku.setSkuStatus(itemSkuOld.getSkuStatus()); // sku 状态,1:有效;0:无效
            itemSku.setSkuType(itemSkuOld.getSkuType()); // sku 类型 1:主sku,2:非主sku
            itemSku.setAd(itemSkuOld.getAd());// 广告语
            itemSku.setAttributes(itemSkuOld.getAttributes()); // 京东没有属性
            itemSku.setSkuErpCode(itemSkuOld.getSkuErpCode());
        }
        itemSku.setOuterSkuId(productPlusProductImportDTO.getOuterSkuId());
        itemSku.setSellerId(JD_SELLER_ID);
        itemSku.setShopId(JD_SHOP_ID);
        itemSku.setCreated(new Date());
        itemSku.setCreateId(JD_CREATE_ID);
        itemSku.setCreateName(JD_CREATE_NAME);
        itemSku.setModifyId(JD_CREATE_ID);
        itemSku.setModifyName(JD_CREATE_NAME);
        return itemSku;
    }

    /**
     * 根据dto转换成ItemPicture
     * @param productPlusPictureImportDTO
     * @return ItemPicture
     */
    private ItemPicture createItemPictureFromDTO(ProductPlusPictureImportDTO productPlusPictureImportDTO, Long itemId, Integer sortNumber) throws Exception {
        String aliCloudUri = uploadJdPictureToAliCloud(productPlusPictureImportDTO.getPictureUrl());
        ItemPicture itemPicture = new ItemPicture();
        itemPicture.setItemId(itemId);// 商品id
        itemPicture.setPictureUrl(aliCloudUri);
        itemPicture.setIsFirst(productPlusPictureImportDTO.getIsFirst());
        itemPicture.setSortNumber(sortNumber);
        itemPicture.setDeleteFlag(0);
        itemPicture.setPictureStatus(1);
        itemPicture.setSellerId(JD_SELLER_ID);
        itemPicture.setShopId(JD_SHOP_ID);
        itemPicture.setCreateId(JD_CREATE_ID);
        itemPicture.setCreateName(JD_CREATE_NAME);
        itemPicture.setCreated(new Date());
        return itemPicture;
    }

    /**
     * 转换京东的图片到阿里云
     * @param jdUrl
     * @return
     * @throws Exception
     */
    private String uploadJdPictureToAliCloud(String jdUrl) throws Exception {
        // 阿里云登录验证，已经图片命名空间
        String key = "";
        OSSClient client = null;
        InputStream inputStream = null;
        try {
            String accessId = SysProperties.getProperty("access_keyid");
            String accessKey = SysProperties.getProperty("access_keysecret");
            String endPoint = SysProperties.getProperty("endpoint");
            String bucketName = SysProperties.getProperty("bucket_name");
            // 京东图片地址
            URL url = new URL("http://img13.360buyimg.com/n1/" + jdUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(4000);
            inputStream = urlConnection.getInputStream();
            int fileSize = urlConnection.getContentLength();
            String fileExt = null;
            if (jdUrl.indexOf(".") != -1) {
                fileExt = jdUrl.substring(jdUrl.indexOf("."));
            }
            key = UUID.randomUUID().toString() + fileExt; // 组成图片URL唯一标识
            // 创建连接
            client = new OSSClient(endPoint, accessId, accessKey);
            // 设置文件格式
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(fileSize);
            meta.setContentType("image/*");
            // 上传文件到云服务器， key ：通过key去命名空间寻找文件
            client.putObject(bucketName, key, inputStream, meta);
            if (!endPoint.endsWith("/")) {
                key = "/" + key;
            }
        } catch (Exception e) {
            logger.error("上传京东图片到阿里云失败, 错误信息 : ", e);
            key = jdUrl; //上传失败，默认返回京东url；然后走任务补偿
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (client != null) {
                client.shutdown();
            }
        }
        logger.info("上传京东图片到阿里云成功: SUCCESS");
        return key;
    }

    /**
     * 根据dto创建价格DTO
     * @param productPlusProductImportDTO 参数
     * @param skuId
     * @param itemId
     * @return
     */
    private ItemSkuBasePrice createItemPriceFromDTO(ProductPlusProductImportDTO productPlusProductImportDTO, Long skuId, Long itemId) {
        ItemSkuBasePrice itemSkuBasePrice = new ItemSkuBasePrice();
        itemSkuBasePrice.setSkuId(skuId);
        itemSkuBasePrice.setItemId(itemId);
        itemSkuBasePrice.setSellerId(JD_SELLER_ID);
        itemSkuBasePrice.setShopId(JD_SHOP_ID);
        itemSkuBasePrice.setItemCode(productPlusProductImportDTO.getOuterSkuId()); // 外接商品skuId
        itemSkuBasePrice.setCategoryId(productPlusProductImportDTO.getCategoryId());
        itemSkuBasePrice.setBrandId(productPlusProductImportDTO.getBrandId());
        itemSkuBasePrice.setChannelCode(ProductChannelEnum.JD_PRODUCT.getCode());
        itemSkuBasePrice.setCostPrice(productPlusProductImportDTO.getCostPrice()); // 外部供货价
        itemSkuBasePrice.setSaleLimitedPrice(productPlusProductImportDTO.getSaleLimitedPrice()); // 外部销售价
        itemSkuBasePrice.setLastPriceSyncTime(new Date()); // 上次价格同步时间
        itemSkuBasePrice.setCreateId(JD_CREATE_ID);
        itemSkuBasePrice.setCreateName(JD_CREATE_NAME);
        itemSkuBasePrice.setCreateTime(new Date());
        return itemSkuBasePrice;
    }


    /**
     * 根据外部渠道类目编码 去映射表 查询我司类目编码
     * @param productPlusProductImportDTO 参数
     * @return 我司类目编码
     */
    private Long getCategoryIdByCode(ProductPlusProductImportDTO productPlusProductImportDTO) {
        Long cid = 0L;
        try {
            OuterChannelCategoryMapping outerChannelCategoryMapping = new OuterChannelCategoryMapping();
            outerChannelCategoryMapping.setChannelCode(ProductChannelEnum.JD_PRODUCT.getCode());
            outerChannelCategoryMapping.setOuterChannelCategoryCode(productPlusProductImportDTO.getOuterChannelCategoryCode());
            outerChannelCategoryMapping.setDeleteFlag(0);
            outerChannelCategoryMapping = this.outerChannelCategoryMappingMapper.select(outerChannelCategoryMapping);
            if (outerChannelCategoryMapping != null) {
                cid =  outerChannelCategoryMapping.getCategoryId(); // 类目ID
            }
        } catch (Exception e) {
            logger.error("getCategoryIdByCode error:", e);
        }
        return cid;
    }

    /**
     * 根据外部渠道品牌编码 去映射表 查询我司品牌编码
     * @param productPlusProductImportDTO 参数
     * @return 我司品牌编码
     */
    private Long getBrandIdByCode(ProductPlusProductImportDTO productPlusProductImportDTO) {
        Long brandId = 0L;
        try {
            OuterChannelBrandMapping outerChannelBrandMapping = new OuterChannelBrandMapping();
            outerChannelBrandMapping.setChannelCode(ProductChannelEnum.JD_PRODUCT.getCode());
            outerChannelBrandMapping.setOuterChannelBrandCode(productPlusProductImportDTO.getOuterChannelBrandCode());
            outerChannelBrandMapping.setDeleteFlag(0);
            outerChannelBrandMapping = this.outerChannelBrandMappingMapper.select(outerChannelBrandMapping);
            if (outerChannelBrandMapping != null) {
                brandId = outerChannelBrandMapping.getBrandId();
            }
        } catch (Exception e) {
            logger.error("getBrandIdByCode error:", e);
        }
        return brandId;
    }

}
