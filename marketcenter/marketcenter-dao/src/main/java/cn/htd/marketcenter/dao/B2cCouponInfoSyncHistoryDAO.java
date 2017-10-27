package cn.htd.marketcenter.dao;

import java.util.List;

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
    public List<B2cCouponInfoSyncDMO> queryNeedDealB2cCouponInfoByB2cActivityCode(B2cCouponInfoSyncDMO targetInfo);

    /**
     * 更新触发返券活动处理失败
     * @param targetInfo
     * @return
     */
    public Integer updateB2cCouponInfoDealFailResult(B2cCouponInfoSyncDMO targetInfo);

    /**
     * 更新触发返券活动处理成功
     * @param targetInfo
     * @return
     */
    public Integer updateB2cCouponInfoDealSuccessResult(B2cCouponInfoSyncDMO targetInfo);

    public  Integer updateB2cCouponInfo4Test();
}