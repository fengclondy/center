package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MallRecAttrQueryDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer recType;// 推荐类型 1,，表示商品 2，表示图片链接类型 3，表示广告词,4.左侧广告词
	private Long recId;// 楼层ID
	private String title;// 广告词或者广告标题 recType：1，2 时，对应图片标题 recType：3，4 时，对应广告词
	private Integer timeFlag;// 时间标记 1 创建时间 2 发布时间 3结束时间
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private Integer status; // 楼层状态 1 生效 0 失效
	private Integer sortNumber;// 排序序号

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

	public Integer getRecType() {
		return recType;
	}

	public void setRecType(Integer recType) {
		this.recType = recType;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
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
}
