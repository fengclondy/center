package cn.htd.marketcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.marketcenter.dto.DiscountConditionDTO;
import cn.htd.marketcenter.dto.ModifyPromotionCouponInfoDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;
import cn.htd.marketcenter.dto.PromotionValidDTO;
import cn.htd.marketcenter.dto.VerifyInfoDTO;

/**
 * 优惠券活动信息
 */
public interface DiscountInfoService {
	/**
	 * 根据条件查询优惠券活动列表
	 * 
	 * @param conditionDTO
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<PromotionDiscountInfoDTO>> queryCouponInfoByCondition(
			DiscountConditionDTO conditionDTO, Pager<PromotionDiscountInfoDTO> page);

	/**
	 * 根据活动ID查询优惠券活动详情
	 * 
	 * @param promotionId
	 * @param levelCode
	 * @return
	 */
	public ExecuteResult<PromotionDiscountInfoDTO> queryCouponInfo(String promotionId, String levelCode);

	/**
	 * 新增优惠券活动信息
	 * 
	 * @param couponInfo
	 * @return
	 */
	public ExecuteResult<PromotionDiscountInfoDTO> addCouponInfo(PromotionDiscountInfoDTO couponInfo);

	/**
	 * 修改优惠券活动信息
	 *
	 * @param couponInfo
	 * @return
	 */
	public ExecuteResult<PromotionDiscountInfoDTO> updateCouponInfo(PromotionDiscountInfoDTO couponInfo);

	/**
	 * 审核通过后修改优惠券活动信息
	 *
	 * @param modifyCouponInfo
	 * @return
	 */
	public ExecuteResult<String> updateConfirmedCouponInfo(ModifyPromotionCouponInfoDTO modifyCouponInfo);

	/**
	 * 审核优惠券活动信息
	 *
	 * @param verifyInfo
	 * @return
	 */
	public ExecuteResult<String> saveVerifiedCouponInfo(VerifyInfoDTO verifyInfo);

	/**
	 * 删除优惠券活动信息
	 * 
	 * @param validDTO
	 * @return
	 */
	public ExecuteResult<String> deleteCouponInfo(PromotionValidDTO validDTO);

}
