package cn.htd.marketcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;

import java.util.List;

public interface PromotionDiscountInfoDAO extends BaseDAO<PromotionDiscountInfoDTO> {

	/**
	 * 根据ID获取促销活动信息
	 * 
	 * @param discountDTO
	 * @return
	 */
	public PromotionDiscountInfoDTO queryDiscountInfoById(PromotionAccumulatyDTO discountDTO);

	/**
	 * 定时任务处理后更新处理标记
	 * 
	 * @param coupinInfo
	 * @return
	 */
	public Integer updateCouponDealFlag(PromotionDiscountInfoDTO coupinInfo);

	/**
	 * 查询所有未结束的返券活动记录
     *
	 * @return
	 */
	public List<PromotionDiscountInfoDTO> queryNoEndedDiscountInfo();

	/**
	 * 根据返券方式取得所有领券期间未结束的自助领券活动信息
     *
	 * @param provideType
	 * @return
	 */
	public List<PromotionDiscountInfoDTO> queryCollectableDiscountInfo(String provideType);

}