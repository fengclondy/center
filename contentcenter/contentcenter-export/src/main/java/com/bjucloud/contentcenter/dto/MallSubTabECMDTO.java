package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [商城设置--页签列表DTO]
 * </p>
 */
public class MallSubTabECMDTO implements Serializable {

	private static final long serialVersionUID = 3193156711185490662L;
	private Long id; //
	private Long sortNum;// 排序顺序
	private String recName;// 楼层名称
	private String title; // 名称
	private String url; // 链接指向
	private String redId; // mall_recommend商城推荐id，即楼层id。
	private String status; // 启用状态。0，不启用；1，启用。
	private Date created; // 创建时间
	private Date modified;// 改动时间
	private Date startTime;// 开始时间
	private Date endTime;// 结束
	private String templateId;// 模板id
	private String remark;// 模板名称
	private Integer themeId;// 主题id
	private String themeName; // 主题名称
	private Integer themeStatus; // 主题状态
	private Integer themeType;// 主题类型
	private String themeUId; // 创建者id
	private Long cid;// 主题类目
	private Long addressId;// 主题地区

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getThemeUId() {
		return themeUId;
	}

	public void setThemeUId(String themeUId) {
		this.themeUId = themeUId;
	}

	public Integer getThemeStatus() {
		return themeStatus;
	}

	public void setThemeStatus(Integer themeStatus) {
		this.themeStatus = themeStatus;
	}

	public Integer getThemeType() {
		return themeType;
	}

	public void setThemeType(Integer themeType) {
		this.themeType = themeType;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public Long getId() {
		return id;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getRedId() {
		return redId;
	}

	public void setRedId(String redId) {
		this.redId = redId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}