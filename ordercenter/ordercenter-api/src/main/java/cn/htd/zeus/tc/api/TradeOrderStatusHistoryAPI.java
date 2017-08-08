package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.TradeOrderItemStatusHistoryListResDTO;
import cn.htd.zeus.tc.dto.response.TradeOrderStatusHistoryListResDTO;
import cn.htd.zeus.tc.dto.response.UpdateOrderStatusResDTO;
import cn.htd.zeus.tc.dto.resquest.TradeOrderStatusHistoryReqDTO;
import cn.htd.zeus.tc.dto.resquest.UpdateOrderStatusReqDTO;

/**
 * 订单状态履历、订单行状态履历API
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderStatusHistoryAPI.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public interface TradeOrderStatusHistoryAPI {

	/**
	 * 根据订单号查询订单状态履历集合
	  * selectByOrderNoAndStatus(方法的作用)
	  * @Title: selectByOrderNoAndStatus
	  * @param record
	  * @return TradeOrderStatusHistoryListResDTO
	  * @throws
	 */
	public TradeOrderStatusHistoryListResDTO selectHistoryByOrderNo(
			TradeOrderStatusHistoryReqDTO record);

	/**
	 * 更新订单状态、订单行状态、订单状态履历、订单行状态履历
	  * updateOrderStatus(方法的作用)
	  * @Title: updateOrderStatus
	  * @param record
	  * @return UpdateOrderStatusResDTO
	  * @throws
	 */
	UpdateOrderStatusResDTO updateOrderStatus(UpdateOrderStatusReqDTO record);

	/**
	 * 根据订单号查询订单行状态履历集合
	  * selectItemsHistoryByOrderNo(方法的作用)
	  * @Title: selectItemsHistoryByOrderNo
	  * @param record
	  * @return TradeOrderItemStatusHistoryListResDTO
	  * @throws
	 */
	public TradeOrderItemStatusHistoryListResDTO selectItemsHistoryByOrderNo(
			TradeOrderStatusHistoryReqDTO record);
}
