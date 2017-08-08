package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MallIconSerDTO implements Serializable {

	private static final long serialVersionUID = 353760922922108156L;
	private Integer id;// id
	private String title;// '名称'
	private String imgPath;// '图片保存路径'
	private String iconLink;// '指向链接'
	private String iconType;// '图片类型，所属区域。1,首推滚动右侧小图标 2，底部服务'
	private Integer status; // '启用状态。0，不启用；1，启用'
	private Integer sortNum; // '排序顺序'
	private Date created;// '创建时间'
	private Date modified;// '最后修改时间'
	private String sugSize; // '图片建议尺寸'

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getIconLink() {
		return iconLink;
	}

	public void setIconLink(String iconLink) {
		this.iconLink = iconLink;
	}

	public String getIconType() {
		return iconType;
	}

	public void setIconType(String iconType) {
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

	public String getSugSize() {
		return sugSize;
	}

	public void setSugSize(String sugSize) {
		this.sugSize = sugSize;
	}
}
