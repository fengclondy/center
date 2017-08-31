package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
@Repository("cn.htd.promotion.cpc.biz.dao.promotionSellerDetailDAO")
public interface PromotionSellerDetailDAO  extends BaseDAO<PromotionSellerDetailDTO>{
    int deleteByPrimaryKey(Long id);

    void insert(PromotionSellerDetailDTO record);

    int insertSelective(PromotionSellerDetailDTO record);

    PromotionSellerDetailDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionSellerDetailDTO record);

    int updateByPrimaryKey(PromotionSellerDetailDTO record);
}