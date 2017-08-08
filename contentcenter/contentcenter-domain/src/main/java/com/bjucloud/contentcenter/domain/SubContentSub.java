package com.bjucloud.contentcenter.domain;

import java.io.Serializable;

public class SubContentSub implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long subContentId;

	private String sellerName;

	private String sellerCode;

	private Long sortNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSubContentId() {
		return subContentId;
	}

	public void setSubContentId(Long subContentId) {
		this.subContentId = subContentId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName == null ? null : sellerName.trim();
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode == null ? null : sellerCode.trim();
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}
}