package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberCompanyInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8809373811940620440L;
	private Long memberId;// 会员ID
	private String memberCode;// 会员Code
	private String curBelongSellerId;// 当前归属商家ID
	private String curBelongManagerId;// 当前商家客户经理ID
	private Integer buyerSellerType;// 会员/商家类型 1：会员，2：商家'
	private String companyName;// 公司名称
	private String artificialPersonName;// 法人姓名
	private String artificialPersonMobile;// 法人手机号码
	private String artificialPersonIdcard;// 法人身份证号
	private String artificialPersonPicSrc;// 法人身份证电子版图片地址
	private String artificialPersonPicBackSrc;// 法人身份证电子版图片地址(反面)
	private String artificialPersonIdcardPicSrc;// 法人手持身份证电子版图片地址
	private String locationProvince;// 所在地-省
	private String locationCity;// 城市
	private String locationCounty;// 区
	private String locationTown;// 镇
	private String locationDetail;// 地址
	private String createId;
	private String createName;
	private String createTime;
	private String modifyId;
	private String modifyName;
	private String modifyTime;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getCurBelongSellerId() {
		return curBelongSellerId;
	}

	public void setCurBelongSellerId(String curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
	}

	public Integer getBuyerSellerType() {
		return buyerSellerType;
	}

	public void setBuyerSellerType(Integer buyerSellerType) {
		this.buyerSellerType = buyerSellerType;
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

	public void setArtificialPersonIdcardPicSrc(
			String artificialPersonIdcardPicSrc) {
		this.artificialPersonIdcardPicSrc = artificialPersonIdcardPicSrc;
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

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
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

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
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

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

}
