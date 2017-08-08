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
package cn.htd.tradecenter.service.handle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.goodscenter.dto.venus.outdto.VenusOrderItemSkuDetailOutDTO;
import cn.htd.goodscenter.service.stock.SkuStockChangeExportService;
import cn.htd.goodscenter.service.venus.VenusItemExportService;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.tradecenter.common.constant.ReturnCodeConst;
import cn.htd.tradecenter.common.enums.BoxAreaTypeEnum;
import cn.htd.tradecenter.common.enums.YesNoEnum;
import cn.htd.tradecenter.common.exception.TradeCenterBusinessException;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsPriceHistoryDTO;

@Service("tradeOrderStockHandle")
public class TradeOrderStockHandle {

	private static final Logger logger = LoggerFactory.getLogger(TradeOrderStockHandle.class);

	@Resource
	private VenusItemExportService venusItemExportService;

	@Resource
	private SkuStockChangeExportService skuStockChangeExportService;

	/**
	 * 取得Venus开单商品详情信息及相应库存价格信息
	 * 
	 * @param skuId
	 * @param boxFlg
	 * @param goodsNum
	 * @return
	 * @throws TradeCenterBusinessException
	 * @throws Exception
	 */
	public TradeOrderItemsDTO getItemSkuDetail(String skuCode, int isBoxFlg, int goodsNum)
			throws TradeCenterBusinessException, Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "TradeOrderStockHandle-getItemSkuDetail", "skuCode:" + skuCode,
				"isBoxFlg:" + isBoxFlg, "goodsNum:" + goodsNum);

		ExecuteResult<VenusOrderItemSkuDetailOutDTO> venusItemDetailResult = null;
		VenusOrderItemSkuDetailOutDTO itemDetailInfo = null;
		ItemSkuPublishInfo skuStockInfo = null;
		ItemSkuBasePriceDTO skuBasePriceInfo = null;
		TradeOrderItemsDTO orderItemsDTO = new TradeOrderItemsDTO();
		String boxFlg = YesNoEnum.YES.getValue() == isBoxFlg ? BoxAreaTypeEnum.BOX.getValue()
				: BoxAreaTypeEnum.AREA.getValue();
		String skuMsg = "";

		try {
			venusItemDetailResult = venusItemExportService.queryOrderItemSkuDetail(skuCode, boxFlg);
			if (!venusItemDetailResult.isSuccess()) {
				throw new TradeCenterBusinessException(venusItemDetailResult.getCode(),
						StringUtils.join(venusItemDetailResult.getErrorMessages(), "\n"));
			}
			itemDetailInfo = venusItemDetailResult.getResult();
			skuStockInfo = itemDetailInfo.getItemSkuPublishInfo();
			skuBasePriceInfo = itemDetailInfo.getItemSkuBasePrice();
			skuMsg = "SKU编码:" + itemDetailInfo.getSkuCode() + " SKU名称:" + itemDetailInfo.getItemName() + " 的"
					+ BoxAreaTypeEnum.getName(boxFlg);
			if (StringUtils.isEmpty(itemDetailInfo.getItemSpuCode())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_SPU_NOT_EXIST,
						skuMsg + " 商品没有商品模版信息");
			}
			if (skuStockInfo == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_AVAILABLE_INVENTORY_LACK,
						skuMsg + " 商品没有库存信息");
			}
			if (skuBasePriceInfo == null) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_NONE_PRICE, skuMsg + " 商品没有价格信息");
			}
			if (goodsNum > (skuStockInfo.getDisplayQuantity().intValue()
					- skuStockInfo.getReserveQuantity().intValue())) {
				throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_AVAILABLE_INVENTORY_LACK,
						skuMsg + " 商品库存不足");
			}
			orderItemsDTO.setChannelCode(itemDetailInfo.getItemChannel());
			orderItemsDTO.setItemCode(itemDetailInfo.getItemCode());
			orderItemsDTO.setGoodsName(itemDetailInfo.getItemName());
			orderItemsDTO.setSkuCode(itemDetailInfo.getSkuCode());
			orderItemsDTO.setSkuEanCode(itemDetailInfo.getEanCode());
			orderItemsDTO.setSkuErpCode(itemDetailInfo.getErpCode());
			orderItemsDTO.setSkuPictureUrl(itemDetailInfo.getPictureUrl());
			orderItemsDTO.setItemSpuCode(itemDetailInfo.getItemSpuCode());
			orderItemsDTO.setFirstCategoryId(itemDetailInfo.getFirstCatId());
			orderItemsDTO.setFirstCategoryName(itemDetailInfo.getFirstCatName());
			orderItemsDTO.setSecondCategoryId(itemDetailInfo.getSecondCatId());
			orderItemsDTO.setSecondCategoryName(itemDetailInfo.getSecondCatName());
			orderItemsDTO.setThirdCategoryId(itemDetailInfo.getThirdCatId());
			orderItemsDTO.setThirdCategoryName(itemDetailInfo.getThirdCatName());
			orderItemsDTO.setErpFirstCategoryCode(itemDetailInfo.getErpFistCatCode());
			orderItemsDTO.setErpFiveCategoryCode(itemDetailInfo.getErpFiveCatCode());
			orderItemsDTO.setBrandId(itemDetailInfo.getBrandId());
			orderItemsDTO.setBrandName(itemDetailInfo.getBrandName());
			orderItemsDTO.setCategoryIdList(orderItemsDTO.getFirstCategoryId() + ","
					+ orderItemsDTO.getSecondCategoryId() + "," + orderItemsDTO.getThirdCategoryId());
			orderItemsDTO.setCategoryNameList(orderItemsDTO.getFirstCategoryName() + ","
					+ orderItemsDTO.getSecondCategoryName() + "," + orderItemsDTO.getThirdCategoryName());
			orderItemsDTO.setIsBoxFlag(skuStockInfo.getIsBoxFlag());
			if (YesNoEnum.YES.getValue() == skuStockInfo.getIsBoxFlag().intValue()) {
				orderItemsDTO.setSalePrice(skuBasePriceInfo.getBoxSalePrice());
			} else {
				orderItemsDTO.setSalePrice(skuBasePriceInfo.getAreaSalePrice());
			}
			orderItemsDTO.setIsVipItem(YesNoEnum.NO.getValue());
			orderItemsDTO.setShopFreightTemplateId(0L);
			orderItemsDTO.setGoodsFreight(BigDecimal.ZERO);
		} catch (TradeCenterBusinessException tcbe) {
			logger.warn("\n 方法[{}]，异常：[{}]", "TradeOrderStockHandle-getItemSkuDetail",
					JSONObject.toJSONString(tcbe.getMessage()));
			throw tcbe;
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderStockHandle-getItemSkuDetail", JSONObject.toJSONString(e));
			throw e;
		}
		return orderItemsDTO;
	}

	/**
	 * 提交订单锁定库存
	 * 
	 * @param messageId
	 * @param orderFrom
	 * @param orderItemList
	 * @throws TradeCenterBusinessException
	 * @throws Exception
	 */
	public ExecuteResult<String> reserveOrderStock(String messageId, String orderFrom,
			List<TradeOrderItemsDTO> orderItemList) throws TradeCenterBusinessException, Exception {
		ExecuteResult<String> stockResult = null;
		Order4StockChangeDTO stockDTO = new Order4StockChangeDTO();
		Order4StockEntryDTO stockEntryDTO = null;
		List<Order4StockEntryDTO> stockEntryList = new ArrayList<Order4StockEntryDTO>();
		String orderNo = "";
		try {

			for (TradeOrderItemsDTO orderItemDTO : orderItemList) {
				stockEntryDTO = new Order4StockEntryDTO();
				orderNo = orderItemDTO.getOrderNo();
				stockEntryDTO.setOrderNo(orderNo);
				stockEntryDTO.setOrderResource(orderFrom);
				stockEntryDTO.setSkuCode(orderItemDTO.getSkuCode());
				stockEntryDTO.setIsBoxFlag(orderItemDTO.getIsBoxFlag());
				stockEntryDTO.setQuantity(orderItemDTO.getGoodsCount());
				stockEntryList.add(stockEntryDTO);
			}
			stockDTO.setMessageId(messageId);
			stockDTO.setOrderNo(orderNo);
			stockDTO.setOrderResource(orderFrom);
			stockDTO.setOrderEntries(stockEntryList);
			stockResult = skuStockChangeExportService.reserveStock(stockDTO);
			if (!stockResult.isSuccess()) {
				throw new TradeCenterBusinessException(stockResult.getCode(),
						StringUtils.join(stockResult.getErrorMessages(), "\n"));
			}
		} catch (TradeCenterBusinessException tcbe) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderStockHandle-reserveOrderStock", JSONObject.toJSONString(tcbe));
			throw tcbe;
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderStockHandle-reserveOrderStock", JSONObject.toJSONString(e));
			throw e;
		}
		return stockResult;
	}

	/**
	 * 释放订单锁定库存
	 * 
	 * @param messageId
	 * @param orderFrom
	 * @param orderItemList
	 * @throws TradeCenterBusinessException
	 */
	public ExecuteResult<String> releaseOrderStock(String messageId, String orderFrom,
			List<TradeOrderItemsDTO> orderItemList) throws TradeCenterBusinessException {
		ExecuteResult<String> stockResult = null;
		Order4StockChangeDTO stockDTO = new Order4StockChangeDTO();
		Order4StockEntryDTO stockEntryDTO = null;
		List<Order4StockEntryDTO> stockEntryList = new ArrayList<Order4StockEntryDTO>();
		String orderNo = "";
		try {

			for (TradeOrderItemsDTO orderItemDTO : orderItemList) {
				stockEntryDTO = new Order4StockEntryDTO();
				orderNo = orderItemDTO.getOrderNo();
				stockEntryDTO.setOrderNo(orderNo);
				stockEntryDTO.setOrderResource(orderFrom);
				stockEntryDTO.setSkuCode(orderItemDTO.getSkuCode());
				stockEntryDTO.setIsBoxFlag(orderItemDTO.getIsBoxFlag());
				stockEntryDTO.setQuantity(orderItemDTO.getGoodsCount());
				stockEntryList.add(stockEntryDTO);
			}
			stockDTO.setMessageId(messageId);
			stockDTO.setOrderNo(orderNo);
			stockDTO.setOrderResource(orderFrom);
			stockDTO.setOrderEntries(stockEntryList);
			stockResult = skuStockChangeExportService.releaseStock(stockDTO);
			if (!stockResult.isSuccess()) {
				throw new TradeCenterBusinessException(stockResult.getCode(),
						StringUtils.join(stockResult.getErrorMessages(), "\n"));
			}
		} catch (TradeCenterBusinessException tcbe) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderStockHandle-releaseOrderStock", JSONObject.toJSONString(tcbe));
			throw tcbe;
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderStockHandle-releaseOrderStock", JSONObject.toJSONString(e));
			throw new TradeCenterBusinessException(ReturnCodeConst.SYSTEM_ERROR, e);
		}
		return stockResult;
	}

	/**
	 * 释放订单锁定库存
	 * 
	 * @param messageId
	 * @param orderFrom
	 * @param orderItemList
	 * @throws TradeCenterBusinessException
	 */
	public ExecuteResult<String> changeOrderStock(String messageId, String orderFrom,
			List<TradeOrderItemsDTO> orderItemList, boolean isOpposition) throws TradeCenterBusinessException {
		ExecuteResult<String> stockResult = null;
		Order4StockChangeDTO stockDTO = new Order4StockChangeDTO();
		Order4StockEntryDTO stockEntryDTO = null;
		List<Order4StockEntryDTO> stockEntryList = new ArrayList<Order4StockEntryDTO>();
		String dealFlag = "";
		String orderNo = "";
		int goodsCount = 0;
		TradeOrderItemsPriceHistoryDTO itemPriceHistoryDTO = null;
		try {

			for (TradeOrderItemsDTO orderItemDTO : orderItemList) {
				dealFlag = orderItemDTO.getDealFlag();
				orderNo = orderItemDTO.getOrderNo();
				if (StringUtils.isEmpty(dealFlag)) {
					continue;
				}
				if ("add".equals(dealFlag)) {
					stockEntryDTO = new Order4StockEntryDTO();
					stockEntryDTO.setStockTypeEnum(isOpposition ? StockTypeEnum.RELEASE : StockTypeEnum.RESERVE);
					stockEntryDTO.setOrderNo(orderNo);
					stockEntryDTO.setOrderResource(orderFrom);
					stockEntryDTO.setSkuCode(orderItemDTO.getSkuCode());
					stockEntryDTO.setIsBoxFlag(orderItemDTO.getIsBoxFlag());
					stockEntryDTO.setQuantity(orderItemDTO.getGoodsCount());
				} else if ("update".equals(dealFlag)) {
					stockEntryDTO = new Order4StockEntryDTO();
					itemPriceHistoryDTO = orderItemDTO.getItemPriceHistoryDTO();
					goodsCount = itemPriceHistoryDTO.getBeforeBargainingGoodsCount()
							- itemPriceHistoryDTO.getAfterBargainingGoodsCount();
					if (goodsCount == 0) {
						continue;
					} else if (goodsCount > 0) {
						stockEntryDTO.setStockTypeEnum(isOpposition ? StockTypeEnum.RESERVE : StockTypeEnum.RELEASE);
					} else {
						goodsCount = goodsCount * -1;
						stockEntryDTO.setStockTypeEnum(isOpposition ? StockTypeEnum.RELEASE : StockTypeEnum.RESERVE);
					}
					stockEntryDTO.setOrderNo(orderNo);
					stockEntryDTO.setOrderResource(orderFrom);
					stockEntryDTO.setSkuCode(orderItemDTO.getSkuCode());
					stockEntryDTO.setIsBoxFlag(orderItemDTO.getIsBoxFlag());
					stockEntryDTO.setQuantity(goodsCount);
				} else if ("delete".equals(dealFlag)) {
					stockEntryDTO = new Order4StockEntryDTO();
					stockEntryDTO.setStockTypeEnum(isOpposition ? StockTypeEnum.RESERVE : StockTypeEnum.RELEASE);
					stockEntryDTO.setOrderNo(orderNo);
					stockEntryDTO.setOrderResource(orderFrom);
					stockEntryDTO.setSkuCode(orderItemDTO.getSkuCode());
					stockEntryDTO.setIsBoxFlag(orderItemDTO.getIsBoxFlag());
					if (YesNoEnum.YES.getValue() == orderItemDTO.getIsChangePrice()) {
						if (orderItemDTO.getBargainingGoodsCount() > 0) {
							stockEntryDTO.setQuantity(orderItemDTO.getBargainingGoodsCount());
						} else {
							stockEntryDTO.setQuantity(orderItemDTO.getGoodsCount());
						}
					} else {
						stockEntryDTO.setQuantity(orderItemDTO.getGoodsCount());
					}
				}
				stockEntryList.add(stockEntryDTO);
			}
			stockDTO.setMessageId(messageId);
			stockDTO.setOrderNo(orderNo);
			stockDTO.setOrderResource(orderFrom);
			stockDTO.setOrderEntries(stockEntryList);
			stockResult = skuStockChangeExportService.comboChangeStock(stockDTO);
			if (!stockResult.isSuccess()) {
				throw new TradeCenterBusinessException(stockResult.getCode(),
						StringUtils.join(stockResult.getErrorMessages(), "\n"));
			}
		} catch (TradeCenterBusinessException tcbe) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderStockHandle-changeOrderStock", JSONObject.toJSONString(tcbe));
			throw tcbe;
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderStockHandle-changeOrderStock", JSONObject.toJSONString(e));
			throw new TradeCenterBusinessException(ReturnCodeConst.SYSTEM_ERROR, e);
		}
		return stockResult;
	}
}
