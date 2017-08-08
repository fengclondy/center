package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberGradeRuleHistoryDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	// 区间类型：1、商品购买 2、金融消费
	private String intervalType;
	// 操作类型：1、更改会员经验规则 2、更改会员保底经验值

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the intervalType
	 */
	public String getIntervalType() {
		return intervalType;
	}

	/**
	 * @param intervalType
	 *            the intervalType to set
	 */
	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
