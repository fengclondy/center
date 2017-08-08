package cn.htd.zeus.tc.api;

import java.util.List;

import cn.htd.zeus.tc.dto.response.EmptyResDTO;
import cn.htd.zeus.tc.dto.response.OrderPayValidateIsBargainCancleResDTO;
import cn.htd.zeus.tc.dto.response.OrderPaymentResultListResDTO;
import cn.htd.zeus.tc.dto.response.OrderPaymentResultResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPayValidateIsBargainCancleReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPaymentResultReqDTO;

public interface OrderPaymentResultAPI {

	/**
	  * selectPaymentResultByTradeNo(方法的作用)
	  * @Title: selectPaymentResultByTradeNo
	  * @param orderPaymentResultDMO
	  * @return List<OrderPaymentResultDMO>
	 */
	public OrderPaymentResultListResDTO selectPaymentResultByTradeNo(OrderPaymentResultReqDTO orderPaymentResultReqDTO);

	/**
	  * 更新支付状态
	  * @Title: updateOrderStatusByTradeNo
	  * @param orderPaymentResultReqDTO
	  * @return OrderPaymentResultResDTO
	  * @throws
	 */
	public OrderPaymentResultResDTO updateOrderStatusByTradeNo(OrderPaymentResultReqDTO orderPaymentResultReqDTO);
	
	public EmptyResDTO updatePayStatusByOrderNo(OrderPaymentResultReqDTO orderPaymentResultReqDTO);

	/**
	 * 支付前校验,是否有议价|是否取消
	 */
	public OrderPayValidateIsBargainCancleResDTO validateIsBargainCancle(OrderPayValidateIsBargainCancleReqDTO orderPayValidateIsBargainCancleReqDTO);
}
