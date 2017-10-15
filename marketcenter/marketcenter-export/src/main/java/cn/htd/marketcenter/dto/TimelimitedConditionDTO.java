package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀活动检索条件
 */
public class TimelimitedConditionDTO implements Serializable {

	private static final long serialVersionUID = 5210372509736403342L;
	/**
	 * 商品编码
	 */
	private String skuCode;
	/**
	 * 商品SKU名称
	 */
	private String skuName;
	/**
	 * 活动状态
	 */
	private String status;

	/**
	 * 供应商编码
	 */
	private String selleCode;

	/**
	 * 限时购开始时间
	 */
	private Date startTime;
	/**
	 * 限时购结束时间
	 */
	private Date endTime;
	/**
	 * 限时购开始时间
	 */
	private String startTimeStr;
	/**
	 * 限时购结束时间
	 */
	private String endTimeStr;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSelleCode() {
		return selleCode;
	}

	public void setSelleCode(String selleCode) {
		this.selleCode = selleCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

}
