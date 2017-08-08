package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MallRecAttrInDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;// 主键ID
	private Integer sortNum;// 排序序号
	private Long itemId;// 商品编号
	private Long recId;// 推荐父id 即页签id
	private Integer recType;// 推荐类型 1,，表示商品 2，表示图片链接类型 3，表示广告词,4.左侧广告词 ,5顶部广告,6 底部广告
	private String picSrc;// 图片地址 recType为1 2 时必填
	private String url;// 图片链接 recType为1 2 时必填
	private String adWords;// 广告词 recType为3 4 5 6 时必填
	private String title;// 图片标题 recType为1 2 时必填
	private Integer timeFlag;// 时间标记 0：即时发布 1：定时发布
	private Date startTime;// 开始展示时间 定时发布时必填
	private Date endTime;// 展示结束时间
	private Integer status; // 状态 1 可用 0 不可用
	private String pictureCode;// 图片
	private String floorId; // 楼层ID
	private String floorName;// 楼层名称
	private String templateId;// 模板号

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Integer getStatus() {
		return status;
	}

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

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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
