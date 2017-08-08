package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BuyerGradeInfoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long buyerId;

	private String buyerGrade;

	private Byte isVip;

	private Long pointGrade;

	private String isUpgrade;

	private String isSbUpgrade;

	private BigDecimal yearExp;

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

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade == null ? null : buyerGrade.trim();
	}

	public Byte getIsVip() {
		return isVip;
	}

	public void setIsVip(Byte isVip) {
		this.isVip = isVip;
	}

	public Long getPointGrade() {
		return pointGrade;
	}

	public void setPointGrade(Long pointGrade) {
		this.pointGrade = pointGrade;
	}

	public String getIsUpgrade() {
		return isUpgrade;
	}

	public void setIsUpgrade(String isUpgrade) {
		this.isUpgrade = isUpgrade == null ? null : isUpgrade.trim();
	}

	public String getIsSbUpgrade() {
		return isSbUpgrade;
	}

	public void setIsSbUpgrade(String isSbUpgrade) {
		this.isSbUpgrade = isSbUpgrade == null ? null : isSbUpgrade.trim();
	}

	public BigDecimal getYearExp() {
		return yearExp;
	}

	public void setYearExp(BigDecimal yearExp) {
		this.yearExp = yearExp;
	}

	public BigDecimal getMonthExp() {
		return monthExp;
	}

	public void setMonthExp(BigDecimal monthExp) {
		this.monthExp = monthExp;
	}

	public BigDecimal getLevelExp() {
		return levelExp;
	}

	public void setLevelExp(BigDecimal levelExp) {
		this.levelExp = levelExp;
	}

	public Long getYearOrderLevel() {
		return yearOrderLevel;
	}

	public void setYearOrderLevel(Long yearOrderLevel) {
		this.yearOrderLevel = yearOrderLevel;
	}

	public Long getYearFinanceLevel() {
		return yearFinanceLevel;
	}

	public void setYearFinanceLevel(Long yearFinanceLevel) {
		this.yearFinanceLevel = yearFinanceLevel;
	}

	public BigDecimal getYearOrderAmount() {
		return yearOrderAmount;
	}

	public void setYearOrderAmount(BigDecimal yearOrderAmount) {
		this.yearOrderAmount = yearOrderAmount;
	}

	public BigDecimal getYearFinanceAvg() {
		return yearFinanceAvg;
	}

	public void setYearFinanceAvg(BigDecimal yearFinanceAvg) {
		this.yearFinanceAvg = yearFinanceAvg;
	}

	public BigDecimal getMonthOrderAmount() {
		return monthOrderAmount;
	}

	public void setMonthOrderAmount(BigDecimal monthOrderAmount) {
		this.monthOrderAmount = monthOrderAmount;
	}

	public BigDecimal getMonthFinanceAvg() {
		return monthFinanceAvg;
	}

	public void setMonthFinanceAvg(BigDecimal monthFinanceAvg) {
		this.monthFinanceAvg = monthFinanceAvg;
	}

	public BigDecimal getMonthOrderExp() {
		return monthOrderExp;
	}

	public void setMonthOrderExp(BigDecimal monthOrderExp) {
		this.monthOrderExp = monthOrderExp;
	}

	public BigDecimal getMonthFinanceExp() {
		return monthFinanceExp;
	}

	public void setMonthFinanceExp(BigDecimal monthFinanceExp) {
		this.monthFinanceExp = monthFinanceExp;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName == null ? null : createName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName == null ? null : modifyName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}