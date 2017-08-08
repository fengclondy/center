package cn.htd.membercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.OMeetingConstants;
import cn.htd.membercenter.dao.OMeetingEvaluateDAO;
import cn.htd.membercenter.dao.OMeetingSignDAO;
import cn.htd.membercenter.domain.OMeetingEvaluate;
import cn.htd.membercenter.domain.OMeetingSign;
import cn.htd.membercenter.dto.OMeetingEvaluateDTO;
import cn.htd.membercenter.dto.OMeetingSignDTO;
import cn.htd.membercenter.service.OMeetingService;

/**
 * 会议签到、会议评价
 * 
 * @author tangjiayong
 *
 */
@Service("oMeetingService")
public class OMeetingServiceImpl implements OMeetingService {

	private final static Logger logger = LoggerFactory.getLogger(OMeetingServiceImpl.class);

	@Resource
	private OMeetingSignDAO oMeetingSignDAO;
	@Resource
	private OMeetingEvaluateDAO oMeetingEvaluateDAO;

	@SuppressWarnings("rawtypes")
	@Override
	public ExecuteResult<DataGrid<OMeetingSign>> queryOMeetingSignForPage(Pager<OMeetingSignDTO> page,
			OMeetingSignDTO signDto) {

		logger.info("OMeetingServiceImpl--->queryOMeetingSignForPage--->parmas:" + JSONObject.toJSONString(page));
		logger.info("OMeetingServiceImpl--->queryOMeetingSignForPage--->parmas:" + JSONObject.toJSONString(signDto));

		ExecuteResult<DataGrid<OMeetingSign>> result = new ExecuteResult();
		result.setCode(OMeetingConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			String meetingStartTime = signDto.getMeetingStateTime();
			if (StringUtils.isNotEmpty(meetingStartTime)) {
				signDto.setMeetingStateTime(meetingStartTime + " 00:00:00");
			}
			DataGrid<OMeetingSign> dataGrid = new DataGrid<OMeetingSign>();
			List<OMeetingSign> list = oMeetingSignDAO.queryOMeetingSignForPage(page, signDto);
			int count = oMeetingSignDAO.getOMeetingSignCount(signDto);
			dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
			dataGrid.setRows(list);
			result.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "OMeetingServiceImpl-queryOMeetingSignForPage", e.toString());
			result.setCode(OMeetingConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}

		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ExecuteResult<DataGrid<OMeetingEvaluate>> queryOMeetingEvaluateForPage(Pager<OMeetingEvaluateDTO> page,
			OMeetingEvaluateDTO evalDto) {

		logger.info(
				"OMeetingServiceImpl--->queryOMeetingEvaluateForPage--->parmas:" + JSONObject.toJSONString(evalDto));

		ExecuteResult<DataGrid<OMeetingEvaluate>> result = new ExecuteResult();
		result.setCode(OMeetingConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			String meetingStartTime = evalDto.getMeetingStateTime();
			if (StringUtils.isNotEmpty(meetingStartTime)) {
				evalDto.setMeetingStateTime(meetingStartTime + " 00:00:00");
			}
			DataGrid<OMeetingEvaluate> dataGrid = new DataGrid<OMeetingEvaluate>();
			List<OMeetingEvaluate> list = oMeetingEvaluateDAO.queryOMeetingEvaluateForPage(page, evalDto);
			int count = oMeetingEvaluateDAO.getOMeetingEvaluateCount(evalDto);
			dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
			dataGrid.setRows(list);
			result.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "OMeetingServiceImpl-queryOMeetingEvaluateForPage", e.toString());
			result.setCode(OMeetingConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}

		return result;
	}

	@Override
	public ExecuteResult<List<OMeetingSign>> queryOMeetingSign(OMeetingSignDTO signDto) {
		logger.info("OMeetingServiceImpl--->queryOMeetingSign--->parmas:" + JSONObject.toJSONString(signDto));

		ExecuteResult<List<OMeetingSign>> result = new ExecuteResult();
		result.setCode(OMeetingConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			String meetingStartTime = signDto.getMeetingStateTime();
			if (StringUtils.isNotEmpty(meetingStartTime)) {
				signDto.setMeetingStateTime(meetingStartTime + " 00:00:00");
			}
			List<OMeetingSign> list = oMeetingSignDAO.queryOMeetingSign(signDto);
			result.setResult(list);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "OMeetingServiceImpl-queryOMeetingSign", e.toString());
			result.setCode(OMeetingConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}

		return result;
	}

	@Override
	public ExecuteResult<List<OMeetingEvaluate>> queryOMeetingEvaluate(OMeetingEvaluateDTO evalDto) {

		logger.info("OMeetingServiceImpl--->queryOMeetingEvaluate--->parmas:" + JSONObject.toJSONString(evalDto));

		ExecuteResult<List<OMeetingEvaluate>> result = new ExecuteResult();
		result.setCode(OMeetingConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			String meetingStartTime = evalDto.getMeetingStateTime();
			if (StringUtils.isNotEmpty(meetingStartTime)) {
				evalDto.setMeetingStateTime(meetingStartTime + " 00:00:00");
			}
			List<OMeetingEvaluate> list = oMeetingEvaluateDAO.queryOMeetingEvaluate(evalDto);
			result.setResult(list);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "OMeetingServiceImpl-queryOMeetingEvaluate", e.toString());
			result.setCode(OMeetingConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}

		return result;
	}

	@Override
	public ExecuteResult<DataGrid<OMeetingEvaluate>> queryOMeetingEvaluateByMeetingNo(Pager page, String meetingNo) {
		logger.info("OMeetingServiceImpl--->queryOMeetingEvaluateByMeetingNo--->parmas:"
				+ JSONObject.toJSONString(meetingNo));
		logger.info(
				"OMeetingServiceImpl--->queryOMeetingEvaluateByMeetingNo--->parmas:" + JSONObject.toJSONString(page));
		ExecuteResult<DataGrid<OMeetingEvaluate>> result = new ExecuteResult<DataGrid<OMeetingEvaluate>>();
		DataGrid<OMeetingEvaluate> dg = new DataGrid<OMeetingEvaluate>();
		result.setCode(OMeetingConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			int count = oMeetingEvaluateDAO.queryOMeetingEvaluateCountByMeetingNo(meetingNo);
			if (count > 0) {
				List<OMeetingEvaluate> list = oMeetingEvaluateDAO.queryOMeetingEvaluateByMeetingNo(page, meetingNo);
				dg.setRows(list);
				dg.setTotal(Long.valueOf(count));
				result.setResult(dg);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "OMeetingServiceImpl-queryOMeetingEvaluateByMeetingNo", e.toString());
			result.setCode(OMeetingConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<OMeetingSign>> queryOMeetingSignByMeetingNo(Pager page, String meetingNo) {
		logger.info(
				"OMeetingServiceImpl--->queryOMeetingSignByMeetingNo--->parmas:" + JSONObject.toJSONString(meetingNo));
		logger.info("OMeetingServiceImpl--->queryOMeetingSignByMeetingNo--->parmas:" + JSONObject.toJSONString(page));
		ExecuteResult<DataGrid<OMeetingSign>> result = new ExecuteResult<DataGrid<OMeetingSign>>();
		DataGrid<OMeetingSign> dg = new DataGrid<OMeetingSign>();
		result.setCode(OMeetingConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			int count = oMeetingSignDAO.queryOMeetingSignCountByMeetingNo(meetingNo);
			if (count > 0) {
				List<OMeetingSign> list = oMeetingSignDAO.queryOMeetingSignByMeetingNo(page, meetingNo);
				dg.setRows(list);
				dg.setTotal(Long.valueOf(count));
				result.setResult(dg);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "OMeetingServiceImpl-queryOMeetingSignByMeetingNo", e.toString());
			result.setCode(OMeetingConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(OMeetingConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}

}
