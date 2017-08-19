package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;

/*
 * 订单状态变更公共类
 */
public interface OrderStatusChangeCommonService {
	/*
	 * 订单状态变化修改--directUpdateOrder == true 直接更新订单表 否则 判断所有订单行的状态更新完了，再更新订单表
	 * 
	 * @param orderItems 按照逗号分开
	 * 
	 */
	public void orderStatusChange(String orderNo, String orderItems, String status,
			String statusText, boolean directUpdateOrder, String orderErrorStatus,
			String orderErrorReason);

	/*
	 * 更新订单表状态
	 */
	public void updateOrder(final String status, final String orderNo, final String statusText,
			String orderErrorStatus, String orderErrorReason, boolean isOrderError);

	/**
	 * 对订单状态、异常展示与否、vms支付完成与否 赋值 此方法是赋值，不是更新，入参必订单号必须不为空
	 * 
	 * @param tradeOrdersDMO
	 */
	public void updateOrderErrorVMSPayStatus(TradeOrdersDMO tradeOrdersDMO);

	public int updateOrderPayTimeLimit(String orderNo);
}
