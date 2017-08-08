package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.membercenter.domain.OMeetingSign;
import cn.htd.membercenter.dto.OMeetingSignDTO;

public interface OMeetingSignDAO extends BaseDAO<OMeetingSign> {

	List<OMeetingSign> queryOMeetingSignForPage(@Param("page") Pager<OMeetingSignDTO> page,
			@Param("dto") OMeetingSignDTO signDto);

	int getOMeetingSignCount(@Param("dto") OMeetingSignDTO signDto);

	List<OMeetingSign> queryOMeetingSign(@Param("dto") OMeetingSignDTO signDto);

	List<OMeetingSign> queryOMeetingSignByMeetingNo(@Param("page") Pager page, @Param("meetingNo") String meetingNo);

	int queryOMeetingSignCountByMeetingNo(@Param("meetingNo") String meetingNo);

}
