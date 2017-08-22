package cn.htd.promotion.cpc.biz.service;

import java.util.List;

import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionSloganResDTO;

public interface PromotionSloganService {

	/**
	 * 查询活动宣传语
	 * 
	 * @param providerSellerCode
	 *            提供方商家编码
	 * @return
	 * @throws Exception
	 */
	public  ExecuteResult<List<PromotionSloganResDTO>> queryBargainSloganBySellerCode(
			String providerSellerCode, String messageId) throws PromotionCenterBusinessException;
}
