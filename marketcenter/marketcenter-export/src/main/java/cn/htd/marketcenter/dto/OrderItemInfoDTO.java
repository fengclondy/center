package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class OrderItemInfoDTO implements Serializable {

	private static final long serialVersionUID = -1628699859231359421L;
	/**
	 * 订单行号
	 */
	private String orderItemNo;
	/**
	 * 卖家编码
	 */
	private String sellerCode;
	/**
	 * 一级类目id
	 */
	private Long firstCategoryId;
	/**
	 * 一级类目名称
	 */
	private String firstCategoryName;
	/**
	 * 二级类目Id
	 */
	private Long secondCategoryId;
	/**
	 * 二级类目名称
	 */
	private String secondCategoryName;
	/**
	 * 三级类目ID
	 */
	@NotNull(message = "商品类目编码不能为空")
	private Long thirdCategoryId;
	/**
	 * 三级类目名称
	 */
	private String thirdCategoryName;
	/**
	 * 商品品牌编码
	 */
	@NotNull(message = "商品品牌编码不能为空")
	private Long brandId;
	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 商品渠道编码
	 */
	@NotBlank(message = "商品渠道编码不能为空")
	private String channelCode;
	/**
	 * 商品ITEM编码
	 */
	private String itemCode;
	/**
	 * 商品SKU编码
	 */
	@NotBlank(message = "商品编码不能为空")
	private String skuCode;
	/**
	 * 商品SPU编码
	 */
	private String itemSpuCode;
	/**
	 * 外接商品外部渠道SKU编码
	 */
	private String outerSkuCode;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品主图
	 */
	private String skuPictureUrl;
	/**
	 * 外部供货价／分销限价
	 */
	private BigDecimal costPrice;
	/**
	 * 销售单价
	 */
	private BigDecimal salePrice;
	/**
	 * 价格浮动比例
	 */
	private BigDecimal priceFloatingRatio;
	/**
	 * 佣金比例
	 */
	private BigDecimal commissionRatio;
	/**
	 * 商品单价种类
	 */
	private String goodsPriceType;
	/**
	 * 商品单价（参加秒杀活动时，此价格为秒杀单价）
	 */
	@NotNull(message = "商品单价不能为空")
	@Min(value = 0, message = "商品单价不能小于0")
	private BigDecimal goodsPrice;
	/**
	 * 购买数量
	 */
	@NotNull(message = "购买数量不能为空")
	@Min(value = 1, message = "购买数量不能小于1")
	private Integer goodsCount;
	/**
	 * 是否包厢标志 0：否，1：是
	 */
	private int isBoxFlag;
	/**
	 * 是否有经营关系标志 0：否，1：是
	 */
	private int isHasDevRelation;
	/**
	 * 是否超出配送范围
	 */
	private int isOutDistribtion;
	/**
	 * 是否VIP套餐商品
	 */
	private int isVipItem;
	/**
	 * VIP商品类型
	 */
	private String vipItemType;
	/**
	 * 同步VIP会员标记
	 */
	private int vipSyncFlag;
	/**
	 * 运费模版ID
	 */
	private Long shopFreightTemplateId;
	/**
	 * 运费金额
	 */
	private BigDecimal goodsFreight;
	/**
	 * 商品总金额=商品单价*购买数量
	 */
	private BigDecimal goodsTotal = BigDecimal.ZERO;
	/**
	 * 是否适用了优惠券标记
	 */
	private boolean hasUsedCoupon = false;
	/**
	 * 参加店铺优惠活动优惠金额
	 */
	private BigDecimal shopDiscountAmount = BigDecimal.ZERO;
	/**
	 * 参加平台优惠活动优惠金额
	 */
	private BigDecimal platformDiscountAmount = BigDecimal.ZERO;
	/**
	 * 使用店铺优惠券优惠金额
	 */
	private BigDecimal shopCouponDiscount = BigDecimal.ZERO;
	/**
	 * 使用平台优惠券优惠金额
	 */
	private BigDecimal platformCouponDiscount = BigDecimal.ZERO;
	/**
	 * 优惠总金额=参加店铺优惠活动优惠金额+参加平台优惠活动优惠金额+使用店铺优惠券优惠金额+使用平台优惠券优惠金额
	 */
	private BigDecimal totalDiscountAmount = BigDecimal.ZERO;
	/**
	 * 订单总金额=商品总金额-优惠总金额
	 */
	private BigDecimal orderItemTotal = BigDecimal.ZERO;
	/**
	 * 该商品能够适用的优惠券列表
	 */
	private List<OrderItemCouponDTO> avalibleCouponList = new ArrayList<OrderItemCouponDTO>();
	/**
	 * 该商品是否参加秒杀活动
	 */
	private boolean hasTimelimitedFlag = false;
	/**
	 * 参加秒杀活动时秒杀活动信息
	 */
	private TimelimitedInfoDTO timelimitedInfo;
	/**
	 * ERP一级类目编码
	 */
	private String erpFirstCategoryCode;
	/**
	 * 订单扩展字段列表
	 */
	private Map<String, Object> extendMap;

	public Map<String, Object> getExtendMap() {
		return extendMap;
	}

	public void setExtendMap(Map<String, Object> extendMap) {
		this.extendMap = extendMap;
	}

	public String getErpFirstCategoryCode() {
		return erpFirstCategoryCode;
	}

	public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
		this.erpFirstCategoryCode = erpFirstCategoryCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
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

	public String getGoodsPriceType() {
		return goodsPriceType;
	}

	public void setGoodsPriceType(String goodsPriceType) {
		this.goodsPriceType = goodsPriceType;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public int getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(int isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

	public int getIsHasDevRelation() {
		return isHasDevRelation;
	}

	public void setIsHasDevRelation(int isHasDevRelation) {
		this.isHasDevRelation = isHasDevRelation;
	}

	public int getIsOutDistribtion() {
		return isOutDistribtion;
	}

	public void setIsOutDistribtion(int isOutDistribtion) {
		this.isOutDistribtion = isOutDistribtion;
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

	public int getVipSyncFlag() {
		return vipSyncFlag;
	}

	public void setVipSyncFlag(int vipSyncFlag) {
		this.vipSyncFlag = vipSyncFlag;
	}

	public Long getShopFreightTemplateId() {
		return shopFreightTemplateId;
	}

	public void setShopFreightTemplateId(Long shopFreightTemplateId) {
		this.shopFreightTemplateId = shopFreightTemplateId;
	}

	public BigDecimal getGoodsFreight() {
		return goodsFreight;
	}

	public void setGoodsFreight(BigDecimal goodsFreight) {
		this.goodsFreight = goodsFreight;
	}

	public BigDecimal getGoodsTotal() {
		return goodsTotal;
	}

	public boolean isHasUsedCoupon() {
		return hasUsedCoupon;
	}

	public void setHasUsedCoupon(boolean hasUsedCoupon) {
		this.hasUsedCoupon = hasUsedCoupon;
	}

	public BigDecimal getOrderItemTotal() {
		return orderItemTotal;
	}

	public BigDecimal getShopDiscountAmount() {
		return shopDiscountAmount;
	}

	public void setShopDiscountAmount(BigDecimal shopDiscountAmount) {
		this.shopDiscountAmount = shopDiscountAmount;
		BigDecimal discountAmount = this.shopDiscountAmount.add(this.platformDiscountAmount);
		BigDecimal couponAmount = this.shopCouponDiscount.add(this.platformCouponDiscount);
		this.totalDiscountAmount = discountAmount.add(couponAmount);
		this.orderItemTotal = this.goodsTotal.subtract(this.totalDiscountAmount).setScale(2, RoundingMode.HALF_UP);
		if (BigDecimal.ZERO.compareTo(this.orderItemTotal) > 0) {
			this.orderItemTotal = BigDecimal.ZERO;
		}
	}

	public BigDecimal getPlatformDiscountAmount() {
		return platformDiscountAmount;
	}

	public void setPlatformDiscountAmount(BigDecimal platformDiscountAmount) {
		this.platformDiscountAmount = platformDiscountAmount;
		BigDecimal discountAmount = this.shopDiscountAmount.add(this.platformDiscountAmount);
		BigDecimal couponAmount = this.shopCouponDiscount.add(this.platformCouponDiscount);
		this.totalDiscountAmount = discountAmount.add(couponAmount);
		this.orderItemTotal = this.goodsTotal.subtract(this.totalDiscountAmount).setScale(2, RoundingMode.HALF_UP);
		if (BigDecimal.ZERO.compareTo(this.orderItemTotal) > 0) {
			this.orderItemTotal = BigDecimal.ZERO;
		}
	}

	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public BigDecimal getShopCouponDiscount() {
		return shopCouponDiscount;
	}

	public void setShopCouponDiscount(BigDecimal shopCouponDiscount) {
		this.shopCouponDiscount = shopCouponDiscount;
		BigDecimal discountAmount = this.shopDiscountAmount.add(this.platformDiscountAmount);
		BigDecimal couponAmount = this.shopCouponDiscount.add(this.platformCouponDiscount);
		this.totalDiscountAmount = discountAmount.add(couponAmount);
		this.orderItemTotal = this.goodsTotal.subtract(this.totalDiscountAmount).setScale(2, RoundingMode.HALF_UP);
		if (BigDecimal.ZERO.compareTo(this.orderItemTotal) > 0) {
			this.orderItemTotal = BigDecimal.ZERO;
		}
	}

	public BigDecimal getPlatformCouponDiscount() {
		return platformCouponDiscount;
	}

	public void setPlatformCouponDiscount(BigDecimal platformCouponDiscount) {
		this.platformCouponDiscount = platformCouponDiscount;
		BigDecimal discountAmount = this.shopDiscountAmount.add(this.platformDiscountAmount);
		BigDecimal couponAmount = this.shopCouponDiscount.add(this.platformCouponDiscount);
		this.totalDiscountAmount = discountAmount.add(couponAmount);
		this.orderItemTotal = this.goodsTotal.subtract(this.totalDiscountAmount).setScale(2, RoundingMode.HALF_UP);
		if (BigDecimal.ZERO.compareTo(this.orderItemTotal) > 0) {
			this.orderItemTotal = BigDecimal.ZERO;
		}
	}

	public List<OrderItemCouponDTO> getAvalibleCouponList() {
		return avalibleCouponList;
	}

	public void setAvalibleCouponList(List<OrderItemCouponDTO> avalibleCouponList) {
		this.avalibleCouponList = avalibleCouponList;
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

	public void initBeforeCalculateCoupon(String sellerCode) {
		this.sellerCode = sellerCode;
		this.hasUsedCoupon = false;
		if (this.goodsPrice == null) {
			this.goodsPrice = BigDecimal.ZERO;
		}
		if (this.goodsCount == null) {
			this.goodsCount = 0;
		}
		this.goodsTotal = this.goodsPrice.multiply(new BigDecimal(this.goodsCount));
		this.shopDiscountAmount = BigDecimal.ZERO;
		this.platformDiscountAmount = BigDecimal.ZERO;
		this.shopCouponDiscount = BigDecimal.ZERO;
		this.platformCouponDiscount = BigDecimal.ZERO;
		this.totalDiscountAmount = BigDecimal.ZERO;
		this.orderItemTotal = this.goodsTotal;
		this.avalibleCouponList = new ArrayList<OrderItemCouponDTO>();
		this.hasTimelimitedFlag = false;
		this.timelimitedInfo = null;
	}
}
