package cn.htd.goodscenter.dto.productplus;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ProductPlusAccessInfoOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8185496257042506275L;
	
	private Map<String,List<String>> data;
	
	private boolean isSuccess;
	private Integer totalPage;
	private String errorMsg;
	public Map<String, List<String>> getData() {
		return data;
	}
	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
