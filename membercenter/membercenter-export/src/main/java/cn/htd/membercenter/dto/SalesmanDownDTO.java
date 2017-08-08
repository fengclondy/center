package cn.htd.membercenter.dto;

import java.io.Serializable;

public class SalesmanDownDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7555358930575357296L;

	private Long businessId;
	private String identifier;
	private String supplierCode;
	private String memberCode;
	private String customerManagerCode;
	private String customerManagerName;
	private String brandCode;
	private String firstClassCategoryCode;
	private String merchOrderNo;// ERP唯一key

	private int isUpdateFlag;

	public String getCustomerManagerCode() {
		return customerManagerCode;
	}

	public void setCustomerManagerCode(String customerManagerCode) {
		this.customerManagerCode = customerManagerCode;
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getFirstClassCategoryCode() {
		return firstClassCategoryCode;
	}

	public void setFirstClassCategoryCode(String firstClassCategoryCode) {
		this.firstClassCategoryCode = firstClassCategoryCode;
	}

	public int getIsUpdateFlag() {
		return isUpdateFlag;
	}

	public void setIsUpdateFlag(int isUpdateFlag) {
		this.isUpdateFlag = isUpdateFlag;
	}

	/**
	 * @return the businessId
	 */
	public Long getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId
	 *            the businessId to set
	 */
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the merchOrderNo
	 */
	public String getMerchOrderNo() {
		return merchOrderNo;
	}

	/**
	 * @param merchOrderNo
	 *            the merchOrderNo to set
	 */
	public void setMerchOrderNo(String merchOrderNo) {
		this.merchOrderNo = merchOrderNo;
	}

	/**
	 * @return the supplierCode
	 */
	public String getSupplierCode() {
		return supplierCode;
	}

	/**
	 * @param supplierCode
	 *            the supplierCode to set
	 */
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

}
