package cn.htd.goodscenter.dto.middleware.indto;

import java.io.Serializable;

public class QueryItemWarehouseInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1716407780501203310L;
	private String token;
	private String supplierCode;
	private String productCode;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
}
