package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class MemberBankInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long bankInfoId;

	private Long memberId;

	private String bankAccountName;

	private String bankAccount;

	private String bankName;

	private String bankBranchJointLine;

	private String bankBranchIsLocated;

	private String bankAccountPermitsPicSrc;

	private Byte deleteFlag;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	public Long getBankInfoId() {
		return bankInfoId;
	}

	public void setBankInfoId(Long bankInfoId) {
		this.bankInfoId = bankInfoId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName == null ? null : bankAccountName.trim();
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount == null ? null : bankAccount.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	public String getBankBranchJointLine() {
		return bankBranchJointLine;
	}

	public void setBankBranchJointLine(String bankBranchJointLine) {
		this.bankBranchJointLine = bankBranchJointLine == null ? null : bankBranchJointLine.trim();
	}

	public String getBankBranchIsLocated() {
		return bankBranchIsLocated;
	}

	public void setBankBranchIsLocated(String bankBranchIsLocated) {
		this.bankBranchIsLocated = bankBranchIsLocated == null ? null : bankBranchIsLocated.trim();
	}

	public String getBankAccountPermitsPicSrc() {
		return bankAccountPermitsPicSrc;
	}

	public void setBankAccountPermitsPicSrc(String bankAccountPermitsPicSrc) {
		this.bankAccountPermitsPicSrc = bankAccountPermitsPicSrc == null ? null : bankAccountPermitsPicSrc.trim();
	}

	public Byte getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Byte deleteFlag) {
		this.deleteFlag = deleteFlag;
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
		this.createName = createName == null ? null : createName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
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
		this.modifyName = modifyName == null ? null : modifyName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}