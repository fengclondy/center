package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallNavDTO;
import com.bjucloud.contentcenter.dto.MallNavInDTO;

public interface MallNavService {
	/**
	 * 
	 * @param publishFlag  
	 * @param page
	 */
	public DataGrid<MallNavDTO> queryMallNavList(String publishFlag, Pager page);
	/**
	 * 
	 * @param MallNavDTO  
	 * @param publishFlag  展示标记,传入参数为1时查询当前可用于前台展示的轮播 0为未发布轮播 不传为全部
	 * @param page
	 */
	public DataGrid<MallNavDTO> queryMallNavList(MallNavDTO mallNavDTO, String publishFlag, Pager page);
	/**
	 * 
	 * @param id 轮播图ID
	 */
	public MallNavDTO getMallNavById(long id);
	/**
	 * 
	 * <p>Discription:[轮播添加.即时发布时，直接添加为上架轮播；定时发布时，添加的轮播是非上架状态  添加时候 请在 mallBannerInDTO 传入STATUS 状态之 1 上架 0 下架]</p>
	 * @param MallNavDTO
	 */
	public ExecuteResult<String> addMallNav(MallNavInDTO mallNavInDTO);
	/**
	 * 
	 * <p>Discription:[轮播修改.即时发布时，直接添加为上架轮播；定时发布时，添加的轮播是非上架状态]</p>
	 * @param MallIconSerInDTO
	 */
	//public ExecuteResult<String> modifyMallIconSer(MallIconSerInDTO mallIconSerInDTO );
	/**
	 * 删除导航
	 * @param id
	 * @return
	 */
	public ExecuteResult<String> delete(Long id);
	/**
	 * 
	 * <p>Discription:[显示控制]</p>
	 * @param id
	 * @param publishFlag 上下架标记,0：上架  1：下架
	 */
	public ExecuteResult<String> motifyMallNavStatus(Long id, String publishFlag);
	
	/**
	 * 
	 * <p>Discription:[显示顺序修改]</p>
	 * @param id
	 * @param sortNum 重新设置后的排序号
	 */
	public ExecuteResult<String> modifyMallNavSort(Long id, Integer sortNum);
	
	/**
	 * 
	 * <p>Discription:[显示顺序修改]</p>
	 * @param id
	 * @param sortNum 重新设置后的排序号
	 */
	public ExecuteResult<String> motifyMallNav(MallNavInDTO mallNavInDTO);
}
