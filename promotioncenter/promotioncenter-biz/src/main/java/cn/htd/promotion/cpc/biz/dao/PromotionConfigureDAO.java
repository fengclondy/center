package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;
@Repository("cn.htd.promotion.cpc.biz.dao.promotionConfigureDAO")
public interface PromotionConfigureDAO extends BaseDAO<PromotionConfigureDTO> {
    int deleteByPrimaryKey(Long id);

    void insert(PromotionConfigureDTO record);

    int insertSelective(PromotionConfigureDTO record);

    PromotionConfigureDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionConfigureDTO record);

    int updateByPrimaryKey(PromotionConfigureDTO record);

	List<PromotionConfigureDTO> selectByPromotionId(String promotionInfoId);
	
	void deleteByPromotionId(String promotionId);
	
}