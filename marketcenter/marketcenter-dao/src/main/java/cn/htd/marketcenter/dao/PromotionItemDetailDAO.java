package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDTO;
import cn.htd.marketcenter.dto.PromotionItemDetailDTO;

public interface PromotionItemDetailDAO extends BaseDAO<PromotionItemDetailDTO> {

	/**
	 * 根据活动ID查询促销活动品类明细
	 * 
	 * @param ruleDTO
	 * @return
	 */
	public List<PromotionItemDetailDTO> queryByPromotionInfo(PromotionCategoryItemRuleDTO ruleDTO);
}