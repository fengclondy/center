package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallRecAttrDTO;
import com.bjucloud.contentcenter.dto.MallRecAttrECMDTO;
import com.bjucloud.contentcenter.dto.MallRecAttrInDTO;
import com.bjucloud.contentcenter.dto.MallRecAttrQueryDTO;

public interface MallRecAttrExportService {
	/**
	 * 
	 * <p>Discription:楼层属性列表查询</p>
	 * @param page
	 * @param mallRecAttrQueryDTO
	 */
	public DataGrid<MallRecAttrDTO> queryMallRecAttrList(Pager page,MallRecAttrQueryDTO mallRecAttrQueryDTO);
	
	/**
	 * 
	* @Title: equeryMallRecAttrList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param page
	* @param mallRecAttrQueryDTO
	* @return    设定文件 
	* @return DataGrid<MallRecAttrDTO>    返回类型 
	* @throws 
	 */
	public DataGrid<MallRecAttrECMDTO> equeryMallRecAttrList(Pager page,MallRecAttrECMDTO mallRecAttrECMDTO);
	/**
	 * 
	 * <p>Discription:[楼层属性详情查询]</p>
	 * @param id
	 * @return
	 */
	public MallRecAttrDTO getMallRecAttrById(Long id);
	
	/**
	 * 
	 * <p>Discription:[楼层属性添加]</p>
	 * @param mallRecAttrInDTO
	 * @return
	 */
	public ExecuteResult<String> addMallRecAttr(MallRecAttrInDTO mallRecAttrInDTO);
	
	/**
	 * 
	 * <p>Discription:[楼层属性修改]</p>
	 * @param mallRecAttrInDTO
	 * @return
	 */
	public ExecuteResult<String> modifyMallRecAttr(MallRecAttrInDTO mallRecAttrInDTO);
	
	/**
	 * 
	 * <p>Discription:[楼层属性上下架]</p>
	 * @param id
	 * @param publishFlag  1、上架  2、下架
	 * @return
	 */
	public ExecuteResult<String> modifyMallRecAttrStatus(Long id,String publishFlag);

	/**
	 * 根据id删除推荐
	 * @param id
	 */
	public ExecuteResult<String> delById(Long id);

}
