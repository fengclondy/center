package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * QQ客服DTO
 */
public class QQCustomerDTO implements Serializable {

	private static final long serialVersionUID = -2006540746150207037L;
	private Long id;// 主键
	private String customerType;// 客服类型：0平台客服，1店铺客服
	private Long sellerId;// 卖家ID
	private Long shopId;// 店铺ID
	private String shopName;// 店铺名称
	private String customerQQNumber;// 客服腾讯账号
	private Integer customerSortNumber;// 客服编号：从0开始
	private Date createTime;// 创建日期
	private Integer deleted;// 是否逻辑删除标记：0在用，未伪删除；1停用，已伪删除
	private Integer isDefault; // 是否默认客服 0否 1是
	private Long createId;
	private String createName;
	private Long modifyId;
	private String modifyName;
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCustomerQQNumber() {
		return customerQQNumber;
	}

	public void setCustomerQQNumber(String customerQQNumber) {
		this.customerQQNumber = customerQQNumber;
	}

	public Integer getCustomerSortNumber() {
		return customerSortNumber;
	}

	public void setCustomerSortNumber(Integer customerSortNumber) {
		this.customerSortNumber = customerSortNumber;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
