package cn.htd.goodscenter.dto.middleware.outdto;

import java.io.Serializable;

public class QueryItemStockOutDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8383386127907587369L;
	
	private String productCode;
	private String storeNum;
	private String supplierCode;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
}
