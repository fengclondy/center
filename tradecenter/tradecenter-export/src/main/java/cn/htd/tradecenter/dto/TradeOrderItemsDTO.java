package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TradeOrderItemsDTO extends TradeOrdersShowDTO implements Serializable {

	private static final long serialVersionUID = -7232023977339053424L;

	private String orderItemNo;

	private String channelCode;

	private String itemCode;

	private String goodsName;

	private String skuCode;

	private String outerSkuCode;

	private String skuErpCode;

	private String skuEanCode;

	private String skuPictureUrl;

	private String itemSpuCode;

	private Long firstCategoryId;

	private String firstCategoryName;

	private Long secondCategoryId;

	private String secondCategoryName;

	private Long thirdCategoryId;

	private String thirdCategoryName;

	private String categoryIdList;

	private String categoryNameList;

	private String erpFirstCategoryCode;

	private String erpFiveCategoryCode;

	private Long brandId;

	private String brandName;

	private String customerManagerCode;

	private String customerManagerName;

	private int isVipItem;

	private String vipItemType;

	private int vipSyncFlag;

	private Integer goodsCount;

	private BigDecimal costPrice;

	private BigDecimal priceFloatingRatio;

	private BigDecimal commissionRatio;

	private BigDecimal commissionAmount;

	private int isBoxFlag;

	private BigDecimal salePrice;

	private String goodsPriceType;

	private BigDecimal goodsPrice;

	private BigDecimal bargainingGoodsPrice;

	private Integer bargainingGoodsCount;

	private Long shopFreightTemplateId;

	private String deliveryAreaCode;

	private BigDecimal goodsAmount;

	private BigDecimal goodsFreight;

	private BigDecimal bargainingGoodsAmount;

	private BigDecimal bargainingGoodsFreight;

	private BigDecimal orderItemTotalAmount;

	private BigDecimal orderItemPayAmount;

	private BigDecimal goodsRealPrice;

	private String outerChannelOrderNo;

	private String outerChannelPuchaseStatus;

	private String outerChannelStatus;

	private String orderItemStatus;

	private Date orderItemReceiptTime;

	private String orderItemErrorStatus;

	private Date orderItemErrorTime;

	private String orderItemErrorReason;

	private int isAddOrderItem;

	private int isCancelOrderItem;

	private Date orderItemCancelTime;

	private Long orderItemCancelOperatorId;

	private String orderItemCancelOperatorName;

	private String orderItemCancelReason;

	private String refundStatus;

	private int isOutDistribtion;

	private Long erpDistributionId;

	private String erpSholesalerCode;

	private String erpRebateNo;

	private String erpRebateCode;

	private int isSettled;

	private String erpLockBalanceCode;

	private BigDecimal orderPayAmount;

	private List<TradeOrderItemsDiscountDTO> discountDTOList;

	private TradeOrderErpDistributionDTO erpDistributionDTO;

	private TradeOrderItemsPriceHistoryDTO itemPriceHistoryDTO;

	private List<TradeOrderItemsWarehouseDetailDTO> warehouseDTOList;

	private List<TradeOrderItemsStatusHistoryDTO> itemStatusHistoryDTOList;

	private String dealFlag = "";
	private String promotionType; //促销活动类型 1：优惠券，2：秒杀，3：限时购

	
	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo == null ? null : orderItemNo.trim();
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode == null ? null : channelCode.trim();
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode == null ? null : itemCode.trim();
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode == null ? null : skuCode.trim();
	}

	public String getOuterSkuCode() {
		return outerSkuCode;
	}

	public void setOuterSkuCode(String outerSkuCode) {
		this.outerSkuCode = outerSkuCode == null ? null : outerSkuCode.trim();
	}

	public String getSkuErpCode() {
		return skuErpCode;
	}

	public void setSkuErpCode(String skuErpCode) {
		this.skuErpCode = skuErpCode == null ? null : skuErpCode.trim();
	}

	public String getSkuEanCode() {
		return skuEanCode;
	}

	public void setSkuEanCode(String skuEanCode) {
		this.skuEanCode = skuEanCode == null ? null : skuEanCode.trim();
	}

	public String getSkuPictureUrl() {
		return skuPictureUrl;
	}

	public void setSkuPictureUrl(String skuPictureUrl) {
		this.skuPictureUrl = skuPictureUrl == null ? null : skuPictureUrl.trim();
	}

	public String getItemSpuCode() {
		return itemSpuCode;
	}

	public void setItemSpuCode(String itemSpuCode) {
		this.itemSpuCode = itemSpuCode == null ? null : itemSpuCode.trim();
	}

	public Long getFirstCategoryId() {
		return firstCategoryId;
	}

	public void setFirstCategoryId(Long firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}

	public String getFirstCategoryName() {
		return firstCategoryName;
	}

	public void setFirstCategoryName(String firstCategoryName) {
		this.firstCategoryName = firstCategoryName == null ? null : firstCategoryName.trim();
	}

	public Long getSecondCategoryId() {
		return secondCategoryId;
	}

	public void setSecondCategoryId(Long secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}

	public String getSecondCategoryName() {
		return secondCategoryName;
	}

	public void setSecondCategoryName(String secondCategoryName) {
		this.secondCategoryName = secondCategoryName == null ? null : secondCategoryName.trim();
	}

	public Long getThirdCategoryId() {
		return thirdCategoryId;
	}

	public void setThirdCategoryId(Long thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}

	public String getThirdCategoryName() {
		return thirdCategoryName;
	}

	public void setThirdCategoryName(String thirdCategoryName) {
		this.thirdCategoryName = thirdCategoryName == null ? null : thirdCategoryName.trim();
	}

	public String getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(String categoryIdList) {
		this.categoryIdList = categoryIdList == null ? null : categoryIdList.trim();
	}

	public String getCategoryNameList() {
		return categoryNameList;
	}

	public void setCategoryNameList(String categoryNameList) {
		this.categoryNameList = categoryNameList == null ? null : categoryNameList.trim();
	}

	public String getErpFirstCategoryCode() {
		return erpFirstCategoryCode;
	}

	public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
		this.erpFirstCategoryCode = erpFirstCategoryCode == null ? null : erpFirstCategoryCode.trim();
	}

	public String getErpFiveCategoryCode() {
		return erpFiveCategoryCode;
	}

	public void setErpFiveCategoryCode(String erpFiveCategoryCode) {
		this.erpFiveCategoryCode = erpFiveCategoryCode == null ? null : erpFiveCategoryCode.trim();
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public String getCustomerManagerCode() {
		return customerManagerCode;
	}

	public void setCustomerManagerCode(String customerManagerCode) {
		this.customerManagerCode = customerManagerCode;
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	public int getIsVipItem() {
		return isVipItem;
	}

	public void setIsVipItem(int isVipItem) {
		this.isVipItem = isVipItem;
	}

	public String getVipItemType() {
		return vipItemType;
	}

	public void setVipItemType(String vipItemType) {
		this.vipItemType = vipItemType == null ? null : vipItemType.trim();
	}

	public int getVipSyncFlag() {
		return vipSyncFlag;
	}

	public void setVipSyncFlag(int vipSyncFlag) {
		this.vipSyncFlag = vipSyncFlag;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getPriceFloatingRatio() {
		return priceFloatingRatio;
	}

	public void setPriceFloatingRatio(BigDecimal priceFloatingRatio) {
		this.priceFloatingRatio = priceFloatingRatio;
	}

	public BigDecimal getCommissionRatio() {
		return commissionRatio;
	}

	public void setCommissionRatio(BigDecimal commissionRatio) {
		this.commissionRatio = commissionRatio;
	}

	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public int getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(int isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getGoodsPriceType() {
		return goodsPriceType;
	}

	public void setGoodsPriceType(String goodsPriceType) {
		this.goodsPriceType = goodsPriceType == null ? null : goodsPriceType.trim();
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public BigDecimal getBargainingGoodsPrice() {
		return bargainingGoodsPrice;
	}

	public void setBargainingGoodsPrice(BigDecimal bargainingGoodsPrice) {
		this.bargainingGoodsPrice = bargainingGoodsPrice;
	}

	public Integer getBargainingGoodsCount() {
		return bargainingGoodsCount;
	}

	public void setBargainingGoodsCount(Integer bargainingGoodsCount) {
		this.bargainingGoodsCount = bargainingGoodsCount;
	}

	public Long getShopFreightTemplateId() {
		return shopFreightTemplateId;
	}

	public void setShopFreightTemplateId(Long shopFreightTemplateId) {
		this.shopFreightTemplateId = shopFreightTemplateId;
	}

	public String getDeliveryAreaCode() {
		return deliveryAreaCode;
	}

	public void setDeliveryAreaCode(String deliveryAreaCode) {
		this.deliveryAreaCode = deliveryAreaCode == null ? null : deliveryAreaCode.trim();
	}

	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public BigDecimal getGoodsFreight() {
		return goodsFreight;
	}

	public void setGoodsFreight(BigDecimal goodsFreight) {
		this.goodsFreight = goodsFreight;
	}

	public BigDecimal getBargainingGoodsAmount() {
		return bargainingGoodsAmount;
	}

	public void setBargainingGoodsAmount(BigDecimal bargainingGoodsAmount) {
		this.bargainingGoodsAmount = bargainingGoodsAmount;
	}

	public BigDecimal getBargainingGoodsFreight() {
		return bargainingGoodsFreight;
	}

	public void setBargainingGoodsFreight(BigDecimal bargainingGoodsFreight) {
		this.bargainingGoodsFreight = bargainingGoodsFreight;
	}

	public BigDecimal getOrderItemTotalAmount() {
		return orderItemTotalAmount;
	}

	public void setOrderItemTotalAmount(BigDecimal orderItemTotalAmount) {
		this.orderItemTotalAmount = orderItemTotalAmount;
	}

	public BigDecimal getOrderItemPayAmount() {
		return orderItemPayAmount;
	}

	public void setOrderItemPayAmount(BigDecimal orderItemPayAmount) {
		this.orderItemPayAmount = orderItemPayAmount;
	}

	public BigDecimal getGoodsRealPrice() {
		return goodsRealPrice;
	}

	public void setGoodsRealPrice(BigDecimal goodsRealPrice) {
		this.goodsRealPrice = goodsRealPrice;
	}

	public String getOuterChannelOrderNo() {
		return outerChannelOrderNo;
	}

	public void setOuterChannelOrderNo(String outerChannelOrderNo) {
		this.outerChannelOrderNo = outerChannelOrderNo == null ? null : outerChannelOrderNo.trim();
	}

	public String getOuterChannelPuchaseStatus() {
		return outerChannelPuchaseStatus;
	}

	public void setOuterChannelPuchaseStatus(String outerChannelPuchaseStatus) {
		this.outerChannelPuchaseStatus = outerChannelPuchaseStatus == null ? null : outerChannelPuchaseStatus.trim();
	}

	public String getOuterChannelStatus() {
		return outerChannelStatus;
	}

	public void setOuterChannelStatus(String outerChannelStatus) {
		this.outerChannelStatus = outerChannelStatus == null ? null : outerChannelStatus.trim();
	}

	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus == null ? null : orderItemStatus.trim();
	}

	public Date getOrderItemReceiptTime() {
		return orderItemReceiptTime;
	}

	public void setOrderItemReceiptTime(Date orderItemReceiptTime) {
		this.orderItemReceiptTime = orderItemReceiptTime;
	}

	public String getOrderItemErrorStatus() {
		return orderItemErrorStatus;
	}

	public void setOrderItemErrorStatus(String orderItemErrorStatus) {
		this.orderItemErrorStatus = orderItemErrorStatus == null ? null : orderItemErrorStatus.trim();
	}

	public Date getOrderItemErrorTime() {
		return orderItemErrorTime;
	}

	public void setOrderItemErrorTime(Date orderItemErrorTime) {
		this.orderItemErrorTime = orderItemErrorTime;
	}

	public String getOrderItemErrorReason() {
		return orderItemErrorReason;
	}

	public void setOrderItemErrorReason(String orderItemErrorReason) {
		this.orderItemErrorReason = orderItemErrorReason == null ? null : orderItemErrorReason.trim();
	}

	public int getIsAddOrderItem() {
		return isAddOrderItem;
	}

	public void setIsAddOrderItem(int isAddOrderItem) {
		this.isAddOrderItem = isAddOrderItem;
	}

	public int getIsCancelOrderItem() {
		return isCancelOrderItem;
	}

	public void setIsCancelOrderItem(int isCancelOrderItem) {
		this.isCancelOrderItem = isCancelOrderItem;
	}

	public Date getOrderItemCancelTime() {
		return orderItemCancelTime;
	}

	public void setOrderItemCancelTime(Date orderItemCancelTime) {
		this.orderItemCancelTime = orderItemCancelTime;
	}

	public Long getOrderItemCancelOperatorId() {
		return orderItemCancelOperatorId;
	}

	public void setOrderItemCancelOperatorId(Long orderItemCancelOperatorId) {
		this.orderItemCancelOperatorId = orderItemCancelOperatorId;
	}

	public String getOrderItemCancelOperatorName() {
		return orderItemCancelOperatorName;
	}

	public void setOrderItemCancelOperatorName(String orderItemCancelOperatorName) {
		this.orderItemCancelOperatorName = orderItemCancelOperatorName == null ? null
				: orderItemCancelOperatorName.trim();
	}

	public String getOrderItemCancelReason() {
		return orderItemCancelReason;
	}

	public void setOrderItemCancelReason(String orderItemCancelReason) {
		this.orderItemCancelReason = orderItemCancelReason == null ? null : orderItemCancelReason.trim();
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus == null ? null : refundStatus.trim();
	}

	public int getIsOutDistribtion() {
		return isOutDistribtion;
	}

	public void setIsOutDistribtion(int isOutDistribtion) {
		this.isOutDistribtion = isOutDistribtion;
	}

	public Long getErpDistributionId() {
		return erpDistributionId;
	}

	public String getErpSholesalerCode() {
		return erpSholesalerCode;
	}

	public void setErpSholesalerCode(String erpSholesalerCode) {
		this.erpSholesalerCode = erpSholesalerCode;
	}

	public void setErpDistributionId(Long erpDistributionId) {
		this.erpDistributionId = erpDistributionId;
	}

	public String getErpRebateNo() {
		return erpRebateNo;
	}

	public void setErpRebateNo(String erpRebateNo) {
		this.erpRebateNo = erpRebateNo == null ? null : erpRebateNo.trim();
	}

	public String getErpRebateCode() {
		return erpRebateCode;
	}

	public void setErpRebateCode(String erpRebateCode) {
		this.erpRebateCode = erpRebateCode == null ? null : erpRebateCode.trim();
	}

	public int getIsSettled() {
		return isSettled;
	}

	public void setIsSettled(int isSettled) {
		this.isSettled = isSettled;
	}

	public String getErpLockBalanceCode() {
		return erpLockBalanceCode;
	}

	public void setErpLockBalanceCode(String erpLockBalanceCode) {
		this.erpLockBalanceCode = erpLockBalanceCode;
	}

	public List<TradeOrderItemsDiscountDTO> getDiscountDTOList() {
		return discountDTOList;
	}

	public void setDiscountDTOList(List<TradeOrderItemsDiscountDTO> discountDTOList) {
		this.discountDTOList = discountDTOList;
	}

	public TradeOrderErpDistributionDTO getErpDistributionDTO() {
		return erpDistributionDTO;
	}

	public void setErpDistributionDTO(TradeOrderErpDistributionDTO erpDistributionDTO) {
		this.erpDistributionDTO = erpDistributionDTO;
	}

	public TradeOrderItemsPriceHistoryDTO getItemPriceHistoryDTO() {
		return itemPriceHistoryDTO;
	}

	public void setItemPriceHistoryDTO(TradeOrderItemsPriceHistoryDTO itemPriceHistoryDTO) {
		this.itemPriceHistoryDTO = itemPriceHistoryDTO;
	}

	public List<TradeOrderItemsWarehouseDetailDTO> getWarehouseDTOList() {
		return warehouseDTOList;
	}

	public void setWarehouseDTOList(List<TradeOrderItemsWarehouseDetailDTO> warehouseDTOList) {
		this.warehouseDTOList = warehouseDTOList;
	}

	public List<TradeOrderItemsStatusHistoryDTO> getItemStatusHistoryDTOList() {
		return itemStatusHistoryDTOList;
	}

	public void setItemStatusHistoryDTOList(List<TradeOrderItemsStatusHistoryDTO> itemStatusHistoryDTOList) {
		this.itemStatusHistoryDTOList = itemStatusHistoryDTOList;
	}

	public String getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public void setTradeOrderItemInfo(TradeOrderItemsDTO itemDTO) {
		String[] codeArr = null;

		super.setTradeOrderInfo(itemDTO);
		this.orderItemNo = itemDTO.getOrderItemNo();
		this.channelCode = itemDTO.getChannelCode();
		this.itemCode = itemDTO.getItemCode();
		this.goodsName = itemDTO.getGoodsName();
		this.skuCode = itemDTO.getSkuCode();
		this.outerSkuCode = itemDTO.getOuterSkuCode();
		this.skuErpCode = itemDTO.getSkuErpCode();
		this.skuEanCode = itemDTO.getSkuEanCode();
		this.skuPictureUrl = itemDTO.getSkuPictureUrl();
		this.itemSpuCode = itemDTO.getItemSpuCode();
		this.firstCategoryId = itemDTO.getFirstCategoryId();
		this.firstCategoryName = itemDTO.getFirstCategoryName();
		this.secondCategoryId = itemDTO.getSecondCategoryId();
		this.secondCategoryName = itemDTO.getSecondCategoryName();
		this.thirdCategoryId = itemDTO.getThirdCategoryId();
		this.thirdCategoryName = itemDTO.getThirdCategoryName();
		this.categoryIdList = itemDTO.getCategoryIdList();
		this.categoryNameList = itemDTO.getCategoryNameList();
		this.erpFirstCategoryCode = itemDTO.getErpFirstCategoryCode();
		this.erpFiveCategoryCode = itemDTO.getErpFiveCategoryCode();
		this.brandId = itemDTO.getBrandId();
		this.brandName = itemDTO.getBrandName();
		this.customerManagerCode = itemDTO.getCustomerManagerCode();
		this.customerManagerName = itemDTO.getCustomerManagerName();
		this.isVipItem = itemDTO.getIsVipItem();
		this.vipItemType = itemDTO.getVipItemType();
		this.vipSyncFlag = itemDTO.getVipSyncFlag();
		this.goodsCount = itemDTO.getGoodsCount();
		this.costPrice = itemDTO.getCostPrice();
		this.priceFloatingRatio = itemDTO.getPriceFloatingRatio();
		this.commissionRatio = itemDTO.getCommissionRatio();
		this.commissionAmount = itemDTO.getCommissionAmount();
		this.isBoxFlag = itemDTO.getIsBoxFlag();
		this.salePrice = itemDTO.getSalePrice();
		this.goodsPriceType = itemDTO.getGoodsPriceType();
		this.goodsPrice = itemDTO.getGoodsPrice();
		this.bargainingGoodsPrice = itemDTO.getBargainingGoodsPrice();
		this.bargainingGoodsCount = itemDTO.getBargainingGoodsCount();
		this.shopFreightTemplateId = itemDTO.getShopFreightTemplateId();
		this.deliveryAreaCode = itemDTO.getDeliveryAreaCode();
		this.goodsAmount = itemDTO.getGoodsAmount();
		this.goodsFreight = itemDTO.getGoodsFreight();
		this.setTotalDiscountAmount(itemDTO.getTotalDiscountAmount());
		this.setShopDiscountAmount(itemDTO.getShopDiscountAmount());
		this.setPlatformDiscountAmount(itemDTO.getPlatformDiscountAmount());
		this.setUsedRebateAmount(itemDTO.getUsedRebateAmount());
		this.bargainingGoodsAmount = itemDTO.getBargainingGoodsAmount();
		this.bargainingGoodsFreight = itemDTO.getBargainingGoodsFreight();
		this.orderItemTotalAmount = itemDTO.getOrderItemTotalAmount();
		this.orderItemPayAmount = itemDTO.getOrderItemPayAmount();
		this.goodsRealPrice = itemDTO.getGoodsRealPrice();
		this.setHasUsedCoupon(itemDTO.getHasUsedCoupon());
		this.setIsChangePrice(itemDTO.getIsChangePrice());
		this.outerChannelOrderNo = itemDTO.getOuterChannelOrderNo();
		this.outerChannelPuchaseStatus = itemDTO.getOuterChannelPuchaseStatus();
		this.outerChannelStatus = itemDTO.getOuterChannelStatus();
		this.orderItemStatus = itemDTO.getOrderItemStatus();
		this.orderItemReceiptTime = itemDTO.getOrderItemReceiptTime();
		this.orderItemErrorStatus = itemDTO.getOrderItemErrorStatus();
		this.orderItemErrorTime = itemDTO.getOrderItemErrorTime();
		this.orderItemErrorReason = itemDTO.getOrderItemErrorReason();
		this.isAddOrderItem = itemDTO.getIsAddOrderItem();
		this.isCancelOrderItem = itemDTO.getIsCancelOrderItem();
		this.orderItemCancelTime = itemDTO.getOrderItemCancelTime();
		this.orderItemCancelOperatorId = itemDTO.getOrderItemCancelOperatorId();
		this.orderItemCancelOperatorName = itemDTO.getOrderItemCancelOperatorName();
		this.orderItemCancelReason = itemDTO.getOrderItemCancelReason();
		this.refundStatus = itemDTO.getRefundStatus();
		this.isOutDistribtion = itemDTO.getIsOutDistribtion();
		this.erpDistributionId = itemDTO.getErpDistributionId();
		if (!StringUtils.isEmpty(itemDTO.getErpSholesalerCode())) {
			this.erpSholesalerCode = itemDTO.getErpSholesalerCode();
			if (this.erpSholesalerCode.indexOf("-") >= 0) {
				codeArr = this.erpSholesalerCode.split("-");
				this.erpSholesalerCode = codeArr[1];
			}
		}
		this.erpRebateNo = itemDTO.getErpRebateNo();
		this.erpRebateCode = itemDTO.getErpRebateCode();
		this.isSettled = itemDTO.getIsSettled();
		this.erpLockBalanceCode = itemDTO.getErpLockBalanceCode();
		this.discountDTOList = itemDTO.getDiscountDTOList();
		this.erpDistributionDTO = itemDTO.getErpDistributionDTO();
		this.warehouseDTOList = itemDTO.getWarehouseDTOList();
		this.itemStatusHistoryDTOList = itemDTO.getItemStatusHistoryDTOList();
		this.setCreateId(itemDTO.getCreateId());
		this.setCreateName(itemDTO.getCreateName());
		this.setCreateTime(itemDTO.getCreateTime());
		this.setModifyId(itemDTO.getModifyId());
		this.setModifyName(itemDTO.getModifyName());
		this.setModifyTime(itemDTO.getModifyTime());
	}

	@Override
	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}

	@Override
	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}
}