package cn.htd.zeus.tc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.TradeOrderErpDistributionLogDMO;

@Repository("cn.htd.zeus.tc.biz.dao.TradeOrderErpDistributionLogDAO")
public interface TradeOrderErpDistributionLogDAO {

    int insertErpDistributionLog(TradeOrderErpDistributionLogDMO tradeOrderErpDistributionLogDMO);

    long selectErpDistributionLogByPrimaryKey(Long erpDistributionId);

    int updateErpDistributionLogByPrimaryKey(TradeOrderErpDistributionLogDMO tradeOrderErpDistributionLogDMO);
}