package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class HTDUserUpgradeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BuyerScoreIntervalDTO orderModel;

	private BuyerScoreIntervalDTO financeModel;

	private BuyerGradeIntervalDTO pointModel;

	private BigDecimal levelPoint;

	private Long userGrade;

	public BuyerScoreIntervalDTO getOrderModel() {
		return orderModel;
	}

	public void setOrderModel(BuyerScoreIntervalDTO orderModel) {
		this.orderModel = orderModel;
	}

	public BuyerScoreIntervalDTO getFinanceModel() {
		return financeModel;
	}

	public void setFinanceModel(BuyerScoreIntervalDTO financeModel) {
		this.financeModel = financeModel;
	}

	public BuyerGradeIntervalDTO getPointModel() {
		return pointModel;
	}

	public void setPointModel(BuyerGradeIntervalDTO pointModel) {
		this.pointModel = pointModel;
	}

	public BigDecimal getLevelPoint() {
		return levelPoint;
	}

	public void setLevelPoint(BigDecimal levelPoint) {
		this.levelPoint = levelPoint;
	}

	public Long getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(Long userGrade) {
		this.userGrade = userGrade;
	}
}
