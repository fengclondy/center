package com.bjucloud.contentcenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.ExecuteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bjucloud.contentcenter.dao.MallInfoDAO;
import com.bjucloud.contentcenter.domain.MallInfo;
import com.bjucloud.contentcenter.dto.LogoDTO;
import com.bjucloud.contentcenter.service.LogoExportService;

@Service("logoExportService")
public class LogoExportServiceImpl implements LogoExportService {

	private static final Logger logger = LoggerFactory.getLogger(LogoExportServiceImpl.class);

	@Resource
	private MallInfoDAO mallInfoDAO;

	/**
	 * <p>
	 * Discription:[Logo查询，查询出来只有一条数据，mall_info表中只会有一条数据]
	 * </p>
	 */
	@Override
	public ExecuteResult<LogoDTO> getMallLogo() {
		ExecuteResult<LogoDTO> executeResult = new ExecuteResult<LogoDTO>();
		try {
			List<LogoDTO> logoDTOList = mallInfoDAO.findAll();
			if (logoDTOList != null && logoDTOList.size() > 0) {
				LogoDTO logoDTO = logoDTOList.get(0);
				executeResult.setResult(logoDTO);
			}
			executeResult.setResultMessage("Logo查询成功");
		} catch (Exception e) {
			logger.error("执行方法：【getMallLogo】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	/**
	 * <p>
	 * Discription:[Logo修改,根据参数执行全部数据字段修改sql，mall_info表中只会有一条数据]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyMallLogo(String logoName, String picUrl) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			List<LogoDTO> logoDTOList = mallInfoDAO.findAll();
			if (logoDTOList != null && logoDTOList.size() > 0) {
				Integer num = mallInfoDAO.updateAll(logoName, picUrl);
				executeResult.setResult(num + "");
				executeResult.setResultMessage("Logo修改成功");
			} else {
				MallInfo mallInfo = new MallInfo();
				mallInfo.setTitle(logoName);
				mallInfo.setLogo(picUrl);
				mallInfoDAO.add(mallInfo);
			}

		} catch (Exception e) {
			logger.error("执行方法：【modifyMallLogo】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

}