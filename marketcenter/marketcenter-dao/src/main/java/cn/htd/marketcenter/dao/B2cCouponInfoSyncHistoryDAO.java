package cn.htd.marketcenter.dao;

import cn.htd.marketcenter.dmo.B2cCouponInfoSyncDMO;
import cn.htd.marketcenter.dmo.B2cCouponUseLogSyncDMO;

public interface B2cCouponInfoSyncHistoryDAO {

    /**
     * 
     * @param record
     * @return
     */
    public int saveB2cCouponInfoSyncHistory(B2cCouponInfoSyncDMO record);

    /**
     * 查询最新一条记录信息
     * @param record
     * @return
     */
    public B2cCouponInfoSyncDMO queryB2cCouponInfoSyncHistory(B2cCouponInfoSyncDMO record);

    /**
     * 根据B2C活动编号查询最新的待处理记录
     * @param targetInfo
     * @return
     */
    public B2cCouponInfoSyncDMO queryLastestB2cCouponInfoByB2cActivityCode(B2cCouponInfoSyncDMO targetInfo);
    
}