package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.List;

import cn.htd.promotion.cpc.dto.request.VoteActivityMemReqDTO;

public class ImportVoteActivityMemResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3074711403918672857L;
	
	//成功个数
	private Integer successCount;
	//失败个数
	private Integer failCount;
	//唯一标志，用于下载失败列表
	private String uniqueId;
	
	private List<VoteActivityMemReqDTO> faillist;
	private List<VoteActivityMemReqDTO> alreadyExistsList;
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
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public List<VoteActivityMemReqDTO> getFaillist() {
		return faillist;
	}
	public void setFaillist(List<VoteActivityMemReqDTO> faillist) {
		this.faillist = faillist;
	}
	public List<VoteActivityMemReqDTO> getAlreadyExistsList() {
		return alreadyExistsList;
	}
	public void setAlreadyExistsList(List<VoteActivityMemReqDTO> alreadyExistsList) {
		this.alreadyExistsList = alreadyExistsList;
	}
}
