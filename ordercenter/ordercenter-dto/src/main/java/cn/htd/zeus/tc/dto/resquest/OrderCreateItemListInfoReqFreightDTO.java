package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.math.BigDecimal;
/*
 * 计算运费专用属性
 */
public class OrderCreateItemListInfoReqFreightDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4050636661999041618L;

	private BigDecimal weight; // 毛重

	private BigDecimal netWeight; // 净重

	private BigDecimal length; // 长

	private BigDecimal width; // 宽

	private BigDecimal height; // 高

	private String weightUnit; // 单位

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
}
