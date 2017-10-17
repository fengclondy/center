package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 秒杀活动结果表
 */
public class TimelimitedListDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8502390312805659790L;
	/**
	 * 促销活动编码
	 */
	@NotBlank(message = "促销活动编码不能为空")
	private String promotionId;
	/**
	 * 促销活动名称
	 */
	@NotBlank(message = "促销活动名称不能为空")
	private String promotionName;
	/**
	 * 活动类型
	 */
	@NotBlank(message = "活动类型")
	private String promotionType;
	/**
	 * 活动开始时间
	 */
	@NotBlank(message = "活动开始时间")
	private Date effectiveTime;
	/**
	 * 活动结束时间
	 */
	@NotBlank(message = "活动结束时间")
	private Date invalidTime;
	/**
	 * 是否vip
	 */
	private int isVip;
	/**
	 * 活动状态
	 */
	@NotBlank(message = "活动状态")
	private String status;
	/**
	 * 审核状态
	 */
	@NotBlank(message = "审核状态")
	private String showStatus;
	/**
	 * 层级序号
	 */
	private int levelNumber;
	/**
	 * 层级编码
	 */
	@NotBlank(message = "审核状态")
	private String levelCode;

	/**
	 * 秒杀ID
	 */
	private Long timelimitedId;
	/**
	 * 卖家编码
	 */
	@NotBlank(message = "卖家编码不能为空")
	private String sellerCode;
	/**
	 * 商品ITEMID
	 */
	@NotNull(message = "商品ITEMID不能为空")
	private Long itemId;
	/**
	 * 商品SKU编码
	 */
	@NotBlank(message = "商品SKU编码不能为空")
	private String skuCode;
	/**
	 * 商品SKU名称
	 */
	@NotBlank(message = "商品SKU名称不能为空")
	private String skuName;
	/**
	 * 商品主图URL
	 */
	@NotBlank(message = "商品主图URL不能为空")
	private String skuPicUrl;
	/**
	 * 商品秒杀价
	 */
	@NotNull(message = "商品秒杀价不能为空")
	@Min(value = 0, message = "商品秒杀价必须大于0")
	private BigDecimal skuTimelimitedPrice;
	/**
	 * 参与秒杀商品数量
	 */
	@NotNull(message = "参与秒杀商品数量不能为空")
	@Min(value = 1, message = "参与秒杀商品数量必须大于0")
	private Integer timelimitedSkuCount;
	/**
	 * 每人限秒数量
	 */
	@NotNull(message = "每人限秒数量不能为空")
	@Min(value = 1, message = "每人限秒数量必须大于0")
	private Integer timelimitedThreshold;
	/**
	 * 秒杀订单有效时间（单位：分钟）
	 */
	@NotNull(message = "秒杀订单有效时间不能为空")
	@Min(value = 1, message = "秒杀订单有效时间必须大于0")
	private Integer timelimitedValidInterval;
	/**
	 * 秒杀商品的品牌品类
	 */
	// @NotBlank(message = "商品品牌品类不能为空")
	private String itemCategoryBrand;
	/**
	 * 商品item编码
	 */
	private String itemCode;
	/**
	 * 每人起购数量
	 */
	private Integer timelimitedThresholdMin;
	/**
	 * 限时购开始时间
	 */
	private Date startTime;
	/**
	 * 限时购结束时间
	 */
	private Date endTime;

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public Long getTimelimitedId() {
		return timelimitedId;
	}

	public void setTimelimitedId(Long timelimitedId) {
		this.timelimitedId = timelimitedId;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuPicUrl() {
		return skuPicUrl;
	}

	public void setSkuPicUrl(String skuPicUrl) {
		this.skuPicUrl = skuPicUrl;
	}

	public BigDecimal getSkuTimelimitedPrice() {
		return skuTimelimitedPrice;
	}

	public void setSkuTimelimitedPrice(BigDecimal skuTimelimitedPrice) {
		this.skuTimelimitedPrice = skuTimelimitedPrice;
	}

	public Integer getTimelimitedSkuCount() {
		return timelimitedSkuCount;
	}

	public void setTimelimitedSkuCount(Integer timelimitedSkuCount) {
		this.timelimitedSkuCount = timelimitedSkuCount;
	}

	public Integer getTimelimitedThreshold() {
		return timelimitedThreshold;
	}

	public void setTimelimitedThreshold(Integer timelimitedThreshold) {
		this.timelimitedThreshold = timelimitedThreshold;
	}

	public Integer getTimelimitedValidInterval() {
		return timelimitedValidInterval;
	}

	public void setTimelimitedValidInterval(Integer timelimitedValidInterval) {
		this.timelimitedValidInterval = timelimitedValidInterval;
	}

	public String getItemCategoryBrand() {
		return itemCategoryBrand;
	}

	public void setItemCategoryBrand(String itemCategoryBrand) {
		this.itemCategoryBrand = itemCategoryBrand;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getTimelimitedThresholdMin() {
		return timelimitedThresholdMin;
	}

	public void setTimelimitedThresholdMin(Integer timelimitedThresholdMin) {
		this.timelimitedThresholdMin = timelimitedThresholdMin;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}