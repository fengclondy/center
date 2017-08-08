package cn.htd.common.middleware;

import java.io.Serializable;

public class ItemStockResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1184419767877589497L;
	private String productCode;
	private Integer storeNum;
	private String supplierCode;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Integer getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(Integer storeNum) {
		this.storeNum = storeNum;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	

}
