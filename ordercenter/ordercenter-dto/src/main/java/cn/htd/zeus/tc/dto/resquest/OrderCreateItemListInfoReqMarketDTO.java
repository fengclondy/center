package cn.htd.zeus.tc.dto.resquest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.htd.zeus.tc.dto.OrderItemCouponDTO;
import cn.htd.zeus.tc.dto.TimelimitedInfoDTO;


public class OrderCreateItemListInfoReqMarketDTO extends OrderCreateItemListInfoReqFreightDTO{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3000566361402537610L;

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
	
	private String erpFirstCategoryCode;// ERP一级类目编码
	
	private String itemCode;//商品code
	
	private String goodsName;//商品名称
	
	private String skuPictureUrl;// item主图 列表页展示
	
	private Integer isOutDistribtion;//是否超出配送范围 
	
	private Long skuId;
	
	private Long shopFreightTemplateId;//运费模版ID
	
	private String levelCode;//层级编码
	/**
	 * 该商品能够适用的优惠券列表(从蒋坤那映射的对象-方便取出levelCode)
	 */
	private List<OrderItemCouponDTO> avalibleCouponList = new ArrayList<OrderItemCouponDTO>();
	
	private BigDecimal goodsFreight;//运费金额
	
	private Long stockId;//库存ID
	
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
	private String goodsPriceType;
	
	//商品单价
	private BigDecimal goodsPrice;
	
	private Integer isVipItem;//是否VIP套餐商品:0 非vip套餐商品 1 vip套餐商品

	private Integer vipItemType;//VIP商品类型:当is_vip_item=1时，该字段有效 1 VIP套餐 2 智慧门店套餐

	private Integer vipSyncFlag;//同步VIP会员标记:当is_vip_item=1时，该字段为1时有效 0 无效 1 有效
	
	private String itemSpuCode;//商品模板编码
	
	private String outerSkuCode;//京东编码
	
	/*
	 *秒杀接收专用 开始 by 张丁
	 */
	
	//该商品是否参加秒杀活动 - true 是秒杀活动
	private boolean hasTimelimitedFlag;
	
	//参加秒杀活动时秒杀活动信息
	private TimelimitedInfoDTO timelimitedInfo;
	
	/*
	 *秒杀接收专用 结束by 张丁
	 */
	
	private Map<String,Object> extendMap;
	
	private List<OrderItemCouponDTO> avaliableCouponList;
	

	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
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

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
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

	public List<OrderItemCouponDTO> getAvaliableCouponList() {
		return avaliableCouponList;
	}

	public void setAvaliableCouponList(List<OrderItemCouponDTO> avaliableCouponList) {
		this.avaliableCouponList = avaliableCouponList;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
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

	public String getErpFirstCategoryCode() {
		return erpFirstCategoryCode;
	}

	public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
		this.erpFirstCategoryCode = erpFirstCategoryCode;
	}

	public List<OrderItemCouponDTO> getAvalibleCouponList() {
		return avalibleCouponList;
	}

	public void setAvalibleCouponList(List<OrderItemCouponDTO> avalibleCouponList) {
		this.avalibleCouponList = avalibleCouponList;
	}

	public String getItemSpuCode() {
		return itemSpuCode;
	}

	public void setItemSpuCode(String itemSpuCode) {
		this.itemSpuCode = itemSpuCode;
	}

	public String getOuterSkuCode() {
		return outerSkuCode;
	}

	public void setOuterSkuCode(String outerSkuCode) {
		this.outerSkuCode = outerSkuCode;
	}

	public Map<String, Object> getExtendMap() {
		return extendMap;
	}

	public void setExtendMap(Map<String, Object> extendMap) {
		this.extendMap = extendMap;
	}

	public boolean isHasTimelimitedFlag() {
		return hasTimelimitedFlag;
	}

	public void setHasTimelimitedFlag(boolean hasTimelimitedFlag) {
		this.hasTimelimitedFlag = hasTimelimitedFlag;
	}

	public TimelimitedInfoDTO getTimelimitedInfo() {
		return timelimitedInfo;
	}

	public void setTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo) {
		this.timelimitedInfo = timelimitedInfo;
	}
}
