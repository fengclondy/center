package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MobileMallCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String pictrueUrl;

	List<MobileMallCategory> childCategory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPictrueUrl() {
		return pictrueUrl;
	}

	public void setPictrueUrl(String pictrueUrl) {
		this.pictrueUrl = pictrueUrl;
	}

	public List<MobileMallCategory> getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(List<MobileMallCategory> childCategory) {
		this.childCategory = childCategory;
	}
}