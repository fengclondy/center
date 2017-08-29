package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.OrderCreateInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreate4huilinReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;

public interface OrderCreateAPI {

	/*
	 * 创建订单
	 * 
	 * @param OrderCreateInfoReqDTO
	 * 
	 * @return OrderCreateInfoResDTO
	 */
	public OrderCreateInfoResDTO orderCreate(OrderCreateInfoReqDTO orderCreateInfoReqDTO);

	/*
	 * 创建订单--提供给中间件(订单来源是超级老板) 定制化该方法的目的是强校验中间件的数据来源
	 * 
	 * @param OrderCreateInfoReqDTO
	 * 
	 * @return OrderCreateInfoResDTO
	 */
	public OrderCreateInfoResDTO orderCreate4MiddleWare(OrderCreateInfoReqDTO orderCreateInfoReqDTO);

	/*
	 * 创建订单--提供给汇掌柜(订单来源汇掌柜) 定制化该方法主要用来拼接该来源订单的相关订单需要的数据
	 * 
	 * @param OrderCreateInfoReqDTO
	 * 
	 * @return OrderCreateInfoResDTO
	 */
	public OrderCreateInfoResDTO orderCreate4Huilin(OrderCreate4huilinReqDTO orderCreate4huilinReqDTO);
}
