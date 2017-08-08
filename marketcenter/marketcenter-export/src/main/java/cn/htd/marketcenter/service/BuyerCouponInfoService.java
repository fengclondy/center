package cn.htd.marketcenter.service;

import java.util.List;
import java.util.Map;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.marketcenter.dto.BuyerCouponConditionDTO;
import cn.htd.marketcenter.dto.BuyerCouponCountDTO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.BuyerReceiveCouponDTO;
import cn.htd.marketcenter.dto.UsedExpiredBuyerCouponDTO;

public interface BuyerCouponInfoService {

	/**
	 * 运营平台查询会员优惠券列表
	 * 
	 * @param condition
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<BuyerCouponInfoDTO>> queryBuyerCouponList(BuyerCouponConditionDTO condition,
			Pager<BuyerCouponInfoDTO> pager);

	/**
	 * 商城根据优惠券类型查询会员可用优惠券数量
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @return
	 */
	public ExecuteResult<Map<String, Long>> getBuyerUnusedOwnCouponCountByType(String messageId, String buyerCode);

	/**
	 * 商城根据优惠券类型和状态查询会员优惠券数量
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @return
	 */
	public ExecuteResult<List<BuyerCouponCountDTO>> getBuyerOwnCouponCountByStatusType(String messageId,
			String buyerCode);

	/**
	 * 商城查询会员优惠券列表
	 * 
	 * @param messageId
	 * @param condition
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<BuyerCouponInfoDTO>> getBuyerOwnCouponList(String messageId,
			BuyerCouponConditionDTO condition, Pager<BuyerCouponInfoDTO> pager);

	/**
	 * 查询优惠券获取会员信息
	 * 
	 * @param condition
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<BuyerCouponCountDTO>> queryBuyerReceiveCouponList(BuyerCouponInfoDTO condition,
			Pager<BuyerCouponInfoDTO> pager);

	/**
	 * 保存会员领取的优惠券
	 * 
	 * @param messageId
	 * @param receiveDTO
	 * @return
	 */
	public ExecuteResult<String> saveBuyerReceiveCoupon(String messageId, BuyerReceiveCouponDTO receiveDTO);

	/**
	 * 删除已用或已过期的会员优惠券
	 * 
	 * @param messageId
	 * @param targetCouponDTO
	 * @return
	 */
	public ExecuteResult<String> deleteUsedExpiredBuyerCoupon(String messageId,
			UsedExpiredBuyerCouponDTO targetCouponDTO);

}
