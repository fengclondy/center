package cn.htd.marketcenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.PromotionGroupSignUpDTO;
import cn.htd.marketcenter.dto.PromotionGroupSignUpRepairDTO;

public interface PromotionGroupService {

	/**
	 * 团购会员报名信息创建
	 * 
	 * @param productGroupSignUpDTO
	 * @return
	 */
	public ExecuteResult<Boolean> insertPromotionGroupSignUpInfo(PromotionGroupSignUpDTO productGroupSignUpDTO);

	/**
	 * 团购活动信息查询
	 * 
	 * @param productGroupSignUpDTO
	 * @return
	 */
	public ExecuteResult<PromotionGroupSignUpDTO> queryPromotionGroupSignUpInfo(
			PromotionGroupSignUpDTO productGroupSignUpDTO);
	/**
	 * 修正团购报名信息
	 * 
	 * @param promotionGroupSignUpRepairDTO
	 * @return
	 */
	public ExecuteResult<String> repairPromotionGroupSignUpInfo(List<PromotionGroupSignUpRepairDTO> promotionGroupSignUpRepairDTOList);
}
