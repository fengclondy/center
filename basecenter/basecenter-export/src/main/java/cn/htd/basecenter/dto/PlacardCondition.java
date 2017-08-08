/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	MemberInfo.java
 * Author:   	jiangkun
 * Date:     	2016年11月23日
 * Description: 公告查询用相关信息 
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月23日	1.0			创建
 */

package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.basecenter.enums.PlacardStatusEnum;
import cn.htd.basecenter.enums.PlacardTypeEnum;

/**
 * @author jiangkun
 *
 */
public class PlacardCondition implements Serializable {

	private static final long serialVersionUID = 7955798434066034364L;
	/**
	 * 入参(必须)：会员ID／供应商ID
	 */
	private Long memberId;

	/**
	 * 入参(必须)：公告类型：all:全部，1:平台公告，2:商家公告,
	 */
	private String placardType = PlacardTypeEnum.ALL.getValue();

	/**
	 * 入参(查询平台公告用)：会员等级值：
	 */
	private String buyerGradeValue = "";

	/**
	 * 入参(查询平台公告用)：供应商类型：1:内部供应商，2:外部供应商
	 */
	private String sellerType = "";

	/**
	 * 入参(查询店铺公告用)：是否已读：空白：不区分，0:未读，1:已读
	 */
	private String hasRead = "";

	/**
	 * 内部用参数非入参
	 */
	private String status;

	/**
	 * 内部用参数非入参
	 */
	private String sellerSendType = PlacardTypeEnum.SELLER.getValue();

	/**
	 * 内部用参数非入参
	 */
	private String platformSendType = PlacardTypeEnum.PLATFORM.getValue();

	/**
	 * 内部用参数非入参
	 */
	private int deleteFlag;

	/**
	 * 内部用参数非入参
	 */
	private int isValidForever;

	/**
	 * 内部用参数非入参
	 */
	private String allBuyerType;

	/**
	 * 内部用参数非入参
	 */
	private String partBuyerType;

	/**
	 * 内部用参数非入参
	 */
	private int taskQueueNum;

	/**
	 * 内部用参数非入参
	 */
	private List<String> taskIdList;

	/**
	 * 内部用参数非入参
	 */
	private List<Long> placardIdList;

	/**
	 * 内部用参数非入参
	 */
	private String pendingStatus = PlacardStatusEnum.PENDING.getValue();

	/**
	 * 内部用参数非入参
	 */
	private String sendingStatus = PlacardStatusEnum.SENDING.getValue();

	/**
	 * 内部用参数非入参
	 */
	private String sentStatus = PlacardStatusEnum.SENT.getValue();

	/**
	 * 内部用参数非入参
	 */
	private String expireStatus = PlacardStatusEnum.EXPIRED.getValue();

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getPlacardType() {
		return placardType;
	}

	public void setPlacardType(String placardType) {
		this.placardType = placardType;
	}

	public String getBuyerGradeValue() {
		return buyerGradeValue;
	}

	public void setBuyerGradeValue(String buyerGradeValue) {
		this.buyerGradeValue = buyerGradeValue;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getHasRead() {
		return hasRead;
	}

	public void setHasRead(String hasRead) {
		this.hasRead = hasRead;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSellerSendType() {
		return sellerSendType;
	}

	public void setSellerSendType(String sellerSendType) {
		this.sellerSendType = sellerSendType;
	}

	public String getPlatformSendType() {
		return platformSendType;
	}

	public void setPlatformSendType(String platformSendType) {
		this.platformSendType = platformSendType;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getIsValidForever() {
		return isValidForever;
	}

	public void setIsValidForever(int isValidForever) {
		this.isValidForever = isValidForever;
	}

	public String getAllBuyerType() {
		return allBuyerType;
	}

	public void setAllBuyerType(String allBuyerType) {
		this.allBuyerType = allBuyerType;
	}

	public String getPartBuyerType() {
		return partBuyerType;
	}

	public void setPartBuyerType(String partBuyerType) {
		this.partBuyerType = partBuyerType;
	}

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

	public List<Long> getPlacardIdList() {
		return placardIdList;
	}

	public void setPlacardIdList(List<Long> placardIdList) {
		this.placardIdList = placardIdList;
	}

	public String getPendingStatus() {
		return pendingStatus;
	}

	public void setPendingStatus(String pendingStatus) {
		this.pendingStatus = pendingStatus;
	}

	public String getSendingStatus() {
		return sendingStatus;
	}

	public void setSendingStatus(String sendingStatus) {
		this.sendingStatus = sendingStatus;
	}

	public String getSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}

	public String getExpireStatus() {
		return expireStatus;
	}

	public void setExpireStatus(String expireStatus) {
		this.expireStatus = expireStatus;
	}

}
