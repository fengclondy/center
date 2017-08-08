package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberBuyerGradeChangeHistoryDTO implements Serializable{

	private static final long serialVersionUID = 436348396658952511L;
	
	private String  gradeHistoryId  ;//'等级履历ID',
	private String  buyerId   ;//'会员ID',
	private String  changeGrade  ;// '变更等级',
	private String  changeTime  ;// '变更时间',
	private String  isUpgrade  ;//'升降级标识',  1升级   0降级
	private String  deleteFlag  ;// '0、未删除，1、已删除',
	private String  createId    ;//'创建人ID',
	private String  createName   ;//'创建人名称',
	private String  createTime  ;//'创建时间',
	private String  modifyId   ;//'更新人ID',
	private String  modifyName  ;//'更新人名称',
	private String  modifyTime  ;// '更新时间',
	public String getGradeHistoryId() {
		return gradeHistoryId;
	}
	public void setGradeHistoryId(String gradeHistoryId) {
		this.gradeHistoryId = gradeHistoryId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getChangeGrade() {
		return changeGrade;
	}
	public void setChangeGrade(String changeGrade) {
		this.changeGrade = changeGrade;
	}
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	public String getIsUpgrade() {
		return isUpgrade;
	}
	public void setIsUpgrade(String isUpgrade) {
		this.isUpgrade = isUpgrade;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
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
	public String getModifyId() {
		return modifyId;
	}
	public void setModifyId(String modifyId) {
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

	
	
}
