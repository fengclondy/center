package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderCreateItemListInfoMarketResDTO extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1198333214257751746L;

	//订单行号
	private String orderItemNo;
	
	//商品SKU编码
	private String skuCode;
	
	//商品数量
	private Long goodsCount;
	
	//渠道编码
	private String channelCode;
	
	//是否包厢标志  0：否，1：是
	private Integer isBoxFlag;
	
	//是否有经营关系  1 有 0 没有
    private Integer isHasDevRelation;
	
	//(订单行)优惠总金额
	private BigDecimal totalDiscountAmount;
	
	// 品牌id
	private Long brandId; 
	
	//  品牌名称
	private String brandName;
	
	private Long firstCategoryId; // 一级类目id

	private String firstCategoryName; // 一级类目名称

	private Long secondCategoryId; // 二级类目Id

	private String secondCategoryName; // 二级类目名称

	private Long thirdCategoryId;// 三级类目ID

	private String thirdCategoryName; // 三级类目名称
	
	private String itemCode;//商品id
	
	private String goodsName;//商品名称
	
	private String skuPictureUrl;// item主图 列表页展示
	
	private Integer isOutDistribtion;//是否超出配送范围 
		
	private Long shopFreightTemplateId;//运费模版ID
	
	private String levelCode;//层级编码
	
	private BigDecimal goodsFreight;//运费金额
	
	private Long stockId;//库存ID
	
	private Long skuId;
	

	//TODO 2017-02-09 张丁开始
	
	//外部供货价/分销限价
	private BigDecimal costPrice;
	
	//价格浮动比例
	private BigDecimal priceFloatingRatio;
	
	//佣金比例
	private BigDecimal commissionRatio;
	
	//销售单价
	private BigDecimal salePrice;
	
	//商品单价种类 0 销售价 1 阶梯价 2 分组价 3 等级价 4 区域价 
	private Integer goodsPriceType;
	
	//商品单价 - 如果是秒杀商品-就传给订单中心秒杀价
	private BigDecimal goodsPrice;
	
	private Integer isVipItem;//是否VIP套餐商品:0 非vip套餐商品 1 vip套餐商品

	private Integer vipItemType;//VIP商品类型:当is_vip_item=1时，该字段有效 1 VIP套餐 2 智慧门店套餐

	private Integer vipSyncFlag;//同步VIP会员标记:当is_vip_item=1时，该字段为1时有效 0 无效 1 有效
	
	//TODO 2017-02-09 张丁结束

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getGoodsCount() {
		return goodsCount;
	}
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(Integer isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
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

	public Long getFirstCategoryId() {
		return firstCategoryId;
	}

	public void setFirstCategoryId(Long firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}

	public Long getSecondCategoryId() {
		return secondCategoryId;
	}

	public void setSecondCategoryId(Long secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}

	public Long getThirdCategoryId() {
		return thirdCategoryId;
	}

	public void setThirdCategoryId(Long thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}

	public String getFirstCategoryName() {
		return firstCategoryName;
	}

	public void setFirstCategoryName(String firstCategoryName) {
		this.firstCategoryName = firstCategoryName;
	}

	public String getSecondCategoryName() {
		return secondCategoryName;
	}

	public void setSecondCategoryName(String secondCategoryName) {
		this.secondCategoryName = secondCategoryName;
	}

	public String getThirdCategoryName() {
		return thirdCategoryName;
	}

	public void setThirdCategoryName(String thirdCategoryName) {
		this.thirdCategoryName = thirdCategoryName;
	}

	
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSkuPictureUrl() {
		return skuPictureUrl;
	}

	public void setSkuPictureUrl(String skuPictureUrl) {
		this.skuPictureUrl = skuPictureUrl;
	}

	public Integer getIsOutDistribtion() {
		return isOutDistribtion;
	}

	public void setIsOutDistribtion(Integer isOutDistribtion) {
		this.isOutDistribtion = isOutDistribtion;
	}

	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Long getShopFreightTemplateId() {
		return shopFreightTemplateId;
	}

	public void setShopFreightTemplateId(Long shopFreightTemplateId) {
		this.shopFreightTemplateId = shopFreightTemplateId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public Integer getIsHasDevRelation() {
		return isHasDevRelation;
	}

	public void setIsHasDevRelation(Integer isHasDevRelation) {
		this.isHasDevRelation = isHasDevRelation;
	}

	public BigDecimal getGoodsFreight() {
		return goodsFreight;
	}

	public void setGoodsFreight(BigDecimal goodsFreight) {
		this.goodsFreight = goodsFreight;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
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

	public Integer getGoodsPriceType() {
		return goodsPriceType;
	}

	public void setGoodsPriceType(Integer goodsPriceType) {
		this.goodsPriceType = goodsPriceType;
	}

	public Integer getIsVipItem() {
		return isVipItem;
	}

	public void setIsVipItem(Integer isVipItem) {
		this.isVipItem = isVipItem;
	}

	public Integer getVipItemType() {
		return vipItemType;
	}

	public void setVipItemType(Integer vipItemType) {
		this.vipItemType = vipItemType;
	}

	public Integer getVipSyncFlag() {
		return vipSyncFlag;
	}

	public void setVipSyncFlag(Integer vipSyncFlag) {
		this.vipSyncFlag = vipSyncFlag;
	}
	
}
