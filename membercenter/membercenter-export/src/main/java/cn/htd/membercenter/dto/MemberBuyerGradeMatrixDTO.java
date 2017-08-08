package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberBuyerGradeMatrixDTO</p>
* @author root
* @date 2016年12月30日
* <p>Description: 
*			买家中心   等级矩阵
* </p>
 */
public class MemberBuyerGradeMatrixDTO implements Serializable{

	private static final long serialVersionUID = -7079128179586979539L;
	
	private BigDecimal mallDistance;//商城消费差距  单位：万
	private BigDecimal financeDistance;//金融产品差距   单位：万
	
	private Long buyerLevel;//会员等级ID
	private Long fromScore;//区间下限
	private Long toScore;//区间上限
	private Long lowestPoint;//保底经验值
	
	private String intervalType;//区间类型 1:商城交易，2:金融产品
	private BigDecimal fromAmount;//金额区间下限
	private BigDecimal toAmount;//金额区间上限
    private BigDecimal score;//经验值
    
    private String buyerGradeWeightJson;//会员等级比重JSON，例子{"order_weight":"60"(意思是60%),"finance_weight":"40"(意思是40%)}
    
	public BigDecimal getMallDistance() {
		return mallDistance;
	}

	public void setMallDistance(BigDecimal mallDistance) {
		this.mallDistance = mallDistance;
	}

	public BigDecimal getFinanceDistance() {
		return financeDistance;
	}

	public void setFinanceDistance(BigDecimal financeDistance) {
		this.financeDistance = financeDistance;
	}

	public Long getBuyerLevel() {
		return buyerLevel;
	}

	public void setBuyerLevel(Long buyerLevel) {
		this.buyerLevel = buyerLevel;
	}

	public Long getFromScore() {
		return fromScore;
	}

	public void setFromScore(Long fromScore) {
		this.fromScore = fromScore;
	}

	public Long getToScore() {
		return toScore;
	}

	public void setToScore(Long toScore) {
		this.toScore = toScore;
	}

	public Long getLowestPoint() {
		return lowestPoint;
	}

	public void setLowestPoint(Long lowestPoint) {
		this.lowestPoint = lowestPoint;
	}

	public String getIntervalType() {
		return intervalType;
	}

	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getBuyerGradeWeightJson() {
		return buyerGradeWeightJson;
	}

	public void setBuyerGradeWeightJson(String buyerGradeWeightJson) {
		this.buyerGradeWeightJson = buyerGradeWeightJson;
	}


}
