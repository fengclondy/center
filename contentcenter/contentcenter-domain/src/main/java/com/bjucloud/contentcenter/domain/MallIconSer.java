package com.bjucloud.contentcenter.domain;

import java.io.Serializable;

/**
 * 
 * <p>
 * Description: [买场中心_小图标、服务信息]
 * </p>
 */
public class MallIconSer implements Serializable {
	private static final long serialVersionUID = -4932316915601061385L;
	private Integer id;// id
	private java.lang.String title;// '名称'
	private java.lang.String imgPath;// '图片保存路径'
	private java.lang.String iconLink;// '指向链接'
	private java.lang.String iconType;// '图片类型，所属区域。1,首推滚动右侧小图标 2，底部服务'
	private Integer status; // '启用状态。0，不启用；1，启用'
	private Integer sortNum; // '排序顺序'
	private java.util.Date created;// '创建时间'
	private java.util.Date modified;// '最后修改时间'
	private java.lang.String sugSize; // '图片建议尺寸'

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getImgPath() {
		return imgPath;
	}

	public void setImgPath(java.lang.String imgPath) {
		this.imgPath = imgPath;
	}

	public java.lang.String getIconLink() {
		return iconLink;
	}

	public void setIconLink(java.lang.String iconLink) {
		this.iconLink = iconLink;
	}

	public java.lang.String getIconType() {
		return iconType;
	}

	public void setIconType(java.lang.String iconType) {
		this.iconType = iconType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public void setCreated(java.util.Date created) {
		this.created = created;
	}

	public java.util.Date getModified() {
		return modified;
	}

	public void setModified(java.util.Date modified) {
		this.modified = modified;
	}

	public java.lang.String getSugSize() {
		return sugSize;
	}

	public void setSugSize(java.lang.String sugSize) {
		this.sugSize = sugSize;
	}

}
