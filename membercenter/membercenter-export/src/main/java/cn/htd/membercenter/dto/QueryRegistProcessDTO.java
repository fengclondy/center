package cn.htd.membercenter.dto;

import java.io.Serializable;

public class QueryRegistProcessDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer memberId;// 会员ID
	private String companyName;// 公司名称
	private String artificialPersonName;// 联系人姓名
	private String artificialPersonMobile;// 联系人电话
	private String artificialPersonIdcard;// 联系人身份证号
	private String artificialPersonPicSrc;// 联系人身份证正面照
	private String artificialPersonPicBackSrc;// 联系人身份证反面照
	private String industryCategory;// 发展行业
	private String buyerBusinessLicensePicSrc;// 营业执照照片
	private String buyerBusinessLicenseId;// 会员营业执照号
	private String locationAddr;// 公司地址
	private String registProcess;// 注册进度
	private String verifyStatus;// 审核状态-->1为待审核，2为通过，3为驳回
	private String infoType;// 会员状态--》11：会员注册运营审核，12：会员注册供应商审核，

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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

	public String getBuyerBusinessLicenseId() {
		return buyerBusinessLicenseId;
	}

	public void setBuyerBusinessLicenseId(String buyerBusinessLicenseId) {
		this.buyerBusinessLicenseId = buyerBusinessLicenseId;
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

	public String getLocationAddr() {
		return locationAddr;
	}

	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	public String getRegistProcess() {
		return registProcess;
	}

	public void setRegistProcess(String registProcess) {
		this.registProcess = registProcess;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

}
