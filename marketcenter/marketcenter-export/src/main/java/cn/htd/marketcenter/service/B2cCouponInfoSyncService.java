package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.B2cCouponInfoSyncDTO;

/**
 * B2C无敌券信息同步接口
 * @author admin
 *
 */
public interface B2cCouponInfoSyncService {
	
	/**
	 * B2C无敌券信息同步接口
	 * @param b2cCouponInfoSyncDTO
	 * @return
	 */
	public ExecuteResult<String> saveB2cCouponInfoSync(B2cCouponInfoSyncDTO b2cCouponInfoSyncDTO);
}
