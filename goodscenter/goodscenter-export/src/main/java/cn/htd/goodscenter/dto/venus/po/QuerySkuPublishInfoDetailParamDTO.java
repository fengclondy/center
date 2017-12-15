package cn.htd.goodscenter.dto.venus.po;

import java.io.Serializable;

public class QuerySkuPublishInfoDetailParamDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2549506683240462129L;
	private Long skuId;
	//上架类型 1 包厢 2 区域
	private String shelfType;
	//供应商编码
	private String supplierCode;
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getShelfType() {
		return shelfType;
	}
	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	private Boolean isNewVms;

	public Boolean getNewVms() {
		return isNewVms;
	}

	public void setNewVms(Boolean newVms) {
		isNewVms = newVms;
	}
}
