package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberVerifyInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long memberId;// 会员ID
	private String registTime;// 注册时间
	private String companyName;// 公司名称
	private String locationProvince;// 省
	private String locationCity;// 市
	private String locationCounty;// 区
	private String locationTown;// 镇
	private String locationDetail;// 详细地址
	private String artificialPersonName;// 法人姓名
	private String artificialPersonMobile;// 法人电话
	private String artificialPersonIdcard;// 法人身份证号
	private int isDiffIndustry;// 是否异业：1是，0否
	private String industryCategory;// 发展行业
	private String belongManagerId;// 客户经理ID
	private String buyerFeature;// 会员属性
	private String buyerBusinessLicensePicSrc;// 营业执照电子图片存放地址

	private String locationAddr;
	// 会员类型: 1：非会员，3：担保会员 ，2：正式会员
	private String memberType;
	// 供应商担保证明电子版图片地址
	private String buyerGuaranteeLicensePicSrc;
	
	/**
	 * 身份证正面
	 */
	private String artificialPersonPicSrc;
	/**
	 * 身份证反面
	 */
	private String artificialPersonPicBackSrc;
	/**
	 * 手持身份证
	 */
	private String artificialPersonIdcardPicSrc;

	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType
	 *            the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the buyerGuaranteeLicensePicSrc
	 */
	public String getBuyerGuaranteeLicensePicSrc() {
		return buyerGuaranteeLicensePicSrc;
	}

	/**
	 * @param buyerGuaranteeLicensePicSrc
	 *            the buyerGuaranteeLicensePicSrc to set
	 */
	public void setBuyerGuaranteeLicensePicSrc(String buyerGuaranteeLicensePicSrc) {
		this.buyerGuaranteeLicensePicSrc = buyerGuaranteeLicensePicSrc;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getIsDiffIndustry() {
		return isDiffIndustry;
	}

	public void setIsDiffIndustry(int isDiffIndustry) {
		this.isDiffIndustry = isDiffIndustry;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getBuyerBusinessLicensePicSrc() {
		return buyerBusinessLicensePicSrc;
	}

	public void setBuyerBusinessLicensePicSrc(String buyerBusinessLicensePicSrc) {
		this.buyerBusinessLicensePicSrc = buyerBusinessLicensePicSrc;
	}

	public String getBelongManagerId() {
		return belongManagerId;
	}

	public void setBelongManagerId(String belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

	public String getBuyerFeature() {
		return buyerFeature;
	}

	public void setBuyerFeature(String buyerFeature) {
		this.buyerFeature = buyerFeature;
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
	
}
