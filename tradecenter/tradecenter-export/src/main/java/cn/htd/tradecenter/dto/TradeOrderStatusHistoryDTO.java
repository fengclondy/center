package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class TradeOrderStatusHistoryDTO implements Serializable {
	private static final long serialVersionUID = 1186337365099963930L;
	private Long id; // ID
	private String orderNo; // 订单ID
	private String orderStatus; // 订单状态
	private String orderStatusText; // 订单状态文本
	private Long createId; // 创建人ID
	private String createName; // 创建人名称
	private Date createTime; // 创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusText() {
		return orderStatusText;
	}

	public void setOrderStatusText(String orderStatusText) {
		this.orderStatusText = orderStatusText;
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
