package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallIconSerDTO;
import com.bjucloud.contentcenter.dto.MallIconSerInDTO;


public interface MallIconSerService {
	/**
	 * 
	 * @param publishFlag  展示标记,传入参数为1时查询当前可用于前台展示的轮播 0为未发布轮播 不传为全部
	 */
	public DataGrid<MallIconSerDTO> queryMallIconSerList(String publishFlag, Pager page);
	/**
	 * 
	 * @param MallBannerDTO  
	 * @param publishFlag  展示标记,传入参数为1时查询当前可用于前台展示的轮播 0为未发布轮播 不传为全部
	 */
	public DataGrid<MallIconSerDTO> queryMallIconSerList(MallIconSerDTO mallIconSerDTO, String publishFlag, Pager page);
	/**
	 * @param id 轮播图ID
	 */
	public MallIconSerDTO getMallIconSerById(Integer id);
	/**
	 * 
	 * @param MallIconSerInDTO
	 */
	public ExecuteResult<String> addMallIconSer(MallIconSerInDTO mallIconSerInDTO );
	/**
	 * 
	 * @param MallIconSerInDTO
	 */
	//public ExecuteResult<String> modifyMallIconSer(MallIconSerInDTO mallIconSerInDTO );
	
	/**
	 * @param id
	 * @param publishFlag 上下架标记,0：上架  1：下架
	 */
	public ExecuteResult<String> motifyMallIconSerStatus(Integer id, String publishFlag);
	
	/**
	 * 
	 * <p>Discription:[显示控制]</p>
	 */
	public ExecuteResult<String> motifyMallIconSer(MallIconSerInDTO mallIconSerInDTO);
	/**
	 * @param id
	 * @param sortNum 重新设置后的排序号
	 */
	public ExecuteResult<String> modifyMallIconSerSort(Integer id, Integer sortNum);
	
	/**
	 * 根据id删除服务图标
	 */
	public ExecuteResult<String> delete(Integer id);

	/**
	 * 根据图标名称查询服务图标
	 * @param title
	 * @param id
     * @return
     */
	public ExecuteResult<Integer> queryMallIconSerByTitle(String title,Integer id);
}
