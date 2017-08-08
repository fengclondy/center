package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class LoginLogDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id; // 日志ID
	private Long loginUserId; // 登录用户ID
	private String loginUserName; // 登录用户名
	private String loginProductName; // 登录产品名
	private String clientIPAddr; // 客户端IP
	private Timestamp loginTime;
	private String loginDeviceInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginProductName() {
		return loginProductName;
	}

	public void setLoginProductName(String loginProductName) {
		this.loginProductName = loginProductName;
	}

	public String getClientIPAddr() {
		return clientIPAddr;
	}

	public void setClientIPAddr(String clientIPAddr) {
		this.clientIPAddr = clientIPAddr;
	}

	/**
	 * @return the loginTime
	 */
	public Timestamp getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime
	 *            the loginTime to set
	 */
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return the loginDeviceInfo
	 */
	public String getLoginDeviceInfo() {
		return loginDeviceInfo;
	}

	/**
	 * @param loginDeviceInfo
	 *            the loginDeviceInfo to set
	 */
	public void setLoginDeviceInfo(String loginDeviceInfo) {
		this.loginDeviceInfo = loginDeviceInfo;
	}

}
