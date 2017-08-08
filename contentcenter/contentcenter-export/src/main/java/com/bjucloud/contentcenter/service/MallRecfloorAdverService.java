package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallRecfloorAdverDTO;

/**
 * 楼层广告位
*
 */
public interface MallRecfloorAdverService {

	/**
	 * 条件分页查询楼层广告
	* @Title: queryList 
	* @Description: TODO 条件分页查询楼层广告
	* @param mallRecfloorAdverDTO 实体
	* @param publishFlag 是否删除
	* @param page 分页参数
	* @return    设定文件 
	* @return DataGrid<MallRecfloorAdverDTO>    返回类型 
	* @throws 
	 */
	public DataGrid<MallRecfloorAdverDTO> queryList(MallRecfloorAdverDTO mallRecfloorAdverDTO, String publishFlag, Pager page);

	/**
	 * 根据ID查询记录
	* @Title: getMallRecfloorAdverById 
	* @Description: TODO 根据ID查询记录
	* @param id
	* @return    设定文件 
	* @return MallRecfloorAdverDTO    返回类型 
	* @throws 
	 */
	public MallRecfloorAdverDTO getMallRecfloorAdverById(Integer id);
	
	/**
	 * 添加记录
	* @Title: addMallRecfloorAdver 
	* @Description: TODO 添加记录
	* @param mallRecfloorAdverDTO
	* @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws 
	 */
	public ExecuteResult<String> addMallRecfloorAdver(MallRecfloorAdverDTO mallRecfloorAdverDTO);
	
	/**
	 * 上下架
	* @Title: motifyMallRecfloorAdverStatus 
	* @Description: TODO 上下架
	* @param id
	* @param publishFlag
	* @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws 
	 */
	public ExecuteResult<String> motifyMallRecfloorAdverStatus(Integer id, String publishFlag);
	
	/**
	 * 删除
	* @Title: delete 
	* @Description: TODO 删除
	* @param id
	* @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws 
	 */
	public ExecuteResult<String> delete(Integer id);
	
	/**
	 * 添加
	* @Title: add 
	* @Description: TODO 添加
	* @param mallRecfloorAdverDTO
	* @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws 
	 */
	public ExecuteResult<String> update(MallRecfloorAdverDTO mallRecfloorAdverDTO);
}
