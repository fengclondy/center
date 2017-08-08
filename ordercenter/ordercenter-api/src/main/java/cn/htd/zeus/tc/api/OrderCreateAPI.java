package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.OrderCreateInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;

public interface OrderCreateAPI {

	/*
	 * 创建订单
	 * @param OrderCreateInfoReqDTO
	 * @return OrderCreateInfoResDTO
	 */
	public OrderCreateInfoResDTO orderCreate(OrderCreateInfoReqDTO orderCreateInfoReqDTO);
	
	

	/*
	 * 创建订单--提供给中间件(订单来源是超级老板)
	 * 定制化该方法的目的是强校验中间件的数据来源
	 * @param OrderCreateInfoReqDTO
	 * @return OrderCreateInfoResDTO
	 */
	public OrderCreateInfoResDTO orderCreate4MiddleWare(OrderCreateInfoReqDTO orderCreateInfoReqDTO);
	
}
