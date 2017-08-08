package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallClassifyDTO;

/**
 * <p>
 * Description: [文档分类查询、添加、下架、修改]
 * </p>
 */
public interface MallClassifyService {

	/**
	 * <p>
	 * Discription:文档分类 列表查询
	 * </p>
	 */
	public DataGrid<MallClassifyDTO> queryMallCassifyList(MallClassifyDTO mallCassifyDTO, Pager page);

	/**
	 * 
	 * <p>
	 * Discription:文档分类添加
	 * </p>
	 */
	public ExecuteResult<String> addMallCassify(MallClassifyDTO mallClassifyDTO);

	/**
	 * 
	 * <p>
	 * Discription:文档分类修改
	 * </p>
	 */
	public ExecuteResult<String> modifyInfoById(MallClassifyDTO mallCassifyDTO);

	/**
	 * 
	 * <p>
	 * Discription:文档分类上下架
	 * </p>
	 */
	public ExecuteResult<String> modifyStatusById(int id, int status);

	/**
	 * 
	 * <p>
	 * Discription:文档分类删除
	 * </p>
	 */
	public ExecuteResult<String> delById(int id);
}
