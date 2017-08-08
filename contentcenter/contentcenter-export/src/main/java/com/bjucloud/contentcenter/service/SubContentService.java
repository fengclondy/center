package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.domain.SubContent;
import com.bjucloud.contentcenter.dto.SubContentDTO;
import com.bjucloud.contentcenter.dto.SubContentSubDTO;

/**
 * 
 * <p>
 * Description: [城市站内容]
 * </p>
 */
public interface SubContentService {

	/**
	 * 根据条件查询城市站内容信息列表
	 * @param record
	 * @param page
     * @return
     */
	public ExecuteResult<DataGrid<SubContentDTO>> queryListByCondition(SubContentDTO record, Pager page) ;

	/**
	 * 根据城市广告位ID查询城市站内容详细信息
	 * @param subAdId
	 * @return
     */
	public ExecuteResult<SubContentDTO> queryBySubAdId(Long subAdId);

	/**
	 * 首次编辑城市站广告位内容，不能添加修改记录
	 * @param dto
	 * @return
     */
	public ExecuteResult<String> firstAdd(SubContentDTO dto);

	/**
	 * 根据城市站内容信息ID更新城市站内容信息/城市中内容信息子表/城市中内容图片子表
	 * @param dto
	 * @return
     */
	public ExecuteResult<String> modify(SubContentDTO dto);

	/**
	 * 加入供应商
	 * @param dto
	 * @return
     */
	public ExecuteResult<String> saveContentSub(SubContentSubDTO dto);

	/**
	 * 刪除供应商
	 * @param sellerCode
	 * @return
     */
	public ExecuteResult<Boolean> deleteById(String sellerCode);


	/**
	 * 保存城市站内容信息
	 * @param subContentDTO
	 * @return
	 */
	public ExecuteResult<String> saveSubContent(SubContentDTO subContentDTO);
}
