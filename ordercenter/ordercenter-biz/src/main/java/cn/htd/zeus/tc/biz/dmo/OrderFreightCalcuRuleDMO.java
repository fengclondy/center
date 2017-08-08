package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;

public class OrderFreightCalcuRuleDMO {

	// 配送方式，1快递，2EMS，3平邮
	private String deliveryType;

	// 首件/首重量/首体积
	private BigDecimal firstPart;

	// 首费
	private BigDecimal firstPrice;

	// 续件/续重量/续体积
	private BigDecimal continuePart;

	// 续费
	private BigDecimal continuePrice;

	// 配送至城市编码
	private String deliveryTo;

	// 配送至城市名称
	private String deliveryAddress;

	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType
	 *            the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the firstPart
	 */
	public BigDecimal getFirstPart() {
		return firstPart;
	}

	/**
	 * @param firstPart
	 *            the firstPart to set
	 */
	public void setFirstPart(BigDecimal firstPart) {
		this.firstPart = firstPart;
	}

	/**
	 * @return the firstPrice
	 */
	public BigDecimal getFirstPrice() {
		return firstPrice;
	}

	/**
	 * @param firstPrice
	 *            the firstPrice to set
	 */
	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}

	/**
	 * @return the continuePart
	 */
	public BigDecimal getContinuePart() {
		return continuePart;
	}

	/**
	 * @param continuePart
	 *            the continuePart to set
	 */
	public void setContinuePart(BigDecimal continuePart) {
		this.continuePart = continuePart;
	}

	/**
	 * @return the continuePrice
	 */
	public BigDecimal getContinuePrice() {
		return continuePrice;
	}

	/**
	 * @param continuePrice
	 *            the continuePrice to set
	 */
	public void setContinuePrice(BigDecimal continuePrice) {
		this.continuePrice = continuePrice;
	}

	/**
	 * @return the deliveryTo
	 */
	public String getDeliveryTo() {
		return deliveryTo;
	}

	/**
	 * @param deliveryTo
	 *            the deliveryTo to set
	 */
	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}

	/**
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * @param deliveryAddress
	 *            the deliveryAddress to set
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

}
