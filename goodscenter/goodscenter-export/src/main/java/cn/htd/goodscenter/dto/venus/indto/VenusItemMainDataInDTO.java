package cn.htd.goodscenter.dto.venus.indto;

import java.io.Serializable;

import cn.htd.goodscenter.dto.common.AbstractPagerDTO;

public class VenusItemMainDataInDTO extends AbstractPagerDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7315635421538070592L;
	private String productName;
	private String brandName;
	private String categoryName;
	//型号
	private String modelType;
	//供应商id 必填
	private Long sellerId;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	
	
}
