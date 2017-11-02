package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.domain.TimelimitedCheckInfo;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 促销活动
 */
public interface PromotionInfoDAO extends BaseDAO<PromotionInfoDTO> {

    /**
     * 根据促销活动ID列表查询促销活动信息
     *
     * @param promotionIdList
     * @return
     */
    public List<PromotionInfoDTO> queryPromotionInfoByIdList(List<String> promotionIdList);

    /**
     * 修改促销活动展示状态(启用,不启用)
     *
     * @param record
     * @return
     */
    public Integer savePromotionValidStatus(PromotionInfoDTO record);

    /**
     * 取得指定类型的进行中或未开始,且期间重复的促销活动列表
     *
     * @param condition
     * @return
     */
    public List<PromotionInfoDTO> queryRepeatTimelimitedList(TimelimitedCheckInfo condition);

    /**
     * 取得定时任务处理对象促销活动
     *
     * @param condition
     * @param page
     * @return
     */
    public List<PromotionInfoDTO> queryPromotionList4Task(@Param("entity") PromotionInfoDTO condition,
            @Param("page") Pager<PromotionInfoDTO> page);

    /**
     * 更新促销活动状态
     *
     * @param promotionInfo
     * @return
     */
    public Integer updatePromotionStatusById(PromotionInfoDTO promotionInfo);

    /**
     * 更新促销活动审核结果
     *
     * @param promotionInfo
     * @return
     */
    public Integer updatePromotionVerifyInfo(PromotionInfoDTO promotionInfo);

    /**
     * 根据促销活动类型查询所有未开始／进行中的促销活动信息
     *
     * @param condition
     * @return
     */
    public List<PromotionInfoDTO> queryPromotionListByType(PromotionInfoDTO condition);

    /**
     * 查询促销活动已经结束，且状态为已开启的活动信息
     *
     * @param condition
     * @param sellerCode
     * @return
     */
    public List<PromotionInfoDTO> queryTimelimitedListBySku(@Param("entity") TimelimitedCheckInfo condition,
            @Param("sellerCode") String sellerCode);

    /**
     * 更新促销活动已经结束，且状态为已开启的活动信息
     *
     * @param promotionIdList
     * @param timelimitedInfoDTO
     * @return
     */
    public void updateTimelimitedListBySku(@Param("promotionIdList") List<String> promotionIdList,
            @Param("entity") TimelimitedInfoDTO timelimitedInfoDTO);

    /**
     * 根据是否已清除Redis标记，查询需要清除的促销活动信息
     *
     * @param condition
     * @param page
     * @return
     */
    public List<PromotionInfoDTO> queryNeedCleanRedisPromotion4Task(@Param("entity") PromotionInfoDTO condition,
            @Param("page") Pager<PromotionInfoDTO> page);

    /**
     * 更新促销活动的清除Redis标记
     *
     * @param promotionInfoDTO
     * @return
     */
    public Integer updateCleanedRedisPromotionStatus(PromotionInfoDTO promotionInfoDTO);

    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170927 start -----
    /**
     * 根据B2C活动编码查询促销活动信息
     *
     * @param promotionInfoDTO
     * @return
     */
    public PromotionInfoDTO queryPromotionInfoByB2cActivityCode(PromotionInfoDTO promotionInfoDTO);
    
    /**
     * 根据B2C活动编码|状态|showStatus查询促销活动信息
     *
     * @param promotionInfoDTO
     * @return
     */
    public PromotionInfoDTO queryPromotionInfoByParam(PromotionInfoDTO promotionInfoDTO);

    /**
     * 取得需要刷新Redis促销信息规则的活动对象
     *
     * @param promotionInfoDTO
     * @return
     */
    public List<PromotionInfoDTO> queryFlushRuleTargetPromotionList(PromotionInfoDTO promotionInfoDTO);
    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170927 end -----
}