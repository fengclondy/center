package cn.htd.usercenter.dto;

import java.io.Serializable;

public class UserRoleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 用户编码
	private String userId;
	// 角色编码
	private String[] roleId;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the roleId
	 */
	public String[] getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(String[] roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
