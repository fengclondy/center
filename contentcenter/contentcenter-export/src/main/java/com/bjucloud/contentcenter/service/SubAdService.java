package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.QuerySubAdOutDTO;
import com.bjucloud.contentcenter.dto.SubAdDTO;

/**
 * 
 * <p>
 * Description: [城市广告位处理]
 * </p>
 */
public interface SubAdService {

	/**
	 * 根据条件查询城市站广告位列表
	 * @param record
	 * @param page
     * @return
     */
	public ExecuteResult<DataGrid<SubAdDTO>> queryListByCondition(SubAdDTO record, Pager page);

	/**
	 * 根据城市广告位ID获取详情
	 * @param id
	 * @return
     */
	public ExecuteResult<SubAdDTO> queryById(String id);

	/**
	 * 保存或者修改广告位信息
	 * @param subAdDTO
	 * @return
     */
	public ExecuteResult<SubAdDTO> saveSubAd(SubAdDTO subAdDTO);

	public ExecuteResult<SubAdDTO> queryByAdId(Long id);

	public ExecuteResult<String> modifyStatusBySubId(String status,String subId);

}
