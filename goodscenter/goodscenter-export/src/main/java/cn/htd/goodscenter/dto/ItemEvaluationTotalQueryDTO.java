package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:商品评价统计输出DTO类]
 * </p>
 */
public class ItemEvaluationTotalQueryDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer scope;// 评分级别
	private BigInteger scopeCount; // 评分级别对应的数量
	private BigInteger scopeSum;// 分数级别对应的总和
	private BigInteger allCount; // 评价的总条数
	private BigInteger allSum; // 评价的总分

	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public BigInteger getScopeCount() {
		return scopeCount;
	}

	public void setScopeCount(BigInteger scopeCount) {
		this.scopeCount = scopeCount;
	}

	public BigInteger getScopeSum() {
		return scopeSum;
	}

	public void setScopeSum(BigInteger scopeSum) {
		this.scopeSum = scopeSum;
	}

	public BigInteger getAllCount() {
		return allCount;
	}

	public void setAllCount(BigInteger allCount) {
		this.allCount = allCount;
	}

	public BigInteger getAllSum() {
		return allSum;
	}

	public void setAllSum(BigInteger allSum) {
		this.allSum = allSum;
	}

}
