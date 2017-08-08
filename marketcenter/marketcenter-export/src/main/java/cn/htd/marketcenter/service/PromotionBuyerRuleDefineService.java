package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDefineDTO;

/**
 * Created by thinkpad on 2016/12/1.
 */
public interface PromotionBuyerRuleDefineService {

	/**
	 * 新增会员规则
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<String> addPromotionBuyerRule(PromotionBuyerRuleDefineDTO dto);

	/**
	 * 修改会员规则
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<String> updatePromotionBuyerRule(PromotionBuyerRuleDefineDTO dto);

	/**
	 * 根据规则ID查询会员规则详情
	 * 
	 * @param ruleId
	 * @return
	 */
	public ExecuteResult<PromotionBuyerRuleDefineDTO> queryPromotionBuyerRule(Long ruleId);

	/**
	 * 删除会员规则详细信息
	 * 
	 * @param detailDto
	 * @return
	 */
	public ExecuteResult<String> deleteBuyerDetailList(PromotionBuyerDetailDefineDTO detailDto);
}
