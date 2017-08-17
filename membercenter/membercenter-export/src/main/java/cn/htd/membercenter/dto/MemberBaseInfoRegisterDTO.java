package cn.htd.membercenter.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class MemberBaseInfoRegisterDTO implements Serializable {
	private static final long serialVersionUID = 6502263076652255161L;
	private Long id;
	private Long memberId;// 会员ID
	private String memberCode;// 会员编码
	/**
	 * 公司名称
	 */
	@NotBlank(message = "公司名称不能为空")
	private String companyName;// 公司名称
	
	private String companyCode;// 公司编号
	/**
	 * 所在地-省
	 */
	@NotBlank(message = "所在地省编码不能为空")
	private String locationProvince;// 所在地-省
	/**
	 * 所在地-市
	 */
	@NotBlank(message = "所在地市编码不能为空")
	private String locationCity;// 所在地-市
	/**
	 * 所在地-区
	 */
	@NotBlank(message = "所在地区编码不能为空")
	private String locationCounty;// 所在地-区
	/**
	 * 所在地-镇
	 */
	@NotBlank(message = "所在地镇编码不能为空")
	private String locationTown;// 所在地-镇
	/**
	 * 所在地-详细地址
	 */
	@NotBlank(message = "所在地详细地址不能为空")
	private String locationDetail;// 所在地-详细
	private String locationAddr;// 所在地
	/**
	 * 法人姓名
	 */
	@NotBlank(message = "法人姓名不能为空")
	private String artificialPersonName;// 法人姓名
	/**
	 * 法人手机
	 */
	//@NotBlank(message = "法人手机号不能为空")
	private String artificialPersonMobile;// 法人手机号码
	private String artificialPersonIdcard;// 法人身份证
	private String artificialPersonPicSrc;// 法人身份证正面
	private String artificialPersonPicBackSrc;// 法人身份证反面
	private String artificialPersonIdcardPicSrc;// 法人手持身份证
	private Integer hasBusinessLicense; // 是否有营业执照
	private Integer hasGuaranteeLicense; // 是否有担保证明
	/**
	 * 营业执照电子版图片地址
	 */
	//@NotBlank(message = "营业执照电子版图片地址不能为空")
	private String businessLicensePicSrc; // 营业执照电子版图片地址
	private String buyerGuaranteeLicensePicSrc;// 供应商担保证明电子版图片地址
	private String businessLicenseCertificatePicSrc;// 会员营业执照变更证明图片地址
	/**
	 * 营业执照号
	 */
	//@NotBlank(message = "营业执照号不能为空")
	private String businessLicenseId;// 营业执照号
	private String buyerBusinessLicenseId;// 会员营业执照注册号
	private String buyerBusinessLicensePicSrc;// 会员营业执照电子版图片地址
	private String cooperateVendor; // 合作供应商
	private String registTime;// 注册时间
	private String registFrom;// 注册来源（推荐会员店）
	/**
	 * 经营范围
	 */
	@NotBlank(message = "经营范围不能为空")
	private String businessScope;// 经营范围
	private Long createId;// 创建人ID
	private String createName;// 创建人名称
	private String createTime;// 创建时间
	private Long modifyId;// 更新人ID
	private String modifyName;// 更新人名称
	private String modifyTime;// 更新时间
	/**
	 * 归属平台
	 */
	//@NotBlank(message = "平台公司编码不能为空")
	private Long belongSellerId;// 归属商家ID
	private Long curBelongSellerId;// 当前归属商家ID
	private Integer buyerSellerType;// 会员/商家类型 1：会员，2：商家
	private String remark;// 审核备注
	private String status;// 运营审批状态
	private String cooperateStatus;// 供应商审核状态
	private Long verifyInfoId;// 审批表id
	private String modifyType;// 业务类型
	private Integer isDiffIndustry;// 是否异业
	private String recommendeCode;// 推荐人编码
	private String promotionPerson;// 合作推广会员店
	/**
	 * 归属客户经理
	 */
	//@NotBlank(message = "客户经理编号不能为空")
	private String curBelongManagerId;//归属客户经理
	private String belongManagerId;//当前归属客户经理

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getLocationProvince() {
		return locationProvince;
	}

	public void setLocationProvince(String locationProvince) {
		this.locationProvince = locationProvince;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public String getLocationCounty() {
		return locationCounty;
	}

	public void setLocationCounty(String locationCounty) {
		this.locationCounty = locationCounty;
	}

	public String getLocationTown() {
		return locationTown;
	}

	public void setLocationTown(String locationTown) {
		this.locationTown = locationTown;
	}

	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
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

	public String getArtificialPersonIdcard() {
		return artificialPersonIdcard;
	}

	public void setArtificialPersonIdcard(String artificialPersonIdcard) {
		this.artificialPersonIdcard = artificialPersonIdcard;
	}

	public String getArtificialPersonPicSrc() {
		return artificialPersonPicSrc;
	}

	public void setArtificialPersonPicSrc(String artificialPersonPicSrc) {
		this.artificialPersonPicSrc = artificialPersonPicSrc;
	}

	public String getArtificialPersonPicBackSrc() {
		return artificialPersonPicBackSrc;
	}

	public void setArtificialPersonPicBackSrc(String artificialPersonPicBackSrc) {
		this.artificialPersonPicBackSrc = artificialPersonPicBackSrc;
	}

	public String getArtificialPersonIdcardPicSrc() {
		return artificialPersonIdcardPicSrc;
	}

	public void setArtificialPersonIdcardPicSrc(String artificialPersonIdcardPicSrc) {
		this.artificialPersonIdcardPicSrc = artificialPersonIdcardPicSrc;
	}

	public Integer getHasBusinessLicense() {
		return hasBusinessLicense;
	}

	public void setHasBusinessLicense(Integer hasBusinessLicense) {
		this.hasBusinessLicense = hasBusinessLicense;
	}

	public Integer getHasGuaranteeLicense() {
		return hasGuaranteeLicense;
	}

	public void setHasGuaranteeLicense(Integer hasGuaranteeLicense) {
		this.hasGuaranteeLicense = hasGuaranteeLicense;
	}

	public String getBusinessLicensePicSrc() {
		return businessLicensePicSrc;
	}

	public void setBusinessLicensePicSrc(String businessLicensePicSrc) {
		this.businessLicensePicSrc = businessLicensePicSrc;
	}

	public String getBusinessLicenseId() {
		return businessLicenseId;
	}

	public void setBusinessLicenseId(String businessLicenseId) {
		this.businessLicenseId = businessLicenseId;
	}

	public String getCooperateVendor() {
		return cooperateVendor;
	}

	public void setCooperateVendor(String cooperateVendor) {
		this.cooperateVendor = cooperateVendor;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public String getRegistFrom() {
		return registFrom;
	}

	public void setRegistFrom(String registFrom) {
		this.registFrom = registFrom;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getCurBelongSellerId() {
		return curBelongSellerId;
	}

	public void setCurBelongSellerId(Long curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	public String getBusinessLicenseCertificatePicSrc() {
		return businessLicenseCertificatePicSrc;
	}

	public void setBusinessLicenseCertificatePicSrc(String businessLicenseCertificatePicSrc) {
		this.businessLicenseCertificatePicSrc = businessLicenseCertificatePicSrc;
	}

	public String getBuyerGuaranteeLicensePicSrc() {
		return buyerGuaranteeLicensePicSrc;
	}

	public void setBuyerGuaranteeLicensePicSrc(String buyerGuaranteeLicensePicSrc) {
		this.buyerGuaranteeLicensePicSrc = buyerGuaranteeLicensePicSrc;
	}

	public String getBuyerBusinessLicenseId() {
		return buyerBusinessLicenseId;
	}

	public void setBuyerBusinessLicenseId(String buyerBusinessLicenseId) {
		this.buyerBusinessLicenseId = buyerBusinessLicenseId;
	}

	public String getBuyerBusinessLicensePicSrc() {
		return buyerBusinessLicensePicSrc;
	}

	public void setBuyerBusinessLicensePicSrc(String buyerBusinessLicensePicSrc) {
		this.buyerBusinessLicensePicSrc = buyerBusinessLicensePicSrc;
	}

	public Integer getBuyerSellerType() {
		return buyerSellerType;
	}

	public void setBuyerSellerType(Integer buyerSellerType) {
		this.buyerSellerType = buyerSellerType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getVerifyInfoId() {
		return verifyInfoId;
	}

	public void setVerifyInfoId(Long verifyInfoId) {
		this.verifyInfoId = verifyInfoId;
	}

	public String getCooperateStatus() {
		return cooperateStatus;
	}

	public void setCooperateStatus(String cooperateStatus) {
		this.cooperateStatus = cooperateStatus;
	}

	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public Long getBelongSellerId() {
		return belongSellerId;
	}

	public void setBelongSellerId(Long belongSellerId) {
		this.belongSellerId = belongSellerId;
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

	public Integer getIsDiffIndustry() {
		return isDiffIndustry;
	}

	public void setIsDiffIndustry(Integer isDiffIndustry) {
		this.isDiffIndustry = isDiffIndustry;
	}

	/**
	 * @return the recommendeCode
	 */
	public String getRecommendeCode() {
		return recommendeCode;
	}

	/**
	 * @param recommendeCode
	 *            the recommendeCode to set
	 */
	public void setRecommendeCode(String recommendeCode) {
		this.recommendeCode = recommendeCode;
	}

	/**
	 * @return the promotionPerson
	 */
	public String getPromotionPerson() {
		return promotionPerson;
	}

	/**
	 * @param promotionPerson
	 *            the promotionPerson to set
	 */
	public void setPromotionPerson(String promotionPerson) {
		this.promotionPerson = promotionPerson;
	}

	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
	}

	public String getBelongManagerId() {
		return belongManagerId;
	}

	public void setBelongManagerId(String belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

}
