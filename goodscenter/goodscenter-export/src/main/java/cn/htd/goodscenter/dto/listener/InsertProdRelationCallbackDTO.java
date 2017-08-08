package cn.htd.goodscenter.dto.listener;

import java.io.Serializable;

public class InsertProdRelationCallbackDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5322511790988569117L;
	private String productCode;
	private String supplierCode;
	private int result;
	private String errormessage;
	private String token;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
