package cn.htd.marketcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDTO;

public interface PromotionBuyerRuleDAO extends BaseDAO<PromotionBuyerRuleDTO> {
	/**
	 * 根据活动ID查询促销活动会员规则信息
	 * 
	 * @param buyerRule
	 * @return
	 */
	public PromotionBuyerRuleDTO queryByPromotionInfo(PromotionBuyerRuleDTO buyerRule);
}