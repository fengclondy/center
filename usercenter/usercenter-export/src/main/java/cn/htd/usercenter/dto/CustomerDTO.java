package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class CustomerDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loginId;// 人员名称

	private String password; // 密码

	private String mobile; // 手机号码

	private String name;// 用户名

	private String companyType;// 公司类型

	private String companyName;// 公司名称

	private Long userId;

	private Long companyId;

	private String erpCode;

	private Boolean isUniqueMobile;

	private Boolean isBindingLogon;

	private Boolean isVmsInnerUser;

	private Long vmsSuperiorId;

	private String vmsDepartment;

	private String vmsPermissions;

	private Integer defaultContact;

	private Boolean deletedFlag;

	private Boolean isLowFlag;

	private Long createdId;

	private Date createdTime;

	private Long lastUpdatedId;

	private Date lastUpdatedTime;

	/**
	 * @return the isLowFlag
	 */
	public Boolean getIsLowFlag() {
		return isLowFlag;
	}

	/**
	 * @param isLowFlag
	 *            the isLowFlag to set
	 */
	public void setIsLowFlag(Boolean isLowFlag) {
		this.isLowFlag = isLowFlag;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the companyType
	 */
	public String getCompanyType() {
		return companyType;
	}

	/**
	 * @param companyType
	 *            the companyType to set
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode == null ? null : erpCode.trim();
	}

	public Boolean getIsUniqueMobile() {
		return isUniqueMobile;
	}

	public void setIsUniqueMobile(Boolean isUniqueMobile) {
		this.isUniqueMobile = isUniqueMobile;
	}

	public Boolean getIsBindingLogon() {
		return isBindingLogon;
	}

	public void setIsBindingLogon(Boolean isBindingLogon) {
		this.isBindingLogon = isBindingLogon;
	}

	public Boolean getIsVmsInnerUser() {
		return isVmsInnerUser;
	}

	public void setIsVmsInnerUser(Boolean isVmsInnerUser) {
		this.isVmsInnerUser = isVmsInnerUser;
	}

	public Long getVmsSuperiorId() {
		return vmsSuperiorId;
	}

	public void setVmsSuperiorId(Long vmsSuperiorId) {
		this.vmsSuperiorId = vmsSuperiorId;
	}

	public String getVmsDepartment() {
		return vmsDepartment;
	}

	public void setVmsDepartment(String vmsDepartment) {
		this.vmsDepartment = vmsDepartment;
	}

	public String getVmsPermissions() {
		return vmsPermissions;
	}

	public void setVmsPermissions(String vmsPermissions) {
		this.vmsPermissions = vmsPermissions;
	}

	public Integer getDefaultContact() {
		return defaultContact;
	}

	public void setDefaultContact(Integer defaultContact) {
		this.defaultContact = defaultContact;
	}

	public Boolean getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(Boolean deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Long getCreatedId() {
		return createdId;
	}

	public void setCreatedId(Long createdId) {
		this.createdId = createdId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getLastUpdatedId() {
		return lastUpdatedId;
	}

	public void setLastUpdatedId(Long lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
}