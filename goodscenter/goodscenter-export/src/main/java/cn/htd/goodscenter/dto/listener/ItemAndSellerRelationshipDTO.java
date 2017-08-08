package cn.htd.goodscenter.dto.listener;

import java.io.Serializable;

public class ItemAndSellerRelationshipDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7534157667745426592L;
	private String productCode;
	private String supplierCode;
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
	
}
