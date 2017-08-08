package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

/**
 * 订单支付结果集合ResDTO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: OrderPaymentResultListResDTO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 */
public class OrderPaymentResultListResDTO extends GenricResDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5884559885180987494L;

	private List<OrderPaymentResultResDTO> orderPaymentResultDMO;

	public List<OrderPaymentResultResDTO> getOrderPaymentResultDMO() {
		return orderPaymentResultDMO;
	}

	public void setOrderPaymentResultDMO(List<OrderPaymentResultResDTO> orderPaymentResultDMO) {
		this.orderPaymentResultDMO = orderPaymentResultDMO;
	}

}
