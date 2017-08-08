package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;

public interface TradeOrderItemStatusHistoryService {
	  /**
     * 新增订单行状态履历
      * insertSelective(方法的作用)
      * @Title: insertSelective
      * @param record
      * @return int
      * @throws
     */
    void insertTradeOrderItemStatusHistory(TradeOrderItemsStatusHistoryDMO record);
}
