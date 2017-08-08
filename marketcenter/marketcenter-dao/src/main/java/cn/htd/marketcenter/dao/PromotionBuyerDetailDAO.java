package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDTO;

/**
 * 促销活动会员明细
 */
public interface PromotionBuyerDetailDAO extends BaseDAO<PromotionBuyerDetailDTO> {

	/**
	 * 根据活动ID获取促销活动会员明细列表
	 * 
	 * @param buyerRule
	 * @return
	 */
	public List<PromotionBuyerDetailDTO> queryByPromotionInfo(PromotionBuyerRuleDTO buyerRule);
}