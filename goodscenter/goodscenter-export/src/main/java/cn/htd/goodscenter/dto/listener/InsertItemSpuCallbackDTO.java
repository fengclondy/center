package cn.htd.goodscenter.dto.listener;

import java.io.Serializable;

public class InsertItemSpuCallbackDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1741784269845362334L;
	private String productCode;
	private String erpProductCode;
	private boolean flag;
	private String token;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getErpProductCode() {
		return erpProductCode;
	}
	public void setErpProductCode(String erpProductCode) {
		this.erpProductCode = erpProductCode;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
