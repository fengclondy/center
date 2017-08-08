package cn.htd.zeus.tc.biz.dmo;

import java.util.List;

/**
 * 订单支付结果DMO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: OrderPaymentResultListDMO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 */
public class OrderPaymentResultListDMO extends GenericDMO{

	/**
	 *
	 */
	private static final long serialVersionUID = 8215535771985959733L;

	private List<OrderPaymentResultDMO> orderPaymentResultDMO;

	public List<OrderPaymentResultDMO> getOrderPaymentResultDMO() {
		return orderPaymentResultDMO;
	}

	public void setOrderPaymentResultDMO(List<OrderPaymentResultDMO> orderPaymentResultDMO) {
		this.orderPaymentResultDMO = orderPaymentResultDMO;
	}

}
