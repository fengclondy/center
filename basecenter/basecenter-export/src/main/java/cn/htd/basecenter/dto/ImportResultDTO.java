package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.List;

public class ImportResultDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1562803490071852118L;

	private Integer successCount;// 成功条数
	
	private Integer failCount;// 失败条数
	
	private List<TransactionRelationDTO> transactionRelationList;
	
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
	public List<TransactionRelationDTO> getTransactionRelationList() {
		return transactionRelationList;
	}
	public void setTransactionRelationList(List<TransactionRelationDTO> transactionRelationList) {
		this.transactionRelationList = transactionRelationList;
	}

}
