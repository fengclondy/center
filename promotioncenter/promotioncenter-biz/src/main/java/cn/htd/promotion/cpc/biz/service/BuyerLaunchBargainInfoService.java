package cn.htd.promotion.cpc.biz.service;

import java.util.List;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

public interface BuyerLaunchBargainInfoService {

	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,String messageId);
	
	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByPromotionId(String promotionId, String messageId);
	
	public ExecuteResult<BuyerLaunchBargainInfoResDTO> addBuyerBargainLaunch(BuyerLaunchBargainInfoResDTO barfainDTO, String messageId)
			throws PromotionCenterBusinessException;;
			
	public Integer updateBuyerLaunchBargainInfo(BuyerBargainLaunchReqDTO buyerBargainLaunch);
	
	public BuyerLaunchBargainInfoResDTO getBuyerBargainLaunchInfoByBargainCode(String bargainCode , String messageId);
	
	public Integer getBuyerLaunchBargainInfoNum(String promotionId,String levelCode,String messageId);
	
}
