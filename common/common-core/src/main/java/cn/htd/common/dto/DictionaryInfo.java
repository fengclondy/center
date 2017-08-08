package cn.htd.common.dto;

import java.io.Serializable;

/**
 * <p>
 * Description: [字典表]
 * </p>
 */
public class DictionaryInfo implements Serializable {

	private static final long serialVersionUID = -4865333716151448827L;
	/**
	 * 名称
	 */
	private String name = "";
	/**
	 * 编码
	 */
	private String code = "";
	/**
	 * 值
	 */
	private String value = "";
	/**
	 * 备考
	 */
	private String comment = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
