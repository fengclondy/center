package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShopFavouriteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long shopId; //店铺ID
	private Long sellerId; //卖家Id
	private Long buyerId; //会员ID
	private Long userId; //用户ID
	private Integer deleted;//删除标记
	private Long createId;
	private String createName;
	private Date createTime;
	private Long modifyId;
	private String modifyName;
	private Date modifyTime;
	private String shopName;
	private String logoUrl;
	private String shopUrl;
	private String sellerName;
	private List<QQCustomerDTO> qqCustomerDTOs;


	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public Long getCreateId() {
		return createId;
	}

	public String getCreateName() {
		return createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<QQCustomerDTO> getQqCustomerDTOs() {
		return qqCustomerDTOs;
	}

	public void setQqCustomerDTOs(List<QQCustomerDTO> qqCustomerDTOs) {
		this.qqCustomerDTOs = qqCustomerDTOs;
	}
}
