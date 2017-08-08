package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallRecommNavDTO;
import com.bjucloud.contentcenter.dto.MallRecommNavECMDTO;

public interface MallRecommNavService {
	/**
	 * @param publishFlag  展示标记,传入参数为1时查询当前可用于前台展示的轮播 0为未发布轮播 不传为全部
	 */
	public DataGrid<MallRecommNavDTO> queryMallRecNavList(String publishFlag, Pager page);
	/**
	 * 
	 * <p>Discription:</p>
	 * Created on 
	 * @param MallBannerDTO  
	 * @param publishFlag  展示标记,传入参数为1时查询当前可用于前台展示的轮播 0为未发布轮播 不传为全部
	 * @param page
	 * @return
	 * @author:
	 */
	public DataGrid<MallRecommNavDTO> queryMallRecNavList(MallRecommNavDTO mallRecommNavDTO, String publishFlag, Pager page);
	/**
	 * 
	* @Title: equeryMallRecNavList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param mallRecommNavDTO
	* @param publishFlag
	* @param page
	* @return    设定文件 
	* @return DataGrid<MallRecommNavDTO>    返回类型 
	* @throws 
	 */
	public DataGrid<MallRecommNavECMDTO> equeryMallRecNavList(MallRecommNavECMDTO mallRecommNavDTO, String publishFlag, Pager page);
	/**
	 * 
	 * <p>Discription:</p>
	 * Created on 
	 * @param id 轮播图ID
	 * @return
	 * @author:
	 */
	public MallRecommNavDTO getMallRecNavById(Integer id);
	
	/**
	 * 
	 * <p>Discription:[楼层导航id 查询对象和主题信息]</p>
	 */
	public MallRecommNavECMDTO getRecNavECMById(Integer id);
	/**
	 * 
	 * <p>Discription:</p>
	 * Created on 
	 * @param MallIconSerInDTO
	 * @return
	 * @author:
	 */
	public ExecuteResult<String> addMallRecNav(MallRecommNavDTO mallRecommNavDTO);
	/**
	 * 
	 * <p>Discription:[显示控制]</p>
	 * Created on 
	 * @param id
	 * @param publishFlag 上下架标记,0：上架  1：下架
	 * @return
	 * @author:
	 */
	public ExecuteResult<String> motifyMallRecNavStatus(Integer id, String publishFlag);
	
	/**
	 * 
	 * <p>Discription:[显示控制]</p>
	 * Created on 
	 * @param id
	 * @param 
	 * @return
	 * @author:
	 */
	public ExecuteResult<String> motifyMallRecNav(MallRecommNavDTO mallRecommNavDTO);
	/**
	 * 
	 * <p>Discription:[显示顺序修改]</p>
	 * Created on 
	 * @param id
	 * @param sortNum 重新设置后的排序号
	 * @return
	 * @author:
	 */
	public ExecuteResult<String> modifyMallRecNavSort(Integer id, Integer sortNum);
	
	/**
	 * 根据id删除服务图标
	 */
	public ExecuteResult<String> delete(Integer id);
}
