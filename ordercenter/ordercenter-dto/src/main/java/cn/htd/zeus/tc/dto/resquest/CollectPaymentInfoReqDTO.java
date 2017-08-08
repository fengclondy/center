package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.math.BigDecimal;

public class CollectPaymentInfoReqDTO extends GenricReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6693325583456624749L;

	/**
	 * 收款方式代码
	 */
	private String payCode;

	/**
	 * 收款金额
	 */
	private BigDecimal amount;

	/**
	 * 中台品牌编号
	 */
	private String brandCode;

	/**
	 * ERP类目代码
	 */
	private String classCode;

	/**
	 * 收款部门
	 */
	private String departmentCode;

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

}
