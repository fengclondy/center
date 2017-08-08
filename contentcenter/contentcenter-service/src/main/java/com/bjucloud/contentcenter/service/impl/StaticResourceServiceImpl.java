package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.bjucloud.contentcenter.common.enmu.GlobalConstant;
import com.bjucloud.contentcenter.dao.StaticResourceDAO;
import com.bjucloud.contentcenter.dto.StaticResourceDTO;
import com.bjucloud.contentcenter.service.StaticResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("staticResourceService")
public class StaticResourceServiceImpl implements StaticResourceService {
	private static final Logger logger = LoggerFactory.getLogger(StaticResourceServiceImpl.class);

	@Resource
	private StaticResourceDAO staticResourceDAO;

	@Override
	public DataGrid<StaticResourceDTO> queryListByCondition(StaticResourceDTO record, Pager page) {
		DataGrid<StaticResourceDTO> dataGrid = new DataGrid<StaticResourceDTO>();
		try {
			long size = staticResourceDAO.selectCountByCondition(record);
			if (size > 0){
				List<StaticResourceDTO> staticResourceDTOs = staticResourceDAO.selectListByCondition(record, page);
				dataGrid.setRows(staticResourceDTOs);
			}
			dataGrid.setTotal(size);
		}catch (Exception e){
			logger.error("执行方法【queryListByCondition】报错! {}", e);
			throw new RuntimeException(e);
		}

		return dataGrid;
	}

	/**
	 * 静态资源删除删除
	 */
	@Override
	public ExecuteResult<String> delById(Long id) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			int delcount = staticResourceDAO.delete(id);
			if (delcount > 0) {
				er.setResult("操作成功");
			} else {
				er.addErrorMsg("操作失败");
			}
		} catch (Exception e) {
			er.addErrorMsg("操作失败");
		}
		return er;
	}
	@Override
	public StaticResourceDTO queryById(Long id) {
		StaticResourceDTO dto = this.staticResourceDAO.selectById(id);
		return dto;
	}

	/**
	 * 静态资源上下架
	 * @param staticResourceDTO
	 * @return
     */
	@Override
	public ExecuteResult<Boolean> modify(StaticResourceDTO staticResourceDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		StaticResourceDTO mallAdDTO = null;
		Date date = null;
		try {
			if (StringUtils.isBlank(staticResourceDTO.getId().toString())){
				rs.addErrorMessage("参数ID不能为空! ");
				return rs;
			}

			mallAdDTO = this.staticResourceDAO.selectById(staticResourceDTO.getId());
			if (mallAdDTO == null){
				rs.addErrorMessage("该静态资源不存在! ");
				return rs;
			}

			this.staticResourceDAO.update(staticResourceDTO);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【StaticResourceServiceImpl】  modify ",e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> save(StaticResourceDTO staticResourceDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			this.staticResourceDAO.add(staticResourceDTO);
		} catch (Exception e) {
			logger.error("执行方法【save】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}
}
