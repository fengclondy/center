package cn.htd.goodscenter.service.mall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.mall.*;
import cn.htd.membercenter.dto.MemberShipDTO;
import cn.htd.membercenter.service.BoxRelationshipService;
import com.alibaba.dubbo.common.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.indto.QueryNewPublishItemInDTO;
import cn.htd.goodscenter.test.common.CommonTest;

import com.google.common.collect.Lists;

/**
 * Created by admin on 2017/1/12.
 */
public class MallItemExportServiceTest extends CommonTest {

    @Qualifier("mallItemExportService")
    @Autowired
    private MallItemExportService mallItemExportService;

    @Test
    public void testQueryCartItemList() {
        List<MallSkuInDTO> mallSkuInDTOList = new ArrayList<>();
        MallSkuInDTO mallSkuInDTO = new MallSkuInDTO(); // 京东加商品
        mallSkuInDTO.setSkuCode("1000009329");
        mallSkuInDTOList.add(mallSkuInDTO);


        ExecuteResult<List<MallSkuOutDTO>> executeResult = this.mallItemExportService.queryCartItemList(mallSkuInDTOList);
        Assert.assertTrue(executeResult.isSuccess());
        System.out.println(JSONArray.fromObject(executeResult.getResult()));
    }
    
    @Test
    public void testQueryNewPublishItemList_inner(){
    	QueryNewPublishItemInDTO queryNewPublishItemInDTO =new QueryNewPublishItemInDTO();
    	queryNewPublishItemInDTO.setCount(12);
    	queryNewPublishItemInDTO.setCurrentSiteCode("020");
    	queryNewPublishItemInDTO.setIsBoxFlag("1");
    	queryNewPublishItemInDTO.setItemCode("HTDH_20000015");
    	queryNewPublishItemInDTO.setShopId(2000000354L);
    	queryNewPublishItemInDTO.setSupplierId(123L);
    	queryNewPublishItemInDTO.setSupplierType("0");
    	queryNewPublishItemInDTO.setIsLoginFlag("1");
    	mallItemExportService.queryNewPublishItemList(queryNewPublishItemInDTO);
    }
    
    @Test
    public void testQueryNewPublishItemList_outer(){
    	QueryNewPublishItemInDTO queryNewPublishItemInDTO =new QueryNewPublishItemInDTO();
    	queryNewPublishItemInDTO.setCount(5);
    	queryNewPublishItemInDTO.setCurrentSiteCode("1");
    	queryNewPublishItemInDTO.setIsBoxFlag("0");
    	queryNewPublishItemInDTO.setItemCode("1");
    	queryNewPublishItemInDTO.setShopId(0L);
    	queryNewPublishItemInDTO.setSupplierId(1L);
    	queryNewPublishItemInDTO.setSupplierType("1");
    	mallItemExportService.queryNewPublishItemList(queryNewPublishItemInDTO);
    }
    
    @Test
    public void testQueryMallItemDetail() {
        MallSkuInDTO mallSkuInDTO = new MallSkuInDTO();
//        mallSkuInDTO.setItemCode("HTDH_2000001福润德v5");
        mallSkuInDTO.setSkuCode("1000009363");
        ExecuteResult<MallSkuOutDTO>  executeResult = this.mallItemExportService.queryMallItemDetail(mallSkuInDTO);
        System.out.println(executeResult.getResult());
    }

    @Test
    public void testQueryMallItemDetailPage() {
        MallSkuInDTO mallSkuInDTO = new MallSkuInDTO();
        mallSkuInDTO.setItemCode("10025196");
//        mallSkuInDTO.setSkuCode("HTDH_100000001");
        ExecuteResult<MallSkuOutDTO>  executeResult = this.mallItemExportService.queryMallItemDetailPage(mallSkuInDTO);
        System.out.println(executeResult.getResult());
    }

    @Test
    public void testQueryMallItemDetailWithStock() {
        MallSkuWithStockInDTO mallSkuWithStockInDTO = new MallSkuWithStockInDTO();
        mallSkuWithStockInDTO.setSkuCode("1000024839");
        mallSkuWithStockInDTO.setIsBoxFlag(0);
        mallSkuWithStockInDTO.setCityCode("3201");
        ExecuteResult<MallSkuWithStockOutDTO> executeResult = this.mallItemExportService.queryMallItemDetailWithStock(mallSkuWithStockInDTO);
        System.out.println(executeResult.getResult());
    }

    @Test
    public void testQueryMallItemDetailWithStockList() {
//        1000013707
//        1000013705
//        1000013706
//        1000013703
//        1000013704
//        1000013702
//        1000013701
//        1000013700
//        1000013698
        List<MallSkuWithStockInDTO> mallSkuWithStockInDTOList = new ArrayList<>();

        MallSkuWithStockInDTO mallSkuWithStockInDTO1 = new MallSkuWithStockInDTO();
        mallSkuWithStockInDTO1.setSkuCode("1000009272");
        mallSkuWithStockInDTO1.setIsBoxFlag(0);
        mallSkuWithStockInDTO1.setCityCode("1");
//
//        MallSkuWithStockInDTO mallSkuWithStockInDTO2 = new MallSkuWithStockInDTO();
//        mallSkuWithStockInDTO2.setSkuCode("1000013705");
//        mallSkuWithStockInDTO2.setIsBoxFlag(0);
//        mallSkuWithStockInDTO2.setCityCode("1");
//
//        MallSkuWithStockInDTO mallSkuWithStockInDTO3 = new MallSkuWithStockInDTO();
//        mallSkuWithStockInDTO3.setSkuCode("1000013706");
//        mallSkuWithStockInDTO3.setIsBoxFlag(0);
//        mallSkuWithStockInDTO3.setCityCode("1");
//
//        MallSkuWithStockInDTO mallSkuWithStockInDTO4 = new MallSkuWithStockInDTO();
//        mallSkuWithStockInDTO4.setSkuCode("1000013703");
//        mallSkuWithStockInDTO4.setIsBoxFlag(0);
//        mallSkuWithStockInDTO4.setCityCode("1");

        mallSkuWithStockInDTOList.add(mallSkuWithStockInDTO1);
//        mallSkuWithStockInDTOList.add(mallSkuWithStockInDTO2);
//        mallSkuWithStockInDTOList.add(mallSkuWithStockInDTO3);
//        mallSkuWithStockInDTOList.add(mallSkuWithStockInDTO4);

        ExecuteResult<List<MallSkuWithStockOutDTO>> executeResult = this.mallItemExportService.queryMallItemDetailWithStockList(mallSkuWithStockInDTOList);
        System.out.println(executeResult.getResult());
    }

    @Test
    public void testQueryMallItemStockInfo() {
        MallSkuStockInDTO mallSkuStockInDTO = new MallSkuStockInDTO();
        mallSkuStockInDTO.setSkuCode("HTDH_100000072");
        mallSkuStockInDTO.setIsBoxFlag(1);
        mallSkuStockInDTO.setProductChannelCode("10");
        ExecuteResult<MallSkuStockOutDTO>  executeResult = this.mallItemExportService.queryMallItemStockInfo(mallSkuStockInDTO);
        Assert.assertTrue(executeResult.isSuccess());
        System.out.println(executeResult.getResult());
    }

    @Test
    public void testQueryFavouriteItemInfo() {
        ItemFavouriteOutDTO itemFavouriteOutDTO = this.mallItemExportService.queryFavouriteItemInfo(1000000579L);
        System.out.println(itemFavouriteOutDTO);
    }
    
    @Test
    public void testQueryMallSearchItemInfo(){
    	List<String> itemIdList=Lists.newArrayList();
    	itemIdList.add("1000018838");
    	itemIdList.add("1000018839");
    	ExecuteResult<List<MallSearchItemDTO>> re =mallItemExportService.queryMallSearchItemInfo(itemIdList);
    	if(re.isSuccess()){
    		re.getResult();
    	}
    }

    @Test
    public void testchoiceMallItemBoxFlagAndStockInfo() {
        MallChoiceBoxProductInDTO mallSkuStockInDTO = new MallChoiceBoxProductInDTO();
        mallSkuStockInDTO.setSkuCode("1000013662");
        mallSkuStockInDTO.setCityCode("3201");
        mallSkuStockInDTO.setIsHasRelation(0);
        mallSkuStockInDTO.setIsLogin(0);
        mallSkuStockInDTO.setProductChannelCode("10");
        mallSkuStockInDTO.setSupplierCode("htd216511");
        this.mallItemExportService.choiceMallItemBoxFlagAndStockInfo(mallSkuStockInDTO);
    }
    
    @Test
    public void testIsBoxItem(){
    	mallItemExportService.isBoxProduct(1000038714L, "4115");
    }


    @Test
    public void testqueryBasicItemSkuInfoList() throws Exception {
        List<String> skuCodeList = new ArrayList();
        skuCodeList.add("1000006866");
//                "1000006867\n" +
//                "1000006868");
//        mallItemExportService.queryBasicItemSkuInfoList(skuCodeList);
    }

    @Autowired
    private BoxRelationshipService boxRelationshipService;

    @Test
    public void testqueryRecommendItemList() {
        MallRecommendItemInDTO mallRecommendItemInDTO = new MallRecommendItemInDTO();
        mallRecommendItemInDTO.setAreaCode("3201");
        mallRecommendItemInDTO.setBuyerCode("htd20070007");
        mallRecommendItemInDTO.setBuyerId(91055L);
        ExecuteResult<List<MemberShipDTO>> boxRelationship =boxRelationshipService.selectBoxRelationship("htd20070007");
        mallRecommendItemInDTO.setBoxSellerDtoList(boxRelationship.getResult());
        mallRecommendItemInDTO.setPromotionItemIdList(new ArrayList<Long>());
        Pager pager = new Pager();
//        pager.setPage(1);
//        pager.setRows(10);
        ExecuteResult<DataGrid<MallRecommendItemOutDTO>>  executeResult = this.mallItemExportService.queryRecommendItemList(mallRecommendItemInDTO, pager);
        System.out.println(com.alibaba.fastjson.JSON.toJSONString(executeResult));
    }

}
