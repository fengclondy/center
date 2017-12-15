package cn.htd.zeus.tc.common.middleware;

public class MiddlewareHttpUrlConfig {
	
	/*
	 * 中间件封装的京东库存地址
	 */
	private String ordercenterMiddleware4JDStock;
	
	/*
	 * 中间件封装的京东预存款地址
	 */
	private String ordercenterMiddleware4JDAmount;
	
	/*
	 * 中间件的token
	 * jd专用的
	 */
	private String ordercenterMiddleware4token;
	
	/*
	 * 中间件的-京东抛单http地址
	 */
	private String ordercenterMiddleware4JDCreateOrder;
	
	/*
	 * 中间件-京东确认预占库存订单接口
	 */
	private String ordercenterMiddleware4JDConfirmCreateOrder;
	
	/*
	 * erp 订单状态回调
	 */
	private String ordercenterMiddleware4StatusCallback;
	
	/*
	 * JD 订单状态回调
	 */
	private String ordercenterMiddleware4JDStatusCallback;
	
	/*
	 * ERP余额解锁接口地址
	 */
	private String ordercenterMiddleware4unLockBalance;
	
	/**
	 * 订单收货下行erp
	 */
	private String ordercenterMiddleware4ConsigneeDown;
	
	/**
	 * 请求的token
	 * 和京东的token区别开
	 */
	private String ordercenterMiddleware4ERPtoken;
	
	
	/**
	 * @return the ordercenterMiddleware4JDStock
	 */
	public String getOrdercenterMiddleware4JDStock() {
		return ordercenterMiddleware4JDStock;
	}

	/**
	 * @param ordercenterMiddleware4JDStock
	 *            the ordercenterMiddleware4JDStock to set
	 */
	public void setOrdercenterMiddleware4JDStock(String ordercenterMiddleware4JDStock) {
		this.ordercenterMiddleware4JDStock = ordercenterMiddleware4JDStock;
	}

	/**
	 * @return the ordercenterMiddleware4JDAmount
	 */
	public String getOrdercenterMiddleware4JDAmount() {
		return ordercenterMiddleware4JDAmount;
	}

	/**
	 * @param ordercenterMiddleware4JDAmount
	 *            the ordercenterMiddleware4JDAmount to set
	 */
	public void setOrdercenterMiddleware4JDAmount(String ordercenterMiddleware4JDAmount) {
		this.ordercenterMiddleware4JDAmount = ordercenterMiddleware4JDAmount;
	}

	/**
	 * @return the ordercenterMiddleware4token
	 */
	public String getOrdercenterMiddleware4token() {
		return ordercenterMiddleware4token;
	}

	/**
	 * @param ordercenterMiddleware4token
	 *            the ordercenterMiddleware4token to set
	 */
	public void setOrdercenterMiddleware4token(String ordercenterMiddleware4token) {
		this.ordercenterMiddleware4token = ordercenterMiddleware4token;
	}

	public String getOrdercenterMiddleware4JDCreateOrder() {
		return ordercenterMiddleware4JDCreateOrder;
	}

	public void setOrdercenterMiddleware4JDCreateOrder(
			String ordercenterMiddleware4JDCreateOrder) {
		this.ordercenterMiddleware4JDCreateOrder = ordercenterMiddleware4JDCreateOrder;
	}

	public String getOrdercenterMiddleware4JDConfirmCreateOrder() {
		return ordercenterMiddleware4JDConfirmCreateOrder;
	}

	public void setOrdercenterMiddleware4JDConfirmCreateOrder(
			String ordercenterMiddleware4JDConfirmCreateOrder) {
		this.ordercenterMiddleware4JDConfirmCreateOrder = ordercenterMiddleware4JDConfirmCreateOrder;
	}

	public String getOrdercenterMiddleware4StatusCallback() {
		return ordercenterMiddleware4StatusCallback;
	}

	public void setOrdercenterMiddleware4StatusCallback(
			String ordercenterMiddleware4StatusCallback) {
		this.ordercenterMiddleware4StatusCallback = ordercenterMiddleware4StatusCallback;
	}

	public String getOrdercenterMiddleware4JDStatusCallback() {
		return ordercenterMiddleware4JDStatusCallback;
	}

	public void setOrdercenterMiddleware4JDStatusCallback(
			String ordercenterMiddleware4JDStatusCallback) {
		this.ordercenterMiddleware4JDStatusCallback = ordercenterMiddleware4JDStatusCallback;
	}

	public String getOrdercenterMiddleware4unLockBalance() {
		return ordercenterMiddleware4unLockBalance;
	}

	public void setOrdercenterMiddleware4unLockBalance(
			String ordercenterMiddleware4unLockBalance) {
		this.ordercenterMiddleware4unLockBalance = ordercenterMiddleware4unLockBalance;
	}

	public String getOrdercenterMiddleware4ConsigneeDown() {
		return ordercenterMiddleware4ConsigneeDown;
	}

	public void setOrdercenterMiddleware4ConsigneeDown(
			String ordercenterMiddleware4ConsigneeDown) {
		this.ordercenterMiddleware4ConsigneeDown = ordercenterMiddleware4ConsigneeDown;
	}

	public String getOrdercenterMiddleware4ERPtoken() {
		return ordercenterMiddleware4ERPtoken;
	}

	public void setOrdercenterMiddleware4ERPtoken(
			String ordercenterMiddleware4ERPtoken) {
		this.ordercenterMiddleware4ERPtoken = ordercenterMiddleware4ERPtoken;
	}
	
}
