package cn.htd.zeus.tc.dto;

import java.io.Serializable;

public class TimelimitedInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960267160224504545L;

	/**
	 * 层级编码
	 */
	private String levelCode;

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
}
