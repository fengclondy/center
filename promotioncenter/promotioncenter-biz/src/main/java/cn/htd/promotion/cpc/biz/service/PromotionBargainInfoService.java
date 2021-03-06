package cn.htd.promotion.cpc.biz.service;

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
	public ExecuteResult<PromotionExtendInfoDTO> addPromotionBargainInfo(
            PromotionExtendInfoDTO promotionExtendInfoDTO) throws PromotionCenterBusinessException;
	
	/**
	 * 删除砍价活动
	 * @param validDTO
	 * @return
	 * @throws PromotionCenterBusinessException
	 */
	public ExecuteResult<String> deleteBargainInfo(PromotionValidDTO validDTO)throws PromotionCenterBusinessException;

	/**
	 * 获取砍价活动信息
	 * @param dto
	 * @throws PromotionCenterBusinessException
	 */
	public ExecuteResult<List<PromotionBargainInfoResDTO>> getPromotionBargainInfoList(
			PromotionBargainInfoResDTO dto) throws PromotionCenterBusinessException;
	
	/**
	 * 修改砍价活动信息
	 * @param promotionExtendInfoDTO
	 * @return
	 * @throws PromotionCenterBusinessException
	 */
	public ExecuteResult<PromotionExtendInfoDTO> updateBargainInfo(PromotionExtendInfoDTO promotionExtendInfoDTO)
	    	throws PromotionCenterBusinessException;

	/**
	 * 根据卖家编码获取对应的促销活动信息
	 * @param buyerCode
	 * @return
	 * @throws PromotionCenterBusinessException
	 */
	public ExecuteResult<DataGrid<PromotonInfoResDTO>> queryPromotionInfoListBySellerCode(
			PromotionInfoReqDTO reqDTO, Pager<PromotionInfoReqDTO> page) throws PromotionCenterBusinessException;

	/**
	 * 根据卖家编码获取对应的促销活动商品概览
	 * @param sellerCode
	 * @return
	 */
	public ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>> queryPromotionBargainOverview(
			String promotionId,  Pager<String> page);
	
    
    /**
     * 促销活动砍价上下架
     * @param dto
     * @return
     */
    public ExecuteResult<PromotionInfoDTO> upDownShelvesPromotionInfo(PromotionValidDTO dto) throws PromotionCenterBusinessException;

    /**
     * 砍价活动入口
     * @param dto
     * @return
     * @throws PromotionCenterBusinessException
     */
	public ExecuteResult<List<PromotionInfoDTO>> queryPromotionInfoEntry(
			PromotionInfoReqDTO dto) throws PromotionCenterBusinessException;
    
}
