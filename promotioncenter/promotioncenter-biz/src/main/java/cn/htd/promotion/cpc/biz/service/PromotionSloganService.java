package cn.htd.promotion.cpc.biz.service;

import java.util.List;

import cn.htd.promotion.cpc.dto.response.PromotionSloganDTO;

/**
 * 促销活动宣传语接口
 * 
 * @author xmz
 *
 */
public interface PromotionSloganService {

	/**
	 * 查询活动宣传语
	 * @param providerSellerCode
	 *            提供方商家编码
	 * @return
	 * @throws Exception
	 */
	public List<PromotionSloganDTO> queryBargainSloganBySellerCode(
			String providerSellerCode, String messageId) throws Exception;

}
