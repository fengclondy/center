package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallThemeDTO;


public interface MallThemeService {

	public DataGrid<MallThemeDTO> queryMallThemeList(MallThemeDTO mallThemeDTO, String publishFlag, Pager page);
	
	public MallThemeDTO getMallThemeById(long id);

	public ExecuteResult<String> addMallTheme(MallThemeDTO mallThemeDTO);
	
	public ExecuteResult<String> delete(Long id);

	public ExecuteResult<String> motifyMallThemeStatus(Long id, String publishFlag);
	
	public ExecuteResult<String> motifyMallTheme(MallThemeDTO mallThemeDTO);
	
}
