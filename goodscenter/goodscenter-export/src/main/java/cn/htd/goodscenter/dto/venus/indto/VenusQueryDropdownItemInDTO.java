package cn.htd.goodscenter.dto.venus.indto;

import java.io.Serializable;

public class VenusQueryDropdownItemInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1584673826703085045L;
	private Long sellerId;
	private Long catId;
	private Long brandId;
	private Long skuId;
	//供应商编码
	private String supplierCode;
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
}
