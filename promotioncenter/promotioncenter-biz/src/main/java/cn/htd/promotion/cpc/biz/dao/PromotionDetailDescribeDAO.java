package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionDetailDescribeDMO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionDetailDescribeDAO")
public interface PromotionDetailDescribeDAO extends BaseDAO<PromotionDetailDescribeDMO> {
	
    int deleteByPrimaryKey(Long id);

    void insert(PromotionDetailDescribeDMO record);

    int insertSelective(PromotionDetailDescribeDMO record);

    PromotionDetailDescribeDMO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionDetailDescribeDMO record);

    int updateByPrimaryKeyWithBLOBs(PromotionDetailDescribeDMO record);

    int updateByPrimaryKey(PromotionDetailDescribeDMO record);
    
    PromotionDetailDescribeDMO selectByPromotionId(PromotionDetailDescribeDMO record);
}