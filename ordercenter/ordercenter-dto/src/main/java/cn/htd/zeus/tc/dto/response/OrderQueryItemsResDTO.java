package cn.htd.zeus.tc.dto.response;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单行DTO(orderItemsList)
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: OrderQueryItemsResDTO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 */
public class OrderQueryItemsResDTO extends GenricResDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -8018950477426345656L;

    private String orderNo;

    private String orderItemNo;

    private String channelCode;

    private String itemCode;

    private String goodsName;

    private String skuCode;

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

    private Integer isVipItem;

    private String vipItemType;

    private Integer vipSyncFlag;

    private Integer goodsCount;

    private BigDecimal costPrice;

    private BigDecimal priceFloatingRatio;

    private BigDecimal commissionRatio;

    private BigDecimal commissionAmount;

    private Integer isBoxFlag;

    private BigDecimal salePrice;

    private String goodsPriceType;

    private BigDecimal goodsPrice;

    private BigDecimal bargainingGoodsPrice;

    private Integer bargainingGoodsCount;

    private Long shopFreightTemplateId;

    private String deliveryAreaCode;

    private BigDecimal goodsAmount;

    private BigDecimal goodsFreight;

    private BigDecimal totalDiscountAmount;

    private BigDecimal shopDiscountAmount;

    private BigDecimal platformDiscountAmount;

    private BigDecimal usedRebateAmount;

    private BigDecimal bargainingGoodsAmount;

    private BigDecimal bargainingGoodsFreight;

    private BigDecimal orderItemTotalAmount;

    private BigDecimal orderItemPayAmount;

    private BigDecimal goodsRealPrice;

    private Integer hasUsedCoupon;

    private Integer isChangePrice;

    private String outerChannelOrderNo;

    private String outerChannelPuchaseStatus;

    private String outerChannelStatus;

    private String orderItemStatus;

    private Date orderItemReceiptTime;

    private String orderItemErrorStatus;

    private Date orderItemErrorTime;

    private String orderItemErrorReason;

    private Integer isAddOrderItem;

    private Integer isCancelOrderItem;

    private Date orderItemCancelTime;

    private Long orderItemCancelOperatorId;

    private String orderItemCancelOperatorName;

    private String orderItemCancelReason;

    private String refundStatus;

    private Integer isOutDistribtion;

    private Long erpDistributionId;

    private String erpSholesalerCode;

    private String erpRebateNo;

    private String erpRebateCode;

    private Integer isSettled;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    private String orderItemStatusText;

    private String outerSkuCode;//京东sku
	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getSkuPictureUrl() {
		return skuPictureUrl;
	}

	public void setSkuPictureUrl(String skuPictureUrl) {
		this.skuPictureUrl = skuPictureUrl;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public BigDecimal getOrderItemPayAmount() {
		return orderItemPayAmount;
	}

	public void setOrderItemPayAmount(BigDecimal orderItemPayAmount) {
		this.orderItemPayAmount = orderItemPayAmount;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSkuErpCode() {
		return skuErpCode;
	}

	public void setSkuErpCode(String skuErpCode) {
		this.skuErpCode = skuErpCode;
	}

	public String getSkuEanCode() {
		return skuEanCode;
	}

	public void setSkuEanCode(String skuEanCode) {
		this.skuEanCode = skuEanCode;
	}

	public String getItemSpuCode() {
		return itemSpuCode;
	}

	public void setItemSpuCode(String itemSpuCode) {
		this.itemSpuCode = itemSpuCode;
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
		this.firstCategoryName = firstCategoryName;
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
		this.secondCategoryName = secondCategoryName;
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
		this.thirdCategoryName = thirdCategoryName;
	}

	public String getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(String categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public String getCategoryNameList() {
		return categoryNameList;
	}

	public void setCategoryNameList(String categoryNameList) {
		this.categoryNameList = categoryNameList;
	}

	public String getErpFirstCategoryCode() {
		return erpFirstCategoryCode;
	}

	public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
		this.erpFirstCategoryCode = erpFirstCategoryCode;
	}

	public String getErpFiveCategoryCode() {
		return erpFiveCategoryCode;
	}

	public void setErpFiveCategoryCode(String erpFiveCategoryCode) {
		this.erpFiveCategoryCode = erpFiveCategoryCode;
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
		this.brandName = brandName;
	}

	public Integer getIsVipItem() {
		return isVipItem;
	}

	public void setIsVipItem(Integer isVipItem) {
		this.isVipItem = isVipItem;
	}

	public String getVipItemType() {
		return vipItemType;
	}

	public void setVipItemType(String vipItemType) {
		this.vipItemType = vipItemType;
	}

	public Integer getVipSyncFlag() {
		return vipSyncFlag;
	}

	public void setVipSyncFlag(Integer vipSyncFlag) {
		this.vipSyncFlag = vipSyncFlag;
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

	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(Integer isBoxFlag) {
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
		this.goodsPriceType = goodsPriceType;
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
		this.deliveryAreaCode = deliveryAreaCode;
	}

	public BigDecimal getGoodsFreight() {
		return goodsFreight;
	}

	public void setGoodsFreight(BigDecimal goodsFreight) {
		this.goodsFreight = goodsFreight;
	}

	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public BigDecimal getShopDiscountAmount() {
		return shopDiscountAmount;
	}

	public void setShopDiscountAmount(BigDecimal shopDiscountAmount) {
		this.shopDiscountAmount = shopDiscountAmount;
	}

	public BigDecimal getPlatformDiscountAmount() {
		return platformDiscountAmount;
	}

	public void setPlatformDiscountAmount(BigDecimal platformDiscountAmount) {
		this.platformDiscountAmount = platformDiscountAmount;
	}

	public BigDecimal getUsedRebateAmount() {
		return usedRebateAmount;
	}

	public void setUsedRebateAmount(BigDecimal usedRebateAmount) {
		this.usedRebateAmount = usedRebateAmount;
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

	public BigDecimal getGoodsRealPrice() {
		return goodsRealPrice;
	}

	public void setGoodsRealPrice(BigDecimal goodsRealPrice) {
		this.goodsRealPrice = goodsRealPrice;
	}

	public Integer getHasUsedCoupon() {
		return hasUsedCoupon;
	}

	public void setHasUsedCoupon(Integer hasUsedCoupon) {
		this.hasUsedCoupon = hasUsedCoupon;
	}

	public Integer getIsChangePrice() {
		return isChangePrice;
	}

	public void setIsChangePrice(Integer isChangePrice) {
		this.isChangePrice = isChangePrice;
	}

	public String getOuterChannelOrderNo() {
		return outerChannelOrderNo;
	}

	public void setOuterChannelOrderNo(String outerChannelOrderNo) {
		this.outerChannelOrderNo = outerChannelOrderNo;
	}

	public String getOuterChannelPuchaseStatus() {
		return outerChannelPuchaseStatus;
	}

	public void setOuterChannelPuchaseStatus(String outerChannelPuchaseStatus) {
		this.outerChannelPuchaseStatus = outerChannelPuchaseStatus;
	}

	public String getOuterChannelStatus() {
		return outerChannelStatus;
	}

	public void setOuterChannelStatus(String outerChannelStatus) {
		this.outerChannelStatus = outerChannelStatus;
	}

	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
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
		this.orderItemErrorStatus = orderItemErrorStatus;
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
		this.orderItemErrorReason = orderItemErrorReason;
	}

	public Integer getIsAddOrderItem() {
		return isAddOrderItem;
	}

	public void setIsAddOrderItem(Integer isAddOrderItem) {
		this.isAddOrderItem = isAddOrderItem;
	}

	public Integer getIsCancelOrderItem() {
		return isCancelOrderItem;
	}

	public void setIsCancelOrderItem(Integer isCancelOrderItem) {
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
		this.orderItemCancelOperatorName = orderItemCancelOperatorName;
	}

	public String getOrderItemCancelReason() {
		return orderItemCancelReason;
	}

	public void setOrderItemCancelReason(String orderItemCancelReason) {
		this.orderItemCancelReason = orderItemCancelReason;
	}

	public Integer getIsOutDistribtion() {
		return isOutDistribtion;
	}

	public void setIsOutDistribtion(Integer isOutDistribtion) {
		this.isOutDistribtion = isOutDistribtion;
	}

	public Long getErpDistributionId() {
		return erpDistributionId;
	}

	public void setErpDistributionId(Long erpDistributionId) {
		this.erpDistributionId = erpDistributionId;
	}

	public String getErpSholesalerCode() {
		return erpSholesalerCode;
	}

	public void setErpSholesalerCode(String erpSholesalerCode) {
		this.erpSholesalerCode = erpSholesalerCode;
	}

	public String getErpRebateNo() {
		return erpRebateNo;
	}

	public void setErpRebateNo(String erpRebateNo) {
		this.erpRebateNo = erpRebateNo;
	}

	public String getErpRebateCode() {
		return erpRebateCode;
	}

	public void setErpRebateCode(String erpRebateCode) {
		this.erpRebateCode = erpRebateCode;
	}

	public Integer getIsSettled() {
		return isSettled;
	}

	public void setIsSettled(Integer isSettled) {
		this.isSettled = isSettled;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOrderItemStatusText() {
		return orderItemStatusText;
	}

	public void setOrderItemStatusText(String orderItemStatusText) {
		this.orderItemStatusText = orderItemStatusText;
	}

	public String getOuterSkuCode() {
		return outerSkuCode;
	}

	public void setOuterSkuCode(String outerSkuCode) {
		this.outerSkuCode = outerSkuCode;
	}

}
