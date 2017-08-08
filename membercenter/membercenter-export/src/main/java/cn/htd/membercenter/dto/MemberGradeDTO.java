package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberGradeDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	// 会员编码
	private String memberId;
	// 会员名称
	private String companyName;
	private String memberCode;
	// 联系电话
	private String phoneNumber;
	// 会员类型
	private String memberType;
	// 账号类型
	private String accountType;
	// 是否商城登陆标志
	private String canMallLogin;
	// 是否有担保证明
	private String hasGuaranteeLicense;
	// 是否有营业执照
	private String hasBusinessLicense;
	// 是否收费会员
	private String isVip;
	// 会员等级
	private String buyerGrade;
	// 会员经验等级
	private String pointGrade;
	// 会员套餐类型 1：VIP套餐 2：智慧门店套餐
	private String memberPackageType;
	// 套餐生效开始时间
	private Date packageActiveStartTime;
	// 套餐生效结束时间
	private Date packageActiveEndTime;
	// 套餐更新时间
	private Date packageUpdateTime;
	// 是否卖家
	private Integer isSeller;

	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

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
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the canMallLogin
	 */
	public String getCanMallLogin() {
		return canMallLogin;
	}

	/**
	 * @param canMallLogin
	 *            the canMallLogin to set
	 */
	public void setCanMallLogin(String canMallLogin) {
		this.canMallLogin = canMallLogin;
	}

	/**
	 * @return the hasGuaranteeLicense
	 */
	public String getHasGuaranteeLicense() {
		return hasGuaranteeLicense;
	}

	/**
	 * @param hasGuaranteeLicense
	 *            the hasGuaranteeLicense to set
	 */
	public void setHasGuaranteeLicense(String hasGuaranteeLicense) {
		this.hasGuaranteeLicense = hasGuaranteeLicense;
	}

	/**
	 * @return the hasBusinessLicense
	 */
	public String getHasBusinessLicense() {
		return hasBusinessLicense;
	}

	/**
	 * @param hasBusinessLicense
	 *            the hasBusinessLicense to set
	 */
	public void setHasBusinessLicense(String hasBusinessLicense) {
		this.hasBusinessLicense = hasBusinessLicense;
	}

	/**
	 * @return the isVip
	 */
	public String getIsVip() {
		return isVip;
	}

	/**
	 * @param isVip
	 *            the isVip to set
	 */
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	/**
	 * @return the buyerGrade
	 */
	public String getBuyerGrade() {
		return buyerGrade;
	}

	/**
	 * @param buyerGrade
	 *            the buyerGrade to set
	 */
	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	/**
	 * @return the pointGrade
	 */
	public String getPointGrade() {
		return pointGrade;
	}

	/**
	 * @param pointGrade
	 *            the pointGrade to set
	 */
	public void setPointGrade(String pointGrade) {
		this.pointGrade = pointGrade;
	}

	/**
	 * @return the memberPackageType
	 */
	public String getMemberPackageType() {
		return memberPackageType;
	}

	/**
	 * @param memberPackageType
	 *            the memberPackageType to set
	 */
	public void setMemberPackageType(String memberPackageType) {
		this.memberPackageType = memberPackageType;
	}

	/**
	 * @return the packageActiveStartTime
	 */
	public Date getPackageActiveStartTime() {
		return packageActiveStartTime;
	}

	/**
	 * @param packageActiveStartTime
	 *            the packageActiveStartTime to set
	 */
	public void setPackageActiveStartTime(Date packageActiveStartTime) {
		this.packageActiveStartTime = packageActiveStartTime;
	}

	/**
	 * @return the packageActiveEndTime
	 */
	public Date getPackageActiveEndTime() {
		return packageActiveEndTime;
	}

	/**
	 * @param packageActiveEndTime
	 *            the packageActiveEndTime to set
	 */
	public void setPackageActiveEndTime(Date packageActiveEndTime) {
		this.packageActiveEndTime = packageActiveEndTime;
	}

	/**
	 * @return the packageUpdateTime
	 */
	public Date getPackageUpdateTime() {
		return packageUpdateTime;
	}

	/**
	 * @param packageUpdateTime
	 *            the packageUpdateTime to set
	 */
	public void setPackageUpdateTime(Date packageUpdateTime) {
		this.packageUpdateTime = packageUpdateTime;
	}

	/**
	 * @return the isSeller
	 */
	public Integer getIsSeller() {
		return isSeller;
	}

	/**
	 * @param isSeller the isSeller to set
	 */
	public void setIsSeller(Integer isSeller) {
		this.isSeller = isSeller;
	}

}
