package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

public interface PromotionBargainInfoService {
 
	/**
	 * 查询砍价商品详情
	 * @param buyerBargainLaunch
	 * @return
	 */
	public PromotionBargainInfoResDTO getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch);
}
