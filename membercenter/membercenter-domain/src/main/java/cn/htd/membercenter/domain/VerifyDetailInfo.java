package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class VerifyDetailInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long verifyId;// ����ID

	private String modifyType;// ҵ�����ͣ�regist����Աע�ᣬ2���ǻ�Աע�ᣬ3����Ա�޸����

	private Integer recordType;// ��¼���� ���磺��˾ID������ID��

	private Long recordId;// ��¼ID

	private String contentName;// ������

	private String changeTableId;// �޸ı�ID

	private String changeFieldId;// �޸��ֶ�ID

	private String beforeChange;// �޸�ǰ������

	private String afterChange;// �޸ĺ������

	private String beforeChangeDesc;// �޸�ǰ������

	private String afterChangeDesc;// �޸ĺ������

	private Long operatorId;// ������ID

	private String operatorName;// ���������

	private Date operateTime;// ����ʱ��
	
	private String remark;

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
		this.modifyType = modifyType == null ? null : modifyType.trim();
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
		this.contentName = contentName == null ? null : contentName.trim();
	}

	public String getChangeTableId() {
		return changeTableId;
	}

	public void setChangeTableId(String changeTableId) {
		this.changeTableId = changeTableId == null ? null : changeTableId.trim();
	}

	public String getChangeFieldId() {
		return changeFieldId;
	}

	public void setChangeFieldId(String changeFieldId) {
		this.changeFieldId = changeFieldId == null ? null : changeFieldId.trim();
	}

	public String getBeforeChange() {
		return beforeChange;
	}

	public void setBeforeChange(String beforeChange) {
		this.beforeChange = beforeChange == null ? null : beforeChange.trim();
	}

	public String getAfterChange() {
		return afterChange;
	}

	public void setAfterChange(String afterChange) {
		this.afterChange = afterChange == null ? null : afterChange.trim();
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
		this.operatorName = operatorName == null ? null : operatorName.trim();
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return the beforeChangeDesc
	 */
	public String getBeforeChangeDesc() {
		return beforeChangeDesc;
	}

	/**
	 * @param beforeChangeDesc the beforeChangeDesc to set
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
	 * @param afterChangeDesc the afterChangeDesc to set
	 */
	public void setAfterChangeDesc(String afterChangeDesc) {
		this.afterChangeDesc = afterChangeDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}