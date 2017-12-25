package cn.htd.goodscenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusStockItemInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;
import cn.htd.goodscenter.dto.venus.po.QuerySkuPublishInfoDetailParamDTO;
import cn.htd.goodscenter.dto.vms.*;
import cn.htd.goodscenter.service.venus.VmsItemExportService;
import cn.htd.goodscenter.test.common.CommonTest;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.dto.StandardPriceDTO;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class VmsItemExportServiceTest extends CommonTest {

    @Autowired
    private VmsItemExportService vmsItemExportService;

    @Test
    public void testQueryMyItemList() {
        QueryVmsMyItemListInDTO queryVmsMyItemListInDTO = new QueryVmsMyItemListInDTO();
        queryVmsMyItemListInDTO.setSellerId(517L);
//        queryVmsMyItemListInDTO.setProductCode("10018231");
//        queryVmsMyItemListInDTO.setAuditStatus(3);
//        queryVmsMyItemListInDTO.setBrandName("五粮液");
//        queryVmsMyItemListInDTO.setFirstCategoryId(11177L);
        Pager pager = new Pager(1, 100);
        ExecuteResult<DataGrid<QueryVmsMyItemListOutDTO>> executeResult = this.vmsItemExportService.queryMyItemList(queryVmsMyItemListInDTO, pager);
        System.out.println(JSON.toJSONString(executeResult));
    }

    @Test
    public void testqueryItemSkuDetail() {
        ExecuteResult<VenusItemSkuDetailOutDTO> executeResult = this.vmsItemExportService.queryItemSkuDetail(241347L);
        System.out.println(JSON.toJSONString(executeResult));
    }


    @Test
    public void testqueryItemSpuDataList() {
        VenusItemMainDataInDTO venusItemSpuInDTO = new VenusItemMainDataInDTO();
        venusItemSpuInDTO.setSellerId(517L);
        Pager<String> page = new Pager<>();
        ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>>  executeResult = this.vmsItemExportService.queryItemSpuDataList(venusItemSpuInDTO, page);
        System.out.println(JSON.toJSONString(executeResult));
    }

    @Test
    public void testqueryItemSpuDetail() {
        System.out.println(JSON.toJSONString(this.vmsItemExportService.queryItemSpuDetail(6l)));
    }


    @Test
    public void testqueryItemSkuPublishInfoList() {
        QueryVmsItemPublishInfoInDTO queryVmsItemPublishInfoInDTO = new QueryVmsItemPublishInfoInDTO();
        queryVmsItemPublishInfoInDTO.setIsBoxFlag(1);
        queryVmsItemPublishInfoInDTO.setSellerId(17606L);
//        queryVmsItemPublishInfoInDTO.seti
//        queryVmsItemPublishInfoInDTO.setShelfStatus(1);
//        Pager<String> page = new Pager<>();
        ExecuteResult<DataGrid<QueryVmsItemPublishInfoOutDTO>>  executeResult = this.vmsItemExportService.queryItemSkuPublishInfoList(queryVmsItemPublishInfoInDTO, null);
        System.out.println(JSON.toJSONString(executeResult));
    }

    @Test
    public void testqueryErpStockItemList() {
        VenusStockItemInDTO venusStockItemInDTO = new VenusStockItemInDTO();
//        venusStockItemInDTO.setProductName("日本Panasonic松下ES-ST29-A干湿两剃电动剃须刀-海外商品");
        venusStockItemInDTO.setProductCode("HTDH_0000077233");
        venusStockItemInDTO.setHasPage("1");
        venusStockItemInDTO.setPageCount("10");
        venusStockItemInDTO.setPageIndex("1");
        venusStockItemInDTO.setCanSellNum("1");
        venusStockItemInDTO.setSupplierCode("htd238861");
        venusStockItemInDTO.setSupplierId(517L);
        ExecuteResult<String> executeResult = this.vmsItemExportService.queryErpStockItemList(venusStockItemInDTO);
        System.out.println(JSON.toJSONString(executeResult));
    }

    @Test
    public void testAddItem() {
        VenusItemInDTO venusItemDTO = new VenusItemInDTO();
        venusItemDTO.setFirstLevelCategoryId(11168L);
        venusItemDTO.setSecondLevelCategoryId(11161L);
        venusItemDTO.setThirdLevelCategoryId(366L);
        venusItemDTO.setBrandId(367L);
        venusItemDTO.setSerial("型号");
        venusItemDTO.setProductName("苹果手机IPHONE-X(580G)");
        venusItemDTO.setUnit("tai");
        venusItemDTO.setTaxRate("0.17");
        venusItemDTO.setOperatorId(0L);
        venusItemDTO.setOperatorName("CHENKANG");
        venusItemDTO.setHtdVendorId(517L);
        venusItemDTO.setShopId(425L);
        venusItemDTO.setShopCid(6065L);
        ItemDescribe itemDescribe = new ItemDescribe();
        venusItemDTO.setDescribe(itemDescribe);
        ExecuteResult<String>  executeResult = this.vmsItemExportService.addItem(venusItemDTO);
        System.out.println(JSON.toJSONString(executeResult));
    }

    @Test
    public void testqueryItemSkuPublishInfoDetail() {
        QuerySkuPublishInfoDetailParamDTO querySkuPublishInfoDetailParamDTO = new QuerySkuPublishInfoDetailParamDTO();
        querySkuPublishInfoDetailParamDTO.setSkuId(269879L);
        querySkuPublishInfoDetailParamDTO.setShelfType("1");
        ExecuteResult<VenusItemSkuPublishInfoDetailOutDTO> executeResult = this.vmsItemExportService.queryItemSkuPublishInfoDetail(querySkuPublishInfoDetailParamDTO);
        System.out.println(JSON.toJSONString(executeResult));
    }


    @Test
    public void testoffShelves() {
        String skuCode = "1000026525";
        Integer isBoxFlag = 1;
        ExecuteResult<String>  executeResult = this.vmsItemExportService.offShelves(skuCode, isBoxFlag, 0L, "CHENKANG");
        System.out.println(JSON.toJSONString(executeResult));
    }


    @Test
    public void testonShelves() {
        VenusItemSkuPublishInDTO venusItemSkuPublishInDTO = new VenusItemSkuPublishInDTO();
        venusItemSkuPublishInDTO.setSkuCode("1000026525"); // 10033904  272699
        venusItemSkuPublishInDTO.setSkuId(257733L);
        venusItemSkuPublishInDTO.setDisplayQty("10");
        venusItemSkuPublishInDTO.setShelfType("1");
        venusItemSkuPublishInDTO.setOperatorId(0L);
        venusItemSkuPublishInDTO.setOperatorName("ckck");
        venusItemSkuPublishInDTO.setSupplierCode("htd100000");
        StandardPriceDTO standardPriceDTO = new StandardPriceDTO();
        ItemSkuBasePrice itemSkuBasePrice = new ItemSkuBasePrice();
        itemSkuBasePrice.setRetailPrice(new BigDecimal(140));
        itemSkuBasePrice.setBoxSalePrice(new BigDecimal(130));
        itemSkuBasePrice.setItemId(262601L);
        itemSkuBasePrice.setSkuId(257733L   );
        itemSkuBasePrice.setCreateId(0l);
        itemSkuBasePrice.setCreateName("ck");
        itemSkuBasePrice.setSellerId(17606l);
        itemSkuBasePrice.setShopId(0l);
        standardPriceDTO.setItemSkuBasePrice(itemSkuBasePrice);
        venusItemSkuPublishInDTO.setStandardPrice(standardPriceDTO);
        ExecuteResult<String>  executeResult = this.vmsItemExportService.onShelves(venusItemSkuPublishInDTO);
        System.out.println(JSON.toJSONString(executeResult));
    }

    @Test
    public void testqueryOffShelfItemBySellerId() {
        QueryOffShelfItemInDTO queryOffShelfItemInDTO = new QueryOffShelfItemInDTO();
        queryOffShelfItemInDTO.setSellerId(17606L);
        queryOffShelfItemInDTO.setIsBoxFlag(1);
        queryOffShelfItemInDTO.setSupplyCode("htd1000000");
        Pager pager = new Pager();
        ExecuteResult<DataGrid<QueryOffShelfItemOutDTO>>  executeResult = this.vmsItemExportService.queryOffShelfItemBySellerId(queryOffShelfItemInDTO, null);
        System.out.println(JSON.toJSONString(executeResult));


    }
}
