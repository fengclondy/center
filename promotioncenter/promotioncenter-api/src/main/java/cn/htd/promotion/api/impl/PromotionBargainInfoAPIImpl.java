package cn.htd.promotion.api.impl;

import org.springframework.stereotype.Service;

import cn.htd.promotion.api.PromotionBargainInfoAPI;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

@Service("promotionBargainInfoAPI")
public class PromotionBargainInfoAPIImpl implements  PromotionBargainInfoAPI{

	@Override
	public ExecuteResult<PromotionBargainInfoResDTO> getPromotionBargainInfoDetail(
			BuyerBargainLaunchReqDTO buyerBargainLaunch) {
		
		return null;
	}

}
