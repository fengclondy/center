package cn.htd.promotion.cpc.biz.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionInfoDAO")
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
     * @param PromotionInfoResDTO
     * @return
     */
    public Integer updateCleanedRedisPromotionStatus(PromotionInfoDTO PromotionInfoResDTO);

    /**
     * 根据卖家编码获取对应的促销活动集合
     * @param buyerCode
     * @return
     */
	public List<PromotionInfoDTO> queryPromotionInfoListBySellerCode(@Param("dto") PromotionInfoReqDTO dto, @Param("page") Pager<PromotionInfoReqDTO> page);

	public Long queryPromotionInfoCountBySellerCode(@Param("dto") PromotionInfoReqDTO dto);
	
	/** 查询扭蛋活动列表
     *
     * @param PromotionInfoResDTO
     * @return
     */
	public List<PromotionInfoDTO> getPromotionGashaponByInfo(
			@Param("entity") PromotionInfoReqDTO promotionInfoReqDTO,@Param("pager") Pager<PromotionInfoReqDTO> pager);

    /**
     * 查询扭蛋活动列表总数
     *
     * @param PromotionInfoResDTO
     * @return
     */
	public long getTotalPromotionGashaponByInfo(@Param("entity") PromotionInfoReqDTO promotionInfoReqDTO);

	/**
	 * 砍价活动上下架
	 * @param dto
	 */
	public void upDownShelvesBargainInfo(@Param("dto") PromotionValidDTO dto);

	/**
	 * 查询时间段内是否有活动进行
	 * @param promotionProviderSellerCode
	 * @return
	 */
	public Integer queryUpPromotionBargainCount(@Param("sellerCode") String promotionProviderSellerCode,
												@Param("effectiveTime") Date effectiveTime,
												@Param("invalidTime") Date invalidTime,
												@Param("promotionId") String promotionId);
	
    /**
     * 更新活动状态已结束超过24小时的促销活动状态为下架
     *
     * @param promotionInfo
     * @return
     */
    public Integer updatePromotionStatus4InvalidById(PromotionInfoDTO promotionInfo);

    /**
     * 砍价活动入口
     * @param sellerCode
     * @return
     */
	public List<PromotionBargainInfoDMO> queryPromotionBargainEntry(@Param("sellerCode") String sellerCode);

}
