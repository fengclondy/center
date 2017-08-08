package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [描述该类概要功能介绍:mall_recommend 商城楼层推荐 DTO类]
 * </p>
 */
public class MallRecDTO implements Serializable {

	private static final long serialVersionUID = -463555445077786802L;
	private Long idDTO;// id
	private Integer floorNumDTO;// 楼层编号
	private String titleDTO;// title
	private Integer positionDTO;// 1:左，2：中，3：右
	private Integer recTypeDTO;// 1,网站首页 2：商品单页 3：类目
	private Date createdDTO;// 创建时间
	private Date modifiedDTO;// 修改时间
	private Integer statusDTO;// 显示状态 0表示不显示 1表示显示
	private Long categoryIdDTO;// 类目id
	private String smalltitleDTO;// 右侧短名称
	private String colorrefDTO; // 楼层色值
	private String bgColorDTO; // 楼层背景色
	private Integer themeId; // 主题id
	private String themeName; // 主题名称
	private Integer themeStatus; // 主题状态
	private Integer themeType;// 主题类型
	private String themeUId; // 创建者id
	private Long cid;// 主题类目
	private Long addressId;// 主题地区

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getThemeUId() {
		return themeUId;
	}

	public void setThemeUId(String themeUId) {
		this.themeUId = themeUId;
	}

	public Integer getThemeStatus() {
		return themeStatus;
	}

	public void setThemeStatus(Integer themeStatus) {
		this.themeStatus = themeStatus;
	}

	public Integer getThemeType() {
		return themeType;
	}

	public void setThemeType(Integer themeType) {
		this.themeType = themeType;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public String getBgColorDTO() {
		return bgColorDTO;
	}

	public void setBgColorDTO(String bgColorDTO) {
		this.bgColorDTO = bgColorDTO;
	}

	public String getColorrefDTO() {
		return colorrefDTO;
	}

	public void setColorrefDTO(String colorrefDTO) {
		this.colorrefDTO = colorrefDTO;
	}

	public Long getIdDTO() {
		return idDTO;
	}

	public void setIdDTO(Long idDTO) {
		this.idDTO = idDTO;
	}

	public Integer getFloorNumDTO() {
		return floorNumDTO;
	}

	public void setFloorNumDTO(Integer floorNumDTO) {
		this.floorNumDTO = floorNumDTO;
	}

	public String getTitleDTO() {
		return titleDTO;
	}

	public void setTitleDTO(String titleDTO) {
		this.titleDTO = titleDTO;
	}

	public Integer getPositionDTO() {
		return positionDTO;
	}

	public void setPositionDTO(Integer positionDTO) {
		this.positionDTO = positionDTO;
	}

	public Integer getRecTypeDTO() {
		return recTypeDTO;
	}

	public void setRecTypeDTO(Integer recTypeDTO) {
		this.recTypeDTO = recTypeDTO;
	}

	public Date getCreatedDTO() {
		return createdDTO;
	}

	public void setCreatedDTO(Date createdDTO) {
		this.createdDTO = createdDTO;
	}

	public Date getModifiedDTO() {
		return modifiedDTO;
	}

	public void setModifiedDTO(Date modifiedDTO) {
		this.modifiedDTO = modifiedDTO;
	}

	public Integer getStatusDTO() {
		return statusDTO;
	}

	public void setStatusDTO(Integer statusDTO) {
		this.statusDTO = statusDTO;
	}

	public Long getCategoryIdDTO() {
		return categoryIdDTO;
	}

	public void setCategoryIdDTO(Long categoryIdDTO) {
		this.categoryIdDTO = categoryIdDTO;
	}

	public String getSmalltitleDTO() {
		return smalltitleDTO;
	}

	public void setSmalltitleDTO(String smalltitleDTO) {
		this.smalltitleDTO = smalltitleDTO;
	}
}
