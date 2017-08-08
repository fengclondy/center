package cn.htd.membercenter.dto;

import java.io.Serializable;

public class YijifuCorporateModifyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;// 用户ID
	private String comName;// 企业名称
	private String legalPersonName;// 法人姓名
	private String legalPersonCertNo;// 法人证件号码
	private String verifyRealName;// 是否验证实名信息
	private String licenceNo;// 营业执照编号
	private String organizationCode;// 组织机构代码证号
	private String taxAuthorityNo;// 税务登记证号码
	private String mobileNo;// 联系手机号码
	private String email;// 联系邮箱

	private String corporateUserType;// 用户类型

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getLegalPersonName() {
		return legalPersonName;
	}

	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}

	public String getLegalPersonCertNo() {
		return legalPersonCertNo;
	}

	public void setLegalPersonCertNo(String legalPersonCertNo) {
		this.legalPersonCertNo = legalPersonCertNo;
	}

	public String getVerifyRealName() {
		return verifyRealName;
	}

	public void setVerifyRealName(String verifyRealName) {
		this.verifyRealName = verifyRealName;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getTaxAuthorityNo() {
		return taxAuthorityNo;
	}

	public void setTaxAuthorityNo(String taxAuthorityNo) {
		this.taxAuthorityNo = taxAuthorityNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the corporateUserType
	 */
	public String getCorporateUserType() {
		return corporateUserType;
	}

	/**
	 * @param corporateUserType the corporateUserType to set
	 */
	public void setCorporateUserType(String corporateUserType) {
		this.corporateUserType = corporateUserType;
	}

}
