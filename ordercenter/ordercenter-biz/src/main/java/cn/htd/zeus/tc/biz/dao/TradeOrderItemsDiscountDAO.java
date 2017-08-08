package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDiscountDMO;

@Repository("cn.htd.zeus.tc.biz.dao.TradeOrderItemsDiscountDAO")
public interface TradeOrderItemsDiscountDAO {
	/*
	 * 插入订单行优惠信息表
	 */
	public int insertTradeOrderItemsDiscount(TradeOrderItemsDiscountDMO tradeOrderItemsDiscountDMO);

	public List<TradeOrderItemsDiscountDMO> selectBuyerCouponCodeByOrderNo(String orderNo);
	
	public List<TradeOrderItemsDiscountDMO> selectBuyerCouponCodeByOrderItemNo(String orderItemNo);
	
	public int updateTradeOrderItemsDiscount(TradeOrderItemsDiscountDMO tradeOrderItemsDiscountDMO);
}
