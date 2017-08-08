package com.bjucloud.contentcenter.service;

import java.util.List;

import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public interface HTDCityCarouselAdvertiseExportService {
	/**
	 * 查询广告列表前整体更新状态
	 * @return
	 */
	public ExecuteResult<Boolean> updateCityAdvStatusInit(List<Integer> SubstationList);

	/**
	 * 静态广告列表查询
	 * @param page
	 * @param mallAdQueryDTO
	 * @return
	 */
	public DataGrid<HTDAdvertisementDTO> queryCityAdvertisement(Pager page,HTDAdvertisementDTO htdAdvertisementDTO);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ExecuteResult<HTDAdvertisementDTO> queryCityAdvById(Long id);

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
	public ExecuteResult<Boolean> updateCityAdvertisement(HTDAdvertisementDTO dto);
	
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
	public ExecuteResult<Boolean> addCityAdvertisement(HTDAdvertisementDTO dto);


	/**
	 * 根据排序号修改状态
	 * @return
	 */
	public ExecuteResult<String> modidfyBySortNum(String status,Long sortNum);
}
