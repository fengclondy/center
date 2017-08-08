package cn.htd.zeus.tc.biz.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultDMO;

/**
 * 支付结果
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: OrderPaymentResultDAO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 */
@Repository("cn.htd.zeus.tc.biz.dao.OrderPaymentResultDAO")
public interface OrderPaymentResultDAO {

	/**
	  * 按交易号查询支付成功数量
	  * @Title: seleteCountByTradeNo
	  * @param orderPaymentResultDMO
	  * @return int
	 */
	int seleteCountByTradeNo(OrderPaymentResultDMO orderPaymentResultDMO);

	/**
	  * 根据交易号查询完成支付的订单
	  * @Title: selectPaymentResultByTradeNo
	  * @param orderPaymentResultDMO
	  * @return List<OrderPaymentResultDMO>
	 */
	public List<OrderPaymentResultDMO> selectPaymentResultByOrderNo(OrderPaymentResultDMO orderPaymentResultDMO);

	/**
	  * 支付成功修改订单状态
	  * @Title: updateOrderStatusByTradeNo
	  * @return int
	  * @throws
	 */
	int updateOrderStatusByTradeNo(OrderPaymentResultDMO orderPaymentResultDMO);
	
	/**
	  * 查询交易总金额
	  * @Title: selectSumOrderPayAmountByTradeNo
	  * @param tradeNo
	  * @return BigDecimal
	  * @throws
	 */
	BigDecimal selectSumOrderPayAmountByOrderNo(String orderNo);

}
