package com.bjucloud.contentcenter.service;

import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: StaticAdvertiseExportService</p>
* @author root
* @date 2017年1月18日
* <p>Description: 
*			静态广告栏
* </p>
 */
public interface HTDStaticAdvertiseExportService {
	
	/**
	 * 查询广告列表前整体更新状态
	 * @return
	 */
	public ExecuteResult<Boolean> updateStaticAdvStatusInit();

	/**
	 * 静态广告列表查询
	 * @param page
	 * @param mallAdQueryDTO
	 * @return
	 */
	public DataGrid<HTDAdvertisementDTO> queryStaticAdvertisement(Pager page,HTDAdvertisementDTO htdAdvertisementDTO);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ExecuteResult<HTDAdvertisementDTO> queryStaticAdvById(Long id);

	/**
	 * 删除
	 * @param valueOf
	 * @return
	 */
	public ExecuteResult<Boolean> deleteById(Long advId);

	/**
	 * 更新静态广告
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> updateStaticAdvertisement(HTDAdvertisementDTO dto);
	
	/**
	 * 查询更改记录
	 * @param modifyType
	 * @return
	 */
	public DataGrid<HTDEditDetailInfoDTO> queryEditDetail(String modifyType,Long advId,Pager pager);

	/**
	 * 增加广告
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> addStaticAdvertisement(HTDAdvertisementDTO dto);
	

}
