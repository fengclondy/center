package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品收藏
 */
public class ItemFavourite implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long userId; // 用户ID

	private Long sellerId; // 卖家ID

	private Long shopId; //店铺ID

	private Long itemId; //商品ID

	private Long skuId; // SKU_ID

	private String channelCode; //渠道编码

	private Integer deleteFlag;//删除标记

  	private Long createId; //创建人ID

	private String createName;//创建人名称

	private Date createdDate;

	private Long modifyId;//更新人ID

	private String modifyName;//更新人名称

	private Date modifyTime;//更新时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	@Override
	public String toString() {
		return "ItemFavourite{" +
				"id=" + id +
				", userId=" + userId +
				", sellerId=" + sellerId +
				", shopId=" + shopId +
				", itemId=" + itemId +
				", skuId=" + skuId +
				", channelCode='" + channelCode + '\'' +
				", deleteFlag=" + deleteFlag +
				", createId=" + createId +
				", createName='" + createName + '\'' +
				", createdDate=" + createdDate +
				", modifyId=" + modifyId +
				", modifyName='" + modifyName + '\'' +
				", modifyTime=" + modifyTime +
				'}';
	}
}
