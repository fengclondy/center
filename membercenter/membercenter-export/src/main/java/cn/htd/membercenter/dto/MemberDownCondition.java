package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MemberDownCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7255465891478854894L;
	private Integer taskQueueNum;
	private Date lastDate;
	private List<String> taskIdList;

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * @return the taskQueueNum
	 */
	public Integer getTaskQueueNum() {
		return taskQueueNum;
	}

	/**
	 * @param taskQueueNum the taskQueueNum to set
	 */
	public void setTaskQueueNum(Integer taskQueueNum) {
		this.taskQueueNum = taskQueueNum;
	}

	/**
	 * @return the taskIdList
	 */
	public List<String> getTaskIdList() {
		return taskIdList;
	}

	/**
	 * @param taskIdList the taskIdList to set
	 */
	public void setTaskIdList(List<String> taskIdList) {
		this.taskIdList = taskIdList;
	}

}
