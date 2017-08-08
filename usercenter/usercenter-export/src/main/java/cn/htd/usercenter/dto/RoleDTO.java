package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class RoleDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	// 角色编码
	private String roleId;
	// 产品编码
	private String productId;
	// 角色名称
	private String name;
	// 添加角色权限
	private String addPermissions;
	// 删除角色权限
	private String deletePermissions;
	// 删除标志
	private int deletedFlag;
	// 创建用户编码
	private String createdId;
	// 创建时间
	private Date createdTime;
	// 更新用户编码
	private String lastUpdatedId;
	// 更新时间
	private Date lastUpdatedTime;
	// 角色是否被绑定标志(程序用非库表字段)
	private String checkedFlag;
	// 产品名称(程序用非库表字段)
	private String productName;

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @return the addPermissions
	 */
	public String getAddPermissions() {
		return addPermissions;
	}

	/**
	 * @param addPermissions
	 *            the addPermissions to set
	 */
	public void setAddPermissions(String addPermissions) {
		this.addPermissions = addPermissions;
	}

	/**
	 * @return the deletePermissions
	 */
	public String getDeletePermissions() {
		return deletePermissions;
	}

	/**
	 * @param deletePermissions
	 *            the deletePermissions to set
	 */
	public void setDeletePermissions(String deletePermissions) {
		this.deletePermissions = deletePermissions;
	}

	/**
	 * @return the deletedFlag
	 */
	public int getDeletedFlag() {
		return deletedFlag;
	}

	/**
	 * @param deletedFlag
	 *            the deletedFlag to set
	 */
	public void setDeletedFlag(int deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	/**
	 * @return the createdId
	 */
	public String getCreatedId() {
		return createdId;
	}

	/**
	 * @param createdId
	 *            the createdId to set
	 */
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime
	 *            the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the lastUpdatedId
	 */
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	/**
	 * @param lastUpdatedId
	 *            the lastUpdatedId to set
	 */
	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the checkedFlag
	 */
	public String getCheckedFlag() {
		return checkedFlag;
	}

	/**
	 * @param checkedFlag
	 *            the checkedFlag to set
	 */
	public void setCheckedFlag(String checkedFlag) {
		this.checkedFlag = checkedFlag;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

}
