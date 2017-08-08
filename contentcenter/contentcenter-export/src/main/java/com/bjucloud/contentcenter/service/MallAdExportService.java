package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.AdReportInDTO;
import com.bjucloud.contentcenter.dto.AdReportOutDTO;
import com.bjucloud.contentcenter.dto.MallAdCountDTO;
import com.bjucloud.contentcenter.dto.MallAdDTO;
import com.bjucloud.contentcenter.dto.MallAdInDTO;
import com.bjucloud.contentcenter.dto.MallAdQueryDTO;


/**
 * 
 * <p>Description: 商城广告服务接口</p>
 */
public interface MallAdExportService {
	/**
	 * 
	 * <p>Discription:商城广告查询</p>
	 */
	public DataGrid<MallAdDTO> queryMallAdList(Pager page, MallAdQueryDTO mallAdQueryDTO);
	
	/**
	 * 
	 * <p>Discription:广告详情查询]</p>
	 */
	public MallAdDTO getMallAdById(Long id);
	

	/**
	 * <p>Discription:广告添加</p>
	 */
	public ExecuteResult<String> addMallAd(MallAdInDTO mallAdInDTO);

	/**
	 * <p>Discription:广告删除</p>
	 */
	public ExecuteResult<String> delById(Long id);

	/**
	 * <p>Discription:广告修改</p>
	 */
	public ExecuteResult<String> modifyMallBanner(MallAdInDTO mallAdInDTO);


	/**
	 * <p>Discription:广告上下架</p>
	 * @param id
	 * @param publishFlag
	 */
	public ExecuteResult<String> modifyMallAdStatus(Long id,String publishFlag);
	
	/**
	 * 广告增加点击量，链接存在则+1，不存在则生成新记录
	 *
	 * @param mallAdCountDTO - 广告ID
	 * @param adTableType - AdTableTypeEnums 类型
	 * @return 
	 */
	ExecuteResult<MallAdCountDTO> saveMallAdCount(Long mallAdId,Long adTableType) throws Exception;
	
	/**
	 * 根据ID查询点击量
	 * 
	 * @param id
	 * @return
	 */
	MallAdCountDTO findMallAdCountById(long id);
	
	/**
	 * 根据条件查询广告点击量信息
	 *
	 * @param mallAdCount - 条件，可空
	 * @param pager - 分页，可空
	 * @return 
	 */
	DataGrid<MallAdCountDTO> findAdCountList(MallAdCountDTO mallAdCountDTO,@SuppressWarnings("rawtypes") Pager pager);
	
	/**
	 * 查询广告点击量报表列表
	 * @param adReportInDto
	 * @param pager
	 * @return
	 */
	public DataGrid<AdReportOutDTO> queryReportList(AdReportInDTO adReportInDto,Pager pager);

	/**
	 * 获得微商城的广告列表
	 * @param pager
	 * @param mallAdQueryDTO
     * @return
     */
	public DataGrid<MallAdDTO> getMobileShopAdList(Pager pager, MallAdQueryDTO mallAdQueryDTO);

	/**
	 * 通过id查询微商城的店铺广告
	 * @param id
	 * @return
     */
	public MallAdDTO getMobileShopAdById(Long id);

	/**
	 * 修改微商城的广告
	 * @param mallAdDTO
	 * @return
     */
	public ExecuteResult<String> modifyMobileShopAd(MallAdDTO mallAdDTO );

}
