package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class BusinessRelationshipDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long businessId;// 经营关系ID
	private Long buyerId;// 同公司信息表主键
	private Long sellerId;// 商家ID
	private Long categoryId;// 品类ID
	private Long brandId;// 品牌ID
	private String customerManagerId;// 客户经理ID
	private String erpStatus;// ERP下行状态
	private Date erpDownTime;// ERP下行时间
	private String erpErrorMsg;// ERP下行错误信息
	private String auditStatus;// 审批状态
	private Integer deleteFlag;// 删除标
	private Long createId;// 创建人ID
	private String createName;// 创建人名称
	private Date createTime;// 创建时间
	private Long modifyId;// 更新人ID
	private String modifyName;// 更新人名称
	private Date modifyTime;// 更新时间
	private Date auditTime;// 审批时间
	private Date deleteTime;// 删除时间
	private Long sellerIdCount;
	private String sellerCode;// 商家Code

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public Date getErpDownTime() {
		return erpDownTime;
	}

	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}

	public String getErpErrorMsg() {
		return erpErrorMsg;
	}

	public void setErpErrorMsg(String erpErrorMsg) {
		this.erpErrorMsg = erpErrorMsg;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
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

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getSellerIdCount() {
		return sellerIdCount;
	}

	public void setSellerIdCount(Long sellerIdCount) {
		this.sellerIdCount = sellerIdCount;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	private String remark;// 审核备注
}
