package cn.htd.zeus.tc.biz.dmo;

import java.util.List;

/**
 * 订单状态履历DMO集合
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderStatusHistoryListDMO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class TradeOrderStatusHistoryListDMO extends GenericDMO{

	/**
	 *
	 */
	private static final long serialVersionUID = -447054551808022366L;

	private List<TradeOrderStatusHistoryDMO> historyList;

	public List<TradeOrderStatusHistoryDMO> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<TradeOrderStatusHistoryDMO> historyList) {
		this.historyList = historyList;
	}

}
