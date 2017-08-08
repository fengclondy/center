package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单状态履历ResDTO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderStatusHistoryResDTO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class TradeOrderStatusHistoryResDTO extends GenricResDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7868370911133347840L;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	private String orderStatusText;

	/**
	 * 创建时间
	 */
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderStatusText() {
		return orderStatusText;
	}

	public void setOrderStatusText(String orderStatusText) {
		this.orderStatusText = orderStatusText;
	}

}
