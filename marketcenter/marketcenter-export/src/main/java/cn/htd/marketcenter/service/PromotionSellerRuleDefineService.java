package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.PromotionSellerDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDefineDTO;

/**
 * Created by thinkpad on 2016/12/1.
 */
public interface PromotionSellerRuleDefineService {
	/**
	 * 增加记录
	 */
	public ExecuteResult<String> addPromotionSellerRule(PromotionSellerRuleDefineDTO dto);

	/**
	 * 根据条件修改记录
	 */
	public ExecuteResult<String> updatePromotionSellerRule(PromotionSellerRuleDefineDTO dto);

	/**
	 * 根据ID查询记录
	 */
	public ExecuteResult<PromotionSellerRuleDefineDTO> queryPromotionSellerRule(Long ruleId);

	/**
	 * 删除卖家规则的指定供应商信息
	 * 
	 * @param detailDto
	 * @return
	 */
	public ExecuteResult<String> deleteSellerDetailList(PromotionSellerDetailDefineDTO detailDto);

}
