package cn.htd.goodscenter.dto.listener;

import java.io.Serializable;

public class Erp2MiddleItemInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6931288094592215407L;
	//中台商品主数据编号
	private String productCode;
	//商品名称
	private String productName;
	//商品型号
	private String productModel;
	//商品单位
	private String productUnit;
	//中台品牌编号
	private String brandCode;
	/**
	 * ERP一级品类编号
	 */
	private String firstCategreyCode;
	/**
	 * ERP五级品类编号
	 */
	private String fiveCategreyCode;
	/**
	 * 中台三级品类编号
	 */
	private String middleGroundThirdCategoryCode;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getProductModel() {
		return productModel;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public String getFirstCategreyCode() {
		return firstCategreyCode;
	}
	public void setFirstCategreyCode(String firstCategreyCode) {
		this.firstCategreyCode = firstCategreyCode;
	}
	public String getFiveCategreyCode() {
		return fiveCategreyCode;
	}
	public void setFiveCategreyCode(String fiveCategreyCode) {
		this.fiveCategreyCode = fiveCategreyCode;
	}
	public String getMiddleGroundThirdCategoryCode() {
		return middleGroundThirdCategoryCode;
	}
	public void setMiddleGroundThirdCategoryCode(
			String middleGroundThirdCategoryCode) {
		this.middleGroundThirdCategoryCode = middleGroundThirdCategoryCode;
	}

}
