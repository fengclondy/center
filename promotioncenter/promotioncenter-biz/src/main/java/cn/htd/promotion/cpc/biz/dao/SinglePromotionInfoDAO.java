package cn.htd.promotion.cpc.biz.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.SinglePromotionInfoResDTO;

public interface SinglePromotionInfoDAO {
	
    int deleteByPrimaryKey(Long id);

    int insert(SinglePromotionInfoReqDTO record);

    int insertSelective(SinglePromotionInfoReqDTO record);
    
    int addPromotionInfo(SinglePromotionInfoReqDTO record);

    SinglePromotionInfoResDTO selectByPrimaryKey(Long id);
    
    SinglePromotionInfoResDTO selectByPromotionId(String promotionId);

    int updateByPrimaryKeySelective(SinglePromotionInfoReqDTO record);
    
    int updatePromotionInfo(SinglePromotionInfoReqDTO record);

    int updateByPrimaryKey(SinglePromotionInfoReqDTO record);
    
	/**
	 * 活动上下架
	 * @param dto
	 */
    int upDownShelvesPromotionInfo(@Param("dto") SinglePromotionInfoReqDTO dto);
	
}