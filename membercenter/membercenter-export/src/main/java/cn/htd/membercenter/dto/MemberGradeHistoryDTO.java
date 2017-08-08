package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberGradeHistoryDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private long gradeHistoryId;
	// 会员编码
	private long buyerId;
	// 变更等级
	private String changeGrade;
	// 变更时间
	private Date changeTime;
	// 操作后会员等级
	private String afterGrade;
	// 是否升级标志
	private String isUpgrade;
	private String createId;
	private String createName;
	private Date createTime;

	/**
	 * @return the gradeHistoryId
	 */
	public long getGradeHistoryId() {
		return gradeHistoryId;
	}

	/**
	 * @param gradeHistoryId
	 *            the gradeHistoryId to set
	 */
	public void setGradeHistoryId(long gradeHistoryId) {
		this.gradeHistoryId = gradeHistoryId;
	}

	/**
	 * @return the buyerId
	 */
	public long getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId
	 *            the buyerId to set
	 */
	public void setBuyerId(long buyerId) {
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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

}
