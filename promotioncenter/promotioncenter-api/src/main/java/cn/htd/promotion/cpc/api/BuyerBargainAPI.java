package cn.htd.promotion.cpc.api;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
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
	 * 查询我的砍价记录
	 * @param buyerCode
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<DataGrid<BuyerLaunchBargainInfoResDTO>> getBuyerLaunchBargainInfoByCondition(Pager<BuyerBargainLaunchReqDTO> pager ,BuyerBargainLaunchReqDTO BuyerBargainLaunch);
	
	/**
	 * 根据活动id和层级编码查询已经砍完的商品集合
	 * @param buyerCode
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<Integer> getBuyerLaunchBargainInfoNum(String promotionId,String levelCode,String messageId);
	
	/**
	 * 更新我的砍价商品
	 * @param buyerBargainLaunch
	 * @return
	 */
	public ExecuteResult<Boolean> updateBuyerLaunchBargainInfo (BuyerBargainLaunchReqDTO buyerBargainLaunch);
	
	/**
	 * 根据砍价编码查询砍价商品详情
	 * @param bargainCode
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<BuyerLaunchBargainInfoResDTO> getBuyerBargainLaunchInfoByBargainCode(String bargainCode , String messageId);

	/**
	 * 发起砍价流程
	 * @param bargainInfoDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<BuyerLaunchBargainInfoResDTO> addBuyerBargainLaunch(BuyerLaunchBargainInfoResDTO bargainInfoDTO, String messageId);
	
	/**
	 * 查询砍价信息
	 * @param buyerBargainLaunch
	 * @return
	 */
	public ExecuteResult<DataGrid<BuyerLaunchBargainInfoResDTO>> queryLaunchBargainInfoList(BuyerBargainLaunchReqDTO buyerBargainLaunch, Pager<BuyerBargainLaunchReqDTO> page);


}
