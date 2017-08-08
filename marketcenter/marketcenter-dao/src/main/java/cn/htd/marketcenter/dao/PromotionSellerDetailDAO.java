package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionSellerDetailDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDTO;

public interface PromotionSellerDetailDAO extends BaseDAO<PromotionSellerDetailDTO> {

	/**
	 * 查询促销活动卖家规则
	 * 
	 * @param sellerRule
	 * @return
	 */
	public List<PromotionSellerDetailDTO> queryByPromotionInfo(PromotionSellerRuleDTO sellerRule);

}
