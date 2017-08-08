package cn.htd.tradecenter.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.service.TransactionRelationService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.tradecenter.dto.TradeOrderItemStockDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsShowDTO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;
import cn.htd.tradecenter.dto.TradeOrdersQueryInDTO;
import cn.htd.tradecenter.dto.VenusConfirmTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusConfirmTradeOrderItemDTO;
import cn.htd.tradecenter.dto.VenusConfirmTradeOrderItemWarehouseDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderItemDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderRebateDTO;

public class TradeOrderServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderServiceImplTest.class);

    private TradeOrderService tradeOrderService = null;
    private ApplicationContext ctx;
    private DictionaryUtils dictionary;
    private TransactionRelationService transactionRelationService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        tradeOrderService = (TradeOrderService) ctx.getBean("tradeOrderService");
        dictionary = (DictionaryUtils) ctx.getBean("dictionaryUtils");
        transactionRelationService = (TransactionRelationService) ctx.getBean("transactionRelationService");
    }
    
    @Test
    public void queryTradeOrderListByConditionTest() {
    	TradeOrdersQueryInDTO tradeOrders = new TradeOrdersQueryInDTO();
    	/*tradeOrders.setBuyerName("测试梁谢超2");
    	tradeOrders.setItemCode("10007990");
    	tradeOrders.setIsBoxFlag(0);*/
    	//tradeOrders.setOrderType(0);
    	Pager<TradeOrdersQueryInDTO> pager = new Pager<TradeOrdersQueryInDTO>();
    	
    	ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> buyerBlackRst = tradeOrderService.queryTradeOrderListByCondition(tradeOrders,pager);
        System.out.println("运营系统订单查询结果:"+JSON.toJSONString(buyerBlackRst));
    }


    @Test
    public void checkBlackListTest() {
        ExecuteResult<Boolean> buyerBlackRst = null;
        buyerBlackRst = transactionRelationService.getTransactionRelationIsRelated("htd1007014");
        System.out.println(JSON.toJSONString(buyerBlackRst));
    }

    @Test
    public void testCreateVenusTradeOrder() {
        VenusCreateTradeOrderDTO venusInDTO = new VenusCreateTradeOrderDTO();
        venusInDTO.setBankAccount("jiangkun test");
        venusInDTO.setBankName("jiangkun bank");
        venusInDTO.setBuyerId(new Long(79269));
        venusInDTO.setConsigneeAddress("南京市下关区测试测试");
        venusInDTO.setConsigneeAddressProvince("02");
        venusInDTO.setConsigneeAddressCity("025");
        venusInDTO.setConsigneeAddressDistrict("025003");
        venusInDTO.setConsigneeAddressTown("025003004");
        venusInDTO.setConsigneeAddressDetail("测试测试");
        venusInDTO.setConsigneeName("蒋坤");
        venusInDTO.setConsigneePhoneNum("1234567890");
        venusInDTO.setContactPhone("98765432d1");
        venusInDTO.setDeliveryType(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_GIVE_TYPE,
                DictionaryConst.OPT_ORDER_GIVE_TYPE_SELLER));
        venusInDTO.setInvoiceAddress("南京市下关区测试测试");
        venusInDTO.setInvoiceCompanyName("测试测试");
        venusInDTO.setInvoiceNotify("bugaosuni");
        venusInDTO.setIsNeedInvoice(1);
        venusInDTO.setInvoiceType(
                dictionary.getValueByCode(DictionaryConst.TYPE_INVOICE_TYPE, DictionaryConst.OPT_INVOICE_TYPE_PLAIN));
        venusInDTO.setOperatorId(new Long(27606));
        venusInDTO.setOperatorName("测试测试");
        venusInDTO.setOrderRemarks("南京市下关区测试测试");
        venusInDTO.setPostCode("210015");
        venusInDTO.setSalesDepartmentCode("1288020901");
        venusInDTO.setSalesType(
                dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_SALE_TYPE, DictionaryConst.OPT_ORDER_SALE_TYPE1));
        venusInDTO.setSellerId(new Long(17606));
        venusInDTO.setTaxManId("12345678");

        List<VenusCreateTradeOrderItemDTO> tradeItemDTOList = new ArrayList<VenusCreateTradeOrderItemDTO>();
        VenusCreateTradeOrderItemDTO orderItemDTO = new VenusCreateTradeOrderItemDTO();
        orderItemDTO.setAgreementCode("jiang kun");
        orderItemDTO.setAvailableInventory(new Integer(100));
        orderItemDTO.setGoodsCount(new Integer(10));
        orderItemDTO.setGoodsPrice(new BigDecimal("1000"));
        orderItemDTO.setCostPrice(new BigDecimal("900"));
        orderItemDTO.setIsAgreementSku(1);
        orderItemDTO.setIsBoxFlag(1);
        orderItemDTO.setProductAttribute(dictionary.getValueByCode(DictionaryConst.TYPE_ERP_PRODUCT_ATTR,
                DictionaryConst.OPT_ERP_PRODUCT_ATTR_SPTYPE1));
        orderItemDTO.setPurchaseDepartmentCode("1224324");
        orderItemDTO.setPurchaseDepartmentName("jiang kun test test");
        orderItemDTO.setRebateUsedAmount(new BigDecimal("1000"));
        orderItemDTO.setSkuCode("1000010856");
        orderItemDTO.setSupplierCode("htd1000000");
        orderItemDTO.setSupplierName("紫金阳光测试");
        orderItemDTO.setWarehouseCode("abcde");
        orderItemDTO.setWarehouseName("liandisys.com.cn");
        tradeItemDTOList.add(orderItemDTO);
        venusInDTO.setTradeItemDTOList(tradeItemDTOList);

        VenusCreateTradeOrderRebateDTO rebateDTO = new VenusCreateTradeOrderRebateDTO();
        venusInDTO.setRebateDTO(rebateDTO);
        rebateDTO.setRebateBalance(new BigDecimal("10000"));
        rebateDTO.setRebateCode("efgh");
        rebateDTO.setRebateNo("98765432d1");
        rebateDTO.setUseRebateAmount(new BigDecimal("1000"));
        venusInDTO.setRebateDTO(rebateDTO);
        ExecuteResult<TradeOrdersDTO> result = this.tradeOrderService.createVenusTradeOrderInfo(venusInDTO);
        if (!result.isSuccess()) {
            logger.info("\n 方法[{}]，出参：[{}]", "VenusTradeOrderServiceImplTest-testCreateVenusTradeOrder",
                    JSONObject.toJSONString(result));
        }
        Assert.assertEquals(true, result.isSuccess());
    }
    
    

    @Test
    public void testConfirmTradeOrder() {
        VenusConfirmTradeOrderDTO confirmOrderDTO = new VenusConfirmTradeOrderDTO();
        List<VenusConfirmTradeOrderItemDTO> orderItemsDTOList = new ArrayList<VenusConfirmTradeOrderItemDTO>();
        VenusConfirmTradeOrderItemDTO orderItemsDTO = new VenusConfirmTradeOrderItemDTO();
        List<VenusConfirmTradeOrderItemWarehouseDTO> warehouseDTOList = new ArrayList<VenusConfirmTradeOrderItemWarehouseDTO>();
        VenusConfirmTradeOrderItemWarehouseDTO wareDetailDTO = new VenusConfirmTradeOrderItemWarehouseDTO();

        confirmOrderDTO.setOrderNo("991612191449000001");
        confirmOrderDTO.setModifyTimeStr("2016-12-19 19:35:51");
        confirmOrderDTO.setOperatorId(new Long(897896789));
        confirmOrderDTO.setOperatorName("蒋坤 jiangkun");
        confirmOrderDTO.setSalesType("1");
        orderItemsDTO.setOrderItemNo("99161219144900000101");
        orderItemsDTO.setModifyTimeStr("2016-12-19 19:35:51");
        orderItemsDTOList.add(orderItemsDTO);
        wareDetailDTO.setGoodsCount(1);
        wareDetailDTO.setWarehouseCode("123456");
        wareDetailDTO.setWarehouseName("jiangkun test");
        wareDetailDTO.setSupplierCode("98765");
        wareDetailDTO.setSupplierName("test for jiangkun");
        wareDetailDTO.setPurchaseDepartmentCode("98765");
        wareDetailDTO.setPurchaseDepartmentName("test for jiangkun");
        wareDetailDTO.setProductAttribute(dictionary.getValueByCode(DictionaryConst.TYPE_ERP_PRODUCT_ATTR,
                DictionaryConst.OPT_ERP_PRODUCT_ATTR_SPTYPE10));
        wareDetailDTO.setAvailableInventory(99);
        wareDetailDTO.setIsAgreementSku(1);
        wareDetailDTO.setAgreementCode("888888");
        warehouseDTOList.add(wareDetailDTO);

        orderItemsDTO.setItemWareHouseDTOList(warehouseDTOList);
        confirmOrderDTO.setOrderItemList(orderItemsDTOList);

        ExecuteResult<TradeOrdersDTO> result = this.tradeOrderService.confirmVenusTradeOrderInfo(confirmOrderDTO);
        if (!result.isSuccess()) {
            logger.info("\n 方法[{}]，出参：[{}]", "VenusTradeOrderServiceImplTest-ConfirmTradeOrder",
                    JSONObject.toJSONString(result));
        }
        Assert.assertEquals(true, result.isSuccess());
    }
    
    
    @Test
    public void testQueryTradeOrderHmsListByItemCodeAndBoxFlag() {
    	TradeOrdersQueryInDTO tradeOrders = new TradeOrdersQueryInDTO();
    	tradeOrders.setItemCode("10007990");
    	tradeOrders.setIsBoxFlag(0);
    	Pager<TradeOrdersQueryInDTO> pager = new Pager<TradeOrdersQueryInDTO>();
        ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> result = this.tradeOrderService.queryTradeOrderHmsListByItemCodeAndBoxFlag(tradeOrders,pager);
        if (result.isSuccess()) {
        	System.out.println(JSON.toJSONString(result.getResult().getRows()));
        }
    }
    
    @Test
    public void testGetItemStockAndLockStock() {
    	TradeOrdersQueryInDTO tradeOrders = new TradeOrdersQueryInDTO();
    	tradeOrders.setItemCode("10007990");
    	tradeOrders.setIsBoxFlag(0);
    	ExecuteResult<TradeOrderItemStockDTO> result = this.tradeOrderService.getItemStockAndLockStock(tradeOrders);
        if (result.isSuccess()) {
        	System.out.println(JSON.toJSONString(result.getResult()));
        }
    }
    
}
