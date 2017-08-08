package cn.htd.goodscenter.dto.venus.indto;

import java.io.Serializable;
import java.util.List;

public class VenusBatchDeleteItemSkuInDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 702287252005996746L;
	private List<Long> skuIdList;
	private Long operatorId;
	private String operatorName;
	public List<Long> getSkuIdList() {
		return skuIdList;
	}
	public void setSkuIdList(List<Long> skuIdList) {
		this.skuIdList = skuIdList;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	
}
