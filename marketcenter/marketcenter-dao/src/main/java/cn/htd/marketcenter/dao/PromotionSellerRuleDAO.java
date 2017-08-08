package cn.htd.marketcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDTO;

public interface PromotionSellerRuleDAO extends BaseDAO<PromotionSellerRuleDTO> {
	/**
	 * 根据活动ID查询促销活动供应商规则信息
	 * 
	 * @param sellerRule
	 * @return
	 */
	public PromotionSellerRuleDTO queryByPromotionInfo(PromotionSellerRuleDTO sellerRule);

}