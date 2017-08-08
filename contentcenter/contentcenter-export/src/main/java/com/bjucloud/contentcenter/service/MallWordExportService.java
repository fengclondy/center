package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallWordDTO;

/**
 * 商城搜索词设置
 */
public interface MallWordExportService {

	/**
	 * 添加商城搜索词
	 */
	public ExecuteResult<MallWordDTO> add(MallWordDTO dto);

	/**
	 * 删除商城搜索词
	 */
	public ExecuteResult<String> delete(Long id);

	/**
	 * 查询商城搜索词
	 */
	public DataGrid<MallWordDTO> datagrid(MallWordDTO dto, Pager page);
}
