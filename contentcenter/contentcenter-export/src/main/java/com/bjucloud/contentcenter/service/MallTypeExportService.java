package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.HTDMallTypeDTO;
import com.bjucloud.contentcenter.dto.MallTypeDTO;
import com.bjucloud.contentcenter.dto.SiteLogoDTO;

/**
 * 
 * <p>
 * Description: [前台类目维护]
 * </p>
 */
public interface MallTypeExportService {

	/**
	 * 根据ID 获取前台类目信息及子信息
	 * @param id
	 * @return
     */
	public ExecuteResult<HTDMallTypeDTO> getMallTypeById(Long id);

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述：修改前台类目信息及子信息]
	 * </p>
	 *
	 * @param mallTypeDTO
	 */
	public ExecuteResult<String> modifyMallType(HTDMallTypeDTO mallTypeDTO);

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:新增前台类目]
	 * </p>
	 *
	 * @param mallTypeDTO
	 */
	public ExecuteResult<String> addMallType(HTDMallTypeDTO mallTypeDTO);
	
	

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:查询全部]
	 * </p>
	 */
	public DataGrid<HTDMallTypeDTO> queryAll(Pager page);


	/**
	 * 根据前台类目名称查询
	 * @param name
	 * @return
	 */
	public DataGrid<HTDMallTypeDTO> queryByName(String name) ;

}
