package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDefineDTO;

public interface PromotionBuyerDetailDefineDAO extends BaseDAO<PromotionBuyerDetailDefineDTO> {

	/**
	 * 按照ID删除会员详细信息
	 * 
	 * @param detailDTO
	 * @return
	 */
	public Long deleteDetailList(PromotionBuyerDetailDefineDTO detailDTO);

	/**
	 * 按照规则ID删除
	 * 
	 * @param ruleDTO
	 * @return
	 */
	public Long deleteByRuleId(PromotionBuyerRuleDefineDTO ruleDTO);

	/**
	 * 取得规则详细会员信息
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<PromotionBuyerDetailDefineDTO> queryBuyerDetailList(Long ruleId);
}
