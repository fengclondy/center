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

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.tradecenter.dto.ReverseCustomerBalanceDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderItemDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderRebateDTO;
import cn.htd.tradecenter.service.handle.TradeOrderMiddlewareHandle;
import cn.htd.tradecenter.service.impl.TradeOrderBaseService;

public class ValetOrderServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(ValetOrderServiceImplTest.class);

    private ApplicationContext ctx;
    private DictionaryUtils dictionary;
    private TradeOrderBaseService baseService;
    private ValetOrderService valetOrderService;
    private TradeOrderMiddlewareHandle tradeOrderMiddlwareHandle;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        dictionary = (DictionaryUtils) ctx.getBean("dictionaryUtils");
        baseService = (TradeOrderBaseService) ctx.getBean("tradeOrderBaseService");
        valetOrderService = (ValetOrderService) ctx.getBean("valetOrderService");
        tradeOrderMiddlwareHandle = (TradeOrderMiddlewareHandle) ctx.getBean("tradeOrderMiddlwareHandle");
    }

    @Test
    public void testCreateVMSValetOrder() {
        VenusCreateTradeOrderDTO venusInDTO = new VenusCreateTradeOrderDTO();
        venusInDTO.setOrderFrom(
                dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_FROM, DictionaryConst.OPT_ORDER_FROM_VMS));
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
        venusInDTO.setOperatorId(new Long(10517));
        venusInDTO.setOperatorName("测试测试");
        venusInDTO.setOrderRemarks("南京市下关区测试测试");
        venusInDTO.setPostCode("210015");
        venusInDTO.setSalesDepartmentCode("0801022001");
        venusInDTO.setSalesType(
                dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_SALE_TYPE, DictionaryConst.OPT_ORDER_SALE_TYPE1));
        venusInDTO.setSellerId(new Long(517));
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
        orderItemDTO.setPurchaseDepartmentCode("0801012001");
        orderItemDTO.setPurchaseDepartmentName("汇通达其他采购小类");
        orderItemDTO.setRebateUsedAmount(new BigDecimal("50"));
        orderItemDTO.setSkuCode("100001886131");
        orderItemDTO.setSpuCode("HPH_0000005501");
        orderItemDTO.setSupplierCode("004162");
        orderItemDTO.setSupplierName("天津战圣瑞达物流有限公司");
        orderItemDTO.setWarehouseCode("08010201");
        orderItemDTO.setWarehouseName("汇通达直拨库");
        tradeItemDTOList.add(orderItemDTO);
        venusInDTO.setTradeItemDTOList(tradeItemDTOList);

        VenusCreateTradeOrderRebateDTO rebateDTO = new VenusCreateTradeOrderRebateDTO();
        venusInDTO.setRebateDTO(rebateDTO);
        rebateDTO.setRebateBalance(new BigDecimal("100"));
        rebateDTO.setRebateCode("0009");
        rebateDTO.setRebateNo("466");
        rebateDTO.setUseRebateAmount(new BigDecimal("50"));
        venusInDTO.setRebateDTO(rebateDTO);
        ExecuteResult<TradeOrdersDTO> result = valetOrderService.createValetOrder(venusInDTO);
        if (!result.isSuccess()) {
            logger.info("\n 方法[{}]，出参：[{}]", "ValetOrderServiceImplTest-testCreateSuperManagerValetOrder",
                    JSONObject.toJSONString(result));
        }
        Assert.assertEquals(true, result.isSuccess());

        // 释放锁定帐存
        if (result.getResult() != null && result.getResult().getOrderItemList() != null) {
            for (TradeOrderItemsDTO item : result.getResult().getOrderItemList()) {
                tradeOrderMiddlwareHandle.releaseBalance(item.getOrderItemNo());
            }
        }
    }

    @Test
    public void testCreateSuperManagerValetOrder() {
        VenusCreateTradeOrderDTO venusInDTO = new VenusCreateTradeOrderDTO();
        venusInDTO.setOrderFrom(
                dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_FROM, DictionaryConst.OPT_ORDER_FROM_SUPER_MANAGER));
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
        venusInDTO.setOperatorId(new Long(10517));
        venusInDTO.setOperatorName("测试测试");
        venusInDTO.setOrderRemarks("南京市下关区测试测试");
        venusInDTO.setPostCode("210015");
        venusInDTO.setSalesDepartmentCode("0801022001");
        venusInDTO.setSalesType(
                dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_SALE_TYPE, DictionaryConst.OPT_ORDER_SALE_TYPE1));
        venusInDTO.setSellerId(new Long(517));
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
        orderItemDTO.setRebateUsedAmount(new BigDecimal("50"));
        orderItemDTO.setSkuCode("100001886131");
        orderItemDTO.setSpuCode("HPH_0000005501");
        tradeItemDTOList.add(orderItemDTO);
        venusInDTO.setTradeItemDTOList(tradeItemDTOList);

        VenusCreateTradeOrderRebateDTO rebateDTO = new VenusCreateTradeOrderRebateDTO();
        venusInDTO.setRebateDTO(rebateDTO);
        rebateDTO.setRebateBalance(new BigDecimal("100"));
        rebateDTO.setRebateCode("0009");
        rebateDTO.setRebateNo("466");
        rebateDTO.setUseRebateAmount(new BigDecimal("50"));
        venusInDTO.setRebateDTO(rebateDTO);
        ExecuteResult<TradeOrdersDTO> result = valetOrderService.createValetOrder(venusInDTO);
        if (!result.isSuccess()) {
            logger.info("\n 方法[{}]，出参：[{}]", "ValetOrderServiceImplTest-testCreateSuperManagerValetOrder",
                    JSONObject.toJSONString(result));
        }
        Assert.assertEquals(true, result.isSuccess());

        // 释放锁定帐存
        if (result.getResult() != null && result.getResult().getOrderItemList() != null) {
            for (TradeOrderItemsDTO item : result.getResult().getOrderItemList()) {
                tradeOrderMiddlwareHandle.releaseBalance(item.getOrderItemNo());
            }
        }
    }

//    @Test
    public void testReverseReleaseBalance() {
        String orderNo = baseService.getOrderNo(
                dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_FROM, DictionaryConst.OPT_ORDER_FROM_VMS));
        String orderItemNo = baseService.getOrderItemNo(orderNo);
        List<ReverseCustomerBalanceDTO> reverseBalanceList = new ArrayList<ReverseCustomerBalanceDTO>();
        ReverseCustomerBalanceDTO reverseBalance = new ReverseCustomerBalanceDTO();
        reverseBalance.setBrandCode("600");
        reverseBalance.setClassCode("11");
        reverseBalance.setCustomerCode("htd1299140");
        reverseBalance.setCompanyCode("htd238861");
        reverseBalance.setChargeAmount("100");
        reverseBalance.setItemOrderNo(orderItemNo);
        reverseBalanceList.add(reverseBalance);
        // 锁定
        boolean result = tradeOrderMiddlwareHandle.reverseBalance(reverseBalanceList);
        Assert.assertEquals(true, result);

        // 释放锁定
        result = tradeOrderMiddlwareHandle.releaseBalance(orderItemNo);
        Assert.assertEquals(true, result);
    }

}
