package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class HTDUserGradeExpDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal realPoint;

	private BigDecimal monthRealPoint;

	private BigDecimal levelPoint;

	private BuyerScoreIntervalDTO financeModel;

	private BuyerScoreIntervalDTO orderModel;

	private BuyerScoreIntervalDTO financeMonthModel;

	private BuyerScoreIntervalDTO orderMonthModel;

	/**
	 * @return the financeMonthModel
	 */
	public BuyerScoreIntervalDTO getFinanceMonthModel() {
		return financeMonthModel;
	}

	/**
	 * @param financeMonthModel
	 *            the financeMonthModel to set
	 */
	public void setFinanceMonthModel(BuyerScoreIntervalDTO financeMonthModel) {
		this.financeMonthModel = financeMonthModel;
	}

	/**
	 * @return the orderMonthModel
	 */
	public BuyerScoreIntervalDTO getOrderMonthModel() {
		return orderMonthModel;
	}

	/**
	 * @param orderMonthModel
	 *            the orderMonthModel to set
	 */
	public void setOrderMonthModel(BuyerScoreIntervalDTO orderMonthModel) {
		this.orderMonthModel = orderMonthModel;
	}

	/**
	 * @return the orderModel
	 */
	public BuyerScoreIntervalDTO getOrderModel() {
		return orderModel;
	}

	/**
	 * @param orderModel
	 *            the orderModel to set
	 */
	public void setOrderModel(BuyerScoreIntervalDTO orderModel) {
		this.orderModel = orderModel;
	}

	/**
	 * @return the financeModel
	 */
	public BuyerScoreIntervalDTO getFinanceModel() {
		return financeModel;
	}

	/**
	 * @param financeModel
	 *            the financeModel to set
	 */
	public void setFinanceModel(BuyerScoreIntervalDTO financeModel) {
		this.financeModel = financeModel;
	}

	public BigDecimal getRealPoint() {
		return realPoint;
	}

	public void setRealPoint(BigDecimal realPoint) {
		this.realPoint = realPoint;
	}

	public BigDecimal getMonthRealPoint() {
		return monthRealPoint;
	}

	public void setMonthRealPoint(BigDecimal monthRealPoint) {
		this.monthRealPoint = monthRealPoint;
	}

	public BigDecimal getLevelPoint() {
		return levelPoint;
	}

	public void setLevelPoint(BigDecimal levelPoint) {
		this.levelPoint = levelPoint;
	}

}
