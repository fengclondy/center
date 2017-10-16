package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GroupbuyingPriceSettingReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6719971616241996091L;

	// 团购价格设置ID
	private Long priceSettingId;
	// 促销活动编码
	private String promotionId;
	// 商品ITEMID
	private Long itemId;
	// 商品SKU编码
	private String skuCode;
	// 参团人数
	private Integer actorCount;
	// 拼团价
	private BigDecimal groupbuyingPrice;
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

	public Long getPriceSettingId() {
		return priceSettingId;
	}

	public void setPriceSettingId(Long priceSettingId) {
		this.priceSettingId = priceSettingId;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
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

	public Integer getActorCount() {
		return actorCount;
	}

	public void setActorCount(Integer actorCount) {
		this.actorCount = actorCount;
	}

	public BigDecimal getGroupbuyingPrice() {
		return groupbuyingPrice;
	}

	public void setGroupbuyingPrice(BigDecimal groupbuyingPrice) {
		this.groupbuyingPrice = groupbuyingPrice;
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

}