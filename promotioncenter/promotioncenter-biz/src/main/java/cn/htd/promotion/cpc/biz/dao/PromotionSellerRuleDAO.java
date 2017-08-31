package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
@Repository("cn.htd.promotion.cpc.biz.dao.promotionSellerRuleDAO")
public interface PromotionSellerRuleDAO extends BaseDAO<PromotionSellerRuleDTO>{
    int deleteByPrimaryKey(Long id);

    void insert(PromotionSellerRuleDTO record);

    int insertSelective(PromotionSellerRuleDTO record);

    PromotionSellerRuleDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionSellerRuleDTO record);

    int updateByPrimaryKey(PromotionSellerRuleDTO record);
}