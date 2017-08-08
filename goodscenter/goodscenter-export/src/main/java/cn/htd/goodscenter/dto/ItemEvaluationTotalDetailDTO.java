package cn.htd.goodscenter.dto;

import java.io.Serializable;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:商品评价统计明细DTO类]
 * </p>
 */
public class ItemEvaluationTotalDetailDTO implements Serializable {
	private static final long serialVersionUID = 2760083891309322693L;
	private Double percent; // 百分比
	private Integer count; // 总数量

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
