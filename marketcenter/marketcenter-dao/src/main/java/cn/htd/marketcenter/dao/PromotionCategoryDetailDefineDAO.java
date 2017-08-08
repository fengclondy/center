package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionCategoryDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDefineDTO;

public interface PromotionCategoryDetailDefineDAO extends BaseDAO<PromotionCategoryDetailDefineDTO> {

	/**
	 * 根据ruleId修改删除标志
	 */
	public Long deleteByRuleId(PromotionCategoryItemRuleDefineDTO dto);

	/**
	 * 根据规则ID取得类目品牌列表
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<PromotionCategoryDetailDefineDTO> queryCategoryDetailList(Long ruleId);
}
