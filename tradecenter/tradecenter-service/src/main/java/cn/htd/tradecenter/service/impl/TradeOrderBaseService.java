/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	TradeOrderBaseService.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: 订单基础服务  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.service.TransactionRelationService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.AddressInfo;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.AddressUtils;
import cn.htd.common.util.DateUtils;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.SalemanDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBusinessRelationService;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.ShopExportService;
import cn.htd.tradecenter.common.constant.ReturnCodeConst;
import cn.htd.tradecenter.common.enums.ProductPlusDataEnum;
import cn.htd.tradecenter.common.enums.YesNoEnum;
import cn.htd.tradecenter.common.exception.TradeCenterBusinessException;
import cn.htd.tradecenter.common.utils.CalculateUtils;
import cn.htd.tradecenter.common.utils.GeneratorUtils;
import cn.htd.tradecenter.dto.CreateTradeOrderDTO;
import cn.htd.tradecenter.dto.TradeOrderErpDistributionDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsShowDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsWarehouseDetailDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsWarehouseDetailShowDTO;
import cn.htd.tradecenter.dto.TradeOrderPayInfoDTO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;
import cn.htd.tradecenter.dto.TradeOrdersQueryInDTO;
import cn.htd.tradecenter.dto.TradeOrdersShowDTO;
import cn.htd.tradecenter.dto.VenusTradeOrdersQueryInDTO;
import cn.htd.tradecenter.enums.OrderDownErrorStatusEnum;
import cn.htd.tradecenter.enums.OrderStatusEnum;
import cn.htd.tradecenter.enums.OuterChannelOrderStatusEnum;

@Service("tradeOrderBaseService")
public class TradeOrderBaseService {

	private static final Logger logger = LoggerFactory.getLogger(TradeOrderBaseService.class);

	@Resource
	private AddressUtils address;

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private GeneratorUtils noGenerator;

	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	@Resource
	private MemberBusinessRelationService memberBusinessRelationService;

	@Resource
	private TransactionRelationService transactionRelationService;

	@Resource
	private ShopExportService shopExportService;

	/**
	 * 获取messageId
	 *
	 * @return
	 * @throws TradeCenterBusinessException
	 */
	public String getMessageId() throws TradeCenterBusinessException {
		try {
			return noGenerator.generateMessageId(noGenerator.getHostIp());
		} catch (Exception e) {
			throw new TradeCenterBusinessException(ReturnCodeConst.SYSTEM_ERROR, e);
		}
	}

	/**
	 * 获取交易号
	 *
	 * @param orderFrom
	 * @return
	 */
	public String getTradeNo(String orderFrom) {
		String orderNoPrefix = orderFrom;
		orderNoPrefix = StringUtils.length(orderFrom) < 2 ? orderFrom + "0" : orderFrom;
		return noGenerator.generateTradeNo(orderNoPrefix);
	}

	/**
	 * 获取订单号
	 *
	 * @param orderFrom
	 * @return
	 */
	public String getOrderNo(String orderFrom) {
		String orderNoPrefix = orderFrom;
		orderNoPrefix = StringUtils.length(orderFrom) < 2 ? orderFrom + "0" : orderFrom;
		return noGenerator.generateOrderNo(orderNoPrefix);
	}

	/**
	 * 获取订单行号
	 *
	 * @param orderNo
	 * @return
	 */
	public String getOrderItemNo(String orderNo) {
		return noGenerator.generateOrderItemNo(orderNo);
	}

	/**
	 * 根据订单行金额计算订单总金额
	 * 
	 * @param dictMap
	 * @param orderDTO
	 */
	public void calculateTradeOrderAmount(Map<String, DictionaryInfo> dictMap, TradeOrdersDTO orderDTO) {
		List<TradeOrderItemsDTO> orderItemList = orderDTO.getOrderItemList();
		int totalGoodsCount = 0;
		BigDecimal totalFreight = BigDecimal.ZERO;
		BigDecimal totalGoodsAmount = BigDecimal.ZERO;
		BigDecimal totalDiscountAmount = BigDecimal.ZERO;
		BigDecimal totalShopDiscountAmount = BigDecimal.ZERO;
		BigDecimal totalPlatformDiscountAmount = BigDecimal.ZERO;
		BigDecimal totalUsedRebateAmount = BigDecimal.ZERO;
		BigDecimal bargainingOrderAmount = BigDecimal.ZERO;
		BigDecimal bargainingOrderFreight = BigDecimal.ZERO;
		BigDecimal orderTotalAmount = BigDecimal.ZERO;
		BigDecimal orderPayAmount = BigDecimal.ZERO;
		int hasUsedCoupon = YesNoEnum.NO.getValue();
		int isChangePrice = YesNoEnum.NO.getValue();
		int isOutDistribtion = YesNoEnum.NO.getValue();
		int hasProductplusFlag = YesNoEnum.NO.getValue();
		if (orderItemList == null || orderItemList.isEmpty()) {
			return;
		}
		for (TradeOrderItemsDTO itemDTO : orderItemList) {
			if (YesNoEnum.DELETE.getValue() == itemDTO.getIsCancelOrderItem()) {
				isChangePrice = YesNoEnum.YES.getValue();
				continue;
			}
			if (YesNoEnum.NO.getValue() != itemDTO.getIsCancelOrderItem()) {
				continue;
			}
			if (YesNoEnum.YES.getValue() == itemDTO.getIsChangePrice()) {
				isChangePrice = itemDTO.getIsChangePrice();
				totalGoodsCount += itemDTO.getBargainingGoodsCount();
				bargainingOrderAmount = CalculateUtils.add(bargainingOrderAmount, itemDTO.getBargainingGoodsAmount());
				bargainingOrderFreight = CalculateUtils.add(bargainingOrderFreight,
						itemDTO.getBargainingGoodsFreight());
			} else {
				totalGoodsCount += itemDTO.getGoodsCount();
				bargainingOrderAmount = CalculateUtils.add(bargainingOrderAmount, itemDTO.getGoodsAmount());
				bargainingOrderFreight = CalculateUtils.add(bargainingOrderFreight, itemDTO.getGoodsFreight());
			}
			totalGoodsAmount = CalculateUtils.add(totalGoodsAmount, itemDTO.getGoodsAmount());
			totalFreight = CalculateUtils.add(totalFreight, itemDTO.getGoodsFreight());
			totalDiscountAmount = CalculateUtils.add(totalDiscountAmount, itemDTO.getTotalDiscountAmount());
			totalShopDiscountAmount = CalculateUtils.add(totalShopDiscountAmount, itemDTO.getShopDiscountAmount());
			totalPlatformDiscountAmount = CalculateUtils.add(totalPlatformDiscountAmount,
					itemDTO.getPlatformDiscountAmount());
			totalUsedRebateAmount = CalculateUtils.add(totalUsedRebateAmount, itemDTO.getUsedRebateAmount());
			orderTotalAmount = CalculateUtils.add(orderTotalAmount, itemDTO.getOrderItemTotalAmount());
			orderPayAmount = CalculateUtils.add(orderPayAmount, itemDTO.getOrderItemPayAmount());
			if (YesNoEnum.YES.getValue() == itemDTO.getHasUsedCoupon()) {
				hasUsedCoupon = YesNoEnum.YES.getValue();
			}
			if (YesNoEnum.YES.getValue() == itemDTO.getIsOutDistribtion()) {
				isOutDistribtion = YesNoEnum.YES.getValue();
			}
			if (itemDTO.getChannelCode().startsWith(getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
					DictionaryConst.OPT_PRODUCT_CHANNEL_PRODUCTPLUS))) {
				hasProductplusFlag = YesNoEnum.YES.getValue();
			}
		}
		orderDTO.setTotalGoodsCount(totalGoodsCount);
		orderDTO.setTotalGoodsAmount(totalGoodsAmount);
		orderDTO.setTotalFreight(totalFreight);
		orderDTO.setTotalDiscountAmount(totalDiscountAmount);
		orderDTO.setShopDiscountAmount(totalShopDiscountAmount);
		orderDTO.setPlatformDiscountAmount(totalPlatformDiscountAmount);
		orderDTO.setUsedRebateAmount(totalUsedRebateAmount);
		if (YesNoEnum.YES.getValue() == isChangePrice) {
			orderDTO.setBargainingOrderAmount(bargainingOrderAmount);
			orderDTO.setBargainingOrderFreight(bargainingOrderFreight);
		} else {
			orderDTO.setBargainingOrderAmount(BigDecimal.ZERO);
			orderDTO.setBargainingOrderFreight(BigDecimal.ZERO);
		}
		orderDTO.setOrderTotalAmount(orderTotalAmount);
		orderDTO.setOrderPayAmount(orderPayAmount);
		orderDTO.setHasProductplusFlag(hasProductplusFlag);
		orderDTO.setIsChangePrice(isChangePrice);
		orderDTO.setIsOutDistribtion(isOutDistribtion);
		orderDTO.setHasUsedCoupon(hasUsedCoupon);
	}

	/**
	 * 通过订单行信息计算订单信息
	 * 
	 * @param dictMap
	 * @param orderNo
	 * @param orderBaseDTO
	 * @param orderItemList
	 * @return
	 * @throws TradeCenterBusinessException
	 */
	public TradeOrdersDTO createTradeOrderInfo(Map<String, DictionaryInfo> dictMap, String orderNo,
			CreateTradeOrderDTO orderBaseDTO, List<TradeOrderItemsDTO> orderItemList)
					throws TradeCenterBusinessException {
		TradeOrdersDTO orderDTO = new TradeOrdersDTO();
		ExecuteResult<ShopDTO> shopResult = null;
		ExecuteResult<MemberDetailInfo> memberResult = null;
		ExecuteResult<Boolean> buyerBlackRst = null;
		MemberDetailInfo memberDetailInfo = null;
		MemberBaseInfoDTO buyerInfo = null;
		MemberBaseInfoDTO sellerInfo = null;
		ShopDTO shopInfo = null;
		Long shopId = null;
		String shopName = "";

		memberResult = memberBaseInfoService.getMemberDetailById(orderBaseDTO.getBuyerId());
		if (!memberResult.isSuccess()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_NOT_EXIST,
					StringUtils.join(memberResult.getErrorMessages(), "\n"));
		}
		memberDetailInfo = memberResult.getResult();
		if (memberDetailInfo == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_NOT_EXIST, "会员买家信息不存在");
		}
		buyerInfo = memberDetailInfo.getMemberBaseInfoDTO();
		logger.info("*************会员中心取得买家信息:" + JSON.toJSONString(buyerInfo));

		buyerBlackRst = transactionRelationService.getTransactionRelationIsRelated(buyerInfo.getMemberCode());
		if (!buyerBlackRst.isSuccess()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_BLACK_LIST_ERROR,
					StringUtils.join(buyerBlackRst.getErrorMessages(), "\n"));
		}
		if (buyerBlackRst.getResult() != null && buyerBlackRst.getResult().booleanValue()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_BLACK_LIST_ERROR,
					"买家" + buyerInfo.getCompanyName() + "是关联企业不能下单");
		}
		memberResult = memberBaseInfoService.getMemberDetailBySellerId(orderBaseDTO.getSellerId());
		if (!memberResult.isSuccess()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.SELLER_NOT_EXIST,
					StringUtils.join(memberResult.getErrorMessages(), "\n"));
		}
		memberDetailInfo = memberResult.getResult();
		if (memberDetailInfo == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.SELLER_NOT_EXIST, "卖家信息不存在");
		}
		sellerInfo = memberDetailInfo.getMemberBaseInfoDTO();
		logger.info("*************会员中心取得卖家信息:" + JSON.toJSONString(sellerInfo));
		shopResult = shopExportService.queryBySellerId(orderBaseDTO.getSellerId());
		if (!shopResult.isSuccess()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.SHOP_NOT_EXIST,
					StringUtils.join(shopResult.getErrorMessages(), "\n"));
		}
		shopInfo = shopResult.getResult();
		if (shopInfo == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.SHOP_NOT_EXIST, "卖家店铺信息不存在");
		}
		shopId = shopInfo.getShopId();
		shopName = shopInfo.getShopName();

		orderDTO.setOrderNo(orderNo);
		orderDTO.setBuyerId(orderBaseDTO.getBuyerId());
		orderDTO.setBuyerCode(buyerInfo.getMemberCode());
		orderDTO.setBuyerType(buyerInfo.getMemberType());
		orderDTO.setBuyerName(buyerInfo.getCompanyName());
		orderDTO.setSellerId(orderBaseDTO.getSellerId());
		orderDTO.setSellerCode(sellerInfo.getMemberCode());
		orderDTO.setSellerType(sellerInfo.getSellerType());
		orderDTO.setSellerName(sellerInfo.getCompanyName());
		orderDTO.setSellerErpCode(sellerInfo.getCompanyCode());
		orderDTO.setOrderRemarks(orderBaseDTO.getOrderRemarks());
		orderDTO.setShopId(shopId);
		orderDTO.setShopName(shopName);
		orderDTO.setDeliveryType(orderBaseDTO.getDeliveryType());
		orderDTO.setIsNeedInvoice(orderBaseDTO.getIsNeedInvoice());
		if (YesNoEnum.YES.getValue() == orderBaseDTO.getIsNeedInvoice()) {
			orderDTO.setInvoiceType(orderBaseDTO.getInvoiceType());
			if (getDictValueByCode(dictMap, DictionaryConst.TYPE_INVOICE_TYPE, DictionaryConst.OPT_INVOICE_TYPE_PLAIN)
					.equals(orderBaseDTO.getInvoiceType())) {
				orderDTO.setInvoiceNotify(orderBaseDTO.getInvoiceNotify());
				orderDTO.setTaxManId(orderBaseDTO.getTaxManId());
			} else if (getDictValueByCode(dictMap, DictionaryConst.TYPE_INVOICE_TYPE,
					DictionaryConst.OPT_INVOICE_TYPE_VAT).equals(orderBaseDTO.getInvoiceType())) {
				orderDTO.setInvoiceCompanyName(orderBaseDTO.getInvoiceCompanyName());
				orderDTO.setTaxManId(orderBaseDTO.getTaxManId());
				orderDTO.setBankName(orderBaseDTO.getBankName());
				orderDTO.setBankAccount(orderBaseDTO.getBankAccount());
				orderDTO.setContactPhone(orderBaseDTO.getContactPhone());
				orderDTO.setInvoiceAddress(orderBaseDTO.getInvoiceAddress());
			}
		}
		orderDTO.setConsigneeName(orderBaseDTO.getConsigneeName());
		orderDTO.setConsigneePhoneNum(orderBaseDTO.getConsigneePhoneNum());
		orderDTO.setConsigneeAddress(orderBaseDTO.getConsigneeAddress());
		orderDTO.setConsigneeAddressProvince(orderBaseDTO.getConsigneeAddressProvince());
		orderDTO.setConsigneeAddressCity(orderBaseDTO.getConsigneeAddressCity());
		orderDTO.setConsigneeAddressDistrict(orderBaseDTO.getConsigneeAddressDistrict());
		orderDTO.setConsigneeAddressTown(orderBaseDTO.getConsigneeAddressTown());
		orderDTO.setConsigneeAddressDetail(orderBaseDTO.getConsigneeAddressDetail());
		orderDTO.setPostCode(orderBaseDTO.getPostCode());
		orderDTO.setOrderItemList(orderItemList);
		calculateTradeOrderAmount(dictMap, orderDTO);
		orderDTO.setCreateId(orderBaseDTO.getOperatorId());
		orderDTO.setCreateName(orderBaseDTO.getOperatorName());
		orderDTO.setModifyId(orderBaseDTO.getOperatorId());
		orderDTO.setModifyName(orderBaseDTO.getOperatorName());

		return orderDTO;
	}

	/**
	 * 根据订单行数据生成收付款数据
	 *
	 * @param dictMap
	 * @param orderDTO
	 * @throws Exception
	 */
	public void createTradeOrderPayInfo(Map<String, DictionaryInfo> dictMap, TradeOrdersDTO orderDTO) throws Exception {
		List<TradeOrderItemsDTO> orderItemList = orderDTO.getOrderItemList();
		List<TradeOrderPayInfoDTO> orderPayList = new ArrayList<TradeOrderPayInfoDTO>();
		Map<String, TradeOrderPayInfoDTO> orderPayInfoMap = new HashMap<String, TradeOrderPayInfoDTO>();
		TradeOrderPayInfoDTO orderPayInfo = null;
		BigDecimal orderItemTotalAmount = BigDecimal.ZERO;
		BigDecimal tmpTotalAmount = BigDecimal.ZERO;
		String key = "";
		String brandCode = "";
		String categoryCode = "";
		if (orderItemList == null || orderItemList.isEmpty()) {
			return;
		}
		for (TradeOrderItemsDTO itemDTO : orderItemList) {
			if (YesNoEnum.NO.getValue() != itemDTO.getIsCancelOrderItem()) {
				continue;
			}
			if (StringUtils.isEmpty(itemDTO.getChannelCode())) {
				continue;
			}
			brandCode = itemDTO.getBrandId().toString();
			categoryCode = itemDTO.getErpFirstCategoryCode();
			orderItemTotalAmount = itemDTO.getOrderItemTotalAmount();
			if (itemDTO.getChannelCode().equals(getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
					DictionaryConst.OPT_PRODUCT_CHANNEL_JD))) {
				brandCode = ProductPlusDataEnum.JD_BRAND_CODE_ERP.getCode();
				categoryCode = ProductPlusDataEnum.JD_CATEGORY_CODE_ERP.getCode();
			}
			key = brandCode + "&" + categoryCode;
			if (orderPayInfoMap.containsKey(key)) {
				orderPayInfo = orderPayInfoMap.get(key);
			} else {
				orderPayInfo = new TradeOrderPayInfoDTO();
				orderPayInfo.setDownOrderNo(getMessageId());
				orderPayInfo.setOrderType(YesNoEnum.YES.getValue());
				orderPayInfo.setPayResultStatus(YesNoEnum.NO.getValue());
				orderPayInfo.setPaySendCount(0);
				orderPayInfo.setBrandCode(brandCode);
				orderPayInfo.setClassCode(categoryCode);
				orderPayInfo.setOrderNo(orderDTO.getOrderNo());
				orderPayInfo.setSupplierCode(orderDTO.getSellerCode());
				orderPayInfo.setIsLockBalanceFlag(YesNoEnum.YES.getValue());
				orderPayInfo.setMemberCode(orderDTO.getBuyerCode());
				orderPayInfo.setProductCode(ProductPlusDataEnum.JD_BRAND_CODE_ERP.getCode().equals(brandCode)
						? ProductPlusDataEnum.PRODUCT_CODE_ERP_EXTERNAL_SUPPLIER.getCode()
						: ProductPlusDataEnum.PRODUCT_CODE_ERP.getCode());
				orderPayInfo.setOrderFrom(orderDTO.getOrderFrom());
			}
			tmpTotalAmount = orderPayInfo.getAmount();
			orderPayInfo.setAmount(CalculateUtils.add(tmpTotalAmount, orderItemTotalAmount));
			orderPayInfoMap.put(key, orderPayInfo);
		}
		for (Entry<String, TradeOrderPayInfoDTO> entry : orderPayInfoMap.entrySet()) {
			orderPayList.add(entry.getValue());
		}
		orderDTO.setOrderPayDTOList(orderPayList);
	}

	/**
	 * 通过订单行信息创建分销单信息
	 *
	 * @param dictMap
	 * @param order
	 * @param orderItemList
	 * @return
	 * @throws TradeCenterBusinessException
	 */
	public List<TradeOrderErpDistributionDTO> createTradeOrderErpDistributionInfo(Map<String, DictionaryInfo> dictMap,
			TradeOrdersDTO order, List<TradeOrderItemsDTO> orderItemList) throws TradeCenterBusinessException {
		List<TradeOrderErpDistributionDTO> erpDistributionList = new ArrayList<TradeOrderErpDistributionDTO>();
		Map<String, TradeOrderErpDistributionDTO> erpDistributionMap = new HashMap<String, TradeOrderErpDistributionDTO>();
		TradeOrderErpDistributionDTO erpDistributionDTO = null;
		Map<String, MemberBusinessRelationDTO> businessRelationMap = null;
		ExecuteResult<MemberDetailInfo> memberResult = null;
		MemberDetailInfo memberDetailInfo = null;
		MemberBaseInfoDTO sellerInfo = null;
		String categoryCode = "";
		String brandId = "";
		String key = "";
		String orderItemNos = "";
		boolean hasInitBusinessRelationFlag = false;
		MemberBusinessRelationDTO businessRelationDTO = null;
		List<MemberBusinessRelationDTO> newBusinessRelationList = new ArrayList<MemberBusinessRelationDTO>();
		ExecuteResult<Long> buyerSellerResult = null;
		if (order == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_EXIST, "订单信息为空");
		}
		if (orderItemList == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NOT_EXIST, "订单信息行为空");
		}
		for (TradeOrderItemsDTO itemDTO : orderItemList) {
			if (YesNoEnum.NO.getValue() != itemDTO.getIsCancelOrderItem()) {
				continue;
			}
			categoryCode = itemDTO.getErpFirstCategoryCode();
			brandId = itemDTO.getBrandId().toString();
			key = categoryCode + "_" + brandId;
			if (erpDistributionMap.containsKey(key)) {
				erpDistributionDTO = erpDistributionMap.get(key);
				orderItemNos = erpDistributionDTO.getOrderItemNos() + "," + itemDTO.getOrderItemNo();
				erpDistributionDTO.setOrderItemNos(orderItemNos);
				if (StringUtils.isEmpty(itemDTO.getCustomerManagerCode())) {
					itemDTO.setCustomerManagerCode(erpDistributionDTO.getCustomerManagerCode());
					itemDTO.setCustomerManagerName(erpDistributionDTO.getCustomerManagerName());
				}
			} else {
				if (!hasInitBusinessRelationFlag) {
					hasInitBusinessRelationFlag = true;
					businessRelationMap = getTradeOrderBusinessRelation(order);
				}
				if (StringUtils.isEmpty(itemDTO.getCustomerManagerCode())) {
					itemDTO.setTradeOrderInfo(order);
					businessRelationDTO = setCustomerManagerInfo(dictMap, itemDTO, businessRelationMap);
					if (businessRelationDTO != null) {
						newBusinessRelationList.add(businessRelationDTO);
					}
				}
				erpDistributionDTO = new TradeOrderErpDistributionDTO();
				erpDistributionDTO.setOrderNo(order.getOrderNo());
				erpDistributionDTO.setBuyerCode(order.getBuyerCode());
				erpDistributionDTO.setSellerCode(order.getSellerCode());
				erpDistributionDTO.setErpLockBalanceCode(getMessageId());
				erpDistributionDTO.setCategoryId(itemDTO.getThirdCategoryId());
				erpDistributionDTO.setCategoryName(itemDTO.getThirdCategoryName());
				erpDistributionDTO.setErpFirstCategoryCode(categoryCode);
				erpDistributionDTO.setBrandId(itemDTO.getBrandId());
				erpDistributionDTO.setBrandName(itemDTO.getBrandName());
				erpDistributionDTO
						.setServiceArea(order.getSellerErpCode() + (StringUtils.isEmpty(order.getConsigneeAddressTown())
								? order.getConsigneeAddressDistrict() : order.getConsigneeAddressTown()));
				erpDistributionDTO.setCustomerManagerCode(itemDTO.getCustomerManagerCode());
				erpDistributionDTO.setCustomerManagerName(itemDTO.getCustomerManagerName());
				erpDistributionDTO.setOrderItemNos(itemDTO.getOrderItemNo());
				// VMS开单新增分销单待确认状态
				setERPStatusByCondition(erpDistributionDTO, dictMap);
				erpDistributionDTO.setCreateId(order.getCreateId());
				erpDistributionDTO.setCreateName(order.getCreateName());
				erpDistributionDTO.setModifyId(order.getModifyId());
				erpDistributionDTO.setModifyName(order.getModifyName());
			}
			erpDistributionMap.put(key, erpDistributionDTO);
		}
		createNewBusinessRelation(newBusinessRelationList);
		for (Entry<String, TradeOrderErpDistributionDTO> entry : erpDistributionMap.entrySet()) {
			erpDistributionList.add(entry.getValue());
		}
		return erpDistributionList;
	}

	private void setERPStatusByCondition(TradeOrderErpDistributionDTO erpDistributionDTO,
			Map<String, DictionaryInfo> dictMap) throws TradeCenterBusinessException {
		String memberCode = erpDistributionDTO.getBuyerCode();
		ExecuteResult<Long> id = memberBaseInfoService.getMemberIdByCode(memberCode);
		if (id.isSuccess()) {
			ExecuteResult<MemberDetailInfo> memberInfo = memberBaseInfoService.getMemberDetailById(id.getResult());
			if (memberInfo.isSuccess()) {
				MemberBaseInfoDTO memberDTO = memberInfo.getResult().getMemberBaseInfoDTO();
				// 含有内部供应商身份的会员以及担保会员以及非会员
				if ((("2".equals(memberDTO.getMemberType()) || "3".equals(memberDTO.getMemberType()))
						&& "1".equals(memberDTO.getSellerType()) && memberDTO.getIsSeller() == 1)
						|| "1".equals(memberDTO.getMemberType())) {
					erpDistributionDTO.setErpStatus(getDictValueByCode(dictMap, DictionaryConst.TYPE_ERP_STATUS,
							DictionaryConst.OPT_ERP_STATUS_PENDING));
				} else {
					erpDistributionDTO.setErpStatus(getDictValueByCode(dictMap, DictionaryConst.TYPE_ERP_STATUS,
							DictionaryConst.OPT_ERP_STATUS_WAIT_CONFIRM));
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

	/**
	 * 通过订单信息取得买家卖家经营关系
	 *
	 * @param orderDTO
	 * @return
	 * @throws TradeCenterBusinessException
	 */
	public Map<String, MemberBusinessRelationDTO> getTradeOrderBusinessRelation(TradeOrdersDTO orderDTO)
			throws TradeCenterBusinessException {
		List<MemberBusinessRelationDTO> businessRelationList = null;
		Map<String, MemberBusinessRelationDTO> businessRelationMap = new HashMap<String, MemberBusinessRelationDTO>();
		ExecuteResult<Long> buyerResult = null;
		ExecuteResult<Long> sellerResult = null;
		ExecuteResult<MemberDetailInfo> memberResult = null;
		MemberDetailInfo memberDetailInfo = null;
		MemberBaseInfoDTO sellerInfo = null;
		String key = "";
		if (orderDTO == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_EXIST, "订单信息为空");
		}
		buyerResult = memberBaseInfoService.getMemberIdByCode(orderDTO.getBuyerCode());
		if (!buyerResult.isSuccess()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_NOT_EXIST,
					"买家编号=" + orderDTO.getBuyerCode() + " 的订单会员信息不存在");
		}
		if (buyerResult.getResult() == null || buyerResult.getResult().equals(0L)) {
			throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_NOT_EXIST,
					"买家编号=" + orderDTO.getBuyerCode() + " 的订单会员信息不存在");
		}
		orderDTO.setBuyerId(buyerResult.getResult());
		logger.info("订单:" + orderDTO.getOrderNo() + ",*************会员中心取得getBuyerCode:" + orderDTO.getBuyerCode()
				+ ",BuyerId:" + buyerResult.getResult());
		sellerResult = memberBaseInfoService.getMemberIdByCode(orderDTO.getSellerCode());
		if (!sellerResult.isSuccess()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.SELLER_NOT_EXIST,
					"商家编号=" + orderDTO.getSellerCode() + " 的订单卖家信息不存在");
		}
		if (sellerResult.getResult() == null || sellerResult.getResult().equals(0L)) {
			throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_NOT_EXIST,
					"商家编号=" + orderDTO.getSellerCode() + " 的订单会员信息不存在");
		}
		orderDTO.setSellerId(sellerResult.getResult());
		logger.info("订单:" + orderDTO.getOrderNo() + ",*************会员中心取得getSellerCode:" + orderDTO.getSellerCode()
				+ ",SellerId:" + sellerResult.getResult());
		memberResult = memberBaseInfoService.getMemberDetailBySellerId(orderDTO.getSellerId());
		if (!memberResult.isSuccess()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.SELLER_NOT_EXIST,
					StringUtils.join(memberResult.getErrorMessages(), "\n"));
		}
		memberDetailInfo = memberResult.getResult();
		if (memberDetailInfo == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.SELLER_NOT_EXIST,
					"商家编号=" + orderDTO.getSellerCode() + " 的卖家信息不存在");
		}
		sellerInfo = memberDetailInfo.getMemberBaseInfoDTO();
		if (sellerInfo == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.SELLER_NOT_EXIST,
					"商家编号=" + orderDTO.getSellerCode() + " 的卖家基础信息不存在");
		}
		logger.info("*************会员中心取得卖家信息:" + JSON.toJSONString(sellerInfo));
		orderDTO.setSellerErpCode(sellerInfo.getCompanyCode());
		businessRelationList = getBuyerSellerBusinessRelationList(orderDTO.getBuyerId(), orderDTO.getSellerId());
		if (businessRelationList != null && !businessRelationList.isEmpty()) {
			for (MemberBusinessRelationDTO relationDTO : businessRelationList) {
				key = relationDTO.getCategoryId() + "_" + relationDTO.getBrandId();
				businessRelationMap.put(key, relationDTO);
			}
		}
		return businessRelationMap;
	}

	/**
	 * 根据经营关系取得客户经理信息，如果没有经营关系则创建经营关系
	 *
	 * @param buyerId
	 * @param sellerId
	 * @return
	 * @throws TradeCenterBusinessException
	 */
	private List<MemberBusinessRelationDTO> getBuyerSellerBusinessRelationList(Long buyerId, Long sellerId)
			throws TradeCenterBusinessException {
		MemberBusinessRelationDTO businessRelationCondition = new MemberBusinessRelationDTO();
		ExecuteResult<DataGrid<MemberBusinessRelationDTO>> businessRelationResult = null;
		if (buyerId == null || sellerId == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "买家或卖家ID不存在");
		}
		businessRelationCondition.setBuyerId(buyerId.toString());
		businessRelationCondition.setSellerId(sellerId.toString());
		businessRelationResult = memberBusinessRelationService
				.queryMemberBusinessRelationListInfo(businessRelationCondition, null);
		if (!businessRelationResult.isSuccess()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_BUSINESS_RELATION_NOT_EXIST,
					StringUtils.join(businessRelationResult.getErrorMessages(), "\n"));
		}
		return businessRelationResult.getResult().getRows() != null ? businessRelationResult.getResult().getRows()
				: new ArrayList<MemberBusinessRelationDTO>();
	}

	/**
	 * 根据经营关系取得客户经理信息，如果没有经营关系则创建经营关系
	 *
	 * @param dictMap
	 * @param orderItemsDTO
	 * @param businessRelationMap
	 * @return
	 * @throws TradeCenterBusinessException
	 */
	public MemberBusinessRelationDTO setCustomerManagerInfo(Map<String, DictionaryInfo> dictMap,
			TradeOrderItemsDTO orderItemsDTO, Map<String, MemberBusinessRelationDTO> businessRelationMap)
					throws TradeCenterBusinessException {
		String firstCustomerManagerCode = "";
		String firstCustomerManagerName = "";
		String customerManagerCode = "";
		String customerManagerName = "";
		String key = orderItemsDTO.getThirdCategoryId() + "_" + orderItemsDTO.getBrandId();
		MemberBusinessRelationDTO relationDTO = null;
		boolean hasSetCustomerManagerFlg = false;
		ExecuteResult<List<SalemanDTO>> customerManagerResult = null;
		List<SalemanDTO> customerManagerList = null;
		MemberBusinessRelationDTO newBusinessRelationDTO = null;
		if (businessRelationMap != null && !businessRelationMap.isEmpty()) {
			if (businessRelationMap.containsKey(key)) {
				relationDTO = businessRelationMap.get(key);
				customerManagerCode = relationDTO.getCustomerManagerId();
				customerManagerName = memberBaseInfoService.getManagerName(relationDTO.getSellerId(),
						customerManagerCode);
				orderItemsDTO.setCustomerManagerCode(customerManagerCode);
				orderItemsDTO.setCustomerManagerName(customerManagerName);
				return null;
			}
		}
		customerManagerResult = memberBaseInfoService.getManagerList(orderItemsDTO.getSellerCode());
		if (!customerManagerResult.isSuccess()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.BUYER_BUSINESS_RELATION_NOT_EXIST,
					"商家编号=" + orderItemsDTO.getSellerCode() + " 没有客户经理信息");
		}
		customerManagerList = customerManagerResult.getResult();
		if (customerManagerList != null && !customerManagerList.isEmpty()) {
			for (SalemanDTO cManagerInfo : customerManagerList) {
				customerManagerCode = cManagerInfo.getCustomerManagerCode();
				customerManagerName = cManagerInfo.getCustomerManagerName();
				if (StringUtils.isEmpty(firstCustomerManagerCode)) {
					firstCustomerManagerCode = customerManagerCode;
					firstCustomerManagerName = customerManagerName;
				}
				if ((orderItemsDTO.getSellerErpCode() + "9999").equals(customerManagerCode)) {
					orderItemsDTO.setCustomerManagerCode(customerManagerCode);
					orderItemsDTO.setCustomerManagerName(customerManagerName);
					hasSetCustomerManagerFlg = true;
					break;
				}
			}
			if (!hasSetCustomerManagerFlg) {
				orderItemsDTO.setCustomerManagerCode(firstCustomerManagerCode);
				orderItemsDTO.setCustomerManagerName(firstCustomerManagerName);
			}
			newBusinessRelationDTO = new MemberBusinessRelationDTO();
			newBusinessRelationDTO.setSellerId(orderItemsDTO.getSellerId().toString());
			newBusinessRelationDTO.setBuyerId(orderItemsDTO.getBuyerId().toString());
			newBusinessRelationDTO.setCustomerManagerId(orderItemsDTO.getCustomerManagerCode());
			newBusinessRelationDTO.setCustomerManagerName(orderItemsDTO.getCustomerManagerName());
			newBusinessRelationDTO.setCategoryId(orderItemsDTO.getThirdCategoryId());
			newBusinessRelationDTO.setCategoryName(orderItemsDTO.getThirdCategoryName());
			newBusinessRelationDTO.setBrandId(orderItemsDTO.getBrandId());
			newBusinessRelationDTO.setBrandName(orderItemsDTO.getBrandName());
			newBusinessRelationDTO.setAuditStatus(getDictValueByCode(dictMap, DictionaryConst.TYPE_VERIFY_TYPE,
					DictionaryConst.OPT_VERIFY_TYPE_PASS));
			newBusinessRelationDTO.setOperateId("0");
			newBusinessRelationDTO.setOperateName("sys");
			if (businessRelationMap == null) {
				businessRelationMap = new HashMap<String, MemberBusinessRelationDTO>();
			}
			businessRelationMap.put(key, newBusinessRelationDTO);
		} else {
			throw new TradeCenterBusinessException(ReturnCodeConst.CUSTOMER_MANAGER_NOT_EXIST,
					"商家编号=" + orderItemsDTO.getSellerCode() + "没有客户经理信息");
		}
		return newBusinessRelationDTO;
	}

	/**
	 * 创建新的经营关系及包厢关系
	 *
	 * @param newBusinessRelationList
	 * @throws TradeCenterBusinessException
	 */
	public void createNewBusinessRelation(List<MemberBusinessRelationDTO> newBusinessRelationList)
			throws TradeCenterBusinessException {
		ExecuteResult<Boolean> createBusinessRelationResult = null;
		if (newBusinessRelationList != null && newBusinessRelationList.size() > 0) {
			createBusinessRelationResult = memberBusinessRelationService
					.insertMemberBusinessRelationInfo(newBusinessRelationList);
			if (!createBusinessRelationResult.isSuccess()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.CREATE_BUSINESS_RELATION_ERROR,
						StringUtils.join(createBusinessRelationResult.getErrorMessages(), "\n"));
			}
			if (!createBusinessRelationResult.getResult().booleanValue()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.CREATE_BUSINESS_RELATION_ERROR,
						"新增会员ID:" + newBusinessRelationList.get(0).getBuyerId() + "商家ID:"
								+ newBusinessRelationList.get(0).getSellerId() + "的经营关系失败 "
								+ JSONObject.toJSONString(newBusinessRelationList));
			}
		}
	}

	/**
	 * 根据订单状态设定订单显示状态描述
	 *
	 * @param dictMap
	 * @param orderStatus
	 * @param errorStatus
	 * @param cancelFlag
	 * @param refundStatus
	 * @return
	 */
	public String setOrderShowStatus(Map<String, DictionaryInfo> dictMap, String orderStatus, String errorStatus,
			int cancelFlag, String refundStatus) {
		// 订单行删除时
		if (YesNoEnum.DELETE.getValue() == cancelFlag) {
			return YesNoEnum.DELETE.getName();
		}
		// 订单状态为空时
		if (StringUtils.isEmpty(orderStatus)) {
			return "";
		}
		// 订单取消时
		if (YesNoEnum.YES.getValue() == cancelFlag) {
			if (!StringUtils.isEmpty(refundStatus)) {
				return getDictNameByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_RETURN);
			}
			// 订单行状态为代发货时，订单状态为订单已退货
			if (getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_DELIVERY).compareTo(orderStatus) <= 0) {
				return getDictNameByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_RETURN);
				// 订单行状态为已支付时，订单状态为订单已取消
			} else if (getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_PAID).compareTo(orderStatus) <= 0) {
				return getDictNameByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_CANCEL);
				// 订单行状态为未支付时，订单状态为订单已关闭
			} else if (getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY).compareTo(orderStatus) <= 0) {
				return getDictNameByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_CLOSE);
				// 订单行状态为待审核时，订单状态为审核拒绝
			} else if (getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_CONFIRM).compareTo(orderStatus) <= 0) {
				return getDictNameByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_CANCEL);
				// 订单行状态为待审核时，订单状态为审核拒绝
			}else if (getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VERIFY_PENDING).equals(orderStatus)) {
				return getDictNameByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_VERIFY_REFUSE);
			}
		}
		// 订单异常时
		if (!StringUtils.isEmpty(errorStatus)) {
			return getDictDescribeByValue(dictMap, DictionaryConst.TYPE_ORDER_STATUS, errorStatus);
		}
		// 订单已收货时
		if (getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS, DictionaryConst.OPT_ORDER_STATUS_RECEIVED)
				.compareTo(orderStatus) <= 0) {
			return getDictNameByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_COMPLETE);
		}
		// 订单正常时
		return getDictDescribeByValue(dictMap, DictionaryConst.TYPE_ORDER_STATUS, orderStatus);
	}

	/**
	 * 根据输入条件订单异常状态设定检索条件
	 *
	 * @param dictMap
	 * @param inDTO
	 */
	public void setOrderErrorStatusCondition(Map<String, DictionaryInfo> dictMap, TradeOrdersQueryInDTO inDTO) {
		List<String> orderStatusList = new ArrayList<String>();
		inDTO.setIsCancelFlag(YesNoEnum.NO.getValue());
		// 订单行状态的检索条件为空时
		if (StringUtils.isEmpty(inDTO.getOrderItemErrorStatus())) {
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_PENDING));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_ERP_DOWNING));
		} else {
			// 订单行为五合一下行异常状态
			if (OrderDownErrorStatusEnum.ERP_DOWN_ERROR.getValue().equals(inDTO.getOrderItemErrorStatus())) {
				orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_ERP_DOWNING));
				inDTO.setChannelCode(getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
						DictionaryConst.OPT_PRODUCT_CHANNEL_HTD));
				// 订单行为预售下行异常
			} else if (OrderDownErrorStatusEnum.PRE_SALES_ERROR.getValue().equals(inDTO.getOrderItemErrorStatus())) {
				orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_ERP_DOWNING));
				inDTO.setChannelCode(getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
						DictionaryConst.OPT_PRODUCT_CHANNEL_PRODUCTPLUS));
				// 订单行为收付款异常
			} else if (OrderDownErrorStatusEnum.POST_STRIKEA_ERROR.getValue().equals(inDTO.getOrderItemErrorStatus())) {
				orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_PENDING));
			} else {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "下行异常参数不正确");
			}
		}
		inDTO.setOrderItemErrorStatus("");
		inDTO.setOrderStatusList(orderStatusList);
	}

	/**
	 * 根据输入条件设定订单行状态的检索条件
	 * 
	 * @param dictMap
	 * @param inDTO
	 */
	public void setOrderStatusCondition(Map<String, DictionaryInfo> dictMap, VenusTradeOrdersQueryInDTO inDTO) {
		List<String> orderStatusList = new ArrayList<String>();
		OrderStatusEnum orderStatusEnumObj = null;
		String statusConditionCode = "";
		// 订单行状态的检索条件为空时
		if (StringUtils.isEmpty(inDTO.getOrderStatus())) {
			inDTO.setIsCancelFlag(-1);
			return;
		}
		orderStatusEnumObj = OrderStatusEnum.getEnum(inDTO.getOrderStatus());
		if (orderStatusEnumObj == null) {
			return;
		}
		inDTO.setIsErrorFlag(YesNoEnum.NO.getValue());
		statusConditionCode = orderStatusEnumObj.getCode();
		// 订单行为待支付时，包含商城下单待支付、审核通过待支付、VMS开单待下行ERP三种状态
		if (DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY.equals(statusConditionCode)) {
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VMS_WAIT_DOWNERP));
			inDTO.setIsCancelFlag(YesNoEnum.NO.getValue());
			// 订单行为已支付时，包含商城下单已支付、已支付待拆单、已拆单待下行ERP，ERP下行中四种状态
		} else if (DictionaryConst.OPT_ORDER_STATUS_PAID.equals(statusConditionCode)) {
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_PAID));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_PENDING));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_SUCCESS));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_PAID_DISTRIBUTED));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_ERP_DOWNING));
			inDTO.setIsCancelFlag(YesNoEnum.NO.getValue());
			// 订单行为已收货时，包含买家收货、订单自动收货、已完成三种状态
		} else if (DictionaryConst.OPT_ORDER_STATUS_RECEIVED.equals(statusConditionCode)) {
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_BUYER_RECEIVED));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_AUTO_RECEIVED));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_COMPLETE));
			inDTO.setIsCancelFlag(YesNoEnum.NO.getValue());
			// 订单行为审核拒绝时，为订单行状态为待审核，且订单取消状态为已取消
		} else if (DictionaryConst.OPT_ORDER_STATUS_VERIFY_REFUSE.equals(statusConditionCode)) {
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VERIFY_PENDING));
			inDTO.setIsCancelFlag(YesNoEnum.YES.getValue());
			// 订单行为已关闭时，为订单行状态包含商城下单待支付、审核通过待支付、VMS开单待下行ERP以及商城下单已支付、已支付待拆单、已拆单待下行ERP六种状态，且订单取消状态为已取消
		} else if (DictionaryConst.OPT_ORDER_STATUS_CLOSE.equals(statusConditionCode)) {
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VMS_WAIT_DOWNERP));
			inDTO.setIsCancelFlag(YesNoEnum.YES.getValue());
			// 订单行为已取消时，为订单行状态包含商城下单已支付、已支付待拆单、已拆单待下行ERP六种状态，且订单取消状态为已取消
		} else if (DictionaryConst.OPT_ORDER_STATUS_CANCEL.equals(statusConditionCode)) {
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_CONFIRM));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_PAID));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_PENDING));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_SUCCESS));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_PAID_DISTRIBUTED));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_ERP_DOWNING));
			inDTO.setIsCancelFlag(YesNoEnum.YES.getValue());
			// 订单行退货时
		} else if (DictionaryConst.OPT_ORDER_STATUS_RETURN.equals(statusConditionCode)) {
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_DELIVERY));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_CONSIGNED));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_BUYER_RECEIVED));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_AUTO_RECEIVED));
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_COMPLETE));
			inDTO.setIsCancelFlag(YesNoEnum.YES.getValue());
			// 订单行为异常时
		} else if (DictionaryConst.OPT_ORDER_STATUS_ERROR.equals(statusConditionCode)) {
			inDTO.setIsErrorFlag(YesNoEnum.YES.getValue());
			// 其他订单行状态时
		} else {
			orderStatusList.add(getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS, statusConditionCode));
			inDTO.setIsCancelFlag(YesNoEnum.NO.getValue());
		}
		inDTO.setOrderStatusList(orderStatusList);
	}

	/**
	 * 根据输入条件设定订单行商品+状态的检索条件
	 *
	 * @param dictMap
	 * @param inDTO
	 */
	public void setOrderItemProductPlusStatusCondition(Map<String, DictionaryInfo> dictMap,
			TradeOrdersQueryInDTO inDTO) {
		OuterChannelOrderStatusEnum productPlusStatusEnumObj = null;
		String productPlusConditionCode = "";
		// 订单行状态的检索条件为空时
		if (StringUtils.isEmpty(inDTO.getOuterChannelPuchaseStatus())) {
			return;
		}
		productPlusStatusEnumObj = OuterChannelOrderStatusEnum.getEnum(inDTO.getOuterChannelPuchaseStatus());
		if (productPlusStatusEnumObj == null) {
			return;
		}
		productPlusConditionCode = productPlusStatusEnumObj.getCode();
		// 采购订单状态为确认订单失败
		if (DictionaryConst.OPT_ORDER_STATUS_PRODUCT_PLUS_CONFIRM_ERROR.equals(productPlusConditionCode)) {
			inDTO.setOrderItemErrorStatus(
					getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS, productPlusConditionCode));
			inDTO.setOuterChannelPuchaseStatus("");
		}
	}

	/**
	 * 根据输入条件的商品渠道设定检索条件
	 *
	 * @param dictMap
	 * @param inDTO
	 */
	public void setProductChannelCondition(Map<String, DictionaryInfo> dictMap, TradeOrdersQueryInDTO inDTO) {
		List<String> productPlusChannelCodeList = new ArrayList<String>();
		List<DictionaryInfo> channelDictList = null;
		String productPlusCode = getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
				DictionaryConst.OPT_PRODUCT_CHANNEL_PRODUCTPLUS);
		// 商品来源为空时
		if (StringUtils.isEmpty(inDTO.getChannelCode())) {
			return;
		}
		channelDictList = getDictOptList(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL);
		if (channelDictList == null || channelDictList.isEmpty()) {
			return;
		}
		if (productPlusCode.equals(inDTO.getChannelCode())) {
			inDTO.setChannelCode("");
			for (DictionaryInfo dictInfo : channelDictList) {
				if (productPlusCode.equals(dictInfo.getValue())) {
					continue;
				}
				if (dictInfo.getValue().startsWith(productPlusCode)) {
					productPlusChannelCodeList.add(dictInfo.getValue());
				}
			}
			inDTO.setProductPlusChannelCodeList(productPlusChannelCodeList);
		}
	}

	/**
	 * 根据订单信息获取订单展示信息
	 * 
	 * @param dictMap
	 * @param orderDTO
	 * @return
	 */
	public TradeOrdersShowDTO exchangeTradeOrdersDTO2Show(Map<String, DictionaryInfo> dictMap,
			TradeOrdersDTO orderDTO) {
		TradeOrdersShowDTO showDTO = new TradeOrdersShowDTO();
		showDTO.setTradeOrderAllInfo(orderDTO);
		setTradeOrdersShowInfo(dictMap, showDTO);
		return showDTO;
	}

	/**
	 * 设定订单信息的显示信息
	 * 
	 * @param dictMap
	 * @param showDTO
	 */
	public void setTradeOrdersShowInfo(Map<String, DictionaryInfo> dictMap, TradeOrdersShowDTO showDTO) {
		AddressInfo addressInfo = null;
		String orderStatus = showDTO.getOrderStatus();
		String errorStatus = showDTO.getOrderErrorStatus();
		int isCancelFlag = showDTO.getIsCancelOrder();

		if (!StringUtils.isEmpty(showDTO.getSite())) {
			addressInfo = address.getAddressName(showDTO.getSite());
			showDTO.setSiteName(addressInfo == null ? "" : addressInfo.getName());
		}
		if (!StringUtils.isEmpty(showDTO.getBuyerType())) {
			showDTO.setBuyerTypeName(
					getDictNameByValue(dictMap, DictionaryConst.TYPE_BUYER_TYPE, showDTO.getBuyerType()));
		}
		if (!StringUtils.isEmpty(showDTO.getSellerType())) {
			showDTO.setSellerTypeName(
					getDictNameByValue(dictMap, DictionaryConst.TYPE_SELLER_TYPE, showDTO.getSellerType()));
		}
		if (!StringUtils.isEmpty(showDTO.getOrderFrom())) {
			showDTO.setOrderFromName(
					getDictNameByValue(dictMap, DictionaryConst.TYPE_ORDER_FROM, showDTO.getOrderFrom()));
		}
		if (!StringUtils.isEmpty(showDTO.getSalesType())) {
			showDTO.setSalesTypeName(
					getDictNameByValue(dictMap, DictionaryConst.TYPE_ORDER_SALE_TYPE, showDTO.getSalesType()));
		}
		showDTO.setOrderStatusName(setOrderShowStatus(dictMap, orderStatus, errorStatus, isCancelFlag, ""));
		if (!StringUtils.isEmpty(showDTO.getPayType())) {
			showDTO.setPayTypeName(getDictNameByValue(dictMap, DictionaryConst.TYPE_PAY_TYPE, showDTO.getPayType()));
		}
		if (!StringUtils.isEmpty(showDTO.getPayStatus())) {
			showDTO.setPayStatusName(
					getDictNameByValue(dictMap, DictionaryConst.TYPE_PAY_STATUS, showDTO.getPayStatus()));
		}
		showDTO.setHasProductPlusName(YesNoEnum.getName(showDTO.getHasProductplusFlag()));
		showDTO.setHasUsedCouponName(YesNoEnum.getName(showDTO.getHasUsedCoupon()));
		showDTO.setIsChangePriceName(YesNoEnum.getName(showDTO.getIsChangePrice()));
		if (!StringUtils.isEmpty(showDTO.getInvoiceType())) {
			showDTO.setInvoiceTypeName(
					getDictNameByValue(dictMap, DictionaryConst.TYPE_INVOICE_TYPE, showDTO.getInvoiceType()));
		}
		if (!StringUtils.isEmpty(showDTO.getDeliveryType())) {
			showDTO.setDeliveryTypeName(
					getDictNameByValue(dictMap, DictionaryConst.TYPE_ORDER_GIVE_TYPE, showDTO.getDeliveryType()));
		}
		if (showDTO.getModifyTime() != null) {
			showDTO.setModifyTimeStr(DateUtils.format(showDTO.getModifyTime(), DateUtils.YMDHMS));
		}
	}

	/**
	 * 根据订单行信息获取订单行展示信息
	 * 
	 * @param dictMap
	 * @param itemDTO
	 * @return
	 */
	public TradeOrderItemsShowDTO exchangeTradeOrderItemsDTO2Show(Map<String, DictionaryInfo> dictMap,
			TradeOrderItemsDTO itemDTO) {
		TradeOrderItemsShowDTO showDTO = new TradeOrderItemsShowDTO();
		showDTO.setTradeOrderItemInfo(itemDTO);
		setTradeOrdersShowInfo(dictMap, showDTO);
		setTradeOrderItemsShowInfo(dictMap, showDTO);
		return showDTO;
	}

	/**
	 * 设定订单行信息获取订单行展示信息
	 * 
	 * @param dictMap
	 * @param showDTO
	 */
	public void setTradeOrderItemsShowInfo(Map<String, DictionaryInfo> dictMap, TradeOrderItemsShowDTO showDTO) {
		String orderStatus = showDTO.getOrderItemStatus();
		String errorStatus = showDTO.getOrderItemErrorStatus();
		int isCancelFlag = showDTO.getIsCancelOrderItem();
		String refundStatus = showDTO.getRefundStatus();
		if (!StringUtils.isEmpty(showDTO.getChannelCode())) {
			showDTO.setChannelCodeName(
					getDictNameByValue(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL, showDTO.getChannelCode()));
		}
		if (!StringUtils.isEmpty(showDTO.getGoodsPriceType())) {
			showDTO.setGoodsPriceTypeName(
					getDictNameByValue(dictMap, DictionaryConst.TYPE_SKU_PRICE_TYPE, showDTO.getGoodsPriceType()));
		}
		if (!StringUtils.isEmpty(showDTO.getOuterChannelPuchaseStatus())) {
			showDTO.setOuterChannelPuchaseStatusName(getDictNameByValue(dictMap,
					DictionaryConst.TYPE_PRODUCT_PLUS_ORDER_STATUS, showDTO.getOuterChannelPuchaseStatus()));
		}
		if (!StringUtils.isEmpty(showDTO.getOuterChannelStatus())) {
			showDTO.setOuterChannelStatusName(getDictNameByValue(dictMap,
					DictionaryConst.TYPE_PRODUCT_PLUS_ORDER_INNER_STATUS, showDTO.getOuterChannelStatus()));
		}
		showDTO.setHasUsedCouponName(YesNoEnum.getName(showDTO.getHasUsedCoupon()));
		showDTO.setIsChangePriceName(YesNoEnum.getName(showDTO.getIsChangePrice()));
		showDTO.setOrderItemStatusName(
				setOrderShowStatus(dictMap, orderStatus, errorStatus, isCancelFlag, refundStatus));
		showDTO.setIsOutDistribtionName(YesNoEnum.getName(showDTO.getIsOutDistribtion()));
		showDTO.setIsSettledName(YesNoEnum.getName(showDTO.getIsSettled()));
		if (showDTO.getModifyTime() != null) {
			showDTO.setModifyTimeStr(DateUtils.format(showDTO.getModifyTime(), DateUtils.YMDHMS));
		}
	}

	/**
	 * 从字典中获取数据转换用信息
	 * 
	 * @return
	 */
	public Map<String, DictionaryInfo> getTradeOrderDictionaryMap() {
		Map<String, DictionaryInfo> exchangeDictMap = new HashMap<String, DictionaryInfo>();
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_BUYER_TYPE));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_SELLER_TYPE));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_ORDER_FROM));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_ORDER_SALE_TYPE));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_PAY_TYPE));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_PAY_STATUS));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_INVOICE_TYPE));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_ORDER_GIVE_TYPE));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_PRODUCT_CHANNEL));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_SKU_PRICE_TYPE));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_PRODUCT_PLUS_ORDER_STATUS));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_PRODUCT_PLUS_ORDER_INNER_STATUS));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_ORDER_STATUS));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_ERP_PRODUCT_ATTR));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_ERP_STATUS));
		exchangeDictMap.putAll(getDictionaryShowName(DictionaryConst.TYPE_VERIFY_TYPE));
		return exchangeDictMap;
	}

	/**
	 * 根据字典类型取得字典展示信息
	 *
	 * @param dictType
	 * @return
	 */
	private Map<String, DictionaryInfo> getDictionaryShowName(String dictType) {
		Map<String, DictionaryInfo> showDictMap = new HashMap<String, DictionaryInfo>();
		List<DictionaryInfo> dictList = null;
		dictList = dictionary.getDictionaryOptList(dictType);
		if (dictList == null || dictList.isEmpty()) {
			return showDictMap;
		}
		for (DictionaryInfo dictInfo : dictList) {
			showDictMap.put(dictType + "&" + dictInfo.getCode(), dictInfo);
			showDictMap.put(dictType + "&" + dictInfo.getValue(), dictInfo);
		}
		return showDictMap;
	}

	/**
	 * 从字典map中取得指定类型和编码的定义值
	 * 
	 * @param dictMap
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public String getDictValueByCode(Map<String, DictionaryInfo> dictMap, String typeCode, String code) {
		DictionaryInfo dict = null;
		if (StringUtils.isBlank(typeCode)) {
			return "";
		}
		if (StringUtils.isBlank(code)) {
			return "";
		}
		if (dictMap == null || dictMap.isEmpty()) {
			return "";
		}
		if (!dictMap.containsKey(typeCode + "&" + code)) {
			return "";
		}
		dict = dictMap.get(typeCode + "&" + code);
		if (dict == null) {
			return "";
		}
		return dict.getValue();
	}

	/**
	 * 从字典map中取得指定类型和值的名称
	 *
	 * @param dictMap
	 * @param typeCode
	 * @param value
	 * @return
	 */
	public String getDictNameByValue(Map<String, DictionaryInfo> dictMap, String typeCode, String value) {
		DictionaryInfo dict = null;
		if (StringUtils.isBlank(typeCode)) {
			return "";
		}
		if (StringUtils.isBlank(value)) {
			return "";
		}
		if (dictMap == null || dictMap.isEmpty()) {
			return "";
		}
		if (!dictMap.containsKey(typeCode + "&" + value)) {
			return "";
		}
		dict = dictMap.get(typeCode + "&" + value);
		if (dict == null) {
			return "";
		}
		return dict.getName();
	}

	/**
	 * 从字典map中取得指定类型和编码的定义值
	 *
	 * @param dictMap
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public String getDictNameByCode(Map<String, DictionaryInfo> dictMap, String typeCode, String code) {
		DictionaryInfo dict = null;
		if (StringUtils.isBlank(typeCode)) {
			return "";
		}
		if (StringUtils.isBlank(code)) {
			return "";
		}
		if (dictMap == null || dictMap.isEmpty()) {
			return "";
		}
		if (!dictMap.containsKey(typeCode + "&" + code)) {
			return "";
		}
		dict = dictMap.get(typeCode + "&" + code);
		if (dict == null) {
			return "";
		}
		return dict.getName();
	}

	/**
	 * 从字典map中取得指定类型和编码的定义值
	 *
	 * @param dictMap
	 * @param typeCode
	 * @param value
	 * @return
	 */
	public String getDictDescribeByValue(Map<String, DictionaryInfo> dictMap, String typeCode, String value) {
		DictionaryInfo dict = null;
		String describe = "";
		if (StringUtils.isBlank(typeCode)) {
			return "";
		}
		if (StringUtils.isBlank(value)) {
			return "";
		}
		if (dictMap == null || dictMap.isEmpty()) {
			return "";
		}
		if (!dictMap.containsKey(typeCode + "&" + value)) {
			return "";
		}
		dict = dictMap.get(typeCode + "&" + value);
		if (dict == null) {
			return "";
		}
		describe = dict.getName();
		describe += StringUtils.isEmpty(dict.getComment()) ? "" : "-" + dict.getComment();
		return describe;
	}

	/**
	 * 从字典Map中取得指定类型的选项列表
	 * 
	 * @param dictMap
	 * @param typeCode
	 * @return
	 */
	public List<DictionaryInfo> getDictOptList(Map<String, DictionaryInfo> dictMap, String typeCode) {
		List<DictionaryInfo> resultList = new ArrayList<DictionaryInfo>();
		Map<String, DictionaryInfo> resultMap = new HashMap<String, DictionaryInfo>();
		String key = "";
		DictionaryInfo value = null;
		for (Entry<String, DictionaryInfo> entry : dictMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (key.startsWith(typeCode + "&")) {
				resultMap.put(value.getCode() + "&" + value.getValue(), value);
			}
		}
		if (resultMap != null && !resultMap.isEmpty()) {
			for (Entry<String, DictionaryInfo> entry : resultMap.entrySet()) {
				resultList.add(entry.getValue());
			}
		}
		return resultList;
	}

	/**
	 * 根据订单行拆单信息获取订单行展示信息
	 *
	 * @param dictMap
	 * @param warehouseDTO
	 * @return
	 */
	public TradeOrderItemsWarehouseDetailShowDTO exchangeTradeOrderItemsWarehouseDTO2Show(
			Map<String, DictionaryInfo> dictMap, TradeOrderItemsWarehouseDetailDTO warehouseDTO) {
		TradeOrderItemsWarehouseDetailShowDTO showDTO = new TradeOrderItemsWarehouseDetailShowDTO();
		showDTO.setTradeOrderItemsWarehouseDetailDTO(warehouseDTO);
		setOrderItemsWarehouseDetailShowDTO(dictMap, showDTO);
		return showDTO;
	}

	/**
	 * 设定订单行拆单展示信息
	 *
	 * @param dictMap
	 * @param warehouseShowDTO
	 */
	public void setOrderItemsWarehouseDetailShowDTO(Map<String, DictionaryInfo> dictMap,
			TradeOrderItemsWarehouseDetailShowDTO warehouseShowDTO) {
		warehouseShowDTO.setWareHouseName(warehouseShowDTO.getWarehouseName());
		if (!StringUtils.isEmpty(warehouseShowDTO.getProductAttribute())) {
			warehouseShowDTO.setProductAttributeName(getDictNameByValue(dictMap, DictionaryConst.TYPE_ERP_PRODUCT_ATTR,
					warehouseShowDTO.getProductAttribute()));
		}
		warehouseShowDTO.setDepartmentName(warehouseShowDTO.getPurchaseDepartmentName());
		warehouseShowDTO.setStoreNum(warehouseShowDTO.getAvailableInventory());
	}
}
