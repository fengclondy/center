package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberBusinessRelationDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private String businessId;
	// 会员编码
	private String buyerId;
	// 客户经理编码
	private String customerManagerId;
	// 客户经理名称
	private String customerManagerName;
	// 商家编码
	private String sellerId;
	// 店铺品类品牌编码
	private String shopCategoryBrandId;
	// 品类编码
	private long categoryId;
	// 品类名称
	private String categoryName;
	// 品牌编码
	private long brandId;
	// 品牌名称
	private String brandName;
	// 审核状态 0：待审核 1：审核通过 2：驳回
	private String auditStatus;
	// 下行ERP状态
	private String erpStatus;
	// 下行ERP时间
	private Date erpDownTime;
	// 下行失败报错信息
	private String erpErrorMsg;
	// 删除标志
	private int deleteFlag;
	private String createId;
	private String createName;
	private Date createTime;
	private String modifyId;
	private String modifyName;
	private Date modifyTime;
	private Date auditTime;
	private Date deleteTime;
	private String remark;

	/**
	 * @return the businessId
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId
	 *            the businessId to set
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * @return the buyerId
	 */
	public String getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId
	 *            the buyerId to set
	 */
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the customerManagerId
	 */
	public String getCustomerManagerId() {
		return customerManagerId;
	}

	/**
	 * @param customerManagerId
	 *            the customerManagerId to set
	 */
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	/**
	 * @return the customerManagerName
	 */
	public String getCustomerManagerName() {
		return customerManagerName;
	}

	/**
	 * @param customerManagerName
	 *            the customerManagerName to set
	 */
	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	/**
	 * @return the sellerId
	 */
	public String getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the shopCategoryBrandId
	 */
	public String getShopCategoryBrandId() {
		return shopCategoryBrandId;
	}

	/**
	 * @param shopCategoryBrandId
	 *            the shopCategoryBrandId to set
	 */
	public void setShopCategoryBrandId(String shopCategoryBrandId) {
		this.shopCategoryBrandId = shopCategoryBrandId;
	}

	/**
	 * @return the categoryId
	 */
	public long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the brandId
	 */
	public long getBrandId() {
		return brandId;
	}

	/**
	 * @param brandId
	 *            the brandId to set
	 */
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return the auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus
	 *            the auditStatus to set
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the erpStatus
	 */
	public String getErpStatus() {
		return erpStatus;
	}

	/**
	 * @param erpStatus
	 *            the erpStatus to set
	 */
	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	/**
	 * @return the erpDownTime
	 */
	public Date getErpDownTime() {
		return erpDownTime;
	}

	/**
	 * @param erpDownTime
	 *            the erpDownTime to set
	 */
	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}

	/**
	 * @return the erpErrorMsg
	 */
	public String getErpErrorMsg() {
		return erpErrorMsg;
	}

	/**
	 * @param erpErrorMsg
	 *            the erpErrorMsg to set
	 */
	public void setErpErrorMsg(String erpErrorMsg) {
		this.erpErrorMsg = erpErrorMsg;
	}

	/**
	 * @return the deleteFlag
	 */
	public int getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag
	 *            the deleteFlag to set
	 */
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return the createId
	 */
	public String getCreateId() {
		return createId;
	}

	/**
	 * @param createId
	 *            the createId to set
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}

	/**
	 * @param createName
	 *            the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyId
	 */
	public String getModifyId() {
		return modifyId;
	}

	/**
	 * @param modifyId
	 *            the modifyId to set
	 */
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	/**
	 * @return the modifyName
	 */
	public String getModifyName() {
		return modifyName;
	}

	/**
	 * @param modifyName
	 *            the modifyName to set
	 */
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the auditTime
	 */
	public Date getAuditTime() {
		return auditTime;
	}

	/**
	 * @param auditTime
	 *            the auditTime to set
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * @return the deleteTime
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}

	/**
	 * @param deleteTime
	 *            the deleteTime to set
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
