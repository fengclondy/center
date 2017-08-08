package cn.htd.tradecenter.dto.bill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 结算详情表
 * @author biao.hu
 *
 */
public class TradeSettlementDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8568650229315590415L;
	
	
	private Long id;
	//结算单号
	private String settlementNo;
	//订单号
	private String orderNo;
	//子订单号
	private String orderItemNo;
	//会员ID
	private Long buyerId;
	//会员编号
	private String buyerCode;
	//会员类型 1:非会员，2：担保会员，3：正式会员
	private String buyerType;
	//会员名称
	private String buyerName;
	//卖家ID
	private Long sellerId;
	//卖家编号
	private String sellerCode;
	//卖家类型
	private String sellerType;
	//卖家名称
	private String sellerName;
	//店铺ID
	private Long shopId;
	//店铺名称
	private String shopName;
	//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
	private String channelCode;
	//渠道名称
	private String channelName;
	//外接渠道品类编码
	private String outerChannelCategoryCode;
	//外接渠道品类名称
	private String outerChannelCategoryName;
	//外接渠道品牌编码
	private String outerChannelBrandCode;
	//外接渠道品牌名称
	private String outerChannelBrandName;
	//商品编码
	private String itemCode;
	//商品名称
	private String goodsName;
	//商品SKU编码
	private String skuCode;
	//商品SKUERP编码，内部商品时保存ERP编码，外接商品时保存外接商品SKUID
	private String skuErpCode;
	//商品SKUEAN码
	private String skuEanCode;
	//一级类目ID
	private Long firstCategoryId;
	//一级类目名称
	private String firstCategoryName;
	//二级类目ID
	private Long secondCategoryId;
	//二级类目名称
	private String secondCategoryName;
	//三级类目ID
	private Long thirdCategoryId;
	//一级类目ID
	private String thirdCategoryName;
	//该此段存储类目集合用，分割如：一级类目编码，二级类目编码，三级类目编码
	private String categoryIdList;
	//该此段存储类目集合用，分割如：一级类目名称，二级类目名称，三级类目名称
	private String categoryNameList;
	//品牌ID
	private Long brandId;
	//品牌名称
	private String brandName;
	//支付方式：1：余额帐支付，2：平台账户支付，3：在线支付
	private String payType;
	//商品数量
	private Integer goodsCount;
	//订单行总价
	private BigDecimal orderItemTotalAmount;
	//订单行实付金额
	private BigDecimal orderItemPayAmount;
	//外部供货价
	private BigDecimal costPrice;
	//价格浮动比例
	private BigDecimal priceFloatingRatio;
	//VIP价浮动比例
	private BigDecimal vipPriceFloatingRatio;
	//佣金比例
	private BigDecimal commissionRate;
	//佣金金额
	private BigDecimal commissionAmount;
	//结算金额
	private BigDecimal settlementAmount;
	
	
	private Long createId;
	private String createName;
	private Date createTime;
	private Long modifyId;
	private String modifyName;
	private Date modifyTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSettlementNo() {
		return settlementNo;
	}
	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderItemNo() {
		return orderItemNo;
	}
	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerCode() {
		return buyerCode;
	}
	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}
	public String getBuyerType() {
		return buyerType;
	}
	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getOuterChannelCategoryCode() {
		return outerChannelCategoryCode;
	}
	public void setOuterChannelCategoryCode(String outerChannelCategoryCode) {
		this.outerChannelCategoryCode = outerChannelCategoryCode;
	}
	public String getOuterChannelCategoryName() {
		return outerChannelCategoryName;
	}
	public void setOuterChannelCategoryName(String outerChannelCategoryName) {
		this.outerChannelCategoryName = outerChannelCategoryName;
	}
	public String getOuterChannelBrandCode() {
		return outerChannelBrandCode;
	}
	public void setOuterChannelBrandCode(String outerChannelBrandCode) {
		this.outerChannelBrandCode = outerChannelBrandCode;
	}
	public String getOuterChannelBrandName() {
		return outerChannelBrandName;
	}
	public void setOuterChannelBrandName(String outerChannelBrandName) {
		this.outerChannelBrandName = outerChannelBrandName;
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
	public Integer getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
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
	public BigDecimal getVipPriceFloatingRatio() {
		return vipPriceFloatingRatio;
	}
	public void setVipPriceFloatingRatio(BigDecimal vipPriceFloatingRatio) {
		this.vipPriceFloatingRatio = vipPriceFloatingRatio;
	}
	public BigDecimal getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}
	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
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
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	
	
	
	

}
