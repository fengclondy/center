package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeOrderDMO extends GenericDMO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1002230109895845166L;

	private Long id;

	private String rechargeOrderNo;

	private String payOrderNo;

	private String payeeAccountNo;

	private BigDecimal amount;

	private String payCode;

	private String payStatus;

	private String memberCode;

	private String memberName;

	private String supplierCode;

	private Date payTime;

	private String departmentCode;

	private Integer start;

	private Integer rows;

	private Date payTimeFrom;

	private Date payTimeTo;

	private String rechargeChannelCode;

	public Date getPayTimeFrom() {
		return payTimeFrom;
	}

	public void setPayTimeFrom(Date payTimeFrom) {
		this.payTimeFrom = payTimeFrom;
	}

	public Date getPayTimeTo() {
		return payTimeTo;
	}

	public void setPayTimeTo(Date payTimeTo) {
		this.payTimeTo = payTimeTo;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRechargeOrderNo() {
		return rechargeOrderNo;
	}

	public void setRechargeOrderNo(String rechargeOrderNo) {
		this.rechargeOrderNo = rechargeOrderNo;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getPayeeAccountNo() {
		return payeeAccountNo;
	}

	public void setPayeeAccountNo(String payeeAccountNo) {
		this.payeeAccountNo = payeeAccountNo;
	}

	/**
	 * @return the rechargeChannelCode
	 */
	public String getRechargeChannelCode() {
		return rechargeChannelCode;
	}

	/**
	 * @param rechargeChannelCode
	 *            the rechargeChannelCode to set
	 */
	public void setRechargeChannelCode(String rechargeChannelCode) {
		this.rechargeChannelCode = rechargeChannelCode;
	}

}