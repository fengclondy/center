package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MemberBuyerVerifyDetailInfoDTO
 * </p>
 * 
 * @author root
 * @date 2016年12月27日
 *       <p>
 *       Description: 买家中心 审批详细信息表
 *       </p>
 */
public class MemberBuyerVerifyDetailInfoDTO implements Serializable {

	private static final long serialVersionUID = 5982822478548155313L;

	private Long id;
	private Long verifyId;// 审批ID
	private String modifyType;// 业务类型，
	private String recordType;// 记录类型
	private Long recordId;// 记录id
	private String contentName;// 内容名
	private String changeTableId;// 表名
	private String changeFieldId;// 字段名
	private String beforeChange;// 修改前
	private String afterChange;// 修改后
	private String beforeChangeDesc;// 修改前描述
	private String afterChangeDesc;// 修改后描述
	private Long operatorId;// 修改人id
	private String operatorName;// 修改人名称
	private String operateTime;

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

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
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

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
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
