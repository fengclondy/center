package cn.htd.promotion.cpc.api;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainOverviewResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;
import cn.htd.promotion.cpc.dto.response.PromotonInfoResDTO;

public interface PromotionBargainInfoAPI {

	public ExecuteResult<PromotionBargainInfoResDTO> getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch);
	
	public ExecuteResult<List<PromotionBargainInfoResDTO>> getPromotionBargainInfoList(String messageId,
            String promotionId);
	
	public ExecuteResult<DataGrid<PromotonInfoResDTO>> queryPromotionInfoListBySellerCode(PromotionInfoReqDTO reqDTO, Pager<PromotionInfoReqDTO> page);
	
	public ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>> queryPromotionBargainOverview(String promotionId, Pager<String> page);
	
    /**
     * 促销活动砍价上下架
     * @param dto
     * @return
     */
    public ExecuteResult<PromotionInfoDTO> upDownShelvesPromotionInfo(PromotionValidDTO dto);
    
	/**
	 * 新增砍价商品
	 * 
	 * @param promotionBargainInfoResDTO
	 */
	public ExecuteResult<PromotionExtendInfoDTO> addPromotionBargainInfo(
			PromotionExtendInfoDTO promotionExtendInfoDTO);
	
	/**
	 * 删除砍价活动
	 * @param validDTO
	 * @return
	 * @throws PromotionCenterBusinessException
	 */
	public ExecuteResult<String> deleteBargainInfo(PromotionValidDTO validDTO);

	/**
	 * 修改砍价活动信息
	 * @param bargainInfoList
	 * @return
	 * @throws PromotionCenterBusinessException
	 */
	public ExecuteResult<PromotionExtendInfoDTO> updateBargainInfo(PromotionExtendInfoDTO promotionExtendInfoDTO);
	
	/**
	 * 砍价活动入口
	 * @param sellerCode
	 * @return
	 */
	public ExecuteResult<List<PromotionInfoDTO>> queryPromotionEntry(String sellerCode);
}
