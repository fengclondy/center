package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.B2cCouponUseLogSyncDTO;

public interface B2cCouponUseLogSyncService {
	
	/**
	 * 粉丝用券同步券到促销中心
	 * 用券场景标识：在线支付订单、货到付款订单确认，在线支付订单取消确认后
	 * @param b2cCouponUseLogSyncDTO
	 * @return
	 */
	public ExecuteResult<String> saveB2cCouponUseLogSync(B2cCouponUseLogSyncDTO b2cCouponUseLogSyncDTO);
}
