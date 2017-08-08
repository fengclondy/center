package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class SubstationDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	// 分站编码
	private int substationId;
	// 分站名称
	private String name;
	// 管辖区域
	private String areas;
	// 分站管理员
	private Long manager;
	// 是否删除标志
	private int deleted_flag;
	// 创建用户编码
	private String createdId;
	// 创建时间
	private Date createdTime;
	// 更新用户编码
	private String lastUpdatedId;
	// 更新时间
	private Date lastUpdatedTime;
	
	//管理员
	private String adminName;
	
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public int getSubstationId() {
		return substationId;
	}

	public void setSubstationId(int substationId) {
		this.substationId = substationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public Long getManager() {
		return manager;
	}

	public void setManager(Long manager) {
		this.manager = manager;
	}

	public int getDeleted_flag() {
		return deleted_flag;
	}

	public void setDeleted_flag(int deleted_flag) {
		this.deleted_flag = deleted_flag;
	}

	public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

}
