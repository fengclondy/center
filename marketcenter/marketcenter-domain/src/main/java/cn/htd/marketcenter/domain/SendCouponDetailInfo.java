package cn.htd.marketcenter.domain;

import java.util.Date;
import java.util.List;

import cn.htd.marketcenter.common.enums.CouponSendStatusEnum;

public class SendCouponDetailInfo {

	private Long id;

	private String buyerCode;

	private String buyerName;

	private String promotionId;

	private String levelCode;

	private String sendStatus;

	private Integer sendTimes;

	private Date createTime;

	private Date modifyTime;

	private int taskQueueNum;

	private List<String> taskIdList;

	private String noSendStatus = CouponSendStatusEnum.NO_SEND.getCode();

	private String sendFailStatus = CouponSendStatusEnum.FAILED.getCode();

	public int getTaskQueueNum() {
		return taskQueueNum;
	}

	public void setTaskQueueNum(int taskQueueNum) {
		this.taskQueueNum = taskQueueNum;
	}

	public List<String> getTaskIdList() {
		return taskIdList;
	}

	public void setTaskIdList(List<String> taskIdList) {
		this.taskIdList = taskIdList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Integer getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getNoSendStatus() {
		return noSendStatus;
	}

	public void setNoSendStatus(String noSendStatus) {
		this.noSendStatus = noSendStatus;
	}

	public String getSendFailStatus() {
		return sendFailStatus;
	}

	public void setSendFailStatus(String sendFailStatus) {
		this.sendFailStatus = sendFailStatus;
	}
}
