package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.response.PromotionPictureDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionPictureDAO")
public interface PromotionPictureDAO extends BaseDAO<PromotionPictureDTO> {
	int deleteByPrimaryKey(Long id);

	void insert(PromotionPictureDTO record);

	int insertSelective(PromotionPictureDTO record);

	PromotionPictureDTO selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(PromotionPictureDTO record);

	int updateByPrimaryKey(PromotionPictureDTO record);
}