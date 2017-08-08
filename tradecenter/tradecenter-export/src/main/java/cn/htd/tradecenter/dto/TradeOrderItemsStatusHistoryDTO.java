package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class TradeOrderItemsStatusHistoryDTO implements Serializable {

	private static final long serialVersionUID = 6452026468072558938L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 订单行编号
	 */
	private String orderItemNo;
	/**
	 * 订单行状态编号
	 */
	private String orderItemStatus;
	/**
	 * 订单状态文本
	 */
	private String orderItemStatusText;
	/**
	 * 创建人ID
	 */
	private Long createId;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}

	public String getOrderItemStatusText() {
		return orderItemStatusText;
	}

	public void setOrderItemStatusText(String orderItemStatusText) {
		this.orderItemStatusText = orderItemStatusText;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
