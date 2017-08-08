package cn.htd.zeus.tc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemPaymentDTO implements Serializable {

	private static final long serialVersionUID = 3599420903806236752L;

	private String itemOrderNo;

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
