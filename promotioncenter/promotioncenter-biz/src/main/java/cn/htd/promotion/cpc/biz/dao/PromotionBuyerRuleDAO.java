package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.response.PromotionBuyerRuleDTO;
@Repository("cn.htd.promotion.cpc.biz.dao.promotionBuyerRuleDAO")
public interface PromotionBuyerRuleDAO extends BaseDAO<PromotionBuyerRuleDTO>{
    int deleteByPrimaryKey(Long id);

    void insert(PromotionBuyerRuleDTO record);

    int insertSelective(PromotionBuyerRuleDTO record);

    PromotionBuyerRuleDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionBuyerRuleDTO record);

    int updateByPrimaryKey(PromotionBuyerRuleDTO record);

	PromotionBuyerRuleDTO selectByPromotionInfoId(String promotionId);
}