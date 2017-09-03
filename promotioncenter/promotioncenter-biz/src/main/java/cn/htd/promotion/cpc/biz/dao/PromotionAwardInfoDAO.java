package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardInfoDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionAwardInfoDAO")
public interface PromotionAwardInfoDAO extends BaseDAO<PromotionAwardInfoDTO> {
	public int deleteByPrimaryKey(Long awardId);

	public void add(PromotionAwardInfoDTO record);

	public int insertSelective(PromotionAwardInfoDTO record);

	public PromotionAwardInfoDTO selectByPrimaryKey(Long awardId);

	public int updateByPrimaryKeySelective(PromotionAwardInfoDTO record);

	public int updateByPrimaryKey(PromotionAwardInfoDTO record);

	public PromotionAwardInfoDTO queryByPIdAndLevel(PromotionAwardInfoDTO pai);
}