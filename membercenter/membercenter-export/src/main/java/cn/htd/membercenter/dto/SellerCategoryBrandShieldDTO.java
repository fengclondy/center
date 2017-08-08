package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class SellerCategoryBrandShieldDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 品类品牌屏蔽ID
	private int categoryBrandShieldId;
	// 商家ID
	private int sellerId;
	// 品类品牌关联ID
	private int categoryBrandId;
	// 三级类目ID
	private int thirdCategoryId;
	// 品牌ID
	private int brandId;
	// 渠道ID
	private int productChannelId;
	// 屏蔽标记 0：未屏蔽，1：已屏蔽
	private int shieldFlag;
	// 创建人ID
	private int userId;
	// 创建人名称
	private String userName;
	// 创建时间
	private Date createdTime;
	// 更新人ID
	private int lastUpdatedId;
	// 更新人名称
	private String lastUpdatedName;
	// 更新时间
	private Date lastUpdatedTime;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getCategoryBrandShieldId() {
		return categoryBrandShieldId;
	}

	public void setCategoryBrandShieldId(int categoryBrandShieldId) {
		this.categoryBrandShieldId = categoryBrandShieldId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getCategoryBrandId() {
		return categoryBrandId;
	}

	public void setCategoryBrandId(int categoryBrandId) {
		this.categoryBrandId = categoryBrandId;
	}

	public int getThirdCategoryId() {
		return thirdCategoryId;
	}

	public void setThirdCategoryId(int thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getProductChannelId() {
		return productChannelId;
	}

	public void setProductChannelId(int productChannelId) {
		this.productChannelId = productChannelId;
	}

	public int getShieldFlag() {
		return shieldFlag;
	}

	public void setShieldFlag(int shieldFlag) {
		this.shieldFlag = shieldFlag;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getLastUpdatedId() {
		return lastUpdatedId;
	}

	public void setLastUpdatedId(int lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	public String getLastUpdatedName() {
		return lastUpdatedName;
	}

	public void setLastUpdatedName(String lastUpdatedName) {
		this.lastUpdatedName = lastUpdatedName;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

}
