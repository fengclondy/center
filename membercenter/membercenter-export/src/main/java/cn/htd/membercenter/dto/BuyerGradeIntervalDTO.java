package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class BuyerGradeIntervalDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long buyerLevel;

	private Long fromScore;

	private Long toScore;

	private Long lowestPoint;

	private Byte deleteFlag;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBuyerLevel() {
		return buyerLevel;
	}

	public void setBuyerLevel(Long buyerLevel) {
		this.buyerLevel = buyerLevel;
	}

	public Long getFromScore() {
		return fromScore;
	}

	public void setFromScore(Long fromScore) {
		this.fromScore = fromScore;
	}

	public Long getToScore() {
		return toScore;
	}

	public void setToScore(Long toScore) {
		this.toScore = toScore;
	}

	public Long getLowestPoint() {
		return lowestPoint;
	}

	public void setLowestPoint(Long lowestPoint) {
		this.lowestPoint = lowestPoint;
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