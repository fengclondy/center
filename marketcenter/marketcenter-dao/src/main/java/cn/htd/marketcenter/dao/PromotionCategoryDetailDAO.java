package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionCategoryDetailDTO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDTO;

public interface PromotionCategoryDetailDAO extends BaseDAO<PromotionCategoryDetailDTO> {

	/**
	 * 根据活动ID查询促销活动品类明细
	 * 
	 * @param ruleDTO
	 * @return
	 */
	public List<PromotionCategoryDetailDTO> queryByPromotionInfo(PromotionCategoryItemRuleDTO ruleDTO);
}