/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	VenusTradeOrderDealServiceImpl.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: 新增订单服务
 * History:
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yiji.openapi.tool.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.middleware.MiddlewareInterfaceConstant;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.util.DateUtils;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.dto.indto.QueryItemStockDetailInDTO;
import cn.htd.goodscenter.dto.outdto.QueryItemStockDetailOutDTO;
import cn.htd.goodscenter.service.ItemExportService;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.service.PromotionInfoService;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.tradecenter.common.constant.ReturnCodeConst;
import cn.htd.tradecenter.common.enums.YesNoEnum;
import cn.htd.tradecenter.common.exception.TradeCenterBusinessException;
import cn.htd.tradecenter.common.utils.CalculateUtils;
import cn.htd.tradecenter.common.utils.ExceptionUtils;
import cn.htd.tradecenter.common.utils.MiddleWare;
import cn.htd.tradecenter.common.utils.ValidateResult;
import cn.htd.tradecenter.common.utils.ValidationUtils;
import cn.htd.tradecenter.dao.TradeOrderErpDistributionDAO;
import cn.htd.tradecenter.dao.TradeOrderItemsDAO;
import cn.htd.tradecenter.dao.TradeOrderItemsDiscountDAO;
import cn.htd.tradecenter.dao.TradeOrderItemsStatusHistoryDAO;
import cn.htd.tradecenter.dao.TradeOrderItemsWarehouseDetailDAO;
import cn.htd.tradecenter.dao.TradeOrderPayInfoDAO;
import cn.htd.tradecenter.dao.TradeOrderStatusHistoryDAO;
import cn.htd.tradecenter.dao.TradeOrdersDAO;
import cn.htd.tradecenter.domain.TradeOrdersDiscountInfo;
import cn.htd.tradecenter.dto.DepartmentDto;
import cn.htd.tradecenter.dto.RedoErpWorkDTO;
import cn.htd.tradecenter.dto.TradeOrderConfirmDTO;
import cn.htd.tradecenter.dto.TradeOrderErpDistributionDTO;
import cn.htd.tradecenter.dto.TradeOrderItemStockDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsDiscountDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsPriceHistoryDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsShowDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsStatusHistoryDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsWarehouseDetailDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsWarehouseDetailShowDTO;
import cn.htd.tradecenter.dto.TradeOrderPayInfoDTO;
import cn.htd.tradecenter.dto.TradeOrderStatusHistoryDTO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;
import cn.htd.tradecenter.dto.TradeOrdersQueryInDTO;
import cn.htd.tradecenter.dto.TradeOrdersShowDTO;
import cn.htd.tradecenter.dto.VenusConfirmTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusConfirmTradeOrderItemDTO;
import cn.htd.tradecenter.dto.VenusConfirmTradeOrderItemWarehouseDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderItemDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderRebateDTO;
import cn.htd.tradecenter.dto.VenusNegotiateTradeOrderDTO;
import cn.htd.tradecenter.dto.VenusNegotiateTradeOrderItemDTO;
import cn.htd.tradecenter.dto.VenusTradeOrdersQueryInDTO;
import cn.htd.tradecenter.dto.VenusVerifyTradeOrdersInDTO;
import cn.htd.tradecenter.service.TradeOrderService;
import cn.htd.tradecenter.service.handle.TradeOrderCouponHandle;
import cn.htd.tradecenter.service.handle.TradeOrderDBHandle;
import cn.htd.tradecenter.service.handle.TradeOrderStockHandle;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.service.CustomerService;
import jodd.util.StringUtil;

@Service("tradeOrderService")
public class TradeOrderServiceImpl implements TradeOrderService {

	private static final Logger logger = LoggerFactory.getLogger(TradeOrderServiceImpl.class);

	@Resource
	private TradeOrderBaseService baseService;

	@Resource
	private CustomerService customerService;

	@Resource
	private PromotionInfoService promotionInfoService;

	@Resource
	private ItemExportService itemExportService;

	@Resource
	private TradeOrderStockHandle orderStockHandle;

	@Resource
	private TradeOrderCouponHandle orderCouponHandle;

	@Resource
	private TradeOrderDBHandle orderDBHandle;

	@Resource
	private TradeOrdersDAO orderDAO;

	@Resource
	private TradeOrderItemsDAO orderItemsDAO;

	@Resource
	private TradeOrderErpDistributionDAO orderErpDistributionDAO;

	@Resource
	private TradeOrderPayInfoDAO orderPayInfoDAO;

	@Resource
	private TradeOrderItemsDiscountDAO orderItemsDiscountDAO;

	@Resource
	private TradeOrderItemsWarehouseDetailDAO warehouseDetailDAO;

	@Resource
	private TradeOrderStatusHistoryDAO orderStatusHistoryDAO;

	@Resource
	private TradeOrderItemsStatusHistoryDAO itemStatusHistoryDAO;

	@Autowired
	private MiddleWare middleware;

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private TradeOrderErpDistributionDAO erpDistributionDAO;

	public ExecuteResult<TradeOrdersDTO> createVenusTradeOrderInfo(VenusCreateTradeOrderDTO venusInDTO) {
		ExecuteResult<TradeOrdersDTO> result = new ExecuteResult<TradeOrdersDTO>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		ExecuteResult<String> reverseStockResult = null;
		List<TradeOrderStatusHistoryDTO> orderStatusHistoryDTOList = new ArrayList<TradeOrderStatusHistoryDTO>();
		TradeOrderStatusHistoryDTO statusHistoryDTO = new TradeOrderStatusHistoryDTO();
		List<TradeOrderItemsDTO> tradeOrderItemsDTOList = null;
		List<TradeOrderErpDistributionDTO> orderErpDistributionDTOList = null;
		TradeOrdersDTO tradeOrdersDTO = null;
		CustomerDTO customerDTO = null;
		String tradeNo = "";
		String orderNo = "";
		String messageId = "";
		String mallOrderFrom = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_FROM,
				DictionaryConst.OPT_ORDER_FROM_MALL);
		String vmsOrderFrom = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_FROM,
				DictionaryConst.OPT_ORDER_FROM_VMS);
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(venusInDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
			}
			// 取得用户ID信息
			customerDTO = customerService.getCustomerInfo(venusInDTO.getOperatorId());
			// 检查用户信息合法性
			if (customerDTO == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_OPERATION_USER_NOT_EXIST,
						"用户ID:" + venusInDTO.getOperatorId() + "信息不存在");
			}
			if (customerDTO.getCompanyId().compareTo(venusInDTO.getSellerId()) != 0) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_OPERATION_SELLER_ERROR,
						"用户ID:" + venusInDTO.getOperatorId() + "所属公司不是该供应商");
			}
			checkCreateVenusOrderParameter(dictMap, venusInDTO,
					customerDTO.getIsLowFlag() == null ? false : customerDTO.getIsLowFlag().booleanValue());
			messageId = baseService.getMessageId();
			// 取得交易号和订单号
			tradeNo = baseService.getTradeNo(mallOrderFrom);
			orderNo = baseService.getOrderNo(mallOrderFrom);
			// 取得订单行信息计算订单行金额信息
			tradeOrderItemsDTOList = getVenusOrderItemList(dictMap, orderNo, venusInDTO);
			tradeOrdersDTO = baseService.createTradeOrderInfo(dictMap, orderNo, venusInDTO, tradeOrderItemsDTOList);
			// 判断账户余额是否足够
			String flag = getPrivateAccount(venusInDTO, tradeOrdersDTO);
			if ("false".equals(flag)) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_PRIVATEACCOUNT_NO_ENOUGH,
						"用户ID:" + tradeOrdersDTO.getBuyerCode() + "账户余额不足");
			}
			orderErpDistributionDTOList = baseService.createTradeOrderErpDistributionInfo(dictMap, tradeOrdersDTO,
					tradeOrderItemsDTOList);
			tradeOrdersDTO.setTradeNo(tradeNo);
			tradeOrdersDTO.setOrderFrom(vmsOrderFrom);
			tradeOrdersDTO.setSalesType(venusInDTO.getSalesType());
			tradeOrdersDTO.setSalesDepartmentCode(venusInDTO.getSalesDepartmentCode());
			// VMS开单订单默认创建状态为待确认
			tradeOrdersDTO.setOrderStatus(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_CONFIRM));
			tradeOrdersDTO.setCreateOrderTime(new Date());
			tradeOrdersDTO.setPayTimeLimit(DateUtils.parse("9999-12-31 23:59:59", DateUtils.YYDDMMHHMMSS));
			tradeOrdersDTO.setPayType(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_PAY_TYPE,
					DictionaryConst.OPT_PAY_TYPE_ERP_ACCOUNT));
			tradeOrdersDTO.setPayStatus(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_PAY_STATUS,
					DictionaryConst.OPT_PAY_STATUS_PENDING));
			tradeOrdersDTO.setIsExpressDelivery(YesNoEnum.NO.getValue());
			tradeOrdersDTO.setOrderDeleteStatus(YesNoEnum.NO.getValue());
			// 生成订单状态履历
			statusHistoryDTO.setOrderNo(orderNo);
			statusHistoryDTO.setOrderStatus(tradeOrdersDTO.getOrderStatus());
			statusHistoryDTO.setOrderStatusText(baseService.setOrderShowStatus(dictMap, tradeOrdersDTO.getOrderStatus(),
					"", YesNoEnum.NO.getValue(), ""));
			statusHistoryDTO.setCreateId(venusInDTO.getOperatorId());
			statusHistoryDTO.setCreateName(venusInDTO.getOperatorName());
			orderStatusHistoryDTOList.add(statusHistoryDTO);
			// 组装订单数据
			tradeOrdersDTO.setOrderItemList(tradeOrderItemsDTOList);
			tradeOrdersDTO.setErpDistributionDTOList(orderErpDistributionDTOList);
			tradeOrdersDTO.setOrderStatusHistoryDTOList(orderStatusHistoryDTOList);
			reverseStockResult = orderStockHandle.reserveOrderStock(messageId, vmsOrderFrom, tradeOrderItemsDTOList);
			tradeOrdersDTO = orderDBHandle.createVenusTradeOrders(tradeOrdersDTO);
			result.setResult(tradeOrdersDTO);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
			if (reverseStockResult != null && reverseStockResult.isSuccess()) {
				try {
					orderStockHandle.releaseOrderStock(messageId, vmsOrderFrom, tradeOrderItemsDTOList);
				} catch (Exception se) {
					logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderServiceImpl-createVenusTradeOrderInfo",
							JSONObject.toJSONString(se));
				}
			}
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			if (reverseStockResult != null && reverseStockResult.isSuccess()) {
				try {
					orderStockHandle.releaseOrderStock(messageId, vmsOrderFrom, tradeOrderItemsDTOList);
				} catch (Exception se) {
					logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderServiceImpl-createVenusTradeOrderInfo",
							JSONObject.toJSONString(se));
				}
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	private String getPrivateAccount(VenusCreateTradeOrderDTO venusInDTO, TradeOrdersDTO tradeOrdersDTO) {
		// 判断需要支付的金额与账户余额比较
		String tokenUrl = middleware.getPath() + "/token/" + MiddlewareInterfaceConstant.MIDDLE_PLATFORM_APP_ID;
		String accessTokenErp = MiddlewareInterfaceUtil.httpGet(tokenUrl, Boolean.TRUE);
		if (StringUtils.isEmpty(accessTokenErp)) {
			return null;
		}
		Map accessTokenMap = (Map) JSONObject.parseObject(accessTokenErp, Map.class);
		String param = null;
		;
		String url = null;
		String accessToken = String.valueOf(accessTokenMap.get("data"));
		if (venusInDTO.getSalesDepartmentCode() != null & venusInDTO.getSalesDepartmentCode() != "") {
			param = "?token=" + accessToken + "&memberCode=" + tradeOrdersDTO.getBuyerCode() + "&supplierCode="
					+ tradeOrdersDTO.getSellerCode() + "&departmentCode=" + venusInDTO.getSalesDepartmentCode();
			url = middleware.getPath() + "/member/balanceList";
		}
		String responseJson = MiddlewareInterfaceUtil.httpGet(url + param, Boolean.TRUE);
		JSONObject jsonObject = JSONObject.parseObject(responseJson);
		String department = jsonObject.getString("data");
		Double privateAccount = 0.00;
		List<DepartmentDto> departmentList = JSON.parseArray(department, DepartmentDto.class);
		if (departmentList.size() > 0) {
			for (int i = 0; i < departmentList.size(); i++) {
				privateAccount += departmentList.get(i).getUsePrice();
			}
		}
		if (tradeOrdersDTO.getOrderPayAmount().compareTo(new BigDecimal(privateAccount.toString())) == 1) {// 应付金额大于专有账户余额
			return "false";
		} else {
			return "true";
		}
	}

	/**
	 * 检查输入参数的合法性
	 *
	 * @param venusInDTO
	 * @param canUnderFloorPrice
	 * @throws TradeCenterBusinessException
	 */
	private void checkCreateVenusOrderParameter(Map<String, DictionaryInfo> dictMap,
			VenusCreateTradeOrderDTO venusInDTO, boolean canUnderFloorPrice) throws TradeCenterBusinessException {
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
						"第" + (i + 1) + "行订单商品数量小于可用库存");
			}
			if (YesNoEnum.YES.getValue() == itemBaseDTO.getIsAgreementSku()
					&& StringUtils.isBlank(itemBaseDTO.getAgreementCode())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NONE_AGREEMENT,
						"第" + (i + 1) + "行协议商品没有协议编号");
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
			rebateDTO.setUseRebateAmount(totalRebateAmount);
		}
	}

	/**
	 * 取得Venus开单商品详情信息及检查相应库存价格信息
	 *
	 * @param dictMap
	 * @param orderNo
	 * @param venusInDTO
	 * @return
	 * @throws TradeCenterBusinessException
	 * @throws Exception
	 */
	private List<TradeOrderItemsDTO> getVenusOrderItemList(Map<String, DictionaryInfo> dictMap, String orderNo,
			VenusCreateTradeOrderDTO venusInDTO) throws TradeCenterBusinessException, Exception {
		List<VenusCreateTradeOrderItemDTO> itemDTOList = venusInDTO.getTradeItemDTOList();
		Map<String, Integer> distinctItemCountMap = new HashMap<String, Integer>();
		Map<String, List<VenusCreateTradeOrderItemDTO>> distinctItemListMap = new HashMap<String, List<VenusCreateTradeOrderItemDTO>>();
		List<VenusCreateTradeOrderItemDTO> venusItemList = null;
		TradeOrderItemsDTO orderItemsDTO = null;
		TradeOrderItemsDTO retOrderItemsDTO = null;
		List<TradeOrderItemsDTO> retOrderItemsDTOList = new ArrayList<TradeOrderItemsDTO>();
		String key = "";
		Integer value = 0;
		String[] keyArr = null;
		String skuCode = "";
		int boxFlg = 0;

		for (VenusCreateTradeOrderItemDTO itemBaseDTO : itemDTOList) {
			skuCode = itemBaseDTO.getSkuCode();
			boxFlg = itemBaseDTO.getIsBoxFlag();
			key = skuCode + "&" + boxFlg;
			value = itemBaseDTO.getGoodsCount();
			if (distinctItemCountMap.containsKey(key)) {
				value = value + distinctItemCountMap.get(key);
				venusItemList = distinctItemListMap.get(key);
			} else {
				venusItemList = new ArrayList<VenusCreateTradeOrderItemDTO>();
			}
			distinctItemCountMap.put(key, value);
			venusItemList.add(itemBaseDTO);
			distinctItemListMap.put(key, venusItemList);
		}
		for (Entry<String, Integer> entry : distinctItemCountMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			keyArr = key.split("&");
			orderItemsDTO = orderStockHandle.getItemSkuDetail(keyArr[0], Integer.parseInt(keyArr[1]), value);
			venusItemList = distinctItemListMap.get(key);
			for (VenusCreateTradeOrderItemDTO tempVenusItemDTO : venusItemList) {
				retOrderItemsDTO = setTradeOrderItemsDTO(dictMap, orderNo, orderItemsDTO, tempVenusItemDTO, venusInDTO);
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

	@Override
	public ExecuteResult<TradeOrdersDTO> confirmVenusTradeOrderInfo(VenusConfirmTradeOrderDTO confirmInDTO) {
		ExecuteResult<TradeOrdersDTO> result = new ExecuteResult<TradeOrdersDTO>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		String orderNo = confirmInDTO.getOrderNo();
		TradeOrdersDTO tradeOrderDTO = null;
		List<TradeOrderItemsDTO> orderItemsList = null;
		List<TradeOrderErpDistributionDTO> orderErpDistributionList = null;
		Map<Long, TradeOrderErpDistributionDTO> orderErpDistributionMap = new HashMap<Long, TradeOrderErpDistributionDTO>();
		TradeOrderErpDistributionDTO orderErpDistribution = null;
		List<TradeOrderStatusHistoryDTO> orderStatusDTOList = new ArrayList<TradeOrderStatusHistoryDTO>();
		TradeOrderStatusHistoryDTO orderStatusDTO = new TradeOrderStatusHistoryDTO();
		List<TradeOrderPayInfoDTO> payInfoList = null;
		String mapKey = "";
		Map<String, String> erpLockBalanceCodeMap = new HashMap<String, String>();
		String distributeStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_PAID_DISTRIBUTED);
		Long key = null;
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(confirmInDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
			}
			tradeOrderDTO = orderDBHandle.queryTradeOrdersByOrderNo(orderNo, false);
			if (tradeOrderDTO == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_EXIST, "订单编号:" + orderNo + "的订单不存在");
			}
			checkConfirmOrderParameter(dictMap, confirmInDTO, tradeOrderDTO);
			orderItemsList = getOrderItemsWarehouseDetailInfo(dictMap, confirmInDTO, tradeOrderDTO);
			if (orderItemsList.size() == 0) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_WAREHOUSE_NOT_EXIST,
						"订单编号:" + orderNo + "的拆单信息没有修改");
			}
			logger.info("********* 订单行拆单结果:" + JSON.toJSONString(orderItemsList) + " **********");
			tradeOrderDTO.setOrderItemList(orderItemsList);
			orderErpDistributionList = tradeOrderDTO.getErpDistributionDTOList();
			if (orderErpDistributionList == null || orderErpDistributionList.isEmpty()) {
				orderErpDistributionList = baseService.createTradeOrderErpDistributionInfo(dictMap, tradeOrderDTO,
						orderItemsList);
				if (orderErpDistributionList != null && !orderErpDistributionList.isEmpty()) {
					payInfoList = orderPayInfoDAO.queryOrderPayInfoByOrderNo(orderNo);
					if (payInfoList != null && !payInfoList.isEmpty()) {
						for (TradeOrderPayInfoDTO payInfo : payInfoList) {
							mapKey = payInfo.getBrandCode() + "&" + payInfo.getClassCode();
							erpLockBalanceCodeMap.put(mapKey, payInfo.getDownOrderNo());
						}
					}
					for (TradeOrderErpDistributionDTO erpDTO : orderErpDistributionList) {
						mapKey = erpDTO.getBrandId().toString() + "&" + erpDTO.getErpFirstCategoryCode();
						if (erpLockBalanceCodeMap.containsKey(mapKey)) {
							erpDTO.setErpLockBalanceCode(erpLockBalanceCodeMap.get(mapKey));
						}
					}
				}
			} else {
				orderErpDistributionList = new ArrayList<TradeOrderErpDistributionDTO>();
				for (TradeOrderItemsDTO orderItemDTO : orderItemsList) {
					orderErpDistribution = getTargetErpDistribution(tradeOrderDTO, orderItemDTO);
					if (!orderErpDistributionMap.containsKey(orderItemDTO.getErpDistributionId())) {
						orderErpDistribution.setErpStatus(ErpStatusEnum.PENDING.getValue());
						orderErpDistribution.setModifyId(confirmInDTO.getOperatorId());
						orderErpDistribution.setModifyName(confirmInDTO.getOperatorName());
						orderErpDistributionMap.put(orderItemDTO.getErpDistributionId(), orderErpDistribution);
					}
				}
				Iterator<Long> it = orderErpDistributionMap.keySet().iterator();
				while (it.hasNext()) {
					key = it.next();
					orderErpDistributionList.add(orderErpDistributionMap.get(key));
				}
			}
			tradeOrderDTO.setErpDistributionDTOList(orderErpDistributionList);
			tradeOrderDTO.setOrderStatus(distributeStatus);
			tradeOrderDTO.setOrderErrorStatus("");
			tradeOrderDTO.setOrderErrorReason("");
			tradeOrderDTO.setSalesType(confirmInDTO.getSalesType());
			tradeOrderDTO.setOrderRemarks(confirmInDTO.getVerifyComment());
			tradeOrderDTO.setModifyId(confirmInDTO.getOperatorId());
			tradeOrderDTO.setModifyName(confirmInDTO.getOperatorName());
			orderStatusDTO.setOrderNo(tradeOrderDTO.getOrderNo());
			orderStatusDTO.setOrderStatus(distributeStatus);
			orderStatusDTO.setOrderStatusText(baseService.setOrderShowStatus(dictMap, distributeStatus, "",
					tradeOrderDTO.getIsCancelOrder(), ""));
			orderStatusDTO.setCreateId(confirmInDTO.getOperatorId());
			orderStatusDTO.setCreateName(confirmInDTO.getOperatorName());
			orderStatusDTOList.add(orderStatusDTO);
			tradeOrderDTO.setOrderStatusHistoryDTOList(orderStatusDTOList);
			logger.info("********* 订单拆单结果保存前:" + JSON.toJSONString(tradeOrderDTO) + " **********");
			tradeOrderDTO = orderDBHandle.saveTradeOrderDistributionInfo(tradeOrderDTO);
			result.setResult(tradeOrderDTO);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 检查输入参数的合法性
	 *
	 * @param dictMap
	 * @param confirmInDTO
	 * @param tradeOrderDTO
	 * @throws TradeCenterBusinessException
	 */
	private void checkConfirmOrderParameter(Map<String, DictionaryInfo> dictMap, VenusConfirmTradeOrderDTO confirmInDTO,
			TradeOrdersDTO tradeOrderDTO) throws TradeCenterBusinessException {
		String orderNo = confirmInDTO.getOrderNo();
		List<TradeOrderItemsDTO> orderItemsList = tradeOrderDTO.getOrderItemList();
		List<VenusConfirmTradeOrderItemDTO> confirmOrderItemList = confirmInDTO.getOrderItemList();
		List<TradeOrderErpDistributionDTO> orderErpDistributionList = tradeOrderDTO.getErpDistributionDTOList();
		List<VenusConfirmTradeOrderItemWarehouseDTO> warehouseDTOList = null;
		VenusConfirmTradeOrderItemWarehouseDTO warehousDTO = null;
		String orderModifyTime = DateUtils.format(tradeOrderDTO.getModifyTime(), DateUtils.YMDHMS);
		String orderItemNo = "";
		String orderItemModifyTime = "";
		String orderStatus = tradeOrderDTO.getOrderStatus();
		String orderItemStatus = "";
		boolean canDistributeFlg = false;
		boolean hasWarehouseFlg = false;
		int goodsCount = 0;
		int warehouseGoodsCount = 0;
		String paidStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_PAID);
		String postSuccessStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_SUCCESS);
		String htdChannel = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
				DictionaryConst.OPT_PRODUCT_CHANNEL_HTD);
		if (!paidStatus.equals(orderStatus) && !postSuccessStatus.equals(orderStatus)) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_STATUS_CANNOT_DISTRIBUTE,
					"订单编号:" + orderNo + "的订单状态为" + baseService.getDictNameByValue(dictMap,
							DictionaryConst.TYPE_ORDER_STATUS, tradeOrderDTO.getOrderStatus()) + "不能拆单");
		}
		if (YesNoEnum.YES.getValue() == tradeOrderDTO.getIsCancelOrder()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_CANCELED, "订单编号:" + orderNo + "的订单已被取消");
		}
		if (StringUtils.isNotBlank(tradeOrderDTO.getOrderErrorStatus())) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ERROR_STATUS_CANNOT_DISTRIBUTE,
					"订单编号:" + orderNo + "的异常状态:" + baseService.getDictNameByValue(dictMap,
							DictionaryConst.TYPE_ORDER_STATUS, tradeOrderDTO.getOrderErrorStatus()) + "不能拆单");
		}
		if (!orderModifyTime.equals(confirmInDTO.getModifyTimeStr())) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_MODIFIED,
					"订单编号:" + orderNo + "的订单已被修改请重新确认");
		}
		if (orderItemsList == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NOT_EXIST,
					"订单编号:" + orderNo + "的订单行信息不存在");
		}
		for (TradeOrderItemsDTO orderItemDTO : orderItemsList) {
			if (YesNoEnum.NO.getValue() != orderItemDTO.getIsCancelOrderItem()) {
				continue;
			}
			orderItemNo = orderItemDTO.getOrderItemNo();
			orderItemModifyTime = DateUtils.format(orderItemDTO.getModifyTime(), DateUtils.YMDHMS);
			goodsCount = orderItemDTO.getGoodsCount().intValue();
			orderItemStatus = orderItemDTO.getOrderItemStatus();
			if (!htdChannel.equals(orderItemDTO.getChannelCode())) {
				continue;
			}
			if (!paidStatus.equals(orderItemStatus) && !postSuccessStatus.equals(orderItemStatus)) {
				continue;
			}
			if (orderErpDistributionList != null && getTargetErpDistribution(tradeOrderDTO, orderItemDTO) != null) {
				continue;
			}
			if (StringUtils.isNotBlank(orderItemDTO.getOrderItemErrorStatus())) {
				continue;
			}
			canDistributeFlg = true;
			if (YesNoEnum.YES.getValue() == orderItemDTO.getIsChangePrice()
					&& orderItemDTO.getBargainingGoodsCount().intValue() > 0) {
				goodsCount = orderItemDTO.getBargainingGoodsCount();
			}
			hasWarehouseFlg = false;
			warehouseGoodsCount = 0;
			for (VenusConfirmTradeOrderItemDTO confirmItemDTO : confirmOrderItemList) {
				if (orderItemNo.equals(confirmItemDTO.getOrderItemNo())) {
					hasWarehouseFlg = true;
					if (!orderItemModifyTime.equals(confirmItemDTO.getModifyTimeStr())) {
						throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_HAS_MODIFIED,
								"订单编号:" + orderNo + " 订单行编号:" + orderItemNo + "信息已被修改请重新确认");
					}
					warehouseDTOList = confirmItemDTO.getItemWarehouseDTOList();
					if (warehouseDTOList == null) {
						throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_WAREHOUSE_NOT_EXIST,
								"订单编号:" + orderNo + " 订单行编号:" + orderItemNo + "没有输入拆单信息");
					}
					for (int i = 0; i < warehouseDTOList.size(); i++) {
						warehousDTO = warehouseDTOList.get(i);
						if (warehousDTO.getGoodsCount() <= 0) {
							throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR,
									"订单编号:" + orderNo + " 订单行编号:" + orderItemNo + "的订单商品第" + (i + 1) + "行拆单数量未输入");
						}
						if (warehousDTO.getGoodsCount() > warehousDTO.getAvailableInventory()) {
							throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_DISTRIBUTE_INVENTORY_LACK,
									"订单编号:" + orderNo + " 订单行编号:" + orderItemNo + "的订单商品第" + (i + 1) + "行拆单数量大于可用库存");
						}
						warehouseGoodsCount += warehousDTO.getGoodsCount();
					}
					if (goodsCount != warehouseGoodsCount) {
						throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_COUNT_WAREHOUSE_NUMER_ERROR,
								"订单编号:" + orderNo + " 订单行编号:" + orderItemNo + "的订单购买商品数量和拆单数量不相等");
					}
					break;
				}
			}
			if (!hasWarehouseFlg) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR,
						"订单编号:" + orderNo + " 订单行编号:" + orderItemNo + "的拆单信息没有输入");
			}
		}
		if (!canDistributeFlg) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_CANNOT_DISTRIBUTE,
					"订单编号:" + orderNo + " 的所有订单行都不能拆单");
		}
		for (VenusConfirmTradeOrderItemDTO confirmItemDTO : confirmOrderItemList) {
			orderItemNo = confirmItemDTO.getOrderItemNo();
			hasWarehouseFlg = false;
			for (TradeOrderItemsDTO orderItemDTO : orderItemsList) {
				if (orderItemNo.equals(orderItemDTO.getOrderItemNo())
						&& YesNoEnum.NO.getValue() == orderItemDTO.getIsCancelOrderItem()) {
					hasWarehouseFlg = true;
					break;
				}
			}
			if (!hasWarehouseFlg) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_HAS_CANCELED,
						"订单编号:" + orderNo + " 订单行编号:" + orderItemNo + "的订单行已经被取消请重新确认");
			}
		}
	}

	/**
	 * 根据订单行的状态，设定拆单明细信息
	 *
	 * @param dictMap
	 * @param confirmInDTO
	 * @param tradeOrderDTO
	 * @return
	 */
	private List<TradeOrderItemsDTO> getOrderItemsWarehouseDetailInfo(Map<String, DictionaryInfo> dictMap,
			VenusConfirmTradeOrderDTO confirmInDTO, TradeOrdersDTO tradeOrderDTO) {
		List<TradeOrderItemsDTO> orderItemsList = tradeOrderDTO.getOrderItemList();
		List<TradeOrderItemsWarehouseDetailDTO> itemWarehouseList = null;
		TradeOrderItemsWarehouseDetailDTO newItemWarehouseDTO = null;
		List<VenusConfirmTradeOrderItemDTO> comfirmOrderItemList = confirmInDTO.getOrderItemList();
		List<VenusConfirmTradeOrderItemWarehouseDTO> confirmItemWareHouseDTOList = null;
		List<TradeOrderItemsDTO> newOrderItemsList = new ArrayList<TradeOrderItemsDTO>();
		List<TradeOrderErpDistributionDTO> orderErpDistributionList = tradeOrderDTO.getErpDistributionDTOList();
		List<TradeOrderItemsStatusHistoryDTO> itemStatusHistoryDTOList = null;
		TradeOrderItemsStatusHistoryDTO itemStatusHistoryDTO = null;
		String orderItemNo = "";
		String orderItemStatus = "";
		Long warehouseId = null;
		boolean hasWarehouseFlg = false;
		boolean hasChangedWarehouseFlg = false;
		String paidStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_PAID);
		String postSuccessStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_SUCCESS);
		String distributiedStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_PAID_DISTRIBUTED);

		for (TradeOrderItemsDTO orderItemDTO : orderItemsList) {
			if (YesNoEnum.NO.getValue() != orderItemDTO.getIsCancelOrderItem()) {
				continue;
			}
			orderItemNo = orderItemDTO.getOrderItemNo();
			orderItemStatus = orderItemDTO.getOrderItemStatus();
			if (!baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
					DictionaryConst.OPT_PRODUCT_CHANNEL_HTD).equals(orderItemDTO.getChannelCode())) {
				continue;
			}
			if (!paidStatus.equals(orderItemStatus) && !postSuccessStatus.equals(orderItemStatus)) {
				continue;
			}
			itemWarehouseList = orderItemDTO.getWarehouseDTOList();
			if (itemWarehouseList == null || itemWarehouseList.isEmpty()) {
				for (VenusConfirmTradeOrderItemDTO confirmItemDTO : comfirmOrderItemList) {
					if (orderItemNo.equals(confirmItemDTO.getOrderItemNo())) {
						confirmItemWareHouseDTOList = confirmItemDTO.getItemWarehouseDTOList();
						break;
					}
				}
				if (confirmItemWareHouseDTOList == null || confirmItemWareHouseDTOList.isEmpty()) {
					continue;
				}
				itemWarehouseList = createTradeOrderItemsWarehouseDetailDTOList(confirmInDTO, orderItemDTO,
						confirmItemWareHouseDTOList);
				orderItemDTO.setOrderItemStatus(distributiedStatus);
				orderItemDTO.setOrderItemErrorStatus("");
				orderItemDTO.setOrderItemErrorReason("");
				orderItemDTO.setWarehouseDTOList(itemWarehouseList);
				orderItemDTO.setModifyId(confirmInDTO.getOperatorId());
				orderItemDTO.setModifyName(confirmInDTO.getOperatorName());

				itemStatusHistoryDTOList = new ArrayList<TradeOrderItemsStatusHistoryDTO>();
				itemStatusHistoryDTO = new TradeOrderItemsStatusHistoryDTO();
				itemStatusHistoryDTO.setOrderItemNo(orderItemNo);
				itemStatusHistoryDTO.setOrderItemStatus(distributiedStatus);
				itemStatusHistoryDTO.setOrderItemStatusText(baseService.setOrderShowStatus(dictMap, distributiedStatus,
						"", orderItemDTO.getIsCancelOrderItem(), orderItemDTO.getRefundStatus()));
				itemStatusHistoryDTO.setCreateId(confirmInDTO.getOperatorId());
				itemStatusHistoryDTO.setCreateName(confirmInDTO.getOperatorName());
				itemStatusHistoryDTOList.add(itemStatusHistoryDTO);
				orderItemDTO.setItemStatusHistoryDTOList(itemStatusHistoryDTOList);
				newOrderItemsList.add(orderItemDTO);
				continue;
			}
			if (StringUtils.isNotBlank(orderItemDTO.getOrderItemErrorStatus()) && !baseService
					.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
							DictionaryConst.OPT_ORDER_STATUS_ERP_DOWN_ERROR)
					.equals(orderItemDTO.getOrderItemErrorStatus())) {
				continue;
			}
			if (orderErpDistributionList != null && getTargetErpDistribution(tradeOrderDTO, orderItemDTO) != null) {
				continue;
			}
			hasChangedWarehouseFlg = false;
			confirmItemWareHouseDTOList = null;
			for (VenusConfirmTradeOrderItemDTO confirmItemDTO : comfirmOrderItemList) {
				if (orderItemNo.equals(confirmItemDTO.getOrderItemNo())) {
					confirmItemWareHouseDTOList = confirmItemDTO.getItemWarehouseDTOList();
					break;
				}
			}
			for (TradeOrderItemsWarehouseDetailDTO itemWarehouseDTO : itemWarehouseList) {
				warehouseId = itemWarehouseDTO.getId();
				hasWarehouseFlg = false;
				for (VenusConfirmTradeOrderItemWarehouseDTO confirmItemWarehouseDTO : confirmItemWareHouseDTOList) {
					if (confirmItemWarehouseDTO.getId() != null
							&& warehouseId.longValue() == confirmItemWarehouseDTO.getId().longValue()) {
						hasWarehouseFlg = true;
						break;
					}
				}
				if (!hasWarehouseFlg) {
					itemWarehouseDTO.setDeleteFlag(YesNoEnum.YES.getValue());
					itemWarehouseDTO.setModifyId(confirmInDTO.getOperatorId());
					itemWarehouseDTO.setModifyName(confirmInDTO.getOperatorName());
					hasChangedWarehouseFlg = true;
				}
			}
			for (VenusConfirmTradeOrderItemWarehouseDTO confirmItemWarehouseDTO : confirmItemWareHouseDTOList) {
				if (confirmItemWarehouseDTO.getId() == null) {
					newItemWarehouseDTO = createTradeOrderItemsWarehouseDetailDTO(confirmInDTO, orderItemDTO,
							confirmItemWarehouseDTO);
					itemWarehouseList.add(newItemWarehouseDTO);
					hasChangedWarehouseFlg = true;
				}
			}
			if (hasChangedWarehouseFlg) {
				orderItemDTO.setOrderItemErrorStatus("");
				orderItemDTO.setOrderItemErrorReason("");
				orderItemDTO.setModifyId(confirmInDTO.getOperatorId());
				orderItemDTO.setModifyName(confirmInDTO.getOperatorName());

				itemStatusHistoryDTOList = new ArrayList<TradeOrderItemsStatusHistoryDTO>();
				itemStatusHistoryDTO = new TradeOrderItemsStatusHistoryDTO();
				itemStatusHistoryDTO.setOrderItemNo(orderItemNo);
				itemStatusHistoryDTO.setOrderItemStatus(distributiedStatus);
				itemStatusHistoryDTO.setOrderItemStatusText(baseService.setOrderShowStatus(dictMap, distributiedStatus,
						"", orderItemDTO.getIsCancelOrderItem(), orderItemDTO.getRefundStatus()));
				itemStatusHistoryDTO.setCreateId(confirmInDTO.getOperatorId());
				itemStatusHistoryDTO.setCreateName(confirmInDTO.getOperatorName());
				itemStatusHistoryDTOList.add(itemStatusHistoryDTO);
				orderItemDTO.setItemStatusHistoryDTOList(itemStatusHistoryDTOList);

				newOrderItemsList.add(orderItemDTO);
			}
		}
		return newOrderItemsList;
	}

	/**
	 * 根据界面输入的拆单信息生成订单行拆单明细
	 *
	 * @param confirmInDTO
	 * @param orderItemDTO
	 * @param confirmItemWarehouseDTOList
	 * @return
	 */
	private List<TradeOrderItemsWarehouseDetailDTO> createTradeOrderItemsWarehouseDetailDTOList(
			VenusConfirmTradeOrderDTO confirmInDTO, TradeOrderItemsDTO orderItemDTO,
			List<VenusConfirmTradeOrderItemWarehouseDTO> confirmItemWarehouseDTOList) {
		List<TradeOrderItemsWarehouseDetailDTO> orderItemsWarehouseList = new ArrayList<TradeOrderItemsWarehouseDetailDTO>();
		TradeOrderItemsWarehouseDetailDTO newItemWarehouseDTO = null;
		for (VenusConfirmTradeOrderItemWarehouseDTO confirmItemWarehouseDTO : confirmItemWarehouseDTOList) {
			newItemWarehouseDTO = createTradeOrderItemsWarehouseDetailDTO(confirmInDTO, orderItemDTO,
					confirmItemWarehouseDTO);
			orderItemsWarehouseList.add(newItemWarehouseDTO);
		}
		return orderItemsWarehouseList;
	}

	/**
	 * 生成订单行拆单明细信息
	 *
	 * @param confirmInDTO
	 * @param orderItemDTO
	 * @param confirmItemWarehouseDTO
	 * @return
	 */
	private TradeOrderItemsWarehouseDetailDTO createTradeOrderItemsWarehouseDetailDTO(
			VenusConfirmTradeOrderDTO confirmInDTO, TradeOrderItemsDTO orderItemDTO,
			VenusConfirmTradeOrderItemWarehouseDTO confirmItemWarehouseDTO) {

		TradeOrderItemsWarehouseDetailDTO newItemWarehouseDTO = new TradeOrderItemsWarehouseDetailDTO();
		newItemWarehouseDTO.setOrderNo(confirmInDTO.getOrderNo());
		newItemWarehouseDTO.setOrderItemNo(orderItemDTO.getOrderItemNo());
		newItemWarehouseDTO.setSkuCode(orderItemDTO.getSkuCode());
		newItemWarehouseDTO.setGoodsCount(confirmItemWarehouseDTO.getGoodsCount());
		newItemWarehouseDTO.setWarehouseCode(confirmItemWarehouseDTO.getWarehouseCode());
		newItemWarehouseDTO.setWarehouseName(confirmItemWarehouseDTO.getWarehouseName());
		newItemWarehouseDTO.setSupplierCode(confirmItemWarehouseDTO.getSupplierCode());
		newItemWarehouseDTO.setSupplierName(confirmItemWarehouseDTO.getSupplierName());
		newItemWarehouseDTO.setPurchaseDepartmentCode(confirmItemWarehouseDTO.getPurchaseDepartmentCode());
		newItemWarehouseDTO.setPurchaseDepartmentName(confirmItemWarehouseDTO.getPurchaseDepartmentName());
		newItemWarehouseDTO.setProductAttribute(confirmItemWarehouseDTO.getProductAttribute());
		newItemWarehouseDTO.setAvailableInventory(confirmItemWarehouseDTO.getAvailableInventory());
		newItemWarehouseDTO.setIsAgreementSku(confirmItemWarehouseDTO.getIsAgreementSku());
		newItemWarehouseDTO.setAgreementCode(confirmItemWarehouseDTO.getAgreementCode());
		newItemWarehouseDTO.setCreateId(confirmInDTO.getOperatorId());
		newItemWarehouseDTO.setCreateName(confirmInDTO.getOperatorName());
		newItemWarehouseDTO.setModifyId(confirmInDTO.getOperatorId());
		newItemWarehouseDTO.setModifyName(confirmInDTO.getOperatorName());

		return newItemWarehouseDTO;
	}

	/**
	 * 检查分销单状态
	 *
	 * @param tradeOrderDTO
	 * @param orderItemDTO
	 * @return
	 */
	private TradeOrderErpDistributionDTO getTargetErpDistribution(TradeOrdersDTO tradeOrderDTO,
			TradeOrderItemsDTO orderItemDTO) {
		List<TradeOrderErpDistributionDTO> orderErpDistributionList = tradeOrderDTO.getErpDistributionDTOList();
		String erpStatus = "";
		long erpDistributionId = orderItemDTO.getErpDistributionId().longValue();
		for (TradeOrderErpDistributionDTO erpDistributionDTO : orderErpDistributionList) {
			if (erpDistributionDTO.getId().longValue() == erpDistributionId) {
				erpStatus = erpDistributionDTO.getErpStatus();
				if (ErpStatusEnum.PENDING.getValue().equals(erpStatus)
						|| ErpStatusEnum.FAILURE.getValue().equals(erpStatus)) {
					return erpDistributionDTO;
				}
			}
		}
		return null;
	}

	@Override
	public ExecuteResult<DataGrid<TradeOrdersShowDTO>> queryVenusDealTradeOrderListByCondition(
			VenusTradeOrdersQueryInDTO conditionDTO, Pager<VenusTradeOrdersQueryInDTO> pager) {
		ExecuteResult<DataGrid<TradeOrdersShowDTO>> result = new ExecuteResult<DataGrid<TradeOrdersShowDTO>>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		List<String> orderStatusList = new ArrayList<String>();
		List<DictionaryInfo> statusInfoList = baseService.getDictOptList(dictMap, DictionaryConst.TYPE_ORDER_STATUS);
		String payPendingStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY);
		String completeStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_COMPLETE);
		try {
			if (conditionDTO != null) {
				conditionDTO.setChannelCode(baseService.getDictValueByCode(dictMap,
						DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_HTD));
				if (statusInfoList != null && !statusInfoList.isEmpty()) {
					for (DictionaryInfo dictInfo : statusInfoList) {
						if (payPendingStatus.compareTo(dictInfo.getValue()) <= 0
								&& completeStatus.compareTo(dictInfo.getValue()) >= 0) {
							orderStatusList.add(dictInfo.getValue());
						}
					}
					conditionDTO.setOrderStatusList(orderStatusList);
				}
			}
			result = queryVenusTradeOrderListByCondition(dictMap, conditionDTO, pager);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<TradeOrdersShowDTO>> queryVenusVerifyTradeOrderListByCondition(
			VenusTradeOrdersQueryInDTO conditionDTO, Pager<VenusTradeOrdersQueryInDTO> pager) {
		ExecuteResult<DataGrid<TradeOrdersShowDTO>> result = new ExecuteResult<DataGrid<TradeOrdersShowDTO>>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		List<String> orderStatusList = new ArrayList<String>();
		try {
			if (conditionDTO != null) {
				conditionDTO.setChannelCode(baseService.getDictValueByCode(dictMap,
						DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_HTD));
				orderStatusList.add(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_VERIFY_PENDING));
				conditionDTO.setOrderStatusList(orderStatusList);
			}
			result = queryVenusTradeOrderListByCondition(dictMap, conditionDTO, pager);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<List<TradeOrdersShowDTO>> exportVenusVerifyTradeOrderListByCondition(
			VenusTradeOrdersQueryInDTO conditionDTO) {
		ExecuteResult<List<TradeOrdersShowDTO>> result = new ExecuteResult<List<TradeOrdersShowDTO>>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		List<String> orderStatusList = new ArrayList<String>();
		try {
			if (conditionDTO != null) {
				conditionDTO.setChannelCode(baseService.getDictValueByCode(dictMap,
						DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_HTD));
				orderStatusList.add(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
						DictionaryConst.OPT_ORDER_STATUS_VERIFY_PENDING));
				conditionDTO.setOrderStatusList(orderStatusList);
			}
			result = exportVenusTradeOrderListByCondition(dictMap, conditionDTO);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<List<TradeOrdersShowDTO>> exportVenusDealTradeOrderListByCondition(
			VenusTradeOrdersQueryInDTO conditionDTO) {
		ExecuteResult<List<TradeOrdersShowDTO>> result = new ExecuteResult<List<TradeOrdersShowDTO>>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		List<String> orderStatusList = new ArrayList<String>();
		List<DictionaryInfo> statusInfoList = baseService.getDictOptList(dictMap, DictionaryConst.TYPE_ORDER_STATUS);
		String payPendingStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY);
		String completeStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_COMPLETE);
		try {
			if (conditionDTO != null) {
				conditionDTO.setChannelCode(baseService.getDictValueByCode(dictMap,
						DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_HTD));
				if (statusInfoList != null && !statusInfoList.isEmpty()) {
					for (DictionaryInfo dictInfo : statusInfoList) {
						if (payPendingStatus.compareTo(dictInfo.getValue()) <= 0
								&& completeStatus.compareTo(dictInfo.getValue()) >= 0) {
							orderStatusList.add(dictInfo.getValue());
						}
					}
					conditionDTO.setOrderStatusList(orderStatusList);
				}
			}
			result = exportVenusTradeOrderListByCondition(dictMap, conditionDTO);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 根据条件查询VMS订单列表
	 *
	 * @param dictMap
	 * @param conditionDTO
	 * @param pager
	 * @return
	 * @throws TradeCenterBusinessException
	 * @throws Exception
	 */
	private ExecuteResult<DataGrid<TradeOrdersShowDTO>> queryVenusTradeOrderListByCondition(
			Map<String, DictionaryInfo> dictMap, VenusTradeOrdersQueryInDTO conditionDTO,
			Pager<VenusTradeOrdersQueryInDTO> pager) throws TradeCenterBusinessException, Exception {
		ExecuteResult<DataGrid<TradeOrdersShowDTO>> result = new ExecuteResult<DataGrid<TradeOrdersShowDTO>>();
		DataGrid<TradeOrdersShowDTO> dataGrid = new DataGrid<TradeOrdersShowDTO>();
		List<TradeOrdersDTO> dataList = null;
		List<TradeOrdersShowDTO> dataShowList = new ArrayList<TradeOrdersShowDTO>();
		long count = 0;
		if (conditionDTO == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "卖家编码不能为空");
		}
		// 输入DTO的验证
		ValidateResult validateResult = ValidationUtils.validateEntity(conditionDTO);
		// 有错误信息时返回错误信息
		if (validateResult.isHasErrors()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
		}
		baseService.setOrderStatusCondition(dictMap, conditionDTO);
		count = orderDAO.queryTradeOrderCountByCondition(conditionDTO);
		if (count > 0) {
			dataList = orderDAO.queryTradeOrderListByCondition(conditionDTO, pager);
			for (TradeOrdersDTO orderDTO : dataList) {
				dataShowList.add(baseService.exchangeTradeOrdersDTO2Show(dictMap, orderDTO));
			}
			dataGrid.setRows(dataShowList);
		}
		dataGrid.setTotal(count);
		result.setResult(dataGrid);
		return result;
	}

	/**
	 * 导出VMS列表订单数据
	 *
	 * @param dictMap
	 * @param conditionDTO
	 * @return
	 * @throws TradeCenterBusinessException
	 * @throws Exception
	 */
	private ExecuteResult<List<TradeOrdersShowDTO>> exportVenusTradeOrderListByCondition(
			Map<String, DictionaryInfo> dictMap, VenusTradeOrdersQueryInDTO conditionDTO)
					throws TradeCenterBusinessException, Exception {
		ExecuteResult<List<TradeOrdersShowDTO>> result = new ExecuteResult<List<TradeOrdersShowDTO>>();
		List<TradeOrdersDTO> dataList = null;
		List<TradeOrdersShowDTO> dataShowList = new ArrayList<TradeOrdersShowDTO>();
		long count = 0;

		if (conditionDTO == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "卖家编码不能为空");
		}
		// 输入DTO的验证
		ValidateResult validateResult = ValidationUtils.validateEntity(conditionDTO);
		// 有错误信息时返回错误信息
		if (validateResult.isHasErrors()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
		}
		baseService.setOrderStatusCondition(dictMap, conditionDTO);
		count = orderDAO.queryTradeOrderCountByCondition(conditionDTO);
		if (count > 0) {
			if (count > 10000) {
				throw new TradeCenterBusinessException(ReturnCodeConst.EXPORT_COUNT_OVER_LIMIT, "导出数据件数超过1万件，请调整检索条件");
			}
			dataList = orderDAO.queryTradeOrderListByCondition(conditionDTO, null);
			for (TradeOrdersDTO orderDTO : dataList) {
				dataShowList.add(baseService.exchangeTradeOrdersDTO2Show(dictMap, orderDTO));
			}
		}
		result.setResult(dataShowList);
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> queryVenusProductPlusTradeOrderListByCondition(
			TradeOrdersQueryInDTO conditionDTO, Pager<TradeOrdersQueryInDTO> pager) {
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		if (conditionDTO != null) {
			if (StringUtils.isEmpty(conditionDTO.getChannelCode())) {
				conditionDTO.setChannelCode(baseService.getDictValueByCode(dictMap,
						DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_PRODUCTPLUS));
			} else {
				if (!conditionDTO.getChannelCode().startsWith(baseService.getDictValueByCode(dictMap,
						DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_PRODUCTPLUS))) {
					conditionDTO.setChannelCode(baseService.getDictValueByCode(dictMap,
							DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_PRODUCTPLUS));
				}
			}
		}
		return queryTradeOrderListByCondition(conditionDTO, pager);
	}

	@Override
	public ExecuteResult<TradeOrdersDTO> approveTradeOrderInfo(VenusVerifyTradeOrdersInDTO verifyDTO) {
		ExecuteResult<TradeOrdersDTO> result = new ExecuteResult<TradeOrdersDTO>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		TradeOrdersDTO orderDTO = null;
		String orderModifyTime = "";
		String orderNo = verifyDTO.getOrderNo();
		String errorCode = "";
		List<String> validOrderStatusList = new ArrayList<String>();
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(verifyDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
			}
			orderDTO = orderDBHandle.queryTradeOrdersByOrderNo(orderNo, false);
			validOrderStatusList.add(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VERIFY_PENDING));
			checkApproveOrderInfo(dictMap, orderDTO, validOrderStatusList);
			orderModifyTime = DateUtils.format(orderDTO.getModifyTime(), DateUtils.YMDHMS);
			if (!orderModifyTime.equals(verifyDTO.getModifyTimeStr())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_MODIFIED,
						"订单编号:" + orderNo + "的订单已被修改请重新确认");
			}
			orderDTO.setOrderRemarks(verifyDTO.getVerifyComment());
			orderDTO.setModifyId(verifyDTO.getOperatorId());
			orderDTO.setModifyName(verifyDTO.getOperatorName());
			orderDTO = orderDBHandle.saveApproveTradeOrderInfo(orderDTO);
			result.setResult(orderDTO);
		} catch (TradeCenterBusinessException tcbe) {
			errorCode = tcbe.getCode();
			if (ReturnCodeConst.ORDER_NOT_VERIFY_PAY_PENDING.equals(errorCode)) {
				result.setCode(ReturnCodeConst.ORDER_NOT_VERIFY_PENDING);
				result.addErrorMessage("订单编号:" + orderNo + " 不是待审核状态");
			} else {
				result.setCode(tcbe.getCode());
				result.addErrorMessage(tcbe.getMessage());
			}
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> queryTradeOrderListByCondition(TradeOrdersQueryInDTO inDTO,
			Pager<TradeOrdersQueryInDTO> pager) {
		logger.info("**********运营系统订单列表查询 开始***********");
		long startTime = System.currentTimeMillis();
		ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> result = new ExecuteResult<DataGrid<TradeOrderItemsShowDTO>>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		DataGrid<TradeOrderItemsShowDTO> dataGrid = new DataGrid<TradeOrderItemsShowDTO>();
		List<TradeOrderItemsDTO> dataList = null;
		List<TradeOrderItemsShowDTO> dataGridList = new ArrayList<TradeOrderItemsShowDTO>();
		long count = 0;
		if (!StringUtils.isEmpty(inDTO.getErpSholesalerCode())) {
			inDTO.setErpSholesalerCode("-" + inDTO.getErpSholesalerCode());
		}
		baseService.setOrderStatusCondition(dictMap, inDTO);
		baseService.setOrderItemProductPlusStatusCondition(dictMap, inDTO);
		baseService.setProductChannelCondition(dictMap, inDTO);

		long endTime0 = System.currentTimeMillis();
		logger.info("***********运营系统订单列表查询 准备处理结束|调用耗时{}ms***********", (endTime0 - startTime));
		try {
			if ("90".equals(inDTO.getOrderStatus())) {
				inDTO.setIsCancelOrderItem(1);
			}
			if ("82".equals(inDTO.getOrderStatus()) || "83".equals(inDTO.getOrderStatus())) {
				inDTO.setIsErrorFlag(-1);
			}
			count = orderItemsDAO.queryTradeOrderCountByCondition(inDTO);
			if (count > 0) {
				dataList = orderItemsDAO.queryTradeOrderListByCondition(inDTO, pager);

				long endTime1 = System.currentTimeMillis();
				logger.info("***********运营系统订单列表查询 查询处理结束|调用耗时{}ms***********", (endTime1 - endTime0));

				for (TradeOrderItemsDTO itemDTO : dataList) {
					dataGridList.add(baseService.exchangeTradeOrderItemsDTO2Show(dictMap, itemDTO));
				}
				long endTime2 = System.currentTimeMillis();
				logger.info("***********运营系统订单列表查询 查询结果变换处理结束|调用耗时{}ms***********", (endTime2 - endTime1));
				dataGrid.setRows(dataGridList);
				long endTime3 = System.currentTimeMillis();
				logger.info("***********运营系统订单列表查询 处理结束返回|调用耗时{}ms***********", (endTime3 - endTime2));
			}
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> queryTradeOrderHmsListByItemCodeAndBoxFlag(
			TradeOrdersQueryInDTO inDTO, Pager<TradeOrdersQueryInDTO> pager) {
		logger.info("**********运营系统根据商品编码查询对应的订单开始***********");
		long startTime = System.currentTimeMillis();
		ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> result = new ExecuteResult<DataGrid<TradeOrderItemsShowDTO>>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		DataGrid<TradeOrderItemsShowDTO> dataGrid = new DataGrid<TradeOrderItemsShowDTO>();
		List<TradeOrderItemsDTO> dataList = new ArrayList<TradeOrderItemsDTO>();
		List<TradeOrderItemsShowDTO> dataGridList = new ArrayList<TradeOrderItemsShowDTO>();
		long count = 0;
		if ("".equals(inDTO.getItemCode()) || inDTO.getIsBoxFlag() == null) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage("商品编码或者包厢标记为空");
		} else {
			try {
				count = orderItemsDAO.queryTradeOrderCountItemCodeAndBoxFlag(inDTO);
				if (count > 0) {
					dataList = orderItemsDAO.queryTradeOrderListItemCodeAndBoxFlag(inDTO, pager);
					for (TradeOrderItemsDTO itemDTO : dataList) {
						if (itemDTO.getIsChangePrice() == 1) {
							itemDTO.setGoodsCount(itemDTO.getBargainingGoodsCount());
						}
						dataGridList.add(baseService.exchangeTradeOrderItemsDTO2Show(dictMap, itemDTO));
					}
					long endTime = System.currentTimeMillis();
					logger.info("***********运营系统根据商品编码查询订单结束|调用耗时{}ms***********", (endTime - startTime));
					dataGrid.setRows(dataGridList);
				}
				dataGrid.setTotal(count);
				result.setCode(ReturnCodeConst.RETURN_SUCCESS);
				result.setResult(dataGrid);
				result.setResultMessage("成功");
			} catch (TradeCenterBusinessException tcbe) {
				result.setCode(tcbe.getCode());
				result.addErrorMessage(tcbe.getMessage());
			} catch (Exception e) {
				result.setCode(ReturnCodeConst.SYSTEM_ERROR);
				result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			}
		}
		return result;
	}

	@Override
	public ExecuteResult<TradeOrderItemStockDTO> getItemStockAndLockStock(TradeOrdersQueryInDTO inDTO) {
		logger.info("**********运营系统根据商品编码获取商品的库存以及锁库存开始***********");
		long startTime = System.currentTimeMillis();
		ExecuteResult<TradeOrderItemStockDTO> result = new ExecuteResult<TradeOrderItemStockDTO>();
		TradeOrderItemStockDTO tradeOrderItemStock = new TradeOrderItemStockDTO();
		QueryItemStockDetailInDTO quantityInfoInDTO = new QueryItemStockDetailInDTO();
		quantityInfoInDTO.setIsBoxFlag(inDTO.getIsBoxFlag());
		quantityInfoInDTO.setItemCode(inDTO.getItemCode());
		ExecuteResult<QueryItemStockDetailOutDTO> queryItemStockDetailOut = itemExportService
				.queryItemQuantityInfo(quantityInfoInDTO);
		if (queryItemStockDetailOut.getCode().equals("00000")) {
			tradeOrderItemStock.setProductStock(queryItemStockDetailOut.getResult().getDisplayQuantity());
			tradeOrderItemStock.setLockStock(queryItemStockDetailOut.getResult().getReserveQuantity());
			result.setCode(ReturnCodeConst.RETURN_SUCCESS);
			result.setResult(tradeOrderItemStock);
			result.setResultMessage("成功");
		} else {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage("商品中心查询库存以及锁库存报错");
		}
		long endTime = System.currentTimeMillis();
		logger.info("***********运营系统根据商品编码获取商品的库存以及锁库存结束|调用耗时{}ms***********", (endTime - startTime));
		return result;
	}

	@Override
	public ExecuteResult<TradeOrdersShowDTO> queryVenusTradeOrderInfo(String orderNo) {
		ExecuteResult<TradeOrdersShowDTO> result = new ExecuteResult<TradeOrdersShowDTO>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		TradeOrdersShowDTO orderShowDTO = null;
		TradeOrderItemsDTO itemDTO = null;
		TradeOrderItemsShowDTO itemShowDTO = null;
		TradeOrderItemsWarehouseDetailShowDTO itemWarehouseShowDTO = null;
		List<TradeOrderItemsShowDTO> itemShowDTOList = null;
		List<TradeOrderItemsDTO> itemDTOList = null;
		List<TradeOrderItemsWarehouseDetailDTO> warehouseDTOList = null;
		List<TradeOrderItemsWarehouseDetailShowDTO> warehouseShowDTOList = new ArrayList<TradeOrderItemsWarehouseDetailShowDTO>();
		DecimalFormat df = new DecimalFormat("###,###,###.##");
		int isCancelOrder = 0;
		Iterator<TradeOrderItemsDTO> itemIt = null;
		Iterator<TradeOrderItemsShowDTO> itemShowIt = null;
		try {
			result = queryTradeOrderInfo(orderNo);
			if (result.isSuccess()) {
				orderShowDTO = result.getResult();
				isCancelOrder = orderShowDTO.getIsCancelOrder();
				itemDTOList = orderShowDTO.getOrderItemList();
				itemShowDTOList = orderShowDTO.getOrderItemShowList();
				itemIt = itemDTOList.iterator();
				itemShowIt = itemShowDTOList.iterator();
				while (itemIt.hasNext()) {
					itemDTO = itemIt.next();
					itemShowDTO = itemShowIt.next();
					if (isCancelOrder != itemDTO.getIsCancelOrderItem()) {
						itemIt.remove();
						itemShowIt.remove();
						continue;
					}
					warehouseDTOList = warehouseDetailDAO.queryOrderItemWarehouseDetailByOrderItemNo(itemDTO);
					itemDTO.setWarehouseDTOList(warehouseDTOList);
					warehouseShowDTOList = new ArrayList<TradeOrderItemsWarehouseDetailShowDTO>();
					for (TradeOrderItemsWarehouseDetailDTO warehouseDTO : warehouseDTOList) {
						itemWarehouseShowDTO = baseService.exchangeTradeOrderItemsWarehouseDTO2Show(dictMap,
								warehouseDTO);
						if (itemDTO.getUsedRebateAmount() != null) {
							itemWarehouseShowDTO.setRebateAmount(df.format(itemDTO.getUsedRebateAmount()));
						}
						warehouseShowDTOList.add(itemWarehouseShowDTO);
					}
					itemShowDTO.setWarehouseShowDTOList(warehouseShowDTOList);
				}
			}
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<TradeOrdersShowDTO> queryTradeOrderInfo(String orderNo) {
		ExecuteResult<TradeOrdersShowDTO> result = new ExecuteResult<TradeOrdersShowDTO>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		TradeOrdersDTO orderDTO = null;
		TradeOrdersShowDTO orderShowDTO = null;
		TradeOrderItemsDTO condition = new TradeOrderItemsDTO();
		List<TradeOrderItemsDTO> orderItemsDTOList = null;
		List<TradeOrderStatusHistoryDTO> orderStatusHistoryDTOList = null;
		List<TradeOrderItemsShowDTO> orderItemsShowDTOList = new ArrayList<TradeOrderItemsShowDTO>();
		List<TradeOrderItemsDiscountDTO> orderItemDiscountDTOList = null;
		List<TradeOrderItemsDiscountDTO> tmpOrderItemDiscountDTOList = null;
		Map<String, TradeOrdersDiscountInfo> orderDiscountDTOMap = new HashMap<String, TradeOrdersDiscountInfo>();
		List<String> promotionNameList = new ArrayList<String>();
		List<TradeOrderErpDistributionDTO> erpDistributionDTOList = null;
		TradeOrdersDiscountInfo orderDiscountDTO = null;
		TradeOrderItemsDTO tmpOrderItemDTO = null;
		TradeOrderItemsShowDTO tmpOrderItemShowDTO = null;
		List<TradeOrderPayInfoDTO> payInfoList = null;
		List<String> promotionIdList = new ArrayList<String>();
		ExecuteResult<List<PromotionInfoDTO>> promotionInfoResult = new ExecuteResult<List<PromotionInfoDTO>>();
		List<PromotionInfoDTO> promotionInfoList = new ArrayList<PromotionInfoDTO>();
		Map<String, PromotionInfoDTO> promotionInfoMap = new HashMap<String, PromotionInfoDTO>();
		String promotionName = "";
		String orderItemNo = "";
		String promotionId = "";
		String couponCode = "";
		String mapKey = "";
		String tradeNo = "";
		List<String> erpLockBalanceCodeList = new ArrayList<String>();
		List<String> erpSholesalerCodeList = new ArrayList<String>();
		Map<String, String> erpSholesalerCodeMap = new HashMap<String, String>();
		DecimalFormat df = new DecimalFormat("###,###,###.##");
		try {
			if (StringUtils.isEmpty(orderNo)) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "订单编号不能为空");
			}
			orderDTO = orderDAO.queryTradeOrderByOrderNo(orderNo);
			if (orderDTO == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_EXIST, "订单数据不存在");
			}
			condition.setOrderNo(orderNo);
			condition.setIsCancelOrderItem(YesNoEnum.DELETE.getValue());
			orderItemsDTOList = orderItemsDAO.queryTradeOrderItemsByOrderNo(condition);
			if (orderItemsDTOList == null || orderItemsDTOList.isEmpty()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NOT_EXIST, "订单行数据不存在");
			}
			if (YesNoEnum.YES.getValue() == orderDTO.getHasUsedCoupon()) {
				orderItemDiscountDTOList = orderItemsDiscountDAO.queryItemDiscountByOrderNo(orderNo);
				if (orderItemDiscountDTOList != null && !orderItemDiscountDTOList.isEmpty()) {
					for (TradeOrderItemsDiscountDTO discountDTO : orderItemDiscountDTOList) {
						orderItemNo = discountDTO.getOrderItemNo();
						promotionId = discountDTO.getPromotionId();
						couponCode = discountDTO.getBuyerCouponCode();
						tmpOrderItemDTO = null;
						for (TradeOrderItemsDTO orderItemDTO : orderItemsDTOList) {
							if (orderItemNo.equals(orderItemDTO.getOrderItemNo())
									&& orderDTO.getIsCancelOrder() == orderItemDTO.getIsCancelOrderItem()
									&& YesNoEnum.YES.getValue() == orderItemDTO.getHasUsedCoupon()) {
								tmpOrderItemDTO = orderItemDTO;
								break;
							}
						}
						if (tmpOrderItemDTO == null) {
							continue;
						}
						promotionIdList.add(promotionId);
						tmpOrderItemDiscountDTOList = tmpOrderItemDTO.getDiscountDTOList();
						if (tmpOrderItemDiscountDTOList == null || tmpOrderItemDiscountDTOList.isEmpty()) {
							tmpOrderItemDiscountDTOList = new ArrayList<TradeOrderItemsDiscountDTO>();
						}
						tmpOrderItemDiscountDTOList.add(discountDTO);
						tmpOrderItemDTO.setDiscountDTOList(tmpOrderItemDiscountDTOList);
						mapKey = orderItemNo + "-" + couponCode;
						if (orderDiscountDTOMap.containsKey(mapKey)) {
							orderDiscountDTO = orderDiscountDTOMap.get(mapKey);
						} else {
							orderDiscountDTO = new TradeOrdersDiscountInfo();
						}
						orderDiscountDTO.setPromotionId(discountDTO.getPromotionId());
						orderDiscountDTO.setLevelCode(discountDTO.getLevelCode());
						orderDiscountDTO.setBuyerCouponCode(couponCode);
						orderDiscountDTO.setCouponProviderType(discountDTO.getCouponProviderType());
						orderDiscountDTO.setCouponType(discountDTO.getCouponType());
						orderDiscountDTO.setCouponDiscount(
								orderDiscountDTO.getCouponDiscount().add(discountDTO.getCouponDiscount()));
						orderDiscountDTOMap.put(mapKey, orderDiscountDTO);
					}
					if (orderDiscountDTOMap.size() > 0) {
						promotionInfoResult = promotionInfoService.queryPromotionInfoByPromotionId(promotionIdList);
						if (promotionInfoResult.isSuccess()) {
							promotionInfoList = promotionInfoResult.getResult();
							if (promotionInfoList != null && !promotionInfoList.isEmpty()) {
								for (PromotionInfoDTO promotionInfo : promotionInfoList) {
									promotionInfoMap.put(promotionInfo.getPromotionId(), promotionInfo);
								}
							}
						}
						for (Entry<String, TradeOrdersDiscountInfo> entry : orderDiscountDTOMap.entrySet()) {
							orderDiscountDTO = entry.getValue();
							promotionId = orderDiscountDTO.getPromotionId();
							promotionName = "优惠券号:" + orderDiscountDTO.getBuyerCouponCode() + " 优惠金额:"
									+ df.format(orderDiscountDTO.getCouponDiscount()) + "元";
							if (promotionInfoMap.containsKey(promotionId)) {
								promotionName = promotionInfoMap.get(promotionId).getPromotionName() + " "
										+ promotionName;
							}
							promotionNameList.add(promotionName);
						}
						orderDTO.setPromotionNameList(promotionNameList);
					}
				}
			} else if (YesNoEnum.YES.getValue() == orderDTO.getIsTimelimitedOrder()) {
				promotionIdList.add(orderDTO.getPromotionId());
				promotionInfoResult = promotionInfoService.queryPromotionInfoByPromotionId(promotionIdList);
				if (promotionInfoResult.isSuccess()) {
					promotionInfoList = promotionInfoResult.getResult();
					if (promotionInfoList != null && !promotionInfoList.isEmpty()) {
						promotionName = promotionInfoList.get(0).getPromotionName();
						promotionNameList.add("秒杀活动名称:" + promotionName);
						orderDTO.setPromotionNameList(promotionNameList);
					}
				}
			}
			orderStatusHistoryDTOList = orderStatusHistoryDAO.queryStatusHistoryByOrderNo(orderNo);
			orderDTO.setOrderStatusHistoryDTOList(orderStatusHistoryDTOList);
			payInfoList = orderPayInfoDAO.queryOrderPayInfoByOrderNo(orderNo);
			if (payInfoList != null && !payInfoList.isEmpty()) {
				for (TradeOrderPayInfoDTO payInfo : payInfoList) {
					erpLockBalanceCodeList.add(payInfo.getDownOrderNo());
				}
				orderDTO.setErpLockBalanceCodeList(erpLockBalanceCodeList);
			}
			erpDistributionDTOList = orderErpDistributionDAO.queryOrderErpDistributionByOrderNo(orderNo);
			orderDTO.setErpDistributionDTOList(erpDistributionDTOList);
			orderShowDTO = baseService.exchangeTradeOrdersDTO2Show(dictMap, orderDTO);
			tradeNo = orderShowDTO.getTradeNo();
			for (TradeOrderItemsDTO orderItemDTO : orderItemsDTOList) {
				tmpOrderItemShowDTO = baseService.exchangeTradeOrderItemsDTO2Show(dictMap, orderItemDTO);
				tmpOrderItemShowDTO.setTradeNo(tradeNo);
				orderItemsShowDTOList.add(tmpOrderItemShowDTO);
				if (!StringUtils.isEmpty(tmpOrderItemShowDTO.getErpSholesalerCode())) {
					erpSholesalerCodeMap.put(tmpOrderItemShowDTO.getErpSholesalerCode(),
							tmpOrderItemShowDTO.getErpSholesalerCode());
				}
			}
			if (!erpSholesalerCodeMap.isEmpty()) {
				for (Entry<String, String> entry : erpSholesalerCodeMap.entrySet()) {
					erpSholesalerCodeList.add(entry.getValue());
				}
				orderShowDTO.setErpSholesalerCodeList(erpSholesalerCodeList);
			}
			orderShowDTO.setOrderItemList(orderItemsDTOList);
			orderShowDTO.setOrderItemShowList(orderItemsShowDTOList);
			result.setResult(orderShowDTO);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<TradeOrderItemsShowDTO> queryTradeOrderItemInfo(String orderNo, String orderItemNo) {
		ExecuteResult<TradeOrderItemsShowDTO> result = new ExecuteResult<TradeOrderItemsShowDTO>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		TradeOrderItemsDTO condition = new TradeOrderItemsDTO();
		TradeOrdersDTO orderDTO = null;
		TradeOrderItemsDTO orderItemDTO = null;
		TradeOrderItemsShowDTO orderItemShowDTO = null;
		Long distributionId = null;
		TradeOrderErpDistributionDTO erpDistributionDTO = null;
		List<TradeOrderItemsWarehouseDetailDTO> warehouseDTOList = null;
		List<TradeOrderItemsWarehouseDetailShowDTO> warehouseShowDTOList = new ArrayList<TradeOrderItemsWarehouseDetailShowDTO>();
		List<TradeOrderPayInfoDTO> orderPayInfoList = null;
		List<TradeOrderItemsStatusHistoryDTO> statusHistoryDTOList = null;
		TradeOrderItemsWarehouseDetailShowDTO itemWarehouseShowDTO = null;
		DecimalFormat df = new DecimalFormat("###,###,###.##");

		try {
			if (StringUtils.isEmpty(orderNo)) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "订单编号不能为空");
			}
			if (StringUtils.isEmpty(orderItemNo)) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "订单行编号不能为空");
			}
			orderDTO = orderDAO.queryTradeOrderByOrderNo(orderNo);
			if (orderDTO == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_EXIST, "订单数据不存在");
			}
			condition.setOrderNo(orderNo);
			condition.setOrderItemNo(orderItemNo);
			orderItemDTO = orderItemsDAO.queryTradeOrderItemsByOrderItemNo(condition);
			if (orderItemDTO == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NOT_EXIST, "订单行数据不存在");
			}
			orderItemDTO.setTradeOrderInfo(orderDTO);
			// 取得erp收付款
			orderPayInfoList = orderPayInfoDAO.queryOrderPayInfoByOrderNo(orderNo);
			if (orderPayInfoList != null && !orderPayInfoList.isEmpty()) {
				if (orderItemDTO.getChannelCode().startsWith(baseService.getDictValueByCode(dictMap,
						DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_PRODUCTPLUS))) {
					orderItemDTO.setErpLockBalanceCode(orderPayInfoList.get(0).getDownOrderNo());
				} else {
					for (TradeOrderPayInfoDTO orderPayInfo : orderPayInfoList) {
						if (Long.parseLong(orderPayInfo.getBrandCode()) == orderItemDTO.getBrandId().longValue()
								&& orderPayInfo.getClassCode().equals(orderItemDTO.getErpFirstCategoryCode())) {
							orderItemDTO.setErpLockBalanceCode(orderPayInfo.getDownOrderNo());
							break;
						}
					}
				}
			}
			distributionId = orderItemDTO.getErpDistributionId();
			if (distributionId != null && distributionId.longValue() > 0) {
				erpDistributionDTO = orderErpDistributionDAO.queryOrderErpDistributionById(distributionId);
				if (erpDistributionDTO != null) {
					orderItemDTO.setErpDistributionDTO(erpDistributionDTO);
				}
			}
			if (baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
					DictionaryConst.OPT_PRODUCT_CHANNEL_HTD).equals(orderItemDTO.getChannelCode())) {
				warehouseDTOList = warehouseDetailDAO.queryOrderItemWarehouseDetailByOrderItemNo(orderItemDTO);
				orderItemDTO.setWarehouseDTOList(warehouseDTOList);
			}
			statusHistoryDTOList = itemStatusHistoryDAO.queryStatusHistoryByOrderItemNo(orderItemNo);
			orderItemDTO.setItemStatusHistoryDTOList(statusHistoryDTOList);
			orderItemShowDTO = baseService.exchangeTradeOrderItemsDTO2Show(dictMap, orderItemDTO);
			if (warehouseDTOList != null && !warehouseDTOList.isEmpty()) {
				for (TradeOrderItemsWarehouseDetailDTO warehouseDTO : warehouseDTOList) {
					itemWarehouseShowDTO = baseService.exchangeTradeOrderItemsWarehouseDTO2Show(dictMap, warehouseDTO);
					if (orderItemShowDTO.getUsedRebateAmount() != null) {
						itemWarehouseShowDTO.setRebateAmount(df.format(orderItemShowDTO.getUsedRebateAmount()));
					}
					warehouseShowDTOList.add(itemWarehouseShowDTO);
				}
				orderItemShowDTO.setWarehouseShowDTOList(warehouseShowDTOList);
			}
			result.setResult(orderItemShowDTO);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<TradeOrdersDTO> negotiateTradeOrderInfo(VenusNegotiateTradeOrderDTO negotiateOrderDTO) {
		ExecuteResult<TradeOrdersDTO> result = new ExecuteResult<TradeOrdersDTO>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		TradeOrdersDTO orderDTO = null;
		CustomerDTO customerDTO = null;
		String orderNo = negotiateOrderDTO.getOrderNo();
		String orderModifyTime = "";
		ExecuteResult<String> stockResult = null;
		ExecuteResult<String> couponResult = null;
		List<String> validOrderStatusList = new ArrayList<String>();
		String messageId = "";
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(negotiateOrderDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
			}
			checkNegotiateOrderParameter(negotiateOrderDTO);
			messageId = baseService.getMessageId();
			orderDTO = orderDBHandle.queryTradeOrdersByOrderNo(orderNo, true);
			validOrderStatusList.add(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VERIFY_PENDING));
			validOrderStatusList.add(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY));
			validOrderStatusList.add(baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
			checkApproveOrderInfo(dictMap, orderDTO, validOrderStatusList);
			if (baseService
					.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_FROM, DictionaryConst.OPT_ORDER_FROM_VMS)
					.equals(orderDTO.getOrderFrom())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_IS_VMS_ADDED,
						"订单编号:" + orderNo + " 是VMS新增订单不能议价");
			}
			// if (YesNoEnum.YES.getValue() == orderDTO.getIsChangePrice()) {
			// throw new
			// TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_NEGOTIATION,
			// "订单编号:" + orderNo + " 已议价过不能再次议价");
			// }
			if (YesNoEnum.YES.getValue() == orderDTO.getIsTimelimitedOrder()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_IS_TIMELIMITED_ERROR,
						"订单编号:" + orderNo + " 秒杀订单不可修改");
			}
			orderModifyTime = DateUtils.format(orderDTO.getModifyTime(), DateUtils.YMDHMS);
			if (!orderModifyTime.equals(negotiateOrderDTO.getModifyTimeStr())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_MODIFIED,
						"订单编号:" + orderNo + "的订单已被修改请重新确认");
			}
			// 取得用户ID信息
			customerDTO = customerService.getCustomerInfo(negotiateOrderDTO.getOperatorId());
			// 检查用户信息合法性
			if (customerDTO == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_OPERATION_USER_NOT_EXIST,
						"用户ID:" + negotiateOrderDTO.getOperatorId() + "信息不存在");
			}
			if (customerDTO.getCompanyId().compareTo(negotiateOrderDTO.getSellerId()) != 0) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_OPERATION_SELLER_ERROR,
						"用户ID:" + negotiateOrderDTO.getOperatorId() + "所属公司不是该供应商");
			}
			orderDTO = makeNegotiateOrderItemList(dictMap, orderDTO, negotiateOrderDTO,
					customerDTO.getIsLowFlag() == null ? false : customerDTO.getIsLowFlag().booleanValue());
			baseService.calculateTradeOrderAmount(dictMap, orderDTO);
			baseService.createTradeOrderPayInfo(dictMap, orderDTO);
			orderDTO.setModifyId(negotiateOrderDTO.getOperatorId());
			orderDTO.setModifyName(negotiateOrderDTO.getOperatorName());
			stockResult = orderStockHandle.changeOrderStock(messageId, orderDTO.getOrderFrom(),
					orderDTO.getOrderItemList(), false);
			couponResult = orderCouponHandle.releaseOrderCoupon(messageId, orderDTO.getOrderItemList());
			orderDTO = orderDBHandle.saveNegotiateTradeOrderInfo(orderDTO);
			result.setResult(orderDTO);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
			if (stockResult != null && stockResult.isSuccess()) {
				try {
					orderStockHandle.changeOrderStock(messageId, orderDTO.getOrderFrom(), orderDTO.getOrderItemList(),
							true);
				} catch (Exception se) {
					logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderServiceImpl-negotiateTradeOrderInfo",
							JSONObject.toJSONString(se));
				}
			}
			if (couponResult != null && couponResult.isSuccess()) {
				try {
					orderCouponHandle.reserveOrderCoupon(messageId, orderDTO.getOrderItemList());
				} catch (Exception se) {
					logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderServiceImpl-negotiateTradeOrderInfo",
							JSONObject.toJSONString(se));
				}
			}
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			if (stockResult != null && stockResult.isSuccess()) {
				try {
					orderStockHandle.changeOrderStock(messageId, orderDTO.getOrderFrom(), orderDTO.getOrderItemList(),
							true);
				} catch (Exception se) {
					logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderServiceImpl-negotiateTradeOrderInfo",
							JSONObject.toJSONString(se));
				}
			}
			if (couponResult != null && couponResult.isSuccess()) {
				try {
					orderCouponHandle.reserveOrderCoupon(messageId, orderDTO.getOrderItemList());
				} catch (Exception se) {
					logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderServiceImpl-negotiateTradeOrderInfo",
							JSONObject.toJSONString(se));
				}
			}
		}
		return result;
	}

	/**
	 * 根据议价结果生成订单行数据
	 *
	 * @param dictMap
	 * @param orderDTO
	 * @param negotiateOrderDTO
	 * @param canUnderFloorPrice
	 * @return
	 * @throws TradeCenterBusinessException
	 * @throws Exception
	 */
	private TradeOrdersDTO makeNegotiateOrderItemList(Map<String, DictionaryInfo> dictMap, TradeOrdersDTO orderDTO,
			VenusNegotiateTradeOrderDTO negotiateOrderDTO, boolean canUnderFloorPrice)
					throws TradeCenterBusinessException, Exception {
		Map<String, TradeOrderItemsDTO> orderItemMap = new HashMap<String, TradeOrderItemsDTO>();
		Map<String, VenusNegotiateTradeOrderItemDTO> negotiateItemMap = new HashMap<String, VenusNegotiateTradeOrderItemDTO>();
		int i = 0;
		String orderNo = orderDTO.getOrderNo();
		String tmpOrderItemNo = "";
		List<VenusNegotiateTradeOrderItemDTO> negotiateItemList = negotiateOrderDTO.getOrderItemList();
		List<TradeOrderItemsDTO> orderItemList = orderDTO.getOrderItemList();
		TradeOrderItemsDTO orderItemDTO = null;
		BigDecimal goodsPrice = BigDecimal.ZERO;
		int goodsCount = 0;
		BigDecimal goodsCountDecimal = BigDecimal.ZERO;
		TradeOrderItemsPriceHistoryDTO itemPriceHistoryDTO = null;
		List<TradeOrderItemsStatusHistoryDTO> itemStatusHistoryDTOList = new ArrayList<TradeOrderItemsStatusHistoryDTO>();
		TradeOrderItemsStatusHistoryDTO itemStatusHistoryDTO = null;
		boolean hasNegotiatedFlg = false;
		for (TradeOrderItemsDTO tmpOrderItemDTO : orderItemList) {
			if (YesNoEnum.NO.getValue() != tmpOrderItemDTO.getIsCancelOrderItem()) {
				continue;
			}
			orderItemMap.put(tmpOrderItemDTO.getOrderItemNo(), tmpOrderItemDTO);
		}
		for (VenusNegotiateTradeOrderItemDTO tmpNegotiateItemDTO : negotiateItemList) {
			if (!StringUtils.isEmpty(tmpNegotiateItemDTO.getOrderItemNo())) {
				negotiateItemMap.put(tmpNegotiateItemDTO.getOrderItemNo(), tmpNegotiateItemDTO);
			}
		}
		for (TradeOrderItemsDTO tmpOrderItemDTO : orderItemList) {
			tmpOrderItemNo = tmpOrderItemDTO.getOrderItemNo();
			if (!negotiateItemMap.containsKey(tmpOrderItemNo)) {
				tmpOrderItemDTO.setIsCancelOrderItem(YesNoEnum.DELETE.getValue());
				tmpOrderItemDTO.setOrderItemCancelOperatorId(negotiateOrderDTO.getOperatorId());
				tmpOrderItemDTO.setOrderItemCancelOperatorName(negotiateOrderDTO.getOperatorName());
				tmpOrderItemDTO.setOrderItemCancelReason(negotiateOrderDTO.getOperatorName() + "删除订单行");
				tmpOrderItemDTO.setModifyId(negotiateOrderDTO.getOperatorId());
				tmpOrderItemDTO.setModifyName(negotiateOrderDTO.getOperatorName());
				tmpOrderItemDTO.setDealFlag("delete");

				itemStatusHistoryDTO = new TradeOrderItemsStatusHistoryDTO();
				itemStatusHistoryDTO.setOrderItemNo(tmpOrderItemNo);
				itemStatusHistoryDTO.setOrderItemStatus(baseService.getDictValueByCode(dictMap,
						DictionaryConst.TYPE_ORDER_STATUS, DictionaryConst.OPT_ORDER_STATUS_CLOSE));
				itemStatusHistoryDTO.setOrderItemStatusText(baseService.getDictNameByCode(dictMap,
						DictionaryConst.TYPE_ORDER_STATUS, DictionaryConst.OPT_ORDER_STATUS_CLOSE));
				itemStatusHistoryDTO.setCreateId(negotiateOrderDTO.getOperatorId());
				itemStatusHistoryDTO.setCreateName(negotiateOrderDTO.getOperatorName());
				itemStatusHistoryDTOList.add(itemStatusHistoryDTO);
				tmpOrderItemDTO.setItemStatusHistoryDTOList(itemStatusHistoryDTOList);
				hasNegotiatedFlg = true;
			}
		}
		for (VenusNegotiateTradeOrderItemDTO negotiateItemDTO : negotiateItemList) {
			i++;
			tmpOrderItemNo = negotiateItemDTO.getOrderItemNo();
			// 已有的订单行时
			if (!StringUtils.isEmpty(tmpOrderItemNo)) {
				if (!orderItemMap.containsKey(tmpOrderItemNo)) {
					continue;
				}
				orderItemDTO = orderItemMap.get(tmpOrderItemNo);
				// 没有议价时跳过此条数据
				if ((negotiateItemDTO.getBargainingGoodsCount() == null
						|| negotiateItemDTO.getBargainingGoodsCount() <= 0)
						&& (negotiateItemDTO.getBargainingGoodsPrice() == null
								|| BigDecimal.ZERO.compareTo(negotiateItemDTO.getBargainingGoodsPrice()) >= 0)) {
					continue;
				}
				// 议价数量和议价金额没有发生变化时
				if (YesNoEnum.YES.getValue() == orderItemDTO.getIsChangePrice()) {
					if (orderItemDTO.getBargainingGoodsCount().equals(negotiateItemDTO.getBargainingGoodsCount())
							&& CalculateUtils.multiply(orderItemDTO.getBargainingGoodsPrice(), new BigDecimal(100))
									.intValue() == CalculateUtils
											.multiply(negotiateItemDTO.getBargainingGoodsPrice(), new BigDecimal(100))
											.intValue()) {
						continue;
					}
				} else {
					if (orderItemDTO.getGoodsCount().equals(negotiateItemDTO.getBargainingGoodsCount())
							&& CalculateUtils.multiply(orderItemDTO.getGoodsPrice(), new BigDecimal(100))
									.intValue() == CalculateUtils
											.multiply(negotiateItemDTO.getBargainingGoodsPrice(), new BigDecimal(100))
											.intValue()) {
						continue;
					}
				}
				// 使用优惠券时
				if (YesNoEnum.YES.getValue() == orderItemDTO.getHasUsedCoupon()) {
					throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_USED_COUPON_ERROR,
							"订单编号:" + orderNo + " 第" + i + "行订单商品使用优惠券不能议价");
				}
				if (!baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
						DictionaryConst.OPT_PRODUCT_CHANNEL_HTD).equals(orderItemDTO.getChannelCode())) {
					throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NONE_HTD_PRODUCT_ERROR,
							"订单编号:" + orderNo + " 第" + i + "行订单商品为商品+商品不能议价");
				}
				goodsCount = negotiateItemDTO.getBargainingGoodsCount();
				goodsCountDecimal = new BigDecimal(goodsCount);
				goodsPrice = negotiateItemDTO.getBargainingGoodsPrice();
				if (!canUnderFloorPrice && goodsPrice.compareTo(orderItemDTO.getCostPrice()) < 0) {
					throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_PRICE_TOO_LOW,
							"订单编号:" + orderNo + " 第" + i + "行订单商品议价价格不能小于分销限价");
				}
				itemPriceHistoryDTO = new TradeOrderItemsPriceHistoryDTO();
				itemPriceHistoryDTO.setOrderNo(orderNo);
				itemPriceHistoryDTO.setOrderItemNo(tmpOrderItemNo);
				itemPriceHistoryDTO.setChannelCode(orderItemDTO.getChannelCode());
				itemPriceHistoryDTO.setItemCode(orderItemDTO.getItemCode());
				itemPriceHistoryDTO.setSkuCode(orderItemDTO.getSkuCode());
				if (YesNoEnum.YES.getValue() == orderItemDTO.getIsChangePrice()) {
					itemPriceHistoryDTO.setBeforeBargainingGoodsPrice(orderItemDTO.getBargainingGoodsPrice());
					itemPriceHistoryDTO.setBeforeBargainingGoodsCount(orderItemDTO.getBargainingGoodsCount());
					itemPriceHistoryDTO.setBeforeTotalPrice(orderItemDTO.getBargainingGoodsAmount());
					itemPriceHistoryDTO.setBeforeFreight(orderItemDTO.getBargainingGoodsFreight());
				} else {
					itemPriceHistoryDTO.setBeforeBargainingGoodsPrice(orderItemDTO.getGoodsPrice());
					itemPriceHistoryDTO.setBeforeBargainingGoodsCount(orderItemDTO.getGoodsCount());
					itemPriceHistoryDTO.setBeforeTotalPrice(orderItemDTO.getGoodsAmount());
					itemPriceHistoryDTO.setBeforeFreight(orderItemDTO.getGoodsFreight());
				}
				itemPriceHistoryDTO.setBeforeTotalDiscount(orderItemDTO.getTotalDiscountAmount());
				itemPriceHistoryDTO.setBeforeShopDiscount(orderItemDTO.getShopDiscountAmount());
				itemPriceHistoryDTO.setBeforePlatformDiscount(orderItemDTO.getPlatformDiscountAmount());
				itemPriceHistoryDTO.setBeforePaymentPrice(orderItemDTO.getOrderItemPayAmount());

				orderItemDTO.setBargainingGoodsCount(goodsCount);
				orderItemDTO.setBargainingGoodsPrice(goodsPrice);
				orderItemDTO.setBargainingGoodsAmount(CalculateUtils.multiply(goodsPrice, goodsCountDecimal));
				orderItemDTO.setBargainingGoodsFreight(orderItemDTO.getGoodsFreight());
				orderItemDTO.setOrderItemTotalAmount(CalculateUtils.add(orderItemDTO.getBargainingGoodsAmount(),
						orderItemDTO.getBargainingGoodsFreight()));
				orderItemDTO.setOrderItemPayAmount(orderItemDTO.getOrderItemTotalAmount());
				orderItemDTO.setIsChangePrice(YesNoEnum.YES.getValue());
				orderItemDTO.setGoodsRealPrice(goodsPrice);
				orderItemDTO.setModifyId(negotiateOrderDTO.getOperatorId());
				orderItemDTO.setModifyName(negotiateOrderDTO.getOperatorName());
				orderItemDTO.setDealFlag("update");

				itemPriceHistoryDTO.setAfterBargainingGoodsPrice(orderItemDTO.getBargainingGoodsPrice());
				itemPriceHistoryDTO.setAfterBargainingGoodsCount(orderItemDTO.getBargainingGoodsCount());
				itemPriceHistoryDTO.setAfterTotalPrice(orderItemDTO.getBargainingGoodsAmount());
				itemPriceHistoryDTO.setAfterFreight(orderItemDTO.getBargainingGoodsFreight());
				itemPriceHistoryDTO.setAfterTotalDiscount(orderItemDTO.getTotalDiscountAmount());
				itemPriceHistoryDTO.setAfterShopDiscount(orderItemDTO.getShopDiscountAmount());
				itemPriceHistoryDTO.setAfterPlatformDiscount(orderItemDTO.getPlatformDiscountAmount());
				itemPriceHistoryDTO.setAfterPaymentPrice(orderItemDTO.getOrderItemPayAmount());
				itemPriceHistoryDTO.setCreateId(negotiateOrderDTO.getOperatorId());
				itemPriceHistoryDTO.setCreateName(negotiateOrderDTO.getOperatorName());
				orderItemDTO.setItemPriceHistoryDTO(itemPriceHistoryDTO);
				hasNegotiatedFlg = true;
				// 新增订单行时
			} else {
				goodsCount = negotiateItemDTO.getBargainingGoodsCount();
				goodsCountDecimal = new BigDecimal(goodsCount);
				goodsPrice = negotiateItemDTO.getBargainingGoodsPrice();
				orderItemDTO = orderStockHandle.getItemSkuDetail(negotiateItemDTO.getSkuCode(),
						negotiateItemDTO.getIsBoxFlag(), negotiateItemDTO.getBargainingGoodsCount());
				if (!baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL,
						DictionaryConst.OPT_PRODUCT_CHANNEL_HTD).equals(orderItemDTO.getChannelCode())) {
					throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NONE_HTD_PRODUCT_ERROR,
							"订单编号:" + orderNo + " 第" + i + "行订单商品为商品+商品不能新增");
				}
				if (!canUnderFloorPrice && goodsPrice.compareTo(negotiateItemDTO.getCostPrice()) < 0) {
					throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_PRICE_TOO_LOW,
							"订单编号:" + orderNo + " 第" + i + "行订单商品议价价格不能小于分销限价");
				}
				tmpOrderItemNo = getValidOrderItemNo(orderNo, orderItemList);
				orderItemDTO.setOrderNo(orderNo);
				orderItemDTO.setOrderItemNo(tmpOrderItemNo);
				orderItemDTO.setGoodsCount(goodsCount);
				orderItemDTO.setGoodsPriceType(baseService.getDictValueByCode(dictMap,
						DictionaryConst.TYPE_SKU_PRICE_TYPE, DictionaryConst.OPT_SKU_PRICE_TYPE_COUSTOM));
				orderItemDTO.setGoodsPrice(orderItemDTO.getSalePrice());
				orderItemDTO.setCostPrice(negotiateItemDTO.getCostPrice());
				orderItemDTO.setBargainingGoodsCount(goodsCount);
				orderItemDTO.setBargainingGoodsPrice(goodsPrice);
				orderItemDTO.setGoodsAmount(CalculateUtils.multiply(orderItemDTO.getSalePrice(), goodsCountDecimal));
				orderItemDTO.setTotalDiscountAmount(BigDecimal.ZERO);
				orderItemDTO.setShopDiscountAmount(BigDecimal.ZERO);
				orderItemDTO.setPlatformDiscountAmount(BigDecimal.ZERO);
				orderItemDTO.setUsedRebateAmount(BigDecimal.ZERO);
				orderItemDTO.setBargainingGoodsAmount(CalculateUtils.multiply(goodsPrice, goodsCountDecimal));
				orderItemDTO.setBargainingGoodsFreight(orderItemDTO.getGoodsFreight());
				orderItemDTO.setOrderItemTotalAmount(CalculateUtils.add(orderItemDTO.getBargainingGoodsAmount(),
						orderItemDTO.getBargainingGoodsFreight()));
				orderItemDTO.setOrderItemPayAmount(orderItemDTO.getOrderItemTotalAmount());
				orderItemDTO.setIsAddOrderItem(YesNoEnum.YES.getValue());
				orderItemDTO.setIsCancelOrderItem(YesNoEnum.NO.getValue());
				orderItemDTO.setIsChangePrice(YesNoEnum.YES.getValue());
				orderItemDTO.setHasUsedCoupon(YesNoEnum.NO.getValue());
				orderItemDTO.setGoodsRealPrice(goodsPrice);
				orderItemDTO.setOrderItemStatus(orderDTO.getOrderStatus());
				orderItemDTO.setCreateId(negotiateOrderDTO.getOperatorId());
				orderItemDTO.setCreateName(negotiateOrderDTO.getOperatorName());
				orderItemDTO.setDealFlag("add");

				itemPriceHistoryDTO = new TradeOrderItemsPriceHistoryDTO();
				itemPriceHistoryDTO.setOrderNo(orderNo);
				itemPriceHistoryDTO.setOrderItemNo(tmpOrderItemNo);
				itemPriceHistoryDTO.setChannelCode(orderItemDTO.getChannelCode());
				itemPriceHistoryDTO.setItemCode(orderItemDTO.getItemCode());
				itemPriceHistoryDTO.setSkuCode(orderItemDTO.getSkuCode());
				itemPriceHistoryDTO.setBeforeBargainingGoodsPrice(orderItemDTO.getGoodsPrice());
				itemPriceHistoryDTO.setBeforeBargainingGoodsCount(orderItemDTO.getGoodsCount());
				itemPriceHistoryDTO.setBeforeTotalPrice(orderItemDTO.getGoodsAmount());
				itemPriceHistoryDTO.setBeforeFreight(orderItemDTO.getGoodsFreight());
				itemPriceHistoryDTO.setBeforeTotalDiscount(orderItemDTO.getTotalDiscountAmount());
				itemPriceHistoryDTO.setBeforeShopDiscount(orderItemDTO.getShopDiscountAmount());
				itemPriceHistoryDTO.setBeforePlatformDiscount(orderItemDTO.getPlatformDiscountAmount());
				itemPriceHistoryDTO.setBeforePaymentPrice(
						CalculateUtils.add(orderItemDTO.getGoodsAmount(), orderItemDTO.getGoodsFreight()));
				itemPriceHistoryDTO.setAfterBargainingGoodsPrice(orderItemDTO.getBargainingGoodsPrice());
				itemPriceHistoryDTO.setAfterBargainingGoodsCount(orderItemDTO.getBargainingGoodsCount());
				itemPriceHistoryDTO.setAfterTotalPrice(orderItemDTO.getBargainingGoodsAmount());
				itemPriceHistoryDTO.setAfterFreight(orderItemDTO.getBargainingGoodsFreight());
				itemPriceHistoryDTO.setAfterTotalDiscount(orderItemDTO.getTotalDiscountAmount());
				itemPriceHistoryDTO.setAfterShopDiscount(orderItemDTO.getShopDiscountAmount());
				itemPriceHistoryDTO.setAfterPlatformDiscount(orderItemDTO.getPlatformDiscountAmount());
				itemPriceHistoryDTO.setAfterPaymentPrice(orderItemDTO.getOrderItemPayAmount());
				itemPriceHistoryDTO.setCreateId(negotiateOrderDTO.getOperatorId());
				itemPriceHistoryDTO.setCreateName(negotiateOrderDTO.getOperatorName());
				orderItemDTO.setItemPriceHistoryDTO(itemPriceHistoryDTO);

				itemStatusHistoryDTO = new TradeOrderItemsStatusHistoryDTO();
				itemStatusHistoryDTO.setOrderItemNo(tmpOrderItemNo);
				itemStatusHistoryDTO.setOrderItemStatus(orderItemDTO.getOrderItemStatus());
				itemStatusHistoryDTO.setOrderItemStatusText(baseService.setOrderShowStatus(dictMap,
						orderItemDTO.getOrderItemStatus(), "", YesNoEnum.NO.getValue(), ""));
				itemStatusHistoryDTO.setCreateId(negotiateOrderDTO.getOperatorId());
				itemStatusHistoryDTO.setCreateName(negotiateOrderDTO.getOperatorName());
				itemStatusHistoryDTOList.add(itemStatusHistoryDTO);
				orderItemDTO.setItemStatusHistoryDTOList(itemStatusHistoryDTOList);
				orderItemList.add(orderItemDTO);
				hasNegotiatedFlg = true;
			}
		}
		if (!hasNegotiatedFlg) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NEGOTIATION_NO_CHANGE,
					"订单编号:" + orderNo + " 对于订单商品没有议价或者议价前后价格和数量完全一致");
		}
		orderDTO.setOrderItemList(orderItemList);
		return orderDTO;
	}

	/**
	 * 取得有效的订单行号
	 *
	 * @param orderNo
	 * @param orderItemList
	 * @return
	 */
	private String getValidOrderItemNo(String orderNo, List<TradeOrderItemsDTO> orderItemList) {
		String orderItemNo = baseService.getOrderItemNo(orderNo);
		String tmpOrderItemNo = "";
		Map<String, String> itemMap = new HashMap<String, String>();
		int count = 0;
		if (orderItemList != null && !orderItemList.isEmpty()) {
			if (orderItemList.size() >= 100) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_COUNT_OVER_LIMIT, "订单行条数超过100上限");
			}
			for (TradeOrderItemsDTO orderItemDTO : orderItemList) {
				tmpOrderItemNo = orderItemDTO.getOrderItemNo();
				itemMap.put(tmpOrderItemNo, tmpOrderItemNo);
			}
			while (count < 100) {
				if (!itemMap.containsKey(orderItemNo)) {
					break;
				}
				orderItemNo = baseService.getOrderItemNo(orderNo);
				count++;
			}
		}
		return orderItemNo;
	}

	/**
	 * 待审核／待支付订单校验数据的合法性
	 *
	 * @param dictMap
	 * @param orderDTO
	 * @param verifyStatusList
	 * @return
	 * @throws TradeCenterBusinessException
	 * @throws Exception
	 */
	private void checkApproveOrderInfo(Map<String, DictionaryInfo> dictMap, TradeOrdersDTO orderDTO,
			List<String> verifyStatusList) throws TradeCenterBusinessException, Exception {
		String orderNo = "";
		List<TradeOrderItemsDTO> orderItemList = null;
		String statusName = "";
		Set<String> statusNameSet = new HashSet<String>();
		boolean hasItemFlag = false;
		boolean hasCorrectItemFlag = false;
		String minOrderStatus = "";
		String maxOrderStatus = "";

		for (String verifyStatus : verifyStatusList) {
			if (StringUtils.isEmpty(minOrderStatus)) {
				minOrderStatus = verifyStatus;
			}
			if (StringUtils.isEmpty(maxOrderStatus)) {
				maxOrderStatus = verifyStatus;
			}
			if (verifyStatus.compareTo(minOrderStatus) < 0) {
				minOrderStatus = verifyStatus;
			}
			if (verifyStatus.compareTo(maxOrderStatus) > 0) {
				maxOrderStatus = verifyStatus;
			}
			statusNameSet.add(baseService.getDictNameByValue(dictMap, DictionaryConst.TYPE_ORDER_STATUS, verifyStatus));
		}
		statusName = StringUtils.join(statusNameSet.toArray(), ",");

		if (orderDTO == null) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_EXIST, "订单编号:" + orderNo + " 的订单数据不存在");
		}
		orderNo = orderDTO.getOrderNo();
		if (YesNoEnum.YES.getValue() == orderDTO.getIsCancelOrder()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_CLOSED, "订单编号:" + orderNo + " 的订单已关闭");
		}
		if (!StringUtil.isEmpty(orderDTO.getOrderErrorStatus())) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ERROR_STATUS,
					"订单编号:" + orderNo + " 为异常订单 " + baseService.getDictNameByValue(dictMap,
							DictionaryConst.TYPE_ORDER_STATUS, orderDTO.getOrderErrorStatus()));
		}
		if (!verifyStatusList.contains(orderDTO.getOrderStatus())) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_VERIFY_PAY_PENDING,
					"订单编号:" + orderNo + " 不是" + statusName + "订单");
		}
		orderItemList = orderDTO.getOrderItemList();
		if (orderItemList == null || orderItemList.isEmpty()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NOT_EXIST,
					"订单编号:" + orderNo + " 的订单行数据不存在");
		}
		for (TradeOrderItemsDTO orderItemDTO : orderItemList) {
			if (YesNoEnum.NO.getValue() != orderItemDTO.getIsCancelOrderItem()) {
				continue;
			}
			hasItemFlag = true;
			if (!StringUtil.isEmpty(orderItemDTO.getOrderItemErrorStatus())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ERROR_STATUS,
						"订单编号:" + orderNo + " 订单行编号:" + orderItemDTO.getOrderItemNo() + " 为"
								+ baseService.getDictDescribeByValue(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
										orderItemDTO.getOrderItemErrorStatus()));
			}
			if (orderItemDTO.getOrderItemStatus().compareTo(minOrderStatus) < 0) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_VERIFY_PAY_PENDING,
						"订单编号:" + orderNo + " 订单行编号:" + orderItemDTO.getOrderItemNo() + " 不是" + statusName + "订单");
			}
			if (orderItemDTO.getOrderItemStatus().compareTo(minOrderStatus) >= 0
					&& orderItemDTO.getOrderItemStatus().compareTo(maxOrderStatus) <= 0) {
				hasCorrectItemFlag = true;
			}
		}
		if (!hasItemFlag) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NOT_EXIST,
					"订单编号:" + orderNo + " 的订单行数据不存在");
		}
		if (!hasCorrectItemFlag) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_VERIFY_PAY_PENDING,
					"订单编号:" + orderNo + " 不是" + statusName + "订单");
		}
	}

	/**
	 * 校验VMS议价的对象
	 *
	 * @param negotiateOrderDTO
	 * @throws TradeCenterBusinessException
	 */
	private void checkNegotiateOrderParameter(VenusNegotiateTradeOrderDTO negotiateOrderDTO)
			throws TradeCenterBusinessException {
		List<VenusNegotiateTradeOrderItemDTO> negotiateItemList = negotiateOrderDTO.getOrderItemList();
		String orderItemNo = "";
		int i = 0;
		String msg = "";
		boolean hasNegotiateFlg = false;
		if (negotiateItemList == null || negotiateItemList.isEmpty()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "订单必须至少有一条订单行数据");
		}
		for (VenusNegotiateTradeOrderItemDTO itemDTO : negotiateItemList) {
			i++;
			orderItemNo = itemDTO.getOrderItemNo();
			if (StringUtils.isEmpty(orderItemNo)) {
				msg = "第" + i + "行新增商品";
				if (StringUtils.isEmpty(itemDTO.getSkuCode())) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "需要提供商品ID数据");
				}
				if (StringUtils.isEmpty(YesNoEnum.getName(itemDTO.getIsBoxFlag()))) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "需要选择商品上架类型");
				}
				if (itemDTO.getBargainingGoodsCount() == null || itemDTO.getBargainingGoodsCount() <= 0) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "需要输入大于0的数量");
				}
				if (itemDTO.getBargainingGoodsPrice() == null) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "需要输入商品价格");
				}
				if (BigDecimal.ZERO.compareTo(itemDTO.getBargainingGoodsPrice()) >= 0) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "价格必须大于0");
				}
				if (itemDTO.getCostPrice() == null) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "商品没有分销限价");
				}
				if (BigDecimal.ZERO.compareTo(itemDTO.getCostPrice()) >= 0) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "分销限价必须大于0");
				}
			} else {
				msg = "第" + i + "行议价商品";
				if (StringUtils.isEmpty(itemDTO.getModifyTimeStr())) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "记录必须更新时间");
				}
				// 没有议价时跳过此条数据
				if ((itemDTO.getBargainingGoodsCount() == null || itemDTO.getBargainingGoodsCount() == 0)
						&& (itemDTO.getBargainingGoodsPrice() == null
								|| BigDecimal.ZERO.compareTo(itemDTO.getBargainingGoodsPrice()) >= 0)) {
					continue;
				}
				if (itemDTO.getBargainingGoodsCount() != null && itemDTO.getBargainingGoodsCount() <= 0) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "数量必须大于0");
				}
				if (itemDTO.getBargainingGoodsPrice() != null
						&& BigDecimal.ZERO.compareTo(itemDTO.getBargainingGoodsPrice()) >= 0) {
					throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, msg + "价格必须大于0");
				}
			}
			hasNegotiateFlg = true;
		}
		if (!hasNegotiateFlg) {
			throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "订单商品中既没有新增商品也没有商品议价，请重新确认！");
		}
	}

	@Override
	public ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> queryTradeOrderErrorListByCondition(
			TradeOrdersQueryInDTO inDTO, Pager<TradeOrdersQueryInDTO> pager) {
		ExecuteResult<DataGrid<TradeOrderItemsShowDTO>> result = new ExecuteResult<DataGrid<TradeOrderItemsShowDTO>>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		DataGrid<TradeOrderItemsShowDTO> dataGrid = new DataGrid<TradeOrderItemsShowDTO>();
		List<TradeOrderItemsDTO> dataList = null;
		List<TradeOrderItemsShowDTO> dataGridList = new ArrayList<TradeOrderItemsShowDTO>();
		long count = 0;
		baseService.setOrderErrorStatusCondition(dictMap, inDTO);
		baseService.setProductChannelCondition(dictMap, inDTO);
		try {
			count = orderItemsDAO.queryTradeOrderCountByCondition(inDTO);
			if (count > 0) {
				dataList = orderItemsDAO.queryErpErrorTradeOrderListByCondition(inDTO, pager);
				for (TradeOrderItemsDTO itemDTO : dataList) {
					dataGridList.add(baseService.exchangeTradeOrderItemsDTO2Show(dictMap, itemDTO));
				}
				dataGrid.setRows(dataGridList);
			}
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> redoErpDownWork(RedoErpWorkDTO workDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		TradeOrdersDTO orderDTO = null;
		TradeOrderItemsDTO condition = new TradeOrderItemsDTO();
		TradeOrderItemsDTO orderItemDTO = null;
		String orderNo = workDTO.getOrderNo();
		String orderItemNo = workDTO.getOrderItemNo();
		String orderModifyTime = "";
		String postPendingStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_PENDING);
		String erpPendingStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_ERP_DOWNING);
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(workDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
			}
			orderDTO = orderDAO.queryTradeOrderByOrderNo(orderNo);
			if (orderDTO == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_EXIST, "订单编号:" + orderNo + "的订单信息不存在");
			}
			if (YesNoEnum.NO.getValue() != orderDTO.getIsCancelOrder()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_CANCELED,
						"订单编号:" + orderNo + "的订单已取消");
			}
			if (!postPendingStatus.equals(orderDTO.getOrderStatus())
					&& !erpPendingStatus.equals(orderDTO.getOrderStatus())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_ERP_PENDING,
						"订单编号:" + orderNo + "的订单状态不正确");
			}
			orderModifyTime = DateUtils.format(orderDTO.getModifyTime(), DateUtils.YMDHMS);
			if (!orderModifyTime.equals(workDTO.getModifyTimeStr())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_MODIFIED,
						"订单编号:" + orderNo + "的订单已被修改请重新确认");
			}
			condition.setOrderNo(orderNo);
			condition.setOrderItemNo(orderItemNo);
			orderItemDTO = orderItemsDAO.queryTradeOrderItemsByOrderItemNo(condition);
			if (orderItemDTO == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NOT_EXIST,
						"订单编号:" + orderNo + " 订单行编号:" + orderItemNo + "的订单行信息不存在");
			}
			if (YesNoEnum.NO.getValue() != orderItemDTO.getIsCancelOrderItem()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_HAS_CANCELED,
						"订单编号:" + orderNo + " 订单行编号:" + orderItemNo + "的订单行已经被取消请重新确认");
			}

			if (!postPendingStatus.equals(orderItemDTO.getOrderItemStatus())
					&& !erpPendingStatus.equals(orderItemDTO.getOrderItemStatus())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NOT_ERP_PENDING,
						"订单编号:" + orderNo + " 订单行编号:" + orderItemNo
								+ "的订单状态为" + baseService.getDictDescribeByValue(dictMap,
										DictionaryConst.TYPE_ORDER_STATUS, orderItemDTO.getOrderItemStatus())
						+ " 不能再次处理");
			}
			orderDBHandle.saveRedoErpDownTradeOrderInfo(workDTO, orderDTO, orderItemDTO);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	@Transactional
	public ExecuteResult<TradeOrdersDTO> confirmVenusOrderByMember(TradeOrderConfirmDTO tradeOrderConfirmDTO) {
		ExecuteResult<TradeOrdersDTO> result = new ExecuteResult<TradeOrdersDTO>();
		TradeOrdersDTO tradeOrderDTO = null;
		String orderNo = tradeOrderConfirmDTO.getOrderNo();
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(tradeOrderConfirmDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
			}
			tradeOrderDTO = orderDBHandle.queryTradeOrdersByOrderNo(orderNo, false);
			if (tradeOrderDTO == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_NOT_EXIST, "订单编号:" + orderNo + "的订单不存在");
			}
			// 校验当前订单是否符合确认订单的要求
			checkOrderInfo4Confirm(tradeOrderConfirmDTO, tradeOrderDTO);
			// 更新订单状态，ERP状态以及记录日志信息
			updateOrderStatus4Confirm(tradeOrderConfirmDTO, tradeOrderDTO);
		} catch (TradeCenterBusinessException tcbe) {
			result.setCode(tcbe.getCode());
			result.addErrorMessage(tcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	private void checkOrderInfo4Confirm(TradeOrderConfirmDTO tradeOrderConfirmDTO, TradeOrdersDTO tradeOrderDTO) {
		String orderStatus = tradeOrderDTO.getOrderStatus();
		String orderNo = tradeOrderDTO.getOrderNo();
		Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
		String orderModifyTime = DateUtils.format(tradeOrderDTO.getModifyTime(), DateUtils.YMDHMS);
		String confirmStatus = baseService.getDictValueByCode(dictMap, DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_WAIT_CONFIRM);
		if (!confirmStatus.equals(orderStatus)) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_STATUS_CANNOT_DISTRIBUTE,
					"订单编号:" + orderNo + "的订单状态为" + baseService.getDictNameByValue(dictMap,
							DictionaryConst.TYPE_ORDER_STATUS, tradeOrderDTO.getOrderStatus()) + "不能拆单");
		}
		if (YesNoEnum.YES.getValue() == tradeOrderDTO.getIsCancelOrder()) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_CANCELED, "订单编号:" + orderNo + "的订单已被取消");
		}
		if (StringUtils.isNotBlank(tradeOrderDTO.getOrderErrorStatus())) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ERROR_STATUS_CANNOT_DISTRIBUTE,
					"订单编号:" + orderNo + "的异常状态:" + baseService.getDictNameByValue(dictMap,
							DictionaryConst.TYPE_ORDER_STATUS, tradeOrderDTO.getOrderErrorStatus()) + "不能拆单");
		}
		if (!orderModifyTime.equals(tradeOrderConfirmDTO.getModifyTimeStr())) {
			throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_HAS_MODIFIED,
					"订单编号:" + orderNo + "的订单已被修改请重新确认");
		}
	}

	public void updateOrderStatus4Confirm(TradeOrderConfirmDTO tradeOrderConfirmDTO, TradeOrdersDTO tradeOrdersDTO)
			throws Exception {
		String orderNo = tradeOrdersDTO.getOrderNo();
		TradeOrdersDTO tradeOrderDTO = new TradeOrdersDTO();
		tradeOrderDTO.setOrderNo(orderNo);
		tradeOrderDTO.setOrderStatus(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
		tradeOrderDTO.setModifyId(tradeOrderConfirmDTO.getOperatorId());
		tradeOrderDTO.setModifyName(tradeOrderConfirmDTO.getOperatorName());
		tradeOrderDTO.setModifyTime(DateUtils.getSystemTime());
		tradeOrderDTO.setConfirmTime(DateUtils.getSystemTime());
		orderDAO.updateTradeOrdersStatusInfo(tradeOrderDTO);
		TradeOrderStatusHistoryDTO orderStatusHistoryDTO = new TradeOrderStatusHistoryDTO();
		orderStatusHistoryDTO.setOrderNo(orderNo);
		orderStatusHistoryDTO.setOrderStatus(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
		orderStatusHistoryDTO.setOrderStatusText(dictionary.getNameByCode(DictionaryConst.TYPE_ORDER_STATUS,
				DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
		orderStatusHistoryDTO.setCreateId(tradeOrderConfirmDTO.getOperatorId());
		orderStatusHistoryDTO.setCreateName(tradeOrderConfirmDTO.getOperatorName());
		orderStatusHistoryDAO.addTradeOrderStatusHistory(orderStatusHistoryDTO);
		List<TradeOrderErpDistributionDTO> erpDistributionDTOList = erpDistributionDAO
				.queryOrderErpDistributionByOrderNo(orderNo);
		for (TradeOrderErpDistributionDTO erpDistribution : erpDistributionDTOList) {
			TradeOrderErpDistributionDTO erpDTO = new TradeOrderErpDistributionDTO();
			erpDTO.setId(erpDistribution.getId());
			erpDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
			erpDTO.setModifyId(tradeOrderConfirmDTO.getOperatorId());
			erpDTO.setModifyName(tradeOrderConfirmDTO.getOperatorName());
			erpDistributionDAO.updateTradeOrdersErpDistributionStatusInfo(erpDistribution);
		}

	}

}
