package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.HotWordDTO;
import com.bjucloud.contentcenter.dto.StaticResourceDTO;

/**
 * 静态资源管理
 */
public interface StaticResourceService {

	public DataGrid<StaticResourceDTO> queryListByCondition(StaticResourceDTO record, Pager page) ;

	/**
	 * <p>Discription:静态资源删除</p>
	 */
	public ExecuteResult<String> delById(Long id);

	/**
	 * <p>
	 * Discription:静态资源详情查询
	 * </p>
	 */
	public StaticResourceDTO queryById(Long id) ;

	/**
	 * 静态资源上下架
	 * @param staticResourceDTO
	 * @return
     */
	public ExecuteResult<Boolean> modify(StaticResourceDTO staticResourceDTO) ;


	/**
	 * <p>Discription:静态资源新增</p>
	 */
	public ExecuteResult<String> save(StaticResourceDTO staticResourceDTO);
}
