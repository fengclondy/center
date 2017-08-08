package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单支付结果ResDTO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: OrderPaymentResultReqDTO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 */
public class OrderPaymentResultReqDTO extends GenricReqDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -6382715584400793756L;

	private String buyerCode;

	private String batchPayInfos;
	
	private String orderNo;
	
	private String payStatus;
	
	private String payChannel;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getBatchPayInfos() {
		return batchPayInfos;
	}

	public void setBatchPayInfos(String batchPayInfos) {
		this.batchPayInfos = batchPayInfos;
	}

}
