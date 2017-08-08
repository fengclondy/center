package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;

/**
 * 订单状态履历表DAO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderStatusHistoryDAO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public interface TradeOrderStatusHistoryDAO {
    int deleteByPrimaryKey(Long id);

    int insert(TradeOrderStatusHistoryDMO record);

    int insertSelective(TradeOrderStatusHistoryDMO record);

    TradeOrderStatusHistoryDMO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeOrderStatusHistoryDMO record);

    int updateByPrimaryKey(TradeOrderStatusHistoryDMO record);

    /**
     * 根据订单号查询订单状态履历集合
      * selectByOrderNoAndStatus(方法的作用)
      * @Title: selectByOrderNoAndStatus
      * @param record
      * @return List<TradeOrderStatusHistoryDMO>
      * @throws
     */
    List<TradeOrderStatusHistoryDMO> selectHistoryByOrderNo(TradeOrderStatusHistoryDMO record);

    /**
     * 根据订单号和订单状态查询数据数量
      * selectCountByOrderNoAndOrderStatus(方法的作用)
      * @Title: selectCountByOrderNoAndOrderStatus
      * @param record
      * @return int
      * @throws
     */
    int selectCountByOrderNoAndOrderStatus(TradeOrderStatusHistoryDMO record);
}