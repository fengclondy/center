package cn.htd.zeus.tc.biz.service;

import java.util.List;
import java.util.Map;

import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.dto.resquest.PostStrikeaBalanceReqDTO;

public interface PostStrikeaBalanceService {
	
	/*
	 * 查询收付款待下行信息表
	 */
	public List<PayOrderInfoDMO> selectPayOrderFromPayOrderInfo(Map paramMap);

	//发送MQ
	void postStrikeaBalance(PostStrikeaBalanceReqDTO postStrikeaBalanceReqDTO);
	
	/*
	 * 执行下行逻辑
	 */
	public void executeDownPostStrikeaBalance(PayOrderInfoDMO[] tasks);
}
