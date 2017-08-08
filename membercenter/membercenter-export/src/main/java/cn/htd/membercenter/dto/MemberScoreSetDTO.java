package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberScoreSetDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	// 区间类型 1:商城交易，2:金融产品
	private String intervalType;
	// 金额区间上限
	private String fromAmount;
	// 金额区间下限
	private String toAmount;
	// 经验值
	private String score;
	// 会员等级level 1:一星会员,2:二星会员,3:三星会员,4:四星会员,5:五星会员
	private String buyerLevel;
	// 经验区间上限
	private String fromScore;
	// 经验区间下限
	private String toScore;
	// 保底经验值
	private String lowestPoint;
	// 商城消费权重
	private String mallWeight;
	// 金融消费权重
	private String financeWeight;
	// 权重拼接jsonStr
	private String jsonStr;

	private String deleteFlag;
	private String createId;
	private String createName;
	private Date createTime;
	private String modifyId;
	private String modifyName;
	private Date modifyTime;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the intervalType
	 */
	public String getIntervalType() {
		return intervalType;
	}

	/**
	 * @param intervalType
	 *            the intervalType to set
	 */
	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}

	/**
	 * @return the fromAmount
	 */
	public String getFromAmount() {
		return fromAmount;
	}

	/**
	 * @param fromAmount
	 *            the fromAmount to set
	 */
	public void setFromAmount(String fromAmount) {
		this.fromAmount = fromAmount;
	}

	/**
	 * @return the toAmount
	 */
	public String getToAmount() {
		return toAmount;
	}

	/**
	 * @param toAmount
	 *            the toAmount to set
	 */
	public void setToAmount(String toAmount) {
		this.toAmount = toAmount;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * @return the buyerLevel
	 */
	public String getBuyerLevel() {
		return buyerLevel;
	}

	/**
	 * @param buyerLevel
	 *            the buyerLevel to set
	 */
	public void setBuyerLevel(String buyerLevel) {
		this.buyerLevel = buyerLevel;
	}

	/**
	 * @return the fromScore
	 */
	public String getFromScore() {
		return fromScore;
	}

	/**
	 * @param fromScore
	 *            the fromScore to set
	 */
	public void setFromScore(String fromScore) {
		this.fromScore = fromScore;
	}

	/**
	 * @return the toScore
	 */
	public String getToScore() {
		return toScore;
	}

	/**
	 * @param toScore
	 *            the toScore to set
	 */
	public void setToScore(String toScore) {
		this.toScore = toScore;
	}

	/**
	 * @return the lowestPoint
	 */
	public String getLowestPoint() {
		return lowestPoint;
	}

	/**
	 * @param lowestPoint
	 *            the lowestPoint to set
	 */
	public void setLowestPoint(String lowestPoint) {
		this.lowestPoint = lowestPoint;
	}

	/**
	 * @return the mallWeight
	 */
	public String getMallWeight() {
		return mallWeight;
	}

	/**
	 * @param mallWeight
	 *            the mallWeight to set
	 */
	public void setMallWeight(String mallWeight) {
		this.mallWeight = mallWeight;
	}

	/**
	 * @return the financeWeight
	 */
	public String getFinanceWeight() {
		return financeWeight;
	}

	/**
	 * @param financeWeight
	 *            the financeWeight to set
	 */
	public void setFinanceWeight(String financeWeight) {
		this.financeWeight = financeWeight;
	}

	/**
	 * @return the jsonStr
	 */
	public String getJsonStr() {
		return jsonStr;
	}

	/**
	 * @param jsonStr
	 *            the jsonStr to set
	 */
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
