package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;

/**
 * 订单行状态履历dao
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderItemsStatusHistoryDAO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public interface TradeOrderItemsStatusHistoryDAO {
    int deleteByPrimaryKey(Long id);

    int insert(TradeOrderItemsStatusHistoryDMO record);

    int insertSelective(TradeOrderItemsStatusHistoryDMO record);

    TradeOrderItemsStatusHistoryDMO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeOrderItemsStatusHistoryDMO record);

    int updateByPrimaryKey(TradeOrderItemsStatusHistoryDMO record);

    /**
     * 根据订单号查询订单行状态履历集合
      * selectItemsHistoryByOrderNo(方法的作用)
      * @Title: selectItemsHistoryByOrderNo
      * @param orderNo
      * @return List<TradeOrderItemsStatusHistoryDMO>
      * @throws
     */
    List<TradeOrderItemsStatusHistoryDMO> selectItemsHistoryByOrderNo(String orderNo);

    /**
     * 根据订单行号和订单行状态查询数量
      * selectCountByOrderItemNoAndOrderItemStatus(方法的作用)
      * @Title: selectCountByOrderItemNoAndOrderItemStatus
      * @param record
      * @return int
      * @throws
     */
    int selectCountByOrderItemNoAndOrderItemStatus(TradeOrderItemsStatusHistoryDMO record);
}