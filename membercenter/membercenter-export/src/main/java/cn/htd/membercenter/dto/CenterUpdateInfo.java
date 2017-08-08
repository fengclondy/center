package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

public class CenterUpdateInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer successCount;// 成功条数
	private Integer failCount;// 失败条数
	private List<FailDetailInfo> failInfoList;// 失败详细信息集合

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public List<FailDetailInfo> getFailInfoList() {
		return failInfoList;
	}

	public void setFailInfoList(List<FailDetailInfo> failInfoList) {
		this.failInfoList = failInfoList;
	}

}
