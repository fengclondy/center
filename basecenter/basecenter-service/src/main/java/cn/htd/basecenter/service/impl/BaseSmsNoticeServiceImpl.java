package cn.htd.basecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BaseSmsNoticeDAO;
import cn.htd.basecenter.dto.BaseSmsNoticeDTO;
import cn.htd.basecenter.service.BaseSmsNoticeService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

@Service("baseSmsNoticeService")
public class BaseSmsNoticeServiceImpl implements BaseSmsNoticeService {

	private static final Logger logger = LoggerFactory.getLogger(BaseSmsNoticeServiceImpl.class);

	@Resource
	private BaseSmsNoticeDAO baseSmsNoticeDAO;

	@Override
	public ExecuteResult<String> insertBaseSmsNotice(BaseSmsNoticeDTO noticeDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseSmsNoticeServiceImpl-insertBaseSmsNotice",
				JSONObject.toJSONString(noticeDTO));
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (StringUtil.isEmpty(noticeDTO.getNoticeName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "预警人名称不能为空");
			}
			if (StringUtil.isEmpty(noticeDTO.getNoticePhone())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "预警人电话不能为空");
			}
			baseSmsNoticeDAO.add(noticeDTO);
			result.setCode(ReturnCodeConst.RETURN_SUCCESS);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteBaseSmsNotice(BaseSmsNoticeDTO noticeDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseSmsNoticeServiceImpl-deleteBaseSmsNotice",
				JSONObject.toJSONString(noticeDTO));
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if(null == noticeDTO.getId()){
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "预警人id不能为空");
			}
			baseSmsNoticeDAO.deleteBaseSmsNotice(noticeDTO);
			result.setCode(ReturnCodeConst.RETURN_SUCCESS);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<BaseSmsNoticeDTO>> queryBaseSmsNotice(BaseSmsNoticeDTO noticeDTO,
			Pager<BaseSmsNoticeDTO> pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseSmsNoticeServiceImpl-queryBaseSmsNotice",
				JSONObject.toJSONString(noticeDTO));
		ExecuteResult<DataGrid<BaseSmsNoticeDTO>> result = new ExecuteResult<DataGrid<BaseSmsNoticeDTO>>();
		DataGrid<BaseSmsNoticeDTO> noticeDataGrid = new DataGrid<BaseSmsNoticeDTO>();
		try {
			int noticeCount = baseSmsNoticeDAO.queryBaseSmsNoticeCount(noticeDTO);
			if(noticeCount > 0){
				List<BaseSmsNoticeDTO> baseSmsNoticeList = baseSmsNoticeDAO.queryBaseSmsNotice(noticeDTO, pager);
				noticeDataGrid.setTotal(Long.parseLong(noticeCount + ""));
				noticeDataGrid.setRows(baseSmsNoticeList);
				result.setResult(noticeDataGrid);
			}
			result.setCode(ReturnCodeConst.RETURN_SUCCESS);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
}
