package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
/**
 * @Purpose 楼层品牌DO
 * @author zf.zhang
 * @since 2017-3-11 17:22
 */
public class FloorBrandDO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9167346844847643251L;
	
	//主键
	private Long id;
	//楼层ID
	private Long floorId;
	//品牌id
	private Long brandId;
	//品牌名称
	private String brandName;
	// 品牌logo地址
	private String brandLogoUrl;
	//楼层内容品牌显示顺序
	private int sortNum;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFloorId() {
		return floorId;
	}
	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandLogoUrl() {
		return brandLogoUrl;
	}
	public void setBrandLogoUrl(String brandLogoUrl) {
		this.brandLogoUrl = brandLogoUrl;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	
	
}
