package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

/**
 * 订单状态履历ReqDTO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderStatusHistoryReqDTO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class TradeOrderStatusHistoryReqDTO extends GenricReqDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 9070822253799834252L;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单状态
	 */
	private String orderStatus;

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

}
