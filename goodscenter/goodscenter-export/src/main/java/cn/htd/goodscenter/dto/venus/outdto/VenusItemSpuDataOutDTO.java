package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;

public class VenusItemSpuDataOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2449723781920906194L;
	
	private Long 	spuId;
	private String  spuCode;
	private String  itemName;
	private String  categoryName;
	private Long categoryId;
	private String  brandName;
	private String  serial;
	private String  color;
	private String  unit;
	private String  grossWeight;
	private String  netWeight;
	private String  length;
	private String  width;
	private String  height;
	private String erpCode;
	private String applyStatus;
	private String taxRate;
	
	public Long getSpuId() {
		return spuId;
	}
	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}
	public String getSpuCode() {
		return spuCode;
	}
	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}


	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
