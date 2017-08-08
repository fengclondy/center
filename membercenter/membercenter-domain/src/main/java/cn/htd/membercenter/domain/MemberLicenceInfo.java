package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class MemberLicenceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long memberId;

	private String buyerBusinessLicenseId;

	private String buyerGuaranteeLicensePicSrc;

	private String buyerBusinessLicensePicSrc;

	private String certificateType;

	private String unifiedSocialCreditCode;

	private String businessLicenseId;

	private String businessLicensePicSrc;

	private String businessLicenseCertificatePicSrc;

	private String organizationId;

	private String organizationPicSrc;

	private String taxpayerType;

	private String taxManId;

	private String taxpayerCode;

	private String taxRegistrationCertificatePicSrc;

	private String taxpayerCertificatePicSrc;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getBuyerGuaranteeLicensePicSrc() {
		return buyerGuaranteeLicensePicSrc;
	}

	public void setBuyerGuaranteeLicensePicSrc(String buyerGuaranteeLicensePicSrc) {
		this.buyerGuaranteeLicensePicSrc = buyerGuaranteeLicensePicSrc == null ? null
				: buyerGuaranteeLicensePicSrc.trim();
	}

	public String getBuyerBusinessLicensePicSrc() {
		return buyerBusinessLicensePicSrc;
	}

	public void setBuyerBusinessLicensePicSrc(String buyerBusinessLicensePicSrc) {
		this.buyerBusinessLicensePicSrc = buyerBusinessLicensePicSrc == null ? null : buyerBusinessLicensePicSrc.trim();
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType == null ? null : certificateType.trim();
	}

	public String getUnifiedSocialCreditCode() {
		return unifiedSocialCreditCode;
	}

	public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
		this.unifiedSocialCreditCode = unifiedSocialCreditCode == null ? null : unifiedSocialCreditCode.trim();
	}

	public String getBusinessLicenseId() {
		return businessLicenseId;
	}

	public void setBusinessLicenseId(String businessLicenseId) {
		this.businessLicenseId = businessLicenseId == null ? null : businessLicenseId.trim();
	}

	public String getBusinessLicensePicSrc() {
		return businessLicensePicSrc;
	}

	public void setBusinessLicensePicSrc(String businessLicensePicSrc) {
		this.businessLicensePicSrc = businessLicensePicSrc == null ? null : businessLicensePicSrc.trim();
	}

	public String getBusinessLicenseCertificatePicSrc() {
		return businessLicenseCertificatePicSrc;
	}

	public void setBusinessLicenseCertificatePicSrc(String businessLicenseCertificatePicSrc) {
		this.businessLicenseCertificatePicSrc = businessLicenseCertificatePicSrc == null ? null
				: businessLicenseCertificatePicSrc.trim();
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId == null ? null : organizationId.trim();
	}

	public String getOrganizationPicSrc() {
		return organizationPicSrc;
	}

	public void setOrganizationPicSrc(String organizationPicSrc) {
		this.organizationPicSrc = organizationPicSrc == null ? null : organizationPicSrc.trim();
	}

	public String getTaxpayerType() {
		return taxpayerType;
	}

	public void setTaxpayerType(String taxpayerType) {
		this.taxpayerType = taxpayerType == null ? null : taxpayerType.trim();
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId == null ? null : taxManId.trim();
	}

	public String getTaxpayerCode() {
		return taxpayerCode;
	}

	public void setTaxpayerCode(String taxpayerCode) {
		this.taxpayerCode = taxpayerCode == null ? null : taxpayerCode.trim();
	}

	public String getTaxRegistrationCertificatePicSrc() {
		return taxRegistrationCertificatePicSrc;
	}

	public void setTaxRegistrationCertificatePicSrc(String taxRegistrationCertificatePicSrc) {
		this.taxRegistrationCertificatePicSrc = taxRegistrationCertificatePicSrc == null ? null
				: taxRegistrationCertificatePicSrc.trim();
	}

	public String getTaxpayerCertificatePicSrc() {
		return taxpayerCertificatePicSrc;
	}

	public void setTaxpayerCertificatePicSrc(String taxpayerCertificatePicSrc) {
		this.taxpayerCertificatePicSrc = taxpayerCertificatePicSrc == null ? null : taxpayerCertificatePicSrc.trim();
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
		this.createName = createName == null ? null : createName.trim();
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
		this.modifyName = modifyName == null ? null : modifyName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the buyerBusinessLicenseId
	 */
	public String getBuyerBusinessLicenseId() {
		return buyerBusinessLicenseId;
	}

	/**
	 * @param buyerBusinessLicenseId the buyerBusinessLicenseId to set
	 */
	public void setBuyerBusinessLicenseId(String buyerBusinessLicenseId) {
		this.buyerBusinessLicenseId = buyerBusinessLicenseId;
	}
}