package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author jiaop
 *
 */
public class RechargeOrderReqDTO extends GenricReqDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -217255955847729436L;

	/**
	 * 充值订单号
	 */
	@NotEmpty(message = "rechargeNo不能为空")
	private String rechargeNo;

	/**
	 * 充值状态
	 */
	@NotEmpty(message = "payStatus不能为空")
	private String payStatus;

	/**
	 * 操作员代码
	 */
	@NotEmpty(message = "operaterCode不能为空")
	private String operaterCode;

	/**
	 * 操作员名称
	 */
	@NotEmpty(message = "operaterName不能为空")
	private String operaterName;

	/**
	 * 收款人账户
	 */
	@NotEmpty(message = "payeeAccountNo不能为空")
	private String payeeAccountNo;

	/**
	 * 业务员代码
	 */
	private String salemanCode;

	/**
	 * 业务员名称
	 */
	private String saleman;

	/**
	 * 销售部门代码
	 */
	@NotEmpty(message = "departmentCode不能为空")
	private String departmentCode;

	/**
	 * 中台会员编号
	 */
	@NotEmpty(message = "memberCode不能为空")
	private String memberCode;

	/**
	 * 产品代码
	 */
	private String productCode;

	/**
	 * 预收冲应收标记
	 */
	private Integer isRushReceivable;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 是否锁余额
	 */
	private Integer isLockBalanceFlag;

	/**
	 * 中台品牌编号
	 */
	private String brandCode;

	/**
	 * ERP类目代码
	 */
	private String classCode;

	/**
	 * 中台供应商编号
	 */
	@NotEmpty(message = "supplierCode不能为空")
	private String supplierCode;

	private Date payTime;

	/**
	 * 收款方式代码
	 */
	@NotEmpty(message = "payCode不能为空")
	private String payCode;

	/**
	 * 收款金额
	 */
	@NotEmpty(message = "amount不能为空")
	private BigDecimal amount;

	/**
	 * 充值渠道
	 */
	@NotEmpty(message = "rechargeChannelCode不能为空")
	private String rechargeChannelCode;

	public String getRechargeNo() {
		return rechargeNo;
	}

	public void setRechargeNo(String rechargeNo) {
		this.rechargeNo = rechargeNo;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getOperaterCode() {
		return operaterCode;
	}

	public void setOperaterCode(String operaterCode) {
		this.operaterCode = operaterCode;
	}

	public String getOperaterName() {
		return operaterName;
	}

	public void setOperaterName(String operaterName) {
		this.operaterName = operaterName;
	}

	public String getSalemanCode() {
		return salemanCode;
	}

	public void setSalemanCode(String salemanCode) {
		this.salemanCode = salemanCode;
	}

	public String getSaleman() {
		return saleman;
	}

	public void setSaleman(String saleman) {
		this.saleman = saleman;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getIsRushReceivable() {
		return isRushReceivable;
	}

	public void setIsRushReceivable(Integer isRushReceivable) {
		this.isRushReceivable = isRushReceivable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getIsLockBalanceFlag() {
		return isLockBalanceFlag;
	}

	public void setIsLockBalanceFlag(Integer isLockBalanceFlag) {
		this.isLockBalanceFlag = isLockBalanceFlag;
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

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

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

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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
