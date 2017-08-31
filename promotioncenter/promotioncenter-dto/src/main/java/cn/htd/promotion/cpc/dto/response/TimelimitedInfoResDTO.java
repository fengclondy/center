package cn.htd.promotion.cpc.dto.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 秒杀活动信息DTO
 * @author zf.zhang
 * @since  2017-8-22 15:14
 *
 */
public class TimelimitedInfoResDTO extends PromotionAccumulatyDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4284500647853447096L;
	
	
	// 秒杀活动ID
	private Long timelimitedId;
	// 促销活动编码
	private String promotionId;
	// 层级编码
	private String levelCode;
	// 商家编码
	private String sellerCode;
	// 商品ITEMID
	private Long itemId;
	// 商品SKU编码
	private String skuCode;
	// 商品SKU名称 商品名称+商品SKU副标题
	private String skuName;
	// 商品一级类目
	private String firstCategoryCode;
	// 商品二级类目
	private String secondCategoryCode;
	// 商品三级类目
	private String thirdCategoryCode;
	// 类目一名称，类目二名称，类目三名称
	private String skuCategoryName;
	// 商品主图URL
	private String skuPicUrl;
	// 商品原价
	private BigDecimal skuCostPrice;
	// 商品秒杀价
	private BigDecimal skuTimelimitedPrice;
	// 参与秒杀商品数量
	private Integer timelimitedSkuCount;
	// 每人限秒数量
	private Integer timelimitedThreshold;
	// 单位：分钟
	private Integer timelimitedValidInterval;

	// 创建人ID
	private Long createId;
	// 创建人名称
	private String createName;
	// 创建时间
	private Date createTime;
	// 更新人ID
	private Long modifyId;
	// 更新人名称
	private String modifyName;
	// 更新时间（促销活动更新时必须传入做乐观排他用）
	private Date modifyTime;
	
	//商品图片
	private List<TimelimitedSkuDescribeResDTO> timelimitedSkuDescribeList;
	//商品详情
	private TimelimitedSkuDescribeResDTO timelimitedSkuDescribe;
	
	/**
	 * 秒杀活动结果
	 */
	private TimelimitedResultDTO timelimitedResult;
	
	public Long getTimelimitedId() {
		return timelimitedId;
	}
	public void setTimelimitedId(Long timelimitedId) {
		this.timelimitedId = timelimitedId;
	}
	public String getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
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
	public String getFirstCategoryCode() {
		return firstCategoryCode;
	}
	public void setFirstCategoryCode(String firstCategoryCode) {
		this.firstCategoryCode = firstCategoryCode;
	}
	public String getSecondCategoryCode() {
		return secondCategoryCode;
	}
	public void setSecondCategoryCode(String secondCategoryCode) {
		this.secondCategoryCode = secondCategoryCode;
	}
	public String getThirdCategoryCode() {
		return thirdCategoryCode;
	}
	public void setThirdCategoryCode(String thirdCategoryCode) {
		this.thirdCategoryCode = thirdCategoryCode;
	}
	public String getSkuCategoryName() {
		return skuCategoryName;
	}
	public void setSkuCategoryName(String skuCategoryName) {
		this.skuCategoryName = skuCategoryName;
	}
	public String getSkuPicUrl() {
		return skuPicUrl;
	}
	public void setSkuPicUrl(String skuPicUrl) {
		this.skuPicUrl = skuPicUrl;
	}
	public BigDecimal getSkuCostPrice() {
		return skuCostPrice;
	}
	public void setSkuCostPrice(BigDecimal skuCostPrice) {
		this.skuCostPrice = skuCostPrice;
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
	

	public TimelimitedResultDTO getTimelimitedResult() {
		return timelimitedResult;
	}
	public void setTimelimitedResult(TimelimitedResultDTO timelimitedResult) {
		this.timelimitedResult = timelimitedResult;
	}
	
	public void setTimelimitedInfo(TimelimitedInfoResDTO timelimitedInfo) {
		super.setPromotionAccumulaty(timelimitedInfo);
		this.timelimitedId = timelimitedInfo.getTimelimitedId();
		this.sellerCode = timelimitedInfo.getSellerCode();
		this.itemId = timelimitedInfo.getItemId();
		this.skuCode = timelimitedInfo.getSkuCode();
		this.skuName = timelimitedInfo.getSkuName();
		this.skuPicUrl = timelimitedInfo.getSkuPicUrl();
		this.skuCostPrice = timelimitedInfo.getSkuCostPrice();
		this.skuTimelimitedPrice = timelimitedInfo.getSkuTimelimitedPrice();
		this.timelimitedSkuCount = timelimitedInfo.getTimelimitedSkuCount();
		this.timelimitedThreshold = timelimitedInfo.getTimelimitedThreshold();
		this.timelimitedValidInterval = timelimitedInfo.getTimelimitedValidInterval();
		this.timelimitedResult = timelimitedInfo.getTimelimitedResult(); 
	}
	public List<TimelimitedSkuDescribeResDTO> getTimelimitedSkuDescribeList() {
		return timelimitedSkuDescribeList;
	}
	public void setTimelimitedSkuDescribeList(
			List<TimelimitedSkuDescribeResDTO> timelimitedSkuDescribeList) {
		this.timelimitedSkuDescribeList = timelimitedSkuDescribeList;
	}
	public TimelimitedSkuDescribeResDTO getTimelimitedSkuDescribe() {
		return timelimitedSkuDescribe;
	}
	public void setTimelimitedSkuDescribe(
			TimelimitedSkuDescribeResDTO timelimitedSkuDescribe) {
		this.timelimitedSkuDescribe = timelimitedSkuDescribe;
	}
	
	

}
