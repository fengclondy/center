package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Discription:[商品SKU协议主信息]
 * </p>
 */
public class ContractInfoDTO implements Serializable {

	private static final long serialVersionUID = -6251858618435829940L;
	private Long id;// 主键
	private String contractNo;// 合同编号
	private String contractName;// 合同名称
	private Integer printerId;// 印刷厂用户主键
	private Integer supplierId;// 供货方主键
	private Date beginDate;// 合同有效期--开始
	private Date endDate;// 合同有效期--结束
	private String payType;// 支付约定 支付类型
	private String invoiceFlag;// 发票状态 0-开票 1-不开票
	private String invoiceType;// 发票类型
	private String invoiceName;// 发票抬头
	private String deliverMethod;// 配送方式
	private String remark;// 备注
	private String confirmBy;// 确认人
	private Date confirmDate;// 确认时间
	private String approveBy;// 审批人
	private Date approveDate;// 审批时间
	private String status;// 合同状态
	private Integer createRole;// 创建者角色（卖0、买1）
	private String createBy;//
	private Date createDate;//
	private String updateBy;//
	private Date updateDate;//
	private String activeFlag;// 有效标记 0-有效 1-无效
	private String loginId;
	private String itemName;
	private List<String> printerIdList;
	private List<String> supplierIdList;
	private List<Map> contractMatDTOs;
	private String annex;// 附件
	private String refusalReason;

	public String getRefusalReason() {
		return refusalReason;
	}

	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}

	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}

	public List<Map> getContractMatDTOs() {
		return contractMatDTOs;
	}

	public void setContractMatDTOs(List<Map> contractMatDTOs) {
		this.contractMatDTOs = contractMatDTOs;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public List<String> getPrinterIdList() {
		return printerIdList;
	}

	public void setPrinterIdList(List<String> printerIdList) {
		this.printerIdList = printerIdList;
	}

	public List<String> getSupplierIdList() {
		return supplierIdList;
	}

	public void setSupplierIdList(List<String> supplierIdList) {
		this.supplierIdList = supplierIdList;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Integer getPrinterId() {
		return printerId;
	}

	public void setPrinterId(Integer printerId) {
		this.printerId = printerId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getInvoiceFlag() {
		return invoiceFlag;
	}

	public void setInvoiceFlag(String invoiceFlag) {
		this.invoiceFlag = invoiceFlag;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getDeliverMethod() {
		return deliverMethod;
	}

	public void setDeliverMethod(String deliverMethod) {
		this.deliverMethod = deliverMethod;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConfirmBy() {
		return confirmBy;
	}

	public void setConfirmBy(String confirmBy) {
		this.confirmBy = confirmBy;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCreateRole() {
		return createRole;
	}

	public void setCreateRole(Integer createRole) {
		this.createRole = createRole;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
}
