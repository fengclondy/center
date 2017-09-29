package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.SinglePromotionInfoResDTO;

public interface SinglePromotionInfoMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(SinglePromotionInfoReqDTO record);

    int insertSelective(SinglePromotionInfoReqDTO record);

    SinglePromotionInfoResDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SinglePromotionInfoReqDTO record);

    int updateByPrimaryKey(SinglePromotionInfoReqDTO record);
}