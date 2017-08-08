package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [积分兑换商品]
 * </p>
 */
public class CreditsExchangeDTO implements Serializable {
	private static final long serialVersionUID = -316329548553235474L;
	private Long id;

	// id不等于
	private Long notIsId;

	// spuid不等于
	private Long notIsSpuId;

	private Long sortNum;// 排列顺序

	private String title;// 积分兑换标题

	private Long pointNum;// 积分数

	private Long itemType;// 积分兑换商品类型，1：推荐位商品，2：猜你喜欢

	private Long spuId;// spu

	private Long status;// 是否上下架 1：上架，2：下架

	private Date createTime;

	private Date updateTime;

	private Date startTime;

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

	private Date endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPointNum() {
		return pointNum;
	}

	public void setPointNum(Long pointNum) {
		this.pointNum = pointNum;
	}

	public Long getItemType() {
		return itemType;
	}

	public void setItemType(Long itemType) {
		this.itemType = itemType;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getNotIsId() {
		return notIsId;
	}

	public void setNotIsId(Long notIsId) {
		this.notIsId = notIsId;
	}

	public Long getNotIsSpuId() {
		return notIsSpuId;
	}

	public void setNotIsSpuId(Long notIsSpuId) {
		this.notIsSpuId = notIsSpuId;
	}
}
