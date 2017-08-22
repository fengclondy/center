package cn.htd.promotion.api;

import java.util.List;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

public interface BuyerBargainAPI {
	
	/**
	 * 查询我的砍价记录
	 * @param buyerCode
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<List<BuyerLaunchBargainInfoResDTO>> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,String messageId);
	
	/**
	 * 更新我的砍价商品
	 * @param buyerBargainLaunch
	 * @return
	 */
	public ExecuteResult<Boolean> updateBuyerLaunchBargainInfo (BuyerBargainLaunchReqDTO buyerBargainLaunch);

	/**
	 * 发起砍价流程
	 * @param bargainInfoDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<BuyerLaunchBargainInfoResDTO> addBuyerBargainLaunch(BuyerLaunchBargainInfoResDTO bargainInfoDTO, String messageId);

}
