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
public class TradeOrderItemStatusHistoryResDTO extends GenricResDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7868370911133347840L;

	/**
	 * 订单行号
	 */
	private String orderItemNo;

	/**
	 * 订单行状态
	 */
	private String orderItemStatus;

	/**
	 * 创建时间
	 */
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
