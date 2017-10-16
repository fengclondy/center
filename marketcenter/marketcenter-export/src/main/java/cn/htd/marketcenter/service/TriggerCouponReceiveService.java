package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.ReceiveTriggerCouponDTO;

public interface TriggerCouponReceiveService {

	/**
	 * 保存小B收到C端优惠券券记录
	 * 
	 * @param messageId
	 * @param triggerDTO
	 * @return
	 */
	public ExecuteResult<String> saveReceiveTriggerCoupon(String messageId, ReceiveTriggerCouponDTO triggerDTO);

}
