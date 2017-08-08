package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.HotWordDTO;
import com.bjucloud.contentcenter.dto.MallWordDTO;
import com.bjucloud.contentcenter.dto.SubAdDTO;

/**
 * 商城搜索词设置
 */
public interface HotWordService {
	/**
	 * 查询热搜词
	 */
	public DataGrid<HotWordDTO> datagrid(HotWordDTO dto, Pager page) ;

	/**
	 * 保存或者修改热搜词
	 * @param hotWordDTO
	 * @return
	 */
	public ExecuteResult<String> saveHotWord(HotWordDTO hotWordDTO);

	/**
	 * 根据热词名称查询
	 * @param name
	 * @return
     */
	public DataGrid<HotWordDTO> queryByName(String name) ;

	/**
	 * 根据排序号查询
	 * @param sortNum
	 * @return
     */
	public DataGrid<HotWordDTO> queryBySortNum(Long sortNum) ;

	/**
	 * 根据ID查询热词
	 * @param id
	 * @return
     */
	public ExecuteResult<HotWordDTO> queryById(Long id) ;

	/**
	 * 根据ID删除热搜词
	 * @param id
	 * @return
     */
	public ExecuteResult<String> delete(Long id);
}
