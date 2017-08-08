package cn.htd.membercenter.dto;

import java.io.Serializable;

public class BoxAddDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memberCode;// 会员code
	private String supplierCode;// 供应商CODE
	private Long modifyId;// 修改人ID
	private String modifyName;// 修改人名称
	private String brandCode;// 品牌CODE
	private String classCategoryCode;// 品类CODE

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getClassCategoryCode() {
		return classCategoryCode;
	}

	public void setClassCategoryCode(String classCategoryCode) {
		this.classCategoryCode = classCategoryCode;
	}

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

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
}
