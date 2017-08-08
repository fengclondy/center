package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

public class MallTypeDTO implements Serializable {
	private static final long serialVersionUID = 2990627565835351070L;

	private Integer mallClassifyId;// 文档分类Id
	private String mallClassifyTitle;// 文档分类标题
	private Integer mallType;// 文档分类类型

	public Integer getMallClassifyId() {
		return mallClassifyId;
	}

	public void setMallClassifyId(Integer mallClassifyId) {
		this.mallClassifyId = mallClassifyId;
	}

	public String getMallClassifyTitle() {
		return mallClassifyTitle;
	}

	public void setMallClassifyTitle(String mallClassifyTitle) {
		this.mallClassifyTitle = mallClassifyTitle;
	}

	public Integer getMallType() {
		return mallType;
	}

	public void setMallType(Integer mallType) {
		this.mallType = mallType;
	}
}
