package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.biz.dmo.PromotionInfoDMO;

public interface PromotionInfoEditDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PromotionInfoDMO record);

    int insertSelective(PromotionInfoDMO record);

    PromotionInfoDMO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionInfoDMO record);

    int updateByPrimaryKey(PromotionInfoDMO record);
}