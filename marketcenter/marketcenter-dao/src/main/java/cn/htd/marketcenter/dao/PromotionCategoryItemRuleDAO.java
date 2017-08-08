package cn.htd.marketcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDTO;

/**
 * 促销活动品类商品规则
 */
public interface PromotionCategoryItemRuleDAO extends BaseDAO<PromotionCategoryItemRuleDTO> {
	/**
	 * 根据活动ID查询促销活动品类商品规则
	 * 
	 * @param categoryItemRule
	 * @return
	 */
	public PromotionCategoryItemRuleDTO queryByPromotionInfo(PromotionCategoryItemRuleDTO categoryItemRule);

}