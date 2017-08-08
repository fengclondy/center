package cn.htd.zeus.tc.biz.service;

import java.util.List;
import java.util.Map;

import cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPDMO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;

public interface ExceptionOrderNoticeService {
	
	/*
	 * 处理五合一异常订单逻辑
	 */
	public void executeFiveAndOneExceptionOrder(List<OrderCompensateERPDMO> tasks);
	
	/*
	 * 五合一异常订单查询
	 */
	public List<OrderCompensateERPDMO> selectErpDistributionExceptionOrdersList(Map paramMap);
	
	/*
	 * 处理收付款异常订单逻辑
	 */
	public void executePostStrikeaExceptionOrder(List<PayOrderInfoDMO> tasks);
	
	/*
	 * 收付款异常订单查询
	 */
	public List<PayOrderInfoDMO> selectPostStrikeaExceptionOrdersList(Map paramMap);
	
	/*
	 * 处理预售下行异常订单逻辑
	 */
	public void executePreSalesOrderExceptionOrder(List<JDOrderInfoDMO> tasks);
	
	/*
	 * 预售下行异常订单查询
	 */
	public List<JDOrderInfoDMO> selectPreSalesOrderExceptionOrdersList(Map paramMap);
}
