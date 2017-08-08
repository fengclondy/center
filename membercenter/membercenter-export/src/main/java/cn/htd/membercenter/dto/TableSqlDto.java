package cn.htd.membercenter.dto;

import java.io.Serializable;

public class TableSqlDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tableName;

	private String fieldName;

	private String fileldValue;

	private String conditionClomn;

	private Long conditionValue;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the conditionClomn
	 */
	public String getConditionClomn() {
		return conditionClomn;
	}

	/**
	 * @param conditionClomn
	 *            the conditionClomn to set
	 */
	public void setConditionClomn(String conditionClomn) {
		this.conditionClomn = conditionClomn;
	}

	/**
	 * @return the fileldValue
	 */
	public String getFileldValue() {
		return fileldValue;
	}

	/**
	 * @param fileldValue
	 *            the fileldValue to set
	 */
	public void setFileldValue(String fileldValue) {
		this.fileldValue = fileldValue;
	}

	/**
	 * @return the conditionValue
	 */
	public Long getConditionValue() {
		return conditionValue;
	}

	/**
	 * @param conditionValue
	 *            the conditionValue to set
	 */
	public void setConditionValue(Long conditionValue) {
		this.conditionValue = conditionValue;
	}

}
