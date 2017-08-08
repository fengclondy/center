package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.List;

public class MallTypeSub implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long typeId;

	private String type1Name;

	private String type1;

	private String type2Name;

	private String type2;

	private String type3Name;

	private String type3;

	private String name;

	private String nameColor;

	private Long sortNum;

	private String status;

	private List<MallTypeSub> mallTypeSubList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getType1Name() {
		return type1Name;
	}

	public void setType1Name(String type1Name) {
		this.type1Name = type1Name == null ? null : type1Name.trim();
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1 == null ? null : type1.trim();
	}

	public String getType2Name() {
		return type2Name;
	}

	public void setType2Name(String type2Name) {
		this.type2Name = type2Name == null ? null : type2Name.trim();
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2 == null ? null : type2.trim();
	}

	public String getType3Name() {
		return type3Name;
	}

	public void setType3Name(String type3Name) {
		this.type3Name = type3Name == null ? null : type3Name.trim();
	}

	public String getType3() {
		return type3;
	}

	public void setType3(String type3) {
		this.type3 = type3 == null ? null : type3.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getNameColor() {
		return nameColor;
	}

	public void setNameColor(String nameColor) {
		this.nameColor = nameColor == null ? null : nameColor.trim();
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * @return the mallTypeSubList
	 */
	public List<MallTypeSub> getMallTypeSubList() {
		return mallTypeSubList;
	}

	/**
	 * @param mallTypeSubList
	 *            the mallTypeSubList to set
	 */
	public void setMallTypeSubList(List<MallTypeSub> mallTypeSubList) {
		this.mallTypeSubList = mallTypeSubList;
	}
}