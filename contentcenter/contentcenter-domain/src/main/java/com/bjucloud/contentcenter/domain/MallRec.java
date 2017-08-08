package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [ 商城楼层推荐 domain类]
 * </p>
 */
public class MallRec implements Serializable {
	private static final long serialVersionUID = -463555445077786802L;

	private Long id;// id

	private Integer floorNum;// 楼层编号

	private String title;// title

	private Integer position;// 1:左，2：中，3：右

	private Integer recType;// 1,网站首页 2：商品单页 3：类目

	private Date created;// 创建时间

	private Date modified;// 修改时间

	private Integer status;// 显示状态 2表示不显示 1表示显示

	private Long categoryId;// 类目id

	private String smalltitle;// 右侧短名称

	private String colorref;// 色值，楼层颜色

	private String bgColor;// 色值，楼层背景色

	private Long themeId; // 主题id

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getColorref() {
		return colorref;
	}

	public void setColorref(String colorref) {
		this.colorref = colorref;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Integer getFloorNum() {
		return this.floorNum;
	}

	public void setFloorNum(Integer value) {
		this.floorNum = value;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer value) {
		this.position = value;
	}

	public Integer getRecType() {
		return this.recType;
	}

	public void setRecType(Integer value) {
		this.recType = value;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date value) {
		this.created = value;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date value) {
		this.modified = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long value) {
		this.categoryId = value;
	}

	public String getSmalltitle() {
		return this.smalltitle;
	}

	public void setSmalltitle(String value) {
		this.smalltitle = value;
	}

}
