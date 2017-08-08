package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

public class VerifyInfoDTO implements Serializable {
	private static final long serialVersionUID = -5933492222398299817L;

	private Long id;
	private String modifyType; // '业务类型，regist：会员注册，2：非会员注册，3：会员修改审核 。。。',
	private Long belongSellerId; // '所属卖家ID',
	private String recordType; // 记录类型
	private Long recordId; // '记录ID',
	private Long verifierId; // '审核人ID',
	private String verifierName; // '审核人名称',
	private String verifyTime; // '审核人时间',
	private String verifyStatus; // '审核状态：1为待审核，2为通过，3为驳回',
	private String remark; // '审核备注',
	private Long createId; // '创建人ID',
	private String createName; // '创建人名称',
	private String createTime; // '创建时间',
	private Long modifyId; // '更新人ID',
	private String modifyName; // '更新人名称',
	private String modifyTime; // '更新时间',
	private List<String> modifyTypelist;
	private String belongSellerCode;// 所属卖家code

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public Long getBelongSellerId() {
		return belongSellerId;
	}

	public void setBelongSellerId(Long belongSellerId) {
		this.belongSellerId = belongSellerId;
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

	public Long getVerifierId() {
		return verifierId;
	}

	public void setVerifierId(Long verifierId) {
		this.verifierId = verifierId;
	}

	public String getVerifierName() {
		return verifierName;
	}

	public void setVerifierName(String verifierName) {
		this.verifierName = verifierName;
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<String> getModifyTypelist() {
		return modifyTypelist;
	}

	public void setModifyTypelist(List<String> modifyTypelist) {
		this.modifyTypelist = modifyTypelist;
	}

	public String getBelongSellerCode() {
		return belongSellerCode;
	}

	public void setBelongSellerCode(String belongSellerCode) {
		this.belongSellerCode = belongSellerCode;
	}

}
