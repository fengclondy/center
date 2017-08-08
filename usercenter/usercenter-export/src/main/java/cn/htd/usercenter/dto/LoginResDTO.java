package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import cn.htd.usercenter.enums.UserEnums.EmployeeCompanyType;

public class LoginResDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ticket; // SSO用Ticket

	private Long uid; // 用户ID
	private String uname; // 用户名
	private String nickname; // 昵称
	private String email; // 邮件地址
	private String mobile; // 手机号码
	private String avatar; // 头像
	private Timestamp lastLoginTime; // 上次登录时间
	private String loginId; // 用户loginid
	private String empNo; // 员工编号
	private String empCompanyId; // 员工所属公司
	private String empCompanyName; // 员工所属公司名
	private EmployeeCompanyType empCompanyType; // 员工所属公司类型
	private Boolean isCustomerManager; // 是否客户经理

	private Long cusCompanyId; // 客户所属公司ID

	private Boolean isVmsInnerUser;

	private String vmsPermissions;

	private Integer defaultContact;

	private Boolean isLowFlag;

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

	/**
	 * @return the isVmsInnerUser
	 */
	public Boolean getIsVmsInnerUser() {
		return isVmsInnerUser;
	}

	/**
	 * @param isVmsInnerUser
	 *            the isVmsInnerUser to set
	 */
	public void setIsVmsInnerUser(Boolean isVmsInnerUser) {
		this.isVmsInnerUser = isVmsInnerUser;
	}

	/**
	 * @return the vmsPermissions
	 */
	public String getVmsPermissions() {
		return vmsPermissions;
	}

	/**
	 * @param vmsPermissions
	 *            the vmsPermissions to set
	 */
	public void setVmsPermissions(String vmsPermissions) {
		this.vmsPermissions = vmsPermissions;
	}

	/**
	 * @return the defaultContact
	 */
	public Integer getDefaultContact() {
		return defaultContact;
	}

	/**
	 * @param defaultContact
	 *            the defaultContact to set
	 */
	public void setDefaultContact(Integer defaultContact) {
		this.defaultContact = defaultContact;
	}

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
	 * 构造函数
	 */
	public LoginResDTO() {
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpCompanyId() {
		return empCompanyId;
	}

	public void setEmpCompanyId(String empCompanyId) {
		this.empCompanyId = empCompanyId;
	}

	public String getEmpCompanyName() {
		return empCompanyName;
	}

	public void setEmpCompanyName(String empCompanyName) {
		this.empCompanyName = empCompanyName;
	}

	public EmployeeCompanyType getEmpCompanyType() {
		return empCompanyType;
	}

	public void setEmpCompanyType(EmployeeCompanyType empCompanyType) {
		this.empCompanyType = empCompanyType;
	}

	public Boolean getIsCustomerManager() {
		return isCustomerManager;
	}

	public void setIsCustomerManager(Boolean isCustomerManager) {
		this.isCustomerManager = isCustomerManager;
	}

	public Long getCusCompanyId() {
		return cusCompanyId;
	}

	public void setCusCompanyId(Long cusCompanyId) {
		this.cusCompanyId = cusCompanyId;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
