package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class VmsmenuDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 子菜单列表
	private List<VmsmenuDTO> subMenus;

	// 项目ID
	private String id;
	// 权限项目名称
	private String name;
	// 权限项目类型
	private String type;
	// 项目阶层
	private int level;
	// 父权限项目ID
	private String parentId;
	// 页面URL
	private String pageUrl;
	// 机能标示
	private String functionSymbol;
	// 展示顺序
	private int displayOrder;
	// 是否删除标志
	private int deletedFlag;
	// 创建用户编码
	private String createdId;
	// 创建时间
	private Date createdTime;
	// 更新用户编码
	private String lastUpdatedId;
	// 更新时间
	private Date lastUpdatedTime;
	private int internal;
	private int external;
	private int enableUrl;
	private int primary;
	private String iconclass;

	/**
	 * @return the internal
	 */
	public int getInternal() {
		return internal;
	}

	/**
	 * @param internal
	 *            the internal to set
	 */
	public void setInternal(int internal) {
		this.internal = internal;
	}

	/**
	 * @return the external
	 */
	public int getExternal() {
		return external;
	}

	/**
	 * @param external
	 *            the external to set
	 */
	public void setExternal(int external) {
		this.external = external;
	}

	/**
	 * @return the enableUrl
	 */
	public int getEnableUrl() {
		return enableUrl;
	}

	/**
	 * @param enableUrl
	 *            the enableUrl to set
	 */
	public void setEnableUrl(int enableUrl) {
		this.enableUrl = enableUrl;
	}

	/**
	 * @return the primary
	 */
	public int getPrimary() {
		return primary;
	}

	/**
	 * @param primary
	 *            the primary to set
	 */
	public void setPrimary(int primary) {
		this.primary = primary;
	}

	/**
	 * @return the iconclass
	 */
	public String getIconclass() {
		return iconclass;
	}

	/**
	 * @param iconclass
	 *            the iconclass to set
	 */
	public void setIconclass(String iconclass) {
		this.iconclass = iconclass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public List<VmsmenuDTO> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<VmsmenuDTO> subMenus) {
		this.subMenus = subMenus;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the functionSymbol
	 */
	public String getFunctionSymbol() {
		return functionSymbol;
	}

	/**
	 * @param functionSymbol
	 *            the functionSymbol to set
	 */
	public void setFunctionSymbol(String functionSymbol) {
		this.functionSymbol = functionSymbol;
	}

	/**
	 * @return the displayOrder
	 */
	public int getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder
	 *            the displayOrder to set
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
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

}
