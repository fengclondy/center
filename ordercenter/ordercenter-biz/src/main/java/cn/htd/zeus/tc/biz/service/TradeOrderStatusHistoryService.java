package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryListDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryListDMO;
import cn.htd.zeus.tc.dto.response.UpdateOrderStatusResDTO;
import cn.htd.zeus.tc.dto.resquest.TradeOrderStatusHistoryReqDTO;
import cn.htd.zeus.tc.dto.resquest.UpdateOrderStatusReqDTO;

/**
 * 订单状态履历及订单行状态履历Service
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderStatusHistoryService.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public interface TradeOrderStatusHistoryService {

	 /**
     * 根据订单号查询订单状态集合
      * selectByOrderNoAndStatus(方法的作用)
      * @Title: selectByOrderNoAndStatus
      * @param record
      * @return List<TradeOrderStatusHistoryDMO>
      * @throws
     */
	TradeOrderStatusHistoryListDMO selectHistoryByOrderNo(TradeOrderStatusHistoryReqDTO record);

    /**
     * 新增订单状态履历
      * insertSelective(方法的作用)
      * @Title: insertSelective
      * @param record
      * @return int
      * @throws
     */
    void insertTradeOrderStatusHistory(TradeOrderStatusHistoryDMO record);

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
     * 根据订单号查询订单行状态集合
      * selectOrderItemsHistoryByOrderNo(方法的作用)
      * @Title: selectOrderItemsHistoryByOrderNo
      * @param record
      * @return TradeOrderItemsStatusHistoryListDMO
      * @throws
     */
    TradeOrderItemsStatusHistoryListDMO selectOrderItemsHistoryByOrderNo(TradeOrderStatusHistoryReqDTO record);
}