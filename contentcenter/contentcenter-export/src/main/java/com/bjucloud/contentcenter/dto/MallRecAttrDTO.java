package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [商品推荐]
 * </p>
 */
public class MallRecAttrDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long platformId;// 平台id
	private Integer source;// 1:选择的商品 2:类目中查询的商品
	private Date modified;// 修改时间
	private Integer timeFlag;// 时间标记 1 创建时间 2 发布时间 3结束时间

	private Long id;// 主键ID
	private Integer sortNum;// 排序序号
	private Long itemId;// 商品编号
	private Long recId;// 推荐父id 即页签id
	private String recName;
	private String floorId; // 楼层ID
	private String floorName;// 楼层名称
	private Date created;// 创建时间
	private Date startTime;// 修改时间
	private Date endTime;// 结束时间
	private Integer status;// 显示状态 1表示显示 2表示不显示
	private Integer recType;// 推荐类型 1,，表示商品 2，表示图片链接类型 3，表示广告词,4.左侧广告词
	private String picSrc;// 图片地址
	private String url;// 图片链接
	private String adWords;// 广告词
	private String title;// 图片标题
	private String pictureCode;// 图片

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRecType() {
		return recType;
	}

	public void setRecType(Integer recType) {
		this.recType = recType;
	}

	public String getPicSrc() {
		return picSrc;
	}

	public void setPicSrc(String picSrc) {
		this.picSrc = picSrc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAdWords() {
		return adWords;
	}

	public void setAdWords(String adWords) {
		this.adWords = adWords;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPictureCode() {
		return pictureCode;
	}

	public void setPictureCode(String pictureCode) {
		this.pictureCode = pictureCode;
	}

}
