package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:商品评价统计DTO类]
 * </p>
 */
public class ItemEvaluationTotalDTO implements Serializable {

	private static final long serialVersionUID = 5929398406596577313L;
	private Double scopeAvg;// 店铺总体评分
	private BigInteger evaluationNum;// 总评价人数
	private Map<Integer, ItemEvaluationTotalDetailDTO> scopeAvgDetails; // 店铺评分详情
																		// key取值:1
																		// 2 3 4
																		// 5
																		// value:对应的百分比

	public Double getScopeAvg() {
		return scopeAvg;
	}

	public void setScopeAvg(Double scopeAvg) {
		this.scopeAvg = scopeAvg;
	}

	public Map<Integer, ItemEvaluationTotalDetailDTO> getScopeAvgDetails() {
		return scopeAvgDetails;
	}

	public void setScopeAvgDetails(Map<Integer, ItemEvaluationTotalDetailDTO> scopeAvgDetails) {
		this.scopeAvgDetails = scopeAvgDetails;
	}

	public BigInteger getEvaluationNum() {
		return evaluationNum;
	}

	public void setEvaluationNum(BigInteger evaluationNum) {
		this.evaluationNum = evaluationNum;
	}
}
