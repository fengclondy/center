package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class VerifyDetailInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long verifyId;// 审批ID

	private String modifyType;// 业务类型，regist：会员注册，2：非会员注册，3：会员修改审核

	private Integer recordType;// 记录类型 例如：公司ID，店铺ID等

	private Long recordId;// 记录ID

	private String contentName;// 内容名

	private String changeTableId;// 修改表ID

	private String changeFieldId;// 修改字段ID

	private String beforeChange;// 修改前的内容

	private String afterChange;// 修改后的内容

	private String beforeChangeDesc;// 修改前的内容描述

	private String afterChangeDesc;// 修改后的内容描述

	private Long operatorId;// 操作人ID

	private String operatorName;// 操作人名称

	private Date operateTime;// 操作时间

	private String verifyStatus;// 审核状态

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Long verifyId) {
		this.verifyId = verifyId;
	}

	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getChangeTableId() {
		return changeTableId;
	}

	public void setChangeTableId(String changeTableId) {
		this.changeTableId = changeTableId;
	}

	public String getChangeFieldId() {
		return changeFieldId;
	}

	public void setChangeFieldId(String changeFieldId) {
		this.changeFieldId = changeFieldId;
	}

	public String getBeforeChange() {
		return beforeChange;
	}

	public void setBeforeChange(String beforeChange) {
		this.beforeChange = beforeChange;
	}

	public String getAfterChange() {
		return afterChange;
	}

	public void setAfterChange(String afterChange) {
		this.afterChange = afterChange;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	/**
	 * @return the beforeChangeDesc
	 */
	public String getBeforeChangeDesc() {
		return beforeChangeDesc;
	}

	/**
	 * @param beforeChangeDesc
	 *            the beforeChangeDesc to set
	 */
	public void setBeforeChangeDesc(String beforeChangeDesc) {
		this.beforeChangeDesc = beforeChangeDesc;
	}

	/**
	 * @return the afterChangeDesc
	 */
	public String getAfterChangeDesc() {
		return afterChangeDesc;
	}

	/**
	 * @param afterChangeDesc
	 *            the afterChangeDesc to set
	 */
	public void setAfterChangeDesc(String afterChangeDesc) {
		this.afterChangeDesc = afterChangeDesc;
	}

}
