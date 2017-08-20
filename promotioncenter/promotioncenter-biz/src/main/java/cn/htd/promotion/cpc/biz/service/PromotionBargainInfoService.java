package cn.htd.promotion.cpc.biz.service;

import java.util.List;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;

public interface PromotionBargainInfoService {

	/**
	 * 查询砍价商品详情
	 * 
	 * @param buyerBargainLaunch
	 * @return
	 */
	public PromotionBargainInfoResDTO getPromotionBargainInfoDetail(
			BuyerBargainLaunchReqDTO buyerBargainLaunch);

	/**
	 * 新增砍价商品
	 * 
	 * @param promotionBargainInfoResDTO
	 */
	public ExecuteResult<List<PromotionBargainInfoResDTO>> addPromotionBargainInfoRedis(
			List<PromotionBargainInfoResDTO> promotionBargainInfoList)throws PromotionCenterBusinessException;
	
	public ExecuteResult<String> deleteBargainInfo(PromotionValidDTO validDTO)throws PromotionCenterBusinessException;

	public ExecuteResult<List<PromotionBargainInfoResDTO>> getPromotionBargainInfoList(
			String messageId, String promotionId) throws PromotionCenterBusinessException;
	
	public ExecuteResult<List<PromotionBargainInfoResDTO>> updateBargainInfo(List<PromotionBargainInfoResDTO> bargainInfoList)
	    	throws PromotionCenterBusinessException;

}
