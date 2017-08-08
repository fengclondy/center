package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BelongRelationship;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.dto.MemberShipDTO;

public interface SuperBossService {
	/**
	 * 会员关系同步
	 *  例：格式：年月日：(‘2012-06-08’)或'20120608' 年月日时分秒：格式‘2012-06-08
	 *            10:48:55’
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberShipDTO>> selectMemberShip(String startTime,String endTime,Pager page);

	/**
	 * 包厢关系同步
	 *  例：格式：年月日：(‘2012-06-08’)或'20120608' 年月日时分秒：格式‘2012-06-08
	 *            10:48:55’
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExecuteResult<DataGrid<BoxRelationship>> selectBoxShip(String startTime,String endTime,Pager page);

	/**
	 * 归属关系同步
	 *  例：格式：年月日：(‘2012-06-08’)或'20120608' 年月日时分秒：格式‘2012-06-08
	 *            10:48:55’
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExecuteResult<DataGrid<BelongRelationship>> selectBelongShip(String startTime,String endTime,Pager page);
}
