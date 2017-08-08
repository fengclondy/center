package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.List;

public class PostStrikeaBalanceReqDTO  extends GenricReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3720019910630866158L;

	/**
	 * 操作员代码
	 */
	private String operaterCode;

	/**
	 * 充值订单号
	 */
	private String rechargeOrderNo;

	/**
	 * 操作员名称
	 */
	private String operaterName;

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
	private String departmentCode;

	/**
	 * 中台会员编号
	 */
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
	private String supplierCode;
	
	/**
	 * 二级节点
	 */
	private List<CollectPaymentInfoReqDTO> detail;

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

	public List<CollectPaymentInfoReqDTO> getDetail() {
		return detail;
	}

	public void setDetail(List<CollectPaymentInfoReqDTO> detail) {
		this.detail = detail;
	}

	public String getRechargeOrderNo() {
		return rechargeOrderNo;
	}

	public void setRechargeOrderNo(String rechargeOrderNo) {
		this.rechargeOrderNo = rechargeOrderNo;
	}

}
