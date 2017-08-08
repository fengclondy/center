package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

public class ApplyBusiRelationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long businessId;// 经营关系ID
	private Long boxId;// 包厢关系Id
	private Long memberId;// 会员ID
	private String memberCode;//会员memberCode
	private Long sellerId;// 商家ID
	private Long categoryId;// 品类ID
	private Long brandId;// 品牌ID
	private String companyName;// 公司名称
	private String artificialPersonName;// 联系人姓名
	private String artificialPersonMobile;// 联系人电话
	private String customerManagerId;// 客户经理ID
	private String customerManagerName;// 客户经理名称
	private String categoryName;// 经营品类
	private String brandName;// 经营品牌
	private Long createId;// 创建人ID
	private String createName;// 创建人
	private Long modifyId;// 修改人ID
	private String modifyName;// 修改人
	private String createTime;// 申请时间
	private List<CategoryBrandDTO> categoryBrand;// 品类品牌ID
	private String auditStatus;// 审批状态
	private String locationAddr;// 公司地址
	private Integer deleteFlag;// 删除标记
	private String buyerFeature;// 会员属性
	private String remark;// 备注：记录审批驳回的原因
	private String erpStatus;// ERP下行状态
	private Long verifyId;

	private String belongStatus;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public String getBuyerFeature() {
		return buyerFeature;
	}

	public void setBuyerFeature(String buyerFeature) {
		this.buyerFeature = buyerFeature;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
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

	public List<CategoryBrandDTO> getCategoryBrand() {
		return categoryBrand;
	}

	public void setCategoryBrand(List<CategoryBrandDTO> categoryBrand) {
		this.categoryBrand = categoryBrand;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
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

	/**
	 * @return the locationAddr
	 */
	public String getLocationAddr() {
		return locationAddr;
	}

	/**
	 * @param locationAddr
	 *            the locationAddr to set
	 */
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	/**
	 * @return the belongStatus
	 */
	public String getBelongStatus() {
		return belongStatus;
	}

	/**
	 * @param belongStatus
	 *            the belongStatus to set
	 */
	public void setBelongStatus(String belongStatus) {
		this.belongStatus = belongStatus;
	}

	/**
	 * @return the verifyId
	 */
	public Long getVerifyId() {
		return verifyId;
	}

	/**
	 * @param verifyId
	 *            the verifyId to set
	 */
	public void setVerifyId(Long verifyId) {
		this.verifyId = verifyId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
	
}
