package cn.htd.marketcenter.domain;

import java.util.List;

public class CouponSearchCondition {

	private String rewardType;

	private int dealFlag;

	private List<String> couponProvideTypeList;

	private int taskQueueNum;

	private List<String> taskIdList;

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public int getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(int dealFlag) {
		this.dealFlag = dealFlag;
	}

	public List<String> getCouponProvideTypeList() {
		return couponProvideTypeList;
	}

	public void setCouponProvideTypeList(List<String> couponProvideTypeList) {
		this.couponProvideTypeList = couponProvideTypeList;
	}

	public int getTaskQueueNum() {
		return taskQueueNum;
	}

	public void setTaskQueueNum(int taskQueueNum) {
		this.taskQueueNum = taskQueueNum;
	}

	public List<String> getTaskIdList() {
		return taskIdList;
	}

	public void setTaskIdList(List<String> taskIdList) {
		this.taskIdList = taskIdList;
	}
}