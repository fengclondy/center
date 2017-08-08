package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class HTDUserUpgradeDistanceDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal orderAmountDistance;

	private BigDecimal financeAmountDistance;

	private BuyerGradeIntervalDTO nextUserGradePointModel;

	private Long nextUserGrade;

	public BigDecimal getOrderAmountDistance() {
		return orderAmountDistance;
	}

	public void setOrderAmountDistance(BigDecimal orderAmountDistance) {
		this.orderAmountDistance = orderAmountDistance;
	}

	public BigDecimal getFinanceAmountDistance() {
		return financeAmountDistance;
	}

	public void setFinanceAmountDistance(BigDecimal financeAmountDistance) {
		this.financeAmountDistance = financeAmountDistance;
	}

	public Long getNextUserGrade() {
		return nextUserGrade;
	}

	public void setNextUserGrade(Long nextUserGrade) {
		this.nextUserGrade = nextUserGrade;
	}

	public BuyerGradeIntervalDTO getNextUserGradePointModel() {
		return nextUserGradePointModel;
	}

	public void setNextUserGradePointModel(BuyerGradeIntervalDTO nextUserGradePointModel) {
		this.nextUserGradePointModel = nextUserGradePointModel;
	}

}
