package com.bjucloud.contentcenter.enums;

/**
 *  广告位点击量对应的表
 */
public enum AdTableTypeEnums{
	advertise(""),recommendAttr("");
	
	private String label;
	AdTableTypeEnums(String label){
		this.label=label;
	}
	
	public String getLabel() {
		return label;
	}
}
