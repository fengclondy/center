package cn.htd.promotion.cpc.biz.service;

import java.util.List;

import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

public interface PromotionBargainInfoService {

	/**
	 * 查询砍价活动信息表
	 * 
	 * @param promotionId
	 *            促销活动id
	 * @return
	 */
	public List<PromotionBargainInfoResDTO> queryPromotionBargainInfoByPomotionId(
			String promotionId, String messageId) throws Exception;
}
