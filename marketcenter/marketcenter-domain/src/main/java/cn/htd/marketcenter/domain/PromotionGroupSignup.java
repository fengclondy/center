package cn.htd.marketcenter.domain;

import java.util.Date;

public class PromotionGroupSignup {

	// 团购主键
	private Long groupId;
	// 团购活动编码
	private Long promotionId;
	// 买家编码
	private String buyerCode;
	// 团购商品数量
	private Integer productNum;
	// 买家姓名
	private String buyerName;
	// 买家手机
	private String buyerMobile;
	// 团购渠道0：平台 1：超级老板 2：H5
	private String signupChannel;
	// 省
	private String buyerAddressProvince;
	// 市
	private String buyerAddressCity;
	// 区
	private String buyerAddressDistrict;
	// 县
	private String buyerAddressTown;
	// 详细地址
	private String buyerAddressDetail;

	private Date createTime;

	private Long createId;

	private String modifyTime;

	private Long modifyId;

	private String remark;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode == null ? null : buyerCode.trim();
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName == null ? null : buyerName.trim();
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile == null ? null : buyerMobile.trim();
	}

	public String getSignupChannel() {
		return signupChannel;
	}

	public void setSignupChannel(String signupChannel) {
		this.signupChannel = signupChannel;
	}

	public String getBuyerAddressProvince() {
		return buyerAddressProvince;
	}

	public void setBuyerAddressProvince(String buyerAddressProvince) {
		this.buyerAddressProvince = buyerAddressProvince == null ? null : buyerAddressProvince.trim();
	}

	public String getBuyerAddressCity() {
		return buyerAddressCity;
	}

	public void setBuyerAddressCity(String buyerAddressCity) {
		this.buyerAddressCity = buyerAddressCity == null ? null : buyerAddressCity.trim();
	}

	public String getBuyerAddressDistrict() {
		return buyerAddressDistrict;
	}

	public void setBuyerAddressDistrict(String buyerAddressDistrict) {
		this.buyerAddressDistrict = buyerAddressDistrict == null ? null : buyerAddressDistrict.trim();
	}

	public String getBuyerAddressTown() {
		return buyerAddressTown;
	}

	public void setBuyerAddressTown(String buyerAddressTown) {
		this.buyerAddressTown = buyerAddressTown == null ? null : buyerAddressTown.trim();
	}

	public String getBuyerAddressDetail() {
		return buyerAddressDetail;
	}

	public void setBuyerAddressDetail(String buyerAddressDetail) {
		this.buyerAddressDetail = buyerAddressDetail == null ? null : buyerAddressDetail.trim();
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

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime == null ? null : modifyTime.trim();
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}