package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDefineDTO;
import cn.htd.marketcenter.dto.PromotionItemDetailDefineDTO;

public interface PromotionItemDetailDefineDAO extends BaseDAO<PromotionItemDetailDefineDTO> {

	/**
	 * 根据ruleId修改删除标志
	 */
	public Long deleteByRuleId(PromotionCategoryItemRuleDefineDTO dto);

	/**
	 * 按照ID删除品类详细信息
	 * 
	 * @param dto
	 * @return
	 */
	public Long deleteDetailList(PromotionItemDetailDefineDTO dto);

	/**
	 * 根据规则ID查询商品详细信息
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<PromotionItemDetailDefineDTO> queryItemDetailList(Long ruleId);
}
