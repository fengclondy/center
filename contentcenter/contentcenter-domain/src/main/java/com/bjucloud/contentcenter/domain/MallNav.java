package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [买场中心_导航信息]
 * </p>
 */
public class MallNav implements Serializable {

	private static final long serialVersionUID = 2464525488347111826L;

	private Integer id;
	private String navTitle; // '名称'
	private String navLink; // '指向链接'
	private Integer sortNum; // '排序顺序'
	private Integer status; // '启用状态，0，不启用；1，启用。'
	private Date created; // '创建时间'
	private Date modified; // '最后修改时间'
	private Integer themeId; // 主题id

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNavTitle() {
		return navTitle;
	}

	public void setNavTitle(String navTitle) {
		this.navTitle = navTitle;
	}

	public String getNavLink() {
		return navLink;
	}

	public void setNavLink(String navLink) {
		this.navLink = navLink;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
