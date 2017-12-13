package cn.htd.zeus.tc.biz.service;

import java.util.List;
import java.util.Map;

import cn.htd.zeus.tc.biz.dmo.TradeOrderConsigneeDownInfoDMO;

public interface OrderConsigneeDownService {
	
	/**
	 * 查询已收货没有下行成功(待下行|下行失败)的数据
	 * @param paramMap
	 * @return
	 */
	public List<TradeOrderConsigneeDownInfoDMO> selectTradeOrderConsigneeDownInfoList(
			Map paramMap);
	
	/**
	 * 已收货订单下行erp逻辑处理
	 * @param tasks
	 */
	public void orderConsigneeDownERP(TradeOrderConsigneeDownInfoDMO[] tasks);
}
