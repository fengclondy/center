package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;

public class OrderFeightPromotionDMO {

	// 优惠方式：1件数， 2金额
	private String preferentialWay;

	// 策略，1满减，2包邮
	private String strategy;

	// 满多少件/金额
	private BigDecimal full;

	// 减多少钱（分）
	private BigDecimal reduce;

	/**
	 * @return the preferentialWay
	 */
	public String getPreferentialWay() {
		return preferentialWay;
	}

	/**
	 * @param preferentialWay
	 *            the preferentialWay to set
	 */
	public void setPreferentialWay(String preferentialWay) {
		this.preferentialWay = preferentialWay;
	}

	/**
	 * @return the strategy
	 */
	public String getStrategy() {
		return strategy;
	}

	/**
	 * @param strategy
	 *            the strategy to set
	 */
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	/**
	 * @return the full
	 */
	public BigDecimal getFull() {
		return full;
	}

	/**
	 * @param full
	 *            the full to set
	 */
	public void setFull(BigDecimal full) {
		this.full = full;
	}

	/**
	 * @return the reduce
	 */
	public BigDecimal getReduce() {
		return reduce;
	}

	/**
	 * @param reduce
	 *            the reduce to set
	 */
	public void setReduce(BigDecimal reduce) {
		this.reduce = reduce;
	}

}
