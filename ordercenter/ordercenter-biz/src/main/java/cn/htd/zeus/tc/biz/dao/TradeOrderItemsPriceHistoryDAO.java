package cn.htd.zeus.tc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsPriceHistoryDMO;


@Repository("cn.htd.zeus.tc.biz.dao.TradeOrderItemsPriceHistoryDAO")
public interface TradeOrderItemsPriceHistoryDAO {
    
	public Integer queryOrderItemsPriceHistoryCount(
			TradeOrderItemsPriceHistoryDMO tradeOrderItemsPriceHistoryDMO);
}