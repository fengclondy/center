package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.membercenter.domain.OMeetingEvaluate;
import cn.htd.membercenter.dto.OMeetingEvaluateDTO;

public interface OMeetingEvaluateDAO extends BaseDAO<OMeetingEvaluate> {

	List<OMeetingEvaluate> queryOMeetingEvaluateForPage(@Param("page") Pager<OMeetingEvaluateDTO> page,
			@Param("dto") OMeetingEvaluateDTO evalDto);

	int getOMeetingEvaluateCount(@Param("dto") OMeetingEvaluateDTO evalDto);

	List<OMeetingEvaluate> queryOMeetingEvaluate(@Param("dto") OMeetingEvaluateDTO evalDto);

	List<OMeetingEvaluate> queryOMeetingEvaluateByMeetingNo(@Param("page") Pager page,
			@Param("meetingNo") String meetingNo);

	int queryOMeetingEvaluateCountByMeetingNo(@Param("meetingNo") String meetingNo);

}
