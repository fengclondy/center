package cn.htd.membercenter.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BelongRelationship;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.dto.MemberShipDTO;

public interface SuperBossDao {
	
	/**
	 * 会员关系同步
	 *  例：格式：年月日：(‘2012-06-08’)或'20120608' 年月日时分秒：格式‘2012-06-08
	 *            10:48:55’
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MemberShipDTO> serachMemberShip(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("page") Pager page);
	/**
	 * 会员关系同步总个数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Long serachMemberShipCount(@Param("startTime") String startTime,@Param("endTime") String endTime);
	/**
	 * 包厢关系同步
	 *  例：格式：年月日：(‘2012-06-08’)或'20120608' 年月日时分秒：格式‘2012-06-08
	 *            10:48:55’
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<BoxRelationship> searchBoxShip(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("page") Pager page);
	
	/**
	 * 包厢关系同步总个数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Long searchBoxShipCount(@Param("startTime") String startTime,@Param("endTime") String endTime);
	/**
	 * 归属关系同步
	 *  例：格式：年月日：(‘2012-06-08’)或'20120608' 年月日时分秒：格式‘2012-06-08
	 *            10:48:55’
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<BelongRelationship> searchBelongShip(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("page") Pager page);
	
	/**
	 * 归属关系同步总个数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Long searchBelongShipCount(@Param("startTime") String startTime,@Param("endTime") String endTime);
}
