package cn.htd.membercenter.domain;

import java.util.Date;

public class BuyerGradeChangeHistory {

	private String gradeHistoryId;
	private String buyerId;
	// 操作后会员等级
	private String changeGrade;
	// 会员更新时间
	private Date changeTime;
	// 升降级标志 1：升级 0：降级
	private String isUpgrade;
	// 变更会员等级套餐等类型:1: 会员等级规则变更操作 2:会员保底经验值变更操作 3:会员权重值变更操作 4:保底经验值变更
	// 5：会员套餐类型变更操作 6：会员套餐时间变更操作
	private String afterGrade;
	private String deleteFlag;
	private String createId;
	private String createName;
	private Date createTime;
	private String modifyId;
	private String modifyName;
	private Date modifyTime;
	private String operateId;
	private String operateName;

	/**
	 * @return the operateId
	 */
	public String getOperateId() {
		return operateId;
	}

	/**
	 * @param operateId
	 *            the operateId to set
	 */
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	/**
	 * @return the operateName
	 */
	public String getOperateName() {
		return operateName;
	}

	/**
	 * @param operateName
	 *            the operateName to set
	 */
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	/**
	 * @return the gradeHistoryId
	 */
	public String getGradeHistoryId() {
		return gradeHistoryId;
	}

	/**
	 * @param gradeHistoryId
	 *            the gradeHistoryId to set
	 */
	public void setGradeHistoryId(String gradeHistoryId) {
		this.gradeHistoryId = gradeHistoryId;
	}

	/**
	 * @return the buyerId
	 */
	public String getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId
	 *            the buyerId to set
	 */
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the changeGrade
	 */
	public String getChangeGrade() {
		return changeGrade;
	}

	/**
	 * @param changeGrade
	 *            the changeGrade to set
	 */
	public void setChangeGrade(String changeGrade) {
		this.changeGrade = changeGrade;
	}

	/**
	 * @return the changeTime
	 */
	public Date getChangeTime() {
		return changeTime;
	}

	/**
	 * @param changeTime
	 *            the changeTime to set
	 */
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	/**
	 * @return the isUpgrade
	 */
	public String getIsUpgrade() {
		return isUpgrade;
	}

	/**
	 * @param isUpgrade
	 *            the isUpgrade to set
	 */
	public void setIsUpgrade(String isUpgrade) {
		this.isUpgrade = isUpgrade;
	}

	/**
	 * @return the afterGrade
	 */
	public String getAfterGrade() {
		return afterGrade;
	}

	/**
	 * @param afterGrade
	 *            the afterGrade to set
	 */
	public void setAfterGrade(String afterGrade) {
		this.afterGrade = afterGrade;
	}

	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag
	 *            the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return the createId
	 */
	public String getCreateId() {
		return createId;
	}

	/**
	 * @param createId
	 *            the createId to set
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}

	/**
	 * @param createName
	 *            the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyId
	 */
	public String getModifyId() {
		return modifyId;
	}

	/**
	 * @param modifyId
	 *            the modifyId to set
	 */
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	/**
	 * @return the modifyName
	 */
	public String getModifyName() {
		return modifyName;
	}

	/**
	 * @param modifyName
	 *            the modifyName to set
	 */
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
