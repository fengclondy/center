package com.bjucloud.contentcenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.AdReportInDTO;
import com.bjucloud.contentcenter.dto.AdReportOutDTO;
import com.bjucloud.contentcenter.dto.MallBannerDTO;
import com.bjucloud.contentcenter.dto.MallBannerInDTO;

public interface MallBannerExportService {
	/**
	 * 
	 * <p>Discription:[前台轮播列表查询]</p>
	 * @param publishFlag  展示标记,传入参数为1时查询当前可用于前台展示的轮播 0为未发布轮播 不传为全部
	 * @param page
	 * @return
	 */
	public DataGrid<MallBannerDTO> queryMallBannerList(String publishFlag, Pager page);
	/**
	 * 
	 * <p>Discription:[前台轮播列表删除]</p>
	 * @param publishFlag  
	 * @param page
	 * @return
	 */
	public void delectMallBanner(Long id );
	/**
	 * 
	 * <p>Discription:[后台轮播列表查询]</p>
	 * @param MallBannerDTO  
	 * @param publishFlag  展示标记,传入参数为1时查询当前可用于前台展示的轮播 0为未发布轮播 不传为全部
	 * @param page
	 * @return
	 */
	public DataGrid<MallBannerDTO> queryMallBannerList(MallBannerDTO mallBannerDTO, String publishFlag, Pager page);
	/**
	 * 
	 * <p>Discription:[轮播详情查询]</p>
	 * @param id 轮播图ID
	 */
	public MallBannerDTO getMallBannerById(long id);
	/**
	 * 
	 * <p>Discription:[轮播添加.即时发布时，直接添加为上架轮播；定时发布时，添加的轮播是非上架状态  添加时候 请在 mallBannerInDTO 传入STATUS 状态之 1 上架 0 下架]</p>
	 * @param mallBannerDTO
	 * @return
	 */
	public ExecuteResult<String> addMallBanner(MallBannerInDTO mallBannerInDTO );
	/**
	 * 
	 * <p>Discription:[轮播修改.即时发布时，直接添加为上架轮播；定时发布时，添加的轮播是非上架状态]</p>
	 * @param mallBannerInDTO
	 * @return
	 */
	public ExecuteResult<String> modifyMallBanner(MallBannerInDTO mallBannerInDTO );
	
	/**
	 * 
	 * <p>Discription:[轮播上下架]</p>
	 * @param id
	 * @param publishFlag 上下架标记,0：上架  1：下架
	 * @return
	 */
	public ExecuteResult<String> motifyMallBannerStatus(Long id, String publishFlag);
	
	/**
	 * 
	 * <p>Discription:[轮播顺序修改]</p>
	 * @param id
	 * @param sortNum 重新设置后的排序号
	 * @return
	 */
	public ExecuteResult<String> modifyMallBannerSort(Long id, Integer sortNum);
	
	/**
	 * 
	* @Title: queryReportList 
	* @param adReportInDto
	* @param pager
	* @return    设定文件 
	* @return DataGrid<AdReportOutDto>    返回类型 
	* @throws 
	 */
	public DataGrid<AdReportOutDTO> queryReportList(AdReportInDTO adReportInDto,Pager pager);
	/**
	 * 
	 * <p>Discription:[查询定时轮播图列表]</p>
	 */
	public List<MallBannerDTO> queryTimeListDTO();

	/**
	 * 查询店铺轮播图
	 * @param mallBannerDTO
	 * @return
     */
	public  DataGrid<MallBannerDTO>  queryMobileShopBannerList(MallBannerDTO mallBannerDTO,Pager pager );

	/**
	 * 根据id查询手机店铺的轮播图
	 * @param id
	 * @return
     */
	public  MallBannerDTO  queryMobileShopBannerById(Long id);

	/**
	 * 根据id修改手机店铺的轮播图
	 * @param mallBannerDTO
	 * @return
     */
	public ExecuteResult<String> modifyMobileShopMallBanner(MallBannerDTO mallBannerDTO );


}
