package com.bjucloud.contentcenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import org.springframework.stereotype.Service;

import com.bjucloud.contentcenter.dao.MallWordDAO;
import com.bjucloud.contentcenter.dto.MallWordDTO;
import com.bjucloud.contentcenter.service.MallWordExportService;

@Service("mallWordExportService")
public class MallWordExportServiceImpl implements MallWordExportService {
	@Resource
	private MallWordDAO mallWordDao;

	@Override
	public ExecuteResult<MallWordDTO> add(MallWordDTO dto) {
		ExecuteResult<MallWordDTO> er = new ExecuteResult<MallWordDTO>();
		this.mallWordDao.add(dto);
		er.setResult(dto);
		return er;
	}

	@Override
	public ExecuteResult<String> delete(Long id) {
		this.mallWordDao.delete(id);
		return new ExecuteResult<String>();
	}

	@Override
	public DataGrid<MallWordDTO> datagrid(MallWordDTO dto, Pager page) {
		DataGrid<MallWordDTO> dg = new DataGrid<MallWordDTO>();

		List<MallWordDTO> list = this.mallWordDao.queryList(dto, page);
		Long total = this.mallWordDao.queryCount(dto);

		dg.setTotal(total);
		dg.setRows(list);
		return dg;
	}

}
