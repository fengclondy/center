package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;

public class OrderItemPaymentDMO {

	private String itemOrderNo;

	private BigDecimal ratio;

	private BigDecimal amount;

	/**
	 * @return the itemOrderNo
	 */
	public String getItemOrderNo() {
		return itemOrderNo;
	}

	/**
	 * @param itemOrderNo
	 *            the itemOrderNo to set
	 */
	public void setItemOrderNo(String itemOrderNo) {
		this.itemOrderNo = itemOrderNo;
	}

	/**
	 * @return the ratio
	 */
	public BigDecimal getRatio() {
		return ratio;
	}

	/**
	 * @param ratio
	 *            the ratio to set
	 */
	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
