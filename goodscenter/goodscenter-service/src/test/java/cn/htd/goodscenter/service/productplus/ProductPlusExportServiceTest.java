package cn.htd.goodscenter.service.productplus;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import cn.htd.common.util.SysProperties;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.dto.productplus.*;
import cn.htd.goodscenter.service.listener.JdSupplyProductPriceServiceImpl;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.common.ProductChannel;
import cn.htd.goodscenter.domain.productplus.OuterChannelBrandMapping;
import cn.htd.goodscenter.domain.productplus.OuterChannelCategoryMapping;
import cn.htd.goodscenter.domain.productplus.SellerCategoryBrandShield;
import cn.htd.goodscenter.service.listener.JdProductDataImportServiceImpl;
import cn.htd.goodscenter.test.common.CommonTest;

/**
 * Created by chenakng on 2016/11/26.
 */
public class ProductPlusExportServiceTest extends CommonTest {

    @Resource
    private ProductPlusExportService productPlusExportService;

    @Autowired
//    @Qualifier("")
    private JdProductDataImportServiceImpl productPlusImportService;

    @Test
    public void testQueryOuterProductChannelList() {
        ExecuteResult<List<ProductChannel>> executeResult = productPlusExportService.queryOuterProductChannelList();
        System.out.println(executeResult.getResult());
        Assert.assertEquals(true, executeResult.isSuccess());
    }

    @Test
    public void testQueryMappedOuterChannelCategoryList() {
        OuterChannelCategoryMapping outerChannelCategoryMapping = new OuterChannelCategoryMapping();
        outerChannelCategoryMapping.setChannelCode("3010");
//        outerChannelCategoryMapping.setCategoryId(11L);
        outerChannelCategoryMapping.setFirstCategoryId(7l);
//        outerChannelCategoryMapping.setSecondCategoryId(9l);
        Pager pager = new Pager();
        pager.setPage(1);
        pager.setRows(10);
        ExecuteResult<DataGrid<OuterChannelCategoryMapping>> executeResult =
                productPlusExportService.queryMappedOuterChannelCategoryList(outerChannelCategoryMapping, pager);
        System.out.println(executeResult.getErrorMessages().toString());
        Assert.assertEquals(true, executeResult.isSuccess());
        System.out.println(executeResult.getResult().getRows());
    }

    @Test
    public void testQueryNoMappedOuterChannelCategoryList() {
        OuterChannelCategoryMapping outerChannelCategoryMapping = new OuterChannelCategoryMapping();
        outerChannelCategoryMapping.setChannelCode("3010");
        outerChannelCategoryMapping.setOuterChannelCategoryName("1");
        Pager pager = new Pager();
        pager.setPage(1);
        pager.setRows(10);
        ExecuteResult<DataGrid<OuterChannelCategoryMapping>> executeResult =
                productPlusExportService.queryNoMappedOuterChannelCategoryList(outerChannelCategoryMapping, pager);
        System.out.println(executeResult.getErrorMessages().toString());
        Assert.assertEquals(true, executeResult.isSuccess());
        System.out.println(executeResult.getResult().getRows());
    }

    @Test
    public void testAddOrModifyOuterChannelCategoryMapping1() {
        // modify
        OuterChannelCategoryMapping outerChannelCategoryMapping = new OuterChannelCategoryMapping();
        outerChannelCategoryMapping.setCategoryMappingId(64L);
        outerChannelCategoryMapping.setCategoryId(12L);
        outerChannelCategoryMapping.setModifyId(10000L);
        outerChannelCategoryMapping.setModifyName("ck");
        ExecuteResult<String> executeResult = productPlusExportService.addOrModifyOuterChannelCategoryMapping(outerChannelCategoryMapping);
        Assert.assertTrue(executeResult.isSuccess());
    }

    @Test
    public void testQueryMappedOuterChannelBrandList() {
        OuterChannelBrandMapping outerChannelBrandMapping = new OuterChannelBrandMapping();
        outerChannelBrandMapping.setChannelCode("3010");
        outerChannelBrandMapping.setBrandName("1");
//        outerChannelCategoryMapping.setCategoryId(111L);
        Pager pager = new Pager();
        pager.setPage(1);
        pager.setRows(10);
        ExecuteResult<DataGrid<OuterChannelBrandMapping>> executeResult =
                productPlusExportService.queryMappedOuterChannelBrandList(outerChannelBrandMapping, pager);
        System.out.println(executeResult.getErrorMessages().toString());
        Assert.assertEquals(true, executeResult.isSuccess());
        System.out.println("条数" + executeResult.getResult().getTotal());
        System.out.println("结果" + executeResult.getResult().getRows());
    }

    @Test
    public void testQueryNoMappedOuterChannelBrandList() {
        OuterChannelBrandMapping outerChannelBrandMapping = new OuterChannelBrandMapping();
        outerChannelBrandMapping.setChannelCode("3010");
//        outerChannelBrandMapping.setOuterChannelBrandName("1");
        outerChannelBrandMapping.setBrandName("4");
        Pager pager = new Pager();
        pager.setPage(1);
        pager.setRows(10);
        ExecuteResult<DataGrid<OuterChannelBrandMapping>> executeResult =
                productPlusExportService.queryNoMappedOuterChannelBrandList(outerChannelBrandMapping, pager);
        Assert.assertEquals(true, executeResult.isSuccess());
        System.out.println("条数" + executeResult.getResult().getTotal());
        System.out.println("结果" + executeResult.getResult().getRows());
    }

    @Test
    public void testAddOrModifyOuterChannelBrandMapping1() {
        // modify
        OuterChannelBrandMapping outerChannelBrandMapping = new OuterChannelBrandMapping();
        outerChannelBrandMapping.setBrandMappingId(62L);
        outerChannelBrandMapping.setBrandId(8L);
        outerChannelBrandMapping.setModifyId(10000L);
        outerChannelBrandMapping.setModifyName("ck");
        ExecuteResult<String> executeResult = productPlusExportService.addOrModifyOuterChannelBrandMapping(outerChannelBrandMapping);
        Assert.assertTrue(executeResult.isSuccess());
    }

    @Test
    public void testQuerySellerOuterProductChannel() {
        SellerOuterProductChannelDTO sellerOuterProductChannelDTO = new SellerOuterProductChannelDTO();
//        sellerOuterProductChannelDTO.setChannelCode("0");
        Pager pager = new Pager();
        pager.setPage(1);
        pager.setRows(10);
        ExecuteResult<DataGrid<SellerOuterProductChannelDTO>> executeResult = productPlusExportService.querySellerOuterProductChannelList(sellerOuterProductChannelDTO, pager);
        System.out.println("数量" + executeResult.getResult().getTotal());
        System.out.println(executeResult.getResult().getRows());
    }

    @Test
    public void testQuerySellerOuterProductChannelDetail() {
        SellerOuterProductChannelDTO sellerOuterProductChannelDTO = new SellerOuterProductChannelDTO();
        sellerOuterProductChannelDTO.setSellerId(1L);
        ExecuteResult<SellerOuterProductChannelDTO> executeResult = productPlusExportService.querySellerOuterProductChannelDetail(sellerOuterProductChannelDTO);
        System.out.println(executeResult.getResult());
    }

    @Test
    public void testBatchAddSellerOuterProductChannel() {
        List<SellerOuterProductChannelDTO> sellerOuterProductChannels = new ArrayList<>();
        Long sellerId = 2000000L;
        for (int i = 0 ; i < 10; i ++) {
            SellerOuterProductChannelDTO sellerOuterProductChannel = new SellerOuterProductChannelDTO();
            sellerOuterProductChannel.setSellerId(++sellerId);
            sellerOuterProductChannel.setChannelName("京东");
            sellerOuterProductChannel.setCreateId(1000000L);
            sellerOuterProductChannel.setCreateName("ck");
            sellerOuterProductChannel.setAccessStatus(1);
            sellerOuterProductChannels.add(sellerOuterProductChannel);
        }
        ExecuteResult<SellerOuterProductChannelImportResultDTO> executeResult = productPlusExportService.batchAddSellerOuterProductChannel(sellerOuterProductChannels);
        System.out.println(executeResult.getResult());
        Assert.assertEquals(true, executeResult.isSuccess());
    }

    @Test
    public void testQueryProductPlushItemList() {
        ProductPlusItemDTO productPlusItemDTO = new ProductPlusItemDTO();
        productPlusItemDTO.setProductChannelCode("3010");//
//        productPlusItemDTO.setItemCode("10013589");
//        productPlusItemDTO.setItemName("奥克斯（AUX）3匹 冷暖 定速 空调柜机(KFR-72LW/NSP1+3)");
        productPlusItemDTO.setBrandName("海尔");
//        productPlusItemDTO.setSkuCode("1000018848");
//        productPlusItemDTO.setFirstCategoryId(14L);
        Pager pager = new Pager();
        pager.setPage(1);
        pager.setRows(10);
        ExecuteResult<DataGrid<ProductPlusItemDTO>> executeResult = productPlusExportService.queryProductPlushItemList(productPlusItemDTO, pager);
        System.out.println(executeResult.getResult().getRows());
        System.out.println("数量：" + executeResult.getResult().getTotal());
        Assert.assertEquals(true, executeResult.isSuccess());
    }

    @Test
    public void testQueryProductPlushItemDetail() {
        ExecuteResult<ProductPlusItemDTO> executeResult = productPlusExportService.queryProductPlushItemDetail(9047l);
        System.out.println(executeResult.getResult());
        Assert.assertEquals(true, executeResult.isSuccess());
    }

    @Test
    public void testDownShelfProductPlushItem() {
        ExecuteResult executeResult = productPlusExportService.downShelfProductPlushItem(1000029569L);//1000018849
        Assert.assertTrue(executeResult.isSuccess());
    }


    @Test
    public void testBatchDownShelfProductPlushItem() {
        Long[] longs = {1000018848l, 1000018849l};
        ExecuteResult executeResult = productPlusExportService.batchDownShelfProductPlushItem(Arrays.asList(longs));
        Assert.assertTrue(executeResult.isSuccess());
    }

    // 批量上架
    @Test
    public void testBatchUpShelfProductPlushItem() {
        List<ProductPlusItemDTO> productPlusItemDTOs = new ArrayList();
        ProductPlusItemDTO p1 = new ProductPlusItemDTO();
        p1.setItemId(1000025665L);
        p1.setSkuId(1000036189L);
//        ProductPlusItemDTO p2 = new ProductPlusItemDTO();
//        p2.setItemId(1000018849L);
//        p2.setSkuId(1000029374L);
        productPlusItemDTOs.add(p1);
//        productPlusItemDTOs.add(p2);
        ProductPlusItemUpShelfSettingDTO productPlusItemUpShelfSettingDTO = new ProductPlusItemUpShelfSettingDTO();
        productPlusItemUpShelfSettingDTO.setAd("yoyoy1");
        productPlusItemUpShelfSettingDTO.setCommissionRatio(new BigDecimal("0.1"));
        productPlusItemUpShelfSettingDTO.setPriceFloatingRatio(new BigDecimal("1"));
        productPlusItemUpShelfSettingDTO.setRetailPriceFloatingRatio(new BigDecimal("1.5"));
        productPlusItemUpShelfSettingDTO.setIsPreSale(1);
        productPlusItemUpShelfSettingDTO.setVipPriceFloatingRatio("1");
        productPlusItemUpShelfSettingDTO.setPreSaleStarttime("2016-12-20");
        productPlusItemUpShelfSettingDTO.setPreSaleEndtime("2016-12-20");
        ExecuteResult executeResult = productPlusExportService.batchUpShelfProductPlushItem(productPlusItemDTOs, productPlusItemUpShelfSettingDTO);
        System.out.println("+++++++++++++++++++++++++" + executeResult.getErrorMessages());
        Assert.assertTrue(executeResult.isSuccess());
    }

    @Test
    public void testJdProductListener() {
//        List<ProductPlusProductImportDTO> productPlusProductImportDTOList = new ArrayList();
//        for (int i = 0 ; i < 10 ; i++) {
            ProductPlusProductImportDTO productPlusProductImportDTO = new ProductPlusProductImportDTO();
            productPlusProductImportDTO.setItemName("京东导入测试商品");
            productPlusProductImportDTO.setOuterItemStatus("2"); // 已上架
            productPlusProductImportDTO.setOuterChannelCategoryCode("1001");
            productPlusProductImportDTO.setOuterChannelBrandCode("1001");
            productPlusProductImportDTO.setModelType("型号");
            productPlusProductImportDTO.setWeightUnit("个");
            productPlusProductImportDTO.setWeight(new BigDecimal("10"));
            productPlusProductImportDTO.setTaxRate(new BigDecimal("0.1"));
            productPlusProductImportDTO.setWeight(new BigDecimal("10"));
            productPlusProductImportDTO.setNetWeight(new BigDecimal("10"));
            productPlusProductImportDTO.setLength(new BigDecimal("10"));
            productPlusProductImportDTO.setWidth(new BigDecimal("10"));
            productPlusProductImportDTO.setHeight(new BigDecimal("10"));
            productPlusProductImportDTO.setAd("广告");
            productPlusProductImportDTO.setOrigin("中国");
            productPlusProductImportDTO.setItemQualification("商品参数");
            productPlusProductImportDTO.setItemPictureUrl("/zhutu/pic/sss");
            productPlusProductImportDTO.setPackingList("清单123hkasd");
            productPlusProductImportDTO.setAfterService("售后服务");
            productPlusProductImportDTO.setKeywords("电器");
            productPlusProductImportDTO.setOuterSkuId("2364698");
            productPlusProductImportDTO.setOuterChannelCategoryName("京东类目2");
            productPlusProductImportDTO.setOuterChannelBrandName("京东品牌2");
            productPlusProductImportDTO.setCostPrice(new BigDecimal("250"));
            productPlusProductImportDTO.setSaleLimitedPrice(new BigDecimal("260"));

//            productPlusProductImportDTOList.add(productPlusProductImportDTO);
//        }
//        final ObjectMapper mapper = new ObjectMapper();
//        try {
//            String value = mapper.writeValueAsString(productPlusProductImportDTO);
////            System.out.println(value);
        try {
            productPlusImportService.importData(productPlusProductImportDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Test
    public void testQuerySellerShieldCategoryBrandList() {
        SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO = new SellerCategoryBrandShieldDTO();
        sellerCategoryBrandShieldDTO.setSellerId(1L);
        sellerCategoryBrandShieldDTO.setShopId(1L);
        sellerCategoryBrandShieldDTO.setChannelCode("3010");
        ExecuteResult<DataGrid<SellerCategoryBrandShieldDTO>> result = this.productPlusExportService.querySellerShieldCategoryBrandList(sellerCategoryBrandShieldDTO, null);
        System.out.println(result.getResult().getRows());
    }

    /** vms **/
    @Test
    public void testQuerySellerNoShieldCategoryBrandList() {
        SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO = new SellerCategoryBrandShieldDTO();
        sellerCategoryBrandShieldDTO.setSellerId(1L);
        sellerCategoryBrandShieldDTO.setChannelCode("3010");
        Pager pager = new Pager();
        ExecuteResult<DataGrid<SellerCategoryBrandShieldDTO>>  executeResult = this.productPlusExportService.querySellerNoShieldCategoryBrandList(sellerCategoryBrandShieldDTO, pager);
        System.out.println("结果：" + executeResult.getResult().getRows());

    }

    @Test
    public void testQuerySellerProductPlusProductList() {
        ProductPlusItemDTO productPlusItemDTO = new ProductPlusItemDTO();
        productPlusItemDTO.setSellerId(197673L);
//        productPlusItemDTO.setBrandName("3");
//        productPlusItemDTO.setFirstCategoryId(1L);
//        productPlusItemDTO.setSkuCode("796eafef-778c-4d0d-bd61-5f0443d469e3");
        productPlusItemDTO.setProductChannelCode("3010");
        Pager pager = new Pager();
        ExecuteResult<DataGrid<ProductPlusItemDTO>>  executeResult = this.productPlusExportService.querySellerProductPlusProductList(productPlusItemDTO, pager);
        System.out.println("结果：" + executeResult.getResult().getRows().size());

    }

    @Test
    public void testAddSellerOuterProductChannel() {
        SellerOuterProductChannelDTO sellerOuterProductChannel1 = new SellerOuterProductChannelDTO();
        sellerOuterProductChannel1.setSellerId(3L);
        sellerOuterProductChannel1.setChannelCodes("3010,3020");
        sellerOuterProductChannel1.setModifyId(10000l);
        sellerOuterProductChannel1.setModifyName("ck");
        ExecuteResult<String> executeResult = this.productPlusExportService.addSellerOuterProductChannel(sellerOuterProductChannel1);
        Assert.assertTrue(executeResult.isSuccess());
    }

    @Test
    public void testshieldCategoryBrand() {
        List<SellerCategoryBrandShield> sellerCategoryBrandShieldList = new ArrayList();
        SellerCategoryBrandShield sellerCategoryBrandShield = new SellerCategoryBrandShield();
        sellerCategoryBrandShield.setSellerId(5L);
        sellerCategoryBrandShield.setThirdCategoryId(11L);
        sellerCategoryBrandShield.setBrandId(6L);
        sellerCategoryBrandShield.setCreateId(10000l);
        sellerCategoryBrandShield.setChannelCode("3010");
        sellerCategoryBrandShield.setCreateName("ck");
        sellerCategoryBrandShieldList.add(sellerCategoryBrandShield);
        ExecuteResult<String> executeResult = this.productPlusExportService.shieldCategoryBrand(sellerCategoryBrandShieldList);
        Assert.assertTrue(executeResult.isSuccess());
    }

    @Test
    public void testcancelShieldCategoryBrand() {
        List<SellerCategoryBrandShield> sellerCategoryBrandShieldList = new ArrayList();
        SellerCategoryBrandShield sellerCategoryBrandShield = new SellerCategoryBrandShield();
        sellerCategoryBrandShield.setCategoryBrandShieldId(261L);
        sellerCategoryBrandShieldList.add(sellerCategoryBrandShield);
        ExecuteResult<String> executeResult = this.productPlusExportService.cancelShieldCategoryBrand(sellerCategoryBrandShieldList);
        Assert.assertTrue(executeResult.isSuccess());
    }


    @Test
    public void testqueryJdProduct4SupperBoss() throws ParseException {

        JdProductQueryInDTO jdProductQueryInDTO = new JdProductQueryInDTO();
        jdProductQueryInDTO.setPager(new Pager(1, 100));
        String startStr = "2017-04-12 16:00:00";
        String endStr = "2017-04-13 16:15:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = simpleDateFormat.parse(startStr);
        Date end = simpleDateFormat.parse(endStr);
        jdProductQueryInDTO.setStartTime(start);
        jdProductQueryInDTO.setEndTime(end);
//        jdProductQueryInDTO.setSkuCode("1000006752");
//        jdProductQueryInDTO.setIsPreSale(0);
        JdProductQueryOutDTO jdProductQueryOutDTO = this.productPlusExportService.queryJdProduct4SupperBoss(jdProductQueryInDTO);
        System.out.println(jdProductQueryOutDTO);
    }
    
    @Test
    public void testQueryProductPlusAccessInfoList(){
    	ProductPlusAccessInfoInDTO inParam=new ProductPlusAccessInfoInDTO();
    	inParam.setCurrentPage(1);
    	ProductPlusAccessInfoOutDTO out=productPlusExportService.queryProductPlusAccessInfoList(inParam);
    	Assert.assertNotNull(out);
    }



    @Test
    public void testcanProductPlusSaleBySeller(){
        ExecuteResult<Boolean> executeResult = productPlusExportService.canProductPlusSaleBySeller(1L, "3010", 11L, 8L);
        System.out.println(JSONObject.fromObject(executeResult));
    }


    @Test
    public void testisSellerAccessProductPlus(){
        ExecuteResult<Boolean> executeResult = productPlusExportService.isSellerAccessProductPlus(25L, "3010");
        System.out.println(JSONObject.fromObject(executeResult));
    }

    @Resource
    private ItemPictureDAO itemPictureDAO;

    @Test
    public void updateJdPicture() {
        // 查询所有京东图片
        List<ItemPicture> allJdPic = this.itemPictureDAO.selectAllJdPic();
        for (ItemPicture itemPicture : allJdPic) {
            String key = null;
            try {
                key = uploadJdPictureToAliCloud(itemPicture.getPictureUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(key);
            itemPicture.setIsFirst(1);
            itemPicture.setPictureUrl(key);
            if (!StringUtils.isEmpty(key)) {
                this.itemPictureDAO.update(itemPicture);
            }
            // 更新
        }
    }



    private String uploadJdPictureToAliCloud(String jdUrl) throws Exception {
        // 阿里云登录验证，已经图片命名空间
        String accessId = SysProperties.getProperty("access_keyid");
        String accessKey = SysProperties.getProperty("access_keysecret");
        String endPoint = SysProperties.getProperty("endpoint");
        String bucketName = SysProperties.getProperty("bucket_name");
        // 京东图片地址
        URL url = new URL("http://img13.360buyimg.com/n0/" + jdUrl);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        int fileSize = urlConnection.getContentLength();
        String fileExt = null;
        if (jdUrl.indexOf(".") != -1) {
            fileExt = jdUrl.substring(jdUrl.indexOf("."));
        }
        String key = UUID.randomUUID().toString() + fileExt; // 组成图片URL唯一标识
        try {
            // 创建连接
            OSSClient client = new OSSClient(endPoint, accessId, accessKey);
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
            key = "";
        }
        return key;
    }

    @Test
    public void testJdImport() throws Exception {
        String message = "{\"ad\":\"\",\"afterService\":\"\",\"brandId\":0,\"categoryId\":0,\"costPrice\":288,\"height\":84,\"itemName\":\"西铁城（CITIZEN）电子血压计 家用全自动腕式血压计CH-650（便携款）\",\"itemPictureUrl\":\"http://img13.360buyimg.com/n0/jfs/t1312/122/765781107/154770/b1f6d2c8/55a4cd98Naee43dae.jpg\",\"itemQualification\":\"\",\"keywords\":\"\",\"length\":130,\"modelType\":\"CH-650\",\"netWeight\":0.26,\"origin\":\"中国广州\",\"outerChannelBrandCode\":\"17907\",\"outerChannelBrandName\":\"西铁城\",\"outerChannelCategoryCode\":\"12189\",\"outerChannelCategoryName\":\"血压计\",\"outerItemStatus\":\"1\",\"outerSkuId\":\"737438\",\"packingList\":\"1\",\"productPlusPictureImportDTOList\":[{\"isFirst\":1,\"pictureUrl\":\"jfs/t1312/122/765781107/154770/b1f6d2c8/55a4cd98Naee43dae.jpg\"},{\"isFirst\":0,\"pictureUrl\":\"jfs/t1396/349/683612236/177281/1579d91a/55a4cd98Nfaba61e1.jpg\"},{\"isFirst\":0,\"pictureUrl\":\"jfs/t1417/73/702889857/128128/f36fca04/55a4cd99N46c66b48.jpg\"},{\"isFirst\":0,\"pictureUrl\":\"jfs/t1396/353/663224508/122133/e6a5988e/55a4cd99N23b9f552.jpg\"},{\"isFirst\":0,\"pictureUrl\":\"jfs/t1399/336/686518055/85395/9d64b4e5/55a4cd9aNa237db39.jpg\"}],\"saleLimitedPrice\":265.05,\"taxRate\":0,\"weight\":0.26,\"weightUnit\":\"\",\"width\":95}";
        /** 解析数据 **/
        ProductPlusProductImportDTO productPlusProductImportDTO = this.parseMessage(message);
        this.productPlusImportService.importData(productPlusProductImportDTO);
    }

    private ProductPlusProductImportDTO parseMessage(String message) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return mapper.readValue(message, ProductPlusProductImportDTO.class);
    }

    @Autowired
    private JdSupplyProductPriceServiceImpl jdSupplyProductPriceService;

    @Test
    public void testJdSupplyProductPriceServiceImpl() {
        ProductPlusPriceSupplyDTO productPlusPriceSupplyDTO = new ProductPlusPriceSupplyDTO();

        productPlusPriceSupplyDTO.setOuterSkuId("3133823");// 1000007797
        productPlusPriceSupplyDTO.setCostPrice(new BigDecimal("5700"));
        productPlusPriceSupplyDTO.setSaleLimitedPrice(new BigDecimal("5700"));
        try {
            jdSupplyProductPriceService.modifyPrice(productPlusPriceSupplyDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


