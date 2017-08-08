package com.bjucloud.contentcenter.service;

import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: HTDAdvertisementExportService</p>
* @author root
* @date 2017年1月12日
* <p>Description: 
*		汇通达 广告接口
* </p>
 */
public interface HTDAdvertisementExportService {
	
	/**
	 * 查询广告列表前整体更新状态
	 * @return
	 */
	public ExecuteResult<Boolean> updateTopAdvStatusInit();

	/**
	 * 顶通广告列表查询
	 * @param page
	 * @param mallAdQueryDTO
	 * @return
	 */
	public DataGrid<HTDAdvertisementDTO> queryTopAdvertisement(Pager page,HTDAdvertisementDTO mallAdQueryDTO);

	/**
	 * 更新广告
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> updateTopAdvertisement(HTDAdvertisementDTO dto);

	/**
	 * 删除广告
	 * @param valueOf
	 * @return
	 */
	public ExecuteResult<Boolean> deleteById(Long advId);

	/**
	 * 根据id查询广告
	 * @param advId
	 * @return
	 */
	public ExecuteResult<HTDAdvertisementDTO> queryTopAdvById(Long advId);

	
	/**
	 * 增加广告
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> addTopAdvertisement(HTDAdvertisementDTO dto);

	
	/**
	 * 查询更改记录
	 * @param modifyType
	 * @return
	 */
	public DataGrid<HTDEditDetailInfoDTO> queryEditDetail(String modifyType,Long advId,Pager pager);

	/**
	 * 查询更改记录
	 * @param modifyType
	 * @return
	 */
	public DataGrid<HTDEditDetailInfoDTO> queryByModityType(String modifyType);

	/**
	 * 添加
	 * @return
	 */
	ExecuteResult<String> saveEditDetail(HTDEditDetailInfoDTO htdEditDetailInfoDTO);
	
}
