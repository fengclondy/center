package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.goodscenter.dto.mall.MallSkuInDTO;
import cn.htd.goodscenter.dto.mall.MallSkuOutDTO;
import cn.htd.goodscenter.dto.mall.MallSkuStockOutDTO;
import cn.htd.goodscenter.dto.mall.MallSkuWithStockOutDTO;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.dto.TradeInfoDTO;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeInfoDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.pricecenter.dto.HzgPriceDTO;
import cn.htd.pricecenter.dto.OrderItemSkuPriceDTO;
import cn.htd.pricecenter.dto.QueryCommonItemSkuPriceDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.zeus.tc.biz.dao.PayOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDiscountDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.ChargeConditionInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderCreateInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderCreateListInfoDMO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDiscountDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.rao.GoodsCenterRAO;
import cn.htd.zeus.tc.biz.rao.GoodsPlusRAO;
import cn.htd.zeus.tc.biz.rao.MarketCenterRAO;
import cn.htd.zeus.tc.biz.rao.MemberCenterRAO;
import cn.htd.zeus.tc.biz.rao.PriceCenterRAO;
import cn.htd.zeus.tc.biz.rao.StoreCenterRAO;
import cn.htd.zeus.tc.biz.service.JDCreateOrderService;
import cn.htd.zeus.tc.biz.service.OrderCreateService;
import cn.htd.zeus.tc.biz.service.OrderFreightInfoService;
import cn.htd.zeus.tc.biz.service.TradeOrderItemStatusHistoryService;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.biz.util.ExternalSupplierCostCaculateUtil;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.GoodCenterEnum;
import cn.htd.zeus.tc.common.enums.MemberCenterEnum;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.PayStatusEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.goodplus.JDConfig;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.OrderItemCouponDTO;
import cn.htd.zeus.tc.dto.TimelimitedInfoDTO;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.OrderCreateInfoResDTO;
import cn.htd.zeus.tc.dto.response.OrderCreateListInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.BatchGetStockReqDTO;
import cn.htd.zeus.tc.dto.resquest.BatchGetStockSkuNumsReqDTO;
import cn.htd.zeus.tc.dto.resquest.JDCreateOrderReqDTO;
import cn.htd.zeus.tc.dto.resquest.JDCreateOrderSkuReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreate4huilinReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateOrderListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateSkuListInfoReqDTO;

import com.alibaba.fastjson.JSONObject;

/*
 * 该类目前只支持有优惠券下单、无优惠券下单、内部供应商下单、外接渠道下单、秒杀、外部供应商的下单
 */
@Service
public class OrderCreateServiceImpl implements OrderCreateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreateServiceImpl.class);

	/*
	 * 锁定优惠券
	 */
	@Autowired
	private MarketCenterRAO marketCenterRAO;

	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;

	@Autowired
	private TradeOrdersDAO tradeOrdersDAO;

	@Autowired
	private TradeOrderItemsDiscountDAO tradeOrderItemsDiscountDAO;

	@Autowired
	private PayOrderInfoDAO payOrderInfoDAO;

	@Autowired
	private MemberCenterRAO memberCenterRAO;

	@Autowired
	private GoodsCenterRAO goodsCenterRAO;

	@Autowired
	private PriceCenterRAO priceCenterRAO;

	@Autowired
	private GoodsPlusRAO goodsPlusRAO;

	@Autowired
	private StoreCenterRAO storeCenterRAO;

	private static final int ZERO = 0;// 常量0

	private static final int ONE = 1;// 常量1

	private static final byte ORDER_TYPE = 1;// 普通订单

	private static final String IS_NEED_BATCH_RESERVE_STOCK_KEY = "isNeedBatchReserveStock";// 是否需要释放商品和优惠券库存key

	@Autowired
	private TradeOrderStatusHistoryService tradeOrderStatusHistoryService;

	@Autowired
	private TradeOrderItemStatusHistoryService tradeOrderItemStatusHistoryService;

	@Autowired
	private OrderFreightInfoService orderFreightInfoService;

	@Autowired
	private JDConfig jdConfig;

	@Autowired
	private JDCreateOrderService jdCreateOrderService;

	DecimalFormat df1 = new DecimalFormat("0.0000");

	/*
	 * 创建订单业务逻辑
	 * 
	 * @param OrderCreateInfoReqDTO
	 * 
	 * @return OrderCreateInfoDMO
	 */
	@Override
	@Transactional
	public OrderCreateInfoDMO orderCreate(OrderCreateInfoReqDTO orderCreateInfoReqDTO) {
		OrderCreateInfoDMO orderCreateInfoDMO = new OrderCreateInfoDMO();
		String couponCode = orderCreateInfoReqDTO.getCouponCode();
		if (StringUtilHelper.isNotNull(couponCode)
				&& StringUtilHelper.isNull(orderCreateInfoReqDTO.getPromotionType())) {
			orderCreateInfoReqDTO.setPromotionType(OrderStatusEnum.PROMOTION_TYPE_COUPON.getCode());
		}
		String promotionType = orderCreateInfoReqDTO.getPromotionType();
		List<OrderItemPromotionDTO> orderItemPromotionList = new ArrayList<OrderItemPromotionDTO>();// 锁定优惠券对象
		List<Order4StockChangeDTO> order4StockChangeDTOs = new ArrayList<Order4StockChangeDTO>();// 锁商品库存创建对象

		// 判断是不是要去商品中心锁库存
		Map<String, String> isNeedBatchReserveStockMap = new HashMap<String, String>();
		isNeedBatchReserveStockMap.put(IS_NEED_BATCH_RESERVE_STOCK_KEY,
				OrderStatusEnum.ORDER_NOT_RELEASE_STOCK.getCode());

		try {
			List<OrderCreateListInfoReqDTO> orderList = orderCreateInfoReqDTO.getOrderList();
			if (null != orderList && orderList.size() > 0) {
				// 调用 memberCallCenterService查询会员信息
				String buyerCode = orderCreateInfoReqDTO.getBuyerCode();
				OtherCenterResDTO<MemberBaseInfoDTO> memberBaseInfoResDTO = memberCenterRAO
						.selectMemberBaseName(buyerCode,
								MemberCenterEnum.MEMBER_TYPE_BUYER.getCode(),
								orderCreateInfoReqDTO.getMessageId());
				if (!ResultCodeEnum.SUCCESS.getCode()
						.equals(memberBaseInfoResDTO.getOtherCenterResponseCode())) {
					orderCreateInfoDMO
							.setResultCode(memberBaseInfoResDTO.getOtherCenterResponseCode());
					orderCreateInfoDMO
							.setResultMsg(memberBaseInfoResDTO.getOtherCenterResponseMsg());
					return orderCreateInfoDMO;
				}
				MemberBaseInfoDTO buyerInfoDTO = memberBaseInfoResDTO.getOtherCenterResult();
				orderCreateInfoReqDTO.setBuyerName(buyerInfoDTO.getCompanyName());
				orderCreateInfoReqDTO.setBuyerType(buyerInfoDTO.getMemberType());
				orderCreateInfoReqDTO.setBuyerGrade(buyerInfoDTO.getBuyerGrade());

				// 执行预处理逻辑
				OrderCreateInfoDMO handleRes = handleReqDTO(orderCreateInfoReqDTO,
						orderCreateInfoDMO, order4StockChangeDTOs, isNeedBatchReserveStockMap);
				if (!ResultCodeEnum.SUCCESS.getCode().equals(handleRes.getResultCode())) {
					orderCreateInfoDMO.setResultCode(handleRes.getResultCode());
					orderCreateInfoDMO.setResultMsg(handleRes.getResultMsg());
					return orderCreateInfoDMO;
				}

				// 如果会员选择用券或者秒杀
				if (StringUtilHelper.isNotNull(promotionType)) {

					orderCreateInfoReqDTO = usePromotion(orderCreateInfoReqDTO, orderCreateInfoDMO,
							order4StockChangeDTOs, isNeedBatchReserveStockMap,
							orderItemPromotionList, buyerInfoDTO);
					if (!ResultCodeEnum.SUCCESS.getCode()
							.equals(orderCreateInfoDMO.getResultCode())) {
						return orderCreateInfoDMO;
					}
				}
				orderCreateInfoDMO = preInsertOrderAndOrderItem(buyerInfoDTO, orderCreateInfoReqDTO,
						orderCreateInfoDMO);
			}
		} catch (Exception e) {
			orderCreateInfoDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderCreateInfoDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderCreateServiceImpl.orderCreate出现异常{}",
					orderCreateInfoReqDTO.getMessageId(), w.toString());
		} finally {
			if (orderCreateInfoDMO.getResultCode()
					.equals(ResultCodeEnum.ORDER_CREATE_ORDER_ROLLBACK_BATCHRELEASE.getCode())) {
				if (StringUtilHelper.isNotNull(promotionType)) {
					// 释放商品和优惠券库存
					batchReleaseGoodsAndMarketStock(order4StockChangeDTOs, orderItemPromotionList,
							promotionType, isNeedBatchReserveStockMap,
							orderCreateInfoReqDTO.getMessageId());
				} else {
					// 释放商品库存
					batchReleaseGoodsAndMarketStock(order4StockChangeDTOs, null, null,
							isNeedBatchReserveStockMap, orderCreateInfoReqDTO.getMessageId());
				}
			}
		}

		return orderCreateInfoDMO;
	}

	/*
	 * 使用优惠券或者秒杀--组装参数 java 是指传递 所以返回orderCreateInfoReqDTO orderCreateInfoReqDTO
	 * = JSONObject.toJavaObject(jsonObjRes,OrderCreateInfoReqDTO.class);
	 */
	private OrderCreateInfoReqDTO usePromotion(OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateInfoDMO orderCreateInfoDMO, List<Order4StockChangeDTO> order4StockChangeDTOs,
			Map<String, String> isNeedBatchReserveStockMap,
			List<OrderItemPromotionDTO> orderItemPromotionList, MemberBaseInfoDTO buyerInfoDTO) {
		orderCreateInfoDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		JSONObject jsonObj = (JSONObject) JSONObject.toJSON(orderCreateInfoReqDTO);
		List<String> couponCodeList = new ArrayList<String>();
		couponCodeList.add(orderCreateInfoReqDTO.getCouponCode());
		TradeInfoDTO cart = JSONObject.toJavaObject(jsonObj, TradeInfoDTO.class);
		OtherCenterResDTO<TradeInfoDTO> calculateRes = marketCenterRAO.calculateCouponDiscount(
				couponCodeList, cart, orderCreateInfoReqDTO.getMessageId());
		if (!ResultCodeEnum.SUCCESS.getCode().equals(calculateRes.getOtherCenterResponseCode())) {
			orderCreateInfoDMO.setResultCode(calculateRes.getOtherCenterResponseCode());
			orderCreateInfoDMO.setResultMsg(calculateRes.getOtherCenterResponseMsg());
			// 释放商品库存
			batchReleaseGoodsAndMarketStock(order4StockChangeDTOs, null, null,
					isNeedBatchReserveStockMap, orderCreateInfoReqDTO.getMessageId());
			return orderCreateInfoReqDTO;
		}
		String messageId = orderCreateInfoReqDTO.getMessageId();
		TradeInfoDTO tradeInfoDTO = calculateRes.getOtherCenterResult();
		JSONObject jsonObjRes = (JSONObject) JSONObject.toJSON(tradeInfoDTO);
		orderCreateInfoReqDTO = JSONObject.toJavaObject(jsonObjRes, OrderCreateInfoReqDTO.class);
		orderCreateInfoReqDTO.setMessageId(messageId);
		// 促销中心锁定库存
		preLockCoupon(orderCreateInfoReqDTO, orderItemPromotionList, buyerInfoDTO);
		OtherCenterResDTO<String> reserveBuyerPromotionRes = marketCenterRAO.reserveBuyerPromotion(
				orderItemPromotionList, orderCreateInfoReqDTO.getMessageId());
		if (!ResultCodeEnum.SUCCESS.getCode()
				.equals(reserveBuyerPromotionRes.getOtherCenterResponseCode())) {
			// 释放商品库存
			batchReleaseGoodsAndMarketStock(order4StockChangeDTOs, null, null,
					isNeedBatchReserveStockMap, orderCreateInfoReqDTO.getMessageId());
			orderCreateInfoDMO.setResultCode(reserveBuyerPromotionRes.getOtherCenterResponseCode());
			orderCreateInfoDMO.setResultMsg(reserveBuyerPromotionRes.getOtherCenterResponseMsg());
			return orderCreateInfoReqDTO;
		}
		return orderCreateInfoReqDTO;
	}

	/*
	 * 处理页面DTO，然后请求促销中心，增加品牌品类等信息
	 * 
	 */
	private OrderCreateInfoDMO handleReqDTO(OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateInfoDMO orderCreateInfoDMO, List<Order4StockChangeDTO> order4StockChangeDTOs,
			Map<String, String> isNeedBatchReserveStockMap) {
		orderCreateInfoDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		List<OrderCreateListInfoReqDTO> orderList = orderCreateInfoReqDTO.getOrderList();

		// 秒杀商品不需要锁定商品中心商品,也不需要校验商品库存
		String promotionType = orderCreateInfoReqDTO.getPromotionType() == null ? ""
				: orderCreateInfoReqDTO.getPromotionType();

		// 汇通达在京东备用金
		OtherCenterResDTO<String> accountAmount4JDRes = new OtherCenterResDTO<String>();
		BigDecimal JDAcountAmt = new BigDecimal(0);
		for (int i = 0; i < orderList.size(); i++) {
			OrderCreateListInfoReqDTO orderTemp = orderList.get(i);
			List<OrderCreateItemListInfoReqDTO> orderItemList = orderTemp.getOrderItemList();
			BatchGetStockReqDTO batchGetStockReqDTO = new BatchGetStockReqDTO();
			List<BatchGetStockSkuNumsReqDTO> skuNums = new ArrayList<>();

			Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();// 锁商品库存创建对象2
			order4StockChangeDTOs.add(order4StockChangeDTO);
			order4StockChangeDTO.setOrderNo(orderTemp.getOrderNo());
			order4StockChangeDTO.setOrderResource(orderTemp.getOrderFrom());
			order4StockChangeDTO.setMessageId(orderCreateInfoReqDTO.getMessageId());
			List<Order4StockEntryDTO> orderEntries = new ArrayList<Order4StockEntryDTO>();// 锁商品库存创建对象3
			order4StockChangeDTO.setOrderEntries(orderEntries);
			// 外部供应商标识
			boolean externalSupplierFlag = false;

			// 创建京东订单 --1
			JDCreateOrderReqDTO jdCreateOrderReqDTO = new JDCreateOrderReqDTO();
			List<JDCreateOrderSkuReqDTO> skuList = new ArrayList<JDCreateOrderSkuReqDTO>();
			jdCreateOrderReqDTO.setSku(skuList);

			if (null != orderItemList && orderItemList.size() > 0) {
				for (int j = 0; j < orderItemList.size(); j++) {
					OrderCreateItemListInfoReqDTO orderItemTemp = orderItemList.get(j);
					String site = orderCreateInfoReqDTO.getSite();
					OtherCenterResDTO<MallSkuWithStockOutDTO> mallSkuWithStockOutResDTO = goodsCenterRAO
							.queryMallItemDetailWithStock(orderItemTemp, site,
									orderCreateInfoReqDTO.getMessageId());
					if (!ResultCodeEnum.SUCCESS.getCode()
							.equals(mallSkuWithStockOutResDTO.getOtherCenterResponseCode())) {
						orderCreateInfoDMO.setResultCode(
								mallSkuWithStockOutResDTO.getOtherCenterResponseCode());
						orderCreateInfoDMO.setResultMsg(
								mallSkuWithStockOutResDTO.getOtherCenterResponseMsg());
						return orderCreateInfoDMO;
					}
					MallSkuOutDTO mallSkuOutDTO = mallSkuWithStockOutResDTO.getOtherCenterResult()
							.getMallSkuOutDTO();
					MallSkuStockOutDTO mallSkuStockOutDTO = mallSkuWithStockOutResDTO
							.getOtherCenterResult().getMallSkuStockOutDTO();

					orderItemTemp.setSkuId(mallSkuOutDTO.getSkuId());
					orderItemTemp.setChannelCode(mallSkuOutDTO.getProductChannelCode());

					OrderItemSkuPriceDTO orderItemSkuPriceDTO = null;
					Map<String, String> buyerGradeMap = new HashMap<String, String>();// 卖家等级
					// 如果是秒杀就不查价格中心
					if (promotionType.equals(OrderStatusEnum.PROMOTION_TYPE_SECKILL.getCode())) {
						orderItemSkuPriceDTO = new OrderItemSkuPriceDTO();
					} else {

						if (null != mallSkuStockOutDTO.getIsUpShelf()
								&& mallSkuStockOutDTO.getIsUpShelf() == 0) {
							orderCreateInfoDMO.setResultCode(
									ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_DOWN_SHELF.getCode());
							orderCreateInfoDMO.setResultMsg(
									ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_DOWN_SHELF.getMsg());
							return orderCreateInfoDMO;
						}

						// 去价格中心查询商品价格
						OtherCenterResDTO<OrderItemSkuPriceDTO> commonItemSkuPriceResDTO = queryItemSkuPrice(
								mallSkuOutDTO, orderCreateInfoReqDTO, orderTemp, orderItemTemp,
								buyerGradeMap, orderCreateInfoReqDTO.getMessageId());
						if (!ResultCodeEnum.SUCCESS.getCode()
								.equals(commonItemSkuPriceResDTO.getOtherCenterResponseCode())) {
							orderCreateInfoDMO.setResultCode(
									commonItemSkuPriceResDTO.getOtherCenterResponseCode());
							orderCreateInfoDMO.setResultMsg(
									commonItemSkuPriceResDTO.getOtherCenterResponseMsg());
							return orderCreateInfoDMO;
						}
						orderItemSkuPriceDTO = commonItemSkuPriceResDTO.getOtherCenterResult();
					}

					orderItemTemp.setGoodsPrice(orderItemSkuPriceDTO.getGoodsPrice() == null
							? new BigDecimal(0) : orderItemSkuPriceDTO.getGoodsPrice());
					// 判断校验内部供应商、外接渠道商品、外部供应商逻辑
					if (StringUtilHelper.isNotNull(mallSkuOutDTO.getProductChannelCode())
							&& mallSkuOutDTO.getProductChannelCode()
									.startsWith(GoodCenterEnum.EXTERNAL_CHANNELS.getCode())) {
						// 是否有外接渠道商品 如果订单行里有一个有外接渠道商品则有
						orderTemp.setHasProductplusFlag(
								Integer.valueOf(OrderStatusEnum.HAS_PRODUCTPLUS_FLAG.getCode()));
						orderItemTemp.setGoodsFreight(mallSkuOutDTO.getFreightAmount() == null
								? new BigDecimal(0) : mallSkuOutDTO.getFreightAmount());
						BatchGetStockSkuNumsReqDTO skuNumsTemp = new BatchGetStockSkuNumsReqDTO();
						skuNumsTemp.setNum(orderItemTemp.getGoodsCount());
						skuNumsTemp.setSkuId(String.valueOf(mallSkuOutDTO.getOuterSkuId()));
						skuNums.add(skuNumsTemp);
						orderItemTemp.setSalePrice(orderItemSkuPriceDTO.getSalePrice() == null
								? new BigDecimal(0) : orderItemSkuPriceDTO.getSalePrice());

						JDAcountAmt = JDAcountAmt.add(orderItemTemp.getGoodsPrice()
								.multiply(new BigDecimal(orderItemTemp.getGoodsCount())));

						// 汇通达在京东备用金
						accountAmount4JDRes = goodsPlusRAO
								.queryAccountAmount4JD(orderCreateInfoReqDTO.getMessageId());
						if (!accountAmount4JDRes.getOtherCenterResponseCode()
								.equals(ResultCodeEnum.SUCCESS.getCode())) {
							orderCreateInfoDMO.setResultCode(
									accountAmount4JDRes.getOtherCenterResponseCode());
							orderCreateInfoDMO
									.setResultMsg(accountAmount4JDRes.getOtherCenterResponseMsg());
							return orderCreateInfoDMO;
						} else {
							BigDecimal bd = new BigDecimal(
									accountAmount4JDRes.getOtherCenterResult());
							if (bd.compareTo(JDAcountAmt) < 0) {
								orderCreateInfoDMO.setResultCode(
										ResultCodeEnum.ORDERSETTLEMENT_DEAL_JDAMOUNT_BEYOND
												.getCode());
								orderCreateInfoDMO.setResultMsg(
										ResultCodeEnum.ORDERSETTLEMENT_DEAL_JDAMOUNT_BEYOND
												.getMsg());
								return orderCreateInfoDMO;
							}
						}

						// 创建京东订单 --2
						JDCreateOrderSkuReqDTO sku = new JDCreateOrderSkuReqDTO();
						sku.setSkuId(mallSkuOutDTO.getOuterSkuId());
						sku.setNum(Integer.valueOf(orderItemTemp.getGoodsCount().toString()));
						sku.setbNeedAnnex(true);
						sku.setbNeedGift(false);
						skuList.add(sku);
					} else if ((StringUtilHelper.isNotNull(mallSkuOutDTO.getProductChannelCode())
							&& mallSkuOutDTO.getProductChannelCode()
									.equals(GoodCenterEnum.EXTERNAL_SUPPLIER.getCode()))) {
						// 校验外部供应商的商品数量是否大于提交数量--秒杀不需要校验
						if (!promotionType
								.equals(OrderStatusEnum.PROMOTION_TYPE_SECKILL.getCode())) {
							orderCreateInfoDMO = validateInternalSupplierQuantity(
									mallSkuStockOutDTO, orderItemTemp, orderCreateInfoDMO);
							isNeedBatchReserveStockMap.put(IS_NEED_BATCH_RESERVE_STOCK_KEY,
									OrderStatusEnum.ORDER_RELEASE_STOCK.getCode());
						}
						if (!orderCreateInfoDMO.getResultCode()
								.equals(ResultCodeEnum.SUCCESS.getCode())) {
							return orderCreateInfoDMO;
						}

						orderItemTemp.setGoodsFreight(new BigDecimal(0));// TODO
																			// 2017-02-08
																			// 外部供应商运费先写成0,等蒋坤提供运费接口(外部供应商是用运费模板，然后计算)

						// 如果是外部供应商-将saleprice不用赋值，计算阶梯价格计算goodprice即可
						BigDecimal goodsPrice = new ExternalSupplierCostCaculateUtil()
								.caculateLadderPrice4outer(orderItemTemp.getGoodsCount().intValue(),
										orderItemSkuPriceDTO.getLadderPriceList());
						orderItemTemp.setGoodsPrice(goodsPrice);

						// 计算外部供应商运费
						assembleFreight(orderItemTemp, mallSkuOutDTO);
						if (externalSupplierFlag == false) {
							externalSupplierFlag = true;
						}

					} else {
						// 校验内部供应商的商品数量是否大于提交数量--秒杀不需要校验
						if (!promotionType
								.equals(OrderStatusEnum.PROMOTION_TYPE_SECKILL.getCode())) {
							orderCreateInfoDMO = validateInternalSupplierQuantity(
									mallSkuStockOutDTO, orderItemTemp, orderCreateInfoDMO);
							isNeedBatchReserveStockMap.put(IS_NEED_BATCH_RESERVE_STOCK_KEY,
									OrderStatusEnum.ORDER_RELEASE_STOCK.getCode());
						}
						if (!orderCreateInfoDMO.getResultCode()
								.equals(ResultCodeEnum.SUCCESS.getCode())) {
							return orderCreateInfoDMO;
						}
						orderItemTemp.setGoodsFreight(mallSkuOutDTO.getFreightAmount() == null
								? new BigDecimal(0) : mallSkuOutDTO.getFreightAmount());
						orderItemTemp.setSalePrice(orderItemSkuPriceDTO.getSalePrice() == null
								? new BigDecimal(0) : orderItemSkuPriceDTO.getSalePrice());

					}

					// 组装填充订单行数据
					assembleOrderItem(orderItemTemp, orderItemSkuPriceDTO, mallSkuOutDTO,
							buyerGradeMap);
					if (StringUtilHelper
							.isNull(orderCreateInfoReqDTO.getConsigneeAddressProvince())) {
						orderItemTemp.setIsOutDistribtion(
								Integer.valueOf(OrderStatusEnum.NOT_OUT_DISTRIBTION.getCode()));
					} else if ((StringUtilHelper.isNotNull(mallSkuOutDTO.getProductChannelCode())
							&& (mallSkuOutDTO.getProductChannelCode()
									.equals(GoodCenterEnum.EXTERNAL_SUPPLIER.getCode()))
							|| mallSkuOutDTO.getProductChannelCode()
									.equals(GoodCenterEnum.JD_SUPPLIER.getCode()))) {
						orderItemTemp.setIsOutDistribtion(
								Integer.valueOf(OrderStatusEnum.NOT_OUT_DISTRIBTION.getCode()));
					} else {
						OtherCenterResDTO<String> isRecevieAddressInSaleRangeRes = goodsCenterRAO
								.isRecevieAddressInSaleRange(orderCreateInfoReqDTO, orderItemTemp,
										orderCreateInfoReqDTO.getMessageId());
						if (!ResultCodeEnum.SUCCESS.getCode().equals(
								isRecevieAddressInSaleRangeRes.getOtherCenterResponseCode())) {
							orderCreateInfoDMO.setResultCode(
									isRecevieAddressInSaleRangeRes.getOtherCenterResponseCode());
							orderCreateInfoDMO.setResultMsg(
									isRecevieAddressInSaleRangeRes.getOtherCenterResponseMsg());
							return orderCreateInfoDMO;
						}
					}
					// TODO 运费总金额 total_freight
					// 判断是不是外接渠道商品,如果是外接渠道商品就不用组装商品对象
					if (StringUtilHelper.isNotNull(mallSkuOutDTO.getProductChannelCode())
							&& !mallSkuOutDTO.getProductChannelCode()
									.startsWith(GoodCenterEnum.EXTERNAL_CHANNELS.getCode())) {
						Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();// 锁商品库存创建对象4
						order4StockEntryDTO.setIsBoxFlag(orderItemTemp.getIsBoxFlag());
						order4StockEntryDTO.setSkuCode(mallSkuOutDTO.getSkuCode());
						order4StockEntryDTO.setQuantity(
								Integer.valueOf(orderItemTemp.getGoodsCount().toString()));
						orderEntries.add(order4StockEntryDTO);
					}
				}
			}

			// 京东商品校验
			if (null != orderTemp.getHasProductplusFlag()) {
				batchGetStockReqDTO.setSkuNums(skuNums);
				OtherCenterResDTO<String> selectChannelAddressDTO = memberCenterRAO
						.selectChannelAddressDTO(orderCreateInfoReqDTO.getMessageId(),
								orderTemp.getSellerCode(), Constant.PRODUCT_CHANNEL_CODE_OUTLINE);
				if (!selectChannelAddressDTO.getOtherCenterResponseCode()
						.equals(ResultCodeEnum.SUCCESS.getCode())) {
					orderCreateInfoDMO
							.setResultCode(selectChannelAddressDTO.getOtherCenterResponseCode());
					orderCreateInfoDMO
							.setResultMsg(selectChannelAddressDTO.getOtherCenterResponseMsg());
					return orderCreateInfoDMO;
				}
				batchGetStockReqDTO.setArea(selectChannelAddressDTO.getOtherCenterResult());
				orderCreateInfoDMO = validateJDGoodsStock(batchGetStockReqDTO, orderCreateInfoDMO,
						orderCreateInfoReqDTO.getMessageId());
				if (!orderCreateInfoDMO.getResultCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
					return orderCreateInfoDMO;
				}

				BigDecimal accountAmount4JD = new BigDecimal(
						accountAmount4JDRes.getOtherCenterResult());
				if (accountAmount4JD.compareTo(JDAcountAmt) < ZERO) {
					orderCreateInfoDMO
							.setResultCode(ResultCodeEnum.JD_RESERVE_FUND_NOT_ENOUGH.getCode());
					orderCreateInfoDMO
							.setResultMsg(ResultCodeEnum.JD_RESERVE_FUND_NOT_ENOUGH.getMsg());
					return orderCreateInfoDMO;
				}
				// 创建京东订单 --3
				OrderCreateInfoDMO jdCreateRes = jdCreateOrderService.createJDOrder(
						orderCreateInfoReqDTO.getMessageId(), jdCreateOrderReqDTO, orderTemp);
				if (!ResultCodeEnum.SUCCESS.getCode().equals(jdCreateRes.getResultCode())) {
					orderCreateInfoDMO.setResultCode(jdCreateRes.getResultCode());
					orderCreateInfoDMO.setResultMsg(jdCreateRes.getResultMsg());
					return orderCreateInfoDMO;
				}
			}
			// 计算外部供应商运费
			if (externalSupplierFlag) {
				// TODO 暂时屏蔽外部供应商的运费计算方法
				LOGGER.info("外部供应商计算运费开始MessageId:{}", orderCreateInfoReqDTO.getMessageId());
				orderFreightInfoService.calculateOrderItemFeight4CreateOrder(orderItemList,
						orderCreateInfoReqDTO.getSite());
				LOGGER.info("外部供应商计算运费结束！MessageId:{}", orderCreateInfoReqDTO.getMessageId());
			}

		}

		// 调用商品中心批量锁定库存
		if (OrderStatusEnum.ORDER_RELEASE_STOCK.getCode()
				.equals(isNeedBatchReserveStockMap.get(IS_NEED_BATCH_RESERVE_STOCK_KEY))) {
			OtherCenterResDTO<String> batchReserveStockRes = goodsCenterRAO
					.batchReserveStock(order4StockChangeDTOs, orderCreateInfoReqDTO.getMessageId());
			if (!ResultCodeEnum.SUCCESS.getCode()
					.equals(batchReserveStockRes.getOtherCenterResponseCode())) {
				orderCreateInfoDMO.setResultCode(batchReserveStockRes.getOtherCenterResponseCode());
				orderCreateInfoDMO.setResultMsg(batchReserveStockRes.getOtherCenterResponseMsg());
				return orderCreateInfoDMO;
			}
		}
		return orderCreateInfoDMO;
	}

	/*
	 * 组装订单行数据
	 */
	private void assembleOrderItem(OrderCreateItemListInfoReqDTO orderItemTemp,
			OrderItemSkuPriceDTO orderItemSkuPriceDTO, MallSkuOutDTO mallSkuOutDTO,
			Map<String, String> buyerGradeMap) {
		orderItemTemp.setCostPrice(orderItemSkuPriceDTO.getCostPrice() == null ? new BigDecimal(0)
				: orderItemSkuPriceDTO.getCostPrice());
		orderItemTemp.setPriceFloatingRatio(orderItemSkuPriceDTO.getPriceFloatingRatio() == null
				? new BigDecimal(0) : orderItemSkuPriceDTO.getPriceFloatingRatio());
		orderItemTemp.setCommissionRatio(orderItemSkuPriceDTO.getCommissionRatio() == null
				? new BigDecimal(0) : orderItemSkuPriceDTO.getCommissionRatio());
		orderItemTemp.setGoodsPriceType(orderItemSkuPriceDTO.getGoodsPriceType() == null ? ""
				: orderItemSkuPriceDTO.getGoodsPriceType().toString());
		String buyerGrade = buyerGradeMap.get("buyerGrade") == null ? ""
				: buyerGradeMap.get("buyerGrade").toString();
		// 商品中心返回的是等级价,且会员是vip,则订单行的价格类型是vip价
		String goodsPriceType = orderItemTemp.getGoodsPriceType();
		if (StringUtilHelper.isNotNull(goodsPriceType, buyerGrade)
				&& goodsPriceType.equals(OrderStatusEnum.GOODS_CENTER_PRICE_TYPE_GRADE.getCode())
				&& buyerGrade.equals(OrderStatusEnum.BUYER_GRADE_VIP.getCode())) {
			orderItemTemp.setGoodsPriceType(OrderStatusEnum.GOODS_PRICE_TYPE_VIP.getCode());
		}
		orderItemTemp.setErpFirstCategoryCode(mallSkuOutDTO.getErpFirstCategoryCode());
		orderItemTemp.setBrandId(mallSkuOutDTO.getBrandId());
		orderItemTemp.setBrandName(mallSkuOutDTO.getBrandName());
		orderItemTemp.setFirstCategoryId(mallSkuOutDTO.getFirstCategoryId());
		orderItemTemp.setFirstCategoryName(mallSkuOutDTO.getFirstCategoryName());
		orderItemTemp.setSecondCategoryId(mallSkuOutDTO.getSecondCategoryId());
		orderItemTemp.setSecondCategoryName(mallSkuOutDTO.getSecondCategoryName());
		orderItemTemp.setThirdCategoryId(mallSkuOutDTO.getThirdCategoryId());
		orderItemTemp.setThirdCategoryName(mallSkuOutDTO.getThirdCategoryName());
		orderItemTemp.setItemCode(mallSkuOutDTO.getItemCode());
		orderItemTemp.setGoodsName(mallSkuOutDTO.getItemName());
		orderItemTemp.setSkuPictureUrl(mallSkuOutDTO.getItemPictureUrl());
		orderItemTemp.setIsVipItem(mallSkuOutDTO.getIsVipItem());
		orderItemTemp.setVipItemType(mallSkuOutDTO.getVipItemType());
		orderItemTemp.setVipSyncFlag(mallSkuOutDTO.getVipSyncFlag());
		orderItemTemp.setShopFreightTemplateId(mallSkuOutDTO.getShopFreightTemplateId());
		orderItemTemp.setItemSpuCode(mallSkuOutDTO.getItemSpuCode());
		orderItemTemp.setOuterSkuCode(mallSkuOutDTO.getOuterSkuId());
		Map<String, Object> extendMap = new HashMap<String, Object>();
		extendMap.put("skuErpCode", mallSkuOutDTO.getSkuErpCode());
		extendMap.put("eanCode", mallSkuOutDTO.getEanCode());
		extendMap.put("erpFiveCategoryCode", mallSkuOutDTO.getErpFiveCategoryCode());
		orderItemTemp.setExtendMap(extendMap);
	}

	/*
	 * 入库预处理
	 *
	 */
	private OrderCreateInfoDMO preInsertOrderAndOrderItem(MemberBaseInfoDTO buyerInfoDTO,
			OrderCreateInfoReqDTO orderCreateInfoReqDTO, OrderCreateInfoDMO orderCreateInfoDMO) {
		try {
			List<OrderCreateListInfoReqDTO> orderList = orderCreateInfoReqDTO.getOrderList();
			List<OrderCreateListInfoDMO> orderResList = new ArrayList<OrderCreateListInfoDMO>();// 创建订单返回结果1
			orderCreateInfoDMO.setOrderResList(orderResList);
			int isOutDistribtion = Integer.parseInt(OrderStatusEnum.NOT_OUT_DISTRIBTION.getCode());// 是否超出配送范围//
																									// 0:否,1:-是(返回给前台页面展示)

			for (int i = 0; i < orderList.size(); i++) {
				OrderCreateListInfoDMO orderCreateListInfoDMO = new OrderCreateListInfoDMO();// 创建订单返回结果(提供前台待支付
																								// )-2
				int isOrderOutDistribtion = Integer
						.parseInt(OrderStatusEnum.NOT_OUT_DISTRIBTION.getCode());// 订单是否超出配送范围//
																					// 0:否,1:-是

				OrderCreateListInfoReqDTO orderTemp = orderList.get(i);
				orderCreateListInfoDMO.setOrderNo(orderTemp.getOrderNo());
				if (StringUtilHelper.isNotNull(orderTemp.getSellerCode())) {
					orderCreateListInfoDMO.setSellerUserId(orderTemp.getSellerCode());
				}
				orderResList.add(orderCreateListInfoDMO);
				List<OrderCreateItemListInfoReqDTO> orderItemList = orderTemp.getOrderItemList();
				Long totalGoodsCount = new Long(0);// 订单商品总数量
				BigDecimal totalGoodsAmount = new BigDecimal(0);// 订单商品总金额
				BigDecimal totalDiscountAmount = new BigDecimal(0);// 优惠总金额
				BigDecimal platformDiscountAmount = new BigDecimal(0);// 平台优惠总金额
				BigDecimal orderTotalAmount = new BigDecimal(0);// 订单总价
				BigDecimal orderPayAmount = new BigDecimal(0);
				BigDecimal totalFreight = new BigDecimal(0);// 运费总金额

				// 按照品牌品类拼装给前台1
				Map<String, BigDecimal> tradeOrderItemsDMOMap = new HashMap<String, BigDecimal>();

				List<ChargeConditionInfoDMO> chargeConditionInfoDMOList = new ArrayList<ChargeConditionInfoDMO>();
				BigDecimal externalAmt = new BigDecimal(0);

				if (null != orderItemList && orderItemList.size() > 0) {
					for (int j = 0; j < orderItemList.size(); j++) {
						OrderCreateItemListInfoReqDTO orderItemTemp = orderItemList.get(j);
						// 如果是秒杀或者用券 就去促销中心锁定库存 且插入订单行优惠信息表
						if (StringUtilHelper.isNotNull(orderCreateInfoReqDTO.getPromotionType())) {
							// 插入订单行优惠信息表
							insertTradeOrderItemsDiscount(orderCreateInfoReqDTO, orderTemp,
									orderItemTemp);
						}
						// 插入订单行表
						TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
						OrderCreateInfoDMO insertTradeOrderItemsRes = insertTradeOrderItems(
								orderCreateInfoReqDTO, orderTemp, orderItemTemp, tradeOrderItemsDMO,
								orderCreateInfoDMO);
						totalFreight = totalFreight.add(tradeOrderItemsDMO.getGoodsFreight());

						// 按照品牌品类拼装给前台2--如果是京东，就写死-不是京东就按erp的品牌品类拆分
						String brandIdErpFirstCategoryCode = null;
						// 预处理返回给前台和组装收付款待下行信息表的参数
						externalAmt = assemblePrePayAndPreSavePayOrderInfo(
								brandIdErpFirstCategoryCode, tradeOrderItemsDMOMap, orderItemTemp,
								tradeOrderItemsDMO, orderTemp, orderCreateInfoReqDTO.getBuyerCode(),
								externalAmt, chargeConditionInfoDMOList,
								orderCreateInfoReqDTO.getMessageId());

						if (OrderStatusEnum.OUT_DISTRIBTION.getCode()
								.equals(tradeOrderItemsDMO.getIsOutDistribtion() == null ? ""
										: tradeOrderItemsDMO.getIsOutDistribtion().toString())) {
							isOrderOutDistribtion = isOutDistribtion = Integer
									.parseInt(OrderStatusEnum.OUT_DISTRIBTION.getCode());
						}
						totalGoodsCount = totalGoodsCount + orderItemTemp.getGoodsCount();
						totalGoodsAmount = totalGoodsAmount
								.add(tradeOrderItemsDMO.getGoodsAmount());
						totalDiscountAmount = totalDiscountAmount.add(
								orderItemTemp.getTotalDiscountAmount() == null ? new BigDecimal(0)
										: orderItemTemp.getTotalDiscountAmount());
						platformDiscountAmount = platformDiscountAmount.add(
								orderItemTemp.getTotalDiscountAmount() == null ? new BigDecimal(0)
										: orderItemTemp.getTotalDiscountAmount());
						orderTotalAmount = orderTotalAmount
								.add(tradeOrderItemsDMO.getOrderItemTotalAmount());
						orderPayAmount = orderPayAmount
								.add(tradeOrderItemsDMO.getOrderItemPayAmount());
						// TODO 运费总金额 total_freight
					}
				}
				// 插入订单表
				Map<String, Object> orderParam = new HashMap<String, Object>();
				assembleOrderParam(orderParam, orderTemp.getHasProductplusFlag(), totalGoodsCount,
						totalGoodsAmount, platformDiscountAmount, orderTotalAmount, orderPayAmount,
						isOrderOutDistribtion);
				OrderCreateInfoDMO insetTradeOrdersRes = insetTradeOrders(buyerInfoDTO,
						orderCreateInfoReqDTO, orderTemp, orderParam, orderCreateInfoDMO,
						totalFreight);

				// 按照品牌品类拼装给前台3
				assemblePrePayAndSavePayOrderInfo(tradeOrderItemsDMOMap, orderTemp,
						chargeConditionInfoDMOList, orderCreateInfoReqDTO.getBuyerCode(),
						externalAmt, orderCreateListInfoDMO,
						orderCreateInfoReqDTO.getPromotionType(),
						orderCreateInfoReqDTO.getMessageId());
			}

			if (isOutDistribtion == Integer.parseInt(OrderStatusEnum.OUT_DISTRIBTION.getCode())) {
				orderCreateInfoDMO.setOrderResStatus(OrderStatusEnum.PRE_CHECK.getCode());
			} else {
				orderCreateInfoDMO.setOrderResStatus(OrderStatusEnum.PRE_PAY.getCode());
			}
		} catch (Exception e) {
			orderCreateInfoDMO.setResultCode(
					ResultCodeEnum.ORDER_CREATE_ORDER_ROLLBACK_BATCHRELEASE.getCode());
			orderCreateInfoDMO
					.setResultMsg(ResultCodeEnum.ORDER_CREATE_ORDER_ROLLBACK_BATCHRELEASE.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderCreateServiceImpl.preInsertOrderAndOrderItem出现异常{}",
					orderCreateInfoReqDTO.getMessageId(), w.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return orderCreateInfoDMO;
	}

	/*
	 * 释放商品和优惠券、秒杀库存
	 */
	private void batchReleaseGoodsAndMarketStock(List<Order4StockChangeDTO> order4StockChangeDTOs,
			List<OrderItemPromotionDTO> orderItemPromotionList, String promotionType,
			Map<String, String> isNeedBatchReserveStockMap, String messageId) {
		try {
			if (OrderStatusEnum.ORDER_RELEASE_STOCK.getCode()
					.equals(isNeedBatchReserveStockMap.get(IS_NEED_BATCH_RESERVE_STOCK_KEY))) {
				// 释放商品库存
				goodsCenterRAO.batchReleaseStock(order4StockChangeDTOs, messageId);
			}
			// 释放优惠券、秒杀库存
			if (StringUtilHelper.isNotNull(promotionType)) {
				marketCenterRAO.releaseBuyerPromotion(orderItemPromotionList, messageId);
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法(释放商品和优惠券、秒杀库存)OrderCreateServiceImpl.batchReleaseGoodsAndMarketStock出现异常{}",
					messageId, w.toString());
		}
	}

	/*
	 * 拼装参数返回给前台和入收付款待下行信息表
	 */
	private BigDecimal assemblePrePayAndPreSavePayOrderInfo(String brandIdErpFirstCategoryCode,
			Map<String, BigDecimal> tradeOrderItemsDMOMap,
			OrderCreateItemListInfoReqDTO orderItemTemp, TradeOrderItemsDMO tradeOrderItemsDMO,
			OrderCreateListInfoReqDTO orderTemp, String buyerCode, BigDecimal externalAmt,
			List<ChargeConditionInfoDMO> chargeConditionInfoDMOList, String messageId)
					throws Exception {

		// 外部供应商不需要按照品牌品类返回给支付,但是具有内部供应商身份的外部供应商要插入到收付款表
		if (StringUtilHelper.isNotNull(orderItemTemp.getChannelCode()) && orderItemTemp
				.getChannelCode().equals(GoodCenterEnum.EXTERNAL_SUPPLIER.getCode())) {
			OtherCenterResDTO<String> memberRes = memberCenterRAO
					.isHasInnerComapanyCert(orderTemp.getSellerCode(), messageId);
			if (ResultCodeEnum.SUCCESS.getCode().equals(memberRes.getOtherCenterResponseCode())
					&& memberRes.getOtherCenterResult()
							.equals(MemberCenterEnum.HAS_INNER_COMAPANY_CERT.getCode())) {
				preSavePayOrderInfo(orderItemTemp, brandIdErpFirstCategoryCode,
						tradeOrderItemsDMOMap, tradeOrderItemsDMO);
			}
			ChargeConditionInfoDMO chargeConditionInfoDMO = new ChargeConditionInfoDMO();
			chargeConditionInfoDMO.setBrandCode(orderItemTemp.getBrandId() == null ? ""
					: orderItemTemp.getBrandId().toString());
			chargeConditionInfoDMO.setClassCode(GoodCenterEnum.EXTERNAL_SUPPLIER.getCode());
			chargeConditionInfoDMO.setItemOrderNo(orderItemTemp.getOrderItemNo());
			BigDecimal orderItemPayAmount = tradeOrderItemsDMO.getOrderItemPayAmount();
			externalAmt = externalAmt.add(orderItemPayAmount);
			chargeConditionInfoDMO.setChargeAmount(orderItemPayAmount);
			chargeConditionInfoDMO.setCompanyCode(orderTemp.getSellerCode());
			chargeConditionInfoDMO.setCustomerCode(buyerCode);
			chargeConditionInfoDMOList.add(chargeConditionInfoDMO);
		} else {
			preSavePayOrderInfo(orderItemTemp, brandIdErpFirstCategoryCode, tradeOrderItemsDMOMap,
					tradeOrderItemsDMO);
		}
		return externalAmt;
	}

	public void preSavePayOrderInfo(OrderCreateItemListInfoReqDTO orderItemTemp,
			String brandIdErpFirstCategoryCode, Map<String, BigDecimal> tradeOrderItemsDMOMap,
			TradeOrderItemsDMO tradeOrderItemsDMO) throws Exception {
		if (StringUtilHelper.isNotNull(orderItemTemp.getChannelCode()) && orderItemTemp
				.getChannelCode().startsWith(GoodCenterEnum.EXTERNAL_CHANNELS.getCode())) {
			brandIdErpFirstCategoryCode = jdConfig.getJdBrandCode() + "#"
					+ jdConfig.getJdClassCode();
		} else if (StringUtilHelper.isNotNull(orderItemTemp.getChannelCode()) && orderItemTemp
				.getChannelCode().equals(GoodCenterEnum.EXTERNAL_SUPPLIER.getCode())) {
			brandIdErpFirstCategoryCode = orderItemTemp.getBrandId() == null ? ""
					: orderItemTemp.getBrandId().toString() + "#"
							+ GoodCenterEnum.EXTERNAL_SUPPLIER.getCode();
		} else if (StringUtilHelper.isNotNull(orderItemTemp.getChannelCode()) && orderItemTemp
				.getChannelCode().equals(GoodCenterEnum.INTERNAL_SUPPLIER.getCode())) {
			brandIdErpFirstCategoryCode = orderItemTemp.getBrandId() + "#"
					+ orderItemTemp.getErpFirstCategoryCode();
			if (StringUtilHelper.isNull(orderItemTemp.getErpFirstCategoryCode())) {
				LOGGER.error("内部供应商商品没有查到erp一级类目ErpFirstCategoryCode:"
						+ orderItemTemp.getErpFirstCategoryCode());
				throw new Exception();
			}
		}
		LOGGER.info("准备(pre)组装给前台品牌品类展示:" + brandIdErpFirstCategoryCode);
		if (tradeOrderItemsDMOMap.containsKey(brandIdErpFirstCategoryCode)) {
			BigDecimal chargeAmount = tradeOrderItemsDMOMap.get(brandIdErpFirstCategoryCode)
					.add(tradeOrderItemsDMO.getOrderItemPayAmount());
			tradeOrderItemsDMOMap.put(brandIdErpFirstCategoryCode, chargeAmount);
		} else {
			tradeOrderItemsDMOMap.put(brandIdErpFirstCategoryCode,
					tradeOrderItemsDMO.getOrderItemPayAmount());
		}
	}

	/*
	 * 1 组装参数返回给前台支付用 2 组装参数插入收付款待下行信息表 3外部供应商商品不需要插入收付款待下行信息表
	 */
	private void assemblePrePayAndSavePayOrderInfo(Map<String, BigDecimal> tradeOrderItemsDMOMap,
			OrderCreateListInfoReqDTO orderTemp,
			List<ChargeConditionInfoDMO> chargeConditionInfoDMOList, String buyerCode,
			BigDecimal externalAmt, OrderCreateListInfoDMO orderCreateListInfoDMO,
			String promotionType, String messageId) throws UnknownHostException {
		BigDecimal amount = new BigDecimal(0);
		String channelCode = "";
		// 如果tradeOrderItemsDMOMap为空就是内部供应商或者京东商品，否则是外部供应商
		if (null != tradeOrderItemsDMOMap && !tradeOrderItemsDMOMap.isEmpty()) {
			for (Map.Entry<String, BigDecimal> m : tradeOrderItemsDMOMap.entrySet()) {

				String[] brandIdThirdCategoryId = m.getKey().split("#");
				LOGGER.info("组装给前台品牌品类展示结果:" + JSONObject.toJSONString(brandIdThirdCategoryId));
				String brandId = brandIdThirdCategoryId[0];
				String categoryId = brandIdThirdCategoryId[1];

				ChargeConditionInfoDMO chargeConditionInfoDMO = new ChargeConditionInfoDMO();
				chargeConditionInfoDMO.setBrandCode(brandId);
				chargeConditionInfoDMO.setClassCode(categoryId);
				String lockAmoutNo = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());

				List<OrderCreateItemListInfoReqDTO> itemList = orderTemp.getOrderItemList();
				if (null != itemList && itemList.size() > 0) {
					OrderCreateItemListInfoReqDTO item = itemList.get(0);
					/*
					 * if(brandId.equals(MiddleWareEnum.JD_BRAND_ID_PAY.getCode(
					 * )) ||
					 * brandId.equals(MiddleWareEnum.JD_BRAND_ID_ERP.getCode())
					 * || GoodCenterEnum.IS_VIP_GOODS.getCode().equals(item.
					 * getIsVipItem().toString())){
					 * chargeConditionInfoDMO.setItemOrderNo(orderTemp.
					 * getOrderNo());
					 * LOGGER.info("京东商品锁定用订单号"+chargeConditionInfoDMO.
					 * getItemOrderNo()); }else
					 * if(GoodCenterEnum.IS_VIP_GOODS.getCode().equals(item.
					 * getIsVipItem().toString())){
					 * chargeConditionInfoDMO.setItemOrderNo(orderTemp.
					 * getOrderNo());
					 * LOGGER.info("VIP商品锁定用订单号"+chargeConditionInfoDMO.
					 * getItemOrderNo()); }else{
					 * chargeConditionInfoDMO.setItemOrderNo(lockAmoutNo); }
					 */
					BigDecimal chargeAmount = m.getValue();
					amount = amount.add(chargeAmount);

					// 插入收付款待下行信息表
					PayOrderInfoDMO record = new PayOrderInfoDMO();
					record.setProductCode(MiddleWareEnum.PRODUCTCODE_ERP.getCode());// 商城
					record.setSupplierCode(orderTemp.getSellerCode());
					if (brandId.equals(MiddleWareEnum.JD_BRAND_ID_PAY.getCode())) {
						record.setBrandCode(MiddleWareEnum.JD_BRAND_ID_ERP.getCode());
						record.setClassCode(MiddleWareEnum.JD_CLASS_CODE_ERP.getCode());
						record.setDownOrderNo(orderTemp.getOrderNo());// 京东商品锁定余额编号
																		// 就用订单号,预售也用订单号下行
						channelCode = GoodCenterEnum.JD_SUPPLIER.getCode();
						chargeConditionInfoDMO.setItemOrderNo(orderTemp.getOrderNo());
						LOGGER.info("京东商品锁定用订单号" + chargeConditionInfoDMO.getItemOrderNo());
					} else if (item.getChannelCode()
							.equals(GoodCenterEnum.EXTERNAL_SUPPLIER.getCode())) {
						record.setBrandCode(MiddleWareEnum.JD_BRAND_ID_ERP.getCode());
						record.setClassCode(MiddleWareEnum.JD_CLASS_CODE_ERP.getCode());
						record.setDownOrderNo(lockAmoutNo);// 具有内部供应商身份的外部供应商
						channelCode = GoodCenterEnum.EXTERNAL_SUPPLIER.getCode();
						record.setProductCode(
								MiddleWareEnum.PRODUCTCODE_ERP_EXTERNAL_SUPPLIER.getCode());// 平台公司在POP交易
						orderCreateListInfoDMO
								.setTradeScene(PayStatusEnum.NOT_SUPPORT_ERP_BLANCE_PAY.getCode());
						OtherCenterResDTO<MemberBaseInfoDTO> memberRes = memberCenterRAO
								.getInnerInfoByOuterHTDCode(orderTemp.getSellerCode(), messageId);
						if (ResultCodeEnum.SUCCESS.getCode()
								.equals(memberRes.getOtherCenterResponseCode())) {
							record.setSupplierCode(
									memberRes.getOtherCenterResult().getMemberCode());
						}
					} else if (GoodCenterEnum.IS_VIP_GOODS.getCode()
							.equals(item.getIsVipItem().toString())) {
						record.setBrandCode(brandIdThirdCategoryId[0]);
						record.setClassCode(brandIdThirdCategoryId[1]);
						record.setDownOrderNo(orderTemp.getOrderNo());
						channelCode = GoodCenterEnum.INTERNAL_SUPPLIER.getCode();
						chargeConditionInfoDMO.setItemOrderNo(orderTemp.getOrderNo());
						LOGGER.info("VIP商品锁定用订单号" + chargeConditionInfoDMO.getItemOrderNo());
					} else {
						record.setBrandCode(brandIdThirdCategoryId[0]);
						record.setClassCode(brandIdThirdCategoryId[1]);
						record.setDownOrderNo(lockAmoutNo);
						channelCode = GoodCenterEnum.INTERNAL_SUPPLIER.getCode();
						chargeConditionInfoDMO.setItemOrderNo(lockAmoutNo);
					}

					chargeConditionInfoDMO.setChargeAmount(chargeAmount);
					chargeConditionInfoDMO.setCompanyCode(orderTemp.getSellerCode());
					chargeConditionInfoDMO.setCustomerCode(buyerCode);
					if (!item.getChannelCode().equals(GoodCenterEnum.EXTERNAL_SUPPLIER.getCode())) {
						chargeConditionInfoDMOList.add(chargeConditionInfoDMO);
					}

					record.setAmount(m.getValue());
					record.setOrderType(ORDER_TYPE);
					record.setOrderNo(orderTemp.getOrderNo());
					record.setOrderFrom(orderTemp.getOrderFrom());
					record.setMemberCode(buyerCode);
					int update = payOrderInfoDAO
							.updatePayorderinfoByOrderNoBrandIdClassCode(record);
					if (update == ZERO) {
						payOrderInfoDAO.insertSelective(record);
					}

				}
			}
		} else {
			LOGGER.info("支付方式不能选择erp余额支付-存在外部供应商");
			orderCreateListInfoDMO
					.setTradeScene(PayStatusEnum.NOT_SUPPORT_ERP_BLANCE_PAY.getCode());
			channelCode = GoodCenterEnum.EXTERNAL_SUPPLIER.getCode();
		}

		// 判断能否走erp余额支付
		if (StringUtilHelper.isNull(orderCreateListInfoDMO.getTradeScene())) {
			orderCreateListInfoDMO.setTradeScene(PayStatusEnum.SUPPORT_ALL_BLANCE_PAY.getCode());
		}
		orderCreateListInfoDMO.setChannelCode(channelCode);
		orderCreateListInfoDMO.setBuyerUserId(buyerCode);
		orderCreateListInfoDMO.setPayerUserId(buyerCode);
		orderCreateListInfoDMO
				.setAmount(amount.compareTo(new BigDecimal(0)) == 0 ? externalAmt : amount);
		orderCreateListInfoDMO.setShopName(orderTemp.getShopName());
		orderCreateListInfoDMO.setChargeConditionInfo(chargeConditionInfoDMOList);
		if (StringUtilHelper.isNotNull(promotionType)
				&& promotionType.equals(OrderStatusEnum.PROMOTION_TYPE_SECKILL.getCode())) {
			orderCreateListInfoDMO.setPayTimeLimit(orderTemp.getPayTimeLimit());
		}
	}

	/*
	 * 组装参数给外部供应商计算运费用
	 */
	private void assembleFreight(OrderCreateItemListInfoReqDTO orderItemTemp,
			MallSkuOutDTO mallSkuOutDTO) {
		orderItemTemp.setWeight(mallSkuOutDTO.getWeight());
		orderItemTemp.setNetWeight(mallSkuOutDTO.getNetWeight());
		orderItemTemp.setLength(mallSkuOutDTO.getLength());
		orderItemTemp.setWidth(mallSkuOutDTO.getWidth());
		orderItemTemp.setHeight(mallSkuOutDTO.getHeight());
		orderItemTemp.setWeightUnit(mallSkuOutDTO.getWeightUnit());
	}

	/*
	 * 查询订单行价格
	 */
	private OtherCenterResDTO<OrderItemSkuPriceDTO> queryItemSkuPrice(MallSkuOutDTO mallSkuOutDTO,
			OrderCreateInfoReqDTO orderCreateInfoReqDTO, OrderCreateListInfoReqDTO orderTemp,
			OrderCreateItemListInfoReqDTO orderItemTemp, Map<String, String> buyerGradeMap,
			String messageId) {
		OtherCenterResDTO<OrderItemSkuPriceDTO> orderItemSkuPriceDTORes = new OtherCenterResDTO<OrderItemSkuPriceDTO>();
		QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO = new QueryCommonItemSkuPriceDTO();
		queryCommonItemSkuPriceDTO.setSkuId(orderItemTemp.getSkuId());
		queryCommonItemSkuPriceDTO.setIsBoxFlag(orderItemTemp.getIsBoxFlag());
		queryCommonItemSkuPriceDTO.setItemChannelCode(orderItemTemp.getChannelCode());
		queryCommonItemSkuPriceDTO.setCitySiteCode(orderCreateInfoReqDTO.getSite());
		// 调用查询会员中心 返回会员分组id和买家等级
		OtherCenterResDTO<MemberGroupDTO> memberGroupDTO = memberCenterRAO.selectBuyCodeSellCode(
				orderTemp.getSellerCode(), orderCreateInfoReqDTO.getBuyerCode(), messageId);
		if (!ResultCodeEnum.SUCCESS.getCode().equals(memberGroupDTO.getOtherCenterResponseCode())) {
			orderItemSkuPriceDTORes
					.setOtherCenterResponseCode(memberGroupDTO.getOtherCenterResponseCode());
			orderItemSkuPriceDTORes
					.setOtherCenterResponseMsg(memberGroupDTO.getOtherCenterResponseMsg());
			return orderItemSkuPriceDTORes;
		}
		String buyerGrade = memberGroupDTO.getOtherCenterResult().getBuyerGrade();
		queryCommonItemSkuPriceDTO
				.setBuyerGrade(buyerGrade);
		buyerGradeMap.put("buyerGrade", queryCommonItemSkuPriceDTO.getBuyerGrade());
		queryCommonItemSkuPriceDTO
				.setMemberGroupId(memberGroupDTO.getOtherCenterResult().getGroupId());
		queryCommonItemSkuPriceDTO.setIsHasDevRelation(orderItemTemp.getIsHasDevRelation());
		queryCommonItemSkuPriceDTO.setIsLogin(Integer.parseInt(GoodCenterEnum.IS_LOGIN.getCode()));
		LOGGER.info("商品渠道编码:" + queryCommonItemSkuPriceDTO.getItemChannelCode());
		if (Constant.PRODUCT_CHANNEL_CODE_OUTLINE
				.equals(queryCommonItemSkuPriceDTO.getItemChannelCode())) {
			OtherCenterResDTO<Long> memberInfo = memberCenterRAO
					.getMemberIdByCode(orderTemp.getSellerCode(), messageId);
			if (!ResultCodeEnum.SUCCESS.getCode().equals(memberInfo.getOtherCenterResponseCode())) {
				orderItemSkuPriceDTORes
						.setOtherCenterResponseCode(memberInfo.getOtherCenterResponseCode());
				orderItemSkuPriceDTORes
						.setOtherCenterResponseMsg(memberInfo.getOtherCenterResponseMsg());
				return orderItemSkuPriceDTORes;
			}
			Long memberId = memberInfo.getOtherCenterResult();
			OtherCenterResDTO<Boolean> flag = goodsCenterRAO.canProductPlusSaleBySeller(memberId,
					queryCommonItemSkuPriceDTO.getItemChannelCode(),
					mallSkuOutDTO.getThirdCategoryId(), mallSkuOutDTO.getBrandId(), messageId);
			queryCommonItemSkuPriceDTO
					.setIsCanSellProdplusItem(flag.getOtherCenterResult() ? 1 : 0);
		}
		orderItemSkuPriceDTORes = priceCenterRAO.queryOrderItemSkuPrice(queryCommonItemSkuPriceDTO,
				messageId);
		//预售商品取预售价格|预售vip价格
		String orderFrom = orderTemp.getOrderFrom();
		if(orderFrom.equals(OrderStatusEnum.ORDER_FROM_PRESALE.getCode())){
			HzgPriceDTO hzgPrice = orderItemSkuPriceDTORes.getOtherCenterResult().getHzgPrice();
			if(null != hzgPrice){
				if(buyerGrade.equals(OrderStatusEnum.BUYER_GRADE_VIP.getCode())){
					orderItemSkuPriceDTORes.getOtherCenterResult().setSalePrice(hzgPrice.getVipPrice());
				}else{
					orderItemSkuPriceDTORes.getOtherCenterResult().setSalePrice(hzgPrice.getSalePrice());
				}
			}
		}
		return orderItemSkuPriceDTORes;
	}

	/*
	 * 准备锁定优惠券和秒杀
	 */
	private void preLockCoupon(OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			List<OrderItemPromotionDTO> orderItemPromotionList, MemberBaseInfoDTO buyerInfoDTO) {
		List<OrderCreateListInfoReqDTO> orderList = orderCreateInfoReqDTO.getOrderList();
		for (int i = 0; i < orderList.size(); i++) {
			OrderCreateListInfoReqDTO orderTemp = orderList.get(i);
			List<OrderCreateItemListInfoReqDTO> orderItemList = orderTemp.getOrderItemList();

			if (null != orderItemList && orderItemList.size() > 0) {
				for (int j = 0; j < orderItemList.size(); j++) {
					OrderCreateItemListInfoReqDTO orderItemTemp = orderItemList.get(j);
					assembleParamPreLockCoupon(orderCreateInfoReqDTO, orderTemp, orderItemTemp,
							orderItemPromotionList, buyerInfoDTO);
				}
			}
		}
	}

	/*
	 * 组装参数-准备锁定优惠券
	 */
	private void assembleParamPreLockCoupon(OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateListInfoReqDTO orderTemp, OrderCreateItemListInfoReqDTO orderItemTemp,
			List<OrderItemPromotionDTO> orderItemPromotionList, MemberBaseInfoDTO buyerInfoDTO) {
		OrderItemPromotionDTO orderItemPromotionDTO = new OrderItemPromotionDTO();
		List<String> couponCodeList = orderCreateInfoReqDTO.getCouponCodeList();
		if (null != couponCodeList && couponCodeList.size() > 0) {
			orderItemPromotionDTO.setCouponCode(couponCodeList.get(0));
		}
		orderItemPromotionDTO.setOrderNo(orderTemp.getOrderNo());
		orderItemPromotionDTO.setOrderItemNo(orderItemTemp.getOrderItemNo());
		orderItemPromotionDTO.setQuantity(orderItemTemp.getGoodsCount().intValue());
		orderItemPromotionDTO.setBuyerCode(orderCreateInfoReqDTO.getBuyerCode());
		orderItemPromotionDTO.setPromotionType(orderCreateInfoReqDTO.getPromotionType());
		orderItemPromotionDTO.setPromotionId(orderCreateInfoReqDTO.getPromotionId() == null ? ""
				: orderCreateInfoReqDTO.getPromotionId().toString());
		orderItemPromotionDTO.setOperaterId(buyerInfoDTO.getId());
		orderItemPromotionDTO.setOperaterName(buyerInfoDTO.getCompanyName());
		if (orderItemTemp.isHasTimelimitedFlag()) {
			orderItemPromotionDTO.setLevelCode(orderItemTemp.getTimelimitedInfo().getLevelCode());
			LOGGER.info("秒杀商品--查询秒杀laveCode:" + orderItemTemp.getTimelimitedInfo().getLevelCode());
		} else {
			List<OrderItemCouponDTO> avalibleCouponList = orderItemTemp.getAvalibleCouponList();
			if (null != avalibleCouponList && avalibleCouponList.size() > 0) {
				orderItemPromotionDTO.setLevelCode(avalibleCouponList.get(0).getLevelCode());
			}
		}
		orderItemPromotionDTO.setDiscountAmount(orderItemTemp.getTotalDiscountAmount());
		orderItemPromotionDTO.setSeckillLockNo(orderCreateInfoReqDTO.getSeckillLockNo());
		orderItemPromotionList.add(orderItemPromotionDTO);
	}

	/*
	 * 插入订单表组装参数
	 */
	private void assembleOrderParam(Map<String, Object> orderParam, Integer hasProductplusFlag,
			Long totalGoodsCount, BigDecimal totalGoodsAmount, BigDecimal platformDiscountAmount,
			BigDecimal orderTotalAmount, BigDecimal orderPayAmount, int isOutDistribtion) {
		orderParam.put("hasProductplusFlag", hasProductplusFlag);
		orderParam.put("totalGoodsCount", totalGoodsCount == null ? "0" : totalGoodsCount);
		orderParam.put("totalGoodsAmount", totalGoodsAmount == null ? "0" : totalGoodsAmount);
		orderParam.put("platformDiscountAmount",
				platformDiscountAmount == null ? "0" : platformDiscountAmount);
		orderParam.put("orderTotalAmount", orderTotalAmount == null ? "0" : orderTotalAmount);
		orderParam.put("orderPayAmount", orderPayAmount == null ? "0" : orderPayAmount);
		orderParam.put("isOutDistribtion", isOutDistribtion);
	}

	/*
	 * 校验内外部供应商的商品数量是否大于提交数量
	 */
	private OrderCreateInfoDMO validateInternalSupplierQuantity(
			MallSkuStockOutDTO mallSkuStockOutDTO, OrderCreateItemListInfoReqDTO orderItemTemp,
			OrderCreateInfoDMO orderCreateInfoDMO) {
		orderCreateInfoDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());

		if (null == mallSkuStockOutDTO.getItemSkuPublishInfo()) {
			// 没有库存信息，或者区域和包厢的已经下架
			orderCreateInfoDMO.setResultCode(
					ResultCodeEnum.GOODSCENTER_NOT_STOCKINFO_OR_GOODS_OFF_SHELF.getCode());
			orderCreateInfoDMO.setResultMsg(
					ResultCodeEnum.GOODSCENTER_NOT_STOCKINFO_OR_GOODS_OFF_SHELF.getMsg());
			return orderCreateInfoDMO;
		}
		Integer displayQuantity = mallSkuStockOutDTO.getItemSkuPublishInfo().getDisplayQuantity();// 显示库存，可用库存
		Integer reserveQuantity = mallSkuStockOutDTO.getItemSkuPublishInfo().getReserveQuantity(); // 锁定库存
		Long preReserveQuantity = orderItemTemp.getGoodsCount();
		// 去商品中心校验库存数量是不是大于等于提交的数量
		if (displayQuantity - reserveQuantity - preReserveQuantity < ZERO) {
			orderCreateInfoDMO.setResultCode(
					ResultCodeEnum.GOODSCENTER_QUERY_SKUINFO_STOCK_LESS_THAN_ZERO.getCode());
			orderCreateInfoDMO.setResultMsg(
					ResultCodeEnum.GOODSCENTER_QUERY_SKUINFO_STOCK_LESS_THAN_ZERO.getMsg());
		}
		return orderCreateInfoDMO;
	}

	/*
	 * 检验京东库存是否足够
	 */
	private OrderCreateInfoDMO validateJDGoodsStock(BatchGetStockReqDTO batchGetStockReqDTO,
			OrderCreateInfoDMO orderCreateInfoDMO, String messageId) {
		orderCreateInfoDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		OtherCenterResDTO<String> otherCenterRes = goodsPlusRAO.queryStock4JD(batchGetStockReqDTO,
				messageId);
		if (!otherCenterRes.getOtherCenterResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
			orderCreateInfoDMO.setResultCode(otherCenterRes.getOtherCenterResponseCode());
			orderCreateInfoDMO.setResultMsg(otherCenterRes.getOtherCenterResponseMsg());
		}
		return orderCreateInfoDMO;
	}

	/*
	 * 插入订单表
	 */
	private OrderCreateInfoDMO insetTradeOrders(MemberBaseInfoDTO buyerInfoDTO,
			OrderCreateInfoReqDTO orderCreateInfoReqDTO, OrderCreateListInfoReqDTO orderTemp,
			Map<String, Object> orderParam, OrderCreateInfoDMO orderCreateInfoDMO,
			BigDecimal totalFreight) {
		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
		tradeOrdersDMO.setTradeNo(orderCreateInfoReqDTO.getTradeNo());
		tradeOrdersDMO.setOrderNo(orderTemp.getOrderNo());
		tradeOrdersDMO.setBuyerCode(orderCreateInfoReqDTO.getBuyerCode());
		tradeOrdersDMO.setBuyerType(buyerInfoDTO.getMemberType());
		tradeOrdersDMO.setBuyerName(buyerInfoDTO.getCompanyName());
		tradeOrdersDMO.setSellerCode(orderTemp.getSellerCode());

		// 调用
		// memberCallCenterService查询会员信息,不用判读是否成功,如果会员中心没有查到数据,直接让程序抛出异常,可以回滚插入的数据
		String messageId = orderCreateInfoReqDTO.getMessageId();
		OtherCenterResDTO<MemberBaseInfoDTO> memberBaseInfoResDTO = memberCenterRAO
				.selectMemberBaseName(orderTemp.getSellerCode(),
						MemberCenterEnum.MEMBER_TYPE_SELLER.getCode(), messageId);

		MemberBaseInfoDTO sellerInfoDTO = memberBaseInfoResDTO.getOtherCenterResult();
		tradeOrdersDMO.setSellerType(sellerInfoDTO.getSellerType());
		tradeOrdersDMO.setSellerName(sellerInfoDTO.getCompanyName());
		tradeOrdersDMO.setShopId(orderTemp.getShopId());
		tradeOrdersDMO.setShopName(orderTemp.getShopName());
		tradeOrdersDMO.setSalesType(OrderStatusEnum.ORDER_SALE_TYPE1.getCode());// 商城下单是正常销售
		tradeOrdersDMO
				.setTotalGoodsCount(Integer.parseInt(orderParam.get("totalGoodsCount").toString()));
		tradeOrdersDMO
				.setTotalGoodsAmount(new BigDecimal(orderParam.get("totalGoodsAmount").toString()));
		tradeOrdersDMO.setPlatformDiscountAmount(
				new BigDecimal(orderParam.get("platformDiscountAmount").toString()));
		tradeOrdersDMO.setTotalDiscountAmount(
				new BigDecimal(orderParam.get("platformDiscountAmount").toString()));
		tradeOrdersDMO
				.setOrderTotalAmount(new BigDecimal(orderParam.get("orderTotalAmount").toString()));
		tradeOrdersDMO
				.setOrderPayAmount(new BigDecimal(orderParam.get("orderPayAmount").toString()));
		tradeOrdersDMO.setIsOutDistribtion(
				Integer.valueOf(orderParam.get("isOutDistribtion").toString()));
		if (null != orderParam.get("hasProductplusFlag")) {
			tradeOrdersDMO.setHasProductplusFlag(
					Integer.valueOf(orderParam.get("hasProductplusFlag").toString()));
			OtherCenterResDTO<Long> memberInfo = memberCenterRAO
					.getMemberIdByCode(orderCreateInfoReqDTO.getBuyerCode(), messageId);
			if (ResultCodeEnum.SUCCESS.getCode().equals(memberInfo.getOtherCenterResponseCode())) {
				Long memberId = memberInfo.getOtherCenterResult();
				OtherCenterResDTO<MemberBuyerGradeInfoDTO> memberBuyerGradeInfo = memberCenterRAO
						.queryBuyerGradeInfo(memberId, messageId);
				if (ResultCodeEnum.SUCCESS.getCode()
						.equals(memberBuyerGradeInfo.getOtherCenterResponseCode())) {
					MemberBuyerGradeInfoDTO memberBuyerGradeInfoRes = memberBuyerGradeInfo
							.getOtherCenterResult();
					if (null != memberBuyerGradeInfoRes
							&& null != memberBuyerGradeInfoRes.getIsVip()) {
						tradeOrdersDMO
								.setIsVipMember(memberBuyerGradeInfoRes.getIsVip().intValue());
					}
				}
			}
		}
		if (StringUtilHelper.isNotNull(orderCreateInfoReqDTO.getPromotionType())
				&& OrderStatusEnum.PROMOTION_TYPE_SECKILL.getCode()
						.equals(orderCreateInfoReqDTO.getPromotionType())) {
			tradeOrdersDMO.setIsTimelimitedOrder(
					Integer.valueOf(OrderStatusEnum.IS_TIMELIMITED_ORDER.getCode()));
		}
		tradeOrdersDMO.setBuyerRemarks(orderTemp.getOrderRemarks());
		tradeOrdersDMO.setIsNeedInvoice(orderCreateInfoReqDTO.getIsNeedInvoice());
		tradeOrdersDMO.setInvoiceType(orderCreateInfoReqDTO.getInvoiceType());
		tradeOrdersDMO.setInvoiceNotify(orderCreateInfoReqDTO.getInvoiceNotify());
		tradeOrdersDMO.setInvoiceCompanyName(orderCreateInfoReqDTO.getInvoiceCompanyName());
		tradeOrdersDMO.setTaxManId(orderCreateInfoReqDTO.getTaxManId());
		tradeOrdersDMO.setBankName(orderCreateInfoReqDTO.getBankName());
		tradeOrdersDMO.setBankAccount(orderCreateInfoReqDTO.getBankAccount());
		tradeOrdersDMO.setContactPhone(orderCreateInfoReqDTO.getContactPhone());
		tradeOrdersDMO.setInvoiceAddress(orderCreateInfoReqDTO.getInvoiceAddress());
		tradeOrdersDMO.setDeliveryType(orderCreateInfoReqDTO.getDeliveryType());
		// 自提的时候 使用会员注册的电话地址等信息
		String consigneeName = "";
		String consigneePhoneNum = "";
		String consigneeAddress = "";
		String consigneeAddressProvince = "";
		String consigneeAddressCity = "";
		String consigneeAddressDistrict = "";
		String consigneeAddressTown = "";
		String consigneeAddressDetail = "";

		if (OrderStatusEnum.ORDER_DELIVERY_TYPE_SINCE.getCode()
				.equals(tradeOrdersDMO.getDeliveryType())) {
			LOGGER.info("页面传入的是自提,则查询会员中心，将注册地址插入订单表开始messageId:" + messageId);
			OtherCenterResDTO<Long> memberInfo = memberCenterRAO
					.getMemberIdByCode(orderCreateInfoReqDTO.getBuyerCode(), messageId);
			Long memberId = memberInfo.getOtherCenterResult();
			OtherCenterResDTO<MemberDetailInfo> memberDetailInfo = memberCenterRAO
					.getMemberDetailById(memberId, messageId);
			MemberDetailInfo memberTemp = memberDetailInfo.getOtherCenterResult();
			MemberBaseInfoDTO memberBaseInfoDTO = memberTemp.getMemberBaseInfoDTO();

			consigneeName = memberBaseInfoDTO.getArtificialPersonName();
			consigneePhoneNum = memberBaseInfoDTO.getArtificialPersonMobile();
			consigneeAddress = memberBaseInfoDTO.getLocationAddr();
			consigneeAddressProvince = memberBaseInfoDTO.getLocationProvince();
			consigneeAddressCity = memberBaseInfoDTO.getLocationCity();
			consigneeAddressDistrict = memberBaseInfoDTO.getLocationCounty();
			consigneeAddressTown = memberBaseInfoDTO.getLocationTown();
			consigneeAddressDetail = memberBaseInfoDTO.getLocationDetail();
		} else {
			consigneeName = orderCreateInfoReqDTO.getConsigneeName();
			consigneePhoneNum = orderCreateInfoReqDTO.getConsigneePhoneNum();
			consigneeAddress = orderCreateInfoReqDTO.getConsigneeAddress();
			consigneeAddressProvince = orderCreateInfoReqDTO.getConsigneeAddressProvince();
			consigneeAddressCity = orderCreateInfoReqDTO.getConsigneeAddressCity();
			consigneeAddressDistrict = orderCreateInfoReqDTO.getConsigneeAddressDistrict();
			consigneeAddressTown = orderCreateInfoReqDTO.getConsigneeAddressTown();
			consigneeAddressDetail = orderCreateInfoReqDTO.getConsigneeAddressDetail();
		}

		tradeOrdersDMO.setConsigneeName(consigneeName);
		tradeOrdersDMO.setConsigneePhoneNum(consigneePhoneNum);
		tradeOrdersDMO.setConsigneeAddress(consigneeAddress);
		tradeOrdersDMO.setConsigneeAddressProvince(consigneeAddressProvince);
		tradeOrdersDMO.setConsigneeAddressCity(consigneeAddressCity);
		tradeOrdersDMO.setConsigneeAddressDistrict(consigneeAddressDistrict);
		tradeOrdersDMO.setConsigneeAddressTown(consigneeAddressTown);
		tradeOrdersDMO.setConsigneeAddressDetail(consigneeAddressDetail);

		tradeOrdersDMO.setPostCode(orderCreateInfoReqDTO.getPostCode());
		tradeOrdersDMO.setCreateOrderTime(DateUtil.getSystemTime());
		tradeOrdersDMO.setTotalFreight(totalFreight);
		if (OrderStatusEnum.OUT_DISTRIBTION.getCode()
				.equals(tradeOrdersDMO.getIsOutDistribtion() == null ? ""
						: tradeOrdersDMO.getIsOutDistribtion().toString())) {
			tradeOrdersDMO.setOrderStatus(OrderStatusEnum.PRE_CHECK.getCode());
		} else {
			tradeOrdersDMO.setOrderStatus(OrderStatusEnum.PRE_PAY.getCode());
		}
		tradeOrdersDMO.setPayStatus(PayStatusEnum.NON_PAY.getCode());
		tradeOrdersDMO.setOrderFrom(orderTemp.getOrderFrom());
		tradeOrdersDMO.setSite(orderCreateInfoReqDTO.getSite());

		// 秒杀商品不需要锁定商品中心商品,也不需要校验商品库存
		String promotionType = orderCreateInfoReqDTO.getPromotionType() == null ? ""
				: orderCreateInfoReqDTO.getPromotionType();
		// 如果是秒杀就不查价格中心
		if (promotionType.equals(OrderStatusEnum.PROMOTION_TYPE_SECKILL.getCode())) {

			Date payTimeLimit = orderTemp.getPayTimeLimit();
			tradeOrdersDMO.setPayTimeLimit(DateUtil.getDaysSqlTime(payTimeLimit));
		} else {
			tradeOrdersDMO.setPayTimeLimit(DateUtil.getDaysTime(ONE));
		}
		tradeOrdersDMO.setHasUsedCoupon(orderTemp.isHasUsedCoupon()
				? Integer.valueOf(OrderStatusEnum.IS_SKILL_ORDER.getCode())
				: Integer.valueOf(OrderStatusEnum.NOT_SKILL_ORDER.getCode()));
		tradeOrdersDMO.setIsTimelimitedOrder(orderTemp.isTimelimitedOrder()
				? Integer.valueOf(OrderStatusEnum.USE_COUPON.getCode())
				: Integer.valueOf(OrderStatusEnum.NOT_USE_COUPON.getCode()));
		Long startTime = System.currentTimeMillis();
		int update = tradeOrdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
		int insertTradeOrdersRes = 0;
		if (update == ZERO) {
			insertTradeOrdersRes = insertTradeOrders(tradeOrdersDMO);
		} else {
			insertTradeOrdersRes = ONE;
		}
		if (insertTradeOrdersRes == ONE) {
			orderCreateInfoDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		}
		Long endTime = System.currentTimeMillis();
		LOGGER.info("MessageId:{} 创建订单--插入订单和订单状态履历表 耗时:{}", orderCreateInfoReqDTO.getMessageId(),
				endTime - startTime);
		insertTradeOrderStatusHistory(tradeOrdersDMO);
		return orderCreateInfoDMO;
	}

	/*
	 * 插入订单行表
	 * 
	 * @param mallSkuOutDTO 商品详情
	 * 
	 * @param orderItemTemp 订单行DTO对象
	 * 
	 * @pram itemTotalDiscountAmount 订单行优惠总金额
	 */
	private OrderCreateInfoDMO insertTradeOrderItems(OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateListInfoReqDTO orderItem, OrderCreateItemListInfoReqDTO orderItemTemp,
			TradeOrderItemsDMO tradeOrderItemsDMO, OrderCreateInfoDMO orderCreateInfoDMO) {
		tradeOrderItemsDMO.setSalePrice(orderItemTemp.getSalePrice());// 销售单价
		tradeOrderItemsDMO.setGoodsPrice(orderItemTemp.getGoodsPrice());
		tradeOrderItemsDMO.setCostPrice(orderItemTemp.getCostPrice());
		tradeOrderItemsDMO.setPriceFloatingRatio(orderItemTemp.getPriceFloatingRatio());
		BigDecimal commissionRatio = orderItemTemp.getCommissionRatio();
		tradeOrderItemsDMO.setCommissionRatio(commissionRatio);
		tradeOrderItemsDMO.setGoodsPriceType(orderItemTemp.getGoodsPriceType().toString());
		tradeOrderItemsDMO.setGoodsAmount(orderItemTemp.getGoodsPrice()
				.multiply(new BigDecimal(orderItemTemp.getGoodsCount())));
		if (null != commissionRatio) {
			tradeOrderItemsDMO.setCommissionAmount(new BigDecimal(
					df1.format(commissionRatio.multiply(tradeOrderItemsDMO.getGoodsAmount()))));
		}

		tradeOrderItemsDMO.setOrderNo(orderItem.getOrderNo());
		tradeOrderItemsDMO.setOrderItemNo(orderItemTemp.getOrderItemNo());
		tradeOrderItemsDMO.setChannelCode(orderItemTemp.getChannelCode());
		tradeOrderItemsDMO.setItemCode(orderItemTemp.getItemCode());
		tradeOrderItemsDMO.setErpFirstCategoryCode(orderItemTemp.getErpFirstCategoryCode());
		tradeOrderItemsDMO.setGoodsName(orderItemTemp.getGoodsName());
		tradeOrderItemsDMO.setSkuCode(orderItemTemp.getSkuCode());
		tradeOrderItemsDMO.setSkuPictureUrl(orderItemTemp.getSkuPictureUrl());
		// tradeOrderItemsDMO.setItemSpuId();//商品模板ID
		tradeOrderItemsDMO.setFirstCategoryId(orderItemTemp.getFirstCategoryId());// 一级类目ID
		tradeOrderItemsDMO.setFirstCategoryName(orderItemTemp.getFirstCategoryName());
		tradeOrderItemsDMO.setSecondCategoryId(orderItemTemp.getSecondCategoryId());
		tradeOrderItemsDMO.setSecondCategoryName(orderItemTemp.getSecondCategoryName());
		tradeOrderItemsDMO.setThirdCategoryId(orderItemTemp.getThirdCategoryId());
		tradeOrderItemsDMO.setThirdCategoryName(orderItemTemp.getThirdCategoryName());
		String categoryIdList = (orderItemTemp.getFirstCategoryId() == null ? ""
				: orderItemTemp.getFirstCategoryId().toString())
				+ ","
				+ (orderItemTemp.getSecondCategoryId() == null ? ""
						: orderItemTemp.getSecondCategoryId().toString())
				+ "," + (orderItemTemp.getThirdCategoryId() == null ? ""
						: orderItemTemp.getThirdCategoryId().toString());
		String categoryNameList = (orderItemTemp.getFirstCategoryName() == null ? ""
				: orderItemTemp.getFirstCategoryName())
				+ ","
				+ (orderItemTemp.getSecondCategoryName() == null ? ""
						: orderItemTemp.getSecondCategoryName())
				+ "," + (orderItemTemp.getThirdCategoryName() == null ? ""
						: orderItemTemp.getThirdCategoryName());
		tradeOrderItemsDMO.setCategoryIdList(categoryIdList);
		tradeOrderItemsDMO.setCategoryNameList(categoryNameList);
		tradeOrderItemsDMO.setBrandId(orderItemTemp.getBrandId());
		tradeOrderItemsDMO.setBrandName(orderItemTemp.getBrandName());
		tradeOrderItemsDMO.setGoodsCount(orderItemTemp.getGoodsCount().intValue());
		tradeOrderItemsDMO.setIsVipItem(orderItemTemp.getIsVipItem());
		tradeOrderItemsDMO.setVipItemType(orderItemTemp.getVipItemType() == null ? ""
				: orderItemTemp.getVipItemType().toString());
		tradeOrderItemsDMO.setVipSyncFlag(orderItemTemp.getVipSyncFlag());

		// tradeOrderItemsDMO.setGoodsPriceInfo(orderItemSkuPriceDTO.getGoodsPriceInfo());//TODO这个不直接入库-2017-01-22
		tradeOrderItemsDMO.setShopFreightTemplateId(orderItemTemp.getShopFreightTemplateId());
		tradeOrderItemsDMO.setGoodsFreight(orderItemTemp.getGoodsFreight() == null
				? new BigDecimal(0) : orderItemTemp.getGoodsFreight());// 运费金额
		BigDecimal totalDiscountAmount = orderItemTemp.getTotalDiscountAmount() == null
				? new BigDecimal(0) : orderItemTemp.getTotalDiscountAmount();
		tradeOrderItemsDMO.setTotalDiscountAmount(totalDiscountAmount);
		tradeOrderItemsDMO.setPlatformDiscountAmount(totalDiscountAmount);// 现在还没有店铺优惠
		BigDecimal goodsAmt = tradeOrderItemsDMO.getGoodsAmount() == null ? new BigDecimal(0)
				: tradeOrderItemsDMO.getGoodsAmount();
		BigDecimal goodsFreight = tradeOrderItemsDMO.getGoodsFreight() == null ? new BigDecimal(0)
				: tradeOrderItemsDMO.getGoodsFreight();
		BigDecimal orderItemTotalAmount = goodsAmt.add(goodsFreight).subtract(totalDiscountAmount);
		tradeOrderItemsDMO.setOrderItemTotalAmount(orderItemTotalAmount);
		tradeOrderItemsDMO.setOrderItemPayAmount(orderItemTotalAmount);
		tradeOrderItemsDMO.setGoodsRealPrice((goodsAmt.subtract(totalDiscountAmount)).divide(
				new BigDecimal(orderItemTemp.getGoodsCount()), 4, BigDecimal.ROUND_HALF_EVEN));
		tradeOrderItemsDMO.setItemSpuCode(orderItemTemp.getItemSpuCode());
		tradeOrderItemsDMO.setOuterSkuCode(orderItemTemp.getOuterSkuCode());

		if (null != orderItem.getExtendMap()
				&& StringUtilHelper.isNotNull(tradeOrderItemsDMO.getChannelCode())
				&& tradeOrderItemsDMO.getChannelCode()
						.startsWith(GoodCenterEnum.EXTERNAL_CHANNELS.getCode())) {
			Map<String, Object> extendMap = orderItem.getExtendMap();
			String outerChannelOrderNo = extendMap.get("outerChannelOrderNo") == null ? ""
					: extendMap.get("outerChannelOrderNo").toString();
			String outerChannelPuchaseStatus = extendMap.get("outerChannelPuchaseStatus") == null
					? "" : extendMap.get("outerChannelPuchaseStatus").toString();
			tradeOrderItemsDMO.setOuterChannelPuchaseStatus(outerChannelPuchaseStatus);
			tradeOrderItemsDMO.setOuterChannelOrderNo(outerChannelOrderNo);
		}
		Map<String, Object> extendMap = orderItemTemp.getExtendMap();
		if (null != extendMap) {
			tradeOrderItemsDMO.setSkuErpCode(extendMap.get("skuErpCode") == null ? ""
					: extendMap.get("skuErpCode").toString());
			tradeOrderItemsDMO.setSkuEanCode(
					extendMap.get("eanCode") == null ? "" : extendMap.get("eanCode").toString());
			tradeOrderItemsDMO.setErpFiveCategoryCode(extendMap.get("erpFiveCategoryCode") == null
					? "" : extendMap.get("erpFiveCategoryCode").toString());
		}

		if (totalDiscountAmount.compareTo(BigDecimal.ZERO) == 0) {
			tradeOrderItemsDMO
					.setHasUsedCoupon(Integer.valueOf(OrderStatusEnum.NOT_USED_COUPON.getCode()));
		} else {
			tradeOrderItemsDMO
					.setHasUsedCoupon(Integer.valueOf(OrderStatusEnum.HAS_USED_COUPON.getCode()));
		}
		if (null != orderItemTemp.getIsBoxFlag()) {
			tradeOrderItemsDMO.setIsBoxFlag(orderItemTemp.getIsBoxFlag());
		}
		// tradeOrderItemsDMO.setOuterChannelOrderNo(outerChannelOrderNo);//商品+外部供应商订单号
		tradeOrderItemsDMO.setIsOutDistribtion(orderItemTemp.getIsOutDistribtion());// 是否超出配送范围
																					// 0:否,1:-是
		if (OrderStatusEnum.OUT_DISTRIBTION.getCode()
				.equals(orderItemTemp.getIsOutDistribtion() == null ? ""
						: orderItemTemp.getIsOutDistribtion().toString())) {
			tradeOrderItemsDMO.setOrderItemStatus(OrderStatusEnum.PRE_CHECK.getCode());
		} else {
			tradeOrderItemsDMO.setOrderItemStatus(OrderStatusEnum.PRE_PAY.getCode());
		}
		Long startTime = System.currentTimeMillis();
		int update = tradeOrderItemsDAO.updateTradeOrderItemsByItemNo(tradeOrderItemsDMO);
		if (update == ZERO) {
			insertTradeOrderItems(tradeOrderItemsDMO);
		}
		Long endTime = System.currentTimeMillis();
		LOGGER.info("MessageId:{} 创建订单--更新插入订单行 耗时:{}", orderCreateInfoReqDTO.getMessageId(),
				endTime - startTime);
		insertTradeOrderItemsStatusHistory(tradeOrderItemsDMO);
		return orderCreateInfoDMO;
	}

	/*
	 * 插入订单行优惠信息表
	 * 
	 */
	private void insertTradeOrderItemsDiscount(OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateListInfoReqDTO orderTemp, OrderCreateItemListInfoReqDTO orderItemTemp) {
		TradeOrderItemsDiscountDMO tradeOrderItemsDiscountDMO = new TradeOrderItemsDiscountDMO();
		tradeOrderItemsDiscountDMO.setOrderNo(orderTemp.getOrderNo());
		tradeOrderItemsDiscountDMO.setOrderItemNo(orderItemTemp.getOrderItemNo());
		tradeOrderItemsDiscountDMO.setBuyerCode(orderCreateInfoReqDTO.getBuyerCode());
		tradeOrderItemsDiscountDMO.setSellerCode(orderTemp.getSellerCode());
		tradeOrderItemsDiscountDMO.setShopId(orderTemp.getShopId());
		tradeOrderItemsDiscountDMO
				.setPromotionId(orderCreateInfoReqDTO.getPromotionId().toString());
		List<OrderItemCouponDTO> avalibleCouponList = orderItemTemp.getAvalibleCouponList();
		LOGGER.info(
				"从促销中心返回优惠券信息avalibleCouponList:" + JSONObject.toJSONString(avalibleCouponList));
		String levelCode = "";
		String buyerCouponCode = "";
		String couponType = "";
		if (OrderStatusEnum.PROMOTION_TYPE_COUPON.getCode()
				.equals(orderCreateInfoReqDTO.getPromotionType())) {
			if (null != avalibleCouponList && avalibleCouponList.size() > 0) {
				OrderItemCouponDTO res = avalibleCouponList.get(0);
				levelCode = res.getLevelCode();
				buyerCouponCode = res.getBuyerCouponCode();
				couponType = res.getCouponType();
			}
			// 目前只有平台优惠
			tradeOrderItemsDiscountDMO
					.setCouponProviderType(OrderStatusEnum.COUPON_PROVIDER_TYPE_PLATFORM.getCode());
			tradeOrderItemsDiscountDMO.setBuyerCouponCode(buyerCouponCode);// 优惠券号
			tradeOrderItemsDiscountDMO.setCouponDiscount(orderItemTemp.getTotalDiscountAmount());
		} else {
			TimelimitedInfoDTO timelimitedInfoDTO = orderItemTemp.getTimelimitedInfo();
			if (null != levelCode) {
				levelCode = timelimitedInfoDTO.getLevelCode();
			}
			couponType = OrderStatusEnum.COUPON_TYPE_SKILL.getCode();
		}
		tradeOrderItemsDiscountDMO.setLevelCode(levelCode);// 层级编码

		tradeOrderItemsDiscountDMO.setCouponType(couponType);
		String messageId = orderCreateInfoReqDTO.getMessageId();
		LOGGER.info("从促销中心查询出优惠券信息MessageId:" + messageId + "levelCode:" + levelCode
				+ " buyerCouponCode" + buyerCouponCode + " couponType" + couponType);
		Long startTime = System.currentTimeMillis();
		int update = tradeOrderItemsDiscountDAO
				.updateTradeOrderItemsDiscount(tradeOrderItemsDiscountDMO);
		if (update == ZERO) {
			insertTradeOrderItemsDiscount(tradeOrderItemsDiscountDMO);
		}
		Long endTime = System.currentTimeMillis();
		LOGGER.info("MessageId:{} 创建订单--更新和插入收付款表 耗时:{}", messageId, endTime - startTime);
	}

	/*
	 * 插入订单状态履历表
	 */
	private void insertTradeOrderStatusHistory(final TradeOrdersDMO tradeOrdersDMO) {
		try {
			new Thread(new Runnable() {
				public void run() {
					TradeOrderStatusHistoryDMO record = new TradeOrderStatusHistoryDMO();
					record.setOrderNo(tradeOrdersDMO.getOrderNo());
					record.setOrderStatus(tradeOrdersDMO.getOrderStatus());
					String orderStatus = tradeOrdersDMO.getOrderStatus();
					if (StringUtilHelper.isNotNull(orderStatus)) {
						String orderStatusText = "";
						if (orderStatus.equals(OrderStatusEnum.PRE_CHECK.getCode())) {
							orderStatusText = OrderStatusEnum.PRE_CHECK.getMsg();
						} else if (orderStatus.equals(OrderStatusEnum.PRE_PAY.getCode())) {
							orderStatusText = OrderStatusEnum.PRE_PAY.getMsg();
						}
						record.setOrderStatusText(orderStatusText);
					}
					tradeOrderStatusHistoryService.insertTradeOrderStatusHistory(record);
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("插入订单状态履历表出现异常-(此异常不需要回滚)");
		}
	}

	/*
	 * 插入订单行状态履历表
	 */
	private void insertTradeOrderItemsStatusHistory(final TradeOrderItemsDMO tradeOrderItemsDMO) {
		try {
			new Thread(new Runnable() {
				public void run() {
					TradeOrderItemsStatusHistoryDMO record = new TradeOrderItemsStatusHistoryDMO();
					record.setOrderItemNo(tradeOrderItemsDMO.getOrderItemNo());
					record.setOrderItemStatus(tradeOrderItemsDMO.getOrderItemStatus());
					String orderItemStatus = tradeOrderItemsDMO.getOrderItemStatus();
					if (StringUtilHelper.isNotNull(orderItemStatus)) {
						String orderStatusText = "";
						if (orderItemStatus.equals(OrderStatusEnum.PRE_CHECK.getCode())) {
							orderStatusText = OrderStatusEnum.PRE_CHECK.getMsg();
						} else if (orderItemStatus.equals(OrderStatusEnum.PRE_PAY.getCode())) {
							orderStatusText = OrderStatusEnum.PRE_PAY.getMsg();
						}
						record.setOrderItemStatusText(orderStatusText);
					}
					tradeOrderItemStatusHistoryService.insertTradeOrderItemStatusHistory(record);
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("插入订单行状态履历表出现异常-(此异常不需要回滚)");
		}
	}

	@Override
	public int insertTradeOrderItems(TradeOrderItemsDMO tradeOrderItemsDMO) {
		// TODO Auto-generated method stub
		return tradeOrderItemsDAO.insertTradeOrderItems(tradeOrderItemsDMO);
	}

	@Override
	public int insertTradeOrders(TradeOrdersDMO tradeOrdersDMO) {
		// TODO Auto-generated method stub
		return tradeOrdersDAO.insertTradeOrders(tradeOrdersDMO);
	}

	@Override
	public int insertTradeOrderItemsDiscount(
			TradeOrderItemsDiscountDMO tradeOrderItemsDiscountDMO) {
		// TODO Auto-generated method stub
		return tradeOrderItemsDiscountDAO.insertTradeOrderItemsDiscount(tradeOrderItemsDiscountDMO);
	}

	@Override
	public OrderCreateInfoResDTO assembleOrder4huilin(String messageId,
			OrderCreate4huilinReqDTO orderCreate4huilinReqDTO,
			OrderCreateInfoResDTO orderCreateInfoResDTO) {
		OrderCreateInfoReqDTO orderCreateInfoReqDTO = new OrderCreateInfoReqDTO();
		// 生成汇掌柜的交易号
		String tradeNo = GenerateIdsUtil.generateTradeID(Constant.COLLECT_SHOP);
		orderCreateInfoReqDTO.setTradeNo(tradeNo);
		// 默认执行结果成功
		orderCreateInfoResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		// 根据买家ID查询买家编码
		String buyerCode = memberCenterRAO.getMemberCodeById(orderCreate4huilinReqDTO.getBuyerId(),messageId)
				.getOtherCenterResult();
		orderCreateInfoReqDTO.setBuyerCode(buyerCode);
		// 设置发票信息
		this.setInvoiceInfo4order(messageId, buyerCode, orderCreateInfoReqDTO,
				orderCreateInfoResDTO);
		if (!ResultCodeEnum.SUCCESS.getCode().equals(orderCreateInfoResDTO.getResponseCode())) {
			LOGGER.warn("从会员中心查询卖家发票信息失败：");
			return orderCreateInfoResDTO;
		}
		// 设置收货地址信息和站点
		this.setConsigInfo4order(messageId, orderCreate4huilinReqDTO.getBuyerId(), orderCreateInfoReqDTO,
				orderCreateInfoResDTO);
		if (!ResultCodeEnum.SUCCESS.getCode().equals(orderCreateInfoResDTO.getResponseCode())) {
			LOGGER.warn("从会员中心查询卖家收货地址信息失败：");
			return orderCreateInfoResDTO;
		}
		// 设置订单和订单行信息
		Map<String,Long> sellerInfoMap = new HashMap<String,Long>();
		this.setSkuListInfo4order(messageId, orderCreate4huilinReqDTO, orderCreateInfoReqDTO,
				orderCreateInfoResDTO,sellerInfoMap);
		if (!ResultCodeEnum.SUCCESS.getCode().equals(orderCreateInfoResDTO.getResponseCode())) {
			LOGGER.warn(orderCreateInfoResDTO.getReponseMsg());
			return orderCreateInfoResDTO;
		}
		orderCreateInfoReqDTO.setMessageId(messageId);
		// 生成订单
		OrderCreateInfoDMO orderCreateInfoDMO = this.orderCreate(orderCreateInfoReqDTO);
		JSONObject jsonObj = (JSONObject) JSONObject.toJSON(orderCreateInfoDMO);
		orderCreateInfoResDTO = JSONObject.toJavaObject(jsonObj, OrderCreateInfoResDTO.class);
		
		List<OrderCreateListInfoResDTO> orderListRes = orderCreateInfoResDTO.getOrderResList();
		if(CollectionUtils.isNotEmpty(orderListRes)){
			for(OrderCreateListInfoResDTO order : orderListRes){
				String sellerUserIdKey = order.getSellerUserId();
				order.setSellerUserId(sellerInfoMap.get(sellerUserIdKey).toString());
			}
		}
		orderCreateInfoResDTO.setResponseCode(orderCreateInfoDMO.getResultCode());
		orderCreateInfoResDTO.setReponseMsg(orderCreateInfoDMO.getResultMsg());
		return orderCreateInfoResDTO;

	}

	/**
	 * 设置B2C创建订单的发票信息参数
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @param orderCreateInfoReqDTO
	 * @param orderCreateInfoResDTO
	 */
	private void setInvoiceInfo4order(String messageId, String buyerCode,
			OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateInfoResDTO orderCreateInfoResDTO) {
		OtherCenterResDTO<MemberInvoiceDTO> memberInvoiceInfo = memberCenterRAO
				.queryMemberInvoiceInfo(buyerCode, "", messageId);
		if (!ResultCodeEnum.SUCCESS.getCode()
				.equals(memberInvoiceInfo.getOtherCenterResponseCode())) {
			orderCreateInfoResDTO.setResponseCode(memberInvoiceInfo.getOtherCenterResponseCode());
			orderCreateInfoResDTO.setReponseMsg(memberInvoiceInfo.getOtherCenterResponseMsg());
			return;
		} else {
			MemberInvoiceDTO memberInvoice = memberInvoiceInfo.getOtherCenterResult();
			orderCreateInfoReqDTO.setInvoiceType("2");
			orderCreateInfoReqDTO.setInvoiceCompanyName(memberInvoice.getInvoiceNotify());
			orderCreateInfoReqDTO.setInvoiceAddress(memberInvoice.getInvoiceAddress());
			orderCreateInfoReqDTO.setTaxManId(memberInvoice.getTaxManId());
			orderCreateInfoReqDTO.setBankName(memberInvoice.getBankName());
			orderCreateInfoReqDTO.setBankAccount(memberInvoice.getBankAccount());
			orderCreateInfoReqDTO.setContactPhone(memberInvoice.getContactPhone());
		}
	}

	/**
	 * 设置B2C创建订单的收货地址参数
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @param orderCreateInfoReqDTO
	 * @param orderCreateInfoResDTO
	 */
	private void setConsigInfo4order(String messageId, Long buyerId,
			OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateInfoResDTO orderCreateInfoResDTO) {
		OtherCenterResDTO<MemberDetailInfo> consigAddress = memberCenterRAO
				.getMemberDetailById(buyerId, messageId);
		
		if (!ResultCodeEnum.SUCCESS.getCode().equals(consigAddress.getOtherCenterResponseCode())) {
			orderCreateInfoResDTO.setResponseCode(consigAddress.getOtherCenterResponseCode());
			orderCreateInfoResDTO.setReponseMsg(consigAddress.getOtherCenterResponseMsg());
			return;
		} else {
			orderCreateInfoReqDTO.setDeliveryType("1");
			MemberDetailInfo memberTemp = consigAddress.getOtherCenterResult();
			MemberBaseInfoDTO memberBaseInfoDTO = memberTemp.getMemberBaseInfoDTO();
			
			orderCreateInfoReqDTO
					.setConsigneeName(memberBaseInfoDTO.getContactName());
			orderCreateInfoReqDTO
					.setConsigneeAddress(memberBaseInfoDTO.getLocationAddr());
			orderCreateInfoReqDTO.setConsigneeAddressDetail(memberBaseInfoDTO.getLocationDetail());
			orderCreateInfoReqDTO.setConsigneeAddressProvince(memberBaseInfoDTO.getLocationProvince());
			String city = memberBaseInfoDTO.getLocationCity();
			orderCreateInfoReqDTO.setConsigneeAddressCity(city);
			orderCreateInfoReqDTO.setSite(city);
			orderCreateInfoReqDTO.setConsigneeAddressDistrict(memberBaseInfoDTO.getLocationCounty());
			orderCreateInfoReqDTO.setConsigneeAddressTown(memberBaseInfoDTO.getLocationTown());
			orderCreateInfoReqDTO.setPostCode("");
		}
	}

	/**
	 * 设置B2C创建订单订单行参数
	 * 
	 * @param messageId
	 * @param orderCreateInfoReqDTO
	 * @param orderCreateInfoResDTO
	 */
	private void setSkuListInfo4order(String messageId,
			OrderCreate4huilinReqDTO orderCreate4huilinReqDTO,
			OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateInfoResDTO orderCreateInfoResDTO,
			Map<String,Long> sellerInfoMap) {
		
		List<OrderCreateOrderListInfoReqDTO> orderList = orderCreate4huilinReqDTO.getOrderList();
		if(CollectionUtils.isNotEmpty(orderList)){
			List<OrderCreateListInfoReqDTO> infoReqDtoList = new ArrayList<OrderCreateListInfoReqDTO>();
			for(OrderCreateOrderListInfoReqDTO order : orderList){
				List<OrderCreateSkuListInfoReqDTO> skuList = order.getSkuList();
				List<MallSkuInDTO> mallSkuInDTOList = new ArrayList<MallSkuInDTO>();
				if (CollectionUtils.isNotEmpty(skuList)) {
					for (OrderCreateSkuListInfoReqDTO sku : skuList) {
						MallSkuInDTO skuIn = new MallSkuInDTO();
						skuIn.setSkuCode(sku.getSkuCode());
						mallSkuInDTOList.add(skuIn);
					}
					OtherCenterResDTO<List<MallSkuOutDTO>> skuOut = goodsCenterRAO
							.queryCartItemList(mallSkuInDTOList, messageId);
					if (!ResultCodeEnum.SUCCESS.getCode().equals(skuOut.getOtherCenterResponseCode())) {
						orderCreateInfoResDTO.setResponseCode(skuOut.getOtherCenterResponseCode());
						orderCreateInfoResDTO.setReponseMsg(skuOut.getOtherCenterResponseMsg());
						return;
					} else {
						List<MallSkuOutDTO> skuOutList = skuOut.getOtherCenterResult();
						if (CollectionUtils.isNotEmpty(skuOutList)) {
							Long memberId = orderCreate4huilinReqDTO.getBuyerId();
							Long sellerId = order.getSellerId();
							String orderNo = GenerateIdsUtil.generateOrderID(Constant.COLLECT_SHOP);
							OrderCreateListInfoReqDTO inforeqDto = new OrderCreateListInfoReqDTO();
							inforeqDto.setOrderFrom(order.getOrderFrom());
							inforeqDto.setOrderNo(orderNo);
							// 根据买家ID查询买家编码
							OtherCenterResDTO<String> sellerInfo = memberCenterRAO
									.queryMemberCodeByMemberId(order.getSellerId(),
											messageId);
							if (!ResultCodeEnum.SUCCESS.getCode().equals(sellerInfo.getOtherCenterResponseCode())){
								orderCreateInfoResDTO.setResponseCode(sellerInfo.getOtherCenterResponseCode());
								orderCreateInfoResDTO.setReponseMsg(sellerInfo.getOtherCenterResponseMsg());
								return;
							}
							String sellerCode = sellerInfo.getOtherCenterResult();
							inforeqDto.setSellerCode(sellerCode);
							sellerInfoMap.put(sellerCode, sellerId);
							OtherCenterResDTO<ShopDTO> shopInfo = storeCenterRAO.findShopInfoById(sellerId);
							inforeqDto.setShopId(shopInfo.getOtherCenterResult().getShopId());
							inforeqDto.setShopName(shopInfo.getOtherCenterResult().getShopName());
							List<OrderCreateItemListInfoReqDTO> itemList = new ArrayList<OrderCreateItemListInfoReqDTO>();
							for (MallSkuOutDTO mallSku : skuOutList) {
								for (OrderCreateSkuListInfoReqDTO sku : skuList) {
									if (mallSku.getSkuCode().equals(sku.getSkuCode())) {
										OrderCreateItemListInfoReqDTO orderCreateItemListInfoReqDTO = new OrderCreateItemListInfoReqDTO();
										orderCreateItemListInfoReqDTO.setOrderItemNo(
												orderNo.concat(GenerateIdsUtil.generateSubOrderSeq()));
										orderCreateItemListInfoReqDTO.setSkuCode(mallSku.getSkuCode());
										orderCreateItemListInfoReqDTO.setGoodsCount(sku.getGoodsCount());
										orderCreateItemListInfoReqDTO.setChannelCode(sku.getChannelCode());
										orderCreateItemListInfoReqDTO.setIsBoxFlag(sku.getIsBoxFlag());
										// 经营关系
										OtherCenterResDTO<ApplyBusiRelationDTO> isHasDevRelation = memberCenterRAO
												.selectBusiRelation(memberId, sellerId,
														mallSku.getThirdCategoryId(), mallSku.getBrandId());
										// 有经营关系
										if (isHasDevRelation.getOtherCenterResult() != null) {
											orderCreateItemListInfoReqDTO.setIsHasDevRelation(1);
										} else {
											// 没有经营关系
											orderCreateItemListInfoReqDTO.setIsHasDevRelation(0);
										}
										itemList.add(orderCreateItemListInfoReqDTO);
									}
								}
							}
							inforeqDto.setOrderItemList(itemList);
							infoReqDtoList.add(inforeqDto);
							orderCreateInfoReqDTO.setOrderList(infoReqDtoList);
						}
					}
				}

			}
		}
		
	}

}
