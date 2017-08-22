package cn.htd.promotion.api;

import java.util.List;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

public interface PromotionBargainInfoAPI {

	public ExecuteResult<PromotionBargainInfoResDTO> getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch);
	
	public ExecuteResult<List<PromotionBargainInfoResDTO>> getPromotionBargainInfoList(String messageId,
            String promotionId);
	
}
