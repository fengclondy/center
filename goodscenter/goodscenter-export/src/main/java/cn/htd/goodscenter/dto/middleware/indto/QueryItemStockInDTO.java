package cn.htd.goodscenter.dto.middleware.indto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class QueryItemStockInDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6248676840436306362L;
	private String token;
	private List<Map<String,Object>> data;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
}
