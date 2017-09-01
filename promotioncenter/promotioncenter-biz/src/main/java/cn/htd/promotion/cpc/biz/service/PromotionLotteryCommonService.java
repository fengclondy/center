package cn.htd.promotion.cpc.biz.service;

import java.util.Map;

import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;

public interface PromotionLotteryCommonService {
	/**
	 * 取得Redis抽奖活动信息
	 * @param promotionId
	 * @param dictMap
	 * @return
	 */
	public PromotionExtendInfoDTO getRedisLotteryInfo(String promotionId, Map<String, String> dictMap);
	
	/**
	 * 
	 * @param promotionInfoDTO
	 * @param requestDTO
	 * @param dictMap
	 * @return
	 */
	public boolean checkPromotionLotteryValid(PromotionExtendInfoDTO promotionInfoDTO, DrawLotteryReqDTO requestDTO,
            Map<String, String> dictMap);
}
