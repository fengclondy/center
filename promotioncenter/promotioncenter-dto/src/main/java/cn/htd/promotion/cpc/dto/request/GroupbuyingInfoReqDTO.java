package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GroupbuyingInfoReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -632080300760820893L;

	// 团购活动ID
	private Long groupbuyingId;
	// 促销活动编码
	private String promotionId;
	// 商家编码
	private String sellerCode;
	// 商家名称
	private String sellerName;
	// 商家别名
	private String sellerAliasName;
	// 商品ITEMID
	private Long itemId;
	// 商品SKU编码
	private String skuCode;
	// 商品SKU名称
	private String skuName;
	// 商品标签
	private String skuLabel;
	// 商品主图URL
	private String skuPicUrl;
	// 商品原价
	private BigDecimal skuCostPrice;
	// 真实参团人数
	private Integer realActorCount;
	// 真实拼团价
	private BigDecimal realGroupbuyingPrice;
	// 团购开始时间
	private Date startTime;
	// 团购结束时间
	private Date endTime;
	// 销售区域编码
	private String salesAreaCode;
	// 销售区域名称
	private String salesAreaName;
	// 参与团购商品数量
	private Integer groupbuyingSkuCount;
	// 每人限购数量
	private Integer groupbuyingThreshold;
	// 团购订单有效时间（单位：分钟）
	private Integer groupbuyingValidInterval;
	// 删除标记
	private Boolean deleteFlag;
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
	// 更新时间
	private Date modifyTime;
	
	
	// 状态 1：活动未开始，2：活动进行中，3：活动已结束，9：已删除
	private String status;
	// 审核状态 0：待审核，1：审核通过，2：审核被驳回，3：启用，4：不启用
	private String showStatus;
	
	// 活动状态 [1.未开始,2.开团进行中,3.下单未开始,4.下单进行中,5.已结束]
	private String activeState;
	// 活动状态文本
	private String activeStateText;
	
	// 参团人账号
	private String buyerCode;

	public Long getGroupbuyingId() {
		return groupbuyingId;
	}

	public void setGroupbuyingId(Long groupbuyingId) {
		this.groupbuyingId = groupbuyingId;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
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

	public String getSkuLabel() {
		return skuLabel;
	}

	public void setSkuLabel(String skuLabel) {
		this.skuLabel = skuLabel;
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

	public Integer getRealActorCount() {
		return realActorCount;
	}

	public void setRealActorCount(Integer realActorCount) {
		this.realActorCount = realActorCount;
	}

	public BigDecimal getRealGroupbuyingPrice() {
		return realGroupbuyingPrice;
	}

	public void setRealGroupbuyingPrice(BigDecimal realGroupbuyingPrice) {
		this.realGroupbuyingPrice = realGroupbuyingPrice;
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

	public String getSalesAreaCode() {
		return salesAreaCode;
	}

	public void setSalesAreaCode(String salesAreaCode) {
		this.salesAreaCode = salesAreaCode;
	}

	public String getSalesAreaName() {
		return salesAreaName;
	}

	public void setSalesAreaName(String salesAreaName) {
		this.salesAreaName = salesAreaName;
	}

	public Integer getGroupbuyingSkuCount() {
		return groupbuyingSkuCount;
	}

	public void setGroupbuyingSkuCount(Integer groupbuyingSkuCount) {
		this.groupbuyingSkuCount = groupbuyingSkuCount;
	}

	public Integer getGroupbuyingThreshold() {
		return groupbuyingThreshold;
	}

	public void setGroupbuyingThreshold(Integer groupbuyingThreshold) {
		this.groupbuyingThreshold = groupbuyingThreshold;
	}

	public Integer getGroupbuyingValidInterval() {
		return groupbuyingValidInterval;
	}

	public void setGroupbuyingValidInterval(Integer groupbuyingValidInterval) {
		this.groupbuyingValidInterval = groupbuyingValidInterval;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}

	public String getActiveStateText() {
		return activeStateText;
	}

	public void setActiveStateText(String activeStateText) {
		this.activeStateText = activeStateText;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerAliasName() {
		return sellerAliasName;
	}

	public void setSellerAliasName(String sellerAliasName) {
		this.sellerAliasName = sellerAliasName;
	}
	
	
	
	

}