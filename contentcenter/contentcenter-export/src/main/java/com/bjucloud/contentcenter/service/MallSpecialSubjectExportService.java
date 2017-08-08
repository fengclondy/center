package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallSpecialSubjectDTO;

/**
 * 供客户端调用的远程接口 专题管理列表接口
 * @author root
 *
 */
public interface MallSpecialSubjectExportService {
	
	/**
	 * 专题管理分页列表查询接口
	 * @param page
	 * @param mallSpecialSubjectDTO
	 * @return
	 */
	public DataGrid<MallSpecialSubjectDTO> querySubjectList(Pager page, MallSpecialSubjectDTO mallSpecialSubjectDTO);

	
	/**
	 * 专题管理添加专题接口
	 * @param dto
	 * @return
	 */
	public ExecuteResult<String> addSpecialSubject(MallSpecialSubjectDTO dto);

	
	/**
	 * 专题管理修改专题接口
	 * @param dto
	 * @return
	 */
	public ExecuteResult<String> modifySpecialSubject(MallSpecialSubjectDTO dto);

	
	/**
	 * 专题管理根据id获取专题接口
	 * @param id
	 * @return
	 */
	public MallSpecialSubjectDTO getSpecialSubjectById(Long id);

}
