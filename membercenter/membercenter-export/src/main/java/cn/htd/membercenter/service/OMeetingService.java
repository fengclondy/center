package cn.htd.membercenter.service;

import java.util.List;

//import javax.servlet.http.HttpServletResponse;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.domain.OMeetingEvaluate;
import cn.htd.membercenter.domain.OMeetingSign;
import cn.htd.membercenter.dto.OMeetingEvaluateDTO;
import cn.htd.membercenter.dto.OMeetingSignDTO;

/**
 * 会议签到、会议评价 展示与导出
 * 
 * @author tangjiayong
 *
 */
public interface OMeetingService {

	/**
	 * 查询会议签到
	 * 
	 * @param page
	 * @param signDto
	 * @return
	 */
	ExecuteResult<DataGrid<OMeetingSign>> queryOMeetingSignForPage(Pager<OMeetingSignDTO> page,
			OMeetingSignDTO signDto);

	/**
	 * 查询会议评价
	 * 
	 * @param evalDto
	 * @return
	 */
	ExecuteResult<DataGrid<OMeetingEvaluate>> queryOMeetingEvaluateForPage(Pager<OMeetingEvaluateDTO> page,
			OMeetingEvaluateDTO evalDto);

	/**
	 * 查询会议签到不分页
	 * 
	 * @param dto
	 * @return
	 */
	ExecuteResult<List<OMeetingSign>> queryOMeetingSign(OMeetingSignDTO dto);

	/**
	 * 查询会议评价不分页
	 * 
	 * @param dto
	 * @return
	 */
	ExecuteResult<List<OMeetingEvaluate>> queryOMeetingEvaluate(OMeetingEvaluateDTO dto);

	/**
	 * 根据meetingNo查询会议评价
	 * 
	 * @param dto
	 * @return
	 */
	ExecuteResult<DataGrid<OMeetingEvaluate>> queryOMeetingEvaluateByMeetingNo(@SuppressWarnings("rawtypes") Pager page,
			String meetingNo);

	/**
	 * 根据meetingNo查询会议签到
	 * 
	 * @param dto
	 * @return
	 */
	ExecuteResult<DataGrid<OMeetingSign>> queryOMeetingSignByMeetingNo(@SuppressWarnings("rawtypes") Pager page,
			String meetingNo);

}
