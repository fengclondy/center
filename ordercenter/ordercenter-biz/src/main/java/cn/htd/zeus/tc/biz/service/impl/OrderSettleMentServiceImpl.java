package cn.htd.zeus.tc.biz.service.impl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.goodscenter.dto.mall.MallSkuAttributeDTO;
import cn.htd.goodscenter.dto.mall.MallSkuInDTO;
import cn.htd.goodscenter.dto.mall.MallSkuOutDTO;
import cn.htd.goodscenter.dto.mall.MallSkuWithStockInDTO;
import cn.htd.goodscenter.dto.mall.MallSkuWithStockOutDTO;
import cn.htd.marketcenter.dto.OrderInfoDTO;
import cn.htd.marketcenter.dto.OrderItemCouponDTO;
import cn.htd.marketcenter.dto.OrderItemInfoDTO;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedMallInfoDTO;
import cn.htd.marketcenter.dto.TradeInfoDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.pricecenter.dto.CommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.QueryCommonItemSkuPriceDTO;
import cn.htd.zeus.tc.biz.rao.GoodsCenterRAO;
import cn.htd.zeus.tc.biz.rao.GoodsPlusRAO;
import cn.htd.zeus.tc.biz.rao.MarketCenterRAO;
import cn.htd.zeus.tc.biz.rao.MemberCenterRAO;
import cn.htd.zeus.tc.biz.rao.PriceCenterRAO;
import cn.htd.zeus.tc.biz.service.OrderFreightInfoService;
import cn.htd.zeus.tc.biz.service.OrderSettleMentService;
import cn.htd.zeus.tc.biz.util.ExternalSupplierCostCaculateUtil;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.FacadeOtherResultCodeEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.OrderConsigAddressDTO;
import cn.htd.zeus.tc.dto.OrderItemAttributeDTO;
import cn.htd.zeus.tc.dto.OrderPromotionInfoDTO;
import cn.htd.zeus.tc.dto.OrderSeckillInfoDTO;
import cn.htd.zeus.tc.dto.OrderSellerInfoDTO;
import cn.htd.zeus.tc.dto.OrderSkuInfoDTO;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.OrderSettleMentResDTO;
import cn.htd.zeus.tc.dto.resquest.BatchGetStockReqDTO;
import cn.htd.zeus.tc.dto.resquest.BatchGetStockSkuNumsReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderSettleMentReqDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

@Service
public class OrderSettleMentServiceImpl implements OrderSettleMentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSettleMentServiceImpl.class);

	@Resource
	private PriceCenterRAO priceCenterRAO;

	@Resource
	private MemberCenterRAO memberCenterRAO;

	@Resource
	private GoodsCenterRAO goodsCenterRAO;

	@Resource
	private MarketCenterRAO marketCenterRAO;

	@Resource
	private GoodsPlusRAO goodsPlusRAO;

	@Resource
	private OrderFreightInfoService orderFreightInfoService;

	/**
	 * 处理不同类型的商品数据
	 * 
	 * @param mallSku
	 * @param skuPrice
	 * @return
	 */
	public void orderItemsSettlement(MallSkuWithStockOutDTO mallSku, OrderSkuInfoDTO orderSku,
			CommonItemSkuPriceDTO skuPrice,TimelimitedInfoDTO limitedTimePurchaseInfo,OrderSettleMentReqDTO orderSettleMentReqDTO) {
		// 内部供应商商品处理
		if (Constant.PRODUCT_CHANNEL_CODE_INNER
				.equals(mallSku.getMallSkuOutDTO().getProductChannelCode())) {
			orderItems4inner(mallSku, orderSku, skuPrice);
			// 外部供应商商品处理
		} else if (Constant.PRODUCT_CHANNEL_CODE_OUTER
				.equals(mallSku.getMallSkuOutDTO().getProductChannelCode())) {
			orderItems4outer(mallSku, orderSku, skuPrice,limitedTimePurchaseInfo,orderSettleMentReqDTO);
			// 外接供应商商品处理
		} else if (Constant.PRODUCT_CHANNEL_CODE_OUTLINE
				.equals(mallSku.getMallSkuOutDTO().getProductChannelCode())) {
			orderItems4outerline(mallSku, orderSku, skuPrice);
		}
	}

	/**
	 * 处理内部供应商商品信息
	 * 
	 * @param mallSku
	 * @param skuPrice
	 * @return
	 */
	private void orderItems4inner(MallSkuWithStockOutDTO mallSku, OrderSkuInfoDTO orderSku,
			CommonItemSkuPriceDTO skuPrice) {
		// 根据运费模板ID计算运费信息
		orderSku.setFreight(Constant.PRODUCT_INNER_FREIGHT);
		// orderSku.setProductAttributeList(
		// transformItemAttributeDTO(mallSku.getMallSkuOutDTO().getItemSaleAttributeList()));
		orderSku.setPrice(skuPrice.getSalesPrice());
		orderSku.setBrandId(mallSku.getMallSkuOutDTO().getBrandId());
		orderSku.setThirdCategoryId(mallSku.getMallSkuOutDTO().getThirdCategoryId());
		orderSku.setProductName(mallSku.getMallSkuOutDTO().getItemName());
		orderSku.setProductUrl(mallSku.getMallSkuOutDTO().getItemPictureUrl());
		orderSku.setProductCode(mallSku.getMallSkuOutDTO().getItemCode());
		orderSku.setItemAttributeList(
				transformItemAttributeDTO(mallSku.getMallSkuOutDTO().getItemAttributeList()));
		orderSku.setProductAttributeList(
				transformItemAttributeDTO(mallSku.getMallSkuOutDTO().getSaleAttributeList()));
	}

	/**
	 * 处理外部供应商商品信息
	 * 
	 * @param mallSku
	 * @param skuPrice
	 * @return
	 */
	private void orderItems4outer(MallSkuWithStockOutDTO mallSku, OrderSkuInfoDTO orderSku,
			CommonItemSkuPriceDTO skuPrice,TimelimitedInfoDTO limitedTimePurchaseInfo,OrderSettleMentReqDTO orderSettleMentReqDTO) {
		// 根据运费模板ID计算运费信息
		orderSku.setFreight(Constant.PRODUCT_INNER_FREIGHT);
		if (null != limitedTimePurchaseInfo) {
			orderSku.setPrice(limitedTimePurchaseInfo.getSkuTimelimitedPrice());
		} else {
			// 计算外部供应商价格
			orderSku.setPrice(new ExternalSupplierCostCaculateUtil()
					.caculateLadderPrice4outer(orderSku.getProductCount(),
							skuPrice.getLadderPriceList()));
		}
		orderSku.setProductName(mallSku.getMallSkuOutDTO().getItemName());
		orderSku.setProductUrl(mallSku.getMallSkuOutDTO().getItemPictureUrl());
		orderSku.setProductCode(mallSku.getMallSkuOutDTO().getItemCode());
		orderSku.setBrandId(mallSku.getMallSkuOutDTO().getBrandId());
		orderSku.setThirdCategoryId(mallSku.getMallSkuOutDTO().getThirdCategoryId());
		orderSku.setLength(mallSku.getMallSkuOutDTO().getLength());
		orderSku.setHeight(mallSku.getMallSkuOutDTO().getHeight());
		orderSku.setWidth(mallSku.getMallSkuOutDTO().getWidth());
		orderSku.setWeight(mallSku.getMallSkuOutDTO().getWeight());
		orderSku.setWeightUnit(mallSku.getMallSkuOutDTO().getWeightUnit());
		orderSku.setNetWeight(mallSku.getMallSkuOutDTO().getNetWeight());
		orderSku.setShopFreightTemplateId(mallSku.getMallSkuOutDTO().getShopFreightTemplateId());
		orderSku.setItemAttributeList(
				transformItemAttributeDTO(mallSku.getMallSkuOutDTO().getItemAttributeList()));
		orderSku.setProductAttributeList(
				transformItemAttributeDTO(mallSku.getMallSkuOutDTO().getSaleAttributeList()));
		// orderSku.setPrice(new BigDecimal("1.00"));
		// orderSku.setProductAttributeList(
		// transformItemAttributeDTO(mallSku.getMallSkuOutDTO().getItemSaleAttributeList()));
	}

	/**
	 * 处理京东商品信息
	 * 
	 * @param mallSku
	 * @param skuPrice
	 * @return
	 */
	private void orderItems4outerline(MallSkuWithStockOutDTO mallSku, OrderSkuInfoDTO orderSku,
			CommonItemSkuPriceDTO skuPrice) {
		// 根据运费模板ID计算运费信息
		orderSku.setFreight(Constant.PRODUCT_INNER_FREIGHT);
		orderSku.setPrice(skuPrice.getSalesPrice());
		orderSku.setBrandId(mallSku.getMallSkuOutDTO().getBrandId());
		orderSku.setThirdCategoryId(mallSku.getMallSkuOutDTO().getThirdCategoryId());
		orderSku.setProductName(mallSku.getMallSkuOutDTO().getItemName());
		orderSku.setProductUrl(mallSku.getMallSkuOutDTO().getItemPictureUrl());
		orderSku.setProductCode(mallSku.getMallSkuOutDTO().getItemCode());
		orderSku.setItemAttributeList(
				transformItemAttributeDTO(mallSku.getMallSkuOutDTO().getItemAttributeList()));
		orderSku.setProductAttributeList(
				transformItemAttributeDTO(mallSku.getMallSkuOutDTO().getSaleAttributeList()));
		// orderSku.setProductAttributeList(
		// transformItemAttributeDTO(mallSku.getMallSkuOutDTO().getItemSaleAttributeList()));
	}

	/**
	 * 转换商品销售属性dto参数
	 * 
	 * @param mallSkuAttrList
	 * @return
	 */
	private List<OrderItemAttributeDTO> transformItemAttributeDTO(
			List<MallSkuAttributeDTO> mallSkuAttrList) {
		List<OrderItemAttributeDTO> itemAttrList = new ArrayList<OrderItemAttributeDTO>();
		if (CollectionUtils.isNotEmpty(mallSkuAttrList)) {
			for (MallSkuAttributeDTO mallSkuAttr : mallSkuAttrList) {
				OrderItemAttributeDTO itemAttr = new OrderItemAttributeDTO();
				itemAttr.setAttributeId(mallSkuAttr.getAttributeId());
				itemAttr.setAttributeName(mallSkuAttr.getAttributeName());
				itemAttr.setAttributeValueId(mallSkuAttr.getAttributeValueId());
				itemAttr.setAttributeValueName(mallSkuAttr.getAttributeValueName());
				itemAttrList.add(itemAttr);
			}
		}
		return itemAttrList;
	}

	private void checkUpShelf(OrderSettleMentResDTO orderSettleMentResDTO,
			List<MallSkuWithStockOutDTO> resultList,Map<String,String> timelimitedInfoMap,String messageId) {
		if (CollectionUtils.isNotEmpty(resultList)) {
			for (MallSkuWithStockOutDTO mallSku : resultList) {
				//校验是否是限时购商品，如果是校验上下架状态，如果是限时购状态未开始，已结束，或者是限时购商品不存在就走正常的商品校验逻辑
				String skuCode = mallSku.getMallSkuStockOutDTO().getSkuCode();
				OtherCenterResDTO<List<TimelimitedInfoDTO>> timeLimitedInfoRes = marketCenterRAO.getTimelimitedInfo(skuCode, messageId);
				String timeLimitedInfoResCode = timeLimitedInfoRes.getOtherCenterResponseCode();
				if(timeLimitedInfoResCode.equals(ResultCodeEnum.ERROR.getCode())){
					orderSettleMentResDTO.setResponseCode(timeLimitedInfoResCode);
					orderSettleMentResDTO.setReponseMsg(timeLimitedInfoRes.getOtherCenterResponseMsg());
					break;
				}
				if(timeLimitedInfoResCode.equals(ResultCodeEnum.SUCCESS.getCode())){
					List<TimelimitedInfoDTO> timelimitedInfoDTOList = timeLimitedInfoRes.getOtherCenterResult();
					if(null == timelimitedInfoDTOList || timelimitedInfoDTOList.size() < 1){
						orderSettleMentResDTO.setResponseCode(ResultCodeEnum.MARKETCENTER_LIMITED_TIME_PURCHASE_IS_NULL.getCode());
						orderSettleMentResDTO.setReponseMsg(ResultCodeEnum.MARKETCENTER_LIMITED_TIME_PURCHASE_IS_NULL.getMsg());
						break;
					}
					String value = JSON.toJSONString(timelimitedInfoDTOList.get(0));
					timelimitedInfoMap.put(skuCode, value);
					continue;
				}
				// 判断商品是否下架
				if (mallSku.getMallSkuStockOutDTO().getIsUpShelf() == 0) {
					orderSettleMentResDTO.setResponseCode(
							ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_DOWN_SHELF.getCode());
					orderSettleMentResDTO.setReponseMsg(
							ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_DOWN_SHELF.getMsg());
					break;
				}
				// vip套餐商品加入标志位
				orderSettleMentResDTO.setIsVipItem(mallSku.getMallSkuOutDTO().getIsVipItem());
			}
		}
	}

	@Override
	public void packageSkuInfo4goodsService(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO orderSettleMentResDTO) {
		List<OrderSellerInfoDTO> sellerList = orderSettleMentReqDTO.getSellerInfoList();
		String memberCode = orderSettleMentReqDTO.getMemberId();
		String messageId = orderSettleMentReqDTO.getMessageId();
		// 调用商品中心接口查询商品sku详细信息
		OtherCenterResDTO<List<MallSkuWithStockOutDTO>> sku = (OtherCenterResDTO<List<MallSkuWithStockOutDTO>>) goodsCenterRAO
				.getMallItemInfoList(transformSearchCondition4goods(sellerList,
						orderSettleMentReqDTO.getCitySiteCode()), messageId);
		if (ResultCodeEnum.SUCCESS.getCode().equals(sku.getOtherCenterResponseCode())) {
			List<MallSkuWithStockOutDTO> resultList = sku.getOtherCenterResult();
			// 校验商品上下架情况,如果是vip套餐商品加入标志位
			Map<String,String> timelimitedInfoMap = new HashMap<String,String>();
			this.checkUpShelf(orderSettleMentResDTO, resultList,timelimitedInfoMap,messageId);
			if (orderSettleMentResDTO.getResponseCode() == ResultCodeEnum.SUCCESS.getCode()) {
				// 初始化京东订单商品价格
				BigDecimal dealPrice = BigDecimal.ZERO;
				outterLoop: for (OrderSellerInfoDTO seller : sellerList) {
					BatchGetStockReqDTO stockReqDTO = new BatchGetStockReqDTO();
					// 查询当前商家地址信息
					OtherCenterResDTO<String> other = memberCenterRAO.selectChannelAddressDTO(
							messageId, seller.getSellerCode(),
							Constant.PRODUCT_CHANNEL_CODE_OUTLINE);
					stockReqDTO.setArea(other.getOtherCenterResult());
					stockReqDTO.setMessageId(messageId);
					List<BatchGetStockSkuNumsReqDTO> stockSkuNumReqList = new ArrayList<BatchGetStockSkuNumsReqDTO>();
					List<OrderSkuInfoDTO> skuList = seller.getSkuInfoList();
					for (OrderSkuInfoDTO orderSku : skuList) {
						for (MallSkuWithStockOutDTO mallSku : resultList) {
							String skuCode = mallSku.getMallSkuOutDTO().getSkuCode();
							if (skuCode.equals(orderSku.getSkuCode())) {
								OtherCenterResDTO<CommonItemSkuPriceDTO> skuPrice = new OtherCenterResDTO<CommonItemSkuPriceDTO>();
								//限时购信息
								TimelimitedInfoDTO limitedTimePurchaseInfo = JSON
										.parseObject(
												timelimitedInfoMap.get(skuCode),
												TimelimitedInfoDTO.class);
								if(null == limitedTimePurchaseInfo){
									// 转换查询条件为商品价格所需查询条件
									QueryCommonItemSkuPriceDTO priceDTO = this
											.transformSearchCondition4price(mallSku, orderSku,
													memberCode, seller.getSellerId());
									// 查询商品价格
									skuPrice = priceCenterRAO
											.queryCommonItemSkuPrice(priceDTO, messageId);
									if (skuPrice.getOtherCenterResponseCode() != ResultCodeEnum.SUCCESS
											.getCode()) {
										orderSettleMentResDTO
												.setResponseCode(skuPrice.getOtherCenterResponseCode());
										orderSettleMentResDTO
												.setReponseMsg(skuPrice.getOtherCenterResponseMsg());
										break outterLoop;
									}
								}
								// 处理商品信息
								this.orderItemsSettlement(mallSku, orderSku,
										skuPrice.getOtherCenterResult(),limitedTimePurchaseInfo,orderSettleMentReqDTO);
								// 查询出商品中含有的京东商品
								if (mallSku.getMallSkuOutDTO().getProductChannelCode()
										.equals(Constant.PRODUCT_CHANNEL_CODE_OUTLINE)) {
									BatchGetStockSkuNumsReqDTO stockSkuNumReq = new BatchGetStockSkuNumsReqDTO();
									stockSkuNumReq
											.setSkuId(mallSku.getMallSkuOutDTO().getOuterSkuId());
									stockSkuNumReq.setNum(Long.valueOf(orderSku.getProductCount()));
									stockSkuNumReqList.add(stockSkuNumReq);
									dealPrice = dealPrice.add(orderSku.getPrice());
								} else {
									// 非京东商品需要校验库存数量是否大于购买数量
									if (!Constant.PRODUCT_CHANNEL_CODE_OUTLINE.equals(
											mallSku.getMallSkuOutDTO().getProductChannelCode())
											) {
										if (null != limitedTimePurchaseInfo) {
											//每单限制数量
											Integer timelimitedThreshold = limitedTimePurchaseInfo.getTimelimitedThreshold();
											//限时购商品剩余数量
											Integer timelimitedSkuCount = limitedTimePurchaseInfo.getTimelimitedSkuCount();
											Integer buyCount = orderSku.getProductCount();
											if (buyCount > timelimitedThreshold
													|| buyCount > timelimitedSkuCount) {
												orderSettleMentResDTO
														.setResponseCode(ResultCodeEnum.MARKERCENTER_LIMITED_TIME_PURCHASE_BUYCOUNT_BEYOND
																.getCode());
												orderSettleMentResDTO
														.setReponseMsg(ResultCodeEnum.MARKERCENTER_LIMITED_TIME_PURCHASE_BUYCOUNT_BEYOND
																.getMsg());
												break outterLoop;
											}
										} else if ((mallSku
												.getMallSkuStockOutDTO()
												.getItemSkuPublishInfo()
												.getDisplayQuantity() - mallSku
												.getMallSkuStockOutDTO()
												.getItemSkuPublishInfo()
												.getReserveQuantity()) < orderSku
												.getProductCount()) {
											orderSettleMentResDTO
													.setResponseCode(ResultCodeEnum.ORDERSETTLEMENT_BUYCOUNT_BEYOND
															.getCode());
											orderSettleMentResDTO
													.setReponseMsg(ResultCodeEnum.ORDERSETTLEMENT_BUYCOUNT_BEYOND
															.getMsg());
											break outterLoop;
										}
									}
								}
							}
						}
					}
					stockReqDTO.setSkuNums(stockSkuNumReqList);
					if (CollectionUtils.isNotEmpty(stockSkuNumReqList)) {
						// 查询当前商品中京东商品的库存信息
						OtherCenterResDTO<String> stockDTO = goodsPlusRAO.queryStock4JD(stockReqDTO,
								messageId);
						// 京东库存校验失败则跳出循环并且返回报错信息
						if (!stockDTO.getOtherCenterResponseCode()
								.equals(ResultCodeEnum.SUCCESS.getCode())) {
							orderSettleMentResDTO
									.setResponseCode(stockDTO.getOtherCenterResponseCode());
							orderSettleMentResDTO
									.setReponseMsg(stockDTO.getOtherCenterResponseMsg());
							break outterLoop;
						}
					}
				}
				// 京东商品订单价格不为空则校验京东商品价格总和是否超出京东账号余额
				if (dealPrice != BigDecimal.ZERO && orderSettleMentResDTO.getResponseCode()
						.equals(ResultCodeEnum.SUCCESS.getCode())) {
					this.checkJDAmount(orderSettleMentResDTO, dealPrice, messageId);
				}
			}
			if (orderSettleMentResDTO.getResponseCode() == ResultCodeEnum.SUCCESS.getCode()) {
				// 计算商品运费
				orderFreightInfoService.calculateOrderItemFeight(sellerList,
						orderSettleMentReqDTO.getCitySiteCode());
				orderSettleMentResDTO.setSellerList(sellerList);
				orderSettleMentResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getCode());
				orderSettleMentResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			}

		} else {
			orderSettleMentResDTO
					.setResponseCode(ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_NULL.getCode());
			orderSettleMentResDTO
					.setReponseMsg(ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_NULL.getMsg());
		}
	}

	@Override
	public void packageSeckillInfo(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO) {
		List<OrderSellerInfoDTO> sellerList = orderSettleMentReqDTO.getSellerInfoList();
		String messageId = orderSettleMentReqDTO.getMessageId();
		// 调用商品中心接口查询商品sku详细信息
		OtherCenterResDTO<List<MallSkuOutDTO>> sku = (OtherCenterResDTO<List<MallSkuOutDTO>>) goodsCenterRAO
				.getMallItemInfoList4SecKill(transformSearchCondition4goods4SecKill(sellerList,
						orderSettleMentReqDTO.getCitySiteCode()), messageId);
		if (ResultCodeEnum.SUCCESS.getCode().equals(sku.getOtherCenterResponseCode())) {
			List<MallSkuOutDTO> resultList = sku.getOtherCenterResult();
			outterLoop: for (OrderSellerInfoDTO seller : sellerList) {
				BatchGetStockReqDTO stockReqDTO = new BatchGetStockReqDTO();
				// 查询当前商家地址信息
				OtherCenterResDTO<String> other = memberCenterRAO.selectChannelAddressDTO(messageId,
						seller.getSellerCode(), Constant.PRODUCT_CHANNEL_CODE_OUTLINE);
				stockReqDTO.setArea(other.getOtherCenterResult());
				stockReqDTO.setMessageId(messageId);
				List<BatchGetStockSkuNumsReqDTO> stockSkuNumReqList = new ArrayList<BatchGetStockSkuNumsReqDTO>();
				List<OrderSkuInfoDTO> skuList = seller.getSkuInfoList();
				for (OrderSkuInfoDTO orderSku : skuList) {
					for (MallSkuOutDTO mallSku : resultList) {
						if (mallSku.getSkuCode().equals(orderSku.getSkuCode())) {
							// 根据运费模板ID计算运费信息
							orderSku.setFreight(Constant.PRODUCT_INNER_FREIGHT);
							// 计算外部供应商价格
							orderSku.setPrice(
									resDTO.getOrderSeckillInfoDTO().getSkuTimelimitedPrice());
							orderSku.setProductName(mallSku.getItemName());
							orderSku.setProductUrl(mallSku.getItemPictureUrl());
							orderSku.setProductCode(mallSku.getItemCode());
							orderSku.setBrandId(mallSku.getBrandId());
							orderSku.setThirdCategoryId(
									mallSku.getThirdCategoryId());
							orderSku.setLength(mallSku.getLength());
							orderSku.setHeight(mallSku.getHeight());
							orderSku.setWidth(mallSku.getWidth());
							orderSku.setWeight(mallSku.getWeight());
							orderSku.setWeightUnit(mallSku.getWeightUnit());
							orderSku.setNetWeight(mallSku.getNetWeight());
							orderSku.setShopFreightTemplateId(
									mallSku.getShopFreightTemplateId());
							orderSku.setItemAttributeList(transformItemAttributeDTO(
									mallSku.getItemAttributeList()));
							orderSku.setProductAttributeList(transformItemAttributeDTO(
									mallSku.getSaleAttributeList()));
							if (Constant.PRODUCT_CHANNEL_CODE_OUTLINE
									.equals(orderSku.getItemChannel())) {
								BatchGetStockSkuNumsReqDTO stockSkuNumReq = new BatchGetStockSkuNumsReqDTO();
								stockSkuNumReq.setSkuId(mallSku.getOuterSkuId());
								stockSkuNumReq.setNum(Long.valueOf(orderSku.getProductCount()));
								stockSkuNumReqList.add(stockSkuNumReq);
								stockReqDTO.setSkuNums(stockSkuNumReqList);
								this.checkJDAmount(resDTO, orderSku.getPrice(), messageId);
								if (CollectionUtils.isNotEmpty(stockSkuNumReqList)) {
									// 查询当前商品中京东商品的库存信息
									OtherCenterResDTO<String> stockDTO = goodsPlusRAO
											.queryStock4JD(stockReqDTO, messageId);
									// 京东库存校验失败则跳出循环并且返回报错信息
									if (!stockDTO.getOtherCenterResponseCode()
											.equals(ResultCodeEnum.SUCCESS.getCode())) {
										resDTO.setResponseCode(
												stockDTO.getOtherCenterResponseCode());
										resDTO.setReponseMsg(stockDTO.getOtherCenterResponseMsg());
										break outterLoop;
									}
								}
							}
						}
					}
				}
				if (resDTO.getResponseCode() == ResultCodeEnum.SUCCESS.getCode()) {
					// 计算商品运费
					orderFreightInfoService.calculateOrderItemFeight(sellerList,
							orderSettleMentReqDTO.getCitySiteCode());
					resDTO.setSellerList(sellerList);
					resDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getCode());
					resDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
				}
			}
		}
	}

	private void checkJDAmount(OrderSettleMentResDTO orderSettleMentResDTO, BigDecimal dealPrice,
			String messageId) {
		OtherCenterResDTO<String> accountDTO = goodsPlusRAO.queryAccountAmount4JD(messageId);
		if (ResultCodeEnum.SUCCESS.getCode().equals(accountDTO.getOtherCenterResponseCode())) {
			BigDecimal bd = new BigDecimal(accountDTO.getOtherCenterResult());
			if (bd.compareTo(dealPrice) < 0) {
				orderSettleMentResDTO.setResponseCode(
						ResultCodeEnum.ORDERSETTLEMENT_DEAL_JDAMOUNT_BEYOND.getCode());
				orderSettleMentResDTO.setReponseMsg(
						ResultCodeEnum.ORDERSETTLEMENT_DEAL_JDAMOUNT_BEYOND.getMsg());
			}
		} else {
			orderSettleMentResDTO
					.setResponseCode(ResultCodeEnum.ORDERSETTLEMENT_JD_AMOUNT_CHECK_FAIL.getCode());
			orderSettleMentResDTO
					.setReponseMsg(ResultCodeEnum.ORDERSETTLEMENT_JD_AMOUNT_CHECK_FAIL.getMsg());
		}
	}

	/**
	 * 转换传入参数为商品接口查询参数
	 * 
	 * @param skuList
	 * @return
	 */
	private List<MallSkuWithStockInDTO> transformSearchCondition4goods(
			List<OrderSellerInfoDTO> sellerList, String cityCode) {
		List<MallSkuWithStockInDTO> mallSkuWithStockInDTOList = new ArrayList<MallSkuWithStockInDTO>();
		for (OrderSellerInfoDTO seller : sellerList) {
			List<OrderSkuInfoDTO> skuList = seller.getSkuInfoList();
			for (OrderSkuInfoDTO sku : skuList) {
				MallSkuWithStockInDTO inDTO = new MallSkuWithStockInDTO();
				inDTO.setSkuCode(sku.getSkuCode());
				// inDTO.setItemCode(sku.getProductCode());
				inDTO.setIsBoxFlag(sku.getIsBoxFlag());
				inDTO.setCityCode(cityCode);
				mallSkuWithStockInDTOList.add(inDTO);
			}
		}

		return mallSkuWithStockInDTOList;
	}

	/**
	 * 转换传入参数为商品接口查询参数-专为秒杀使用
	 * 
	 * @param skuList
	 * @return
	 */
	private List<MallSkuInDTO> transformSearchCondition4goods4SecKill(
			List<OrderSellerInfoDTO> sellerList, String cityCode) {
		List<MallSkuInDTO> mallSkuInDTOList = new ArrayList<MallSkuInDTO>();
		for (OrderSellerInfoDTO seller : sellerList) {
			List<OrderSkuInfoDTO> skuList = seller.getSkuInfoList();
			for (OrderSkuInfoDTO sku : skuList) {
				MallSkuInDTO inDTO = new MallSkuInDTO();
				inDTO.setSkuCode(sku.getSkuCode());
				// inDTO.setItemCode(sku.getProductCode());
				/*inDTO.setIsBoxFlag(sku.getIsBoxFlag());
				inDTO.setCityCode(cityCode);*/
				mallSkuInDTOList.add(inDTO);
			}
		}

		return mallSkuInDTOList;
	}
	
	/**
	 * 转换传入参数为商品价格接口查询参数
	 * 
	 * @param skuList
	 * @return
	 */
	private QueryCommonItemSkuPriceDTO transformSearchCondition4price(
			MallSkuWithStockOutDTO mallSku, OrderSkuInfoDTO sku, String memberCode, long sellerId) {
		QueryCommonItemSkuPriceDTO priceDTO = new QueryCommonItemSkuPriceDTO();
		MemberGroupDTO groupDto = new MemberGroupDTO();
		priceDTO.setCitySiteCode(sku.getCitySiteCode());
		priceDTO.setIsBoxFlag(sku.getIsBoxFlag());
		priceDTO.setIsHasDevRelation(sku.getIsHasDevRelation());
		priceDTO.setIsLogin(1);
		priceDTO.setItemChannelCode(sku.getItemChannel());
		priceDTO.setSkuId(mallSku.getMallSkuOutDTO().getSkuId());
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String messageId = GenerateIdsUtil.generateId(addr.getHostAddress().toString());
			OtherCenterResDTO<String> sellerCode = memberCenterRAO
					.queryMemberCodeByMemberId(mallSku.getMallSkuOutDTO().getSellerId(), messageId);
			OtherCenterResDTO<MemberGroupDTO> gradeResult = memberCenterRAO.selectBuyCodeSellCode(
					sellerCode.getOtherCenterResult(), memberCode, messageId);
			groupDto = gradeResult.getOtherCenterResult();
			if (groupDto != null) {
				priceDTO.setBuyerGrade(groupDto.getBuyerGrade());
				priceDTO.setMemberGroupId(groupDto.getGroupId());
			}
			if (Constant.PRODUCT_CHANNEL_CODE_OUTLINE.equals(sku.getItemChannel())) {
				OtherCenterResDTO<Boolean> flag = goodsCenterRAO.canProductPlusSaleBySeller(
						sellerId, mallSku.getMallSkuOutDTO().getProductChannelCode(),
						mallSku.getMallSkuOutDTO().getThirdCategoryId(),
						mallSku.getMallSkuOutDTO().getBrandId(), messageId);
				priceDTO.setIsCanSellProdplusItem(flag.getOtherCenterResult() ? 1 : 0);
			}
		} catch (UnknownHostException e) {
			LOGGER.error("transformSearchCondition4price-------------获取主机IP失败");
		}
		return priceDTO;
	}

	@Override
	public boolean paramValidate(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO) {
		boolean flag = false;
		List<OrderSellerInfoDTO> sellerList = orderSettleMentReqDTO.getSellerInfoList();
		String messageId = orderSettleMentReqDTO.getMessageId();
		String memberCode = orderSettleMentReqDTO.getMemberId();
		if (StringUtils.isBlank(memberCode)) {
			resDTO.setResponseCode(ResultCodeEnum.ORDERSETTLEMENT_MEMBER_ID_NULL.getCode());
			resDTO.setReponseMsg(ResultCodeEnum.ORDERSETTLEMENT_MEMBER_ID_NULL.getMsg());
			flag = true;
		} else if (StringUtils.isBlank(messageId)) {
			resDTO.setResponseCode(ResultCodeEnum.ORDERSETTLEMENT_MESSAGE_ID_NULL.getCode());
			resDTO.setReponseMsg(ResultCodeEnum.ORDERSETTLEMENT_MESSAGE_ID_NULL.getMsg());
			flag = true;
		} else if (CollectionUtils.isEmpty(sellerList)) {
			resDTO.setResponseCode(ResultCodeEnum.ORDERSETTLEMENT_SELLER_INFO_NULL.getCode());
			resDTO.setReponseMsg(ResultCodeEnum.ORDERSETTLEMENT_SELLER_INFO_NULL.getMsg());
			flag = true;
		} else {
			resDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			resDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		}
		return flag;
	}

	@Override
	public void getCouponAvailableList(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO) {
		List<OrderSellerInfoDTO> sellerList = orderSettleMentReqDTO.getSellerInfoList();
		String memberCode = orderSettleMentReqDTO.getMemberId();
		String messageId = orderSettleMentReqDTO.getMessageId();
		TradeInfoDTO cart = new TradeInfoDTO();
		OtherCenterResDTO<MemberGradeDTO> gradeResult = memberCenterRAO
				.queryMemberGradeInfo(memberCode, messageId);
		cart.setBuyerGrade(gradeResult.getOtherCenterResult().getBuyerGrade());
		cart.setBuyerCode(memberCode);
		List<OrderInfoDTO> orderList = new ArrayList<OrderInfoDTO>();
		for (OrderSellerInfoDTO seller : sellerList) {
			List<OrderItemInfoDTO> productList = new ArrayList<OrderItemInfoDTO>();
			List<OrderSkuInfoDTO> skuList = seller.getSkuInfoList();
			for (OrderSkuInfoDTO sku : skuList) {
				OrderItemInfoDTO itemInfo = new OrderItemInfoDTO();
				itemInfo.setSkuCode(sku.getSkuCode());
				itemInfo.setGoodsPrice(sku.getPrice());
				itemInfo.setBrandId(sku.getBrandId());
				itemInfo.setThirdCategoryId(sku.getThirdCategoryId());
				itemInfo.setChannelCode(sku.getItemChannel());
				itemInfo.setGoodsCount(sku.getProductCount());
				productList.add(itemInfo);
			}
			OrderInfoDTO orderInfo = new OrderInfoDTO();
			orderInfo.setSellerCode(seller.getSellerCode());
			orderInfo.setOrderItemList(productList);
			orderList.add(orderInfo);
		}
		cart.setOrderList(orderList);
		OtherCenterResDTO<TradeInfoDTO> other = marketCenterRAO.getAvailableCouponInfo(cart,
				messageId);
		if (other.getOtherCenterResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
			List<OrderItemCouponDTO> avaliableCouponList = other.getOtherCenterResult()
					.getAvaliableCouponList();
			// 转换可用优惠券信息
			List<OrderPromotionInfoDTO> couponUsedList = this
					.transformCouponList(avaliableCouponList);
			resDTO.setCouponUsedList(couponUsedList);
			List<OrderItemCouponDTO> unAvaliableCouponList = other.getOtherCenterResult()
					.getUnavaliableCouponList();
			// 转换可用优惠券信息
			List<OrderPromotionInfoDTO> couponUnUsedList = this
					.transformCouponList(unAvaliableCouponList);
			resDTO.setCouponUnusedList(couponUnUsedList);
		} else {
			LOGGER.error(
					"MessageId:{} 调用方法OrderSettleMentService.getCouponAvailableList获取优惠券信息出现异常{}",
					messageId, JSONObject.toJSONString(other));
		}
	}

	private List<OrderPromotionInfoDTO> transformCouponList(
			List<OrderItemCouponDTO> marketCouponList) {
		List<OrderPromotionInfoDTO> couponList = new ArrayList<OrderPromotionInfoDTO>();
		if (CollectionUtils.isNotEmpty(marketCouponList)) {
			String marketCouponListString = JSON.toJSONString(marketCouponList);
			couponList = JSON.parseObject(marketCouponListString,
					new TypeReference<ArrayList<OrderPromotionInfoDTO>>() {
					});
		}
		return couponList;
	}

	@Override
	public OrderSettleMentResDTO getInvoiceInfo(String memberCode, String messageId) {
		OrderSettleMentResDTO invoiceDTO = new OrderSettleMentResDTO();
		/*
		 * OtherCenterResDTO<MemberInvoiceDTO> resDTO = memberCenterRAO
		 * .queryMemberInvoiceInfo(memberCode, messageId); MemberInvoiceDTO
		 * invoiceInfo = resDTO.getOtherCenterResult(); OrderInvoiceDTO
		 * orderInvoiceDTO = new OrderInvoiceDTO();
		 * orderInvoiceDTO.setMemberId(invoiceInfo.getMemberId());
		 * orderInvoiceDTO.setTaxManId(invoiceInfo.getTaxManId());
		 * orderInvoiceDTO.setBankAccount(invoiceInfo.getBankAccount());
		 * orderInvoiceDTO.setBankName(invoiceInfo.getBankName());
		 * orderInvoiceDTO.setChannelCode(invoiceInfo.getChannelCode());
		 * orderInvoiceDTO.setContactPhone(invoiceInfo.getContactPhone());
		 * orderInvoiceDTO.setInvoiceAddress(invoiceInfo.getInvoiceAddress());
		 * orderInvoiceDTO.setInvoiceAddressCity(invoiceInfo.
		 * getInvoiceAddressCity());
		 * orderInvoiceDTO.setInvoiceAddressCounty(invoiceInfo.
		 * getInvoiceAddressCounty());
		 * orderInvoiceDTO.setInvoiceAddressDetail(invoiceInfo.
		 * getInvoiceAddressDetail());
		 * orderInvoiceDTO.setInvoiceAddressProvince(invoiceInfo.
		 * getInvoiceAddressProvince());
		 * orderInvoiceDTO.setInvoiceAddressTown(invoiceInfo.
		 * getInvoiceAddressTown());
		 * orderInvoiceDTO.setInvoiceCompanyName(invoiceInfo.
		 * getInvoiceCompanyName());
		 * orderInvoiceDTO.setInvoiceNotify(invoiceInfo.getInvoiceNotify());
		 * orderInvoiceDTO.setInvoicePerson(invoiceInfo.getInvoicePerson());
		 * invoiceDTO.setOrderInvoiceDTO(orderInvoiceDTO);
		 */
		invoiceDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getCode());
		invoiceDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		return invoiceDTO;
	}

	@Override
	public void getConsigAddressList(OrderSettleMentResDTO resDTO, String memberCode,
			String messageId, String citySiteCode) {
		List<OrderConsigAddressDTO> orderAddr = new ArrayList<OrderConsigAddressDTO>();
		OtherCenterResDTO<List<MemberConsigAddressDTO>> consigList = memberCenterRAO
				.queryConsigAddressList(memberCode, messageId);
		List<MemberConsigAddressDTO> list = consigList.getOtherCenterResult();
		if (CollectionUtils.isNotEmpty(list)) {
			for (MemberConsigAddressDTO consigAdd : list) {
				if (citySiteCode.equals(consigAdd.getConsigneeAddressCity())) {
					OrderConsigAddressDTO addr = new OrderConsigAddressDTO();
					addr.setConsigneeAddress(consigAdd.getConsigneeAddress());
					addr.setConsigneeAddressCity(consigAdd.getConsigneeAddressCity());
					addr.setConsigneeAddressDetail(consigAdd.getConsigneeAddressDetail());
					addr.setConsigneeAddressDistrict(consigAdd.getConsigneeAddressDistrict());
					addr.setConsigneeAddressProvince(consigAdd.getConsigneeAddressProvince());
					addr.setConsigneeAddressTown(consigAdd.getConsigneeAddressTown());
					addr.setConsigneeEmail(consigAdd.getConsigneeEmail());
					addr.setConsigneeMobile(consigAdd.getConsigneeMobile());
					addr.setConsigneeName(consigAdd.getConsigneeName());
					addr.setContactPhone(consigAdd.getContactPhone());
					addr.setDefaultFlag(consigAdd.getDefaultFlag());
					addr.setPostCode(consigAdd.getPostCode());
					orderAddr.add(addr);
				}
			}
			resDTO.setConsigAddressList(orderAddr);
			resDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getCode());
			resDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} else {
			resDTO.setReponseMsg(ResultCodeEnum.ORDERSETTLEMENT_CONSIGADDR_NULL.getCode());
			resDTO.setReponseMsg(ResultCodeEnum.ORDERSETTLEMENT_CONSIGADDR_NULL.getMsg());
		}
	}

	@Override
	public void getSeckillInfo(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO) {
		String messageId = orderSettleMentReqDTO.getMessageId();
		// List<OrderSellerInfoDTO> sellerList =
		// orderSettleMentReqDTO.getSellerInfoList();
		// String skuCode =
		// sellerList.get(0).getSkuInfoList().get(0).getSkuCode();
		// OtherCenterResDTO<TimelimitedInfoDTO> seckillInfo = marketCenterRAO
		// .getSeckillInfo(messageId, skuCode);
		String promotionId = orderSettleMentReqDTO.getPromotionId();
		OtherCenterResDTO<TimelimitedMallInfoDTO> seckillInfo = marketCenterRAO
				.getSeckillInfo(promotionId, messageId);
		OrderSeckillInfoDTO seckill = new OrderSeckillInfoDTO();
		if (seckillInfo.getOtherCenterResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
			seckill.setPromotionId(seckillInfo.getOtherCenterResult().getPromotionId());
			seckill.setPromotionType(seckillInfo.getOtherCenterResult().getPromotionType());
			seckill.setLevelCode(seckillInfo.getOtherCenterResult().getLevelCode());
			seckill.setSkuTimelimitedPrice(
					seckillInfo.getOtherCenterResult().getSkuTimelimitedPrice());
			seckill.setTimelimitedSkuCount(
					seckillInfo.getOtherCenterResult().getTimelimitedSkuCount());
			seckill.setTimelimitedThreshold(
					seckillInfo.getOtherCenterResult().getTimelimitedThreshold());
			seckill.setTimelimitedValidInterval(
					seckillInfo.getOtherCenterResult().getTimelimitedValidInterval());
			resDTO.setOrderSeckillInfoDTO(seckill);
		}
		resDTO.setReponseMsg(seckillInfo.getOtherCenterResponseMsg());
		resDTO.setResponseCode(seckillInfo.getOtherCenterResponseCode());
	}

	@Override
	public int queryJDproductStock(String outerSkuId, String sellerCode, String messageId) {
		String stockNum = "";
		BatchGetStockReqDTO stockReqDTO = new BatchGetStockReqDTO();
		// 查询当前商家地址信息
		OtherCenterResDTO<String> other = memberCenterRAO.selectChannelAddressDTO(messageId,
				sellerCode, Constant.PRODUCT_CHANNEL_CODE_OUTLINE);
		stockReqDTO.setArea(other.getOtherCenterResult());
		stockReqDTO.setMessageId(messageId);
		List<BatchGetStockSkuNumsReqDTO> stockSkuNumReqList = new ArrayList<BatchGetStockSkuNumsReqDTO>();
		BatchGetStockSkuNumsReqDTO stockSkuNumReq = new BatchGetStockSkuNumsReqDTO();
		stockSkuNumReq.setSkuId(outerSkuId);
		stockSkuNumReq.setNum(1L);
		stockSkuNumReqList.add(stockSkuNumReq);
		stockReqDTO.setSkuNums(stockSkuNumReqList);
		// 查询当前商品中京东商品的库存信息
		OtherCenterResDTO<String> stockDTO = goodsPlusRAO.queryProductStock4JD(stockReqDTO,
				messageId);
		// 京东库存校验失败则跳出循环并且返回报错信息
		if (stockDTO.getOtherCenterResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
			stockNum = stockDTO.getOtherCenterResult();
		}
		return StringUtils.isNotBlank(stockNum) ? Integer.valueOf(stockNum) : 0;
	}

	@Override
	public OtherCenterResDTO<String> queryJDproductStock4xj(String outerSkuId, String sellerCode,
			String messageId) {
		BatchGetStockReqDTO stockReqDTO = new BatchGetStockReqDTO();
		// 查询当前商家地址信息
		OtherCenterResDTO<String> other = memberCenterRAO.selectChannelAddressDTO(messageId,
				sellerCode, Constant.PRODUCT_CHANNEL_CODE_OUTLINE);
		stockReqDTO.setArea(other.getOtherCenterResult());
		stockReqDTO.setMessageId(messageId);
		List<BatchGetStockSkuNumsReqDTO> stockSkuNumReqList = new ArrayList<BatchGetStockSkuNumsReqDTO>();
		BatchGetStockSkuNumsReqDTO stockSkuNumReq = new BatchGetStockSkuNumsReqDTO();
		stockSkuNumReq.setSkuId(outerSkuId);
		stockSkuNumReq.setNum(1L);
		stockSkuNumReqList.add(stockSkuNumReq);
		stockReqDTO.setSkuNums(stockSkuNumReqList);
		// 查询当前商品中京东商品的库存信息
		OtherCenterResDTO<String> stockDTO = goodsPlusRAO.queryProductStock4JD(stockReqDTO,
				messageId);
		return stockDTO;
	}

	@Override
	public void reserveSeckillproductStock(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO) {
		String messageId = orderSettleMentReqDTO.getMessageId();
		String reOrderId = GenerateIdsUtil.generateOrderID(Constant.ORDER_PREHOLDING_NUMBER);
		resDTO.getOrderSeckillInfoDTO().setReOrderId(reOrderId);
		LOGGER.info("打印秒杀商品库存锁定接口信息=======reserveSeckillproductStock,预占订单编码：{}", reOrderId);
		List<OrderItemPromotionDTO> orderItemPromotionList = new ArrayList<OrderItemPromotionDTO>();
		OrderItemPromotionDTO itemDTO = new OrderItemPromotionDTO();
		itemDTO.setSeckillLockNo(reOrderId);
		itemDTO.setBuyerCode(orderSettleMentReqDTO.getMemberId());
		itemDTO.setPromotionId(resDTO.getOrderSeckillInfoDTO().getPromotionId());
		itemDTO.setPromotionType(resDTO.getOrderSeckillInfoDTO().getPromotionType());
		itemDTO.setLevelCode(resDTO.getOrderSeckillInfoDTO().getLevelCode());
		itemDTO.setQuantity(orderSettleMentReqDTO.getSellerInfoList().get(0).getSkuInfoList().get(0)
				.getProductCount());
		itemDTO.setOperaterId(Long.valueOf(Constant.OPERATE_CODE));
		itemDTO.setOperaterName(Constant.OPERATER_NAME);
		orderItemPromotionList.add(itemDTO);
		OtherCenterResDTO<String> resultDTO = marketCenterRAO
				.reserveBuyerPromotion(orderItemPromotionList, messageId);
		resDTO.setResponseCode(resultDTO.getOtherCenterResponseCode());
		resDTO.setReponseMsg(resultDTO.getOtherCenterResponseMsg());
	}

	public static void main(String[] args) {
		List<OrderItemCouponDTO> marketCouponList = new ArrayList<OrderItemCouponDTO>();
		OrderItemCouponDTO orderItemCouponDTO = new OrderItemCouponDTO();
		orderItemCouponDTO.setTotalDiscountAmount(new BigDecimal("0.01"));
		orderItemCouponDTO.setCouponType("1");
		orderItemCouponDTO.setBuyerCouponCode("10000");
		orderItemCouponDTO.setCouponDescribe("1321321");
		orderItemCouponDTO.setCouponUseRang("11123");
		orderItemCouponDTO.setCouponTargetItemLimit("321312");
		orderItemCouponDTO.setCouponAmount(new BigDecimal("0.01"));
		orderItemCouponDTO.setCouponName("3213123");
		orderItemCouponDTO.setCouponStartTime(new Date());
		orderItemCouponDTO.setCouponEndTime(new Date());
		orderItemCouponDTO.setPromotionId("111321321");
		marketCouponList.add(orderItemCouponDTO);
		String marketCouponListString = JSON.toJSONString(marketCouponList);
		List<OrderPromotionInfoDTO> orderCouponList = JSON.parseObject(marketCouponListString,
				new TypeReference<ArrayList<OrderPromotionInfoDTO>>() {
				});

	}

}
