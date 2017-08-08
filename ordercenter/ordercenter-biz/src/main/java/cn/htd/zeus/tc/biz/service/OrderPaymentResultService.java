package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.biz.dmo.OrderPayResultInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPayValidateIsBargainCancleResDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultListDMO;
import cn.htd.zeus.tc.dto.response.OrderPayValidateIsBargainCancleResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPayValidateIsBargainCancleReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPaymentResultReqDTO;

public interface OrderPaymentResultService {

	/**
	  * selectPaymentResultByTradeNo(方法的作用)
	  * @Title: selectPaymentResultByTradeNo
	  * @param orderPaymentResultDMO
	  * @return List<OrderPaymentResultDMO>
	 */
	public OrderPaymentResultListDMO selectPaymentResultByTradeNo(OrderPaymentResultReqDTO orderPaymentResultReqDTO);

	/**
	  * 支付成功修改订单状态
	  * @Title: updateOrderStatusByTradeNo
	  * @return int
	  * @throws
	 */
	public OrderPaymentResultDMO updateOrderStatusByTradeNo(OrderPayResultInfoDMO orderPayResultInfoDMO);
	

	public OrderPaymentResultDMO updatePayStatusByOrderNo(OrderPaymentResultReqDTO orderPaymentResultReqDTO);

	/**
	 * 支付前校验,是否有议价|是否取消
	 */
	public OrderPayValidateIsBargainCancleResDMO validateIsBargainCancle(OrderPayValidateIsBargainCancleReqDTO orderPayValidateIsBargainCancleReqDTO);
}
