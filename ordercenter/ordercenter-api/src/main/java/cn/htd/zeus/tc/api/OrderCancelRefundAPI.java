/**
 * 
 */
package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.OrderCancelInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCancelInfoReqDTO;

/**
 * @author ly
 *
 */
public interface OrderCancelRefundAPI {
	/**
	 * 订单取消接口
	 * @param orderCancelInfoDTO
	 * @return
	 */
	public OrderCancelInfoResDTO orderCancel(OrderCancelInfoReqDTO orderCancelInfoDTO);
	
	/**
	 * 订单行退款接口
	 * @param orderCancelInfoDTO
	 * @return
	 */
	public OrderCancelInfoResDTO orderItemRefund(OrderCancelInfoReqDTO orderCancelInfoDTO);
	
	/**
	 * 商城订单行取消接口
	 * @param orderCancelInfoDTO
	 * @return
	 */
	public OrderCancelInfoResDTO orderItemCancel(OrderCancelInfoReqDTO orderCancelInfoDTO);
	
	/**
	 * VMS和运营后台订单取消接口
	 * @param orderCancelInfoDTO
	 * @return
	 */
	public OrderCancelInfoResDTO vmsOperateOrderItemCancel(OrderCancelInfoReqDTO orderCancelInfoDTO);
	
	/**
	 * VMS和运营后台订单行取消接口
	 * @param orderCancelInfoDTO
	 * @return
	 */
	public OrderCancelInfoResDTO vmsOperateOrderCancel(OrderCancelInfoReqDTO orderCancelInfoDTO);


	/**
	 * 订单删除取消接口
	 * @param orderCancelInfoDTO
	 * @return
	 */
	public OrderCancelInfoResDTO orderDelete(OrderCancelInfoReqDTO orderCancelInfoDTO);

}
