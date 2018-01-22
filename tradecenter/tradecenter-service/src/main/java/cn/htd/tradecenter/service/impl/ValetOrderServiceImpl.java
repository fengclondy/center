/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName:    ValetOrderService.java
 * Author:      shihb
 * Date:        2018年1月22日
 * Description: 代客下单服务实现类
 * History:     
 * shihb     2018年1月22日 1.0         创建
 */
package cn.htd.tradecenter.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.DateUtils;
import cn.htd.goodscenter.dto.middleware.outdto.QueryItemWarehouseOutDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.tradecenter.common.constant.ReturnCodeConst;
import cn.htd.tradecenter.common.enums.YesNoEnum;
import cn.htd.tradecenter.common.exception.TradeCenterBusinessException;
import cn.htd.tradecenter.common.utils.CalculateUtils;
import cn.htd.tradecenter.common.utils.DateUtil;
import cn.htd.tradecenter.common.utils.ExceptionUtils;
import cn.htd.tradecenter.common.utils.ValidateResult;
import cn.htd.tradecenter.common.utils.ValidationUtils;
import cn.htd.tradecenter.dto.ProductPriceDTO;
import cn.htd.tradecenter.dto.RebateDTO;
import cn.htd.tradecenter.dto.ReverseCustomerBalanceDTO;
import cn.htd.tradecenter.dto.TradeOrderErpDistributionDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsStatusHistoryDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsWarehouseDetailDTO;
import cn.htd.tradecenter.dto.TradeOrderStatusHistoryDTO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderItemDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderRebateDTO;
import cn.htd.tradecenter.service.ValetOrderService;
import cn.htd.tradecenter.service.handle.TradeOrderDBHandle;
import cn.htd.tradecenter.service.handle.TradeOrderMiddlewareHandle;
import cn.htd.tradecenter.service.handle.TradeOrderStockHandle;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.service.CustomerService;

/**
 * 代客下单服务实现类
 */
@Service("valetOrderService")
public class ValetOrderServiceImpl implements ValetOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ValetOrderServiceImpl.class);

    @Resource
    private TradeOrderBaseService baseService;

    @Resource
    private CustomerService customerService;

    @Resource
    private MemberBaseInfoService memberBaseInfoService;

    @Resource
    private TradeOrderStockHandle orderStockHandle;

    @Resource
    private TradeOrderMiddlewareHandle orderMiddlewareHandle;

    @Resource
    private TradeOrderDBHandle orderDBHandle;

    /**
     * 代客下单新增订单
     * 
     * @param venusInDTO
     *            订单信息
     * @return 新增订单结果
     */
    public ExecuteResult<TradeOrdersDTO> createValetOrder(VenusCreateTradeOrderDTO venusInDTO) {
        ExecuteResult<TradeOrdersDTO> result = new ExecuteResult<TradeOrdersDTO>();
        String tradeNo = "";
        String orderNo = "";
        String buyerCode = "";
        String sellerCode = "";
        List<ReverseCustomerBalanceDTO> reverseBalanceList = new ArrayList<ReverseCustomerBalanceDTO>();
        boolean reverseBalanceResult = false;

        try {
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(venusInDTO);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
            }
            // 取得用户ID信息
            CustomerDTO customerDTO = customerService.getCustomerInfo(venusInDTO.getOperatorId());
            // 检查用户信息合法性
            if (customerDTO == null) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_OPERATION_USER_NOT_EXIST,
                        "用户ID:" + venusInDTO.getOperatorId() + "信息不存在");
            }
            if (customerDTO.getCompanyId().compareTo(venusInDTO.getSellerId()) != 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_OPERATION_SELLER_ERROR,
                        "用户ID:" + venusInDTO.getOperatorId() + "所属公司不是该供应商");
            }

            // 卖家存在验证
            ExecuteResult<MemberDetailInfo> sellerDetail = memberBaseInfoService
                    .getMemberDetailBySellerId(venusInDTO.getSellerId());
            if (sellerDetail.isSuccess() && sellerDetail.getResult() != null
                    && sellerDetail.getResult().getMemberBaseInfoDTO() != null) {
                sellerCode = sellerDetail.getResult().getMemberBaseInfoDTO().getMemberCode();
            }
            if (StringUtils.isEmpty(sellerCode)) {
                throw new TradeCenterBusinessException(ReturnCodeConst.SELLER_NOT_EXIST, "卖家信息不存在");
            }

            // 买家存在验证
            ExecuteResult<MemberDetailInfo> buyerDetail = memberBaseInfoService
                    .getMemberDetailById(venusInDTO.getBuyerId());
            if (buyerDetail.isSuccess() && buyerDetail.getResult() != null
                    && buyerDetail.getResult().getMemberBaseInfoDTO() != null) {
                buyerCode = buyerDetail.getResult().getMemberBaseInfoDTO().getMemberCode();
            }
            if (StringUtils.isEmpty(buyerCode)) {
                throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_NOT_EXIST, "买家信息不存在");
            }

            Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
            checkCreateVenusOrderParameter(dictMap, venusInDTO,
                    customerDTO.getIsLowFlag() == null ? false : customerDTO.getIsLowFlag().booleanValue(), sellerCode,
                    buyerCode);
            // 取得交易号和订单号
            tradeNo = baseService.getTradeNo(venusInDTO.getOrderFrom());
            orderNo = baseService.getOrderNo(venusInDTO.getOrderFrom());
            // 取得订单行信息计算订单行金额信息
            List<TradeOrderItemsDTO> tradeOrderItemsDTOList = getValetOrderItemList(dictMap, orderNo, venusInDTO,
                    sellerCode);
            TradeOrdersDTO tradeOrdersDTO = baseService.createTradeOrderInfo(dictMap, orderNo, venusInDTO,
                    tradeOrderItemsDTOList);
            // 判断账户余额是否足够
            BigDecimal privateAccount = orderMiddlewareHandle.getPrivateAccount(tradeOrdersDTO.getSellerCode(),
                    tradeOrdersDTO.getBuyerCode(), venusInDTO.getSalesDepartmentCode());
            // 应付金额大于专有账户余额时，
            if (tradeOrdersDTO.getOrderPayAmount().compareTo(privateAccount) > 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_PRIVATEACCOUNT_NO_ENOUGH,
                        "订单实付金额已超出客户在此销售部门的可用余额！");
            }
            List<TradeOrderErpDistributionDTO> orderErpDistributionDTOList = baseService
                    .createTradeOrderErpDistributionInfo(dictMap, tradeOrdersDTO, tradeOrderItemsDTOList);
            tradeOrdersDTO.setTradeNo(tradeNo);
            tradeOrdersDTO.setOrderFrom(venusInDTO.getOrderFrom());
            tradeOrdersDTO.setSalesType(venusInDTO.getSalesType());
            tradeOrdersDTO.setSalesDepartmentCode(venusInDTO.getSalesDepartmentCode());
            tradeOrdersDTO.setCreateOrderTime(new Date());
            tradeOrdersDTO.setPayTimeLimit(DateUtils.parse("9999-12-31 23:59:59", DateUtils.YYDDMMHHMMSS));
            // VMS开单订单默认创建状态为待确认,设置ERP分销单状态为待确认
            setOrderStatusByCondition(tradeOrdersDTO, dictMap, orderErpDistributionDTOList);
            tradeOrdersDTO.setPayType(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_PAY_TYPE,
                    DictionaryConst.OPT_PAY_TYPE_ERP_ACCOUNT));
            tradeOrdersDTO.setPayStatus(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_PAY_STATUS,
                    DictionaryConst.OPT_PAY_STATUS_PENDING));
            tradeOrdersDTO.setIsExpressDelivery(YesNoEnum.NO.getValue());
            tradeOrdersDTO.setOrderDeleteStatus(YesNoEnum.NO.getValue());
            // 生成订单状态履历
            TradeOrderStatusHistoryDTO statusHistoryDTO = new TradeOrderStatusHistoryDTO();
            statusHistoryDTO.setOrderNo(orderNo);
            statusHistoryDTO.setOrderStatus(tradeOrdersDTO.getOrderStatus());
            statusHistoryDTO.setOrderStatusText(baseService.setOrderShowStatus(dictMap, tradeOrdersDTO.getOrderStatus(),
                    "", YesNoEnum.NO.getValue(), ""));
            statusHistoryDTO.setCreateId(venusInDTO.getOperatorId());
            statusHistoryDTO.setCreateName(venusInDTO.getOperatorName());
            List<TradeOrderStatusHistoryDTO> orderStatusHistoryDTOList = new ArrayList<TradeOrderStatusHistoryDTO>();
            orderStatusHistoryDTOList.add(statusHistoryDTO);
            // 组装订单数据
            tradeOrdersDTO.setOrderItemList(tradeOrderItemsDTOList);
            tradeOrdersDTO.setErpDistributionDTOList(orderErpDistributionDTOList);
            tradeOrdersDTO.setOrderStatusHistoryDTOList(orderStatusHistoryDTOList);

            // 待确认订单时，锁定客户帐存
            String confirmStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
                    DictionaryConst.OPT_ORDER_STATUS_WAIT_CONFIRM);
            if (confirmStatus.equals(tradeOrdersDTO.getOrderStatus())) {
                for (TradeOrderItemsDTO orderItem : tradeOrderItemsDTOList) {
                    ReverseCustomerBalanceDTO reverseBalance = new ReverseCustomerBalanceDTO();
                    reverseBalance.setBrandCode(String.valueOf(orderItem.getBrandId()));
                    reverseBalance.setClassCode(orderItem.getErpFirstCategoryCode());
                    reverseBalance.setCustomerCode(orderItem.getBuyerCode());
                    reverseBalance.setCompanyCode(orderItem.getSellerCode());
                    reverseBalance.setChargeAmount(orderItem.getOrderItemTotalAmount().toString());
                    reverseBalance.setItemOrderNo(orderItem.getOrderItemNo());
                    reverseBalanceList.add(reverseBalance);
                }
                // 锁定帐存
                reverseBalanceResult = orderMiddlewareHandle.reverseBalance(reverseBalanceList);
                if (!reverseBalanceResult) {
                    throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_REVERSE_CUSTOMER_BALANCE_ERROR,
                            "代客开单锁定客户帐存失败");
                }
            }
            logger.info("VMS开单创建订单数据===================" + JSONObject.toJSONString(tradeOrdersDTO));
            tradeOrdersDTO = orderDBHandle.createVenusTradeOrders(tradeOrdersDTO);
            result.setResult(tradeOrdersDTO);
        } catch (TradeCenterBusinessException tcbe) {
            result.setCode(tcbe.getCode());
            result.addErrorMessage(tcbe.getMessage());
            if (reverseBalanceResult) {
                // 释放帐存锁定
                for (ReverseCustomerBalanceDTO dto : reverseBalanceList) {
                    boolean releaseResult = orderMiddlewareHandle.releaseBalance(dto.getItemOrderNo());
                    if (!releaseResult) {
                        logger.error("订单行({})释放帐存锁定发生错误", dto.getItemOrderNo());
                    }
                }
            }
        } catch (Exception e) {
            result.setCode(ReturnCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            if (reverseBalanceResult) {
                // 释放帐存锁定
                for (ReverseCustomerBalanceDTO dto : reverseBalanceList) {
                    boolean releaseResult = orderMiddlewareHandle.releaseBalance(dto.getItemOrderNo());
                    if (!releaseResult) {
                        logger.error("订单行({})释放帐存锁定发生错误", dto.getItemOrderNo());
                    }
                }
            }
        }
        return result;
    }

    /**
     * 检查输入参数的合法性
     * 
     * @param dictMap
     * @param venusInDTO
     * @param canUnderFloorPrice
     * @param sellerCode
     * @param buyerCode
     * @throws TradeCenterBusinessException
     */
    private void checkCreateVenusOrderParameter(Map<String, DictionaryInfo> dictMap,
            VenusCreateTradeOrderDTO venusInDTO, boolean canUnderFloorPrice, String sellerCode, String buyerCode)
            throws TradeCenterBusinessException {
        List<VenusCreateTradeOrderItemDTO> itemDTOList = venusInDTO.getTradeItemDTOList();
        VenusCreateTradeOrderRebateDTO rebateDTO = venusInDTO.getRebateDTO();
        VenusCreateTradeOrderItemDTO itemBaseDTO = null;
        ValidateResult validateResult = null;
        Integer goodsCount = 0;
        BigDecimal costPrice = BigDecimal.ZERO;
        BigDecimal goodsPrice = BigDecimal.ZERO;
        BigDecimal goodsAmount = BigDecimal.ZERO;
        BigDecimal totalRebateAmount = BigDecimal.ZERO;
        boolean hasDeliveryTypeFlag = false;
        List<DictionaryInfo> deliveryTypeList = baseService.getDictOptList(dictMap,
                DictionaryConst.TYPE_ORDER_GIVE_TYPE);
        if (deliveryTypeList != null) {
            for (DictionaryInfo deliveryType : deliveryTypeList) {
                if (deliveryType.getValue().equals(venusInDTO.getDeliveryType())) {
                    hasDeliveryTypeFlag = true;
                    break;
                }
            }
        }
        if (!hasDeliveryTypeFlag) {
            throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_UNSUPPORT_DELIVERY_TYPE, "不支持所选的配送方式");
        }

        Map<String, List<QueryItemWarehouseOutDTO>> stockMap = new HashMap<String, List<QueryItemWarehouseOutDTO>>();
        for (int i = 0; i < itemDTOList.size(); i++) {
            itemBaseDTO = itemDTOList.get(i);
            goodsCount = itemBaseDTO.getGoodsCount();
            costPrice = itemBaseDTO.getCostPrice();
            goodsPrice = itemBaseDTO.getGoodsPrice();
            goodsAmount = CalculateUtils.multiply(goodsPrice, new BigDecimal(goodsCount));
            if (!canUnderFloorPrice && goodsPrice.compareTo(costPrice) < 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_PRICE_TOO_LOW,
                        "第" + (i + 1) + "行订单商品价格不能小于分销限价");
            }
            if (goodsAmount.compareTo(itemBaseDTO.getRebateUsedAmount()) < 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_AMOUNT_LACK,
                        "第" + (i + 1) + "行订单商品总金额小于使用返利金额");
            }
            if (itemBaseDTO.getGoodsCount() > itemBaseDTO.getAvailableInventory()) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_COUNT_LACK,
                        "第" + (i + 1) + "行订单商品数量大于可用库存");
            }
            if (YesNoEnum.YES.getValue() == itemBaseDTO.getIsAgreementSku()
                    && StringUtils.isBlank(itemBaseDTO.getAgreementCode())) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NONE_AGREEMENT,
                        "第" + (i + 1) + "行协议商品没有协议编号");
            }
            // 查询ERP库存信息
            String spuCode = itemBaseDTO.getSpuCode();
            // 相同商品只查询一次库存
            if (!stockMap.containsKey(spuCode)) {
                // 查询ERP库存信息
                List<QueryItemWarehouseOutDTO> stockList = orderMiddlewareHandle.queryItemStock(sellerCode, spuCode);
                stockMap.put(spuCode, stockList);
            }
            int storeNum = 0;
            if (stockMap.get(spuCode) != null) {
                for (QueryItemWarehouseOutDTO stockDTO : stockMap.get(spuCode)) {
                    // 取得选择仓库的实际库存
                    if (itemBaseDTO.getSupplierCode().equals(stockDTO.getSupplierCode())
                            && itemBaseDTO.getPurchaseDepartmentCode().equals(stockDTO.getDepartmentCode())
                            && itemBaseDTO.getWarehouseCode().equals(stockDTO.getWareHouseCode())
                            && itemBaseDTO.getProductAttribute().equals(stockDTO.getProductAttribute())) {
                        storeNum = Integer.parseInt(stockDTO.getStoreNum());
                        break;
                    }
                }
            }
            // 校验ERP库存信息
            if (itemBaseDTO.getGoodsCount() > storeNum) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_COUNT_LACK, MessageFormat.format(
                        "商品：{0} 在仓库：{1} 仓库库存数量不足，请重新选择仓库！", itemBaseDTO.getSkuCode(), itemBaseDTO.getWarehouseName()));
            }
            totalRebateAmount = CalculateUtils.add(totalRebateAmount, itemBaseDTO.getRebateUsedAmount());
        }
        if (BigDecimal.ZERO.compareTo(totalRebateAmount) >= 0) {
            venusInDTO.setRebateDTO(null);
        } else {
            if (rebateDTO == null) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NONE_REBATE, "使用了返利金额但未选择对应返利单");
            }
            validateResult = ValidationUtils.validateEntity(rebateDTO);
            if (validateResult.isHasErrors()) {
                throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
            }
            if (totalRebateAmount.compareTo(rebateDTO.getRebateBalance()) > 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_REBATE_AMOUNT_LACK,
                        "返利单" + rebateDTO.getRebateCode() + "的返利余额小于使用返利总金额");
            }

            // 查询ERP当前选择返利单的余额
            BigDecimal rebateBalance = new BigDecimal(0);
            // 查询销售部门名称
            String departmentName = orderMiddlewareHandle.querySaleDepartmentName(venusInDTO.getSalesDepartmentCode());
            // 查询返利单信息
            List<RebateDTO> rebateList = orderMiddlewareHandle.queryRebate(buyerCode, departmentName);
            if (rebateList != null) {
                for (RebateDTO rebate : rebateList) {
                    // 取得当前选择返利单的余额
                    if (rebate.getRebateCode().equals(rebateDTO.getRebateCode())) {
                        rebateBalance = new BigDecimal(rebate.getRebatePrice());
                        break;
                    }
                }
            }
            // 校验ERP返利单余额
            if (totalRebateAmount.compareTo(rebateBalance) > 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_REBATE_AMOUNT_LACK,
                        "使用返利超出额度！");
            }

            rebateDTO.setUseRebateAmount(totalRebateAmount);
        }
    }

    /**
     * 取得代课开单商品详情信息及检查相应库存价格信息
     *
     * @param dictMap
     * @param orderNo
     * @param venusInDTO
     * @param sellerCode
     * @return
     * @throws TradeCenterBusinessException
     * @throws Exception
     */
    private List<TradeOrderItemsDTO> getValetOrderItemList(Map<String, DictionaryInfo> dictMap, String orderNo,
            VenusCreateTradeOrderDTO venusInDTO, String sellerCode) throws TradeCenterBusinessException, Exception {
        List<String> skuList = new ArrayList<String>();
        List<String> spuList = new ArrayList<String>();
        Map<String, List<VenusCreateTradeOrderItemDTO>> distinctItemListMap = new HashMap<String, List<VenusCreateTradeOrderItemDTO>>();
        Map<String, ProductPriceDTO> priceMap = new HashMap<String, ProductPriceDTO>();
        List<TradeOrderItemsDTO> retOrderItemsDTOList = new ArrayList<TradeOrderItemsDTO>();

        for (VenusCreateTradeOrderItemDTO itemBaseDTO : venusInDTO.getTradeItemDTOList()) {
            String skuCode = itemBaseDTO.getSkuCode();
            if (!skuList.contains(skuCode)) {
                skuList.add(skuCode);
                spuList.add(itemBaseDTO.getSpuCode());
                distinctItemListMap.put(skuCode, new ArrayList<VenusCreateTradeOrderItemDTO>());
            }
            distinctItemListMap.get(skuCode).add(itemBaseDTO);
        }
        for (int i = 0; i < skuList.size(); i++) {
            String skuCode = skuList.get(i);
            String spuCode = spuList.get(i);
            // 代客下单时，包厢标志位传"-1"，不校验商品中心库存
            TradeOrderItemsDTO orderItemsDTO = orderStockHandle.getItemSkuDetail(skuCode, -1, 0);
            for (VenusCreateTradeOrderItemDTO tempVenusItemDTO : distinctItemListMap.get(skuCode)) {
                // 查询ERP商品价格
                if (priceMap.containsKey(spuCode)) {
                    ProductPriceDTO productPrice = orderMiddlewareHandle.queryProductPrice(sellerCode, spuCode);
                    priceMap.put(spuCode, productPrice);
                }
                // 默认设定大厅
                orderItemsDTO.setIsBoxFlag(0);
                // 销售价格使用ERP分销单价
                BigDecimal salePrice = new BigDecimal(0);
                if (priceMap.get(spuCode) != null) {
                    salePrice = priceMap.get(spuCode).getWebPrice();
                }
                // 分销单价不存在时，就用商品价格
                if (salePrice == null || salePrice.compareTo(new BigDecimal(0)) <= 0) {
                    salePrice = orderItemsDTO.getGoodsPrice();
                }
                orderItemsDTO.setSalePrice(salePrice);

                TradeOrderItemsDTO retOrderItemsDTO = setTradeOrderItemsDTO(dictMap, orderNo, orderItemsDTO,
                        tempVenusItemDTO, venusInDTO);
                retOrderItemsDTOList.add(retOrderItemsDTO);
            }
        }
        return retOrderItemsDTOList;
    }

    /**
     * 设定订单行数据
     *
     * @param dictMap
     * @param orderNo
     * @param targetDTO
     * @param baseDTO
     * @param venusInDTO
     * @return
     * @throws TradeCenterBusinessException
     */
    private TradeOrderItemsDTO setTradeOrderItemsDTO(Map<String, DictionaryInfo> dictMap, String orderNo,
            TradeOrderItemsDTO targetDTO, VenusCreateTradeOrderItemDTO baseDTO, VenusCreateTradeOrderDTO venusInDTO)
            throws TradeCenterBusinessException {
        TradeOrderItemsDTO retDTO = new TradeOrderItemsDTO();
        VenusCreateTradeOrderRebateDTO rebateDTO = venusInDTO.getRebateDTO();
        int goodsNum = baseDTO.getGoodsCount();
        BigDecimal price = baseDTO.getGoodsPrice();
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<TradeOrderItemsWarehouseDetailDTO> itemWarehouseDetailList = new ArrayList<TradeOrderItemsWarehouseDetailDTO>();
        TradeOrderItemsWarehouseDetailDTO itemWarehouseDetailDTO = new TradeOrderItemsWarehouseDetailDTO();
        List<TradeOrderItemsStatusHistoryDTO> itemStatusHistoryList = new ArrayList<TradeOrderItemsStatusHistoryDTO>();
        TradeOrderItemsStatusHistoryDTO itemStatusHistoryDTO = new TradeOrderItemsStatusHistoryDTO();

        retDTO.setOrderNo(orderNo);
        retDTO.setOrderItemNo(baseService.getOrderItemNo(orderNo));
        retDTO.setChannelCode(targetDTO.getChannelCode());
        retDTO.setItemCode(targetDTO.getItemCode());
        retDTO.setGoodsName(targetDTO.getGoodsName());
        retDTO.setSkuCode(targetDTO.getSkuCode());
        retDTO.setSkuErpCode(targetDTO.getSkuErpCode());
        retDTO.setSkuEanCode(targetDTO.getSkuEanCode());
        retDTO.setSkuPictureUrl(targetDTO.getSkuPictureUrl());
        retDTO.setItemSpuCode(targetDTO.getItemSpuCode());
        retDTO.setFirstCategoryId(targetDTO.getFirstCategoryId());
        retDTO.setFirstCategoryName(targetDTO.getFirstCategoryName());
        retDTO.setSecondCategoryId(targetDTO.getSecondCategoryId());
        retDTO.setSecondCategoryName(targetDTO.getSecondCategoryName());
        retDTO.setThirdCategoryId(targetDTO.getThirdCategoryId());
        retDTO.setThirdCategoryName(targetDTO.getThirdCategoryName());
        retDTO.setErpFirstCategoryCode(targetDTO.getErpFirstCategoryCode());
        retDTO.setErpFiveCategoryCode(targetDTO.getErpFiveCategoryCode());
        retDTO.setBrandId(targetDTO.getBrandId());
        retDTO.setBrandName(targetDTO.getBrandName());
        retDTO.setCategoryIdList(targetDTO.getCategoryIdList());
        retDTO.setCategoryNameList(targetDTO.getCategoryNameList());
        retDTO.setIsBoxFlag(targetDTO.getIsBoxFlag());
        retDTO.setGoodsCount(goodsNum);
        retDTO.setCostPrice(baseDTO.getCostPrice());
        retDTO.setSalePrice(targetDTO.getSalePrice());
        retDTO.setGoodsPriceType(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_SKU_PRICE_TYPE,
                DictionaryConst.OPT_SKU_PRICE_TYPE_COUSTOM));
        retDTO.setGoodsPrice(price);
        retDTO.setGoodsAmount(CalculateUtils.multiply(price, new BigDecimal(goodsNum)));
        retDTO.setGoodsFreight(targetDTO.getGoodsFreight());
        totalAmount = retDTO.getGoodsAmount();
        if (BigDecimal.ZERO.compareTo(baseDTO.getRebateUsedAmount()) < 0) {
            retDTO.setTotalDiscountAmount(baseDTO.getRebateUsedAmount());
            retDTO.setUsedRebateAmount(baseDTO.getRebateUsedAmount());
            retDTO.setErpRebateCode(rebateDTO.getRebateCode());
            retDTO.setErpRebateNo(rebateDTO.getRebateNo());
            totalAmount = CalculateUtils.subtract(totalAmount, baseDTO.getRebateUsedAmount());
        }
        retDTO.setGoodsRealPrice(CalculateUtils.divide(totalAmount, new BigDecimal(goodsNum)));
        totalAmount = CalculateUtils.add(totalAmount, retDTO.getGoodsFreight());
        retDTO.setOrderItemTotalAmount(totalAmount);
        retDTO.setOrderItemPayAmount(totalAmount);
        retDTO.setHasProductplusFlag(YesNoEnum.NO.getValue());
        retDTO.setIsOutDistribtion(YesNoEnum.NO.getValue());
        retDTO.setHasUsedCoupon(YesNoEnum.NO.getValue());
        retDTO.setIsChangePrice(YesNoEnum.NO.getValue());
        retDTO.setOrderItemStatus(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
                DictionaryConst.OPT_ORDER_STATUS_VMS_WAIT_DOWNERP));
        retDTO.setIsCancelOrderItem(YesNoEnum.NO.getValue());
        retDTO.setCreateId(venusInDTO.getOperatorId());
        retDTO.setCreateName(venusInDTO.getOperatorName());
        retDTO.setModifyId(venusInDTO.getOperatorId());
        retDTO.setModifyName(venusInDTO.getOperatorName());

        itemWarehouseDetailDTO.setOrderNo(orderNo);
        itemWarehouseDetailDTO.setOrderItemNo(retDTO.getOrderItemNo());
        itemWarehouseDetailDTO.setSkuCode(retDTO.getSkuCode());
        itemWarehouseDetailDTO.setGoodsCount(goodsNum);
        itemWarehouseDetailDTO.setWarehouseCode(baseDTO.getWarehouseCode());
        itemWarehouseDetailDTO.setWarehouseName(baseDTO.getWarehouseName());
        itemWarehouseDetailDTO.setSupplierCode(baseDTO.getSupplierCode());
        itemWarehouseDetailDTO.setSupplierName(baseDTO.getSupplierName());
        itemWarehouseDetailDTO.setPurchaseDepartmentCode(baseDTO.getPurchaseDepartmentCode());
        itemWarehouseDetailDTO.setPurchaseDepartmentName(baseDTO.getPurchaseDepartmentName());
        itemWarehouseDetailDTO.setProductAttribute(baseDTO.getProductAttribute());
        itemWarehouseDetailDTO.setAvailableInventory(baseDTO.getAvailableInventory());
        itemWarehouseDetailDTO.setIsAgreementSku(baseDTO.getIsAgreementSku());
        itemWarehouseDetailDTO.setAgreementCode(baseDTO.getAgreementCode());
        itemWarehouseDetailDTO.setCreateId(venusInDTO.getOperatorId());
        itemWarehouseDetailDTO.setCreateName(venusInDTO.getOperatorName());
        itemWarehouseDetailDTO.setModifyId(venusInDTO.getOperatorId());
        itemWarehouseDetailDTO.setModifyName(venusInDTO.getOperatorName());
        itemWarehouseDetailList.add(itemWarehouseDetailDTO);
        retDTO.setWarehouseDTOList(itemWarehouseDetailList);

        itemStatusHistoryDTO.setOrderItemNo(retDTO.getOrderItemNo());
        itemStatusHistoryDTO.setOrderItemStatus(retDTO.getOrderItemStatus());
        itemStatusHistoryDTO.setOrderItemStatusText(
                baseService.setOrderShowStatus(dictMap, retDTO.getOrderItemStatus(), "", YesNoEnum.NO.getValue(), ""));
        itemStatusHistoryDTO.setCreateId(venusInDTO.getOperatorId());
        itemStatusHistoryDTO.setCreateName(venusInDTO.getOperatorName());
        itemStatusHistoryList.add(itemStatusHistoryDTO);
        retDTO.setItemStatusHistoryDTOList(itemStatusHistoryList);

        return retDTO;
    }

    public void setOrderStatusByCondition(TradeOrdersDTO tradeOrdersDTO, Map<String, DictionaryInfo> dictMap,
            List<TradeOrderErpDistributionDTO> orderErpDistributionDTOList) throws Exception {
        String memberCode = tradeOrdersDTO.getBuyerCode();
        ExecuteResult<Long> id = memberBaseInfoService.getMemberIdByCode(memberCode);
        if (id.isSuccess()) {
            ExecuteResult<MemberDetailInfo> memberInfo = memberBaseInfoService.getMemberDetailById(id.getResult());
            if (memberInfo.isSuccess()) {
                MemberBaseInfoDTO memberDTO = memberInfo.getResult().getMemberBaseInfoDTO();
                // 含有内部供应商身份的会员以及担保会员以及非会员
                if ((("2".equals(memberDTO.getMemberType()) || "3".equals(memberDTO.getMemberType()))
                        && "1".equals(memberDTO.getSellerType()) && memberDTO.getIsSeller() == 1)
                        || "1".equals(memberDTO.getMemberType())) {
                    tradeOrdersDTO.setOrderStatus(baseService.getDictValueByCode(dictMap,
                            DictionaryConst.TYPE_ORDER_STATUS, DictionaryConst.OPT_ORDER_STATUS_VMS_WAIT_DOWNERP));
                } else {
                    String confirmStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
                            DictionaryConst.OPT_ORDER_STATUS_WAIT_CONFIRM);
                    tradeOrdersDTO.setOrderStatus(confirmStatus);
                    tradeOrdersDTO.setPayTimeLimit(DateUtil.getDaysTime(1));
                    List<TradeOrderItemsDTO> itemList = tradeOrdersDTO.getOrderItemList();
                    for (TradeOrderItemsDTO itemDTO : itemList) {
                        List<TradeOrderItemsStatusHistoryDTO> itemHistoryList = itemDTO.getItemStatusHistoryDTOList();
                        for (TradeOrderItemsStatusHistoryDTO itemHistory : itemHistoryList) {
                            itemHistory.setOrderItemStatus(confirmStatus);
                            itemHistory.setOrderItemStatusText(baseService.getDictNameByCode(dictMap,
                                    DictionaryConst.TYPE_ORDER_STATUS, DictionaryConst.OPT_ORDER_STATUS_WAIT_CONFIRM));
                        }
                        itemDTO.setOrderItemStatus(confirmStatus);
                    }
                    for (TradeOrderErpDistributionDTO orderErp : orderErpDistributionDTOList) {
                        orderErp.setErpStatus(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ERP_STATUS,
                                DictionaryConst.OPT_ERP_STATUS_WAIT_CONFIRM));
                    }
                }
            } else {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_SEARCH_FOR_MEMBERID_ERROR,
                        "VMS开单查询会员信息失败");
            }
        } else {
            throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_SEARCH_FOR_MEMBERINFO_ERROR,
                    "VMS开单查询会员code失败");
        }
    }
}
