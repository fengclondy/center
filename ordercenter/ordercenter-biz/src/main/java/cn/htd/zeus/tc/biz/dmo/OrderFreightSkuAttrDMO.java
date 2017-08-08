package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;

public class OrderFreightSkuAttrDMO {

	private String skuCode;

	private long templateId;

	private BigDecimal weight; // 毛重

	private BigDecimal netWeight; // 净重

	private BigDecimal length; // 长

	private BigDecimal width; // 宽

	private BigDecimal height; // 高

	private String weightUnit; // 单位

	private int productCount;

	private BigDecimal price;

	private BigDecimal freight; // 运费

	/**
	 * @return the skuCode
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * @param skuCode
	 *            the skuCode to set
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * @return the templateId
	 */
	public long getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return the netWeight
	 */
	public BigDecimal getNetWeight() {
		return netWeight;
	}

	/**
	 * @param netWeight
	 *            the netWeight to set
	 */
	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * @return the length
	 */
	public BigDecimal getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(BigDecimal length) {
		this.length = length;
	}

	/**
	 * @return the width
	 */
	public BigDecimal getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * @return the weightUnit
	 */
	public String getWeightUnit() {
		return weightUnit;
	}

	/**
	 * @param weightUnit
	 *            the weightUnit to set
	 */
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	/**
	 * @return the productCount
	 */
	public int getProductCount() {
		return productCount;
	}

	/**
	 * @param productCount
	 *            the productCount to set
	 */
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the freight
	 */
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * @param freight
	 *            the freight to set
	 */
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

}
