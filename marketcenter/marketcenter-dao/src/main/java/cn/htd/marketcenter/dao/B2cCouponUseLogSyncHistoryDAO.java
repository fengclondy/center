package cn.htd.marketcenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.marketcenter.dmo.B2cCouponUseLogSyncDMO;


public interface B2cCouponUseLogSyncHistoryDAO {
	 
	/**
     * 
     * @param record
     * @return
     */
    int saveB2cCouponUseLogSyncHistory(B2cCouponUseLogSyncDMO record);
    
    /**
     * 查询deal_flag=0的数据
     * @param paramMap
     * @return
     */
    List<B2cCouponUseLogSyncDMO> queryB2cCouponUseLogSyncHistoryList(Map paramMap);
    
    /**
     * 查询优惠券用券数量
     * @param record
     * @return
     */
    List<B2cCouponUseLogSyncDMO> queryB2cCouponUseLogSyncHistoryUseCouponCount(B2cCouponUseLogSyncDMO record);
    
    /**
     * 更新b2c_coupon_use_log_sync_history表
     * @param record
     * @return
     */
    int updateB2cCouponUseLogSyncHistory(B2cCouponUseLogSyncDMO record);
}