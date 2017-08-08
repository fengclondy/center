package cn.htd.marketcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.domain.BuyerUseCouponLog;

public interface BuyerUseCouponLogDAO extends BaseDAO<BuyerUseCouponLog> {

	/**
	 * 根据条件查询会员优惠券履历信息
	 * 
	 * @param buyerUseCouponLog
	 * @return
	 */
	public BuyerUseCouponLog queryUseLogByCode(BuyerUseCouponLog buyerUseCouponLog);

	/**
	 * 根据使用条件更新优惠券使用履历
	 * 
	 * @param buyerUseCouponLog
	 * @return
	 */
	public Integer updateUseLogStatusByCode(BuyerUseCouponLog buyerUseCouponLog);

}
