package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 秒杀活动DTO
 */
public class TimelimitedInfoDTO extends PromotionAccumulatyDTO implements Serializable {

	private static final long serialVersionUID = 5210372509736403342L;

	/**
	 * ID
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
	 * 秒杀活动结果
	 */
	private TimelimitedResultDTO timelimitedResult;
	/**
	 *秒杀商品的品牌品类
	 */
	//@NotBlank(message = "商品品牌品类不能为空")
	private String itemCategoryBrand;
	/**
	 * 商品item编码
	 */
	private String itemCode;
	private HashMap<String,String> itemStockInfo;
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
	/**
	 * 限时购聚合页标识  1:今日特惠 2:开售预告
	 */
	private int purchaseFlag;
	/**
	 * 限时购排序标识  1:销售量  2:上架时间  3:价格倒序  4:价格升序
	 */
	private int purchaseSort;
	/**
	 * 优惠力度
	 */
	private double preferentialStrength;
	/**
	 * 销量
	 */
	private int salesVolume;
	/**
	 * 销售额（销量*销售价）
	 */
	private double salesVolumePrice;
	/**
	 * 限时购起价标示
	 */
	private int purchasePriceFlag;
	/**
	 * 商家名称
	 */
	private String sellerName;
	
	public int getPurchasePriceFlag() {
		return purchasePriceFlag;
	}

	public void setPurchasePriceFlag(int purchasePriceFlag) {
		this.purchasePriceFlag = purchasePriceFlag;
	}

	public double getPreferentialStrength() {
		return preferentialStrength;
	}

	public void setPreferentialStrength(double preferentialStrength) {
		this.preferentialStrength = preferentialStrength;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public double getSalesVolumePrice() {
		return salesVolumePrice;
	}

	public void setSalesVolumePrice(double salesVolumePrice) {
		this.salesVolumePrice = salesVolumePrice;
	}

	public int getPurchaseSort() {
		return purchaseSort;
	}

	public void setPurchaseSort(int purchaseSort) {
		this.purchaseSort = purchaseSort;
	}

	public int getPurchaseFlag() {
		return purchaseFlag;
	}

	public void setPurchaseFlag(int purchaseFlag) {
		this.purchaseFlag = purchaseFlag;
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

	public TimelimitedResultDTO getTimelimitedResult() {
		return timelimitedResult;
	}

	public void setTimelimitedResult(TimelimitedResultDTO timelimitedResult) {
		this.timelimitedResult = timelimitedResult;
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

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public void setTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo) {
		super.setPromotionAccumulaty(timelimitedInfo);
		this.timelimitedId = timelimitedInfo.getTimelimitedId();
		this.sellerCode = timelimitedInfo.getSellerCode();
		this.itemId = timelimitedInfo.getItemId();
		this.skuCode = timelimitedInfo.getSkuCode();
		this.skuName = timelimitedInfo.getSkuName();
		this.skuPicUrl = timelimitedInfo.getSkuPicUrl();
		this.skuTimelimitedPrice = timelimitedInfo.getSkuTimelimitedPrice();
		this.timelimitedSkuCount = timelimitedInfo.getTimelimitedSkuCount();
		this.timelimitedThreshold = timelimitedInfo.getTimelimitedThreshold();
		this.timelimitedValidInterval = timelimitedInfo.getTimelimitedValidInterval();
		this.timelimitedResult = timelimitedInfo.getTimelimitedResult();
		this.timelimitedThresholdMin = timelimitedInfo.getTimelimitedThresholdMin();
		this.startTime = timelimitedInfo.getStartTime();
		this.endTime = timelimitedInfo.getEndTime();
		this.purchaseSort = timelimitedInfo.getPurchaseSort();
		this.preferentialStrength = timelimitedInfo.getPreferentialStrength();
		this.salesVolume = timelimitedInfo.getSalesVolume();
		this.salesVolumePrice = timelimitedInfo.getSalesVolumePrice();
		this.purchasePriceFlag = timelimitedInfo.getPurchasePriceFlag();
		this.sellerName = timelimitedInfo.getSellerName();
	}

	public HashMap<String, String> getItemStockInfo() {
		return itemStockInfo;
	}

	public void setItemStockInfo(HashMap<String, String> itemStockInfo) {
		this.itemStockInfo = itemStockInfo;
	}
}
