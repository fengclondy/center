/**
 * 
 */
package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.dto.resquest.OrderCancelInfoReqDTO;

/**
 * @author ly
 *
 */
public interface OrderCancelService {

	/**
	 * 订单取消逻辑
	 * @param orderCancelInfoDTO
	 * @return
	 * @throws Exception
	 */
	TradeOrdersDMO orderCancel(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception;

	/**
	 * 订单行取消逻辑
	 * @param orderCancelInfoDTO
	 * @return
	 * @throws Exception
	 */
	TradeOrderItemsDMO orderItemCancel(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception;
	
	
	/**
	 * 订单取消逻辑
	 * @param orderCancelInfoDTO
	 * @return
	 * @throws Exception
	 */
	TradeOrdersDMO vmsOperateOrderCancel(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception;

	/**
	 * 订单行取消逻辑
	 * @param orderCancelInfoDTO
	 * @return
	 * @throws Exception
	 */
	TradeOrderItemsDMO vmsOperateOrderItemCancel(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception;
	
	/**
	 * 订单删除逻辑
	 * @param orderCancelInfoDTO
	 * @return
	 * @throws Exception
	 */
	TradeOrdersDMO orderDelete(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception;
	
	/**
	 * VMS2.0 取消订单行
	 * 
	 * @param orderCancelInfoDTO
	 * @return
	 * @throws Exception
	 */
	TradeOrderItemsDMO cancelOrderItemForNewVMS(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception;
	
	/**
	 * VMS2.0 取消订单
	 * 
	 * @param orderCancelInfoDTO
	 * @return
	 * @throws Exception
	 */
	TradeOrdersDMO cancelOrderForNewVMS(OrderCancelInfoReqDTO orderCancelInfoDTO) throws Exception;
}
