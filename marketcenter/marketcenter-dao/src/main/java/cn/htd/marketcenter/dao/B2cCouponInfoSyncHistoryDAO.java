package cn.htd.marketcenter.dao;

import cn.htd.marketcenter.dmo.B2cCouponInfoSyncDMO;

public interface B2cCouponInfoSyncHistoryDAO {
    /**
     * 
     * @param record
     * @return
     */
    int saveB2cCouponInfoSyncHistory(B2cCouponInfoSyncDMO record);

    /**
     * 查询最新一条记录信息
     * @param record
     * @return
     */
    B2cCouponInfoSyncDMO queryB2cCouponInfoSyncHistory(B2cCouponInfoSyncDMO record);
    
}