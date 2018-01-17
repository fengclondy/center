package com.bjucloud.contentcenter.service;

import java.util.List;

import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.dto.PurchaseChannelsDTO;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * 
 * <p>
 * Description: [采购频道维护]
 * </p>
 */
public interface PurchaseChannelsService {

	/**
	 * 根据ID 获取前台类目信息及子信息
	 * @param id
	 * @return
     */
	public ExecuteResult<PurchaseChannelsDTO> getMallTypeById(Long id);

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述：修改前台类目信息及子信息]
	 * </p>
	 *
	 * @param mallTypeDTO
	 */
	public ExecuteResult<String> modifyMallType(PurchaseChannelsDTO mallTypeDTO);

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:新增前台类目]
	 * </p>
	 *
	 * @param mallTypeDTO
	 */
	public ExecuteResult<String> addMallType(PurchaseChannelsDTO mallTypeDTO);
	
	

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:查询全部]
	 * </p>
	 */
	public DataGrid<PurchaseChannelsDTO> queryAll(Pager page);


	/**
	 * 根据前台类目名称查询
	 * @param name
	 * @return
	 */
	public DataGrid<PurchaseChannelsDTO> queryByName(String name) ;

	public DataGrid<HTDEditDetailInfoDTO> queryEditDetail(String adType, Long id, Pager pager);

	public ExecuteResult<String> delete(Long id);

	public ExecuteResult<String> modifyStatus(PurchaseChannelsDTO mallTypeDTO);

	/**
	 * app首页频道
	 * @return
	 */
	public ExecuteResult<List<PurchaseChannelsDTO>> queryPurchaseChannelsList();
}
