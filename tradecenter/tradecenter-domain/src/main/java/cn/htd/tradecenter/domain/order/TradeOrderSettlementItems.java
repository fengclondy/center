package cn.htd.tradecenter.domain.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class TradeOrderSettlementItems implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String orderItemNo;
	
	private String orderNo;
	
	//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
	private String channelCode;
	// 商品编码
	private String itemCode;
	// 商品名称
	private String goodsName;
    // 商品SKU编码
	private String skuCode;
    // 商品SKUERP编码，内部商品时保存ERP编码，外接商品时保存外接商品SKUID
	private String skuErpCode;
    // 商品SKUEAN码
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

	// 是否VIP套餐商品
	private int isVipItem;

	private String vipItemType;

	//商品数量
	private Integer goodsCount;
    // 外部供货价/分销限价s
	private BigDecimal costPrice;
    // 价格浮动比例（商品+商品专用）
	private BigDecimal priceFloatingRatio;

	// 佣金比例（商品+商品专用）
	private BigDecimal commissionRatio;
    // 佣金金额（商品+商品专用）
	private BigDecimal commissionAmount;

	private BigDecimal goodsAmount;

	// 订单行总价
	private BigDecimal orderItemTotalAmount;
    // 订单行实付金额
	private BigDecimal orderItemPayAmount;

	private BigDecimal goodsRealPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
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

	public String getSkuPictureUrl() {
		return skuPictureUrl;
	}

	public void setSkuPictureUrl(String skuPictureUrl) {
		this.skuPictureUrl = skuPictureUrl;
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
		this.vipItemType = vipItemType;
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

	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
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
	


	

	
}