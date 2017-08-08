package cn.htd.zeus.tc.biz.dmo;

import java.util.List;

/**
 * 订单行状态履历DMO集合
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderItemsStatusHistoryListDMO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class TradeOrderItemsStatusHistoryListDMO extends GenericDMO{

	/**
	 *
	 */
	private static final long serialVersionUID = -447054551808022366L;

	private List<TradeOrderItemsStatusHistoryDMO> orderItemsHistoryList;

	public List<TradeOrderItemsStatusHistoryDMO> getOrderItemsHistoryList() {
		return orderItemsHistoryList;
	}

	public void setOrderItemsHistoryList(List<TradeOrderItemsStatusHistoryDMO> orderItemsHistoryList) {
		this.orderItemsHistoryList = orderItemsHistoryList;
	}

}
