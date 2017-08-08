/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	CreateTradeOrderService.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: 新增订单服务  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.tradecenter.dto.TradeOrderCountDTO;

public interface TradeOrderTodoCountService {

	/**
	 * 根据条件查询订单待办数量
	 * 
	 * @param sellerCode
	 * @return
	 */
	public ExecuteResult<List<TradeOrderCountDTO>> queryOrderStatusCount(String sellerCode);
}
