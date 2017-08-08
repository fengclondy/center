package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

public class LogoDTO implements Serializable {
	private static final long serialVersionUID = 2990627565835351070L;
	private String logoName;
	private String picUrl;

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
