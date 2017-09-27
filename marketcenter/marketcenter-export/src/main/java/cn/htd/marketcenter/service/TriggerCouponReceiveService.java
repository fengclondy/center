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
	//----- discard by jiangkun for 2017活动需求商城无敌券 on 20170927 start -----
	@Deprecated
	public ExecuteResult<String> saveReceiveTriggerCoupon(String messageId, ReceiveTriggerCouponDTO triggerDTO);
	//----- discard by jiangkun for 2017活动需求商城无敌券 on 20170927 end -----
}
