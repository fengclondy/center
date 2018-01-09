package cn.htd.promotion.cpc.biz.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

public interface BuyerLaunchBargainInfoService {

	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,String messageId);
	
	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByPromotionId(String promotionId, String messageId);
	
	public ExecuteResult<BuyerLaunchBargainInfoResDTO> addBuyerBargainLaunch(BuyerLaunchBargainInfoResDTO barfainDTO, String messageId)
			throws PromotionCenterBusinessException;
			
	public Integer updateBuyerLaunchBargainInfo(BuyerBargainLaunchReqDTO buyerBargainLaunch);
	
	public BuyerLaunchBargainInfoResDTO getBuyerBargainLaunchInfoByBargainCode(String bargainCode , String messageId);
	
	public Integer getBuyerLaunchBargainInfoNum(String promotionId,String levelCode,String messageId);
	
	public ExecuteResult<DataGrid<BuyerLaunchBargainInfoResDTO>> queryLaunchBargainInfoList(
			BuyerBargainLaunchReqDTO buyerBargainLaunch, Pager<BuyerBargainLaunchReqDTO> page);

	/**
	 * 确认购买
	 * @param promotionId
	 * @param bargainCode
	 * @return
	 */
	public ExecuteResult<String> updateBargainRecord(String promotionId, String bargainCode);

	public ExecuteResult<String> optationbargain(String buyerCode, String promotionId, String levelCode,
			String bargainCode, String helperPicture, String helperName, String openedId, String messageId);
	
}
