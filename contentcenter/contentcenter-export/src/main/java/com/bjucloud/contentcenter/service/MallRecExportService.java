package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallRecDTO;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:楼层数据 接口]
 * </p>
 */
public interface MallRecExportService {
	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:新增楼层]
	 * </p>
	 * 
	 * @param mallRecDTO
	 */
	public ExecuteResult<String> addMallRec(MallRecDTO mallRecDTO);

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:楼层详情]
	 * </p>
	 * 
	 * @param id
	 */
	public MallRecDTO getMallRecById(Long id);

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述：查询楼层列表]
	 * </p>
	 * 
	 * @param mallRecDTO
	 * @param page
	 */
	public DataGrid<MallRecDTO> queryMallRecList(MallRecDTO mallRecDTO, Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述：修改楼层]
	 * </p>
	 * 
	 * @param mallRecDTO
	 */
	public ExecuteResult<String> modifyMallRec(MallRecDTO mallRecDTO);

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:楼层上下架]
	 * </p>
	 * 
	 * @param id
	 * @param publishFlag
	 */
	public ExecuteResult<String> modifyMallRecStatus(Long id, String publishFlag);

	public ExecuteResult<String> deleteMallRec(Long id);
}