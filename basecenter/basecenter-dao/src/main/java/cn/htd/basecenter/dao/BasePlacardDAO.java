package cn.htd.basecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.basecenter.domain.BasePlacard;
import cn.htd.basecenter.dto.PlacardCondition;
import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

public interface BasePlacardDAO extends BaseDAO<BasePlacard> {

	/**
	 * 查询会员所有公告件数统计
	 * 
	 * @param placardCondition
	 * @return
	 */
	public long getAllPlacardCount(@Param("entity") PlacardCondition placardCondition);

	/**
	 * 查询会员所有公告列表
	 * 
	 * @param placardCondition
	 * @param pager
	 * @return
	 */
	public List<BasePlacard> getAllPlacardList(@Param("entity") PlacardCondition placardCondition,
			@Param("page") Pager<PlacardCondition> page);

	/**
	 * 查询会员平台公告件数统计
	 * 
	 * @param placardCondition
	 * @return
	 */
	public long getPlatformPlacardCount(@Param("entity") PlacardCondition placardCondition);

	/**
	 * 查询会员平台公告列表
	 * 
	 * @param placardCondition
	 * @param pager
	 * @return
	 */
	public List<BasePlacard> getPlatformPlacardList(@Param("entity") PlacardCondition placardCondition,
			@Param("page") Pager<PlacardCondition> page);

	/**
	 * 查询会员商家公告件数统计
	 * 
	 * @param placardCondition
	 * @return
	 */
	public long getSellerPlacardCount(@Param("entity") PlacardCondition placardCondition);

	/**
	 * 查询会员商家公告列表
	 * 
	 * @param placardCondition
	 * @param pager
	 * @return
	 */
	public List<BasePlacard> getSellerPlacardList(@Param("entity") PlacardCondition placardCondition,
			@Param("page") Pager<PlacardCondition> page);

	/**
	 * 查询能删除的公告列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<BasePlacard> queryDeletePlacardByIdList(PlacardCondition condition);

	/**
	 * 查询定时任务处理公告列表
	 * 
	 * @param condition
	 * @param pager
	 * @return
	 */
	public List<BasePlacard> queryPlacardList4Task(@Param("entity") PlacardCondition condition,
			@Param("page") Pager<PlacardCondition> pager);

	/**
	 * 更新公告信息的状态为删除
	 * 
	 * @param parameter
	 */
	public void deletePlacardByIdList(BasePlacard parameter);

	/**
	 * 更新公告信息的状态
	 * 
	 * @param placard
	 */
	public void updatePlacardStatusById(BasePlacard placard);

}
