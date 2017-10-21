package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.domain.BuyerCouponCondition;
import cn.htd.marketcenter.dto.BuyerCouponConditionDTO;
import cn.htd.marketcenter.dto.BuyerCouponCountDTO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;
import org.apache.ibatis.annotations.Param;

public interface BuyerCouponInfoDAO extends BaseDAO<BuyerCouponInfoDTO> {

    /**
     * 查询会员优惠券件数
     *
     * @param condition
     * @return
     */
    public Long queryBuyerCouponCount(@Param("entity") BuyerCouponConditionDTO condition);

    /**
     * 查询会员优惠券列表
     *
     * @param condition
     * @return
     */
    public List<BuyerCouponInfoDTO> queryBuyerCouponList(@Param("entity") BuyerCouponConditionDTO condition,
                                                         @Param("page") Pager<BuyerCouponInfoDTO> pager);

    /**
     * 查询领取优惠券会员数量
     *
     * @param condition
     * @return
     */
    public Long queryBuyerReceiveCouponCount(@Param("entity") BuyerCouponInfoDTO condition);

    /**
     * 查询领取优惠券会员列表
     *
     * @param condition
     * @param pager
     * @return
     */
    public List<BuyerCouponCountDTO> queryBuyerReceiveCouponList(@Param("entity") BuyerCouponInfoDTO condition,
                                                                 @Param("page") Pager<BuyerCouponInfoDTO> pager);

    /**
     * 根据优惠券号取得会员优惠券信息
     *
     * @param couponCondition
     * @return
     */
    public BuyerCouponInfoDTO queryCouponInfoByCode(BuyerCouponInfoDTO couponCondition);

    /**
     * 更新会员优惠券使用信息
     *
     * @param couponInfo
     * @return
     */
    public int updateBuyerCouponUseInfo(BuyerCouponInfoDTO couponInfo);

    /**
     * 过期会员优惠券更新用任务
     *
     * @param condition
     * @param pager
     * @return
     */
    public List<BuyerCouponInfoDTO> queryBuyerCoupon4Task(@Param("entity") BuyerCouponCondition condition,
                                                          @Param("page") Pager<BuyerCouponInfoDTO> pager);

    /**
     * 过期3个月以上的会员优惠券清除Redis任务
     *
     * @param condition
     * @param pager
     * @return
     */
    public List<BuyerCouponInfoDTO> queryNeedCleanRedisBuyerCoupon4Task(@Param("entity") BuyerCouponCondition condition,
                                                                        @Param("page") Pager<BuyerCouponInfoDTO> pager);

    /**
     * 更新会员优惠券Redis清理状态
     *
     * @param couponInfo
     * @return
     */
    public Long updateBuyerCouponCleanRedisStatus(BuyerCouponInfoDTO couponInfo);

    /**
     * 添加会员优惠券信息
     *
     * @param couponInfo
     * @return
     */
    public int addBuyerCouponInfo(BuyerCouponInfoDTO couponInfo);

    /**
     * 根据返券活动信息取得领券的会员优惠券数量
     * @param discountDTO
     * @return
     */
    public Long queryBuyerCouponCountByDiscountInfo(PromotionDiscountInfoDTO discountDTO);

    /**
     * 根据返券活动信息取得领券的会员优惠券信息
     *
     * @param discountDTO
     * @param pager
     * @return
     */
    public List<BuyerCouponInfoDTO> queryBuyerCouponListByDiscountInfo(@Param("entity") PromotionDiscountInfoDTO discountDTO,
                                                                       @Param("page") Pager<PromotionDiscountInfoDTO> pager);
    /**
     * 根据促销活动id和会员店编码查询会员优惠券号
     * @param couponInfo
     * @return
     */
    public BuyerCouponInfoDTO queryBuyerCouponByBuyerCodeAndPomotionId(BuyerCouponInfoDTO couponInfo);
    
    public int updateBuyerCouponByBuyerCodeCouponCodeAndPomotionId(BuyerCouponInfoDTO couponInfo);

    /**
     * 根据同步B2C活动信息更新会员优惠券的
     * @param buyerCouponInfoDTO
     * @return
     */
    public Long updateB2cActivityInfo2BuyerCoupon(BuyerCouponInfoDTO buyerCouponInfoDTO);
}
