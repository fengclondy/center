package cn.htd.marketcenter.domain;

import java.util.List;

public class BuyerCouponCondition {

	private String status;

	private int taskQueueNum;

	private List<String> taskIdList;

	private String cleanRedisInterval;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCleanRedisInterval() {
		return cleanRedisInterval;
	}

	public void setCleanRedisInterval(String cleanRedisInterval) {
		this.cleanRedisInterval = cleanRedisInterval;
	}
}