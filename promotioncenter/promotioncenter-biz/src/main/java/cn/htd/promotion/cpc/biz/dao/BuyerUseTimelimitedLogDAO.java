package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;

public interface BuyerUseTimelimitedLogDAO extends BaseDAO<BuyerUseTimelimitedLogDMO> {

	/**
	 * 根据条件更新会员秒杀活动参加履历
	 *
	 * @param BuyerUseTimelimitedLogDMO
	 * @return
	 */
	public Integer updateUseLogStatusByCode(BuyerUseTimelimitedLogDMO BuyerUseTimelimitedLogDMO);

	/**
	 * 根据条件更新会员秒杀活动的订单号
	 *
	 * @param BuyerUseTimelimitedLogDMO
	 * @return
	 */
	public Integer updateUseLogOrderNoByLockCode(BuyerUseTimelimitedLogDMO BuyerUseTimelimitedLogDMO);

	/**
	 * 根据条件检索秒杀活动参加履历表
	 *
	 * @param condition
	 * @return
	 */
	public BuyerUseTimelimitedLogDMO queryUseLogByLockCode(BuyerUseTimelimitedLogDMO condition);

	/**
	 * 根据条件检索秒杀活动参加履历表
	 *
	 * @param condition
	 * @return
	 */
	public BuyerUseTimelimitedLogDMO queryUseLogByCode(BuyerUseTimelimitedLogDMO condition);

	/**
	 * @param condition
	 * @param pager
	 * @return
	 */
	public List<BuyerUseTimelimitedLogDMO> queryNeedReleaseTimelimitedStock4Task(
			@Param("entity") BuyerUseTimelimitedLogDMO condition,
			@Param("page") Pager<BuyerUseTimelimitedLogDMO> pager);

	/**
	 * 更新秒杀活动记录的释放库存标记
	 *
	 * @param useTimelimitedLog
	 * @return
	 */
	public Integer updateTimelimitedReleaseStockStatus(BuyerUseTimelimitedLogDMO useTimelimitedLog);
}
