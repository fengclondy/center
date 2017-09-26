package cn.htd.marketcenter.dao;

import cn.htd.marketcenter.dmo.B2cCouponUseLogSyncDMO;


public interface B2cCouponUseLogSyncHistoryDAO {
	 
	/**
     * 
     * @param record
     * @return
     */
    int saveB2cCouponUseLogSyncHistory(B2cCouponUseLogSyncDMO record);
}