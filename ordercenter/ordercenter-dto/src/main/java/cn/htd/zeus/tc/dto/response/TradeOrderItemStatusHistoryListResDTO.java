package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

/**
 * 订单状态履历ResDTO集合
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderStatusHistoryListResDTO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class TradeOrderItemStatusHistoryListResDTO extends GenricResDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5820822638328158685L;

	private List<TradeOrderItemStatusHistoryResDTO> orderItemsHistoryList;

	public List<TradeOrderItemStatusHistoryResDTO> getOrderItemsHistoryList() {
		return orderItemsHistoryList;
	}

	public void setOrderItemsHistoryList(List<TradeOrderItemStatusHistoryResDTO> orderItemsHistoryList) {
		this.orderItemsHistoryList = orderItemsHistoryList;
	}

}
