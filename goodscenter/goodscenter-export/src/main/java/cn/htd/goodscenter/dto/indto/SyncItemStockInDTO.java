package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SyncItemStockInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5332324737836060624L;
	//productCode
	//supplierCode
	private List<Map<String,Object>> data;
	private Long operatorId;
	private String operatorName;
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
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	

}
