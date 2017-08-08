package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallSubTabDTO;
import com.bjucloud.contentcenter.dto.MallSubTabECMDTO;

/**
 * 
 * <p>
 * Description: [页签功能service接口]
 * </p>
 */
public interface MallSubTabService {

	/**
	 * <p>
	 * Discription:页签分页
	 * </p>
	 */
	public DataGrid<MallSubTabDTO> queryMallSubTabPage(MallSubTabDTO mllSubTabDTO, Pager page);

	public DataGrid<MallSubTabECMDTO> equeryMallSubTabPage(MallSubTabECMDTO mallSubTabECMDTO, Pager page);

	/**
	 * 
	 * <p>
	 * Discription:页签新增
	 * </p>
	 */
	public ExecuteResult<String> addMallSubTab(MallSubTabDTO dto);

	/**
	 * <p>
	 * Discription:页签获取数据根据id
	 * </p>
	 */
	public MallSubTabDTO getMallSubTabData(Long id);

	/**
	 * 
	 * <p>
	 * Discription:[页签id查询 得到页签对象 和子站主题信息]
	 * </p>
	 * 
	 * @param id
	 */
	public MallSubTabECMDTO getMallSubTabECMData(Long id);

	/**
	 * 
	 * 
	 * <p>
	 * Discription:页签修改数据
	 * </p>
	 */
	public ExecuteResult<String> updateMallSubData(MallSubTabDTO dto);

	/**
	 * 是否启用
	 * 
	 * <p>
	 * Discription:是否启用
	 * </p>
	 */
	public ExecuteResult<String> updateStatus(Long id, String status);

	/**
	 * 是否启用
	 *
	 * <p>
	 * Discription:删除页签
	 * </p>
	 */
	public ExecuteResult<String> deleteMallSub(Long id);
}
