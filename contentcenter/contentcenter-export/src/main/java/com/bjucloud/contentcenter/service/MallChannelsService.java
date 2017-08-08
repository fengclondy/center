package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.domain.MallChannels;
import com.bjucloud.contentcenter.dto.HotWordDTO;
import com.bjucloud.contentcenter.dto.MallChannelsDTO;

/**
 * 商城频道导航
 */
public interface MallChannelsService {

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:商城频道导航详情]
	 * </p>
	 *
	 * @param id
	 */
	public MallChannelsDTO getMallChannelsById(Long id);
	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述：修改商城频道导航]
	 * </p>
	 *
	 * @param mallRecDTO
	 */
	public ExecuteResult<String> modifyMallChannels(MallChannelsDTO mallRecDTO);

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:新建商城频道导航]
	 * </p>
	 */
	public ExecuteResult<String> addMallChannels(MallChannelsDTO mallRecDTO) ;

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:根据条件查询记录]
	 * </p>
	 */
	DataGrid<MallChannelsDTO> queryByCondition(MallChannelsDTO dto,Pager page);

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:根据ID删除记录]
	 * </p>
	 */
	ExecuteResult<String> delete(Long id);
}
