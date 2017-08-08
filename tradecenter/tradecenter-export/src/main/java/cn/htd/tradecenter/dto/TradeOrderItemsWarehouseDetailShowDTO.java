package cn.htd.tradecenter.dto;

import java.io.Serializable;

public class TradeOrderItemsWarehouseDetailShowDTO extends TradeOrderItemsWarehouseDetailDTO implements Serializable {

	private static final long serialVersionUID = -7524390301092658130L;

	private String wareHouseName;

	private String productAttributeName;

	private String departmentName;

	private Integer storeNum;

	private String rebateAmount;

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public String getProductAttributeName() {
		return productAttributeName;
	}

	public void setProductAttributeName(String productAttributeName) {
		this.productAttributeName = productAttributeName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(Integer storeNum) {
		this.storeNum = storeNum;
	}

	public String getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(String rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

}