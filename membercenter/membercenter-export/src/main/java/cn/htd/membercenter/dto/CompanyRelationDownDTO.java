package cn.htd.membercenter.dto;

import java.io.Serializable;

public class CompanyRelationDownDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long boxId;
	private String identifier;
	private String memberCode;// 中台会员编号
	private String supplierCode;// 中台供应商编号
	private String memberName;// 会员名称
	private int isUpdateFlag;// 是否更新，更新：1，新增：0

	private String merchOrderNo;// ERP唯一key

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getIsUpdateFlag() {
		return isUpdateFlag;
	}

	public void setIsUpdateFlag(int isUpdateFlag) {
		this.isUpdateFlag = isUpdateFlag;
	}

	/**
	 * @return the boxId
	 */
	public Long getBoxId() {
		return boxId;
	}

	/**
	 * @param boxId
	 *            the boxId to set
	 */
	public void setBoxId(Long boxId) {
		this.boxId = boxId;
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

}
