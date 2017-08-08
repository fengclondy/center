package cn.htd.zeus.tc.dto;

import java.io.Serializable;

public class OrderItemAttributeDTO implements Serializable {

	private static final long serialVersionUID = 231599169383228237L;

	private Long attributeId; // 属性id

	private String attributeName; // 属性名称

	private Long attributeValueId; // 属性值id

	private String attributeValueName; // 属性值名称

	/**
	 * @return the attributeId
	 */
	public Long getAttributeId() {
		return attributeId;
	}

	/**
	 * @param attributeId
	 *            the attributeId to set
	 */
	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName
	 *            the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @return the attributeValueId
	 */
	public Long getAttributeValueId() {
		return attributeValueId;
	}

	/**
	 * @param attributeValueId
	 *            the attributeValueId to set
	 */
	public void setAttributeValueId(Long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}

	/**
	 * @return the attributeValueName
	 */
	public String getAttributeValueName() {
		return attributeValueName;
	}

	/**
	 * @param attributeValueName
	 *            the attributeValueName to set
	 */
	public void setAttributeValueName(String attributeValueName) {
		this.attributeValueName = attributeValueName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
