package cn.htd.membercenter.dto;

import java.io.Serializable;

public class YijifuCorporateDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getOutUserId() {
		return outUserId;
	}

	public void setOutUserId(String outUserId) {
		this.outUserId = outUserId;
	}

	public String getCorporateUserType() {
		return corporateUserType;
	}

	public void setCorporateUserType(String corporateUserType) {
		this.corporateUserType = corporateUserType;
	}

	public LegalPerson getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(LegalPerson legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	private String outUserId;// 外部会员唯一ID
	private String corporateUserType;// 企业用户类型
	private LegalPerson legalPerson;// 法人信息
	private String comName;// 企业名称
	private String verifyRealName;// 是否验证实名
	private String licenceNo;// 营业执照编号
	private String organizationCode;// 组织机构代码
	private String taxAuthorityNo;// 税务登记证号码
	private String email;// 联系邮箱
	private String mobileNo;// 手机号码
}
