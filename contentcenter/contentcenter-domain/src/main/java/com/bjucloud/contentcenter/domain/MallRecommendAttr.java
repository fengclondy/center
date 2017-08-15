package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: MallRecommendAttr domain类
 * </p>
 */
public class MallRecommendAttr implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//
	private Long itemId;// 商品编号
	private Long platformId;// 平台id
	private Integer source;// 1:选择的商品 2:类目中查询的商品
	private Long recId;// 推荐父id 即页签id
	private Date startTime;// 开始显示时间
	private Date endTime;// 结束显示时间
	private Integer recType;// 推荐类型 1,，表示商品 2，表示图片链接类型 3，表示广告词,4.左侧广告词
	private String picSrc;// 图片地址
	private String url;// 图片链接
	private String adWords;// 广告词
	private String title;// 图片标题
	private Integer sortNumber;// 排序序号
	private Integer status;// 显示状态 0：不显示 1：显示
	private Date created;// 创建时间
	private Date modified;// 修改时间
	private Integer timeFlag;// 时间标记 1 创建时间 2 发布时间 3结束时间
	private String pictureCode;// 图片

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
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

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Integer getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String getPictureCode() {
		return pictureCode;
	}

	public void setPictureCode(String pictureCode) {
		this.pictureCode = pictureCode;
	}

}
