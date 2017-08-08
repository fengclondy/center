package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderPayValidateIsBargainCancleListReqDTO implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 4000743386633202951L;
	
    /**
     * 中台订单号
     */
	private String orderNo;
	
	/**
	 * 支付打开收银台时间 格式 yyyy-MM-dd HH:mm:ss
	 */
	private String openCashierDeskTime;
	
	/**
	 * 订单实付金额
	 */
	private String orderPayAmount;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOpenCashierDeskTime() {
		return openCashierDeskTime;
	}

	public void setOpenCashierDeskTime(String openCashierDeskTime) {
		this.openCashierDeskTime = openCashierDeskTime;
	}

	public String getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(String orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}
	
}
