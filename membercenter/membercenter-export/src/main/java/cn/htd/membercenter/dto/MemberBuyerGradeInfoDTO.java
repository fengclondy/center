package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MemberBuyerGradeInfoDTO
 * </p>
 * 
 * @author root
 * @date 2016年12月26日
 *       <p>
 *       Description: 买家中心 等级详细信息
 *       </p>
 */
public class MemberBuyerGradeInfoDTO implements Serializable {

	private static final long serialVersionUID = 1812727224011573263L;

	private Long memberId;
	private String buyerGrade;// '会员店等级(1:一星会员,2:二星会员,3:三星会员,4:四星会员,5:五星会员,6:VIP会员)'
	private Long pointGrade;// 经验会员等级 计算使用
	private BigDecimal yearExp;// 年度经验值

	private Byte isVip;

	private String isUpgrade;

	private String isSbUpgrade;

	private BigDecimal monthExp;

	private BigDecimal levelExp;

	private Long yearOrderLevel;

	private Long yearFinanceLevel;

	private BigDecimal yearOrderAmount;

	private BigDecimal yearFinanceAvg;

	private BigDecimal monthOrderAmount;

	private BigDecimal monthFinanceAvg;

	private BigDecimal monthOrderExp;

	private BigDecimal monthFinanceExp;

	private List<MemberBuyerGradeMatrixDTO> pathList;// 会员升级路径 两个路径最短的

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	public Long getPointGrade() {
		return pointGrade;
	}

	public void setPointGrade(Long pointGrade) {
		this.pointGrade = pointGrade;
	}

	public BigDecimal getYearExp() {
		return yearExp;
	}

	public void setYearExp(BigDecimal yearExp) {
		this.yearExp = yearExp;
	}

	public List<MemberBuyerGradeMatrixDTO> getPathList() {
		return pathList;
	}

	public void setPathList(List<MemberBuyerGradeMatrixDTO> pathList) {
		this.pathList = pathList;
	}

	/**
	 * @return the isVip
	 */
	public Byte getIsVip() {
		return isVip;
	}

	/**
	 * @param isVip
	 *            the isVip to set
	 */
	public void setIsVip(Byte isVip) {
		this.isVip = isVip;
	}

	/**
	 * @return the isUpgrade
	 */
	public String getIsUpgrade() {
		return isUpgrade;
	}

	/**
	 * @param isUpgrade
	 *            the isUpgrade to set
	 */
	public void setIsUpgrade(String isUpgrade) {
		this.isUpgrade = isUpgrade;
	}

	/**
	 * @return the isSbUpgrade
	 */
	public String getIsSbUpgrade() {
		return isSbUpgrade;
	}

	/**
	 * @param isSbUpgrade
	 *            the isSbUpgrade to set
	 */
	public void setIsSbUpgrade(String isSbUpgrade) {
		this.isSbUpgrade = isSbUpgrade;
	}

	/**
	 * @return the monthExp
	 */
	public BigDecimal getMonthExp() {
		return monthExp;
	}

	/**
	 * @param monthExp
	 *            the monthExp to set
	 */
	public void setMonthExp(BigDecimal monthExp) {
		this.monthExp = monthExp;
	}

	/**
	 * @return the levelExp
	 */
	public BigDecimal getLevelExp() {
		return levelExp;
	}

	/**
	 * @param levelExp
	 *            the levelExp to set
	 */
	public void setLevelExp(BigDecimal levelExp) {
		this.levelExp = levelExp;
	}

	/**
	 * @return the yearOrderLevel
	 */
	public Long getYearOrderLevel() {
		return yearOrderLevel;
	}

	/**
	 * @param yearOrderLevel
	 *            the yearOrderLevel to set
	 */
	public void setYearOrderLevel(Long yearOrderLevel) {
		this.yearOrderLevel = yearOrderLevel;
	}

	/**
	 * @return the yearFinanceLevel
	 */
	public Long getYearFinanceLevel() {
		return yearFinanceLevel;
	}

	/**
	 * @param yearFinanceLevel
	 *            the yearFinanceLevel to set
	 */
	public void setYearFinanceLevel(Long yearFinanceLevel) {
		this.yearFinanceLevel = yearFinanceLevel;
	}

	/**
	 * @return the yearOrderAmount
	 */
	public BigDecimal getYearOrderAmount() {
		return yearOrderAmount;
	}

	/**
	 * @param yearOrderAmount
	 *            the yearOrderAmount to set
	 */
	public void setYearOrderAmount(BigDecimal yearOrderAmount) {
		this.yearOrderAmount = yearOrderAmount;
	}

	/**
	 * @return the yearFinanceAvg
	 */
	public BigDecimal getYearFinanceAvg() {
		return yearFinanceAvg;
	}

	/**
	 * @param yearFinanceAvg
	 *            the yearFinanceAvg to set
	 */
	public void setYearFinanceAvg(BigDecimal yearFinanceAvg) {
		this.yearFinanceAvg = yearFinanceAvg;
	}

	/**
	 * @return the monthOrderAmount
	 */
	public BigDecimal getMonthOrderAmount() {
		return monthOrderAmount;
	}

	/**
	 * @param monthOrderAmount
	 *            the monthOrderAmount to set
	 */
	public void setMonthOrderAmount(BigDecimal monthOrderAmount) {
		this.monthOrderAmount = monthOrderAmount;
	}

	/**
	 * @return the monthFinanceAvg
	 */
	public BigDecimal getMonthFinanceAvg() {
		return monthFinanceAvg;
	}

	/**
	 * @param monthFinanceAvg
	 *            the monthFinanceAvg to set
	 */
	public void setMonthFinanceAvg(BigDecimal monthFinanceAvg) {
		this.monthFinanceAvg = monthFinanceAvg;
	}

	/**
	 * @return the monthOrderExp
	 */
	public BigDecimal getMonthOrderExp() {
		return monthOrderExp;
	}

	/**
	 * @param monthOrderExp
	 *            the monthOrderExp to set
	 */
	public void setMonthOrderExp(BigDecimal monthOrderExp) {
		this.monthOrderExp = monthOrderExp;
	}

	/**
	 * @return the monthFinanceExp
	 */
	public BigDecimal getMonthFinanceExp() {
		return monthFinanceExp;
	}

	/**
	 * @param monthFinanceExp
	 *            the monthFinanceExp to set
	 */
	public void setMonthFinanceExp(BigDecimal monthFinanceExp) {
		this.monthFinanceExp = monthFinanceExp;
	}

}
