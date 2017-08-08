package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDefineDTO;
import cn.htd.marketcenter.dto.PromotionItemDetailDefineDTO;

public interface PromotionCategoryItemRuleDefineService {
	/**
	 * 增加记录
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<String> addPromotionCategoryItemRule(PromotionCategoryItemRuleDefineDTO dto);

	/**
	 * 修改记录
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<String> updatePromotionCategoryItemRule(PromotionCategoryItemRuleDefineDTO dto);

	/**
	 * 查询记录
	 * 
	 * @param ruleId
	 * @return
	 */
	public ExecuteResult<PromotionCategoryItemRuleDefineDTO> queryPromotionCategoryItemRule(Long ruleId);

	/**
	 * 删除商品详细信息
	 * 
	 * @param detailDto
	 * @return
	 */
	public ExecuteResult<String> deleteItemDetailList(PromotionItemDetailDefineDTO detailDto);
}
